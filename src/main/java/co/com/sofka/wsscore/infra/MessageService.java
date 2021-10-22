package co.com.sofka.wsscore.infra;

import co.com.sofka.wsscore.domain.generic.Command;
import co.com.sofka.wsscore.infra.generic.CommandSerializer;
import com.rabbitmq.client.*;
import io.quarkiverse.rabbitmqclient.RabbitMQClient;
import io.quarkus.runtime.StartupEvent;
import io.vertx.mutiny.core.eventbus.EventBus;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;

@ApplicationScoped
public class MessageService {
    private static final String EXCHANGE = "executor";
    private static final String QUEUE = "executor.queue";

    private final EventBus bus;
    private final RabbitMQClient rabbitMQClient;

    private Channel channel;

    public MessageService(EventBus bus, RabbitMQClient rabbitMQClient) {
        this.bus = bus;
        this.rabbitMQClient = rabbitMQClient;
    }

    public void onApplicationStart(@Observes StartupEvent event) {
        setupQueues();
        setupReceiving();
    }

    private void setupQueues() {
        try {
            Connection connection = rabbitMQClient.connect();
            channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE, BuiltinExchangeType.TOPIC, true);
            channel.queueDeclare(QUEUE, true, false, false, null);
            channel.queueBind(QUEUE, EXCHANGE, "executor-command");
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void setupReceiving() {
        try {
            channel.basicConsume(QUEUE, true, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                   var message = new String(body, StandardCharsets.UTF_8);
                    try {
                        var command = CommandSerializer.instance()
                                .deserialize(message, Class.forName(properties.getContentType()));
                        bus.publish("executor-command", command);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            });
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void send(Command command) {
        try {
            var message = CommandSerializer.instance().serialize(command);
            var props = new AMQP.BasicProperties.Builder().contentType(command.getClass().getTypeName()).build();
            channel.basicPublish(EXCHANGE, "executor-command", props, message.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
package co.com.sofka.wsscore.infra;

import co.com.sofka.wsscore.usecases.ExtractScoreUseCase;
import com.rabbitmq.client.*;
import io.quarkiverse.rabbitmqclient.RabbitMQClient;
import io.quarkus.runtime.StartupEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;

@ApplicationScoped
public class MessageService {
    private static final String EXCHANGE = "executor";
    private static final String QUEUE = "executor.queue";

    private final ExtractScoreUseCase extractScoreUseCase;
    private final RabbitMQClient rabbitMQClient;

    private Channel channel;

    public MessageService(ExtractScoreUseCase extractScoreUseCase, RabbitMQClient rabbitMQClient) {
        this.extractScoreUseCase = extractScoreUseCase;
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
            channel.queueBind(QUEUE, EXCHANGE, "code");
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void setupReceiving() {
        try {
            channel.basicConsume(QUEUE, true, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                   var pathIdentity = new String(body, StandardCharsets.UTF_8);
                    System.out.println(pathIdentity);
                    try {
                        var extractor = extractScoreUseCase.apply(pathIdentity);
                        System.out.println(extractor);
                    } catch (RuntimeException e){
                        e.printStackTrace();
                    }

                }
            });
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void send(String message) {
        try {
            System.out.println("send...");
            channel.basicPublish(EXCHANGE, "code", null, message.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
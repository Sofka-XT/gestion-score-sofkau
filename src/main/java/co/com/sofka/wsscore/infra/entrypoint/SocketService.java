package co.com.sofka.wsscore.infra.entrypoint;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import co.com.sofka.wsscore.domain.generic.DomainEvent;
import co.com.sofka.wsscore.infra.generic.EventSerializer;
import org.jboss.logging.Logger;

@ServerEndpoint("/retrieve/{correlationId}")
@ApplicationScoped
public class SocketService {

    private static final Logger LOG = Logger.getLogger(SocketService.class);

    Map<String, Map<String, Session>> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("correlationId") String correlationId) {
        var map = sessions.getOrDefault(correlationId, new HashMap<>());
        map.put(session.getId(), session);
        sessions.put(correlationId, map);
    }

    @OnClose
    public void onClose(Session session, @PathParam("correlationId") String correlationId) {
        sessions.get(correlationId).remove(session.getId());
    }

    @OnError
    public void onError(Session session, @PathParam("correlationId") String correlationId, Throwable throwable) {
        sessions.get(correlationId).remove(session.getId());
        LOG.error("onError", throwable);
    }

    public void send(String correlationId, DomainEvent event){
        var message = EventSerializer.instance().serialize(event);
        if(sessions.containsKey(correlationId)){
            sessions.get(correlationId).values()
                    .forEach(session -> session.getAsyncRemote().sendObject(message));
        }
    }

}
package co.com.sofka.wsscore.domain.generic;

import co.com.sofka.wsscore.infra.generic.DeserializeException;
import co.com.sofka.wsscore.infra.generic.EventSerializer;
import co.com.sofka.wsscore.infra.generic.StoredEventSerializer;
import com.google.gson.Gson;

import java.util.Date;

public final class StoredEvent {


    private String eventBody;
    private Date occurredOn;
    private String typeName;
    protected Gson gson;

    public StoredEvent() {

    }


    public StoredEvent(String typeName, Date occurredOn, String eventBody) {
        this.setEventBody(eventBody);
        this.setOccurredOn(occurredOn);
        this.setTypeName(typeName);
    }


    public static StoredEvent wrapEvent(DomainEvent domainEvent) {
        return new StoredEvent(domainEvent.getClass().getCanonicalName(),
                new Date(),
                EventSerializer.instance().serialize(domainEvent)
        );
    }


    public String getEventBody() {
        return eventBody;
    }


    public void setEventBody(String eventBody) {
        this.eventBody = eventBody;
    }


    public Date getOccurredOn() {
        return occurredOn;
    }


    public void setOccurredOn(Date occurredOn) {
        this.occurredOn = occurredOn;
    }


    public String getTypeName() {
        return typeName;
    }


    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }


    public DomainEvent deserializeEvent() {
        try {
            return EventSerializer
                    .instance()
                    .deserialize(this.getEventBody(), Class.forName(this.getTypeName()));
        } catch (ClassNotFoundException e) {
            throw new DeserializeException(e.getCause());
        }
    }

    @Override
    public String toString() {
        return StoredEventSerializer.instance().serialize(this);
    }


}
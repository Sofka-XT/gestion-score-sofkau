package co.com.sofka.wsscore.domain.generic;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public abstract class DomainEvent implements Serializable {
    private final String type;
    private final Instant instant;
    private String aggregateId;
    private final String id;

    public DomainEvent(String type){
        this.id = UUID.randomUUID().toString();
        this.type = type;
        this.instant = Instant.now();
    }

    public String getType() {
        return type;
    }


    public Instant getInstant() {
        return instant;
    }


    public  void setAggregateId(String aggregateId){
        this.aggregateId = aggregateId;
    }

    public String getId() {
        return id;
    }

    public String getAggregateId() {
        return aggregateId;
    }
}

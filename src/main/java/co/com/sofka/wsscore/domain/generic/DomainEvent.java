package co.com.sofka.wsscore.domain.generic;

import java.time.Instant;
import java.util.UUID;

public abstract class DomainEvent {
    private String type;
    private Instant instant;
    private String aggregateId;
    private final String id;

    public DomainEvent(){
        this.id = UUID.randomUUID().toString();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Instant getInstant() {
        return instant;
    }

    public void setInstant(Instant instant) {
        this.instant = instant;
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

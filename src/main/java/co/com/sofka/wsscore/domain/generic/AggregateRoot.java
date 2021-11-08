package co.com.sofka.wsscore.domain.generic;

import java.util.List;

public abstract class AggregateRoot {

    private final String id;
    private final ChangeEventSubscriber changeEventSubscriber;

    protected AggregateRoot(String id) {
        this.id = id;
        this.changeEventSubscriber = new ChangeEventSubscriber();
    }

    protected final void subscribe(EventChange eventChange) {
        changeEventSubscriber.subscribe(eventChange);
    }

    protected ChangeEventSubscriber.ChangeApply appendChange(DomainEvent event) {
        return appendChange(event, id);
    }

    protected ChangeEventSubscriber.ChangeApply appendChange(DomainEvent event, String correlationId) {
        event.setAggregateId(id);
        event.setCorrelationId(correlationId);
        return changeEventSubscriber.appendChange(event);
    }

    protected void applyEvent(DomainEvent domainEvent) {
        changeEventSubscriber.applyEvent(domainEvent);
    }

    public List<DomainEvent> getUncommittedChanges() {
        return List.copyOf(changeEventSubscriber.getChanges());
    }
}

package co.com.sofka.wsscore.domain.generic;

import java.util.*;
import java.util.function.Consumer;

public class ChangeEventSubscriber {
    private final List<DomainEvent> changes = new LinkedList<>();
    private final Set<Consumer<? super DomainEvent>> observables = new HashSet<>();

    public List<DomainEvent> getChanges() {
        return changes;
    }


    public final ChangeApply appendChange(DomainEvent event) {
        changes.add(event);
        return () -> applyEvent(event);
    }


    public final void subscribe(EventChange eventChange) {
        this.observables.addAll(eventChange.behaviors);
    }


    public final void applyEvent(DomainEvent domainEvent) {
        observables.forEach(consumer -> {
            try {
                consumer.accept(domainEvent);
            } catch (ClassCastException ignored) {
            }
        });
    }



    @FunctionalInterface
    public interface ChangeApply {
        /**
         * Apply.
         */
        void apply();
    }

}
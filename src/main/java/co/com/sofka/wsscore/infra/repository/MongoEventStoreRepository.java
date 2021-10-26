package co.com.sofka.wsscore.infra.repository;

import co.com.sofka.wsscore.domain.generic.DomainEvent;
import co.com.sofka.wsscore.domain.generic.EventStoreRepository;
import co.com.sofka.wsscore.domain.generic.StoredEvent;

import javax.inject.Singleton;
import java.util.List;

@Singleton
public class MongoEventStoreRepository implements EventStoreRepository {
    @Override
    public List<DomainEvent> getEventsBy(String aggregateName, String aggregateRootId) {
        return null;
    }

    @Override
    public void saveEvent(String aggregateName, String aggregateRootId, StoredEvent storedEvent) {
    }
}

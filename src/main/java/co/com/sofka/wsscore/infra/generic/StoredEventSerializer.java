package co.com.sofka.wsscore.infra.generic;


import co.com.sofka.wsscore.domain.generic.StoredEvent;

public final class StoredEventSerializer extends AbstractSerializer {

    private static StoredEventSerializer eventSerializer;

    private StoredEventSerializer() {
        super();
    }


    public static synchronized StoredEventSerializer instance() {
        if (StoredEventSerializer.eventSerializer == null) {
            StoredEventSerializer.eventSerializer = new StoredEventSerializer();
        }
        return StoredEventSerializer.eventSerializer;
    }


    public StoredEvent deserialize(String aSerialization, Class<StoredEvent> aType) {
        return gson.fromJson(aSerialization, aType);
    }


    public String serialize(StoredEvent object) {
        return gson.toJson(object);
    }

}
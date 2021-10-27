package co.com.sofka.wsscore.infra.materialize;

import co.com.sofka.wsscore.domain.program.event.ProgramCreated;
import com.mongodb.client.MongoClient;
import io.quarkus.vertx.ConsumeEvent;
import org.bson.Document;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class ProgramHandle {
    private final MongoClient mongoClient;

    public ProgramHandle(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }


    @ConsumeEvent(value = "sofkau.program.programcreated", blocking = true)
    void consumeProgramCreated(ProgramCreated event) {
        Map<String, Object> document = new HashMap<>();
        document.put("_id", event.getAggregateId());
        document.put("name", event.getName());
        mongoClient.getDatabase("queries").getCollection("program")
                .insertOne(new Document(document));
    }


}
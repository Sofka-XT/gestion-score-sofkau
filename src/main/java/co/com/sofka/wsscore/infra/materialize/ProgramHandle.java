package co.com.sofka.wsscore.infra.materialize;

import co.com.sofka.wsscore.domain.program.event.CourseAssigned;
import co.com.sofka.wsscore.domain.program.event.ProgramCreated;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.model.Filters;
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

        mongoClient.getDatabase("queries")
                .getCollection("program")
                .insertOne(new Document(document));
    }

    @ConsumeEvent(value = "sofkau.program.courseassigned", blocking = true)
    void consumeProgramCreated(CourseAssigned event) {
        BasicDBObject document = new BasicDBObject();
        document.put("courses."+event.getCourseId()+".name", event.getName());
        document.put("courses."+event.getCourseId()+".categories", event.getCategories());

        BasicDBObject updateObject = new BasicDBObject();
        updateObject.put("$set", document);

        mongoClient.getDatabase("queries")
                .getCollection("program")
                .updateOne( Filters.eq("_id", event.getAggregateId()), updateObject);
    }


}
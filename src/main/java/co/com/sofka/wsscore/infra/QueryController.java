package co.com.sofka.wsscore.infra;

import com.mongodb.client.MongoClient;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/api")
public class QueryController {
    private final MongoClient mongoClient;

    public QueryController(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/program/{id}")
    public Response get(@PathParam("id") String programId) {
        List<Document> documentList = new ArrayList<>();
        mongoClient.getDatabase("queries")
                .getCollection("program")
                .find(Filters.eq("_id", programId))
                .forEach(documentList::add);
        return Response.ok(documentList).build();
    }
}

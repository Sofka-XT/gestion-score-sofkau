package co.com.sofka.wsscore.infra;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/extract")
public class ScoreResource {


    private final MessageService messageService;

    public ScoreResource(MessageService messageService){
        this.messageService = messageService;
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/listtestusers/{code}")
    public Response listtestusers(@PathParam("code") String code) {
        messageService.send("listtestusers/id:"+code);
        return Response.ok().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/listassignmentusers/{code}")
    public Response listassignmentusers(@PathParam("code") String code) {
        messageService.send("listassignmentusers/id:"+code);
        return Response.ok().build();
    }
}
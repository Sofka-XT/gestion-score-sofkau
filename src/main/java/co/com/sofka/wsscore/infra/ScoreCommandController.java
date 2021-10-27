package co.com.sofka.wsscore.infra;


import co.com.sofka.wsscore.domain.generic.Command;
import co.com.sofka.wsscore.domain.program.command.AssignScoreCommand;
import co.com.sofka.wsscore.domain.program.command.CreateProgramCommand;
import org.jboss.resteasy.annotations.Body;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api")
public class ScoreCommandController {


    private final MessageService messageService;

    public ScoreCommandController(MessageService messageService){
        this.messageService = messageService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/assignScore")
    public Response executor(AssignScoreCommand command) {
        System.out.println("Executor command "+command.getType());
        messageService.send(command);
        return Response.ok().build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/createProgram")
    public Response executor(CreateProgramCommand command) {
        System.out.println("Executor command "+command.getType());
        messageService.send(command);
        return Response.ok().build();
    }


}
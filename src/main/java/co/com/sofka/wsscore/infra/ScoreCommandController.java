package co.com.sofka.wsscore.infra;


import co.com.sofka.wsscore.domain.generic.Command;
import co.com.sofka.wsscore.domain.program.command.AssignScoreCommand;

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
    @Path("/command")
    public Response executor(AssignScoreCommand command) {
        messageService.send(command);
        return Response.ok().build();
    }

}
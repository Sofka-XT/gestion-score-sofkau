package co.com.sofka.wsscore.infra;


import co.com.sofka.wsscore.domain.generic.Command;
import co.com.sofka.wsscore.domain.program.command.AddCourseCommand;
import co.com.sofka.wsscore.domain.program.command.AssignScoreCommand;
import co.com.sofka.wsscore.domain.program.command.CreateProgramCommand;
import org.jboss.resteasy.annotations.Body;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api")
public class CommandController {


    private final MessageService messageService;

    public CommandController(MessageService messageService){
        this.messageService = messageService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/assignScore")
    public Response executor(AssignScoreCommand command) {
        messageService.send(command);
        return Response.ok().build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/createProgram")
    public Response executor(CreateProgramCommand command) {
        messageService.send(command);
        return Response.ok().build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/addCourse")
    public Response executor(AddCourseCommand command) {
        messageService.send(command);
        return Response.ok().build();
    }

}
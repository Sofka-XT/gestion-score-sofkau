package co.com.sofka.wsscore.infra;

import co.com.sofka.wsscore.domain.Score;
import co.com.sofka.wsscore.usecases.ExtractScoreUseCase;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/extract")
public class ScoreResource {

    private final ExtractScoreUseCase extractScoreUseCase;


    public ScoreResource(ExtractScoreUseCase extractScoreUseCase){
        this.extractScoreUseCase = extractScoreUseCase;
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/listtestusers/{code}")
    public List<Score> listtestusers(@PathParam("code") String code) {
        return extractScoreUseCase.apply("listtestusers/id:"+code);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/listassignmentusers/{code}")
    public List<Score> listassignmentusers(@PathParam("code") String code) {
        return extractScoreUseCase.apply("listassignmentusers/id:"+code);
    }
}
package co.com.sofka.wsscore.infra.handle;

import co.com.sofka.wsscore.domain.program.command.AssignScoreCommand;
import co.com.sofka.wsscore.infra.generic.UseCaseHandle;
import co.com.sofka.wsscore.usecases.ExtractScoreUseCase;
import io.quarkus.vertx.ConsumeEvent;

import javax.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class AssignScoreUseCaseHandle extends UseCaseHandle {
    private final ExtractScoreUseCase extractScoreUseCase;

    public AssignScoreUseCaseHandle(ExtractScoreUseCase extractScoreUseCase) {
        this.extractScoreUseCase = extractScoreUseCase;
    }

    @ConsumeEvent(value = "sofkau.program.assignscore")
    void consumeBlocking(AssignScoreCommand command) {
        var events = extractScoreUseCase.apply(command);
        saveProgram(command.getProgramId(), events);
    }


}
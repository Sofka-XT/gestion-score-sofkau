package co.com.sofka.wsscore.infra.handle;

import co.com.sofka.wsscore.domain.program.command.AssignScoreCommand;
import co.com.sofka.wsscore.usecases.ExtractScoreUseCase;
import io.quarkus.vertx.ConsumeEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class UseCaseHandle {
    private final ExtractScoreUseCase extractScoreUseCase;

    @Inject
    public UseCaseHandle(ExtractScoreUseCase extractScoreUseCase) {
        this.extractScoreUseCase = extractScoreUseCase;
    }

    @ConsumeEvent(value = "executor-command", blocking = true)
    void consumeBlocking(AssignScoreCommand command) {
        var events = extractScoreUseCase.apply(command);
        System.out.println(events);
    }
}
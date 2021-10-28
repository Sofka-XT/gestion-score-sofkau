package co.com.sofka.wsscore.infra.handle;

import co.com.sofka.wsscore.domain.program.command.CreateProgramCommand;
import co.com.sofka.wsscore.infra.generic.UseCaseHandle;
import co.com.sofka.wsscore.usecases.CreateProgramUseCase;
import io.quarkus.vertx.ConsumeEvent;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CreateProgramUseCaseHandle extends UseCaseHandle {

    private final CreateProgramUseCase createProgramUseCase;

    public CreateProgramUseCaseHandle(CreateProgramUseCase createProgramUseCase) {
        this.createProgramUseCase = createProgramUseCase;
    }

    @ConsumeEvent(value = "sofkau.program.createprogram", blocking = true)
    void consumeBlocking(CreateProgramCommand command) {
        var events = createProgramUseCase.apply(command);
        saveProgram(command.getProgramId(), events);
    }
}
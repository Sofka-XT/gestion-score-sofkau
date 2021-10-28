package co.com.sofka.wsscore.infra.handle;

import co.com.sofka.wsscore.domain.program.command.AddCourseCommand;

import co.com.sofka.wsscore.infra.generic.UseCaseHandle;
import co.com.sofka.wsscore.usecases.AddCourseUseCase;

import io.quarkus.vertx.ConsumeEvent;

import javax.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class AddCourseUseCaseHandle extends UseCaseHandle {
    private final AddCourseUseCase addCourseUseCase;

    public AddCourseUseCaseHandle(AddCourseUseCase addCourseUseCase) {
        this.addCourseUseCase = addCourseUseCase;
    }

    @ConsumeEvent(value = "sofkau.program.addcourse", blocking = true)
    void consumeBlocking(AddCourseCommand command) {
        var events = addCourseUseCase.apply(command);
        saveProgram(command.getProgramId(), events);
    }


}
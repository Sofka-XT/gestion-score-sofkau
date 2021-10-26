package co.com.sofka.wsscore.usecases;

import co.com.sofka.wsscore.domain.generic.DomainEvent;
import co.com.sofka.wsscore.domain.program.Program;
import co.com.sofka.wsscore.domain.program.command.CreateProgramCommand;

import java.util.List;
import java.util.function.Function;

public class CreateProgramUseCase  implements Function<CreateProgramCommand, List<DomainEvent>> {
    @Override
    public List<DomainEvent> apply(CreateProgramCommand command) {
        var program = new Program(command.getProgramId(), command.getName());
        return program.getUncommittedChanges();
    }
}

package co.com.sofka.wsscore.usecases;

import co.com.sofka.wsscore.domain.generic.DomainEvent;
import co.com.sofka.wsscore.domain.generic.EventStoreRepository;
import co.com.sofka.wsscore.domain.program.Program;
import co.com.sofka.wsscore.domain.program.command.AddCourseCommand;

import javax.enterprise.context.Dependent;
import java.util.List;
import java.util.function.Function;

@Dependent
public class AddCourseUseCase implements Function<AddCourseCommand, List<DomainEvent>> {

    private final EventStoreRepository repository;

    public AddCourseUseCase(EventStoreRepository repository){
        this.repository = repository;
    }

    @Override
    public List<DomainEvent> apply(AddCourseCommand addCourseCommand) {
        var program = Program.from(
                addCourseCommand.getProgramId(), repository.getEventsBy("program", addCourseCommand.getProgramId())
        );
        program.addCourse(addCourseCommand.getCourseId(), addCourseCommand.getCategories());
        return program.getUncommittedChanges();
    }
}

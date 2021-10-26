package co.com.sofka.wsscore.infra.handle;

import co.com.sofka.wsscore.domain.generic.DomainEvent;
import co.com.sofka.wsscore.domain.generic.EventStoreRepository;
import co.com.sofka.wsscore.domain.generic.StoredEvent;
import co.com.sofka.wsscore.domain.program.command.AssignScoreCommand;
import co.com.sofka.wsscore.domain.program.command.CreateProgramCommand;
import co.com.sofka.wsscore.infra.generic.EventSerializer;
import co.com.sofka.wsscore.usecases.CreateProgramUseCase;
import co.com.sofka.wsscore.usecases.ExtractScoreUseCase;
import io.quarkus.vertx.ConsumeEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

@ApplicationScoped
public class UseCaseHandle {
    private final EventStoreRepository repository;
    private final ExtractScoreUseCase extractScoreUseCase;
    private final CreateProgramUseCase createProgramUseCase;

    @Inject
    public UseCaseHandle(EventStoreRepository repository, ExtractScoreUseCase extractScoreUseCase, CreateProgramUseCase createProgramUseCase) {
        this.repository = repository;
        this.extractScoreUseCase = extractScoreUseCase;
        this.createProgramUseCase = createProgramUseCase;
    }

    @ConsumeEvent(value = "executor-command", blocking = true)
    void consumeBlocking(AssignScoreCommand command) {
        var events = extractScoreUseCase.apply(command);
        saveProgram(command.getProgramId(), events);
        //publicar(events)
    }



    @ConsumeEvent(value = "executor-command", blocking = true)
    void consumeBlocking(CreateProgramCommand command) {
        var events = createProgramUseCase.apply(command);
        saveProgram(command.getProgramId(), events);
        //publicar(events)
    }

    private void saveProgram(String programId, List<DomainEvent> events) {
        events.stream().map(event -> {
            String eventBody = EventSerializer.instance().serialize(event);
            return new StoredEvent(event.getClass().getTypeName(), new Date(), eventBody);
        }).forEach(storedEvent -> repository.saveEvent("program", programId, storedEvent));
    }
}
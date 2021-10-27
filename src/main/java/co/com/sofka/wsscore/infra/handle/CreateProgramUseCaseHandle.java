package co.com.sofka.wsscore.infra.handle;

import co.com.sofka.wsscore.domain.generic.DomainEvent;
import co.com.sofka.wsscore.domain.generic.EventStoreRepository;
import co.com.sofka.wsscore.domain.generic.StoredEvent;
import co.com.sofka.wsscore.domain.program.command.AssignScoreCommand;
import co.com.sofka.wsscore.domain.program.command.CreateProgramCommand;
import co.com.sofka.wsscore.infra.MessageService;
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
public class CreateProgramUseCaseHandle {
    private final EventStoreRepository repository;
    private final CreateProgramUseCase createProgramUseCase;
    private final MessageService messageService;

    public CreateProgramUseCaseHandle(EventStoreRepository repository, CreateProgramUseCase createProgramUseCase, MessageService messageService) {
        this.repository = repository;
        this.createProgramUseCase = createProgramUseCase;
        this.messageService = messageService;
    }

    @ConsumeEvent(value = "sofkau.program.createprogram", blocking = true)
    void consumeBlocking(CreateProgramCommand command) {
        var events = createProgramUseCase.apply(command);
        saveProgram(command.getProgramId(), events);
        events.forEach(messageService::send);
    }

    private void saveProgram(String programId, List<DomainEvent> events) {
        events.stream().map(event -> {
            String eventBody = EventSerializer.instance().serialize(event);
            return new StoredEvent(event.getClass().getTypeName(), new Date(), eventBody);
        }).forEach(storedEvent -> repository.saveEvent("program", programId, storedEvent));
    }
}
package co.com.sofka.wsscore.domain.program.event;

import co.com.sofka.wsscore.domain.generic.DomainEvent;

public class ProgramCreated extends DomainEvent {

    private final String name;

    public ProgramCreated(String name) {
        super("sofkau.program.programcreated");
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

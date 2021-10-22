package co.com.sofka.wsscore.infra.generic;

import co.com.sofka.wsscore.domain.generic.Command;
import co.com.sofka.wsscore.domain.generic.DomainEvent;

import java.lang.reflect.Type;

public final class CommandSerializer extends AbstractSerializer {

    private static CommandSerializer commandSerializer;

    private CommandSerializer() {
        super();
    }
    
    public static synchronized CommandSerializer instance() {
        if (CommandSerializer.commandSerializer == null) {
            CommandSerializer.commandSerializer = new CommandSerializer();
        }
        return CommandSerializer.commandSerializer;
    }

    
    public <T extends Command> T deserialize(String aSerialization, final Class<?> aType) {
        return gson.fromJson(aSerialization, (Type) aType);
    }

    public String serialize(Command object) {
        return gson.toJson(object);
    }

}
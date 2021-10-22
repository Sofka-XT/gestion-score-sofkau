package co.com.sofka.wsscore.domain.generic;

import java.time.Instant;

public abstract class Command {
    private String type;
    private Instant instant;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Instant getInstant() {
        return instant;
    }

    public void setInstant(Instant instant) {
        this.instant = instant;
    }

}

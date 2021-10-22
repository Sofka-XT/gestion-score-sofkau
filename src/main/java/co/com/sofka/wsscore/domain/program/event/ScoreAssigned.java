package co.com.sofka.wsscore.domain.program.event;

import co.com.sofka.wsscore.domain.generic.DomainEvent;

import java.util.Date;

public class ScoreAssigned extends DomainEvent {
    private final String user;
    private final String value;
    private final Date date;

    public ScoreAssigned(String user, String value, Date date) {
        this.user = user;
        this.value = value;
        this.date = date;
    }

    public String getValue() {
        return value;
    }

    public Date getDate() {
        return date;
    }

    public String getUser() {
        return user;
    }
}

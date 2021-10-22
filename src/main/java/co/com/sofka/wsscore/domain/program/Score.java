package co.com.sofka.wsscore.domain.program;

import java.util.Date;
import java.util.Objects;

public class Score {
    private final String user;
    private final String value;
    private final Date date;

    public Score(String user, String value, Date date) {
        this.user = Objects.requireNonNull(user);
        this.value = Objects.requireNonNull(value);
        this.date = Objects.requireNonNull(date);
    }

    public String user() {
        return user;
    }

    public Date date() {
        return date;
    }

    public String value() {
        return value;
    }
}

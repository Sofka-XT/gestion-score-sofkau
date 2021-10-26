package co.com.sofka.wsscore.domain.program.event;

import co.com.sofka.wsscore.domain.generic.DomainEvent;

import java.util.Date;

public class ScoreAssigned extends DomainEvent {
    private final String user;
    private final String courseId;
    private final String category;
    private final String value;
    private final Date date;

    public ScoreAssigned(String user, String courseId, String category, String value, Date date) {
        super("sofkau.program.scoreassigned");
        this.user = user;
        this.courseId = courseId;
        this.category = category;
        this.value = value;
        this.date = date;
    }

    public String getUser() {
        return user;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCategory() {
        return category;
    }

    public String getValue() {
        return value;
    }

    public Date getDate() {
        return date;
    }




}

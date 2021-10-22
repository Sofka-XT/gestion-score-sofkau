package co.com.sofka.wsscore.domain.program;

import co.com.sofka.wsscore.domain.generic.AggregateRoot;
import co.com.sofka.wsscore.domain.generic.DomainEvent;
import co.com.sofka.wsscore.domain.generic.EventChange;
import co.com.sofka.wsscore.domain.program.event.CourseAndCategoryAssigned;
import co.com.sofka.wsscore.domain.program.event.ScoreAssigned;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;


public class Program extends AggregateRoot implements EventChange {
    private  String course;
    private  String category;
    private Map<String, Score> scores;

    public Program(String id, String course, String category){
        super(id);
        appendChange(new CourseAndCategoryAssigned(course, category)).apply();
    }

    private Program(String id){
        super(id);
        subscribe(this);
        listener((Consumer<CourseAndCategoryAssigned>) event -> {
            this.course = event.getCourse();
            this.category = event.getCategory();
        });
        listener((Consumer<ScoreAssigned>) event -> {
            this.scores.put(event.getUser(), new Score(
                    event.getUser(), event.getValue(), event.getDate()
            ));
        });

    }

    public static Program from(String id, List<DomainEvent> events){
        var program = new Program(id);
        events.forEach(program::applyEvent);
        return program;
    }

    public void assignScore(String user, String value, Date date){
        appendChange(new ScoreAssigned(user, value, date)).apply();
    }

    public String course() {
        return course;
    }

    public String category() {
        return category;
    }

    public Map<String, Score> scores() {
        return scores;
    }
}
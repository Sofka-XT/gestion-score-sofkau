package co.com.sofka.wsscore.domain.program;

import co.com.sofka.wsscore.domain.generic.AggregateRoot;
import co.com.sofka.wsscore.domain.generic.DomainEvent;
import co.com.sofka.wsscore.domain.generic.EventChange;
import co.com.sofka.wsscore.domain.program.event.CourseAssigned;
import co.com.sofka.wsscore.domain.program.event.ProgramCreated;
import co.com.sofka.wsscore.domain.program.event.ScoreAssigned;

import java.util.*;


public class Program extends AggregateRoot implements EventChange {
    private Map<String, Course> courses;
    private Map<String, Score> scores;
    private String name;

    public Program(String programId, String name){
        super(programId);
        appendChange(new ProgramCreated(name)).apply();
    }


    public void addCourse(String courseId, String name, List<String> categories){
        appendChange(new CourseAssigned(courseId, name, categories)).apply();
    }

    public void assignScore(String user, String courseId, String category, String value, Date date){
        appendChange(new ScoreAssigned(user, courseId, category, value, date)).apply();
    }


    private Program(String id){
        super(id);
        subscribe(this);
        listener((ProgramCreated event)-> {
          this.name = event.getName();
          this.scores = new HashMap<>();
          this.courses =  new HashMap<>();
        });
        listener((CourseAssigned event) -> {
            var course =  new Course(event.getCourseId(), event.getName());
            event.getCategories().forEach(course::addCategory);
            courses.put(event.getCourseId(), course);
        });
        listener((ScoreAssigned event) -> {
            var scoreId = event.getCourseId()+event.getCategory()+event.getUser();
            this.scores.put(scoreId, new Score(
                    scoreId, event.getUser(), event.getValue(), event.getDate()
            ));
        });

    }

    public static Program from(String id, List<DomainEvent> events){
        var program = new Program(id);
        events.forEach(program::applyEvent);
        return program;
    }

    public String name() {
        return name;
    }

    public Course getCoursesById(String courseId) {
        return courses.get(courseId);
    }

    public Score getScoreByCourseIdAndCategoryAndUser(String courseId, String category, String user){
        return this.scores.get(courseId+category+user);
    }

    public Map<String, Score> scores() {
        return scores;
    }
}
package co.com.sofka.wsscore.domain.program;

import co.com.sofka.wsscore.domain.generic.AggregateRoot;
import co.com.sofka.wsscore.domain.generic.DomainEvent;
import co.com.sofka.wsscore.domain.generic.EventChange;
import co.com.sofka.wsscore.domain.program.event.CourseAssigned;
import co.com.sofka.wsscore.domain.program.event.ProgramCreated;
import co.com.sofka.wsscore.domain.program.event.ScoreAssigned;

import java.util.*;


public class Program extends AggregateRoot  {
    protected Map<String, Course> courses;
    protected Map<String, Score> scores;
    protected String name;

    public Program(String programId, String name){
        super(programId);
        subscribe(new ProgramEventChange(this));
        appendChange(new ProgramCreated(name), "xxx").apply();
    }


    public void addCourse(String courseId, String name, List<String> categories){
        appendChange(new CourseAssigned(courseId, name, categories), "xxx").apply();
    }

    public void assignScore(String user, String courseId, String category, String value, Date date){
        appendChange(new ScoreAssigned(user, courseId, category, value, date), "xxx").apply();
    }


    private Program(String id){
        super(id);
        subscribe(new ProgramEventChange(this));
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
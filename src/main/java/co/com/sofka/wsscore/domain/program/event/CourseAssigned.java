package co.com.sofka.wsscore.domain.program.event;

import co.com.sofka.wsscore.domain.generic.DomainEvent;

import java.util.List;

public class CourseAssigned extends DomainEvent {
    private final String courseId;
    private final List<String> categories;

    public CourseAssigned(String courseId, List<String> categories) {
        super("sofkau.program.courseassigned");
        this.courseId = courseId;
        this.categories = categories;
    }


    public List<String> getCategories() {
        return categories;
    }

    public String getCourseId() {
        return courseId;
    }
}

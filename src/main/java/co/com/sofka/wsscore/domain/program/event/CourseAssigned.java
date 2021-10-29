package co.com.sofka.wsscore.domain.program.event;

import co.com.sofka.wsscore.domain.generic.DomainEvent;

import java.util.List;

public class CourseAssigned extends DomainEvent {
    private final String courseId;
    private final String name;
    private final List<String> categories;

    public CourseAssigned(String courseId, String name, List<String> categories) {
        super("sofkau.program.courseassigned");
        this.courseId = courseId;
        this.name = name;
        this.categories = categories;
    }


    public List<String> getCategories() {
        return categories;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getName() {
        return name;
    }
}

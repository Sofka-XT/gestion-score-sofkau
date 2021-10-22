package co.com.sofka.wsscore.domain.program.event;

import co.com.sofka.wsscore.domain.generic.DomainEvent;

public class CourseAndCategoryAssigned extends DomainEvent {
    private final String course;
    private final String category;

    public CourseAndCategoryAssigned(String course, String category) {
        this.course = course;
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public String getCourse() {
        return course;
    }
}

package co.com.sofka.wsscore.domain.program;

import java.util.List;
import java.util.Objects;

public class Course {
    private final String id;
    private List<String> categories;

    public Course( String id, List<String> category) {
        this.categories = Objects.requireNonNull(category);
        this.id = Objects.requireNonNull(id);
    }

    public void addCategory(String category){
        categories.add(category);
    }

    public  List<String> categories() {
        return categories;
    }

    public String id() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(id, course.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

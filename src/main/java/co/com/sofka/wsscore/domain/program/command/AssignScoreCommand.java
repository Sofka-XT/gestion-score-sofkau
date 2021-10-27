package co.com.sofka.wsscore.domain.program.command;

import co.com.sofka.wsscore.domain.generic.Command;

public class AssignScoreCommand extends Command {
    private  String programId;
    private  String courseId;
    private  String category;
    private  String path;

    public AssignScoreCommand(){

    }

    public String getProgramId() {
        return programId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}

package co.com.sofka.wsscore.domain;

public class Score {
    private  String name;
    private  String grade;
    public Score(){

    }
    public Score(String name, String grade) {
        this.name = name;
        this.grade = grade;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public String getGrade() {
        return grade;
    }

    @Override
    public String toString() {
        return "Score{" +
                "name='" + name + '\'' +
                ", grade='" + grade + '\'' +
                '}';
    }
}
import java.util.ArrayList;

/**
 * Класс, описывающий содержание ТУП
 * */
public class Plan {
    /** Заголовок */
    private String title;

    /** Семестр */
    private int semester;

    /** Факультет */
    private String faculty;

    /** Направление */
    private String direction;

    /** Основные исциплины */
    private ArrayList<Subject> subjects = new ArrayList<>();

    /** Факультативы и физ. культура */
    private ArrayList<Subject> electives = new ArrayList<>();

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void addSubject(Subject subject){
        subjects.add(subject);
    }

    public void addElective(Subject subject){
        electives.add(subject);
    }

    public int getSemester() {
        return semester;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getDirection() {
        return direction;
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public ArrayList<Subject> getElectives() {
        return electives;
    }

    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append(title).append("\n").append("Семестр: ").append(semester).append(" Факультет: ").append(faculty).
                append(" Направление: ").append(direction).append("\n");
        for(int i = 0; i < subjects.size(); i++){
            builder.append(i + 1).append(". ").append(subjects.get(i).toString()).append("\n");
        }
        if(electives.size() > 0) {
            builder.append("Факультативы и физ. культура\n");
        }
        for(int i = 0; i < electives.size(); i++){
            builder.append(i + subjects.size() + 1).append(". ").append(electives.get(i).toString()).append("\n");
        }
        return builder.toString();
    }
}

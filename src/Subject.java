/**
 * Класс, описывающий отдельную дисциплину
 * */
public class Subject {
    /** Название дисциплины */
    private String name;

    /** Кафедра */
    private String cathedra;

    /** Количество лекций по неделям */
    private int[] lectures = new int[18];

    /** Количество лабораторных по неделям */
    private int[] labs = new int[18];

    /** Количество практик по неделям */
    private int[] practices = new int[18];

    /** Количество индивидуальных занятий по неделям */
    private int[] individual = new int[18];

    /** Зачёт */
    private int pass = 0;

    /** Экзамен */
    private int exam = 0;

    /** Курсовой проект */
    private int courseProject = 0;

    /** Курсовая работа */
    private int courseWork = 0;

    /** К. */
    private int k = 0;

    /** Дифференцированный зачёт */
    private int gradedPass = 0;

    public void setName(String name) {
        this.name = name;
    }

    public void setCathedra(String cathedra) {
        this.cathedra = cathedra;
    }

    public void setLectures(int[] lectures) {
        this.lectures = lectures;
    }

    public void setLabs(int[] labs) {
        this.labs = labs;
    }

    public void setPractices(int[] practices) {
        this.practices = practices;
    }

    public void setIndividual(int[] individual) {
        this.individual = individual;
    }

    public void setPass(int pass) {
        this.pass = pass;
    }

    public void setExam(int exam) {
        this.exam = exam;
    }

    public void setCourseProject(int courseProject) {
        this.courseProject = courseProject;
    }

    public void setCourseWork(int courseWork) {
        this.courseWork = courseWork;
    }

    public void setK(int k) {
        this.k = k;
    }

    public void setGradedPass(int gradedPass) {
        this.gradedPass = gradedPass;
    }

    public String getName() {
        return name;
    }

    public String getCathedra() {
        return cathedra;
    }

    public int[] getLectures() {
        return lectures;
    }

    public int[] getLabs() {
        return labs;
    }

    public int[] getPractices() {
        return practices;
    }

    public int[] getIndividual() {
        return individual;
    }

    public int getPass() {
        return pass;
    }

    public int getExam() {
        return exam;
    }

    public int getCourseProject() {
        return courseProject;
    }

    public int getCourseWork() {
        return courseWork;
    }

    public int getK() {
        return k;
    }

    public int getGradedPass() {
        return gradedPass;
    }

    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append(name).append(" (").append(cathedra).append(")").append(" Форма контроля: ");
        if(pass != 0){
            builder.append("Зач., ");
        }
        if(exam != 0){
            builder.append("Экз., ");
        }
        if(courseProject != 0){
            builder.append("К.п., ");
        }
        if(courseWork != 0){
            builder.append("К.р., ");
        }
        if(k != 0){
            builder.append("К., ");
        }
        if(gradedPass != 0){
            builder.append("Зач. с оценкой ,");
        }
        builder.setLength(builder.length() - 2);
        builder.append("\n              Количество часов по неделям");
        builder.append("\n              1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18");
        builder.append("\n      Лекции");
        for(int i = 0; i < 18; i++){
            builder.append("  ").append(lectures[i]);
        }
        builder.append("\n        Лаб.");
        for(int i = 0; i < 18; i++){
            builder.append("  ").append(labs[i]);
        }
        builder.append("\nПр. зан.(С.)");
        for(int i = 0; i < 18; i++){
            builder.append("  ").append(practices[i]);
        }
        builder.append("\n   Инд. зан.");
        for(int i = 0; i < 18; i++){
            builder.append("  ").append(individual[i]);
        }
        builder.append("\n");
        return builder.toString();
    }
}
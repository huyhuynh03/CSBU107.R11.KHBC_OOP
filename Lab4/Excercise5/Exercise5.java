import java.util.*;

abstract class CourseRegistration {
    private final String code;
    private final String courseName;
    private final int credits;
    private final int registeredStudents;

    protected CourseRegistration(String code, String courseName, int credits, int registeredStudents) {
        if (code == null || code.trim().isEmpty() || courseName == null || courseName.trim().isEmpty())
            throw new IllegalArgumentException("Course code and name must not be empty");
        if (credits < 1 || registeredStudents < 0) throw new IllegalArgumentException("Invalid credits or students");
        this.code = code.trim(); this.courseName = courseName.trim();
        this.credits = credits; this.registeredStudents = registeredStudents;
    }

    public abstract long tuitionPerStudent();
    public long expectedCollection() { return tuitionPerStudent() * registeredStudents; }
    public void displayInfo() {
        System.out.printf("Code: %s | Type: %s | Course: %s | Credits: %d | Students: %d | Tuition/student: %,d VND | Expected: %,d VND%n",
                code, getClass().getSimpleName(), courseName, credits, registeredStudents, tuitionPerStudent(), expectedCollection());
    }
    public String getCode() { return code; }
    public String getCourseName() { return courseName; }
    public int getCredits() { return credits; }
    public int getRegisteredStudents() { return registeredStudents; }
}

class TheoryCourse extends CourseRegistration {
    public TheoryCourse(String code, String name, int credits, int students) { super(code, name, credits, students); }
    @Override public long tuitionPerStudent() { return getCredits() * 450000L; }
}

class LabCourse extends CourseRegistration {
    private final long equipmentFee;
    public LabCourse(String code, String name, int credits, int students, long equipmentFee) {
        super(code, name, credits, students);
        if (equipmentFee < 0) throw new IllegalArgumentException("Equipment fee must be >= 0");
        this.equipmentFee = equipmentFee;
    }
    @Override public long tuitionPerStudent() { return getCredits() * 500000L + equipmentFee; }
    public long getEquipmentFee() { return equipmentFee; }
}

public class Exercise5 {
    public static void main(String[] args) {
        List<CourseRegistration> courses = Arrays.asList(
                new TheoryCourse("T01", "OOP", 3, 20),
                new LabCourse("L01", "Programming Lab", 2, 10, 100000));
        Set<String> codes = new HashSet<>();
        long total = 0; int theory = 0, lab = 0, students = 0;
        CourseRegistration largest = null;
        for (CourseRegistration course : courses) {
            if (!codes.add(course.getCode())) throw new IllegalArgumentException("Duplicate course code");
            course.displayInfo(); total += course.expectedCollection(); students += course.getRegisteredStudents();
            if (course instanceof TheoryCourse) theory++; else lab++;
            if (largest == null || course.expectedCollection() > largest.expectedCollection()) largest = course;
        }
        double average = students == 0 ? 0 : (double) courses.stream().mapToLong(c -> c.tuitionPerStudent() * c.getRegisteredStudents()).sum() / students;
        System.out.printf("Theory: %d | Laboratory: %d | Total expected collection: %,d VND%n", theory, lab, total);
        if (largest != null) System.out.println("Largest collection: " + largest.getCourseName());
        System.out.printf("Weighted average tuition: %,.2f VND/student%n", average);
    }
}

/*
 * Exercise 7: UITStudent - Constructor Overloading (Challenge)
 * Object/Class : UITStudent
 * Attributes   : private String studentId, fullName, major; private double gpa (0.0 - 4.0)
 * Constructors : UITStudent()                              -> this("N/A", "Unknown")
 *                UITStudent(String studentId, String fullName) -> this(id, name, "Undeclared", 0.0)
 *                UITStudent(String studentId, String fullName, String major, double gpa)
 * Methods      : displayInfo(), isPassed(), academicClassification()
 *
 * GPA thresholds (4.0 scale):
 *   isPassed(): gpa >= 2.0
 *   academicClassification():
 *     gpa >= 3.6 -> Excellent
 *     gpa >= 3.2 -> Very Good
 *     gpa >= 2.5 -> Good
 *     gpa >= 2.0 -> Average
 *     otherwise  -> Weak (failed)
 */
class UITStudent {
    private String studentId;
    private String fullName;
    private String major;
    private double gpa;

    // No-argument constructor: chains to the two-argument constructor
    public UITStudent() {
        this("N/A", "Unknown");
    }

    // Two-argument constructor: chains to the full constructor with default major and GPA
    public UITStudent(String studentId, String fullName) {
        this(studentId, fullName, "Undeclared", 0.0);
    }

    // Full constructor: validates GPA in [0.0, 4.0]
    public UITStudent(String studentId, String fullName, String major, double gpa) {
        if (gpa < 0.0 || gpa > 4.0) {
            throw new IllegalArgumentException("GPA must be in [0.0, 4.0]: " + gpa);
        }
        this.studentId = studentId;
        this.fullName = fullName;
        this.major = major;
        this.gpa = gpa;
    }

    // Input: none | Output: none (prints to console) | Purpose: show all student information
    public void displayInfo() {
        System.out.printf("ID: %-10s | Name: %-18s | Major: %-14s | GPA: %.2f | Passed: %-3s | Classification: %s%n",
                studentId, fullName, major, gpa, isPassed() ? "Yes" : "No", academicClassification());
    }

    // Input: none | Output: boolean | Purpose: true if GPA >= 2.0
    public boolean isPassed() {
        return gpa >= 2.0;
    }

    // Input: none | Output: String | Purpose: classify the student by GPA
    public String academicClassification() {
        if (gpa >= 3.6) {
            return "Excellent";
        } else if (gpa >= 3.2) {
            return "Very Good";
        } else if (gpa >= 2.5) {
            return "Good";
        } else if (gpa >= 2.0) {
            return "Average";
        } else {
            return "Weak";
        }
    }
}

public class Exercise7 {
    public static void main(String[] args) {
        // Student 1: no-argument constructor
        UITStudent s1 = new UITStudent();

        // Student 2: studentId + fullName constructor
        UITStudent s2 = new UITStudent("23520001", "Nguyen Van An");

        // Students 3-5: full constructor with different information
        UITStudent s3 = new UITStudent("23520002", "Tran Thi Binh", "Software Engineering", 3.75);
        UITStudent s4 = new UITStudent("23520003", "Le Van Cuong", "Information Systems", 2.40);
        UITStudent s5 = new UITStudent("23520004", "Pham Thi Dung", "Computer Science", 1.80);

        UITStudent[] students = {s1, s2, s3, s4, s5};
        System.out.println("=== Student list ===");
        for (UITStudent s : students) {
            s.displayInfo();
        }

        // Invalid GPA is rejected by the full constructor
        try {
            new UITStudent("23520005", "Invalid GPA", "Data Science", 4.5);
        } catch (IllegalArgumentException e) {
            System.out.println("Expected error: " + e.getMessage());
        }
    }
}

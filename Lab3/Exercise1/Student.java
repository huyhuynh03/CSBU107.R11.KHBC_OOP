/**
 * Lab 3 - Exercise 1: University Student Graduation Management
 * Student: Huynh Vu Quoc Huy - MSSV: 25560070
 */
public abstract class Student {
    private String studentId;
    private String fullName;
    private String address;
    private int totalCredits;
    private double gpa;

    public Student(String studentId, String fullName, String address,
                   int totalCredits, double gpa) {
        this.studentId   = studentId;
        this.fullName    = fullName;
        this.address     = address;
        this.totalCredits = totalCredits;
        this.gpa         = gpa;
    }

    // Getters
    public String getStudentId()   { return studentId; }
    public String getFullName()    { return fullName; }
    public String getAddress()     { return address; }
    public int    getTotalCredits(){ return totalCredits; }
    public double getGpa()         { return gpa; }

    // Setters
    public void setStudentId(String studentId)     { this.studentId    = studentId; }
    public void setFullName(String fullName)        { this.fullName     = fullName; }
    public void setAddress(String address)          { this.address      = address; }
    public void setTotalCredits(int totalCredits)   { this.totalCredits = totalCredits; }
    public void setGpa(double gpa)                  { this.gpa          = gpa; }

    /** Each subclass defines its own graduation check. */
    public abstract boolean canGraduate();

    @Override
    public String toString() {
        return String.format("[%s] %s | Credits: %d | GPA: %.1f",
                studentId, fullName, totalCredits, gpa);
    }
}

public class CollegeStudent extends Student {
    private double graduationExamScore;

    public CollegeStudent(String studentId, String fullName, String address,
                          int totalCredits, double gpa, double graduationExamScore) {
        super(studentId, fullName, address, totalCredits, gpa);
        this.graduationExamScore = graduationExamScore;
    }

    public double getGraduationExamScore() { return graduationExamScore; }
    public void setGraduationExamScore(double score) { this.graduationExamScore = score; }

    @Override
    public boolean canGraduate() {
        return getTotalCredits() >= 120 && getGpa() >= 5.0
                && graduationExamScore >= 5.0;
    }

    @Override
    public String toString() {
        return "College " + super.toString()
                + String.format(" | Exam: %.1f | Eligible: %s", graduationExamScore, canGraduate());
    }
}

public class UniversityStudent extends Student {
    private String thesisTitle;
    private double thesisScore;

    public UniversityStudent(String studentId, String fullName, String address,
                             int totalCredits, double gpa, String thesisTitle,
                             double thesisScore) {
        super(studentId, fullName, address, totalCredits, gpa);
        this.thesisTitle = thesisTitle;
        this.thesisScore = thesisScore;
    }

    public String getThesisTitle() { return thesisTitle; }
    public double getThesisScore() { return thesisScore; }
    public void setThesisTitle(String title) { this.thesisTitle = title; }
    public void setThesisScore(double score) { this.thesisScore = score; }

    @Override
    public boolean canGraduate() {
        return getTotalCredits() >= 170 && getGpa() >= 5.0 && thesisScore >= 5.0;
    }

    @Override
    public String toString() {
        return "University " + super.toString()
                + String.format(" | Thesis: %s (%.1f) | Eligible: %s",
                thesisTitle, thesisScore, canGraduate());
    }
}

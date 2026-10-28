import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Lab 3 - Exercise 1: University Student Graduation Management
 *
 * Student : Huynh Vu Quoc Huy
 * MSSV    : 25560070
 */
public class Exercise1Main {

    public static void main(String[] args) {
        printHeader();
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of students: ");
        int n = readInt(sc, "Number of students", 0);

        List<Student> students = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            System.out.println("\n--- Student #" + (i + 1) + " ---");
            System.out.println("  1. College student");
            System.out.println("  2. University student");
            int type = readInt(sc, "Choose program (1/2)", 1);

            System.out.print("Student ID: ");
            String id = sc.nextLine().trim();
            System.out.print("Full name: ");
            String name = sc.nextLine().trim();
            System.out.print("Address: ");
            String address = sc.nextLine().trim();
            int credits = readInt(sc, "Total credits", 0);
            double gpa = readDouble(sc, "GPA (0 - 10)", 0.0, 10.0);

            if (type == 1) {
                double exam = readDouble(sc, "Graduation exam score (0 - 10)", 0.0, 10.0);
                students.add(new CollegeStudent(id, name, address, credits, gpa, exam));
            } else {
                System.out.print("Thesis title: ");
                String title = sc.nextLine().trim();
                double thesis = readDouble(sc, "Thesis score (0 - 10)", 0.0, 10.0);
                students.add(new UniversityStudent(id, name, address, credits, gpa,
                        title, thesis));
            }
        }

        report(students);
        sc.close();
    }

    // ------------------------------------------------------------------ report

    private static void report(List<Student> students) {
        separate();
        System.out.println("STUDENT LIST");
        separate();
        for (Student s : students) {
            System.out.println(s);
        }

        // 1) Count students satisfying their own program criteria
        int eligible = 0;
        int collegeCount = 0, universityCount = 0;
        for (Student s : students) {
            if (s.canGraduate()) eligible++;
            if (s instanceof CollegeStudent) collegeCount++;
            else if (s instanceof UniversityStudent) universityCount++;
        }

        separate();
        System.out.println("Total students          : " + students.size());
        System.out.println("  - College students    : " + collegeCount);
        System.out.println("  - University students : " + universityCount);
        System.out.println("Eligible for graduation : " + eligible);

        // 2) University student with the highest GPA (polymorphic filter)
        UniversityStudent best = null;
        for (Student s : students) {
            if (s instanceof UniversityStudent) {
                UniversityStudent u = (UniversityStudent) s;
                if (best == null || u.getGpa() > best.getGpa()) best = u;
            }
        }
        System.out.println();
        if (best == null) {
            System.out.println("No University student in the list.");
        } else {
            System.out.println("University student with highest GPA:");
            System.out.println("  " + best);
        }
        separate();
    }

    // ------------------------------------------------------------------ helpers

    private static void printHeader() {
        System.out.println("========================================");
        System.out.println(" Exercise 1 - Graduation Management");
        System.out.println(" Student: Huynh Vu Quoc Huy - 25560070");
        System.out.println("========================================");
    }

    private static void separate() {
        System.out.println("----------------------------------------");
    }

    private static int readInt(Scanner sc, String label, int min) {
        while (true) {
            System.out.print(label + ": ");
            String line = sc.nextLine().trim();
            try {
                int v = Integer.parseInt(line);
                if (v < min) {
                    System.out.println("  ! Value must be >= " + min + ". Try again.");
                    continue;
                }
                return v;
            } catch (NumberFormatException e) {
                System.out.println("  ! Invalid integer. Try again.");
            }
        }
    }

    private static double readDouble(Scanner sc, String label, double min, double max) {
        while (true) {
            System.out.print(label + ": ");
            String line = sc.nextLine().trim();
            try {
                double v = Double.parseDouble(line);
                if (v < min || v > max) {
                    System.out.printf("  ! Value must be in [%.1f, %.1f]. Try again.%n", min, max);
                    continue;
                }
                return v;
            } catch (NumberFormatException e) {
                System.out.println("  ! Invalid number. Try again.");
            }
        }
    }
}

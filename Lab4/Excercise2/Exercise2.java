import java.util.*;

abstract class Employee {
    private final String id;
    private final String fullName;
    private final String department;

    protected Employee(String id, String fullName, String department) {
        this.id = text(id, "ID");
        this.fullName = text(fullName, "Full name");
        this.department = text(department, "Department");
    }

    public abstract long calculateSalary();

    public void displayInfo() {
        System.out.printf("ID: %s | Type: %s | Name: %s | Department: %s | Salary: %,d VND%n",
                id, getClass().getSimpleName(), fullName, department, calculateSalary());
    }

    public String getId() { return id; }
    public String getFullName() { return fullName; }
    public String getDepartment() { return department; }

    protected static String text(String value, String field) {
        if (value == null || value.trim().isEmpty()) throw new IllegalArgumentException(field + " must not be empty");
        return value.trim();
    }
}

class FullTimeEmployee extends Employee {
    private final long baseSalary, allowance;
    private final int workdays;

    public FullTimeEmployee(String id, String name, String department, long baseSalary, long allowance, int workdays) {
        super(id, name, department);
        if (baseSalary < 0 || allowance < 0 || workdays < 0 || workdays > 26) throw new IllegalArgumentException("Invalid full-time data");
        this.baseSalary = baseSalary; this.allowance = allowance; this.workdays = workdays;
    }

    @Override public long calculateSalary() { return baseSalary * workdays / 26 + allowance; }
}

class PartTimeEmployee extends Employee {
    private final int hoursWorked;
    private final long hourlyRate;

    public PartTimeEmployee(String id, String name, String department, int hoursWorked, long hourlyRate) {
        super(id, name, department);
        if (hoursWorked < 0 || hourlyRate < 0) throw new IllegalArgumentException("Hours and rate must be >= 0");
        this.hoursWorked = hoursWorked; this.hourlyRate = hourlyRate;
    }

    @Override public long calculateSalary() { return hoursWorked * hourlyRate; }
}

public class Exercise2 {
    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();
        employees.add(new FullTimeEmployee("E01", "Nguyen Van An", "IT", 10000000, 1000000, 26));
        employees.add(new PartTimeEmployee("E02", "Tran Thi Binh", "it", 40, 50000));

        Set<String> ids = new HashSet<>();
        for (Employee employee : employees) {
            if (!ids.add(employee.getId())) throw new IllegalArgumentException("Duplicate ID");
            employee.displayInfo();
        }

        String department = "IT";
        long total = employees.stream().filter(e -> e.getDepartment().equalsIgnoreCase(department))
                .mapToLong(Employee::calculateSalary).sum();
        Employee highest = employees.stream().max(Comparator.comparingLong(Employee::calculateSalary)).orElse(null);
        System.out.printf("Payroll of %s: %,d VND%n", department, total);
        if (highest != null) System.out.println("Highest salary employee: " + highest.getFullName());
        findEmployee(employees, "E01").ifPresent(e -> System.out.println("Found by ID: " + e.getFullName()));
        findEmployee(employees, "Tran Thi Binh", "IT").ifPresent(e -> System.out.println("Found by name/department: " + e.getId()));
    }

    public static Optional<Employee> findEmployee(List<Employee> list, String id) {
        return list.stream().filter(e -> e.getId().equalsIgnoreCase(id)).findFirst();
    }

    public static Optional<Employee> findEmployee(List<Employee> list, String name, String department) {
        return list.stream().filter(e -> e.getFullName().equalsIgnoreCase(name)
                && e.getDepartment().equalsIgnoreCase(department)).findFirst();
    }
}

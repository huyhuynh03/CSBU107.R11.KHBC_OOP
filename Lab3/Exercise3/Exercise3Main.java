/** Lab 3 - Exercise 3: Hotel Room Rental Management
 * Student: Huynh Vu Quoc Huy - MSSV: 25560070
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

abstract class Person {
    private String fullName;
    private int age;
    protected Person(String fullName, int age) {
        if (age < 0) throw new IllegalArgumentException("Age cannot be negative");
        this.fullName = fullName; this.age = age;
    }
    public String getFullName() { return fullName; }
    public int getAge() { return age; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setAge(int age) { if (age < 0) throw new IllegalArgumentException("Age cannot be negative"); this.age = age; }
    @Override public String toString() { return fullName + " (age " + age + ")"; }
}

class Guest extends Person {
    private String citizenId;
    public Guest(String citizenId, String fullName, int age) { super(fullName, age); this.citizenId = citizenId; }
    public String getCitizenId() { return citizenId; }
    @Override public String toString() { return "Guest " + super.toString() + " | CCCD: " + citizenId; }
}

class Staff extends Person {
    private String licenseId;
    private String employeeId;
    public Staff(String licenseId, String employeeId, String fullName, int age) {
        super(fullName, age); this.licenseId = licenseId; this.employeeId = employeeId;
    }
    public String getLicenseId() { return licenseId; }
    public String getEmployeeId() { return employeeId; }
    @Override public String toString() { return "Staff " + super.toString() + " | License: " + licenseId + " | Employee ID: " + employeeId; }
}

class Room {
    public enum RoomType {
        VIP1(900_000L), VIP2(700_000L), VIP3(500_000L);
        private final long dailyPrice;
        RoomType(long dailyPrice) { this.dailyPrice = dailyPrice; }
        public long getDailyPrice() { return dailyPrice; }
    }
    private RoomType type;
    private int rentalDays;
    public Room(RoomType type, int rentalDays) {
        if (rentalDays <= 0) throw new IllegalArgumentException("Rental days must be positive");
        this.type = type; this.rentalDays = rentalDays;
    }
    public RoomType getType() { return type; }
    public int getRentalDays() { return rentalDays; }
    public long calculateCost() { return type.getDailyPrice() * rentalDays; }
    @Override public String toString() { return type + " | " + rentalDays + " day(s) | Cost: " + String.format("%,d", calculateCost()) + " VND"; }
}

public class Exercise3Main {
    private static final List<Guest> guests = new ArrayList<>();
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("========================================");
        System.out.println(" Exercise 3 - Hotel Room Rental");
        System.out.println(" Student: Huynh Vu Quoc Huy - 25560070");
        System.out.println("========================================");
        int n = readInt(sc, "Number of guests", 0);
        for (int i = 0; i < n; i++) {
            System.out.println("\n--- Guest #" + (i + 1) + " ---");
            System.out.print("Citizen ID (CCCD): "); String id = sc.nextLine().trim();
            System.out.print("Full name: "); String name = sc.nextLine().trim();
            int age = readInt(sc, "Age", 0);
            guests.add(new Guest(id, name, age));
        }
        System.out.println("\nGuests currently staying:");
        guests.forEach(System.out::println);
        System.out.println("\nRoom rental calculation");
        System.out.println("Available room types: VIP1 (900,000), VIP2 (700,000), VIP3 (500,000) VND/day");
        System.out.print("Room type: ");
        Room.RoomType type;
        while (true) {
            try { type = Room.RoomType.valueOf(sc.nextLine().trim().toUpperCase()); break; }
            catch (IllegalArgumentException e) { System.out.print("  ! Invalid type. Enter VIP1, VIP2, or VIP3: "); }
        }
        int days = readInt(sc, "Number of rental days", 1);
        Room room = new Room(type, days);
        System.out.println("Room: " + room);
        System.out.println("Total rental cost: " + String.format("%,d", room.calculateCost()) + " VND");
        sc.close();
    }
    public static void addGuest(Guest guest) { guests.add(guest); }
    public static boolean removeGuest(String citizenId) { return guests.removeIf(g -> g.getCitizenId().equals(citizenId)); }
    private static int readInt(Scanner sc, String label, int min) {
        while (true) { System.out.print(label + ": "); try { int v = Integer.parseInt(sc.nextLine().trim()); if (v >= min) return v; } catch (NumberFormatException ignored) {} System.out.println("  ! Invalid value. Try again."); }
    }
}

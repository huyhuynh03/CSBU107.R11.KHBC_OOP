/** Lab 3 - Exercise 4: Singer Salary Management for XYZ Company
 * Student: Huynh Vu Quoc Huy - MSSV: 25560070
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

abstract class Singer {
    private String fullName;
    private int yearsOfExperience;
    private int albumsSold;
    private int performances;
    protected Singer(String fullName, int yearsOfExperience, int albumsSold, int performances) {
        if (yearsOfExperience < 0 || albumsSold < 0 || performances < 0) throw new IllegalArgumentException("Values cannot be negative");
        this.fullName = fullName; this.yearsOfExperience = yearsOfExperience; this.albumsSold = albumsSold; this.performances = performances;
    }
    public String getFullName() { return fullName; }
    public int getYearsOfExperience() { return yearsOfExperience; }
    public int getAlbumsSold() { return albumsSold; }
    public int getPerformances() { return performances; }
    public abstract long calculateSalary();
    @Override public String toString() { return String.format("%s | Experience: %d years | Albums: %d | Performances: %d | Salary: %,d VND", fullName, yearsOfExperience, albumsSold, performances, calculateSalary()); }
}

class RegularSinger extends Singer {
    public RegularSinger(String name, int years, int albums, int performances) { super(name, years, albums, performances); }
    @Override public long calculateSalary() { return 3_000_000L + 500_000L * getYearsOfExperience() + 1_000L * getAlbumsSold() + 200_000L * getPerformances(); }
}

class FamousSinger extends Singer {
    private int gameShowsAttended;
    public FamousSinger(String name, int years, int albums, int performances, int gameShows) { super(name, years, albums, performances); if (gameShows < 0) throw new IllegalArgumentException("Game shows cannot be negative"); this.gameShowsAttended = gameShows; }
    public int getGameShowsAttended() { return gameShowsAttended; }
    @Override public long calculateSalary() { return 5_000_000L + 500_000L * getYearsOfExperience() + 1_200L * getAlbumsSold() + 500_000L * getPerformances() + 500_000L * gameShowsAttended; }
    @Override public String toString() { return "Famous " + super.toString() + " | Game shows: " + gameShowsAttended; }
}

public class Exercise4Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Singer> singers = new ArrayList<>();
        System.out.println("========================================");
        System.out.println(" Exercise 4 - Singer Salary Management");
        System.out.println(" Student: Huynh Vu Quoc Huy - 25560070");
        System.out.println("========================================");
        int n = readInt(sc, "Number of singers", 0);
        for (int i = 0; i < n; i++) {
            System.out.println("\n--- Singer #" + (i + 1) + " ---");
            int type = readInt(sc, "Category (1=Regular, 2=Famous)", 1, 2);
            System.out.print("Full name: "); String name = sc.nextLine().trim();
            int years = readInt(sc, "Years of experience", 0);
            int albums = readInt(sc, "Albums sold", 0);
            int performances = readInt(sc, "Number of performances", 0);
            if (type == 1) singers.add(new RegularSinger(name, years, albums, performances));
            else singers.add(new FamousSinger(name, years, albums, performances, readInt(sc, "Game shows attended", 0)));
        }
        Singer highest = null;
        System.out.println("\n---------------- SINGER LIST ----------------");
        for (Singer singer : singers) { System.out.println(singer); if (highest == null || singer.calculateSalary() > highest.calculateSalary()) highest = singer; }
        System.out.println("----------------------------------------------");
        if (highest != null) System.out.println("Highest-paid singer: " + highest);
        sc.close();
    }
    private static int readInt(Scanner sc, String label, int min) { return readInt(sc, label, min, Integer.MAX_VALUE); }
    private static int readInt(Scanner sc, String label, int min, int max) { while (true) { System.out.print(label + ": "); try { int v = Integer.parseInt(sc.nextLine().trim()); if (v >= min && v <= max) return v; } catch (NumberFormatException ignored) {} System.out.println("  ! Invalid value. Try again."); } }
}

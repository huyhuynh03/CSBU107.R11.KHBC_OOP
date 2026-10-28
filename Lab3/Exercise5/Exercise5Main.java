/** Lab 3 - Exercise 5: Cosmetic Store Order Management
 * Student: Huynh Vu Quoc Huy - MSSV: 25560070
 */
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// ─────────────────────── PRODUCTS ────────────────────────────────────────────

abstract class Product {
    private String productId;
    private String type;
    private double volume;       // ml
    private long unitPrice;      // VND per ml

    protected Product(String productId, String type, double volume, long unitPrice) {
        if (volume <= 0) throw new IllegalArgumentException("Volume must be positive");
        this.productId = productId; this.type = type;
        this.volume = volume; this.unitPrice = unitPrice;
    }
    public String getProductId() { return productId; }
    public String getType()      { return type; }
    public double getVolume()    { return volume; }
    public long   getUnitPrice() { return unitPrice; }
    public long calculatePrice() { return (long)(volume * unitPrice); }

    @Override public String toString() {
        return String.format("[%s] %s | %.0f ml | %,d VND/ml | Total: %,d VND",
                productId, type, volume, unitPrice, calculatePrice());
    }
}

class Perfume extends Product {
    // Rose: 20,000  |  Chamomile: 15,000
    public enum PerfumeType { ROSE(20_000L), CHAMOMILE(15_000L);
        final long price; PerfumeType(long p){ price = p; } }
    private PerfumeType perfumeType;
    public Perfume(String id, PerfumeType t, double volume) { super(id, t.name() + "_PERFUME", volume, t.price); this.perfumeType = t; }
    public PerfumeType getPerfumeType() { return perfumeType; }
}

class Shampoo extends Product {
    // Dry: 20,000  |  Oily Std1: 30,000  |  Oily Std2: 40,000
    public enum ShampooType { DRY(20_000L), OILY_STD1(30_000L), OILY_STD2(40_000L);
        final long price; ShampooType(long p){ price = p; } }
    private ShampooType shampooType;
    public Shampoo(String id, ShampooType t, double volume) { super(id, t.name() + "_SHAMPOO", volume, t.price); this.shampooType = t; }
    public ShampooType getShampooType() { return shampooType; }
}

class ShowerGel extends Product {
    // Dry Skin: 20,000  |  Oily Skin: 40,000
    public enum GelType { DRY_SKIN(20_000L), OILY_SKIN(40_000L);
        final long price; GelType(long p){ price = p; } }
    public ShowerGel(String id, GelType t, double volume) { super(id, t.name() + "_SHOWER_GEL", volume, t.price); }
}

// ─────────────────────── PACKAGES ────────────────────────────────────────────

abstract class CosmeticPackage {
    private String packageName;
    private List<Product> products = new ArrayList<>();

    protected CosmeticPackage(String packageName) { this.packageName = packageName; }
    public String getPackageName() { return packageName; }
    public List<Product> getProducts() { return products; }
    public void addProduct(Product p) { products.add(p); }

    public long calculatePackagePrice() {
        long total = 0;
        for (Product p : products) total += p.calculatePrice();
        return total;
    }

    @Override public String toString() {
        StringBuilder sb = new StringBuilder("  Package: " + packageName + "\n");
        for (Product p : products) sb.append("    - ").append(p).append("\n");
        sb.append(String.format("  Package Total: %,d VND", calculatePackagePrice()));
        return sb.toString();
    }
}

class RomanticPackage extends CosmeticPackage {
    // Rose Perfume is mandatory; Shampoo and ShowerGel are optional
    public RomanticPackage() { super("Romantic Package"); }
    public void setRosePerfume(Perfume p) {
        if (p.getPerfumeType() != Perfume.PerfumeType.ROSE)
            throw new IllegalArgumentException("Romantic Package requires Rose Perfume");
        getProducts().removeIf(pr -> pr instanceof Perfume);
        addProduct(p);
    }
    public void setOptionalShampoo(Shampoo s) {
        getProducts().removeIf(pr -> pr instanceof Shampoo);
        addProduct(s);
    }
    public void setOptionalShowerGel(ShowerGel g) {
        getProducts().removeIf(pr -> pr instanceof ShowerGel);
        addProduct(g);
    }
}

class FreshAirPackage extends CosmeticPackage {
    // Oily Hair Shampoo is mandatory; Perfume and ShowerGel are optional
    public FreshAirPackage() { super("Fresh-Air Package"); }
    public void setOilyShampoo(Shampoo s) {
        if (s.getShampooType() == Shampoo.ShampooType.DRY)
            throw new IllegalArgumentException("Fresh-Air Package requires Oily Hair Shampoo");
        getProducts().removeIf(pr -> pr instanceof Shampoo);
        addProduct(s);
    }
    public void setOptionalPerfume(Perfume p) {
        getProducts().removeIf(pr -> pr instanceof Perfume);
        addProduct(p);
    }
    public void setOptionalShowerGel(ShowerGel g) {
        getProducts().removeIf(pr -> pr instanceof ShowerGel);
        addProduct(g);
    }
}

// ─────────────────────── CUSTOMER / ORDER ────────────────────────────────────

class Customer {
    private String customerId;
    private String fullName;
    private String address;
    private String phoneNumber;

    public Customer(String customerId, String fullName, String address, String phoneNumber) {
        this.customerId = customerId; this.fullName = fullName;
        this.address = address; this.phoneNumber = phoneNumber;
    }
    public String getCustomerId() { return customerId; }
    public String getFullName()   { return fullName; }
    public String getAddress()    { return address; }
    public String getPhoneNumber(){ return phoneNumber; }
    @Override public String toString() {
        return String.format("Customer [%s] %s | %s | Tel: %s", customerId, fullName, address, phoneNumber);
    }
}

class Order {
    private static int counter = 1;
    private String orderId;
    private Customer customer;
    private LocalDate invoiceDate;
    private CosmeticPackage cosmeticPackage;

    public Order(Customer customer, CosmeticPackage cosmeticPackage) {
        this.orderId = "ORD" + String.format("%03d", counter++);
        this.customer = customer;
        this.cosmeticPackage = cosmeticPackage;
        this.invoiceDate = LocalDate.now();
    }
    public String getOrderId() { return orderId; }
    public Customer getCustomer() { return customer; }
    public long getOrderPrice() { return cosmeticPackage.calculatePackagePrice(); }
    public CosmeticPackage getCosmeticPackage() { return cosmeticPackage; }

    @Override public String toString() {
        return String.format("Order ID : %s%nDate     : %s%n%s%n%s%nOrder Total: %,d VND",
                orderId, invoiceDate, customer, cosmeticPackage, getOrderPrice());
    }
    public String toFileString() { return toString() + "\n" + "=".repeat(50); }
}

// ─────────────────────── MAIN ─────────────────────────────────────────────────

public class Exercise5Main {
    private static int productCounter = 1;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Order> orders = new ArrayList<>();

        System.out.println("========================================");
        System.out.println(" Exercise 5 - Cosmetic Store Orders");
        System.out.println(" Student: Huynh Vu Quoc Huy - 25560070");
        System.out.println("========================================");

        int n = readInt(sc, "Number of orders (N)", 0);
        for (int i = 0; i < n; i++) {
            System.out.println("\n========== Order #" + (i + 1) + " ==========");
            Customer customer = inputCustomer(sc);
            CosmeticPackage pkg = inputPackage(sc);
            orders.add(new Order(customer, pkg));
        }

        System.out.println("\n================ ALL ORDERS ================");
        for (Order o : orders) System.out.println(o + "\n");

        saveToFile(orders, "Exercise5/order_list.txt");
        sc.close();
    }

    // ── Input helpers ──────────────────────────────────────────────────────────

    private static Customer inputCustomer(Scanner sc) {
        System.out.print("Customer ID   : "); String id = sc.nextLine().trim();
        System.out.print("Full name     : "); String name = sc.nextLine().trim();
        System.out.print("Address       : "); String addr = sc.nextLine().trim();
        System.out.print("Phone number  : "); String phone = sc.nextLine().trim();
        return new Customer(id, name, addr, phone);
    }

    private static CosmeticPackage inputPackage(Scanner sc) {
        System.out.println("Package type:");
        System.out.println("  1. Romantic Package (Rose Perfume required)");
        System.out.println("  2. Fresh-Air Package (Oily Hair Shampoo required)");
        int type = readInt(sc, "Choose (1/2)", 1, 2);
        return type == 1 ? inputRomantic(sc) : inputFreshAir(sc);
    }

    private static RomanticPackage inputRomantic(Scanner sc) {
        RomanticPackage pkg = new RomanticPackage();
        // Mandatory: Rose Perfume
        double vol = readDouble(sc, "Rose Perfume volume (ml)", 0.1, 10_000.0);
        pkg.setRosePerfume(new Perfume("P" + productCounter++, Perfume.PerfumeType.ROSE, vol));
        // Optional Shampoo
        if (readBool(sc, "Add optional Shampoo? (y/n)")) {
            int st = readInt(sc, "Shampoo type (1=Dry 20k, 2=Oily Std1 30k, 3=Oily Std2 40k)", 1, 3);
            Shampoo.ShampooType stype = st == 1 ? Shampoo.ShampooType.DRY : st == 2 ? Shampoo.ShampooType.OILY_STD1 : Shampoo.ShampooType.OILY_STD2;
            double svol = readDouble(sc, "Shampoo volume (ml)", 0.1, 10_000.0);
            pkg.setOptionalShampoo(new Shampoo("P" + productCounter++, stype, svol));
        }
        // Optional ShowerGel
        if (readBool(sc, "Add optional Shower Gel? (y/n)")) {
            int gt = readInt(sc, "Gel type (1=Dry Skin 20k, 2=Oily Skin 40k)", 1, 2);
            double gvol = readDouble(sc, "Shower Gel volume (ml)", 0.1, 10_000.0);
            pkg.setOptionalShowerGel(new ShowerGel("P" + productCounter++, gt == 1 ? ShowerGel.GelType.DRY_SKIN : ShowerGel.GelType.OILY_SKIN, gvol));
        }
        return pkg;
    }

    private static FreshAirPackage inputFreshAir(Scanner sc) {
        FreshAirPackage pkg = new FreshAirPackage();
        // Mandatory: Oily Hair Shampoo
        int st = readInt(sc, "Oily Shampoo type (1=Std1 30k, 2=Std2 40k)", 1, 2);
        double svol = readDouble(sc, "Oily Shampoo volume (ml)", 0.1, 10_000.0);
        pkg.setOilyShampoo(new Shampoo("P" + productCounter++, st == 1 ? Shampoo.ShampooType.OILY_STD1 : Shampoo.ShampooType.OILY_STD2, svol));
        // Optional Perfume
        if (readBool(sc, "Add optional Perfume? (y/n)")) {
            int pt = readInt(sc, "Perfume type (1=Rose 20k, 2=Chamomile 15k)", 1, 2);
            double pvol = readDouble(sc, "Perfume volume (ml)", 0.1, 10_000.0);
            pkg.setOptionalPerfume(new Perfume("P" + productCounter++, pt == 1 ? Perfume.PerfumeType.ROSE : Perfume.PerfumeType.CHAMOMILE, pvol));
        }
        // Optional ShowerGel
        if (readBool(sc, "Add optional Shower Gel? (y/n)")) {
            int gt = readInt(sc, "Gel type (1=Dry Skin 20k, 2=Oily Skin 40k)", 1, 2);
            double gvol = readDouble(sc, "Shower Gel volume (ml)", 0.1, 10_000.0);
            pkg.setOptionalShowerGel(new ShowerGel("P" + productCounter++, gt == 1 ? ShowerGel.GelType.DRY_SKIN : ShowerGel.GelType.OILY_SKIN, gvol));
        }
        return pkg;
    }

    // ── File I/O ───────────────────────────────────────────────────────────────

    private static void saveToFile(List<Order> orders, String path) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            bw.write("LAB 3 - EXERCISE 5: COSMETIC STORE ORDER LIST\n");
            bw.write("Student: Huynh Vu Quoc Huy - MSSV: 25560070\n");
            bw.write("=".repeat(50) + "\n");
            for (Order o : orders) { bw.write(o.toFileString() + "\n"); }
            System.out.println("Order list saved to: " + path);
        } catch (IOException e) {
            System.err.println("Failed to write file: " + e.getMessage());
        }
    }

    // ── Validation helpers ─────────────────────────────────────────────────────

    private static int readInt(Scanner sc, String label, int min) { return readInt(sc, label, min, Integer.MAX_VALUE); }
    private static int readInt(Scanner sc, String label, int min, int max) {
        while (true) { System.out.print(label + ": "); try { int v = Integer.parseInt(sc.nextLine().trim()); if (v >= min && v <= max) return v; } catch (NumberFormatException ignored) {} System.out.println("  ! Invalid value. Try again."); }
    }
    private static double readDouble(Scanner sc, String label, double min, double max) {
        while (true) { System.out.print(label + ": "); try { double v = Double.parseDouble(sc.nextLine().trim()); if (v >= min && v <= max) return v; } catch (NumberFormatException ignored) {} System.out.printf("  ! Value must be in [%.1f, %.1f]. Try again.%n", min, max); }
    }
    private static boolean readBool(Scanner sc, String label) {
        while (true) { System.out.print(label + ": "); String s = sc.nextLine().trim().toLowerCase(); if (s.equals("y") || s.equals("yes")) return true; if (s.equals("n") || s.equals("no")) return false; System.out.println("  ! Enter y or n."); }
    }
}

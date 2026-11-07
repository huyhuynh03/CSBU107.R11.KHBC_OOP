import java.util.*;

abstract class Delivery {
    private final String orderId;
    private final String customerName;
    private final double distance;
    private final double weight;
    private static int successfulObjects;

    protected Delivery(String orderId, String customerName, double distance, double weight) {
        if (orderId == null || orderId.trim().isEmpty() || customerName == null || customerName.trim().isEmpty())
            throw new IllegalArgumentException("Order ID and customer name must not be empty");
        if (distance <= 0 || weight <= 0) throw new IllegalArgumentException("Distance and weight must be > 0");
        this.orderId = orderId.trim(); this.customerName = customerName.trim();
        this.distance = distance; this.weight = weight; successfulObjects++;
    }

    public abstract long calculateShippingFee();
    public void displayInfo() {
        System.out.printf("Order: %s | Customer: %s | Type: %s | Distance: %.1f km | Weight: %.1f kg | Fee: %,d VND%n",
                orderId, customerName, getClass().getSimpleName(), distance, weight, calculateShippingFee());
    }
    public String getOrderId() { return orderId; }
    public String getCustomerName() { return customerName; }
    public double getDistance() { return distance; }
    public double getWeight() { return weight; }
    public static int getSuccessfulObjects() { return successfulObjects; }
}

class StandardDelivery extends Delivery {
    public StandardDelivery(String id, String customer, double distance, double weight) { super(id, customer, distance, weight); }
    @Override public long calculateShippingFee() { return Math.round(15000 + 3000 * getDistance() + 2000 * getWeight()); }
}

class ExpressDelivery extends Delivery {
    public ExpressDelivery(String id, String customer, double distance, double weight) { super(id, customer, distance, weight); }
    @Override public long calculateShippingFee() { return Math.round(30000 + 5000 * getDistance() + 4000 * getWeight()); }
}

public class Exercise3 {
    public static void main(String[] args) {
        List<Delivery> deliveries = Arrays.asList(
                new StandardDelivery("S01", "An", 10, 2),
                new ExpressDelivery("E01", "Binh", 10, 2),
                new StandardDelivery("S02", "Cuong", 5, 1));
        Set<String> ids = new HashSet<>();
        long revenue = 0; int standard = 0, express = 0;
        for (Delivery delivery : deliveries) {
            if (!ids.add(delivery.getOrderId())) throw new IllegalArgumentException("Duplicate order ID");
            delivery.displayInfo(); revenue += delivery.calculateShippingFee();
            if (delivery instanceof StandardDelivery) standard++; else express++;
        }
        System.out.printf("Standard: %d | Express: %d | Total revenue: %,d VND%n", standard, express, revenue);
        System.out.println("Successfully created Delivery objects: " + Delivery.getSuccessfulObjects());
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter fee threshold (VND): ");
        if (scanner.hasNextDouble()) {
            double threshold = scanner.nextDouble();
            deliveries.stream().filter(d -> d.calculateShippingFee() > threshold).forEach(Delivery::displayInfo);
        }
    }
}

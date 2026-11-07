import java.util.*;

abstract class Ticket {
    private final String id;
    private final String movieTitle;
    private final int quantity;

    protected Ticket(String id, String movieTitle, int quantity) {
        if (id == null || id.trim().isEmpty() || movieTitle == null || movieTitle.trim().isEmpty())
            throw new IllegalArgumentException("ID and movie title must not be empty");
        if (quantity < 1) throw new IllegalArgumentException("Quantity must be >= 1");
        this.id = id.trim(); this.movieTitle = movieTitle.trim(); this.quantity = quantity;
    }

    public abstract long unitPrice();
    public abstract double discountRate();
    public long calculateDiscount() { return Math.round(unitPrice() * quantity * discountRate()); }
    public long calculateTotalPrice() { return unitPrice() * quantity - calculateDiscount(); }
    public String getId() { return id; }
    public String getMovieTitle() { return movieTitle; }
    public int getQuantity() { return quantity; }
    public void displayInfo() {
        System.out.printf("ID: %s | Type: %s | Movie: %s | Quantity: %d | Discount: %,d VND | Total: %,d VND%n",
                id, getClass().getSimpleName(), movieTitle, quantity, calculateDiscount(), calculateTotalPrice());
    }
}

class RegularTicket extends Ticket {
    public RegularTicket(String id, String movieTitle, int quantity) { super(id, movieTitle, quantity); }
    @Override public long unitPrice() { return 80000; }
    @Override public double discountRate() { return getQuantity() >= 5 ? 0.10 : 0; }
}

class VIPTicket extends Ticket {
    public VIPTicket(String id, String movieTitle, int quantity) { super(id, movieTitle, quantity); }
    @Override public long unitPrice() { return 150000; }
    @Override public double discountRate() { return getQuantity() >= 3 ? 0.15 : 0; }
}

public class Exercise4 {
    public static void main(String[] args) {
        List<Ticket> tickets = Arrays.asList(
                new RegularTicket("R01", "Dune", 5),
                new VIPTicket("V01", "Dune", 3),
                new RegularTicket("R02", "Avatar", 2));
        Set<String> ids = new HashSet<>();
        for (Ticket ticket : tickets) {
            if (!ids.add(ticket.getId())) throw new IllegalArgumentException("Duplicate ticket ID");
            ticket.displayInfo();
        }
        long revenue = tickets.stream().mapToLong(Ticket::calculateTotalPrice).sum();
        System.out.printf("Total revenue: %,d VND%n", revenue);
        String movie = "Dune";
        long movieRevenue = tickets.stream().filter(t -> t.getMovieTitle().equalsIgnoreCase(movie))
                .mapToLong(Ticket::calculateTotalPrice).sum();
        System.out.printf("Revenue for %s: %,d VND%n", movie, movieRevenue);
        tickets.stream().sorted(Comparator.comparingLong(Ticket::calculateTotalPrice).reversed()).forEach(Ticket::displayInfo);
    }
}

/**
 * Lab 3 - Exercise 2: Ticket Management for Dam Sen Park
 * Student: Huynh Vu Quoc Huy - MSSV: 25560070
 */
public abstract class Ticket {
    private String ticketId;
    private String holderName;
    private int yearOfBirth;
    private int gamesPlayed;

    protected Ticket(String ticketId, String holderName, int yearOfBirth, int gamesPlayed) {
        if (gamesPlayed < 0) throw new IllegalArgumentException("Games played cannot be negative");
        this.ticketId = ticketId;
        this.holderName = holderName;
        this.yearOfBirth = yearOfBirth;
        this.gamesPlayed = gamesPlayed;
    }

    public String getTicketId() { return ticketId; }
    public String getHolderName() { return holderName; }
    public int getYearOfBirth() { return yearOfBirth; }
    public int getGamesPlayed() { return gamesPlayed; }
    public void setGamesPlayed(int gamesPlayed) {
        if (gamesPlayed < 0) throw new IllegalArgumentException("Games played cannot be negative");
        this.gamesPlayed = gamesPlayed;
    }

    public abstract long calculatePrice();

    @Override
    public String toString() {
        return String.format("[%s] %s | Birth year: %d | Games: %d | Price: %,d VND",
                ticketId, holderName, yearOfBirth, gamesPlayed, calculatePrice());
    }
}

class FullPackageTicket extends Ticket {
    private static final long PRICE = 200_000L;

    public FullPackageTicket(String id, String holder, int birthYear, int games) {
        super(id, holder, birthYear, games);
    }

    @Override
    public long calculatePrice() { return PRICE; }
}

class PartialTicket extends Ticket {
    private static final long ENTRANCE_FEE = 70_000L;
    private static final long PRICE_PER_GAME = 20_000L;

    public PartialTicket(String id, String holder, int birthYear, int games) {
        super(id, holder, birthYear, games);
    }

    @Override
    public long calculatePrice() {
        return ENTRANCE_FEE + (long) getGamesPlayed() * PRICE_PER_GAME;
    }
}

class Exercise2Main {
    public static void main(String[] args) {
        java.util.Scanner sc = new java.util.Scanner(System.in);
        java.util.List<Ticket> tickets = new java.util.ArrayList<>();
        System.out.println("========================================");
        System.out.println(" Exercise 2 - Dam Sen Park Tickets");
        System.out.println(" Student: Huynh Vu Quoc Huy - 25560070");
        System.out.println("========================================");

        int n = readInt(sc, "Number of tickets", 0);
        for (int i = 0; i < n; i++) {
            System.out.println("\n--- Ticket #" + (i + 1) + " ---");
            int type = readInt(sc, "Type (1=Full Package, 2=Partial)", 1, 2);
            System.out.print("Ticket ID: ");
            String id = sc.nextLine().trim();
            System.out.print("Holder name: ");
            String holder = sc.nextLine().trim();
            int birthYear = readInt(sc, "Year of birth", 1900);
            int games = type == 1 ? 30 : readInt(sc, "Number of games played", 0);
            tickets.add(type == 1 ? new FullPackageTicket(id, holder, birthYear, games)
                    : new PartialTicket(id, holder, birthYear, games));
        }

        long revenue = 0;
        int partialCount = 0;
        System.out.println("\n---------------- TICKET LIST ----------------");
        for (Ticket ticket : tickets) {
            System.out.println(ticket);
            revenue += ticket.calculatePrice();
            if (ticket instanceof PartialTicket) partialCount++;
        }
        System.out.println("----------------------------------------------");
        System.out.printf("Total revenue       : %,d VND%n", revenue);
        System.out.println("Partial tickets sold: " + partialCount);
        sc.close();
    }

    private static int readInt(java.util.Scanner sc, String label, int min) {
        return readInt(sc, label, min, Integer.MAX_VALUE);
    }

    private static int readInt(java.util.Scanner sc, String label, int min, int max) {
        while (true) {
            System.out.print(label + ": ");
            try {
                int value = Integer.parseInt(sc.nextLine().trim());
                if (value >= min && value <= max) return value;
            } catch (NumberFormatException ignored) { }
            System.out.println("  ! Invalid value. Try again.");
        }
    }
}

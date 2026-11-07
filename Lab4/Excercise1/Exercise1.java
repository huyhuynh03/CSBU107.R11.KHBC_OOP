import java.util.*;

abstract class LibraryItem {
    private final String id;
    private final String title;
    private final String author;

    protected LibraryItem(String id, String title, String author) {
        this.id = requireText(id, "ID");
        this.title = requireText(title, "Title");
        this.author = requireText(author, "Author");
    }

    public abstract long calculateFee();

    public void displayInfo() {
        System.out.printf("ID: %s | Title: %s | Author: %s | Fee: %,d VND%n",
                id, title, author, calculateFee());
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }

    protected static String requireText(String value, String field) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " must not be empty");
        }
        return value.trim();
    }
}

class PrintedBook extends LibraryItem {
    private final int pages;
    private final int daysBorrowed;

    public PrintedBook(String id, String title, String author, int pages, int daysBorrowed) {
        super(id, title, author);
        if (pages < 1 || daysBorrowed < 1) throw new IllegalArgumentException("Pages and days must be >= 1");
        this.pages = pages;
        this.daysBorrowed = daysBorrowed;
    }

    @Override public long calculateFee() { return 5000L + pages * 10L + daysBorrowed * 1000L; }
}

class Magazine extends LibraryItem {
    private final int issueNumber;
    private final int daysBorrowed;

    public Magazine(String id, String title, String author, int issueNumber, int daysBorrowed) {
        super(id, title, author);
        if (issueNumber < 1 || daysBorrowed < 1) throw new IllegalArgumentException("Issue and days must be >= 1");
        this.issueNumber = issueNumber;
        this.daysBorrowed = daysBorrowed;
    }

    @Override public long calculateFee() { return 3000L * daysBorrowed + 2000L; }
}

public class Exercise1 {
    public static void main(String[] args) {
        List<LibraryItem> items = new ArrayList<>();
        items.add(new PrintedBook("B01", "OOP Java", "Nguyen Van A", 200, 3));
        items.add(new Magazine("M01", "Tech Monthly", "Tech Press", 12, 3));

        Set<String> ids = new HashSet<>();
        long revenue = 0;
        int books = 0, magazines = 0;
        for (LibraryItem item : items) {
            if (!ids.add(item.getId())) throw new IllegalArgumentException("Duplicate ID: " + item.getId());
            item.displayInfo();
            revenue += item.calculateFee();
            if (item instanceof PrintedBook) books++; else magazines++;
        }
        System.out.printf("Printed books: %d | Magazines: %d | Total revenue: %,d VND%n", books, magazines, revenue);
        String searchId = "B01";
        items.stream().filter(item -> item.getId().equalsIgnoreCase(searchId)).findFirst()
                .ifPresentOrElse(LibraryItem::displayInfo, () -> System.out.println("Not found: " + searchId));
    }
}

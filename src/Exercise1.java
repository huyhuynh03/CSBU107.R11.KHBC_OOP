/*
 * Exercise 1: Rectangle - Basic Class Design
 * Object/Class : Rectangle
 * Attributes   : private double width, height
 * Constructors : Rectangle()            -> 1 x 1 rectangle
 *                Rectangle(double, double) -> invalid (<= 0) values replaced by 1
 * Methods      : area(), perimeter(), displayInfo()
 */
class Rectangle {
    private double width;
    private double height;

    // No-argument constructor: creates a 1 x 1 rectangle
    public Rectangle() {
        this(1, 1);
    }

    // Parameterized constructor: non-positive values are replaced by 1
    public Rectangle(double width, double height) {
        this.width = (width > 0) ? width : 1;
        this.height = (height > 0) ? height : 1;
    }

    // Input: none | Output: double (area) | Purpose: width * height
    public double area() {
        return width * height;
    }

    // Input: none | Output: double (perimeter) | Purpose: 2 * (width + height)
    public double perimeter() {
        return 2 * (width + height);
    }

    // Input: none | Output: none (prints to console) | Purpose: show all information
    public void displayInfo() {
        System.out.printf("Rectangle[width=%.2f, height=%.2f, area=%.2f, perimeter=%.2f]%n",
                width, height, area(), perimeter());
    }
}

public class Exercise1 {
    public static void main(String[] args) {
        Rectangle r1 = new Rectangle();          // 1 x 1
        Rectangle r2 = new Rectangle(5, 3);      // valid 5 x 3
        Rectangle r3 = new Rectangle(-2, 4);     // width -2 is invalid -> replaced by 1

        Rectangle[] rectangles = {r1, r2, r3};
        for (int i = 0; i < rectangles.length; i++) {
            System.out.println("Rectangle #" + (i + 1) + ":");
            rectangles[i].displayInfo();
            System.out.println("  area()      = " + rectangles[i].area());
            System.out.println("  perimeter() = " + rectangles[i].perimeter());
            System.out.println();
        }
    }
}

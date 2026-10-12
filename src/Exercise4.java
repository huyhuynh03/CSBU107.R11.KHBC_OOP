/*
 * Exercise 4: Circle - Geometry as an Object
 * Object/Class : Circle C(a, b, r)
 * Attributes   : private double centerX, centerY, radius (radius > 0)
 * Constructors : Circle()                       -> unit circle centered at (0, 0), r = 1
 *                Circle(double a, double b, double r)
 * Methods      : area(), perimeter(), contains(double x, double y)
 *                contains returns true if the point is inside or on the circle.
 */
class Circle {
    private double centerX;
    private double centerY;
    private double radius;

    // Default constructor: unit circle centered at (0, 0)
    public Circle() {
        this(0, 0, 1);
    }

    // Parameterized constructor: radius must be positive
    public Circle(double centerX, double centerY, double radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("Radius must be positive: " + radius);
        }
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
    }

    // Input: none | Output: double | Purpose: pi * r^2
    public double area() {
        return Math.PI * radius * radius;
    }

    // Input: none | Output: double | Purpose: 2 * pi * r
    public double perimeter() {
        return 2 * Math.PI * radius;
    }

    // Input: double x, double y | Output: boolean | Purpose: true if (x, y) is inside or on the circle
    public boolean contains(double x, double y) {
        double dx = x - centerX;
        double dy = y - centerY;
        double distanceSquared = dx * dx + dy * dy;
        // small tolerance avoids floating-point error for points exactly on the boundary
        return distanceSquared <= radius * radius + 1e-9;
    }

    // Output: none | Purpose: show circle information
    public void displayInfo() {
        System.out.printf("Circle center=(%.2f, %.2f), radius=%.2f, area=%.2f, perimeter=%.2f%n",
                centerX, centerY, radius, area(), perimeter());
    }
}

public class Exercise4 {
    public static void main(String[] args) {
        Circle c1 = new Circle();          // unit circle at (0, 0)
        Circle c2 = new Circle(3, 4, 5);   // center (3, 4), radius 5
        Circle c3 = new Circle(-1, 2, 2.5);

        Circle[] circles = {c1, c2, c3};
        for (int i = 0; i < circles.length; i++) {
            System.out.print("Circle " + (i + 1) + ": ");
            circles[i].displayInfo();
        }
        System.out.println();

        // Test several points against c2 (center (3, 4), radius 5)
        double[][] points = {
            {3, 4},    // center -> inside
            {0, 0},    // distance 5 -> on the boundary -> inside
            {8, 4},    // distance 5 -> on the boundary -> inside
            {9, 9},    // outside
            {4, 5}     // distance ~1.41 -> inside
        };
        for (double[] p : points) {
            System.out.printf("c2.contains(%.1f, %.1f) = %b%n", p[0], p[1], c2.contains(p[0], p[1]));
        }
        System.out.println();

        // Test the unit circle
        System.out.println("c1.contains(0.5, 0.5) = " + c1.contains(0.5, 0.5)); // true
        System.out.println("c1.contains(1, 1)     = " + c1.contains(1, 1));     // false
    }
}

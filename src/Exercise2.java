/*
 * Exercise 2: Fraction - State Validation and Object Operations
 * Object/Class : Fraction
 * Attributes   : private int numerator, denominator (denominator never 0)
 * Constructors : Fraction()                  -> 0/1
 *                Fraction(int, int)          -> validates denominator != 0
 *                Fraction(Fraction other)    -> copy constructor
 * Methods      : simplify(), add(Fraction), subtract(Fraction),
 *                multiply(Fraction), divide(Fraction), display()
 *                Arithmetic methods return a NEW Fraction object.
 */
class Fraction {
    private int numerator;
    private int denominator;

    // No-argument constructor: represents 0/1
    public Fraction() {
        this(0, 1);
    }

    // Parameterized constructor: rejects denominator 0, keeps the sign in the numerator
    public Fraction(int numerator, int denominator) {
        if (denominator == 0) {
            throw new IllegalArgumentException("Denominator must not be 0.");
        }
        if (denominator < 0) {
            numerator = -numerator;
            denominator = -denominator;
        }
        this.numerator = numerator;
        this.denominator = denominator;
    }

    // Copy constructor: creates a distinct object with the same state
    public Fraction(Fraction other) {
        this.numerator = other.numerator;
        this.denominator = other.denominator;
    }

    public int getNumerator() {
        return numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    // Helper: greatest common divisor (Euclid)
    private static int gcd(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            int temp = a % b;
            a = b;
            b = temp;
        }
        return a;
    }

    // Input: none | Output: new reduced Fraction | Purpose: divide numerator and denominator by gcd
    public Fraction simplify() {
        int g = gcd(numerator, denominator); // denominator != 0, so g >= 1
        return new Fraction(numerator / g, denominator / g);
    }

    // Input: Fraction other | Output: new Fraction (this + other) | Purpose: addition
    public Fraction add(Fraction other) {
        int n = this.numerator * other.denominator + other.numerator * this.denominator;
        int d = this.denominator * other.denominator;
        return new Fraction(n, d).simplify();
    }

    // Input: Fraction other | Output: new Fraction (this - other) | Purpose: subtraction
    public Fraction subtract(Fraction other) {
        int n = this.numerator * other.denominator - other.numerator * this.denominator;
        int d = this.denominator * other.denominator;
        return new Fraction(n, d).simplify();
    }

    // Input: Fraction other | Output: new Fraction (this * other) | Purpose: multiplication
    public Fraction multiply(Fraction other) {
        int n = this.numerator * other.numerator;
        int d = this.denominator * other.denominator;
        return new Fraction(n, d).simplify();
    }

    // Input: Fraction other | Output: new Fraction (this / other) | Purpose: division
    public Fraction divide(Fraction other) {
        if (other.numerator == 0) {
            throw new ArithmeticException("Cannot divide by a fraction equal to 0.");
        }
        int n = this.numerator * other.denominator;
        int d = this.denominator * other.numerator;
        return new Fraction(n, d).simplify();
    }

    // Input: none | Output: none (prints to console) | Purpose: show the fraction as n/d
    public void display() {
        System.out.println(numerator + "/" + denominator);
    }
}

public class Exercise2 {
    public static void main(String[] args) {
        Fraction f1 = new Fraction(1, 2);
        Fraction f2 = new Fraction(-3, 4);
        Fraction f4 = new Fraction(6, -8);   // stored as -6/8, simplified later to -3/4
        Fraction zero = new Fraction();      // 0/1

        System.out.print("f1 = ");  f1.display();
        System.out.print("f2 = ");  f2.display();
        System.out.print("f4 = ");  f4.display();
        System.out.print("zero = ");  zero.display();
        System.out.println();

        System.out.print("f4.simplify() = ");  f4.simplify().display();
        System.out.print("f1 + f2 = ");        f1.add(f2).display();
        System.out.print("f1 - f2 = ");        f1.subtract(f2).display();
        System.out.print("f1 * f2 = ");        f1.multiply(f2).display();
        System.out.print("f1 / f2 = ");        f1.divide(f2).display();
        System.out.println();

        // Copy constructor: create f3 from f1 and verify it is a distinct object
        Fraction f3 = new Fraction(f1);
        System.out.print("f3 (copy of f1) = ");
        f3.display();
        System.out.println("f1 == f3 ? " + (f1 == f3));                 // false: different objects
        System.out.println("Same state?  " + (f1.getNumerator() == f3.getNumerator()
                && f1.getDenominator() == f3.getDenominator()));        // true: same values

        // Demonstrate that arithmetic returns a new object, leaving the operands unchanged
        Fraction sum = f1.add(f3);
        System.out.println("sum == f1 ? " + (sum == f1) + ", f1 still = " 
                + f1.getNumerator() + "/" + f1.getDenominator());
    }
}

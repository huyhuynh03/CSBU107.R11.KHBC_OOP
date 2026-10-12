/*
 * Exercise 6: Calculator - Method Overloading
 * Class       : Calculator (stateless, static utility methods)
 * Methods     : add(int, int), add(double, double), add(int, int, int)
 *               max(int, int), max(double, double)
 * Overload resolution: Java picks the most specific version whose parameter types
 * match the arguments, using widening conversion (int -> double) when no exact match exists.
 */
class Calculator {
    // Input: int a, int b | Output: int | Purpose: sum of two integers
    public static int add(int a, int b) {
        return a + b;
    }

    // Input: double a, double b | Output: double | Purpose: sum of two decimals
    public static double add(double a, double b) {
        return a + b;
    }

    // Input: int a, int b, int c | Output: int | Purpose: sum of three integers
    public static int add(int a, int b, int c) {
        return a + b + c;
    }

    // Input: int a, int b | Output: int | Purpose: larger of two integers
    public static int max(int a, int b) {
        return (a > b) ? a : b;
    }

    // Input: double a, double b | Output: double | Purpose: larger of two decimals
    public static double max(double a, double b) {
        return (a > b) ? a : b;
    }
}

public class Exercise6 {
    public static void main(String[] args) {
        // add(int, int) is selected: both arguments are int literals
        System.out.println("add(2, 3)           = " + Calculator.add(2, 3)
                + "  -> add(int, int) selected (exact match)");

        // add(double, double) is selected: arguments are double literals
        System.out.println("add(2.5, 3.5)       = " + Calculator.add(2.5, 3.5)
                + "  -> add(double, double) selected (exact match)");

        // add(double, double) is selected: int 2 is widened to double, no int,int version fits a double
        System.out.println("add(2, 3.5)         = " + Calculator.add(2, 3.5)
                + "  -> add(double, double) selected (int widened to double)");

        // add(int, int, int) is selected: three arguments
        System.out.println("add(1, 2, 3)        = " + Calculator.add(1, 2, 3)
                + "  -> add(int, int, int) selected (three parameters)");

        // max(int, int) is selected
        System.out.println("max(7, 4)           = " + Calculator.max(7, 4)
                + "  -> max(int, int) selected (exact match)");

        // max(double, double) is selected
        System.out.println("max(2.5, 1.5)       = " + Calculator.max(2.5, 1.5)
                + "  -> max(double, double) selected (exact match)");

        // max(double, double) is selected: int 3 is widened to double
        System.out.println("max(3, 2.5)         = " + Calculator.max(3, 2.5)
                + "  -> max(double, double) selected (int widened to double)");
    }
}

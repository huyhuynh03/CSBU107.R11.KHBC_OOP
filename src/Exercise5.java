import java.util.Random;

/*
 * Exercise 5: Dice - Object State and Repeated Method Calls
 * Object/Class : Dice
 * Attributes   : private int value (most recent roll, 1-6)
 * Constructors : Dice()  -> value initialized to 1
 * Methods      : roll() -> generates a new value in [1, 6], updates state, returns it
 *                getValue() -> returns the most recent roll
 */
class Dice {
    private static final Random RANDOM = new Random();
    private int value;

    // Constructor: initial value is 1
    public Dice() {
        this.value = 1;
    }

    // Input: none | Output: int (new roll, 1-6) | Purpose: roll the die and store the result
    public int roll() {
        value = RANDOM.nextInt(6) + 1;
        return value;
    }

    // Input: none | Output: int | Purpose: read the most recent roll
    public int getValue() {
        return value;
    }
}

public class Exercise5 {
    public static void main(String[] args) {
        final int n = 6000;                 // number of rolls
        Dice dice = new Dice();
        int[] frequency = new int[7];       // index 1..6 used for the faces

        System.out.println("Initial value: " + dice.getValue());

        // Show how the object state changes over the first few rolls, counting each roll
        for (int i = 1; i <= 5; i++) {
            int result = dice.roll();
            frequency[result]++;
            System.out.println("Roll " + i + " -> " + result + " (getValue() = " + dice.getValue() + ")");
        }

        // Roll the remaining times and count each face (total exactly n rolls)
        for (int i = 6; i <= n; i++) {
            frequency[dice.roll()]++;
        }

        System.out.println();
        System.out.println("Face | Count | Experimental probability");
        for (int face = 1; face <= 6; face++) {
            double probability = (double) frequency[face] / n;
            System.out.printf("%4d | %5d | %.4f (%.2f%%)%n",
                    face, frequency[face], probability, probability * 100);
        }
        System.out.println("Theoretical probability of each face: 1/6 = 0.1667");
    }
}

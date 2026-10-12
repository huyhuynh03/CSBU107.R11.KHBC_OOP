/*
 * Exercise 3: Time - Maintaining a Valid Object State
 * Object/Class : Time
 * Attributes   : private int hour [0,23], minute [0,59], second [0,59]
 * Constructors : Time()                    -> 00:00:00
 *                Time(int, int, int)       -> validates each field
 * Methods      : display(), addSeconds(int), subtractSeconds(int)
 *                Results wrap around a 24-hour day.
 */
class Time {
    private int hour;
    private int minute;
    private int second;

    // No-argument constructor: 00:00:00
    public Time() {
        this(0, 0, 0);
    }

    // Parameterized constructor: validates hour in [0,23], minute and second in [0,59]
    public Time(int hour, int minute, int second) {
        if (hour < 0 || hour > 23) {
            throw new IllegalArgumentException("Hour must be in [0, 23]: " + hour);
        }
        if (minute < 0 || minute > 59) {
            throw new IllegalArgumentException("Minute must be in [0, 59]: " + minute);
        }
        if (second < 0 || second > 59) {
            throw new IllegalArgumentException("Second must be in [0, 59]: " + second);
        }
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    // Input: none | Output: none (prints HH:MM:SS) | Purpose: show the current time
    public void display() {
        System.out.printf("%02d:%02d:%02d%n", hour, minute, second);
    }

    // Input: int seconds | Output: none (updates this object) | Purpose: move time forward, wrapping at 24h
    public void addSeconds(int seconds) {
        setFromTotalSeconds(toTotalSeconds() + seconds);
    }

    // Input: int seconds | Output: none (updates this object) | Purpose: move time backward, wrapping at 24h
    public void subtractSeconds(int seconds) {
        addSeconds(-seconds);
    }

    // Helper: convert the time to seconds since midnight
    private int toTotalSeconds() {
        return hour * 3600 + minute * 60 + second;
    }

    // Helper: set fields from seconds since midnight, wrapping with floorMod (handles negatives)
    private void setFromTotalSeconds(int total) {
        int t = Math.floorMod(total, 24 * 3600);
        this.hour = t / 3600;
        this.minute = (t % 3600) / 60;
        this.second = t % 60;
    }
}

public class Exercise3 {
    public static void main(String[] args) {
        // Case 1: 23:59:50 + 20 seconds -> wraps to 00:00:10
        Time t1 = new Time(23, 59, 50);
        System.out.print("Start: ");
        t1.display();
        t1.addSeconds(20);
        System.out.print("After addSeconds(20): ");
        t1.display();
        System.out.println();

        // Case 2: 00:00:10 - 20 seconds -> wraps to 23:59:50
        Time t2 = new Time(0, 0, 10);
        System.out.print("Start: ");
        t2.display();
        t2.subtractSeconds(20);
        System.out.print("After subtractSeconds(20): ");
        t2.display();
        System.out.println();

        // Case 3: default constructor and a larger jump
        Time t3 = new Time();
        System.out.print("Default time: ");
        t3.display();
        t3.addSeconds(3725);   // 1 hour, 2 minutes, 5 seconds
        System.out.print("After addSeconds(3725): ");
        t3.display();

        // Case 4: invalid input is rejected
        try {
            new Time(24, 0, 0);
        } catch (IllegalArgumentException e) {
            System.out.println("Expected error: " + e.getMessage());
        }
    }
}

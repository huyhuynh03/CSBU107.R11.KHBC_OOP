# Lab 2 Exercise Explanations (OOP - Java)

Each exercise is in its own file under `src/`. The header comment of each file describes the class, attributes, constructors, and methods.

## Exercise 1: Rectangle
- **Attributes:** `width` and `height` as `double`, declared `private`.
- **Constructors:** the no-argument constructor calls `this(1, 1)`. The parameterized constructor replaces any non-positive value with 1.
- **Methods:** `area()` returns the area, `perimeter()` returns the perimeter, and `displayInfo()` prints the information.
- **main:** creates three rectangles, including one with a negative width to test the replacement rule.

## Exercise 2: Fraction
- **Attributes:** `numerator` and `denominator` as `int`, both `private`. The constructor throws an exception if the denominator is 0 and moves any negative sign to the numerator.
- **Constructors:** no-argument (0/1), parameterized, and the copy constructor `Fraction(Fraction other)`.
- **Methods:** `simplify()` divides the numerator and denominator by their greatest common divisor (Euclid's algorithm). `add`, `subtract`, `multiply`, and `divide` create and return a new simplified `Fraction`.
- **main:** checks the copy with `==` (different objects) and by comparing values (same state).

## Exercise 3: Time
- **Attributes:** `hour`, `minute`, `second`. The constructor validates hour in [0, 23] and minute and second in [0, 59].
- **Wrap-around:** the time is converted to seconds since midnight, the seconds are added or subtracted, and the result is taken with `Math.floorMod(total, 86400)`. This keeps the value within one day, including when the input is negative.
- **main:** demonstrates 23:59:50 + 20 s = 00:00:10 and 00:00:10 − 20 s = 23:59:50.

## Exercise 4: Circle
- **Attributes:** `centerX`, `centerY`, `radius`. The default constructor creates a unit circle centered at (0, 0).
- **Methods:** `area()` = π·r² and `perimeter()` = 2π·r.
- **`contains(x, y)`:** compares the squared distance from the point to the center with r². A small tolerance is added so points exactly on the boundary count as inside.

## Exercise 5: Dice
- **Attribute:** `value`, initialized to 1.
- **`roll()`:** generates a random number from 1 to 6 with `Random.nextInt(6) + 1`, stores it in `value`, and returns it.
- **main:** rolls 6000 times, counts each face in the `frequency` array, and computes the experimental probability as count / total. The initial counting bug, which skipped the first five rolls, has been fixed.

## Exercise 6: Calculator
- **Overloads:** `add(int, int)`, `add(double, double)`, `add(int, int, int)`, `max(int, int)`, and `max(double, double)`.
- **Overload resolution:** Java selects the version that matches the argument types and count. If no exact type match exists, an `int` is widened to `double`. For example, `add(2, 3.5)` calls the `double` version.

## Exercise 7: UITStudent
- **Attributes:** `studentId`, `fullName`, `major`, and `gpa`, all `private`. The GPA is validated to be in [0.0, 4.0].
- **Constructor chaining:** the no-argument constructor calls `this("N/A", "Unknown")`. The two-argument constructor calls `this(id, name, "Undeclared", 0.0)`.
- **Methods:**
  - `isPassed()`: returns true when GPA ≥ 2.0.
  - `academicClassification()`: ≥ 3.6 is Excellent, ≥ 3.2 is Very Good, ≥ 2.5 is Good, ≥ 2.0 is Average, and anything lower is Weak.
  - `displayInfo()`: prints all information.
- **main:** creates five students using the three different constructors.

## Compile and Run
```
javac -encoding UTF-8 -d out src/*.java
java -cp out Exercise1
```
Replace `Exercise1` with the exercise you want to run.

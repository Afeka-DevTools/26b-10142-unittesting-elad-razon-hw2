# Chat 1 – learning unit testing in Java (part 2)

Tool: Claude
Around 17 June 2026

these are notes from the chat i had to understand unit testing before i started. i kept only the useful parts, not everything.

---

**Me:** ok so i have a "development tools" assignment. there is a gradle java project with a class called App that has some static methods (math, strings, and a few that work on lists). i need to write unit tests for them but i never wrote tests before. can you explain simply what a unit test is?

**Claude:** Sure. A unit test is a small piece of code that calls one of your methods with a known input and checks the result is what you expect. Instead of running the whole program and eyeballing the output, the test does the check for you and you can re-run it anytime. The point of it: you catch bugs early, and later you can change your code without being scared you quietly broke something.

**Me:** which framework do i use? it's a normal gradle project

**Claude:** JUnit 5 (also called "Jupiter"), that's the current standard. In Gradle you switch it on with `useJUnitPlatform()` in the test task plus the `junit-jupiter` dependency — but your project template already has all that wired up, so you really just need to write the test class.

**Me:** can you show me how a basic test looks? just so i understand how to write it

**Claude:**
```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExampleTest {
    @Test
    void addsTwoNumbers() {
        assertEquals(5, Example.add(2, 3));
    }
}
```
Every test is a method marked `@Test`. The usual flow is arrange–act–assert: set up the input, call the method, assert on what comes back. Since your App methods are `static`, you just call them like `App.add(2, 3)` — no need to create an object first.

**Me:** what assert methods are there? i don't want to use only assertEquals for everything

**Claude:** The ones you'll actually use:
- `assertEquals(expected, actual)` — values, and it also works for lists/maps
- `assertTrue` / `assertFalse` — for booleans
- `assertThrows(SomeException.class, () -> ...)` — checks the call throws
- `assertIterableEquals` — compares two iterables element by element
- and there's an `assertEquals(expected, actual, delta)` version for doubles, so you're not comparing floats for exact equality

**Me:** how do i test that a method throws an exception? i didn't understand this part

**Claude:** `assertThrows` — you pass the exception type and a lambda with the call that should blow up:
```java
assertThrows(IllegalArgumentException.class, () -> App.factorial(-1));
```
It also returns the exception, so if you want you can check the message too:
```java
var ex = assertThrows(IllegalArgumentException.class, () -> App.factorial(-1));
assertEquals("Negative number", ex.getMessage());
```

**Me:** some of my methods don't check null at all, so they will just crash on null. should i test that too or skip it?

**Claude:** Test it. If passing null makes the method throw a NullPointerException, that's still part of how it behaves, so an `assertThrows(NullPointerException.class, ...)` is fair game — it documents what actually happens. It's a legit edge case.

**Me:** and how do i know when i wrote enough tests? like that i checked everything and not only the obvious cases

**Claude:** For each method think in three buckets: the normal case, the edges (empty, zero, negatives, a single element), and the error ones (bad input, null). To actually measure whether you hit every branch, use a coverage tool — for Gradle that's JaCoCo. It runs alongside the tests and reports line and branch coverage. 100% branch means every if/else was taken both ways at least once. Easiest to add it once you've got the tests written.

**Me:** ok, that's enough to start

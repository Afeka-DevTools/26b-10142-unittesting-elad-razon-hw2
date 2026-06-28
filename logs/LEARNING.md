# LEARNING.md - learning unit testing in Java (part 2)

my write-up of part 2: i used an LLM to learn how to write unit tests in Java before i started writing my own.

- Tool: Claude (paid account)
- Full chat: [chats/01-learning-unit-testing.md](../chats/01-learning-unit-testing.md)
- Date: around 17 June 2026

## what i wanted to learn

i never wrote unit tests in Java before, so i asked Claude to explain the basics for my case: a Gradle project with a class `App` that has a bunch of static methods (math, strings, and some that work on lists). i asked for short examples so i could actually see how a test looks.

## what i learned

- a unit test calls one method with a known input and checks the result is what i expect - automatically, so i don't have to run the program and check by hand every time.
- the framework is JUnit 5 (Jupiter). in Gradle it's turned on with `useJUnitPlatform()` and the `junit-jupiter` dependency, which the template already had.
- a test is a method with `@Test`. since my methods are `static` i just call them like `App.add(2, 3)` - no need to create an object.
- the assert methods i'd use: `assertEquals` (also works for lists and maps), `assertTrue` / `assertFalse`, `assertThrows` for exceptions (and i can check the message too), `assertIterableEquals` for lists, and `assertEquals` with a delta for doubles.
- to test an exception you use `assertThrows` with the exception type and a lambda. and if a method doesn't check for null and just crashes with a NullPointerException, that's still worth testing.
- to know if i wrote enough tests: cover the normal case, the edges (empty, zero, negative, a single element), and the error cases (bad input, null). and to really check i hit every branch, use a coverage tool - JaCoCo - which shows line and branch coverage.

## how this helped in part 3

this gave me a simple checklist: for each of the 11 methods, think about normal / edge / error, write the tests, then check with JaCoCo that i didn't miss a branch. that's exactly what i did in part 3 (see [logs/COPILOT.md](COPILOT.md)).

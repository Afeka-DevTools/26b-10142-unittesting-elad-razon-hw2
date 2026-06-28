# 26B-10142 Unit Testing - Development Tools (HW2)

Unit tests written in Java (JUnit 5) for the functions provided in `App.java`,
built and run with Gradle. This is the second assignment of the "Development
Tools" course (working with Gradle and writing unit tests in Java).

## Team

- Elad Razon

> This assignment was completed individually, with approval.

## What was done

The base project provides a class `App` with eleven static utility functions.
The placeholder test that comes with the template does not even compile against
this class, so it was replaced with a full test class, `AppTest`, that verifies
all of the functions - covering the normal cases, the boundary values and the
error paths.

The functions under test:

| Function | Description |
|----------|-------------|
| `add(a, b)` | Sum of two integers. |
| `isPrime(n)` | Whether `n` is a prime number. |
| `reverse(s)` | The string reversed. |
| `factorial(n)` | n!; throws `IllegalArgumentException` on a negative input. |
| `isPalindrome(s)` | Palindrome check, ignoring case and non-alphanumeric characters. |
| `fibonacciUpTo(n)` | List of Fibonacci numbers up to `n`; throws on a negative input. |
| `charFrequency(s)` | Map of each character to how many times it appears. |
| `isAnagram(s1, s2)` | Whether the two strings are anagrams (ignoring case and spaces). |
| `average(arr)` | Average of an int array; throws `IllegalArgumentException` on an empty array. |
| `filterEvens(list)` | A list with only the even numbers from the input. |
| `mostCommonWord(text)` | The word that appears most often in the text. |

The test suite contains **53 tests** and reaches **full (100%) branch coverage**
of `App.java`, verified with JaCoCo (see [Code coverage](#code-coverage)).

## Requirements

- A **Java JDK** installed. The project is configured to build with **Java 21**
  through a Gradle [toolchain](https://docs.gradle.org/current/userguide/toolchains.html);
  if Java 21 is not already on the machine, Gradle downloads it automatically on
  the first build. Any modern JDK is enough to start Gradle itself.

  Check that Java is available:

  ```bash
  java -version
  ```

- **Gradle does not need to be installed** - the project ships with the Gradle
  wrapper (`gradlew` / `gradlew.bat`), which downloads the correct Gradle version
  (8.14) automatically.

- An **internet connection** is needed on the first run (to download Gradle, the
  Java toolchain if needed, and the JUnit dependency).

## How to run the tests

### 1. Clone the repository

```bash
git clone https://github.com/Afeka-DevTools/26b-10142-unittesting-elad-razon-hw2.git
cd 26b-10142-unittesting-elad-razon-hw2
```

### 2. Run the tests

**On macOS / Linux (and the vLab terminal):**

```bash
./gradlew test
```

**On Windows:**

```bat
gradlew.bat test
```

When the tests pass you will see `BUILD SUCCESSFUL`. To run everything from a
clean state you can use `./gradlew clean test`.

### 3. View the test report

A detailed HTML report is generated at:

```
app/build/reports/tests/test/index.html
```

Open it in a browser to see every test and whether it passed.

## Code coverage

Coverage is measured with the JaCoCo plugin and is generated automatically right
after the tests run. After `./gradlew test`, open:

```
app/build/reports/jacoco/test/html/index.html
```

- **Branch coverage of `App.java`: 100%** (26/26) - every `if`/`else` and loop
  decision is exercised in both directions.
- **Line coverage of `App.java`: ~98%** (45/46). The single uncovered line is the
  class's implicit default constructor: every method in `App` is `static`, so the
  class is never instantiated and the constructor is never called. All of the
  actual logic is fully covered.

This is how I checked that every path in every function is tested. You can
regenerate the report on its own with:

```bash
./gradlew jacocoTestReport
```

## Project structure

```
.
├── app
│   ├── build.gradle.kts             # module build (JUnit 5 + JaCoCo, Java 21 toolchain)
│   └── src
│       ├── main/java/org/example/App.java       # functions under test (provided by the course)
│       └── test/java/org/example/AppTest.java   # the unit tests
├── chats                            # LLM conversation logs (Parts 2-3)
├── logs
│   ├── LEARNING.md                  # documentation of Part 2 (self-study)
│   └── COPILOT.md                   # documentation of Part 3 (writing the tests)
├── gradle
│   ├── libs.versions.toml           # dependency versions (version catalog)
│   └── wrapper                      # Gradle wrapper files
├── gradlew / gradlew.bat            # Gradle wrapper launchers
├── settings.gradle.kts
└── README.md
```

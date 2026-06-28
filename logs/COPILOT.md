# COPILOT.md - writing the tests with an LLM (part 3)

my write-up of part 3: i used Claude inside my editor to help write the unit tests for `App.java`, and then to check that the tests cover everything.

- Tool: Claude (in my editor)
- Full chat: [chats/02-writing-the-tests.md](../chats/02-writing-the-tests.md)
- Dates: around 18-20 June 2026

## starting point

`App` has 11 static methods. the `AppTest` that came with the template tests a `getGreeting()` method that doesn't even exist in `App`, so it doesn't compile - i replaced it with my own tests.

## how i worked

i didn't ask Claude to just write everything for me. i went method by method: i picked the cases myself, and used Claude to check i wasn't missing an edge case - mostly the weird ones that come from how the code is actually written. for example:
- `isPalindrome("A man, a plan, a canal: Panama")` is `true`, because the method first removes anything that isn't a letter or digit.
- `average(new int[]{})` throws `IllegalArgumentException("Empty array")`, but `average(null)` throws a `NullPointerException` (it reads `arr.length` before the check), so those are two separate tests.
- a few methods don't check for null, so passing null throws an NPE - i tested that on purpose.

i grouped the tests per method with comment headers, and i also checked the exception messages where the code sets them (`factorial`, `fibonacciUpTo`, `average`).

## the cases i tested

| Method | Cases tested |
|--------|--------------|
| `add` | positives; negatives; adding zero; opposite numbers (=0) |
| `isPrime` | 0/1; negative; 2 and 3; composites 4/9/25; primes 17/97 |
| `reverse` | normal word; empty; single char; null -> NPE |
| `factorial` | 0 and 1; 5; 12 (largest int); negative -> IAE |
| `isPalindrome` | plain; mixed case + punctuation + spaces; digits; non-palindrome; empty; symbols-only; null -> NPE |
| `fibonacciUpTo` | 0; 10; stops at the limit (12); negative -> IAE |
| `charFrequency` | repeated chars; mixed string; empty -> empty map; null -> NPE |
| `isAnagram` | simple; ignores case & spaces; different words; different lengths; null -> NPE |
| `average` | several; single; fractional; negatives; empty -> IAE; null -> NPE |
| `filterEvens` | mixed; none even; zero & negative evens; empty; null -> NPE |
| `mostCommonWord` | clear winner; case-insensitive; punctuation; single word; null -> NPE |

asserts i used: `assertEquals` (values, lists and maps), `assertEquals` with a delta (for the double in `average`), `assertTrue`, `assertFalse`, `assertIterableEquals`, and `assertThrows`.

## how i know the tests are enough

the assignment asks how you know the tests cover everything and how to check that all the paths were tested. i used code coverage:

1. i added the `jacoco` plugin to `app/build.gradle.kts` so the report is made automatically after the tests run.
2. i ran `./gradlew test jacocoTestReport` and opened `app/build/reports/jacoco/test/html/index.html`.

results:
- 53 tests, all passing.
- branch coverage of `App`: 100% (26/26) - every if/else and loop goes both ways.
- line coverage: about 98% (45/46). the one line not covered is the class's automatic constructor - all my methods are static, so the class is never created with `new App()`. i didn't add a fake test just to make that line green.

so every branch of every real method is tested.

## what i think

Claude was mostly useful as a second pair of eyes: confirming my edge cases, reminding me about the null-vs-empty thing in `average`, and showing me the JaCoCo setup. the decisions about what to test, and reading the coverage, were mine.

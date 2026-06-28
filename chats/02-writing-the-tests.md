# Chat 2 – writing the actual tests (part 3)

Tool: Claude (in my editor)
Around 18–19 June 2026

i went method by method. i picked the cases myself and used Claude mostly to check that i didn't miss some edge case in how the code works. kept only the useful parts.

---

**Me:** ok here is the App class. it has 11 static methods: add, isPrime, reverse, factorial, isPalindrome, fibonacciUpTo, charFrequency, isAnagram, average, filterEvens, mostCommonWord. the AppTest from the template tests a getGreeting() method that does not even exist in the class, so i am deleting it and writing my own. before i start, help me list which cases to test for each method

**Claude:** Yeah, that template test is just the default Gradle init placeholder, fine to drop. Quick rundown per method:
- add — positives, negatives, with zero, two opposite numbers (sum 0)
- isPrime — 0 and 1, a negative, 2 and 3, some composites (4, 9, 25), a couple of bigger primes (17, 97)
- reverse — normal word, empty string, single char, and null (it doesn't null-check, so it throws NPE)
- factorial — 0 and 1, a small one (5), the biggest that still fits in an int (12 → 479001600), and a negative (throws IllegalArgumentException, "Negative number")
- isPalindrome — a plain one, one with capitals + punctuation + spaces (it strips those out), digits, a non-palindrome, empty string, and null (NPE)
- fibonacciUpTo — 0, 10, a "stops at the limit" case, and a negative (throws "Negative input")
- charFrequency — repeated chars, a mixed string, empty (empty map), null (NPE)
- isAnagram — a normal anagram, one that ignores case and spaces, two different words, different lengths, null (NPE)
- average — several numbers, a single number, a fractional result, negatives, empty array (throws "Empty array"), and null
- filterEvens — mixed list, all odd, zero and negative evens, empty list, null (NPE)
- mostCommonWord — a clear winner, mixed case, with punctuation, a single word, null (NPE)

**Me:** small question on isPalindrome - what does it return for "A man, a plan, a canal: Panama"? i want to check the right value

**Claude:** It strips everything that isn't a letter or digit and lowercases, so you end up with "amanaplanacanalpanama", which reads the same backwards → true. Good one to include because it actually exercises the cleaning part, not just a plain palindrome.

**Me:** also i saw that average(null) and average with an empty array are not the same? i almost wrote one test for both

**Claude:** Right, don't — they're different. The empty array hits the `if (arr.length == 0)` check and throws IllegalArgumentException("Empty array"). null throws a NullPointerException instead, because it reads `arr.length` before that check even runs. Two different paths, so two separate tests, one assertThrows for each type.

**Me:** for the methods that return a list or a map, how do i compare them? assertEquals on a whole map looks strange to me

**Claude:** It's actually fine. `assertEquals(Map.of('a', 2, 'b', 1), result)` works because map equality doesn't care about order. For lists you can use `assertEquals(List.of(...), result)` or `assertIterableEquals(...)`. And for average, since it's a double, use the 3-arg `assertEquals(expected, actual, 1e-9)` so floating point doesn't trip you up.

**Me:** ok i wrote all the AppTest, grouped by method, and i added the jacoco plugin to build.gradle.kts. how do i run it and see the coverage?

**Claude:** Run `./gradlew test jacocoTestReport`, then open `app/build/reports/jacoco/test/html/index.html`. The column that matters is branch coverage — 100% there means every decision went both ways.

**Me:** i ran it, 53 tests passed. branch is 100% but line coverage says 98% and one method is "not covered"?? what did i miss?

**Claude:** Look at which method it is — it's almost certainly the class's implicit default constructor. All your methods are static, so you never write `new App()`, which means that auto-generated constructor never runs. It's not real logic, so I wouldn't bother adding a fake `new App()` test just to turn it green. 100% branch with only the constructor left means every actual code path is tested.

**Me:** yes that was it, the marked line was the class declaration. so i am leaving it like this

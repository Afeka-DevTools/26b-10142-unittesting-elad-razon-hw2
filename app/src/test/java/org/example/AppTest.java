package org.example;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for {@link App}.
 *
 * The methods in App are all static, so the tests call them directly through
 * the class. The tests are grouped per method, and for every method I tried to
 * cover the normal case, the boundary values and the error paths (so that each
 * branch of the code is exercised at least once).
 */
class AppTest {

    // ---------- add ----------

    @Test
    void addsTwoPositiveNumbers() {
        assertEquals(5, App.add(2, 3));
    }

    @Test
    void addsTwoNegativeNumbers() {
        assertEquals(-5, App.add(-2, -3));
    }

    @Test
    void addingZeroReturnsTheOtherNumber() {
        assertEquals(10, App.add(10, 0));
    }

    @Test
    void addingOppositeNumbersGivesZero() {
        assertEquals(0, App.add(-7, 7));
    }

    // ---------- isPrime ----------

    @Test
    void numbersBelowTwoAreNotPrime() {
        assertFalse(App.isPrime(0));
        assertFalse(App.isPrime(1));
    }

    @Test
    void negativeNumbersAreNotPrime() {
        assertFalse(App.isPrime(-5));
    }

    @Test
    void twoAndThreeArePrime() {
        assertTrue(App.isPrime(2));
        assertTrue(App.isPrime(3));
    }

    @Test
    void smallCompositesAreNotPrime() {
        assertFalse(App.isPrime(4));
        assertFalse(App.isPrime(9));
        assertFalse(App.isPrime(25));
    }

    @Test
    void largerPrimesAreDetected() {
        assertTrue(App.isPrime(17));
        assertTrue(App.isPrime(97));
    }

    // ---------- reverse ----------

    @Test
    void reverseRegularWord() {
        assertEquals("cba", App.reverse("abc"));
    }

    @Test
    void reverseEmptyStringStaysEmpty() {
        assertEquals("", App.reverse(""));
    }

    @Test
    void reverseSingleCharacter() {
        assertEquals("a", App.reverse("a"));
    }

    @Test
    void reverseNullThrows() {
        // The method does not guard against null, so a NullPointerException is expected.
        assertThrows(NullPointerException.class, () -> App.reverse(null));
    }

    // ---------- factorial ----------

    @Test
    void factorialOfZeroAndOneIsOne() {
        assertEquals(1, App.factorial(0));
        assertEquals(1, App.factorial(1));
    }

    @Test
    void factorialOfSmallNumber() {
        assertEquals(120, App.factorial(5));
    }

    @Test
    void factorialOfTwelveStillFitsInAnInt() {
        assertEquals(479001600, App.factorial(12));
    }

    @Test
    void factorialOfNegativeThrows() {
        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class, () -> App.factorial(-1));
        assertEquals("Negative number", ex.getMessage());
    }

    // ---------- isPalindrome ----------

    @Test
    void simplePalindromeIsDetected() {
        assertTrue(App.isPalindrome("racecar"));
    }

    @Test
    void palindromeCheckIgnoresCasePunctuationAndSpaces() {
        // Non-alphanumeric characters are stripped before the comparison.
        assertTrue(App.isPalindrome("A man, a plan, a canal: Panama"));
    }

    @Test
    void digitsCanFormAPalindrome() {
        assertTrue(App.isPalindrome("12321"));
    }

    @Test
    void nonPalindromeReturnsFalse() {
        assertFalse(App.isPalindrome("hello"));
    }

    @Test
    void emptyStringIsConsideredAPalindrome() {
        assertTrue(App.isPalindrome(""));
    }

    @Test
    void stringWithOnlySymbolsIsTreatedAsEmptyAndIsAPalindrome() {
        // After cleaning, the string is empty, which equals its own reverse.
        assertTrue(App.isPalindrome("!!!"));
    }

    @Test
    void palindromeNullThrows() {
        assertThrows(NullPointerException.class, () -> App.isPalindrome(null));
    }

    // ---------- fibonacciUpTo ----------

    @Test
    void fibonacciUpToZeroContainsOnlyZero() {
        assertEquals(List.of(0), App.fibonacciUpTo(0));
    }

    @Test
    void fibonacciUpToTen() {
        assertEquals(List.of(0, 1, 1, 2, 3, 5, 8), App.fibonacciUpTo(10));
    }

    @Test
    void fibonacciStopsAtTheLimit() {
        // 13 is the next Fibonacci number, so it must not appear for a limit of 12.
        assertEquals(List.of(0, 1, 1, 2, 3, 5, 8), App.fibonacciUpTo(12));
    }

    @Test
    void fibonacciOfNegativeThrows() {
        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class, () -> App.fibonacciUpTo(-1));
        assertEquals("Negative input", ex.getMessage());
    }

    // ---------- charFrequency ----------

    @Test
    void charFrequencyCountsRepeatedCharacters() {
        assertEquals(Map.of('a', 2, 'b', 1), App.charFrequency("aab"));
    }

    @Test
    void charFrequencyOfMixedString() {
        assertEquals(Map.of('h', 1, 'e', 1, 'l', 2, 'o', 1), App.charFrequency("hello"));
    }

    @Test
    void charFrequencyOfEmptyStringIsEmptyMap() {
        assertTrue(App.charFrequency("").isEmpty());
    }

    @Test
    void charFrequencyNullThrows() {
        assertThrows(NullPointerException.class, () -> App.charFrequency(null));
    }

    // ---------- isAnagram ----------

    @Test
    void recognisesASimpleAnagram() {
        assertTrue(App.isAnagram("listen", "silent"));
    }

    @Test
    void anagramCheckIgnoresCaseAndSpaces() {
        assertTrue(App.isAnagram("conversation", "voices rant on"));
    }

    @Test
    void differentWordsAreNotAnagrams() {
        assertFalse(App.isAnagram("hello", "world"));
    }

    @Test
    void wordsOfDifferentLengthAreNotAnagrams() {
        assertFalse(App.isAnagram("abc", "abcd"));
    }

    @Test
    void anagramNullThrows() {
        assertThrows(NullPointerException.class, () -> App.isAnagram(null, "abc"));
    }

    // ---------- average ----------

    @Test
    void averageOfSeveralNumbers() {
        assertEquals(4.0, App.average(new int[] {2, 4, 6}), 1e-9);
    }

    @Test
    void averageOfASingleNumber() {
        assertEquals(5.0, App.average(new int[] {5}), 1e-9);
    }

    @Test
    void averageCanBeFractional() {
        assertEquals(1.5, App.average(new int[] {1, 2}), 1e-9);
    }

    @Test
    void averageHandlesNegativeNumbers() {
        assertEquals(0.0, App.average(new int[] {-2, 2}), 1e-9);
    }

    @Test
    void averageOfEmptyArrayThrows() {
        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class, () -> App.average(new int[] {}));
        assertEquals("Empty array", ex.getMessage());
    }

    @Test
    void averageOfNullArrayThrows() {
        assertThrows(NullPointerException.class, () -> App.average(null));
    }

    // ---------- filterEvens ----------

    @Test
    void filterEvensKeepsOnlyEvenNumbers() {
        assertIterableEquals(List.of(2, 4), App.filterEvens(List.of(1, 2, 3, 4)));
    }

    @Test
    void filterEvensReturnsEmptyListWhenThereAreNoEvens() {
        assertTrue(App.filterEvens(List.of(1, 3, 5)).isEmpty());
    }

    @Test
    void filterEvensKeepsZeroAndNegativeEvens() {
        assertIterableEquals(List.of(-2, 0), App.filterEvens(List.of(-2, -1, 0)));
    }

    @Test
    void filterEvensOfEmptyListIsEmpty() {
        assertTrue(App.filterEvens(List.of()).isEmpty());
    }

    @Test
    void filterEvensNullThrows() {
        assertThrows(NullPointerException.class, () -> App.filterEvens(null));
    }

    // ---------- mostCommonWord ----------

    @Test
    void findsTheMostCommonWord() {
        assertEquals("the", App.mostCommonWord("the cat the dog the bird"));
    }

    @Test
    void mostCommonWordIgnoresCase() {
        assertEquals("apple", App.mostCommonWord("Apple apple APPLE banana"));
    }

    @Test
    void mostCommonWordIgnoresPunctuation() {
        assertEquals("hello", App.mostCommonWord("Hello, hello! Goodbye."));
    }

    @Test
    void mostCommonWordOfASingleWord() {
        assertEquals("word", App.mostCommonWord("word"));
    }

    @Test
    void mostCommonWordNullThrows() {
        assertThrows(NullPointerException.class, () -> App.mostCommonWord(null));
    }
}

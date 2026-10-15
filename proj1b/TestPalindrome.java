import org.junit.Test;

import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testisPalindrome() {
        assertTrue(palindrome.isPalindrome("qwerewq"));
        assertFalse(palindrome.isPalindrome("abcde"));
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome(""));
    }

    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testPalindromeOffByOne() {
        assertTrue(palindrome.isPalindrome("flake", offByOne));
        assertTrue(palindrome.isPalindrome("a", offByOne));
        assertTrue(palindrome.isPalindrome("", offByOne));
        assertFalse(palindrome.isPalindrome("abc", offByOne));
    }

    static CharacterComparator offByN = new OffByN(2);

    @Test
    public void testPalindromeOffByN() {
        assertTrue(palindrome.isPalindrome("", offByN));
        assertTrue(palindrome.isPalindrome("a", offByN));
        assertTrue(palindrome.isPalindrome("ac", offByN));
        assertFalse(palindrome.isPalindrome("abcd", offByN));

    }
}

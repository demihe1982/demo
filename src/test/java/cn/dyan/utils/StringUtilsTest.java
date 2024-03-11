
package cn.dyan.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StringUtilsTest {

    /**
     * Tests whether a string is all in lower case.
     */
    @Test
    void testIsLowerCase() {
        // Test strings that are entirely lower case
        assertTrue(StringUtils.isLowerCase("abc"));
        assertTrue(StringUtils.isLowerCase("xyz"));

        // Test strings containing non-lowercase letters
        assertFalse(StringUtils.isLowerCase("111"));
        assertFalse(StringUtils.isLowerCase("111#abc"));

        // Test empty and space-only strings
        assertFalse(StringUtils.isLowerCase(""));
        assertFalse(StringUtils.isLowerCase("Abc"));
        assertFalse(StringUtils.isLowerCase("XYZ"));

        // Test for NullPointerException when input is null
        assertThrows(NullPointerException.class, () -> {
            StringUtils.isLowerCase(null);
        });
    }

    /**
     * Tests the removal of consecutive duplicate characters from a string.
     *
     */
    @Test
    void testRemoveConsecutiveDuplicates() {
        // Test removing all consecutive duplicates
        assertEquals("", StringUtils.removeConsecutiveDuplicates("bbb"));

        // Test a string with no consecutive duplicates
        assertEquals("aabbc", StringUtils.removeConsecutiveDuplicates("aabbc"));

        // Test removing consecutive 'y'
        assertEquals("x", StringUtils.removeConsecutiveDuplicates("xyyy"));

        // Test when there are consecutive duplicates at the end of the string
        assertEquals("abcdef", StringUtils.removeConsecutiveDuplicates("abcdefgggg"));

        // Test removing isolated consecutive duplicates within the string
        assertEquals("d", StringUtils.removeConsecutiveDuplicates("aabcccbbad"));

        // Test replacing all repeating characters with an empty string
        assertEquals("", StringUtils.removeConsecutiveDuplicates("dcccddd"),
                "When first 'd' connects to end 'ddd', they should all be replaced with an empty string");

        // Test when the remaining characters after replacement do not match the consecutive repeat condition
        assertEquals("dd", StringUtils.removeConsecutiveDuplicates("dddcccdd"),
                "When first 'ddd' is replaced by an empty string, then the last 'dd' does not match three or more consecutive repeating characters. ");

        // Test for IllegalArgumentException with invalid characters or empty string
        assertThrows(IllegalArgumentException.class, () -> {
            StringUtils.removeConsecutiveDuplicates("ABCDEF");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            StringUtils.removeConsecutiveDuplicates("");
        });
        assertThrows(NullPointerException.class, () -> {
            StringUtils.removeConsecutiveDuplicates(null);
        });
    }

    /**
     * Tests replacing consecutive duplicate characters in a string.
     *
     */
    @Test
    public void testReplaceConsecutiveDuplicates() {
        // Test strings without any consecutive duplicates
        assertEquals("abc", StringUtils.replaceConsecutiveDuplicates("abc"));
        assertEquals("abcde", StringUtils.replaceConsecutiveDuplicates("abcde"));

        // Test replacing consecutive duplicates
        assertEquals("aabbcc", StringUtils.replaceConsecutiveDuplicates("aabbcc"));
        assertEquals("aabbccdd", StringUtils.replaceConsecutiveDuplicates("aabbccdd"));

        // Test replacing all repeating characters with an empty string
        assertEquals("", StringUtils.replaceConsecutiveDuplicates("aaaa"),
                "When 'a' is duplicated, it should be replaced with an empty string");

        // Test replacing mixed consecutive duplicates
        assertEquals("dacc", StringUtils.replaceConsecutiveDuplicates("daaaabbbbcc"));
        assertEquals("d", StringUtils.replaceConsecutiveDuplicates("abcccbad"));

        // Test replacing consecutive duplicates and checking the resulting string
        assertEquals("dbc", StringUtils.replaceConsecutiveDuplicates("dcccddd"));
        assertEquals("c", StringUtils.replaceConsecutiveDuplicates("deeeeddd"));
        assertEquals("bdd", StringUtils.replaceConsecutiveDuplicates("dddcccdd"));

        // Test for IllegalArgumentException with invalid characters or empty string
        assertThrows(IllegalArgumentException.class, () -> {
            StringUtils.replaceConsecutiveDuplicates("ABCDEF");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            StringUtils.replaceConsecutiveDuplicates("111abc");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            StringUtils.replaceConsecutiveDuplicates("");
        });
        assertThrows(NullPointerException.class, () -> {
            StringUtils.replaceConsecutiveDuplicates(null);
        });
    }

    /**
     * Tests the performance of removing and replacing consecutive duplicates.
     */
    @Test
    // @Disabled
    public void testPerformance() {
        String str = "abcccbad";
        long start = System.currentTimeMillis();
        int times = 1000000;

        // Test performance of removeConsecutiveDuplicates
        for (int i = 0; i < times; i++) {
            StringUtils.removeConsecutiveDuplicates(str);
        }
        long end = System.currentTimeMillis();
        System.out.println("removeConsecutiveDuplicates: " + (end - start) + "ms");

        // Test performance of replaceConsecutiveDuplicates
        start = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            StringUtils.replaceConsecutiveDuplicates(str);
        }
        end = System.currentTimeMillis();
        System.out.println("replaceConsecutiveDuplicates: " + (end - start) + "ms");
    }
}
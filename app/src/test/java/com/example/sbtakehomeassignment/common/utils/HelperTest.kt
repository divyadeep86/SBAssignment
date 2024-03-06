package com.example.sbtakehomeassignment.common.utils

import org.junit.Assert.*

import org.junit.Test
import java.text.DecimalFormat
import java.util.TreeMap
/**
 * Test class for number formatting helper functions.
 *
 * This class contains tests to ensure that numbers are correctly formatted
 * into a more readable form using suffixes for thousands, millions, etc.
 * The function under test is an extension function on the Int type that
 * formats the number with appropriate suffixes (k, M, G, T, P, E) based on its size.
 */
class HelperTest {
    /**
     * Tests the formatNumber extension function.
     *
     * The test cases cover various scenarios:
     * - Numbers below 1000 should not be formatted and returned as a string representation of the number itself.
     * - Numbers exactly 1000 and above should be formatted with the 'k' suffix and rounded to one decimal place where necessary.
     * - Numbers that reach the millions should use the 'M' suffix.
     * - Numbers that reach the billions should use the 'G' suffix.
     * - It also tests edge cases, such as when the number is just below a new suffix range.
     * - The special case of Int.MAX_VALUE is tested to ensure it's formatted correctly.
     */
    @Test
    fun testFormatNumber() {
        // Test standard cases
        assertEquals("999", 999.formatNumber())
        assertEquals("1k", 1000.formatNumber())
        assertEquals("1.1k", 1100.formatNumber())
        assertEquals("1.5k", 1500.formatNumber())
        assertEquals("10k", 10000.formatNumber())
        assertEquals("1M", 1000000.formatNumber())
        assertEquals("2.1M", 2100000.formatNumber())
        assertEquals("1G", 1000000000.formatNumber())
        // Test edge cases
        assertEquals("999M", 999000000.formatNumber())
        assertEquals("999.9M", 999900000.formatNumber())
        // Test handling of Int.MAX_VALUE
        assertEquals("2.1G", Int.MAX_VALUE.formatNumber())
    }

}
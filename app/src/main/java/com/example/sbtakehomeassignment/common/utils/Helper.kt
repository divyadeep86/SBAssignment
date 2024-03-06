package com.example.sbtakehomeassignment.common.utils

import java.text.DecimalFormat
import java.util.TreeMap

/**
 * Extension function on Int to format numbers with suffixes for thousands, millions, etc.
 *
 * Numbers smaller than 1000 are returned as is.
 * Larger numbers are divided by the appropriate
 * power of 1000 and suffixed with the corresponding letter (k, M, G, T, P, E).
 * A decimal point is included only if it's necessary (e.g., 1.1k instead of 1.0k).
 *
 * @return A string representing the formatted number.
 */
fun Int.formatNumber(): String {
    if (this < 1000) return toString() // No need to format numbers less than 1000

    val suffixes = TreeMap<Long, String>().apply {
        put(1_000L, "k")
        put(1_000_000L, "M")
        put(1_000_000_000L, "G")
        put(1_000_000_000_000L, "T")
        put(1_000_000_000_000_000L, "P")
        put(1_000_000_000_000_000_000L, "E")
    }

    val e = suffixes.floorEntry(toLong())
    val divideBy = e.key
    val suffix = e.value

    val divisionResult = toLong() * 10 / divideBy
    val hasDecimal = divisionResult < 10000 && divisionResult % 10 != 0L
    return if (hasDecimal) {
        DecimalFormat("#.#").format(divisionResult / 10.0) + suffix
    } else {
        (divisionResult / 10).toString() + suffix
    }
}
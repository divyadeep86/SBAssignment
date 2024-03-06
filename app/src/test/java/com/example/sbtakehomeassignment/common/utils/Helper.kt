package com.example.sbtakehomeassignment.common.utils

import java.io.InputStreamReader
/**
 * Purpose: This utility class provides a method to read the contents of a file from the resources directory, converting it into a String. It's primarily used in tests to load mock JSON responses.
 *
 * Functions:
 * readFileResource(fileName: String): String
 * Purpose: Reads a file from the resources directory and returns its content as a string.
 *
 * How It Works:
 * Retrieves an InputStream for the specified file name using the class loader.
 * Initializes an InputStreamReader with the input stream to read the file content.
 * Uses a StringBuilder to accumulate the lines read from the file.
 * Iterates over each line of the file, appending it to the StringBuilder.
 * Returns the complete file content as a string.
 * */
object Helper {

    fun readFileResource(fileName: String): String {
        val inputStream = Helper::class.java.getResourceAsStream(fileName)
        val builder = StringBuilder()
        val reader = InputStreamReader(inputStream, "UTF-8")
        reader.readLines().forEach {
            builder.append(it)
        }
        return builder.toString()
    }
}
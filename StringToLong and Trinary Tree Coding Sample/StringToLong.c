/*
 * Author: Gary C. Gregg.
 *
 * Note: I wrote stringToLong(string) to behave as much as possible as
 * the atoi(const char*) function in the C stdlib library.  Its only
 * limitation, like atoi, is that strings representing numbers greater
 * than LONG_MAX will result in LONG_MAX being returned, and strings
 * representing numbers smaller than LONG_MIN will result in LONG_MIN
 * being returned.
 */

#include <limits.h>
#include <stdbool.h>
#include <stdio.h>

// Type definition of a string for this exercise.
typedef char* string;

/**
 * Determines if a character is whitespace.
 * 
 * @param c
 *            The character to examine
 * @return True if the character is whitespace, false otherwise
 */
bool isWhiteSpace(char c)
{

    // Declare the whitespace characters, and count them.
    static const char whitespace[] = { ' ', '\f', '\n', '\r', '\t', '\v' };
    static const size_t whitespaceCount = sizeof(whitespace) / sizeof(whitespace[0]);

    /*
     * Declare and initialize the return value, and cycle for each known
     * whitespace character.
     */
    bool white = false;
    for (size_t i = 0; (!white) && (i < whitespaceCount); ++i) {

        /*
         * Reset the return value, and stop looping if the current character
         * is the first/next whitespace character.
         */
        white = (c == whitespace[i]);
    }

    // Return the result to caller.
    return white;
}

/*
 * Converts a string to a long integer.
 * 
 * @param s The string to convert
 * 
 * @return The long integer corresponding to the given string
 */
long stringToLong(string s)
{

    // A safety check for a null string.
    if (NULL == s) {
        return 0l;
    }

    // Do not consider whitespace.
    char* nextCharacter = s;
    while (isWhiteSpace(*nextCharacter)) {

        /*
         * Increment the next character, and continue to check
         * for whitespace.
         */
        ++nextCharacter;
    }

    // Is the number negative?
    const bool negative = ('-' == *nextCharacter);
    if (negative) {

        /*
         * The number is negative. Increment the pointer to the
         * next digit.
         */
        ++nextCharacter;
    }

    /*
     * Declare and initialize the arithmetic base, and the
     * maximum number that the result can contain before
     * multiplication by the base.
     */
    static const long base = 10;
    const long maximum = LONG_MAX / base;

    /*
     * Declare and initialize the first digit, and the last
     * digit.
     */
    static const char firstDigit = '0';
    static const char lastDigit = '9';

    /*
     * Declare and initialize variables to contain the
     * candidate character, and the current digit.
     */
    char candidateDigit = *nextCharacter;
    long currentDigit = 0l;

    /*
     * Declare and initialize the return value, and cycle
     * while characters are digits.
     */
    long longFromString = 0l;
    while ((firstDigit <= candidateDigit) && (lastDigit >= candidateDigit)) {

        /*
         * The first/next character is a valid digit.
         * Return LONG_MIN or LONG_MAX if multiplying
         * by the base will result in overflow.
         */
        if (maximum < longFromString) {
            return negative ? LONG_MIN: LONG_MAX;
        }

        /*
         * Multiply the current result by the base.
         * Calculate the current digit by subtracting
         * the first digit from the candidate.
         */
        longFromString *= base;
        currentDigit = candidateDigit - firstDigit;

        /*
         * Return LONG_MIN or LONG_MAX if adding
         * the current digit will result in overflow.
         */
        if (LONG_MAX - currentDigit < longFromString) {
            return negative ? LONG_MIN : LONG_MAX;
        }

        /*
         * Add the current digit to the result value.
         * Consider the next digit, and loop.
         */
        longFromString += currentDigit;
        candidateDigit = *(++nextCharacter);
    }

    // Return the result to caller.
    return negative ? -longFromString : longFromString;
}

/*
 * Performs a single test of the stringToLong(string) function,
 * and displays the results.
 *
 * @param s The string to test
 * @param expected The expected result
 * @return True if the result of the text was expected, false otherwise
 */
bool test(string s, long expected)
{

    // Call stringToLong, and determine if the result was expected.
    const long result = stringToLong(s);
    const bool success = (result == expected);

    // Print information about the test, and return to caller.
    printf("Expected:\t%ld; Received:\t%ld -- %s\n", expected, result, success ? "Success!" : "Failure");
    return success;
}

/*
 * Runs a series of stringToLong(string) tests.
 *
 * @return Zero if the tests run to completion; false otherwise
 */
int main()
{

    // Null, zero and one tests.
    test(NULL, 0l);
    test("", 0l);
    test("0", 0l);
    test("1", 1l);

    // Minus zero and minus one tests.
    test("-0", 0l);
    test("-1", -1l);

    // Whitespace character tests.
    test(" \f\n\r\t\v-1", -1l);
    test(" \nx\t-1", 0l);

    // Positive overflow tests.
    test("2147483645", LONG_MAX - 2);
    test("2147483646", LONG_MAX - 1);
    test("2147483647", LONG_MAX);
    test("2147483648", LONG_MAX);
    test("2147483649", LONG_MAX);
    test("12147483646", LONG_MAX);

    // Negative overflow tests.
    test("-2147483646", LONG_MIN + 2);
    test("-2147483647", LONG_MIN + 1);
    test("-2147483648", LONG_MIN);
    test("-2147483649", LONG_MIN);
    test("-2147483650", LONG_MIN);
    test("-12147483657", LONG_MIN);

    // Miscellaneous tests.
    test(" -5", -5l);
    test(" - 5", -0l);
    test("56&79", 56l);

    // Return.
    return 0;
}


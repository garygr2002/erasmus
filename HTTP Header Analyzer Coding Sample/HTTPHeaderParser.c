/*
 * HTTPHeaderParser.c
 *
 *  Created on: Nov 20, 2014
 *      Author: gary
 */

#include <regex.h>
#include <stddef.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// HTTP headers as defined by RFC 2616
const char* headers[] = { "Accept", "Accept-Charset", "Accept-Encoding",
		"Accept-Language", "Accept-Ranges", "Age", "Allow", "Authorization",
		"Cache-Control", "Connection", "Content-Encoding", "Content-Language",
		"Content-Length", "Content-Location", "Content-MD5", "Content-Range",
		"Content-Type", "Date", "ETag", "Expect", "Expires", "From", "Host",
		"If-Match", "If-Modified-Since", "If-None-Match", "If-Range",
		"If-Unmodified-Since", "Last-Modified", "Location", "Max-Forwards",
		"Pragma", "Proxy-Authenticate", "Proxy-Authorization", "Range",
		"Referer", "Retry-After", "Server", "TE", "Trailer",
		"Transfer-Encoding", "Upgrade", "User-Agent", "Vary", "Via", "Warning",
		"WWW-Authenticate" };

// The number of HTTP headers
const size_t headerCount = sizeof(headers) / sizeof(headers[0]);

/**
 * Counts the number of times a character occurs in a string.
 *
 * @param string The string to consider
 * @param character The character to count
 * @return The number of times the character occurs in the string
 */
static size_t count(const char* string, const char character) {

	/*
	 * Initialize the return value, and cycle until the null
	 * character is observed.
	 */
	size_t total = 0;
	while ('\0' != *string) {

		/*
		 * Increment the total if the first/next character is
		 * character under consideration.
		 */
		if (character == *string) {
			++total;
		}

		// Move the string pointer.
		++string;
	}

	// Return the total.
	return total;
}

/**
 * Builds a regular expression for an HTTP header. Use deleteRegex(char*) to
 * dispose of the returned memory.
 *
 * @param header An HTTP header
 * @return A regular expression corresponding to the HTTP header.
 */
static char* buildHeaderRegex(const char* header) {

	/*
	 * Declare and initialize a constant dash character. Determine the length
	 * of the header string.
	 */
	const static char dash[] = "-";
	const size_t headerLength = strlen(header);

	/*
	 * Allocate a buffer large enough to build the header name as regular
	 * expression.
	 */
	char* headerRegex = calloc(headerLength + count(header, dash[0]) + 7,
			sizeof(char));

	/*
	 * Create an automatic buffer large enough to hold a copy of the
	 * header string, and copy the header string to the buffer.
	 */
	char headerCopy[headerLength + 1];
	strcpy(headerCopy, header);

	/*
	 * Initialize the header regular expression to an open parenthesis.
	 * Find the first token in the header string before any dash. Copy
	 * the token into the regular expression.
	 */
	strcpy(headerRegex, "(");
	char* token = strtok(headerCopy, dash);
	strcat(headerRegex, token);

	// Does another token exist after a dash?
	token = strtok(NULL, dash);
	while (NULL != token) {

		/*
		 * Another token exists after a dash. Append an escape character
		 * to treat the dash as a literal, and append the dash.
		 */
		strcat(headerRegex, "\\");
		strcat(headerRegex, dash);

		// Append the next token, and look for another dash.
		strcat(headerRegex, token);
		token = strtok(NULL, dash);
	}

	/*
	 * Concatenate a closing parenthesis, zero or more whitespace
	 * characters, and a colon. Return the regular expression.
	 */
	strcat(headerRegex, ")\\s*:");
	return headerRegex;
}

/**
 * Deletes a header regular expression in a uniform way.
 *
 * @param headerRegex The regular expression to delete.
 */
static void deleteRegex(char* headerRegex) {
	free(headerRegex);
}

/**
 * Compiles a regular expression.
 *
 * @param regex The structure to receive the compiled regular
 * expression
 * @param uncompiledRegex An uncompiled regular expression given
 * as a string
 * @param Zero for a successful compilation process, or error
 * values as determined by regcomp()
 */
static int compileRegularExpression(regex_t* regex, char* uncompiledRegex) {
	return regcomp(regex, uncompiledRegex, REG_EXTENDED);
}

/**
 * Frees a regular expression.
 *
 * @param regex The regular expression to free
 */
static void freeRegularExpression(regex_t* regex) {
	regfree(regex);
}

/**
 * Counts the number of times a regular expression occurs in a
 * source string.
 *
 * @param source The source string
 * @param regex A compiled regular expression
 * @return The number of times the regular expression occurs in
 * the source string
 */
static unsigned countOccurrences(const char* source, regex_t* regex) {

	/*
	 * Declare and initialize a flags constant for regexec(), and
	 * the return value for this method.
	 */
	const int eflags = REG_NOTBOL | REG_NOTEOL;
	unsigned occurrences = 0;

	/*
	 * Declare and initialize an offset into the source string for
	 * multiple calls to regexec(), and a match structure for use
	 * by regexec().
	 */
	regoff_t offset = 0;
	regmatch_t pmatch;

	/*
	 * Search for the first occurrence of the regular expression.
	 * Were any found?
	 */
	int searchResult = regexec(regex, source, 1, &pmatch, eflags);
	while (!searchResult) {

		/*
		 * The regular expression was found. Increment the number
		 * of occurrences of the regular expression, increment the
		 * search offset, and search again.
		 */
		++occurrences;
		offset += pmatch.rm_eo;
		searchResult = regexec(regex, source + offset, 1, &pmatch, eflags);
	}

	// Return the number of occurrences.
	return occurrences;
}

/**
 * Calculates the size of a file.
 *
 * @param file Pointer to a file structure
 * @return The size of the file
 */
static long calculateFileSize(FILE* file) {

	/*
	 * Seek the end of the file, and calculate the offset from
	 * the beginning.
	 */
	fseek(file, 0L, SEEK_END);
	const long offsetToEnd = ftell(file);

	// Return the file to the beginning, and return the offset.
	fseek(file, 0L, SEEK_SET);
	return offsetToEnd;
}

/**
 * Analyzes a buffer for occurrences of each HTTP header.
 *
 * @param The buffer to search for HTTP headers
 * @return An array of counts of HTTP header occurrences,
 * corresponding in position to the headers as they occur
 * in the array
 */
unsigned* analyze(const char* buffer) {

	/*
	 * Declare and initialize a header regex and a regex
	 * structure. Allocate a buffer for statistics.
	 */
	char* headerRegex = NULL;
	regex_t regex;
	unsigned* statistics = calloc(headerCount, sizeof(unsigned));

	// Cycle for each HTTP header.
	size_t i = 0;
	for (i = 0; i < headerCount; ++i) {

		/*
		 * Build the header regex for the first/next
		 * header. Compile a regular expression based
		 * on the header regex.
		 */
		headerRegex = buildHeaderRegex(headers[i]);
		compileRegularExpression(&regex, headerRegex);

		// Delete the header regex.
		deleteRegex(headerRegex);
		headerRegex = NULL;

		/*
		 * Count occurrences of the header in the buffer,
		 * and free the compiled regular expression.
		 */
		statistics[i] = countOccurrences(buffer, &regex);
		freeRegularExpression(&regex);
	}

	// Return the statistics buffer.
	return statistics;
}

/**
 * Frees statistics created by analyze(const char*).
 *
 * @param statistics Statistics created by
 * analyze(const char*)
 */
void freeStatistics(unsigned* statistics) {
	free(statistics);
}

/**
 * Reports on statistics created by analyze(const char*).
 *
 * @param statistics Statistics created by
 * analyze(const char*)
 */
void report(const unsigned* statistics) {

	// Output a report identifier.
	FILE* outputFile = stdout;
	fprintf(outputFile, "HTTP Header Report Statistics:\n\n");

	// Cycle for each header.
	size_t i = 0;
	for (i = 0; i < headerCount; ++i) {

		// Print the header name and its statistics.
		fprintf(outputFile, "%20s: %10u\n", headers[i], statistics[i]);
	}
}

/**
 * Performs an HTTP header analysis on a file.
 *
 * @param argc Count of command line arguments
 * @param argv Command line arguments
 * @return Zero if the program ran successfully
 */
int main(int argc, char* argv[]) {

	/*
	 * Declare and initialize the file pointer to be used for
	 * error reporting. Are there less than two arguments?
	 */
	FILE* errorOutput = stderr;
	if (argc < 2) {

		/*
		 * There are less than two arguments. Print an error
		 * message and return.
		 */
		fprintf(errorOutput, "Filename must be given as program argument.\n");
		return 0;
	}

	/*
	 * Attempt to open the file given as the first command
	 * line argument. Did the file not open successfully?
	 */
	FILE* file = fopen(argv[1], "r");
	if (NULL == file) {

		/*
		 * The file did not open successfully. Print an
		 * error and return.
		 */
		fprintf(errorOutput, "Unable to open file.\n");
		return 0;
	}

	/*
	 * Calculate the size of the file, and allocate a buffer
	 * large enough to hold all of it.
	 */
	const long fileSize = calculateFileSize(file);
	char* buffer = calloc(fileSize, sizeof(char));

	/*
	 * Read the whole file. Were all the characters not read
	 * into the buffer?
	 */
	const size_t charsRead = fread(buffer, sizeof(char), fileSize, file);
	if (charsRead != fileSize) {

		/*
		 * All the characters were not read into the buffer.
		 * Print an error message, but continue.
		 */
		fprintf(errorOutput, "Not all the file could be read.\n");
	}

	// Analyze the buffer and report statistics.
	unsigned* statistics = analyze(buffer);
	report(statistics);

	// Free statistics.
	freeStatistics(statistics);
	statistics = NULL;

	// Free the buffer.
	free(buffer);
	buffer = NULL;

	// Close the file and exit.
	fclose(file);
	file = NULL;
	return 0;
}

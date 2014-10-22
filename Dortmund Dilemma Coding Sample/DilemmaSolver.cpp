/*
 * DilemmaSolver.cpp
 *
 *  Created on: Sep 9, 2014
 *      Author: Gary Gregg
 */

#include "DilemmaSolver.h"
#include "CombinationException.h"
#include "CombinationMaker.h"
#include "DilemmaSolverException.h"
#include "DilemmaType.h"
#include "IndexType.h"

/**
 * Calculates qualifying prefix/suffix words for word lengths from zero to a
 * predefined maximum using a given number of symbols.
 *
 * @param symbolIndex The given number of symbols.
 */
void DilemmaSolver::calculateQualifyingWords(IndexType symbolIndex) {

	/*
	 * The symbol count is the given symbol index plus one. Declare and
	 * initialize variables to received the total number of words for a word
	 * length consisting of 'word count' symbols, and a pointer to hold the
	 * address of the current word length structure being populated.
	 */
	const IndexType symbolCount = symbolIndex + 1;
	DilemmaType totalWords = 1;
	WordLength* current = 0;

	/*
	 * Declare and initialize a word index. Cycle for the first two elements of
	 * the word counts array for the given number of symbols.
	 */
	IndexType wordIndex = 0;
	for (wordIndex = 0; wordIndex < 2; ++wordIndex) {

		/*
		 * Get the address of the first/next word length object. Set the number
		 * of words without the prefix/suffix property, and the total number of
		 * words.
		 */
		current = &wordCounts[wordIndex][symbolIndex];
		current->setWithoutProperty(totalWords);
		current->setTotal(totalWords);

		/*
		 * Recalculate the total number of words for the next iteration, and
		 * set the number of words with the prefix/suffix property.
		 */
		totalWords *= symbolCount;
		current->setWithProperty(0);
	}

	/*
	 * Declare and initialize a variable to hold a temporary value for a word
	 * count. Get the maximum word length, and cycle for the remainder of the
	 * word lengths, continuing the first two iterations, above.
	 */
	DilemmaType wordCount = 0;
	const IndexType wordLength = getMaximumWordLength();
	for (; wordIndex <= wordLength; ++wordIndex) {

		/*
		 * Get the address of the first/next word length object. Initialize
		 * the word count for both even and odd word lengths to be the count
		 * of words without the prefix/suffix property having one less length,
		 * times the symbol count.
		 */
		current = &wordCounts[wordIndex][symbolIndex];
		wordCount = wordCounts[wordIndex - 1][symbolIndex].getWithoutProperty()
				* symbolCount;

		/*
		 * Subtract words of half the word length not having the
		 * prefix/suffix property. This accounts for words that would now
		 * have the prefix/suffix property for the current word length under
		 * consideration.
		 */
		if (0 == wordIndex % 2) {
			wordCount -=
					wordCounts[wordIndex / 2][symbolIndex].getWithoutProperty();
		}

		/*
		 * Set the number of words without the prefix/suffix property, and
		 * the total number of words.
		 */
		current->setWithoutProperty(wordCount);
		current->setTotal(totalWords);

		/*
		 * Recalculate the total number of words for the next iteration, and
		 * set the number of words with the prefix/suffix property.
		 */
		totalWords *= symbolCount;
		current->setWithProperty(
				current->getTotal() - current->getWithoutProperty());
	}
}

/**
 * Calculates qualifying prefix/suffix words for word lengths from zero to a
 * predefined maximum using all counts of symbols from one to a predefined
 * maximum.
 */
void DilemmaSolver::calculateQualifyingWords() {

	/*
	 * Only perform the calculation if the calculation has not already been
	 * performed.
	 */
	if (!areQualifyingWordCalculated()) {

		// Get the symbol count, and cycle for each symbol.
		const IndexType symbolCount = getAlphabetSize();
		for (IndexType symbolIndex = 0; symbolIndex < symbolCount;
				++symbolIndex) {

			// Calculate the qualifying words for the current symbol index.
			calculateQualifyingWords(symbolIndex);
		}

		// Set the qualifying words calculation as performed.
		setQualifyingWordsCalculated();
	}
}

/**
 * Gets a solution for the Dortmund Dilemma.
 *
 * @param wordLength The length of the word being considered
 * @param symbolCount The number of symbols being considered
 * @throws CombinationException If an internal error resulted in an illegal call
 * to the combination maker
 * @throws DilemmaSolverException If the internal state of the solver is not
 * ready, or if the parameters are not valid
 * @return A solution for the Dortmund Dilemma
 */
IndexType DilemmaSolver::getSolution(IndexType wordLength,
		IndexType symbolCount) const throw (CombinationException,
				DilemmaSolverException) {

	// Throw an exception if the qualifying words have not yet been calculated.
	if (!areQualifyingWordCalculated()) {
		throw DilemmaSolverException(
				"Qualifying words have not yet been calculated");
	}

	// Throw an exception if the word length is not within range.
	if ((wordLength < 0) || (getMaximumWordLength() < wordLength)) {
		throw DilemmaSolverException("Given word length is invalid");
	}

	// Throw an exception if the symbol count is not within range.
	if ((symbolCount < 1) || (getAlphabetSize() < symbolCount)) {
		throw DilemmaSolverException("Given symbol count is invalid");
	}

	/*
	 * Declare and initialize a value to alternate between adding a word count
	 * to an accumulator that makes a word count larger, and another that
	 * makes it smaller. This is part of the step to insure that the returned
	 * count uses ALL of the required symbols. Declare and initialize a
	 * variable for temporary calculations.
	 */
	bool addToMakesLarger = false;
	DilemmaType adjuster = 0;

	/*
	 * Declare and initialize accumulators for making a word count larger, and
	 * another for making it smaller. Decrement the current symbol index, and
	 * cycle while the symbol index is greater than zero.
	 */
	DilemmaType makesLarger = 0;
	DilemmaType makesSmaller = 0;
	for (IndexType symbolIndex = symbolCount - 1; 0 < symbolIndex;) {

		/*
		 * Initialize the adjuster to the number of combinations of the current
		 * symbol index taken from the symbol count. Decrement the symbol index
		 * to make it zero-based, and multiply to the temporary value the word
		 * count of the words of the given word length that use one less symbol.
		 * Should the result be used to make the word count larger?
		 */
		adjuster = getCombinations(symbolCount, symbolIndex--);
		adjuster *= wordCounts[wordLength][symbolIndex].getWithProperty();
		if (addToMakesLarger) {

			/*
			 * The result should be used to make the word count larger. Add the
			 * temporary value to the 'makes larger' accumulator.
			 */
			makesLarger += adjuster;
		}

		/*
		 * ...otherwise the result should be used to make the word count smaller.
		 * Add the temporary value to the 'makes smaller' accumulator. Note:
		 * At the conclusion of this loop, the 'makes smaller' accumulator will
		 * always have a larger value than the 'makes larger' accumulator.
		 */
		else {
			makesSmaller += adjuster;
		}

		// Invert the 'add to make larger' flag.
		addToMakesLarger = !addToMakesLarger;
	}

	/*
	 * Get the count of words with the prefix/suffix property of the given word
	 * length. Subtract the 'makes larger' accumulator from the 'makes smaller'
	 * accumulator, then subtract that whole result from the word counts value.
	 * Multiply that by the number of combinations of the given symbol count
	 * taken from the possible number of letters in the alphabet.
	 */
	return (wordCounts[wordLength][symbolCount - 1].getWithProperty()
			- (makesSmaller - makesLarger))
			* getCombinations(getAlphabetSize(), symbolCount);
}

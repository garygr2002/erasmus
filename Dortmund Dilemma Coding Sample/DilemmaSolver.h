/*
 * DilemmaSolver.h
 *
 *  Created on: Sep 9, 2014
 *      Author: Gary Gregg
 */

#ifndef DILEMMASOLVER_H_
#define DILEMMASOLVER_H_

#include "CombinationException.h"
#include "DilemmaSolverException.h"
#include "DilemmaType.h"
#include "IndexType.h"
#include "WordLength.h"

/**
 * Contains a class to solve the Dortmund Dilemma.
 */
class DilemmaSolver {

public:

	/**
	 * Constructs the dilemma solver.
	 */
	DilemmaSolver() :
			qualifyingWordCalculated(false) {
	}

	/**
	 * Determines if the qualifying words have already been calculated.
	 *
	 * @return True if the qualifying words have already been calculated, false
	 * otherwise
	 */
	bool areQualifyingWordCalculated() const {
		return qualifyingWordCalculated;
	}

	/**
	 * Calculates qualifying prefix/suffix words for word lengths from zero to a
	 * predefined maximum using all counts of symbols from one to a predefined
	 * maximum.
	 */
	void calculateQualifyingWords();

	/**
	 * Gets the size of the alphabet.
	 *
	 * @return The size of the alphabet
	 */
	IndexType getAlphabetSize() const {
		return sizeof(wordCounts[0]) / sizeof(wordCounts[0][0]);
	}

	/**
	 * Gets the maximum word length.
	 *
	 * @return The maximum word length
	 */
	IndexType getMaximumWordLength() const {
		return sizeof(wordCounts) / sizeof(wordCounts[0]) - 1;
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
	IndexType getSolution(IndexType wordLength, IndexType symbolCount) const
			throw (CombinationException, DilemmaSolverException);

private:

	/**
	 * Calculates qualifying prefix/suffix words for word lengths from zero to a
	 * predefined maximum using a given number of symbols.
	 *
	 * @param symbolIndex The given number of symbols.
	 */
	void calculateQualifyingWords(IndexType symbolIndex);

	/**
	 * Sets the state of the dilemma solver to 'qualifying words calculated.'
	 */
	void setQualifyingWordsCalculated() {
		qualifyingWordCalculated = true;
	}

	// True if the qualifying words have already been calculated, false otherwise
	bool qualifyingWordCalculated;

	/*
	 * The matrix of word counts, indexed first my alphabet size, second by word
	 * length
	 */
	WordLength wordCounts[100001][26];
};

#endif /* DILEMMASOLVER_H_ */

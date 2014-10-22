/*
 * WordLength.h
 *
 *  Created on: Sep 4, 2014
 *      Author: Gary Gregg
 */

#ifndef WORDLENGTH_H_
#define WORDLENGTH_H_

#include "DilemmaType.h"

/**
 * Contains a class that contains counts of words that: 1) The total number
 * number of words for a given number of symbols; 2) The number of words for a
 * given number of symbols that have the prefix/suffix property, and 3) The
 * number of words for a given number of symbols that DO NOT have the
 * prefix/suffix property.
 */
class WordLength {

public:

	/**
	 * Constructs the word length.
	 */
	WordLength() :
			total(0), withProperty(0), withoutProperty(0) {
	}

	/**
	 * Gets the total number of words for a given number of symbols.
	 *
	 * @return The total number of words for a given number of symbols
	 */
	DilemmaType getTotal() const {
		return total;
	}

	/**
	 * Gets the number of words for a given number of symbols that have the
	 * prefix/suffix property.
	 *
	 * @return The number of words for a given number of symbols that have the
	 * prefix/suffix property
	 */
	DilemmaType getWithProperty() const {
		return withProperty;
	}

	/*
	 * Gets the number of words for a given number of symbols that DO NOT have the
	 * prefix/suffix property.
	 *
	 * @return The number of words for a given number of symbols that DO NOT have
	 * the prefix/suffix property
	 */
	DilemmaType getWithoutProperty() const {
		return withoutProperty;
	}

	/**
	 * Sets the total number of words for a given number of symbols.
	 *
	 * @param value The total number of words for a given number of symbols
	 */
	void setTotal(const DilemmaType& value) {
		total = value;
	}

	/**
	 * Sets the number of words for a given number of symbols that have the
	 * prefix/suffix property.
	 *
	 * @param value The number of words for a given number of symbols that have the
	 * prefix/suffix property
	 */
	void setWithProperty(const DilemmaType& value) {
		withProperty = value;
	}

	/**
	 * Sets the number of words for a given number of symbols that DO NOT have the
	 * prefix/suffix property.
	 *
	 * @param value The number of words for a given number of symbols that DO NOT
	 * have the prefix/suffix property
	 */
	void setWithoutProperty(const DilemmaType& value) {
		withoutProperty = value;
	}

private:

	// The total number of words for a given number of symbols
	DilemmaType total;

	/*
	 * The number of words for a given number of symbols that have the prefix/suffix
	 * property
	 */
	DilemmaType withProperty;

	/*
	 * The number of words for a given number of symbols that DO NOT have the
	 * prefix/suffix property
	 */
	DilemmaType withoutProperty;
};

#endif /* WORDLENGTH_H_ */

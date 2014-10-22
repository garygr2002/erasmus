/*
 * ModulusNumber.h
 *
 *  Created on: Sep 4, 2014
 *      Author: Gary Gregg
 */

#ifndef MODULUSNUMBER_H_
#define MODULUSNUMBER_H_

#include "IndexType.h"

/**
 * Contains a class to conduct arithmetic within the bounds of a given modulus.
 */
class ModulusNumber {

public:

	/**
	 * Constructs the modulus number.
	 *
	 * @param number An integral value with which to initialize the modulus
	 * number
	 */
	ModulusNumber() :
			containedNumber(0) {
	}

	/**
	 * Constructs the modulus number.
	 *
	 * @param number An integral value with which to initialize the modulus number
	 */
	ModulusNumber(IndexType number);

	/**
	 * Assigns an integral value to the modulus number.
	 *
	 * @param number An integral value
	 * @return A reference to this modulus number
	 */
	ModulusNumber& operator=(IndexType number);

	/**
	 * Adds an integral value to this modulus number.
	 *
	 * @param number An integral value
	 * @return A reference to this modulus number
	 */
	ModulusNumber& operator+=(IndexType number);

	/**
	 * Adds a modulus number value to this modulus number.
	 *
	 * @param modulusNumber A modulus number
	 * @return A reference to this modulus number
	 */
	ModulusNumber& operator+=(const ModulusNumber& modulusNumber);

	/**
	 * Subtracts an integral value from this modulus number.
	 *
	 * @param number An integral value
	 * @return A reference to this modulus number
	 */
	ModulusNumber& operator-=(IndexType number);

	/**
	 * Subtracts a modulus number from this modulus number.
	 *
	 * @param modulusNumber A modulus number
	 * @return A reference to this modulus number
	 */
	ModulusNumber& operator-=(const ModulusNumber& modulusNumber);

	/**
	 * Multiplies an integral value to this modulus number.
	 *
	 * @param number An integral value
	 * @return A reference to this modulus number
	 */
	ModulusNumber& operator*=(IndexType number);

	/**
	 * Multiplies a modulus number to this modulus number.
	 *
	 * @param modulusNumber A modulus number
	 * @return A reference to this modulus number
	 */
	ModulusNumber& operator*=(const ModulusNumber& modulusNumber);

	/**
	 * Increments this modulus number.
	 *
	 * @return A reference to this modulus number
	 */
	ModulusNumber& operator++();

	/**
	 * Performs a cast of a modulus number to an integer.
	 *
	 * @return An integral cast of a modulus number
	 */
	operator int() const {
		return containedNumber;
	}

private:

	/**
	 * Returns the modulus used in internal calculations.
	 *
	 * @return The modulus used in internal calculations
	 */
	static IndexType getModulus() {
		return modulus;
	}

	// The modulus to be used in internal calculations
	static const IndexType modulus;

	// The integral value contained in this object
	IndexType containedNumber;
};

/**
 * Adds a modulus number and an integral value.
 *
 * @param modulusNumber A modulus number
 * @param number An integral value
 * @return A new modulus number
 */
ModulusNumber operator+(const ModulusNumber& modulusNumber, IndexType number);

/**
 * Adds an integral value and a modulus number.
 *
 * @param number An integral value
 * @param modulusNumber A modulus number
 * @return A new modulus number
 */
ModulusNumber operator+(IndexType number, const ModulusNumber& modulusNumber);

/**
 * Adds two modulus numbers.
 *
 * @param first The first modulus number
 * @param second The second modulus number
 * @return A new modulus number
 */
ModulusNumber operator+(const ModulusNumber& first,
		const ModulusNumber& second);

/**
 * Subtracts an integral value from a modulus number.
 *
 * @param modulusNumber A modulus number
 * @param number An integral value
 * @return A new modulus number
 */
ModulusNumber operator-(const ModulusNumber& modulusNumber, IndexType number);

/**
 * Subtracts a modulus number from an integral value.
 *
 * @param number An integral value
 * @param modulusNumber A modulus number
 * @return A new modulus number
 */
ModulusNumber operator-(IndexType number, const ModulusNumber& modulusNumber);

/**
 * Subtracts a modulus number from another modulus number.
 *
 * @param first The minuend
 * @param second The subtrahend
 * @return A new modulus number
 */
ModulusNumber operator-(const ModulusNumber& first, const ModulusNumber& second);

/**
 * Multiplies a modulus number and an integral value.
 *
 * @param modulusNumber A modulus number
 * @param number An integral value
 * @return A new modulus number
 */
ModulusNumber operator*(const ModulusNumber& modulusNumber, IndexType number);

/**
 * Multiplies an integral number and a modulus number.
 *
 * @param number An integeral value
 * @param modulusNumber A modulus number
 * @return A new modulus number
 */
ModulusNumber operator*(IndexType number, const ModulusNumber& modulusNumber);

/**
 * Multiplies two modulus numbers.
 *
 * @param first The first modulus number
 * @param second The second modulus number
 * @return A new modulus number
 */
ModulusNumber operator*(const ModulusNumber& first,
		const ModulusNumber& second);

/**
 * Determines equivalence of two modulus numbers.
 *
 * @param first The first modulus number
 * @param second The second modulus number
 * @return True if the parameters are equal, false otherwise
 */
bool operator==(const ModulusNumber first, const ModulusNumber second);

/**
 * Determines lack of equivalence of two modulus numbers.
 *
 * @param first The first modulus number
 * @param second The second modulus number
 * @return True if the parameters are not equal, false otherwise
 */
bool operator!=(const ModulusNumber first, const ModulusNumber second);

#endif /* MODULUSNUMBER_H_ */

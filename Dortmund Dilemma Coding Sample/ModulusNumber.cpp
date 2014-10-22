/*
 * ModulusNumber.cpp
 *
 *  Created on: Sep 4, 2014
 *      Author: Gary Gregg
 */

#include <stdint.h>

#include "ModulusNumber.h"
#include "IndexType.h"

// The modulus to be used in internal calculations
const IndexType ModulusNumber::modulus = 10 * 10 * 10 * 10 * 10 * 10 * 10 * 10
		* 10 + 9;

/**
 * Constructs the modulus number.
 *
 * @param number An integral value with which to initialize the modulus number
 */
ModulusNumber::ModulusNumber(IndexType number) :
		containedNumber(0) {

	operator=(number);
}

/**
 * Assigns an integral value to the modulus number.
 *
 * @param number An integral value
 * @return A reference to this modulus number
 */
ModulusNumber& ModulusNumber::operator=(const IndexType number) {

	int64_t i = number;
	containedNumber = i % getModulus();
	return *this;
}

/**
 * Adds an integral value to this modulus number.
 *
 * @param number An integral value
 * @return A reference to this modulus number
 */
ModulusNumber& ModulusNumber::operator+=(const IndexType number) {

	int64_t i = containedNumber;
	i += number;

	containedNumber = i % getModulus();
	return *this;
}

/**
 * Adds a modulus number value to this modulus number.
 *
 * @param modulusNumber A modulus number
 * @return A reference to this modulus number
 */
ModulusNumber& ModulusNumber::operator+=(const ModulusNumber& modulusNumber) {

	const IndexType i = modulusNumber;
	operator+=(i);
	return *this;
}

/**
 * Subtracts an integral value from this modulus number.
 *
 * @param number An integral value
 * @return A reference to this modulus number
 */
ModulusNumber& ModulusNumber::operator-=(IndexType number) {

	// Make sure the given number is within the modulus.
	const IndexType modulus = getModulus();
	number %= modulus;

	/*
	 * Add the modulus to the contained number if subtraction of
	 * the given value will produce a negative number.
	 */
	int64_t i = containedNumber;
	if (i < number) {

		// Add the modulus to the contained number.
		i += modulus;
	}

	// Subract the give value from the contained number.
	containedNumber = i - number;
	return *this;
}

/**
 * Subtracts a modulus number from this modulus number.
 *
 * @param modulusNumber A modulus number
 * @return A reference to this modulus number
 */
ModulusNumber& ModulusNumber::operator-=(const ModulusNumber& modulusNumber) {

	const IndexType i = modulusNumber;
	operator-=(i);
	return *this;
}

/**
 * Multiplies an integral value to this modulus number.
 *
 * @param number An integral value
 * @return A reference to this modulus number
 */
ModulusNumber& ModulusNumber::operator*=(const IndexType number) {

	int64_t i = containedNumber;
	i *= number;

	containedNumber = i % getModulus();
	return *this;
}

/**
 * Multiplies a modulus number to this modulus number.
 *
 * @param modulusNumber A modulus number
 * @return A reference to this modulus number
 */
ModulusNumber& ModulusNumber::operator*=(const ModulusNumber& modulusNumber) {

	const IndexType i = modulusNumber;
	operator*=(i);
	return *this;
}

/**
 * Increments this modulus number.
 *
 * @return A reference to this modulus number
 */
ModulusNumber& ModulusNumber::operator++() {

	operator+=(1);
	return *this;
}

/**
 * Adds a modulus number and an integral value.
 *
 * @param modulusNumber A modulus number
 * @param number An integral value
 * @return A new modulus number
 */
ModulusNumber operator+(const ModulusNumber& modulusNumber, const IndexType number) {

	ModulusNumber newNumber = modulusNumber;
	return newNumber += number;
}

/**
 * Adds an integral value and a modulus number.
 *
 * @param number An integral value
 * @param modulusNumber A modulus number
 * @return A new modulus number
 */
ModulusNumber operator+(const IndexType number, const ModulusNumber& modulusNumber) {

	ModulusNumber newNumber = modulusNumber;
	return newNumber += number;
}

/**
 * Adds two modulus numbers.
 *
 * @param first The first modulus number
 * @param second The second modulus number
 * @return A new modulus number
 */
ModulusNumber operator+(const ModulusNumber& first,
		const ModulusNumber& second) {

	ModulusNumber newNumber = first;
	return newNumber += second;
}

/**
 * Subtracts an integral value from a modulus number.
 *
 * @param modulusNumber A modulus number
 * @param number An integral value
 * @return A new modulus number
 */
ModulusNumber operator-(const ModulusNumber& modulusNumber, const IndexType number) {

	ModulusNumber newNumber = modulusNumber;
	return newNumber -= number;
}

/**
 * Subtracts a modulus number from an integral value.
 *
 * @param number An integral value
 * @param modulusNumber A modulus number
 * @return A new modulus number
 */
ModulusNumber operator-(const IndexType number, const ModulusNumber& modulusNumber) {

	ModulusNumber newNumber = modulusNumber;
	return newNumber -= number;
}

/**
 * Subtracts a modulus number from another modulus number.
 *
 * @param first The minuend
 * @param second The subtrahend
 * @return A new modulus number
 */
ModulusNumber operator-(const ModulusNumber& first, const ModulusNumber& second) {

	ModulusNumber newNumber = first;
	return newNumber -= second;
}

/**
 * Multiplies a modulus number and an integral value.
 *
 * @param modulusNumber A modulus number
 * @param number An integral value
 * @return A new modulus number
 */
ModulusNumber operator*(const ModulusNumber& modulusNumber, const IndexType number) {

	ModulusNumber newNumber = modulusNumber;
	return newNumber *= number;
}

/**
 * Multiplies an integral number and a modulus number.
 *
 * @param number An integeral value
 * @param modulusNumber A modulus number
 * @return A new modulus number
 */
ModulusNumber operator*(const IndexType number, const ModulusNumber& modulusNumber) {

	ModulusNumber newNumber = modulusNumber;
	return newNumber *= number;
}

/**
 * Multiplies two modulus numbers.
 *
 * @param first The first modulus number
 * @param second The second modulus number
 * @return A new modulus number
 */
ModulusNumber operator*(const ModulusNumber& first,
		const ModulusNumber& second) {

	ModulusNumber newNumber = first;
	return newNumber *= second;
}

/**
 * Determines equivalence of two modulus numbers.
 *
 * @param first The first modulus number
 * @param second The second modulus number
 * @return True if the parameters are equal, false otherwise
 */
bool operator==(const ModulusNumber first, const ModulusNumber second) {

	IndexType i = first;
	IndexType j = second;
	return i == j;
}

/**
 * Determines lack of equivalence of two modulus numbers.
 *
 * @param first The first modulus number
 * @param second The second modulus number
 * @return True if the parameters are not equal, false otherwise
 */
bool operator!=(const ModulusNumber first, const ModulusNumber second) {
	return !(first == second);
}

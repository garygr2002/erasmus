/*
 * DilemmaSolverException.h
 *
 *  Created on: Sep 9, 2014
 *      Author: Gary Gregg
 */

#ifndef DILEMMASOLVEREXCEPTION_H_
#define DILEMMASOLVEREXCEPTION_H_

#include <stdexcept>

using namespace std;

/**
 * Contains a class to be used for exceptions thrown from the class
 * DilemmaSolver.
 */
class DilemmaSolverException: public exception {

public:

	/**
	 * Constructs the exception.
	 *
	 * @param message The 'what' message to be used for the this standard
	 * exception
	 */
	DilemmaSolverException(const char* message = 0) :
			message(message) {
	}

	/**
	 * Returns a message describing the reason for the exception.
	 *
	 * @return The 'what' message describing the cause of the exception
	 */
	virtual const char* what() const throw () {
		return message;
	}

private:

	// The cause of the exception
	const char* message;
};

#endif /* DILEMMASOLVEREXCEPTION_H_ */

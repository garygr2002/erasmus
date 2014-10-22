/*
 * CombinationException.h
 *
 *  Created on: Sep 6, 2014
 *      Author: Gary Gregg
 */

#ifndef COMBINATIONEXCEPTION_H_
#define COMBINATIONEXCEPTION_H_

#include <stdexcept>

using namespace std;

/**
 * Contains a class to be used for exceptions thrown from the class
 * CombinationMaker.
 */
class CombinationException: public exception {

public:

	/**
	 * Constructs the exception.
	 *
	 * @param message The 'what' message to be used for the this standard
	 * exception
	 */
	CombinationException(const char* message = 0) :
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

#endif /* COMBINATIONEXCEPTION_H_ */

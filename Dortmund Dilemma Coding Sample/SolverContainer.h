/*
 * SolverContainer.h
 *
 *  Created on: Sep 7, 2014
 *      Author: Gary Gregg
 */

#ifndef SOLVERCONTAINER_H_
#define SOLVERCONTAINER_H_

#include "DilemmaSolver.h"

/**
 * Contains a class that contains a Dilemma Solver. Used to allocate a Dilemma
 * Solver on the heap in a leak-safe way.
 */
class SolverContainer {

public:

	/**
	 * Constructs the container.
	 */
	SolverContainer() :
			solver(0) {
		solver = new DilemmaSolver();
	}

	/**
	 * Destroys the container.
	 */
	~SolverContainer() {
		delete solver;
	}

	/**
	 * Gets the contained dilemma solver.
	 *
	 * @return The contained dilemma solver
	 */
	DilemmaSolver* getSolver() {
		return solver;
	}

private:

	// The contained dilemma solver
	DilemmaSolver* solver;
};

#endif /* SOLVERCONTAINER_H_ */

/*
 * MainAndTests.cpp
 *
 *  Created on: Sep 4, 2014
 *      Author: Gary Gregg
 */

#include <stdio.h>

#include "CombinationException.h"
#include "DilemmaSolver.h"
#include "DilemmaSolverException.h"
#include "IndexType.h"
#include "SolverContainer.h"

/**
 * Performs a single test of the DilemmaSolver.
 *
 * @param solver The dilemma solver to be used for the test
 * @param wordLength The length of the word being considered
 * @param symbolCount The number of symbols being considered
 * @param expectedResult The expected result of the calculation
 * @throws CombinationException If an internal error resulted in an illegal call
 * to the combination maker
 * @throws DilemmaSolverException If the internal state of the solver is not
 * ready, or if the parameters are not valid
 */
void performTest(DilemmaSolver* solver, IndexType n, IndexType k,
		IndexType expectedResult) throw (CombinationException,
				DilemmaSolverException) {

	const IndexType actualResult = solver->getSolution(n, k);
	printf(
			"Case for 'n' = %d and 'k' = %d; expected result: %d; actual result: %d - %s.\n",
			n, k, expectedResult, actualResult,
			((expectedResult == actualResult) ? "Pass" : "Fail"));
}

/**
 * Performs a series of tests on a dilemma solver.
 *
 * @param solver The dilemma solver to be used for the tests
 * @throws CombinationException If an internal error resulted in an illegal call
 * to the combination maker
 * @throws DilemmaSolverException If the internal state of the solver is not
 * ready, or if the parameters are not valid
 */
void performTests(DilemmaSolver* solver) throw (CombinationException,
		DilemmaSolverException) {

	// Perform the tests.
	performTest(solver, 1, 1, 0);
	performTest(solver, 1, 3, 0);
	performTest(solver, 2, 1, 26);
	performTest(solver, 2, 2, 0);
	performTest(solver, 3, 2, 650);
	performTest(solver, 4, 2, 2600);
	performTest(solver, 5, 1, 26);
	performTest(solver, 6, 2, 13650);
	performTest(solver, 1000, 2, 325941308);

	performTest(solver, 2, 10, 0);
	performTest(solver, 7, 1, 26);
	performTest(solver, 7, 5, 126297600);
	performTest(solver, 8, 6, 646843173);
	performTest(solver, 4, 3, 15600);
	performTest(solver, 100000, 2, 164406250);
	performTest(solver, 100000, 13, 805670708);
	performTest(solver, 100000, 26, 805737283);
}

/**
 * Performs a series of Hacker Rank tests.
 *
 * @param solver The dilemma solver to be used for the tests
 * @throws CombinationException If an internal error resulted in an illegal call
 * to the combination maker
 * @throws DilemmaSolverException If the internal state of the solver is not
 * ready, or if the parameters are not valid
 */
void solveHackerRank(DilemmaSolver* solver) throw (CombinationException,
		DilemmaSolverException) {

	// Declare and initialize variables to receive 'n' and 'k'.
	IndexType n = 0;
	IndexType k = 0;

	/*
	 * Declare and initialize a variable to receive 't'. Read 't', and cycle
	 * that many times.
	 */
	IndexType t = 0;
	scanf("%d", &t);
	for (IndexType i = 0; i < t; ++i) {

		/*
		 * Read 'n' and 'k' for the first/next iteration, solve the problem, and
		 * print the solution.
		 */
		scanf("%d %d", &n, &k);
		printf("%d\n", solver->getSolution(n, k));
	}
}

/**
 * Drives use of a Dortmund Dilemma solver.
 *
 * @param argc The count of command line arguments (unused)
 * @param argv The command line arguments (unused)
 * @return Zero of the routine runs successful, non-zero otherwise
 */
int main(int argc, char* argv[]) {

	// Declare a solver container, and get its contained dilemma solver.
	SolverContainer container = SolverContainer();
	DilemmaSolver* solver = container.getSolver();
	try {

		/*
		 * Calculate the qualifying words, and perform some canned tests or
		 * perform the Hacker Rank tests.
		 */
		solver->calculateQualifyingWords();
		performTests(solver);
		// solveHackerRank(solver);
	}

	// Catch and describe any CombinationException that may occur.
	catch (const CombinationException& exception) {
		fprintf(stderr, "Combination exception was thrown with message: %s.",
				exception.what());
	}

	// Catch and describe any DilemmaSolverException that may occur.
	catch (const DilemmaSolverException& exception) {
		fprintf(stderr, "Dilemma solver exception was thrown with message: %s.",
				exception.what());
	}

	// Return zero; this main method always succeeds.
	return 0;
}

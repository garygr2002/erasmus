package com.garygregg.coverity.codingtest.operation;

/**
 * Encapsulates a binary operation
 * 
 * @author Gary Gregg
 */
public interface BinaryOperation {

	/**
	 * Performs the binary operation.
	 * 
	 * @param argument1
	 *            The first argument of the operation
	 * @param argument2
	 *            The second argument of the operation
	 * @return The result of the operation
	 */
	int performOperation(int argument1, int argument2);
}

package com.garygregg.coverity.codingtest.token;

/**
 * Encapsulates an operation token in the calculator.
 * 
 * @author Gary Gregg
 */
public class OperationToken extends KeyToken {

	/**
	 * Creates an operation token with an expression.
	 * 
	 * @param expression
	 *            The expression of the token
	 * @param position
	 *            The position of the token in the input stream
	 */
	OperationToken(String expression, int position) {
		super(expression, position);
	}
}

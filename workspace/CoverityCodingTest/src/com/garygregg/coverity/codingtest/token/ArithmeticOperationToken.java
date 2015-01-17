package com.garygregg.coverity.codingtest.token;

/**
 * Encapsulates an arithmetic operation token in the calculator.
 * 
 * @author Gary Gregg
 */
public class ArithmeticOperationToken extends OperationToken {

	/**
	 * Creates an arithmetic operation token with an expression.
	 * 
	 * @param expression
	 *            The expression of the token
	 * @param position
	 *            The position of the token in the input stream
	 */
	ArithmeticOperationToken(String expression, int position) {
		super(expression, position);
	}
};

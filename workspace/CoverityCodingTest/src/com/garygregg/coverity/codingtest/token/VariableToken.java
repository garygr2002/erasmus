package com.garygregg.coverity.codingtest.token;

/**
 * Contains a variable token in the calculator.
 * 
 * @author Gary Gregg
 */
public class VariableToken extends Token {

	/**
	 * Constructs a variable token.
	 * 
	 * @param expression
	 *            The expression of the token
	 * @param position
	 *            The position of the token in the input stream
	 */
	public VariableToken(String expression, int position) {
		super(expression, position);
	}
}

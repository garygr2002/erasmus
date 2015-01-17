package com.garygregg.coverity.codingtest.token;

/**
 * Contains an unknown token in the calculator.
 * 
 * @author Gary Gregg
 */
public class UnknownToken extends Token {

	/**
	 * Constructs an unknown token.
	 * 
	 * @param expression
	 *            The expression of the token
	 * @param position
	 *            The position of the token in the input stream
	 */
	public UnknownToken(String expression, int position) {
		super(expression, position);
	}
}

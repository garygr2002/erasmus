package com.garygregg.coverity.codingtest.token;

/**
 * Encapsulates a key token in the calculator. The token is either a keyword or
 * required syntactic punctuation.
 * 
 * @author Gary Gregg
 */
public abstract class KeyToken extends Token {

	/**
	 * Creates a key token with an expression.
	 * 
	 * @param expression
	 *            The expression of the token
	 * @param position
	 *            The position of the token in the input stream
	 */
	KeyToken(String expression, int position) {
		super(expression, position);
	}
}

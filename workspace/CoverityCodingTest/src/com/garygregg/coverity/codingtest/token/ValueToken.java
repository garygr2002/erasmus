package com.garygregg.coverity.codingtest.token;

/**
 * Contains a value token in the calculator.
 * 
 * @author Gary Gregg
 */
public class ValueToken extends Token {

	/**
	 * Constructs a synthesized value token outside of an input stream.
	 * 
	 * @param value
	 *            The value of the token
	 */
	public ValueToken(int value) {
		super(value, null);
	}

	/**
	 * Constructs a value token.
	 * 
	 * @param value
	 *            The value of the token
	 * @param position
	 *            The position of the token in the input stream
	 */
	public ValueToken(int value, int position) {
		super(value, position);
	}
}

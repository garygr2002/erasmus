package com.garygregg.coverity.codingtest.token;

/**
 * Contains an open ('(') token in the calculator.
 * 
 * @author Gary Gregg
 */
public class OpenToken extends KeyToken {

	// The common expression for all open tokens
	private static final String commonExpression = "(";

	/**
	 * Gets the common expression for all open tokens.
	 * 
	 * @return The common expression for all open tokens
	 */
	public static String getCommonExpression() {
		return commonExpression;
	}

	/**
	 * Creates an open ('(') token.
	 * 
	 * @param position
	 *            The position of the token in the input stream
	 */
	public OpenToken(int position) {
		super(getCommonExpression(), position);
	}
}

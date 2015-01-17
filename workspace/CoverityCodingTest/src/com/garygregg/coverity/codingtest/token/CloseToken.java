package com.garygregg.coverity.codingtest.token;

/**
 * Contains a close (')') token in the calculator.
 * 
 * @author Gary Gregg
 */
public class CloseToken extends KeyToken {

	// The common expression for all close tokens
	private static final String commonExpression = ")";

	/**
	 * Gets the common expression for all close tokens.
	 * 
	 * @return The common expression for all close tokens
	 */
	public static String getCommonExpression() {
		return commonExpression;
	}

	/**
	 * Creates a close (')') token.
	 * 
	 * @param position
	 *            The position of the token in the input stream
	 */
	public CloseToken(int position) {
		super(getCommonExpression(), position);
	}
}

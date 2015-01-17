package com.garygregg.coverity.codingtest.token;

/**
 * Contains a comma (',') token in the calculator.
 * 
 * @author Gary Gregg
 */
public class CommaToken extends KeyToken {

	// The common expression for all comma tokens
	private static final String commonExpression = ",";

	/**
	 * Gets the common expression for all comma tokens.
	 * 
	 * @return The common expression for all comma tokens
	 */
	public static String getCommonExpression() {
		return commonExpression;
	}

	/**
	 * Creates a comma (',') token.
	 * 
	 * @param position
	 *            The position of the token in the input stream
	 */
	public CommaToken(int position) {
		super(getCommonExpression(), position);
	}
}

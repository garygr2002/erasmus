package com.garygregg.coverity.codingtest.token;

/**
 * Contains a divide ('div') token in the calculator.
 * 
 * @author Gary Gregg
 */
public class DivideToken extends ArithmeticOperationToken {

	// The common expression for all divide tokens
	private static final String commonExpression = "div";

	/**
	 * Gets the common expression for all divide tokens.
	 * 
	 * @return The common expression for all divide tokens
	 */
	public static String getCommonExpression() {
		return commonExpression;
	}

	/**
	 * Creates a divide ('div') token.
	 * 
	 * @param position
	 *            The position of the token in the input stream
	 */
	public DivideToken(int position) {
		super(getCommonExpression(), position);
	}
}

package com.garygregg.coverity.codingtest.token;

/**
 * Contains a subtract ('sub') token in the calculator.
 * 
 * @author Gary Gregg
 */
public class SubtractToken extends ArithmeticOperationToken {

	// The common expression for all subtract tokens
	private static final String commonExpression = "sub";

	/**
	 * Gets the common expression for all subtract tokens.
	 * 
	 * @return The common expression for all subract tokens
	 */
	public static String getCommonExpression() {
		return commonExpression;
	}

	/**
	 * Creates a subtract ('sub') token.
	 * 
	 * @param position
	 *            The position of the token in the input stream
	 */
	public SubtractToken(int position) {
		super(getCommonExpression(), position);
	}
}

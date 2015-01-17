package com.garygregg.coverity.codingtest.token;

/**
 * Contains an add ('add') token in the calculator.
 * 
 * @author Gary Gregg
 */
public class AddToken extends ArithmeticOperationToken {

	// The common expression for all add tokens
	private static final String commonExpression = "add";

	/**
	 * Gets the common expression for all add tokens.
	 * 
	 * @return The common expression for all add tokens
	 */
	public static String getCommonExpression() {
		return commonExpression;
	}

	/**
	 * Creates an add ('add') token.
	 * 
	 * @param position
	 *            The position of the token in the input stream
	 */
	public AddToken(int position) {
		super(getCommonExpression(), position);
	}
}

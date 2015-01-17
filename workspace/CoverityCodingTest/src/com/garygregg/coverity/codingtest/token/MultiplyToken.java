package com.garygregg.coverity.codingtest.token;

/**
 * Contains a multiply ('mult') token in the calculator.
 * 
 * @author Gary Gregg
 */
public class MultiplyToken extends ArithmeticOperationToken {

	// The common expression for all multiply tokens
	private static final String commonExpression = "mult";

	/**
	 * Gets the common expression for all multiply tokens.
	 * 
	 * @return The common expression for all multiply tokens
	 */
	public static String getCommonExpression() {
		return commonExpression;
	}

	/**
	 * Creates a multiply ('mult') token.
	 * 
	 * @param position
	 *            The position of the token in the input stream
	 */
	public MultiplyToken(int position) {
		super(getCommonExpression(), position);
	}
}

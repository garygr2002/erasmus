package com.garygregg.coverity.codingtest.token;

/**
 * Contains a let ('let') token in the calculator.
 * 
 * @author Gary Gregg
 */
public class LetToken extends OperationToken {

	// The common expression for all let tokens
	private static final String commonExpression = "let";

	/**
	 * Gets the common expression for all let tokens.
	 * 
	 * @return The common expression for all let tokens
	 */
	public static String getCommonExpression() {
		return commonExpression;
	}

	/**
	 * Creates a let ('let') token.
	 * 
	 * @param position
	 *            The position of the token in the input stream
	 */
	public LetToken(int position) {
		super(getCommonExpression(), position);
	}
}

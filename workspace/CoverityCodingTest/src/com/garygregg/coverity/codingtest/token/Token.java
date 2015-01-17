package com.garygregg.coverity.codingtest.token;

/**
 * Encapsulates a token in the calculator.
 * 
 * @author Gary Gregg
 */
public class Token {

	/**
	 * Calculates an expression for a value.
	 * 
	 * @param value
	 *            The value for which to create an expression
	 * @return An expression for the given value
	 */
	private static String createExpressionForValue(int value) {
		return Integer.toString(value);
	}

	// The expression for the token, null if no expression
	private final String expression;

	/*
	 * The position of the token in the input stream, or null if the token was
	 * synthesized outside of the input stream
	 */
	private final Integer position;

	// The value of the token, null if no value
	private final Integer value;

	/**
	 * Creates a token with a value.
	 * 
	 * @param value
	 *            The value of the token
	 */
	Token(int value) {
		this(createExpressionForValue(value), value);
	}

	/**
	 * Creates a token with a value.
	 * 
	 * @param value
	 *            The value of the token
	 * @param position
	 *            The position of the token in the input stream, or null if the
	 *            token was synthesized outside of the input stream
	 */
	Token(int value, Integer position) {
		this(createExpressionForValue(value), value, position);
	}

	/**
	 * Creates a token with an expression.
	 * 
	 * @param expression
	 *            The expression of the token
	 */
	Token(String expression) {
		this(expression, null);
	}

	/**
	 * Creates a token with an expression.
	 * 
	 * @param expression
	 *            The expression of the token
	 * @param position
	 *            The position of the token in the input stream, or null if the
	 *            token was synthesized outside of the input stream
	 */
	Token(String expression, Integer position) {
		this(expression, null, position);
	}

	/**
	 * Creates a token.
	 * 
	 * @param expression
	 *            The expression of the token
	 * @param value
	 *            The value of the token
	 * @param position
	 *            The position of the token in the input stream, or null if the
	 *            token was synthesized outside of the input stream
	 */
	private Token(String expression, Integer value, Integer position) {

		// Set member variables.
		this.expression = expression;
		this.value = value;
		this.position = position;
	}

	/**
	 * Gets the expression of the token.
	 * 
	 * @return The expression of the token
	 */
	public String getExpression() {
		return expression;
	}

	/**
	 * Gets the position of the token in the input stream, if any
	 * 
	 * @return The position of the token in the input stream, or null if the
	 *         token was synthesized outside of the input stream
	 */
	public Integer getPosition() {
		return position;
	}

	/**
	 * Gets the value of the token.
	 * 
	 * @return The value of the token
	 */
	public Integer getValue() {
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "token of type: '" + getClass().getSimpleName()
				+ "' with expression '" + getExpression() + "' and value: '"
				+ getValue() + "'";
	}
}

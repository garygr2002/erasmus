package com.garygregg.coverity.codingtest;

import com.garygregg.coverity.codingtest.operation.BinaryOperation;
import com.garygregg.coverity.codingtest.token.Token;

/**
 * Contains a class with token metadata for the calculator stack.
 * 
 * @author Gary Gregg
 */
class TokenInformation {

	// The binary operation represented by the token, if any
	private final BinaryOperation operation;

	// The parse state at the time the token was created
	private final State state;

	// The token
	private final Token token;

	/**
	 * Constructs the token information with a state, an a default binary
	 * operation
	 * 
	 * @param token
	 *            The token
	 * @param state
	 *            The state at the time the token was created
	 */
	public TokenInformation(Token token, State state) {
		this(token, state, null);
	}

	/**
	 * Constructs the token information with a state and an explicit binary
	 * operation.
	 * 
	 * @param token
	 *            The token
	 * @param state
	 *            The parse state at the time the token was created
	 * @param operation
	 *            The binary operation represented by the token, if any
	 */
	public TokenInformation(Token token, State state, BinaryOperation operation) {

		// Set the token, state and operation.
		this.token = token;
		this.state = state;
		this.operation = operation;
	}

	/**
	 * Gets the binary operation represented by the token, if any.
	 * 
	 * @return The binary operation represented by the token, if any
	 */
	public BinaryOperation getOperation() {
		return operation;
	}

	/**
	 * Gets the parse state at the time the token was created
	 * 
	 * @return The parse state at the time the token was created
	 */
	public State getState() {
		return state;
	}

	/**
	 * Gets the token.
	 * 
	 * @return The token
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * Returns true if the token has a non-null operation, false otherwise
	 * 
	 * @return True if the token has a non-null operation, false otherwise
	 */
	public boolean hasOperation() {
		return null != getOperation();
	}

	/**
	 * Performs the operation of the token, if available, and returns the
	 * result.
	 * 
	 * @param argument1
	 *            The first argument to the operation
	 * @param argument2
	 *            The second argument to the operation
	 * @return Null if the token has no operation, or the result of the
	 *         operation if it does
	 */
	public Integer performOperation(int argument1, int argument2) {
		return hasOperation() ? operation
				.performOperation(argument1, argument2) : null;
	}
}

package com.garygregg.coverity.codingtest.lexical_analysis;

import com.garygregg.coverity.codingtest.token.ValueToken;

/**
 * Contains a value token dispatcher.
 * 
 * @author Gary Gregg
 */
class ValueTokenDispatcher implements TokenDispatcher<ValueToken> {

	// The value token to dispatch
	private ValueToken token;

	// The value for created value tokens
	private int value;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.garygregg.coverity.codingtest.lexical_analysis.TokenDispatcher#
	 * createToken(int)
	 */
	@Override
	public void createToken(int position) {
		token = new ValueToken(getValue(), position);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.garygregg.coverity.codingtest.lexical_analysis.TokenDispatcher#
	 * dispatchToken
	 * (com.garygregg.coverity.codingtest.lexical_analysis.AnalysisListener)
	 */
	@Override
	public void dispatchToken(AnalysisListener listener) {
		listener.receiveValueToken(token);
	}

	/**
	 * Gets the value for created value tokens.
	 * 
	 * @return The value for created value tokens
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Sets the value for created value tokens.
	 * 
	 * @param value
	 *            The value for created value tokens
	 */
	public void setValue(int value) {
		this.value = value;
	}
}

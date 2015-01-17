package com.garygregg.coverity.codingtest.lexical_analysis;

import com.garygregg.coverity.codingtest.token.VariableToken;

/**
 * Contains a variable token dispatcher.
 * 
 * @author Gary Gregg
 */
class VariableTokenDispatcher implements TokenDispatcher<VariableToken> {

	// The expression for created variable tokens
	private String expression;

	// The variable token to dispatch
	private VariableToken token;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.garygregg.coverity.codingtest.lexical_analysis.TokenDispatcher#
	 * createToken(int)
	 */
	@Override
	public void createToken(int position) {
		token = new VariableToken(getExpression(), position);
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
		listener.receiveVariableToken(token);
	}

	/**
	 * Gets the expression for created variable tokens.
	 * 
	 * @return The expression for created variable tokens
	 */
	public String getExpression() {
		return expression;
	}

	/**
	 * Sets the expression for created variable tokens.
	 * 
	 * @param expression
	 *            The expression for created variable tokens
	 */
	public void setExpression(String expression) {
		this.expression = expression;
	}
}

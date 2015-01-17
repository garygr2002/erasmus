package com.garygregg.coverity.codingtest.lexical_analysis;

import com.garygregg.coverity.codingtest.token.UnknownToken;

/**
 * Contains an unknown token dispatcher.
 * 
 * @author Gary Gregg
 */
class UnknownTokenDispatcher implements TokenDispatcher<UnknownToken> {

	// The expression for created unknown tokens
	private String expression;

	// The unknown token to dispatch
	private UnknownToken token;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.garygregg.coverity.codingtest.lexical_analysis.TokenDispatcher#
	 * createToken(int)
	 */
	@Override
	public void createToken(int position) {
		token = new UnknownToken(getExpression(), position);
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
		listener.receiveUnknownToken(token);
	}

	/**
	 * Gets the expression for created unknown tokens.
	 * 
	 * @return The expression for created unknown tokens
	 */
	public String getExpression() {
		return expression;
	}

	/**
	 * Sets the expression for created unknown tokens.
	 * 
	 * @param expression
	 *            The expression for created unknown tokens
	 */
	public void setExpression(String expression) {
		this.expression = expression;
	}
}

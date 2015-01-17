package com.garygregg.coverity.codingtest.lexical_analysis;

import com.garygregg.coverity.codingtest.token.Token;

/**
 * Contains a token dispatch operation.
 * 
 * @author Gary Gregg
 * 
 * @param <TokenDispatcherType>
 *            A token dispatcher type
 */
class TokenDispatchOperation<TokenDispatcherType extends TokenDispatcher<? extends Token>>
		implements Operation<AnalysisListener> {

	// A token dispatcher
	private TokenDispatcherType dispatcher;

	/**
	 * Gets the token dispatcher.
	 * 
	 * @return The token dispatcher
	 */
	public TokenDispatcherType getDispatcher() {
		return dispatcher;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.garygregg.coverity.codingtest.lexical_analysis.Operation#perform(
	 * java.lang.Object)
	 */
	@Override
	public void perform(AnalysisListener argument) {
		dispatcher.dispatchToken(argument);
	}

	/**
	 * Sets the token dispatcher.
	 * 
	 * @param dispatcher
	 *            The token dispatcher
	 */
	public void setDispatcher(TokenDispatcherType dispatcher) {
		this.dispatcher = dispatcher;
	}
}

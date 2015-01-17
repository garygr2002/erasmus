package com.garygregg.coverity.codingtest.lexical_analysis;

import com.garygregg.coverity.codingtest.token.Token;

/**
 * Encapsulates a token dispatcher.
 * 
 * @author Gary Gregg
 */
interface TokenDispatcher<KeyTokenType extends Token> {

	/**
	 * Creates a token with a given position.
	 * 
	 * @param position
	 *            The position for the token
	 */
	void createToken(int position);

	/**
	 * Dispatches a token.
	 * 
	 * @param listener
	 *            The listener to receive the token
	 */
	void dispatchToken(AnalysisListener listener);
}

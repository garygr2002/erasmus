package com.garygregg.coverity.codingtest.lexical_analysis;

import com.garygregg.coverity.codingtest.token.OpenToken;
import com.garygregg.coverity.codingtest.token.KeyTokenFactory;

/**
 * Contains an open token dispatcher.
 * 
 * @author Gary Gregg
 */
class OpenTokenDispatcher implements TokenDispatcher<OpenToken> {

	// The factory for this factory-and-dispatcher
	private final KeyTokenFactory<OpenToken> factory = new KeyTokenFactory<OpenToken>() {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.garygregg.coverity.codingtest.token.KeyTokenFactory#createToken
		 * (int)
		 */
		@Override
		public OpenToken createToken(int position) {
			return new OpenToken(position);
		}
	};

	// The open token to dispatch
	private OpenToken token;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.garygregg.coverity.codingtest.lexical_analysis.TokenDispatcher#
	 * createToken(int)
	 */
	@Override
	public void createToken(int position) {
		token = factory.createToken(position);
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
		listener.receiveOpenToken(token);
	}
}

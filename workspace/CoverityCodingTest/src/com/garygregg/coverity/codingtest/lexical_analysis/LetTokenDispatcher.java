package com.garygregg.coverity.codingtest.lexical_analysis;

import com.garygregg.coverity.codingtest.token.LetToken;
import com.garygregg.coverity.codingtest.token.KeyTokenFactory;

/**
 * Contains a let token dispatcher.
 * 
 * @author Gary Gregg
 */
class LetTokenDispatcher implements TokenDispatcher<LetToken> {

	// The factory for this factory-and-dispatcher
	private final KeyTokenFactory<LetToken> factory = new KeyTokenFactory<LetToken>() {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.garygregg.coverity.codingtest.token.KeyTokenFactory#createToken
		 * (int)
		 */
		@Override
		public LetToken createToken(int position) {
			return new LetToken(position);
		}
	};

	// The let token to dispatch
	private LetToken token;

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
		listener.receiveLetToken(token);
	}
}

package com.garygregg.coverity.codingtest.lexical_analysis;

import com.garygregg.coverity.codingtest.token.DivideToken;
import com.garygregg.coverity.codingtest.token.KeyTokenFactory;

/**
 * Contains a divide token dispatcher.
 * 
 * @author Gary Gregg
 */
class DivideTokenDispatcher implements TokenDispatcher<DivideToken> {

	// The factory for this factory-and-dispatcher
	private final KeyTokenFactory<DivideToken> factory = new KeyTokenFactory<DivideToken>() {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.garygregg.coverity.codingtest.token.KeyTokenFactory#createToken
		 * (int)
		 */
		@Override
		public DivideToken createToken(int position) {
			return new DivideToken(position);
		}
	};

	// The divide token to dispatch
	private DivideToken token;

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
		listener.receiveDivideToken(token);
	}
}

package com.garygregg.coverity.codingtest.lexical_analysis;

import com.garygregg.coverity.codingtest.token.CloseToken;
import com.garygregg.coverity.codingtest.token.KeyTokenFactory;

/**
 * Contains a close token dispatcher.
 * 
 * @author Gary Gregg
 */
class CloseTokenDispatcher implements TokenDispatcher<CloseToken> {

	// The factory for this factory-and-dispatcher
	private final KeyTokenFactory<CloseToken> factory = new KeyTokenFactory<CloseToken>() {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.garygregg.coverity.codingtest.token.KeyTokenFactory#createToken
		 * (int)
		 */
		@Override
		public CloseToken createToken(int position) {
			return new CloseToken(position);
		}
	};

	// The close token to dispatch
	private CloseToken token;

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
		listener.receiveCloseToken(token);
	}
}

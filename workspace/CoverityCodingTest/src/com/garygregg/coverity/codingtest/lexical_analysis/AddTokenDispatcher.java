package com.garygregg.coverity.codingtest.lexical_analysis;

import com.garygregg.coverity.codingtest.token.AddToken;
import com.garygregg.coverity.codingtest.token.KeyTokenFactory;

/**
 * Contains an add token dispatcher.
 * 
 * @author Gary Gregg
 */
class AddTokenDispatcher implements TokenDispatcher<AddToken> {

	// The factory for this factory-and-dispatcher
	private final KeyTokenFactory<AddToken> factory = new KeyTokenFactory<AddToken>() {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.garygregg.coverity.codingtest.token.KeyTokenFactory#createToken
		 * (int)
		 */
		@Override
		public AddToken createToken(int position) {
			return new AddToken(position);
		}
	};

	// The add token to dispatch
	private AddToken token;

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
		listener.receiveAddToken(token);
	}
}

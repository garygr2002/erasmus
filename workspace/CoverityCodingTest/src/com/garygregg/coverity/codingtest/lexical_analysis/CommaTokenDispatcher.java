package com.garygregg.coverity.codingtest.lexical_analysis;

import com.garygregg.coverity.codingtest.token.CommaToken;
import com.garygregg.coverity.codingtest.token.KeyTokenFactory;

/**
 * Contains a comma token dispatcher.
 * 
 * @author Gary Gregg
 */
class CommaTokenDispatcher implements TokenDispatcher<CommaToken> {

	// The factory for this factory-and-dispatcher
	private final KeyTokenFactory<CommaToken> factory = new KeyTokenFactory<CommaToken>() {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.garygregg.coverity.codingtest.token.KeyTokenFactory#createToken
		 * (int)
		 */
		@Override
		public CommaToken createToken(int position) {
			return new CommaToken(position);
		}
	};

	// The comma token to dispatch
	private CommaToken token;

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
		listener.receiveCommaToken(token);
	}
}

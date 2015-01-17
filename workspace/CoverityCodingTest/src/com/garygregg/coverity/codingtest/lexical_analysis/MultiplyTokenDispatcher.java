package com.garygregg.coverity.codingtest.lexical_analysis;

import com.garygregg.coverity.codingtest.token.MultiplyToken;
import com.garygregg.coverity.codingtest.token.KeyTokenFactory;

/**
 * Contains a multiply token dispatcher.
 * 
 * @author Gary Gregg
 */
class MultiplyTokenDispatcher implements TokenDispatcher<MultiplyToken> {

	// The factory for this factory-and-dispatcher
	private final KeyTokenFactory<MultiplyToken> factory = new KeyTokenFactory<MultiplyToken>() {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.garygregg.coverity.codingtest.token.KeyTokenFactory#createToken
		 * (int)
		 */
		@Override
		public MultiplyToken createToken(int position) {
			return new MultiplyToken(position);
		}
	};

	// The multiply token to dispatch
	private MultiplyToken token;

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
		listener.receiveMultiplyToken(token);
	}
}

package com.garygregg.coverity.codingtest.lexical_analysis;

import com.garygregg.coverity.codingtest.token.SubtractToken;
import com.garygregg.coverity.codingtest.token.KeyTokenFactory;

/**
 * Contains a subtract token dispatcher.
 * 
 * @author Gary Gregg
 */
class SubtractTokenDispatcher implements TokenDispatcher<SubtractToken> {

	// The factory for this factory-and-dispatcher
	private final KeyTokenFactory<SubtractToken> factory = new KeyTokenFactory<SubtractToken>() {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.garygregg.coverity.codingtest.token.KeyTokenFactory#createToken
		 * (int)
		 */
		@Override
		public SubtractToken createToken(int position) {
			return new SubtractToken(position);
		}
	};

	// The subtract token to dispatch
	private SubtractToken token;

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
		listener.receiveSubtractToken(token);
	}
}

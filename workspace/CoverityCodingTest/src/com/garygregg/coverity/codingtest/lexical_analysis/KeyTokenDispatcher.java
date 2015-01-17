package com.garygregg.coverity.codingtest.lexical_analysis;

import java.util.HashMap;
import java.util.Map;

import com.garygregg.coverity.codingtest.token.AddToken;
import com.garygregg.coverity.codingtest.token.CloseToken;
import com.garygregg.coverity.codingtest.token.CommaToken;
import com.garygregg.coverity.codingtest.token.DivideToken;
import com.garygregg.coverity.codingtest.token.KeyToken;
import com.garygregg.coverity.codingtest.token.LetToken;
import com.garygregg.coverity.codingtest.token.MultiplyToken;
import com.garygregg.coverity.codingtest.token.OpenToken;
import com.garygregg.coverity.codingtest.token.SubtractToken;

/**
 * Contains a key token dispatcher.
 * 
 * @author Gary Gregg
 */
class KeyTokenDispatcher implements ListenerCollection<AnalysisListener> {

	// A map of key token common expression to their respective dispatchers
	private final Map<String, TokenDispatcher<? extends KeyToken>> dispatcherMap = new HashMap<String, TokenDispatcher<? extends KeyToken>>();

	// A dispatcher for listeners
	private final ListenerDispatcher listenerDispatcher = new ListenerDispatcher();

	{

		// Associate the add token dispatcher with the add token expression.
		dispatcherMap.put(AddToken.getCommonExpression(),
				new AddTokenDispatcher());

		// Associate the close token dispatcher with the close token expression.
		dispatcherMap.put(CloseToken.getCommonExpression(),
				new CloseTokenDispatcher());

		// Associate the comma token dispatcher with the comma token expression.
		dispatcherMap.put(CommaToken.getCommonExpression(),
				new CommaTokenDispatcher());

		/*
		 * Associate the divide token dispatcher with the divide token
		 * expression.
		 */
		dispatcherMap.put(DivideToken.getCommonExpression(),
				new DivideTokenDispatcher());

		// Associate the let token dispatcher with the let token expression.
		dispatcherMap.put(LetToken.getCommonExpression(),
				new LetTokenDispatcher());

		/*
		 * Associate the multiply token dispatcher with the multiply token
		 * expression.
		 */
		dispatcherMap.put(MultiplyToken.getCommonExpression(),
				new MultiplyTokenDispatcher());

		// Associate the open token dispatcher with the open token expression.
		dispatcherMap.put(OpenToken.getCommonExpression(),
				new OpenTokenDispatcher());

		/*
		 * Associate the subtract token dispatcher with the subtract token
		 * expression.
		 */
		dispatcherMap.put(SubtractToken.getCommonExpression(),
				new SubtractTokenDispatcher());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.garygregg.coverity.codingtest.lexical_analysis.ListenerCollection
	 * #addListener(java.lang.Object)
	 */
	@Override
	public boolean addListener(AnalysisListener listener) {
		return listenerDispatcher.addListener(listener);
	}

	/**
	 * Dispatches a key token if the given string represents the expression of a
	 * known key token.
	 * 
	 * @param string
	 *            The string representing the expression of a known key token
	 * @param position
	 *            The position of the token in the input stream
	 * @return True if the given string represented the expression of a known
	 *         key token, and dispatch was successful to all listeners; false
	 *         otherwise
	 */
	public boolean dispatchToken(String string, int position) {

		// Attempt to find a dispatcher for the given string.
		final TokenDispatcher<? extends KeyToken> dispatcher = dispatcherMap
				.get(string);

		/*
		 * Dispatch a token to all the listeners only if a dispatcher exists for
		 * the given string, and one or more listeners exist.
		 */
		final boolean dispatcherExists = (null != dispatcher);
		if (dispatcherExists && listenerDispatcher.hasListeners()) {

			// Create a token, and dispatch it to all listeners.
			dispatcher.createToken(position);
			listenerDispatcher.dispatch(dispatcher);
		}

		// Return true if a dispatcher exists for the given string.
		return dispatcherExists;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.garygregg.coverity.codingtest.lexical_analysis.ListenerCollection
	 * #getListenerCount()
	 */
	@Override
	public int getListenerCount() {
		return listenerDispatcher.getListenerCount();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.garygregg.coverity.codingtest.lexical_analysis.ListenerCollection
	 * #hasListeners()
	 */
	@Override
	public boolean hasListeners() {
		return listenerDispatcher.hasListeners();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.garygregg.coverity.codingtest.lexical_analysis.ListenerCollection
	 * #removeListener(java.lang.Object)
	 */
	public boolean removeListener(AnalysisListener listener) {
		return listenerDispatcher.removeListener(listener);
	}
}

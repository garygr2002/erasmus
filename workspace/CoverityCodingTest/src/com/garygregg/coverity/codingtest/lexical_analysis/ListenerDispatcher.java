package com.garygregg.coverity.codingtest.lexical_analysis;

import java.util.HashSet;
import java.util.Set;

import com.garygregg.coverity.codingtest.token.Token;

/**
 * Contains a listener dispatcher.
 * 
 * @author Gary Gregg
 */
class ListenerDispatcher implements ListenerCollection<AnalysisListener> {

	// A set of analysis listeners
	private final Set<AnalysisListener> listeners = new HashSet<AnalysisListener>();

	// A start operation listener
	private final Operation<AnalysisListener> startOperation = new Operation<AnalysisListener>() {

		/**
		 * Starts analysis for the given listener.
		 * 
		 * @param argument
		 *            An analysis listener
		 */
		@Override
		public void perform(AnalysisListener argument) {
			argument.startAnalysis();
		}
	};

	// A stop operation listener
	private final Operation<AnalysisListener> stopOperation = new Operation<AnalysisListener>() {

		/**
		 * Stops analysis for the given listener.
		 * 
		 * @param argument
		 *            An analysis listener
		 */
		@Override
		public void perform(AnalysisListener argument) {
			argument.stopAnalysis();
		}
	};

	// A token dispatch operation
	private final TokenDispatchOperation<TokenDispatcher<? extends Token>> tokenDispatchOperation = new TokenDispatchOperation<TokenDispatcher<? extends Token>>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.garygregg.coverity.codingtest.lexical_analysis.Listener#addListener
	 * (java.lang.Object)
	 */
	public boolean addListener(AnalysisListener listener) {
		return (null == listener) ? false : listeners.add(listener);
	}

	/**
	 * Calls a dispatcher for each listener.
	 * 
	 * @param dispatcher
	 *            The dispatcher to be called for each listener
	 */
	public void dispatch(TokenDispatcher<? extends Token> dispatcher) {

		/*
		 * Set the given dispatcher in the token dispatch operation, and perform
		 * the operation for each listener.
		 */
		tokenDispatchOperation.setDispatcher(dispatcher);
		perform(tokenDispatchOperation);
	}

	/**
	 * Dispatches start to each listener.
	 */
	public void dispatchStart() {
		perform(startOperation);
	}

	/**
	 * Dispatches stop to each listener.
	 */
	public void dispatchStop() {
		perform(stopOperation);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.garygregg.coverity.codingtest.lexical_analysis.Listener#getListenerCount
	 * ()
	 */
	public int getListenerCount() {
		return listeners.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.garygregg.coverity.codingtest.lexical_analysis.Listener#hasListeners
	 * ()
	 */
	public boolean hasListeners() {
		return 0 < getListenerCount();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.garygregg.coverity.codingtest.lexical_analysis.Listener#removeListener
	 * (java.lang.Object)
	 */
	public boolean removeListener(AnalysisListener listener) {
		return listeners.remove(listener);
	}

	/**
	 * Performs an operation for each listener.
	 * 
	 * @param operation
	 *            An operation to be performed for each listener
	 */
	private void perform(Operation<AnalysisListener> operation) {

		// Perform the operation for each listener.
		for (AnalysisListener listener : listeners) {
			operation.perform(listener);
		}
	}
}

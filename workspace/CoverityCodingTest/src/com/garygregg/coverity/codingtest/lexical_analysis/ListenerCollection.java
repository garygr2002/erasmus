package com.garygregg.coverity.codingtest.lexical_analysis;

/**
 * Implements the abstraction of a listener collection.
 * 
 * @author Gary Gregg
 */
interface ListenerCollection<Type extends Object> {

	/**
	 * Adds a listener.
	 * 
	 * @param listener
	 *            The listener to add
	 * @return True if the listener was non-null, and was not already added as a
	 *         listener; false otherwise
	 */
	boolean addListener(Type listener);

	/**
	 * Gets the listener count.
	 * 
	 * @return The listener count
	 */
	int getListenerCount();

	/**
	 * Determines if there are any listeners.
	 * 
	 * @return True if there are one or more listeners, false otherwise
	 */
	boolean hasListeners();

	/**
	 * Removes a listener.
	 * 
	 * @param listener
	 *            The listener to remove
	 * @return True if the listener was removed; false if it was not previously
	 *         a listener
	 */
	boolean removeListener(Type listener);
}

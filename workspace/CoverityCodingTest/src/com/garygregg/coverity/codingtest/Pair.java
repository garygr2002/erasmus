package com.garygregg.coverity.codingtest;

/**
 * Contains a class that associates one object with one other object.
 * 
 * @author Gary Gregg
 * 
 * @param <S>
 *            An arbitrary type for the first object
 * @param <T>
 *            An arbitrary type for the second object
 */
class Pair<S, T> {

	// The first object
	private final S firstObject;

	// The second object
	private final T secondObject;

	/**
	 * Constructs the pair.
	 * 
	 * @param firstObject
	 *            The first object
	 * @param secondObject
	 *            The second object
	 */
	public Pair(S firstObject, T secondObject) {

		// Set the member variables.
		this.firstObject = firstObject;
		this.secondObject = secondObject;
	}

	/**
	 * Gets the first object.
	 * 
	 * @return The first object
	 */
	public S getFirst() {
		return firstObject;
	}

	/**
	 * Gets the second object.
	 * 
	 * @return The second object
	 */
	public T getSecond() {
		return secondObject;
	}
}

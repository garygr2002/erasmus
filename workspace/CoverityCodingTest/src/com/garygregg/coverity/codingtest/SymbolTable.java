package com.garygregg.coverity.codingtest;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

/**
 * Contains a symbol table.
 * 
 * @author Gary Gregg
 */
class SymbolTable {

	/**
	 * Compares a string to the string in a pair.
	 * 
	 * @param string
	 *            The string to compare
	 * @param pair
	 *            The pair containing a string to compare
	 * @return True if the string and the string in the pair are equal; false
	 *         otherwise
	 */
	private static boolean compare(String string, Pair<String, Integer> pair) {

		/*
		 * Get the string from the pair. Compare the given string to the string
		 * from the pair (with case sensitivity) if both are non-null. Otherwise
		 * if they are null compare them with object equality. Return the
		 * result.
		 */
		final String pairString = pair.getFirst();
		return ((null != string) && (null != pairString)) ? (0 == pairString
				.compareTo(string)) : (pairString == string);
	}

	// The symbol deque
	private final Deque<Pair<String, Integer>> symbolDeque = new ArrayDeque<Pair<String, Integer>>();

	/**
	 * Adds a symbol to the table.
	 * 
	 * @param symbol
	 *            The symbol to add
	 * @param value
	 *            The value to be assigned to the symbol
	 */
	public void addSymbol(String symbol, int value) {
		symbolDeque.push(new Pair<String, Integer>(symbol, value));
	}

	/**
	 * Clears the symbol table.
	 */
	public void clear() {
		symbolDeque.clear();
	}

	/**
	 * Finds a value for a symbol, or null if no value exists for the symbol.
	 * 
	 * @param symbol
	 *            The symbol for which to find a value
	 * @return The value if the symbol has one, or null otherwise
	 */
	public Integer findValueFor(String symbol) {

		/*
		 * Create an iterator for the symbol deque. Declare and initialize other
		 * local variables.
		 */
		final Iterator<Pair<String, Integer>> iterator = symbolDeque.iterator();
		boolean match = false;
		Pair<String, Integer> pair = null;

		/*
		 * Cycle until symbols are exhausted, or until a match is found for the
		 * given symbol.
		 */
		while (iterator.hasNext()
				&& !(match = compare(symbol, pair = iterator.next())))
			;

		/*
		 * Return the value for the matching symbol, or null if no match was
		 * found.
		 */
		return match ? pair.getSecond() : null;
	}

	/**
	 * Determine if the symbol table is empty.
	 * 
	 * @return True if the symbol table is empty, false otherwise
	 */
	public boolean isEmpty() {
		return symbolDeque.isEmpty();
	}

	/**
	 * Removes the last added symbol from the symbol table.
	 * 
	 * @return True if the last symbol added to the table was removed, false
	 *         otherwise
	 */
	public boolean removeLastSymbol() {

		// Does the table have at least one symbol?
		final boolean hasOne = !isEmpty();
		if (hasOne) {

			// The table has at least one symbol. Pop the last pushed symbol.
			symbolDeque.pop();
		}

		// Return whether the table had at least one symbol.
		return hasOne;
	}
}

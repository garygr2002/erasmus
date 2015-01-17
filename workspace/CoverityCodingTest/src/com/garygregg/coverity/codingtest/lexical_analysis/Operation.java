package com.garygregg.coverity.codingtest.lexical_analysis;

/**
 * Encapsulates an operation.
 * 
 * @author Gary Gregg
 * 
 * @param <ArgumentType>
 *            An arbitrary argument type
 */
interface Operation<ArgumentType> {

	/**
	 * Performs the operation.
	 * 
	 * @param argument
	 *            An argument of the given type
	 */
	void perform(ArgumentType argument);
}

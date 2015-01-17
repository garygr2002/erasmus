package com.garygregg.coverity.codingtest;

/**
 * Encapsulates the parsing states of the calculator.
 * 
 * @author Gary Gregg
 */
public enum State {

	/**
	 * The state when a close parenthesis after an operation or let expression
	 * is expected; the next state state will be EXPECTING_OPERATION (if ')' is
	 * encountered and the enclosing operation is top level),
	 * EXPECTING_LAST_COMMA (if ')' is encountered and the enclosing operation
	 * is the 2nd argument to a 'let' operation) or EXPECTING_CLOSE (if ')' is
	 * encountered and the enclosing operation is the last argument to an
	 * operation)
	 */
	EXPECTING_CLOSE,

	/**
	 * The state when a comma after a let expression is expected; the next state
	 * will be EXPECTING_SECOND_ARGUMENT_AFTER_LET (if a ',' is encountered)
	 */
	EXPECTING_COMMA_AFTER_LET,

	/**
	 * The state when the first argument after an operation is expected; the
	 * next state will be EXPECTING_OPEN (if an operation is encountered) or
	 * EXPECTING_LAST_COMMA (if a value or variable is encountered)
	 */
	EXPECTING_FIRST_ARGUMENT,

	/**
	 * The state when the first argument after a let expression is expected; the
	 * next state will be EXPECTING_COMMA_AFTER_LET (if a variable is
	 * encountered)
	 */
	EXPECTING_FIRST_ARGUMENT_AFTER_LET,

	/**
	 * The state when the last argument after an operation or let expression is
	 * expected; the next state will be EXPECTING_OPEN (if an operation is
	 * encountered) or EXPECTING_CLOSE (if a value or variable is encountered)
	 */
	EXPECTING_LAST_ARGUMENT,

	/**
	 * The state when a last comma after an operation or let expression is
	 * expected; the next state will be EXPECTING_LAST_ARGUMENT (if ',' is
	 * encountered)
	 */
	EXPECTING_LAST_COMMA,

	/**
	 * The state when an open parenthesis after an operation is expected; the
	 * next state will be EXPECTING_FIRST_ARGUMENT (if '(' is encountered)
	 */
	EXPECTING_OPEN,

	/**
	 * The state when an open parenthesis after a let expression is expected;
	 * the next state will be EXPECTING_FIRST_ARGUMENT_AFTER_LET (if '(' is
	 * encountered)
	 */
	EXPECTING_OPEN_AFTER_LET,

	/**
	 * The state when an operation or let expression is expected; the next state
	 * will be EXPECTING_OPEN (if an arithmetic operation is encountered) or
	 * EXPECTING_OPEN_AFTER_LET (if 'let' is encountered)
	 */
	EXPECTING_OPERATION,

	/**
	 * The state when the second argument after a let expression is expected;
	 * the next state will be EXPECTING_LAST_COMMA (if a value or variable is
	 * encountered)or EXPECTING_OPEN (if an operation is encountered)
	 */
	EXPECTING_SECOND_ARGUMENT_AFTER_LET,
}

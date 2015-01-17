package com.garygregg.coverity.codingtest.operation;

import java.io.PrintStream;

/**
 * Contains a subtract operation.
 * 
 * @author Gary Gregg
 */
public class SubtractOperation extends ReportingOperation {

	/**
	 * Creates the subtract operation with a default print stream.
	 */
	public SubtractOperation() {
		super();
	}

	/**
	 * Creates the subtract operation with an explicit print stream.
	 * 
	 * @param reportStream
	 *            The print stream for reporting information
	 */
	public SubtractOperation(PrintStream reportStream) {
		super(reportStream);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.garygregg.coverity.codingtest.operation.BinaryOperation#performOperation
	 * (int, int)
	 */
	@Override
	public int performOperation(int argument1, int argument2) {
		return argument1 - argument2;
	}
}

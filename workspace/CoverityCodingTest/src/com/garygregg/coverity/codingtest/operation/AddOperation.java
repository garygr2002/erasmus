package com.garygregg.coverity.codingtest.operation;

import java.io.PrintStream;

/**
 * Contains an add operation.
 * 
 * @author Gary Gregg
 */
public class AddOperation extends ReportingOperation {

	/**
	 * Creates the add operation with a default print stream.
	 */
	public AddOperation() {
		super();
	}

	/**
	 * Creates the add operation with an explicit print stream.
	 * 
	 * @param reportStream
	 *            The print stream for reporting information
	 */
	public AddOperation(PrintStream reportStream) {
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
		return argument1 + argument2;
	}
}

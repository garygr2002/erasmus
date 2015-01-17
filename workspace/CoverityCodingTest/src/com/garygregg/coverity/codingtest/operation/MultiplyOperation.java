package com.garygregg.coverity.codingtest.operation;

import java.io.PrintStream;

/**
 * Contains a multiply operation.
 * 
 * @author Gary Gregg
 */
public class MultiplyOperation extends ReportingOperation {

	/**
	 * Creates the multiply operation with a default print stream.
	 */
	public MultiplyOperation() {
		super();
	}

	/**
	 * Creates the multiply operation with an explicit print stream.
	 * 
	 * @param reportStream
	 *            The print stream for reporting information
	 */
	public MultiplyOperation(PrintStream reportStream) {
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
		return argument1 * argument2;
	}
}

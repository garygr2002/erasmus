package com.garygregg.coverity.codingtest.operation;

import java.io.PrintStream;

/**
 * Contains a divide operation.
 * 
 * @author Gary Gregg
 */
public class DivideOperation extends ReportingOperation {

	/**
	 * Creates the divide operation with a default print stream.
	 */
	public DivideOperation() {
		super();
	}

	/**
	 * Creates the divide operation with an explicit print stream.
	 * 
	 * @param reportStream
	 *            The print stream for reporting information
	 */
	public DivideOperation(PrintStream reportStream) {
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

		// Declare and initialize the result. Is the second argument zero?
		int result = 0;
		if (0 == argument2) {

			/*
			 * The second argument is zero. Declare and initialize a message
			 * object. Is the first argument less than zero?
			 */
			String message = "Divisor of a divide operation is zero - ";
			if (argument1 < 0) {

				/*
				 * The first argument is less than zero. Use the minimum integer
				 * in place of negative infinity. Update the message.
				 */
				result = Integer.MIN_VALUE;
				message += " dividend is negative; using minimum integer as a result.";
			}

			else if (0 == argument1) {

				/*
				 * The first argument is zero too. Use a result of one. Update
				 * the message.
				 */
				result = 1;
				message += " dividend is also zero; using one as a result.";
			}

			// The first argument is greater than zero.
			else {

				/*
				 * The first argument is positive. Use the maximum integer in
				 * place of positive infinity. Update the message.
				 */
				result = Integer.MAX_VALUE;
				message += " dividend is postive; using maximum integer as a result.";
			}

			// Print the message.
			println(message);
		}

		/*
		 * The second argument is not zero; a divide operation is okay to
		 * perform, so perform it and assign the result.
		 */
		else {
			result = argument1 / argument2;
		}

		// Return the result.
		return result;
	}
}

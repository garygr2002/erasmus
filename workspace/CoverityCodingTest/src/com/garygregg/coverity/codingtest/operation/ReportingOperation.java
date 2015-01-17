package com.garygregg.coverity.codingtest.operation;

import java.io.PrintStream;

/**
 * Contains an operation with a print stream for reporting information.
 * 
 * @author Gary Gregg
 */
public abstract class ReportingOperation implements BinaryOperation {

	/**
	 * Encapsulates a print operation.
	 * 
	 * @author Gary Gregg
	 * 
	 * @param <ArgumentType>
	 *            An arbitrary argument type
	 */
	private interface PrintOperation<ArgumentType> {

		/**
		 * Performs the print operation.
		 * 
		 * @param reportStream
		 *            A non-null report stream to print to
		 * @param argument
		 *            The object to report
		 */
		void perform(PrintStream reportStream, ArgumentType argument);
	}

	// A print operation that prints the argument without a new line
	private final PrintOperation<String> print = new PrintOperation<String>() {

		/**
		 * Performs the print operation.
		 * 
		 * @param reportStream
		 *            A non-null report stream to print to
		 * @param argument
		 *            The object to report
		 */
		@Override
		public void perform(PrintStream reportStream, String argument) {
			reportStream.print(argument);
		}
	};

	// A print operation that prints the argument with a new line
	private final PrintOperation<String> println = new PrintOperation<String>() {

		/**
		 * Performs the print operation.
		 * 
		 * @param reportStream
		 *            A non-null report stream to print to
		 * @param argument
		 *            The object to report
		 */
		@Override
		public void perform(PrintStream reportStream, String argument) {
			reportStream.println(argument);
		}
	};

	// The print stream for reporting information
	private final PrintStream reportStream;

	/**
	 * Creates the reporting operation with a default print stream. 
	 */
	protected ReportingOperation() {
		this(null);
	}

	/**
	 * Creates the reporting operation with an explicit print stream.
	 * 
	 * @param reportStream
	 *            The print stream for reporting information
	 */
	protected ReportingOperation(PrintStream reportStream) {
		this.reportStream = reportStream;
	}

	/**
	 * Gets the print stream for reporting information.
	 * 
	 * @return The print stream for reporting information
	 */
	public PrintStream getReportStream() {
		return reportStream;
	}

	/**
	 * Prints a message without a new line.
	 * 
	 * @param message
	 *            An arbitrary message
	 * @return True if the print was performed, false otherwise
	 */
	public boolean print(String message) {
		return perform(message, print);
	}

	/**
	 * Prints a message with a new line.
	 * 
	 * @param message
	 *            An arbitrary message
	 * @return True if the println was performed, false otherwise
	 */
	public boolean println(String message) {
		return perform(message, println);
	}

	/**
	 * Performs a print operation if the report stream is not null.
	 * 
	 * @param message
	 *            An arbitrary message
	 * @param operation
	 *            The print operation to perform
	 * @return True if the print operation was performed, false otherwise
	 */
	private boolean perform(String message, PrintOperation<String> operation) {

		// Get the report stream. Is the stream not null?
		final PrintStream stream = getReportStream();
		final boolean streamNotNull = (null != stream);
		if (streamNotNull) {

			// The report stream is not null. Perform the operation.
			operation.perform(stream, message);
		}

		// Return whether or not the operation was performed.
		return streamNotNull;
	}
}

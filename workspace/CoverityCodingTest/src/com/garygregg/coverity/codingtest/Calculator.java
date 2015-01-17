package com.garygregg.coverity.codingtest;

/**
 * Runs the calculator.
 * 
 * @author Gary Gregg
 */
public class Calculator {

	/**
	 * Runs the parser with the first command line argument.
	 * 
	 * @param args
	 *            Command line arguments.
	 */
	public static void main(String[] args) {
		runWithCommandLineArgs(args);
	}

	// /**
	// * Runs a series of canned tests.
	// */
	// private static void runCannedTests() {
	//
	// /*
	// * Set the print stream for the parser class. Create the test
	// * expressions.
	// */
	// Parser.setPrintStream(System.out);
	// final String[] expressions = { "add(2,3)",
	// "let(b,2,add(b,let(b,2,add(1,1))))", "div(8,sub(1,1))",
	// "%^&*(", "add", "add(", "add(5", "add(5,7", "add(5,7)",
	// "add(% ,99)", "mult(6 ^ 7)", "sub(6,7 ^ )",
	// "add(1,1) div(1,1) mult(1,1) sub(1,1)" };
	//
	// // Create a calculator, and run it with the expressions.
	// final Calculator calculator = new Calculator();
	// calculator.run(expressions);
	// }

	/**
	 * Runs the calculator once with command line arguments.
	 * 
	 * @param args
	 *            The command line arguments
	 */
	private static void runWithCommandLineArgs(String[] args) {

		/*
		 * Set the print stream for the parser class. Only proceed if there is
		 * at least two command line arguments.
		 */
		Parser.setPrintStream(System.err);
		if (1 < args.length) {

			/*
			 * There are two or more command line arguments. Create a
			 * calculator, and run it with the second command line argument.
			 */
			final Calculator calculator = new Calculator();
			calculator.run(args[1]);
		}

		/*
		 * There are fewer than two command line arguments. Output an error
		 * message.
		 */
		else {
			System.err
					.println("The calculator is missing a command line argument to parse.");
		}
	}

	// The parser for the calculator.
	private final Parser parser = new Parser();

	/**
	 * Runs a calculator parser with a single given expression.
	 * 
	 * @param expression
	 *            The single expression for the calculator to parse
	 */
	private void run(String expression) {

		// Output the expression being parsed.
		System.out.println("Running calculator for expression: '" + expression
				+ "'.");

		// Set the expression in the parser, and connect it for analysis.
		parser.setExpression(expression);
		parser.connectForAnalysis();

		// Launch the parser, and disconnect it from analysis.
		parser.launch();
		parser.disconnectFromAnalysis();

		/*
		 * Get the results of the parse, and the length of the returned array.
		 * Is there at least one result?
		 */
		final Integer[] results = parser.getResults();
		final int length = results.length;
		if (0 < length) {

			// There is at least one result. Print the first.
			int i = 0;
			System.out.print(results[i]);

			// Print each subsequent result preceded by a tab.
			for (++i; i < length; ++i) {
				System.out.print("\t" + results[i]);
			}

			// Print a new line.
			System.out.print("\n");
		}
	}

	// /**
	// * Runs a calculator parser with an array of expressions.
	// *
	// * @param expressions
	// * An array of expressions for the calculator to parse
	// */
	// private void run(String[] expressions) {
	//
	// // Run each expression.
	// for (String expression : expressions) {
	// run(expression);
	// }
	// }
}

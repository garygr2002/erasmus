package com.garygregg.coverity.codingtest;

import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.garygregg.coverity.codingtest.lexical_analysis.AnalysisListener;
import com.garygregg.coverity.codingtest.lexical_analysis.LexicalAnalyzer;
import com.garygregg.coverity.codingtest.operation.AddOperation;
import com.garygregg.coverity.codingtest.operation.BinaryOperation;
import com.garygregg.coverity.codingtest.operation.DivideOperation;
import com.garygregg.coverity.codingtest.operation.MultiplyOperation;
import com.garygregg.coverity.codingtest.operation.SubtractOperation;
import com.garygregg.coverity.codingtest.token.AddToken;
import com.garygregg.coverity.codingtest.token.CloseToken;
import com.garygregg.coverity.codingtest.token.CommaToken;
import com.garygregg.coverity.codingtest.token.DivideToken;
import com.garygregg.coverity.codingtest.token.LetToken;
import com.garygregg.coverity.codingtest.token.MultiplyToken;
import com.garygregg.coverity.codingtest.token.OpenToken;
import com.garygregg.coverity.codingtest.token.OperationToken;
import com.garygregg.coverity.codingtest.token.SubtractToken;
import com.garygregg.coverity.codingtest.token.Token;
import com.garygregg.coverity.codingtest.token.UnknownToken;
import com.garygregg.coverity.codingtest.token.ValueToken;
import com.garygregg.coverity.codingtest.token.VariableToken;

/**
 * Contains a parser for the calculator.
 * 
 * @author Gary Gregg
 */
class Parser implements AnalysisListener {

	/*
	 * A map of states to their transition states when argument tokens are
	 * received
	 */
	private static final Map<State, State> argumentTransitionMap = new HashMap<State, State>();

	// A set of common states where value or variable tokens may be received
	private static final Set<State> commonStatesWhereValuesOrVariablesArePossible = new HashSet<State>();

	// The print stream for errors
	private static PrintStream errorStream = getDefaultPrintStream();

	// An array of the operation token expressions
	private static final String[] knownOperationTokenExpressions = {
			AddToken.getCommonExpression(), DivideToken.getCommonExpression(),
			LetToken.getCommonExpression(),
			MultiplyToken.getCommonExpression(),
			SubtractToken.getCommonExpression() };

	/*
	 * A map of states from the last state on the stack to the state for
	 * substitute tokens that replace operations
	 */
	private static final Map<State, State> operationSubstitutionTransitionMap = new HashMap<State, State>();

	// A set of states where operation tokens may be received
	private static final Set<State> statesWhereOperationsArePossible = new HashSet<State>();

	// A map of states to 'whileExpecting' strings (used for error output)
	private static final Map<State, String> whileExpectingMap = new HashMap<State, String>();

	static {

		/*
		 * Build the argument transition map, and the operation substitution
		 * transition map.
		 */
		buildArgumentTransitionMap();
		buildOperationSubstitutionTransitionMap();

		/*
		 * Build the set of states where operation tokens may be received and
		 * the set of states where value tokens may be received. Build the map
		 * of parser states to strings describing what token(s) are expected in
		 * each state.
		 */
		buildStatesWhereOperationsArePossible();
		buildCommonStatesWhereValueOrVariableTokensArePossible();
		buildWhileExpectingMap();
	}

	/**
	 * Sets the print stream.
	 * 
	 * @param printStream
	 *            The print stream
	 */
	public static void setPrintStream(PrintStream printStream) {
		Parser.errorStream = (null == printStream) ? getDefaultPrintStream()
				: printStream;
	}

	/**
	 * Builds the map of states to transition states when argument tokens are
	 * received.
	 */
	private static void buildArgumentTransitionMap() {

		// null
		argumentTransitionMap.put(null, State.EXPECTING_OPERATION);

		// EXPECTING_FIRST_ARGUMENT
		argumentTransitionMap.put(State.EXPECTING_FIRST_ARGUMENT,
				State.EXPECTING_LAST_COMMA);

		// EXPECTING_LAST_ARGUMENT
		argumentTransitionMap.put(State.EXPECTING_LAST_ARGUMENT,
				State.EXPECTING_CLOSE);

		// EXPECTING_CLOSE
		argumentTransitionMap.put(State.EXPECTING_CLOSE,
				State.EXPECTING_OPERATION);

		// EXPECTING_FIRST_ARGUMENT_AFTER_LET
		argumentTransitionMap.put(State.EXPECTING_FIRST_ARGUMENT_AFTER_LET,
				State.EXPECTING_COMMA_AFTER_LET);

		// EXPECTING_SECOND_ARGUMENT_AFTER_LET
		argumentTransitionMap.put(State.EXPECTING_SECOND_ARGUMENT_AFTER_LET,
				State.EXPECTING_LAST_COMMA);
	}

	/**
	 * Builds the set of common states where value or variable tokens may be
	 * received.
	 */
	private static void buildCommonStatesWhereValueOrVariableTokensArePossible() {

		// EXPECTING_FIRST_ARGUMENT
		commonStatesWhereValuesOrVariablesArePossible
				.add(State.EXPECTING_FIRST_ARGUMENT);

		// EXPECTING_LAST_ARGUMENT
		commonStatesWhereValuesOrVariablesArePossible
				.add(State.EXPECTING_LAST_ARGUMENT);

		// EXPECTING_SECOND_ARGUMENT_AFTER_LET
		commonStatesWhereValuesOrVariablesArePossible
				.add(State.EXPECTING_SECOND_ARGUMENT_AFTER_LET);
	}

	/**
	 * Builds the map of states from the last state on the stack to the state
	 * for substitute tokens that replace operations.
	 */
	private static void buildOperationSubstitutionTransitionMap() {

		// null
		operationSubstitutionTransitionMap.put(null, null);

		// EXPECTING_OPERATION
		operationSubstitutionTransitionMap.put(State.EXPECTING_OPERATION,
				State.EXPECTING_FIRST_ARGUMENT);

		// EXPECTING_FIRST_ARGUMENT
		operationSubstitutionTransitionMap.put(State.EXPECTING_FIRST_ARGUMENT,
				State.EXPECTING_LAST_ARGUMENT);

		// EXPECTING_FIRST_ARGUMENT_AFTER_LET
		operationSubstitutionTransitionMap.put(
				State.EXPECTING_FIRST_ARGUMENT_AFTER_LET,
				State.EXPECTING_SECOND_ARGUMENT_AFTER_LET);

		// EXPECTING_SECOND_ARGUMENT_AFTER_LET
		operationSubstitutionTransitionMap.put(
				State.EXPECTING_SECOND_ARGUMENT_AFTER_LET,
				State.EXPECTING_LAST_ARGUMENT);
	}

	/**
	 * Builds the set of states where operation tokens may be received.
	 */
	private static void buildStatesWhereOperationsArePossible() {

		/*
		 * Build up the set of states where receipt of operations is possible.
		 * The first two are when a top-level operation is expected, and when a
		 * first argument is expected.
		 */
		statesWhereOperationsArePossible.add(State.EXPECTING_OPERATION);
		statesWhereOperationsArePossible.add(State.EXPECTING_FIRST_ARGUMENT);

		/*
		 * The next two are when a last argument is expected, and when a 2nd
		 * argument after a 'let' statement is expected.
		 */
		statesWhereOperationsArePossible.add(State.EXPECTING_LAST_ARGUMENT);
		statesWhereOperationsArePossible
				.add(State.EXPECTING_SECOND_ARGUMENT_AFTER_LET);
	}

	/**
	 * Builds the 'while expecting' string for known operation expressions.
	 * 
	 * @return The 'while expecting' string for known operation expressions
	 */
	private static String buildWhileExpectingForOperations() {

		/*
		 * Get the length of the array of known token expressions. Declare and
		 * initialize a variable for the word 'operation'.
		 */
		int length = knownOperationTokenExpressions.length;
		final String operation = " operation";

		/*
		 * Declare and initialize the return value. Is the length of the known
		 * expressions array greater than zero?
		 */
		String whileExpectingForOperations = "a";
		if (0 < length) {

			/*
			 * The length of the known expressions array is greater than zero.
			 * Declare an array index, and initialize it to zero. Get the first
			 * known expressions string. Does the first expression begin with a
			 * vowel?
			 */
			int i = 0;
			String expression = knownOperationTokenExpressions[i];
			if (expression.matches("[AEIOUaeiou].*")) {

				/*
				 * The first expression begins with a vowel. Append an 'n' to
				 * the return value.
				 */
				whileExpectingForOperations += "n";
			}

			/*
			 * Append to the return value a space followed by the first
			 * expression. Decrement the length, and cycle for each known
			 * expression except the last.
			 */
			whileExpectingForOperations += " " + expression;
			--length;
			for (++i; i < length; ++i) {

				/*
				 * Append to the return value a comma followed by the next known
				 * expression.
				 */
				whileExpectingForOperations += (", " + knownOperationTokenExpressions[i]);
			}

			/*
			 * Append to the return value an ' or ' followed by the last known
			 * expression, followed by the word 'operation'.
			 */
			whileExpectingForOperations += " or "
					+ knownOperationTokenExpressions[length] + operation;

		}

		/*
		 * The length of the known expression array is zero or less. Append a
		 * suffix to the return string so that it will simply read, 'an
		 * operation'.
		 */
		else {
			whileExpectingForOperations += "n" + operation;
		}

		// Return the built-up string.
		return whileExpectingForOperations;
	}

	/**
	 * Builds the map of parser states to strings describing what token(s) are
	 * expected in each state.
	 */
	private static void buildWhileExpectingMap() {

		/*
		 * Declare and initialize the description of an argument and an open
		 * parenthesis.
		 */
		final String argumentDescription = "an operation, integer value or defined variable";
		final String openParenthesisDescription = "an opening parenthesis, '('";

		// Add the string for EXPECTING_OPERATION.
		whileExpectingMap.put(State.EXPECTING_OPERATION,
				buildWhileExpectingForOperations());

		// Add the string for EXPECTING_OPEN.
		whileExpectingMap.put(State.EXPECTING_OPEN, openParenthesisDescription
				+ ", after an arithmetic operation");

		// Add the string for EXPECTING_FIRST_ARGUMENT.
		whileExpectingMap.put(State.EXPECTING_FIRST_ARGUMENT,
				argumentDescription + " as a first argument");

		// Add the string for EXPECTING_COMMA.
		whileExpectingMap.put(State.EXPECTING_LAST_COMMA,
				"a ',' before the final argument of an operation");

		// Add the string for EXPECTING_LAST_ARGUMENT.
		whileExpectingMap.put(State.EXPECTING_LAST_ARGUMENT,
				argumentDescription + " as a last argument");

		// Add the string for EXPECTING_CLOSE.
		whileExpectingMap.put(State.EXPECTING_CLOSE,
				"a closing parenthesis, ')'");

		// Add the string for EXPECTING_OPEN_AFTER_LET.
		whileExpectingMap.put(State.EXPECTING_OPEN_AFTER_LET,
				openParenthesisDescription + ", after a 'let' statement");

		// Add the string for EXPECTING_FIRST_ARGUMENT_AFTER_LET.
		whileExpectingMap
				.put(State.EXPECTING_FIRST_ARGUMENT_AFTER_LET,
						"as a first argument for a 'let' statement, a variable to be defined");

		// Add the string for EXPECTING_COMMA_AFTER_LET.
		whileExpectingMap.put(State.EXPECTING_COMMA_AFTER_LET,
				"a ',' before the second argument of a 'let' statement");

		// Add the string for EXPECTING_SECOND_ARGUMENT_AFTER_LET.
		whileExpectingMap.put(State.EXPECTING_SECOND_ARGUMENT_AFTER_LET,
				"as a second argument for a 'let' statement, "
						+ argumentDescription);
	}

	/**
	 * Gets the default print stream.
	 */
	private static PrintStream getDefaultPrintStream() {
		return System.err;
	}

	/**
	 * Gets the error stream.
	 * 
	 * @return The error stream
	 */
	private static PrintStream getErrorStream() {
		return errorStream;
	}

	/**
	 * Returns the transition state from a known argument acceptance state.
	 * 
	 * @param argumentState
	 *            The known argument acceptance state
	 * @return The transition state for the known argument acceptance state, or
	 *         null if the argument is not an argument acceptance state
	 */
	private static State transitionFromArgumentStateTo(State argumentState) {
		return argumentTransitionMap.get(argumentState);
	}

	/**
	 * Returns the state for a substitute token that replaces an operation when
	 * the operation is complete.
	 * 
	 * @param lastStateOnTokenDeque
	 *            The last state on the token deque
	 * @return The state for a substitute token that replaces an operation when
	 *         the operation is complete
	 */
	private static State transitionFromCloseStateTo(State lastStateOnTokenDeque) {
		return operationSubstitutionTransitionMap.get(lastStateOnTokenDeque);
	}

	// Our add operation
	private final AddOperation addOperation = new AddOperation(errorStream);

	// Our divide operation
	private final DivideOperation divideOperation = new DivideOperation(
			errorStream);

	// Our let operation
	private final BinaryOperation letOperation = new BinaryOperation() {

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.garygregg.coverity.codingtest.operation.BinaryOperation#
		 * performOperation(int, int)
		 */
		@Override
		public int performOperation(int argument1, int argument2) {

			/*
			 * Remove the last symbol from the symbol table, and return the
			 * unmodified 2nd argument.
			 */
			removeSymbol();
			return argument2;
		}
	};

	// The lexical analyzer for the parser
	private final LexicalAnalyzer lexicalAnalyzer;

	// Our multiply operation
	private final MultiplyOperation multiplyOperation = new MultiplyOperation(
			errorStream);

	// The state of the parser
	private State state;

	// Our subtract operation
	private final SubtractOperation subtractOperation = new SubtractOperation(
			errorStream);

	// The symbol table
	private final SymbolTable symbolTable = new SymbolTable();

	// A deque for tokens and their metadata
	private final Deque<TokenInformation> tokenDeque = new ArrayDeque<TokenInformation>();

	/**
	 * Constructs a parser with a default expression.
	 */
	public Parser() {
		this(null);
	}

	/**
	 * Constructs the parser with an explicit expression.
	 * 
	 * @param expression
	 *            The expression to be used by this parser
	 */
	public Parser(String expression) {

		// Create the lexical analyzer, and set the expression.
		lexicalAnalyzer = new LexicalAnalyzer();
		setExpression(expression);
	}

	/**
	 * Connects the parser for analysis.
	 */
	public void connectForAnalysis() {
		lexicalAnalyzer.addListener(this);
	}

	/**
	 * Disconnects the parser from analysis.
	 */
	public void disconnectFromAnalysis() {
		lexicalAnalyzer.removeListener(this);
	}

	/**
	 * Gets the results of the parse.
	 * 
	 * @return The results of the parse as an array of token values
	 */
	public Integer[] getResults() {

		/*
		 * Declare a list to receive the parse results, and a variable to
		 * receive tokens.
		 */
		final List<Integer> results = new ArrayList<Integer>();
		Token token = null;

		/*
		 * Create a descending iterator from the token deque, and cycle while
		 * token metadata exists.
		 */
		final Iterator<TokenInformation> iterator = tokenDeque
				.descendingIterator();
		while (iterator.hasNext()) {

			/*
			 * Get the first/next token from the current token metadata. The
			 * token was created by this parser if does not have position
			 * information.
			 */
			token = iterator.next().getToken();
			if ((null != token) && (null == token.getPosition())) {

				/*
				 * The token was created by this parser. Add its value to the
				 * results.
				 */
				results.add(token.getValue());
			}
		}

		// Return the results to our caller as an array.
		return results.toArray(new Integer[results.size()]);
	}

	/**
	 * Launches the lexical parser.
	 */
	public void launch() {
		lexicalAnalyzer.launch();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.garygregg.coverity.codingtest.lexical_analysis.AnalysisListener#
	 * receiveAddToken(com.garygregg.coverity.codingtest.token.AddToken)
	 */
	@Override
	public void receiveAddToken(AddToken token) {
		receiveOperationToken(token, State.EXPECTING_OPEN, addOperation);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.garygregg.coverity.codingtest.lexical_analysis.AnalysisListener#
	 * receiveCloseToken(com.garygregg.coverity.codingtest.token.CloseToken)
	 */
	@Override
	public void receiveCloseToken(CloseToken token) {

		// We can only close if our current state is EXPECTING_CLOSE.
		if (State.EXPECTING_CLOSE.equals(state)) {

			/*
			 * We are in the EXPECTING_CLOSE state. Pop information for the last
			 * token on the token deque. Cycle while the metadata is not null,
			 * and the metadata does not have an operation (i.e., it is an
			 * argument token).
			 */
			final Deque<Integer> argumentDeque = new ArrayDeque<Integer>();
			TokenInformation metadata = popLastToken();
			while ((null != metadata) && (!metadata.hasOperation())) {

				/*
				 * The metadata indicates that the first/next token is an
				 * argument token. Get the argument value, and push it onto the
				 * argument deque. Pop the next token.
				 */
				argumentDeque.push(getArgumentValue(metadata.getToken()));
				metadata = popLastToken();
			}

			/*
			 * Is the metadata null? This indicates the token deque is empty, or
			 * that null token data was pushed on the token deque.
			 */
			if (null == metadata) {

				// Output an error message, and do not change state.
				getErrorStream()
						.println(
								"Error: no token with an operation was found upon operation reduction.");
			}

			/*
			 * The metadata is not null, and the token is an operation. Perform
			 * a binary operation.
			 */
			else {
				performBinaryOperation(argumentDeque, metadata);
			}
		}

		/*
		 * We are not in the EXPECTING_CLOSE state. Output an unexpected token
		 * message.
		 */
		else {
			outputUnexpectedToken(token, getWhileExpectingString());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.garygregg.coverity.codingtest.lexical_analysis.AnalysisListener#
	 * receiveCommaToken(com.garygregg.coverity.codingtest.token.CommaToken)
	 */
	@Override
	public void receiveCommaToken(CommaToken token) {

		/*
		 * Transition to the expecting last argument state if the current state
		 * is expecting last comma.
		 */
		if (State.EXPECTING_LAST_COMMA.equals(state)) {
			state = State.EXPECTING_LAST_ARGUMENT;
		}

		/*
		 * Otherwise transition to the expecting 2nd argument after 'let' state
		 * if the current state is expecting comma after 'let'.
		 */
		else if (State.EXPECTING_COMMA_AFTER_LET.equals(state)) {
			state = State.EXPECTING_SECOND_ARGUMENT_AFTER_LET;
		}

		// Otherwise output an unexpected token message.
		else {
			outputUnexpectedToken(token, getWhileExpectingString());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.garygregg.coverity.codingtest.lexical_analysis.AnalysisListener#
	 * receiveDivideToken(com.garygregg.coverity.codingtest.token.DivideToken)
	 */
	@Override
	public void receiveDivideToken(DivideToken token) {
		receiveOperationToken(token, State.EXPECTING_OPEN, divideOperation);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.garygregg.coverity.codingtest.lexical_analysis.AnalysisListener#
	 * receiveLetToken(com.garygregg.coverity.codingtest.token.LetToken)
	 */
	@Override
	public void receiveLetToken(LetToken token) {

		/*
		 * The 'let' operation is unlike the arithmetic operations in that it
		 * transitions to the special EXPECTING_OPEN_AFTER_LET state, and also
		 * uses the member variable 'letOperation' that gives access to this
		 * parser's internal state so that a symbol can be removed from the
		 * symbol table upon completion of the 'let' statement.
		 */
		receiveOperationToken(token, State.EXPECTING_OPEN_AFTER_LET,
				letOperation);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.garygregg.coverity.codingtest.lexical_analysis.AnalysisListener#
	 * receiveMultiplyToken
	 * (com.garygregg.coverity.codingtest.token.MultiplyToken)
	 */
	@Override
	public void receiveMultiplyToken(MultiplyToken token) {
		receiveOperationToken(token, State.EXPECTING_OPEN, multiplyOperation);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.garygregg.coverity.codingtest.lexical_analysis.AnalysisListener#
	 * receiveOpenToken(com.garygregg.coverity.codingtest.token.OpenToken)
	 */
	@Override
	public void receiveOpenToken(OpenToken token) {

		/*
		 * Transition to the expecting 1st argument state if the current state
		 * is expecting open.
		 */
		if (State.EXPECTING_OPEN.equals(state)) {
			state = State.EXPECTING_FIRST_ARGUMENT;
		}

		/*
		 * Otherwise transition to the 1st argument after 'let' state if the
		 * current state is expecting open after 'let'.
		 */
		else if (State.EXPECTING_OPEN_AFTER_LET.equals(state)) {
			state = State.EXPECTING_FIRST_ARGUMENT_AFTER_LET;
		}

		// Otherwise output an unexpected token message.
		else {
			outputUnexpectedToken(token, getWhileExpectingString());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.garygregg.coverity.codingtest.lexical_analysis.AnalysisListener#
	 * receiveSubtractToken
	 * (com.garygregg.coverity.codingtest.token.SubtractToken)
	 */
	@Override
	public void receiveSubtractToken(SubtractToken token) {
		receiveOperationToken(token, State.EXPECTING_OPEN, subtractOperation);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.garygregg.coverity.codingtest.lexical_analysis.AnalysisListener#
	 * receiveUnknownToken(com.garygregg.coverity.codingtest.token.UnknownToken)
	 */
	@Override
	public void receiveUnknownToken(UnknownToken token) {
		outputUnexpectedToken(token, getWhileExpectingString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.garygregg.coverity.codingtest.lexical_analysis.AnalysisListener#
	 * receiveValueToken(com.garygregg.coverity.codingtest.token.ValueToken)
	 */
	@Override
	public void receiveValueToken(ValueToken token) {

		// May a value token be received in the current state?
		if (mayReceiveValueToken()) {

			/*
			 * A value token may be received in the current state. Try to add a
			 * symbol for the value, if appropriate. Use the given token along
			 * with the current state to create token metadata. Push the token
			 * metadata onto the token deque. Perform the indicated state
			 * transition as a last step.
			 */
			addSymbol(token.getValue());
			tokenDeque.push(new TokenInformation(token, state));
			state = transitionFromArgumentStateTo();
		}

		/*
		 * An value token may not be received in the current state. Output an
		 * unexpected token message.
		 */
		else {
			outputUnexpectedToken(token, getWhileExpectingString());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.garygregg.coverity.codingtest.lexical_analysis.AnalysisListener#
	 * receiveVariableToken
	 * (com.garygregg.coverity.codingtest.token.VariableToken)
	 */
	@Override
	public void receiveVariableToken(VariableToken token) {

		// May a variable token be received in the current state?
		if (mayReceiveVariableToken()) {

			/*
			 * A variable token may be received in the current state. Try to add
			 * a symbol for the expression of the token from the symbol table,
			 * if appropriate. Use the given token along with the current state
			 * to create token metadata. Push the token metadata onto the token
			 * deque. Perform the indicated state transition as a last step.
			 */
			addSymbol(token);
			tokenDeque.push(new TokenInformation(token, state));
			state = transitionFromArgumentStateTo();
		}

		/*
		 * An value token may not be received in the current state. Output an
		 * unexpected token message.
		 */
		else {
			outputUnexpectedToken(token, getWhileExpectingString());
		}
	}

	/**
	 * Resets the parser.
	 */
	public void reset() {

		// Reset the lexical analyzer and clear the symbol table.
		lexicalAnalyzer.reset();
		symbolTable.clear();

		// Clear the token deque, and start analysis.
		tokenDeque.clear();
		startAnalysis();
	}

	/**
	 * Sets the expression of the parser.
	 * 
	 * @param expression
	 *            The expression of the parser
	 */
	public void setExpression(String expression) {

		// Set the expression in the lexical analyzer, and reset the parser.
		lexicalAnalyzer.setExpression(expression);
		reset();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.garygregg.coverity.codingtest.lexical_analysis.AnalysisListener#
	 * startAnalysis()
	 */
	@Override
	public void startAnalysis() {
		state = State.EXPECTING_OPERATION;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.garygregg.coverity.codingtest.lexical_analysis.AnalysisListener#
	 * stopAnalysis()
	 */
	@Override
	public void stopAnalysis() {

		// At this point our state should be EXPECTING_OPERATION.
		if (!State.EXPECTING_OPERATION.equals(state)) {

			/*
			 * Our state is not EXPECTING_OPERATION. This could be caused by a
			 * missing close parenthesis, or any number of poorly formatted
			 * input strings. Output an error message.
			 */
			getErrorStream().println(
					"Error: parse of input has ended in state of " + state
							+ ".");
		}

		// Restart the analysis.
		startAnalysis();
	}

	/**
	 * Access a value for a token from the symbol table.
	 * 
	 * @param token
	 *            A token containing the expression used to look up a value in
	 *            the symbol table
	 * @return The value of the expression in the symbol table, or null if the
	 *         expression is not in the symbol table
	 */
	private Integer accessSymbol(Token token) {

		// Get the expression of the given token. Use null if the token is null.
		final String expression = (null == token) ? null : token
				.getExpression();

		/*
		 * Find the value of the expression in the symbol table. Is the value
		 * null?
		 */
		final Integer value = symbolTable.findValueFor(expression);
		if (null == value) {

			// The value is null. Output an error message.
			getErrorStream().println(
					"Attempt to use undefined symbol, '" + expression
							+ "' at position " + token.getPosition() + ".");

		}

		// Return the value.
		return value;
	}

	/**
	 * Adds a symbol to the symbol table.
	 * 
	 * @param value
	 *            The value to add for the symbol
	 * @return True if a symbol was successfully added to the symbol table,
	 *         false otherwise
	 */
	private boolean addSymbol(int value) {

		/*
		 * It is only okay to add a symbol if the current state of the parser is
		 * EXPECTING_SECOND_ARGUMENT_AFTER_LET. Is the current state correct?
		 */
		boolean okayToAdd = State.EXPECTING_SECOND_ARGUMENT_AFTER_LET
				.equals(state);
		if (okayToAdd) {

			/*
			 * The current state is correct. Get the token information, if
			 * available, of the most recent token on the token deque. We can
			 * only add a symbol if there is a token on the stack, and the state
			 * of the parser at the time it was added was
			 * EXPECTING_FIRST_ARGUMENT_AFTER LET.
			 */
			final TokenInformation metadata = peekLastToken();
			if (okayToAdd = ((null != metadata) && (State.EXPECTING_FIRST_ARGUMENT_AFTER_LET
					.equals(metadata.getState())))) {

				/*
				 * Add a symbol to the stack using the expression of the token
				 * on the stack and the given value.
				 */
				symbolTable.addSymbol(metadata.getToken().getExpression(),
						value);
			}
		}

		// Return whether a symbol was added.
		return okayToAdd;
	}

	/**
	 * Adds a symbol to the symbol table.
	 * 
	 * @param token
	 *            A variable token containing the expression used to look up a
	 *            value in the symbol table
	 * @return True if a symbol was successfully added to the symbol table,
	 *         false otherwise
	 */
	private boolean addSymbol(VariableToken token) {

		/*
		 * Access the value of the expression of the given token in the symbol
		 * table only if our state is EXPECTING_SECOND_ARGUMENT_AFTER_LET. We
		 * can add a new symbol to the symbol table if the obtained value is not
		 * null.
		 */
		final Integer value = (State.EXPECTING_SECOND_ARGUMENT_AFTER_LET
				.equals(state)) ? accessSymbol(token) : null;
		boolean okayToAdd = (null != value);

		/*
		 * Add a new symbol to the symbol table if the obtained value is not
		 * null.
		 */
		if (okayToAdd) {
			okayToAdd = addSymbol(value);
		}

		// Return whether a symbol was added.
		return okayToAdd;
	}

	/**
	 * Gets the value for an argument token.
	 * 
	 * @param token
	 *            The argument token
	 * @return The value for the argument token
	 */
	private int getArgumentValue(Token token) {

		/*
		 * Try to use the token's given value, if it has one. It doesn't have
		 * one if the value is null. Is the value null?
		 */
		Integer value = token.getValue();
		if (null == value) {

			/*
			 * The value for the token is null. Try to access a value for its
			 * expression in the symbol table.
			 */
			value = accessSymbol(token);
		}

		// Is there still no value?
		if (null == value) {

			/*
			 * There is neither a value for the token, nor a value for its
			 * expression in the symbol table. Output an error message.
			 */
			getErrorStream().println(
					"Cannot resolve value for token expression '"
							+ token.getExpression()
							+ "', using default value of zero.");

			// Use a default value of zero.
			value = 0;
		}

		// Return the value.
		return value;
	}

	/**
	 * Gets the state of the last token metadata on the token deque.
	 * 
	 * @return The state of the last token metadata on the token deque
	 */
	private State getLastState() {

		/*
		 * Get the token metadata for the last token on the token deque. Return
		 * null if the metadata is null, or the state in the metadata if the
		 * metadata is not null.
		 */
		final TokenInformation metadata = peekLastToken();
		return (null == metadata) ? null : metadata.getState();
	}

	/**
	 * Gets the next argument from an argument deque.
	 * 
	 * @param deque
	 *            The deque containing the arguments.
	 * @param argumentNumber
	 *            The number of the argument being sought (for reporting
	 *            purposes)
	 * @param position
	 *            The position of the argument
	 * @return The next argument from the given argument deque
	 */
	private int getNextArgumentValue(Deque<? extends Integer> deque,
			int argumentNumber, Integer position) {

		/*
		 * Pop the last argument. Declare and initialize the return value. Is
		 * the argument value null?
		 */
		final Integer argumentValue = deque.isEmpty() ? null : deque.pop();
		int returnValue = 0;
		if (null == argumentValue) {

			// The argument value is null. Output an error message.
			getErrorStream().println(
					"No argument number "
							+ Integer.toString(argumentNumber + 1)
							+ " for the operation at position " + position
							+ "; using a default of " + returnValue + ".");
		}

		/*
		 * The last argument is not null. Unbox it and assign it to the return
		 * value.
		 */
		else {
			returnValue = argumentValue;
		}

		// Return the value.
		return returnValue;
	}

	/**
	 * Gets the 'while expecting' string for a given state.
	 * 
	 * @return The 'while expecting' string for the given state
	 */
	private String getWhileExpectingString() {
		return whileExpectingMap.get(state);
	}

	/**
	 * Determines if the parser may currently receive an operation token.
	 * 
	 * @return True if the parser may receive an operation token, false
	 *         otherwise
	 */
	private boolean mayReceiveOperationToken() {
		return statesWhereOperationsArePossible.contains(state);
	}

	/**
	 * Determines if the parser may currently receive a value token.
	 * 
	 * @return True if the parser may receive a value token, false otherwise
	 */
	private boolean mayReceiveValueToken() {
		return commonStatesWhereValuesOrVariablesArePossible.contains(state)
				|| (null == state);
	}

	/**
	 * Determine if the parser may currently receive a variable token.
	 * 
	 * @return True if the parser may receive a variable token, false otherwise
	 */
	private boolean mayReceiveVariableToken() {
		return commonStatesWhereValuesOrVariablesArePossible.contains(state)
				|| (State.EXPECTING_FIRST_ARGUMENT_AFTER_LET.equals(state));
	}

	/**
	 * Outputs unexpected tokens to the error stream in a uniform way.
	 * 
	 * @param token
	 *            The token that was unexpected
	 * @param whileExpecting
	 *            What was unexpected when this token was received
	 */
	private void outputUnexpectedToken(Token token, String whileExpecting) {

		// Output the message to the error stream.
		getErrorStream().println(
				"Unexpected " + token.toString() + " encountered at position "
						+ token.getPosition() + " while expecting "
						+ whileExpecting + "; continuing to parse.");
	}

	/**
	 * Peeks at the last token from the token deque.
	 * 
	 * @return The last token from the token deque, or null if the token deque
	 *         is empty
	 */
	private TokenInformation peekLastToken() {
		return tokenDeque.isEmpty() ? null : tokenDeque.peek();
	}

	/**
	 * Performs a binary operation.
	 * 
	 * @param argumentDeque
	 *            An argument deque where the two arguments for the binary
	 *            operation are the first two on the deque
	 * @param metadata
	 *            Token metadata containing the operation to be performed and
	 *            the position of the operation
	 */
	private void performBinaryOperation(final Deque<Integer> argumentDeque,
			TokenInformation metadata) {

		// Pop all but the last two arguments from the argument deque.
		while (2 < argumentDeque.size()) {
			argumentDeque.pop();
		}

		/*
		 * Get the position of the operation. Declare and initialize an argument
		 * number variable.
		 */
		final Integer operationPosition = metadata.getToken().getPosition();
		int argumentNumber = 0;

		/*
		 * Get the last argument from the argument deque. This the first
		 * argument to our operation.
		 */
		final int firstArgument = getNextArgumentValue(argumentDeque,
				argumentNumber++, operationPosition);

		/*
		 * Get the 2nd to last argument from the argument deque. This is the
		 * second argument to our operation.
		 */
		final int secondArgument = getNextArgumentValue(argumentDeque,
				argumentNumber, operationPosition);

		// Perform the operation as specified in the token metadata.
		final int result = metadata.getOperation().performOperation(
				firstArgument, secondArgument);

		// State the state, and receive a new value token.
		state = transitionFromCloseStateTo();
		receiveValueToken(new ValueToken(result));
	}

	/**
	 * Pops the last token from the token deque.
	 * 
	 * @return The last token from the token deque, or null if the token deque
	 *         is empty
	 */
	private TokenInformation popLastToken() {
		return tokenDeque.isEmpty() ? null : tokenDeque.pop();
	}

	/**
	 * Receives an operation token.
	 * 
	 * @param token
	 *            The key token to be received
	 * @param transitionTo
	 *            The state to transition if the given key token may be received
	 * @param operation
	 *            The operation to be performed when the operation is reduced
	 */
	private void receiveOperationToken(OperationToken token,
			State transitionTo, BinaryOperation operation) {

		// May an operation token be received in the current state?
		if (mayReceiveOperationToken()) {

			/*
			 * An operation may be received in the current state. Use the given
			 * token and operation - along with the current state - to create
			 * token metadata. Push the token metadata onto the token deque.
			 * Perform the indicated state transition as a last step.
			 */
			tokenDeque.push(new TokenInformation(token, state, operation));
			state = transitionTo;
		}

		/*
		 * An operation may not be received in the current state. Output an
		 * unexpected token message.
		 */
		else {
			outputUnexpectedToken(token, getWhileExpectingString());
		}
	}

	/**
	 * Removes a symbol from the symbol table.
	 * 
	 * @return True if a symbol was successfully removed from the symbol table,
	 *         false otherwise
	 */
	private boolean removeSymbol() {

		/*
		 * It is only okay to remove a symbol if the current state of the parser
		 * is EXPECTING_CLOSE. Is the current state correct?
		 */
		final boolean okayToRemove = State.EXPECTING_CLOSE.equals(state);
		if (okayToRemove) {

			// Remove the last symbol from the symbol table.
			symbolTable.removeLastSymbol();
		}

		// Return whether a symbol was removed.
		return okayToRemove;
	}

	/**
	 * Returns the transition state from a known argument acceptance state.
	 * 
	 * @return The transition state for the known argument acceptance state
	 */
	private State transitionFromArgumentStateTo() {

		/*
		 * Get the new state when transitioning from the current state. Assert
		 * that the new state is not null, then return it.
		 */
		final State newState = transitionFromArgumentStateTo(state);
		assert (null != newState);
		return newState;
	}

	/**
	 * Returns the state for a substitute token that replaces an operation when
	 * the operation is complete.
	 * 
	 * @return The state for a substitute token that replaces an operation when
	 *         the operation is complete
	 */
	private State transitionFromCloseStateTo() {

		/*
		 * Get the new state when transitioning from the last state on the token
		 * deque. Assert that the new state is not null, then return it.
		 */
		final State newState = transitionFromCloseStateTo(getLastState());
		assert (null != newState);
		return newState;
	}
}

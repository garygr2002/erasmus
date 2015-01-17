package com.garygregg.coverity.codingtest.lexical_analysis;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.garygregg.coverity.codingtest.token.CloseToken;
import com.garygregg.coverity.codingtest.token.CommaToken;
import com.garygregg.coverity.codingtest.token.OpenToken;
import com.garygregg.coverity.codingtest.token.Token;

/**
 * Contains a lexical analyzer for the calculator.
 * 
 * @author Gary Gregg
 */
public class LexicalAnalyzer implements ListenerCollection<AnalysisListener> {

	// A non-whitespace regular expression
	private static final Pattern pattern = Pattern.compile(buildSearchRegex());

	// The regular expression for values
	private static final String valueRegex = "(\\+|-)?\\d+";

	// The regular expression for variables
	private static final String variableRegex = "[a-zA-Z_][a-zA-Z0-9_]*";

	/**
	 * Builds the regular expression used to search the input expression.
	 * 
	 * @return The regular expression used to search the input expression
	 */
	private static String buildSearchRegex() {

		// Declare and initialize the close and open token expressions.
		final String closeTokenExpression = CloseToken.getCommonExpression();
		final String openTokenExpression = OpenToken.getCommonExpression();

		// Declare and initialize regex escape and logical 'or' strings.
		final String regexEscape = "\\";
		final String regexOr = "|";

		// Build and return the regular expression to be used for the search.
		return openTokenExpression + valueRegex + closeTokenExpression
				+ regexOr + openTokenExpression + variableRegex
				+ closeTokenExpression + regexOr + openTokenExpression
				+ regexEscape + closeTokenExpression + closeTokenExpression
				+ regexOr + openTokenExpression + regexEscape
				+ CommaToken.getCommonExpression() + closeTokenExpression
				+ regexOr + openTokenExpression + regexEscape
				+ openTokenExpression + closeTokenExpression + regexOr
				+ openTokenExpression + "[\\S]+" + closeTokenExpression;
	}

	// The expression to be used by this lexical analyzer
	private String expression;

	// A dispatcher for key tokens
	private final KeyTokenDispatcher keyTokenDispatcher = new KeyTokenDispatcher();

	// A dispatcher for listeners
	private final ListenerDispatcher listenerDispatcher = new ListenerDispatcher();

	// The pattern matcher for this lexical analyzer
	private Matcher matcher;

	// The dispatcher for unknown tokens
	private final UnknownTokenDispatcher unknownTokenDispatcher = new UnknownTokenDispatcher();

	// The dispatcher for value tokens
	private final ValueTokenDispatcher valueTokenDispatcher = new ValueTokenDispatcher();

	// The dispatcher for variable tokens
	private final VariableTokenDispatcher variableTokenDispatcher = new VariableTokenDispatcher();

	/**
	 * Constructs the lexical analyzer with a default expression
	 */
	public LexicalAnalyzer() {
		setExpression(null);
	}

	/**
	 * Constructs the lexical analyzer with an explicit expression.
	 * 
	 * @param expression
	 *            The expression to be used by this lexical analyzer
	 */
	public LexicalAnalyzer(String expression) {
		setExpression(expression);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.garygregg.coverity.codingtest.lexical_analysis.ListenerCollection
	 * #addListener(java.lang.Object)
	 */
	public boolean addListener(AnalysisListener listener) {

		/*
		 * Add the listener to our own dispatcher listener. Was the add
		 * successful?
		 */
		boolean listenerAdded = listenerDispatcher.addListener(listener);
		if (listenerAdded) {

			/*
			 * Adding the listener to our own dispatcher listener was
			 * successful. Add the listener to the KeyToken dispatcher.
			 */
			keyTokenDispatcher.addListener(listener);
		}

		// Return whether the listener was successfully added.
		return listenerAdded;
	}

	/**
	 * Gets the expression to be used by this lexical analyzer.
	 * 
	 * @return The expression to be used by this lexical analyzer
	 */
	public String getExpression() {
		return expression;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.garygregg.coverity.codingtest.lexical_analysis.ListenerCollection
	 * #getListenerCount()
	 */
	@Override
	public int getListenerCount() {
		return listenerDispatcher.getListenerCount();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.garygregg.coverity.codingtest.lexical_analysis.ListenerCollection
	 * #hasListeners()
	 */
	@Override
	public boolean hasListeners() {
		return listenerDispatcher.hasListeners();
	}

	/**
	 * Launches the lexical analyzer.
	 */
	public void launch() {

		/*
		 * Declare and initialize a token start position, and a variable to
		 * receive token strings.
		 */
		int position = 0;
		String tokenString = null;

		// Dispatch start to each listener. Cycle while tokens exist.
		listenerDispatcher.dispatchStart();
		while ((null != matcher) && matcher.find()) {

			/*
			 * Get the first next token string. Call the key token dispatcher to
			 * dispatch any key tokens. Does the token string not represent a
			 * key token?
			 */
			tokenString = getExpression().substring(position = matcher.start(),
					matcher.end());
			if (!keyTokenDispatcher.dispatchToken(tokenString, position)) {

				/*
				 * The token string does not represent a key token. Does it
				 * represent a variable?
				 */
				if (tokenString.matches(variableRegex)) {

					/*
					 * The token string represents a variable. Set the
					 * expression in the variable token dispatcher, and dispatch
					 * the token to all listeners.
					 */
					variableTokenDispatcher.setExpression(tokenString);
					createTokenAndDispatch(position, variableTokenDispatcher);
				}

				/*
				 * The token string does not represent either a key token nor a
				 * variable token. Does it represent a value token?
				 */
				else if (tokenString.matches(valueRegex)) {

					/*
					 * The token string represents a value token. Set the value
					 * in the value token dispatcher, and dispatch the token to
					 * all listeners.
					 */
					valueTokenDispatcher
							.setValue(Integer.parseInt(tokenString));
					createTokenAndDispatch(position, valueTokenDispatcher);
				}

				// The token string represents no known token.
				else {

					/*
					 * The token string represents no known token. Set the
					 * expression in the unknown token dispatcher, and dispatch
					 * the token to all listeners.
					 */
					unknownTokenDispatcher.setExpression(tokenString);
					createTokenAndDispatch(position, unknownTokenDispatcher);
				}
			}
		}

		// Dispatch stop to each listener.
		listenerDispatcher.dispatchStop();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.garygregg.coverity.codingtest.lexical_analysis.ListenerCollection
	 * #removeListener(java.lang.Object)
	 */
	public boolean removeListener(AnalysisListener listener) {

		/*
		 * Remove the listener from our own dispatcher listener. Was the remove
		 * successful?
		 */
		boolean listenerRemoved = listenerDispatcher.removeListener(listener);
		if (listenerRemoved) {

			/*
			 * Removing the listener from our own dispatcher listener was
			 * successful. Remove the listener to the KeyToken dispatcher.
			 */
			keyTokenDispatcher.removeListener(listener);
		}

		// Return whether the listener was successfully removed.
		return listenerRemoved;
	}

	/**
	 * Resets the lexical analyzer.
	 */
	public void reset() {

		// Reset the matcher if it is not null.
		if (null != matcher) {
			matcher.reset();
		}
	}

	/**
	 * Sets the expression to be used by this lexical analyzer.
	 * 
	 * @param expression
	 *            The expression to be used by this lexical analyzer
	 */
	public void setExpression(String expression) {

		// Set the expression. Clear the matcher if the expression is null.
		if (null == (this.expression = expression)) {
			matcher = null;
		}

		// The expression is not null. Create a new matcher for the expression.
		else {
			matcher = pattern.matcher(this.expression = expression);
		}

		// Reset the analyzer.
		reset();
	}

	/**
	 * Creates a token and dispatches it to the listeners of the analyzer.
	 * 
	 * @param position
	 *            The position to be used for token creation
	 * @param dispatcher
	 *            The token dispatcher to dispatch the token
	 */
	private void createTokenAndDispatch(int position,
			TokenDispatcher<? extends Token> dispatcher) {

		/*
		 * Create the token in the dispatcher, and call all the listeners with
		 * the dispatcher.
		 */
		dispatcher.createToken(position);
		listenerDispatcher.dispatch(dispatcher);
	}
}

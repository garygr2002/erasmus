package com.garygregg.coverity.codingtest.lexical_analysis;

import com.garygregg.coverity.codingtest.token.AddToken;
import com.garygregg.coverity.codingtest.token.CloseToken;
import com.garygregg.coverity.codingtest.token.CommaToken;
import com.garygregg.coverity.codingtest.token.DivideToken;
import com.garygregg.coverity.codingtest.token.LetToken;
import com.garygregg.coverity.codingtest.token.MultiplyToken;
import com.garygregg.coverity.codingtest.token.OpenToken;
import com.garygregg.coverity.codingtest.token.SubtractToken;
import com.garygregg.coverity.codingtest.token.UnknownToken;
import com.garygregg.coverity.codingtest.token.ValueToken;
import com.garygregg.coverity.codingtest.token.VariableToken;

/**
 * Encapsulates a listener of calculator lexical analysis.
 * 
 * @author Gary Gregg
 */
public interface AnalysisListener {

	/**
	 * Receives an add token.
	 * 
	 * @param token
	 *            An add token
	 */
	void receiveAddToken(AddToken token);

	/**
	 * Receives a close token.
	 * 
	 * @param token
	 *            A close token
	 */
	void receiveCloseToken(CloseToken token);

	/**
	 * Receives a comma token.
	 * 
	 * @param token
	 *            A comma token
	 */
	void receiveCommaToken(CommaToken token);

	/**
	 * Receives a divide token.
	 * 
	 * @param token
	 *            A divide token
	 */
	void receiveDivideToken(DivideToken token);

	/**
	 * Receives a let token.
	 * 
	 * @param token
	 *            A let token
	 */
	void receiveLetToken(LetToken token);

	/**
	 * Receives a multiply token.
	 * 
	 * @param token
	 *            A multiply token
	 */
	void receiveMultiplyToken(MultiplyToken token);

	/**
	 * Receives an open token.
	 * 
	 * @param token
	 *            An open token
	 */
	void receiveOpenToken(OpenToken token);

	/**
	 * Receives a subtract token.
	 * 
	 * @param token
	 *            A subtract token
	 */
	void receiveSubtractToken(SubtractToken token);

	/**
	 * Receives an unknown token.
	 * 
	 * @param token
	 *            An unknown token
	 */
	void receiveUnknownToken(UnknownToken token);

	/**
	 * Receives a value token.
	 * 
	 * @param token
	 *            A value token
	 */
	void receiveValueToken(ValueToken token);

	/**
	 * Receives a variable token.
	 * 
	 * @param token
	 *            A variable token
	 */
	void receiveVariableToken(VariableToken token);

	/**
	 * Indicates that lexical analysis is starting.
	 */
	void startAnalysis();

	/**
	 * Indicates that lexical analysis is stopping.
	 */
	void stopAnalysis();
}

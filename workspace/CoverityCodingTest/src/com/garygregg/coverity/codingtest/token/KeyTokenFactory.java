package com.garygregg.coverity.codingtest.token;

/**
 * Encapsulates a factory for key tokens.
 * 
 * @author Gary Gregg
 */
public interface KeyTokenFactory<KeyTokenType extends KeyToken> {

	/**
	 * Creates a key token.
	 * 
	 * @param position
	 *            The position of the token in the input stream
	 * @return A key token with the given position in the input stream
	 */
	KeyTokenType createToken(int position);
}

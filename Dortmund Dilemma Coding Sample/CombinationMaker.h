/*
 * CombinationMaker.h
 *
 *  Created on: Sep 9, 2014
 *      Author: Gary Gregg
 */

#ifndef COMBINATIONMAKER_H_
#define COMBINATIONMAKER_H_

#include "CombinationException.h"
#include "IndexType.h"

/**
 * Returns the number of combinations of 'r' items taken from 'n' items, up to a
 * maximum number for 'n'.
 *
 * @param n The given n
 * @param r The given r
 * @throws CombinationException if either 'n' or 'r' is invalid
 * @return The number of combinations of 'r' items taken from 'n' items
 */
IndexType getCombinations(IndexType n, IndexType r) throw (CombinationException);

#endif /* COMBINATIONMAKER_H_ */

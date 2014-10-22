The Dortmund Dilemma is a coding challenge I discovered at the website Hacker Rank (www.hackerrank.com).  A statement of the problem is included in a Word document included in this folder with the name “Dortmund Dilemma Problem Description.docx”.  The dilemma is a challenge involving combinatorics in two parts: Given a grammar with 'K' symbols, and words of length 'N', find – 1) All words that have a proper prefix equaling a proper suffix in the word, and; 2) All words satisfying the first characteristic should use all the given symbols at least once.

Included in this folder are fourteen C++ code files that solve the dilemma.  The file MainAndTests.cpp contains two routines to test this solution.  The first test routine, “performTests”, performs 17 canned tests, with expected answers supplied.  The second test routine, “solveHackerRank”, can solve an indefinite number of problems by reading them from standard input in the format described in the problem description document.  During performance testing, this solution to the dilemma was able to successfully solve 100,000 instances of the problem in under 2 seconds.  The problem instances used word lengths up to 100,000, and up to 26 symbols.

This solution to the dilemma solves problems in two phases.  In the first phase, the solution builds a table of words of length 1 to 100,000 having the above described prefix/suffix property.  This step is an example of dynamic programming.  The first step only needs to be done once for an indefinite number of problem instances that follow.

In the second phase of the solution, the algorithm reads any number of word lengths and number of symbols required for a problem instance.  It then determines which of the precalculated prefix/suffix word combinations satisfy the requirement to use all the symbols.  The second phase of the problem solution can be done in linear time, O(n) with regard to the number of symbols required.  The efficiency of the second part of the algorithm allows the program to solve a very large number of problem instances very quickly.

This solution also uses a precalculated combinations table, C(n, r), for 'N' and 'R' from zero to 26.  This table is included in the file CombinationMaker.cpp.  The table allows for maximal efficiency for determining combinations in the required range of numbers, as its run-time efficiency is constant time, O(1).

The solution has a built-in area for optimization.  In the dynamic programming step of the solution, the program only needs to calculate a table of words having the prefix/suffix property.  Part of that process involves first calculating the number of words that DO NOT have this property, and then subtracting this number from the total number of possible words.  This solution retains in its table both the total number of words of a given word length, as well as the number of words not having the prefix/suffix property.  This extraneous information may be optimized away for a smaller memory footprint.

I am the sole author of the code and documentation for this solution to the Dortmund Dilemma.  My resume is also included in this folder.  I would greatly enjoy the opportunity to discuss with you how I can solve business oriented applications of similar complexity to this problem, and thereby make a significant contribution to your organization.

Best regards,
Gary C. Gregg


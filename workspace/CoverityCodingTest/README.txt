This jar file contains my solution to the Coverity coding test.  A description of the problem is
contained in the file named 'Java-interview-homework-question.rtf'.  Also included is my C.V.  I
certify that I, Gary C. Gregg, am the sole author of this Java code and all documentation.

This jar file may be run on a command line with the command:

"java -jar CoverityCodingTest.jar Calculator 'mult(100,10)'"

where the argument to the program, 'mult(100,10)' is a representative argument of the calculator
language specified in the problem description.

The program consists of a number of token classes used by a fully-functional lexical analyzer.  The
tokens represent command keywords, punctuation, value and variables that are contained in the
calculator language.  These tokens, and their lexical values are:

Add ('add')
Close (')')
Comma (',')
Div ('div')
Let ('let')
Mult ('mult')
Open ('(')
Sub ('sub')
Value (any integer between Integer.MIN_VALUE and Integer.MAX_VALUE)
Variable (any variable name acceptable to the lexical string '[a-zA-Z_][a-zA-Z0-9_]*')

The program also contains a basic token parser.  The parser accepts any string in the calculator
language, and should correctly identify all syntax errors in the calculator language.  The calculator
language is described in the 'rtf' file, named above.

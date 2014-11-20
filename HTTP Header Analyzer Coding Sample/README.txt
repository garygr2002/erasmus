I have written the HTTP header parsing program to read an entire file into memory before performing
the the analysis of HTTP headers.  The driver of the program is run through 'main', which takes a
filename from the first argument on a command line.  I intended that the following variables and
methods may be linked to externally:

1. The array containing the HTTP header names, as strings
2. A count of the number of HTTP header names
3. The methods 'analyze(const char*),' 'freeStatistics(unsigned*)' and 'report(const unsigned*)'

Linking externally should allow the functionality of the analyzer to be used outside of the driver.

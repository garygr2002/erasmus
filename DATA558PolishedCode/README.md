# DATA558PolishedCode
A repository containing the polished code release assignment for DATA 558, University of Washington, Spring 2017

This file contains code for the Polished Code Release assignment, University of Washington, Spring 2017, as
submitted by Gary C. Gregg.  The work consists of a hand-coded logistic regression algorithm.  The algorithm
can be run either with a simulated data set, or the spam data set from Stanford University.

The simulated data consists of two predictors uniformly distributed from 0 to 100.  The response is calculated
as follows:

1) The difference of the predictors is taken
2) This difference is divided by twenty
3) If the difference is less than -1, use -1
4) If the difference is greater than 1, use 1
5) If the difference is between -1 and 1, then generate a random number between 0 and 1
6) If the random number is greater than the difference, use -1, else use 1

The last two steps would suggest that some misclassification may occur if the separation boundary is
drawn in a line.

Run either simulation as follows.  For spam data:

$ python use_spam.data.py

For simulated data:

$ python use_simulated_data.py

The output for spam data will produce the following graphics files:

1. given_lambda_objective.png
2. optimal_lambda_objective.png
3. test_misclassification.png
4. training_misclassification.png

The output for spam data will also given information regarding iteration counts, and the optimal
lambda value that has been discovered.


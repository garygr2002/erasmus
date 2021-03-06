"""
Contains a demonstration of hand-coded logistic regression versus the
implementation in scikit-learn using the spam data set.
"""
import numpy as np
import sklearn.preprocessing
import pandas as pd

import LogisticRegression


class SpamData(object):
    """
    This class reads the spam data set, and divides it into training and test
    sets.  It separates responses from predictors, and standardizes the
    predictors.
    """

    def __init__(self):
        """
        Constructs a SpamData object.
        """

        # Read the spam data set.
        spam = pd.read_table('https://statweb.stanford.edu/'
                             '~tibs/ElemStatLearn/datasets/spam.data',
                             sep=' ', header=None)

        # Determine which rows are test rows, and which rows are training
        # rows.
        test_indicator = pd.read_table('https://statweb.stanford.edu/~tibs/'
                                       'ElemStatLearn/datasets/spam.traintest', sep=' ',
                                       header=None)

        # Separate the predictors from the responses, and transform the test
        # indicator.
        predictors = np.asarray(spam)[:, 0:-1]
        responses = np.asarray(spam)[:, -1]*2 - 1  # Convert to +/- 1
        test_indicator = np.array(test_indicator).T[0]

        # Divide the data into training and test sets.
        predictors_training = predictors[test_indicator == 0, :]
        predictors_test = predictors[test_indicator == 1, :]
        self.responses_training = responses[test_indicator == 0]
        self.responses_test = responses[test_indicator == 1]

        # Standardize the predictors, both training and test.
        scaler = sklearn.preprocessing.StandardScaler()
        scaler.fit(predictors_training)
        self.predictors_training = scaler.transform(predictors_training)
        self.predictors_test = scaler.transform(predictors_test)

        # Keep track of the number of samples in the training and test sets,
        # and also the number of features.
        self.training_sample_count = len(self.responses_training)
        self.test_sample_count = len(self.responses_test)
        self.feature_count = np.size(predictors, 1)

    def get_feature_count(self):
        """
        Gets the spam data set feature count.
        :return: The spam data set feature count
        """
        return self.feature_count

    def get_test_sample_count(self):
        """
        Gets the number of test samples.
        :return: The number of test samples
        """
        return self.test_sample_count

    def get_test_predictors(self):
        """
        Gets the number of test predictors.
        :return: The number of test predictors
        """
        return self.predictors_test

    def get_test_responses(self):
        """
        Gets the test responses.
        :return: The test responses
        """
        return self.responses_test

    def get_training_predictors(self):
        """
        Gets the training predictors.
        :return: The training predictors
        """
        return self.predictors_training

    def get_training_responses(self):
        """
        Gets the training responses.
        :return: The training responses
        """
        return self.responses_training

    def get_training_sample_count(self):
        """
        Gets the number of training samples.
        :return: The number of training samples
        """
        return self.training_sample_count

# Create a spam data set object, print a start message, and give a
# demonstration of hand-coded logistic regression using the spam
# data set!
DATA_SET = SpamData()
print("Demonstration of hand-coded logistic gradient descent versus "
      "scikit-learn's implementation using the spam data set...")
LogisticRegression.give_demonstration(DATA_SET)

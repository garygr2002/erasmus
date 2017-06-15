"""
Contains a demonstration of hand-coded logistic regression versus the
implementation in scikit-learn using the simulated data set.
"""
import numpy as np
import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler

import LogisticRegression


class SimulatedData(object):
    """
    This class reads the simulated data set, and divides it into training and
    test sets.  It separates responses from predictors, and standardizes the
    predictors.
    """

    def __init__(self):
        """
        Constructs a SimulateData object.
        """

        # Read the simulated data.
        simulated = pd.read_csv("simulated.csv", index_col=0)
        predictors = np.asarray(simulated)[:, 0:-1]
        responses = np.asarray(simulated)[:, -1]

        # Divide the simulated data into training and test sets.
        predictors_training, predictors_test,\
        self.responses_training, self.responses_test =\
            train_test_split(predictors, responses, test_size=0.33)

        # Standardize the predictors, both training and test.
        scaler = StandardScaler()
        scaler.fit(predictors_training)
        self.predictors_training = scaler.transform(predictors_training)
        self.predictors_test = scaler.transform(predictors_test)

        # Keep track of the number of samples in the training and test sets,
        # and also the number of features.
        self.training_sample_count = len(self.responses_training)
        self.test_sample_count = len(self.responses_test)
        self.feature_count = np.size(predictors, 1)
        return None

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

# Create a simulated data set object, print a start message, and give a
# demonstration of hand-coded logistic regression using the simulated
# data set!
DATA_SET = SimulatedData()
print("Demonstration of hand-coded logistic gradient descent versus "
      "scikit-learn's implementation using the simulated data set...")
LogisticRegression.give_demonstration(DATA_SET)

"""
Contains a demonstration of hand-coded logistic regression versus the
implementation in scikit-learn.
"""
from __future__ import division
from __future__ import print_function

import matplotlib.pyplot as plt
import numpy as np
import scipy.linalg
import sklearn.linear_model
import sklearn.preprocessing


class LogisticRegression(object):
    """
    This class performs logistic regression with either the regular gradient
    descent algorithm, or a fast gradient descent algorithm.
    """

    def __init__(self, predictors, responses):
        """
        Initializes the regression.
        :param predictors: The data set predictors
        :param responses: The data set responses
        """

        # Set the predictors and responses in this object.
        self.predictors = predictors
        self.responses = responses

        # Set the verbosity flag, and return.
        self.verbose = False
        return None

    @property
    def am_verbose(self):
        """
        Am I verbose?
        :return: True if I am verbose, false otherwise
        """
        return self.verbose

    def compute_gradient(self, beta, lambdah):
        """
        Computes the gradient function.
        :param beta: The existing set of coefficients
        :param lambdah: The l2 penalization parameter
        :return: The computed gradient
        """

        # Get the predictors and responses.
        predictors = self.get_predictors()
        responses = self.get_responses()

        # Calculate the 'yx' product, and from that the denominator of the
        # calculation that follows.
        yx_product = responses[:, np.newaxis]*predictors
        denominator = 1+np.exp(-yx_product.dot(beta))

        # Calculate the gradient, and return it.
        grad = (1 / len(responses)) *\
               np.sum(-yx_product *
                      np.exp(-yx_product.dot(beta[:, np.newaxis])) /
                      denominator[:, np.newaxis], axis=0) +\
               (2 * lambdah * beta)
        return grad

    def compute_objective(self, beta, lambdah):
        """
        Computes the objective function.
        :param beta: The existing set of coefficients
        :param lambdah: The l2 penalization parameter
        :return: The computed objective
        """

        # Get the predictors and responses.
        predictors = self.get_predictors()
        responses = self.responses

        # Compute and return the objective.
        return (1 / len(responses)) *\
               np.sum(np.log(1 + np.exp(-responses * predictors.dot(beta)))) +\
               lambdah * np.linalg.norm(beta) ** 2

    def create_objective_plot(self, gradient_betas, fast_gradient_betas,
                              lambdah, save_file=''):
        """
        Creates an objective function plot.
        :param gradient_betas: Coefficients from the regular gradient algorithm
        :param fast_gradient_betas: Coefficients from the fast gradient algorithm
        :param lambdah: The l2 penalization parameter
        :param save_file: The optional name of a file to save the plots
        :return: None
        """

        # Ascertain the number of points.  Initialize the gradient and fast
        # gradient objectives.
        number_of_points = np.size(gradient_betas, 0)
        gradient_objectives = np.zeros(number_of_points)
        fast_gradient_objectives = np.zeros(number_of_points)

        # Cycle for each point.
        for i in range(0, number_of_points):

            # Set the first/next gradient objective.
            gradient_objectives[i] =\
                self.compute_objective(gradient_betas[i, :], lambdah)

            # Set the first/next fast gradient objective.
            fast_gradient_objectives[i] =\
                self.compute_objective(fast_gradient_betas[i, :], lambdah)

        # Create the gradient plot.
        _, axes = plt.subplots()
        axes.plot(range(1, number_of_points + 1), gradient_objectives,
                  label='Gradient Descent')

        # Create the fast gradient plot.
        axes.plot(range(1, number_of_points + 1), fast_gradient_objectives,
                  c='red', label='Fast Gradient Descent')

        # Label the axes, and give the plot a title.
        plt.xlabel('Iteration')
        plt.ylabel('Objective value')
        plt.title('Objective Value vs. Iteration when Lambda = '+str(lambdah))

        # Give the plot a legend.  If there is no save file, show the plot...
        axes.legend(loc='upper right')
        if not save_file:
            plt.show()

        # ...otherwise if there is a save file, save the plot.
        else:
            plt.savefig(save_file)
        return None

    def get_predictors(self):
        """
        Gets the predictors set in this regression.
        :return: The predictors set in this regression
        """
        return self.predictors

    def get_responses(self):
        """
        Gets the responses set in this regression.
        :return: The responses set in this regression
        """
        return self.responses


    #pylint: disable=too-many-arguments
    #pylint: disable=too-many-locals
    def perform_fast_gradient(self, beta_init, theta_init, lambdah, eta_init,
                              maximum_iterations):
        """
        Performs a fast gradient descent.
        :param beta_init: The initial beta values
        :param theta_init: The initial theta values
        :param lambdah: The l2 penalization parameter
        :param eta_init: The initial step size
        :param maximum_iterations: The maximum number of iterations to perform
        :return: The finalized beta and theta values
        """

        # Use local copies of the initial beta and theta values.
        beta = beta_init
        theta = theta_init

        # Initialize the gradient theta.  Initialize the list of beta and theta
        # values that will be returned.
        gradient_theta = self.compute_gradient(theta, lambdah)
        beta_values = beta
        theta_values = theta

        # Cycle until maximum iterations are achieved.
        iteration = 0
        while iteration < maximum_iterations:

            # Calculate the step size using line search.  Use that to calculate
            # a new beta value, and a theta value.
            eta = self.perform_line_search(theta, lambdah, eta=eta_init)
            beta_new = theta - eta * gradient_theta
            theta = beta_new + iteration / (iteration + 3) * (beta_new-beta)

            # Store all of the places we step to in the lists that will be
            # returned.
            beta_values = np.vstack((beta_values, beta_new))
            theta_values = np.vstack((theta_values, theta))

            # Compute a new gradient theta, and a new beta.  Increment the
            # number of iterations.
            gradient_theta = self.compute_gradient(theta, lambdah)
            beta = beta_new
            iteration += 1

            # Print a message every so often so that the caller knows that the
            # algorithm has not hung.
            if self.am_verbose and iteration % 100 == 0:
                print('Fast gradient iteration', iteration)

        # Return the accumulated beta and theta lists.
        return beta_values, theta_values

    def perform_gradient_descent(self, beta_init, lambdah, eta_init,
                                 maximum_iterations):
        """
        Performs a regular gradient descent.
        :param beta_init: The initial beta values
        :param lambdah: The l2 penalization parameter
        :param eta_init: The initial step size
        :param maximum_iterations: The maximum number of iterations to perform
        :return: The finalized beta values
        """

        # Use a local copy of the initial beta.  Initialize the gradient beta,
        # and the list of beta values that will be returned.
        beta = beta_init
        gradient_beta = self.compute_gradient(beta, lambdah)
        beta_values = beta

        # Cycle until maximum iterations are achieved.
        iteration = 0
        while iteration < maximum_iterations:

            # Calculate a new step size using line search.  Use that to
            # calculate a new beta value.
            eta = self.perform_line_search(beta, lambdah, eta=eta_init)
            beta = beta - eta * gradient_beta

            # Store all of the places we step to in the list that will be
            # returned.  Compute a new gradient beta.  Increment the number
            # of iterations.
            beta_values = np.vstack((beta_values, beta))
            gradient_beta = self.compute_gradient(beta, lambdah)
            iteration += 1

            # Print a message every so often so that the caller knows that the
            # algorithm has not hung.
            if self.am_verbose and iteration % 100 == 0:
                print('Gradient descent iteration', iteration)

        # Return the accumulated beta list.
        return beta_values

    def perform_line_search(self, beta, lambdah, eta=1, alpha=0.5, betaparam=0.8,
                            maxiter=100):
        """
        Peforms backtracking line search.
        :param beta: The existing set of coefficients
        :param lambdah: The l2 penalization parameter
        :param eta: The step size
        :param alpha: The alpha
        :param betaparam: The beta
        :param maxiter: The maximum number of iterations
        :return: The calculated step size
        """

        # Initialize the gradient beta, the normalized gradient beta, and a
        # flag indicating if an eta has been found.
        gradient_beta = self.compute_gradient(beta, lambdah)
        normalized_gradient_beta = np.linalg.norm(gradient_beta)
        found_eta = 0

        # Cycle until an eta is found, or until maximum iterations have been
        # exceeded.
        iterations = 0
        while found_eta == 0 and iterations < maxiter:

            # Determine if the eta-found criterion has been met.
            if self.compute_objective(beta - eta * gradient_beta, lambdah) <\
                            self.compute_objective(beta, lambdah) -\
                                            alpha * eta * normalized_gradient_beta ** 2:

                # The eta-found criterion has been met.  Set the flag.
                found_eta = 1

            # The maximum number of iterations has been exceeded.
            elif self.am_verbose and iterations == maxiter - 1:
                print('Warning: Max number of iterations of backtracking line search reached')

            # The eta-found criterion has not been met, nor has the maximum
            # number of iterations been exceeded.  Update the eta parameter.
            else:
                eta *= betaparam

            # Increment the number of iterations, and cycle.
            iterations += 1

        # Return the most recent value of eta.
        return eta


def calculate_initial_eta(response_count, predictors, feature_count, lambdah):
    """
    Calculates an initial step size.
    :param response_count: The count of responses
    :param predictors: The predictors matrix
    :param feature_count: The count of features
    :param lambdah: The l2 penalization parameter
    :return: An initial step size
    """
    return 1 / (scipy.linalg.eigh(1 / response_count * predictors.T.dot(predictors),
                                  eigvals=(feature_count - 1, feature_count - 1),
                                  eigvals_only=True)[0] + lambdah)


def compute_misclassification_error(beta_optimal, predictors, responses):
    """
    Computes the misclassification error between actual and predicted values.
    :param beta_optimal: The optimal beta coefficients
    :param predictors: The predictors
    :param responses: The responses
    :return: The misclassification error
    """

    # Calculate the predicted responses and how they deviate from the known
    # responses.  Return the results.
    predicted_responses = 1 / (1 + np.exp(-predictors.dot(beta_optimal))) > 0.5
    predicted_responses = predicted_responses * 2 - 1  # Convert to +/- 1
    return np.mean(predicted_responses != responses)


# pylint: disable=too-many-arguments
def plot_misclassification_error(betas_gradient, betas_fast_gradient,
                                 predictors, responses, save_file='',
                                 title=''):
    """
    Plots misclassification errors.
    :param betas_gradient: The betas for regular gradient descent
    :param betas_fast_gradient: The betas for fast gradient descent
    :param predictors: The problem predictors
    :param responses: The problem response
    :param save_file: An optional save file
    :param title: An optional title of the resulting plot
    :return: None
    """

    # Set the iteration count to the number of stored betas.  Initialize
    # an equal number of error lists for gradient and fast gradient
    # descent.
    iteration_count = np.size(betas_gradient, 0)
    gradient_error = np.zeros(iteration_count)
    fast_gradient_error = np.zeros(iteration_count)

    # Cycle for each stored beta.
    for i in range(iteration_count):

        # Compute the misclassification error for gradient descent.
        gradient_error[i] = compute_misclassification_error(
            betas_gradient[i, :], predictors, responses)

        # Compute the misclassification error for fast gradient descent.
        fast_gradient_error[i] = compute_misclassification_error(
            betas_fast_gradient[i, :], predictors, responses)

    # Create the plots.  First gradient descent...
    _, axes = plt.subplots()
    axes.plot(range(1, iteration_count + 1),
              gradient_error, label='Gradient Descent')

    # ...next fast gradient descent.
    axes.plot(range(1, iteration_count + 1),
              fast_gradient_error, c='red', label='Fast Gradient Descent')

    # Label the plot.
    plt.xlabel('Iteration')
    plt.ylabel('Misclassification error')

    # Give the plot a title, if specified.
    if title:
        plt.title(title)

    # Give the plot a legend.  Show the plot if a save file has not been
    # specified...
    axes.legend(loc='upper right')
    if not save_file:
        plt.show()

    # ...otherwise save the plot instead of showing it.
    else:
        plt.savefig(save_file)
    return None


# pylint: disable=too-many-locals
def give_demonstration(data_set):
    """
    Demonstrates logistic gradient descent.
    :param data_set: The data set of interest
    :return: None
    """

    # The data set object is presumed to have the following methods defined.
    # Get all the specifics of the data.
    feature_count = data_set.get_feature_count()
    n_train = data_set.get_training_sample_count()
    x_test = data_set.get_test_predictors()
    x_train = data_set.get_training_predictors()
    y_test = data_set.get_test_responses()
    y_train = data_set.get_training_responses()

    # Set the initial lambda.  Set the maximum number of iterations for
    # gradient and fast gradient.
    lambdah = 0.1
    maximum_iterations = 300

    # Set common parameters for scikit-learn.
    sklearn_tol = 10e-8
    sklearn_fit_intercept = False
    sklearn_max_iter = 1000
    sklearn_penalty = 'l2'

    # Give beta, and theta initial values.
    beta_init = np.zeros(feature_count)
    theta_init = np.zeros(feature_count)

    # Give eta an initial value.  Create a logistic regression object.
    eta_init = calculate_initial_eta(len(y_train), x_train, feature_count,
                                     lambdah)
    logistic_regression = LogisticRegression(x_train, y_train)

    # Perform gradient descent.
    betas_gradient = logistic_regression.perform_gradient_descent(
        beta_init, lambdah, eta_init, maximum_iterations)

    # Perform fast gradient descent.
    betas_fast_gradient, _ = logistic_regression.perform_fast_gradient(
        beta_init, theta_init, lambdah, eta_init, maximum_iterations)

    # Create objective plots for gradient descent, and fast gradient descent.
    logistic_regression.create_objective_plot(
        betas_gradient, betas_fast_gradient, lambdah,
        save_file='given_lambda_objective.png')

    # Create a scikit-learn model to solve the same problem.
    sklearn_logistic_regression = sklearn.linear_model.LogisticRegression(
        penalty=sklearn_penalty, C=1 / (2 * lambdah * n_train), fit_intercept=False,
        tol=sklearn_tol, max_iter=sklearn_max_iter)

    # Fit the scikit-learn model.  Print coefficients from this.
    sklearn_logistic_regression.fit(x_train, y_train)
    print('Coefficients from scikit-learn: ' +
          str(sklearn_logistic_regression.coef_))

    # Print the coefficients from fast-gradient descent.  The output should be
    # the same as above.
    print('Coefficients from hand-coded logistic gradient descent: ' +
          str(betas_fast_gradient[-1, :]))

    # Print the value of the objective function from scikit-learn.
    print('Value of objective function from scikit-learn: ' +
          str(logistic_regression.compute_objective(
              sklearn_logistic_regression.coef_.flatten(), lambdah)))

    # Print the value of the objective function from fast gradient descent.
    print('Value of objective function from hand-coded gradient descent: ' +
          str(logistic_regression.compute_objective(
              betas_fast_gradient[-1, :], lambdah)))

    # Now let scikit-learn find the optimal lambda.
    lr_cv = sklearn.linear_model.LogisticRegressionCV(
        penalty=sklearn_penalty, fit_intercept=sklearn_fit_intercept,
        tol=sklearn_tol, max_iter=sklearn_max_iter)
    lr_cv.fit(x_train, y_train)

    # Retrieve and print the optimal lambda.
    optimal_lambda = lr_cv.C_[0]
    print('Optimal lambda=', optimal_lambda)

    # Perform gradient descent with the best lambda.
    betas_gradient = logistic_regression.perform_gradient_descent(
        beta_init, optimal_lambda, eta_init, maximum_iterations)

    # Perform fast gradient descent with the best lambda.
    betas_fast_gradient, _ = logistic_regression.perform_fast_gradient(
        beta_init, theta_init, optimal_lambda, eta_init,
        maximum_iterations)

    # Create objective plots for gradient and fast gradient descent when used
    # with the optimal lambda.
    logistic_regression.create_objective_plot(
        betas_gradient, betas_fast_gradient, optimal_lambda,
        save_file='optimal_lambda_objective.png')

    # Plot training misclassification errors for gradient descent.
    plot_misclassification_error(betas_gradient, betas_fast_gradient, x_train, y_train,
                                 save_file='training_misclassification.png',
                                 title='Training set misclassification error')

    # Plot test misclassification errors for fast gradient descent.
    plot_misclassification_error(betas_gradient, betas_fast_gradient, x_test, y_test,
                                 save_file='test_misclassification.png',
                                 title='Test set misclassification error')
    return None

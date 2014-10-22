package com.garygregg;

/**
 * Contains an augmented binary tree that also has a middle node. The middle
 * node stores duplicates of the value at the current node.
 * 
 * @author Gary C. Gregg
 */
public class TrinaryTree<ValueType extends Comparable<ValueType>> {

	/**
	 * Determines if a trinary tree is empty.
	 * 
	 * @param tree
	 *            The tree to examine
	 * @return True if the tree is empty, false otherwise
	 */
	public static boolean isEmpty(TrinaryTree<?> tree) {
		return (null == tree) || (null == tree.value);
	}

	/**
	 * Runs a series of tests on the trinary tree.
	 * 
	 * @param args
	 *            Command line arguments (unused)
	 */
	public static void main(String[] args) {

		/*
		 * Create the tree with an initial element, then insert the remainder of
		 * the elements.
		 */
		TrinaryTree<Integer> tree = new TrinaryTree<Integer>(5);
		tree.insert(4);
		tree.insert(9);
		tree.insert(5);
		tree.insert(7);
		tree.insert(2);
		tree.insert(2);

		// Run a series of tests. The output should be true in all cases.
		System.out.println(false == tree.delete(100));
		System.out.println(true == tree.delete(7));
		System.out.println(false == tree.delete(7));
		System.out.println(true == tree.delete(5));
		System.out.println(true == tree.delete(5));
		System.out.println(false == tree.delete(5));
		System.out.println(true == tree.delete(2));
		System.out.println(true == tree.delete(2));
		System.out.println(false == tree.delete(2));
		System.out.println(true == tree.delete(9));
		System.out.println(false == tree.delete(9));
		System.out.println(false == isEmpty(tree));
		System.out.println(true == tree.delete(4));
		System.out.println(false == tree.delete(4));
		System.out.println(true == isEmpty(tree));

		// Add more elements.
		tree.insert(100);
		tree.insert(105);
		tree.insert(106);
		tree.insert(102);
		tree.insert(100);
		tree.insert(101);
		tree.insert(101);
		tree.insert(107);
		tree.insert(110);

		// Run more tests.
		System.out.println(true == tree.delete(106));
		System.out.println(true == tree.delete(101));
		System.out.println(true == tree.delete(110));
		System.out.println(true == tree.delete(100));
		System.out.println(true == tree.delete(102));
		System.out.println(true == tree.delete(101));
		System.out.println(true == tree.delete(107));
		System.out.println(true == tree.delete(100));
		System.out.println(true == tree.delete(105));
		System.out.println(true == isEmpty(tree));

		// Run even more tests.
		TrinaryTree<Long> longTrinaryTree = new TrinaryTree<Long>();
		final int count = 100;
		for (int i = 0; i < count; ++i) {

			/*
			 * Insert a random long integer between zero and ten thousand into
			 * the tree.
			 */
			longTrinaryTree.insert(Math.round(Math.random() * 10000));
		}

		// Print the numbers in sorted order.
		System.out.print("\n");
		int line = 1;
		while (!isEmpty(longTrinaryTree)) {

			// Print the output.
			System.out.println((line++) + ". "
					+ longTrinaryTree.deleteSmallest());
		}

		// Now try to print integers in reverse order.
		for (int i = 0; i < count; ++i) {

			/*
			 * Insert a random long integer between zero and twenty into the
			 * tree (this will result in a lot of duplicates, and therefore
			 * a lot of middle subtree action).
			 */
			longTrinaryTree.insert(Math.round(Math.random() * 20));
		}

		// Print the numbers in reverse sorted order.
		System.out.print("\n");
		line = 1;
		while (!isEmpty(longTrinaryTree)) {

			// Print the output.
			System.out.println((line++) + ". "
					+ longTrinaryTree.deleteLargest());
		}
	}

	/**
	 * Performs a comparison that intercepts the definition of null for the
	 * given type.
	 * 
	 * @param first
	 *            The first element to compare
	 * @param second
	 *            The second element to compare
	 * @return The result of the comparison
	 */
	private static <T> int compare(Comparable<T> first, T second) {

		return (null == first) ? ((null == second) ? 0 : -1)
				: (null == second) ? 1 : first.compareTo(second);
	}

	// The value contained in the current node.
	private ValueType value;

	// The left subtree.
	private TrinaryTree<ValueType> left;

	// The middle subtree.
	private TrinaryTree<ValueType> middle;

	// The right subtree.
	private TrinaryTree<ValueType> right;

	/**
	 * Constructs a null trinary tree.
	 */
	public TrinaryTree() {
		this(null);
	}

	/**
	 * Constructs the trinary tree with an explicit first node.
	 * 
	 * @param value
	 *            The explicit first node
	 */
	public TrinaryTree(ValueType value) {
		this.value = value;
	}

	/**
	 * Delete a value from the tree.
	 * 
	 * @param value
	 *            The value to delete
	 * @return True if the value was found, and deleted; false otherwise
	 */
	public boolean delete(ValueType value) {

		/*
		 * Initialize the return value, and compare the value at this node to
		 * the given value. Is the given value less than the value at this node?
		 */
		boolean found = false;
		final int comparison = compare(this.value, value);
		if (0 < comparison) {

			/*
			 * The given value is less than the value at this node. Is the left
			 * subtree not empty?
			 */
			if (!isEmpty(left)) {

				/*
				 * The left subtree is not empty. Try to delete the given
				 * element from the left subtree. Is the left subtree now empty?
				 */
				found = left.delete(value);
				if (isEmpty(left)) {

					/*
					 * The left subtree is now empty. Explicitly release the
					 * reference.
					 */
					left = null;
				}
			}
		}

		// Is the given value equal to the value at this node?
		else if (0 == comparison) {

			/*
			 * The given value is equal to the value at this node. No matter
			 * what, we have found the element to delete. However, try to delete
			 * the node in the middle subtree that is farthest from the current
			 * node. Is the middle subtree not empty?
			 */
			found = true;
			if (!isEmpty(middle)) {

				/*
				 * The middle subtree is not empty. Delete the value from the
				 * middle subtree. Is the middle subtree now empty?
				 */
				middle.delete(value);
				if (isEmpty(middle)) {

					/*
					 * The middle subtree is now empty. Explicitly release the
					 * reference.
					 */
					middle = null;
				}
			}

			/*
			 * The middle subtree is empty. An attempt must be made to replace
			 * the value at the current node with the largest value in the left
			 * subtree, or the smallest value in the right subtree. Is the left
			 * subtree not empty?
			 */
			else if (!isEmpty(left)) {

				/*
				 * The left subtree is not empty. Delete the largest value from
				 * the left subtree. Is the left subtree now empty?
				 */
				this.value = left.deleteLargest();
				if (isEmpty(left)) {

					/*
					 * The left subtree is now empty. Explicitly release the
					 * reference.
					 */
					left = null;
				}
			}

			// The left subtree is empty. Is the right subtree not empty?
			else if (!isEmpty(right)) {

				/*
				 * The right subtree is not empty. Delete the smallest value
				 * from the right subtree. Is the right subtree now empty?
				 */
				this.value = right.deleteSmallest();
				if (isEmpty(right)) {

					/*
					 * The right subtree is now empty. Explicitly release the
					 * reference.
					 */
					right = null;
				}
			}

			/*
			 * Both the left and right subtrees are empty. Explicitly release
			 * this value, thereby emptying the subtree.
			 */
			else {
				this.value = null;
			}
		}

		/*
		 * The given value is greater than the value at this node. Is the right
		 * subtree not empty?
		 */
		else if (!isEmpty(right)) {

			/*
			 * The right subtree is not empty. Try to delete the given element
			 * from the right subtree. Is the right subtree now empty?
			 */
			found = right.delete(value);
			if (isEmpty(right)) {

				/*
				 * The right subtree is now empty. Explicitly release the
				 * reference.
				 */
				right = null;
			}
		}

		// Return true if the value was found, and deleted; false otherwise.
		return found;
	}

	/**
	 * Inserts a value into the tree.
	 * 
	 * @param value
	 */
	public void insert(ValueType value) {

		// Is this node empty?
		if (isEmpty(this)) {

			// This node is empty. Just add the value this node, and return.
			this.value = value;
			return;
		}

		/*
		 * This node is not null. Compare the value at this node to the given
		 * value. Is the given value less than the value at this node?
		 */
		final int comparison = compare(this.value, value);
		if (0 < comparison) {

			/*
			 * The given value is less than the value at this node. Create a new
			 * left subtree for this node if the left subtree is empty...
			 */
			if (isEmpty(left)) {
				left = new TrinaryTree<ValueType>(value);
			}

			// ...otherwise just insert the given value in the left subtree.
			else {
				left.insert(value);
			}
		}

		// Is the given value equal to the value at this node?
		else if (0 == comparison) {

			/*
			 * The given value is equal to the value at this node. Create a new
			 * middle subtree for this node if the middle subtree is empty...
			 */
			if (isEmpty(middle)) {
				middle = new TrinaryTree<ValueType>(value);
			}

			// ...otherwise just insert the given value in the middle subtree.
			else {
				middle.insert(value);
			}
		}

		/*
		 * The given value is greater than the value at this node. Create a new
		 * right subtree for this node if the right subtree is empty...
		 */
		else if (isEmpty(right)) {
			right = new TrinaryTree<ValueType>(value);
		}

		// ...otherwise just insert the given value in the right subtree.
		else {
			right.insert(value);
		}
	}

	/**
	 * Deletes the largest value in a tree, and returns the value.
	 * 
	 * @return The largest value in the tree
	 */
	private ValueType deleteLargest() {

		/*
		 * Declare and initialize the return value. Is the right subtree not
		 * empty?
		 */
		ValueType largest = null;
		if (!isEmpty(right)) {

			/*
			 * The right subtree is not empty. The largest element will come
			 * from that tree.
			 */
			largest = right.deleteLargest();
		}

		// The right subtree is empty.
		else {

			/*
			 * Explicitly release the right subtree. Is the middle subtree not
			 * empty?
			 */
			right = null;
			if (!isEmpty(middle)) {

				/*
				 * The middle subtree is not empty. The largest element will
				 * come from that tree.
				 */
				largest = middle.deleteLargest();
			}

			// The middle subtree is empty.
			else {

				/*
				 * Explicitly release the middle subtree. Set the return value
				 * to the value at this node. If the left subtree is empty,
				 * remove the reference to the value at this node. Otherwise
				 * replace it with the largest node from the left subtree.
				 */
				middle = null;
				largest = value;
				value = isEmpty(left) ? null : left.deleteLargest();
			}
		}

		// Return the deleted value.
		return largest;
	}

	/**
	 * Deletes the smallest value in a tree, and returns the value.
	 * 
	 * @return The smallest value in the tree
	 */
	private ValueType deleteSmallest() {

		/*
		 * Declare and initialize the return value. Is the left subtree not
		 * empty?
		 */
		ValueType smallest = null;
		if (!isEmpty(left)) {

			/*
			 * The left subtree is not empty. The smallest element will come
			 * from that tree.
			 */
			smallest = left.deleteSmallest();
		}

		// The left subtree is empty.
		else {

			/*
			 * Explicitly release the left subtree. Is the middle subtree not
			 * empty?
			 */
			left = null;
			if (!isEmpty(middle)) {

				/*
				 * The middle subtree is not empty. The smallest element will
				 * come from that tree.
				 */
				smallest = middle.deleteLargest();
			}

			// The middle subtree is empty.
			else {

				/*
				 * Explicitly release the middle subtree. Set the return value
				 * to the value at this node. If the right subtree is empty,
				 * remove the reference to the value at this node. Otherwise
				 * replace it with the smallest node from the right subtree.
				 */
				middle = null;
				smallest = value;
				value = isEmpty(right) ? null : right.deleteSmallest();
			}
		}

		// Return the deleted value.
		return smallest;
	}
}

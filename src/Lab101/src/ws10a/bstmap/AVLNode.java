package Lab101.src.ws10a.bstmap;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

// WS10A - AVLNode is being used as the basis for the BSTMap
// it provides the underlying self-balancing tree functionality
// It is an adaptation of BSTNode from WS9B / Private study 9
//
public class AVLNode<E extends Comparable<E>> {
	public AVLNode<E> parent; // The parent node
	public AVLNode<E> left;
	public AVLNode<E> right;
	public E element; // the actual data the node stores
	private Comparator<E> comp; // Ex 9B Advanced - the comparator

	public AVLNode(E value) {
		this(value, null);
	}

	public AVLNode(E value, Comparator<E> comp) {
		this.element = value;
		this.comp = comp;
	}

	// Private Study 9:- Calculates the balance value for a given
	// node
	public int balanceVal() {
		int leftHeight = 0;
		if (this.left != null)
			leftHeight = this.left.height();

		int rightHeight = 0;
		if (this.right != null)
			rightHeight = this.right.height();

		return leftHeight - rightHeight;
	}

	// Private Study 9:- Rotates the specified node to the left
	public AVLNode<E> rotateToLeft(AVLNode<E> node) {
		AVLNode<E> rightChild = node.right;
		node.right = rightChild.left;
		rightChild.left = node;
		return rightChild;
	}

	// Private Study 9:- Rotates the specified node to the right
	public AVLNode<E> rotateToRight(AVLNode<E> node) {
		AVLNode<E> leftChild = node.left;
		node.left = leftChild.right;
		leftChild.right = node;
		return leftChild;
	}

	// Private Study 9:- Balances the specified node and its subtree if required
	public AVLNode<E> balanceCheck(AVLNode<E> node) {
		int balance = node.balanceVal();
		// left subtree longer by 2
		if (balance == 2) {
			// this is a Left-Right case
			if (node.left.balanceVal() == -1)
				node.left = rotateToLeft(node.left);
			// this is a Left-Left case
			node = rotateToRight(node);
		}
		// right subtree longer by 2
		else if (balance == -2) {
			// this is a Right-Left case
			if (node.right.balanceVal() == 1)
				node.right = rotateToRight(node.right);
			// this is a Right-Right case
			node = rotateToLeft(node);
		}
		return node;
	}

	private int nodeCompare(E key, AVLNode<E> node) {
		int cmp = 0;
		if (comp != null)
			cmp = comp.compare(key, node.element);
		else
			cmp = key.compareTo(node.element);
		return cmp;

	}

	// a convenient method for debugging
	public String toString() {
		return "(" + super.toString() + "), " + element + " L:" + (left != null) + ", R:" + (right != null);
	}

	public AVLNode<E> search(E key) {
		// Using the comparable functionality to do the comparison
		int cmp = nodeCompare(key, this);
		if (cmp == 0)
			return this;
		else if (cmp < 0 && this.left != null)
			return this.left.search(key);
		else if (cmp > 0 && this.right != null)
			return this.right.search(key);
		return null;
	}

	public AVLNode<E> insert(E key) {
		return insert(this, key);
	}

	public AVLNode<E> insert(AVLNode<E> node, E key) {
		if (node == null)
			return new AVLNode<E>(key);

		int cmp = nodeCompare(key, this);
		if (cmp == 0)
			return this;
		else if (cmp < 0) {
			node.left = insert(node.left, key);
			// Private Study 9:- Addition of balance check after insert
			node = balanceCheck(node);

			if (node.left != null)
				node.left.parent = node;
		} else if (cmp > 0) {
			node.right = insert(node.right, key);
			// Private Study 9:- Addition of balance check after insert
			node = balanceCheck(node);

			if (node.right != null)
				node.right.parent = node;
		}
		return node;
	}

	public AVLNode<E> delete(E key) {
		int cmp = nodeCompare(key, this);
		if (cmp < 0) {
			if (this.left != null)
				this.left = this.left.delete(key);
			// Private Study 9: Add Balance Check to deletion
			return balanceCheck(this);
		} else if (cmp > 0) {
			if (this.right != null)
				this.right = this.right.delete(key);
			// Private Study 9: Add Balance Check to deletion
			return balanceCheck(this);
		} else {
			if (this.right == null)
				return this.left;
			if (this.left == null)
				return this.right;
			// if node has both children
			AVLNode<E> x = this.right.min(); // find min in r subtree
			x.right = this.right.deleteMin(); // delete min in r subtree
			x.left = this.left; // copy l subtree "as-is"

			// Private Study 9: Add Balance Check to deletion
			return balanceCheck(x);
		}
	}

	private AVLNode<E> min() {
		if (this.left == null)
			return this;
		else
			return this.left.min();
	}

	private AVLNode<E> deleteMin() {
		if (this.left == null)
			return this.right;
		this.left = this.left.deleteMin();
		return this;
	}

	public AVLNode<E> simpleDelete(E key) {
		int cmp = nodeCompare(key, this);
		if (cmp < 0) {
			if (this.left != null)
				this.left = this.left.simpleDelete(key);
		} else if (cmp > 0) {
			if (this.right != null)
				this.right = this.right.simpleDelete(key);
		} else {
			if (this.right == null)
				return this.left;
			if (this.left == null)
				return this.right;
		}
		return this;
	}

	// Here are all the measuring methods from WS8 and WS9, adapted, where
	// necessary for an AVLNode

	public void preOrderTraversePrint() {

		System.out.println(this.element);
		if (left != null)
			left.preOrderTraversePrint();
		if (right != null)
			right.preOrderTraversePrint();

	}

	public void postOrderTraversePrint() {
		if (left != null)
			left.postOrderTraversePrint();
		if (right != null)
			right.postOrderTraversePrint();

		System.out.println(this.element);

	}

	public void inOrderTraversePrint() {
		if (left != null)
			left.inOrderTraversePrint();

		System.out.println(this.element);

		if (right != null)
			right.inOrderTraversePrint();
	}

	public void eulerTraversePrint() {
		System.out.print("L:" + this.element + ", ");
		if (left != null)
			left.eulerTraversePrint();

		System.out.print("B:" + this.element + ", ");

		if (right != null)
			right.eulerTraversePrint();
		System.out.print("R:" + this.element + ", ");
	}

	public void levelOrderTraversePrint() {
		Queue<AVLNode<E>> q = new LinkedList<AVLNode<E>>();
		q.offer(this);
		while (!q.isEmpty()) {
			AVLNode<E> node = q.poll();
			System.out.println("Node: " + node.element);

			if (node.left != null)
				q.offer(node.left);
			if (node.right != null)
				q.offer(node.right);
		}
	}

	public int size() {
		int size = 1;
		if (left != null)
			size += left.size();
		if (right != null)
			size += right.size();
		return size;
	}

	public int depth() {
		// At the moment, this method just returns 0
		// It should be rewritten so that it returns the depth of this node
		// (that is, the number of ancestors this node has)
		if (this.parent != null)
			return this.parent.depth() + 1;
		return 0;
	}

	public int height() {
		// At the moment, this method just returns 0
		// It should be rewritten so that it returns the height of this
		// (sub)tree
		// (that is, the maximum "depth" in this subtree)

		int max = 0;
		if (left != null) {
			int currentHeight = left.height() + 1;
			if (currentHeight > max)
				max = currentHeight;
		}
		if (right != null) {
			int currentHeight = right.height() + 1;
			if (currentHeight > max)
				max = currentHeight;
		}
		return max;
	}

	// Returns all of the data associated with each node, in order
	public Vector<E> inOrderTraverse() {
		Vector<E> inOrder = new Vector<E>();
		if (left != null)
			inOrder.addAll(left.inOrderTraverse());

		inOrder.add(this.element);

		if (right != null)
			inOrder.addAll(right.inOrderTraverse());

		return inOrder;
	}

}

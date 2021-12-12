package CW2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BST<E extends Comparable<E>> {
	public BST<E> parent;
	public BST<E> left;
	public BST<E> right;
	public E element;
	public List<E> elements;
	private Comparator<E> comp;

	public BST(E value, Comparator<E> comp) {
		this.element = value;
		this.comp = comp;
	}

	private int nodeCompare(E key, BST<E> node) {
		int cmp = 0;
		if (comp != null)
			cmp = comp.compare(key, node.element);
		else
			cmp = key.compareTo(node.element);
		return cmp;
	}

	public BST<E> insertdups(E key) {
		return insertdups(this, key);
	}

	public BST<E> insertdups(BST<E> node, E key) {
		if (node == null)
			return new BST<E>(key, comp);
		int cmp = nodeCompare(key, node);
		if (cmp == 0) {
			if (node.elements == null) {
				node.elements = new ArrayList<>();
			}
			node.elements.add(key);
		} else if (cmp < 0) {
			node.left = insertdups(node.left, key);
			if (node.left != null)
				node.left.parent = node;
		} else if (cmp > 0) {
			node.right = insertdups(node.right, key);
			if (node.right != null)
				node.right.parent = node;
		}
		return node;
	}

	public List<E> inOrder() {
		return inOrderTraverse(null);
	}

	public List<E> inOrderTraverse(List<E> list) {
		if (list == null)
			list = new ArrayList<E>();
		if (left != null)
			left.inOrderTraverse(list);
		list.add(this.element);
		if (this.elements != null)
			list.addAll(this.elements);
		if (right != null)
			right.inOrderTraverse(list);
	return list;
	}

}
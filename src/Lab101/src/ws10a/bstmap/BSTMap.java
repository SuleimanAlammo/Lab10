package Lab101.src.ws10a.bstmap;

import java.util.Vector;

// This is a BST (Binary Search Tree) implementation of Map
public class BSTMap <K extends Comparable<K>, V> {
	
	// the underlying AVL tree implementation
	private AVLNode<KeyValue<K,V>> bstMap;
	

	// searches the map for a key/value pair matching key
	// used by most of the other methods
	public KeyValue<K,V> search(K key)
	{
		if (bstMap != null)
		{
			// make a dummy keyvalue pair to search for the matching key
			KeyValue<K,V> searcher = new KeyValue<K, V>(key, null);
			AVLNode<KeyValue<K,V>> node  = bstMap.search(searcher);
			if (node != null)
				return node.element;
		}
		return null;
	}

	// puts the specified value into the map against the specified key
	// returns the previous value mapped against the key, if one existed
	public void put(K key, V value)
	{
		KeyValue<K,V> pair = search(key);
		if (pair != null)
			pair.value = value;
		else 
		{
			pair = new KeyValue<K,V>(key, value);
			if (bstMap!= null)
				bstMap = bstMap.insert(pair);
			else
				bstMap = new AVLNode<KeyValue<K, V>>(pair);
		}
	}

	// gets the value matching the key 
	// returns null if no key match was found
	public V get(K key)
	{
		KeyValue<K, V> newKeyValue = search(key);
		if (newKeyValue != null)
			return newKeyValue.value;
		else
			return null;
	}

	// removes the key from the map, returning the existing 
	// value if it existed
	// returns null if the key was not found
	public V remove (K key)
	{
		KeyValue<K, V> newKeyValue = search(key);
		if (newKeyValue != null) {
			bstMap = bstMap.delete(newKeyValue);
			return newKeyValue.value;
		} else
			return null;
	}

	// returns the size of the map
	public int size() {
		if (bstMap == null)
			return 0;
		return bstMap.size();
	}

	// returns true if empty
	public boolean isEmpty() {
		return size() == 0;
	}

	// returns a set of the keys present in this map
	public Iterable<K> keys() {
		Vector<K> keys = new Vector<K>();
		
		for (KeyValue<K,V> pair : bstMap.inOrderTraverse())
			keys.add(pair.key);
		return keys;
	}
	
}

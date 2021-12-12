package Lab101.src.ws10a.listmap;

import java.util.Vector;

// This is a Vector (dynamic array) implementation of Map
public class VectorMap<K extends Comparable<K>, V> {

	public boolean debug = true;
	private Vector<KeyValue<K, V>> mapList;

	// creates the vectormap
	public VectorMap() {
		mapList = new Vector<KeyValue<K, V>>();
	}

	// searches the map for a key/value pair matching key
	// used by most of the other methods
	public KeyValue<K, V> search(K key) {
		// WS10A - Intermediate - print out search time
		int searchcnt = 0;
		// WS10A - Basic search the map for a matching key
		for (KeyValue<K, V> keyValue : mapList) {
			searchcnt++;
			if (keyValue.key.equals(key)) {
				// WS10A - Intermediate - print out search time
				if (debug)
					System.out.println("Found in " + searchcnt + " searches ");
				return keyValue;
			}
		}
		// WS10A - Intermediate - print out search time
		if (debug)
			System.out.println("Not found; took " + searchcnt + " searches");
		return null;
	}

	// puts the specified value into the map against the specified key
	// returns the previous value mapped against the key, if one existed
	public V put(K key, V value) {
		// search map for the key
		KeyValue<K, V> newKeyValue = search(key);
		V oldValue = null;

		// if found, replace the key/value pair's value
		if (newKeyValue != null) {
			oldValue = newKeyValue.value;
			newKeyValue.value = value;
		} else // if key was not found, create a new one and add it
		{
			newKeyValue = new KeyValue<K, V>(key, value);
			mapList.addElement(newKeyValue);
		}
		return oldValue;
	}

	// gets the value matching the key
	// returns null if no key match was found
	public V get(K key) {
		KeyValue<K, V> newKeyValue = search(key);
		if (newKeyValue != null)
			return newKeyValue.value;
		else
			return null;
	}

	// removes the key from the map, returning the existing
	// value if it existed
	// returns null if the key was not found
	public V remove(K key) {
		KeyValue<K, V> newKeyValue = search(key);
		if (newKeyValue != null) {
			mapList.remove(newKeyValue);
			return newKeyValue.value;
		} else
			return null;
	}

	// returns the size of the map
	public int size() {
		return mapList.size();
	}

	// returns true if empty
	public boolean isEmpty() {
		return size() == 0;
	}

	public Iterable<K> keys() {
		Vector<K> keys = new Vector<K>();
		for (KeyValue<K, V> pair : mapList)
			keys.add(pair.key);
		return keys;
	}

}

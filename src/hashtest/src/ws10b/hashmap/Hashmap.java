package hashtest.src.ws10b.hashmap;

import java.util.Vector;

//This is a Hashmap implementation of Map
public class Hashmap<K extends Comparable<K>, V> {

	// the buckets that hold the key/value pairs
	private KeyValue<K, V>[] buckets;
	// keeps track of how many entries are held in this map
	private int occupied;

	public Hashmap() {
		createBuckets(10); // initialise this map with 40 empty buckets
	}
	
	// create a bucket store of the specified size
	// this also resets the occupied counter
	@SuppressWarnings("unchecked")
	public void createBuckets(int size)
	{
		this.buckets = (KeyValue<K, V>[]) new KeyValue[size];
		occupied = 0;
	}

	// calculates a hashcode for the specified key
	public int hash(K key) {
		// this is our simple way:
		return key.hashCode();
	}

	// compresses the hashcode to the bounds of the underlying
	// bucket store
	public int compress(int hash) {
		return hash & (buckets.length - 1);
	}

	// searches the map for a key/value pair matching key
	// used by most of the other methods
	public KeyValue<K, V> search(K key) {
		int index = hash(key);
		index = compress(index);

		KeyValue<K, V> match = buckets[index];
		// if this bucket doesn't have what we're looking for,
		// go on to see if anything else is chained on to the bucket
		// (SEPARATE CHAINING collision resolution)
		
		// this is another way of doing the same thing
		//K theKey = match.key; //inside loop too
		//while (match != null && !theKey.equals(key)) {
		
		while (match != null && !match.key.equals(key)) {
			// move to next chain link
			match = match.next;
		}
		return match;
	}

	// puts the specified value into the map against the specified key
	// returns the previous value mapped against the key, if one existed
	public void put(K key, V value) {
		// locate the key/value pair, if stored in the map
		KeyValue<K, V> pair = search(key);
		// if found, just replace the value
		if (pair != null)
			pair.value = value;
		else {
			pair = new KeyValue<K, V>(key, value);

			int index = hash(key);
			index = compress(index);

			// see if something is already hashed here:
			KeyValue<K, V> collision = buckets[index];
			if (collision != null) { // if so...
				int colcount = 1;
				// move to the last collision stored in this bucket
				while (collision.next != null) {
					collision = collision.next;
					colcount++;
				}

				// WS10B : Intermediate - measure the performance
				System.out.println("Bucket " + index + " (" + colcount
						+ " collisions), for " + key + ", " + value
						+ " - with " + collision.key);

				// read to end of other entries in this bucket, then place new
				// one at end of list
				collision.next = pair;
			} else
				// if no collision, just place it where the hashcode suggests
				buckets[index] = pair;
			occupied++;
		}

		// WS10B - Advanced - Add resizing / rehashing functionality
		// rehash if the load factor increases over .75 (i.e. more than 3/4
		// full)
		if (loadFactor() > 0.75d)
			rehash(buckets.length * 2);
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
		KeyValue<K, V> removingKeyValue = search(key);
		if (removingKeyValue != null) {

			// get index position
			int index = hash(key);
			index = compress(index);

			// The removal is slightly more complex
			// as we might encounter a linked list 
			// (SEPARATE CHAINING Collision resolution)
			
			KeyValue<K, V> e = buckets[index];
			KeyValue<K, V> prev = e;
			while (e != null) {
				KeyValue<K, V> next = e.next;
				// if we've found the right key
				if (e.key.equals(key)) {
					if (prev == e) // if this is the first entry
						buckets[index] = next;
					else
						prev.next = next;
				}
				// go to next separately chained entry
				prev = e;
				e = next;
			}
			occupied--;

			// return the old value
			return removingKeyValue.value;
		} else
			return null;
	}

	// returns the size of the map
	public int size() {
		return occupied;
	}

	// returns true if empty
	public boolean isEmpty() {
		return size() == 0;
	}

	// returns a set of the keys present in this map
	public Iterable<K> keys() {
		Vector<K> keys = new Vector<K>();

		for (KeyValue<K, V> pair : buckets)
			while (pair != null) {
				keys.add(pair.key);
				pair = pair.next;
			}
		return keys;
	}

	// WS10B : Advanced - Resize/rehash - required to determine when to rehash
	// give an indication of how "loaded" this hashmap is
	public double loadFactor() {
		return (double) size() / buckets.length;
	}

	// WS10B : Advanced - Resize/rehash - rehashes the map to a new size
	// resize and rehash the underlying store
	public void rehash(int newSize) {
		System.out.println("***---REHASHING to " + newSize);

		// backup all entries
		Vector<KeyValue<K, V>> keys = new Vector<KeyValue<K, V>>();
		for (KeyValue<K, V> pair : buckets)
			while (pair != null) {
				keys.add(pair);
				pair = pair.next;
			}

		// make a new bucket store at the specified size
		createBuckets(newSize);
		// copy everything back using the put operation
		// to rehash every entry
		for (KeyValue<K, V> pair : keys)
			this.put(pair.key, pair.value);
	}

}

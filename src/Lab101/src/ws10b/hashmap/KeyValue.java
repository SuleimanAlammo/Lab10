package Lab101.src.ws10b.hashmap;

// This class represents the key / value pair for HashMap
// Uses Java Generics to allow programmers to choose the datatype for the key and the value
public class KeyValue<K extends Comparable<K>, V> 
						implements Comparable<KeyValue<K, V>> {
	public K key;
	public V value;
	// SEPARATE CHAINING Collision Resolution
	public KeyValue<K, V> next; // next pointer for collisions

	public KeyValue(K key, V value) {
		this.key = key;
		this.value = value;
	}

	public int compareTo(KeyValue<K,V> comp) {
		return key.compareTo(comp.key);
	}
	
	public int hashCode() {
		return key.hashCode();
	}
}
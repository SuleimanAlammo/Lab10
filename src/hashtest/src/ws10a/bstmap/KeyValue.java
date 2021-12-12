package ws10a.bstmap;

// This class represents the key / value pair for BSTMap
// Uses Java Generics to allow programmers to choose the datatype for the key and the value
public class KeyValue<K extends Comparable<K>, V> 
 implements Comparable<KeyValue<K,V>> {
	public K key;
	public V value;

	public KeyValue(K key, V value) {
		this.key = key;
		this.value = value;
	}

	public int compareTo(KeyValue<K, V> comp) {
		return key.compareTo(comp.key);
	}
}
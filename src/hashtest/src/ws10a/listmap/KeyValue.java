package ws10a.listmap;
// This class represents the key / value pair for VectorMap
// Uses Java Generics to allow programmers to choose the datatype for the key and the value
public class KeyValue<K, V> 
 {
	public K key;
	public V value;

	public KeyValue(K key, V value) {
		this.key = key;
		this.value = value;
	}
}
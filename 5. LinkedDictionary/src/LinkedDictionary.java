import java.util.Iterator;

/**
 *
 * @author Andrew Stipcak
 */
public class LinkedDictionary<K,V> implements Dictionary<K,V> {

    private class Node {
        private K key;
        private V value;
        private Node next;
        
        public Node(K key, V value, Node next){
            this.key = key;
            this.value = value;
            this.next = next;
        }
        
        public Node getNext() {
            return next;
        }
        
        public void setNext(Node next) {
            this.next = next;
        }
        
        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
        
    }
    
    private Node head;
    private int size;
    
    public LinkedDictionary() {
        head = null;
        size = 0;
    }

    @Override
    public List<K> keys() {
        List<K> k = new AList<>();
        Node current = head;
        for(int i=0; i<size; i++) {
            k.add(current.getKey());
            current = current.getNext();
        }
        return k;
    }

    @Override
    public List<V> values() {
        List<V> v = new AList<>();
        Node current = head;
        for(int i=0; i<size; i++) {
            v.add(current.getValue());
            current = current.getNext();
        }
        return v;
    }

    @Override
    public V get(Object key) {
        if(getNodeContaining((K)key) == null)
            return null;
        return getNodeContaining((K)key).getValue();
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public V put(K key, V value) {
        if(isEmpty()) {
            Node n = new Node(key, value, null);
            n.setNext(n);
            head = n;
            size++;
            return null;
	} else {
            Node n = getNodeContaining(key);
            if(n == null) {
		n = new Node(key,value,head.getNext());
		head.setNext(n);
		size++;
		return null;
            } else {
		V oldValue = n.getValue();
		n.setValue(value);
		return oldValue;
            }
	}
    }

    @Override
    public V remove(Object key) {
        Node n = getNodeBefore((K) key);
	if (n != null) {
            if(n.getNext().equals(head)) {
             	head = n;
            }
            V oldValue = n.getNext().getValue();
            n.setNext(n.getNext().getNext());
            if(--size == 0) {
		head = null;
            }
            return oldValue;
	}
	return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(K key) {
        return getNodeContaining(key) != null;
    }
    
    private Node getNodeBefore(K key) {
	Node previous = head;
	Node current = head.getNext();
	for(int i=0; i<size; i++) {
            if(current.getKey().equals(key)) {
		return previous;
            }
            previous = current;
            current = current.getNext();
	}
	return null;
    }
    
    private Node getNodeContaining(K key) {
	Node current = head.getNext();
	for(int i=0; i<size; i++) {
            if(current.getKey().equals(key)) {
		return current;
            }
            current = current.getNext();
	}
	return null;        
    }
    
}



3    
0    
2    
123    
1    
123 
Hank 
Chigla     
2     
123     
1     
567 
Tom 
Smith     
1
789 
Barnabas 
Ansy     
2     
789     
1     
111 
Joe 
Burns     
1     
111 
Vicktor 
Bosco     
1    
13X3 
Bob 
Cat     
2    
13X3      
1    
334 
Yaris 
Maggle    
1    
A1167 
Bert 
Sase    
1    
D223 
Bernadette 
Giggly    
1    
F4 
Anita 
Raise    
1    
6KJ  
Superfly 
Snuka    
2    
6KJ    
1    
aaa 
bbb 
ccc    
1    
ZZZ 
ZZZ 
ZZZ    
3    
1    
0
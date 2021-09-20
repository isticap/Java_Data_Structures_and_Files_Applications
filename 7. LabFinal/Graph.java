import java.util.Iterator;

/**
 *
 * @author Andrew Stipcak
 */
 
public interface Graph <T> {
	public int size();
	public void clear();
	public boolean contains(T obj);
	public void addNode(T obj);
	public Node<T> getNode(T obj);
	public boolean addEdgeNode(Node n1, Node n2);
	public Node<T> removeNode(T obj);
	public boolean removeNode(Node<T> n);
	public Iterator<T> dftIterator(T startpoint);
	public Iterator<T> bftIterator(T startpoint);
	public boolean connected();
}
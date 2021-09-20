import java.util.Iterator;

/**
 *
 * @author Andrew Stipcak
 */
 
public class DirGraph<T> implements Graph<T> {
	private Object[] Nodes;
	private static int DEFAULT_CAPACITY = 25;
	private int capacity;
	private int size;
	private boolean [][] adjacentMatrix;
	
	public DirGraph() {
		this(DEFAULT_CAPACITY);
	}
	
	public DirGraph(int initialCapacity) {
		this.capacity = initialCapacity;
		nodes = new Object[capacity];
		adjacentMatrix = new boolean[capacity][capacity];
	}
	
	@Override
	public int size() {
		return size;
	}
	
	@Override
	public void clear() {
		size = 0;
		adjacentMatrix = new boolean[capacity][capacity];
		nodes = new Object[capacity];
	}
	
	@Override
	public boolean contains(T obj) {
		for (int i=0; i<size; i++) {
			if (obj == ((Node)nodes[i]).getValue()) {
				return true;
			}
			if (obj != null && obj.equals(((Node)nodes[i]).getValue())) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void addNode(T obj) {
		if (size == capacity) {
			throw new IllegalStateException("Full Capacity");
		}
		Node<T> n = new Node<>(obj);
		nodes[size] = n;
		size++;
	}
	
	@Override
	public Node<T> getNode(T obj) {
		for (int i=0; i<size; i++) {
			if(obj == ((Node)nodes[i]).getValue()) {
				return (Node<T>)nodes[i];
			}
			if(obj != null && obj.equals(((Node)nodes[i]).getValue())) {
				return (Node<T>nodes[i]);
			}
		}
		return null;
	}
	
	@Override
	public boolean addEdgeNode(Node n1, Node n2) {
		int indexn1 = getIndexOfNode(n1);
		int indexn2 = getIndexOfNode(n2);
		if (indexn1 < 0 || indexn2 < 0) {
			return false;
		}
		adjacentMatrix[indexn1][indexn2] = true;
		adjacentMatrix[indexn2][indexn1] = true;
		return true;
	}
	
	private int getIndexOfNode(Node n) {
		for (int i=0; i<size; i++) {
			if (n == nodes[i]) {
				return i;
			}
		}
		return -1;
	}
	
	@Override
	public Node<T> removeNode(T obj) {
		Node<T> n = getNode(obj);
		if (n != null) {
			removeNode(n);
		}
		return n;
	}
	
	@Override
	public boolean removeNode(Node<T> n) {
		int index = getIndexOfNode(node);
		if (index < 0) {
			return false;
		}
		nodes[index] = nodes[--size];
		adjacentMatrix[index] = adjacentMatrix[size];
		for (int i=0; i<size; i++) {
			adjacentMatrix[i][index] = adjacentMatrix[i][size];
			adjacentMatrix[i][size] = false;
			adjacentMatrix[size][i] = false;
		}
		return true;
	}
	
	public Iterator<T> dftIterator(T startpoint) {
		Stack<Integer> stack = new VectorStack<>(15);
		List<T> vStack = new AList<>();
		
		Node<T> start = getNode(startpoint);
		start.setVisited(true);
		//push vertex index in stack
		stack.push(getIndexOfNode(start));
		vStack.add(start.getValue());
		
		while(!stack.empty()) {
			//get the unvisited vertex of vertex which is at top of the stack
			int unvisitedVertex = getAdjUnvisitedVertex(stack.peek());
			
			//no adjacent vertex found
			if(unvisitedVertex == -1) {
				stack.pop();
			} else {
				vStack.add(((Node<T>)nodes[unvisitedVertex]).getValue());
				((Node<T>)nodes[unvisitedVertex]).setVisited(true);
				stack.push(unvisitedVertex);
			}
		}
		
		//stack is empty, search is complete, reset the visited flag 
		for (int i=0; i<size; i++) {
			((Node<T>)nodes[i]).setVisited(false);
		}
		return vStack.iterator();
	}
	
	private int getAdjUnvisitedVertex(int vertexIndex) {
		for (int i=0; i<size; i++) {
			if (adjacentMatrix[vertexIndex][i] == true && ((Node<T>)nodes[i]).isVisited() == false) {
				return i;
			}
		}
		return -1;
	}
	
	public Iterator<T> bftIterator(T startpoint) {
		List<Integer> queue = new AList<>();
		List<T> vQueue = new AList<>();
		
		Node<T> start = getNode(startpoint);
		start.setVisited(true);
		
		queue.add(getIndexOfNode(start));
		vQueue.add(start.getValue());
		
		int unvisitedVertex;
		
		while(!queue.isEmpty()) {
			int temp = queue.remove(0);
			while ((unvisitedVertex = getAdjUnvisitedVertex(temp)) != -1) {
				vQueue.add(((Node<T>)nodes[unvisitedVertex]).getValue());
				((Node<T>)nodes[unvisitedVertex]).setVisited(true);
				queue.add(unvisitedVertex);
			}
		}
		
		for (int i=0; i<size; i++) {
			((Node<T>)nodes[i]).setVisited(false);
		}
		return vQueue.iterator();
	}
	
	@Override
	public boolean connected() {
		T startpoint = ((Node<T>)nodes[0]).getValue();
		int ck1, ck2;
		ck1 = ck2 = 0;
		Iterator<T> DFT = dftIterator(startpoint);
		while(DFT.hasNext()) {
			DFT.next();
			ck1++;
		}
		Iterator<T> BFT = bftIterator(startpoint);
		while(BFT.hasNext()) {
			BFT.next();
			ck2++;
		}
		return (size == ck1 && size == ck2);
	}
	
}

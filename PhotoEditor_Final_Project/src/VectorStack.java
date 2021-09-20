import java.util.EmptyStackException;
import java.util.Vector;

public class VectorStack<T> implements Stack<T> {
    private Vector<T> stack;
    private int topIndex;
    private static final int DEFAULT_CAPACITY=15;

    public VectorStack(int initialCapacity) {
        stack = new Vector<T>(initialCapacity);
        topIndex = -1;
    }

    public VectorStack() {
        this(DEFAULT_CAPACITY);
    }
    /**
     * Adds something to the top of the stack.
     *
     * @param obj - the object to be added.
     */
    @java.lang.Override
    public T push(T obj) {
        topIndex++;
        stack.add(topIndex, obj);
        return obj;
    }

    /**
     * Returns a refernce to the top of the stack.
     * Does not modify the stack.
     *
     * @return a reference to the top of the stack.
     */
    @java.lang.Override
    public T peek() {
        if (empty())
            throw new EmptyStackException();
        return stack.get(topIndex);
    }

    /**
     * Removes the top element from the stack.
     *
     * @return a reference to the element that was on the top of the stack.
     */
    @java.lang.Override
    public T pop() {
        if (empty())
            throw new EmptyStackException();
        T ret = stack.get(topIndex);
        topIndex--;
        return ret;
    }

    /**
     * Determines if the stack is empty.
     *
     * @return true if the stack is empty.
     */
    @java.lang.Override
    public boolean empty() {
        return topIndex < 0;
    }
}

import java.io.PrintStream;
import java.util.NoSuchElementException;

// Generic type data can be used for every function

/**
 * Defines the methods for a Double-ended Queue that handles T items
 * @param <T> the type of elements in the queue (Can take any type of data)
 */

public interface StringDoubleEndedQueue<T> {
	/**
	 * @return true if the queue is empty
	 */
	public boolean isEmpty();

	/**
	 * insert a T item at the front of the queue
	 */
	public void addFirst(T item);

	/**
	 * remove and return the item at the front of the queue
	 * @return T from the front of the queue
	 * @throws NoSuchElementException if the queue is empty
	 */
	public T removeFirst() throws NoSuchElementException;

	/**
	 * insert a T item at the end of the queue
	 */
	public void addLast(T item);

	/**
	 * remove and return the item at the end of the queue
	 * @return T from the end of the queue
	 * @throws NoSuchElementException if the queue is empty
	 */
	public T removeLast() throws NoSuchElementException;
	
	/**
	 * return without removing the item at the front of the queue
	 * @return T from the front of the queue
	 * @throws NoSuchElementException if the queue is empty
	 */
	public T getFirst();

	/**
	 * return without removing the item at the end of the queue
	 * @return T from the end of the queue
	 * @throws NoSuchElementException if the queue is empty
	 */
	public T getLast();
	
	
	/**
	 * print the T items of the queue, starting from the front, 
     	 * to the print stream given as argument. For example, to 
         * print the elements to the
	 * standard output, pass System.out as parameter. E.g.,
	 * printQueue(System.out);
	 */
	public void printQueue(PrintStream stream);

	/**
	 * return the size of the queue, 0 if empty
	 * @return number of elements in the queue
	 */
	public int size();
}

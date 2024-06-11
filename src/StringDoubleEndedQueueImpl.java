import java.util.NoSuchElementException;
import java.io.PrintStream;

public class StringDoubleEndedQueueImpl<T> implements StringDoubleEndedQueue<T> {

    private Node<T> head = null;
    private Node<T> tail = null;
    private int size = 0; // Insantiation of list size
    
    public StringDoubleEndedQueueImpl() {

    }

    // Check if list is Empty, act accordingly
    @Override
    public boolean isEmpty() {
        return head == null;
    }

    // Getter methods  
    public T getFirst() throws NoSuchElementException {
        if (isEmpty()) { 
            throw new NoSuchElementException();
        } else {
            T item = head.getItem();
            return item;
        }
    }

    public T getLast() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException();
        } else {
            T item = tail.getItem();
            return item;
        }
    }

    // Insert data in front of the list
    @Override
    public void addFirst(T item) {
        Node<T> n = new Node<>(item);

        if (isEmpty()) {
            head = n;
            tail = n;
        } else {
            n.setNext(head);
            head = n;
        }

        size++;
    }

    // Insert data in the end of the list
    @Override
    public void addLast(T item) {
        Node<T> n = new Node<>(item);

        if (isEmpty()) {
            head = n;
            tail = n;
        } else {
            tail.setNext(n);
            tail = n;
        }

        size++;
    }

    // Remove item from front
    @Override
    public T removeFirst() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        T item = head.getItem();

        if (head == tail) {
            head = tail = null;
        } else {
            head = head.getNext();
        }
        
        size--;
        return item;
    }

    // Returns and removes the data from the end of the list
    @Override
    public T removeLast() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        T item = tail.getItem();

        if (head == tail) {
            head = tail = null;
        } else {
            Node<T> iterator = head;
            while (iterator.getNext() != tail) {
                iterator = iterator.getNext();
            }

            iterator.setNext(null);
            tail = iterator;
        }

        size--;
        return item;
    }

    // Returns the size of the list, with time complexity of O(1)
    public int size() {
        return size;
    }

    // Print the T items of the queue, starting from the front
    // System.out argument to be passed in the main function
    @Override
    public void printQueue(PrintStream stream) {
        Node<T> iterator = head;

        while (iterator != null) {
            stream.print(iterator.getItem() + " ");
            iterator = iterator.getNext();
        }

        stream.println();
    }
}
public class Node<T> {
    protected T item;
    protected Node<T> next = null;

    // Constructor method
    Node(T item) {
        this.item = item;
    }

    public T getItem() {
        return item;
    }

    // Getter method
    public Node<T> getNext() {
        return next;
    }

    // Setter method
    public void setNext(Node<T> next) {
        this.next = next;
    }
}

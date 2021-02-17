package deque;
public interface Deque<T> {

    void addFirst(T item);

    void addLast(T item);

    void printDeque();

    int size();

    T get(int index);

    T removeFirst();

    T removeLast();

    default boolean isEmpty() {
        return this.size() == 0;
    }

}

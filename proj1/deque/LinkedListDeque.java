package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {
        private static class Node<T> {
            private T item;
            private Node prev;
            private Node next;

            private Node() {
                item = null;
                prev = null;
                next = null;
            }

            private Node(T t, Node p, Node n) {
                item = t;
                prev = p;
                next = n;
            }

            private T get() {
                return item;
            }
        }

        private int size;
        private Node sentinel;



        public void addFirst(T item) {
            sentinel.next = new Node(item, sentinel, sentinel.next);
            sentinel.next.next.prev = sentinel.next;
            size += 1;
        }
        public void addLast(T item) {
            sentinel.prev = new Node(item, sentinel.prev, sentinel);
            sentinel.prev.prev.next = sentinel.prev;
            size += 1;
        }
//        public boolean isEmpty() {
//            if (size > 0) {
//                return false;
//            }
//            return true;
//        }

        public int size() {
            return size;
        }

        public void printDeque() {
            Node dequePrint = sentinel.next;
            while (dequePrint != sentinel) {
                System.out.print(dequePrint.item + " ");
                dequePrint = dequePrint.next;
            }
            System.out.println();
        }
        public T removeFirst() {
            if (isEmpty()) {
                return null;
            }
            T first = (T) sentinel.next.item;
            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
            size -= 1;
            return first;
        }
        public T removeLast() {
            if (isEmpty()) {
                return null;
            }
            T last = (T) sentinel.prev.item;
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next = sentinel;
            size -= 1;
            return last;
        }
        public T get(int index) {
            if (index > size) {
                return null;
            }
            Node getItem = sentinel.next;
            while (index > 0) {
                getItem = getItem.next;
                index -= 1;
            }
            return (T) getItem.item;
        }

        public LinkedListDeque() {
            sentinel = new Node(null, null, null);
            sentinel.prev = sentinel;
            sentinel.next = sentinel;
            size = 0;
        }
        public T getRecursive(int index) {
            return getRecursiveHelper(index, sentinel.next);
        }

        private T getRecursiveHelper(int index, Node recur) {
            if (index == 0) {
                return (T) recur.get();
            } else {
                return getRecursiveHelper(index - 1, recur.next);
            }
        }


    private class iteratorHelper implements Iterator<T> {
        private int index = 0;

        public boolean hasNext() {
            return index < size();
        }

        public T next() {
            if (hasNext() == true) {
                T result = get(index);
                index += 1;
                return result;
            }
            return null;
        }
    }

    public Iterator<T> iterator() {

        return new iteratorHelper();
    }

    public boolean equals(Object o) {
        if (o instanceof Deque) {
            Deque<T> compare = (Deque<T>) o;
            if (compare.size() != size()) {
                return false;
            }

            for (int i = 0; i < size(); i++) {
                if (!compare.get(i).equals(get(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }








}

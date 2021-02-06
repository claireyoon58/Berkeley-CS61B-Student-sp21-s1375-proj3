package deque;

public class ArrayDeque<T> {
    private int size;
    private int newFirst;
    private int newLast;
    private T[] items;

    public ArrayDeque() {
        this.items = (T[]) new Object[8];
        this.size = 0;
        this.newFirst = 0;
        this.newLast = 1;
    }

    public void addFirst(T item) {
        if (this.items.length == this.size) {
            this.arraysize(this.size * 2);
        }
        this.items[this.newFirst] = item;
        this.newFirst = decreaseindex(this.newFirst);
        this.size += 1;
    }

    public void addLast(T item) {
        if (this.items.length == this.size) {
            this.arraysize((this.size * 2));
        }
        this.items[this.newLast] = item;
        this.newLast = increaseindex(this.newLast);
        this.size += 1;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return this.size;
    }

    private void arraysize(int arraysize) {
        T[] newsize = (T[]) new Object[arraysize];
        for (int indexi = 0; indexi < this.size; indexi++) {
            newsize[indexi] = this.get(indexi);
        }

        this.items = newsize;
        this.newFirst = decreaseindex(0);
        this.newLast = this.size;
    }


    private int decreaseindex(int n) {
        if (n == 0) {
            return this.items.length - 1;
        }
        return n - 1;
    }

    private int increaseindex(int n) {
        if (n == this.items.length - 1) {
            return 0;
        }
        return n + 1;
    }

    public void printDeque() {
        for (int indexi = 0; indexi < this.size; indexi++) {
            System.out.print(this.get(indexi) + " ");
        }
        System.out.println();
    }
    public T removeFirst() {
        if (this.size == 0) {
            return null;
        }

        int firstIndex = (this.newFirst + 1) % this.items.length;
        T firsti = this.items[firstIndex];
        this.items[firstIndex] = null;
        this.newFirst = firstIndex;

        this.size -= 1;
        this.usageratio();

        return firsti;
    }
    private void usageratio() {
        double ratio = (double) this.size / this.items.length;

        if (this.items.length > 16 && 0.25 > ratio) {
            this.arraysize(this.items.length / 2);
        }
    }

    public T removeLast() {
        if (this.size == 0) {
            return null;
        }
        int lasti = decreaseindex(this.newLast);
        T lastItem = this.items[lasti];
        this.items[lasti] = null;
        this.newLast = lasti;
        this.size -= 1;
        this.usageratio();

        return lastItem;
    }

    public T get(int index) {
        int getitem = (newFirst + 1 + index) %  this.items.length;
        T x = items[getitem];
        return x;
    }
}

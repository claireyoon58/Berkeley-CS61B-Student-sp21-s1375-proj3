package deque;
import java.util.*;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> x;

    public MaxArrayDeque(Comparator<T> x) {
        this.x = x;
    }

    public T max() {
        if (size() == 0) {
            return null;
        }
        return max(x);
    }


    public T max(Comparator<T> c) {
        if (size() == 0) {
            return null;
        }
        int maxNum = 0;

        for (int i = 0; i < size(); i++) {
            if (c.compare(get(i), get(maxNum)) == 1) {
                maxNum = i;

            }
        }

        return get(maxNum);

    }






}


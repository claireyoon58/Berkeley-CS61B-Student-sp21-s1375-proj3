package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
   @Test
   public void testThreeAddThreeRemove() {
       AListNoResizing<Integer> L = new AListNoResizing<>();
       BuggyAList<Integer> broken = new BuggyAList<>();

       L.addLast(4);
       L.addLast(5);
       L.addLast(6);
       broken.addLast(4);
       broken.addLast(5);
       broken.addLast(6);

       assertEquals(L.size(), broken.size());

       assertEquals(L.removeLast(), broken.removeLast());
       assertEquals(L.removeLast(), broken.removeLast());
       assertEquals(L.removeLast(), broken.removeLast());

   }

    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> broken = new BuggyAList<>();

        int N = 500;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                broken.addLast(randVal);
                System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                L.getLast();
                L.removeLast();
                System.out.println("size: " + size);
            }
        }
    }





}

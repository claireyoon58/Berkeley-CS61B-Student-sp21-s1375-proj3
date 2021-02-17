package tester;
import static org.junit.Assert.*;
import org.junit.Test;
import java.util.*;
import edu.princeton.cs.introcs.StdRandom;
import java.util.Random;
import java.util.ArrayList;
import student.StudentArrayDeque;

public class TestArrayDequeEC {

    @Test
    public void TestStudentArray() {
        StudentArrayDeque<Integer> a = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> b = new ArrayDequeSolution<>();

        List methods = new ArrayList();

        for (int i = 0; i < 10000; i ++) {
            int randomNum = StdRandom.uniform(100);
            a.addFirst(randomNum);
            b.addFirst(randomNum);
            methods.add("addFirst(" + randomNum + ")");
        }
        for (int i = 0; i < 10000; i++) {
            int expected = a.get(i);
            int actual = b.get(i);
            assertEquals("Error Expected" + methods, expected, actual);
        }
//        addlast


        for (int i = 0; i < 10000; i ++) {
            int randomNum = StdRandom.uniform(100);
            a.addLast(randomNum);
            b.addLast(randomNum);
            methods.add("addLast(" + randomNum + ")");
        }

        for (int i = 0; i < 10000; i++) {
            int expected = a.get(i);
            int actual = b.get(i);
            assertEquals("Error Expected" + methods, expected, actual);
        }

//        removeFirst

        for (int i = 0; i < 10000; i ++) {
            int randomNum = StdRandom.uniform(100);
            a.removeFirst();
            b.removeFirst();
            methods.add("removeFirst()");
        }
        for (int i = 0; i < 10000; i++) {
            int expected = a.get(i);
            int actual = b.get(i);
            assertEquals("Error Expected" + methods, expected, actual);
        }


//        removeLast

        for (int i = 0; i < 10000; i ++) {
            int randomNum = StdRandom.uniform(100);
            a.removeLast();
            b.removeLast();
        }
        for (int i = 0; i < 10000; i++) {
            int expected = a.get(i);
            int actual = b.get(i);
            assertEquals("Error Expected" + methods, expected, actual);
        }



    }
}

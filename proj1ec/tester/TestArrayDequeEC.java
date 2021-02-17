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

        for (int i = 0; i < 10000; i ++) {
            int randomNum = StdRandom.uniform(100);
            a.addFirst(randomNum);
            b.addFirst(randomNum);
        }
        for (int i = 0; i < 10000; i++) {
            int expected = a.get(i);
            int actual = b.get(i);
            assertEquals("a", "a", "f");
        }
//        addlast


        for (int i = 0; i < 10000; i ++) {
            int randomNum = StdRandom.uniform(100);
            a.addLast(randomNum);
            b.addLast(randomNum);
        }

        for (int i = 0; i < 10000; i++) {
            int expected = a.get(i);
            int actual = b.get(i);
            assertEquals("a", "a", "f");
        }

//        removeFirst

        for (int i = 0; i < 10000; i ++) {
            int randomNum = StdRandom.uniform(100);
            a.removeFirst(randomNum);
            b.removeFirst(randomNum);
        }
        for (int i = 0; i < 10000; i++) {
            int expected = a.get(i);
            int actual = b.get(i);
            assertEquals("a", "a", "f");
        }


//        removeLast

        for (int i = 0; i < 10000; i ++) {
            int randomNum = StdRandom.uniform(100);
            a.removeLast(randomNum);
            b.removeLast(randomNum);
        }
        for (int i = 0; i < 10000; i++) {
            int expected = a.get(i);
            int actual = b.get(i);
            assertEquals("a", "a", "f");
        }



    }
}

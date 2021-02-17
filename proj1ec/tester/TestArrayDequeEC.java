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

//        List methods = new ArrayList();

        for (int i = 0; i < 100; i++) {
            int methodcall = StdRandom.uniform(0, 4);
//            int randomNum = StdRandom.uniform(1000);

            if (methodcall == 0) {
                a.addFirst(i);
                b.addFirst(i);
                int expected = a.get(0);
                int actual = b.get(0);
                assertEquals(expected, actual);
                System.out.println("addFirst(" + i + ")");
            } else if (methodcall == 1) {
                a.addLast(i);
                b.addLast(i);
                int expected = a.get(a.size() - 1);
                int actual = b.get(b.size() - 1);
                assertEquals(expected, actual);
                System.out.println("addLast(" + i + ")");
            } else if (methodcall == 2) {
                if (a.size() != 0 && b.size() != 0) {
                    int expected = a.removeFirst();
                    int actual = b.removeFirst();
                    assertEquals(expected, actual);
                    System.out.println("removeFirst()");
                }
            } else if (methodcall == 3) {
                if (a.size() != 0 && b.size() != 0) {
                    int expected = a.removeLast();
                    int actual = b.removeLast();
                    assertEquals(expected, actual);
                    System.out.println("removeLast");
                }
            }
        }
    }
}




//        for (int i = 0; i < 10000; i++) {
//            int expected = a.get(i);
//            int actual = b.get(i);
//            assertEquals(expected, actual);
//            System.out.println("addFirst(" + i + ")");
//        }
//        addlast


//        for (int i = 0; i < 10000; i ++) {
//            if (a.size() != 0 && b.size() != 0) {
//                int randomNum = StdRandom.uniform(100);
//                a.addLast(randomNum);
//                b.addLast(randomNum);
//                ethods.add("addLast(" + randomNum + ")")m;


//        for (int i = 0; i < 10000; i++) {
//            int expected = a.get(i);
//            int actual = b.get(i);
//            assertEquals(expected, actual);
//            System.out.println("addLast(" + i + ")");
//        }

//        removeFirst

//        for (int i = 0; i < 10000; i ++) {
//            if (a.size() != 0 && b.size() != 0) {
//                int randomNum = StdRandom.uniform(100);
//                a.removeFirst();
//                b.removeFirst();
//                methods.add("removeFirst()");

//        for (int i = 0; i < 10000; i++) {
//            int expected = a.get(i);
//            int actual = b.get(i);
//            assertEquals(expected, actual);
//            System.out.println("removeFirst(" + i + ")");
//        }


//        removeLast

//        for (int i = 0; i < 10000; i ++) {
//            if (a.size() != 0 && b.size() != 0) {
//                int randomNum = StdRandom.uniform(100);
//                a.removeLast();
//                b.removeLast();
//            }
//        for (int i = 0; i < 10000; i++) {
//            int expected = a.get(i);
//            int actual = b.get(i);
//            assertEquals(expected, actual );
//            System.out.println("removeLast(" + i + ")");

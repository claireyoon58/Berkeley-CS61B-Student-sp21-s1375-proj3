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
        ArrayDequeSolution<Integer> a = new ArrayDequeSolution<>();
        StudentArrayDeque<Integer> b = new StudentArrayDeque<>();


        for (int i = 0; i < 10; i++) {
            int random = StdRandom.uniform(100);
            a.addFirst(random);
            b.addFirst(random);
            int actual = a.get(0);
            int expected = b.get(0);
            assertEquals("addFirst(" + random + ")", actual, expected);
            System.out.println("addFirst(" + random + ")");

            random = StdRandom.uniform(100);
            a.addLast(random);
            b.addLast(random);
            actual = a.get(1);
            expected = b.get(1);
            assertEquals("addLast(" + random + ")", actual, expected);
            System.out.println("addLast(" + random + ")");

            actual = a.removeFirst();
            expected = b.removeFirst();
            assertEquals("removeFirst()", actual, expected);
            System.out.println("removeFirst()");

            actual = a.removeLast();
            expected = b.removeLast();
            assertEquals("removeLast()", actual, expected);
            System.out.println("removeLast()");
        }
    }
}

//for (int i = 0; i < 10; i++) {
////            int methodcall = StdRandom.uniform(0, 4);
//        int randomNum = StdRandom.uniform(1000);
//
////            if (methodcall == 0) {
//        a.addFirst(randomNum);
//        b.addFirst(randomNum);
//        }
////                int expected = ;
////                int actual = b.get(0);
//
//        for (int i = 0; i < 10; i++) {
//        int expected = a.get(i);
//        int actual = b.get(i);
//
//        assertEquals(expected, actual);
//        System.out.println("addFirst(" + i + ")");
//        }
//        for (int i = 0; i < 10; i++) {
//        int randomNum = StdRandom.uniform(1000);
//        a.addLast(randomNum);
//        b.addLast(randomNum);
//        }
//
//        for (int i = 0; i < 10; i++) {
//        int expected = a.get(i);
//        int actual = b.get(i);
//        assertEquals(expected, actual);
//        System.out.println("addLast(" + i + ")");
//        }
//
//        List<Integer> actualL = new ArrayList<>();
//        List<Integer> expectedL = new ArrayList<>();
//
//        for (int i = 0; i < 10; i++) {
//        actualL.add(a.removeFirst());
//        expectedL.add(b.removeFirst());
//        }
//
//        for (int i = 0; i < 10; i++) {
//        int expected = a.get(i);
//        int actual = b.get(i);
//        assertEquals(expected, actual);
//        System.out.println("removeFirst()");
//        }
//
//        List<Integer> actualL2 = new ArrayList<>();
//        List<Integer> expectedL2 = new ArrayList<>();
//
//        for (int i = 0; i < 10; i++) {
//        actualL2.add(a.removeLast());
//        expectedL2.add(b.removeLast());
//        }
//        int expectedSize = a.size();
//        int actualSize = b.size();
//
//
//        assertEquals(expectedSize, actualSize);
//
//        for (int i = 0; i < 10; i++) {
//        assertEquals(expectedL2.get(i), actualL2.get(i));
//        System.out.println("removeLast()");
//        }
//        }
//        }
//










//            } else if (methodcall == 1) {

//            } else if (methodcall == 2 && a.size() != 0 && b.size() != 0) {
//                    int expected = a.removeFirst();
//                    int actual = b.removeFirst();
//                    assertEquals(a.get(0) , b.get(0));
//                    System.out.println("removeFirst()");
//            } else if (methodcall == 3 && a.size() != 0 && b.size() != 0) {
//                    int expected = a.removeLast();
//                    int actual = b.removeLast();
//                    assertEquals(expected, actual);
//                    System.out.println("removeLast()");
//                }
//        }
//    }
//}




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


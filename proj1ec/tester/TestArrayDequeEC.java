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
        String message = "\n";

        for (int i = 0; i < 500; i += 1) {
            double randomNum = StdRandom.uniform();

            if (randomNum < 0.25) {
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
                a.addLast(i);
                b.addLast(i);

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
                message += "addLast(" + i + ")\n";

                //        assertEquals(expected, actual);
//        System.out.println("addFirst(" + i + ")");
//        }
//        for (int i = 0; i < 10; i++) {
//        int randomNum = StdRandom.uniform(1000);
//        a.addLast(randomNum);
//        b.addLast(randomNum);
//        }
//
                Integer expected = b.get(b.size() - 1);
                Integer actual = a.get(a.size() - 1);
                assertEquals(message, expected, actual);

                //            if (methodcall == 0) {
//                a.addLast(i);
//                b.addLast(i);
//                Integer expected = a.get(a.size() - 1);
//                Integer actual = b.get(b.size() - 1);
//                assertEquals(expected, actual);
//                System.out.println("addLast(" + i + ")");

//            } else if (methodcall == 1) {
//                a.addFirst(i);
//                b.addFirst(i);
//                Integer expected = a.get(0);
//                Integer actual = b.get(0);
//                assertEquals(expected, actual);
//                System.out.println("addFirst(" + i + ")");
//
//

            } else if (randomNum < 0.5) {
                a.addFirst(i);
                b.addFirst(i);


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
                message += "addFirst(" + i + ")\n";
                Integer expected = b.get(0);
                Integer actual = a.get(0);
                assertEquals(message, expected, actual);
            } else if (randomNum < 0.75) {
                if (b.size() == 0) {
                    continue;
                }
                Integer actual = a.removeFirst();
                Integer expected = b.removeFirst();
                message += "removeFirst()\n";
                assertEquals(message, expected, actual);
            } else {
                if (b.size() == 0) {
                    continue;
                }

//        for (int i = 0; i < 10; i++) {
//            int random = StdRandom.uniform(100);
//            a.addFirst(random);
//            b.addFirst(random);
//            int actual = a.get(0);
//            int expected = b.get(0);
//            assertEquals(actual, expected);
//            System.out.println("addFirst(" + random + ")");
//
//            random = StdRandom.uniform(100);
//            a.addLast(random);
//            b.addLast(random);
//            actual = a.get(1);
//            expected = b.get(1);
//            assertEquals(actual, expected);
//            System.out.println("addLast(" + random + ")");


//
                Integer actual = a.removeLast();
                Integer expected = b.removeLast();
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
                message += "removeLast()\n";
                assertEquals(message, expected, actual);
            }
        }

    }
}
////        List methods = new ArrayList();
//        for (Integer i = 0; i < 100; i++) {
//            Integer methodcall = StdRandom.uniform(0, 4);
////            int randomNum = StdRandom.uniform(1000);

//
//            } else if (methodcall == 2 && a.size() != 0 && b.size() != 0) {
//                Integer expected = a.removeFirst();
//                Integer actual = b.removeFirst();
//                assertEquals(expected, actual);
//                System.out.println("removeFirst()");
//            } else if (methodcall == 3 && a.size() != 0 && b.size() != 0) {
//                Integer expected = a.removeLast();
//                Integer actual = b.removeLast();
//                assertEquals(expected, actual);
//                System.out.println("removeLast()");
//            }
//        }
//    }


//            actual = a.removeFirst();
//            expected = b.removeFirst();
//            assertEquals("removeFirst()", actual, expected);
//            System.out.println("removeFirst()");
//
//            actual = a.removeLast();
//            expected = b.removeLast();
//            assertEquals("removeLast()", actual, expected);
//            System.out.println("removeLast()");
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

//        }
//        }
//















//        for (int i = 0; i < 10000; i++) {
//            int expected = a.get(i);
//            int actual = b.get(i);
//            assertEquals(expected, actual);
//            System.out.println("addFirst(" + i + ")");
//        }
//        addlast



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




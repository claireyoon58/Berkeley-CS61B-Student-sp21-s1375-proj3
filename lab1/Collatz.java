/** Class that prints the Collatz sequence starting from a given number.
 *  @author YOUR NAME HERE
 */
public class Collatz {

    /** Buggy implementation of nextNumber! */
    public static int nextNumber(int n) {
        if (n  == 128) {
            return 1;
        } else if (n == 5) {
            return 3 * n + 1;
        } else {
            return n * 2;
        }
    }

    public static void main(String[] args) {
        int n = 5;
<<<<<<< HEAD
        while (n > 1) {
            System.out.print(n + " ");
            n = nextNumber(n);
        }

        System.out.print(1);
=======
        System.out.print(n + " ");
        while (n != 1) {
            n = nextNumber(n);
            System.out.print(n + " ");
        }
        System.out.println();
>>>>>>> 7aa1b6fc79cb752e1ed844cd9cdd8c9c21e7f3d4
    }

    public static int nextNumber(int n) {
            if (n % 2 == 0) {
                n = n / 2;
                return n;
            } else {
                n = (3 * n) + 1;
                return n;
            }
        }

}


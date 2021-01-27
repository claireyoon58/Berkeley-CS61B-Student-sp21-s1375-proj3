/** Class that prints the Collatz sequence starting from a given number.
 *  @author YOUR NAME HERE
 */
public class Collatz {
    public static void main(String[] args) {
        int n = 5;
        while (n > 1) {
            System.out.print(n + " ");
            n = nextNumber(n);
        }

        System.out.print(1);
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


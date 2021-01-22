/** Class that prints the Collatz sequence starting from a given number.
 *  @author YOUR NAME HERE
 */
public class Collatz {
    public static void main(String[] args) {
        int n = 5;
        System.out.print(n + "" + nextNumber(n));

    }

    public static int nextNumber(int n) {
        if (n == 1) {
            return 0;
        } else if (n % 2 == 0) {
            int even = n / 2;
            return (nextNumber(even) + 1);
        } else {
            int odd = (3 * n) + 1;
            return (nextNumber(odd) + 1);
        }
    }
}


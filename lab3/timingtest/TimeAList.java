package timingtest;
import edu.princeton.cs.algs4.Stopwatch;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;


/**
 * Created by hug.
 */
public class TimeAList {
    private static void printTimingTable(ArrayList<Integer> Ns, ArrayList<Double> times, ArrayList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeAListConstruction();
    }

    public static void timeAListConstruction() {
        ArrayList<Double> t = new ArrayList<>();
        ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000));

        for (int i = 0; i < numbers.size(); i++) {
            AList<Integer> timeconstruct = new AList<Integer>();
            Stopwatch watch = new Stopwatch();
            for (int k = 0; k < numbers.get(i); k++) {
                timeconstruct.addLast((1));
            }
            t.add(watch.elapsedTime());
        }
        printTimingTable(numbers, t, numbers);
    }
}

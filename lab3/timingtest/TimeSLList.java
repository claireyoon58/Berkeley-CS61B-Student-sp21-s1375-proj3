package timingtest;
import edu.princeton.cs.algs4.Stopwatch;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

/**
 * Created by hug.
 */
public class TimeSLList {
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
        timeGetLast();
    }

    public static void timeGetLast() {
        ArrayList<Double> t = new ArrayList<>();
        ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000));
        ArrayList<Integer> Ops = new ArrayList<>(Arrays.asList(10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000));

        for (int i = 0; i < numbers.size(); i++) {
            SLList<Integer> run = new SLList<>();
            for (int b = 0; b < numbers.get(i); b++) {
                run.addLast(1);
            }
            Stopwatch watch = new Stopwatch();
            for (int c = 0; c < 10000; c++) {
                run.getLast();
            }
            t.add(watch.elapsedTime());
        }
        printTimingTable(numbers, t, Ops);
    }

}

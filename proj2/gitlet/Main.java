package gitlet;
import java.io.*;
import java.util.*;
/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author Claire Yoon
 */
public class Main extends Repository implements Serializable {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ...
     */


//    HashMap<String, Commit> commits;



    public static void main(String... args) throws ClassNotFoundException, IOException {
        // TODO: what if args is empty?
//        String firstArg = args[0];
//        switch(firstArg) {
//            case "init":
//                // TODO: handle the `init` command
//                break;
        if (args.length == 0 || args == null) {
            System.out.println("Please enter a command");
            System.exit(0);
        }

//           break;
//        case "add":
//                // TODO: handle the `add [filename]` command
        Repository case1 = new Repository();
        case1.main(args);
//
//
//
    }
}

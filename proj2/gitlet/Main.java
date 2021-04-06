package gitlet;
import java.io.*;
import java.util.*;
/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author Claire Yoon
 */
public class Main extends Repository implements Serializable {

    public static void main(String... args) throws ClassNotFoundException, IOException {
        boolean argslen = (args.length == 0);
//        String firstArg = args[0];
//        switch(firstArg) {
        boolean nullcase = (args == null);
//            case "init":
//
//                break;
        if (argslen || nullcase) {
            helperErrorExit("Please enter a command");
        }

//           break;
//        case "add":
//
        Repository c = new Repository();
        c.main(args);
    }

    public static void helperErrorExit(String message) {
        if (message != null && !message.equals("")) {
            System.out.println(message);
            System.exit(0);
        }
    }
}

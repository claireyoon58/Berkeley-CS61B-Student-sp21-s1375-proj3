package gitlet;
import java.io.*;
import java.util.*;
/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author Claire Yoon
 */
public class Main extends Repository implements Serializable {




    public static void main(String... args) throws ClassNotFoundException, IOException {

//        String firstArg = args[0];
//        switch(firstArg) {
//            case "init":
//
//                break;
        boolean argslen = (args.length == 0);
        boolean nullcase = (args == null);
        if (argslen || nullcase) {
            System.out.println("Please enter a command");
            System.exit(0);
        }

//           break;
//        case "add":
//
        Repository c = new Repository();
        c.main(args);
//
//
//
    }
}

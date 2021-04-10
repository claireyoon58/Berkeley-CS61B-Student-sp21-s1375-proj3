package gitlet;

import java.io.File;
//import static gitlet.Utils.*;
import java.io.*;
//import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.io.ObjectInputStream;
//import java.util.stream.Collectors;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.*;
import java.text.*;
import java.util.LinkedHashMap;

//  @author Claire Yoon
public class Repository implements Serializable {
    /**
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    private HashMap<String, Blob> _staged;
    private HashMap<String, Commit> _commits;
    private HashMap<String, String> _changes;
    private LinkedHashMap<String, String> _branchhash;
    private LinkedHashMap<String, Commit> allcoms;
    private HashMap<String, Blob> _currblobs;
    private ArrayList<String> _xtrack;
    private HashMap<String, String> _splits;
    private Commit _split;
    private ArrayList<String> _removing;
    private ArrayList<String> _removed;
    private String function;
    private ArrayList<String> _op;
    private String _idparent;
    private String _curbranch;
    private Commit _head;


    public Repository() {
        _staged = new HashMap<>();
        _xtrack = new ArrayList<>();
        _removing = new ArrayList<>();
        _removed = new ArrayList<>();
        allcoms = new LinkedHashMap<>();
        _commits = new HashMap<>();
        _idparent = null;
        _curbranch = null;
        _head = null;
        _currblobs = new HashMap<>();
        _branchhash = new LinkedHashMap<>();
        _commits = new HashMap<>();
        _split = null;
        _splits = new HashMap<>();
        _changes = new HashMap<>();
    }

    public void replicate(Repository copy) {
        _staged = copy._staged;
        _xtrack = copy._xtrack;
        _removing = copy._removing;
        _removed = copy._removed;
        allcoms = copy.allcoms;
        _commits = copy._commits;
        _idparent = copy._idparent;
        _curbranch = copy._curbranch;
        _head = copy._head;
        _currblobs = copy._currblobs;
        _branchhash = copy._branchhash;
        _commits = copy._commits;
        _split = copy._split;
        _splits = copy._splits;
        _changes = copy._changes;
    }


    /**
     * The current working directory.
     */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /**
     * The .gitlet directory.
     */
//    public static final File GITLET_DIR = join(CWD, ".gitlet");
//    public static final File BRANCH_DIR = join(GITLET_DIR, "refs");
//    public static final File OBJ_DIR = join(GITLET_DIR, "objective");
//    public static final File COMMIT_DIR = join(Repository.OBJ_DIR, "commits");


    public void main(String... args) throws IOException, ClassNotFoundException {
        Repository gitlet1 = new Repository();
        boolean gitletsave = Repository.getold() != null;
        if (gitletsave) {
            gitlet1 = Repository.getold();
        }
        replicate(gitlet1);

        ArrayList<String> ord = new ArrayList<>();
        for (String s : args) {
            ord.add(s);
        }
        function = ord.remove(0);
        _op = ord;
//
//        String[] possibleFunctions = [""]
//        if (function.equals(null)) {
//            helperErrorExit("No command with that name exists.");
//        }
        boolean runCommand = true;

        if (function.equals("init")) {
            if (_op.size() != 0) {
                helperErrorExit("Incorrect operands.");
            }
            gitlet1.init();
        }
        File gitl = new File(".gitlet");
        if (!gitl.exists()) {
            System.out.println("Not in an intialized Gitlet directory.");
            return;
        } else if (function.equals("commit")) {
            boolean opsize = (_op.size() != 1);
            boolean opget = (_op.get(0) == null);
            if (opsize || opget) {
                helperErrorExit("Incorrect operands.");
            }
            String message = _op.get(0);
            gitlet1.commit(message);
        } else if (function.equals("add")) {
            boolean opsize = (_op.size() != 1);
            boolean opget = (_op.get(0) == null);
            if (opsize || opget) {
                helperErrorExit("Incorrect operands.");
            }
            String a = _op.get(0);
            gitlet1.add(a);
        } else if (function.equals("checkout")) {
            gitlet1.checkout(_op);
        } else {
            if (!function.equals("init")) {
                runCommand = false;
            }
        }
        mainpart2(gitlet1, runCommand);
        saveGitlet(gitlet1);
    }

    public void mainpart2(Repository gitlet, boolean runCommand)
            throws IOException, ClassNotFoundException {
        boolean opsize0 = (_op.size() != 0);
        boolean opsize1 = (_op.size() != 1);
        if (function.equals("log")) {
            if (opsize0) {
                helperErrorExit("Incorrect operands.");
            }
            gitlet.log();
        } else if (function.equals("rm")) {
            if (opsize1) {
                helperErrorExit("Incorrect operands.");
            }
            gitlet.rm(_op.get(0));
        } else if (function.equals("global-log")) {
            if (opsize0) {
                helperErrorExit("Incorrect operands.");
            }
            gitlet.globalLog();
        } else if (function.equals("status")) {
            if (opsize0) {
                helperErrorExit("Incorrect operands.");
            }
            gitlet.status();
        } else if (function.equals("branch")) {
            if (opsize1) {
                helperErrorExit("Incorrect operands.");
            }
            gitlet.branch(_op.get(0));
        } else if (function.equals("rmBranch")) {
            if (opsize1) {
                helperErrorExit("Incorrect operands.");
            }
            gitlet.rmBranch(_op.get(0));
        } else if (function.equals("reset")) {
            if (opsize1) {
                helperErrorExit("Incorrect operands.");
            }
            gitlet.reset(_op.get(0));
        } else if (function.equals("find")) {
            if (opsize1) {
                helperErrorExit("Incorrect operands.");
            }
            gitlet.find(_op.get(0));
        } else if (function.equals("merge")) {
            if (_op.size() != 1) {
                helperErrorExit("Incorrect operands.");
            }
            gitlet.merge(_op.get(0));
        } else {
            if (!runCommand) {
                helperErrorExit("No command with that name exists.");
            }
        }
//
    }

//use lab 6
    private static void saveGitlet(Repository gitlet1) {

        try {
            File hold = new File(".gitlet/hold.ser");
            FileOutputStream sending = new FileOutputStream(hold);
            ObjectOutputStream sendobj = new ObjectOutputStream(sending);
            sendobj.writeObject(gitlet1);
            sendobj.close();
            sendobj.close();

//            public void saveDog() {
//            File dogsaved = Utils.join(DOG_FOLDER, this.name);
//            try {
//                dogsaved.createNewFile();
//            } catch (IOException exception) {
//                exception.printStackTrace();
//            }
//            Utils.writeObject(dogsaved, this);
//
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private static Repository getold() {
//        String save = ".gitlet/hold.ser";
        Repository old = null;
        if (new File(".gitlet/hold.ser").exists()) {
            //        public void saveDog() {
//            File dogsaved = Utils.join(DOG_FOLDER, this.name);
//            try {

//
            try {
                FileInputStream f = new FileInputStream(".gitlet/hold.ser");
                ObjectInputStream input = new ObjectInputStream(f);
                Object oldO = input.readObject();
                old = (Repository) oldO;
            } catch (FileNotFoundException exception) {
                exception.printStackTrace();
                //                dogsaved.createNewFile();
//            } catch (IOException exception) {

//            Utils.writeObject(dogsaved, this);
            } catch (IOException exception) {
                exception.printStackTrace();
            } catch (ClassNotFoundException exception) {
                //                exception.printStackTrace();
//            }
                exception.printStackTrace();
            }
        }
        return old;
    }
    public void init() throws IOException {
//        if (GITLET_DIR.exists()) {;
//            File gitlet = new File(".gitlet");
//            gitlet.mkdir();

        Path gitletPath = Paths.get(".gitlet");
        if (!Files.exists(gitletPath)) {
            File gitlet = new File(".gitlet");

//        Commit init = new Commit();
            gitlet.mkdir();
//            try {
            Files.createDirectory(Paths.get(".gitlet/merge"));
            Files.createDirectory(Paths.get(".gitlet/commit"));
            Files.createDirectory(Paths.get(".gitlet/stage"));
//                Files.createDirectory(Paths.get(".gitlet/merge"));
//                gitlet.createNewFile();
//            } catch (IOException exception) {
//                exception.printStackTrace();
//            }
//            try {
//                Files.createDirectory(Paths.get(".gitlet/commit"));
//                gitlet.createNewFile();
//            } catch (IOException exception) {
//                exception.printStackTrace();
//            }
//            try {
//                Files.createDirectory(Paths.get(".gitlet/stage"));
//                gitlet.createNewFile();
//            } catch (IOException exception) {
//                exception.printStackTrace();
//            }
        } else {
            helperErrorExit("A Gitlet version-control system "
                    + "already exists in the current directory.");
        }
            //        public void saveDog() {
//            File dogsaved = Utils.join(DOG_FOLDER, this.name);
//            try {
//                dogsaved.createNewFile();
//            } catch (IOException exception) {
//                exception.printStackTrace();
//            }
//            Utils.writeObject(dogsaved, this);
//
// Relationship between a Branch and a Commielse {

        String message = "initial commit";
        String p1 = null;
        String p2 = null;
        _curbranch = "master";
        String time = "Wed Dec 31 00:00:00 1969 -0800";
//        boolean current = (_curbranch.equals("master"));
        HashMap<String, Blob> blobfile = new HashMap<>();
//        boolean bh = _branchhash != null;
//        boolean bhkey = _branchhash.containsKey(_curbranch);
        if (!_curbranch.equals("master") && _branchhash != null
                && _branchhash.containsKey(_curbranch)) {
            helperErrorExit("A branch with that name already exists.");
        } else {
            _branchhash.put(_curbranch, _idparent);
            _splits.put(_curbranch, _idparent);
        }
//        Commit commitmethod = new Commit(p1, p2, message, timestamp, blobfile, _curbranch);
        Commit method = new Commit(message, time,
                blobfile, p1, p2, _curbranch);
        allcoms.put(method.getstringhash(), method);
        _head = method;

        _idparent = _head.inithash();
        LinkedList<Commit> branchc = new LinkedList<>();
        branchc.add(_head);
        File file = new File(".gitlet/commit/" + _idparent);
        Utils.writeContents(file, Utils.serialize(method));
    }

    //            byte[] serialb = Utils.serialize(blob1);
//            if (!_staged.isEmpty() && _staged.containsKey(filename)) {
////                String blobstage = ".gitlet/stage/"
// + blob1.getblob();
//                File old = new File(".gitlet/stage/" + blob1.getblob());
//                old.delete();
//                boolean containrm = _removed.
//                contains(filename);
//                if (!containrm) {
////                    addStage.containsKey(name)) {
////                        addStage.replace(name, ident);
//                    _staged.put(filename,
//                    blob1);
//                    _currblobs.put(filename, blob1);
//                    File newf = new File(".gitlet/stage/" +
//                    blob1.getblob());
//                    Utils.writeContents(newf, serialb);
//                } else {
//                    File newf = new File(filename);
//
////                    Utils.readObject(head, Commit.class);
//                    Utils.writeContents(newf, serialb);
//                    _removed.remove(filename);
////                    Utils.readObject(add, Stage.class);
////                    rUtils.readObject(rm, Stage.class);
//                    _changes.remove(filename);
//                }
//                _currblobs.put(filename, blob1);
    //    Add Method
//    Cannot invoke "gitlet.Blob.getblob()" because the
//    return value of "java.util.HashMap.get(Object)" is null


//    public void add(String filename) throws IOException {
//        File newfilen = new File(filename);
//        if (!newfilen.exists()) {
//            helperErrorExit("File does not exist");
//            return;
//        }
//        if (_removing.contains(filename)) {
//            _removing.remove(filename);
////            if (blob1.getstrinbh().equals(_head.getblob().get(hash).getstrinbh())) {
//            _xtrack.remove(filename);
//        }
//        Blob blobadd = new Blob(filename);
//        Path commit1 = Paths.get(".gitlet/commit/" + _idparent);
//        for (String h : _head.getblob().keySet()) {
//            //        Path path1 = Paths.get(".gitlet/commit/"+ _idparent);
//////        HashMap<String, Blob> headblob = _head.getblob();
//            if (blobadd.getstringbh().equals(
//                    _head.getblob().get(h).getstringbh())) {
//                if (_staged.containsKey(filename)) {
//                    File old =
//                            new File("gitlet/stage/"
//                                    + _staged.get(filename).getstringbh());
//                    old.delete();
//                    _staged.remove(filename);
//                    return;
//                }
//                return;

//        }
//    }


    public void add(String filename) throws IOException {
        File newfilen = new File(filename);
        if (!newfilen.exists()) {
            helperErrorExit("File does not exist.");
            return;
        }
        if (_removing.contains(filename)) {
            _removing.remove(filename);
//            if (blob1.getstrinbh().equals) {if (_staged.containsKey(filename)) {
            _xtrack.remove(filename);
        }
        Blob blobadd = new Blob(filename);
        Path commit1 = Paths.get(".gitlet/commit/" + _idparent);
        for (String h : _head.getblob().keySet()) {
//        for (String hash : _head.getblob().keySet()) {
            if (blobadd.getstringbh().equals(_head.getblob().get(h).getstringbh())) {
                if (_staged.containsKey(filename)) {
                    File old =
                            new File("gitlet/stage/"
                                    + _staged.get(filename).getstringbh());
                    old.delete();
                    _staged.remove(filename);
                    return;
                }
                return;
            }
        }
        String blobhasha = blobadd.getstringbh();
        //Utils.writeContents(newfs.readObject(head, Commit.class);_removed.remove(filename);
// Utils.readObject(add, Stage.class);//rUtils.readObject(rm, Stage.class);_changes.remove(fi
        boolean parentid = (_idparent.equals(blobhasha));
        boolean stageempty = _staged.isEmpty();
        boolean stagefile = (!_staged.containsKey(filename));
        if (!parentid) {
            if (stagefile || stageempty) {
                File before = new File(".gitlet/stage/" + blobadd.getstringbh());
                before.delete();
                boolean containrm = _removed.contains(filename);
                if (!containrm) {
                    _staged.put(filename, blobadd);
                    String addbh = blobadd.getstringbh(); //_staged.put(filename, blob1);
//                    File newf = new File(".gitlet/stage/" +blob1.getstrinbh());
                    _currblobs.put(filename, blobadd);
                    File updated = new File(".gitlet/stage/" + addbh);
                    byte[] serialb = Utils.serialize(blobadd);
                    Utils.writeContents(updated, serialb);
                } else {
                    File updated = new File(filename);
                    byte[] serialb = Utils.serialize(blobadd);
                    Utils.writeContents(updated, serialb);
                    _removed.remove(filename); //_currblobs.put(filename, blob1);
                    _changes.remove(filename);
                }
                _currblobs.put(filename, blobadd);
            } else if (_staged.isEmpty() || !_staged.containsKey(filename)) {
                if (!_removed.contains(filename)) {
                    _staged.put(filename, blobadd);
                    String addstring = blobadd.getstringbh();
                    //System.out.println(_staged);Utils.writeContents(newf, Utils.serialize(blob1));
                    File updated = new File(".gitlet/stage/"
                            + addstring);
                    byte[] serialb = Utils.serialize(blobadd);
                    Utils.writeContents(updated, serialb);
                } else {
                    File updated = new File(filename);
                    byte[] serialb = Utils.serialize(blobadd);
                    Utils.writeContents(updated, serialb);
                    _removed.remove(filename);
                }
                _currblobs.put(filename, blobadd);
            }
        } else {
            String bhstage = _staged.get(filename).getstringbh();
            File before = new File(".gitlet/stage/" + bhstage);
            //_staged.put(filename, b); newf = new File(".gitlet/stage/" + blob1.getstrinbh());
            before.delete();
            _staged.remove(filename);
        }
    }
    //    Helperserialize
    public static Object serialhelp(Path p) {
        File newf = p.toFile();
        Object result =  null;
        try {
            FileInputStream newfis = new FileInputStream(newf);
            ObjectInputStream in = new ObjectInputStream(newfis);
            result = in.readObject();
            in.close();
        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
        return result;
    }

    public void commit(String message) {
        List<String> util = Utils.plainFilenamesIn(System.getProperty("user.dir"));
        for (String file : util) {
            boolean stagekey = _staged.containsKey(file);
            if (!stagekey) {
                _xtrack.add(file);
            }
        }
        if (message.equals("")) {
            helperErrorExit("Please enter a commit message");
        }
        boolean stageempty = _staged.isEmpty();
        boolean xtractempty = _xtrack.isEmpty();
        if (stageempty && xtractempty) {
            helperErrorExit("No changes added to the commit.");
        }

        Date currenttime = new Date();
        String datec = new SimpleDateFormat(
                "EEE MMM d HH:mm:ss yyyy").format(currenttime);
        String current = datec + " -0800";
        Commit committed = new Commit(message, current, _currblobs,
                _idparent, null, _curbranch);
        boolean equalhash = _idparent.equals(committed.getstringhash());
        boolean parentcheck = (_idparent != null);
        if (equalhash && parentcheck) {
            helperErrorExit("No changes added to the commit.");
        }
        String committedstringhash = committed.getstringhash();

        _branchhash.put(_curbranch, committedstringhash);
        allcoms.put(committedstringhash, committed);
        _commits.put(committedstringhash, committed);
        _idparent = committedstringhash;
        _branchhash.put(_curbranch, _idparent);
        _head = committed;
        String commithashf = ".gitlet/commit/" + committed.getstringhash();
        File newfile = new File(commithashf);
        byte[] commitserial = Utils.serialize(committed);
        Utils.writeContents(newfile, commitserial);
        File stage = new File(".gitlet/stage/");
        for (File f : stage.listFiles()) {
            boolean filedirect = f.isDirectory();
            if (!filedirect) {
                f.delete();
            }
        }
        _removing.clear();
        _xtrack.clear();
        _removed.clear();
        _changes.clear();
        boolean empty = _staged.isEmpty();
        if (!empty) {
            _staged.clear();
        }
    }


    //    method RM
    private void rm(String name) {
        boolean rm = true;
        File currentfile = new File(name);
        Commit commit = allcoms.get(_idparent);
        HashMap<String, Blob> tracked = commit.getblob();
        boolean emptystage = (!_staged.isEmpty());
        boolean trackedkey = tracked.containsKey(name);
        boolean containkey = _staged.containsKey(name);
        boolean existfile = currentfile.exists();

        if (!trackedkey && !existfile) {
            helperErrorExit("File does not exist.");
        }


        if  (containkey && emptystage) {
            String addold = _staged.get(name).getstringbh();
            File old = new File(".gitlet/stage/" + addold);
            old.delete();
            _staged.remove(name);
            _currblobs.remove(name);
            _xtrack.add(name);
            rm = false;
        }
        if (tracked != null & trackedkey) {
            _currblobs.remove(name);
            _removing.add(name);
            if (_xtrack.contains(name)) {
                _xtrack.remove(name);
                return;
            }
            File old = new File(name);
            old.delete();
            _removed.add(name);
            rm = false;
        }

        if (rm) {
            helperErrorExit("No reason to remove the file.");
        }


    }

//Method Log
    private void log() throws IOException, ClassNotFoundException {
        List<String> arraylist = new ArrayList<>();
        String save = null;
        for (String hash : allcoms.keySet()) {
            if (allcoms.get(hash).getmessage().equals("initial commit")) {
                save = hash;
            } else {
                arraylist.add(hash);
            }
        }
        Collections.reverse(arraylist);
        arraylist.add(save);
        for (String h : arraylist) {
            Commit committed = allcoms.get(h);
//            String reash = ;
            System.out.println("===");
            System.out.println("commit " + h.substring(7));
//            String date = ;
            System.out.println("Date: " + committed.gettime());
            System.out.println(committed.getmessage());
            System.out.println();
        }
    }


//    Global Log Method


    private void globalLog() throws IOException, ClassNotFoundException {
        List<String> arraylist = new ArrayList<>();
        String save = null;
        for (String hash : allcoms.keySet()) {
            if (allcoms.get(hash).getmessage().equals("initial commit")) {
                save = hash;
            } else {
                arraylist.add(hash);
            }
        }
        Collections.reverse(arraylist);
        arraylist.add(save);
        for (String h : arraylist) {
            Commit committed = allcoms.get(h);
            String r = h.substring(7);
            System.out.println("===");
            System.out.println("commit " + r);
            String date = "Date: " + committed.gettime();
            System.out.println(date);
            System.out.println(committed.getmessage());
            System.out.println();
        }
    }

    //    Method FInd
    private void find(String message)
            throws IOException, ClassNotFoundException {
        boolean tracker = false;
        for (String hash : allcoms.keySet()) {
            if (allcoms.get(hash).getmessage().equals(message)) {
                tracker = true;
                String id = hash.substring(7);
                System.out.println(id);
            }
        }
        if (!tracker) {
            System.out.println("Found no commit with that message");
        }
    }

    //Method Status
    private void status() throws IOException, ClassNotFoundException {
        System.out.println("=== Branches ===");
        boolean bhempty = _branchhash.isEmpty();
        Set<String> key = _branchhash.keySet();
        _curbranch = "master";
        if (!bhempty) {
            for (String s : key) {
                if (s.equals(_curbranch)) {
                    System.out.println("*" + _curbranch);
                } else {
                    System.out.println(s);
                }
            }
        }
        System.out.println();
        System.out.println("=== Staged Files ===");
        if (!_staged.isEmpty()) {

            List<String> sorted = new ArrayList<>(_staged.keySet());
            Collections.sort(sorted);

            for (String s : sorted) {
                System.out.println(s);
            }
        }
        System.out.println();
        System.out.println("=== Removed Files ===");
        if (!_removed.isEmpty()) {
            for (String rem : _removed) {
                System.out.println(rem);
            }
        }
        System.out.println();
        System.out.println("=== Modifications Not Staged For Commit ===");
        if (!_changes.isEmpty()) {
            for (String s : _changes.keySet()) {
                System.out.println(s + "(" + _changes.get(s) + ")");
            }
        }
        System.out.println();
        System.out.println("=== Untracked Files ===");
        if (!_xtrack.isEmpty()) {
            for (String s : _xtrack) {
                System.out.println(s);
            }
        }
        System.out.println();
    }
    public static void helperErrorExit(String message) {
        if (message != null && !message.equals("")) {
            System.out.println(message);
            System.exit(0);
        }
    }

//    public static void checkout(String[] args) throws IOException {
//        if (args.length == 2) {
//
//        } else if ((args[1].equals("--")) && (args.length == 3)) {
//
//        } else if (args[2].equals("--") && (args.length == 4)) {
//            String id;
//            String committ = args[1];
//            if (committ.charAt(0) != 'c') {
//                id = "commit-" + committ;
//            } else {
//                id = committ;
//            }
//           \
//        } else helperErrorExit("Incorrect operands.");
//
//    }

    public void checkoutBranch(String branch) {

        HashMap<String, Blob> curfiles = new HashMap<>();
        String branchhash = _branchhash.get(branch);
        Path bnco = Paths.get(".getlet/commit/" + branchhash);

        Commit committ = (Commit) serialhelp(bnco);
        boolean tracker = trackerHelper(branch);
        for (String filename : committ.getstringfile()) {
//            String file : Utils.plainFilenamesIn(System.getProp
            for (String file : Utils.plainFilenamesIn(System.getProperty("user.dir"))) {
//                Path bnco = Paths.get(".getlet/commit/" + _branchhash.get(branchname));
//                Commit committ = (Commit) Utils.helper2(bnco);
                boolean filecon = committ.getblob().containsKey(file);
                boolean trackercon = (_xtrack.contains(file) || tracker);
                if (trackercon && filecon) {
                    helperErrorExit("There is an untracked file in the way;"
                            + " delete it or add it first.");
                }
            }
            File updated = new File(filename);
            Blob before = committ.getblob().get(filename);
            String info = before.getcontentstring();
            //            File newfile = new File(s);
            Utils.writeContents(updated, info);
//            filename.put(
            curfiles.put(filename, committ.getblob().get(filename));
        }

//        for (String s : committ.getstringfile()) {

//            Blob old = committ.getblob().get(s);

        for (String filename : _currblobs.keySet()) {
//            Utils.writeObject(add, new Stage());
//
            boolean curfile = curfiles.containsKey(filename);
            if (!curfile) {
                File newfile = new File(filename);
                newfile.delete();
            }
        }

        _currblobs.clear();
        _currblobs.putAll(curfiles);
        _idparent = _branchhash.get(branch);
//        Utils.writeObject(remFile, new Stage());
        _head = allcoms.get(_idparent);
        _curbranch = branch;
        File staging = new File(".gitlet/stage");
        for (File file : staging.listFiles()) {
            boolean direct = file.isDirectory();
            if (!direct) {
                file.delete();
            }
        }
        _staged.clear();
    }

    public void checkoutFile(String file) {
        String idc = _head.getstringbranch();
        if ((".gitlet/commit/" + idc) == null) {
            helperErrorExit("File does not exist in that commit.");
        }
        if (_head.getblob().containsKey(file)) {
            File fileutd = new File(file);
            Blob old = _head.getblob().get(file);
            String info =  old.getcontentstring();
            Utils.writeContents(fileutd, info);
        }
    }

    public void checkoutCommitted(String id, String file) {
        String idc;
//        char id_First = id.charAt(0);
//        boolean checkout_helper = id_First
        if (id.charAt(0) != 'c') {
            idc = "commit-" + id;
        } else {
            idc = id;
        }
        Path commitfile = Paths.get(".gitlet/commit/" + idc);
        boolean containall = allcoms.containsKey(idc);
        if (!containall) {
            helperErrorExit("No commit with that id exists.");
        }
        Commit curr = (Commit) serialhelp(commitfile);
        if (!curr.getblob().keySet().contains(file)) {
            helperErrorExit("File does not exist in that commit.");
        }
//        File fileutd = new File(file);
//        Blob old = _head.getblob().get(file);
//        String info =  old.getcontentstring();
//        Utils.writeContents(fileutd, info);
        File fileupdated = new File(file);
        Blob old = curr.getblob().get(file);
        String contents = old.getcontentstring();
        Utils.writeContents(fileupdated, contents);
    }



    //    public static checkout(String[] args) throws IOException, ClassNotFoundException {
    public void checkout(ArrayList<String> operand) {
//        if (args.length == 2) {
//            String branchname = args[0];
        if (operand.size() == 1) {
            String branch = operand.get(0);
            //            if (_branchhash.get(branchname) == null) {
//            String commit_id = operands.get(0);
//            String dashes = operands.get(1);
//            String file_name = operands.get(2)
//                System.out.println("No such branch exists.");
//                System.exit(0);
            String getbranch = _branchhash.get(branch);
            if (branch.equals(_curbranch)) {
                helperErrorExit("No need to checkout the current branch.");
            }
            if (getbranch == null) {
                helperErrorExit("No such branch exists.");
            }
//            if (file == null) {
//                System.out.println("File does not exist in that commit.");
//                System.exit(0);
            checkoutBranch(branch);
        } else if (operand.size() == 2) {
            String file = operand.get(1);
            boolean operandequal = operand.get(0).equals("--");
            if (!operandequal) {
                helperErrorExit("Incorrect operands.");
            }
            checkoutFile(file);
        } else if (operand.size() == 3) {
            boolean op = (operand.get(1).equals("--"));
            if (!op) {
                helperErrorExit("Incorrect operands.");
            }
            checkoutCommitted(operand.get(0), operand.get(2));
        } else {
            helperErrorExit("Incorrect operands.");
        }
    }




    public boolean trackerHelper(String branch) {
//            int _length = args.length();
        Path finish = Paths.get(".gitlet/commit/" + _branchhash.get(branch));
        HashMap<String, Blob> header = _head.getblob();
//            List<String> directory = Utils.plainFilenamesIn(_commits)
//            .stream().filter(s -> (s.substring(0, _length))
//            .equals(args)).collect(Collectors.toList());
//            if (directory.size() > 1) {
        Commit com = (Commit) serialhelp(finish);
        for (String f : Utils.plainFilenamesIn(System.getProperty("user.dir"))) {
            boolean headerr = _head.getstringfile().isEmpty();
            boolean headercon = (!_head.getstringfile().contains(f));
            if (headerr || headercon) {
                return true;
            } else if (header.isEmpty()) {
                String info = header.get(f).getcontentstring();
                Blob blob1 = new Blob(f);
                boolean contentblob = blob1.getcontentstring().equals(info);
                if (!contentblob) {
                    return true;
                }
            }
        }
        return false;
    }



    public void branch(String name) {
        if (_branchhash.containsKey(name)) {
            helperErrorExit("A branch with that name already exists");
        } else {
            _splits.put(name, _idparent);
            _branchhash.put(name, _idparent);
        }
    }

    /** Removes the branch with NAME. */
    public void rmBranch(String name) {
        if (name.equals(_curbranch)) {
            helperErrorExit("Cannot remove the current branch.");
        } else if (!_branchhash.containsKey(name)) {
            helperErrorExit("A branch with that name does not exist.");
        } else {
            _branchhash.remove(name);
        }
    }

//currently: making commit and making a list for allcoms
//Need todo: need to presist allcoms and read in allcoms and contain all the ids
//not saving allcoms afteradding it to the list, we cna't make it disspear
// need persistence with the allcoms variable,
//Look back at the code 952-954

//
//    if (!allcoms.containsKey(id)) {
//        helperErrorExit("No commit with that id exists.");
//    }
//    File commitid = new File(".gitlet/commit" + hashcode);
//
//        if (!commitid.exists()) {
//        helperErrorExit("No commit with that id exists.");
//    }
//
//
//    File d = new File("");
//    File[] fi = d.listFiles();
//
//        if (fi != null) {
//        for (File f : fi) {
//            boolean stageK = (!_staged.containsKey(f));
//            boolean containT = _xtrack.contains(f);
//            boolean containK = (!_currblobs.containsKey(f));
//            boolean currblob = trackerHelper(_curbranch);
//            boolean stageck = (stageK && containK);
//            if (stageck || containT || currblob) {
//                helperErrorExit("There is an untracked file in the way; "
//                        + "delete it or add it first");
//            }
//        }
//    }
    public void reset(String hashcode) {
        String id = hashcode;

        File commitsf = new File(".gitlet/commits/" + hashcode);

        if (!commitsf.exists()) {
            helperErrorExit("No commit with that id exists.");
        }

        boolean xtrack = _xtrack.isEmpty();

        Commit comitted = (Commit) serialhelp(Paths.get(
                ".gitlet/commits/" + hashcode));

        List<String> f = Utils.plainFilenamesIn
                (System.getProperty("user.dir"));


        if (!xtrack) {
            helperErrorExit("There is an untracked file in the way; "
                        + "delete it or add it first");

        }

        for (String s: f) {
            boolean stagekey = _staged.containsKey(f);
            boolean blobkey = _currblobs.containsKey((f));
            if (!stagekey && !blobkey) {
                helperErrorExit("There is an untracked file in the way; "
                        + "delete it or add it first");
            }
        }
//
//        for (String s: f) {
//            File commitsf = new File(".gitlet/commits/" + hashcode);
//            commitsf.delete();
//        }



        for (String s : comitted.getblob().keySet()) {
            if (commitsf.equals(s)) {
                commitsf.delete();
            }
            if (_currblobs.containsKey(s)
                    || _staged.containsKey(s)) {
                ArrayList<String> op = new ArrayList<>();
                op.add(hashcode);
                op.add("--");
                op.add(s);
                checkout(op);
            }
        }
//            for (String name : comitted.getblob().keySet()) {
//                Commit old =;
//
//                boolean stageK = _staged.containsKey(name);
//                boolean currK = _currblobs.containsKey(name);
////                comitted = comitted.delete();
//                if (stageK || currK) {
//                    checkoutCommitted(id, name);
//                }
//            }
        _staged.clear();
        _curbranch = comitted.getstringbranch();
        _branchhash.put(_curbranch, hashcode);
        _head = comitted;
        _idparent = hashcode;
    }
//    2. One file in current, and the other in given(different),
//    but not present in the split point, all three versions
//    in the file is different(deleting the file counts),
//    merge conflict appears

    private void mergecase3(Commit curr, Commit chosen) {
        List<String> split =  _split.getstringfile();
        for (String s : split) {
            Boolean chosencontains = chosen.getstringfile().contains(s);
            Boolean currcontains = curr.getstringfile().contains(s);
            if (currcontains && chosencontains) {
                String mergehash = chosen.getblob().get(s).getstringbh();
                boolean merge = mergehash.equals(_split.getblob().get(s).getstringbh());
                String currhash = curr.getblob().get(s).getstringbh();
                boolean currhasheq = currhash.equals(_split.getblob().get(s).getstringbh());
                if (currhasheq && !merge) {
                    File file = new File(s);
                    Utils.writeContents(file, chosen.getblob().get(s).getcontentstring());
                    _staged.put(s, chosen.getblob().get(s));
                    _currblobs.put(s, chosen.getblob().get(s));
                    checkoutCommitted(chosen.getblob().get(s).getstringhash(), s);
                }
//                boolean mergehashequalupdate = mergehash.equals(_split.getblob().get(s).getstringbh());
                String userdir = System.getProperty("user.dir");
                if (!currhasheq && !merge) {
                    File path = new
                            File(userdir + File.separator + s);
                    String line = System.getProperty("line.separator");
                    String chosencontent = chosen.getblob().get(s).getcontentstring();
                    String currcontent = curr.getblob().get(s).getcontentstring();
                    String result = "<<<<<<< HEAD" + line + currcontent
                            + "=======" + line + chosencontent
                            + ">>>>>>>" + line;
                    Utils.writeContents(path,
                            result.getBytes(StandardCharsets.UTF_8));
                    Blob bl = new Blob(s);
                    _staged.put(s, bl);
                    _currblobs.put(s, bl);
                    System.out.println("Encountered a merge conflict.");
                }
            }
            boolean chosenblob = chosen.getblob().keySet().contains(s);
            boolean file = curr.getstringfile().contains(s);
            boolean currblob = _currblobs.containsKey(s);
            if (!file && chosenblob && !currblob) {
                String spl = _split.getblob().get(s).getstringbh();
                if (!chosen.getblob().get(s).getstringbh().equals(spl)) {
                    String user = System.getProperty("user.dir");
                    File folder = new File(user + File.separator + s);
                    String chosencontent = chosen.getblob().get(s).getcontentstring();
                    String result =  "<<<<<<< HEAD" + System.lineSeparator()
                            + chosencontent + "======="
                            + System.lineSeparator()
                            + ">>>>>>>" + System.lineSeparator();
                    Utils.writeContents(folder, result);
                    Blob b = new Blob(s);
                    _staged.put(s, b);
                    _currblobs.put(s, b);
                    System.out.println("Encountered a merge conflict.");
                }
            }
        }
    }

////    3. Modified in one commmit but not the other,

    private void mergecase1(Commit curr, Commit chosen) {

        List<String> chosenstring = chosen.getstringfile();
        for (String s : chosenstring) {
            boolean splitstring = _split.getstringfile().contains(s);
            boolean currstring = curr.getstringfile().contains(s);
            if (!currstring && !splitstring) {
//                if (splitCommit.contents.containsKey(file))
//                    splitID = splitCommit.contents.get(file)
                String chosensh = chosen.getstringhash();
                checkoutCommitted(chosensh, s);
                _currblobs.put(s, chosen.getblob().get(s));
                _staged.put(s, chosen.getblob().get(s));
            }
            //the file was absent at split point and has different contents in the given and current
            if (currstring && !splitstring) {
                boolean merge = chosen.getblob().get(s).getstringbh().
                        equals(curr.getblob().get(s).getstringbh());
//                for (Map.Entry<String, String> set : givenCommit.contents.entrySet())
//                    if (data.contains(set.getKey()) && !head.contents.containsKey(set.getKey()))
                if (!merge) {
                    File path = new File(System.getProperty("user.dir")
                            + File.separator + s);
                    String line = System.getProperty("line.separator");
                    String chosencontent = chosen.getblob().get(s).getcontentstring();
                    String currcontent = curr.getblob().get(s).getcontentstring();
                    String result = "<<<<<<< HEAD" + line + currcontent
                            + "=======" + line + chosencontent
                            + ">>>>>>>" + line;
                    Utils.writeContents(path, result);
                    Blob b = new Blob(s);
                    _staged.put(s, b);
                    _currblobs.put(s, b);
                    System.out.println("Encountered a merge conflict.");
                }
            }
        }
    }
////    this file is modified in EITHER given or current
////    ex) present in the commit and not changed within
////    the split point, given branch is changed--> keep the changed one
////    to-do: are these two files exist in given or current helper
////    function that checks whether the files are in the given and current
//
    private void mergecase2(Commit curr, Commit chosen) {
        //Any files present at the split point, unmodified in the current branch, and absent in the given branch should be removed (and untracked).
        for (String c : curr.getstringfile()) {
            if (_split.getstringfile().contains(c)
                    && !chosen.getstringfile().contains(c)) {
                if (curr.getblob().get(c).getstringbh().
                        equals(_split.getblob().get(c).getstringbh())) {
                    rm(c);
                } else {
                    File path = new
                            File(System.getProperty("user.dir")
                            + File.separator + c);

                    String line = System.getProperty("line.separator");
                    String chosencontent = chosen.getblob().get(c).getcontentstring();
                    String result =  "<<<<<<< HEAD" + line
                        + chosencontent + "=======" + line
                        + ">>>>>>>" + line;

                    Utils.writeContents(path, result);
                    Blob blob = new Blob(c);
                    _staged.put(c, blob);
                    _currblobs.put(c, blob);
                    System.out.println("Encountered a merge conflict.");
                }
                if (!_split.getstringfile().contains(c)
                        && !chosen.getstringfile().contains(c)) {
                    checkoutCommitted(curr.getstringhash(), c);
                    _currblobs.put(c, curr.getblob().get(c));
                    _staged.put(c, curr.getblob().get(c));
                }
            }
            if (!_split.getstringfile().contains(c)
                    && chosen.getstringfile().contains(c)) {
                if (!chosen.getblob().get(c).getstringbh().
                        equals(curr.getblob().get(c).getstringbh())) {
                    File path = new File(System.getProperty("user.dir")
                            + File.separator + c);
                    String line = System.getProperty("line.separator");
                    String chosencontent = chosen.getblob().get(c).getcontentstring();
                    String currcontent = curr.getblob().get(c).getcontentstring();
                    String result = "<<<<<<< HEAD" + line + currcontent
                            + "=======" + line + chosencontent
                            + ">>>>>>>" + line;
                    Utils.writeContents(path, result);
                    Blob bl = new Blob(c);
                    _currblobs.put(c, bl);
                    _staged.put(c, bl);
                    System.out.println("Encountered a merge conflict.");
                }
            }
        }
    }
//
//
    private void merge(String branch) {
//        Failurecases
        if (!_staged.isEmpty()) {
            helperErrorExit("You have uncommitted changes.");
        }
        if (!_branchhash.containsKey(branch)) {
            helperErrorExit("A branch with that name does not exist.");
        }
        if (branch.equals(_curbranch)) {
            helperErrorExit("Cannot merge a branch with itself.");
        }

        if (branch.equals("master")) {
            _split = (Commit) serialhelp(Paths.get(".gitlet/commit/"
                            + _splits.get(_curbranch)));
        } else {
            _split = (Commit) serialhelp(Paths.get(".gitlet/commit/"
                            + _splits.get(branch)));
        }
        Commit currbranch = allcoms.get(_branchhash.get(_curbranch));
        Commit chosen = (Commit) serialhelp(Paths.get(".gitlet/commits/"
                + _branchhash.get(branch)));
        String user = System.getProperty("user.dir");
        List<String> userfile = Utils.plainFilenamesIn(user);
        boolean fastforward = _split.getstringhash().equals(currbranch.getstringhash());
        boolean ancestor = _split.getstringhash().equals(chosen.getstringhash());
        if (fastforward) {
            helperErrorExit("Current branch fast-forwarded.");
        }
        if (ancestor) {
            helperErrorExit("Given branch is an ancestor"
                    + " of the current branch.");
        }
        boolean blobkey = _currblobs.containsKey(userfile);
        boolean stagekey = _staged.containsKey(userfile);
        boolean mergekey = chosen.getblob().containsKey(userfile);
        for (String s : userfile) {
            if (!blobkey && !stagekey && mergekey) {
                helperErrorExit("There is an untracked file in the way; "
                        + "delete it or add it first.");
            }
        }
////1.find the split point
////    -track back ancestors within the tree
////    split pint, current brand(head), given branch
////    three    scenarios: base) a file in the split point, in the
////    current and given, same content--> don't do anything because we want to combine
        mergecase1(currbranch, chosen);
        mergecase2(currbranch, chosen);
        mergecase3(currbranch, chosen);
        String d = new
                SimpleDateFormat("EEE MMM d HH:mm:ss yyyy").format(new Date());
        String merge = "Merged " + branch
                + " into " + _curbranch + ".";
        Commit commit =
                new Commit(merge, d + " -0800", _currblobs, _idparent,
                        _branchhash.get(branch), _curbranch);
        String commithash = commit.getstringhash();
        _idparent = commit.getstringhash();
        allcoms.put(commithash, commit);
        String csh = commit.getstringhash();
        Object serialcommit = Utils.serialize(commit);
        File newf = new File(".gitlet/commit/" + csh);
        Utils.writeContents(newf, serialcommit);
        File dir = new File(".gitlet/stage/");
        for (File ff : dir.listFiles()) {
            boolean ffdirect = ff.isDirectory();
            if (!ffdirect) {
                ff.delete();
            }
        }
        _branchhash.put(_curbranch, commit.getstringhash());
        boolean stage = _staged.isEmpty();
        if (!stage) {
            _staged.clear();
        }
        _removing.clear();
    }













}

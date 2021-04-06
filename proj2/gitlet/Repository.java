package gitlet;

import java.io.File;
//import static gitlet.Utils.*;
import java.io.*;
//import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.*;
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
public class Repository {
    /**
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    private static HashMap<String, Blob> _staged;
    private static HashMap<String, Commit> _commits;
    private static HashMap<String, String> _changes;
    private static LinkedHashMap<String, String> _branchhash;
    private static LinkedHashMap<String, Commit> allcoms;
    private static HashMap<String, Blob> _currblobs;
    private static ArrayList<String> _untracked;
    private static HashMap<String, String> _splits;
    private static Commit _split;
    private static ArrayList<String> _toremove;
    private static ArrayList<String> _removed;
    private static String function;
    private static ArrayList<String> _op;
    private static String _idparent;
    private static String _curbranch;
    private static Commit _head;


    public Repository() {
        _staged = new HashMap<>();
        _untracked = new ArrayList<>();
        _toremove = new ArrayList<>();
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
        _untracked = copy._untracked;
        _toremove = copy._toremove;
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


    public static void main(String[] args) throws IOException, ClassNotFoundException {
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
        }
        mainpart2(gitlet1);
        saveGitlet(gitlet1);
    }

    public static void mainpart2(Repository gitlet) throws IOException, ClassNotFoundException {
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
        }
//        } else if (function.equals("merge")) {
//            if (_op.size() != 1) {
//                System.out.println("Incorrect operands.");
//                System.exit(0);
//            }
//            gitlet1.merge(_op.get(0));
//        }
    }

//use lab 6
    private void saveGitlet(Repository gitlet1) {

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
        String save = ".gitlet/hold.ser";
        Repository old = null;
        if (new File(save).exists()) {
            //        public void saveDog() {
//            File dogsaved = Utils.join(DOG_FOLDER, this.name);
//            try {

//
            try {
                FileInputStream f = new FileInputStream(save);
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
    public void init() {
//        if (GITLET_DIR.exists()) {;
//            File gitlet = new File(".gitlet");
//            gitlet.mkdir();

        Path gitletPath = Paths.get(".gitlet");
        if (!Files.exists(gitletPath)) {
            File gitlet = new File(".gitlet");
//        GITLET_DIR.mkdir();
//        BRANCH_DIR.mkdir();
//        OBJ_DIR.mkdir();
//        COMMIT_DIR.mkdir();
//        Commit init = new Commit();
            gitlet.mkdir();
            try {
                Files.createDirectory(Paths.get(".gitlet/merge"));
                gitlet.createNewFile();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            try {
                Files.createDirectory(Paths.get(".gitlet/commit"));
                gitlet.createNewFile();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            try {
                Files.createDirectory(Paths.get(".gitlet/stage"));
                gitlet.createNewFile();
            } catch (IOException exception) {
                exception.printStackTrace();
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
// Relationship between a Branch and a Commit
        } else {
            helperErrorExit("A Gitlet version-control system "
                    + "already exists in the current directory.");
        }
        String message = "initial commit";
        String p1 = null;
        String p2 = null;
        _curbranch = "master";
        String timestamp = "Wed Dec 31 00:00:00 1969 -0800";
        boolean current = (!_curbranch.equals("master"));
        HashMap<String, Blob> blobfile = new HashMap<>();
        boolean bh = _branchhash != null;
        boolean bhkey = _branchhash.containsKey(_curbranch);
        if (current && bh && bhkey) {
            helperErrorExit("A branch with that name already exists.");
        } else {
            _branchhash.put(_curbranch, _idparent);
            _splits.put(_curbranch, _idparent);
        }
        Commit commitmethod = new Commit(p1, p2, message, timestamp, blobfile, _curbranch);
        allcoms.put(commitmethod.getstringhash(), commitmethod);
        _head = commitmethod;
        _idparent = _head.inithash();
        LinkedList<Commit> branchcommits = new LinkedList<>();
        branchcommits.add(_head);
        File file = new File(".gitlet/commit/" + _idparent);
        Utils.writeContents(file, Utils.serialize(commitmethod));
    }


    //    Add Method
    public void add(String filename) throws IOException {
        String commitparent = ".gitlet/commit/" + _idparent;
        File f = new File(filename);
        String stagefolder = "gitlet/stage/" + _staged.get(filename).getblob();
        if (!f.exists()) {
            helperErrorExit("File does not exist");
        }
        if (_toremove.contains(filename)) {
            _toremove.remove(filename);
            _untracked.remove(filename);
        }
        Blob blob1 = new Blob(filename);
        Path path1 = Paths.get(commitparent);
        HashMap<String, Blob> headblob = _head.getblob();
        for (String hash : headblob.keySet()) {

            if (_head.getblob().equals((_head.getblob().get(hash)))) {
                if (_staged.containsKey(filename)) {
                    String stage = "gitlet/stage/" + _staged.get(filename).getblob();
                    File old = new File(stage);
                    old.delete();
                    _staged.remove(filename);
                    return;
                }
                return;
            }
        }
        boolean stageempty = _staged.isEmpty();
        boolean stagefile = (!_staged.containsKey(filename));
        if (stagefile || stageempty) {
            byte[] serialb = Utils.serialize(blob1);
//            assiged B ahead of time
            boolean containrm = _removed.contains(filename);
            if (!containrm) {
                String blobstage = ".gitlet/stage/" + blob1.getblob();
                _staged.put(filename, blob1);
                File newf = new File(blobstage);
                Utils.writeContents(newf, serialb);
            } else {
                File newfile = new File(filename);
                Utils.writeContents(newfile, serialb);
                _removed.remove(filename);
            }
            _currblobs.put(filename, blob1);
        } else if (!_idparent.equals(blob1.getblob())) {
            byte[] serialb = Utils.serialize(blob1);
            if (!_staged.isEmpty() && _staged.containsKey(filename)) {
                String blobstage = ".gitlet/stage/" + blob1.getblob();
                File old = new File(blobstage);
                old.delete();
                boolean containrm = _removed.contains(filename);
                if (!containrm) {
//                    addStage.containsKey(name)) {
//                        addStage.replace(name, ident);
                    _staged.put(filename, blob1);
                    _currblobs.put(filename, blob1);
                    File newf = new File(".gitlet/stage/" + blob1.getblob());
                    Utils.writeContents(newf, serialb);
                } else {
                    File newf = new File(filename);

//                    Utils.readObject(head, Commit.class);
                    Utils.writeContents(newf, serialb);
                    _removed.remove(filename);
//                    Utils.readObject(add, Stage.class);
//                    rUtils.readObject(rm, Stage.class);
                    _changes.remove(filename);
                }
                _currblobs.put(filename, blob1);
            } else {
                File old = new File(".gitlet/stage/"
                        + _staged.get(filename).getblob());
                old.delete();
                _staged.remove(filename);
//                Utils.writeObject(currFile, blobb);
//                Utils.writeObject(remFile, remStage);
            }
        }
    }

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

    public void commit(String message) throws IOException, ClassNotFoundException {
        List<String> util = Utils.plainFilenamesIn(System.getProperty("user.dir"));
        for (String file : util) {
            boolean stagekey = _staged.containsKey(file);
            if (!stagekey) {
                _untracked.add(file);
            }
        }
        if (message.equals("")) {
            helperErrorExit("Please enter a commit message");
        }

        if (_staged.isEmpty() && _untracked.isEmpty()) {
            helperErrorExit("No changes added to the commit.");
        }

        Date currenttime = new Date();
        String datec = new
                SimpleDateFormat("EEE MMM d HH:mm:ss yyyy").format(currenttime);
        String current = datec + " -0800";
        Commit committed = new Commit(_idparent, null, message, current, _currblobs, _curbranch);
        boolean equalhash = _idparent.equals(committed.getstringhash());
        boolean parentcheck = (_idparent != null);
        if (equalhash && parentcheck) {
            helperErrorExit("No changes added to the commit.");
        }
        _branchhash.put(_curbranch, committed.getstringhash());
        allcoms.put(committed.getstringhash(), committed);
        _commits.put(committed.getstringhash(), committed);
        _idparent = committed.getstringhash();
        _branchhash.put(_curbranch, _idparent);
        _head = committed;
        String commithashf = ".gitlet/commit/" + committed.getstringhash();
        File newf = new File(commithashf);
        Utils.writeContents(newf, Utils.serialize(committed));
        File stage = new File(".gitlet/stage/");
        for (File f : stage.listFiles()) {
            boolean filedirect = f.isDirectory();
            if (!filedirect) {
                f.delete();
            }
        }
        _toremove.clear();
        _untracked.clear();
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
        File file = new File(name);
        Commit commit = allcoms.get(_idparent);
        HashMap<String, Blob> tracked = commit.getblob();
        boolean emptystage = (!_staged.isEmpty());
        boolean trackedkey = (!tracked.containsKey(name));
        boolean containkey = _staged.containsKey(name);
        boolean existfile = (!file.exists());

        if (trackedkey && existfile) {
            helperErrorExit("File does not exist.");
        }
        if (rm) {
            helperErrorExit("No reason to remove the file.");
        }
        if (tracked != null & tracked.containsKey(file)) {
            _currblobs.remove(name);
            _toremove.add(name);
            if (_untracked.contains(name)) {
                _untracked.remove(name);
                return;
            }
            File old = new File(name);
            old.delete();
            _removed.add(name);
            rm = false;
        }
        if  (containkey && emptystage) {
            String addold = _staged.get(name).getblobhash();
            File old = new File(".gitlet/stage/" + addold);
            old.delete();
            _staged.remove(name);
            _currblobs.remove(name);
            _untracked.add(name);
            rm = false;
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
            for (String s : _staged.keySet()) {
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
        if (!_untracked.isEmpty()) {
            for (String s : _untracked) {
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
        Path bnco = Paths.get(".getlet/commit/" + _branchhash.get(branch));

        Commit committ = (Commit) serialhelp(bnco);
        boolean tracker = trackerHelper(branch);
        for (String filename : committ.getstringfile()) {
//            String file : Utils.plainFilenamesIn(System.getProp
            for (String file : Utils.plainFilenamesIn(System.getProperty("user.dir"))) {
//                Path bnco = Paths.get(".getlet/commit/" + _branchhash.get(branchname));
//                Commit committ = (Commit) Utils.helper2(bnco);
                boolean filecon = committ.getblob().containsKey(file);
                boolean trackercon = (_untracked.contains(file) || tracker);
                if (trackercon && filecon) {
                    helperErrorExit("There is an untracked file in the way;"
                            + " delete it or add it first.");
                }
            }
        }

        for (String s : committ.getstringfile()) {
            File newfile = new File(s);
            Blob old = committ.getblob().get(s);
//            File CWD = new File(CWD.getPath() + "/" + set.getKey());
//            File value = new File(stage.getPath() + "/" + set.getValue());
            String content = old.getcontentstring();
            Utils.writeContents(newfile, content);
            curfiles.put(s, committ.getblob().get(s));
        }

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
        String commid = _head.getstringbranch();
        String nfile = ".gitlet/commit/" + commid;
        if (nfile == null) {
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
        if (id.contains("commit")) {
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
            helperErrorExit("File does not exist in that commit");
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
    public void checkout(ArrayList<String> operand) throws IOException, ClassNotFoundException {
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
//            }
            checkoutBranch(branch);
        } else if (operand.size() == 2) {
            String file = operand.get(1);
            boolean operandequal = operand.get(0).equals("--");
            if (!operandequal) {
                helperErrorExit("Incorrect operands");
            }
            checkoutFile(file);
        } else if (operand.size() == 3) {
            boolean op = (operand.get(1).equals("--"));
            if (!op) {
                helperErrorExit("Incorrect operands.");
            }
            checkoutCommitted(operand.get(0), operand.get(2));
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


    public void reset(String hashcode)
            throws IOException, ClassNotFoundException {
        String id;
        if (hashcode.charAt(0) != 'c') {
            id = "commit-" + hashcode;
        } else {
            id = hashcode;
        }
        if (!allcoms.containsKey(id)) {
            helperErrorExit("No commit with that id exists.");
        }
        Commit comitted = allcoms.get(id);
        File d = new File("");
        File[] fi = d.listFiles();
        if (fi != null) {
            for (File f : fi) {
                boolean stageK = (!_staged.containsKey(f));
                boolean containT = _untracked.contains(f);
                boolean containK = (!_currblobs.containsKey(f));
                boolean currblob = trackerHelper(_curbranch);
                boolean stageck = (stageK && containK);
                if (stageck || containT || currblob) {
                    helperErrorExit("There is an untracked file in the way; "
                            + "delete it or add it first");
                }
            }
        }
        if (comitted != null) {
            for (String name : comitted.getblob().keySet()) {
                boolean stageK = _staged.containsKey(name);
                boolean currK = _currblobs.containsKey(name);
                if (stageK || currK) {
                    checkoutCommitted(id, name);
                }
            }
        }
        _staged.clear();
        _curbranch = comitted.getstringbranch();
        _branchhash.put(_curbranch, hashcode);
        _head = comitted;
        _idparent = hashcode;
    }











//1.find the split point
//    -track back ancestors within the tree
//    split pint, current brand(head), given branch
//    three scenarios: base) a file in the split point, in the
//    current and given, same content--> don't do anything because we want to combine
//    2. One file in current, and the other in given(different),
//    but not present in the split point, all three versions
//    in the file is different(deleting the file counts),
//    merge conflict appears
//    3. Modified in one commmit but not the other,
//    this file is modified in EITHER given or current
//    ex) present in the commit and not changed within
//    the split point, given branch is changed--> keep the changed one
//    to-do: are these two files exist in given or current helper
//    function that checks whether the files are in the given and current




}

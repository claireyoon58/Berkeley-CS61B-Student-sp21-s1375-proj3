package gitlet;

import java.io.File;
import static gitlet.Utils.*;
import java.io.*;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.*;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.util.stream.Collectors;
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

    private static HashMap<String, Blob> _staged;
    private static HashMap<String, Commit> _commits;
    private  HashMap<String, String> _changes;
    private static LinkedHashMap<String, String> _branchhash;
    private  LinkedHashMap<String, Commit> _allcommits;
    private static HashMap<String, Blob> _currblobs;
    private static ArrayList<String> _untracked;
    private  HashMap<String, String> _splits;
    private  Commit _split;
    private  ArrayList<String> _toremove;
    private  ArrayList<String> _removed;
    private  String _command;
    private  ArrayList<String> _operand;
    private static String _idparent;
    private static String _curbranch;
    private static Commit _head;

    public Repository() {
        _staged = new HashMap<>();
        _untracked = new ArrayList<>();
        _toremove = new ArrayList<>();
        _removed = new ArrayList<>();
        _allcommits = new LinkedHashMap<>();
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
        _allcommits = copy._allcommits;
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
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    public static final File BRANCH_DIR = join(GITLET_DIR, "refs");
    public static final File OBJ_DIR = join(GITLET_DIR, "objective");
    public static final File COMMIT_DIR = join(Repository.OBJ_DIR, "commits");

    //    Main Method  continues the main function w
    public void main(String... args) throws IOException, ClassNotFoundException {
        Repository gitlet1 = new  Repository();
        if (Repository.getsaved() != null) {
            gitlet1 = Repository.getsaved();
        }
        replicate(gitlet1);
        ArrayList<String> commands = new ArrayList<>();
        for (String arg : args) {
            commands.add(arg);
        }
        _command = commands.remove(0);
        _operand = commands;
        if (_command.equals("init")) {
            if (_operand.size() != 0) {
                System.out.println("Incorrect operands.");
                System.exit(0);
            }
            gitlet1.init();
        }
        File gitl = new File(".gitlet");
        if (!gitl.exists()) {
            System.out.println("Not in an intialized Gitlet directory.");
            return;
        } else if (_command.equals("commit")) {
            if (_operand.size() != 1 || _operand.get(0) == null) {
                System.out.println("Incorrect operands.");
                System.exit(0);
            }
            String message = _operand.get(0);
            gitlet1.commit(message);
        } else if (_command.equals("add")) {
            if (_operand.size() != 1 || _operand.get(0) == null) {
                System.out.println("Incorrect operands.");
                System.exit(0);
            }
            String argg = _operand.get(0);
            gitlet1.add(argg);
        } else if (_command.equals("checkout")) {
            gitlet1.checkout(_operand);
        } else if (_command.equals("log")) {
            if (_operand.size() != 0) {
                System.out.println("Incorrect operands.");
                System.exit(0);
            }
            gitlet1.log();
        } else if (_command.equals("rm")) {
            if (_operand.size() != 1) {
                System.out.println("Incorrect operands.");
                System.exit(0);
            }
            gitlet1.rm(_operand.get(0));
        } else if (_command.equals("global-log")) {
            if (_operand.size() != 0) {
                System.out.println("Incorrect operands.");
                System.exit(0);
            }
            gitlet1.globallog();
        }
        continuemain(gitlet1); save(gitlet1);
    }

    /** Continues main function with gitlet G. */
    public void continuemain(Repository gitlet1)
            throws IOException, ClassNotFoundException {
        if (_command.equals("status")) {
            if (_operand.size() != 0) {
                System.out.println("Incorrect operands.");
                System.exit(0);
            }
            gitlet1.status();
        } else if (_command.equals("branch")) {
            if (_operand.size() != 1) {
                System.out.println("Incorrect operands.");
                System.exit(0);
            }
            gitlet1.branch(_operand.get(0));
        } else if (_command.equals("rm_branch")) {
            if (_operand.size() != 1) {
                System.out.println("Incorrect operands.");
                System.exit(0);
            }
            gitlet1.rm_branch(_operand.get(0));
        } else if (_command.equals("reset")) {
            if (_operand.size() != 1) {
                System.out.println("Incorrect operands.");
                System.exit(0);
            }
            gitlet1.reset(_operand.get(0));
        } else if (_command.equals("find")) {
            if (_operand.size() != 1) {
                System.out.println("Incorrect operands.");
                System.exit(0);
            }
            gitlet1.find(_operand.get(0));
        } else if (_command.equals("merge")) {
            if (_operand.size() != 1) {
                System.out.println("Incorrect operands.");
                System.exit(0);
            }
            gitlet1.merge(_operand.get(0));
        }
    }

    /** Saves the current Gitlet G. */
    private void save(Repository gitlet1) {
        try {
            File savegitlet = new File(".gitlet/savedgitlet.ser");
            FileOutputStream fileOut = new FileOutputStream(savegitlet);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(gitlet1);
            objectOut.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Returns the last saved Gitlet object. */
    private static Repository getsaved() {
        Repository prevGitlet = null;
        if (new File(".gitlet/savedgitlet.ser").exists()) {
            try {
                FileInputStream fin = new
                        FileInputStream(".gitlet/savedgitlet.ser");
                ObjectInputStream ois = new ObjectInputStream(fin);
                Object historyObject = ois.readObject();
                prevGitlet = (Repository) historyObject;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return prevGitlet;
    }
    public void init() {
//        if (GITLET_DIR.exists()) {
//            throw new java.lang.Error("A Gitlet version-control system already exists in the current directory.");
//
//        }
//        GITLET_DIR.mkdir();
//        BRANCH_DIR.mkdir();
//        OBJ_DIR.mkdir();
//        COMMIT_DIR.mkdir();
//        Commit init = new Commit();

//        Path git = Paths.get(".gitlet");
//        if (!Files.exists(git)) {
//            File gitlet = new File(".gitlet");
//            gitlet.mkdir();
//
//    }

//
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

        Path gitletPath = Paths.get(".gitlet");
        if (!Files.exists(gitletPath)) {
            File gitlet = new File(".gitlet");
//            gitlet.mkdir();

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
        } else {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
        }
        String message = "initial commit";
        HashMap<String, Blob> blobfile = new HashMap<>();
        String timestamp = "Wed Dec 31 00:00:00 1969 -0800";
        String SHAparent1 = null;
        String SHAparent2 = null;
        _curbranch = "master";
        if (!_curbranch.equals("master") && _branchhash != null
                && _branchhash.containsKey(_curbranch)) {
            System.out.println("A branch with that name already exists.");
        } else {
            _branchhash.put(_curbranch, _idparent);
            _splits.put(_curbranch, _idparent);
        }
        Commit commitmethod = new Commit( SHAparent1, SHAparent2, message, timestamp, blobfile, _curbranch);
        _allcommits.put(commitmethod.get_Stringhash(), commitmethod);
        _head = commitmethod;
        _idparent = _head.inithash();
        LinkedList<Commit> branchcommits = new LinkedList<>();
        branchcommits.add(_head);
        File file = new File(".gitlet/commit/" + _idparent);
        Utils.writeContents(file, Utils.serialize(commitmethod));

    }


    //    Add Method
    public void add(String filename) throws IOException {
        File f = new File(filename);
        if (!f.exists()) {
            System.out.println("File does not exist");
            System.exit(0);
        }
        if (_toremove.contains(filename)) {
            _toremove.remove(filename);
            _untracked.remove(filename);
        }
        Blob blob1 = new Blob(filename);
        Path path1 = Paths.get(".gitlet/commit/" + _idparent);
        HashMap<String, Blob> headblob = _head.get_blob();
        for (String hash : headblob.keySet()) {
            if (_head.get_blob().equals((_head.get_blob().get(hash)))) {
                if (_staged.containsKey(filename)) {
                    File old = new File("gitlet/stage/" + _staged.get(filename).get_blob());
                    old.delete();
                    _staged.remove(filename);
                    return;
                }
                return;
            }
        }
        if (!_idparent.equals(blob1.get_blob())) {
            if (!_staged.isEmpty() && _staged.containsKey(filename)) {
                File old = new File(".gitlet/stage/" + blob1.get_blob());
                old.delete();
                if (!_removed.contains(filename)) {
                    _staged.put(filename, blob1); _currblobs.put(filename, blob1);
                    File newfile = new File(".gitlet/stage/" + blob1.get_blob());
                    Utils.writeContents(newfile, Utils.serialize(blob1));
                } else {
                    File newfile = new File(filename);
                    Utils.writeContents(newfile, Utils.serialize(blob1));
                    _removed.remove(filename); _changes.remove(filename);
                }
                _currblobs.put(filename, blob1);
            } else if (_staged.isEmpty() || !_staged.containsKey(filename)) {
                if (!_removed.contains(filename)) {
                    _staged.put(filename, blob1);
                    File newfile = new File(".gitlet/stage/"
                            + blob1.get_blob());
                    Utils.writeContents(newfile, Utils.serialize(blob1));
                } else {
                    File newfile = new File(filename);
                    Utils.writeContents(newfile, Utils.serialize(blob1));
                    _removed.remove(filename);
                }
                _currblobs.put(filename, blob1);
            }
        } else {
            File old = new File(".gitlet/stage/"
                    + _staged.get(filename).get_blob());
            old.delete();
            _staged.remove(filename);
        }
    }

    public void commit(String message) throws IOException, ClassNotFoundException {
        for (String file : Utils.plainFilenamesIn(System.
                getProperty("user.dir"))) {
            if (_staged.containsKey(file) == false) {
                _untracked.add(file);
            }
        }
        if (_staged.isEmpty() && _untracked.isEmpty()) {
            System.out.println("No changes added to the commit.");
            System.exit(0);
        }
        if (message.equals("")) {
            System.out.println("Please enter a commit message");
            System.exit(0);
        }
        Date curr = new Date();
        String date = new
                SimpleDateFormat("EEE MMM d HH:mm:ss yyyy")
                .format(curr);
        String cur = date + " -0800";
        Commit com = new Commit( _idparent,  null, message, cur, _currblobs, _curbranch);
        if (_idparent != null && _idparent.equals(com.get_Stringhash())) {
            System.out.println("No changes added to commit");
        }
        _branchhash.put(_curbranch, com.get_Stringhash());
        _allcommits.put(com.get_Stringhash(), com);
        _commits.put(com.get_Stringhash(), com);
        _idparent = com.get_Stringhash();
        _branchhash.put(_curbranch, _idparent);
        _head = com;
        File newf = new File(".gitlet/commit/"
                + com.get_Stringhash());
        Utils.writeContents(newf, Utils.serialize(com));
        File stage = new File(".gitlet/stage/");
        for (File f : stage.listFiles()) {
            if (!f.isDirectory()) {
                f.delete();
            }
        }
        _toremove.clear();
        _untracked.clear();
        _removed.clear();
        _changes.clear();
        if (_staged.isEmpty() == false) {
            _staged.clear();
        }
    }


    //    method RM
    private void rm(String filename)
            throws IOException, ClassNotFoundException {
        boolean rem = true;
        File file = new File(filename);
        Commit commit = _allcommits.get(_idparent);
        HashMap<String, Blob> tracked = commit.get_blob();
        if (!file.exists() && !tracked.containsKey(filename)) {
            System.out.println("File does not exist.");
        }
        if (!_staged.isEmpty()
                && _staged.containsKey(filename)) {
            File old = new File(".gitlet/stage/"
                    + _staged.get(filename).getblobhash());
            old.delete();
            _staged.remove(filename);
            _currblobs.remove(filename);
            _untracked.add(filename);
            rem = false;
        }
        if (tracked != null & tracked.containsKey(filename)) {
            _currblobs.remove(filename);
            _toremove.add(filename);
            if (_untracked.contains(filename)) {
                _untracked.remove(filename);
                return;
            }
            File old = new File(filename);
            old.delete();
            _removed.add(filename);
            rem = false;
        }
        if (rem) {
            System.out.println("No reason to remove the file.");
        }
    }

//Method Log

    private void log() throws IOException, ClassNotFoundException {
        List<String> temp = new ArrayList<>();
        String store = null;
        for (String hash : _allcommits.keySet()) {
            if (_allcommits.get(hash).get_message().equals("initial "
                    + "commit")) {
                store = hash;
            } else {
                temp.add(hash);
            }
        }
        Collections.reverse(temp);
        temp.add(store);
        for (String hash : temp) {
            Commit com = _allcommits.get(hash);
            String rethash = hash.substring(7);
            System.out.println("===");
            System.out.println("commit " + rethash);
            String date = com.get_timestamp();
            System.out.println("Date: " + date);
            String msg = com.get_message();
            System.out.println(msg);
            System.out.println();
        }
    }


//    Global Log Method


    private void globallog()
            throws IOException, ClassNotFoundException {
        List<String> temp = new ArrayList<>();
        String store = null;
        for (String hash : _allcommits.keySet()) {
            if (_allcommits.get(hash).get_message().equals("initial commit")) {
                store = hash;
            } else {
                temp.add(hash);
            }
        }
        Collections.reverse(temp);
        temp.add(store);
        for (String hash : temp) {
            Commit com = _allcommits.get(hash);
            String rethash = hash.substring(7);
            System.out.println("===");
            System.out.println("commit " + rethash);
            String date = com.get_timestamp();
            System.out.println("Date: " + date);
            String msg = com.get_message();
            System.out.println(msg);
            System.out.println();
        }
    }

    //    Method FInd
    private void find(String message)
            throws IOException, ClassNotFoundException {
        boolean tracker = false;
        for (String hash : _allcommits.keySet()) {
            if (_allcommits.get(hash).get_message().equals(message)) {
                tracker = true;
                String id = hash.substring(7);
                System.out.println(id);
            }
        }
        if (tracker == false) {
            System.out.println("Found no commit with that message");
        }
    }

    //Method Status
    private void status() throws IOException, ClassNotFoundException {
        System.out.println("=== Branches ===");
        if (!_branchhash.isEmpty()) {
            for (String s : _branchhash.keySet()) {
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
        if (message != null && !message.equals(""))
            System.out.println(message);
        System.exit(0);

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





    public void branch(String name) {
        if (_branchhash.containsKey(name)) {
            System.out.println("A branch with that name already exists");
        } else {
            _splits.put(name, _idparent);
            _branchhash.put(name, _idparent);
        }
    }

    /** Removes the branch with NAME. */
    public void rm_branch(String name) {
        if (name.equals(_curbranch)) {
            System.out.println("Cannot remove the current branch.");
        } else if (!_branchhash.containsKey(name)) {
            System.out.println("A branch with that name does "
                    + "not exist.");
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
        if (!_allcommits.containsKey(id)) {
            System.out.println("No commit with that id exists.");
            System.exit(0);
        }
        Commit com = _allcommits.get(id);
        File dir = new File("");
        File[] files = dir.listFiles();
        if (files != null) {
            for (File f : files) {
                if ((!_staged.containsKey(f) && !_currblobs.containsKey(f))
                        || (_untracked.contains(f) || checkout_helper(_curbranch))) {
                    System.out.println("There is an untracked file "
                            + "in the way; delete it or add it first");
                    System.exit(0);
                }
            }
        }
        if (com != null) {
            for (String name : com.get_blob().keySet()) {
                if (_staged.containsKey(name)
                        || _currblobs.containsKey(name)) {
                    checkout(id, name);
                }
            }
        }
        _staged.clear();
        _curbranch = com.get_branchname();
        _branchhash.put(_curbranch, hashcode);
        _head = com;
        _idparent = hashcode;
    }

    public static String checkout(String[] args) throws IOException, ClassNotFoundException {
        if (args.length == 2) {
            String branchname = args[0];
            if (_branchhash.get(branchname) == null) {
                System.out.println("No such branch exists.");
                System.exit(0);
            }
            if (branchname.equals(args[1])) {
                System.out.println("No need to checkout the current branch.");
                System.exit(0);
            }
            HashMap<String, Blob> current_files = new HashMap<>();
            Path branchname_co = Paths.get(".getlet/commit/" + _branchhash.get(branchname));
            Commit committ = (Commit) Utils.helper2(branchname_co);
            boolean tracker = checker(branchname);
            for (String filename : committ.get_Stringfile()) {
                for (String file: Utils.plainFilenamesIn(System.getProperty("user.dir"))) {
                    if ((_untracked.contains(file) || tracker) && committ.get_blob().containsKey(file)) {
                        System.out.println(
                                "There is an untracked file in the way; delete it or add it first.");
                        System.exit(0);
                    }
                }
            }

            for (String s : committ.get_Stringfile()) {
                File newfile = new File(s);
                Blob old = committ.get_blob().get(s);
                String content = old.getcontent_string();
                Utils.writeContents(newfile, content);
                newfile.put(s, committ.get_blob().get(s));
            }

            for (String filename: _currblobs.keySet()) {
                if (current_files.containsKey(filename) == false) {
                    File newfile = new File(filename);
                    newfile.delete();
                }
            }

            _currblobs.clear.putAll(current_files);
            _idparent = _branchhash.get(branchname);
            _head = _allcommits.get(_idparent);
            _curbranch = branchname;
            File staging = new File(".gitlet/stage");
            for (File file: staging.listFiles()) {
                if (file.isDirectory() == false) {
                    file.delete();
                }
            }
            _staged.clear();

        } else if ((args.length == 3) && (args[1].equals("--"))) {
            String id;
            id = _head.get_Stringhash();
            String file = ".gitlet/commit" + id;
            if (file == null) {
                System.out.println("File does not exist in that commit.");
                System.exit(0);
            }
            if (_head.get_blob().containsKey(filename)) {
                File newfile = new File(filename);
                Blob old = _head.get_blob();
                old = old.get(filename);
                String content = old.getcontent_string();
                Utils.writeContents(newfile, content);
            }
        } else if ((args.length == 4) && (args[2].equals("--"))) {
            String id;
            String committ_id = args[2];
            if (committ_id.charAt(0) != 'c') {
                id = "commit-" + committ_id;
            } else {
                id = committ_id;
            }
            Path finish = Paths.get(".gitlet/commit/" + id);
            if (_allcommits.containsKey(id) == false) {
                System.out.println("No commit with that id exists.");
                System.exit(0);
            }

            Commit current = (Commit) Utils.helper2(finish);
            if (current.get_blob().keySet().contains(filename) == false)) {
                System.out.println("File does not exist in that commit");
                System.exit(0);
            }
            File newFile = new File(filename);
            Blob old = current.get_blob().get(filename);
            String content = old.getcontent_string();
            Utils.writeContents(newFile, content);

        } else {
            return helperErrorExit("Incorrect operands.");

        }


        public static String checkout_helper(String[] args) {
            int _length = args.length();
            List<String> directory = Utils.plainFilenamesIn(_commits).stream().filter(s -> (s.substring(0, _length)).equals(args)).collect(Collectors.toList());
            if (directory.size() > 1) {
                helperErrorExit("");
            } else if (directory.size() == 1) {
                return directory.get(0);
            }
            return null;
        }












    }

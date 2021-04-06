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
    private  HashMap<String, String> _changes;
    public static LinkedHashMap<String, String> _branchhash;
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
//    public static final File GITLET_DIR = join(CWD, ".gitlet");
//    public static final File BRANCH_DIR = join(GITLET_DIR, "refs");
//    public static final File OBJ_DIR = join(GITLET_DIR, "objective");
//    public static final File COMMIT_DIR = join(Repository.OBJ_DIR, "commits");


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
                helperErrorExit("Incorrect operands.");
            }
            gitlet1.init();
        }
        File gitl = new File(".gitlet");
        if (!gitl.exists()) {
            System.out.println("Not in an intialized Gitlet directory.");
            return;
        } else if (_command.equals("commit")) {
            if (_operand.size() != 1 || _operand.get(0) == null) {
                helperErrorExit("Incorrect operands.");
            }
            String message = _operand.get(0);
            gitlet1.commit(message);
        } else if (_command.equals("add")) {
            if (_operand.size() != 1 || _operand.get(0) == null) {
                helperErrorExit("Incorrect operands.");
            }
            String argg = _operand.get(0);
            gitlet1.add(argg);
        } else if (_command.equals("checkout")) {
            gitlet1.checkout(_operand);
        } else if (_command.equals("log")) {
            if (_operand.size() != 0) {
                helperErrorExit("Incorrect operands.");
            }
            gitlet1.log();
        } else if (_command.equals("rm")) {
            if (_operand.size() != 1) {
                helperErrorExit("Incorrect operands.");
            }
            gitlet1.rm(_operand.get(0));
        } else if (_command.equals("global-log")) {
            if (_operand.size() != 0) {
                helperErrorExit("Incorrect operands.");
            }
            gitlet1.globallog();
        } else if (_command.equals("status")) {
            if (_operand.size() != 0) {
                helperErrorExit("Incorrect operands.");
            }
            gitlet1.status();
        } else if (_command.equals("branch")) {
            if (_operand.size() != 1) {
                helperErrorExit("Incorrect operands.");
            }
            gitlet1.branch(_operand.get(0));
        } else if (_command.equals("rm_branch")) {
            if (_operand.size() != 1) {
                helperErrorExit("Incorrect operands.");
            }
            gitlet1.rm_branch(_operand.get(0));
        } else if (_command.equals("reset")) {
            if (_operand.size() != 1) {
                helperErrorExit("Incorrect operands.");
            }
            gitlet1.reset(_operand.get(0));
        } else if (_command.equals("find")) {
            if (_operand.size() != 1) {
                helperErrorExit("Incorrect operands.");
            }
            gitlet1.find(_operand.get(0));
        }
//        } else if (_command.equals("merge")) {
//            if (_operand.size() != 1) {
//                System.out.println("Incorrect operands.");
//                System.exit(0);
//            }
//            gitlet1.merge(_operand.get(0));
//        }
    }


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

    private static Repository getsaved() {
        Repository prevGitlet = null;
        if (new File(".gitlet/savedgitlet.ser").exists()) {
            //        public void saveDog() {
//            File dogsaved = Utils.join(DOG_FOLDER, this.name);
//            try {

//
            try {
                FileInputStream fin = new
                        FileInputStream(".gitlet/savedgitlet.ser");
                ObjectInputStream ois = new ObjectInputStream(fin);
                Object historyObject = ois.readObject();
                prevGitlet = (Repository) historyObject;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                //                dogsaved.createNewFile();
//            } catch (IOException exception) {

//            Utils.writeObject(dogsaved, this);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                //                exception.printStackTrace();
//            }
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
            helperErrorExit("File does not exist");
        }
        if (_toremove.contains(filename)) {
            _toremove.remove(filename);
            _untracked.remove(filename);
        }
        Blob blob1 = new Blob(filename);
        Path path1 = Paths.get(".gitlet/commit/" + _idparent);
        HashMap<String, Blob> headblob = _head.get_Blob();
        for (String hash : headblob.keySet()) {
            if (_head.get_Blob().equals((_head.get_Blob().get(hash)))) {
                if (_staged.containsKey(filename)) {
                    File old = new File("gitlet/stage/" + _staged.get(filename).get_Blob());
                    old.delete();
                    _staged.remove(filename);
                    return;
                }
                return;
            }
        }
        if (_staged.isEmpty() || !_staged.containsKey(filename)) {
            byte[] serialize_B = Utils.serialize(blob1);
            if (!_removed.contains(filename)) {

                _staged.put(filename, blob1);
                File newfile = new File(".gitlet/stage/" + blob1.get_Blob());
                Utils.writeContents(newfile, serialize_B);
            } else {
                File newfile = new File(filename);
                Utils.writeContents(newfile, serialize_B);
                _removed.remove(filename);
            }
            _currblobs.put(filename, blob1);
        } else if (!_idparent.equals(blob1.get_Blob())) {
            byte[] serialize_B = Utils.serialize(blob1);
            if (!_staged.isEmpty() && _staged.containsKey(filename)) {
                File old = new File(".gitlet/stage/" + blob1.get_Blob());
                old.delete();
                if (!_removed.contains(filename)) {
//                    addStage.containsKey(name)) {
//                        addStage.replace(name, ident);
                    _staged.put(filename, blob1);
                    _currblobs.put(filename, blob1);
                    File newfile = new File(".gitlet/stage/" + blob1.get_Blob());
                    Utils.writeContents(newfile, serialize_B);
                } else {
                    File newfile = new File(filename);

//                    Utils.readObject(head, Commit.class);
                    Utils.writeContents(newfile, serialize_B);
                    _removed.remove(filename);
//                    Utils.readObject(add, Stage.class);
//                    rUtils.readObject(rm, Stage.class);
                    _changes.remove(filename);
                }
                _currblobs.put(filename, blob1);
            } else {
                File old = new File(".gitlet/stage/"
                        + _staged.get(filename).get_Blob());
                old.delete();
                _staged.remove(filename);
//                Utils.writeObject(currFile, blobb);
//                Utils.writeObject(remFile, remStage);
            }
        }
    }
    public static Object serialize_Helper(Path p) {
        File tempo = p.toFile();
        Object o =  null;
        try {
            ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream(tempo));
            o = in.readObject();
            in.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return o;
    }

    public void commit(String message) throws IOException, ClassNotFoundException {
        for (String file : Utils.plainFilenamesIn(System.
                getProperty("user.dir"))) {
            if (_staged.containsKey(file) == false) {
                _untracked.add(file);
            }
        }
        if (_staged.isEmpty() && _untracked.isEmpty()) {
            helperErrorExit("No changes added to the commit.");
        }
        if (message.equals("")) {
            helperErrorExit("Please enter a commit message");
        }
        Date currenttime = new Date();
        String datec = new
                SimpleDateFormat("EEE MMM d HH:mm:ss yyyy").format(currenttime);
        String current = datec + " -0800";
        Commit committed = new Commit( _idparent,  null, message, current, _currblobs, _curbranch);
        boolean equalhash = _idparent.equals(committed.get_Stringhash());
        boolean parentcheck = (_idparent != null);
        if (equalhash && parentcheck ) {
            System.out.println("No changes added to commit");
        }
        _branchhash.put(_curbranch, committed.get_Stringhash());
        _allcommits.put(committed.get_Stringhash(), committed);
        _commits.put(committed.get_Stringhash(), committed);
        _idparent = committed.get_Stringhash();
        _branchhash.put(_curbranch, _idparent);
        _head = committed;
        File newf = new File(".gitlet/commit/" + committed.get_Stringhash());
        Utils.writeContents(newf, Utils.serialize(committed));
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
    private void rm(String file) {
        boolean rm = true;
        File file = new File(file);
        Commit commit = _allcommits.get(_idparent);
        HashMap<String, Blob> tracked = commit.get_Blob();
        boolean emptystage = (_staged.isEmpty() == false);
        boolean trackedkey = (tracked.containsKey(file) == false);
        boolean containkey = _staged.containsKey(file);
        boolean existfile = (file.exists() == false);

        if (trackedkey && existfile) {
            helperErrorExit("File does not exist.");
        }
        if (rm) {
            helperErrorExit("No reason to remove the file.");
        }
        if (tracked != null & tracked.containsKey(file)) {
            _currblobs.remove(file);
            _toremove.add(file);
            if (_untracked.contains(file)) {
                _untracked.remove(file);
                return;
            }
            File old = new File(file);
            old.delete();
            _removed.add(file);
            rm = false;
        }
        if  (containkey && emptystage) {
            String addold = _staged.get(file).getblobhash();
            File old = new File(".gitlet/stage/" + addold);
            old.delete();
            _staged.remove(file);
            _currblobs.remove(file);
            _untracked.add(file);
            rm = false;
        }


    }

//Method Log

    private void log() throws IOException, ClassNotFoundException {
        List<String> arraylist= new ArrayList<>();
        String save = null;
        for (String hash : _allcommits.keySet()) {
            if (_allcommits.get(hash).get_Message().equals("initial commit")) {
                save = hash;
            } else {
                arraylist.add(hash);
            }
        }
        Collections.reverse(arraylist);
        arraylist.add(save);
        for (String h : arraylist) {
            Commit committed = _allcommits.get(h);
//            String reash = ;
            System.out.println("===");
            System.out.println("commit " + h.substring(7));
//            String date = ;
            System.out.println("Date: " + committed.get_Timestamp());
            System.out.println(committed.get_Message());
            System.out.println();
        }
    }


//    Global Log Method


    private void globallog()
            throws IOException, ClassNotFoundException {
        List<String> temp = new ArrayList<>();
        String store = null;
        for (String hash : _allcommits.keySet()) {
            if (_allcommits.get(hash).get_Message().equals("initial commit")) {
                store = hash;
            } else {
                temp.add(hash);
            }
        }
        Collections.reverse(temp);
        temp.add(store);
        for (String hash : temp) {
            Commit com = _allcommits.get(hash);
            String r = hash.substring(7);
            System.out.println("===");
            System.out.println("commit " + r);
            String date = com.get_Timestamp();
            System.out.println("Date: " + date);
            String msg = com.get_Message();
            System.out.println(msg);
            System.out.println();
        }
    }

    //    Method FInd
    private void find(String message)
            throws IOException, ClassNotFoundException {
        boolean tracker = false;
        for (String hash : _allcommits.keySet()) {
            if (_allcommits.get(hash).get_Message().equals(message)) {
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
        if (!_allcommits.containsKey(id)) {
            helperErrorExit("No commit with that id exists.");
        }
        Commit comitted = _allcommits.get(id);
        File d = new File("");
        File[] fi = d.listFiles();
        if (fi != null) {
            for (File f : fi) {
                boolean stageK = (_staged.containsKey(f) == false);
                boolean containT = _untracked.contains(f);
                boolean containK = (_currblobs.containsKey(f) == false);
                boolean currb = tracker_helper(_curbranch);
                boolean StageCK = (stageK && containK);
                if (StageCK || containT || currb) {
                    helperErrorExit("There is an untracked file in the way; delete it or add it first");
                }
            }
        }
        if (comitted != null) {
            for (String name : comitted.get_Blob().keySet()) {
                boolean stageK = _staged.containsKey(name);
                boolean currK = _currblobs.containsKey(name);
                if (stageK || currK) {
                    checkout_Commited(id, name);
                }
            }
        }
        _staged.clear();
        _curbranch = comitted.get_Branchname();
        _branchhash.put(_curbranch, hashcode);
        _head = comitted;
        _idparent = hashcode;
    }



    public void checkout_Branch(String branch) {

        HashMap<String, Blob> current_files = new HashMap<>();
        Path branchname_co = Paths.get(".getlet/commit/" + _branchhash.get(branch));

        Commit committ = (Commit) serialize_Helper(branchname_co);
        boolean tracker = tracker_helper(branch);
        for (String filename : committ.get_Stringfile()) {
//            String file : Utils.plainFilenamesIn(System.getProp
            for (String file : Utils.plainFilenamesIn(System.getProperty("user.dir"))) {
//                Path branchname_co = Paths.get(".getlet/commit/" + _branchhash.get(branchname));
//                Commit committ = (Commit) Utils.helper2(branchname_co);
                boolean file_Contain = committ.get_Blob().containsKey(file);
                boolean tracker_Contain = (_untracked.contains(file) || tracker);
                if (tracker_Contain && file_Contain ) {
                    helperErrorExit("There is an untracked file in the way; delete it or add it first.");
                }
            }
        }

        for (String s : committ.get_Stringfile()) {
            File newfile = new File(s);
            Blob old = committ.get_Blob().get(s);
//            File CWD = new File(CWD.getPath() + "/" + set.getKey());
//            File value = new File(stage.getPath() + "/" + set.getValue());
            String content = old.getcontent_string();
            Utils.writeContents(newfile, content);
            current_files.put(s, committ.get_Blob().get(s));
        }

        for (String filename : _currblobs.keySet()) {
//            Utils.writeObject(add, new Stage());
//
            if (current_files.containsKey(filename) == false) {
                File newfile = new File(filename);
                newfile.delete();
            }
        }

        _currblobs.clear();
        _currblobs.putAll(current_files);
        _idparent = _branchhash.get(branch);
//        Utils.writeObject(remFile, new Stage());
        _head = _allcommits.get(_idparent);
        _curbranch = branch;
        File staging = new File(".gitlet/stage");
        for (File file : staging.listFiles()) {
            if (file.isDirectory() == false) {
                file.delete();
            }
        }
        _staged.clear();
    }

    public void checkout_File(String file) {
        String commid = _head.get_Branchname();
        String n_File = ".gitlet/commit/" + commid;
        if (n_File == null) {
            helperErrorExit("File does not exist in that commit.");
        }
        if (_head.get_Blob().containsKey(file)) {
            File file_Updated = new File(file);
            Blob old = _head.get_Blob().get(file);
            String info =  old.getcontent_string();
            Utils.writeContents(file_Updated, info);
        }

    }

    public void checkout_Commited(String id, String file) {
        String id_C;
//        char id_First = id.charAt(0);
//        boolean checkout_helper = id_First
        if (id.contains("commit")) {
            id_C = "commit-" + id;
        } else {
            id_C = id;
        }
        Path file_Commit = Paths.get(".gitlet/commit/" + id_C);
        if (_allcommits.containsKey(id_C) == false) {
            helperErrorExit("No commit with that id exists.");
        }
        Commit curr = (Commit) serialize_Helper(file_Commit);
        if (!curr.get_Blob().keySet().contains(file)) {
            helperErrorExit("File does not exist in that commit");
        }
//        File file_Updated = new File(file);
//        Blob old = _head.get_blob().get(file);
//        String info =  old.getcontent_string();
//        Utils.writeContents(file_Updated, info);
        File file_Updated = new File(file);
        Blob old = curr.get_Blob().get(file);
        String contents = old.getcontent_string();
        Utils.writeContents(file_Updated, contents);
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
            checkout_Branch(branch);
        } else if (operand.size() == 2) {
            String file = operand.get(1);
            if ((operand.get(0).equals("--") == false)) {
                helperErrorExit("Incorrect operands");
            }
            checkout_File(file);
        } else if (operand.size() == 3) {
            boolean operand_ = (operand.get(1).equals("--"));
            if (operand_ == false) {
                helperErrorExit("Incorrect operands.");
            }
            checkout_Commited(operand.get(0), operand.get(2));
        }
    }




        public boolean tracker_helper(String branch) {
//            int _length = args.length();
            Path finish = Paths.get(".gitlet/commit/" + _branchhash.get(branch));
            HashMap<String, Blob> header = _head.get_Blob();
//            List<String> directory = Utils.plainFilenamesIn(_commits).stream().filter(s -> (s.substring(0, _length)).equals(args)).collect(Collectors.toList());
//            if (directory.size() > 1) {
            Commit com = (Commit) serialize_Helper(finish);
            for (String f : Utils.plainFilenamesIn(System.getProperty("user.dir"))) {
                boolean headerr = _head.get_Stringfile().isEmpty();
                boolean headercon = (_head.get_Stringfile().contains(f) == false);
                if ( headerr || headercon) {
                    return true;
                } else if (header.isEmpty()) {
                    String info = header.get(f).getcontent_string();
                    Blob blob1 = new Blob(f);
                    if (blob1.getcontent_string().equals(info) == false) {
                        return true;
                    }
                }
            }
            return false;
        }













//1.find the split point
//    -track back ancestors within the tree
//    split pint, current brand(head), given branch
//    three scenarios: base) a file in the split point, in the current and given, same content--> don't do anything because we want to combine
//    2. One file in current, and the other in given(different),
//    but not present in the split point, all three versions
//    in the file is different(deleting the file counts),
//    merge conflict appears
//    3. Modified in one commmit but not the other, this file is modified in EITHER given or current
//    ex) present in the commit and not changed within the split point, given branch is changed--> keep the changed one
//    to-do: are these two files exist in given or current helper function that checks whether the files are in the given and current




    }

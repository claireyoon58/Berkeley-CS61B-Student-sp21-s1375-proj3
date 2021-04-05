package gitlet;

import java.io.Serializable;
import java.util.HashMap;
import java.util.*;
//import java.util.logging.Logger;
//import java.nio.charset.StandardCharsets;
//import java.security.MessageDigest;
//import java.util.Date;

/** Represents a gitlet commit object.
 *
 *  @author Claire Yoon
 */
public class Commit implements Serializable {

//    getblbs
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /**
     * The message of this Commit.
     */
    private String message;

//    private Date dateofcommit;
    private String time;
    private String SHAparent1;
    private String SHAparent2;
    private List<String> filename;
    private HashMap<String, Blob> storingfile;
    private HashMap<String, List<Object>> hashmap;
    private String storehashcode;
    private String storebranchname;

    public String get_Branchname() {
        return storebranchname;
    }

    public List<String> get_Stringfile() {
        return filename;
    }

    public String get_Message() {
        return message;
    }

    public String get_Timestamp() {
        return time;
    }

    public HashMap<String, Blob> get_Blob() {
        return storingfile;
    }



    public Commit(String original, String original2, String message, String time, HashMap<String, Blob> blobfiles, String branchname) {
        this.hashmap = new HashMap<>();
        this.message = message;
        this.storingfile = blobfiles;
        this.SHAparent1 = original;
        this.SHAparent2 = original2;
        this.storebranchname = branchname;
        this.storehashcode = commithc();
        this.filename = new ArrayList<>();
        this.filename.addAll(blobfiles.keySet());


        LinkedList<Object> newlist = new LinkedList<>();
        newlist.add(message);
        newlist.add(blobfiles);
        newlist.add(SHAparent1);
        newlist.add(SHAparent2);

        Boolean tracker = false;


        for (String key : hashmap.keySet()) {
            if (hashmap.get(key).equals(newlist)) {
                tracker = true;
                storehashcode = key;
                break;
            }
        }
        if (tracker == false) {
            storehashcode = commithc();
        }
        storehashcode = commithc();

        }





    public String commithc() {
        List<Object> list1 = new ArrayList<>();
        list1.add(message);
        if (storingfile.size() != 0) {
            for (String b : storingfile.keySet()) {
                list1.add(b);
            }
        }
        if (SHAparent1 != null) {
            list1.add(SHAparent1);
        }
        if (SHAparent2 != null) {
            list1.add(SHAparent2);
        }
        String commith = Utils.sha1(list1);
        String hash = "commit-" + commith;
        hashmap.put(hash, list1);
        return hash;
    }


    public String inithash() {
        ArrayList<Object> init = new ArrayList<>();
        init.add("initial commit");
        String hash = Utils.sha1(init);
        String hashcode = "commit-" + hash;
        return hashcode;
    }


    public String get_Stringhash() {
        return storehashcode;
    }

}





//new commit will want have the same parent file but want to make changes based in the staging area, make changes to the storing hashmap
//    write COmmit to a file
//    new Commit store= parent


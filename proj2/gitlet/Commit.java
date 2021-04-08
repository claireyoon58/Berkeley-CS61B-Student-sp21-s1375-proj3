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
    private String shaparent1;
    private String shaparent2;
    private List<String> filename;
    private HashMap<String, Blob> blob;
    private HashMap<String, List<Object>> hashmap;
    private String storehashcode;
    private String storebranchname;

    public Commit() {

    }

    public String getstringbranch() {
        return storebranchname;
    }

    public List<String> getstringfile() {
        return filename;
    }

    public String getmessage() {
        return message;
    }

    public String gettime() {
        return time;
    }

    public HashMap<String, Blob> getblob() {
        return blob;
    }



    public Commit(String message, String time, HashMap<String, Blob> blob,
                  String p1, String p2, String branch) {
//                  String time, HashMap<String, Blob> blob, String branchname) {
        this.hashmap = new HashMap<>();
        this.message = message;
        this.blob = blob;
        this.time = time;
        this.shaparent1 = p1;
        this.shaparent2 = p2;
        this.storebranchname = branch;
        this.storehashcode = commithc();
        this.filename = new ArrayList<>();
        this.filename.addAll(blob.keySet());


        LinkedList<Object> newlist = new LinkedList<>();
        newlist.add(message);
        newlist.add(blob);
        newlist.add(shaparent1);
        newlist.add(shaparent2);

        Boolean tracker = false;


        for (String key : hashmap.keySet()) {
            if (hashmap.get(key).equals(newlist)) {
                tracker = true;
                storehashcode = key;
                break;
            }
        }
        if (!tracker) {
            storehashcode = commithc();
        }
        storehashcode = commithc();

    }





    public String commithc() {
        List<Object> list1 = new ArrayList<>();
        list1.add(message);
        if (blob.size() != 0) {
            for (String b : blob.keySet()) {
                list1.add(b);
            }
        }
        if (shaparent1 != null) {
            list1.add(shaparent1);
        }
        if (shaparent2 != null) {
            list1.add(shaparent2);
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


    public String getstringhash() {
        return storehashcode;
    }

}





//new commit will want have the same parent file
// but want to make changes based in the staging area,
// make changes to the storing hashmap
//    write COmmit to a file
//    new Commit store= parent


package gitlet;

import java.io.Serializable;
import java.io.File;
import java.util.*;

public class Blob extends Commit implements Serializable {
    static final long serialVersionUID = 0;
    static boolean hasMergeConflict = false;
    public String _blobhash;
    public String _getfilename;
    public String _makestringcont;
    private HashMap<String, List<Object>> hashmap1= new HashMap<>();
    private String SHA;
    private String shortSHA;
    private String fileN;
    private File dir;
    private byte[] content;


    private String blob_hash() {
        List<Object> list1 = new ArrayList<>();
        list1.add(_getfilename);
        list1.add(content);
        list1.add(_makestringcont);
        String hh = Utils.sha1(list1);
        String hash_blob = "blob-" + hh;
        hashmap1.put(hash_blob, list1);
        return hash_blob;
    }

    public Blob(String nameOfFile) {
        File fileN = new File(nameOfFile);
//        _getfilename = nameOfFile;
        _getfilename = nameOfFile;
        content = Utils.serialize(fileN);
//        _makestringcont = Utils.readContentsAsString(fileN);
        List<Object> list1 = new ArrayList<>();
        list1.add(_getfilename);
        list1.add(content);
        list1.add(_makestringcont);
        String hh = Utils.sha1(list1);
//        dir = Utils.join(dir_loc, nameOfFile);
        Boolean tracker = false;
//        if (!dir.exists()) {
        for (String key: hashmap1.keySet()) {
            if (hashmap1.get(key) == list1) {
                tracker = true;
                _blobhash = key;
                break;
            }
        }
//            content = null;
        if (tracker == false) {
            _blobhash = blob_hash();
        }
//        } else {
//            content = Utils.readContents(dir);
//        }
//    }

    }
//


//
//
//    public Blob(Blob given) {
//        this.fileN = given.fileN;
//        this.dir = given.dir;
//    }

    public String getcontent_string() {
        return _makestringcont;
    }

    boolean compareBlobs(Blob blob1, Blob blob2) {
        return blob1.shortSHA.equals(blob2.shortSHA);
    }

    public String getfilename() {
        return _getfilename;
    }

    boolean compareContent(Blob blob1) {
        return shortSHA.equals(blob1.shortSHA);
    }


    public String getblobhash() {
        return _blobhash;
    }




}


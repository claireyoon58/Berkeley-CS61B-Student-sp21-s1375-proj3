package gitlet;

import java.io.Serializable;
import java.io.File;
import java.util.*;

public class Blob extends Commit implements Serializable {
    static final long serialVersionUID = 0;
    static boolean hasMergeConflict = false;
    private String _blobhash;
    private String _getfilename;
    private String _makestringcont;
    private HashMap<String, List<Object>> hashmap1 = new HashMap<>();
    private String SHA;
    private String shortSHA;
//    private String fileN;
    private File dir;
    private byte[] content;

    public Blob(String nameOfFile) {
//        super();
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
        if (!tracker) {
            _blobhash = blobhash();
        }
//        } else {
//            content = Utils.readContents(dir);

    }


    private String blobhash() {
        List<Object> list1 = new ArrayList<>();
        list1.add(_getfilename);
        list1.add(content);
        list1.add(_makestringcont);
        String hh = Utils.sha1(list1);
        String hashblob = "blob-" + hh;
        hashmap1.put(hashblob, list1);
        return hashblob;
    }


//


//
//
//    public Blob(Blob given) {
//        this.fileN = given.fileN;
//        this.dir = given.dir;
//    }

    public String getcontentstring() {
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

//    public String getblob() {
//        return _blob
//    }

//
//    public HashMap<String, Blob> getblob() {
//        return blob;
//    }
}


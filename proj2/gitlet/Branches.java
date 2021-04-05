package gitlet;

import java.io.Serializable;

public class Branches implements Serializable {
    static final long serialVersionUID = 0;

    String shasplit;
    String name;
    String shalast; //sha of commit

    //outdated
    public Branches(String n, String spl) {
        name = n;
        shasplit = spl;
        shalast = spl;
    }

}

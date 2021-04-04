package gitlet;

import java.io.Serializable;

public class Branches implements Serializable {
    static final long serialVersionUID = 0;

    String SHASplit;
    String name;
    String SHALast; //sha of commit

    //outdated
    public Branches(String n, String spl) {
        name = n;
        SHASplit = spl;
        SHALast = spl;
    }

}

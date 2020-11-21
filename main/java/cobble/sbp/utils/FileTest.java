package cobble.sbp.utils;

import java.io.File;

public class FileTest {
    public static boolean main(String args) {
        File f = new File(args);
        System.out.println(f + (f.exists()? " is found " : " is missing "));
        return f.exists()? true : false;
    }
}

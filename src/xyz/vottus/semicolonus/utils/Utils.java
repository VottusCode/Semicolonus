package xyz.vottus.semicolonus.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;

public class Utils {

    public static boolean exists(String path) {
        return new File(path).exists();
    }

    public static boolean isFile(String path) {
        return new File(path).isFile();
    }

    public static boolean createFile(String path) {
        try {
            return new File(path).createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteFile(String file) {
        return new File(file).delete();
    }

    public static boolean getOverride(String[] args) {
        try {
            return args[2].equalsIgnoreCase("--override");
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }
}

package utils;

import com.sun.jna.platform.win32.WinReg;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.Stack;

import static utils.ConstantForAll.DIRECTORY_TEMP;
import static utils.ConstantForAll.DIRECTORY_USER_PROG;

public class UtilsFilesAndFolders {

    private static String getDirectoryProgramDataForCfg777plus() {
        if (System.getenv("PROGRAMDATA") == null)
            return System.getenv("APPDATA") + "\\" + DIRECTORY_USER_PROG;
        else
            return System.getenv("PROGRAMDATA") + "\\" + DIRECTORY_USER_PROG;
    }

    public static String getUserDirectoryMyDocuments(String myDir) {
        return getUserDirectoryMyDocuments() + "\\" + myDir;
    }

    private static String getUserDirectoryMyDocuments() {
        return new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
    }

    public static File createTempDir() {
        return createTempDirectory(DIRECTORY_TEMP);
    }

    public static File getTempDir() {
        return getTempDirectory(DIRECTORY_TEMP);
    }

    public static File getTempDirectory(String strDirName) {
        final File dirTemp = new File(FileUtils.getTempDirectory().getAbsolutePath() + "/" + strDirName);
        if (Files.exists(Paths.get(dirTemp.toURI()), LinkOption.NOFOLLOW_LINKS)) return dirTemp;
        dirTemp.mkdir();
        return dirTemp;
    }

    private static File createTempDirectory(String strDirName) {
        final File dirTemp = new File(FileUtils.getTempDirectory().getAbsolutePath() + "/" + strDirName);
        dirTemp.mkdir();
        return dirTemp;
    }

    private static void createHookForDelTempDir(final File dirTemp) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                delStacklessDirAndFiles(dirTemp);
            }
        });
    }

    static void delStacklessDirAndFiles(File dirForDel) {
        String root = dirForDel.getAbsolutePath();
        Stack<String> dirStack = new Stack<>();
        dirStack.push(root);
        while (!dirStack.empty()) {
            String dir = dirStack.pop();
            try {
                File f = new File(dir);
                if (f.listFiles().length == 0) {
                    f.delete();
                } else {
                    dirStack.push(dir);
                    for (File ff : f.listFiles()) {
                        if (ff.isFile())
                            ff.delete();
                        else if (ff.isDirectory())
                            dirStack.push(ff.getPath());
                    }
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }
}

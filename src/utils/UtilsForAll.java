package utils;

import main.gui.GUIProgram;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.awt.event.InputEvent;
import java.io.*;
import java.net.URL;
import java.util.Objects;
import java.util.prefs.Preferences;

import static utils.ConstantForAll.*;
import static utils.UtilsFilesAndFolders.delStacklessDirAndFiles;
import static utils.UtilsFilesAndFolders.getTempDirectory;

public class UtilsForAll {

    public static void setSwingForShowGUI() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException |
                IllegalAccessException | UnsupportedLookAndFeelException  e) {
            JOptionPane.showMessageDialog(null, "e.getMessage()");
        }
    }

    public static void log(String iStr) {
        System.out.println(iStr);
    }

    public static String getStringInCp1251(String strUTF8) {
        byte[] byteBuff = new byte[0];
        try {
            byteBuff = strUTF8.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String str1251 = null;
        try {
            str1251 = new String(byteBuff, "Cp1251");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str1251;
    }

    public static byte[] readByteFile(String path) throws IOException {
        return readByteFile(path, -1);
    }

    public static byte[] readByteFile(String path, int len) throws IOException {
        ByteArrayOutputStream out = null;
        InputStream input = null;
        boolean modeLen = false;
        if (len != -1) modeLen = true;
        int cntData = 0;
        try {
            out = new ByteArrayOutputStream();
            input = new BufferedInputStream(new FileInputStream(path));
            int data;
            while ((data = input.read()) != -1) {
                out.write(data);
                cntData++;
                if (modeLen && (cntData == len)) break;
            }
        } finally {
            if (null != input) {
                input.close();
            }
            if (null != out) {
                out.close();
            }
        }
        return out.toByteArray();
    }

    public static byte getByteDD(int i) {
        int x, y;
        if (i == 0) return 0;
        x = i / 10;
        y = i - x * 10;
        return (byte) ((byte) (x << 4) + (y));
    }

    public static void goRobotPressKey(int codeKey) {
        Robot robot;
        try {
            GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice screen = env.getDefaultScreenDevice();
            robot = new Robot(screen);
            robot.keyPress(codeKey);
        } catch (AWTException ex) {
            ex.printStackTrace();
        }
    }

    public static void goRobotMoveAndClick(Point point) {
        Robot robot;
        goRobotMove(point);
        try {
            GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice screen = env.getDefaultScreenDevice();
            robot = new Robot(screen);
            robot.mousePress(InputEvent.BUTTON1_MASK);
        } catch (AWTException ex) {
            ex.printStackTrace();
        }
    }

    public static void goRobotMove(Point point) {
        Robot robot;
        try {
            GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice screen = env.getDefaultScreenDevice();
            robot = new Robot(screen);
            robot.mouseMove(point.x, point.y);
        } catch (AWTException ex) {
            ex.printStackTrace();
        }
    }

    public static void exitFromProgram() {
        GUIProgram.savePosAndSizeMain();
        File dirFile = new File(String.valueOf(getTempDirectory(DIRECTORY_TEMP)));
        delStacklessDirAndFiles(dirFile);
        System.exit(0);
    }

    public static String getPrefIntAsString(String prefName, int presetInt) {
        Preferences prefs = Preferences.userRoot();
        String iStr;
        try {
            iStr = Integer.toString(
                    prefs.getInt(prefName, presetInt));
        } catch (NumberFormatException ex) {
            iStr = Integer.toString(presetInt);
        }
        return iStr;
    }

    public static String getPrefIntAsString(String prefName, int presetInt, boolean modeNotZero) {
        Preferences prefs = Preferences.userRoot();
        String iStr;
        int iInt = prefs.getInt(prefName, presetInt);
        if (iInt == 0) iInt = presetInt;
        try {
            iStr = Integer.toString(iInt);
        } catch (NumberFormatException ex) {
            iStr = Integer.toString(presetInt);
        }
        return iStr;
    }

    public static String getPrefString(String prefName, String presetStr) {
        Preferences prefs = Preferences.userRoot();
        String iStr = prefs.get(prefName, presetStr);
        if (Objects.equals(iStr, "")) iStr = presetStr;
        return iStr;
    }

    public static String getPrefIntAsStringWithZero(String prefName, int presetInt, boolean modeNotZero) {
        Preferences prefs = Preferences.userRoot();
        String iStr;
        int iInt = prefs.getInt(prefName, presetInt);
        try {
            iStr = Integer.toString(iInt);
        } catch (NumberFormatException ex) {
            iStr = Integer.toString(presetInt);
        }
        return iStr;
    }

    public static DefaultTreeCellRenderer setNewIconForTreeNode() {
        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
        URL resURL = utils.UtilsForAll.getMainClass().getResource(NODE_OPEN_ICON);
        renderer.setClosedIcon(new ImageIcon(resURL));
        renderer.setOpenIcon(new ImageIcon(resURL));
        return renderer;
    }

    //вывести 16-ричное, состоящее из 2-х символов
    public static String getHexStrHH(byte b) {
        String str = Integer.toHexString((byte) (b & 0xFF)).toUpperCase();
        if (str.length() < 2) str = "0" + str;
        if (str.length() > 2) str = str.substring(str.length() - 2, str.length());
        return str;
    }

    //вывести десятичное с 0 впереди, если состоит из 1 символа
    public static String getDecStrDD(int d) {
        if (d < 0) return "00";
        String str = Integer.toString(d);
        if (str.length() < 2) str = "0" + str;
        return str;
    }

    //русификация чосера
    private static JFileChooser setUpdateUI(JFileChooser choose) {
        UIManager.put("FileChooser.openButtonText", "Открыть");
        UIManager.put("FileChooser.cancelButtonText", "Отмена");
        UIManager.put("FileChooser.lookInLabelText", "Смотреть в");
        UIManager.put("FileChooser.fileNameLabelText", "Имя файла");
        UIManager.put("FileChooser.filesOfTypeLabelText", "Тип файла");

        UIManager.put("FileChooser.saveButtonText", "Сохранить");
        UIManager.put("FileChooser.saveButtonToolTipText", "Сохранить");
        UIManager.put("FileChooser.openButtonText", "Открыть");
        UIManager.put("FileChooser.openButtonToolTipText", "Открыть");
        UIManager.put("FileChooser.cancelButtonText", "Отмена");
        UIManager.put("FileChooser.cancelButtonToolTipText", "Отмена");

        UIManager.put("FileChooser.lookInLabelText", "Папка");
        UIManager.put("FileChooser.saveInLabelText", "Папка");
        UIManager.put("FileChooser.fileNameLabelText", "Имя файла");
        UIManager.put("FileChooser.filesOfTypeLabelText", "Тип файлов");

        UIManager.put("FileChooser.upFolderToolTipText", "На один уровень вверх");
        UIManager.put("FileChooser.newFolderToolTipText", "Создание новой папки");
        UIManager.put("FileChooser.listViewButtonToolTipText", "Список");
        UIManager.put("FileChooser.detailsViewButtonToolTipText", "Таблица");
        UIManager.put("FileChooser.fileNameHeaderText", "Имя");
        UIManager.put("FileChooser.fileSizeHeaderText", "Размер");
        UIManager.put("FileChooser.fileTypeHeaderText", "Тип");
        UIManager.put("FileChooser.fileDateHeaderText", "Изменен");
        UIManager.put("FileChooser.fileAttrHeaderText", "Атрибуты");

        UIManager.put("FileChooser.acceptAllFileFilterText", "Все файлы");
        choose.updateUI();

        return choose;
    }

    public static byte calcOneByteCRC(byte[] buff, int startPos, int lenByte) {
        byte resultCRC = 0;
        for (int i = startPos; i < (startPos + lenByte); i++) {
            resultCRC += buff[i] & 0xFF;
        }
        return resultCRC;
    }

    public static short calcTwoByteCRC(byte[] buff, int lenByte) {
        int resultCRC = 0;
        for (int i = 0; i < lenByte; i++) {
            resultCRC += buff[i] & 0xFF;
        }
        return (short) resultCRC;
    }

    public static int compareUnsignedByte(byte a, byte b) {
        return Byte.compare((byte) (a ^ 0x80), (byte) (b ^ 0x80));
    }

    public static int compareUnsignedShort(short a, short b) {
        return Short.compare((short) (a ^ 0x8000), (short) (b ^ 0x8000));
    }

    public static String getFileNameWithSaveChooser(String strFileName, String strTitle) {
        JFileChooser fileToSave = new JFileChooser();
        UtilsForAll.setUpdateUI(fileToSave);
        fileToSave.setDialogTitle(strTitle);
        fileToSave.setSelectedFile(new File(strFileName));
        fileToSave.setCurrentDirectory(new File(""));
        if (fileToSave.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            return fileToSave.getSelectedFile().toString();
        } else strFileName = "";
        return strFileName;
    }

    public static String getFileNameWithOpenChooser(String strFileName, String strTitle) {
        JFileChooser fileToSave = new JFileChooser();
        UtilsForAll.setUpdateUI(fileToSave);
        fileToSave.setDialogTitle(strTitle);
        fileToSave.setSelectedFile(new File(strFileName));
        fileToSave.setCurrentDirectory(new File(""));
        if (fileToSave.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            return fileToSave.getSelectedFile().toString();
        } else strFileName = "";
        return strFileName;
    }

    public static Class getMainClass() {
        return main.Main.class;
    }

    public static String getStrFromBufferForLogger(byte[] iBuff, int iBuffLen, boolean modeInOut) {
        String strLogger = "--->>: ";
        if (modeInOut) strLogger = "<<---: ";
        for (int i = 0; i < iBuffLen; i++) {
            strLogger += utils.UtilsForAll.getHexStrHH((byte) (iBuff[i] & 0xFF)) + " ";
        }
        return strLogger;
    }

    public static Color getBackgroundTextFildColor() {
        return new Color(229, 241, 251);
    }
}



package utils;

import java.util.logging.Level;

public class ConstantForAll {
    public static boolean DEBUG = false;
    public static String PROGRAM_VERSION = "0.0.1";
    public static String PROGRAM_TITLE = DEBUG ? "Шаблон программы [DEBUG]" :
            "Шаблон программы";
    private static final String MODULE_SIGN = "ProgramTemplate";

    static Level LOG_LEVEL = Level.ALL;
    public static final String LS = System.getProperty("line.separator");

    //логгер
    static String LOG_FILENAME = MODULE_SIGN + ".log";

    //настройки
    public static String XML_ROOT_NODE = "Root" + MODULE_SIGN;

    //меню
    public static String MENU_COMMAND = "/res/menu/MenuCommand.xml";
    public static String MENU_PARAM = "/res/menu/MenuParam.xml";

    //текстовые файлы
    public static String TXT_ABOUT = "/res/txt/about.txt";
    public static String TXT_HISTORY = "/res/txt/history.txt";

    //директория программы
    public static String DIRECTORY_USER_PROG = MODULE_SIGN + "\\";
    public static String DIRECTORY_USER_PROG_LOG = MODULE_SIGN + "\\Log\\";
    public static String DIRECTORY_USER_PROG_SET = MODULE_SIGN + "\\Set\\";
    public static final String FILE_XML_PARAMS = MODULE_SIGN + ".xml";

    //временная директория
    public static String DIRECTORY_TEMP = MODULE_SIGN + "Tmp";
    public static String FILE_XML_PARAMS_TEMP = "temp.xml";

    //изображения
    public static String RES_IMG = "/res/img/";
    public static String ICO_PNG_16 = "/res/img/oos_16.png";
    public static String ICO_PNG_32 = "/res/img/oos_32.png";
    public static String ICO_PNG_48 = "/res/img/oos_48.png";
    public static String ICO_PNG_64 = "/res/img/oos_64.png";
    public static String NODE_OPEN_ICON = "/res/img/list.gif";
    public static String BTN_DLG_OPEN = "/res/img/btnopen.gif";
    public static String BTN_DLG_SAVE = "/res/img/btnsave.gif";

}


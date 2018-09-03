package utils;

import main.xml.XMLSettings;
import main.xml.XMLSettingsParsing;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.logging.*;

import static utils.ConstantForAll.*;
import static utils.ConstantForAll.DIRECTORY_USER_PROG;
import static utils.ConstantForAll.DIRECTORY_USER_PROG_SET;

public class UtilsLogger {

    public static Logger setupLoggerAndSettings() {
        Logger logger = setupLogger();

        if (!XMLSettings.getInstance().isXMLSettingsFile()){
            JOptionPane.showMessageDialog(null, "Версия программы меньше чем версия файла настроек.\nПрограмма будет закрыта.");
            return null;
        }
        XMLSettingsParsing xmlSettingsParsing = XMLSettingsParsing.getInstance();
        Level level = xmlSettingsParsing.getLogLevelFromSettings();
        if (logger != null) {
            logger.setLevel(level==null ? LOG_LEVEL : level);
            if (xmlSettingsParsing.getIsLogFromSettings()){
                logger.fine("Set Logger ON");
            }else{
                logger.fine("Set Logger OFF");
                logger.setLevel(Level.OFF);
            }
        }
        return logger;
    }

    private static Logger setupLogger() {
        Logger logger = Logger.getLogger(utils.UtilsForAll.getMainClass().getName());
        if (DEBUG) {
            setLoggerConsoleHandler(logger);
        } else {
            if (!setLoggerFileHandler(logger)) {
                JOptionPane.showMessageDialog(null, "Ошибка доступа к настройкам логгера");
                return null;
            }
        }
        logger.fine("Logger Ok!");
        return logger;
    }

    private static boolean setLoggerFileHandler(Logger logger) {
        //удалить все хэндлерсы для логгера
        Handler[] handlers = logger.getHandlers();
        for (Handler handler : handlers) {
            logger.removeHandler(handler);
        }
        //добавить новый
        FileHandler fh;
        File fileLog = createDirForLog();
        if (fileLog == null) return false;
        String logFileName = fileLog.getAbsolutePath();
        logFileName += "\\" + LOG_FILENAME;
        try {
            fh = new FileHandler(logFileName, false);
            fh.setLevel(Level.ALL);
            fh.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {
                    Date iDate = java.sql.Timestamp.valueOf(LocalDateTime.now());
                    String itemDateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(iDate);
                    return String.format("[%s] %s: %s%n",
                            itemDateStr,
                            fh.getLevel().getName(),
                            record.getMessage()
                    );
                }
            });

            logger.addHandler(fh);
            logger.setUseParentHandlers(false);
        } catch (SecurityException | IOException e) {
            logger.log(Level.SEVERE, "Exception: ", e);
            return false;
        }
        return true;
    }

    public static String getFileNameFromProgramDataDir(String strItemFileName) {
        File fileParams = createDirForXMLParams();
        if (fileParams == null) return "";
        String strFileName = fileParams.getAbsolutePath();
        strFileName += "\\" + strItemFileName;
        return strFileName;
    }

    private static File createDirForLog() {
        if (createDirectoryInProgramData(DIRECTORY_USER_PROG) == null) return null;
        return createDirectoryInProgramData(DIRECTORY_USER_PROG_LOG);
    }

    private static File createDirForXMLParams() {
        if (createDirectoryInProgramData(DIRECTORY_USER_PROG) == null) return null;
        return createDirectoryInProgramData(DIRECTORY_USER_PROG_SET);
    }

    private static File createDirectoryInProgramData(String strDirName) {
        String strDir = getUserDirectoryProgramData(strDirName);
        final File fileDir = new File(strDir);
        if (!fileDir.exists())
            if (!fileDir.mkdir()) return null;
        return fileDir;
    }

    private static String getUserDirectoryProgramData(String strDirName) {
        return getDirectoryProgramData() + "\\" + strDirName;
    }

    private static String getDirectoryProgramData() {
        if (System.getenv("PROGRAMDATA") == null)
            return System.getenv("APPDATA");
        else
            return System.getenv("PROGRAMDATA");
    }

    private static boolean setLoggerConsoleHandler(Logger logger) {
        //удалить все хэндлерсы для логгера
        Handler[] handlers = logger.getHandlers();
        for (Handler handler : handlers) {
            logger.removeHandler(handler);
        }
        //добавить новый
        ConsoleHandler fh;
        try {
            fh = new ConsoleHandler();
            fh.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {
                    Date iDate = java.sql.Timestamp.valueOf(LocalDateTime.now());
                    String itemDateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(iDate);
                    return String.format("[%s] %s: %s%n",
                            itemDateStr,
                            fh.getLevel().getName(),
                            record.getMessage()
                    );
                }
            });
            logger.addHandler(fh);
            logger.setUseParentHandlers(false);
        } catch (SecurityException e) {
            logger.log(Level.SEVERE, "Exception: ", e);
            return false;
        }
        return true;
    }
}

package main;

import java.util.Objects;
import java.util.logging.Logger;

import main.gui.GUIProgram;
import utils.ConstantForAll;

import static utils.UtilsFilesAndFolders.createTempDir;
import static utils.UtilsForAll.exitFromProgram;
import static utils.UtilsForAll.setSwingForShowGUI;
import static utils.UtilsLogger.setupLoggerAndSettings;

public class Main {

    public static void main(String[] args) {
        if ((args.length != 0) && Objects.equals(args[0], "-debug")) ConstantForAll.DEBUG = true;

        setSwingForShowGUI();
        Logger logger = setupLoggerAndSettings();
        if (logger == null) {exitFromProgram(); return;}

        if (utils.ConstantForAll.DEBUG) logger.info("DEBUG_MODE");

        createTempDir();
        logger.info("createTempDir");

        TimerIdle timerIdle = TimerIdle.getInstance();
        timerIdle.setInstanceParam(logger);

        GUIProgram.getInstance().setInstanceParam(logger);
        timerIdle.go();

    }

}

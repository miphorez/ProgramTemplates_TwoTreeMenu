package main.gui;

import containers.FrmContainerProgram;
import main.xml.XMLSettingsParsing;
import utils.ConstantForAll;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.logging.Logger;

import static utils.ConstantForAll.*;
import static utils.ConstantForAll.ICO_PNG_64;
import static utils.UtilsForAll.exitFromProgram;
import static utils.UtilsForAll.getMainClass;

public class GUIProgram {
    private static JFrame frameMain;
    private static Logger logger;
    private static FrmContainerProgram frmContainerProgram;

    private static GUIProgram instance;

    public static GUIProgram getInstance() {
        if (instance == null) {
            instance = new GUIProgram();
        }
        return instance;
    }

    public void setInstanceParam(Logger logger) {
        GUIProgram.logger = logger;
        SwingUtilities.invokeLater(GUIProgram::createGUI);
    }

    private static void createGUI() {
        logger.info("== Запуск окна программы ==");
        frameMain = new JFrame();
        String strTitle = PROGRAM_TITLE+" (v."+ ConstantForAll.PROGRAM_VERSION+")";
        frameMain.setTitle(strTitle);
        frameMain.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        //создание главного контейнера настроек
        frmContainerProgram = new FrmContainerProgram(logger, frameMain);
        frameMain.setContentPane(frmContainerProgram.jpMain);

        //контролируем кнопку выхода из программы
        frameMain.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitFromProgram();
            }
        });

        setCustomIconForProgram(frameMain);
        frameMain.pack();
        setPositionAndSizeFromXMLSettings();
        frameMain.setVisible(true);
    }

    public static void savePosAndSizeMain(){
        XMLSettingsParsing xmlSettings = XMLSettingsParsing.getInstance();
        Rectangle frameBounds = frameMain.getBounds();
        xmlSettings.setRootAttrToSettingsDoc("posXMain", String.valueOf(frameBounds.x));
        xmlSettings.setRootAttrToSettingsDoc("posYMain", String.valueOf(frameBounds.y));
        xmlSettings.setRootAttrToSettingsDoc("widthMain", String.valueOf(frameBounds.width));
        xmlSettings.setRootAttrToSettingsDoc("hightMain", String.valueOf(frameBounds.height));
        xmlSettings.setRootAttrToSettingsDoc("posDividerLocation", String.valueOf(frmContainerProgram.getSplitPane().getDividerLocation()));
    }

    private static void setPositionAndSizeFromXMLSettings() {
        XMLSettingsParsing xmlSettings = XMLSettingsParsing.getInstance();
        xmlSettings.setInstanceParam(logger);
        int posXMainWindow = Integer.parseInt(xmlSettings.getRootAttr("posXMain", "0"));
        int posYMainWindow = Integer.parseInt(xmlSettings.getRootAttr("posYMain", "0"));
        int widthMainWindow = Integer.parseInt(xmlSettings.getRootAttr("widthMain", "400"));
        int hightMainWindow = Integer.parseInt(xmlSettings.getRootAttr("hightMain", "240"));
        int posDividerLocation = Integer.parseInt(xmlSettings.getRootAttr("posDividerLocation", "150"));

        frameMain.setBounds(posXMainWindow,
                posYMainWindow,
                widthMainWindow,
                hightMainWindow);

        if ((posXMainWindow == 0) &&
                (posYMainWindow == 0))
            frameMain.setLocationRelativeTo(null);
        frmContainerProgram.getSplitPane().setDividerLocation(posDividerLocation);
    }

    private static void setCustomIconForProgram(JFrame frame) {
        ArrayList<Image> images = new ArrayList<>();
        ImageIcon icon = new ImageIcon(getMainClass().getResource(ICO_PNG_16));
        images.add(icon.getImage());
        icon = new ImageIcon(getMainClass().getResource(ICO_PNG_32));
        images.add(icon.getImage());
        icon = new ImageIcon(getMainClass().getResource(ICO_PNG_48));
        images.add(icon.getImage());
        icon = new ImageIcon(getMainClass().getResource(ICO_PNG_64));
        images.add(icon.getImage());
        frame.setIconImages(images);
    }

}

package main.menu;

import containers.EPanelMenu;
import containers.FrmContainerProgram;
import frm.gui.HintMouseAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Menu extends JMenuBar {

    private FrmContainerProgram mainContainer;
    private JMenuBar menuBar;
    private Point point;

    public Menu(FrmContainerProgram mainContainer) {
        this.mainContainer = mainContainer;
        createMenu();
    }

    private void createMenu() {
        menuBar = new JMenuBar();

        JMenu iMenu = new JMenu(TypeMenu.MENU_EXIT.getHtmlStrVal());
        iMenu.addMouseListener(new HintMouseAdapter("Выход из программы", mainContainer));
        iMenu.setName(TypeMenu.MENU_EXIT.name());
        iMenu.addMouseListener(new MenuMouseAdapter(mainContainer));
        menuBar.add(iMenu);

        iMenu = new JMenu(TypeMenu.MENU_COMMAND.getHtmlBoldStrVal());
        iMenu.addMouseListener(new HintMouseAdapter("Перейти в меню команд", mainContainer));
        iMenu.setName(TypeMenu.MENU_COMMAND.name());
        iMenu.addMouseListener(new MenuMouseAdapter(mainContainer));
        menuBar.add(iMenu);

        iMenu = new JMenu(TypeMenu.MENU_PARAMS.getHtmlStrVal());
        iMenu.addMouseListener(new HintMouseAdapter("Перейти в меню настроек", mainContainer));
        iMenu.setName(TypeMenu.MENU_PARAMS.name());
        iMenu.addMouseListener(new MenuMouseAdapter(mainContainer));
        menuBar.add(iMenu);

        mainContainer.getFrameProgram().setJMenuBar(menuBar);
    }

    public Point getPointLastMouseClick() {
        return point;
    }

    class MenuMouseAdapter extends MouseAdapter {
        FrmContainerProgram mainContainer;

        MenuMouseAdapter(FrmContainerProgram mainContainer) {
            this.mainContainer = mainContainer;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            point = new Point(e.getXOnScreen(), e.getYOnScreen());
            JMenu itemMenu = (JMenu) e.getSource();
            TypeMenu typeMenu = TypeMenu.getType(itemMenu.getName());
            setBoldNameItemMenu(typeMenu);
            switch (typeMenu) {
                case MENU_COMMAND:
                    mainContainer.goToPanelByMenu(EPanelMenu.PANEL_MENU_COMMAND.name(), true);
                    break;
                case MENU_PARAMS:
                    mainContainer.goToPanelByMenu(EPanelMenu.PANEL_MENU_PARAM.name(), true);
                    break;
                case MENU_EXIT:
                    utils.UtilsForAll.exitFromProgram();
            }
        }

        private void setBoldNameItemMenu(TypeMenu typeMenu) {
            for (int i = 0; i < mainContainer.menu.menuBar.getMenuCount(); i++) {
                JMenu iMenu = mainContainer.menu.menuBar.getMenu(i);
                TypeMenu iType = TypeMenu.getType(iMenu.getName());
                if (typeMenu == iType)
                    iMenu.setText(iType.getHtmlBoldStrVal());
                else
                    iMenu.setText(iType.getHtmlStrVal());
            }
        }

    }
}

enum TypeMenu {
    MENU_NONE(""),
    MENU_EXIT("Выход"),
    MENU_COMMAND("Команды"),
    MENU_PARAMS("Настройка параметров"),;
    private String strVal = "";

    TypeMenu(String strVal) {
        this.strVal = strVal;
    }

    public String getStrVal() {
        return strVal;
    }

    public String getHtmlStrVal() {
        return "<html>" + strVal;
    }

    public String getHtmlBoldStrVal() {
        return "<html><b>" + strVal;
    }

    static public TypeMenu getType(String strType) {
        for (TypeMenu type : TypeMenu.values()) {
            if (type.name().equals(strType)) {
                return type;
            }
        }
        return MENU_NONE;
    }
}

package containers;

import frm.gui.CreateLabel;
import frm.treemenu.FrmTreeMenuCommand;
import frm.treemenu.FrmTreeMenuParam;
import main.menu.Menu;
import frm.treemenu.MenuNode;
import utils.gui.BoxLayoutUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Objects;
import java.util.logging.Logger;

public class FrmContainerProgram {
    public JPanel jpMain;
    private JPanel jpContainerProgram;
    public JLabel lStatusGlobal;
    public FrmTreeMenuCommand frmTreeMenuCommand;
    public FrmTreeMenuParam frmTreeMenuParam;
    private JPanel jpTreeMain;
    private JSplitPane jSplitPane;

    EPanelMenu itemTypeMenu;
    private int posDividerCommand;
    private int posDividerParam;

    private JFrame frameProgram;
    public Menu menu;

    public ContainerFormsCommand containerFormsCommand;
    public ContainerFormsParam containerFormsParam;
    private Logger logger;

    public FrmContainerProgram(Logger logger, JFrame frameProgram) {
        this.logger = logger;
        this.frameProgram = frameProgram;
        itemTypeMenu = EPanelMenu.PANEL_MENU_COMMAND;

        jpMain = new JPanel(new BorderLayout());

        jpTreeMain = new JPanel(new GridLayout(1, 1));
        frmTreeMenuCommand = new FrmTreeMenuCommand(this);
        frmTreeMenuParam = new FrmTreeMenuParam(this);
        jpTreeMain.add(frmTreeMenuCommand.jpMain);

        jpContainerProgram = new JPanel(new CardLayout());
        jpContainerProgram.setBorder(new EmptyBorder(2, 5, 0, 5));

        jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                jpTreeMain, jpContainerProgram);
        jSplitPane.setBorder(new EmptyBorder(0, 5, 0, 5));
        jSplitPane.	setDividerLocation(150);
        jSplitPane.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                JSplitPane sourceSplitPane = (JSplitPane) pce.getSource();
                String propertyName = pce.getPropertyName();
                if (propertyName.equals(JSplitPane.DIVIDER_LOCATION_PROPERTY)) {
                    switch (itemTypeMenu) {
                        case PANEL_MENU_COMMAND:
                            posDividerCommand = sourceSplitPane.getDividerLocation();
                            break;
                        case PANEL_MENU_PARAM:
                            posDividerParam = sourceSplitPane.getDividerLocation();
                            break;
                    }
                }
            }
        });

        jpMain.add(createJpPanelInfo(), BorderLayout.NORTH);
        jpMain.add(createJpStatusGlobal(), BorderLayout.SOUTH);
        jpMain.add(jSplitPane, BorderLayout.CENTER);

        addFormsToContainer();
        menu = new Menu(this);

        MenuNode itemNode = frmTreeMenuCommand.getNodeByNamePanelAndFocusNode(EPanelCommand.PANEL_CMD_3.name());
        frmTreeMenuCommand.setLastSelectedNode(itemNode);
        itemNode = frmTreeMenuParam.getNodeByNamePanelAndFocusNode(EPanelParam.PANEL_PARAM_ABOUT.name());
        frmTreeMenuParam.setLastSelectedNode(itemNode);

        goToPanelByMenu(EPanelMenu.PANEL_MENU_COMMAND.name(), false);
    }

    public void goToPanelByMenu(String namePanel, boolean modeClickMouse) {
        goOpenFrmByNamePanel(namePanel);
        switch (EPanelMenu.getType(namePanel)) {
            case PANEL_MENU_COMMAND:
                itemTypeMenu = EPanelMenu.PANEL_MENU_COMMAND;
                jpContainerProgram.add(containerFormsCommand.jpContainer);
                jpContainerProgram.remove(containerFormsParam.jpContainer);
                jSplitPane.remove(jpTreeMain);
                jpTreeMain.add(frmTreeMenuCommand.jpMain);
                jpTreeMain.remove(frmTreeMenuParam.jpMain);
                jSplitPane.setLeftComponent(jpTreeMain);
                frmTreeMenuCommand.goOpenFrmForItemNode(frmTreeMenuCommand.getLastSelectedNode());
                if (modeClickMouse) {
                    utils.UtilsForAll.goRobotMoveAndClick(frmTreeMenuCommand.getPointLastMouseClick());
                    utils.UtilsForAll.goRobotMove(menu.getPointLastMouseClick());
                }
                frmTreeMenuCommand.jtreeMenu.setSelectionRow(frmTreeMenuCommand.getLastSelectedNode().getNumNode());
                lStatusGlobal.setText(frmTreeMenuCommand.getLastSelectedNode().getDescriptionNodeHTML());
                break;
            case PANEL_MENU_PARAM:
                itemTypeMenu = EPanelMenu.PANEL_MENU_PARAM;
                jpContainerProgram.add(containerFormsParam.jpContainer);
                jpContainerProgram.remove(containerFormsCommand.jpContainer);
                jSplitPane.remove(jpTreeMain);
                jpTreeMain.add(frmTreeMenuParam.jpMain);
                jpTreeMain.remove(frmTreeMenuCommand.jpMain);
                jSplitPane.setLeftComponent(jpTreeMain);
                frmTreeMenuParam.goOpenFrmForItemNode(frmTreeMenuParam.getLastSelectedNode());
                if (modeClickMouse) {
                    utils.UtilsForAll.goRobotMoveAndClick(frmTreeMenuParam.getPointLastMouseClick());
                    utils.UtilsForAll.goRobotMove(menu.getPointLastMouseClick());
                }
                frmTreeMenuParam.jtreeMenu.setSelectionRow(frmTreeMenuParam.getLastSelectedNode().getNumNode());
                lStatusGlobal.setText(frmTreeMenuParam.getLastSelectedNode().getDescriptionNodeHTML());
                break;
        }
        setPosDividerLocation();
    }

    private void goOpenFrmByNamePanel(String namePanel) {
        CardLayout cardLayout = (CardLayout) (jpContainerProgram.getLayout());
        cardLayout.show(jpContainerProgram, namePanel);
        if (Objects.equals(namePanel, EPanelMenu.PANEL_MENU_COMMAND.name())) {
            frmTreeMenuCommand.jtreeMenu.requestFocusInWindow();
        }
    }

    private JPanel createJpPanelInfo() {
        JPanel jpPanelInfo = new JPanel(new GridLayout(1, 1));
        jpPanelInfo.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(5, 5, 5, 5),
                new LineBorder(new Color(132, 132, 132))));
        jpPanelInfo.setPreferredSize(new Dimension(-1, 42));

        JPanel jpListScripts = BoxLayoutUtils.createHorizontalPanel();
        JLabel lListScriptTopInfo = new CreateLabel(0, 0, SwingConstants.RIGHT, SwingConstants.CENTER,
                "...");

        jpListScripts.add(Box.createHorizontalGlue());
        jpListScripts.add(lListScriptTopInfo);
        jpListScripts.add(Box.createHorizontalGlue());

        jpPanelInfo.add(jpListScripts);
        return jpPanelInfo;
    }

    private JPanel createJpStatusGlobal() {
        JPanel jpStatusGlobal = new JPanel(new GridLayout(1, 1));
        jpStatusGlobal.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(5, 5, 5, 5),
                new LineBorder(new Color(132, 132, 132))));
        jpStatusGlobal.setPreferredSize(new Dimension(-1, 42));
        lStatusGlobal = new JLabel();
        lStatusGlobal.setFont(new Font("Tahoma", Font.PLAIN, 11));
        JPanel jpLabel = new JPanel(new BorderLayout());
        jpLabel.setBorder(new EmptyBorder(0, 5, 0, 5));
        jpLabel.add(lStatusGlobal);
        jpStatusGlobal.add(jpLabel);
        return jpStatusGlobal;
    }

    private void addFormsToContainer() {
        containerFormsCommand = new ContainerFormsCommand(this);
        containerFormsCommand.addFormsToContainer();
        containerFormsParam = new ContainerFormsParam(this);
        containerFormsParam.addFormsToContainer();
    }


    public Component getFocusComponent() {
        return frmTreeMenuCommand.getTreeAsComponent();
    }

    public Logger getLogger() {
        return logger;
    }

    public JFrame getFrameProgram() {
        return frameProgram;
    }

    public JSplitPane getSplitPane() {
        return jSplitPane;
    }

    public void setPosDividerParam(int posDividerParam) {
        this.posDividerParam = posDividerParam;
    }

    public void setPosDividerCommand(int posDividerCommand) {
        this.posDividerCommand = posDividerCommand;
    }

    public int getPosDividerCommand() {
        return posDividerCommand;
    }

    public int getPosDividerParam() {
        return posDividerParam;
    }

    public void setPosDividerLocation() {
        switch (itemTypeMenu) {
            case PANEL_MENU_COMMAND:
                jSplitPane.setDividerLocation(posDividerCommand);
                break;
            case PANEL_MENU_PARAM:
                jSplitPane.setDividerLocation(posDividerParam);
                break;
        }
    }
}

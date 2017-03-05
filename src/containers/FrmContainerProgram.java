package containers;

import frm.gui.CreateLabel;
import frm.treemenu.FrmTreeMenuCommand;
import frm.treemenu.FrmTreeMenuParam;
import main.menu.Menu;
import main.menu.MenuNode;
import utils.gui.BoxLayoutUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Objects;
import java.util.logging.Logger;

public class FrmContainerProgram {
    public JPanel jpMain;
    private JPanel jpContainerProgram;
    public JLabel lStatusGlobal;
    public FrmTreeMenuCommand frmTreeMenuCommand;
    public FrmTreeMenuParam frmTreeMenuParam;
    private JPanel jpTreeMain;


    private JFrame frameProgram;
    public Menu menu;

    public ContainerFormsCommand containerFormsCommand;
    public ContainerFormsParam containerFormsParam;
    private Logger logger;

    public FrmContainerProgram(Logger logger, JFrame frameProgram) {
        this.logger = logger;
        this.frameProgram = frameProgram;

        jpMain = new JPanel(new BorderLayout());
        JPanel jpProgramMain = BoxLayoutUtils.createHorizontalPanel();

        jpTreeMain = new JPanel(new GridLayout(1, 1));
        frmTreeMenuCommand = new FrmTreeMenuCommand(this);
        frmTreeMenuParam = new FrmTreeMenuParam(this);
        jpTreeMain.add(frmTreeMenuCommand.jpMain);

        jpContainerProgram = new JPanel(new CardLayout());
        jpContainerProgram.setBorder(new EmptyBorder(2, 5, 0, 5));

        jpProgramMain.add(Box.createHorizontalStrut(5));
        jpProgramMain.add(jpTreeMain);
        jpProgramMain.add(jpContainerProgram);

        jpMain.add(createJpPanelInfo(), BorderLayout.NORTH);
        jpMain.add(createJpStatusGlobal(), BorderLayout.SOUTH);
        jpMain.add(jpProgramMain, BorderLayout.CENTER);

        addFormsToContainer();
        menu = new Menu(this);

        MenuNode itemNode = frmTreeMenuCommand.getNodeByNamePanelAndFocusNode(EPanelCommand.PANEL_CMD_3.name());
        frmTreeMenuCommand.setLastSelectedNode(itemNode);
        itemNode = frmTreeMenuParam.getNodeByNamePanelAndFocusNode(EPanelParam.PANEL_PARAM_31.name());
        frmTreeMenuParam.setLastSelectedNode(itemNode);

        goToPanelByMenu(EPanelMenu.PANEL_MENU_COMMAND.name(), false);
    }

    public void goToPanelByMenu(String namePanel, boolean modeClickMouse) {
        goOpenFrmByNamePanel(namePanel);
        switch (EPanelMenu.getType(namePanel)) {
            case PANEL_MENU_COMMAND:
                jpContainerProgram.add(containerFormsCommand.jpContainer);
                jpContainerProgram.remove(containerFormsParam.jpContainer);
                jpTreeMain.add(frmTreeMenuCommand.jpMain);
                jpTreeMain.remove(frmTreeMenuParam.jpMain);
                frmTreeMenuCommand.goOpenFrmForItemNode(frmTreeMenuCommand.getLastSelectedNode());
                if (modeClickMouse) {
                    utils.UtilsForAll.goRobotMoveAndClick(frmTreeMenuCommand.getPointLastMouseClick());
                    utils.UtilsForAll.goRobotMove(menu.getPointLastMouseClick());
                }
                frmTreeMenuCommand.jtreeMenu.setSelectionRow(frmTreeMenuCommand.getLastSelectedNode().getNumNode());
                lStatusGlobal.setText(frmTreeMenuCommand.getLastSelectedNode().getDescriptionNodeHTML());
                break;
            case PANEL_MENU_PARAM:
                jpContainerProgram.add(containerFormsParam.jpContainer);
                jpContainerProgram.remove(containerFormsCommand.jpContainer);
                jpTreeMain.add(frmTreeMenuParam.jpMain);
                jpTreeMain.remove(frmTreeMenuCommand.jpMain);
                frmTreeMenuParam.goOpenFrmForItemNode(frmTreeMenuParam.getLastSelectedNode());
                if (modeClickMouse) {
                    utils.UtilsForAll.goRobotMoveAndClick(frmTreeMenuParam.getPointLastMouseClick());
                    utils.UtilsForAll.goRobotMove(menu.getPointLastMouseClick());
                }
                frmTreeMenuParam.jtreeMenu.setSelectionRow(frmTreeMenuParam.getLastSelectedNode().getNumNode());
                lStatusGlobal.setText(frmTreeMenuParam.getLastSelectedNode().getDescriptionNodeHTML());
                break;
        }
    }

    private void goOpenFrmByNamePanel(String namePanel) {
        CardLayout cardLayout = (CardLayout) (jpContainerProgram.getLayout());
        cardLayout.show(jpContainerProgram, namePanel);
        if (Objects.equals(namePanel, EPanelMenu.PANEL_MENU_COMMAND.name())) {
            frmTreeMenuCommand.jtreeMenu.requestFocusInWindow();
        }
    }

    private JPanel createJpPanelInfo() {
        JPanel jpPanelInfo = new JPanel(new FlowLayout());
        JPanel jpListScripts = BoxLayoutUtils.createHorizontalPanel();
        JLabel lListScriptTopInfo = new CreateLabel(0, 0, SwingConstants.RIGHT, SwingConstants.RIGHT,
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
}

package containers;

import frm.command.*;
import frm.treemenu.MenuNode;

import javax.swing.*;
import java.awt.*;

public class ContainerFormsCommand {
    public JPanel jpContainer;

    private FrmContainerProgram mainContainer;

    public ContainerFormsCommand(FrmContainerProgram mainContainer) {
        this.mainContainer = mainContainer;
        jpContainer = new JPanel(new CardLayout());
    }

    private FrmCommand1 frmCommand1;
    private FrmCommand11 frmCommand11;
    private FrmCommand12 frmCommand12;
    private FrmCommand2 frmCommand2;
    private FrmCommand21 frmCommand21;
    private FrmCommand31 frmCommand31;

    public void addFormsToContainer() {
        frmCommand1 = new FrmCommand1(mainContainer);
        frmCommand11 = new FrmCommand11(mainContainer);
        frmCommand12 = new FrmCommand12(mainContainer);
        frmCommand2 = new FrmCommand2(mainContainer);
        frmCommand21 = new FrmCommand21(mainContainer);
        frmCommand31 = new FrmCommand31(mainContainer);

        jpContainer.add(frmCommand1.jpMain, EPanelCommand.PANEL_CMD_1.name());
        jpContainer.add(frmCommand11.jpMain, EPanelCommand.PANEL_CMD_11.name());
        jpContainer.add(frmCommand12.jpMain, EPanelCommand.PANEL_CMD_12.name());
        jpContainer.add(frmCommand2.jpMain, EPanelCommand.PANEL_CMD_2.name());
        jpContainer.add(frmCommand21.jpMain, EPanelCommand.PANEL_CMD_21.name());
        jpContainer.add(frmCommand31.jpMain, EPanelCommand.PANEL_CMD_3.name());

    }

    public void viewItemForm(MenuNode itemNode) {
        switch (EPanelCommand.getType(itemNode.getNamePanel())) {
            case PANEL_CMD_1:
                frmCommand1.setViewItemParam(itemNode);
                break;
            case PANEL_CMD_11:
                frmCommand11.setViewItemParam(itemNode);
                break;
            case PANEL_CMD_12:
                frmCommand12.setViewItemParam(itemNode);
                break;
            case PANEL_CMD_2:
                frmCommand2.setViewItemParam(itemNode);
                break;
            case PANEL_CMD_21:
                frmCommand21.setViewItemParam(itemNode);
                break;
            case PANEL_CMD_3:
                frmCommand31.setViewItemParam(itemNode);
                break;
        }
    }

    private void goOpenFrmByNamePanel(String namePanel) {
        CardLayout cardLayout = (CardLayout) (jpContainer.getLayout());
        cardLayout.show(jpContainer, namePanel);
    }

    public void goToPanelByMenu(String namePanel) {
        goOpenFrmByNamePanel(namePanel);
        MenuNode itemNode = mainContainer.frmTreeMenuCommand.getNodeByNamePanelAndFocusNode(namePanel);
        mainContainer.frmTreeMenuCommand.setLastSelectedNode(itemNode);
        mainContainer.frmTreeMenuCommand.jtreeMenu.setSelectionRow(itemNode.getNumNode());
        viewItemForm(itemNode);
        mainContainer.lStatusGlobal.setText(itemNode.getDescriptionNodeHTML());
    }
}

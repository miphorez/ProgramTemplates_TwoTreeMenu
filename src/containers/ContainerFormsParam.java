package containers;

import frm.param.*;
import frm.treemenu.MenuNode;

import javax.swing.*;
import java.awt.*;

import static containers.EPanelParam.PANEL_PARAM_1;

public class ContainerFormsParam {
    public JPanel jpContainer;

    private FrmContainerProgram mainContainer;

    public ContainerFormsParam(FrmContainerProgram mainContainer) {
        this.mainContainer = mainContainer;
        jpContainer = new JPanel(new CardLayout());
    }

    private FrmParam1 frmParam1;
    private FrmParam2 frmParam2;
    private FrmParam21 frmParam21;
    private FrmParam3 frmParam3;
    private FrmParam31 frmParam31;
    private FrmParam32 frmParam32;
    private FrmParamAbout frmParamAbout;

    public void addFormsToContainer() {
        frmParam1 = new FrmParam1(mainContainer);
        frmParam2 = new FrmParam2(mainContainer);
        frmParam21 = new FrmParam21(mainContainer);
        frmParam3 = new FrmParam3(mainContainer);
        frmParam31 = new FrmParam31(mainContainer);
        frmParam32 = new FrmParam32(mainContainer);
        frmParamAbout = new FrmParamAbout(mainContainer);

        jpContainer.add(frmParam1.jpMain, PANEL_PARAM_1.name());
        jpContainer.add(frmParam2.jpMain, EPanelParam.PANEL_PARAM_2.name());
        jpContainer.add(frmParam21.jpMain, EPanelParam.PANEL_PARAM_21.name());
        jpContainer.add(frmParam3.jpMain, EPanelParam.PANEL_PARAM_3.name());
        jpContainer.add(frmParam31.jpMain, EPanelParam.PANEL_PARAM_31.name());
        jpContainer.add(frmParam32.jpMain, EPanelParam.PANEL_PARAM_32.name());
        jpContainer.add(frmParamAbout.jpMain, EPanelParam.PANEL_PARAM_ABOUT.name());

    }

    public void viewItemForm(MenuNode itemNode) {
        switch (EPanelParam.getType(itemNode.getNamePanel())) {
            case PANEL_PARAM_1:
                frmParam1.setViewItemParam(itemNode);
                break;
            case PANEL_PARAM_2:
                frmParam2.setViewItemParam(itemNode);
                break;
            case PANEL_PARAM_21:
                frmParam21.setViewItemParam(itemNode);
                break;
            case PANEL_PARAM_3:
                frmParam3.setViewItemParam(itemNode);
                break;
            case PANEL_PARAM_31:
                frmParam31.setViewItemParam(itemNode);
                break;
            case PANEL_PARAM_32:
                frmParam32.setViewItemParam(itemNode);
                break;
            case PANEL_PARAM_ABOUT:
                frmParamAbout.setViewItemParam(itemNode);
                break;
        }
    }

    private void goOpenFrmByNamePanel(String namePanel) {
        CardLayout cardLayout = (CardLayout) (jpContainer.getLayout());
        cardLayout.show(jpContainer, namePanel);
    }

    public void goToPanelByMenu(String namePanel) {
        goOpenFrmByNamePanel(namePanel);
        MenuNode itemNode = mainContainer.frmTreeMenuParam.getNodeByNamePanelAndFocusNode(namePanel);
        mainContainer.frmTreeMenuParam.setLastSelectedNode(itemNode);
        mainContainer.frmTreeMenuParam.jtreeMenu.setSelectionRow(itemNode.getNumNode());
        viewItemForm(itemNode);
        mainContainer.lStatusGlobal.setText(itemNode.getDescriptionNodeHTML());
    }
}

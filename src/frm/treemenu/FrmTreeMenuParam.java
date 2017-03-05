package frm.treemenu;


import containers.EPanelParam;
import containers.FrmContainerProgram;
import main.menu.MenuNode;

import java.awt.*;
import java.util.Objects;

import static utils.ConstantForAll.MENU_PARAM;

public class FrmTreeMenuParam extends TreeMenu {

    public FrmTreeMenuParam(FrmContainerProgram mainContainer) {
        super(mainContainer, MENU_PARAM);
    }

    @Override
    public void goOpenFrmForItemNode(MenuNode itemNode) {
        if (Objects.equals(itemNode.getNamePanel(), EPanelParam.PANEL_NONE.name())) return;
        CardLayout cardLayout = (CardLayout)(getMainContainer().containerFormsParam.jpContainer.getLayout());
        cardLayout.show(getMainContainer().containerFormsParam.jpContainer, itemNode.getNamePanel());
        getMainContainer().containerFormsParam.viewItemForm(itemNode);
        String iStr = mainContainer.frmTreeMenuParam.getLastSelectedNode().getDescriptionNodeHTML();
        mainContainer.lStatusGlobal.setText(iStr);
    }

}
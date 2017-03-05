package frm.treemenu;


import containers.EPanelCommand;
import containers.FrmContainerProgram;
import main.menu.MenuNode;

import java.awt.*;
import java.util.Objects;

import static utils.ConstantForAll.MENU_COMMAND;

public class FrmTreeMenuCommand extends TreeMenu {

    public FrmTreeMenuCommand(FrmContainerProgram mainContainer) {
        super(mainContainer, MENU_COMMAND);
    }

    @Override
    public void goOpenFrmForItemNode(MenuNode itemNode) {
        if (Objects.equals(itemNode.getNamePanel(), EPanelCommand.PANEL_NONE.name())) return;
        CardLayout cardLayout = (CardLayout)(getMainContainer().containerFormsCommand.jpContainer.getLayout());
        cardLayout.show(getMainContainer().containerFormsCommand.jpContainer, itemNode.getNamePanel());
        getMainContainer().containerFormsCommand.viewItemForm(itemNode);
        String iStr = mainContainer.frmTreeMenuCommand.getLastSelectedNode().getDescriptionNodeHTML();
        mainContainer.lStatusGlobal.setText(iStr);
    }

}
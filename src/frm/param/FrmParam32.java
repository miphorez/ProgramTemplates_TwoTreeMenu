package frm.param;

import containers.FrmContainerProgram;
import frm.FrmParam;
import frm.gui.CreateLineBorderBox;
import frm.gui.CreateTextArea;
import frm.treemenu.MenuNode;
import utils.gui.BoxLayoutUtils;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class FrmParam32 extends FrmParam {
    private JTextArea textArea;

    public FrmParam32(FrmContainerProgram mainContainer) {
        super(mainContainer, false);
        Border emptyBorder, lBorder, tBorder;

        jpMain = BoxLayoutUtils.createVerticalPanel();

        JPanel jpTAabout = new CreateLineBorderBox(0, 0, "Настройка 3-2");
        CreateTextArea createTextArea = new CreateTextArea(false);
        jpTAabout.add(createTextArea, BorderLayout.CENTER);
        textArea = createTextArea.jTextArea;

        jpMain.add(jpTAabout);
        jpMain.add(Box.createVerticalStrut(5));
    }

    @Override
    public void setViewItemParam(MenuNode itemNode) {
        textArea.setText("Настройка 3-2");
        jpMain.revalidate();
    }

}

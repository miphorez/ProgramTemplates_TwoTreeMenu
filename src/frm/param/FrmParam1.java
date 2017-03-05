package frm.param;

import containers.FrmContainerProgram;
import frm.FrmParam;
import main.menu.MenuNode;
import frm.gui.CreateLineBorderBox;
import frm.gui.CreateTextArea;
import utils.gui.BoxLayoutUtils;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class FrmParam1 extends FrmParam {
    private JTextArea textArea;

    public FrmParam1(FrmContainerProgram mainContainer) {
        super(mainContainer, false);
        Border emptyBorder, lBorder, tBorder;

        jpMain = BoxLayoutUtils.createVerticalPanel();

        JPanel jpTAabout = new CreateLineBorderBox(0, 0, "Настройка 1");
        CreateTextArea createTextArea = new CreateTextArea(false);
        jpTAabout.add(createTextArea, BorderLayout.CENTER);
        textArea = createTextArea.jTextArea;

        jpMain.add(jpTAabout);
        jpMain.add(Box.createVerticalStrut(5));
    }

    @Override
    public void setViewItemParam(MenuNode itemNode) {
        textArea.setText("Настройка 1");
        jpMain.revalidate();
    }

}

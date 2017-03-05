package frm.command;

import frm.FrmCommand;
import containers.FrmContainerProgram;
import main.menu.MenuNode;
import frm.gui.CreateLineBorderBox;
import frm.gui.CreateTextArea;
import utils.gui.BoxLayoutUtils;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class FrmCommand11 extends FrmCommand {
    private JTextArea textArea;

    public FrmCommand11(FrmContainerProgram mainContainer) {
        super(mainContainer, false);
        Border emptyBorder, lBorder, tBorder;

        jpMain = BoxLayoutUtils.createVerticalPanel();

        JPanel jpTAabout = new CreateLineBorderBox(0, 0, "Команда 1-1");
        CreateTextArea createTextArea = new CreateTextArea(false);
        jpTAabout.add(createTextArea, BorderLayout.CENTER);
        textArea = createTextArea.jTextArea;

        jpMain.add(jpTAabout);
        jpMain.add(Box.createVerticalStrut(5));
    }

    @Override
    public void setViewItemParam(MenuNode itemNode) {
        textArea.setText("Команда 1-1");
        jpMain.revalidate();
    }

}

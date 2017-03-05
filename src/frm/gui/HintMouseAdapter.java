package frm.gui;


import containers.FrmContainerProgram;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HintMouseAdapter extends MouseAdapter {
    String messageEntered;
    JLabel lStatus;

    public HintMouseAdapter(String messageEntered, FrmContainerProgram containerMain) {
        this.messageEntered = messageEntered;
        this.lStatus = containerMain.lStatusGlobal;
    }

    public void mouseEntered(MouseEvent e) {
        lStatus.setText("<html>"+messageEntered);
    }
}


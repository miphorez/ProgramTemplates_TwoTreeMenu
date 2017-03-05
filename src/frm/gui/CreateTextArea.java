package frm.gui;


import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.DefaultCaret;
import java.awt.*;

public class CreateTextArea extends JPanel {
    public JTextArea jTextArea;
    JScrollPane scrollPane;

    public CreateTextArea(boolean modeOpaque) {
        super(new BorderLayout());
        Border lBorder;
        Border tBorder;
        lBorder = new LineBorder(new Color(132, 132, 132));
        setBorder(lBorder);
        setPreferredSize(new Dimension(240,500));
        setMinimumSize(new Dimension(240,50));

        jTextArea = new JTextArea();
        DefaultCaret caret = (DefaultCaret)jTextArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        jTextArea.setFont(new Font("Tahoma",Font.PLAIN,11));
        jTextArea.setLineWrap(true);
        jTextArea.setWrapStyleWord(true);
        jTextArea.setEditable(false);
        jTextArea.setOpaque(modeOpaque);

        scrollPane = new JScrollPane(jTextArea);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        EmptyBorder emptyBorder = new EmptyBorder(3, 3, 3, 3);
        if (modeOpaque) scrollPane.setBackground(Color.white);
        scrollPane.setBorder(emptyBorder);
        add(scrollPane, BorderLayout.CENTER);
    }
}

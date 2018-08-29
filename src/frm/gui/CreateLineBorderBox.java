package frm.gui;

import utils.classes.RecValues;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Objects;

public class CreateLineBorderBox extends JPanel {

    public CreateLineBorderBox(int sizeX, int sizeY, String title) {
        super(new BorderLayout());
        if ((sizeX != 0)&&(sizeY != 0)) {
            setPreferredSize(new Dimension(sizeX, sizeY));
            setMaximumSize(new Dimension(sizeX, sizeY));
            setMinimumSize(new Dimension(sizeX, sizeY));
        }
        LineBorder lBorder = new LineBorder(new Color(152, 152, 152));
        TitledBorder tBorder = new TitledBorder(lBorder, " "+title+" ",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Tahoma",Font.PLAIN,11));
        if (Objects.equals(title, ""))
        setBorder(null); else
        setBorder(tBorder);
    }

    public CreateLineBorderBox(RecValues valSize, String title, RecValues valBorders) {
        super(new BorderLayout());
        int sizeX = (valSize.get(0) != -1)? valSize.get(0) : 0;
        int sizeY = (valSize.get(1) != -1)? valSize.get(1) : 0;
        if ((sizeX != 0) && (sizeY != 0)) {
            setPreferredSize(new Dimension(sizeX, sizeY));
            setMaximumSize(new Dimension(sizeX, sizeY));
            setMinimumSize(new Dimension(sizeX, sizeY));
        }
        setBorder(new EmptyBorder(
                (valBorders.get(0) != -1)? valBorders.get(0) : 0,
                (valBorders.get(1) != -1)? valBorders.get(1) : 0,
                (valBorders.get(2) != -1)? valBorders.get(2) : 0,
                (valBorders.get(3) != -1)? valBorders.get(3) : 0
        ));

        JPanel jWorkPane = new JPanel();
        LineBorder lBorder = new LineBorder(new Color(152, 152, 152));
        TitledBorder tBorder = new TitledBorder(lBorder, " " + title + " ",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Tahoma", Font.PLAIN, 11));
        if (Objects.equals(title, ""))
            jWorkPane.setBorder(null);
        else
            jWorkPane.setBorder(tBorder);

        add(jWorkPane);

    }
}

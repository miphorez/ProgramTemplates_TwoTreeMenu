package frm;

import containers.FrmContainerProgram;
import frm.treemenu.MenuNode;
import main.xml.XMLSettingsParsing;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Objects;
import java.util.logging.Logger;

public abstract class FrmParam {
    public JPanel jpMain;
    FrmContainerProgram mainContainer;
    private Border lBorder;
    private boolean modeTitle;
    private XMLSettingsParsing xmlSettingsParsing;

    public FrmParam(FrmContainerProgram mainContainer, boolean modeTitle) {
        this.mainContainer = mainContainer;
        xmlSettingsParsing = XMLSettingsParsing.getInstance();
        this.modeTitle = modeTitle;
        jpMain = new JPanel(new FlowLayout());
        if (modeTitle) {
            lBorder = new LineBorder(new Color(132, 132, 132));
            jpMain.setBorder(new TitledBorder(lBorder, ""));
        }
    }

    public Logger getLogger(){
        return mainContainer.getLogger();
    }

    void setTitleBorder(String textNode) {
        if (Objects.equals(textNode, "") || !modeTitle) return;
        jpMain.setBorder(new TitledBorder(lBorder, " " + textNode + " "));
    }

    public abstract void setViewItemParam(MenuNode itemNode);

    public XMLSettingsParsing getXmlSettingsParsing() {
        return xmlSettingsParsing;
    }
}

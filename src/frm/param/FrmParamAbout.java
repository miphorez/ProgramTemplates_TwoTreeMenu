package frm.param;

import containers.FrmContainerProgram;
import frm.FrmParam;
import frm.gui.CreateLineBorderBox;
import frm.treemenu.MenuNode;
import utils.classes.RecValues;
import utils.gui.BoxLayoutUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

import static utils.ConstantForAll.LS;
import static utils.UtilsForAll.getFileNameResourceImgInTemp;
import static utils.UtilsForAll.getMainClass;

public class FrmParamAbout extends FrmParam {
    public JPanel jpMain;
    private JTextPane taHistory;
    private JTextPane taAbout;

    public FrmParamAbout(FrmContainerProgram mainContainer) {
        super(mainContainer, false);
        jpMain = new JPanel(new FlowLayout());
        JPanel jpMainBorder = new JPanel(new BorderLayout());
        JPanel jpContainerMain = new JPanel(new CardLayout());
        jpContainerMain.setBorder(new EmptyBorder(0,5,0,5));
        jpMainBorder.add(jpContainerMain, BorderLayout.CENTER);

        jpMain = BoxLayoutUtils.createVerticalPanel();

        JPanel jpTAabout = new CreateLineBorderBox(new RecValues(new int[]{0,0}),
                "О программе", new RecValues(new int[]{0,0,0,0}));
        CreateTextPane createTextPane_About = new CreateTextPane(false);
        jpTAabout.add(createTextPane_About, BorderLayout.CENTER);
        taAbout = createTextPane_About.jTextPane;
        taAbout.addHyperlinkListener(this::gotoLink);

        JPanel jpTAhistory = new CreateLineBorderBox(new RecValues(new int[]{0,0}),
                "История версий", new RecValues(new int[]{5,0,0,0}));
        CreateTextPane createTextPane_History = new CreateTextPane(false);
        jpTAhistory.add(createTextPane_History, BorderLayout.CENTER);
        taHistory = createTextPane_History.jTextPane;
        taHistory.addHyperlinkListener(this::gotoLink);

        JSplitPane jSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                jpTAabout, jpTAhistory);
        jSplitPane.setBorder(new EmptyBorder(0, 3, 0, 0));
        jSplitPane.setResizeWeight(0.5f);

        jpMain.add(jSplitPane);

        jpContainerMain.add(jpMain);
    }

    private void gotoLink(HyperlinkEvent e) {
        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            if (Desktop.isDesktopSupported()) {
                String strLink = e.getDescription();
                try {
                    Desktop.getDesktop().browse(new URI(strLink));
                } catch (IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    @Override
    public void setViewItemParam(MenuNode itemNode) {
        String TXT_ABOUT = "/res/txt/about.txt";
        String TXT_HISTORY = "/res/txt/history.txt";
        readResourceFileToTextArea(TXT_ABOUT, taAbout);
        readResourceFileToTextArea(TXT_HISTORY, taHistory);
        jpMain.revalidate();
    }

    private static void readResourceFileToTextArea(String strFileNameInResource, JTextPane jTextPane) {
        InputStream inputStream = getMainClass().getResourceAsStream(strFileNameInResource);
        Reader reader = null;
        try {
            reader = new InputStreamReader(inputStream, "Cp1251");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        BufferedReader bufferedReader = null;
        if (reader != null) {
            bufferedReader = new BufferedReader(reader);
        }
        String oneLine;
        jTextPane.setText("");
        StringBuilder strArea = new StringBuilder();
        try {
            if (bufferedReader != null) {
                while ((oneLine = bufferedReader.readLine()) != null) {
                    if ((oneLine.contains(".png"))||
                            (oneLine.contains(".jpg"))||
                            (oneLine.contains(".gif")))
                    {
                        oneLine = "file:\\"+getFileNameResourceImgInTemp(oneLine.trim());
                    }
                    strArea.append(oneLine).append(LS);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        jTextPane.setText(strArea.toString());
        jTextPane.setCaretPosition(0);
    }
}

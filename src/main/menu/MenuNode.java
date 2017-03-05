package main.menu;


import javax.swing.tree.DefaultMutableTreeNode;

public class MenuNode extends DefaultMutableTreeNode {
    String namePanel = "";
    String nameCmd = "";
    String descriptionNode = "";
    String numNode = "";

    public MenuNode(Object userObject, boolean allowsChildren,
                    String namePanel,
                    String nameCmd,
                    String descriptionNode,
                    String numNode
    ) {
        super(userObject, allowsChildren);
        this.namePanel = namePanel;
        this.nameCmd = nameCmd;
        this.descriptionNode = descriptionNode;
        this.numNode = numNode;
    }

    public String getTextNode() {
        return getUserObject().toString();
    }

    public String getNameCmd() {
        return nameCmd;
    }

    public String getNamePanel() {
        return namePanel;
    }

    public String getDescriptionNode() {
        return descriptionNode;
    }

    public String getDescriptionNodeHTML() {
        return "<html>"+descriptionNode;
    }

    public int getNumNode() {
        return Integer.parseInt(numNode);
    }
}

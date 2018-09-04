package frm.treemenu;


import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

public class MenuNode extends DefaultMutableTreeNode {
    private String namePanel;
    private String nameCmd;
    private String descriptionNode;
    private int numNode;
    private TreePath treePath;

    public MenuNode(Object userObject, boolean allowsChildren,
                    String namePanel,
                    String nameCmd,
                    String descriptionNode
    ) {
        super(userObject, allowsChildren);
        this.namePanel = namePanel;
        this.nameCmd = nameCmd;
        this.descriptionNode = descriptionNode;
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

    public void setNumNode(int numNode) {
        this.numNode = numNode;
    }

    public int getNumNode() {
        return numNode;
    }

    public TreePath getTreePath() {
        return treePath;
    }

    public void setTreePath(TreePath treePath) {
        this.treePath = treePath;
    }
}

package frm.treemenu;

import containers.FrmContainerProgram;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static utils.UtilsForAll.getMainClass;
import static utils.UtilsForAll.setNewIconForTreeNode;

public abstract class TreeMenu {
    public JTree jtreeMenu;
    private DefaultTreeModel modelMenu;
    public JPanel jpMain;
    private int lastX;
    private int lastY;
    public int getLastX() {
        return lastX;
    }
    public void setLastX(int lastX) {
        this.lastX = lastX;
    }
    public int getLastY() {
        return lastY;
    }
    public void setLastY(int lastY) {
        this.lastY = lastY;
    }
    public void clearXY(){lastX = 0; lastY = 0;}
    public boolean isZeroMousePosition(){
        return ((lastX == 0) && (lastY == 0));
    }

    protected FrmContainerProgram mainContainer;
    protected FrmContainerProgram getMainContainer() {
        return mainContainer;
    }

    private XMLTreeMenuLoader xmlTreeMenuLoader;

    private MenuNode lastSelectedNode;

    public MenuNode getLastSelectedNode() {
        return lastSelectedNode;
    }
    public void setLastSelectedNode(MenuNode lastSelectedNode) {
        this.lastSelectedNode = lastSelectedNode;
    }

    public TreeMenu(FrmContainerProgram mainContainer, String strResourceMenu) {
        this.mainContainer = mainContainer;
        jtreeMenu = new JTree(new DefaultTreeModel(null));
        jtreeMenu.setRootVisible(false);
        jpMain = new JPanel(new GridLayout(1,1));
        jpMain.setBorder(new EmptyBorder(2,0,0,0));
        JPanel jpTree = new JPanel(new BorderLayout());
        jpTree.setBackground(Color.white);
        jpTree.setBorder(new EmptyBorder(5,5,5,5));
        jpTree.add(jtreeMenu);
        jpMain.add(new JScrollPane(jpTree));
        createTreeMenu(strResourceMenu);

        //перехватываем и отменяем работы с узлами разделов
        jtreeMenu.addTreeExpansionListener(new TreeExpansionListener() {
            @Override
            public void treeExpanded(TreeExpansionEvent event) {
                expandAllNodes();
            }

            @Override
            public void treeCollapsed(TreeExpansionEvent event) {
                expandAllNodes();
            }
        });

        //реагируем на одиночный и двойной клик на узле
        jtreeMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                int selRow = jtreeMenu.getRowForLocation(e.getX(), e.getY());
                if(selRow != -1) {
                    setLastSelectedNode((MenuNode) jtreeMenu.getLastSelectedPathComponent());
                    setLastX(e.getX());
                    setLastY(e.getY());
                    if(e.getClickCount() == 1) {
                        goOpenFrmForItemNode(getLastSelectedNode());
                    }
                } else setLastSelectedNode(null);
            }
        });
        jtreeMenu.addTreeSelectionListener(e -> setLastSelectedNode((MenuNode) jtreeMenu.getLastSelectedPathComponent()));
        jtreeMenu.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode()==10){
                    goOpenFrmForItemNode(getLastSelectedNode());
                }
            }
        });
    }

    public Point getPointLastMouseClick(){
        Point point = new Point(lastX, lastY);
        SwingUtilities.convertPointToScreen(point, jtreeMenu);
        return point;
    }

    public MenuNode getNodeByNamePanelAndFocusNode(String strNamePanel){
        HashMap<String, MenuNode> menuMap = xmlTreeMenuLoader.getMenuStorage();
        int index = 0;
        for (Map.Entry<String, MenuNode> menuNode: menuMap.entrySet()) {
            MenuNode value = menuNode.getValue();
            if (Objects.equals(value.getNamePanel(), strNamePanel)) {
                TreePath treePath = jtreeMenu.getPathForRow(menuMap.entrySet().size() - 2 - index);
                jtreeMenu.setSelectionPath(treePath);
                return value;
            }
            index++;
        }
        return null;
    }

    private void createTreeMenu(String strResourceMenu){
        InputStream inputStream = getMainClass().getResourceAsStream(strResourceMenu);
        xmlTreeMenuLoader = new XMLTreeMenuLoader(inputStream);
        try {xmlTreeMenuLoader.parse();} catch (Exception e) {e.printStackTrace();}

        modelMenu = new DefaultTreeModel(xmlTreeMenuLoader.getRootMainMenu());
        modelMenu.setAsksAllowsChildren(true);
        jtreeMenu.setModel(modelMenu);
        jtreeMenu.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        jtreeMenu.setCellRenderer(setNewIconForTreeNode());
        expandAllNodes();
        setNumNodeAllNodes();
    }

    private void expandAllNodes(){
        MenuNode rootNode = xmlTreeMenuLoader.getRootMainMenu();
        for (int i = 0; i<modelMenu.getChildCount(rootNode); i++){
            MenuNode itemNode = (MenuNode)modelMenu.getChild(rootNode,i);
            int itemCntNode = modelMenu.getChildCount(itemNode);
            if (itemCntNode != 0) {
                TreeNode[] nodes = modelMenu.getPathToRoot((MenuNode) modelMenu.getChild(itemNode, --itemCntNode));
                TreePath path = new TreePath(nodes);
                jtreeMenu.makeVisible(path);
            }
        }
    }

    private void setNumNodeAllNodes() {
        MenuNode root = xmlTreeMenuLoader.getRootMainMenu();
        Enumeration e;
        e = root.preorderEnumeration();
        int index = 0;
        while (e.hasMoreElements()) {
            MenuNode menuNode = (MenuNode) e.nextElement();
            if (!menuNode.getNameCmd().toLowerCase().contains("root")) {
                menuNode.setTreePath(new TreePath(menuNode));
                menuNode.setNumNode(index);
//                System.out.println(menuNode.getTextNode() +
//                        " - " + menuNode.getTreePath().toString() +
//                        " [" + menuNode.getNumNode() + "]"
//                );
                index++;
            } else  menuNode.setNumNode(-1);

        }
    }

    public abstract void goOpenFrmForItemNode(MenuNode itemNode);

    public Component getTreeAsComponent() {
        return jtreeMenu;
    }

    public int getRowNodeByNamePanel(String strNamePanel) {
        HashMap<String, MenuNode> menuMap = xmlTreeMenuLoader.getMenuStorage();
        int index = 0;
        for (Map.Entry<String, MenuNode> menuNode: menuMap.entrySet()) {
            MenuNode value = menuNode.getValue();
            if (Objects.equals(value.getNamePanel(), strNamePanel)) {
                return index;
            }
            index++;
        }
        return -1;
    }
}

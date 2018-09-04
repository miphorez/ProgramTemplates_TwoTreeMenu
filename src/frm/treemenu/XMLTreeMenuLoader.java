package frm.treemenu;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.LinkedList;

public class XMLTreeMenuLoader {
    private InputSource source;
    private SAXParser parser;
    private DefaultHandler documentHandler;

    private MenuNode rootMainMenu;
    public MenuNode getRootMainMenu() {
        return rootMainMenu;
    }

    private LinkedList menus = new LinkedList();
    private HashMap<String, MenuNode> menuStorage = new HashMap<>();

    public XMLTreeMenuLoader(InputStream stream) {
        try {
            Reader reader = new InputStreamReader(stream,"Cp1251");
            source = new InputSource(reader);
            parser = SAXParserFactory.newInstance().newSAXParser();
        } catch (Exception ex) {throw new RuntimeException(ex);}
        documentHandler = new XMLParser();
    }

    public void parse() throws Exception {
        parser.parse(source, documentHandler);
    }

    public MenuNode getMenuNode(String name) {
        return menuStorage.get(name);
    }

    class XMLParser extends DefaultHandler {

        public void startElement(String uri, String localName,
                                 String qName, Attributes attributes) {
            // определяем тип узла
            switch (qName) {
                case "menubar":
                    break;
                case "root":
                    parseRoot(attributes);
                    break;
                case "menu":
                    parseMenu(attributes);
                    break;
                case "menuitem":
                    parseMenuItem(attributes);
                    break;
            }
        }

        // конец узла, используется для для удаления из стека
        public void endElement(String uri, String localName, String qName) {
            if (qName.equals("menu")) menus.removeFirst();
        }

        protected void parseRoot(Attributes attrs) {
            String name = attrs.getValue("name");
            MenuNode menuNode = new MenuNode(
                    attrs.getValue("title"),
                    Boolean.parseBoolean(attrs.getValue("allowsChildren")),
                    attrs.getValue("namePanel"),
                    attrs.getValue("name"),
                    attrs.getValue("descriptionNode")
            );
            menuStorage.put(name, menuNode);
            rootMainMenu = menuNode;
        }

        protected void parseMenu(Attributes attrs) {
            String name = attrs.getValue("name");
            MenuNode menuNode = new MenuNode(
                    attrs.getValue("title"),
                    Boolean.parseBoolean(attrs.getValue("allowsChildren")),
                    attrs.getValue("namePanel"),
                    attrs.getValue("name"),
                    attrs.getValue("descriptionNode")
            );
            // добавляем меню корневому
            menuStorage.put(name, menuNode);
            int numRootNodes = rootMainMenu.getChildCount();
            rootMainMenu.insert(menuNode, numRootNodes);
            // добавляем в стек меню
            menus.addFirst(menuNode);
        }

        protected void parseMenuItem(Attributes attrs) {
            String name = attrs.getValue("name");
            MenuNode menuNode = new MenuNode(
                    attrs.getValue("title"),
                    Boolean.parseBoolean(attrs.getValue("allowsChildren")),
                    attrs.getValue("namePanel"),
                    attrs.getValue("name"),
                    attrs.getValue("descriptionNode")
            );
            menuStorage.put(name, menuNode);
            MenuNode lastNode = (MenuNode)menus.getFirst();
            int numChildNodes = lastNode.getChildCount();
            lastNode.insert(menuNode, numChildNodes);
        }
    }

    public HashMap<String, MenuNode> getMenuStorage() {
        return menuStorage;
    }
}

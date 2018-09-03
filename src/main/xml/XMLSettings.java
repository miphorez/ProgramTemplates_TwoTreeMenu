package main.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import utils.ConstantForAll;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.logging.Logger;

import static utils.ConstantForAll.FILE_XML_PARAMS;
import static utils.ConstantForAll.XML_ROOT_NODE;
import static utils.UtilsLogger.getFileNameFromProgramDataDir;

public class XMLSettings {
    private Logger logger;
    private String strFileName;

    private static XMLSettings instance;

    public static XMLSettings getInstance() {
        if (instance == null) {
            instance = new XMLSettings();
        }
        return instance;
    }

    XMLSettings() {
        strFileName = getFileNameFromProgramDataDir(FILE_XML_PARAMS);
    }

    Logger getLogger() {
        return logger;
    }

    void setLogger(Logger logger) {
        this.logger = logger;
    }

    public boolean isXMLSettingsFile() {
        return isXMLSettingsFile(strFileName) && controlVerXMLSettingsFile();
    }

    public boolean isXMLSettingsFile(String strName) {
        if (!Files.exists(Paths.get(strName), LinkOption.NOFOLLOW_LINKS)) {
            return createXMLSettingsFile(strName) && Files.exists(Paths.get(strName), LinkOption.NOFOLLOW_LINKS);
        }
        return true;
    }

    private boolean controlVerXMLSettingsFile() {
        XMLSettingsParsing xmlSettingsParsing = XMLSettingsParsing.getInstance();
        String strVersion = xmlSettingsParsing.getRootAttrFromSettingsDoc("Ver");
        TVersion tVersion = new TVersion(strVersion);
        loggerInfo("Версия файла настроек: "+ tVersion.getStrValueVer());
        if (tVersion.isEqVerLo(ConstantForAll.PROGRAM_VERSION)) {
            loggerInfo("Ошибка! Версия программы ниже, чем версия файла настроек");
            return false;
        }
        if (tVersion.isEqVer(ConstantForAll.PROGRAM_VERSION)) {
            return true;
        }
        if (tVersion.isEqVerHi(ConstantForAll.PROGRAM_VERSION)) {
            loggerInfo("Внимание! Новая версия файла настроек!");
            return XMLSettingsUpdate.getInstance().go();
        }
        return true;
    }

    public void loggerInfo(String strInfo){
        if (logger != null) logger.info(strInfo);
    }

    String getFileName() {
        return strFileName;
    }

    Document getSettingsDoc() {
        return getSettingsDoc(strFileName);
    }

    Document getSettingsDoc(String fileName) {
        Document doc;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(false);
        DocumentBuilder db;
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return null;
        }
        try {
            File file = new File(fileName);
            if (file.exists())
                doc = db.parse(new FileInputStream(file));
            else doc = null;
        } catch (SAXException | IOException e) {
            e.printStackTrace();
            return null;
        }
        return doc;
    }

    boolean setAttrItemScriptNode(Document doc,
                                  String strId,
                                  String strNodeName,
                                  String strAttr, String strValue) {
        if (Objects.equals(strId, "")) {
            //базовые атрибуты файла установок
            return setAttr(doc, strAttr, strValue);
        }

        NodeList nodeList = getNodeList(doc.getElementsByTagName("ItemScript"));
        if (nodeList == null) return false;

        for (int i = 0; i < nodeList.getLength(); i++) {
            Element node = (Element) nodeList.item(i);
            if (Objects.equals(node.getAttribute("id"), strId)) {
                if (Objects.equals(strNodeName, "ItemScript")) {
                    node.setAttribute(strAttr, strValue);
                    return true;
                }
                nodeList = node.getElementsByTagName(strNodeName);
                if (nodeList == null) return false;
                for (int j = 0; j < nodeList.getLength(); j++) {
                    ((Element) nodeList.item(j)).setAttribute(strAttr, strValue);
                }
                return true;
            }
        }
        return false;
    }

    boolean setAttr(Document doc, String strAttr, String strValue) {
        NodeList entries = doc.getElementsByTagName(XML_ROOT_NODE);

        int num;
        if (entries != null) {
            num = entries.getLength();
        } else return false;

        if (num == 0) return false;
        Element node = (Element) entries.item(0);
        node.setAttribute(strAttr, strValue);
        return true;
    }

    private NodeList getNodeList(NodeList itemNodeList) {
        int num;
        if (itemNodeList == null) return null;
        num = itemNodeList.getLength();
        if (num == 0) return null;
        return itemNodeList;
    }

    private boolean createXMLSettingsFile(String strName) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            DOMSource source = new DOMSource(createXMLFileParams());

            StreamResult result = new StreamResult(new File(strName));

            if (transformer != null) {
                transformer.transform(source, result);
            }

        } catch (ParserConfigurationException | TransformerException pce) {
            loggerInfo(pce.getMessage());
            return false;
        }
        return true;
    }

    private Document createXMLFileParams() throws ParserConfigurationException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        Document doc = docBuilder.newDocument();

        Element rootElement = doc.createElement(XML_ROOT_NODE);
        rootElement.setAttribute("Log", "1");
        rootElement.setAttribute("LogLevel", "INFO");
        rootElement.setAttribute("Ver", utils.ConstantForAll.PROGRAM_VERSION);
        rootElement.setAttribute("posXMain", "100");
        rootElement.setAttribute("posYMain", "100");
        rootElement.setAttribute("widthMain", "400");
        rootElement.setAttribute("hightMain", "240");
        doc.appendChild(rootElement);
        return doc;
    }
}

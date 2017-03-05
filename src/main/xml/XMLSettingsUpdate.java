package main.xml;

import org.w3c.dom.*;
import utils.ConstantForAll;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.logging.Logger;

import static utils.ConstantForAll.FILE_XML_PARAMS_TEMP;
import static utils.UtilsLogger.getFileNameFromProgramDataDir;

public class XMLSettingsUpdate extends XMLSettings {

    private static XMLSettingsUpdate instance;

    public static XMLSettingsUpdate getInstance() {
        if (instance == null) {
            instance = new XMLSettingsUpdate();
        }
        return instance;
    }

    public void setInstanceParam(Logger logger) {
        super.setLogger(logger);
    }

    public boolean go() {
        loggerInfo("==== Апдейт файла настроек ====");

        if (!moveParamsToTempFileSettings()) return false;
        loggerInfo("==== перенесены старые параметры в новый файл настроек");

        return (new File(getFileNameFromProgramDataDir(FILE_XML_PARAMS_TEMP)).delete());
    }

    private boolean moveParamsToTempFileSettings() {
        Document docXMLSettingsOld = getSettingsDoc();
        Document docXMLSettingsNew = getSettingsDoc(getFileNameFromProgramDataDir(FILE_XML_PARAMS_TEMP));

        transferNodeAttr("", docXMLSettingsOld.getFirstChild(), docXMLSettingsNew);
        XMLSettingsParsing.getInstance().setAttr(docXMLSettingsNew, "Ver", ConstantForAll.PROGRAM_VERSION);
        return !saveNewSettinsFile(docXMLSettingsNew, getFileName());
    }

    boolean saveNewSettinsFile(Document docXMLSettingsNew, String fileName) {
        Logger logger = getLogger();
        StreamResult result = new StreamResult(new File(fileName));
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            if (logger!=null) logger.info(e.getMessage());
            return true;
        }
        DOMSource source = new DOMSource(docXMLSettingsNew);
        if (transformer != null) {
            try {
                transformer.transform(source, result);
            } catch (TransformerException e) {
                if (logger!=null) logger.info(e.getMessage());
                return true;
            }
        }
        return false;
    }

    private void transferNodeAttr(String strId, Node nodeOld, Document docNew) {

        while ((nodeOld != null)&&(nodeOld.getNodeType() == Node.COMMENT_NODE))
            nodeOld = nodeOld.getNextSibling();
        if (nodeOld == null) return;

        String text = nodeOld.getNodeValue();
        if (text != null && !text.isEmpty() && (!text.contains("\n"))) {
            System.out.println(" value = \"" + text + "\"");
        }

        NamedNodeMap attributes = nodeOld.getAttributes();
        if (attributes != null) {
                //иначе перенести старые параметры
                transferAttrToNewSettinsById(docNew, strId, nodeOld, attributes);
        }

        NodeList children = nodeOld.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            transferNodeAttr(strId, children.item(i), docNew);
        }
    }

    private void transferAttrToNewSettinsById(Document doc,
                                                     String strId,
                                                     Node node,
                                                     NamedNodeMap attributes) {
        for (int i = 0; i < attributes.getLength(); i++) {
            Node attr = attributes.item(i);
            setAttrItemScriptNode(doc,
                    strId,
                    node.getNodeName(),
                    attr.getNodeName(),
                    attr.getTextContent());
        }
    }
}

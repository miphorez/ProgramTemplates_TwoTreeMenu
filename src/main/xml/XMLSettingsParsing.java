package main.xml;

import org.w3c.dom.*;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import static utils.ConstantForAll.XML_ROOT_NODE;

public class XMLSettingsParsing extends XMLSettings {
    private static XMLSettingsParsing instance;

    public static XMLSettingsParsing getInstance() {
        if (instance == null) {
            instance = new XMLSettingsParsing();
        }
        return instance;
    }

    public void setInstanceParam(Logger logger) {
        super.setLogger(logger);
    }

    public boolean getIsLogFromSettings() {
        return Objects.equals(getRootAttrFromSettingsDoc("Log"), "1");
    }

    public Level getLogLevelFromSettings() {
        String strLevel = getRootAttrFromSettingsDoc("LogLevel");
        return ELogLevel.getLevelbyName(strLevel);
    }

    public String getRootAttr(String strAttr, String strPreset) {
        String iStr = getRootAttrFromSettingsDoc(strAttr);
        if (Objects.equals(iStr, "")) return strPreset;
        else return iStr;
    }

    public String getRootAttrFromSettingsDoc(String strAttr) {
        NodeList entries;
        Document doc = getSettingsDoc();
        if (doc != null) {
            entries = doc.getElementsByTagName(XML_ROOT_NODE);
        } else return "";

        int num;
        if (entries != null) {
            num = entries.getLength();
        } else return "";

        if (num == 0) return "";
        Element node = (Element) entries.item(0);
        if (node.hasAttribute(strAttr))
        return node.getAttribute(strAttr);
        else return "";
    }

    public boolean setRootAttrToSettingsDoc(String strAttr, String strValue) {
        Document doc = getSettingsDoc();
        return doc != null &&
                setAttr(doc, strAttr, strValue) &&
                !(XMLSettingsUpdate.getInstance().saveNewSettinsFile(doc, getFileName()));
    }

}

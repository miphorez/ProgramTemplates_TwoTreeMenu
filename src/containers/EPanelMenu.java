package containers;

public enum EPanelMenu {
    PANEL_MENU_NONE(""),
    PANEL_MENU_COMMAND(""),
    PANEL_MENU_PARAM(""),
    ;
    private String strVal = "";

    EPanelMenu(String strVal) {
        this.strVal = strVal;
    }
    public String getStrVal() {
        return strVal;
    }

    static public EPanelMenu getType(String str) {
        for (EPanelMenu typeData: EPanelMenu.values()) {
            if (typeData.name().equals(str)) {
                return typeData;
            }
        }
        return PANEL_MENU_NONE;
    }

 }

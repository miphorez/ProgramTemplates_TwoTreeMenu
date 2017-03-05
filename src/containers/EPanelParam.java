package containers;

public enum EPanelParam {
    PANEL_NONE,
    PANEL_PARAM_1,
    PANEL_PARAM_2,
    PANEL_PARAM_21,
    PANEL_PARAM_3,
    PANEL_PARAM_31,
    PANEL_PARAM_32,
    PANEL_PARAM_ABOUT,
            ;

    static public EPanelParam getType(String str) {
        for (EPanelParam typeData: values()) {
            if (typeData.name().equals(str)) {
                return typeData;
            }
        }
        return PANEL_NONE;
    }
}

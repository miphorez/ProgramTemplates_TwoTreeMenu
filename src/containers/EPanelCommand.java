package containers;

public enum EPanelCommand {
    PANEL_NONE,
    PANEL_CMD_1,
    PANEL_CMD_11,
    PANEL_CMD_12,
    PANEL_CMD_2,
    PANEL_CMD_21,
    PANEL_CMD_3,
            ;

    static public EPanelCommand getType(String str) {
        for (EPanelCommand typeData: values()) {
            if (typeData.name().equals(str)) {
                return typeData;
            }
        }
        return PANEL_NONE;
    }
}

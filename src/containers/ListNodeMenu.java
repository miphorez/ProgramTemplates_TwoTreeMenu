package containers;


public enum ListNodeMenu {
    NODE_ROOT_PARAM_MENU,
    NODE_PARAM_1,
    NODE_PARAM_2,
    NODE_PARAM_21,
    NODE_PARAM_3,
    NODE_PARAM_31,
    NODE_PARAM_32,
    NODE_PARAM_ABOUT,
    //
    NODE_ROOT_COMMAND_MENU,
    NODE_CMD_1,
    NODE_CMD_11,
    NODE_CMD_12,
    NODE_CMD_2,
    NODE_CMD_21,
    NODE_CMD_3,
    //
    DEBUG,
    NONE,
    ;

    static public ListNodeMenu getType(String pType) {
        for (ListNodeMenu type: ListNodeMenu.values()) {
            if (type.name().equals(pType)) {
                return type;
            }
        }
        return NONE;
    }

}

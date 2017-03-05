package containers;


public enum ListNodeMenu {
    NODE_ROOT_PARAM_MENU(200),
    NODE_PARAM_1(201),
    NODE_PARAM_2(201),
    NODE_PARAM_21(202),
    NODE_PARAM_3(203),
    NODE_PARAM_31(204),
    NODE_PARAM_32(205),
    //
    NODE_ROOT_COMMAND_MENU(100),
    NODE_CMD_1(101),
    NODE_CMD_11(102),
    NODE_CMD_12(103),
    NODE_CMD_2(104),
    NODE_CMD_21(105),
    NODE_CMD_3(106),
    //
    DEBUG(1),
    NONE(0),
    ;
    private int cmdVal = 0;

    ListNodeMenu(int cmdVal) {
        this.cmdVal = cmdVal;
    }

    public int getListCmd() {
        return cmdVal;
    }

    static public ListNodeMenu getType(String pType) {
        for (ListNodeMenu type: ListNodeMenu.values()) {
            if (type.name().equals(pType)) {
                return type;
            }
        }
        return NONE;
    }

    static public int getTypeCmd(String pType) {
        for (ListNodeMenu type: ListNodeMenu.values()) {
            if (Integer.toString(type.cmdVal).equals(pType)) {
                return type.getListCmd();
            }
        }
        return 0;
    }
}

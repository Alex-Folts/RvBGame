package com.me.rvbgame;

import com.badlogic.gdx.graphics.Color;

public class StatsHelper {

    public static final Color COLOR_DARK_RED = new Color(0.5f, 0, 0, 1);
    public static final Color COLOR_DARK_BLUE = new Color(0, 0, 0.5f, 1);
    public static final Color COLOR_DARK_GREEN = new Color(0, 0.5f, 0, 1);
    
    public static final Color COLOR_EMPTY_UNIT_SLOT = new Color(0, 1, 1, 1);
    public static final Color COLOR_BLACK_ALPHA_0_5 = new Color(0, 0, 0, 0.5f);
    public static final float SPACING = 120.0f;

    public static String whatUnit(UnitType unitType) {
        switch (unitType) {
            case UNIT_TYPE_DEFENDER:
                return "UNIT_TYPE_DEFENDER";
//                break;
            case UNIT_TYPE_MELEE:
                return "UNIT_TYPE_MELEE";
//                break;
            case UNIT_TYPE_SPECIAL:
                return "UNIT_TYPE_SPECIAL";
//                break;
            case UNIT_TYPE_RANGED:
                return "UNIT_TYPE_RANGED";
//                break;
            case UNIT_TYPE_RANGED_MASS:
                return "UNIT_TYPE_MASS";
//                break;
            case UNIT_TYPE_TOWER:
                break;
        }
        return "";
    }
}

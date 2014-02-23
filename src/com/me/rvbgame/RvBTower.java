package com.me.rvbgame;

public class RvBTower extends RvBUnit {

    public RvBTower(BattleScreen parentScreen, RvBPlayer playerOwner) {
        super(parentScreen, playerOwner);
        this.unitType = UnitType.UNIT_TYPE_TOWER;
    }
}

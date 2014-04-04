package com.me.rvbgame.units;

import com.me.rvbgame.RvBPlayer;
import com.me.rvbgame.RvBUnit;
import com.me.rvbgame.UnitType;
import com.me.rvbgame.screens.BattleScreen;

public class UnitDefender extends RvBUnit {

    public UnitDefender(BattleScreen parentScreen, RvBPlayer playerOwner, String jsonData) {
        super(parentScreen, playerOwner, jsonData);
        this.unitType = UnitType.UNIT_TYPE_DEFENDER;
    }
	
    public UnitDefender(BattleScreen parentScreen, RvBPlayer playerOwner) {
        super(parentScreen, playerOwner);
        this.unitType = UnitType.UNIT_TYPE_DEFENDER;
//        StatsHelper.initiate(this);

    }
	
    public UnitDefender(){
        this.unitType = UnitType.UNIT_TYPE_DEFENDER;
//        StatsHelper.initiate(this);
    }
}

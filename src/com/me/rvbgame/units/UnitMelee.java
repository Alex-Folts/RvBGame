package com.me.rvbgame.units;

import com.me.rvbgame.RvBPlayer;
import com.me.rvbgame.RvBUnit;
import com.me.rvbgame.UnitType;
import com.me.rvbgame.screens.BattleScreen;

public class UnitMelee extends RvBUnit{

    public UnitMelee(BattleScreen parentScreen, RvBPlayer playerOwner, String jsonData) {
        super(parentScreen, playerOwner, jsonData);

        this.unitType = UnitType.UNIT_TYPE_MELEE;
    }
	
	public UnitMelee(BattleScreen parentScreen, RvBPlayer playerOwner) {
        super(parentScreen, playerOwner);

        this.unitType = UnitType.UNIT_TYPE_MELEE;
    }

    public UnitMelee(){
        this.unitType = UnitType.UNIT_TYPE_MELEE;
    }
}

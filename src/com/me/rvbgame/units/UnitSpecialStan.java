package com.me.rvbgame.units;

import com.me.rvbgame.screens.BattleScreen;
import com.me.rvbgame.RvBPlayer;
import com.me.rvbgame.RvBUnit;
import com.me.rvbgame.UnitType;

public class UnitSpecialStan extends RvBUnit{

    public UnitSpecialStan(BattleScreen parentScreen, RvBPlayer playerOwner, String jsonData) {
        super(parentScreen, playerOwner, jsonData);
        this.unitType = UnitType.UNIT_TYPE_SPECIAL;
//        StatsHelper.initiate(this);
    }
	
	public UnitSpecialStan(BattleScreen parentScreen, RvBPlayer playerOwner) {
        super(parentScreen, playerOwner);
        this.unitType = UnitType.UNIT_TYPE_SPECIAL;
//        StatsHelper.initiate(this);
    }

    public UnitSpecialStan(){
        this.unitType = UnitType.UNIT_TYPE_SPECIAL;
//        StatsHelper.initiate(this);
    }

    void useAim(){
        if(this.getAttackRange() <= 2)
            this.setAttackRange((byte) 3);
        else
        if (this.getTargetsNum() < 5)
            this.setTargetsNum((byte) (this.getTargetsNum()+1));
    }
}

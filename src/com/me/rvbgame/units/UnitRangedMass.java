package com.me.rvbgame.units;

import com.me.rvbgame.BattleScreen;
import com.me.rvbgame.RvBPlayer;
import com.me.rvbgame.RvBUnit;
import com.me.rvbgame.UnitType;

public class UnitRangedMass extends RvBUnit {

    public UnitRangedMass(BattleScreen parentScreen, RvBPlayer playerOwner) {
        super(parentScreen, playerOwner);

        this.unitType = UnitType.UNIT_TYPE_RANGED_MASS;
        StatsHelper.initiate(this);
    }

    public UnitRangedMass(){
        this.unitType = UnitType.UNIT_TYPE_RANGED_MASS;
        StatsHelper.initiate(this);
    }
}

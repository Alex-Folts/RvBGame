package com.me.rvbgame.units;

import com.me.rvbgame.BattleScreen;
import com.me.rvbgame.RvBPlayer;
import com.me.rvbgame.RvBUnit;
import com.me.rvbgame.UnitType;

public class UnitRanged extends RvBUnit{

    private int criticalChance = 1;

    public UnitRanged(BattleScreen parentScreen, RvBPlayer playerOwner) {
        super(parentScreen, playerOwner);

        this.unitType = UnitType.UNIT_TYPE_RANGED;
        StatsHelper.initiate(this);
    }

    public UnitRanged(){
        this.unitType = UnitType.UNIT_TYPE_RANGED;
        StatsHelper.initiate(this);
    }

    void useAim(){
        if (this.criticalChance<=25)
            this.criticalChance *= 2;
        else if (this.criticalChance <= 99)
            this.criticalChance += 5;
    }

}

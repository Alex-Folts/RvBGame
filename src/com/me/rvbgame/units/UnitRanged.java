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

        this.setHealth(StatsHelper.MIN_VALUE);
        this.setEnergy(StatsHelper.AVERAGE_VALUE);

        this.setpAttack(StatsHelper.MAX_VALUE);
        this.setpDefence(StatsHelper.MIN_VALUE);

        this.setiAttack(StatsHelper.MAX_VALUE);
        this.setiDefence(StatsHelper.MIN_VALUE);

        this.setTargetsNum((byte) 1);
        this.setAttackRange((byte) 3);
    }

    void useAim(){
        if (this.criticalChance<=25)
            this.criticalChance *= 2;
        else if (this.criticalChance <= 99)
            this.criticalChance += 5;
    }

}

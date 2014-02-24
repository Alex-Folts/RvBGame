package com.me.rvbgame.units;

import com.me.rvbgame.BattleScreen;
import com.me.rvbgame.RvBPlayer;
import com.me.rvbgame.RvBUnit;
import com.me.rvbgame.UnitType;

public class UnitRangedMass extends RvBUnit {

    public UnitRangedMass(BattleScreen parentScreen, RvBPlayer playerOwner) {
        super(parentScreen, playerOwner);
        this.unitType = UnitType.UNIT_TYPE_RANGED_MASS;

        this.setHealth(StatsHelper.AVERAGE_VALUE);
        this.setEnergy(StatsHelper.MAX_VALUE);

        this.setpAttack(StatsHelper.AVERAGE_VALUE);
        this.setpDefence(StatsHelper.AVERAGE_VALUE);

        this.setiAttack(StatsHelper.AVERAGE_VALUE);
        this.setiDefence(StatsHelper.AVERAGE_VALUE);

        this.setTargetsNum((byte) 5);
        this.setAttackRange((byte) 3);
    }
}

package com.me.rvbgame.units;

import com.me.rvbgame.BattleScreen;
import com.me.rvbgame.RvBPlayer;
import com.me.rvbgame.RvBUnit;
import com.me.rvbgame.UnitType;

public class UnitMelee extends RvBUnit{
    public UnitMelee(BattleScreen parentScreen, RvBPlayer playerOwner) {
        super(parentScreen, playerOwner);

        this.unitType = UnitType.UNIT_TYPE_MELEE;

        this.setHealth(StatsHelper.MAX_VALUE);
        this.setEnergy(StatsHelper.AVERAGE_VALUE);

        this.setpAttack(StatsHelper.MAX_VALUE);
        this.setpDefence(StatsHelper.AVERAGE_VALUE);

        this.setiAttack(StatsHelper.AVERAGE_VALUE);
        this.setiDefence(StatsHelper.AVERAGE_VALUE);

        this.setTargetsNum((byte) 1);
        this.setAttackRange((byte) 1);
    }
}

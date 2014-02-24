package com.me.rvbgame.units;

import com.me.rvbgame.BattleScreen;
import com.me.rvbgame.RvBPlayer;
import com.me.rvbgame.RvBUnit;
import com.me.rvbgame.UnitType;

public class UnitDefender extends RvBUnit {

    public UnitDefender(BattleScreen parentScreen, RvBPlayer playerOwner) {
        super(parentScreen, playerOwner);
        this.unitType = UnitType.UNIT_TYPE_DEFENDER;

        this.setHealth(StatsHelper.MAX_VALUE);
        this.setEnergy(StatsHelper.ZERO_VALUE);

        this.setpAttack(StatsHelper.AVERAGE_VALUE);
        this.setpDefence(StatsHelper.MAX_VALUE);

        this.setiAttack(StatsHelper.ZERO_VALUE);
        this.setiDefence(StatsHelper.MAX_VALUE);

        this.setTargetsNum((byte) 1);
        this.setAttackRange((byte) 1);
    }
}

package com.me.rvbgame.units;

import com.me.rvbgame.BattleScreen;
import com.me.rvbgame.RvBPlayer;
import com.me.rvbgame.RvBUnit;
import com.me.rvbgame.UnitType;

public class UnitSpecialStan extends RvBUnit{
    public UnitSpecialStan(BattleScreen parentScreen, RvBPlayer playerOwner) {
        super(parentScreen, playerOwner);
        this.unitType = UnitType.UNIT_TYPE_SPECIAL;

        this.setHealth(StatsHelper.MAX_VALUE);
        this.setEnergy(StatsHelper.AVERAGE_VALUE);

        this.setpAttack(StatsHelper.MAX_VALUE);
        this.setpDefence(StatsHelper.AVERAGE_VALUE);

        this.setiAttack(StatsHelper.AVERAGE_VALUE);
        this.setiDefence(StatsHelper.AVERAGE_VALUE);

        this.setTargetsNum((byte) 1);
        this.setAttackRange((byte) 2);
    }

    void useAim(){
        if(this.getAttackRange() <= 2)
            this.setAttackRange((byte) 3);
        else
        if (this.getTargetsNum() < 5)
            this.setTargetsNum((byte) (this.getTargetsNum()+1));
    }
}

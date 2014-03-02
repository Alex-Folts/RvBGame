package com.me.rvbgame;

public class RvBCurrentUnit extends RvBUnit {

    public RvBCurrentUnit(BattleScreen parentScreen, RvBPlayer playerOwner, UnitType unitType) {
        super(parentScreen, playerOwner);
        this.unitType = unitType;
    }
    
    @Override
    public void resize(int width, int height) {
    	super.resize(width, height);
    }
}

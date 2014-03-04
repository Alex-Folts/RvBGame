package com.me.rvbgame;

public abstract class RvBBase {

	BattleScreen battleScreen;
	
	public RvBBase(BattleScreen parentScreen) {
		battleScreen = parentScreen;
	}

    protected RvBBase() {
    }
}

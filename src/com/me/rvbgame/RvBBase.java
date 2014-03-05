package com.me.rvbgame;

public abstract class RvBBase {

	BattleScreen battleScreen;
	
	public RvBBase(BattleScreen parentScreen) {
		battleScreen = parentScreen;
	}

	public RvBBase() {
		
	}
	
	public void show() {

	}
	
	public void resize(int width, int height) {

	}

	public void dispose() {

	}
}

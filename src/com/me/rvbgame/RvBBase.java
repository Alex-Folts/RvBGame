package com.me.rvbgame;

import com.me.rvbgame.screens.BattleScreen;

public abstract class RvBBase {

	BattleScreen battleScreen;
	
	public RvBBase(BattleScreen parentScreen) {
		battleScreen = parentScreen;
	}

	public void addToDraw() {
		
	}
	
	public void removeFromDraw() {
		
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

package com.me.rvbgame;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class RvBWorld extends RvBBase {

	private boolean currentTurnRight = true;
	private String bgPath = "";
	
	private Image bgImage;
	
//	private playerLeft;
//	private playerRight;
	
	public RvBWorld(BattleScreen parentScreen) {
		super(parentScreen);
	}

	public static boolean Damage(/*attacker, victim,*/ int attackID) {
		return false;
	}
	
	public boolean PerformTickDamage() {
		return false;
	}
	
	public static boolean Heal(/*heller, target,*/ int attackID) {
		return false;
	}
	
	public boolean CalcTurn() {
		currentTurnRight = !currentTurnRight;
		if (currentTurnRight) {
//			playerRight.beginTurn();
		} else {
//			playerLeft.beginTurn();
		}
		return true;
	}
	
	public boolean endTurn() {
		return CalcTurn();
	}
}

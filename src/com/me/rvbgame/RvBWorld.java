package com.me.rvbgame;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class RvBWorld extends RvBBase {

	private boolean currentTurnRight = true;
	private String bgPath = "";
	
	private Image bgImage;
	
	private RvBPlayer playerLeft;
	private RvBPlayer playerRight;
	
	public RvBWorld(BattleScreen parentScreen) {
		super(parentScreen);
	}

	public static boolean Damage(RvBUnit attacker, RvBUnit victim, int attackID) {
		int finalDamage;
		
		if (RvBUnit.IsPhysAttack(attackID))
		{
			finalDamage = attacker.getpAttack() - victim.getpDefence();
		} else
		{
			finalDamage = attacker.getiAttack() - victim.getiDefence();
		}
		
		if (finalDamage <= 0)
		{
			return false;
		}
		
		int newHealth = victim.getHealth() - finalDamage;
		if (newHealth <= 0)
		{
			newHealth = 0;
			victim.setbDead(true);
		}
		victim.setHealth(newHealth);
		
		return true;
	}
	
	public boolean PerformTickDamage() {
		return false;
	}
	
	public static boolean Heal(RvBUnit heller, RvBUnit target, int attackID) {
		return false;
	}
	
	public boolean CalcTurn() {
		currentTurnRight = !currentTurnRight;
		if (currentTurnRight) {
			playerRight.beginTurn();
		} else {
			playerLeft.beginTurn();
		}
		return true;
	}
	
	public boolean endTurn(RvBPlayer player) {
		player.isMyTurn = false;
		return CalcTurn();
	}
	
	public RvBPlayer getCurrentTurnPlayer() {
		if (currentTurnRight)
		{
			return playerRight;
		}

		return playerLeft;
	}
}

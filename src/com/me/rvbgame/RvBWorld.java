package com.me.rvbgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Scaling;

public class RvBWorld extends RvBBase {

	private boolean currentTurnRight = true;
	private String bgPath = "data/Andr_087098.png";
	
	private Image bgImage;
	private Texture bgTexture;
	
	protected RvBPlayer playerLeft;
	protected RvBPlayer playerRight;
	
	static final Vector2 LEFT_TOWER_SLOT = new Vector2(0, 0);
	static final Vector2 LEFT_UNIT_SLOT01 = new Vector2(0, 0);
	static final Vector2 LEFT_UNIT_SLOT02 = new Vector2(0, 0);
	static final Vector2 LEFT_UNIT_SLOT03 = new Vector2(0, 0);
	static final Vector2 LEFT_UNIT_SLOT04 = new Vector2(0, 0);
	static final Vector2 LEFT_UNIT_SLOT05 = new Vector2(0, 0);

	static final Vector2 RIGHT_TOWER_SLOT = new Vector2(0, 0);
	static final Vector2 RIGHT_UNIT_SLOT01 = new Vector2(0, 0);
	static final Vector2 RIGHT_UNIT_SLOT02 = new Vector2(0, 0);
	static final Vector2 RIGHT_UNIT_SLOT03 = new Vector2(0, 0);
	static final Vector2 RIGHT_UNIT_SLOT04 = new Vector2(0, 0);
	static final Vector2 RIGHT_UNIT_SLOT05 = new Vector2(0, 0);
	
	public RvBWorld(BattleScreen parentScreen) {
		super(parentScreen);
		
		playerLeft = new RvBPlayer(battleScreen);
		playerRight = new RvBPlayer(battleScreen);
		
		calcTurn();
	}

	public static boolean damage(RvBUnit attacker, RvBUnit victim, int attackID) {
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
	
	public boolean performTickDamage() {
		return false;
	}
	
	public static boolean Heal(RvBUnit heller, RvBUnit target, int attackID) {
		return false;
	}
	
	public boolean calcTurn() {
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
		return calcTurn();
	}
	
	public RvBPlayer getCurrentTurnPlayer() {
		if (currentTurnRight)
		{
			return playerRight;
		}

		return playerLeft;
	}
	
	@Override
	public void show() {
		super.show();
		
		if (playerLeft != null){
			playerLeft.show();
		}
		if (playerRight != null){
			playerRight.show();
		}
		
		bgTexture = new Texture(Gdx.files.internal(bgPath));
		bgTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	}
	
	@Override
	public void dispose() {
		super.dispose();
		
		if (playerLeft != null){
			playerLeft.dispose();
		}
		if (playerLeft != null){
			playerRight.dispose();
		}
		
		bgTexture.dispose();
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		
		if (playerLeft != null){
			playerLeft.resize(width, height);
		}
		if (playerLeft != null){
			playerRight.resize(width, height);
		}
		
		TextureRegion region = new TextureRegion(bgTexture, 0, 0, 512, 288);
		
		bgImage = new Image(region);
		bgImage.setScaling(Scaling.stretch);
		bgImage.setAlign((Align.bottom | Align.left));
		bgImage.setSize(width, height);
//		bgImage.setZIndex(WorldDrawLayer.DRAW_LAYER_BG);
		
//		battleScreen.stage.addActor(bgImage);
//		battleScreen.sceneLayerBG.addActor(bgImage);
	}
}

package com.me.rvbgame;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class RvBWorld extends RvBBase {

	private boolean currentTurnRight = true;
	private String bgPath = "";
	
	private Image bgImage;
	
	public RvBWorld(BattleScreen parentScreen) {
		super(parentScreen);
	}

}

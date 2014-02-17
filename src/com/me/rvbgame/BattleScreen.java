package com.me.rvbgame;

public class BattleScreen extends GameScreen {

	protected final RvBWorld world;
	
	public BattleScreen(RvbGdxGame game) {
		super(game);
		
		world = new RvBWorld(this);
	}

}

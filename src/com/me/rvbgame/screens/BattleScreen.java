package com.me.rvbgame.screens;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.me.rvbgame.RvBRadialMenu;
import com.me.rvbgame.RvBWorld;
import com.me.rvbgame.RvbGdxGame;

public class BattleScreen extends GameScreen {

	public RvBWorld world;
    public Group sceneLayerZero = new Group();
	public Group sceneLayerBG = new Group();
	public Group sceneLayerTowers = new Group();
	public Group sceneLayerUnits = new Group();
    public Group sceneLayerFX = new Group();
    public RvBRadialMenu sceneLayerRadialMenu = null;
	public Group sceneLayerGUI = new Group();
	public Group sceneLayerMenu = new Group();
    public Group sceneLayerTop = new Group();
	
	public float screenResW;
	public float screenResH;
	
	public BattleScreen(RvbGdxGame game) {
		super(game);
		
		world = new RvBWorld(this);
		
		stage.addActor(sceneLayerZero);
		stage.addActor(sceneLayerBG);
		stage.addActor(sceneLayerTowers);
		stage.addActor(sceneLayerUnits);
		stage.addActor(sceneLayerFX);
//		stage.addActor(sceneLayerRadialMenu);
		stage.addActor(sceneLayerGUI);
		stage.addActor(sceneLayerMenu);
		stage.addActor(sceneLayerTop);
		
/*		if (world != null)
		{
			world.initWorld();
		}*/
	}

	@Override
	public void show() {
		super.show();

		if (world != null){
			world.show();
		}
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		
		screenResW = width;
		screenResH = height;
		
		if (world != null){
			world.resize(width, height);
		}
	}
	
	@Override
	public void dispose() {
		super.dispose();
		
		if (world != null){
			world.dispose();
		}
	}
}
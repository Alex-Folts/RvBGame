package com.me.rvbgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;

public class BattleScreen extends GameScreen {

	protected final RvBWorld world;
	protected Group sceneLayerZero = new Group();
	protected Group sceneLayerBG = new Group();
	protected Group sceneLayerTowers = new Group();
	protected Group sceneLayerUnits = new Group();
	protected Group sceneLayerFX = new Group();
	protected Group sceneLayerRadialmenu = new Group();
	protected Group sceneLayerGUI = new Group();
	protected Group sceneLayerMenu = new Group();
	protected Group sceneLayerTop = new Group();
	
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
		stage.addActor(sceneLayerRadialmenu);
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

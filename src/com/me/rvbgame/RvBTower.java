package com.me.rvbgame;

import com.badlogic.gdx.Gdx;

public class RvBTower extends RvBUnit {

//	public String avaPath = "data/towerimage01.png";
//	protected String avaPath = "data/Andr_087098.png";

    public RvBTower(BattleScreen parentScreen, RvBPlayer playerOwner, String jsonData) {
        super(parentScreen, playerOwner, jsonData);
        this.unitType = UnitType.UNIT_TYPE_TOWER;
    }
	
    public RvBTower(BattleScreen parentScreen, RvBPlayer playerOwner) {
        super(parentScreen, playerOwner);
        this.unitType = UnitType.UNIT_TYPE_TOWER;
    }
    
    public RvBTower() {
//		super();
	}
    
    @Override
    public void resize(int width, int height) {
    	super.resize(width, height);
    	
//    	avaImage.setZIndex(WorldDrawLayer.DRAW_LAYER_TOWER);
//    	avaImage.setPosition(100, 100);
    	Gdx.app.log("RvB", "RvBTower:resize "+avaPath);
    	
    	if (player == battleScreen.world.playerRight)
    	{
    		avaImage.setPosition(width - avaImage.getWidth(), height - avaImage.getHeight());
    	}
    }
}

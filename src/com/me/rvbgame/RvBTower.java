package com.me.rvbgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class RvBTower extends RvBUnit {

	protected String avaPath = "data/towerimage01.png";
//	protected String avaPath = "data/Andr_087098.png";
	
    public RvBTower(BattleScreen parentScreen, RvBPlayer playerOwner) {
        super(parentScreen, playerOwner);
        this.unitType = UnitType.UNIT_TYPE_TOWER;
    }
    
    public String getImagePath() {
		return avaPath;
	}
    
    public int getAvaImagwWidth() {
		return 512;
	}
    
    public int getAvaImagwHeight() {
		return 512;
	}
    
    public Vector2 getAvaSize() {
		return new Vector2(128, 128);
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

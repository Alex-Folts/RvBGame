package com.me.rvbgame;



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
    
/*    @Override
    public void resize(int width, int height) {
    	super.resize(width, height);
    	
//    	avaImage.setZIndex(WorldDrawLayer.DRAW_LAYER_TOWER);
//    	avaImage.setPosition(100, 100);
    	Gdx.app.log("RvB", "RvBTower:resize "+avaPath);
    	
    	if (avaImage != null)
    	{
	    	if (player == battleScreen.world.playerRight)
	    	{
	    		avaImage.setPosition(RvBWorld.RIGHT_TOWER_SLOT.x - (avaImage.getWidth() * 0.5f), RvBWorld.RIGHT_TOWER_SLOT.y - (avaImage.getHeight() * 0.5f));
	    	} else
	    	{
	    		avaImage.setPosition(RvBWorld.LEFT_TOWER_SLOT.x - (avaImage.getWidth() * 0.5f), RvBWorld.LEFT_TOWER_SLOT.y - (avaImage.getHeight() * 0.5f));
	    	}
    	}
    }*/
    
    @Override
    	public void resize(int width, int height) {
    		super.resize(width, height);
    		
        	if (avaImage != null)
        	{
        		float deltaWidthCoef = (battleScreen.screenResW / RvBWorld.WORLD_NATIVE_RES.x);
        		float deltaHeightCoef = (battleScreen.screenResH / RvBWorld.WORLD_NATIVE_RES.y);
        		float deltaAvaSizeW = getAvaSize().x * deltaHeightCoef;
        		
    	    	if (player == battleScreen.world.playerRight)
    	    	{
/*    	    		Gdx.app.log("RvB", "addToDraw "+RvBWorld.WORLD_NATIVE_RES.x);
    	    		Gdx.app.log("RvB", "addToDraw "+battleScreen.screenResW);
    	    		Gdx.app.log("RvB", "addToDraw "+deltaWidthCoef);
    	    		Gdx.app.log("RvB", "addToDraw "+deltaHeightCoef);
    	    		Gdx.app.log("RvB", "addToDraw "+deltaAvaSizeW);
    	    		Gdx.app.log("RvB", "addToDraw "+deltaAvaSizeW / battleScreen.screenResW);
    	    		Gdx.app.log("RvB", "addToDraw "+deltaAvaSizeW / battleScreen.screenResH);*/
    	    		avaImage.setPosition((RvBWorld.RIGHT_TOWER_SLOT.x * deltaWidthCoef * (1.0f + ((deltaAvaSizeW - getAvaSize().x) / battleScreen.screenResW))), RvBWorld.RIGHT_TOWER_SLOT.y * deltaHeightCoef);
    	    	} else
    	    	{
    	    		avaImage.setPosition((RvBWorld.LEFT_TOWER_SLOT.x * deltaWidthCoef * (1.0f + ((deltaAvaSizeW - getAvaSize().x) / battleScreen.screenResW))), RvBWorld.LEFT_TOWER_SLOT.y * deltaHeightCoef);
    	    	}
    	    	avaImage.setSize(deltaAvaSizeW, deltaAvaSizeW);
        	}
    	}
/*    
    @Override
    	public void addToDraw() {
    		super.addToDraw();
    		
        	if (avaImage != null)
        	{
    	    	if (player == battleScreen.world.playerRight)
    	    	{
    	    		Gdx.app.log("RvB", "addToDraw "+RvBWorld.WORLD_NATIVE_RES.x);
    	    		Gdx.app.log("RvB", "addToDraw "+battleScreen.screenResW);
    	    		Gdx.app.log("RvB", "addToDraw "+(RvBWorld.WORLD_NATIVE_RES.x / battleScreen.screenResW));
    	    		avaImage.setPosition((RvBWorld.RIGHT_TOWER_SLOT.x * (RvBWorld.WORLD_NATIVE_RES.x / battleScreen.screenResW)) - (avaImage.getWidth() * 0.5f), RvBWorld.RIGHT_TOWER_SLOT.y - (avaImage.getHeight() * 0.5f));
    	    	} else
    	    	{
    	    		avaImage.setPosition((RvBWorld.LEFT_TOWER_SLOT.x * (RvBWorld.WORLD_NATIVE_RES.x / battleScreen.screenResW)) - (avaImage.getWidth() * 0.5f), RvBWorld.LEFT_TOWER_SLOT.y - (avaImage.getHeight() * 0.5f));
    	    	}
        	}
    	}*/
}

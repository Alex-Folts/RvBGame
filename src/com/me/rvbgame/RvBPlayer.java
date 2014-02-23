package com.me.rvbgame;

public class RvBPlayer extends RvBBase{

    public RvBTower tower;
    public RvBUnit[] units;

    public RvBUnit slot1;
    public RvBUnit slot2;
    public RvBUnit slot3;
    public RvBUnit slot4;
    public RvBUnit slot5;
    
    public boolean isAI;
    public boolean isMyTurn;

    private RvBUnit actingUnit;
    
//    private RvBWorld world;

    public RvBPlayer(BattleScreen parentScreen/*, RvBWorld world*/) {
        super(parentScreen);
//        this.world = world;
    }

    public void beginTurn()
    {
    	isMyTurn = true;
    	ClearCorpses();
    	tower.setbCanOperate(true);
    	slot1.setbCanOperate(true);
    	slot2.setbCanOperate(true);
    	slot3.setbCanOperate(true);
    	slot4.setbCanOperate(true);
    	slot5.setbCanOperate(true);

        if(isAI)
            this.makeMove();
        else {
//       enable interaction
        }
    }

    private void makeMove() {
//        make move by AI
        this.battleScreen.world.endTurn(this);
    }

	public RvBUnit getActingUnit() {
		return actingUnit;
	}

	public void setActingUnit(RvBUnit actingUnit) {
		this.actingUnit = actingUnit;
	}
	
	private void ClearCorpses() {
		if (slot1.isbDead())
		{
			slot1 = null;
		}
		if (slot2.isbDead())
		{
			slot2 = null;
		}
		if (slot3.isbDead())
		{
			slot3 = null;
		}
		if (slot4.isbDead())
		{
			slot4 = null;
		}
		if (slot5.isbDead())
		{
			slot5 = null;
		}
	}
}

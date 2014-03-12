package com.me.rvbgame;

import com.me.rvbgame.units.UnitDefender;



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

    public RvBPlayer(BattleScreen parentScreen) {
        super(parentScreen);
        
        tower = new RvBTower(battleScreen, this, "data/json_files/tower.json");
        
        units = new RvBUnit[5];
        slot1 = new UnitDefender(battleScreen, this, "data/json_files/defender.json");
        units[0] = slot1;
        slot2 = new UnitDefender(battleScreen, this, "data/json_files/defender.json");
        units[1] = slot2;
        slot3 = new UnitDefender(battleScreen, this, "data/json_files/defender.json");
        units[2] = slot3;
        slot4 = new UnitDefender(battleScreen, this, "data/json_files/defender.json");
        units[3] = slot4;
        slot5 = new UnitDefender(battleScreen, this, "data/json_files/defender.json");
        units[4] = slot5;
    }

    public void beginTurn()
    {
    	isMyTurn = true;
    	ClearCorpses();
    	if (tower != null)
    	{
    		tower.setbCanOperate(true);
    	}
    	if (slot1 != null)
    	{
    		slot1.setbCanOperate(true);
    	}
    	if (slot2 != null)
    	{
    		slot2.setbCanOperate(true);
    	}
    	if (slot3 != null)
    	{
    		slot3.setbCanOperate(true);
    	}
    	if (slot4 != null)
    	{
    		slot4.setbCanOperate(true);
    	}
    	if (slot5 != null)
    	{
    		slot5.setbCanOperate(true);
    	}
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
		if (slot1 != null)
		{
			if (slot1.isbDead())
			{
				slot1 = null;
			}
		}
		if (slot2 != null)
		{
			if (slot2.isbDead())
			{
				slot2 = null;
			}
		}
		if (slot3 != null)
		{
			if (slot3.isbDead())
			{
				slot3 = null;
			}
		}
		if (slot4 != null)
		{
			if (slot4.isbDead())
			{
				slot4 = null;
			}
		}
		if (slot5 != null)
		{
			if (slot5.isbDead())
			{
				slot5 = null;
			}
		}
	}
	
	@Override
	public void show() {
		super.show();
		
		if (tower != null){
			tower.show();
		}
		if (slot1 != null){
			slot1.show();
		}
		if (slot2 != null){
			slot2.show();
		}
		if (slot3 != null){
			slot3.show();
		}
		if (slot4 != null){
			slot4.show();
		}
		if (slot5 != null){
			slot5.show();
		}
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		
		if (tower != null){
			tower.resize(width, height);
		}
		if (slot1 != null){
			slot1.resize(width, height);
		}
		if (slot2 != null){
			slot2.resize(width, height);
		}
		if (slot3 != null){
			slot3.resize(width, height);
		}
		if (slot4 != null){
			slot4.resize(width, height);
		}
		if (slot5 != null){
			slot5.resize(width, height);
		}
	}
	
	@Override
	public void dispose() {
		super.dispose();
		
		if (tower != null){
			tower.dispose();
		}
		if (slot1 != null){
			slot1.dispose();
		}
		if (slot2 != null){
			slot2.dispose();
		}
		if (slot3 != null){
			slot3.dispose();
		}
		if (slot4 != null){
			slot4.dispose();
		}
		if (slot5 != null){
			slot5.dispose();
		}
	}
}

package com.me.rvbgame;

import com.badlogic.gdx.graphics.Color;
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
//        if (battleScreen != null && battleScreen.world != null) battleScreen.world.actionPointsLeftLabel.setText("200");
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

    public void endTurn()
    {
        isMyTurn = true;
        ClearCorpses();
        if (tower != null)
        {
            tower.setbCanOperate(false);
        }
        if (slot1 != null)
        {
            slot1.setbCanOperate(false);
        }
        if (slot2 != null)
        {
            slot2.setbCanOperate(false);
        }
        if (slot3 != null)
        {
            slot3.setbCanOperate(false);
        }
        if (slot4 != null)
        {
            slot4.setbCanOperate(false);
        }
        if (slot5 != null)
        {
            slot5.setbCanOperate(false);
        }
        if(isAI)
            this.makeMove();
        else {
//       enable interaction
        }

        setActingUnit(null);
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
		
		clearSelection();

		if (this.actingUnit != null)
		{
			this.actingUnit.settowerColor(new Color(0, 1, 0, 1));
            battleScreen.world.updateStatLabels(this.actingUnit);
		}
	}
	
	public void clearSelection(){
		if (tower != null)
		{
			tower.settowerColor(new Color(1, 1, 1, 1));
		}

		if (slot1 != null)
		{
			slot1.settowerColor(new Color(1, 1, 1, 1));
		}
		if (slot2 != null)
		{
			slot2.settowerColor(new Color(1, 1, 1, 1));
		}
		if (slot3 != null)
		{
			slot3.settowerColor(new Color(1, 1, 1, 1));
		}
		if (slot4 != null)
		{
			slot4.settowerColor(new Color(1, 1, 1, 1));
		}
		if (slot5 != null)
		{
			slot5.settowerColor(new Color(1, 1, 1, 1));
		}
	}
	
	private void ClearCorpses() {
		if (slot1 != null)
		{
			if (slot1.isbDead())
			{
				slot1.removeFromDraw();
				slot1 = null;
			}
		}
		if (slot2 != null)
		{
			if (slot2.isbDead())
			{
				slot2.removeFromDraw();
				slot2 = null;
			}
		}
		if (slot3 != null)
		{
			if (slot3.isbDead())
			{
				slot3.removeFromDraw();
				slot3 = null;
			}
		}
		if (slot4 != null)
		{
			if (slot4.isbDead())
			{
				slot4.removeFromDraw();
				slot4 = null;
			}
		}
		if (slot5 != null)
		{
			if (slot5.isbDead())
			{
				slot5.removeFromDraw();
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

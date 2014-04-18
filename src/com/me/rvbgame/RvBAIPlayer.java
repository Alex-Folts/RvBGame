package com.me.rvbgame;

import com.badlogic.gdx.Gdx;
import com.me.rvbgame.screens.BattleScreen;

public class RvBAIPlayer extends RvBPlayer {

	public RvBAIPlayer(BattleScreen parentScreen) {
		super(parentScreen);
        this.tower = new RvBTower(battleScreen, this, "data/json_files/tower_ai.json");
        this.tower.line = 3;
    }

	@Override
	protected void makeMove() {
		
		Gdx.app.log("BVGE", "RvBAIPlayer:makeMove()");
		
		manageUnits();
		
		unitsActions();
		
		// End of AI turn
		super.makeMove();
	}
	
	private boolean manageUnits() {
		Gdx.app.log("BVGE", "RvBAIPlayer:manageUnits()...");
		return false;
	}
	
	private boolean unitsActions() {
		int attackCount = 0;
		Gdx.app.log("BVGE", "RvBAIPlayer:unitsActions()...");
		for (int j = 1; j <= 5; j++)
		{
			if (getSlotUnitByIdx(j) != null)
			{
				Gdx.app.log("BVGE", "RvBAIPlayer:unitsActions() 1");
				if (!getSlotUnitByIdx(j).bFreeze && getSlotUnitByIdx(j).isbCanOperate())
				{
					Gdx.app.log("BVGE", "RvBAIPlayer:unitsActions() 2");
					setActingUnit(getSlotUnitByIdx(j));
					
					for(int i = 1; i <= 5; i++)
					{
						RvBUnit tmpVictim = RvBWorld.getOppositePlayer().getSlotUnitByIdx(i);
						if (tmpVictim != null)
						{
							Gdx.app.log("BVGE", "RvBAIPlayer:unitsActions() 3");
							if (!tmpVictim.isbDead())
							{
								Gdx.app.log("BVGE", "RvBAIPlayer:unitsActions() 4");
								if (!tmpVictim.isDefended())
								{
									Gdx.app.log("BVGE", "RvBAIPlayer:unitsActions() 5");
									getActingUnit().actionType = ActionType.ACTION_TYPE_ATTACK;
									RvBWorld.getOppositePlayer().fillAvailableVictims(getActingUnit().getAttackRange());
									if (tmpVictim.isbReachable())
									{
										Gdx.app.log("BVGE", "RvBAIPlayer:unitsActions() 5.5");
						                if(RvBWorld.applyActionOnVictim(getActingUnit(), tmpVictim));
										{
											Gdx.app.log("BVGE", "RvBAIPlayer:unitsActions() 6");
						                    if (RvBWorld.getCurrentTurnPlayer().getActingUnit().getActionPoints() == 0){
						                    	Gdx.app.log("BVGE", "RvBAIPlayer:unitsActions() 7");
						                        RvBWorld.getCurrentTurnPlayer().getActingUnit().setbCanOperate(false);
						                        RvBWorld.getCurrentTurnPlayer().getActingUnit().actionType = ActionType.ACTION_TYPE_DONE;
		//				                        if (!RvBWorld.getCurrentTurnPlayer().checkIfCanMove()){
		//				                            RvBWorld.getCurrentTurnPlayer().endTurn();
		//				                        }
						                    }
		//				                    battleScreen.sceneLayerRadialMenu.setDefaultColors();
		//				                    battleScreen.sceneLayerRadialMenu.hide();
						                    attackCount++;
						                    break;
										}
									}
								}
							}
						}
					}
					
					if (attackCount == 0)
					{
						Gdx.app.log("BVGE", "RvBAIPlayer:unitsActions() 8");
						for(int i = 1; i <= 5; i++)
						{
							RvBUnit tmpVictim = RvBWorld.getOppositePlayer().getSlotUnitByIdx(i);
							if (tmpVictim != null)
							{
								Gdx.app.log("BVGE", "RvBAIPlayer:unitsActions() 9");
								if (!tmpVictim.isbDead())
								{									
									Gdx.app.log("BVGE", "RvBAIPlayer:unitsActions() 10");
					                if(RvBWorld.applyActionOnVictim(getActingUnit(), tmpVictim));
									{
										Gdx.app.log("BVGE", "RvBAIPlayer:unitsActions() 11");
					                    if (RvBWorld.getCurrentTurnPlayer().getActingUnit().getActionPoints() == 0){
					                    	Gdx.app.log("BVGE", "RvBAIPlayer:unitsActions() 12");
					                        RvBWorld.getCurrentTurnPlayer().getActingUnit().setbCanOperate(false);
					                        RvBWorld.getCurrentTurnPlayer().getActingUnit().actionType = ActionType.ACTION_TYPE_DONE;
	//					                        if (!RvBWorld.getCurrentTurnPlayer().checkIfCanMove()){
	//					                            RvBWorld.getCurrentTurnPlayer().endTurn();
	//					                        }
					                    }
	//					                    battleScreen.sceneLayerRadialMenu.setDefaultColors();
	//					                    battleScreen.sceneLayerRadialMenu.hide();
					                    attackCount++;
									}
								}
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	@Override
	public boolean isAI() {
		return true;
	}
}

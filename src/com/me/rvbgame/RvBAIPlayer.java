package com.me.rvbgame;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.me.rvbgame.screens.BattleScreen;

public class RvBAIPlayer extends RvBPlayer {

	private ArrayList<RvBUnit> attackQueryList = new ArrayList<RvBUnit>();
	
	public RvBAIPlayer(BattleScreen parentScreen) {
		super(parentScreen);
		
		this.tower = new RvBTower(battleScreen, this, "data/json_files/tower_ai.json");
	    this.tower.line = 3;
	}

	@Override
	protected void makeMove() {
		
		Gdx.app.log("BVGE", "RvBAIPlayer:makeMove()");
		
		manageUnits();
		
		fillAttackQuery();
		
		unitsActions();
		
		// End of AI turn
//		super.makeMove();
	}
	
	private boolean manageUnits() {
		Gdx.app.log("BVGE", "RvBAIPlayer:manageUnits()...");
		return false;
	}
	
	private boolean fillAttackQuery() {
		Gdx.app.log("BVGE", "RvBAIPlayer:fillAttackQuery()...");
		
		RvBUnit tmpUnit;
		
		attackQueryList.clear();
		if (tower != null && !tower.isbDead() && !tower.bFreeze)
		{
			attackQueryList.add(tower);
		}
		for (int j = 1; j <= 5; j++)
		{
			tmpUnit = getSlotUnitByIdx(j);
			if (tmpUnit != null)
			{
//				Gdx.app.log("BVGE", "RvBAIPlayer:fillAttackQuery() 1");
				if (!tmpUnit.bFreeze && tmpUnit.isbCanOperate())
				{
					attackQueryList.add(tmpUnit);
				}
			}
		}
		
		return false;
	}
	
	private boolean unitsActions() {
		Gdx.app.log("BVGE", "RvBAIPlayer:unitsActions()...");
/*		
 		int attackCount = 0;
		if (tower != null && !tower.isbDead() && !tower.bFreeze)
		{
			boolean bAttackDone = false;
			int trysCount = 0;
			
			setActingUnit(tower);
			
			while (!bAttackDone)
			{
				RvBUnit tmpUnit = RvBWorld.getOppositePlayer().getSlotUnitByIdx(0);
				if (tmpUnit != null)
				{
					Gdx.app.log("BVGE", "RvBAIPlayer:unitsActions() tower 3");
					if (!tmpUnit.isbDead())
					{
						Gdx.app.log("BVGE", "RvBAIPlayer:unitsActions() tower 4");
						if (!tmpUnit.isDefended() || trysCount > 10)
						{
							Gdx.app.log("BVGE", "RvBAIPlayer:unitsActions() 5");
							getActingUnit().actionType = ActionType.ACTION_TYPE_ATTACK;
							RvBWorld.getOppositePlayer().fillAvailableVictims(getActingUnit().getAttackRange());
							if (tmpUnit.isbReachable())
							{
								Gdx.app.log("BVGE", "RvBAIPlayer:unitsActions() tower 5.5");
				                if(RvBWorld.applyActionOnVictim(getActingUnit(), tmpUnit));
								{
									Gdx.app.log("BVGE", "RvBAIPlayer:unitsActions() tower 6");
				                    if (RvBWorld.getCurrentTurnPlayer().getActingUnit().getActionPoints() == 0){
				                    	Gdx.app.log("BVGE", "RvBAIPlayer:unitsActions() tower 7");
				                        RvBWorld.getCurrentTurnPlayer().getActingUnit().setbCanOperate(false);
				                        RvBWorld.getCurrentTurnPlayer().getActingUnit().actionType = ActionType.ACTION_TYPE_DONE;
	//				                        if (!RvBWorld.getCurrentTurnPlayer().checkIfCanMove()){
	//				                            RvBWorld.getCurrentTurnPlayer().endTurn();
	//				                        }
				                    }
	//				                    battleScreen.sceneLayerRadialMenu.setDefaultColors();
	//				                    battleScreen.sceneLayerRadialMenu.hide();
				                    bAttackDone = true;
				                    break;
								}
							}
						}
					}
				}
				trysCount++;
				if (trysCount > 20)
				{
					bAttackDone = true;
				}
			}
		}
		
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
					                    break;
									}
								}
							}
						}
					}
				}
			}
			attackCount = 0;
		}*/
		
		DoAction();
		
		return false;
	}
	
	@Override
	public boolean isAI() {
		return true;
	}
	
	private void DoAction() {
		int attackCount = 0;
		boolean wasAction = false;
		if (attackQueryList != null && attackQueryList.size() > 0)
		{
			Gdx.app.log("BVGE", "RvBAIPlayer:DoAction() 1");
			attackQueryList.get(0);
			if (!attackQueryList.get(0).bFreeze && attackQueryList.get(0).isbCanOperate())
			{
//				if (((RvBTower)attackQueryList.get(0)) != null)
//				{
//					Gdx.app.log("BVGE", "RvBAIPlayer:DoAction() Tower");
//					return;
//				}
				Gdx.app.log("BVGE", "RvBAIPlayer:DoAction() 2");
				setActingUnit(attackQueryList.get(0));
				
				for(int i = 1; i <= 5; i++)
				{
					RvBUnit tmpVictim = RvBWorld.getOppositePlayer().getSlotUnitByIdx(i);
					if (tmpVictim != null)
					{
						Gdx.app.log("BVGE", "RvBAIPlayer:DoAction() 3");
						if (!tmpVictim.isbDead())
						{
							Gdx.app.log("BVGE", "RvBAIPlayer:DoAction() 4");
							if (!tmpVictim.isDefended())
							{
								Gdx.app.log("BVGE", "RvBAIPlayer:unitsActions() 5");
								getActingUnit().actionType = ActionType.ACTION_TYPE_ATTACK;
								RvBWorld.getOppositePlayer().fillAvailableVictims(getActingUnit().getAttackRange());
								if (tmpVictim.isbReachable())
								{
									Gdx.app.log("BVGE", "RvBAIPlayer:DoAction() 5.5");
					                if(RvBWorld.applyActionOnVictim(getActingUnit(), tmpVictim));
									{
										Gdx.app.log("BVGE", "RvBAIPlayer:DoAction() 6");
					                    if (RvBWorld.getCurrentTurnPlayer().getActingUnit().getActionPoints() == 0){
					                    	Gdx.app.log("BVGE", "RvBAIPlayer:DoAction() 7");
					                        RvBWorld.getCurrentTurnPlayer().getActingUnit().setbCanOperate(false);
					                        RvBWorld.getCurrentTurnPlayer().getActingUnit().actionType = ActionType.ACTION_TYPE_DONE;
	//				                        if (!RvBWorld.getCurrentTurnPlayer().checkIfCanMove()){
	//				                            RvBWorld.getCurrentTurnPlayer().endTurn();
	//				                        }
					                    }
	//				                    battleScreen.sceneLayerRadialMenu.setDefaultColors();
	//				                    battleScreen.sceneLayerRadialMenu.hide();
					                    attackCount++;
					                    attackQueryList.remove(0);
					                    wasAction = true;
					                    break;
									}
								}
							}
						}
					}
				}
				
				if (!wasAction)
				{
					if (RvBWorld.getOppositePlayer().tower != null)
					{
						Gdx.app.log("BVGE", "RvBAIPlayer:DoAction() 3");
						if (!RvBWorld.getOppositePlayer().tower.isbDead())
						{
							Gdx.app.log("BVGE", "RvBAIPlayer:DoAction() 4");
							getActingUnit().actionType = ActionType.ACTION_TYPE_ATTACK;
							RvBWorld.getOppositePlayer().fillAvailableVictims(getActingUnit().getAttackRange());
							if (RvBWorld.getOppositePlayer().tower.isbReachable())
							{
								Gdx.app.log("BVGE", "RvBAIPlayer:DoAction() 5.5");
				                if(RvBWorld.applyActionOnVictim(getActingUnit(), RvBWorld.getOppositePlayer().tower));
								{
									Gdx.app.log("BVGE", "RvBAIPlayer:DoAction() 6");
				                    if (RvBWorld.getCurrentTurnPlayer().getActingUnit().getActionPoints() == 0){
				                    	Gdx.app.log("BVGE", "RvBAIPlayer:DoAction() 7");
				                        RvBWorld.getCurrentTurnPlayer().getActingUnit().setbCanOperate(false);
				                        RvBWorld.getCurrentTurnPlayer().getActingUnit().actionType = ActionType.ACTION_TYPE_DONE;
//				                        if (!RvBWorld.getCurrentTurnPlayer().checkIfCanMove()){
//				                            RvBWorld.getCurrentTurnPlayer().endTurn();
//				                        }
				                    }
//				                    battleScreen.sceneLayerRadialMenu.setDefaultColors();
//				                    battleScreen.sceneLayerRadialMenu.hide();
				                    attackCount++;
				                    attackQueryList.remove(0);
				                    wasAction = true;
								}
							}
						}
					}
				}
/*				
				if (attackCount == 0)
				{
					Gdx.app.log("BVGE", "RvBAIPlayer:DoAction() 8");
					for(int i = 1; i <= 5; i++)
					{
						RvBUnit tmpVictim = RvBWorld.getOppositePlayer().getSlotUnitByIdx(i);
						if (tmpVictim != null)
						{
							Gdx.app.log("BVGE", "RvBAIPlayer:DoAction() 9");
							if (!tmpVictim.isbDead())
							{									
								Gdx.app.log("BVGE", "RvBAIPlayer:DoAction() 10");
				                if(RvBWorld.applyActionOnVictim(getActingUnit(), tmpVictim));
								{
									Gdx.app.log("BVGE", "RvBAIPlayer:DoAction() 11");
				                    if (RvBWorld.getCurrentTurnPlayer().getActingUnit().getActionPoints() == 0){
				                    	Gdx.app.log("BVGE", "RvBAIPlayer:DoAction() 12");
				                        RvBWorld.getCurrentTurnPlayer().getActingUnit().setbCanOperate(false);
				                        RvBWorld.getCurrentTurnPlayer().getActingUnit().actionType = ActionType.ACTION_TYPE_DONE;
//					                        if (!RvBWorld.getCurrentTurnPlayer().checkIfCanMove()){
//					                            RvBWorld.getCurrentTurnPlayer().endTurn();
//					                        }
				                    }
//					                    battleScreen.sceneLayerRadialMenu.setDefaultColors();
//					                    battleScreen.sceneLayerRadialMenu.hide();
				                    attackCount++;
				                    break;
								}
							}
						}
					}
				}*/
			}
		}
		if (!wasAction)
		{
			attackQueryList.remove(0);
			checkAction();
		}
	}
	
	private void checkAction() {
		if (attackQueryList != null && attackQueryList.size() > 0)
		{
			DoAction();
		} else
		{
			// End of AI turn
			super.makeMove();
		}
	}
	
	@Override
	public void actionAnimEnded() {
		super.actionAnimEnded();
		
		Gdx.app.log("BVGE", "RvBAIPlayer:actionAnimEnded()");
		
		checkAction();
/*		if (attackQueryList != null && attackQueryList.size() > 0)
		{
			DoAction();
		} else
		{
			// End of AI turn
			super.makeMove();
		}*/
	}
}

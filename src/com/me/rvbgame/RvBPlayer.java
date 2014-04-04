package com.me.rvbgame;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.me.rvbgame.screens.BattleScreen;
import com.me.rvbgame.units.UnitDefender;
import com.me.rvbgame.units.UnitMelee;
import com.me.rvbgame.units.UnitRanged;
import com.me.rvbgame.units.UnitRangedMass;
import com.me.rvbgame.units.UnitSpecialStan;


public class RvBPlayer extends RvBBase{

    public RvBTower tower;
//    public RvBUnit[] units;
    public ArrayList<RvBUnit> units;

    public RvBUnit slot1;
    public RvBUnit slot2;
    public RvBUnit slot3;
    public RvBUnit slot4;
    public RvBUnit slot5;
    public boolean isAI;
    public boolean isMyTurn;

    private RvBUnit actingUnit;

    private boolean isInventoryOpened;
    
    private Window inventoryWindow;
    private TextButton inventoryCloseButton;
    private ScrollPane inventoryItemsScrollPane;
    private Table inventoryItemsTable;
    
    private Texture emptySlotTex;
    
    private static final String emptySlotImgPath = "data/6_social_add_person.png";
    
    private Image slot01EmptyImage;
    private Image slot02EmptyImage;
    private Image slot03EmptyImage;
    private Image slot04EmptyImage;
    private Image slot05EmptyImage;
    
    private boolean bWaitForCard = false;
    private boolean bWaitForSlot = false;
    
    private RvBUnit waitForSlotUnit;

    
    public RvBPlayer(BattleScreen parentScreen) {
        super(parentScreen);
        
        tower = new RvBTower(battleScreen, this, "data/json_files/tower.json");
        tower.line = 3;
        
//        units = new RvBUnit[5];
        units = new ArrayList<RvBUnit>();
        units.add(new UnitRangedMass(battleScreen, this, "data/json_files/ranged_mass.json"));
        units.add(new UnitRanged(battleScreen, this, "data/json_files/ranged.json"));
        units.add(new UnitMelee(battleScreen, this, "data/json_files/melee.json"));
        
        slot1 = new UnitRanged(battleScreen, this, "data/json_files/ranged.json");
        slot1.line = 3;
//        units[0] = slot1;
//        units.add(slot1);
        slot2 = new UnitSpecialStan(battleScreen, this, "data/json_files/special_stan.json");
        slot2.line = 2;
//        units[1] = slot2;
//        units.add(slot2);
        slot3 = new UnitRangedMass(battleScreen, this, "data/json_files/ranged_mass.json");
        slot3.line = 3;
//        units[2] = slot3;
//        units.add(slot3);
        slot4 = new UnitDefender(battleScreen, this, "data/json_files/defender.json");
        slot4.line = 1;
//        units[3] = slot4;
//        units.add(slot4);
        slot5 = new UnitMelee(battleScreen, this, "data/json_files/melee.json");
        slot5.line = 1;
//        units[4] = slot5;
//        units.add(slot5);
        
        inventoryUpdated();
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
            if (slot1.isDefended()) {
                slot1.setDefended(false);
                slot1.setiDefence(slot1.getiDefence()/2);
                slot1.setpDefence(slot1.getpDefence()/2);
            }
    	} else
    	{
//    		Gdx.app.log("BVGE", "beginTurn: ");
    		slot01EmptyImage.setVisible(true);
    	}
    	if (slot2 != null)
    	{
    		slot2.setbCanOperate(true);
            if (slot2.isDefended()) {
                slot2.setDefended(false);
                slot2.setiDefence(slot2.getiDefence()/2);
                slot2.setpDefence(slot2.getpDefence()/2);
            }
    	} else
    	{
    		slot02EmptyImage.setVisible(true);
    	}
    	if (slot3 != null)
    	{
    		slot3.setbCanOperate(true);
            if (slot3.isDefended()) {
                slot3.setDefended(false);
                slot3.setiDefence(slot3.getiDefence()/2);
                slot3.setpDefence(slot3.getpDefence()/2);
            }

        } else
    	{
    		slot03EmptyImage.setVisible(true);
    	}
    	if (slot4 != null)
    	{
    		slot4.setbCanOperate(true);
            if (slot4.isDefended()) {
                slot4.setDefended(false);
                slot4.setiDefence(slot4.getiDefence()/2);
                slot4.setpDefence(slot4.getpDefence()/2);
            }

        } else
    	{
    		slot04EmptyImage.setVisible(true);
    	}
    	if (slot5 != null)
    	{
    		slot5.setbCanOperate(true);
            if (slot5.isDefended()) {
                slot5.setDefended(false);
                slot5.setiDefence(slot5.getiDefence()/2);
                slot5.setpDefence(slot5.getpDefence()/2);
            }

        } else
    	{
    		slot05EmptyImage.setVisible(true);
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
//            tower.actionType = ActionType.ACTION_TYPE_DONE;
        }
        if (slot1 != null)
        {
            slot1.setbCanOperate(false);
            if (slot1.bFreeze) slot1.unFreeze();
            if  (slot1.getActionPoints() <= 0) slot1.setActionPoints(slot1.getMinActionPoints());
        }
        if (slot2 != null)
        {
            slot2.setbCanOperate(false);
            if (slot2.bFreeze) slot2.unFreeze();
            if (slot2.getActionPoints() <= 0) slot2.setActionPoints(slot2.getMinActionPoints());
        }
        if (slot3 != null)
        {
            slot3.setbCanOperate(false);
            if (slot3.bFreeze) slot3.unFreeze();
            if (slot3.getActionPoints() <= 0) slot3.setActionPoints(slot3.getMinActionPoints());
        }
        if (slot4 != null)
        {
            slot4.setbCanOperate(false);
            if (slot4.bFreeze) slot4.unFreeze();
            if (slot4.getActionPoints() <= 0) slot4.setActionPoints(slot4.getMinActionPoints());
        }
        if (slot5 != null)
        {
            slot5.setbCanOperate(false);
            if (slot5.bFreeze) slot5.unFreeze();
            if (slot5.getActionPoints() <= 0) slot5.setActionPoints(slot5.getMinActionPoints());
        }
        if(isAI)
            this.makeMove();
        else {
//       enable interaction
        }

        clearWaitingUnit();
        
        setActingUnit(null);

        if (this.battleScreen.sceneLayerRadialMenu!=null)
            this.battleScreen.sceneLayerRadialMenu.setDefaultColors();
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
			this.actingUnit.settowerColor(StatsHelper.COLOR_DARK_GREEN);
            battleScreen.world.updateStatLabels(this.actingUnit);
            battleScreen.world.revealRadialMenu(this.actingUnit);
		}
	}
	
	public void clearSelection(){
		if (tower != null)
		{
			tower.settowerColor(Color.DARK_GRAY);//(new Color(1, 1, 1, 1));
		}

		if (slot1 != null)
		{
			slot1.setbReachable(false);////settowerColor(Color.DARK_GRAY);//(new Color(1, 1, 1, 1));
		}
		if (slot2 != null)
		{
			slot2.setbReachable(false);//settowerColor(Color.DARK_GRAY);//(new Color(1, 1, 1, 1));
		}
		if (slot3 != null)
		{
			slot3.setbReachable(false);//settowerColor(Color.DARK_GRAY);//(new Color(1, 1, 1, 1));
		}
		if (slot4 != null)
		{
			slot4.setbReachable(false);//settowerColor(Color.DARK_GRAY);//(new Color(1, 1, 1, 1));
		}
		if (slot5 != null)
		{
			slot5.setbReachable(false);//settowerColor(Color.DARK_GRAY);//(new Color(1, 1, 1, 1));
		}
	}
	
	private void ClearCorpses() {
		boolean bRes = false;
		
		if (slot1 != null)
		{
			if (slot1.isbDead())
			{
				slot1.removeFromDraw();
//				units.remove(slot1);
				slot1 = null;
				bRes = true;
			}
		}
		if (slot2 != null)
		{
			if (slot2.isbDead())
			{
				slot2.removeFromDraw();
//				units.remove(slot2);
				slot2 = null;
				bRes = true;
			}
		}
		if (slot3 != null)
		{
			if (slot3.isbDead())
			{
				slot3.removeFromDraw();
//				units.remove(slot3);
				slot3 = null;
				bRes = true;
			}
		}
		if (slot4 != null)
		{
			if (slot4.isbDead())
			{
				slot4.removeFromDraw();
//				units.remove(slot4);
				slot4 = null;
				bRes = true;
			}
		}
		if (slot5 != null)
		{
			if (slot5.isbDead())
			{
				slot5.removeFromDraw();
//				units.remove(slot5);
				slot5 = null;
				bRes = true;
			}
		}
		
		if (bRes)
		{
			inventoryUpdated();
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
		
		emptySlotTex = new Texture(Gdx.files.internal(emptySlotImgPath));
		emptySlotTex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
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
		
		inventoryWindow = new Window("Inventory", battleScreen.getSkin());
		inventoryWindow.setVisible(false);
		inventoryWindow.setMovable(false);
		inventoryWindow.setSize(width, height * 0.75f);
		inventoryWindow.setPosition(0, height * 0.5f - inventoryWindow.getHeight() * 0.5f);
		
		inventoryCloseButton = new TextButton("Close", battleScreen.getSkin());
		inventoryCloseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	inventoryVisible(false);
            };
        });
		
		inventoryItemsTable = new Table(battleScreen.getSkin());
		
		inventoryItemsScrollPane = new ScrollPane(inventoryItemsTable, battleScreen.getSkin());
		inventoryItemsScrollPane.setScrollingDisabled(false, true);
		inventoryItemsScrollPane.setFadeScrollBars(true);
		
		inventoryWindow.add(inventoryItemsScrollPane);
		inventoryWindow.row();
		inventoryWindow.add(inventoryCloseButton);
		
		battleScreen.sceneLayerMenu.addActor(inventoryWindow);
		
		TextureRegion region = new TextureRegion(emptySlotTex, 0, 0, 64, 64);
		
		float deltaWidthCoef = (battleScreen.screenResW / RvBWorld.WORLD_NATIVE_RES.x);
		float deltaHeightCoef = (battleScreen.screenResH / RvBWorld.WORLD_NATIVE_RES.y);
		float deltaAvaSizeW = 48 * deltaHeightCoef;
		
		//slot 1
		slot01EmptyImage = new Image(region);
		slot01EmptyImage.setScaling(Scaling.stretch);
		slot01EmptyImage.setAlign((Align.bottom | Align.left));
		slot01EmptyImage.setSize(48, 48);
		slot01EmptyImage.setColor(StatsHelper.COLOR_EMPTY_UNIT_SLOT);
		slot01EmptyImage.setVisible(false);
		
		slot01EmptyImage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if (battleScreen.world.getCurrentTurnPlayer() == RvBPlayer.this)
            	{
            		if (bWaitForSlot)
            		{
            			if (waitForSlotUnit != null && slot1 == null)
            			{
            				slot1 = waitForSlotUnit;
            				slot1.show();
            				slot1.resize((int)battleScreen.screenResW, (int)battleScreen.screenResH);
            				slot01EmptyImage.setVisible(false);
            				units.remove(waitForSlotUnit);
            				inventoryUpdated();
            				clearWaitingUnit();
            			}
            		}
            	}
            };
        });
		
		if (this == battleScreen.world.playerRight)
		{
//			slot01EmptyImage.setPosition(RvBWorld.RIGHT_UNIT_SLOT01.x - (slot01EmptyImage.getWidth() * 0.5f), RvBWorld.RIGHT_UNIT_SLOT01.y - (slot01EmptyImage.getHeight() * 0.5f));
			slot01EmptyImage.setPosition((RvBWorld.RIGHT_UNIT_SLOT01.x * deltaWidthCoef * (1.0f + ((deltaAvaSizeW - 48) / battleScreen.screenResW))), RvBWorld.RIGHT_UNIT_SLOT01.y * deltaHeightCoef);
		} else
		{
//			slot01EmptyImage.setPosition(RvBWorld.LEFT_UNIT_SLOT01.x - (slot01EmptyImage.getWidth() * 0.5f), RvBWorld.LEFT_UNIT_SLOT01.y - (slot01EmptyImage.getHeight() * 0.5f));
			slot01EmptyImage.setPosition((RvBWorld.LEFT_UNIT_SLOT01.x * deltaWidthCoef * (1.0f + ((deltaAvaSizeW - 48) / battleScreen.screenResW))), RvBWorld.LEFT_UNIT_SLOT01.y * deltaHeightCoef);
		}
		
		battleScreen.sceneLayerUnits.addActor(slot01EmptyImage);
		
		//slot 2
		slot02EmptyImage = new Image(region);
		slot02EmptyImage.setScaling(Scaling.stretch);
		slot02EmptyImage.setAlign((Align.bottom | Align.left));
		slot02EmptyImage.setSize(48, 48);
		slot02EmptyImage.setColor(StatsHelper.COLOR_EMPTY_UNIT_SLOT);
		slot02EmptyImage.setVisible(false);
		
		slot02EmptyImage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if (battleScreen.world.getCurrentTurnPlayer() == RvBPlayer.this)
            	{
            		if (bWaitForSlot)
            		{
            			if (waitForSlotUnit != null && slot2 == null)
            			{
            				slot2 = waitForSlotUnit;
            				slot2.show();
            				slot2.resize((int)battleScreen.screenResW, (int)battleScreen.screenResH);
            				slot02EmptyImage.setVisible(false);
            				units.remove(waitForSlotUnit);
            				inventoryUpdated();
            				clearWaitingUnit();
            			}
            		}
            	}
            };
        });
		
		if (this == battleScreen.world.playerRight)
		{
			slot02EmptyImage.setPosition((RvBWorld.RIGHT_UNIT_SLOT02.x * deltaWidthCoef * (1.0f + ((deltaAvaSizeW - 48) / battleScreen.screenResW))), RvBWorld.RIGHT_UNIT_SLOT02.y * deltaHeightCoef);
		} else
		{
			slot02EmptyImage.setPosition((RvBWorld.LEFT_UNIT_SLOT02.x * deltaWidthCoef * (1.0f + ((deltaAvaSizeW - 48) / battleScreen.screenResW))), RvBWorld.LEFT_UNIT_SLOT02.y * deltaHeightCoef);
		}
		battleScreen.sceneLayerUnits.addActor(slot02EmptyImage);
		
		//slot 3
		slot03EmptyImage = new Image(region);
		slot03EmptyImage.setScaling(Scaling.stretch);
		slot03EmptyImage.setAlign((Align.bottom | Align.left));
		slot03EmptyImage.setSize(48, 48);
		slot03EmptyImage.setColor(StatsHelper.COLOR_EMPTY_UNIT_SLOT);
		slot03EmptyImage.setVisible(false);
		
		slot03EmptyImage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if (battleScreen.world.getCurrentTurnPlayer() == RvBPlayer.this)
            	{
            		if (bWaitForSlot)
            		{
            			if (waitForSlotUnit != null && slot3 == null)
            			{
            				slot3 = waitForSlotUnit;
            				slot3.show();
            				slot3.resize((int)battleScreen.screenResW, (int)battleScreen.screenResH);
            				slot03EmptyImage.setVisible(false);
            				units.remove(waitForSlotUnit);
            				inventoryUpdated();
            				clearWaitingUnit();
            			}
            		}
            	}
            };
        });
		
		if (this == battleScreen.world.playerRight)
		{
			slot03EmptyImage.setPosition((RvBWorld.RIGHT_UNIT_SLOT03.x * deltaWidthCoef * (1.0f + ((deltaAvaSizeW - 48) / battleScreen.screenResW))), RvBWorld.RIGHT_UNIT_SLOT03.y * deltaHeightCoef);
		} else
		{
			slot03EmptyImage.setPosition((RvBWorld.LEFT_UNIT_SLOT03.x * deltaWidthCoef * (1.0f + ((deltaAvaSizeW - 48) / battleScreen.screenResW))), RvBWorld.LEFT_UNIT_SLOT03.y * deltaHeightCoef);
		}
		battleScreen.sceneLayerUnits.addActor(slot03EmptyImage);
	
		//slot 4
		slot04EmptyImage = new Image(region);
		slot04EmptyImage.setScaling(Scaling.stretch);
		slot04EmptyImage.setAlign((Align.bottom | Align.left));
		slot04EmptyImage.setSize(48, 48);
		slot04EmptyImage.setColor(StatsHelper.COLOR_EMPTY_UNIT_SLOT);
		slot04EmptyImage.setVisible(false);
		
		slot04EmptyImage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if (battleScreen.world.getCurrentTurnPlayer() == RvBPlayer.this)
            	{
            		if (bWaitForSlot)
            		{
            			if (waitForSlotUnit != null && slot4 == null)
            			{
            				slot4 = waitForSlotUnit;
            				slot4.show();
            				slot4.resize((int)battleScreen.screenResW, (int)battleScreen.screenResH);
            				slot04EmptyImage.setVisible(false);
            				units.remove(waitForSlotUnit);
            				inventoryUpdated();
            				clearWaitingUnit();
            			}
            		}
            	}
            };
        });
		
		if (this == battleScreen.world.playerRight)
		{
			slot04EmptyImage.setPosition((RvBWorld.RIGHT_UNIT_SLOT04.x * deltaWidthCoef * (1.0f + ((deltaAvaSizeW - 48) / battleScreen.screenResW))), RvBWorld.RIGHT_UNIT_SLOT04.y * deltaHeightCoef);
		} else
		{
			slot04EmptyImage.setPosition((RvBWorld.LEFT_UNIT_SLOT04.x * deltaWidthCoef * (1.0f + ((deltaAvaSizeW - 48) / battleScreen.screenResW))), RvBWorld.LEFT_UNIT_SLOT04.y * deltaHeightCoef);
		}
		battleScreen.sceneLayerUnits.addActor(slot04EmptyImage);
		
		//slot 5
		slot05EmptyImage = new Image(region);
		slot05EmptyImage.setScaling(Scaling.stretch);
		slot05EmptyImage.setAlign((Align.bottom | Align.left));
		slot05EmptyImage.setSize(48, 48);
		slot05EmptyImage.setColor(StatsHelper.COLOR_EMPTY_UNIT_SLOT);
		slot05EmptyImage.setVisible(false);
		
		slot05EmptyImage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if (battleScreen.world.getCurrentTurnPlayer() == RvBPlayer.this)
            	{
            		if (bWaitForSlot)
            		{
            			if (waitForSlotUnit != null && slot5 == null)
            			{
            				slot5 = waitForSlotUnit;
            				slot5.show();
            				slot5.resize((int)battleScreen.screenResW, (int)battleScreen.screenResH);
            				slot05EmptyImage.setVisible(false);
            				units.remove(waitForSlotUnit);
            				inventoryUpdated();
            				clearWaitingUnit();
            			}
            		}
            	}
            };
        });
		
		if (this == battleScreen.world.playerRight)
		{
			slot05EmptyImage.setPosition((RvBWorld.RIGHT_UNIT_SLOT05.x * deltaWidthCoef * (1.0f + ((deltaAvaSizeW - 48) / battleScreen.screenResW))), RvBWorld.RIGHT_UNIT_SLOT05.y * deltaHeightCoef);
		} else
		{
			slot05EmptyImage.setPosition((RvBWorld.LEFT_UNIT_SLOT05.x * deltaWidthCoef * (1.0f + ((deltaAvaSizeW - 48) / battleScreen.screenResW))), RvBWorld.LEFT_UNIT_SLOT05.y * deltaHeightCoef);
		}
		battleScreen.sceneLayerUnits.addActor(slot05EmptyImage);
		
		inventoryUpdated();
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

    public void fillAvailableVictims(RvBUnit unit, byte attackRange) {
        clearSelection();
        switch (attackRange){
            case 1:
                if (slot4!=null) slot4.settowerColor(StatsHelper.COLOR_DARK_GREEN);
                if (slot5!=null) slot5.settowerColor(StatsHelper.COLOR_DARK_GREEN);
                if (slot4!=null || slot5!=null) unit.setAttackRange((byte) 1);
                if (slot4 == null && slot5 == null && slot2 != null){
                    slot2.settowerColor(StatsHelper.COLOR_DARK_GREEN);
                    unit.setAttackRange((byte) 2);
                }
                else if (slot4 == null && slot5 == null && slot2 == null) {
                    if (slot1!=null) slot1.settowerColor(StatsHelper.COLOR_DARK_GREEN);
                    if (slot3!=null) slot3.settowerColor(StatsHelper.COLOR_DARK_GREEN);
                    unit.setAttackRange((byte) 3);
                }
                break;
            case 2:
                if (slot2!=null) slot2.settowerColor(StatsHelper.COLOR_DARK_GREEN);
                if (slot4!=null) slot4.settowerColor(StatsHelper.COLOR_DARK_GREEN);
                if (slot5!=null) slot5.settowerColor(StatsHelper.COLOR_DARK_GREEN);

                if (slot4 == null && slot5 == null && unit.unitType!=UnitType.UNIT_TYPE_DEFENDER && unit.unitType != UnitType.UNIT_TYPE_MELEE) {
                    if (slot1!=null) slot1.settowerColor(StatsHelper.COLOR_DARK_GREEN);
                    if (slot3!=null) slot3.settowerColor(StatsHelper.COLOR_DARK_GREEN);
                    unit.setAttackRange((byte) 3);
                }else
                if (slot4 == null && slot5 == null && slot2 == null) {
                    if (slot1!=null) slot1.settowerColor(StatsHelper.COLOR_DARK_GREEN);
                    if (slot3!=null) slot3.settowerColor(StatsHelper.COLOR_DARK_GREEN);
                    unit.setAttackRange((byte) 3);
                }else
                    unit.setAttackRange((byte) 2);
                break;
            case 3:
                if (slot1!=null) slot1.settowerColor(StatsHelper.COLOR_DARK_GREEN);
                if (slot2!=null) slot2.settowerColor(StatsHelper.COLOR_DARK_GREEN);
                if (slot3!=null) slot3.settowerColor(StatsHelper.COLOR_DARK_GREEN);
                if (slot4!=null) slot4.settowerColor(StatsHelper.COLOR_DARK_GREEN);
                if (slot5!=null) slot5.settowerColor(StatsHelper.COLOR_DARK_GREEN);
                break;
            default:
                break;
        }
    }

    public void fillAvailableVictims(int attackRange){
        clearSelection();
        int range = attackRange;
        if (slot2!=null)
            range += 1-numOfLinesInFront(slot2);
        else if (slot1!=null)
            range += 2-numOfLinesInFront(slot1);
        else if (slot3!=null)
            range += 2-numOfLinesInFront(slot3);
        Gdx.app.log("PLA","range " + range);
        switch (range){
            case 1:
                if (slot4!=null) slot4.setbReachable(true);//slot4.settowerColor(StatsHelper.COLOR_DARK_GREEN);
                if (slot5!=null) slot5.setbReachable(true);//settowerColor(StatsHelper.COLOR_DARK_GREEN);
                break;
            case 2:
                if (slot2!=null) slot2.setbReachable(true);//settowerColor(StatsHelper.COLOR_DARK_GREEN);
                if (slot4!=null) slot4.setbReachable(true);//settowerColor(StatsHelper.COLOR_DARK_GREEN);
                if (slot5!=null) slot5.setbReachable(true);//settowerColor(StatsHelper.COLOR_DARK_GREEN);
                break;
            default:
                if (slot1!=null) slot1.setbReachable(true);//settowerColor(StatsHelper.COLOR_DARK_GREEN);
                if (slot2!=null) slot2.setbReachable(true);//settowerColor(StatsHelper.COLOR_DARK_GREEN);
                if (slot3!=null) slot3.setbReachable(true);//settowerColor(StatsHelper.COLOR_DARK_GREEN);
                if (slot4!=null) slot4.setbReachable(true);//settowerColor(StatsHelper.COLOR_DARK_GREEN);
                if (slot5!=null) slot5.setbReachable(true);//settowerColor(StatsHelper.COLOR_DARK_GREEN);
                break;
        }
    }

	public void toggleInventory() {
		inventoryVisible(!isInventoryOpened);
	}
	
	public void inventoryVisible(boolean bVisible) {
		isInventoryOpened = bVisible;
		inventoryWindow.setVisible(isInventoryOpened);		
	}
	
	private void inventoryUpdated() {
		if (inventoryItemsTable != null)
		{
			inventoryItemsTable.clear();
			if (units != null && units.size() > 0)
			{
				for (int i = 0; i < units.size(); i++)
				{
					Texture tmpTexture = new Texture(Gdx.files.internal(units.get(i).getImagePath()));
					tmpTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
					
					TextureRegion region = new TextureRegion(tmpTexture, 0, 0, units.get(i).getAvaImageWidth(), units.get(i).getAvaImageHeight());
					
					Image tmpImage = new Image(region);
					tmpImage.setScaling(Scaling.stretch);
					tmpImage.setAlign((Align.bottom | Align.left));
					tmpImage.setSize(units.get(i).getAvaSize().x, units.get(i).getAvaSize().y);
//					tmpImage.setColor(Color.DARK_GRAY);
					tmpImage.setName(""+i);
					tmpImage.addListener( new ClickListener() {             
						@Override
						public void clicked(InputEvent event, float x, float y) {
							Gdx.app.log("BVGE", "clicked:");
							if (bWaitForCard)
							{
								
							} else
							{
								waitForSlotUnit = units.get(Integer.decode(event.getListenerActor().toString()));								
								bWaitForSlot = true;
								inventoryVisible(false);
//								Gdx.app.log("BVGE", "inventoryUpdated: "+event.getListenerActor().toString());
							}
						};
					});
					
					InventoryItem tmpInvItem = new InventoryItem();
					tmpInvItem.setUnit(units.get(i));
					tmpInvItem.setAvaImage(tmpImage);
					
					
					
//					inventoryItemsTable.add(tmpImage).width(battleScreen.screenResH * 0.35f).height(battleScreen.screenResH * 0.35f).padLeft(5).padRight(5).padBottom(20).padTop(10);
					inventoryItemsTable.add(tmpInvItem).width(battleScreen.screenResH * 0.35f).height(battleScreen.screenResH * 0.55f).padLeft(5).padRight(5).padBottom(20).padTop(10);
					
					tmpInvItem.init(battleScreen.screenResH * 0.35f, battleScreen.screenResH * 0.55f);
				}
			} else
			{
				
			}
		}
	}
	
	private void clearWaitingUnit() {
        bWaitForSlot = false;
        bWaitForCard = false;
        waitForSlotUnit = null;
	}

    public boolean checkIfUnitsDead() {
      if (slot1 == null && slot2 == null && slot3 == null && slot4 == null && slot5 == null)
            return true;
      else
            return false;
    }
    
    class InventoryItem extends Group
    {
    	private RvBUnit unit;
    	private Image avaImage;
    	private TextButton pickButton;
    	private Table bgTable;
    	private Table fgTable;
    	
    	private Label statsHpLabel;
    	private Label statsEpLabel;
    	
    	private Label statsPDmgLabel;
    	private Label statsPDefLabel;
    	
    	private Label statsIDmgLabel;
    	private Label statsIDefLabel;
    	
    	private Label statsTargetsLabel;
    	private Label statsRangeLabel;
    	
    	private Image statsHpImage;
    	private Image statsEpImage;
    	
    	private Image statsPDmgImage;
    	private Image statsPDefImage;
    	
    	private Image statsIDmgImage;
    	private Image statsIDefImage;
    	
    	private Image statsTargetsImage;
    	private Image statsRangeImage;
    	
    	InventoryItem(){
    		super();
    	}

    	void init(float width, float height)
    	{
    		float statsIcoSize = 24;
    		
    		setWidth(width);
    		setHeight(height);
    		
//    		Gdx.app.log("BVGE", "init: "+getWidth()+" x "+getHeight());
    		
    		bgTable = new Table(battleScreen.getSkin());
    		bgTable.setWidth(width);
    		bgTable.setHeight(height);

    		fgTable = new Table(battleScreen.getSkin());
    		fgTable.setWidth(width);
    		fgTable.setHeight(height);
    		
    		this.addActor(bgTable);
    		this.addActor(fgTable);
    		
    		setPickButton(new TextButton("pick", battleScreen.getSkin()));
    		setStatsHpLabel(new Label("HP: ", battleScreen.getSkin()));
    		setStatsEpLabel(new Label("EP: ", battleScreen.getSkin()));
    		setStatsPDmgLabel(new Label("PDmg: ", battleScreen.getSkin()));
    		setStatsPDefLabel(new Label("PDef: ", battleScreen.getSkin()));
    		setStatsIDmgLabel(new Label("IDmg: ", battleScreen.getSkin()));
    		setStatsIDefLabel(new Label("IDef: ", battleScreen.getSkin()));
    		setStatsTargetsLabel(new Label("Targets: ", battleScreen.getSkin()));
    		setStatsRangeLabel(new Label("Range: ", battleScreen.getSkin()));
    		
    		setStatsHpImage(makeStatsIco(0, "data/heart_ico.png", StatsHelper.COLOR_DARK_RED));
    		setStatsEpImage(makeStatsIco(0, "data/ap_icon.png", StatsHelper.COLOR_DARK_BLUE));
    		
    		setStatsPDmgImage(makeStatsIco(0, "data/sword_icon.png", StatsHelper.COLOR_DARK_RED));
    		setStatsPDefImage(makeStatsIco(0, "data/shield_icon.png", StatsHelper.COLOR_DARK_RED));
    		
    		setStatsIDmgImage(makeStatsIco(0, "data/sword_icon.png", StatsHelper.COLOR_DARK_BLUE));
    		setStatsIDefImage(makeStatsIco(0, "data/shield_icon.png", StatsHelper.COLOR_DARK_BLUE));
    		
    		setStatsTargetsImage(makeStatsIco(0, "data/range_cross.png", StatsHelper.COLOR_DARK_BLUE));
    		setStatsRangeImage(makeStatsIco(0, "data/range_cross.png", StatsHelper.COLOR_DARK_BLUE));
    		
    		fgTable.left();
    		fgTable.bottom();
    		fgTable.add(statsHpImage).width(statsIcoSize).height(statsIcoSize);
    		fgTable.add(statsHpLabel);
    		fgTable.row();
    		fgTable.add(statsEpImage).width(statsIcoSize).height(statsIcoSize);
    		fgTable.add(statsEpLabel);
    		fgTable.row();
    		fgTable.add(statsPDmgImage).width(statsIcoSize).height(statsIcoSize);
    		fgTable.add(statsPDmgLabel);
    		fgTable.row();
    		fgTable.add(statsPDefImage).width(statsIcoSize).height(statsIcoSize);
    		fgTable.add(statsPDefLabel);
    		fgTable.row();
    		fgTable.add(statsIDmgImage).width(statsIcoSize).height(statsIcoSize);
    		fgTable.add(statsIDmgLabel);
    		fgTable.row();
    		fgTable.add(statsIDefImage).width(statsIcoSize).height(statsIcoSize);
    		fgTable.add(statsIDefLabel);
    		fgTable.row();
    		fgTable.add(statsTargetsImage).width(statsIcoSize).height(statsIcoSize);
    		fgTable.add(statsTargetsLabel);
    		fgTable.row();
    		fgTable.add(statsRangeImage).width(statsIcoSize).height(statsIcoSize);
    		fgTable.add(statsRangeLabel);
//    		fgTable.row();
    		fgTable.add(pickButton);
    		
    		bgTable.add(this.avaImage);
    		
    		avaImage.setColor(0.222f, 0.555f, 0.555f, 1.0f);
    		
    		fillLabels();
    	}
    	
    	public void fillLabels()
    	{
    		statsHpLabel.setText(""+unit.getHealth());
    		statsEpLabel.setText(""+unit.getEnergy());
    		statsPDmgLabel.setText(""+unit.getpAttack());
    		statsPDefLabel.setText(""+unit.getpDefence());
    		statsIDmgLabel.setText(""+unit.getiAttack());
    		statsIDefLabel.setText(""+unit.getiDefence());
    		statsTargetsLabel.setText(""+unit.getTargetsNum());
    		statsRangeLabel.setText(""+unit.getAttackRange());
    	}
    	
		public RvBUnit getUnit() {
			return unit;
		}

		public void setUnit(RvBUnit unit) {
			this.unit = unit;
		}

		public Image getAvaImage() {
			return avaImage;
		}

		public void setAvaImage(Image avaImage) {
			this.avaImage = avaImage;
		}

		public TextButton getPickButton() {
			return pickButton;
		}

		public void setPickButton(TextButton pickButton) {
			this.pickButton = pickButton;
		}

		public Label getStatsHpLabel() {
			return statsHpLabel;
		}

		public void setStatsHpLabel(Label statsHpLabel) {
			this.statsHpLabel = statsHpLabel;
		}

		public Label getStatsEpLabel() {
			return statsEpLabel;
		}

		public void setStatsEpLabel(Label statsEpLabel) {
			this.statsEpLabel = statsEpLabel;
		}

		public Label getStatsPDmgLabel() {
			return statsPDmgLabel;
		}

		public void setStatsPDmgLabel(Label statsPDmgLabel) {
			this.statsPDmgLabel = statsPDmgLabel;
		}

		public Label getStatsPDefLabel() {
			return statsPDefLabel;
		}

		public void setStatsPDefLabel(Label statsPDefLabel) {
			this.statsPDefLabel = statsPDefLabel;
		}

		public Label getStatsIDmgLabel() {
			return statsIDmgLabel;
		}

		public void setStatsIDmgLabel(Label statsIDmgLabel) {
			this.statsIDmgLabel = statsIDmgLabel;
		}

		public Label getStatsIDefLabel() {
			return statsIDefLabel;
		}

		public void setStatsIDefLabel(Label statsIDefLabel) {
			this.statsIDefLabel = statsIDefLabel;
		}

		public Label getStatsTargetsLabel() {
			return statsTargetsLabel;
		}

		public void setStatsTargetsLabel(Label statsTargetsLabel) {
			this.statsTargetsLabel = statsTargetsLabel;
		}

		public Label getStatsRangeLabel() {
			return statsRangeLabel;
		}

		public void setStatsRangeLabel(Label statsRangeLabel) {
			this.statsRangeLabel = statsRangeLabel;
		}
		
		public Image makeStatsIco(float size, String imgPath, Color color)
		{
			if (imgPath != "")
			{
/*				if (size == 0.0f)
				{
					size = 32.0f;
				}*/
				
				Texture texture = new Texture(Gdx.files.internal(imgPath));
		        texture.setFilter(TextureFilter.Linear,TextureFilter.Linear);
		        TextureRegion region = new TextureRegion(texture, 0, 0, 512, 512);
		        Image resImage = new Image(region);
		        resImage.setScaling(Scaling.stretch);
		        resImage.setAlign((Align.bottom | Align.left));
		        resImage.setSize(size, size);
		        if (color == null)
		        {
		        	color = Color.GRAY;
		        }
		        resImage.setColor(color);
		        
		        return resImage;
			}
			
			return null;
		}

		public Image getStatsHpImage() {
			return statsHpImage;
		}

		public void setStatsHpImage(Image statsHpImage) {
			this.statsHpImage = statsHpImage;
		}

		public Image getStatsEpImage() {
			return statsEpImage;
		}

		public void setStatsEpImage(Image statsEpImage) {
			this.statsEpImage = statsEpImage;
		}

		public Image getStatsPDmgImage() {
			return statsPDmgImage;
		}

		public void setStatsPDmgImage(Image statsPDmgImage) {
			this.statsPDmgImage = statsPDmgImage;
		}

		public Image getStatsPDefImage() {
			return statsPDefImage;
		}

		public void setStatsPDefImage(Image statsPDefImage) {
			this.statsPDefImage = statsPDefImage;
		}

		public Image getStatsIDmgImage() {
			return statsIDmgImage;
		}

		public void setStatsIDmgImage(Image statsIDmgImage) {
			this.statsIDmgImage = statsIDmgImage;
		}

		public Image getStatsIDefImage() {
			return statsIDefImage;
		}

		public void setStatsIDefImage(Image statsIDefImage) {
			this.statsIDefImage = statsIDefImage;
		}

		public Image getStatsTargetsImage() {
			return statsTargetsImage;
		}

		public void setStatsTargetsImage(Image statsTargetsImage) {
			this.statsTargetsImage = statsTargetsImage;
		}

		public Image getStatsRangeImage() {
			return statsRangeImage;
		}

		public void setStatsRangeImage(Image statsRangeImage) {
			this.statsRangeImage = statsRangeImage;
		}
    }
    
    public boolean checkIfCanMove() {
        return  (slot1.isbCanOperate()
                || slot2.isbCanOperate()
                || slot3.isbCanOperate()
                || slot4.isbCanOperate()
                || slot5.isbCanOperate());
    }

    public int numOfLinesInFront(RvBUnit unit){
        Gdx.app.log("BVGE","numOfLinesInFront returns:");
        switch (unit.line){
            case 1:
                Gdx.app.log("BVGE","case 1: "+0);
                return 0;
            case 2:
                if (slot4 != null || slot5 != null){ //only first line ahead
                    Gdx.app.log("BVGE","case 2: "+1);
                    return 1;
                }
                if (slot4 == null && slot5 == null){
                    Gdx.app.log("BVGE","case 2: "+0);
                    return 0;
                }
            case 3:
                if (slot2 != null && (slot4 != null || slot5 != null))  //first and second line ahead
                {
                    Gdx.app.log("BVGE","case 3: "+2);
                    return 2;
                }
                else if (slot2 != null && (slot4 == null && slot5 == null)) //only second line ahead
                {
                    Gdx.app.log("BVGE","case 3: "+1);
                    return 1;
                }
            default:
                break;
        }
        Gdx.app.log("BVGE","nocase: "+0);
        return 0;
    }
}

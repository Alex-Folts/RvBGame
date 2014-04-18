package com.me.rvbgame;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.me.rvbgame.screens.BattleScreen;
import com.me.rvbgame.screens.MainMenuScreen;
import com.me.rvbgame.screens.WinScreen;

public class RvBWorld extends RvBBase {

	private static boolean currentTurnRight = true;
	private String bgPath = "data/abstract_v2.png";
	static final Vector2 BG_IMAGE_SIZE = new Vector2(1024, 768);
	
	private Image bgImage;
	private Texture bgTexture;

	private Table buttonsTable;

//    private TextButton toggleInventoryButton;
	
    private Image nextTurnButton;
    private Image pauseButton;
    //Stats:
    public Label actionPointsLeftLabel;
    public Label statHealthLabel;
    public Label statEnergyLabel;
    public Label statPAtackLabel;
    public Label statPDefLabel;
    public Label statIDefLabel;
    public Label statIAtackLabel;
    public Label statRangeLabel;
    public Label critChanceLabel;

    
    private Image healthImage;
    private Image energyImage;
    private Image pAtackImage;
    private Image pDefenceImage;
    private Image iAtackImage;
    private Image iDefenceImage;
    private Image rangeImage;
    private Image critChanceImage;
    private Image actionPointsImage;

	protected static RvBPlayer playerLeft;
	protected static RvBPlayer playerRight;
	
	static final Vector2 LEFT_TOWER_SLOT = new Vector2((64 - 64), (360 - 300) - 64);
	static final Vector2 LEFT_UNIT_SLOT01 = new Vector2((64 - 24), (360 - 180) - 24);   //bot_right
	static final Vector2 LEFT_UNIT_SLOT02 = new Vector2((144 - 24), (360 - 220) - 24);  //middle
	static final Vector2 LEFT_UNIT_SLOT03 = new Vector2((182 - 24), (360 - 300) - 24);  //bot_left
	static final Vector2 LEFT_UNIT_SLOT04 = new Vector2((144 - 24), (360 - 150) - 24);  //top_left
	static final Vector2 LEFT_UNIT_SLOT05 = new Vector2((224 - 24), (360 - 228) - 24);  //top_right

	static final Vector2 RIGHT_TOWER_SLOT = new Vector2((418 - 64), (360 - 64) - 64);
	static final Vector2 RIGHT_UNIT_SLOT01 = new Vector2((418 - 24), (360 - 180) - 24); //bot_right
	static final Vector2 RIGHT_UNIT_SLOT02 = new Vector2((338 - 24), (360 - 144) - 24); //middle
	static final Vector2 RIGHT_UNIT_SLOT03 = new Vector2((300 - 24), (360 - 64) - 24);  //bot_left
	static final Vector2 RIGHT_UNIT_SLOT04 = new Vector2((346 - 24), (360 - 220) - 24); //top_right
	static final Vector2 RIGHT_UNIT_SLOT05 = new Vector2((262 - 24), (360 - 130) - 24); //top_left
	
	static final Vector2 WORLD_NATIVE_RES = new Vector2(480, 360);
	static final float WORLD_NATIVE_RATIO = (WORLD_NATIVE_RES.x / WORLD_NATIVE_RES.y);
    public ArrayList<UnitType> selectedUnitsList = new ArrayList<UnitType>();
    private Window pauseWindow;
    private TextButton pauseCloseButton;
    private TextButton pauseExitButton;

    private int resizeWidth;
    private int resizeHeight;

    static ActionAnimator actionAnimator;
    private RvBSceneLayerFX layerFX = null;

//    private int actionPointsLeft = 10;

    public RvBWorld(BattleScreen parentScreen) {
		super(parentScreen);
		
		playerLeft = new RvBPlayer(battleScreen);
//		playerRight = new RvBPlayer(battleScreen);
		playerRight = new RvBAIPlayer(battleScreen);
//		calcTurn();
		
		actionAnimator = new ActionAnimator(battleScreen.getSkin());
		battleScreen.sceneLayerActionAnimation.addActor(actionAnimator);
		actionAnimator.setVisible(false);
	}

	public static boolean damage(RvBUnit attacker, RvBUnit victim, int attackID) {
		int finalHealthDamage = 0, finalEnergyDamage = 0;
		
//		Gdx.app.log("BVGE", "damage: "+attacker+" "+victim);
        switch (attacker.unitType) {
            case UNIT_TYPE_DEFENDER:
            case UNIT_TYPE_MELEE:
                finalHealthDamage = attacker.getpAttack() - victim.getpDefence();
                break;

            case UNIT_TYPE_SPECIAL:
                finalHealthDamage = attacker.getpAttack() - victim.getpDefence();
                finalEnergyDamage = attacker.getiAttack() - victim.getiDefence();
                break;

            case UNIT_TYPE_TOWER:
            case UNIT_TYPE_RANGED:
                finalHealthDamage = attacker.getpAttack() - victim.getpDefence();
                finalEnergyDamage = attacker.getiAttack() - victim.getiDefence();
                int rand = (int)(Math.random() * 100) ;
                if (  rand <= attacker.getCriticalChance()){
                    finalHealthDamage += attacker.getpAttack()/2;
                    finalEnergyDamage += attacker.getiAttack()/2;
                }
                break;

            case UNIT_TYPE_RANGED_MASS:
                return massDamage(attacker, getOppositePlayer(), victim.getAttackRange());

        }

        if (attacker.unitType == UnitType.UNIT_TYPE_RANGED_MASS)
            return true;

        int newEnergy = victim.getEnergy() - finalEnergyDamage;
        if (newEnergy <= 0 && finalEnergyDamage>0)
        {
            finalHealthDamage += -newEnergy;
            newEnergy = 0;
            victim.setEnergy(newEnergy);
        }else if (finalEnergyDamage>0) victim.setEnergy(newEnergy);

//        Gdx.app.log("BVGE", "energy damage: ");

        if (finalHealthDamage<0)
            return false;

        int newHealth = victim.getHealth() - finalHealthDamage;
        if (newHealth <= 0)
        {
            newHealth = 0;
            victim.setbDead(true);
        }
        setupActionAnimation(attacker, victim, finalHealthDamage, ActionType.ACTION_TYPE_ATTACK);
        victim.setHealth(newHealth);
//        Gdx.app.log("BVGE", "victim health = "+victim.getHealth());

		return true;
	}

    public static boolean ordinaryDamage(RvBUnit unitRangedMass, RvBUnit unit, int additionalDamage){
        if (unit != null){
            int finalHealthDamage = 0, finalEnergyDamage = 0;

            finalHealthDamage = unitRangedMass.getpAttack() - unit.getpDefence()+additionalDamage;
            finalEnergyDamage = unitRangedMass.getiAttack() - unit.getiDefence();

            int newEnergy = unit.getEnergy() - finalEnergyDamage;
            if (newEnergy <= 0 && finalEnergyDamage>0)
            {
                finalHealthDamage += -newEnergy;
                newEnergy = 0;
                unit.setEnergy(newEnergy);
            }else if (finalEnergyDamage>0) unit.setEnergy(newEnergy);

            if (finalHealthDamage>0){
                int newHealth = unit.getHealth() - finalHealthDamage;
                if (newHealth <= 0)
                {
                    newHealth = 0;
                    unit.setbDead(true);
                }
                unit.setHealth(newHealth);
            }
        }
        return true;
    }

	public static boolean massDamage(RvBUnit unitRangedMass, RvBPlayer oppositePlayer, byte attackRange){
        int randDamage = 0;
        int critDamage = 0;
        int rand = (int)(Math.random() * 100) ;
        boolean bPlayedAnim = false;
        RvBUnit firstVictim = null;
        if (rand <= unitRangedMass.getCriticalChance())
            critDamage += unitRangedMass.getpAttack()/2;
        if (attackRange == 5){
            for(int i = 1; i <= 5; i++)
            {
                RvBUnit tmpVictim = RvBWorld.getOppositePlayer().getSlotUnitByIdx(i);

                if (unitRangedMass.getEnergy()>0)
                    randDamage = (int)(Math.random() * (unitRangedMass.getEnergy()/5));
                else
                    randDamage = 0;
                ordinaryDamage(unitRangedMass,tmpVictim,randDamage+critDamage);
                if (tmpVictim != null && firstVictim == null)
                {
                	firstVictim = tmpVictim;
                }
            }
            if (oppositePlayer.slot4 == null && oppositePlayer.slot5 == null)
            {
                ordinaryDamage(unitRangedMass, oppositePlayer.tower, randDamage + critDamage);
                if (oppositePlayer.tower != null && firstVictim == null)
                {
                	firstVictim = oppositePlayer.tower;
                }
            }
            unitRangedMass.setEnergy(unitRangedMass.getEnergy()-randDamage);
        }
        else
        if (attackRange == 3)
        {
            if (unitRangedMass.getEnergy()>0)
                randDamage = 5+(int)(Math.random() * (unitRangedMass.getEnergy()/3));
            else
                randDamage = 0;
            unitRangedMass.setEnergy(unitRangedMass.getEnergy()-randDamage);
            ordinaryDamage(unitRangedMass,oppositePlayer.slot2,randDamage);
            ordinaryDamage(unitRangedMass,oppositePlayer.slot4,randDamage);
            ordinaryDamage(unitRangedMass, oppositePlayer.slot5, randDamage);
            if (oppositePlayer.slot5 != null && firstVictim == null)
            {
            	firstVictim = oppositePlayer.slot5;
            }
            if (oppositePlayer.slot4 != null && firstVictim == null)
            {
            	firstVictim = oppositePlayer.slot4;
            }
            if (oppositePlayer.slot2 != null && firstVictim == null)
            {
            	firstVictim = oppositePlayer.slot2;
            }
        }
        else
        if (attackRange == 1){
            if (unitRangedMass.getEnergy()>0)
                randDamage = (int)(Math.random() * (unitRangedMass.getEnergy()/2));
            else
                randDamage = 0;
            unitRangedMass.setEnergy(unitRangedMass.getEnergy() - randDamage);
            ordinaryDamage(unitRangedMass,oppositePlayer.slot4,0);
            ordinaryDamage(unitRangedMass,oppositePlayer.slot5,0);
            if (oppositePlayer.slot5 != null && firstVictim == null)
            {
            	firstVictim = oppositePlayer.slot5;
            }
            if (oppositePlayer.slot4 != null && firstVictim == null)
            {
            	firstVictim = oppositePlayer.slot4;
            }
        }
        setupActionAnimation(unitRangedMass, firstVictim, randDamage, ActionType.ACTION_TYPE_ATTACK);
        return true;
    }
	
    public boolean performTickDamage() {
		return false;
	}
	
	public static boolean heal(RvBUnit healler, RvBUnit target, int attackID) {
//        Gdx.app.log("TOW","heal "+target);
        if (target.getHealth() == target.getMaxHealth())
            return false;
        int newHealth = target.getHealth() + StatsHelper.HEAL_POINTS;
        if (newHealth > target.getMaxHealth())
        {
            newHealth = target.getMaxHealth();
        }
        target.setHealth(newHealth);

		return true;
	}
	
	public boolean calcTurn() {
		currentTurnRight = !currentTurnRight;

        if (playerLeft.checkIfUnitsDead())
            battleScreen.mGame.setScreen( new WinScreen(battleScreen.mGame, false));
        else if (playerRight.checkIfUnitsDead())
            battleScreen.mGame.setScreen( new WinScreen(battleScreen.mGame, true));

        if (currentTurnRight) {
        	playerLeft.endTurn();
			playerRight.beginTurn();
            
//            Gdx.app.log("BVGE", "Current turn: Right");
            if (actionPointsLeftLabel != null)
            actionPointsLeftLabel.setText(String.format("Curr: right"));
		} else {
//            Gdx.app.log("BVGE", "Current turn: Left");
            if (actionPointsLeftLabel != null)
            actionPointsLeftLabel.setText(String.format("Curr: left"));
			
            playerRight.endTurn();
            playerLeft.beginTurn();
		}
		return true;
	}
	
	public boolean endTurn(RvBPlayer player) {
		player.isMyTurn = false;
		return calcTurn();
	}
	
	public static  RvBPlayer getCurrentTurnPlayer() {
		if (currentTurnRight)
		{
			return playerRight;
		}

		return playerLeft;
	}

    public static RvBPlayer getOppositePlayer(){
        if (!currentTurnRight)
        {
            return playerRight;
        }

        return playerLeft;
    }

	@Override
	public void show() {
		super.show();
		
		if (playerLeft != null){
			playerLeft.show();
		}
		if (playerRight != null){
			playerRight.show();
		}
		if (actionAnimator != null)
		{
			actionAnimator.show();
		}
        bgTexture = new Texture(Gdx.files.internal(bgPath));
		bgTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		initWorld();
		calcTurn();

        Texture texture = new Texture(Gdx.files.internal("data/btn_next.png"));
        texture.setFilter(TextureFilter.Linear,TextureFilter.Linear);
        TextureRegion region = new TextureRegion(texture, 0, 0, 512, 512);

        nextTurnButton = new Image(region);//TextButton("Next turn", battleScreen.getSkin());

        texture = new Texture(Gdx.files.internal("data/btn_pause.png"));
        texture.setFilter(TextureFilter.Linear,TextureFilter.Linear);
        region = new TextureRegion(texture, 0, 0, 512, 512);

        pauseButton = new Image(region);

        actionPointsLeftLabel = new Label("",battleScreen.getSkin());

        statHealthLabel = new Label("",battleScreen.getSkin());
        statEnergyLabel = new Label("",battleScreen.getSkin());
        statIAtackLabel = new Label("",battleScreen.getSkin());
        statIDefLabel = new Label("",battleScreen.getSkin());
        statPAtackLabel = new Label("",battleScreen.getSkin());
        statPDefLabel = new Label("",battleScreen.getSkin());
        statRangeLabel = new Label("",battleScreen.getSkin());
        critChanceLabel = new Label("",battleScreen.getSkin());


        texture = new Texture(Gdx.files.internal("data/heart_ico.png"));
        texture.setFilter(TextureFilter.Linear,TextureFilter.Linear);
        region = new TextureRegion(texture, 0, 0, 512, 512);
        healthImage = new Image(region);
        healthImage.setScaling(Scaling.stretch);
        healthImage.setAlign((Align.bottom | Align.left));
        healthImage.setSize(48, 48);
        healthImage.setColor(Color.DARK_GRAY);

        texture = new Texture(Gdx.files.internal("data/ap_icon.png"));
        texture.setFilter(TextureFilter.Linear,TextureFilter.Linear);
        region = new TextureRegion(texture, 0, 0, 512, 512);

        energyImage = new Image(region);
        energyImage.setScaling(Scaling.stretch);
        energyImage.setAlign((Align.bottom | Align.left));
        energyImage.setSize(48,48);
        energyImage.setColor(Color.DARK_GRAY);

        texture = new Texture(Gdx.files.internal("data/sword_icon.png"));
        texture.setFilter(TextureFilter.Linear,TextureFilter.Linear);
        region = new TextureRegion(texture, 0, 0, 512, 512);

        pAtackImage = new Image(region);
        pAtackImage.setScaling(Scaling.stretch);
        pAtackImage.setAlign((Align.bottom | Align.left));
        pAtackImage.setSize(48,48);
        pAtackImage.setColor(Color.DARK_GRAY);

        iAtackImage = new Image(region);
        iAtackImage.setScaling(Scaling.stretch);
        iAtackImage.setAlign((Align.bottom | Align.left));
        iAtackImage.setSize(48,48);
        iAtackImage.setColor(Color.DARK_GRAY);

        texture = new Texture(Gdx.files.internal("data/shield_icon.png"));
        texture.setFilter(TextureFilter.Linear,TextureFilter.Linear);
        region = new TextureRegion(texture, 0, 0, 512, 512);

        pDefenceImage = new Image(region);
        pDefenceImage.setScaling(Scaling.stretch);
        pDefenceImage.setAlign((Align.bottom | Align.left));
        pDefenceImage.setSize(48,48);
        pDefenceImage.setColor(Color.DARK_GRAY);

        iDefenceImage = new Image(region);
        iDefenceImage.setScaling(Scaling.stretch);
        iDefenceImage.setAlign((Align.bottom | Align.left));
        iDefenceImage.setSize(48,48);
        iDefenceImage.setColor(Color.DARK_GRAY);

        texture = new Texture(Gdx.files.internal("data/range_cross.png"));
        texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        region = new TextureRegion(texture, 0, 0, 512, 512);

        rangeImage = new Image(region);
        rangeImage.setScaling(Scaling.stretch);
        rangeImage.setAlign((Align.bottom | Align.left));
        rangeImage.setSize(48, 48);
        rangeImage.setColor(Color.DARK_GRAY);

        texture = new Texture(Gdx.files.internal("data/crit_chance.png"));
        texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        region = new TextureRegion(texture, 0, 0, 512, 512);

        critChanceImage = new Image(region);
        critChanceImage.setScaling(Scaling.stretch);
        critChanceImage.setAlign((Align.bottom | Align.left));
        critChanceImage.setSize(48, 48);
        critChanceImage.setColor(Color.DARK_GRAY);

        texture = new Texture(Gdx.files.internal("data/action_points.png"));
        texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        region = new TextureRegion(texture, 0, 0, 512, 512);

        actionPointsImage = new Image(region);
        actionPointsImage.setScaling(Scaling.stretch);
        actionPointsImage.setAlign((Align.bottom | Align.left));
        actionPointsImage.setSize(48, 48);
        actionPointsImage.setColor(Color.DARK_GRAY);

        battleScreen.sceneLayerGUI.addActor(healthImage);
        battleScreen.sceneLayerGUI.addActor(energyImage);
        battleScreen.sceneLayerGUI.addActor(pAtackImage);
        battleScreen.sceneLayerGUI.addActor(iAtackImage);
        battleScreen.sceneLayerGUI.addActor(pDefenceImage);
        battleScreen.sceneLayerGUI.addActor(iDefenceImage);
        battleScreen.sceneLayerGUI.addActor(rangeImage);
        battleScreen.sceneLayerGUI.addActor(critChanceImage);

        battleScreen.sceneLayerGUI.addActor(statHealthLabel);
        battleScreen.sceneLayerGUI.addActor(statEnergyLabel);
        battleScreen.sceneLayerGUI.addActor(statIAtackLabel);
        battleScreen.sceneLayerGUI.addActor(statIDefLabel);
        battleScreen.sceneLayerGUI.addActor(statPAtackLabel);
        battleScreen.sceneLayerGUI.addActor(statPDefLabel);
        battleScreen.sceneLayerGUI.addActor(statRangeLabel);
        battleScreen.sceneLayerGUI.addActor(critChanceLabel);
        battleScreen.sceneLayerGUI.addActor(actionPointsImage);

//        battleScreen.sceneLayerGUI.addActor(nextTurnButton);
        battleScreen.sceneLayerGUI.addActor(actionPointsLeftLabel);
        
        Gdx.input.setInputProcessor(battleScreen.stage);
        
//        Gdx.app.log("BVGE", "show!!");
	}
	
	@Override
	public void dispose() {
		super.dispose();
		
		if (playerLeft != null){
			playerLeft.dispose();
		}
		if (playerLeft != null){
			playerRight.dispose();
		}
		if (actionAnimator != null)
		{
			actionAnimator.dispose();
		}
		
		bgTexture.dispose();
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		resizeHeight = height;
        resizeWidth = width;
		if (playerLeft != null){
			playerLeft.resize(width, height);
		}
		if (playerLeft != null){
			playerRight.resize(width, height);
		}
		if (actionAnimator != null)
		{
			actionAnimator.resize(width, height);
		}

        if ( layerFX == null )
        {
            layerFX = new RvBSceneLayerFX();
            battleScreen.sceneLayerFX.applyShine(playerLeft,playerRight);
            battleScreen.sceneLayerFX.setVisible(true);
        }
		
        TextureRegion region = new TextureRegion(bgTexture, 0, 0, 1024, 768);
		bgImage = new Image(region);
		bgImage.setScaling(Scaling.stretch);
		bgImage.setAlign((Align.bottom | Align.left));
//		bgImage.setSize(-(height * (BG_IMAGE_SIZE.x / BG_IMAGE_SIZE.y)), height);//mirrored image!!
        bgImage.setSize(width, height);
//		bgImage.setPosition((width * 0.5f) - (bgImage.getWidth() * 0.5f), 0);
        bgImage.setPosition(0, 0);
//		bgImage.setColor(0.75f, 0.75f, 0.75f, 1);
//		bgImage.setSize(width, (float)width / (BG_IMAGE_SIZE.x / BG_IMAGE_SIZE.y));

		buttonsTable = new Table();
		buttonsTable.size(width, height);
		buttonsTable.bottom();
		buttonsTable.right();
		
		battleScreen.sceneLayerGUI.addActor(buttonsTable);
		
		buttonsTable.row();
		
//      nextTurnButton
//        float currX = width-50;
//        float currY = 5;
//        float bWidth = 50;
//        float bHeight = 50;
//        nextTurnButton.setBounds(currX, currY, bWidth, bHeight);
        nextTurnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                Gdx.app.log("GUI", "Next turn");
                clearStatLabels();
                if (currentTurnRight) {
                    endTurn(playerRight);
                } else
                    endTurn(playerLeft);

            }

            ;
        });
//        battleScreen.sceneLayerGUI.addActor(nextTurnButton);

//        pauseButton.setBounds(currX-bWidth-5,currY,bWidth,bHeight);
        pauseButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
//                Gdx.app.log("GUI", "Stop");

                revealPauseWindow();
            }
        });
//        battleScreen.sceneLayerGUI.addActor(pauseButton);

//        buttonsTable.add(nextTurnButton).width(96 * (battleScreen.screenResW / WORLD_NATIVE_RES.x)).height(24 * (battleScreen.screenResW / WORLD_NATIVE_RES.x)).padBottom(4);
        buttonsTable.add(pauseButton).width(50 * (battleScreen.screenResW / WORLD_NATIVE_RES.x)).height(50 * (battleScreen.screenResW / WORLD_NATIVE_RES.x));
        buttonsTable.add(nextTurnButton).width(50 * (battleScreen.screenResW / WORLD_NATIVE_RES.x)).height(50 * (battleScreen.screenResW / WORLD_NATIVE_RES.x));
        
        actionPointsLeftLabel.setBounds(35, height-60, 30, 30);
        actionPointsLeftLabel.setAlignment(Align.center);
        actionPointsImage.setBounds(35, height-60, 30, 30);

        healthImage.setBounds   (0, height - 30, 30, 30);
        statHealthLabel.setBounds(0, height - 30, 30, 30);
        statHealthLabel.setAlignment(Align.center, Align.center);

        energyImage.setBounds   (0, height - 60, 30, 30);
        statEnergyLabel.setBounds (0, height - 60, 30, 30);
        statEnergyLabel.setAlignment(Align.center, Align.center);

        pDefenceImage.setBounds (35, height - 30, 30, 30);
        statPDefLabel.setBounds (35, height - 30, 30, 30);
        statPDefLabel.setAlignment(Align.center, Align.center);
        iDefenceImage.setBounds (65, height - 30, 30, 30);
        statIDefLabel.setBounds  (65, height - 30, 30, 30);
        statIDefLabel.setAlignment(Align.center, Align.center);

        pAtackImage.setBounds   (95, height - 30, 30, 30);
        statPAtackLabel.setBounds(95, height - 30, 30, 30);
        statPAtackLabel.setAlignment(Align.center, Align.center);

        iAtackImage.setBounds   (125, height - 30, 30, 30);
        statIAtackLabel.setBounds   (125, height - 30, 30, 30);
        statIAtackLabel.setAlignment(Align.center, Align.center);

        rangeImage.setBounds    (155, height - 30, 30, 30);
        statRangeLabel.setBounds   (155, height - 30, 30, 30);
        statRangeLabel.setAlignment(Align.center, Align.center);

        critChanceImage.setBounds(185, height - 30, 30, 30);
        critChanceLabel.setBounds(185, height - 30, 30, 30);
        critChanceLabel.setAlignment(Align.center);

//		bgImage.setZIndex(WorldDrawLayer.DRAW_LAYER_BG);
		
//		battleScreen.stage.addActor(bgImage);
		battleScreen.sceneLayerBG.addActor(bgImage);
        
//        Gdx.app.log("BVGE", "resize!!");
	}

    private void revealPauseWindow() {
        if (pauseWindow == null){
            pauseWindow = new Window("Pause Game", battleScreen.getSkin());
            pauseWindow.setVisible(false);
            pauseWindow.setMovable(false);
            pauseWindow.setSize(resizeWidth, resizeHeight * 0.75f);
            pauseWindow.setPosition(0, resizeHeight * 0.5f - pauseWindow.getHeight() * 0.5f);

//            Texture texture = new Texture(Gdx.files.internal("data/btn_next.png"));
//            texture.setFilter(TextureFilter.Linear,TextureFilter.Linear);
//            TextureRegion region = new TextureRegion(texture, 0, 0, 512, 512);
//            pauseCloseButton = new Image(region);

            float currX = (pauseWindow.getWidth() - StatsHelper.BUTTON_WIDTH) / 2;
            float currY = pauseWindow.getHeight() - StatsHelper.BUTTON_HEIGHT - 20;
            pauseCloseButton = new TextButton("Resume game", battleScreen.getSkin());
            pauseCloseButton.setBounds(currX,currY,StatsHelper.BUTTON_WIDTH,StatsHelper.BUTTON_HEIGHT);
            pauseCloseButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
    //                        inventoryVisible(false);
//                    Gdx.app.log("GUI","Close window");
                    pauseWindow.setVisible(false);
                };
            });
            pauseWindow.addActor(pauseCloseButton);

            pauseExitButton = new TextButton("To Main Menu",battleScreen.getSkin());
            currY -= StatsHelper.BUTTON_HEIGHT + StatsHelper.BUTTON_SPACING;
            pauseExitButton.setBounds(currX,currY,StatsHelper.BUTTON_WIDTH,StatsHelper.BUTTON_HEIGHT);
            pauseExitButton.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
//                    Gdx.app.log("GUI","Exit battle");
                    exitBattle();
                }
            });
            pauseWindow.addActor(pauseExitButton);
            battleScreen.sceneLayerGUI.addActor(pauseWindow);
        }
        pauseWindow.setVisible(true);
    }

    private void exitBattle() {
//        getCurrentTurnPlayer().setActingUnit(null);
        currentTurnRight = true;
        battleScreen.mGame.setScreen(new MainMenuScreen(battleScreen.mGame));
    }

    public void updateStatLabels(RvBUnit unit){
        statPAtackLabel.setText(String.format("%d",unit.getpAttack()));
        statIAtackLabel.setText(String.format("%d",unit.getiAttack()));
        statIDefLabel.setText(String.format("%d",unit.getiDefence()));
        statPDefLabel.setText(String.format("%d",unit.getpDefence()));
        statHealthLabel.setText(unit.healthLeftLabel.getText());
        statEnergyLabel.setText(String.format("%d",unit.getEnergy()));
        statRangeLabel.setText(String.format("%d",unit.getAttackRange()));
        actionPointsLeftLabel.setText(String.format("%d",unit.getActionPoints()));

        healthImage.setColor(StatsHelper.COLOR_DARK_RED);//(Color.RED);
        energyImage.setColor(StatsHelper.COLOR_DARK_BLUE);
        pAtackImage.setColor(StatsHelper.COLOR_DARK_RED);
        pDefenceImage.setColor(StatsHelper.COLOR_DARK_RED);
        iAtackImage.setColor(StatsHelper.COLOR_DARK_BLUE);
        iDefenceImage.setColor(StatsHelper.COLOR_DARK_BLUE);
        rangeImage.setColor(StatsHelper.COLOR_DARK_RED);

        if (unit.unitType == UnitType.UNIT_TYPE_RANGED || unit.unitType == UnitType.UNIT_TYPE_RANGED_MASS || unit.unitType == UnitType.UNIT_TYPE_TOWER)
        {
            critChanceLabel.setText(String.format("%d%%", unit.getCriticalChance()));
            critChanceImage.setColor(Color.GRAY);
            critChanceLabel.setVisible(true);
            critChanceImage.setVisible(true);
        }else{
            critChanceLabel.setVisible(false);
            critChanceImage.setVisible(false);
        }

    }

    public void clearStatLabels(){
        statPAtackLabel.setText("");
        statIAtackLabel.setText("");
        statIDefLabel.setText("");
        statPDefLabel.setText("");
        statHealthLabel.setText("");
        statEnergyLabel.setText("");
        statRangeLabel.setText("");

        healthImage.setColor(Color.DARK_GRAY);
        energyImage.setColor(Color.DARK_GRAY);
        pAtackImage.setColor(Color.DARK_GRAY);
        iAtackImage.setColor(Color.DARK_GRAY);
        pDefenceImage.setColor(Color.DARK_GRAY);
        iDefenceImage.setColor(Color.DARK_GRAY);
        rangeImage.setColor(Color.DARK_GRAY);
    }

	protected void initWorld()
	{
		currentTurnRight = true;
/*		if (playerLeft != null)
		{
			if (playerLeft.tower != null)
			{
				playerLeft.tower.addToDraw();
			}
			if (playerLeft.slot1 != null)
			{
				playerLeft.slot1.addToDraw();
			}
			if (playerLeft.slot2 != null)
			{
				playerLeft.slot2.addToDraw();
			}
			if (playerLeft.slot3 != null)
			{
				playerLeft.slot3.addToDraw();
			}
			if (playerLeft.slot4 != null)
			{
				playerLeft.slot4.addToDraw();
			}
			if (playerLeft.slot5 != null)
			{
				playerLeft.slot5.addToDraw();
			}
		}
		if (playerRight != null)
		{
			if (playerRight.tower != null)
			{
				playerRight.tower.addToDraw();
			}
			if (playerRight.slot1 != null)
			{
				playerRight.slot1.addToDraw();
			}
			if (playerRight.slot2 != null)
			{
				playerRight.slot2.addToDraw();
			}
			if (playerRight.slot3 != null)
			{
				playerRight.slot3.addToDraw();
			}
			if (playerRight.slot4 != null)
			{
				playerRight.slot4.addToDraw();
			}
			if (playerRight.slot5 != null)
			{
				playerRight.slot5.addToDraw();
			}
		}*/
	}

    public void revealRadialMenu(RvBUnit actingUnit) {

        if (battleScreen.sceneLayerRadialMenu == null){
            battleScreen.sceneLayerRadialMenu = new RvBRadialMenu(actingUnit);
            battleScreen.sceneLayerRadialMenu.setVisible(true);
            battleScreen.stage.addActor(battleScreen.sceneLayerRadialMenu);
//            Gdx.app.log("RM", "Creating...");
        }else {
            battleScreen.sceneLayerRadialMenu.changeCoords(actingUnit);
            battleScreen.sceneLayerRadialMenu.setVisible(true);
//            Gdx.app.log("RM", "Moving...");
        }

    }

    public static boolean applyActionOnVictim(RvBUnit attacker, RvBUnit victim) {
        if (!victim.isbReachable() && attacker.unitType != UnitType.UNIT_TYPE_TOWER)
        {
            return false;
        }
        
        if (victim.isbDead())
        {
        	return false;
        }
        
        switch (attacker.actionType) {
            case ACTION_TYPE_ATTACK:
//                Gdx.app.log("RM","v def"+victim.getpDefence());
                if (damage(attacker,victim,0)){                	
                    attacker.setActionPoints(attacker.getActionPoints()-1);
                    return true;
                };
                break;
            case ACTION_TYPE_FREEZE:
                if (attacker.getEnergy()>0){    //can freeze
                    victim.freeze();
                    setupActionAnimation(attacker, victim, 0, ActionType.ACTION_TYPE_FREEZE);
                    attacker.setEnergy(attacker.getEnergy()-20);
                    attacker.setActionPoints(attacker.getActionPoints()-1);
                }
                return true;
            case ACTION_TYPE_DONE:
                break;
//            case ACTION_TYPE_HEAL:
//                if (attacker.getEnergy()>=StatsHelper.HEAL_COST){
//                    if (heal(attacker,victim,0)){
//                    };
//                }
//                break;
            case ACTION_TYPE_HACK:
                break;
        }
        return false;
    }

    //actionType: 0 - phys attack, 1 - int attack, 2 - skill
    public static void setupActionAnimation(RvBUnit attacker, RvBUnit victim, int damage, ActionType actionType) {
    	actionAnimator.getRightDamageLabel().setVisible(false);
    	actionAnimator.getLeftDamageLabel().setVisible(false);
    	
    	if (currentTurnRight)
    	{
    		actionAnimator.changeTexture(actionAnimator.getLeftUnitImage(), victim.getImagePath());
    		actionAnimator.changeTexture(actionAnimator.getRightUnitImage(), attacker.getImagePath());
    		if (actionType == ActionType.ACTION_TYPE_ATTACK)
    		{
    			actionAnimator.getLeftDamageLabel().setText("-"+damage);
    			actionAnimator.getLeftDamageLabel().setVisible(true);
    			actionAnimator.changeTexture(actionAnimator.getRightAnimImage(), "data/anim_sword_ico.png");
    		} else if (actionType == ActionType.ACTION_TYPE_FREEZE)
    		{
    			actionAnimator.changeTexture(actionAnimator.getRightAnimImage(), "data/radial_menu_files/rm_freeze.png");
    		} else
    		{
    			actionAnimator.changeTexture(actionAnimator.getRightAnimImage(), "data/anim_sword_ico.png");
    		}
    		if (victim.isDefended())
    		{
    			actionAnimator.changeTexture(actionAnimator.getLeftAnimImage(), "data/anim_shield_ico.png");
    		} else
    		{
    			actionAnimator.changeTexture(actionAnimator.getLeftAnimImage(), "data/anim_swordleft_ico.png");
    		}
    	} else
    	{
    		actionAnimator.changeTexture(actionAnimator.getLeftUnitImage(), attacker.getImagePath());
    		actionAnimator.changeTexture(actionAnimator.getRightUnitImage(), victim.getImagePath());
    		if (actionType == ActionType.ACTION_TYPE_ATTACK)
    		{
    			actionAnimator.getRightDamageLabel().setText("-"+damage);
    			actionAnimator.getRightDamageLabel().setVisible(true);
    			actionAnimator.changeTexture(actionAnimator.getLeftAnimImage(), "data/anim_swordleft_ico.png");
    		} else if (actionType == ActionType.ACTION_TYPE_FREEZE)
    		{
    			actionAnimator.changeTexture(actionAnimator.getLeftAnimImage(), "data/radial_menu_files/rm_freeze.png");
    		} else
    		{
    			actionAnimator.changeTexture(actionAnimator.getLeftAnimImage(), "data/anim_swordleft_ico.png");
    		}
    		if (victim.isDefended())
    		{
    			actionAnimator.changeTexture(actionAnimator.getRightAnimImage(), "data/anim_shield_ico.png");
    		} else
    		{
    			actionAnimator.changeTexture(actionAnimator.getRightAnimImage(), "data/anim_sword_ico.png");
    		}
    	}
//    	actionAnimator.setVisible(true);
    	actionAnimator.showAndStartAnim(damage > 0, currentTurnRight, getCurrentTurnPlayer());
	}
    
    public void applyActionOnSelf(RvBUnit unit) {

        switch (unit.actionType) {
            case ACTION_TYPE_DEFEND:
                unit.setiDefence(unit.getiDefence()*2);
                unit.setpDefence(unit.getpDefence() * 2);
                unit.setDefended(true);
                unit.setActionPoints(unit.getActionPoints()-1);
                this.updateStatLabels(unit);
                break;
            case ACTION_TYPE_WAIT:
                unit.setActionPoints(unit.getActionPoints()+1);
                unit.setbCanOperate(false);
                this.battleScreen.sceneLayerRadialMenu.hide();
                this.updateStatLabels(unit);
                break;
            case ACTION_TYPE_AIM:
                unit.setCriticalChance(unit.getCriticalChance()*2);
                unit.setActionPoints(unit.getActionPoints()-1);
                updateStatLabels(unit);
                break;
            case ACTION_TYPE_HEAL:
                break;
        }
    }

	public ActionAnimator getActionAnimator() {
		return actionAnimator;
	}
}

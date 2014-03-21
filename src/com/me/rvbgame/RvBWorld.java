package com.me.rvbgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.me.rvbgame.units.StatsHelper;
import com.me.rvbgame.units.UnitRangedMass;

public class RvBWorld extends RvBBase {

	private static boolean currentTurnRight = true;
	private String bgPath = "data/Andr_087098.png";
	
	private Image bgImage;
	private Texture bgTexture;

    private TextButton nextTurnButton;
    //Stats:
    public Label actionPointsLeftLabel;
    public Label statHealthLabel;
    public Label statEnergyLabel;
    public Label statPAtackLabel;
    public Label statPDefLabel;
    public Label statIDefLabel;
    public Label statIAtackLabel;
    public Label statRangeLabel;

    private Image healthImage;
    private Image energyImage;
    private Image pAtackImage;
    private Image pDefenceImage;
    private Image iAtackImage;
    private Image iDefenceImage;
    private Image rangeImage;

	protected static RvBPlayer playerLeft;
	protected static RvBPlayer playerRight;
	
	static final Vector2 LEFT_TOWER_SLOT = new Vector2((64 - 64), (360 - 300) - 64);
	static final Vector2 LEFT_UNIT_SLOT01 = new Vector2((64 - 24), (360 - 180) - 24);
	static final Vector2 LEFT_UNIT_SLOT02 = new Vector2((144 - 24), (360 - 220) - 24);
	static final Vector2 LEFT_UNIT_SLOT03 = new Vector2((182 - 24), (360 - 300) - 24);
	static final Vector2 LEFT_UNIT_SLOT04 = new Vector2((144 - 24), (360 - 150) - 24);
	static final Vector2 LEFT_UNIT_SLOT05 = new Vector2((224 - 24), (360 - 228) - 24);

	static final Vector2 RIGHT_TOWER_SLOT = new Vector2((418 - 64), (360 - 64) - 64);
	static final Vector2 RIGHT_UNIT_SLOT01 = new Vector2((418 - 24), (360 - 180) - 24);
	static final Vector2 RIGHT_UNIT_SLOT02 = new Vector2((338 - 24), (360 - 144) - 24);
	static final Vector2 RIGHT_UNIT_SLOT03 = new Vector2((300 - 24), (360 - 64) - 24);
	static final Vector2 RIGHT_UNIT_SLOT04 = new Vector2((346 - 24), (360 - 220) - 24);
	static final Vector2 RIGHT_UNIT_SLOT05 = new Vector2((262 - 24), (360 - 130) - 24);
	
	static final Vector2 WORLD_NATIVE_RES = new Vector2(480, 360);
	static final float WORLD_NATIVE_RATIO = (WORLD_NATIVE_RES.x / WORLD_NATIVE_RES.y);
	
    private int actionPointsLeft = 10;

    public RvBWorld(BattleScreen parentScreen) {
		super(parentScreen);
		
		playerLeft = new RvBPlayer(battleScreen);
		playerRight = new RvBPlayer(battleScreen);
		
//		calcTurn();
	}

	public static boolean damage(RvBUnit attacker, RvBUnit victim, int attackID) {
		int finalHealthDamage = 0, finalEnergyDamage = 0;
		
		Gdx.app.log("BVGE", "damage: "+attacker+" "+victim);
        switch (attacker.unitType) {
            case UNIT_TYPE_DEFENDER:
            case UNIT_TYPE_MELEE:
                finalHealthDamage = attacker.getpAttack() - victim.getpDefence();
                break;

            case UNIT_TYPE_SPECIAL:
                finalHealthDamage = attacker.getpAttack() - victim.getpDefence();
                finalEnergyDamage = attacker.getiAttack() - victim.getiDefence();
                if (attacker.getEnergy()>0){    //can freeze
                    victim.freeze();
                    attacker.setEnergy(attacker.getEnergy()-20);
                }
                break;

            case UNIT_TYPE_RANGED:
                finalHealthDamage = attacker.getpAttack() - victim.getpDefence();
                finalEnergyDamage = attacker.getiAttack() - victim.getiDefence();
                break;

            case UNIT_TYPE_RANGED_MASS:
                return massDamage(attacker, getOppositePlayer(), victim.getAttackRange());

            case UNIT_TYPE_TOWER:
                break;
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

        Gdx.app.log("BVGE", "energy damage: ");

        if (finalHealthDamage<0)
            return false;

        int newHealth = victim.getHealth() - finalHealthDamage;
        if (newHealth <= 0)
        {
            newHealth = 0;
            victim.setbDead(true);
        }
        victim.setHealth(newHealth);

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
        /* And there you have it. A random integer value in the range [Min,Max], or per the example [5,10]:

            5 + (int)(Math.random() * ((10 - 5) + 1))
        * */
        if (attackRange == 3){
            for(RvBUnit unit : oppositePlayer.units){
                if (unitRangedMass.getEnergy()>0)
                    randDamage = (int)(Math.random() * (unitRangedMass.getEnergy()/5));
                else
                    randDamage = 0;
                ordinaryDamage(unitRangedMass,unit,randDamage);
            }
            unitRangedMass.setEnergy(unitRangedMass.getEnergy()-randDamage);
        }
        else
        if (attackRange == 2)
        {
            if (unitRangedMass.getEnergy()>0)
                randDamage = 5+(int)(Math.random() * (unitRangedMass.getEnergy()/3));
            else
                randDamage = 0;
            unitRangedMass.setEnergy(unitRangedMass.getEnergy()-randDamage);
            ordinaryDamage(unitRangedMass,oppositePlayer.slot2,randDamage);
            ordinaryDamage(unitRangedMass,oppositePlayer.slot4,randDamage);
            ordinaryDamage(unitRangedMass,oppositePlayer.slot5,randDamage);
        }
        else
        if (attackRange == 1){
            if (unitRangedMass.getEnergy()>0)
                randDamage = (int)(Math.random() * (unitRangedMass.getEnergy()/2));
            else
                randDamage = 0;
            unitRangedMass.setEnergy(unitRangedMass.getEnergy()-randDamage);
            ordinaryDamage(unitRangedMass,oppositePlayer.slot4,0);
            ordinaryDamage(unitRangedMass,oppositePlayer.slot5,0);
        }
        return true;
    }
    public boolean performTickDamage() {
		return false;
	}
	
	public static boolean heal(RvBUnit heller, RvBUnit target, int attackID) {
		return false;
	}
	
	public boolean calcTurn() {
		currentTurnRight = !currentTurnRight;

        if (playerLeft.checkIfUnitsDead())
            battleScreen.mGame.setScreen( new WinScreen(battleScreen.mGame, false));
        else if (playerRight.checkIfUnitsDead())
            battleScreen.mGame.setScreen( new WinScreen(battleScreen.mGame, true));

        if (currentTurnRight) {
			playerRight.beginTurn();
            playerLeft.endTurn();
            Gdx.app.log("BVGE", "Current turn: Right");
            if (actionPointsLeftLabel != null)
            actionPointsLeftLabel.setText(String.format("Curr: right"));
		} else {


            Gdx.app.log("BVGE", "Current turn: Left");
            if (actionPointsLeftLabel != null)
            actionPointsLeftLabel.setText(String.format("Curr: left"));
			playerLeft.beginTurn();
            playerRight.endTurn();
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
		
		bgTexture = new Texture(Gdx.files.internal(bgPath));
		bgTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		initWorld();
		calcTurn();

        nextTurnButton = new TextButton("Next turn", battleScreen.getSkin());
        actionPointsLeftLabel = new Label(String.format("AP: %d",actionPointsLeft),battleScreen.getSkin());

        statHealthLabel = new Label("",battleScreen.getSkin());
        statEnergyLabel = new Label("",battleScreen.getSkin());
        statIAtackLabel = new Label("",battleScreen.getSkin());
        statIDefLabel = new Label("",battleScreen.getSkin());
        statPAtackLabel = new Label("",battleScreen.getSkin());
        statPDefLabel = new Label("",battleScreen.getSkin());
        statRangeLabel = new Label("",battleScreen.getSkin());


        Texture texture = new Texture(Gdx.files.internal("data/heart_ico.png"));
        texture.setFilter(TextureFilter.Linear,TextureFilter.Linear);
        TextureRegion region = new TextureRegion(texture, 0, 0, 512, 512);
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

        battleScreen.sceneLayerGUI.addActor(healthImage);
        battleScreen.sceneLayerGUI.addActor(energyImage);
        battleScreen.sceneLayerGUI.addActor(pAtackImage);
        battleScreen.sceneLayerGUI.addActor(iAtackImage);
        battleScreen.sceneLayerGUI.addActor(pDefenceImage);
        battleScreen.sceneLayerGUI.addActor(iDefenceImage);
        battleScreen.sceneLayerGUI.addActor(rangeImage);

        battleScreen.sceneLayerGUI.addActor(statHealthLabel);
        battleScreen.sceneLayerGUI.addActor(statEnergyLabel);
        battleScreen.sceneLayerGUI.addActor(statIAtackLabel);
        battleScreen.sceneLayerGUI.addActor(statIDefLabel);
        battleScreen.sceneLayerGUI.addActor(statPAtackLabel);
        battleScreen.sceneLayerGUI.addActor(statPDefLabel);
        battleScreen.sceneLayerGUI.addActor(statRangeLabel);

        battleScreen.sceneLayerGUI.addActor(nextTurnButton);
        battleScreen.sceneLayerGUI.addActor(actionPointsLeftLabel);

        Gdx.input.setInputProcessor(battleScreen.stage);
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
		
		bgTexture.dispose();
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		
		if (playerLeft != null){
			playerLeft.resize(width, height);
		}
		if (playerLeft != null){
			playerRight.resize(width, height);
		}
		
		TextureRegion region = new TextureRegion(bgTexture, 0, 0, 512, 288);
		
		bgImage = new Image(region);
		bgImage.setScaling(Scaling.stretch);
		bgImage.setAlign((Align.bottom | Align.left));
		bgImage.setSize(width, height);

//      nextTurnButton
        float currX = width-100;
        float currY = LEFT_TOWER_SLOT.y;
        float bWidth = 100;
        float bHeight = 100;
        nextTurnButton.setBounds(currX, currY, bWidth, bHeight);
        nextTurnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("BVGE", "Next turn");
                clearStatLabels();
                if (currentTurnRight) {
                    endTurn(playerRight);
                } else
                    endTurn(playerLeft);

                if (actionPointsLeft>=0)
                {
                    actionPointsLeft--;
//                    actionPointsLeftLabel.setText(String.format("AP: %d",actionPointsLeft));
                }
            }

            ;
        });
        actionPointsLeftLabel.setBounds(35, height-60, 30, 30);
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


//		bgImage.setZIndex(WorldDrawLayer.DRAW_LAYER_BG);
		
//		battleScreen.stage.addActor(bgImage);
//		battleScreen.sceneLayerBG.addActor(bgImage);
	}

    public void updateStatLabels(RvBUnit unit){
        statPAtackLabel.setText(String.format("%d",unit.getpAttack()));
        statIAtackLabel.setText(String.format("%d",unit.getiAttack()));
        statIDefLabel.setText(String.format("%d",unit.getiDefence()));
        statPDefLabel.setText(String.format("%d",unit.getpDefence()));
        statHealthLabel.setText(unit.healthLeftLabel.getText());
        statEnergyLabel.setText(String.format("%d",unit.getEnergy()));
        statRangeLabel.setText(String.format("%d",unit.getAttackRange()));

        healthImage.setColor(StatsHelper.COLOR_DARK_RED);//(Color.RED);
        energyImage.setColor(StatsHelper.COLOR_DARK_BLUE);
        pAtackImage.setColor(StatsHelper.COLOR_DARK_RED);
        pDefenceImage.setColor(StatsHelper.COLOR_DARK_RED);
        iAtackImage.setColor(StatsHelper.COLOR_DARK_BLUE);
        iDefenceImage.setColor(StatsHelper.COLOR_DARK_BLUE);
        rangeImage.setColor(StatsHelper.COLOR_DARK_RED);
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
}

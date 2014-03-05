package com.me.rvbgame;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;

public abstract class RvBUnit extends RvBBase {

    private int health;
    private int energy;

    private int pAttack;    //physical attack
    private int pDefence;   //physical defence

    private int iAttack;    //intelligence attack
    private int iDefence;   //intelligence defence

    private byte targetsNum;
    private byte attackRange;

//    private Image unitImage;

    public UnitType unitType;
    
    protected RvBPlayer player;
    private boolean bCanOperate;
    private boolean bDead;

	protected final String avaPath = "data/Andr_087098.png";
	
	protected Image avaImage;
	private Texture avaTexture;
	protected int avaTexWidth = 512;
	protected int avaTexHeight = 512;
    
    public RvBUnit(BattleScreen parentScreen, RvBPlayer playerOwner) {
        super(parentScreen);
        
        player = playerOwner;
    }

    public RvBUnit() {
		super();
	}
    
    public String getImagePath() {
		return avaPath;
	}
    
    public int getAvaImagwWidth() {
		return avaTexWidth;
	}
    
    public int getAvaImagwHeight() {
		return avaTexHeight;
	}
    
    public Vector2 getAvaSize() {
		return new Vector2(128, 128);
	}

    public void setUnitImage(String texturePath, int width, int height){
/*        Texture texture = new Texture(Gdx.files.internal(texturePath));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        TextureRegion region = new TextureRegion(texture, 0,0, width, height);
        Image splashImage = new Image( region);
        splashImage.setScaling(Scaling.stretch);
        splashImage.setAlign((Align.bottom | Align.left));
        splashImage.setSize(width, height);
        splashImage.setColor(1.0f, 1.0f, 1.0f, 0.0f);

        this.setUnitImage(splashImage);*/
    }

/*    public Image getUnitImage() {
        return unitImage;
    }*/

    public void setUnitImage(Image image) {
 /*       this.unitImage = image;
        this.battleScreen.stage.addActor(this.unitImage);
        this.unitImage.addListener( new ClickListener() {             
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (battleScreen.world.getCurrentTurnPlayer() == player)
				{
					player.setActingUnit(RvBUnit.this);
				} else
				{
					if (player.getActingUnit() != null)
					{
						RvBWorld.damage(player.getActingUnit(), RvBUnit.this, 0);
						player.getActingUnit().setbCanOperate(false);
					}
				}
			};
		});*/
    }

    public static boolean IsPhysAttack(int attackID) {
		return true;
	}
    
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getpAttack() {
        return pAttack;
    }

    public void setpAttack(int pAttack) {
        this.pAttack = pAttack;
    }

    public int getpDefence() {
        return pDefence;
    }

    public void setpDefence(int pDefence) {
        this.pDefence = pDefence;
    }

    public int getiAttack() {
        return iAttack;
    }

    public void setiAttack(int iAttack) {
        this.iAttack = iAttack;
    }

    public int getiDefence() {
        return iDefence;
    }

    public void setiDefence(int iDefence) {
        this.iDefence = iDefence;
    }

    public byte getTargetsNum() {
        return targetsNum;
    }

    public void setTargetsNum(byte targetsNum) {
        this.targetsNum = targetsNum;
    }

    public byte getAttackRange() {
        return attackRange;
    }

    public void setAttackRange(byte attackRange) {
        this.attackRange = attackRange;
    }

	public boolean isbCanOperate() {
		return bCanOperate;
	}

	public void setbCanOperate(boolean bCanOperate) {
		this.bCanOperate = bCanOperate;

		if (bCanOperate)
		{
			settowerColor(new Color(0, 1, 0, 1));
		} else
		{
			settowerColor(new Color(1, 1, 1, 1));
		}
		Gdx.app.log("RvB", "setbCanOperate "+bCanOperate);
	}

	public boolean isbDead() {
		return bDead;
	}

	public void setbDead(boolean bDead) {
		this.bDead = bDead;
	}
	
	@Override
	public void show() {
		super.show();
		
		avaTexture = new Texture(Gdx.files.internal(getImagePath()));
		avaTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	}
	
	@Override
	public void dispose() {
		super.dispose();
		
		avaTexture.dispose();
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		
		TextureRegion region = new TextureRegion(avaTexture, 0, 0, getAvaImagwWidth(), getAvaImagwHeight());
		
		avaImage = new Image(region);
		avaImage.setScaling(Scaling.stretch);
		avaImage.setAlign((Align.bottom | Align.left));
		avaImage.setSize(getAvaSize().x, getAvaSize().y);
		
		avaImage.addListener( new ClickListener() {             
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (battleScreen.world.getCurrentTurnPlayer() == player)
				{
					player.setActingUnit(RvBUnit.this);
				} else
				{
					if (player.getActingUnit() != null)
					{
						RvBWorld.damage(player.getActingUnit(), RvBUnit.this, 0);
						player.getActingUnit().setbCanOperate(false);
					}
				}
			};
		});
		
		if (bCanOperate)
		{
			settowerColor(new Color(0, 1, 0, 1));
		} else
		{
			settowerColor(new Color(1, 1, 1, 1));
		}
		
//		battleScreen.stage.addActor(avaImage);
		battleScreen.sceneLayerUnits.addActor(avaImage);
	}
	
	public void settowerColor(Color newClor)
	{
		if (avaImage != null)
		{
			avaImage.setColor(newClor);
		}
	}
}

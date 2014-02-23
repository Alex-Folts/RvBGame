package com.me.rvbgame;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
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

    private Image unitImage;

    public UnitType unitType;

    public RvBUnit(BattleScreen parentScreen) {
        super(parentScreen);
    }

    public void setUnitImage(String texturePath, int width, int height){
        Texture texture = new Texture(Gdx.files.internal(texturePath));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        TextureRegion region = new TextureRegion(texture, 0,0, width, height);
        Image splashImage = new Image( region);
        splashImage.setScaling(Scaling.stretch);
        splashImage.setAlign((Align.bottom | Align.left));
        splashImage.setSize(width, height);
        splashImage.setColor(1.0f, 1.0f, 1.0f, 0.0f);

        this.setUnitImage(splashImage);
    }

    public Image getUnitImage() {
        return unitImage;
    }

    public void setUnitImage(Image image) {
        this.unitImage = image;
        this.battleScreen.stage.addActor(this.unitImage);
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
}

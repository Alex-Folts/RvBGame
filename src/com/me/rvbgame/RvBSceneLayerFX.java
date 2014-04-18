package com.me.rvbgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Scaling;

public class RvBSceneLayerFX extends Group {
    public Image shineLeftSlot1;
    public Image shineLeftSlot2;
    public Image shineLeftSlot3;
    public Image shineLeftSlot4;
    public Image shineLeftSlot5;

    public Image shineRightSlot1;
    public Image shineRightSlot2;
    public Image shineRightSlot3;
    public Image shineRightSlot4;
    public Image shineRightSlot5;

    public Image shineRightTower;
    public Image shineLeftTower;

    public void applyShine(RvBPlayer playerLeft, RvBPlayer playerRight){
        shineLeftSlot1 = applyTexture("data/chosen_left.png",playerLeft.slot1);
        shineLeftSlot2 = applyTexture("data/chosen_left.png",playerLeft.slot2);
        shineLeftSlot3 = applyTexture("data/chosen_left.png",playerLeft.slot3);
        shineLeftSlot4 = applyTexture("data/chosen_left.png",playerLeft.slot4);
        shineLeftSlot5 = applyTexture("data/chosen_left.png",playerLeft.slot5);
        shineLeftTower = applyTexture("data/shine.png",playerLeft.tower);

        shineRightSlot1 = applyTexture("data/chose.png",playerRight.slot1);
        shineRightSlot2 = applyTexture("data/chose.png",playerRight.slot2);
        shineRightSlot3 = applyTexture("data/chose.png",playerRight.slot3);
        shineRightSlot4 = applyTexture("data/chose.png",playerRight.slot4);
        shineRightSlot5 = applyTexture("data/chose.png",playerRight.slot5);
        shineRightTower = applyTexture("data/shine.png",playerRight.tower);

        this.addActor(shineLeftSlot1);
        this.addActor(shineLeftSlot2);
        this.addActor(shineLeftSlot3);
        this.addActor(shineLeftSlot4);
        this.addActor(shineLeftSlot5);
        this.addActor(shineLeftTower);

        this.addActor(shineRightSlot1);
        this.addActor(shineRightSlot2);
        this.addActor(shineRightSlot3);
        this.addActor(shineRightSlot4);
        this.addActor(shineRightSlot5);
        this.addActor(shineRightTower);

        allInvisiblie();
    }

    public void allInvisiblie() {
        if (shineLeftSlot1 == null)
            return;
        shineLeftSlot1.setVisible(false);
        shineLeftSlot2.setVisible(false);
        shineLeftSlot3.setVisible(false);
        shineLeftSlot4.setVisible(false);
        shineLeftSlot5.setVisible(false);
        shineLeftTower.setVisible(false);

        shineLeftSlot1.setColor(StatsHelper.COLOR_DARK_GREEN);
        shineLeftSlot2.setColor(StatsHelper.COLOR_DARK_GREEN);
        shineLeftSlot3.setColor(StatsHelper.COLOR_DARK_GREEN);
        shineLeftSlot4.setColor(StatsHelper.COLOR_DARK_GREEN);
        shineLeftSlot5.setColor(StatsHelper.COLOR_DARK_GREEN);
        shineLeftTower.setColor(StatsHelper.COLOR_DARK_GREEN);

        shineRightSlot1.setVisible(false);
        shineRightSlot2.setVisible(false);
        shineRightSlot3.setVisible(false);
        shineRightSlot4.setVisible(false);
        shineRightSlot5.setVisible(false);
        shineRightTower.setVisible(false);
    }

    public void halfInvisiblie(boolean isRight) {
        if (!isRight){
            if (shineLeftSlot1 == null)
                return;
            shineLeftSlot1.setVisible(false);
            shineLeftSlot2.setVisible(false);
            shineLeftSlot3.setVisible(false);
            shineLeftSlot4.setVisible(false);
            shineLeftSlot5.setVisible(false);
            shineLeftTower.setVisible(false);
        }else{
            if (shineRightSlot1 == null)
                return;
            shineRightSlot1.setVisible(false);
            shineRightSlot2.setVisible(false);
            shineRightSlot3.setVisible(false);
            shineRightSlot4.setVisible(false);
            shineRightSlot5.setVisible(false);
            shineRightTower.setVisible(false);
        }

    }


    public Image applyTexture(String path, RvBUnit unit){

        Texture avaTexture = new Texture(Gdx.files.internal(path));
        avaTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        TextureRegion region = new TextureRegion(avaTexture, 0, 0, unit.getAvaImageWidth(), unit.getAvaImageHeight());
        Image avaImage = new Image(region);
        avaImage.setScaling(Scaling.stretch);
        avaImage.setAlign((Align.bottom | Align.left));
        avaImage.setSize(unit.avaSize.x, unit.avaSize.y);
        avaImage.setPosition(unit.avaImage.getX(),unit.avaImage.getY());
        return avaImage;
    }


    public void shineUnit(int slotNum){//,boolean isRight) {
        Gdx.app.log("TOW","slotNum "+slotNum);
        switch (slotNum){
            case 1:
                shineLeftSlot1.setVisible(true);
                break;
            case 2:
                shineLeftSlot2.setVisible(true);
                break;
            case 3:
                shineLeftSlot3.setVisible(true);
                break;
            case 4:
                shineLeftSlot4.setVisible(true);
                break;
            case 5:
                shineLeftSlot5.setVisible(true);
                break;
            case 6:
                shineLeftTower.setVisible(true);
                break;
            default:
                break;
        }
    }
}

package com.me.rvbgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Scaling;

public class RvBRadialMenu extends Group{

    private RvBUnit unit;

    private Image upperChoiceImage;
    private Image upperLeftChoiceImage;
    private Image upperRightChoiceImage;
    private Image bottomChoiceImage;

    private Image bottomLeftChoiceImage;
    private Image bottomRightChoiceImage;
    private Image leftChoiceImage;
    private Image rightChoiceImage;

    public RvBRadialMenu(RvBUnit unit){
        this.unit = unit;
        if (unit.unitType == UnitType.UNIT_TYPE_TOWER)
            return;

        Actor background = new Actor() {
            @Override
            public void draw(Batch batch, float parentAlpha) {
                super.draw(batch, parentAlpha);

                batch.end();
                ShapeRenderer shapeRenderer = new ShapeRenderer();
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(StatsHelper.COLOR_BLACK_ALPHA_0_5);
                shapeRenderer.rect(0, 0, 300, 20);
                shapeRenderer.end();
                batch.begin();
            }
        };
//        this.addActor(background);

        //default actions:
        Image avaImage = applyTexture("data/radial_menu_files/rm_att.png");
        avaImage.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                //This method is overrided with:
                Gdx.app.log("RM", "clicked: attack");
                applyAction(ActionType.ACTION_TYPE_ATTACK);
            }
        });
        this.upperChoiceImage = avaImage;
        this.addActor(upperChoiceImage);

        avaImage = applyTexture("data/radial_menu_files/rm_def.png");
        avaImage.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                //This method is overrided with:
                Gdx.app.log("RM", "clicked: defence");
                applyAction(ActionType.ACTION_TYPE_DEFEND);
            }
        });
        this.upperRightChoiceImage = avaImage;
        this.addActor(upperRightChoiceImage);

        avaImage = applyTexture("data/radial_menu_files/rm_wait.png");
        avaImage.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                //This method is overrided with:
                Gdx.app.log("RM", "clicked: wait");
                applyAction(ActionType.ACTION_TYPE_WAIT);
            }
        });
        this.upperLeftChoiceImage = avaImage;
        this.addActor(upperLeftChoiceImage);

        avaImage = applyTexture("data/radial_menu_files/rm_cancel.png");
        avaImage.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                //This method is overrided with:
                Gdx.app.log("RM", "clicked: cancel");
                hide();
            }
        });
        this.bottomChoiceImage = avaImage;
        this.addActor(bottomChoiceImage);

        this.changeCoords(unit);
        this.setVisible(false);
    }

    private void applyAction(ActionType actionType) {
        unit.actionType = actionType;
        if (actionType != ActionType.ACTION_TYPE_ATTACK && actionType!=ActionType.ACTION_TYPE_FREEZE){
            unit.setbCanOperate(false);
            this.hide();
        }
    }

    public void reveal(){
        if (unit.unitType == UnitType.UNIT_TYPE_TOWER)
            return;
        this.setVisible(true);
    }

    public void hide(){
        this.setVisible(false);
    }

    public void changeCoords(RvBUnit unit){

        this.unit = unit;
        Gdx.app.log("RM",""+StatsHelper.whatUnit(unit.unitType));

        if (upperChoiceImage != null){
            upperChoiceImage.setPosition(
                    unit.avaImage.getX(),
                    unit.avaImage.getY() + unit.avaSize.y
            );
        }
        if (upperLeftChoiceImage != null){
            upperLeftChoiceImage.setPosition(
                    unit.avaImage.getX() - unit.avaSize.x, // - StatsHelper.SPACING,
                    unit.avaImage.getY() + unit.avaSize.y / 2
            );
        }
        if (upperRightChoiceImage != null){
            upperRightChoiceImage.setPosition(
                    unit.avaImage.getX() + unit.avaSize.x,//unit.avaImage.getHeight() + StatsHelper.SPACING,
                    unit.avaImage.getY() + unit.avaSize.y / 2
            );
        }
        if (bottomChoiceImage != null){
            bottomChoiceImage.setPosition(
                    unit.avaImage.getX(),
                    unit.avaImage.getY() - unit.avaSize.y
            );

        }

        if (bottomRightChoiceImage != null){
            bottomRightChoiceImage.setPosition(
                    unit.avaImage.getX() + unit.avaSize.x,
                    unit.avaImage.getY() - unit.avaSize.y / 2
            );
            if (unit.unitType == UnitType.UNIT_TYPE_SPECIAL)
                bottomRightChoiceImage.setVisible(true);
            else
                bottomRightChoiceImage.setVisible(false);
        }else if (unit.unitType == UnitType.UNIT_TYPE_SPECIAL){
            Image avaImage = applyTexture("data/radial_menu_files/rm_freeze.png");
            avaImage.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);

                    //This method is overrided with:
                    Gdx.app.log("RM", "clicked: freeze");
                    applyAction(ActionType.ACTION_TYPE_FREEZE);
                }
            });
            this.bottomRightChoiceImage = avaImage;
            this.addActor(bottomRightChoiceImage);
            bottomRightChoiceImage.setPosition(
                    unit.avaImage.getX() + unit.avaSize.x,
                    unit.avaImage.getY() - unit.avaSize.y / 2
            );

        }

        if (bottomLeftChoiceImage != null){
            bottomLeftChoiceImage.setPosition(
                    unit.avaImage.getX() - unit.avaSize.x,
                    unit.avaImage.getY() - unit.avaSize.y / 2
            );
            if (unit.unitType == UnitType.UNIT_TYPE_RANGED)
                bottomLeftChoiceImage.setVisible(true);
            else
                bottomLeftChoiceImage.setVisible(false);
        }else if (unit.unitType == UnitType.UNIT_TYPE_RANGED){
            Image avaImage = applyTexture("data/radial_menu_files/rm_aim.png");
            avaImage.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    //This method is overrided with:
                    Gdx.app.log("RM", "clicked: aim");
                    applyAction(ActionType.ACTION_TYPE_AIM);
                }
            });
            this.bottomLeftChoiceImage = avaImage;
            this.addActor(bottomLeftChoiceImage);
            bottomLeftChoiceImage.setPosition(
                    unit.avaImage.getX() - unit.avaSize.x,
                    unit.avaImage.getY() - unit.avaSize.y / 2
            );
        }


    }

    public Image applyTexture(String path){

        Texture avaTexture = new Texture(Gdx.files.internal(path));
        avaTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        TextureRegion region = new TextureRegion(avaTexture, 0, 0, unit.getAvaImageWidth(), unit.getAvaImageHeight());
        Image avaImage = new Image(region);
        avaImage.setScaling(Scaling.stretch);
        avaImage.setAlign((Align.bottom | Align.left));
        avaImage.setSize(unit.getAvaSize().x, unit.getAvaSize().y);

        return avaImage;
    }
}

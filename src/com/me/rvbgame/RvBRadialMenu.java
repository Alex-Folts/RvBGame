package com.me.rvbgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
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

    private Image additionalUpperImage;
    private Image additionalUpperLeftImage;
    private Image additionalLeftImage;
    private Image additionalBottomImage;

    Vector2 itemSize = new Vector2(48, 48);

    boolean additionalVisibe = false;

    public RvBRadialMenu(RvBUnit unit){
        this.unit = unit;

        //default actions:
        Image avaImage = applyTexture("data/radial_menu_files/rm_att.png");
        avaImage.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                //This method is overrided with:
                Gdx.app.log("RM", "clicked: attack");
                upperChoiceImage.setColor(StatsHelper.COLOR_DARK_RED);
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
        if (unit.unitType != UnitType.UNIT_TYPE_TOWER)
            this.changeCoords(unit);
        else
            this.changeCoordsToTower();
        this.setVisible(false);
    }

    private void applyAction(ActionType actionType) {
        unit.actionType = actionType;
        Gdx.app.log("TOW","at clicked "+actionType);
        if (actionType != ActionType.ACTION_TYPE_ATTACK
                && actionType!=ActionType.ACTION_TYPE_FREEZE
                && actionType!=ActionType.ACTION_TYPE_HEAL){
            unit.battleScreen.world.applyActionOnSelf(unit);

            if (unit.getActionPoints() == 0){
                unit.setbCanOperate(false);
                this.hide();
                this.setDefaultColors();
                if (!RvBWorld.getCurrentTurnPlayer().checkIfCanMove()){
                    RvBWorld.getCurrentTurnPlayer().endTurn();
                }
            }
        }
    }

    public void reveal(){
        this.setVisible(true);
    }

    public void hide(){
        this.setVisible(false);
    }

    public void changeCoords(RvBUnit unit){

        this.unit = unit;
        Gdx.app.log("RM","what "+StatsHelper.whatUnit(unit.unitType));
        if (unit.unitType == UnitType.UNIT_TYPE_TOWER){
            changeCoordsToTower();
            return;
        }else{
            if (additionalBottomImage!=null) additionalBottomImage.setVisible(false);
            if (additionalLeftImage!=null) additionalLeftImage.setVisible(false);
            if (additionalUpperLeftImage!=null) additionalUpperLeftImage.setVisible(false);
            if (additionalUpperImage!=null) additionalUpperImage.setVisible(false);
            additionalVisibe = false;
            if  (upperChoiceImage!=null) upperChoiceImage.setVisible(true);
            if  (upperLeftChoiceImage!=null) upperLeftChoiceImage.setVisible(true);
            if  (upperRightChoiceImage!=null) upperRightChoiceImage.setVisible(true);
        }

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
                    bottomRightChoiceImage.setColor(StatsHelper.COLOR_DARK_RED);
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
            if (unit.unitType == UnitType.UNIT_TYPE_RANGED || unit.unitType == UnitType.UNIT_TYPE_RANGED_MASS)
                bottomLeftChoiceImage.setVisible(true);
            else
                bottomLeftChoiceImage.setVisible(false);
        }else if (unit.unitType == UnitType.UNIT_TYPE_RANGED || unit.unitType == UnitType.UNIT_TYPE_RANGED_MASS){

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

    //change coords for Tower
    private void changeCoordsToTower() {
        if (additionalBottomImage == null){
            additionalBottomImage = applyTexture("data/radial_menu_files/rm_more.png");
            additionalBottomImage.addListener( new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    Gdx.app.log("RM", "clicked: more");
                    applyActionMore();
                }
            } );
            this.addActor(additionalBottomImage);
            additionalBottomImage.setPosition(
                    unit.avaImage.getX() + 3*unit.avaSize.x / 5,
                    unit.avaImage.getY() + 2
            );
        }else
            additionalBottomImage.setVisible(true);

        if (upperChoiceImage != null){
            upperChoiceImage.setPosition(
                    unit.avaImage.getX() + itemSize.y/2,
                    (float) (unit.avaImage.getY() + itemSize.y * 2.5)// unit.avaSize.y
            );
        }
        if (upperLeftChoiceImage != null){
            upperLeftChoiceImage.setPosition(
                    unit.avaImage.getX() + 3*unit.avaSize.x / 5, // - StatsHelper.SPACING,
                    unit.avaImage.getY() + itemSize.y*2
            );
        }
        if (upperRightChoiceImage != null){
            upperRightChoiceImage.setPosition(
                    unit.avaImage.getX() + 3*unit.avaSize.x / 4,//unit.avaImage.getHeight() + StatsHelper.SPACING,
                    unit.avaImage.getY() + itemSize.y
            );
        }
        if (bottomChoiceImage != null){
            bottomChoiceImage.setPosition(
                    unit.avaImage.getX() + itemSize.y/2,
                    unit.avaImage.getY()
            );

        }
        if (bottomLeftChoiceImage!=null)
            bottomLeftChoiceImage.setVisible(false);
        if (bottomRightChoiceImage!=null)
            bottomRightChoiceImage.setVisible(false);
    }

    private void applyActionMore() {

        if (!additionalVisibe){
            if (additionalUpperImage == null){
                additionalUpperImage = applyTexture("data/radial_menu_files/rm_heal.png");
                additionalUpperImage.setPosition(upperChoiceImage.getX(), upperChoiceImage.getY());
                additionalUpperImage.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        applyAction(ActionType.ACTION_TYPE_HEAL);
                    }
                });
                upperChoiceImage.setVisible(false);
                this.addActor(additionalUpperImage);
            } else {
                upperChoiceImage.setVisible(false);
                additionalUpperImage.setVisible(true);
            }
            if (additionalUpperLeftImage == null){
                additionalUpperLeftImage = applyTexture("data/radial_menu_files/rm_aim.png");
                additionalUpperLeftImage.setPosition(upperLeftChoiceImage.getX(), upperLeftChoiceImage.getY());
                additionalUpperLeftImage.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        applyAction(ActionType.ACTION_TYPE_AIM);
                    }
                });
                upperLeftChoiceImage.setVisible(false);
                this.addActor(additionalUpperLeftImage);
            }
            else
            {
                upperLeftChoiceImage.setVisible(false);
                additionalUpperLeftImage.setVisible(true);
            }

            if (additionalLeftImage == null){
                additionalLeftImage = applyTexture("data/radial_menu_files/rm_inv.png");
                additionalLeftImage.setPosition(upperRightChoiceImage.getX(), upperRightChoiceImage.getY());
                additionalLeftImage.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        RvBWorld.getCurrentTurnPlayer().inventoryVisible(true);
                        hide();
                    }
                });
                upperRightChoiceImage.setVisible(false);
                this.addActor(additionalLeftImage);
            }
            else
            {
                upperRightChoiceImage.setVisible(false);
                additionalLeftImage.setVisible(true);
            }

            upperRightChoiceImage.setVisible(false);
//            additionalVisibe = true;
        }
        else
        {
            upperRightChoiceImage.setVisible(true);
            upperLeftChoiceImage.setVisible(true);
            upperChoiceImage.setVisible(true);
            additionalUpperImage.setVisible(false);
            additionalUpperLeftImage.setVisible(false);
            additionalLeftImage.setVisible(false);
//            additionalVisibe = false;
        }
        additionalVisibe = !additionalVisibe;
    }

    public Image applyTexture(String path){

        Texture avaTexture = new Texture(Gdx.files.internal(path));
        avaTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        TextureRegion region = new TextureRegion(avaTexture, 0, 0, unit.getAvaImageWidth(), unit.getAvaImageHeight());
        Image avaImage = new Image(region);
        avaImage.setScaling(Scaling.stretch);
        avaImage.setAlign((Align.bottom | Align.left));
        avaImage.setSize(itemSize.x, itemSize.y);

        return avaImage;
    }

    public void setDefaultColors() {
        if (upperChoiceImage != null)
            upperChoiceImage.setColor(Color.WHITE);
        if (bottomRightChoiceImage != null)
            bottomRightChoiceImage.setColor(Color.WHITE);
    }
}

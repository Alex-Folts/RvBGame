package com.me.rvbgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.me.rvbgame.RvbGdxGame;
import com.me.rvbgame.StatsHelper;
import com.me.rvbgame.UnitType;
import com.me.rvbgame.units.UnitDefender;
import java.util.ArrayList;

public class SPSettingsScreen extends GameScreen {

    ArrayList<UnitType> selectedUnitsList = new ArrayList<UnitType>();
    Skin skin;

    int maxDefNum = StatsHelper.MAX_AVAILABLE_DEF;
    int maxAttNum = StatsHelper.MAX_AVAILABLE_ATT;
    int maxRanNum = StatsHelper.MAX_AVAILABLE_RAN;
    int maxMRaNum = StatsHelper.MAX_AVAILABLE_MAS;
    int maxSpeNum = StatsHelper.MAX_AVAILABLE_SPE;

    int maxUnitsToChoose = StatsHelper.MAX_AVAILABLE_UNITS;
    int imageSize;
    UnitType unitTypeCurrent;

    Label infoLabel;

    Label defLeftLabel;
    Label atackLeftLabel;
    Label rangedLeftLabel;
    Label massLeftLabel;
    Label stanLeftLabel;

    Label defChosenLabel;
    Label atackChosenLabel;
    Label rangedChosenLabel;
    Label massChosenLabel;
    Label stanChosenLabel;

    Image defSelectedImage;
    Image attSelectedImage;
    Image ranSelectedImage;
    Image masSelectedImage;
    Image speSelectedImage;

    Label stats1Label;
    Label stats2Label;
    Label stats3Label;
    
    Label factionCaptionLabel;

    TextButton startBattleButton;
    TextButton backButton;
    
    SelectBox factionSelectBox;

	public SPSettingsScreen(RvbGdxGame game) {
		super(game);
	}

	@Override
	public void resize(final int width, int height) {
		super.resize(width, height);
		
		Table table = new Table(getSkin());
        table.setSize(width, height);
        
        stats1Label = new Label("HP:0", getSkin());
        stats1Label.setFontScale(1.0f);
        stats2Label = new Label("Armor:0", getSkin());
        stats2Label.setFontScale(1.0f);
        stats3Label = new Label("Attack:0", getSkin());
        stats3Label.setFontScale(1.0f);
        
        factionCaptionLabel = new Label("Faction", getSkin());
        factionCaptionLabel.setFontScale(1.0f);

        startBattleButton = new TextButton("Start battle", getSkin());
        startBattleButton.addListener( new ClickListener() {             
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	Gdx.app.log("RvB", "SPSettingsScreen:clicked");
                mGame.setScreen(new BattleScreen(mGame));
            };
        });
        backButton = new TextButton("Back", getSkin());
        backButton.addListener( new ClickListener() {             
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	Gdx.app.log("RvB", "SPSettingsScreen:clicked");
                mGame.setScreen(new MainMenuScreen(mGame));
            };
        });
        
        factionSelectBox = new SelectBox(new String[] {"Red", "Blue"}, getSkin());

        table.top();
        table.add(factionCaptionLabel);
        table.add(factionSelectBox);
        table.row();
        table.add(stats1Label);
        table.row();
        table.add(stats2Label);
        table.row();
        table.add(stats3Label);
        table.left();
        table.row();
        table.add(backButton);
        table.add(startBattleButton);
        
//        stage.addActor(table);
////////////////////////////////
        skin = new Skin();
        skin.add("default", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        skin.add("badlogic", new Texture("data/ap_icon.png"));

        skin.add("defender", new Texture("data/defender.png"));
        skin.add("atacker_melee", new Texture("data/atacker_melee.png"));
        skin.add("atacker_ranged", new Texture("data/atacker_ranged.png"));
        skin.add("mass_ranged", new Texture("data/mass_ranged.png"));
        skin.add("special_stan", new Texture("data/special_stan.png"));

        infoLabel = new Label(String.format("Units left to choose:\n%d",StatsHelper.MAX_AVAILABLE_UNITS),skin);
        stage.addActor(infoLabel);

        imageSize = height/6;

        defLeftLabel = new Label(String.format("x%d",maxDefNum),skin);
        atackLeftLabel = new Label(String.format("x%d",maxAttNum),skin);
        rangedLeftLabel = new Label(String.format("x%d",maxRanNum),skin);
        massLeftLabel = new Label(String.format("x%d",maxMRaNum),skin);
        stanLeftLabel = new Label(String.format("x%d",maxSpeNum),skin);

        Image sourceImage = new Image(skin, "defender");
        sourceImage.setBounds(10, 10, imageSize, imageSize);
        stage.addActor(sourceImage);

        defLeftLabel.setPosition(sourceImage.getX()+imageSize, sourceImage.getY());
        stage.addActor(defLeftLabel);

        final Image dragImage = new Image(skin,"defender");
        dragImage.setBounds(sourceImage.getX(), sourceImage.getY(), imageSize, imageSize);
        dragImage.setVisible(false);
        dragImage.setColor(Color.GREEN);
        stage.addActor(dragImage);

        DragAndDrop.Source sourceD = new DragAndDrop.Source(sourceImage) {
            public DragAndDrop.Payload dragStart (InputEvent event, float x, float y, int pointer) {
                DragAndDrop.Payload payload = new DragAndDrop.Payload();
                payload.setObject("defender!");
                dragImage.setDrawable(skin, "defender");
                dragImage.setVisible(true);
                payload.setDragActor(dragImage); //(new Label("Some payload!", skin));
                unitTypeCurrent = UnitType.UNIT_TYPE_DEFENDER;
                Label validLabel = new Label("Some valid!", skin);
                validLabel.setColor(0, 1, 0, 1);
                payload.setValidDragActor(validLabel);

                Label invalidLabel = new Label("Some invalid!", skin);
                invalidLabel.setColor(1, 0, 0, 1);
                payload.setInvalidDragActor(invalidLabel);

                return payload;
            }
        };

        Image sourceImage2 = new Image(skin, "atacker_melee");
        sourceImage2.setBounds(10, 20 + imageSize, imageSize, imageSize);
        stage.addActor(sourceImage2);

        atackLeftLabel.setPosition(sourceImage2.getX() + imageSize, sourceImage2.getY());
        stage.addActor(atackLeftLabel);

        DragAndDrop.Source sourceA = new DragAndDrop.Source(sourceImage2) {
            public DragAndDrop.Payload dragStart (InputEvent event, float x, float y, int pointer) {
                DragAndDrop.Payload payload = new DragAndDrop.Payload();
                payload.setObject("Atacker!");
                dragImage.setDrawable(skin,"atacker_melee");
                dragImage.setVisible(true);
                payload.setDragActor(dragImage);

                unitTypeCurrent = UnitType.UNIT_TYPE_MELEE;

                Label validLabel = new Label("Some valid!", skin);
                validLabel.setColor(0, 1, 0, 1);
                payload.setValidDragActor(validLabel);

                Label invalidLabel = new Label("Some invalid!", skin);
                invalidLabel.setColor(1, 0, 0, 1);
                payload.setInvalidDragActor(invalidLabel);

                return payload;
            }
        };

        Image sourceImage3 = new Image(skin, "atacker_ranged");
        sourceImage3.setBounds(10, 30+imageSize*2, imageSize, imageSize);
        stage.addActor(sourceImage3);

        rangedLeftLabel.setPosition(sourceImage3.getX() + imageSize, sourceImage3.getY());
        stage.addActor(rangedLeftLabel);

        DragAndDrop.Source sourceR = new DragAndDrop.Source(sourceImage3) {
            public DragAndDrop.Payload dragStart (InputEvent event, float x, float y, int pointer) {
                DragAndDrop.Payload payload = new DragAndDrop.Payload();
                payload.setObject("Ranged!");
                dragImage.setDrawable(skin,"atacker_ranged");
                dragImage.setVisible(true);
                payload.setDragActor(dragImage);

                unitTypeCurrent = UnitType.UNIT_TYPE_RANGED;

                Label validLabel = new Label("Some valid!", skin);
                validLabel.setColor(0, 1, 0, 1);
                payload.setValidDragActor(validLabel);

                Label invalidLabel = new Label("Some invalid!", skin);
                invalidLabel.setColor(1, 0, 0, 1);
                payload.setInvalidDragActor(invalidLabel);

                return payload;
            }
        };

        Image sourceImage4 = new Image(skin, "mass_ranged");
        sourceImage4.setBounds(10, 40+imageSize*3, imageSize, imageSize);
        stage.addActor(sourceImage4);

        massLeftLabel.setPosition(sourceImage4.getX() + imageSize, sourceImage4.getY());
        stage.addActor(massLeftLabel);

        DragAndDrop.Source sourceMR = new DragAndDrop.Source(sourceImage4) {
            public DragAndDrop.Payload dragStart (InputEvent event, float x, float y, int pointer) {
                DragAndDrop.Payload payload = new DragAndDrop.Payload();
                payload.setObject("mass_ranged!");
                dragImage.setDrawable(skin,"mass_ranged");
                dragImage.setVisible(true);
                payload.setDragActor(dragImage);

                unitTypeCurrent = UnitType.UNIT_TYPE_RANGED_MASS;

                Label validLabel = new Label("Some valid!", skin);
                validLabel.setColor(0, 1, 0, 1);
                payload.setValidDragActor(validLabel);

                Label invalidLabel = new Label("Some invalid!", skin);
                invalidLabel.setColor(1, 0, 0, 1);
                payload.setInvalidDragActor(invalidLabel);

                return payload;
            }
        };

        Image sourceImage5 = new Image(skin, "special_stan");
        sourceImage5.setBounds(10, 50+imageSize*4, imageSize, imageSize);
        stage.addActor(sourceImage5);

        stanLeftLabel.setPosition(sourceImage5.getX()+imageSize, sourceImage5.getY());
        stage.addActor(stanLeftLabel);

        DragAndDrop.Source sourceS = new DragAndDrop.Source(sourceImage5) {
            public DragAndDrop.Payload dragStart (InputEvent event, float x, float y, int pointer) {
                DragAndDrop.Payload payload = new DragAndDrop.Payload();
                payload.setObject("special_stan!");
                dragImage.setDrawable(skin,"special_stan");
                dragImage.setVisible(true);
                payload.setDragActor(dragImage);

                unitTypeCurrent = UnitType.UNIT_TYPE_SPECIAL;

                Label validLabel = new Label("Some valid!", skin);
                validLabel.setColor(0, 1, 0, 1);
                payload.setValidDragActor(validLabel);

                Label invalidLabel = new Label("Some invalid!", skin);
                invalidLabel.setColor(1, 0, 0, 1);
                payload.setInvalidDragActor(invalidLabel);

                return payload;
            }
        };

        Image validTargetImage = new Image(skin, "badlogic");
        validTargetImage.setBounds(200, 50, 100, 100);
        stage.addActor(validTargetImage);

        Image invalidTargetImage = new Image(skin, "badlogic");
//        invalidTargetImage.setBounds(200, 200, 100, 100);
//        stage.addActor(invalidTargetImage);
        infoLabel.setPosition(200,200);

        DragAndDrop dragAndDrop = new DragAndDrop();

        dragAndDrop.addSource(sourceD);
        dragAndDrop.addSource(sourceA);
        dragAndDrop.addSource(sourceR);
        dragAndDrop.addSource(sourceMR);
        dragAndDrop.addSource(sourceS);

        dragAndDrop.addTarget(new DragAndDrop.Target(validTargetImage) {
            public boolean drag (DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                getActor().setColor(Color.GREEN);
                return true;
            }

            public void reset (DragAndDrop.Source source, DragAndDrop.Payload payload) {
                getActor().setColor(Color.WHITE);
            }

            public void drop (DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                if(selectedUnitsList.size() < maxUnitsToChoose){
                    updateLabels();
                    selectedUnitsList.add(unitTypeCurrent);
                    Gdx.app.log("DAD", "Accepted: " + payload.getObject() );
                }else
                    Gdx.app.log("DAD", "Declined: " + payload.getObject() );
//                Gdx.app.log("DAD", "selectedUnitsList: " + selectedUnitsList);
                Gdx.app.log("DAD","Size " + selectedUnitsList.size());
            }
        });

        dragAndDrop.addTarget(new DragAndDrop.Target(invalidTargetImage) {
            public boolean drag (DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                getActor().setColor(Color.RED);
                return false;
            }

            public void reset (DragAndDrop.Source source, DragAndDrop.Payload payload) {
                getActor().setColor(Color.WHITE);
            }

            public void drop (DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                Gdx.app.log("DAD", "Deleted: " + payload.getObject() + " " + x + ", " + y);
//                imageArrayList.remove(payload.getDragActor());
//                for (Actor actor: imageArrayList){
//                    Gdx.app.log("DAD", "Deleted: " + actor);
//                }
           }
        });


    }

    private boolean updateLabels() {
        boolean updated = false;
        infoLabel.setText(String.format("Units left to choose:\n%d",StatsHelper.MAX_AVAILABLE_UNITS - selectedUnitsList.size()-1 ));
        switch (unitTypeCurrent) {
            case UNIT_TYPE_DEFENDER:
                maxDefNum--;
                if (maxDefNum>=0){
                    defLeftLabel.setText(String.format("x%d", maxDefNum));
                    if (defSelectedImage == null){
                        defSelectedImage = new Image(skin,"defender");
                        defSelectedImage.setBounds(
                                stage.getWidth() - imageSize,
                                stage.getHeight() - imageSize,
                                imageSize,
                                imageSize
                                );
                        stage.addActor(defSelectedImage);
                        defChosenLabel = new Label(String.format("x%d", StatsHelper.MAX_AVAILABLE_DEF - maxDefNum),skin);
                        defChosenLabel.setPosition(defSelectedImage.getX() - defChosenLabel.getPrefWidth(), defSelectedImage.getY());
                        stage.addActor(defChosenLabel);
                    }
                    else
                    {
                        defChosenLabel.setText(String.format("x%d", StatsHelper.MAX_AVAILABLE_DEF - maxDefNum));
                    }
                    updated = true;
                }
//                Gdx.app.log("DAD", "S: " + s + "mdn " + maxDefNum);
                break;

            case UNIT_TYPE_MELEE:
                maxAttNum--;
                if (maxAttNum>=0){
                    atackLeftLabel.setText(String.format("x%d", maxAttNum));
                    if (attSelectedImage == null){
                        attSelectedImage = new Image(skin,"atacker_melee");
                        attSelectedImage.setBounds(
                                stage.getWidth() - imageSize,
                                stage.getHeight() - imageSize * 2,
                                imageSize,
                                imageSize
                        );
                        stage.addActor(attSelectedImage);
                        atackChosenLabel = new Label(String.format("x%d", StatsHelper.MAX_AVAILABLE_ATT - maxAttNum),skin);
                        atackChosenLabel.setPosition(attSelectedImage.getX() - atackChosenLabel.getPrefWidth(), attSelectedImage.getY());
                        stage.addActor(atackChosenLabel);
                    }
                    else
                    {
                        atackChosenLabel.setText(String.format("x%d", StatsHelper.MAX_AVAILABLE_ATT - maxAttNum));
                    }
                    updated = true;
                }
                break;

            case UNIT_TYPE_SPECIAL:
                maxSpeNum--;
                if (maxSpeNum>=0){
                    stanLeftLabel.setText(String.format("x%d", maxSpeNum));
                    if (speSelectedImage == null){
                        speSelectedImage = new Image(skin,"special_stan");
                        speSelectedImage.setBounds(
                                stage.getWidth() - imageSize,
                                stage.getHeight() - imageSize * 3,
                                imageSize,
                                imageSize
                        );
                        stage.addActor(speSelectedImage);
                        stanChosenLabel = new Label(String.format("x%d", StatsHelper.MAX_AVAILABLE_SPE - maxSpeNum),skin);
                        stanChosenLabel.setPosition(speSelectedImage.getX() - stanChosenLabel.getPrefWidth(), speSelectedImage.getY());
                        stage.addActor(stanChosenLabel);
                    }
                    else
                    {
                        stanChosenLabel.setText(String.format("x%d", StatsHelper.MAX_AVAILABLE_SPE - maxSpeNum));
                    }
                    updated = true;
                }
                break;

            case UNIT_TYPE_RANGED:
                maxRanNum--;
                if (maxRanNum>=0){
                    rangedLeftLabel.setText(String.format("x%d", maxRanNum));
                    if (ranSelectedImage == null){
                        ranSelectedImage = new Image(skin,"atacker_ranged");
                        ranSelectedImage.setBounds(
                                stage.getWidth() - imageSize,
                                stage.getHeight() - imageSize * 4,
                                imageSize,
                                imageSize
                        );
                        stage.addActor(ranSelectedImage);
                        rangedChosenLabel = new Label(String.format("x%d", StatsHelper.MAX_AVAILABLE_RAN - maxRanNum),skin);
                        rangedChosenLabel.setPosition(ranSelectedImage.getX() - rangedChosenLabel.getPrefWidth(), ranSelectedImage.getY());
                        stage.addActor(rangedChosenLabel);
                    }
                    else
                    {
                        rangedChosenLabel.setText(String.format("x%d", StatsHelper.MAX_AVAILABLE_RAN - maxRanNum));
                    }
                    updated = true;
                }
                break;

            case UNIT_TYPE_RANGED_MASS:
                maxMRaNum--;
                if (maxMRaNum>=0){
                    massLeftLabel.setText(String.format("x%d", maxMRaNum));
                    if (masSelectedImage == null){
                        masSelectedImage = new Image(skin,"mass_ranged");
                        masSelectedImage.setBounds(
                                stage.getWidth() - imageSize,
                                stage.getHeight() - imageSize * 5,
                                imageSize,
                                imageSize
                        );
                        stage.addActor(masSelectedImage);
                        massChosenLabel = new Label(String.format("x%d", StatsHelper.MAX_AVAILABLE_MAS - maxMRaNum),skin);
                        massChosenLabel.setPosition(masSelectedImage.getX() - massChosenLabel.getPrefWidth(), masSelectedImage.getY());
                        stage.addActor(massChosenLabel);
                    }
                    else
                    {
                        massChosenLabel.setText(String.format("x%d", StatsHelper.MAX_AVAILABLE_MAS - maxMRaNum));
                    }
                    updated = true;
                }
                break;
        }
        return updated;
    }

    @Override
	public void show() {
		super.show();
		
		Gdx.input.setInputProcessor(stage);
	}

    @Override
    public void render(float delta) {
        super.render(delta);
    }
}

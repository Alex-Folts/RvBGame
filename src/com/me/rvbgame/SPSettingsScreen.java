package com.me.rvbgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class SPSettingsScreen extends GameScreen {

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
	public void resize(int width, int height) {
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
        
        stage.addActor(table);
	}
	
	@Override
	public void show() {
		super.show();
		
		Gdx.input.setInputProcessor(stage);
	}
}

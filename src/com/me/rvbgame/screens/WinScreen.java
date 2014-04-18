package com.me.rvbgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.me.rvbgame.RvbGdxGame;

public class WinScreen extends GameScreen{

    Label congratLabel;
    TextButton toMainMenuButton;
    TextButton toSPSettingsButton;

	public Group sceneLayerBG = new Group();
    
    private String bgPath = "data/the_city_v7.png";
    static final Vector2 BG_IMAGE_SIZE = new Vector2(1024, 694);
    private Texture bgTexture;
    private Image bgImage;
    
    
    public WinScreen(RvbGdxGame game, boolean bWin) {
        super(game);

        if (bWin){
            congratLabel = new Label("You win! ^_^",this.getSkin());
        }
        else
        {
            congratLabel = new Label("You loose! ==\"",this.getSkin());
        }
        congratLabel.setAlignment(Align.center, Align.center);

        toMainMenuButton = new TextButton("Return to Main Menu",this.getSkin());
        toMainMenuButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mGame.setScreen(new MainMenuScreen(mGame));
            };
        });

        toSPSettingsButton = new TextButton("Play Again", this.getSkin());
        toSPSettingsButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mGame.setScreen(new SPSettingsScreen(mGame));
            };
        });

        stage.addActor(sceneLayerBG);
        stage.addActor(congratLabel);
        stage.addActor(toMainMenuButton);
        stage.addActor(toSPSettingsButton);

        Gdx.input.setInputProcessor(this.stage);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        TextureRegion region = new TextureRegion(bgTexture, 0, 0, 1024, 694);
		bgImage = new Image(region);
		bgImage.setScaling(Scaling.stretch);
		bgImage.setAlign((Align.bottom | Align.left));
//		bgImage.setSize(height * (BG_IMAGE_SIZE.x / BG_IMAGE_SIZE.y), height);
		bgImage.setSize(width, width * (BG_IMAGE_SIZE.y / BG_IMAGE_SIZE.x));
//        bgImage.setSize(width, height);
//		bgImage.setPosition((width * 0.5f) - (bgImage.getWidth() * 0.5f), 0);
		bgImage.setPosition(0, (height * 0.5f) - (bgImage.getHeight() * 0.5f));
//        bgImage.setPosition(0, 0);
//		bgImage.setColor(0.75f, 0.75f, 0.75f, 1);
//		bgImage.setSize(width, (float)width / (BG_IMAGE_SIZE.x / BG_IMAGE_SIZE.y));
		
		sceneLayerBG.addActor(bgImage);
        
        congratLabel.setBounds(0,height * 0.75f,width,10);
        toMainMenuButton.setBounds(width/2-100,height / 2,200,25);
        toSPSettingsButton.setBounds(width/2-100,height / 2 - 30,200,25);
    }
    
    @Override
    public void show() {
    	super.show();
    	
        bgTexture = new Texture(Gdx.files.internal(bgPath));
		bgTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
    }
    
    @Override
    public void dispose() {
    	super.dispose();
    	
    	bgTexture.dispose();
    }
}

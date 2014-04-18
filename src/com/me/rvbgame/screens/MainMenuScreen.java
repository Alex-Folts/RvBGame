package com.me.rvbgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.me.rvbgame.RvbGdxGame;
import com.me.rvbgame.StatsHelper;

public class MainMenuScreen extends GameScreen implements InputProcessor {

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Texture texture;
    private Sprite sprite;
    private RvbGdxGame mGame;
    
    private String bgPath = "data/the_city_v7.png";
    static final Vector2 BG_IMAGE_SIZE = new Vector2(1024, 694);
    private Texture bgTexture;
    private Image bgImage;

    @Override
    public void resize(int width, int height )
    {
        super.resize( width, height );
        StatsHelper.width = width;
        StatsHelper.height = height;
        float currX, currY;

        Table table = new Table( getSkin() );
        table.setWidth(width);
        table.setHeight(height);
//        table.setBackground("data/Andr_087098.png");
        stage.addActor( table );


        String full = "w ";
        currX = (width - StatsHelper.BUTTON_WIDTH) / 2;
        currY = (height * 0.85f) - StatsHelper.BUTTON_HEIGHT ;
//label "welcome"
        Label welcomeLabel = new Label( "Hi!", getSkin() );
        welcomeLabel.setBounds(currX, currY, StatsHelper.BUTTON_WIDTH, StatsHelper.BUTTON_HEIGHT / 2);
//btnBegin  //
        currY -= StatsHelper.BUTTON_HEIGHT + StatsHelper.BUTTON_SPACING;
        TextButton startGameButton = new TextButton("Start game", getSkin());
        startGameButton.setBounds(currX, currY, StatsHelper.BUTTON_WIDTH, StatsHelper.BUTTON_HEIGHT);
        startGameButton.
                addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                Gdx.app.log("BVG", "MainMenuScreen:clicked start");

                mGame.setScreen(new SPSettingsScreen(mGame));
            };

            ;
        });

//btnExit
        currY -= (StatsHelper.BUTTON_HEIGHT + StatsHelper.BUTTON_SPACING);
        TextButton exitGameButton = new TextButton("Escape game", getSkin());
        exitGameButton.setBounds(currX, currY, StatsHelper.BUTTON_WIDTH, StatsHelper.BUTTON_HEIGHT);
        exitGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                Gdx.app.log("BVG", "MainMenuScreen:clicked exit");

//                mGame.setScreen(new SplashScreen(mGame));
                Gdx.app.exit();
            };
        });
        full += "xl ";
        full += String.valueOf(currX);

        
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
        
        
        welcomeLabel.setText(full);
        Gdx.input.setInputProcessor(stage);
        stage.addActor( bgImage );
        stage.addActor( welcomeLabel );
        stage.addActor( startGameButton );
        stage.addActor( exitGameButton );


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
    
    public MainMenuScreen(RvbGdxGame game) {
        super(game);
        mGame = game;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }



}

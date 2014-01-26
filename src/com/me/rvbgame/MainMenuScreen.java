package com.me.rvbgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created with love.
 * User: Sasha
 * Package: com.me.rvbgame
 */


public class MainMenuScreen extends GameScreen implements InputProcessor {

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Texture texture;
    private Sprite sprite;
    private RvbGdxGame mGame;

    @Override
    public void resize(
            int width,
            int height )
    {
        super.resize( width, height );

        float currX, currY;

        float BUTTON_WIDTH = 300f;
        float BUTTON_HEIGHT = 50f;
        float BUTTON_SPACING = 10f;

        Table table = new Table( getSkin() );
        table.setWidth(width);
        table.setHeight(height);
//        table.setBackground("data/Andr_087098.png");
        stage.addActor( table );


        String full = "w ";
        currX = (width - BUTTON_WIDTH) / 2;
        currY = height - BUTTON_HEIGHT ;
//label "welcome"
        Label welcomeLabel = new Label( "Hi!", getSkin() );
        welcomeLabel.setBounds(currX, currY, BUTTON_WIDTH, BUTTON_HEIGHT / 2);
//btnBegin
        currY -= BUTTON_HEIGHT + BUTTON_SPACING;
        TextButton startGameButton = new TextButton("Start game", getSkin());
        startGameButton.setBounds(currX, currY, BUTTON_WIDTH, BUTTON_HEIGHT);
        startGameButton.
                addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("BVG", "MainMenuScreen:clicked exit");

                mGame.setScreen(new SplashScreen(mGame));
            };

            ;
        });

//btnExit
        currY -= (BUTTON_HEIGHT + BUTTON_SPACING);
        TextButton exitGameButton = new TextButton("Escape game", getSkin());
        exitGameButton.setBounds(currX, currY, BUTTON_WIDTH, BUTTON_HEIGHT);
        exitGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("BVG", "MainMenuScreen:clicked exit");

                mGame.setScreen(new SplashScreen(mGame));
            };
        });
        full += "xl ";
        full += String.valueOf(currX);

        welcomeLabel.setText(full);
        Gdx.input.setInputProcessor(stage);
        stage.addActor( welcomeLabel );
        stage.addActor( startGameButton );
        stage.addActor( exitGameButton );


    }

    public MainMenuScreen(RvbGdxGame game) {
        super(game);
        mGame = game;

//        float w = Gdx.graphics.getWidth();
//        float h = Gdx.graphics.getHeight();
//
//        camera = new OrthographicCamera(1, h/w);
//        batch = new SpriteBatch();
//
//        texture = new Texture(Gdx.files.internal("data/Andr_087098.png"));
//        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
//        TextureRegion region = new TextureRegion(texture, 0, 0, 512, 288);
//
//        sprite = new Sprite(region);
//        sprite.setSize(1.0f, 1.0f * sprite.getHeight() / sprite.getWidth());
//        sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
//        sprite.setPosition(-sprite.getWidth()/2, -sprite.getHeight()/2);
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

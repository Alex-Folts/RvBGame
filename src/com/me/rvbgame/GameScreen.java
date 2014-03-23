package com.me.rvbgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public abstract class GameScreen implements Screen {

	protected final RvbGdxGame mGame;
	protected final Stage stage;
	
    private BitmapFont font;
    private SpriteBatch batch;
    private Skin skin;
	
	public GameScreen(RvbGdxGame game) {
		this.mGame = game;
		this.stage = new Stage( 0, 0, true );
	}

	@Override
	public void render(float delta) {
		stage.act( delta );

        Gdx.gl.glClearColor( 0f, 0f, 0f, 1f );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );

        stage.draw();
	}

	@Override
	public void resize(int width, int height) {
//		FreeTypeFontGenerator fg;
//		if (skin != null)
//		{
//			skin.
//		}
		stage.setViewport( width, height, true );
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		stage.dispose();
		
        if( font != null ) font.dispose();
        if( batch != null ) batch.dispose();
        if( skin != null ) skin.dispose();
	}

	public BitmapFont getFont()
    {
        if( font == null ) {
            font = new BitmapFont();
        }
        return font;
    }

    public SpriteBatch getBatch()
    {
        if( batch == null ) {
            batch = new SpriteBatch();
        }
        return batch;
    }

    protected Skin getSkin()
    {
        if( skin == null ) {
        	skin = new Skin( Gdx.files.internal("data/uiskin.json"));
        }
        return skin;
    }
}

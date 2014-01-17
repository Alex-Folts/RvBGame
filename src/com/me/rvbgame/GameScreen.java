package com.me.rvbgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class GameScreen implements Screen {

	protected final RvbGdxGame mGame;
	protected final Stage stage;
	
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
	}

}

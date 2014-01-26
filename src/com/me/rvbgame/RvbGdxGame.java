package com.me.rvbgame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;

public class RvbGdxGame extends Game implements ApplicationListener {
	
	private SplashScreen splash;
	private MainMenuScreen menuScreen;

	@Override
	public void create() {		
//		splash = new SplashScreen(this);
//		setScreen(splash);
        menuScreen = new MainMenuScreen(this);
        setScreen(menuScreen);
	}

	@Override
	public void dispose() {

	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		super.render();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}

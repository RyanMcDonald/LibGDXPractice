package com.me.mycoolgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.me.mycoolgame.MyCoolGame;
import com.me.mycoolgame.util.Assets;

public class SplashScreen implements Screen {

	MyCoolGame game;
	SpriteBatch spriteBatch;
	
	public SplashScreen(MyCoolGame game, SpriteBatch spriteBatch) {
		this.game = game;
		this.spriteBatch = spriteBatch;
	}
	
	@Override
	public void render(float delta) {
		// Clear the screen
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		if (Assets.manager.update()) {
			// We're done loading, move to the game screen!
			game.setScreen(new GameScreen(game, spriteBatch));
		}
		
		// We still have assets to load, display the progress bar
		float progress = Assets.manager.getProgress();
		//System.out.println("Assets loading: " + progress);
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}

}

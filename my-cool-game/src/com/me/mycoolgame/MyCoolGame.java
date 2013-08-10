package com.me.mycoolgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.me.mycoolgame.screens.SplashScreen;
import com.me.mycoolgame.util.Assets;

public class MyCoolGame extends Game {

	private Assets assets;
	private SpriteBatch spriteBatch;

	@Override
	public void create() {
		assets = new Assets();
		spriteBatch = new SpriteBatch();

		setScreen(new SplashScreen(this, spriteBatch));
	}

}

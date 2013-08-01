package com.me.mycoolgame;

import com.badlogic.gdx.Game;
import com.me.mycoolgame.screens.GameScreen;

public class MyCoolGame extends Game {

	@Override
	public void create() {
		setScreen(new GameScreen(this));
	}
	
}

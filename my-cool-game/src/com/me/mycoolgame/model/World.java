package com.me.mycoolgame.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class World {

	Player player;
	Level level;
	
	public World() {
		createDemoWorld();
	}
	
	private void createDemoWorld() {
		player = new Player(new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2));
		level = new Level();
	}
	
	public Player getPlayer() {
		return player;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}
	
}

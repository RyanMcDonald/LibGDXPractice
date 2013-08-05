package com.me.mycoolgame.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;

public class World {

	private Player player;
	private TiledMap map;
	
	public World() {
		createDemoWorld();
	}
	
	private void createDemoWorld() {
		player = new Player(new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2));
		map = null;
	}
	
	public Player getPlayer() {
		return player;
	}

	public TiledMap getMap() {
		return map;
	}

	public void setMap(TiledMap map) {
		this.map = map;
	}
	
}

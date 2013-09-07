package com.me.mycoolgame.util;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class Assets {

	public static AssetManager manager;
	
	public Assets() {
		manager = new AssetManager();
		
		load();
	}
	
	private void load() {
		manager.load("images/textures/textures.pack", TextureAtlas.class);
		
		manager.load("data/music/places_of_soul.mp3", Music.class);
		
		manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		manager.load("images/tilesets/nature/nature.tmx", TiledMap.class);
	}
  
}

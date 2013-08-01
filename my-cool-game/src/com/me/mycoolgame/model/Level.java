package com.me.mycoolgame.model;

public class Level {

	private String backgroundFilename;
	
	public Level() {
		loadDemoLevel();
	}
	
	private void loadDemoLevel() {
		backgroundFilename = "images/grass_background.png";
	}
	
	public String getBackgroundFilename() {
		return backgroundFilename;
	}
}

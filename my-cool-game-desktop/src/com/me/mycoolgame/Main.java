package com.me.mycoolgame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "My Cool Game";
		cfg.useGL20 = false;
		cfg.width = 512;
		cfg.height = 512;
		
		TexturePacker2.process("images/", "../my-cool-game-android/assets/images/textures", "textures.pack");
		
		new LwjglApplication(new MyCoolGame(), cfg);
	}
}

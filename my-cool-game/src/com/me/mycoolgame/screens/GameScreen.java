package com.me.mycoolgame.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.me.mycoolgame.controller.PlayerController;
import com.me.mycoolgame.model.World;
import com.me.mycoolgame.view.WorldRenderer;

public class GameScreen implements Screen, InputProcessor {

	private Game game;
	
	private SpriteBatch spriteBatch;
	private World world;
	private WorldRenderer renderer;
	private PlayerController controller;
	
	public GameScreen(Game game) {
		this.game = game;
		spriteBatch = new SpriteBatch();
	}
	
	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.W) {
			controller.upPressed();
		}

		if (keycode == Keys.D) {
			controller.rightPressed();
		}

		if (keycode == Keys.S) {
			controller.downPressed();
		}
		
		if (keycode == Keys.A) {
			controller.leftPressed();
		}
		
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.W) {
			controller.upReleased();
		}
		
		if (keycode == Keys.D) {
			controller.rightReleased();
		}
		
		if (keycode == Keys.S) {
			controller.downReleased();
		}
		
		if (keycode == Keys.A) {
			controller.leftReleased();
		}
		
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		controller.rightPressed();
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		controller.rightReleased();
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		controller.update(delta);
		renderer.render();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		world = new World();
		renderer = new WorldRenderer(world, spriteBatch);
		controller = new PlayerController(world);
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
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
		Gdx.input.setInputProcessor(null);
	}

}

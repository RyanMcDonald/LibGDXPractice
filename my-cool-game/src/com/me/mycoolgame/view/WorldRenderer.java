package com.me.mycoolgame.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pool;
import com.me.mycoolgame.model.Player;
import com.me.mycoolgame.model.Player.State;
import com.me.mycoolgame.model.World;

public class WorldRenderer {

	private static final float RUNNING_FRAME_DURATION = 0.2f;
	
	private World world;
	private OrthographicCamera camera;
	private SpriteBatch spriteBatch;
	
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	
	private TextureAtlas atlas;
	private TextureRegion playerIdleNorth;
	private TextureRegion playerIdleNortheast;
	private TextureRegion playerIdleEast;
	private TextureRegion playerIdleSoutheast;
	private TextureRegion playerIdleSouth;
	private TextureRegion playerIdleSouthwest;
	private TextureRegion playerIdleWest;
	private TextureRegion playerIdleNorthwest;
	
	private Animation walkNorthAnimation;
	private Animation walkNortheastAnimation;
	private Animation walkEastAnimation;
	private Animation walkSoutheastAnimation;
	private Animation walkSouthAnimation;
	private Animation walkSouthwestAnimation;
	private Animation walkWestAnimation;
	private Animation walkNorthwestAnimation;
	
	public WorldRenderer(World world, SpriteBatch spriteBatch) {
		this.world = world;
		this.camera = new OrthographicCamera();
		camera.setToOrtho(false, 500, 300);
		camera.update();
		
		this.spriteBatch = spriteBatch;
		this.atlas = new TextureAtlas(Gdx.files.internal("images/textures/textures.pack"));
		
		loadTextures();
	}
	
	public void render() {
		Vector2 playerPosition = world.getPlayer().getPosition();
		camera.position.x = playerPosition.x;
		camera.position.y = playerPosition.y;
		
		keepCameraInBounds();
		
		camera.update();
		spriteBatch.setProjectionMatrix(camera.combined);
		
		renderBackground();
		renderObjects();
	}
	
	private void loadTextures() {
		map = new TmxMapLoader().load("images/tilesets/nature/nature.tmx");
		world.setMap(map);
		renderer = new OrthogonalTiledMapRenderer(map, 1f);
		
		playerIdleNorth = atlas.findRegion("player-idle-north");
		playerIdleNortheast = atlas.findRegion("player-idle-northeast");
		playerIdleEast = atlas.findRegion("player-idle-east");
		playerIdleSoutheast = atlas.findRegion("player-idle-southeast");
		playerIdleSouth = atlas.findRegion("player-idle-south");
		playerIdleSouthwest = atlas.findRegion("player-idle-southwest");
		playerIdleWest = atlas.findRegion("player-idle-west");
		playerIdleNorthwest = atlas.findRegion("player-idle-northwest");

		TextureRegion[] walkNorthFrames = new TextureRegion[3];
		TextureRegion[] walkNortheastFrames = new TextureRegion[3];
		TextureRegion[] walkEastFrames = new TextureRegion[3];
		TextureRegion[] walkSoutheastFrames = new TextureRegion[3];
		TextureRegion[] walkSouthFrames = new TextureRegion[3];
		TextureRegion[] walkSouthwestFrames = new TextureRegion[3];
		TextureRegion[] walkWestFrames = new TextureRegion[3];
		TextureRegion[] walkNorthwestFrames = new TextureRegion[3];
		
		// We want the idle images to be the first frame of the animations
		walkNorthFrames[0] = atlas.findRegion("player-idle-north");
		walkNortheastFrames[0] = atlas.findRegion("player-idle-northeast");
		walkEastFrames[0] = atlas.findRegion("player-idle-east");
		walkSoutheastFrames[0] = atlas.findRegion("player-idle-southeast");
		walkSouthFrames[0] = atlas.findRegion("player-idle-south");
		walkSouthwestFrames[0] = atlas.findRegion("player-idle-southwest");
		walkWestFrames[0] = atlas.findRegion("player-idle-west");
		walkNorthwestFrames[0] = atlas.findRegion("player-idle-northwest");
		
		for (int i = 1; i < 3; i++) {
			walkNorthFrames[i] = atlas.findRegion("player-walking-north-" + i);
			walkNortheastFrames[i] = atlas.findRegion("player-walking-northeast-" + i);
			walkEastFrames[i] = atlas.findRegion("player-walking-east-" + i);
			walkSoutheastFrames[i] = atlas.findRegion("player-walking-southeast-" + i);
			walkSouthFrames[i] = atlas.findRegion("player-walking-south-" + i);
			walkSouthwestFrames[i] = atlas.findRegion("player-walking-southwest-" + i);
			walkWestFrames[i] = atlas.findRegion("player-walking-west-" + i);
			walkNorthwestFrames[i] = atlas.findRegion("player-walking-northwest-" + i);
		}
		
		walkNorthAnimation = new Animation(RUNNING_FRAME_DURATION, walkNorthFrames);
		walkNortheastAnimation = new Animation(RUNNING_FRAME_DURATION, walkNortheastFrames);
		walkEastAnimation = new Animation(RUNNING_FRAME_DURATION, walkEastFrames);
		walkSoutheastAnimation = new Animation(RUNNING_FRAME_DURATION, walkSoutheastFrames);
		walkSouthAnimation = new Animation(RUNNING_FRAME_DURATION, walkSouthFrames);
		walkSouthwestAnimation = new Animation(RUNNING_FRAME_DURATION, walkSouthwestFrames);
		walkWestAnimation = new Animation(RUNNING_FRAME_DURATION, walkWestFrames);
		walkNorthwestAnimation = new Animation(RUNNING_FRAME_DURATION, walkNorthwestFrames);
	}
	
	private void renderBackground () {
		// set the tile map renderer view based on what the camera sees and render the map
		renderer.setView(camera);
		renderer.render();
	}
	
	private void renderObjects() {
		spriteBatch.enableBlending();
		spriteBatch.begin();
		
		renderPlayer();
		
		spriteBatch.end();
	}
	
	private void renderPlayer() {
		
		Player player = world.getPlayer();
		TextureRegion playerFrame = null;
		
		if (player.getState().equals(State.IDLE)) {
			switch (player.getFacingDirection()) {
			case NORTH:
				playerFrame = playerIdleNorth;
				break;
			case NORTHEAST:
				playerFrame = playerIdleNortheast;
				break;
			case EAST:
				playerFrame = playerIdleEast;
				break;
			case SOUTHEAST:
				playerFrame = playerIdleSoutheast;
				break;
			case SOUTH:
				playerFrame = playerIdleSouth;
				break;
			case SOUTHWEST:
				playerFrame = playerIdleSouthwest;
				break;
			case WEST:
				playerFrame = playerIdleWest;
				break;
			case NORTHWEST:
				playerFrame = playerIdleNorthwest;
				break;
			}
		} else if (player.getState().equals(State.WALKING)) {
			switch (player.getFacingDirection()) {
			case NORTH:
				playerFrame = walkNorthAnimation.getKeyFrame(player.getStateTime(), true);
				break;
			case NORTHEAST:
				playerFrame = walkNortheastAnimation.getKeyFrame(player.getStateTime(), true);
				break;
			case EAST:
				playerFrame = walkEastAnimation.getKeyFrame(player.getStateTime(), true);
				break;
			case SOUTHEAST:
				playerFrame = walkSoutheastAnimation.getKeyFrame(player.getStateTime(), true);
				break;
			case SOUTH:
				playerFrame = walkSouthAnimation.getKeyFrame(player.getStateTime(), true);
				break;
			case SOUTHWEST:
				playerFrame = walkSouthwestAnimation.getKeyFrame(player.getStateTime(), true);
				break;
			case WEST:
				playerFrame = walkWestAnimation.getKeyFrame(player.getStateTime(), true);
				break;
			case NORTHWEST:
				playerFrame = walkNorthwestAnimation.getKeyFrame(player.getStateTime(), true);
				break;
			}
		}
		
		spriteBatch.draw(playerFrame, player.getPosition().x, player.getPosition().y);
	}
	
	private void keepCameraInBounds() {
		// Map width = number of tiles on the x-axis * each tile's width in pixels
		int width = (Integer) renderer.getMap().getProperties().get("width");
		int tileWidth = (Integer) renderer.getMap().getProperties().get("tilewidth");
		int mapWidth = width * tileWidth;
		
		// Map width = number of tiles on the y-axis * each tile's height in pixels
		int height = (Integer) renderer.getMap().getProperties().get("height");
		int tileHeight = (Integer) renderer.getMap().getProperties().get("tileheight");
		int mapHeight = height * tileHeight;
		
		if (camera.position.x - camera.viewportWidth / 2 < 0) {
			camera.position.x = camera.viewportWidth / 2;
		}
		
		if (camera.position.x > mapWidth - camera.viewportWidth / 2) {
			camera.position.x = mapWidth - camera.viewportWidth / 2;
		}
		
		if (camera.position.y - camera.viewportHeight / 2 < 0) {
			camera.position.y = camera.viewportHeight / 2;
		}
		
		if (camera.position.y > mapHeight - camera.viewportHeight / 2) {
			camera.position.y = mapHeight - camera.viewportHeight / 2;
		}
	}
}

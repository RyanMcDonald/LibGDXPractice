package com.me.mycoolgame.view;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.me.mycoolgame.model.Player;
import com.me.mycoolgame.model.Player.State;
import com.me.mycoolgame.model.World;
import com.me.mycoolgame.util.Assets;

public class WorldRenderer {
	
	private World world;
	private Player player;
	private OrthographicCamera camera;
	private SpriteBatch spriteBatch;
	
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	
	Music backgroundMusic;
	
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
		this.player = world.getPlayer();
		this.camera = new OrthographicCamera();
		camera.setToOrtho(false, 500, 300);
		camera.update();
		
		this.spriteBatch = spriteBatch;
		this.atlas = Assets.manager.get("images/textures/textures.pack", TextureAtlas.class);
		
		loadTextures();

		backgroundMusic = Assets.manager.get("data/music/places_of_soul.mp3", Music.class);
		backgroundMusic.setLooping(true);
		backgroundMusic.play();
	}
	
	public void render() {
		Vector2 playerPosition = world.getPlayer().getPosition();

		// Keep the camera centered on the player
		camera.position.x = playerPosition.x;
		camera.position.y = playerPosition.y;
		
		keepCameraInBounds();
		
		camera.update();
		spriteBatch.setProjectionMatrix(camera.combined);

		// set the tile map renderer view based on what the camera sees and render the map
		renderer.setView(camera);
		
		// Render the "Top" layer after the player, so that it overlaps the player, i.e., when they walk behind treetops.
		renderer.render(new int[] { 0, 1, 3 });

		renderPlayer();
		
		renderer.render(new int[] { 2 });
	}
	
	private void loadTextures() {
		map = Assets.manager.get("images/tilesets/nature/nature.tmx");
		world.setMap(map);
		renderer = new OrthogonalTiledMapRenderer(map, 1f);
		
		playerIdleNorth = atlas.findRegion(player.getIdleNorthImage());
		playerIdleNortheast = atlas.findRegion(player.getIdleNortheastImage());
		playerIdleEast = atlas.findRegion(player.getIdleEastImage());
		playerIdleSoutheast = atlas.findRegion(player.getIdleSoutheastImage());
		playerIdleSouth = atlas.findRegion(player.getIdleNorthImage());
		playerIdleSouthwest = atlas.findRegion(player.getIdleSouthwestImage());
		playerIdleWest = atlas.findRegion(player.getIdleWestImage());
		playerIdleNorthwest = atlas.findRegion(player.getIdleNorthwestImage());

		Array<String> walkNorthImages = player.getWalkNorthImages();
		Array<String> walkNortheastImages = player.getWalkNortheastImages();
		Array<String> walkEastImages = player.getWalkEastImages();
		Array<String> walkSoutheastImages = player.getWalkSoutheastImages();
		Array<String> walkSouthImages = player.getWalkSouthImages();
		Array<String> walkSouthwestImages = player.getWalkSouthwestImages();
		Array<String> walkWestImages = player.getWalkWestImages();
		Array<String> walkNorthwestImages = player.getWalkNorthwestImages();
		
		TextureRegion[] walkNorthFrames = new TextureRegion[walkNorthImages.size];
		TextureRegion[] walkNortheastFrames = new TextureRegion[walkNortheastImages.size];
		TextureRegion[] walkEastFrames = new TextureRegion[walkEastImages.size];
		TextureRegion[] walkSoutheastFrames = new TextureRegion[walkSoutheastImages.size];
		TextureRegion[] walkSouthFrames = new TextureRegion[walkSouthImages.size];
		TextureRegion[] walkSouthwestFrames = new TextureRegion[walkSouthwestImages.size];
		TextureRegion[] walkWestFrames = new TextureRegion[walkWestImages.size];
		TextureRegion[] walkNorthwestFrames = new TextureRegion[walkNorthwestImages.size];
		
		for (int i = 0; i < walkNorthImages.size; i++) {
			walkNorthFrames[i] = atlas.findRegion(walkNorthImages.get(i));
		}

		for (int i = 0; i < walkNortheastImages.size; i++) {
			walkNortheastFrames[i] = atlas.findRegion(walkNortheastImages.get(i));
		}

		for (int i = 0; i < walkEastImages.size; i++) {
			walkEastFrames[i] = atlas.findRegion(walkEastImages.get(i));
		}

		for (int i = 0; i < walkSoutheastImages.size; i++) {
			walkSoutheastFrames[i] = atlas.findRegion(walkSoutheastImages.get(i));
		}

		for (int i = 0; i < walkSouthImages.size; i++) {
			walkSouthFrames[i] = atlas.findRegion(walkSouthImages.get(i));
		}

		for (int i = 0; i < walkSouthwestImages.size; i++) {
			walkSouthwestFrames[i] = atlas.findRegion(walkSouthwestImages.get(i));
		}

		for (int i = 0; i < walkWestImages.size; i++) {
			walkWestFrames[i] = atlas.findRegion(walkWestImages.get(i));
		}

		for (int i = 0; i < walkNorthwestImages.size; i++) {
			walkNorthwestFrames[i] = atlas.findRegion(walkNorthwestImages.get(i));
		}
		
		float walkingFrameDuration = world.getPlayer().getSpeed() / 800;
		walkNorthAnimation = new Animation(walkingFrameDuration, walkNorthFrames);
		walkNortheastAnimation = new Animation(walkingFrameDuration, walkNortheastFrames);
		walkEastAnimation = new Animation(walkingFrameDuration, walkEastFrames);
		walkSoutheastAnimation = new Animation(walkingFrameDuration, walkSoutheastFrames);
		walkSouthAnimation = new Animation(walkingFrameDuration, walkSouthFrames);
		walkSouthwestAnimation = new Animation(walkingFrameDuration, walkSouthwestFrames);
		walkWestAnimation = new Animation(walkingFrameDuration, walkWestFrames);
		walkNorthwestAnimation = new Animation(walkingFrameDuration, walkNorthwestFrames);
	}

	private void renderPlayer() {
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

		spriteBatch.enableBlending();
		spriteBatch.begin();
		spriteBatch.draw(playerFrame, player.getPosition().x, player.getPosition().y);
		spriteBatch.end();
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

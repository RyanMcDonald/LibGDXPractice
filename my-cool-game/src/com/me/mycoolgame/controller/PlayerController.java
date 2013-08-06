package com.me.mycoolgame.controller;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.me.mycoolgame.model.Player;
import com.me.mycoolgame.model.Player.Direction;
import com.me.mycoolgame.model.Player.State;
import com.me.mycoolgame.model.World;

public class PlayerController {

	private World world;
	private Player player;
	
	public enum Keys {
		UP, RIGHT, LEFT, DOWN
	}
	
	static Map<Keys, Boolean> keys = new HashMap<Keys, Boolean>();
	static {
		keys.put(Keys.UP, false);
		keys.put(Keys.RIGHT, false);
		keys.put(Keys.LEFT, false);
		keys.put(Keys.DOWN, false);
	};
	
	// We use this for optimization to avoid garbage collection overhead. Otherwise we would
	// have to allocate a rectangle on each frame when checking collisions.
	private Pool<Rectangle> rectPool = new Pool<Rectangle>() {
		@Override
		protected Rectangle newObject () {
			return new Rectangle();
		}
	};
	
	public PlayerController(World world) {
		this.world = world;
		this.player = world.getPlayer();
	}
	
	public void upPressed() {
		keys.put(Keys.UP, true);
	}
	
	public void upReleased() {
		keys.put(Keys.UP, false);
	}
	
	public void rightPressed() {
		keys.put(Keys.RIGHT, true);
	}
	
	public void rightReleased() {
		keys.put(Keys.RIGHT, false);
	}
	
	public void downPressed() {
		keys.put(Keys.DOWN, true);
	}
	
	public void downReleased() {
		keys.put(Keys.DOWN, false);
	}
	
	public void leftPressed() {
		keys.put(Keys.LEFT, true);
	}
	
	public void leftReleased() {
		keys.put(Keys.LEFT, false);
	}
	
	public void update(float delta) {
		// Process the input and set the player's states
		processInput(delta);

		if (player.getState().equals(Player.State.WALKING)) {
			checkCollisionsWithLayer(delta);
			preventOutOfBounds();
		}
		
		player.update(delta);
		
		
	}
	
	private void processInput(float delta) {
		// If they are only pressing UP
		if (keys.get(Keys.UP) && !keys.get(Keys.LEFT) && !keys.get(Keys.RIGHT)) {
			updatePlayerMovingState(Player.Direction.NORTH, Player.State.WALKING, new Vector2(0, player.getSpeed()));
			
		} else if (keys.get(Keys.UP) && keys.get(Keys.RIGHT)) {
			updatePlayerMovingState(Player.Direction.NORTHEAST, Player.State.WALKING, new Vector2(player.getSpeed(), player.getSpeed()));
			
		// If they are only pressing RIGHT
		} else if (keys.get(Keys.RIGHT) && !keys.get(Keys.UP) && !keys.get(Keys.DOWN)) {
			updatePlayerMovingState(Player.Direction.EAST, Player.State.WALKING, new Vector2(player.getSpeed(), 0));
			
		} else if (keys.get(Keys.RIGHT) && keys.get(Keys.DOWN)) {
			updatePlayerMovingState(Player.Direction.SOUTHEAST, Player.State.WALKING, new Vector2(player.getSpeed(), -player.getSpeed()));
			
		// If they are only pressing DOWN
		} else if (keys.get(Keys.DOWN) && !keys.get(Keys.LEFT) && !keys.get(Keys.RIGHT)) {
			updatePlayerMovingState(Player.Direction.SOUTH, Player.State.WALKING, new Vector2(0, -player.getSpeed()));
			
		} else if (keys.get(Keys.DOWN) && keys.get(Keys.LEFT)) {
			updatePlayerMovingState(Player.Direction.SOUTHWEST, Player.State.WALKING, new Vector2(-player.getSpeed(), -player.getSpeed()));
			
		// If they are only pressing LEFT
		} else if (keys.get(Keys.LEFT) && !keys.get(Keys.UP) && !keys.get(Keys.DOWN)) {
			updatePlayerMovingState(Player.Direction.WEST, Player.State.WALKING, new Vector2(-player.getSpeed(), 0));
			
		} else if (keys.get(Keys.LEFT) && keys.get(Keys.UP)) {
			updatePlayerMovingState(Player.Direction.NORTHWEST, Player.State.WALKING, new Vector2(-player.getSpeed(), player.getSpeed()));
			
		} else {
			updatePlayerMovingState(player.getFacingDirection(), Player.State.IDLE, new Vector2(0, 0));
		}

	}
	
	private void updatePlayerMovingState(Direction direction, State state, Vector2 velocity) {
		player.setFacingDirection(direction);
		player.setState(state);
		player.setVelocity(velocity);
	}
	
	private void preventOutOfBounds() {
		float posX = player.getPosition().x;
		float posY = player.getPosition().y;
		
		// Map width = number of tiles on the x-axis * each tile's width in pixels
		int width = (Integer) world.getMap().getProperties().get("width");
		int tileWidth = (Integer) world.getMap().getProperties().get("tilewidth");
		int mapWidth = width * tileWidth;
		
		// Map width = number of tiles on the y-axis * each tile's height in pixels
		int height = (Integer) world.getMap().getProperties().get("height");
		int tileHeight = (Integer) world.getMap().getProperties().get("tileheight");
		int mapHeight = height * tileHeight;
		
		if (posX < 0) {
			player.setPosition(new Vector2(0, posY));
		}
		
		if (posX > mapWidth - player.getWidth()) {
			player.setPosition(new Vector2(mapWidth - player.getWidth(), posY));
		}
		
		if (posY < 0) {
			player.setPosition(new Vector2(posX, 0));
		}
		
		if (posY > mapHeight - player.getHeight()) {
			player.setPosition(new Vector2(posX, mapHeight - player.getHeight()));
		}
	}
	
	private void checkCollisionsWithLayer(float delta) {
		// Get the player's velocity in terms of frame units, so that we know how far they will have gone.
		player.getVelocity().scl(delta);
		
		// Get a rectangle from the pool, instead of always creating a new one; avoids garbage collection overhead.
		Rectangle playerRect = rectPool.obtain();
		
		// We want the player's bounding box to only be his lower half, so that the collision looks more realistic since only
		// his feet won't be able to walk into the collidable area
		playerRect.set(player.getPosition().x, player.getPosition().y, player.getWidth(), player.getHeight());
		
		int startX, endX, startY, endY;
		Array<Rectangle> tiles = new Array<Rectangle>();
		
		// If they are moving right, check the tiles to the right of the player's bounding box.
		if (player.getVelocity().x > 0) {
			startX = endX = (int) (player.getPosition().x + player.getWidth() + player.getVelocity().x);
		
		// Else they are moving left, so check the tiles to the left of their bounding box.
		} else {
			startX = endX = (int) (player.getPosition().x + player.getVelocity().x);
		}
		
		startY = (int) (player.getPosition().y);
		endY = (int) (player.getPosition().y + player.getHeight());
		
		// Get the possible tiles the player could collide with
		getTiles(startX, startY, endX, endY, tiles);
		
		playerRect.x += player.getVelocity().x;
		for(Rectangle tile: tiles) {
			if(playerRect.overlaps(tile)) {
				player.getVelocity().x = 0;
				break;
			}
		}
		playerRect.x = player.getPosition().x;
		
		// Repeat for the Y-axis. If they're moving up, check the tiles above their bounding box.
		if (player.getVelocity().y > 0) {
			startY = endY = (int) (player.getPosition().y + player.getHeight() + player.getVelocity().y);
		
		// Otherwise they are moving down, so check the tiles on the bottom of their bounding box.
		} else {
			startY = endY = (int) (player.getPosition().y + player.getVelocity().y);
		}
		
		startX = (int) (player.getPosition().x);
		endX = (int) (player.getPosition().x + player.getWidth());
		
		// Get the possible tiles the player could collide with
		getTiles(startX, startY, endX, endY, tiles);
		
		playerRect.y += player.getVelocity().y;
		for(Rectangle tile: tiles) {
			if(playerRect.overlaps(tile)) {
				player.getVelocity().y = 0;
				break;
			}
		}
		
		// We're done with our rectangle in the pool; get rid of it.
		rectPool.free(playerRect);
		
		// Unscale the velocity by the inverse delta time
		player.getVelocity().scl(1/delta);
	}
	
	private void getTiles(int startX, int startY, int endX, int endY, Array<Rectangle> tiles) {
		// Get the "Foreground" layer, consisting of all the tiles marked as foreground
		TiledMapTileLayer layer = (TiledMapTileLayer) world.getMap().getLayers().get("Foreground");
		
		rectPool.freeAll(tiles);
		tiles.clear();
		
		for(int y = startY; y <= endY; y ++) {
			
			for(int x = startX; x <= endX; x++) {
				Cell cell = layer.getCell(MathUtils.floor(x / layer.getTileWidth()), MathUtils.floor(y / layer.getTileHeight()));
				if(cell != null) {
					Rectangle rect = rectPool.obtain();
					rect.set(x, y, 1, 1);
					tiles.add(rect);
				}
			}
			
		}
	}
	
}

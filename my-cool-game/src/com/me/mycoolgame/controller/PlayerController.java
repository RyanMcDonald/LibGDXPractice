package com.me.mycoolgame.controller;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.me.mycoolgame.controller.skill.SkillController;
import com.me.mycoolgame.model.Player;
import com.me.mycoolgame.model.Player.State;
import com.me.mycoolgame.model.World;
import com.me.mycoolgame.util.CardinalDirection.Direction;

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
	
	// Our hotkey map with button name as the key and skill name as the value
	private Map<String, String> hotkeys = new HashMap<String, String>();
	
	// We use this for optimization to avoid garbage collection overhead. Otherwise we would
	// have to allocate a rectangle on each frame when checking collisions with a layer.
	private Pool<Rectangle> rectPool = new Pool<Rectangle>() {
		@Override
		protected Rectangle newObject () {
			return new Rectangle();
		}
	};
	
	public PlayerController(World world) {
		this.world = world;
		this.player = world.getPlayer();
		
		setUpHotkeys();
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
	
	public void activateSkillTargetIndicator(Vector2 destination, String screenButton) {
		// Pass in the World so that we can create a controller for the skill

		// TODO: Enable the appropriate targeting indicator to follow the touch after a certain delay
		// player.prepareSkill(world, destination, screenButton);
	}
	
	public void activateSkill(Vector2 destination, String screenButton) {
		// Pass in the World so that we can create a controller for the skill
		
		// Get the name of the skill that is bound to that button
		String skillName = hotkeys.get(screenButton);
		
		player.useSkill(world, destination, skillName);
	}
	
	public void touchpadPressed(Touchpad touchpad) {
		float touchpadX = touchpad.getKnobPercentX();
		float touchpadY = touchpad.getKnobPercentY();
		
		float newXVelocity = touchpadX * player.getSpeed();
		float newYVelocity = touchpadY * player.getSpeed();
		Vector2 newVelocity = new Vector2(newXVelocity, newYVelocity);
		
		// Calculate what direction the player should be facing based on the touchpad orientation. Default to North.
		Direction playerDirection = Direction.NORTH;
		
		if (touchpadX != 0 && touchpadY > 0) {
			if (touchpadX >= -0.5 && touchpadX <= 0.5) {
				playerDirection = Direction.NORTH;
			} else if (touchpadX > 0.5 && touchpadY > 0.5) {
				playerDirection = Direction.NORTHEAST;
			} else if (touchpadX > 0 && touchpadY <= 0.5) {
				playerDirection = Direction.EAST;
			} else if (touchpadX < -0.5 && touchpadY > 0.5) {
				playerDirection = Direction.NORTHWEST;
			} else if (touchpadX < 0 && touchpadY <= 0.5) {
				playerDirection = Direction.WEST;
			}
		} else if (touchpadX != 0 && touchpadY < 0) {
			if (touchpadX >= -0.5 && touchpadX <= 0.5) {
				playerDirection = Direction.SOUTH;
			} else if (touchpadX > 0.5 && touchpadY < -0.5) {
				playerDirection = Direction.SOUTHEAST;
			} else if (touchpadX > 0 && touchpadY >= -0.5) {
				playerDirection = Direction.EAST;
			} else if (touchpadX < -0.5 && touchpadY < -0.5) {
				playerDirection = Direction.SOUTHWEST;
			} else if (touchpadX < 0 && touchpadY >= -0.5) {
				playerDirection = Direction.WEST;
			}
		}

		// If they're performing an action, they shouldn't be able to do anything else
		if (player.getState().equals(State.ACTING) && player.getStateTime() < player.getActingTime()) {
			updatePlayerMovingState(player.getFacingDirection(), Player.State.ACTING, new Vector2(0, 0));
		} else {
			updatePlayerMovingState(playerDirection, Player.State.WALKING, newVelocity);
		}
	}
	
	public void touchpadReleased() {
		// If they're performing an action, they shouldn't be able to do anything else
		if (player.getState().equals(State.ACTING) && player.getStateTime() < player.getActingTime()) {
			updatePlayerMovingState(player.getFacingDirection(), Player.State.ACTING, new Vector2(0, 0));
		} else {
			updatePlayerMovingState(player.getFacingDirection(), Player.State.IDLE, new Vector2(0, 0));
		}
	}

	// Process the input and set the player's states
	public void update(float delta) {
		if (Gdx.app.getType() != ApplicationType.Android && Gdx.app.getType() != ApplicationType.iOS) {
			processKeyInput(delta);
		}

		if (player.getState().equals(Player.State.WALKING)) {
			checkCollisionsWithObjects(delta);
			preventOutOfBounds();
		}
		
		for (SkillController controller : player.getSkillControllers()) {
			controller.update(delta);
		}

		player.update(delta);
	}
	
	/**
	 * Checks which keyboard keys are pressed and sets the player's state accordingly. Not used for the mobile platforms.
	 * @param delta
	 */
	private void processKeyInput(float delta) {
		// If they're performing an action, they shouldn't be able to do anything else
		if (player.getState().equals(State.ACTING) && player.getStateTime() < player.getActingTime()) {
			updatePlayerMovingState(player.getFacingDirection(), Player.State.ACTING, new Vector2(0, 0));

		// If they are only pressing UP
		} else if (keys.get(Keys.UP) && !keys.get(Keys.LEFT) && !keys.get(Keys.RIGHT)) {
			updatePlayerMovingState(Direction.NORTH, Player.State.WALKING, new Vector2(0, player.getSpeed()));
			
		} else if (keys.get(Keys.UP) && keys.get(Keys.RIGHT)) {
			updatePlayerMovingState(Direction.NORTHEAST, Player.State.WALKING, new Vector2(player.getSpeed(), player.getSpeed()));
			
		// If they are only pressing RIGHT
		} else if (keys.get(Keys.RIGHT) && !keys.get(Keys.UP) && !keys.get(Keys.DOWN)) {
			updatePlayerMovingState(Direction.EAST, Player.State.WALKING, new Vector2(player.getSpeed(), 0));
			
		} else if (keys.get(Keys.RIGHT) && keys.get(Keys.DOWN)) {
			updatePlayerMovingState(Direction.SOUTHEAST, Player.State.WALKING, new Vector2(player.getSpeed(), -player.getSpeed()));
			
		// If they are only pressing DOWN
		} else if (keys.get(Keys.DOWN) && !keys.get(Keys.LEFT) && !keys.get(Keys.RIGHT)) {
			updatePlayerMovingState(Direction.SOUTH, Player.State.WALKING, new Vector2(0, -player.getSpeed()));
			
		} else if (keys.get(Keys.DOWN) && keys.get(Keys.LEFT)) {
			updatePlayerMovingState(Direction.SOUTHWEST, Player.State.WALKING, new Vector2(-player.getSpeed(), -player.getSpeed()));
			
		// If they are only pressing LEFT
		} else if (keys.get(Keys.LEFT) && !keys.get(Keys.UP) && !keys.get(Keys.DOWN)) {
			updatePlayerMovingState(Direction.WEST, Player.State.WALKING, new Vector2(-player.getSpeed(), 0));
			
		} else if (keys.get(Keys.LEFT) && keys.get(Keys.UP)) {
			updatePlayerMovingState(Direction.NORTHWEST, Player.State.WALKING, new Vector2(-player.getSpeed(), player.getSpeed()));
			
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
		float posX = player.getBounds().x;
		float posY = player.getBounds().y;
		
		// Map width = number of tiles on the x-axis * each tile's width in pixels
		int width = (Integer) world.getMap().getProperties().get("width");
		int tileWidth = (Integer) world.getMap().getProperties().get("tilewidth");
		int mapWidth = width * tileWidth;
		
		// Map height = number of tiles on the y-axis * each tile's height in pixels
		int height = (Integer) world.getMap().getProperties().get("height");
		int tileHeight = (Integer) world.getMap().getProperties().get("tileheight");
		int mapHeight = height * tileHeight;
		
		float newPosX = posX;
		float newPosY = posY;

		if (posX < 0) {
			newPosX = 0;
		}

		if (posX > mapWidth - player.getWidth()) {
			newPosX = mapWidth - player.getWidth();
		}
		
		if (posY < 0) {
			newPosY = 0;
		}
		
		if (posY > mapHeight - player.getHeight()) {
			newPosY = mapHeight - player.getHeight();
		}

		player.setBounds(new Rectangle(newPosX, newPosY, player.getWidth(), player.getHeight()));
	}
	
	private void checkCollisionsWithLayer(String layerName, float delta) {
		// Get the player's velocity in terms of frame units, so that we know how far they will have gone.
		player.getVelocity().scl(delta);
		
		// Get a rectangle from the pool, instead of always creating a new one; avoids garbage collection overhead.
		Rectangle playerRect = rectPool.obtain();
		
		// We want the player's bounding box to only be his lower half, so that the collision looks more realistic since only
		// his feet won't be able to walk into the collidable area
		playerRect.set(player.getBounds().x, player.getBounds().y, player.getWidth(), player.getHeight());
		
		int startX, endX, startY, endY;
		Array<Rectangle> tiles = new Array<Rectangle>();
		
		// If they are moving right, check the tiles to the right of the player's bounding box.
		if (player.getVelocity().x > 0) {
			startX = endX = (int) (player.getBounds().x + player.getWidth() + player.getVelocity().x);
		
		// Else they are moving left, so check the tiles to the left of their bounding box.
		} else {
			startX = endX = (int) (player.getBounds().x + player.getVelocity().x);
		}
		
		startY = (int) (player.getBounds().y);
		endY = (int) (player.getBounds().y + player.getHeight());
		
		// Get the possible tiles the player could collide with
		getTiles(layerName, startX, startY, endX, endY, tiles);
		
		playerRect.x += player.getVelocity().x;
		for(Rectangle tile: tiles) {
			if(playerRect.overlaps(tile)) {
				player.getVelocity().x = 0;
				break;
			}
		}
		playerRect.x = player.getBounds().x;
		
		// Repeat for the Y-axis. If they're moving up, check the tiles above their bounding box.
		if (player.getVelocity().y > 0) {
			startY = endY = (int) (player.getBounds().y + player.getHeight() + player.getVelocity().y);
		
		// Otherwise they are moving down, so check the tiles on the bottom of their bounding box.
		} else {
			startY = endY = (int) (player.getBounds().y + player.getVelocity().y);
		}
		
		startX = (int) (player.getBounds().x);
		endX = (int) (player.getBounds().x + player.getWidth());
		
		// Get the possible tiles the player could collide with
		getTiles(layerName, startX, startY, endX, endY, tiles);
		
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
	
	private void getTiles(String layerName, int startX, int startY, int endX, int endY, Array<Rectangle> tiles) {
		// Get the "Foreground" layer, consisting of all the tiles marked as foreground
		TiledMapTileLayer layer = (TiledMapTileLayer) world.getMap().getLayers().get(layerName);
		
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
	
	/**
	 * Checks if the player would collide with any objects in the Collision layer of the Tiled map, and
	 * adjusts their position accordingly.
	 * @param delta
	 */
	private void checkCollisionsWithObjects(float delta) {
		// Get the player's velocity in terms of frame units, so that we know how far they will have gone.
		player.getVelocity().scl(delta);
		
		Rectangle playerRect = new Rectangle();

		// We want the player's bounding box to only be his lower half, so that the collision looks more realistic since only
		// his feet won't be able to walk into the collidable area
		playerRect.set(player.getBounds().x, player.getBounds().y, player.getWidth(), player.getHeight() / 2);
		
		// TODO: Possibly move this outside of this method so it's not instantiated on every frame
		MapObjects objects = world.getMap().getLayers().get("Collision").getObjects();
		for (int i = 0; i < objects.getCount(); i++) {
			RectangleMapObject objectRect = (RectangleMapObject) objects.get(i);
			Rectangle rect = objectRect.getRectangle();
			
			// If the player will collide with an object in the next x position calculation, set x velocity to 0.
			playerRect.x += player.getVelocity().x;
			if(playerRect.overlaps(rect)) {
				player.getVelocity().x = 0;
			}
			
			// Reset the x position of the playerRect so we can use it in the y-axis collision calculation
			playerRect.x = player.getBounds().x;
			
			// If the player will collide with an object in the next y position calculation, set y velocity to 0.
			playerRect.y += player.getVelocity().y;
			if(playerRect.overlaps(rect)) {
				player.getVelocity().y = 0;
			}
			
			playerRect.y = player.getBounds().y;
		}
		
		// Unscale the velocity by the inverse delta time
		player.getVelocity().scl(1/delta);
	}
	
	/**
	 * 
	 */
	private void setUpHotkeys() {
		// TODO: Get their hotkeys from local storage somehow
		
		hotkeys.put("button1", "fireball");
	}
	
}

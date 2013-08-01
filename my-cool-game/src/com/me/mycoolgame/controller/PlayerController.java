package com.me.mycoolgame.controller;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;
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
		processInput();
		
		player.update(delta);
	}
	
	private void processInput() {
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
}

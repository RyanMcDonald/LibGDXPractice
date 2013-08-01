package com.me.mycoolgame.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player {

	public enum State {
		IDLE, WALKING, DEAD
	}
	
	public enum Direction {
		NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST
	}
	
	private float width = 1f;
	private float height = 1f;
	
	private Vector2 position = new Vector2();
	private Vector2 velocity = new Vector2();
	private float speed = 50f;
	private Rectangle bounds = new Rectangle();
	private State state = State.IDLE;
	private Direction facingDirection = Direction.NORTH;
	private float stateTime = 0;
	
	public Player(Vector2 position) {
		this.position = position;
		this.bounds.x = position.x;
		this.bounds.y = position.y;
		this.bounds.width = width;
		this.bounds.height = height;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
		this.bounds.x = position.x;
		this.bounds.y = position.y;
	}
	
	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}
	
	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
	
	public Direction getFacingDirection() {
		return facingDirection;
	}

	public void setFacingDirection(Direction direction) {
		this.facingDirection = direction;
	}

	public float getStateTime() {
		return stateTime;
	}

	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}
	
	public void update(float delta) {
		this.stateTime += delta;
		position.add(velocity.x * delta, velocity.y * delta);
	}
	
}

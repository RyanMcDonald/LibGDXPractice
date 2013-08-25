package com.me.mycoolgame.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.me.mycoolgame.controller.skill.SkillController;

public abstract class Player {

	public enum State {
		IDLE, WALKING, DEAD
	}
	
	public enum Direction {
		NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST
	}
	
	private float width = 32f;
	private float height = 32f;
	
	private Vector2 position = new Vector2();
	private Vector2 velocity = new Vector2();
	private float speed = 100f;
	private Rectangle bounds = new Rectangle();
	private State state = State.IDLE;
	private Direction facingDirection = Direction.NORTH;
	private float stateTime = 0;
	
	private Array<SkillController> skillControllers;
	
	public Player(Vector2 position) {
		this.position = position;
		this.bounds.x = position.x;
		this.bounds.y = position.y;
		this.bounds.width = width;
		this.bounds.height = height;
		
		skillControllers = new Array<SkillController>();
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
		this.bounds.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
		this.bounds.height = height;
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
	
	public Array<SkillController> getSkillControllers() {
		return skillControllers;
	}

	public void setSkillControllers(Array<SkillController> skillControllers) {
		this.skillControllers = skillControllers;
	}

	public void update(float delta) {
		this.stateTime += delta;
		position.add(velocity.x * delta, velocity.y * delta);
	}
	
	public abstract String getIdleNorthImage();
	public abstract String getIdleNortheastImage();
	public abstract String getIdleEastImage();
	public abstract String getIdleSoutheastImage();
	public abstract String getIdleSouthImage();
	public abstract String getIdleSouthwestImage();
	public abstract String getIdleWestImage();
	public abstract String getIdleNorthwestImage();
	
	public abstract void setIdleNorthImage(String idleNorthImage);
	public abstract void setIdleNortheastImage(String idleNortheastImage);
	public abstract void setIdleEastImage(String idleEastImage);
	public abstract void setIdleSoutheastImage(String idleSoutheastImage);
	public abstract void setIdleSouthImage(String idleSouthImage);
	public abstract void setIdleSouthwestImage(String idleSouthwestImage);
	public abstract void setIdleWestImage(String idleWestImage);
	public abstract void setIdleNorthwestImage(String idleNorthwestImage);
	
	public abstract Array<String> getWalkNorthImages();
	public abstract Array<String> getWalkNortheastImages();
	public abstract Array<String> getWalkEastImages();
	public abstract Array<String> getWalkSoutheastImages();
	public abstract Array<String> getWalkSouthImages();
	public abstract Array<String> getWalkSouthwestImages();
	public abstract Array<String> getWalkWestImages();
	public abstract Array<String> getWalkNorthwestImages();

	public abstract void setWalkNorthImages(Array<String> walkNorthImages);
	public abstract void setWalkNortheastImages(Array<String> walkNortheastImages);
	public abstract void setWalkEastImages(Array<String> walkEastImages);
	public abstract void setWalkSoutheastImages(Array<String> walkSoutheastImages);
	public abstract void setWalkSouthImages(Array<String> walkSouthImages);
	public abstract void setWalkSouthwestImages(Array<String> walkSouthwestImages);
	public abstract void setWalkWestImages(Array<String> walkWestImages);
	public abstract void setWalkNorthwestImages(Array<String> walkNorthwestImages);
	
	public abstract void shoot(World world);
	
}

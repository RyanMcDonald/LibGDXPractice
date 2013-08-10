package com.me.mycoolgame.model;

import com.badlogic.gdx.math.Vector2;
import com.me.mycoolgame.model.Player.Direction;

public class Projectile extends SkillEffect {

	private Direction shootingDirection = Direction.NORTH;
	
	// Keep track of the initial position so we know how far the projectile has travelled
	private Vector2 initialPosition;
	
	private int travelDistancePixels = 200;
	
	public Projectile(Vector2 position, Direction shootingDirection) {
		super(position);
		
		this.shootingDirection = shootingDirection;
		initialPosition = position.cpy();
		
		setImage("images/fireball.png");
		
		setWidth(32f);
		setHeight(32f);
		
		setSpeed(200f);
	}
	
	public void update(float delta) {
		super.update(delta);
		
		// Check if the projectile has gone its maximum distance
		if (getPosition().dst(initialPosition) > travelDistancePixels) {
			reset();
		}
		
		// Set velocity based on the direction it's shooting
		switch (shootingDirection) {
		case NORTH:
			getVelocity().x = 0;
			getVelocity().y = getSpeed();
			break;
		case NORTHEAST:
			getVelocity().x = getSpeed();
			getVelocity().y = getSpeed();
			break;
		case EAST:
			getVelocity().x = getSpeed();
			getVelocity().y = 0;
			break;
		case SOUTHEAST:
			getVelocity().x = getSpeed();
			getVelocity().y = -getSpeed();
			break;
		case SOUTH:
			getVelocity().x = 0;
			getVelocity().y = -getSpeed();
			break;
		case SOUTHWEST:
			getVelocity().x = -getSpeed();
			getVelocity().y = -getSpeed();
			break;
		case WEST:
			getVelocity().x = -getSpeed();
			getVelocity().y = 0;
			break;
		case NORTHWEST:
			getVelocity().x = -getSpeed();
			getVelocity().y = getSpeed();
			break;
		}
		
		getPosition().add(getVelocity().x * delta, getVelocity().y * delta);
	}
	
	private void reset() {
		//setState(State.DONE);
		setState(State.READY);
	}
	
	public Direction getShootingDirection() {
		return shootingDirection;
	}

	public void setShootingDirection(Direction shootingDirection) {
		this.shootingDirection = shootingDirection;
	}

	public Vector2 getInitialPosition() {
		return initialPosition;
	}

	public void setInitialPosition(Vector2 initialPosition) {
		this.initialPosition = initialPosition;
	}

	public int getTravelDistancePixels() {
		return travelDistancePixels;
	}

	public void setTravelDistancePixels(int travelDistancePixels) {
		this.travelDistancePixels = travelDistancePixels;
	}
	
}

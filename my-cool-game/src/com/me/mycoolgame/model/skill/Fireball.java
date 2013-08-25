package com.me.mycoolgame.model.skill;

import com.badlogic.gdx.math.Vector2;
import com.me.mycoolgame.model.Skill;
import com.me.mycoolgame.model.Player.Direction;

public class Fireball extends Skill {

	private Direction shootingDirection = Direction.NORTH;
	
	// Keep track of the initial position so we know how far the projectile has travelled
	private Vector2 initialPosition;
	
	private int travelDistancePixels = 200;
	
	public Fireball(Vector2 position, Direction shootingDirection) {
		super(position);
		
		this.shootingDirection = shootingDirection;
		initialPosition = position.cpy();
		
		setImage("images/fireball.png");
		
		setWidth(32f);
		setHeight(32f);
		
		setSpeed(200f);

		setState(State.ACTIVE);
	}
	
	public void update(float delta) {
		super.update(delta);
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

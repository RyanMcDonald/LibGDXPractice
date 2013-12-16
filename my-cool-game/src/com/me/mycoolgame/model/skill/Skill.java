package com.me.mycoolgame.model.skill;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Skill {

	public enum State {
		READY, ACTIVE, DONE, COLLIDING
	}
	
	private float width = 0;
	private float height = 0;
	private Rectangle bounds = new Rectangle();
	
	private Vector2 position = new Vector2();
	private Vector2 velocity = new Vector2();
	private float speed = 0;
	private State state;
	private float stateTime = 0;

	private Float cooldown;
	
	private Animation activeAnimation;
	private Animation collidingAnimation;

	private Boolean texturesLoaded = false;
	
	public Skill(Vector2 position) {
		setPosition(position);
		
		this.bounds.width = getWidth();
		this.bounds.height = getHeight();
		
		this.state = State.READY;
	}

	public void update(float delta) {
		this.stateTime += delta;

		// Calculate the new position by multiplying the velocity by the time since the last
		// frame was updated, then adding it to the current position.
		setPosition(position.add(velocity.scl(delta)));
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

	/**
	 * Sets the position based on the given vector2. We want position to indicate the center of the
	 * image, so calculate it based on the width and height.
	 * 
	 * This method also updates the bounds of the object so it stays current.
	 * 
	 * @param position
	 */
	public void setPosition(Vector2 position) {
		this.position.x = position.x;
		this.position.y = position.y;
		
		// Subtract the width and height from the bounds because the position represents the center of the object
		this.bounds.x = position.x - (getWidth() / 2);
		this.bounds.y = position.y - (getHeight() / 2);
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

	public State getState() {
		return state;
	}

	public void setState(State state) {
		// If we changed states, reset the stateTime
		if (!this.state.equals(state)) {
			stateTime = 0;
		}
		this.state = state;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
		
		this.position.x = bounds.x + (getWidth() / 2);
		this.position.y = bounds.y + (getHeight() / 2);
	}

	public float getStateTime() {
		return stateTime;
	}

	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}

	public Float getCooldown() {
		return cooldown;
	}

	public void setCooldown(Float cooldown) {
		this.cooldown = cooldown;
	}

	public Animation getActiveAnimation() {
		return activeAnimation;
	}

	public void setActiveAnimation(Animation animation) {
		this.activeAnimation = animation;
	}
	
	public Animation getCollidingAnimation() {
		return collidingAnimation;
	}

	public void setCollidingAnimation(Animation collidingAnimation) {
		this.collidingAnimation = collidingAnimation;
	}

	public Boolean getTexturesLoaded() {
		return texturesLoaded;
	}

	public void setTexturesLoaded(Boolean texturesLoaded) {
		this.texturesLoaded = texturesLoaded;
	}

	/**
	 * Loads the textures for the skill by grabbing the images from the atlas and setting up the
	 * animations. This method will return if the textures have already been loaded.
	 * @param atlas
	 */
	public abstract void loadTextures(TextureAtlas atlas);
	
}

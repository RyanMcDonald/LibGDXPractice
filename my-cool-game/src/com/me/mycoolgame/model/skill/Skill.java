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
		this.position = position;
		this.bounds.x = position.x;
		this.bounds.y = position.y;
		this.bounds.width = width;
		this.bounds.height = height;
		
		this.state = State.READY;
	}

	public void update(float delta) {
		this.stateTime += delta;
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

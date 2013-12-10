package com.me.mycoolgame.model;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.me.mycoolgame.controller.skill.SkillController;
import com.me.mycoolgame.model.skill.Skill;

public abstract class Player {

	public enum State {
		IDLE, WALKING, ACTING, DEAD
	}
	
	public enum Direction {
		NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST
	}
	
	private Map<String, Float> cooldowns = new HashMap<String, Float>();

	private TextureRegion idleNorthTextureRegion;
	private TextureRegion idleNortheastTextureRegion;
	private TextureRegion idleEastTextureRegion;
	private TextureRegion idleSoutheastTextureRegion;
	private TextureRegion idleSouthTextureRegion;
	private TextureRegion idleSouthwestTextureRegion;
	private TextureRegion idleWestTextureRegion;
	private TextureRegion idleNorthwestTextureRegion;

	private Animation walkNorthAnimation;
	private Animation walkNortheastAnimation;
	private Animation walkEastAnimation;
	private Animation walkSoutheastAnimation;
	private Animation walkSouthAnimation;
	private Animation walkSouthwestAnimation;
	private Animation walkWestAnimation;
	private Animation walkNorthwestAnimation;
	
	private Animation actingAnimation;
	private float actingTime = 0.5f;

	private float width = 32f;
	private float height = 32f;
	
	private Vector2 position = new Vector2();
	private Vector2 velocity = new Vector2();
	private float speed = 100f;
	private Rectangle bounds = new Rectangle();
	private State state = State.IDLE;
	private Direction facingDirection = Direction.NORTH;
	private float stateTime = 0;

	private Array<Skill> skills;
	private Array<SkillController> skillControllers;

	public Player(Vector2 position) {
		this.position = position;
		this.bounds.x = position.x;
		this.bounds.y = position.y;
		this.bounds.width = width;
		this.bounds.height = height;
		
		skills = new Array<Skill>();
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
		// If we changed states, reset the stateTime
		if (!this.state.equals(state)) {
			stateTime = 0;
		}
		
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
	
	public Array<Skill> getSkills() {
		return skills;
	}

	public void setSkills(Array<Skill> skills) {
		this.skills = skills;
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

	public TextureRegion getIdleNorthTextureRegion() {
		return idleNorthTextureRegion;
	}

	public void setIdleNorthTextureRegion(TextureRegion idleNorthTextureRegion) {
		this.idleNorthTextureRegion = idleNorthTextureRegion;
	}

	public TextureRegion getIdleNortheastTextureRegion() {
		return idleNortheastTextureRegion;
	}

	public void setIdleNortheastTextureRegion(
			TextureRegion idleNortheastTextureRegion) {
		this.idleNortheastTextureRegion = idleNortheastTextureRegion;
	}

	public TextureRegion getIdleEastTextureRegion() {
		return idleEastTextureRegion;
	}

	public void setIdleEastTextureRegion(TextureRegion idleEastTextureRegion) {
		this.idleEastTextureRegion = idleEastTextureRegion;
	}

	public TextureRegion getIdleSoutheastTextureRegion() {
		return idleSoutheastTextureRegion;
	}

	public void setIdleSoutheastTextureRegion(
			TextureRegion idleSoutheastTextureRegion) {
		this.idleSoutheastTextureRegion = idleSoutheastTextureRegion;
	}

	public TextureRegion getIdleSouthTextureRegion() {
		return idleSouthTextureRegion;
	}

	public void setIdleSouthTextureRegion(TextureRegion idleSouthTextureRegion) {
		this.idleSouthTextureRegion = idleSouthTextureRegion;
	}

	public TextureRegion getIdleSouthwestTextureRegion() {
		return idleSouthwestTextureRegion;
	}

	public void setIdleSouthwestTextureRegion(
			TextureRegion idleSouthwestTextureRegion) {
		this.idleSouthwestTextureRegion = idleSouthwestTextureRegion;
	}

	public TextureRegion getIdleWestTextureRegion() {
		return idleWestTextureRegion;
	}

	public void setIdleWestTextureRegion(TextureRegion idleWestTextureRegion) {
		this.idleWestTextureRegion = idleWestTextureRegion;
	}

	public TextureRegion getIdleNorthwestTextureRegion() {
		return idleNorthwestTextureRegion;
	}

	public void setIdleNorthwestTextureRegion(
			TextureRegion idleNorthwestTextureRegion) {
		this.idleNorthwestTextureRegion = idleNorthwestTextureRegion;
	}
	
	public Animation getWalkNorthAnimation() {
		return walkNorthAnimation;
	}

	public void setWalkNorthAnimation(Animation walkNorthAnimation) {
		this.walkNorthAnimation = walkNorthAnimation;
	}

	public Animation getWalkNortheastAnimation() {
		return walkNortheastAnimation;
	}

	public void setWalkNortheastAnimation(Animation walkNortheastAnimation) {
		this.walkNortheastAnimation = walkNortheastAnimation;
	}

	public Animation getWalkEastAnimation() {
		return walkEastAnimation;
	}

	public void setWalkEastAnimation(Animation walkEastAnimation) {
		this.walkEastAnimation = walkEastAnimation;
	}

	public Animation getWalkSoutheastAnimation() {
		return walkSoutheastAnimation;
	}

	public void setWalkSoutheastAnimation(Animation walkSoutheastAnimation) {
		this.walkSoutheastAnimation = walkSoutheastAnimation;
	}

	public Animation getWalkSouthAnimation() {
		return walkSouthAnimation;
	}

	public void setWalkSouthAnimation(Animation walkSouthAnimation) {
		this.walkSouthAnimation = walkSouthAnimation;
	}

	public Animation getWalkSouthwestAnimation() {
		return walkSouthwestAnimation;
	}

	public void setWalkSouthwestAnimation(Animation walkSouthwestAnimation) {
		this.walkSouthwestAnimation = walkSouthwestAnimation;
	}

	public Animation getWalkWestAnimation() {
		return walkWestAnimation;
	}

	public void setWalkWestAnimation(Animation walkWestAnimation) {
		this.walkWestAnimation = walkWestAnimation;
	}

	public Animation getWalkNorthwestAnimation() {
		return walkNorthwestAnimation;
	}

	public void setWalkNorthwestAnimation(Animation walkNorthwestAnimation) {
		this.walkNorthwestAnimation = walkNorthwestAnimation;
	}

	public Animation getActingAnimation() {
		return actingAnimation;
	}

	public void setActingAnimation(Animation actingAnimation) {
		this.actingAnimation = actingAnimation;
	}

	public float getActingTime() {
		return actingTime;
	}

	public void setActingTime(float actingTime) {
		this.actingTime = actingTime;
	}

	public abstract void loadTextures(TextureAtlas atlas);
	
	public Map<String, Float> getCooldowns() {
		return cooldowns;
	}

	public void setCooldowns(Map<String, Float> cooldowns) {
		this.cooldowns = cooldowns;
	}

	public abstract void shootProjectile(World world, Vector2 destination);
	
}

package com.me.mycoolgame.controller.skill;

import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.me.mycoolgame.model.World;
import com.me.mycoolgame.model.skill.Skill;

public abstract class SkillController {

	private World world;
	private Skill skill;
	
	public SkillController(World world, Skill skill) {
		this.world = world;
		this.skill = skill;
	}
	
	public void update(float delta) {
		// Determine what the skill's next position/state should be
		if (skill.getState().equals(Skill.State.ACTIVE)) {
			if (checkCollisionsWithObjects(delta)) {
				collision();
			}
			
			checkOutOfBounds();
		}
		
		// Update the skill with the newly calculated information
		skill.update(delta);
	}
	
	public World getWorld() {
		return world;
	}
	
	public void setWorld(World world) {
		this.world = world;
	}
	
	public Skill getSkill() {
		return skill;
	}
	
	public void setSkill(Skill skill) {
		this.skill = skill;
	}
	
	private void checkOutOfBounds() {
		Boolean isOutOfBounds = false;
		
		float posX = skill.getPosition().x;
		float posY = skill.getPosition().y;
		
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
			isOutOfBounds = true;
		}

		if (posX > mapWidth - skill.getWidth()) {
			newPosX = mapWidth - skill.getWidth();
			isOutOfBounds = true;
		}
		
		if (posY < 0) {
			newPosY = 0;
			isOutOfBounds = true;
		}
		
		if (posY > mapHeight - skill.getHeight()) {
			newPosY = mapHeight - skill.getHeight();
			isOutOfBounds = true;
		}

		if (isOutOfBounds) {
			outOfBounds(newPosX, newPosY);
		}
		
		//skill.setPosition(new Vector2(newPosX, newPosY));
	}
	
	/**
	 * Checks if the skill would collide with any objects in the Collision layer of the Tiled map, and
	 * adjusts its position accordingly.
	 * @param delta
	 */
	private Boolean checkCollisionsWithObjects(float delta) {
		Boolean collides = false;
		
		// Get the skill's velocity in terms of frame units, so that we know how far they will have gone.
		skill.getVelocity().scl(delta);
		
		Rectangle skillRect = new Rectangle();
		
		skillRect.set(skill.getPosition().x, skill.getPosition().y, skill.getWidth(), skill.getHeight());
		
		// TODO: Possibly move this outside of this method so it's not instantiated on every frame
		MapObjects objects = world.getMap().getLayers().get("Collision").getObjects();
		for (int i = 0; i < objects.getCount(); i++) {
			RectangleMapObject objectRect = (RectangleMapObject) objects.get(i);
			Rectangle rect = objectRect.getRectangle();
			
			// If the skill will collide with an object in the next x position calculation, set x velocity to 0.
			skillRect.x += skill.getVelocity().x;
			if(skillRect.overlaps(rect)) {
				collides = true;
			}
			
			// Reset the x position of the playerRect so we can use it in the y-axis collision calculation
			skillRect.x = skill.getPosition().x;
			
			// If the skill will collide with an object in the next y position calculation, set y velocity to 0.
			skillRect.y += skill.getVelocity().y;
			if(skillRect.overlaps(rect)) {
				collides = true;
			}
			
			skillRect.y = skill.getPosition().y;
		}
		
		// Unscale the velocity by the inverse delta time
		skill.getVelocity().scl(1/delta);
		
		return collides;
	}
	
	abstract void collision();
	
	abstract void outOfBounds(float newPosX, float newPosY);
	
}

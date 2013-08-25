package com.me.mycoolgame.model;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.me.mycoolgame.controller.skill.FireballController;
import com.me.mycoolgame.model.skill.Fireball;

public class MagePlayer extends Player {
	
	public MagePlayer(Vector2 position) {
		super(position);
		
		getSkills().add(new Fireball(getPosition().cpy(), getFacingDirection()));
	}

	public void update(float delta) {
		super.update(delta);
	}

	public void shoot(World world) {
		Fireball fireball = new Fireball(getPosition().cpy(), getFacingDirection());
		FireballController controller = new FireballController(world, fireball);
		getSkillControllers().add(controller);
	}

	@Override
	public void loadTextures(TextureAtlas atlas) {
		setIdleNorthTextureRegion(atlas.findRegion("player-idle-north"));
		setIdleNortheastTextureRegion(atlas.findRegion("player-idle-northeast"));
		setIdleEastTextureRegion(atlas.findRegion("player-idle-east"));
		setIdleSoutheastTextureRegion(atlas.findRegion("player-idle-southeast"));
		setIdleSouthTextureRegion(atlas.findRegion("player-idle-south"));
		setIdleSouthwestTextureRegion(atlas.findRegion("player-idle-southwest"));
		setIdleWestTextureRegion(atlas.findRegion("player-idle-west"));
		setIdleNorthwestTextureRegion(atlas.findRegion("player-idle-northwest"));

		Array<String> walkNorthImages = new Array<String>();
		walkNorthImages.add("player-walking-north-1");
		walkNorthImages.add("player-idle-north");
		walkNorthImages.add("player-walking-north-2");

		Array<String> walkNortheastImages = new Array<String>();
		walkNortheastImages.add("player-walking-northeast-1");
		walkNortheastImages.add("player-idle-northeast");
		walkNortheastImages.add("player-walking-northeast-2");
		
		Array<String> walkEastImages = new Array<String>();
		walkEastImages.add("player-walking-east-1");
		walkEastImages.add("player-idle-east");
		walkEastImages.add("player-walking-east-2");
		
		Array<String> walkSoutheastImages = new Array<String>();
		walkSoutheastImages.add("player-walking-southeast-1");
		walkSoutheastImages.add("player-idle-southeast");
		walkSoutheastImages.add("player-walking-southeast-2");
		
		Array<String> walkSouthImages = new Array<String>();
		walkSouthImages.add("player-walking-south-1");
		walkSouthImages.add("player-idle-south");
		walkSouthImages.add("player-walking-south-2");
		
		Array<String> walkSouthwestImages = new Array<String>();
		walkSouthwestImages.add("player-walking-southwest-1");
		walkSouthwestImages.add("player-idle-southwest");
		walkSouthwestImages.add("player-walking-southwest-2");
		
		Array<String> walkWestImages = new Array<String>();
		walkWestImages.add("player-walking-west-1");
		walkWestImages.add("player-idle-west");
		walkWestImages.add("player-walking-west-2");
		
		Array<String> walkNorthwestImages = new Array<String>();
		walkNorthwestImages.add("player-walking-northwest-1");
		walkNorthwestImages.add("player-idle-northwest");
		walkNorthwestImages.add("player-walking-northwest-2");

		TextureRegion[] walkNorthFrames = new TextureRegion[walkNorthImages.size];
		TextureRegion[] walkNortheastFrames = new TextureRegion[walkNortheastImages.size];
		TextureRegion[] walkEastFrames = new TextureRegion[walkEastImages.size];
		TextureRegion[] walkSoutheastFrames = new TextureRegion[walkSoutheastImages.size];
		TextureRegion[] walkSouthFrames = new TextureRegion[walkSouthImages.size];
		TextureRegion[] walkSouthwestFrames = new TextureRegion[walkSouthwestImages.size];
		TextureRegion[] walkWestFrames = new TextureRegion[walkWestImages.size];
		TextureRegion[] walkNorthwestFrames = new TextureRegion[walkNorthwestImages.size];

		for (int i = 0; i < walkNorthImages.size; i++) {
			walkNorthFrames[i] = atlas.findRegion(walkNorthImages.get(i));
		}

		for (int i = 0; i < walkNortheastImages.size; i++) {
			walkNortheastFrames[i] = atlas.findRegion(walkNortheastImages.get(i));
		}

		for (int i = 0; i < walkEastImages.size; i++) {
			walkEastFrames[i] = atlas.findRegion(walkEastImages.get(i));
		}

		for (int i = 0; i < walkSoutheastImages.size; i++) {
			walkSoutheastFrames[i] = atlas.findRegion(walkSoutheastImages.get(i));
		}

		for (int i = 0; i < walkSouthImages.size; i++) {
			walkSouthFrames[i] = atlas.findRegion(walkSouthImages.get(i));
		}

		for (int i = 0; i < walkSouthwestImages.size; i++) {
			walkSouthwestFrames[i] = atlas.findRegion(walkSouthwestImages.get(i));
		}

		for (int i = 0; i < walkWestImages.size; i++) {
			walkWestFrames[i] = atlas.findRegion(walkWestImages.get(i));
		}

		for (int i = 0; i < walkNorthwestImages.size; i++) {
			walkNorthwestFrames[i] = atlas.findRegion(walkNorthwestImages.get(i));
		}

		float walkingFrameDuration = getSpeed() / 800;
		setWalkNorthAnimation(new Animation(walkingFrameDuration, walkNorthFrames));
		setWalkNortheastAnimation(new Animation(walkingFrameDuration, walkNortheastFrames));
		setWalkEastAnimation(new Animation(walkingFrameDuration, walkEastFrames));
		setWalkSoutheastAnimation(new Animation(walkingFrameDuration, walkSoutheastFrames));
		setWalkSouthAnimation(new Animation(walkingFrameDuration, walkSouthFrames));
		setWalkSouthwestAnimation(new Animation(walkingFrameDuration, walkSouthwestFrames));
		setWalkWestAnimation(new Animation(walkingFrameDuration, walkWestFrames));
		setWalkNorthwestAnimation(new Animation(walkingFrameDuration, walkNorthwestFrames));

//		for (Skill skill : getSkills()) {
//			skill.loadTextures(atlas);
//		}
	}

}

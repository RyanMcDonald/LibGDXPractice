package com.me.mycoolgame.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.me.mycoolgame.controller.skill.FireballController;
import com.me.mycoolgame.model.skill.Fireball;

public class MagePlayer extends Player {

	private String idleNorthImage;
	private String idleNortheastImage;
	private String idleEastImage;
	private String idleSoutheastImage;
	private String idleSouthImage;
	private String idleSouthwestImage;
	private String idleWestImage;
	private String idleNorthwestImage;
	
	private Array<String> walkNorthImages;
	private Array<String> walkNortheastImages;
	private Array<String> walkEastImages;
	private Array<String> walkSoutheastImages;
	private Array<String> walkSouthImages;
	private Array<String> walkSouthwestImages;
	private Array<String> walkWestImages;
	private Array<String> walkNorthwestImages;
	
	public MagePlayer(Vector2 position) {
		super(position);
		
		idleNorthImage = "player-idle-north";
		idleNortheastImage = "player-idle-northeast";
		idleEastImage = "player-idle-east";
		idleSoutheastImage = "player-idle-southeast";
		idleSouthImage = "player-idle-south";
		idleSouthwestImage = "player-idle-southwest";
		idleWestImage = "player-idle-west";
		idleNorthwestImage = "player-idle-northwest";
		
		walkNorthImages = new Array<String>();
		walkNorthImages.add("player-walking-north-1");
		walkNorthImages.add(idleNorthImage);
		walkNorthImages.add("player-walking-north-2");
		
		walkNortheastImages = new Array<String>();
		walkNortheastImages.add("player-walking-northeast-1");
		walkNortheastImages.add(idleNortheastImage);
		walkNortheastImages.add("player-walking-northeast-2");
		
		walkEastImages = new Array<String>();
		walkEastImages.add("player-walking-east-1");
		walkEastImages.add(idleEastImage);
		walkEastImages.add("player-walking-east-2");
		
		walkSoutheastImages = new Array<String>();
		walkSoutheastImages.add("player-walking-southeast-1");
		walkSoutheastImages.add(idleSoutheastImage);
		walkSoutheastImages.add("player-walking-southeast-2");
		
		walkSouthImages = new Array<String>();
		walkSouthImages.add("player-walking-south-1");
		walkSouthImages.add(idleSouthImage);
		walkSouthImages.add("player-walking-south-2");
		
		walkSouthwestImages = new Array<String>();
		walkSouthwestImages.add("player-walking-southwest-1");
		walkSouthwestImages.add(idleSouthwestImage);
		walkSouthwestImages.add("player-walking-southwest-2");
		
		walkWestImages = new Array<String>();
		walkWestImages.add("player-walking-west-1");
		walkWestImages.add(idleWestImage);
		walkWestImages.add("player-walking-west-2");
		
		walkNorthwestImages = new Array<String>();
		walkNorthwestImages.add("player-walking-northwest-1");
		walkNorthwestImages.add(idleNorthwestImage);
		walkNorthwestImages.add("player-walking-northwest-2");
	}

	public void update(float delta) {
		super.update(delta);
	}

	public void shoot(World world) {
		Fireball fireball = new Fireball(getPosition().cpy(), getFacingDirection());
		FireballController controller = new FireballController(world, fireball);
		getSkillControllers().add(controller);
	}
	
	public String getIdleNorthImage() {
		return idleNorthImage;
	}

	public void setIdleNorthImage(String idleNorthImage) {
		this.idleNorthImage = idleNorthImage;
	}

	public String getIdleNortheastImage() {
		return idleNortheastImage;
	}

	public void setIdleNortheastImage(String idleNortheastImage) {
		this.idleNortheastImage = idleNortheastImage;
	}

	public String getIdleEastImage() {
		return idleEastImage;
	}

	public void setIdleEastImage(String idleEastImage) {
		this.idleEastImage = idleEastImage;
	}

	public String getIdleSoutheastImage() {
		return idleSoutheastImage;
	}

	public void setIdleSoutheastImage(String idleSoutheastImage) {
		this.idleSoutheastImage = idleSoutheastImage;
	}

	public String getIdleSouthImage() {
		return idleSouthImage;
	}

	public void setIdleSouthImage(String idleSouthImage) {
		this.idleSouthImage = idleSouthImage;
	}

	public String getIdleSouthwestImage() {
		return idleSouthwestImage;
	}

	public void setIdleSouthwestImage(String idleSouthwestImage) {
		this.idleSouthwestImage = idleSouthwestImage;
	}

	public String getIdleWestImage() {
		return idleWestImage;
	}

	public void setIdleWestImage(String idleWestImage) {
		this.idleWestImage = idleWestImage;
	}

	public String getIdleNorthwestImage() {
		return idleNorthwestImage;
	}

	public void setIdleNorthwestImage(String idleNorthwestImage) {
		this.idleNorthwestImage = idleNorthwestImage;
	}

	public Array<String> getWalkNorthImages() {
		return walkNorthImages;
	}

	public void setWalkNorthImages(Array<String> walkNorthImages) {
		this.walkNorthImages = walkNorthImages;
	}

	public Array<String> getWalkNortheastImages() {
		return walkNortheastImages;
	}

	public void setWalkNortheastImages(Array<String> walkNortheastImages) {
		this.walkNortheastImages = walkNortheastImages;
	}

	public Array<String> getWalkEastImages() {
		return walkEastImages;
	}

	public void setWalkEastImages(Array<String> walkEastImages) {
		this.walkEastImages = walkEastImages;
	}

	public Array<String> getWalkSoutheastImages() {
		return walkSoutheastImages;
	}

	public void setWalkSoutheastImages(Array<String> walkSoutheastImages) {
		this.walkSoutheastImages = walkSoutheastImages;
	}

	public Array<String> getWalkSouthImages() {
		return walkSouthImages;
	}

	public void setWalkSouthImages(Array<String> walkSouthImages) {
		this.walkSouthImages = walkSouthImages;
	}

	public Array<String> getWalkSouthwestImages() {
		return walkSouthwestImages;
	}

	public void setWalkSouthwestImages(Array<String> walkSouthwestImages) {
		this.walkSouthwestImages = walkSouthwestImages;
	}

	public Array<String> getWalkWestImages() {
		return walkWestImages;
	}

	public void setWalkWestImages(Array<String> walkWestImages) {
		this.walkWestImages = walkWestImages;
	}

	public Array<String> getWalkNorthwestImages() {
		return walkNorthwestImages;
	}

	public void setWalkNorthwestImages(Array<String> walkNorthwestImages) {
		this.walkNorthwestImages = walkNorthwestImages;
	}

}

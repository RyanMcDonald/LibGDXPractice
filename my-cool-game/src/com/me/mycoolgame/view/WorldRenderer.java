package com.me.mycoolgame.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.me.mycoolgame.model.Player;
import com.me.mycoolgame.model.Player.State;
import com.me.mycoolgame.model.World;

public class WorldRenderer {

	private static final float RUNNING_FRAME_DURATION = 0.2f;
	
	private World world;
	private OrthographicCamera camera;
	private SpriteBatch spriteBatch;
	
	private TextureRegion backgroundRegion;
	
	private TextureAtlas atlas;
	private TextureRegion playerIdleNorth;
	private TextureRegion playerIdleNortheast;
	private TextureRegion playerIdleEast;
	private TextureRegion playerIdleSoutheast;
	private TextureRegion playerIdleSouth;
	private TextureRegion playerIdleSouthwest;
	private TextureRegion playerIdleWest;
	private TextureRegion playerIdleNorthwest;
	
	private Animation walkNorthAnimation;
	private Animation walkNortheastAnimation;
	private Animation walkEastAnimation;
	private Animation walkSoutheastAnimation;
	private Animation walkSouthAnimation;
	private Animation walkSouthwestAnimation;
	private Animation walkWestAnimation;
	private Animation walkNorthwestAnimation;
	
	public WorldRenderer(World world, SpriteBatch spriteBatch) {
		this.world = world;
		this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.camera.position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0);
		this.spriteBatch = spriteBatch;
		this.atlas = new TextureAtlas(Gdx.files.internal("images/textures/textures.pack"));
	}
	
	public void render() {
		Vector2 playerPosition = world.getPlayer().getPosition();
		
		if (playerPosition.x != camera.position.x) {
			camera.position.x = playerPosition.x;
		}
		
		if (playerPosition.y != camera.position.y) {
			camera.position.y = playerPosition.y;
		}
		
		camera.update();
		spriteBatch.setProjectionMatrix(camera.combined);
		renderBackground();
		renderObjects();
	}
	
	private void renderBackground () {
		backgroundRegion = new TextureRegion(new Texture(Gdx.files.internal(world.getLevel().getBackgroundFilename())));
		
		spriteBatch.disableBlending();
		spriteBatch.begin();
		spriteBatch.draw(backgroundRegion, Gdx.graphics.getWidth() / 2 - backgroundRegion.getRegionWidth() / 2, Gdx.graphics.getHeight() / 2 - backgroundRegion.getRegionHeight() / 2);
		spriteBatch.end();
	}
	
	private void renderObjects() {
		spriteBatch.enableBlending();
		spriteBatch.begin();
		
		renderPlayer();
		
		spriteBatch.end();
	}
	
	private void renderPlayer() {
		playerIdleNorth = atlas.findRegion("player-idle-north");
		playerIdleNortheast = atlas.findRegion("player-idle-northeast");
		playerIdleEast = atlas.findRegion("player-idle-east");
		playerIdleSoutheast = atlas.findRegion("player-idle-southeast");
		playerIdleSouth = atlas.findRegion("player-idle-south");
		playerIdleSouthwest = atlas.findRegion("player-idle-southwest");
		playerIdleWest = atlas.findRegion("player-idle-west");
		playerIdleNorthwest = atlas.findRegion("player-idle-northwest");

		TextureRegion[] walkNorthFrames = new TextureRegion[6];
		TextureRegion[] walkNortheastFrames = new TextureRegion[6];
		TextureRegion[] walkEastFrames = new TextureRegion[6];
		TextureRegion[] walkSoutheastFrames = new TextureRegion[6];
		TextureRegion[] walkSouthFrames = new TextureRegion[6];
		TextureRegion[] walkSouthwestFrames = new TextureRegion[6];
		TextureRegion[] walkWestFrames = new TextureRegion[6];
		TextureRegion[] walkNorthwestFrames = new TextureRegion[6];
		
		// We want the idle images to be the first frame of the animations
		walkNorthFrames[0] = atlas.findRegion("player-idle-north");
		walkNortheastFrames[0] = atlas.findRegion("player-idle-northeast");
		walkEastFrames[0] = atlas.findRegion("player-idle-east");
		walkSoutheastFrames[0] = atlas.findRegion("player-idle-southeast");
		walkSouthFrames[0] = atlas.findRegion("player-idle-south");
		walkSouthwestFrames[0] = atlas.findRegion("player-idle-southwest");
		walkWestFrames[0] = atlas.findRegion("player-idle-west");
		walkNorthwestFrames[0] = atlas.findRegion("player-idle-northwest");
		
		for (int i = 1; i < 6; i++) {
			walkNorthFrames[i] = atlas.findRegion("player-walking-north-" + i);
			walkNortheastFrames[i] = atlas.findRegion("player-walking-northeast-" + i);
			walkEastFrames[i] = atlas.findRegion("player-walking-east-" + i);
			walkSoutheastFrames[i] = atlas.findRegion("player-walking-southeast-" + i);
			walkSouthFrames[i] = atlas.findRegion("player-walking-south-" + i);
			walkSouthwestFrames[i] = atlas.findRegion("player-walking-southwest-" + i);
			walkWestFrames[i] = atlas.findRegion("player-walking-west-" + i);
			walkNorthwestFrames[i] = atlas.findRegion("player-walking-northwest-" + i);
		}
		
		walkNorthAnimation = new Animation(RUNNING_FRAME_DURATION, walkNorthFrames);
		walkNortheastAnimation = new Animation(RUNNING_FRAME_DURATION, walkNortheastFrames);
		walkEastAnimation = new Animation(RUNNING_FRAME_DURATION, walkEastFrames);
		walkSoutheastAnimation = new Animation(RUNNING_FRAME_DURATION, walkSoutheastFrames);
		walkSouthAnimation = new Animation(RUNNING_FRAME_DURATION, walkSouthFrames);
		walkSouthwestAnimation = new Animation(RUNNING_FRAME_DURATION, walkSouthwestFrames);
		walkWestAnimation = new Animation(RUNNING_FRAME_DURATION, walkWestFrames);
		walkNorthwestAnimation = new Animation(RUNNING_FRAME_DURATION, walkNorthwestFrames);
		
		Player player = world.getPlayer();
		TextureRegion playerFrame = null;
		
		if (player.getState().equals(State.IDLE)) {
			switch (player.getFacingDirection()) {
			case NORTH:
				playerFrame = playerIdleNorth;
				break;
			case NORTHEAST:
				playerFrame = playerIdleNortheast;
				break;
			case EAST:
				playerFrame = playerIdleEast;
				break;
			case SOUTHEAST:
				playerFrame = playerIdleSoutheast;
				break;
			case SOUTH:
				playerFrame = playerIdleSouth;
				break;
			case SOUTHWEST:
				playerFrame = playerIdleSouthwest;
				break;
			case WEST:
				playerFrame = playerIdleWest;
				break;
			case NORTHWEST:
				playerFrame = playerIdleNorthwest;
				break;
			}
		} else if (player.getState().equals(State.WALKING)) {
			switch (player.getFacingDirection()) {
			case NORTH:
				playerFrame = walkNorthAnimation.getKeyFrame(player.getStateTime(), true);
				break;
			case NORTHEAST:
				playerFrame = walkNortheastAnimation.getKeyFrame(player.getStateTime(), true);
				break;
			case EAST:
				playerFrame = walkEastAnimation.getKeyFrame(player.getStateTime(), true);
				break;
			case SOUTHEAST:
				playerFrame = walkSoutheastAnimation.getKeyFrame(player.getStateTime(), true);
				break;
			case SOUTH:
				playerFrame = walkSouthAnimation.getKeyFrame(player.getStateTime(), true);
				break;
			case SOUTHWEST:
				playerFrame = walkSouthwestAnimation.getKeyFrame(player.getStateTime(), true);
				break;
			case WEST:
				playerFrame = walkWestAnimation.getKeyFrame(player.getStateTime(), true);
				break;
			case NORTHWEST:
				playerFrame = walkNorthwestAnimation.getKeyFrame(player.getStateTime(), true);
				break;
			}
		}
		
		spriteBatch.draw(playerFrame, player.getPosition().x, player.getPosition().y);
		
	}
}

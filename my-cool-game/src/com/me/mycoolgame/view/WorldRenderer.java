package com.me.mycoolgame.view;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.me.mycoolgame.controller.skill.SkillController;
import com.me.mycoolgame.model.Player;
import com.me.mycoolgame.model.Player.State;
import com.me.mycoolgame.model.skill.Skill;
import com.me.mycoolgame.model.World;
import com.me.mycoolgame.util.Assets;

public class WorldRenderer {
	
	private World world;
	private Player player;
	private OrthographicCamera camera;
	private SpriteBatch spriteBatch;
	
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	
	Music backgroundMusic;
	
	private TextureAtlas atlas;
	
	public WorldRenderer(World world, SpriteBatch spriteBatch) {
		this.world = world;
		this.player = world.getPlayer();
		this.camera = new OrthographicCamera();
		camera.setToOrtho(false, 500, 300);
		camera.update();
		
		this.spriteBatch = spriteBatch;
		this.atlas = Assets.manager.get("images/textures/textures.pack", TextureAtlas.class);
		
		loadTextures();

		backgroundMusic = Assets.manager.get("data/music/places_of_soul.mp3", Music.class);
		backgroundMusic.setLooping(true);
		backgroundMusic.play();
	}
	
	public void render() {
		Vector2 playerPosition = world.getPlayer().getPosition();

		// Keep the camera centered on the player
		camera.position.x = playerPosition.x;
		camera.position.y = playerPosition.y;
		
		keepCameraInBounds();
		
		camera.update();
		spriteBatch.setProjectionMatrix(camera.combined);

		// set the tile map renderer view based on what the camera sees and render the map
		renderer.setView(camera);
		
		// Render the "Top" layer after the player, so that it overlaps the player, i.e., when they walk behind treetops.
		renderer.render(new int[] { 0, 1, 3 });

		spriteBatch.enableBlending();
		spriteBatch.begin();
		
		renderPlayer();
		
		// Render objects associated with the player, e.g., skills
		renderPlayerSkills();
		
		spriteBatch.end();
		
		renderer.render(new int[] { 2 });
	}
	
	private void loadTextures() {
		map = Assets.manager.get("images/tilesets/nature/nature.tmx");
		world.setMap(map);
		renderer = new OrthogonalTiledMapRenderer(map, 1f);
		
		player.loadTextures(atlas);
	}

	private void renderPlayer() {
		TextureRegion playerFrame = null;
		
		if (player.getState().equals(State.IDLE)) {
			switch (player.getFacingDirection()) {
			case NORTH:
				playerFrame = player.getIdleNorthTextureRegion();
				break;
			case NORTHEAST:
				playerFrame = player.getIdleNortheastTextureRegion();
				break;
			case EAST:
				playerFrame = player.getIdleEastTextureRegion();
				break;
			case SOUTHEAST:
				playerFrame = player.getIdleSoutheastTextureRegion();
				break;
			case SOUTH:
				playerFrame = player.getIdleSouthTextureRegion();
				break;
			case SOUTHWEST:
				playerFrame = player.getIdleSouthwestTextureRegion();
				break;
			case WEST:
				playerFrame = player.getIdleWestTextureRegion();
				break;
			case NORTHWEST:
				playerFrame = player.getIdleNorthwestTextureRegion();
				break;
			}
		} else if (player.getState().equals(State.WALKING)) {
			switch (player.getFacingDirection()) {
			case NORTH:
				playerFrame = player.getWalkNorthAnimation().getKeyFrame(player.getStateTime(), true);
				break;
			case NORTHEAST:
				playerFrame = player.getWalkNortheastAnimation().getKeyFrame(player.getStateTime(), true);
				break;
			case EAST:
				playerFrame = player.getWalkEastAnimation().getKeyFrame(player.getStateTime(), true);
				break;
			case SOUTHEAST:
				playerFrame = player.getWalkSoutheastAnimation().getKeyFrame(player.getStateTime(), true);
				break;
			case SOUTH:
				playerFrame = player.getWalkSouthAnimation().getKeyFrame(player.getStateTime(), true);
				break;
			case SOUTHWEST:
				playerFrame = player.getWalkSouthwestAnimation().getKeyFrame(player.getStateTime(), true);
				break;
			case WEST:
				playerFrame = player.getWalkWestAnimation().getKeyFrame(player.getStateTime(), true);
				break;
			case NORTHWEST:
				playerFrame = player.getWalkNorthwestAnimation().getKeyFrame(player.getStateTime(), true);
				break;
			}
		}

		spriteBatch.draw(playerFrame, player.getPosition().x, player.getPosition().y);
		
	}
	
	private void renderPlayerSkills() {
		for (SkillController controller : player.getSkillControllers()) {
			Skill skill = controller.getSkill();
			if (skill.getState() == Skill.State.ACTIVE) {
				// Load the textures if we haven't loaded them already
				skill.loadTextures(atlas);
				
				TextureRegion skillFrame = skill.getActiveAnimation().getKeyFrame(skill.getStateTime(), true);
				//Texture effectTexture = skillAnimation.getKeyFrame(skill.getStateTime(), true);
				spriteBatch.draw(skillFrame, skill.getPosition().x, skill.getPosition().y);
			}
		}
	}
	
	private void keepCameraInBounds() {
		// Map width = number of tiles on the x-axis * each tile's width in pixels
		int width = (Integer) renderer.getMap().getProperties().get("width");
		int tileWidth = (Integer) renderer.getMap().getProperties().get("tilewidth");
		int mapWidth = width * tileWidth;
		
		// Map width = number of tiles on the y-axis * each tile's height in pixels
		int height = (Integer) renderer.getMap().getProperties().get("height");
		int tileHeight = (Integer) renderer.getMap().getProperties().get("tileheight");
		int mapHeight = height * tileHeight;
		
		if (camera.position.x - camera.viewportWidth / 2 < 0) {
			camera.position.x = camera.viewportWidth / 2;
		}
		
		if (camera.position.x > mapWidth - camera.viewportWidth / 2) {
			camera.position.x = mapWidth - camera.viewportWidth / 2;
		}
		
		if (camera.position.y - camera.viewportHeight / 2 < 0) {
			camera.position.y = camera.viewportHeight / 2;
		}
		
		if (camera.position.y > mapHeight - camera.viewportHeight / 2) {
			camera.position.y = mapHeight - camera.viewportHeight / 2;
		}
	}
	
	public void dispose() {
		
	}
}

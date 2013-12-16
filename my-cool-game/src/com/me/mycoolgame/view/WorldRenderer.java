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

/**
 * This class is responsible for positioning the camera, loading all the textures, rendering the "Background",
 * "Foreground", and "Top" tiles, rendering the player, and rendering all of the player's active skills. 
 * @author Ryan
 *
 */
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
		
		// Don't let the edges of the camera go off the map
		keepCameraInBounds();
		
		// TODO: What do these lines do again?
		camera.update();
		spriteBatch.setProjectionMatrix(camera.combined);

		// Set the tile map renderer view based on what the camera sees and render the map
		renderer.setView(camera);
		
		// Render the "Background" and "Foreground" layers first
		renderer.render(new int[] { 0, 1 });

		spriteBatch.enableBlending();
		spriteBatch.begin();
		
		renderPlayer();
		
		// Render objects associated with the player, e.g., skills
		renderPlayerSkills();
		
		spriteBatch.end();
		
		// Render the "Top" layer after the other layers, so that it overlaps the player, e.g., when they walk behind treetops.
		renderer.render(new int[] { 2 });
	}
	
	private void loadTextures() {
		map = Assets.manager.get("images/tilesets/nature/nature.tmx");
		world.setMap(map);
		renderer = new OrthogonalTiledMapRenderer(map, 1f);
		
		player.loadTextures(atlas);
	}

	/**
	 * Find the state of the player and what direction they are facing, then render the appropriate
	 * texture.
	 */
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
		} else if (player.getState().equals(State.ACTING)) {
			playerFrame = player.getActingAnimation().getKeyFrame(player.getStateTime(), true);
		}

		spriteBatch.draw(playerFrame, player.getBounds().x, player.getBounds().y);
	}
	
	/**
	 * 
	 */
	private void renderPlayerSkills() {
		for (SkillController controller : player.getSkillControllers()) {
			Skill skill = controller.getSkill();
			TextureRegion skillFrame = null;
			
			// We only want to render the skill if it's active
			if (skill.getState() == Skill.State.ACTIVE) {
				// Load the textures if we haven't loaded them already
				skill.loadTextures(atlas);
				
				skillFrame = skill.getActiveAnimation().getKeyFrame(skill.getStateTime(), true);
				spriteBatch.draw(skillFrame, skill.getBounds().x, skill.getBounds().y);
				
			} else if (skill.getState() == Skill.State.COLLIDING) {
				// Load the textures if we haven't loaded them already
				skill.loadTextures(atlas);
				
				skillFrame = skill.getCollidingAnimation().getKeyFrame(skill.getStateTime(), true);
				spriteBatch.draw(skillFrame, skill.getBounds().x, skill.getBounds().y);
				
			} else if (skill.getState() == Skill.State.DONE) {
				// TODO: If the skill is done, we need to find a way to remove it from the game.
				// Possibly look up that tutorial where they used a Pool of Rects, for example.
			}
		}
	}
	
	/**
	 * Calculates width and height of the TiledMap, then makes sure the camera's coordinates
	 * are within those bounds.
	 */
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
	
	public OrthographicCamera getCamera() {
		return camera;
	}

	public void setCamera(OrthographicCamera camera) {
		this.camera = camera;
	}

	public void dispose() {
		
	}
}

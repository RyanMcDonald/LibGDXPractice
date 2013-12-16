package com.me.mycoolgame.model.skill;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.me.mycoolgame.util.CardinalDirection;
import com.me.mycoolgame.util.CardinalDirection.Direction;

/**
 * The Mage's Fireball skill. Explodes upon impact.
 * Skill type: Projectile.
 * @author Ryan
 *
 */
public class Fireball extends Skill {
	
	public static final String NAME = "fireball";

	// Keep track of the initial position so we know how far the projectile has travelled
	private Vector2 initialPosition;
	
	// The destination that the projectile has to shoot towards
	private Vector2 destinationPosition;
	
	// The direction vector representing the exact direction the projectile is headed
	private Vector2 directionVector;
	
	// The cardinal direction, which determines which textures to display for the projectile animation
	private Direction shootingDirection = Direction.NORTH;
	
	// When the projectile has travelled this many pixels, it stops.
	private int travelDistancePixels = 200;

	public Fireball(Vector2 position, Vector2 destinationPosition) {
		super(position.cpy());
		
		setWidth(32f);
		setHeight(32f);
		
		setPosition(position.cpy());

		this.initialPosition = getPosition().cpy();
		
		this.destinationPosition = destinationPosition.cpy();
		
		this.directionVector = destinationPosition.cpy().sub(initialPosition);
		
		// Normalize the direction vector so we get a vector of length 1 to accurately give us direction.
		// Check that the direction isn't the zero vector, otherwise we would get a divide by zero error.
		if (!directionVector.equals(Vector2.Zero)) {
			directionVector = directionVector.nor();
		}
		
		// Calculate shooting direction based on initial position and destination position
		this.shootingDirection = CardinalDirection.calculateCardinalDirection(directionVector);
		
		setCooldown(0.0f);

		setSpeed(200f);

		setState(State.ACTIVE);
	}
	
	public void update(float delta) {
		super.update(delta);
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

	public Vector2 getDestinationPosition() {
		return destinationPosition;
	}

	public void setDestinationPosition(Vector2 destinationPosition) {
		this.destinationPosition = destinationPosition;
	}

	public Vector2 getDirectionVector() {
		return directionVector;
	}

	public void setDirectionVector(Vector2 directionVector) {
		this.directionVector = directionVector;
	}

	public int getTravelDistancePixels() {
		return travelDistancePixels;
	}

	public void setTravelDistancePixels(int travelDistancePixels) {
		this.travelDistancePixels = travelDistancePixels;
	}

	@Override
	public void loadTextures(TextureAtlas atlas) {
		// If the textures have already been loaded before, we don't need to do anything.
		if (getTexturesLoaded()) {
			return;
		}

		// The animation that display while the projectile is shooting mid-air
		Array<String> shootingImages = new Array<String>();
		shootingImages.add("fireball-shooting-1");
		shootingImages.add("fireball-shooting-2");
		shootingImages.add("fireball-shooting-3");
		shootingImages.add("fireball-shooting-4");

		TextureRegion[] shootingFrames = new TextureRegion[shootingImages.size];

		for (int i = 0; i < shootingImages.size; i++) {
			shootingFrames[i] = atlas.findRegion(shootingImages.get(i));
		}

		float shootingFrameDuration = getSpeed() / 2000;
		setActiveAnimation(new Animation(shootingFrameDuration, shootingFrames));
		
		// The animation that displays while the projectile is colliding with an object
		Array<String> collidingImages = new Array<String>();
		collidingImages.add("generic-shooting-1");
		collidingImages.add("generic-shooting-2");
		collidingImages.add("generic-shooting-3");

		TextureRegion[] collidingFrames = new TextureRegion[collidingImages.size];

		for (int i = 0; i < collidingImages.size; i++) {
			collidingFrames[i] = atlas.findRegion(collidingImages.get(i));
		}

		float collidingFrameDuration = getSpeed() / 2000;
		setCollidingAnimation(new Animation(collidingFrameDuration, collidingFrames));

		setTexturesLoaded(true);
	}
	
}

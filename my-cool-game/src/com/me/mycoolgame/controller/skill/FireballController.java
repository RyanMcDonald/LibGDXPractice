package com.me.mycoolgame.controller.skill;

import com.badlogic.gdx.math.Vector2;
import com.me.mycoolgame.model.World;
import com.me.mycoolgame.model.skill.Fireball;
import com.me.mycoolgame.model.skill.Skill.State;

public class FireballController extends SkillController {

	private Fireball fireball;
	
	public FireballController(World world, Fireball fireball) {
		super(world, fireball);

		this.fireball = fireball;
		//setSkill(fireball);
	}
	
	public void update(float delta) {
		super.update(delta);
		
		// Check if the projectile has gone its maximum distance
		if (fireball.getPosition().dst(fireball.getInitialPosition()) > fireball.getTravelDistancePixels()) {
			fireball.setState(State.DONE);
		}
		
		// Check if the fireball collided with an object and has finished exploding
		if (fireball.getState() == State.COLLIDING && fireball.getStateTime() > 0.5) {
			fireball.setState(State.DONE);
		}
		
		// Set velocity so that it shoots towards its destination
		if (fireball.getState() == State.ACTIVE) {
			
			Vector2 direction = fireball.getDestinationPosition().cpy().sub(fireball.getInitialPosition());
			
			// Normalize the vector so we get a vector of length 1 to accurately give us direction
			// Check that the direction isn't the zero vector, otherwise we would get a divide by zero error.
			if (!direction.equals(Vector2.Zero)) {
				direction = direction.nor();
			}
			
			fireball.getVelocity().x = direction.x * fireball.getSpeed();
			fireball.getVelocity().y = direction.y * fireball.getSpeed();
		}
		
	}

	@Override
	void collision() {
		fireball.setState(State.COLLIDING);
		fireball.getVelocity().x = 0;
		fireball.getVelocity().y = 0;
	}
	
	@Override
	void outOfBounds(float newPosX, float newPosY) {
		fireball.setState(State.DONE);
	}

}

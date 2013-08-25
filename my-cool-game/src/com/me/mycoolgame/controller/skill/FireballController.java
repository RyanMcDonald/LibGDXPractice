package com.me.mycoolgame.controller.skill;

import com.me.mycoolgame.model.Skill.State;
import com.me.mycoolgame.model.World;
import com.me.mycoolgame.model.skill.Fireball;

public class FireballController extends SkillController {

	private Fireball fireball;
	
	public FireballController(World world, Fireball fireball) {
		super(world, fireball);

		this.fireball = fireball;
	}
	
	public void update(float delta) {
		super.update(delta);
		
		// Check if the projectile has gone its maximum distance
		if (fireball.getPosition().dst(fireball.getInitialPosition()) > fireball.getTravelDistancePixels()) {
			fireball.setState(State.DONE);
		}
		
		// Set velocity based on the direction it's shooting
		switch (fireball.getShootingDirection()) {
		case NORTH:
			fireball.getVelocity().x = 0;
			fireball.getVelocity().y = fireball.getSpeed();
			break;
		case NORTHEAST:
			fireball.getVelocity().x = fireball.getSpeed();
			fireball.getVelocity().y = fireball.getSpeed();
			break;
		case EAST:
			fireball.getVelocity().x = fireball.getSpeed();
			fireball.getVelocity().y = 0;
			break;
		case SOUTHEAST:
			fireball.getVelocity().x = fireball.getSpeed();
			fireball.getVelocity().y = -fireball.getSpeed();
			break;
		case SOUTH:
			fireball.getVelocity().x = 0;
			fireball.getVelocity().y = -fireball.getSpeed();
			break;
		case SOUTHWEST:
			fireball.getVelocity().x = -fireball.getSpeed();
			fireball.getVelocity().y = -fireball.getSpeed();
			break;
		case WEST:
			fireball.getVelocity().x = -fireball.getSpeed();
			fireball.getVelocity().y = 0;
			break;
		case NORTHWEST:
			fireball.getVelocity().x = -fireball.getSpeed();
			fireball.getVelocity().y = fireball.getSpeed();
			break;
		}
		
	}

	@Override
	void collision() {
		getSkill().setState(State.DONE);
	}
	
	@Override
	void outOfBounds(float newPosX, float newPosY) {
		getSkill().setState(State.DONE);
	}

}

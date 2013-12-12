package com.me.mycoolgame.util;

import com.badlogic.gdx.math.Vector2;

public class CardinalDirection {
	
	public enum Direction {
		NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST
	}
	
	public static Direction calculateCardinalDirection(Vector2 directionVector) {
		float angle = directionVector.angle();
		
		if (angle >= 67.5 && angle <= 112.5) {
			return Direction.NORTH;
			
		} else if (angle > 112.5 && angle < 157.5) {
			return Direction.NORTHWEST;
			
		} else if (angle >= 157.5 && angle <= 202.5) {
			return Direction.WEST;
			
		} else if (angle > 202.5 && angle < 247.5) {
			return Direction.SOUTHWEST;
			
		} else if (angle >= 247.5 && angle <= 292.5) {
			return Direction.SOUTH;
			
		} else if (angle > 292.5 && angle < 337.5) {
			return Direction.SOUTHEAST;
			
		} else if ((angle >= 337.5 && angle <= 360) || (angle <= 22.5 && angle >= 0)) {
			return Direction.EAST;
			
		} else if (angle > 22.5 && angle < 67.5) {
			return Direction.NORTHEAST;
		}
		
		return null;
	}
}

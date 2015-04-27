package game_engine.physics.objects;

import java.util.List;
import java.util.Map;

import game_engine.hitboxes.IHitbox;
import game_engine.physics.Vector;
import game_engine.physics.engines.PhysicsEngine;
import game_engine.sprite.Animation;

public class AcceleratingPhysicsObject extends MovingPhysicsObject {
	
	private double xAccel, yAccel;
	
	public AcceleratingPhysicsObject(PhysicsEngine physEng, Map<String, List<IHitbox>> hitbox, Vector position, Animation animation) {
		super(physEng, hitbox, position, animation);
		xAccel = 0.0;
		yAccel = 0.0;
	}
	
	public void update(double frameRate) {
		Vector totalAccel = new Vector(xAccel, yAccel);
		super.increment(totalAccel.times(frameRate));
		super.update(frameRate);
	}
	
	public void set(Vector amount) {
		 xAccel = amount.getX();
		 yAccel = amount.getY();
	}
	
	public void increment(Vector amount) {
		 xAccel += amount.getX();
		 yAccel += amount.getY();
	}
	
	public void applyImpulse(Vector impulse) {
		super.increment(impulse);
	}
}
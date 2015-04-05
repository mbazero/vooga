package game_engine.physics;

import java.util.Map;
import java.util.HashMap;

import game_engine.sprite.Sprite;

public class PhysicsEngine {

	//TODO move these to properties file
	private static String GRAV_STRING = "gravity";
	private static double GRAV_DIRECTION = -90;
	private static double GRAV_MAGNITUDE = 9.8;
	private static double SLOP = 0.2;

	private Map<String, Vector> myGlobalForces;

	public PhysicsEngine(double gravity) {
		myGlobalForces = new HashMap<>();
		setGravity(gravity);
	}

	public PhysicsEngine() {
		this(GRAV_MAGNITUDE);
	}

	public void setGravity(double gravity) {
		myGlobalForces.put(GRAV_STRING, Vector.getPolarVector(GRAV_DIRECTION, gravity));
	}

	public void resolveCollision(PhysicsObject a, PhysicsObject b, Vector normal) {
		// compute relative velocity
		Vector relVel = b.getVelocity().minus(b.getVelocity());

		// project relative velocity along collision normal
		double projOnNorm = relVel.dot(normal);

		// return if objects are moving apart
		if(projOnNorm > 0) {
			return;
		}

		// get minimum restitution
		double restitution = Math.min(a.getRestitution(), b.getRestitution());

		// calculate impulse
		double implsMag = -(1 + restitution) * projOnNorm;
		implsMag /= a.getInvMass() + b.getInvMass();
		Vector impulse = normal.multiply(implsMag);

		// apply impulse
		a.applyImpulse(impulse.negate());
		b.applyImpulse(impulse);
	}

	public void resolveCollision(Sprite a, Sprite b, Vector normal) {
		resolveCollision(a.getPhysicsObject(), b.getPhysicsObject(), normal);
	}

	private void applyPosCorrection(Object a, Object b, Vector normal, double pDepth) {



}

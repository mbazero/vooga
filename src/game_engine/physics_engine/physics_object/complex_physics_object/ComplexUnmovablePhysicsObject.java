package game_engine.physics_engine.physics_object.complex_physics_object;

import game_engine.physics.Material;
import game_engine.physics.PhysicsEngine;
import game_engine.physics.RigidBody;
//import game_engine.physics.RigidBodyFactory;
import game_engine.physics.RigidBody.RBodyType;

public class ComplexUnmovablePhysicsObject extends ComplexPhysicsObject {

	public ComplexUnmovablePhysicsObject(PhysicsEngine physics,
			RigidBody rigidBody, Material material, int xPosPixels,
			int yPosPixels) {
		super(physics, rigidBody, material, xPosPixels, yPosPixels);
	}

	public ComplexUnmovablePhysicsObject(PhysicsEngine physics, RBodyType rbType, int widthPixels, int heightPixels, Material material, int xPosPixels, int yPosPixels) {
		super(physics, rbType,widthPixels,heightPixels,material,xPosPixels,yPosPixels);
	}

	@Override
	public void update() {

	}

}

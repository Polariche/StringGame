package com.spygame.game.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.spygame.game.util.ShapeCreator;

public class InteractableSwitch extends Switch implements Interactable {
	
	public InteractableSwitch(World world, float x, float y, Activatable user) {
		super(world, x, y, user);
	}

	public InteractableSwitch(World world, Vector2 pos, Activatable user) {
		super(world, pos.x, pos.y,user);
	}

	@Override
	protected void formBody(Object... arg) {
		ShapeCreator sc = new ShapeCreator();
		PolygonShape shape = sc.createBox(32, 32, 0, 0, false);
		createSensor(shape,FixtureType.Default);
		shape.dispose();
	}

	@Override
	public void interact() {
		activate();
	}
	
}

//InteractableSwitch
//WeightedSwitch

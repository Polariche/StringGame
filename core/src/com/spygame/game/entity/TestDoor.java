package com.spygame.game.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.spygame.game.util.ShapeCreator;

public class TestDoor extends Entity implements Activatable {

	private boolean isActive;

	public TestDoor(World world, float x, float y) {
		super(BodyType.StaticBody, world, x, y);
		// TODO Auto-generated constructor stub
	}

	public TestDoor(World world, Vector2 pos) {
		this(world, pos.x,pos.y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void activate() {
		destroy();
		isActive = true;
	}

	@Override
	public void deactivate() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void formBody(Object... arg) {
		ShapeCreator sc = new ShapeCreator();
		PolygonShape shape = sc.createBox(200, 32, 0, 0, false);
		createFixture(shape,FixtureType.Default);
		shape.dispose();
	}

	@Override
	public boolean isActivated() {
		return isActive;
	}

}

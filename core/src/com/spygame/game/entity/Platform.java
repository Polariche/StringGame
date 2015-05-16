package com.spygame.game.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.spygame.game.util.ShapeCreator;

public class Platform extends Entity {
	protected Fixture mainFixture;
	
	public Platform(World world, Vector2 pos, float width, float height) {
		this(world, pos.x, pos.y, width, height);
	}

	public Platform(World world, float x, float y, float width, float height) {
		super(BodyType.StaticBody, world, x, y, width, height);
	}

	@Override
	protected void formBody(Object... arg) {
		float width = (Float) arg[0];
		float height = (Float) arg[1];
		
		ShapeCreator sc = new ShapeCreator();
		
		PolygonShape shape = sc.createBox(width, height-1, 0, 0, false);
		mainFixture = createFixture(shape, FixtureType.Platform);
		
		shape.dispose();
		
		shape = sc.createBox(width-2, 2, 1, height-2, false);
		Fixture f = createFixture(shape, FixtureType.Platform);
		f.setFriction(1.5f);
		
		shape.dispose();
	}

}

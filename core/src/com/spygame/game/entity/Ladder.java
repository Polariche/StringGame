package com.spygame.game.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.spygame.game.util.ShapeCreator;

public class Ladder extends Entity implements Contactable {


	public Ladder(World world, Vector2 pos, float length) {
		this(world,pos.x,pos.y,length);
	}

	public Ladder(World world, float x, float y, float length) {
		super(BodyType.StaticBody, world, x, y, length);
	}

	@Override
	protected void formBody(Object... arg) {
		final float length = (Float) arg[0];
		ShapeCreator sc = new ShapeCreator();

		PolygonShape shape = sc.createBox(5, length, 0, 0, false);
		createSensor(shape, FixtureType.Sensor);
		shape.dispose();
	}

	@Override
	public void startContact(Fixture f1, Fixture f2) {
		if (f1.getUserData() == FixtureType.Player) {
			Player player = (Player) f1.getBody().getUserData();
			player.ladder = this;
		}
	}

	@Override
	public void endContact(Fixture f1, Fixture f2) {
		if (f1.getUserData() == FixtureType.Player) {
			Player player = (Player) f1.getBody().getUserData();
			
			player.getOffLadder();
		}
	}

}

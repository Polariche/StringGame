package com.spygame.game.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.spygame.game.util.ShapeCreator;


public class HazardPlatform extends Platform implements Contactable {
	
	public HazardPlatform(World world, Vector2 pos, float width, float height) {
		this(world, pos.x, pos.y, width, height);
	}

	public HazardPlatform(World world, float x, float y, float width, float height) {
		super(world, x, y, width, height);
	}

	@Override
	protected void formBody(Object... arg) {
		float width = (Float) arg[0];
		float height = (Float) arg[1];
		
		ShapeCreator sc = new ShapeCreator();
		PolygonShape shape = sc.createBox(width, height, 0, 0, false);

		createFixture(shape, FixtureType.Platform);
		shape.dispose();
	}
	
	public void startContact(Fixture f1, Fixture f2) {
		if (f1.getUserData().equals(FixtureType.Player)) {
			Player player = (Player) f1.getBody().getUserData();
			
			player.die();
		}
	}

	@Override
	public void endContact(Fixture f1, Fixture f2) {
		// TODO Auto-generated method stub
		
	}
}

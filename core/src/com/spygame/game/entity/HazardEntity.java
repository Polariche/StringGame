package com.spygame.game.entity;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

public abstract class HazardEntity extends Entity implements Contactable {
	
	public HazardEntity(BodyType bodyType, World world, float x, float y) {
		super(bodyType, world, x, y);
	}
	
	public HazardEntity(BodyType bodyType, World world, float x, float y, Object... arg) {
		super(bodyType, world, x, y, arg);
	}

	@Override
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

package com.spygame.game.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class HalfPlatform extends Platform implements Activatable {
	public HalfPlatform(World world, Vector2 pos, float width, float height) {
		this(world, pos.x, pos.y, width, height);
	}

	public HalfPlatform(World world, float x, float y, float width, float height) {
		super(world, x, y, width, height);
	}

	@Override
	public void activate() {
		Filter filter = mainFixture.getFilterData();
		filter.maskBits = (short) (~FixtureType.Platform.maskBits & ~FixtureType.Player.maskBits);
	
		mainFixture.setFilterData(filter);
		
		Timer.schedule(new Task() {
			@Override
			public void run() {
				deactivate();
			}
		}, 3);
	}

	@Override
	public void deactivate() {
		Filter filter = mainFixture.getFilterData();
		filter.maskBits = (short) (~FixtureType.Platform.maskBits);
		
		mainFixture.setFilterData(filter);
	}

	@Override
	public boolean isActivated() {
		// TODO Auto-generated method stub
		return false;
	}
}

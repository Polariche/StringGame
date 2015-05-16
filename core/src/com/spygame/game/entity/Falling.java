package com.spygame.game.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.spygame.game.entity.FixtureType.FixtureBits;
import com.spygame.game.util.ShapeCreator;

public class Falling extends Entity implements Contactable, Activatable {
	
	private boolean activated;
	Fixture mainFixture;
	Fixture sensorFixture;

	public Falling(World world, float x, float y) {
		super(BodyType.StaticBody, world, x, y);
		
	}
	
	public Falling(World world, Vector2 pos) {
		this(world,pos.x,pos.y);
	}

	@Override
	protected void formBody(Object... arg) {
		ShapeCreator sc = new ShapeCreator();
		
		PolygonShape shape = sc.createBox(32, 32, 0, 0, false);
		mainFixture = createFixture(shape,FixtureType.Platform);
		mainFixture.setDensity(1);
		shape.dispose();
		
		shape = sc.createPolygon(new float[]{0,16,8,0,16,16},8,8); //¡ä shape
		createFixture(shape,FixtureType.Platform); 
		shape.dispose();
		
		shape = sc.createBox(30, 1, 1, 31, false);
		sensorFixture = createSensor(shape,FixtureType.Sensor);
		shape.dispose();
	}

	@Override
	public void startContact(Fixture f1, Fixture f2) {
		if(f1.getUserData().equals(FixtureType.Player) && f2.getUserData().equals(FixtureType.Sensor)) {
			activate();
		}
		if(f1.getUserData().equals(FixtureType.Hazard)) {
			//((Entity) f1.getBody().getUserData()).destroy();
		}
	}
	
	

	@Override
	public void endContact(Fixture f1, Fixture f2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void activate() {
		if(!activated) {
			activated = true;
			
			Timer.schedule(new Task() {
				@Override
				public void run() {
					Filter filter = mainFixture.getFilterData();
					filter.maskBits = FixtureBits.HAZARD;
					mainFixture.setFilterData(filter);
					
					body.setType(BodyType.DynamicBody);
				}
			}, 0.15f);
		}
	}

	@Override
	public void deactivate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isActivated() {
		return activated;
	}

}

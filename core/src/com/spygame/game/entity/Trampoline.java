package com.spygame.game.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.spygame.game.util.ShapeCreator;

public class Trampoline extends Entity implements Contactable {
	protected final static BodyType bodyType = BodyType.StaticBody;

	public Trampoline(World world, Vector2 pos) {
		this(world,pos.x,pos.y);
	}

	public Trampoline(World world, float x, float y) {
		super(BodyType.StaticBody, world, x, y);
		
		//texture = new Texture(textureAtlas.findRegion("spike"));
	}

	@Override
	protected void formBody(Object... arg) {
		ShapeCreator sc = new ShapeCreator();

		PolygonShape shape = sc.createBox(128, 3, 0, 27, false);
		createSensor(shape, FixtureType.Sensor);
		shape.dispose();

		shape = sc.createBox(128, 7, 0, 18, false);
		createFixture(shape, FixtureType.Default);
		shape.dispose();
	}

	@Override
	public void startContact(Fixture f1, Fixture f2) {
		if (f1.getUserData() == FixtureType.Player && f2.getUserData() == FixtureType.Sensor) {
			Body playerBody = f1.getBody();
			Player player = (Player) playerBody.getUserData();
			
			player.resetVelocityY();
			playerBody.applyLinearImpulse(new Vector2(0, Player.JUMP_SPEED*1.2f),playerBody.getPosition(), false);
			
			//f1.setRestitution(1f);
			
			playerBody.getFixtureList().get(1).setRestitution(1);
			
			System.out.println("START");
		}
	}

	@Override
	public void endContact(Fixture f1, Fixture f2) {
	}

}

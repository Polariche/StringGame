package com.spygame.game.entity;

import static com.spygame.game.handler.Vars.PPM;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.spygame.game.Game;

public abstract class Entity {

	protected final static TextureAtlas atlas = (TextureAtlas) Game.assets.get(Game.absolute()+".atlas");
	
	protected TextureRegion texture;
	protected Body body;
	protected HashMap<FixtureType, Fixture> sensors;
	protected World world;
	
	/*
	public Entity(BodyType bodyType, World world, Vector2 pos) {
		this(bodyType,world,pos.x,pos.y);
	}*/
	
	protected Entity(BodyType bodyType, World world, float x, float y,Object... arg) {
		sensors = new HashMap<FixtureType, Fixture>();
		
		BodyDef def = new BodyDef();
		def.type = bodyType;
		
		this.world = world;
		this.body = world.createBody(def);
		body.setUserData(this);
		
		setPosition(x,y,0);
		formBody(arg);
	}

	/** formBody(Object... arg)**/
	protected abstract void formBody(Object... arg);

	public void update(float delta) {
	}
	
	public void draw(SpriteBatch batch) {
		if (texture != null) {
			batch.draw(texture, body.getPosition().x*PPM, body.getPosition().y*PPM, );
		}
	}
	
	public void dispose() {
		if (texture != null) {
			texture.dispose();
		}
	}
	
	public void destroy() {
		world.destroyBody(body);
	}

	protected Fixture createSensor(Shape shape, FixtureType type) {
		Fixture f = createFixture(shape,type);
		f.setSensor(true);
		sensors.put(type, f);
		
		return f;
	}
	
	protected Fixture createFixture(Shape shape) {
		return body.createFixture(shape,0);
	}
	
	protected Fixture createFixture(Shape shape, FixtureType type)  {
		Fixture f = createFixture(shape);
		f.setUserData(type);
		
		Filter filter = new Filter();
		filter.categoryBits = type.categoryBits;
		filter.maskBits = type.maskBits;
		f.setFilterData(filter);
		
		return f;
	}

	public void setPosition(float x, float y, float angle) {
		body.setTransform(x/PPM,y/PPM,angle);
	}
	
	public void setPosition(Vector2 pos, float angle) {
		setPosition(pos.x,pos.y,angle);
	}
	
	public void resetVelocity() {
		Vector2 pos = body.getPosition();
		Vector2 vel = body.getLinearVelocity();
		
		body.applyLinearImpulse(new Vector2(-vel.x,-vel.y), pos, false);
	}
	
	public void resetVelocityX() {
		Vector2 pos = body.getPosition();
		Vector2 vel = body.getLinearVelocity();
		
		body.applyLinearImpulse(new Vector2(-vel.x,0), pos, false);
	}
	
	public void resetVelocityY() {
		Vector2 pos = body.getPosition();
		Vector2 vel = body.getLinearVelocity();
		
		body.applyLinearImpulse(new Vector2(0,-vel.y), pos, false);
	}

	public HashMap<FixtureType, Fixture> getSensors() {
		return sensors;
	}

	public Fixture getSensorFixture(String s) {
		return sensors.get(s);
	}

	public Body getSensorBody(String s) {
		return sensors.get(s).getBody();
	}

	public Body getBody() {
		return body;
	}

	public Fixture getFixture() {
		return body.getFixtureList().get(0);
	}

	public Texture getTexture() {
		return texture;
	}

}

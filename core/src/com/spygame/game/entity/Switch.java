package com.spygame.game.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;

public class Switch extends Entity implements Activatable {

	protected boolean isActive;
	protected Activatable user;

	public Switch(World world, float x, float y, Activatable user) {
		this(world, x, y);
		this.user = user;
	}

	public Switch(World world, Vector2 pos, Activatable user) {
		this(world, pos.x, pos.y);
		this.user = user;
	}

	public Switch(World world, Vector2 pos) {
		this(world, pos.x, pos.y);
	}

	public Switch(World world, float x, float y) {
		super(BodyType.StaticBody, world, x, y);
	}

	@Override
	protected void formBody(Object... arg) {
		// TODO Auto-generated method stub
	}

	@Override
	public void activate() {
		if(!isActive) {
			isActive = true;

			user.activate();
		}
	}

	@Override
	public void deactivate() {
		isActive = false;
	}

	public void setUser(Activatable user) {
		this.user = user;
	}

	public Activatable getUser() {
		return user;
	}

	@Override
	public boolean isActivated() {
		return isActive;
	}

}

// InteractableSwitch
// WeightedSwitch

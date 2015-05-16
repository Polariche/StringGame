package com.spygame.game.entity;

import com.badlogic.gdx.physics.box2d.Fixture;

public interface Contactable {
	public void startContact(Fixture f1, Fixture f2);
	public void endContact(Fixture f1, Fixture f2);
}

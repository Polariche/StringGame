package com.spygame.game.entity;

public enum FixtureType {
	
	Platform(FixtureBits.PLATFORM, (short) ~FixtureBits.PLATFORM),
	Ghost(FixtureBits.DEFAULT, (short) 0),
	Sensor(FixtureBits.DEFAULT, FixtureBits.PLAYER),
	Player(FixtureBits.PLAYER), 
	Hazard(FixtureBits.HAZARD),
	PlayerFoot(FixtureBits.DEFAULT, FixtureBits.PLATFORM),
	Default;
	
	final short categoryBits;
	final short maskBits;
	
	FixtureType() {
		this(FixtureBits.DEFAULT,(short)-1);
	}
	FixtureType(short category) {
		this(category,(short)-1);
	}
	FixtureType(short category, short mask) {
		categoryBits = category;
		maskBits = mask;
	}
	
	public class FixtureBits {
		public final static short DEFAULT = (short) 0x0001;
		public final static short PLAYER = (short) 0x0002;
		public final static short PLATFORM = (short) 0x0004;
		public final static short HAZARD = (short) 0x0008;
	}
	
}
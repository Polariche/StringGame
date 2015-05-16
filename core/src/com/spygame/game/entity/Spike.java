package com.spygame.game.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.spygame.game.util.ShapeCreator;

public class Spike extends HazardEntity {
	
	public Spike(World world, Vector2 pos) {
		this(world,pos.x,pos.y);
	}

	public Spike(World world, float x, float y) {
		super(BodyType.StaticBody,world, x, y);
        texture = atlas.findRegion("level-screen/car");
	}

	@Override
	protected void formBody(Object... arg) {
		ShapeCreator sc = new ShapeCreator();
		
		float[] v = new float[] {3,0, 16,29, 32,0}; //(x,y), (x,y), (x,y)
		PolygonShape shape = sc.createPolygon(v, 0, 0);
		createSensor(shape,FixtureType.Hazard);
		shape.dispose();
	}


    bool isDotUpperLine(float dotX, float dotY, float x1, float y1, float x2, float y2){
        //dotX, dotY = 식별할 점
        // x1,y1 선분의 시작 위치
        // x2,y2 선분의 끝 위치

        float a = (y2-y1)/(x2-x1);
        return a*(dotX - x1) < dotY - y1;
    }

}

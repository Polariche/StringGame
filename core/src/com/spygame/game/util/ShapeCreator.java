package com.spygame.game.util;

import static com.spygame.game.handler.Vars.PPM;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;

/**
 * 
 * creates shapes in center
 * 
 * @author Moth
 *
 */

public class ShapeCreator {
	public PolygonShape createBox(float width, float height, float x, float y, boolean center) {
		PolygonShape shape = new PolygonShape();
		
		Vector2 point;
		if(center) {
			point = new Vector2(x/PPM,y/PPM);
		} else {
			point = new Vector2((x+width/2)/PPM, (y+height/2)/PPM);
		}
		
		shape.setAsBox(width/2 /PPM, height/2 /PPM, point, 0);
		
		return shape;
	}
	
	public CircleShape createCircle(float radius, float x, float y) {
		CircleShape shape = new CircleShape();
		shape.setRadius(radius / PPM);
		
		return shape;
	}
	
	public PolygonShape createPolygon(float[] v, float x, float y) {
		float[] vertices = new float[v.length];
		PolygonShape shape = new PolygonShape();
		
		for(int i=0; i<v.length; i++) {
			float add = i%2==0? x/PPM : y/PPM ;
			vertices[i] = v[i] / PPM + add;
		}
		shape.set(vertices);
		
		return shape;
	}
}

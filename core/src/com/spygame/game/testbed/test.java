package com.spygame.game.testbed;


import static com.spygame.game.handler.Vars.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.utils.Array;
import com.spygame.game.state.GameState;
import com.spygame.game.state.GameStateManager;

public class test extends GameState {

	private Box2DDebugRenderer debugRenderer;
	World world;
	
	private void createWorld() {
		world = new World(new Vector2(0, -20f), false); 
		
		Array<Body> bodyList = new Array<Body>();
		int bodyCount = 2;
		
		Body ground = createBox(BodyType.StaticBody,1000,50,0);
		
		for(int i=0;i<bodyCount;i++) {
			Body b = createBox(BodyType.DynamicBody,10,10,0);
			b.setTransform((100+50*i)/PPM, (300+50*i)/PPM, 0);
			
			bodyList.add(b);
		}
		
		RevoluteJointDef jd1 = new RevoluteJointDef();
		jd1.bodyA = bodyList.get(0);
		jd1.bodyB = bodyList.get(1);
		world.createJoint(jd1);
		
	}
	
	private void createCamera() {
		cam.setToOrtho(false, Gdx.graphics.getWidth() / PPM*2,Gdx.graphics.getHeight() / PPM*2);
		cam.position.set(Gdx.graphics.getWidth() / (PPM * 2),Gdx.graphics.getHeight() / (PPM * 2), 0);

		debugRenderer = new Box2DDebugRenderer();
	}
	
	public test(GameStateManager gsm) {
		super(gsm);

		createWorld();
		createCamera();
		
	}

	private Body createBox(BodyType type, float width, float height,float density) {
		BodyDef def = new BodyDef();
		def.type = type;
		Body box = world.createBody(def);

		PolygonShape poly = new PolygonShape();
		poly.setAsBox(width / PPM, height / PPM);
		box.createFixture(poly, density);
		poly.dispose();

		return box;
	}
	
	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		debugRenderer.render(world, cam.combined);
		dispose();
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		world.step(delta, 6, 2);
		cam.update();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
}

package com.spygame.game.entity;

import static com.spygame.game.handler.Vars.PLAYER_RADIUS;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.spygame.game.handler.PSInputHandler;
import com.spygame.game.state.PlayState;
import com.spygame.game.util.ShapeCreator;

public class Player extends Entity {

	public boolean canMove = true;
	
	public ArrayList<Interactable> interactables;
	public ArrayList<Entity> footContact;
	public Ladder ladder = null;
	
	private Fixture mainFixture;
	private PlayerState state;
	
	final static float SPEED = 0.5f;
	final static float JUMP_SPEED = 6;
	final static float MAX_VELOCITY = 1.5f;
	
	PSInputHandler inputHandler;

	public enum PlayerState {
		Grounded, Flying, OnLadder;
	}

	public Player(PlayState ps) {
		super(BodyType.DynamicBody, ps.getWorld(), 0, 0);

		inputHandler = ps.getInputHandler();
		interactables = new ArrayList<Interactable>();
		footContact = new ArrayList<Entity>();

		body.setSleepingAllowed(false);
	}

	@Override
	protected void formBody(Object... arg) {
		body.setFixedRotation(true);
		ShapeCreator sc = new ShapeCreator();

		PolygonShape shape = sc.createBox(PLAYER_RADIUS*2,PLAYER_RADIUS*2, 0, 0, true);
		mainFixture = createFixture(shape, FixtureType.Player);
		shape.dispose();

		PolygonShape shape2 = sc.createBox(PLAYER_RADIUS - 2, 6, 0,
				-PLAYER_RADIUS, true);
		createSensor(shape2, FixtureType.PlayerFoot);
		shape2.dispose();
	}



	public void interact() {
		for (Interactable i : interactables) {
			i.interact();
		}
	}
	
	public void moveLeft() {
		Vector2 pos = body.getPosition();
		Vector2 vel = body.getLinearVelocity();

		switch(state) {
			case OnLadder:
				getOffLadder();
				break;
			default: 
				if (vel.x > -MAX_VELOCITY) {
					body.applyLinearImpulse(new Vector2(-SPEED, 0), pos, true);
				}
				break;
		}
		
	}

	public void moveRight() {
		Vector2 pos = body.getPosition();
		Vector2 vel = body.getLinearVelocity();

		switch(state) {
			case OnLadder:
				getOffLadder();
				break;
			default: 
				/** move to right **/
				if (vel.x < MAX_VELOCITY) {
					body.applyLinearImpulse(new Vector2(SPEED, 0), pos, true);
				}
				break;
		}
	}
	
	public void moveUp() {
		Vector2 pos = body.getPosition();
		Vector2 vel = body.getLinearVelocity();
		
		switch(state) {
			case OnLadder: 
				/** go up while climbing a ladder **/
				if (vel.y < MAX_VELOCITY) {
					body.applyLinearImpulse(new Vector2(0,SPEED), pos, true);
				}
				break;
			case Grounded:
				jump();
				break;
			default: break;
		}
		
		if (ladder != null & state != PlayerState.OnLadder) {
			/** pressing down while passing through a ladder will make the player climb the ladder **/
			climbLadder();
		}
	}
	
	public void moveDown() {
		Vector2 pos = body.getPosition();
		Vector2 vel = body.getLinearVelocity();
		
		switch(state) {
			case OnLadder: 
				/** go down while climbing a ladder **/
				if (vel.y > -MAX_VELOCITY) {
					body.applyLinearImpulse(new Vector2(0,-SPEED), pos, true);
				}
				break;
			case Grounded:
				/** half platform **/
					for (Entity e : footContact) {
						if (e instanceof HalfPlatform) {
							((HalfPlatform) e).activate();
						}
					}
				break;
			default:
				break;
			}
	}
	
	public void stopMoving() {
		if (state == PlayerState.OnLadder) {
			resetVelocity();
		}
	}
	
	public void jump() {
		setState(PlayerState.Flying);
		body.applyLinearImpulse(new Vector2(0, JUMP_SPEED), body.getPosition(), true);
	}
	
	public void climbLadder() {
		body.setGravityScale(0);
		setState(PlayerState.OnLadder);
		
		resetVelocity();
		
		System.out.println("ladder");
	}
	
	public void getOffLadder() {
		if (state == PlayerState.OnLadder) {
			body.setGravityScale(1);
			setState(PlayerState.Flying);
		}
		ladder = null;
		
		System.out.println("getting off ladder");
	}

	
	public void create() {
		canMove = true;
		
		Filter filter = mainFixture.getFilterData();
		filter.maskBits = FixtureType.Player.maskBits;
		mainFixture.setFilterData(filter);
	}

	public void die() {
		Timer.schedule(new Task() {
            @Override
            public void run() {
            	inputHandler.reset = true;
            	create();
            }

         }, 2);
		
		//Dying animation (Mario style)
		Filter filter = mainFixture.getFilterData();
		filter.maskBits = 0;
		mainFixture.setFilterData(filter);
		
		canMove = false;
		body.setLinearVelocity(0, 0);
		body.applyLinearImpulse(new Vector2(0, JUMP_SPEED*1.3f), body.getPosition(), true);
	}

	public void setState(PlayerState state) {
		this.state = state;
		System.out.println("PlayerState: "+state);
	}
	
	public PlayerState getState() {
		return state;
	}
}

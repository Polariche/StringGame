package com.spygame.game.handler;

import java.util.HashMap;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.spygame.game.state.PlayState;

public class PSInputHandler {
	
	private void down(int keycode) {
		switch(keycode) {
			case Input.Keys.R: reset = true; break;
			case Input.Keys.E: ps.getPlayer().interact(); break;
		}
	}
	
	private void up(int keycode) {
		switch(keycode) {
		case Input.Keys.W: ps.getPlayer().stopMoving(); break;
		case Input.Keys.A: ps.getPlayer().stopMoving();  break;
		case Input.Keys.S: ps.getPlayer().stopMoving();  break;
		case Input.Keys.D: ps.getPlayer().stopMoving();  break;
		}
	}
	
	public void process() {
		if(canPlayerMove()) {
			if (isInputHeld(Input.Keys.A)) {
				ps.getPlayer().moveLeft();
			}
			if (isInputHeld(Input.Keys.D)) {
				ps.getPlayer().moveRight();
			}
			if (isInputHeld(Input.Keys.W)) {
				ps.getPlayer().moveUp();
			}
			if (isInputHeld(Input.Keys.S)) {
				ps.getPlayer().moveDown();
			}
		}
	}
	
	private PlayState ps;
	public boolean reset;
	
	private HashMap<Integer, Boolean> input;
	public enum InputType {
		Right(Input.Keys.D), Left(Input.Keys.A), Down(Input.Keys.S), Up(Input.Keys.W), 
		Center(Input.Keys.E), Reset(Input.Keys.R);
		
		final int key;
		
		InputType(int key) {
			this.key = key;
		}
	}

	private InputListener keyListener = new InputListener() {
		public boolean keyDown(InputEvent event, int keycode) {
			input.put(keycode, true);
			down(keycode);

			return false;
		}

		public boolean keyUp(InputEvent event, int keycode) {
			input.put(keycode, false);
			up(keycode);

			return false;
		}
	};
	
	public void inputDown(InputType i) {
		input.put(i.key, true);
		System.out.println("Down: "+i);
		down(i.key);
	}
	
	public void inputUp(InputType i) {
		input.put(i.key, false);
		up(i.key);
	}
	
	public boolean isInputHeld(int i) {
		return input.containsKey(i) ? input.get(i) : false;
	}
	
	
	public InputListener getKeyListener() {
		return keyListener;
	}
	
	public PSInputHandler(PlayState ps) {
		this.ps = ps;

		input = new HashMap<Integer, Boolean>();
	}
	
	private boolean canPlayerMove() {
		return ps.getPlayer().canMove;
	}
}

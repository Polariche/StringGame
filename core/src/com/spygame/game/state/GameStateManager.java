package com.spygame.game.state;

import java.util.Stack;

import com.spygame.game.Game;

public class GameStateManager {
	private Game game;
	private Stack<GameState> gameStates;
	
	public final static int MAIN = 0; /** MainState **/
	public final static int PLAY = 1; /** PlayState **/
	
	public GameStateManager(Game game) {
		gameStates = new Stack<GameState>();
		this.game = game;
		pushState(MAIN);
	}
	
	private GameState getStates(int i) {
		switch(i) {
			case MAIN: return new MainState(this); 
			case PLAY: return new PlayState(this);
		}
		return null;
	}
	
	
	private void pushState(int i) {
		gameStates.push(getStates(i));
	}
	
	private void popState() {
		GameState g = gameStates.pop();
		g.dispose();
	}
	
	public void setState(int i) {
		popState();
		pushState(i);
	}
	
	public Game game() {
		return game;
	}
	
	public GameState currentState() {
		return gameStates.peek();
	}
	
	public void update(float delta) {
		currentState().update(delta);
	}
	
	public void render() {
		currentState().render();
	}
	
	public void resize(int width, int height) {
		currentState().resize(width,height);
	}

	public void dispose() {
		currentState().dispose();
	}
}



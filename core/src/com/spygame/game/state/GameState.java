package com.spygame.game.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.spygame.game.Game;

public abstract class GameState {
	protected Game game;
	protected GameStateManager gsm;
	
	protected SpriteBatch batch;
	protected OrthographicCamera cam;
	protected Stage stage;
	
	public abstract void render();
	public abstract void update(float delta);
	public abstract void pause();
	public abstract void dispose();
	
	public GameState(GameStateManager gsm) {
		this.gsm = gsm;
		game = gsm.game();
		batch = game.batch;
		cam = game.cam;
				
		stage = new Stage();
		
		Gdx.input.setInputProcessor(stage);
	}
	
	public Game game() {
		return game;
	}
	
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}
}

package com.spygame.game.state;

import static com.spygame.game.handler.Vars.PPM;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainState extends GameState {

	public void createStage() {

		Skin skin = new Skin();

		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("white", new Texture(pixmap));

		skin.add("default", new BitmapFont());

		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.down = skin.newDrawable("white", Color.LIGHT_GRAY);
		textButtonStyle.font = skin.getFont("default");
		skin.add("default", textButtonStyle);

		Table table = new Table();
		table.setFillParent(true);
		table.debug();
		stage.addActor(table);

		final HashMap<String, TextButton> buttons = new HashMap<String, TextButton>();

		buttons.put("Start", new TextButton(" Start Game ", skin));
		//buttons.put("2", new TextButton(" Button2 ", skin));
		//buttons.put("3", new TextButton(" Button3 ", skin));

		buttons.get("Start").addListener(new ClickListener() {
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				gsm.setState(GameStateManager.PLAY);
			}
		});

		for (final TextButton button : buttons.values()) {
			table.add(button);
		}
	}

	private void createCamera() {
		cam.setToOrtho(false, Gdx.graphics.getWidth() / PPM,
				Gdx.graphics.getHeight() / PPM);
		cam.position.set(Gdx.graphics.getWidth() / (PPM * 2),
				Gdx.graphics.getHeight() / (PPM * 2), 0);
	}

	public MainState(final GameStateManager gsm) {
		super(gsm);

		createCamera();
		createStage();
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		/*
		 * batch.begin(); font.draw(batch, "Click Anywhere to Start", 200, 200);
		 * batch.end();
		 */
		stage.draw();
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

	@Override
	public void update(float delta) {
		stage.act(delta);
		cam.update();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

}

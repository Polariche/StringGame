package com.spygame.game.state;

import static com.spygame.game.handler.Vars.GRAVITY;
import static com.spygame.game.handler.Vars.PPM;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.spygame.game.Game;
import com.spygame.game.Map;
import com.spygame.game.entity.Entity;
import com.spygame.game.entity.Ladder;
import com.spygame.game.entity.Player;
import com.spygame.game.handler.PSContactListener;
import com.spygame.game.handler.PSInputHandler;
import com.spygame.game.handler.PSInputHandler.InputType;

public class PlayState extends GameState {
	
	private Box2DDebugRenderer debugRenderer;
	//private TiledMapRenderer tiledMapRenderer;

	private World world;
	private Array<Body> bodies;
	private Player player;
	private PSInputHandler inputHandler;
	private Map map;

	private final static String mapPath = Game.absolute()+"maps/slslsls.tmx";
	
	public PlayState(GameStateManager gsm) {
		super(gsm);
		
		bodies = new Array<Body>();
		inputHandler = new PSInputHandler(this);
		map = new Map((TiledMap) Game.assets.get(mapPath));
		stage.addListener(inputHandler.getKeyListener());
		
		createCamera();
		createWorld();
		createStage();
		
		debugSpawn();
	}
	
	private void createCamera() {
		cam.setToOrtho(false, Gdx.graphics.getWidth() / PPM,
				Gdx.graphics.getHeight() / PPM);
		cam.position.set(Gdx.graphics.getWidth() / (PPM * 2),
				Gdx.graphics.getHeight() / (PPM * 2), 0);

		debugRenderer = new Box2DDebugRenderer();
		//tiledMapRenderer = new OrthogonalTiledMapRenderer(map.getTiledMap());
	}

	private void createWorld() {
		world = new World(new Vector2(0, -GRAVITY), true);
		
		map.applyMap(world);
		
		player = new Player(this);
		player.setPosition(map.getStart(), 0);
		world.setContactListener(new PSContactListener(this));
	}
	
	private void createStage() {
		
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

		Table root = new Table();
		root.debug();
		root.align(Align.bottomLeft);
		root.setFillParent(true);
		stage.addActor(root);
		
		Table controls = new Table();
		controls.setPosition(30,30);
		controls.setWidth(100);
		controls.setHeight(75);
		root.add(controls);
		
		final ArrayList<TextButton> buttons = new ArrayList<TextButton>();
		
		buttons.add(new TextButton(" ^ ", skin));
		buttons.add(new TextButton(" <- ", skin));
		buttons.add(new TextButton(" -> ", skin));
		buttons.add(new TextButton(" v ", skin));
		
		
		
		for (final TextButton button : buttons) {
			final InputType inputType;
			
			switch(buttons.indexOf(button)) {
			case 0: inputType = InputType.Up; break;
			case 1: inputType = InputType.Left; break;
			case 2: inputType = InputType.Right; break;
			case 3: inputType = InputType.Down; break;
			default: inputType = null;
			}
			
			button.addListener(new ClickListener() {
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					inputHandler.inputDown(inputType);
					return true;
				}
				
				public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
					inputHandler.inputUp(inputType);
				}
			});
		}
		
		controls.add();
		controls.add(buttons.get(0));
		controls.add().row();
		controls.add(buttons.get(1));
		controls.add();
		controls.add(buttons.get(2)).row();
		controls.add();
		controls.add(buttons.get(3));
		controls.add();
		
		for (final Cell<?> cell : controls.getCells()) {
			cell.pad(5,5,5,5);
		}
		
	}
	
	private void debugSpawn() {
		new Ladder(world,188,100,180);
	}

	

	@Override
	public void update(float delta) {
		if(inputHandler.reset == true) {
			inputHandler.reset = false;
			reset();
		}
		
		world.step(delta, 6, 2);
		stage.act(delta);
		inputHandler.process();
		player.update(delta);
		cam.update();
		
		
		world.getBodies(bodies);
	}

	@Override
	public void render() {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		debugRenderer.render(getWorld(), cam.combined);

		batch.begin();
		for(Body body : bodies) {
			((Entity) body.getUserData()).draw(batch);
		}
		batch.end();
		//tiledMapRenderer.setView(cam);
        //tiledMapRenderer.render();
        
		stage.draw();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
	}

	@Override
	public void dispose() {
		stage.dispose();
		for(Body body : bodies) {
			((Entity) body.getUserData()).getTexture().dispose();
		}
	}

	public void reset() {
		Timer.instance().clear();
		world.dispose();
		
		createWorld();
		debugSpawn();
		
		player.setPosition(map.getStart(), 0);
		player.resetVelocity();
	}

	public World getWorld() {
		return world;
	}
	public Player getPlayer() {
		return player;
	}
	public PSInputHandler getInputHandler() {
		return inputHandler;
	}
}

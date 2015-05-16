package com.spygame.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.spygame.game.state.GameStateManager;


public class Game implements ApplicationListener {

    public SpriteBatch batch;
    public OrthographicCamera cam;
    
    static public AssetManager assets;
    
    private GameStateManager gsm;
	

    @Override
	public void create() {
        batch = new SpriteBatch();
        cam = new OrthographicCamera();
        assets = new AssetManager();
        
        loadResource();
        loadTexture();
        
        gsm = new GameStateManager(this);
        
    }

	private void loadResource() {
    	assets.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
    	assets.load(absolute()+"maps/wonjun.tmx", TiledMap.class);
    	assets.load(absolute()+"maps/HYEONJU.tmx", TiledMap.class);
    	assets.load(absolute()+"maps/1-2-1.tmx", TiledMap.class);
    	assets.load(absolute()+"maps/slslsls.tmx", TiledMap.class);
    	
    	assets.load(absolute()+".atlas", TextureAtlas.class);
    	assets.finishLoading();
    }
	
	private void loadTexture() {
		
	}

    @Override
	public void render() {
    	gsm.update(Gdx.graphics.getDeltaTime());
    	gsm.render();
    }

    @Override
	public void dispose() {
        batch.dispose();
        gsm.dispose();
    }

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		gsm.resize(width,height);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	
	public static String absolute() {
		return "D:/eclipse/project/StringGame/core/assets/";
		//return "";
	}

}
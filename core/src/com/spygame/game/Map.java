package com.spygame.game;

import static com.spygame.game.handler.Vars.PLAYER_RADIUS;

import java.util.ArrayList;
import java.util.Locale;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.spygame.game.entity.Falling;
import com.spygame.game.entity.HazardPlatform;
import com.spygame.game.entity.Platform;
import com.spygame.game.entity.Spike;
import com.spygame.game.entity.Trampoline;

public class Map {
	
	private TiledMap tileMap;
	private Vector2 start;
	private Vector2 finish;
	
	private ArrayList<EntityData> objects;
	//private HashMap<String, EntityData> namedObjects;
	
	
	public Map(TiledMap tileMap) {
		this.objects = new ArrayList<EntityData>();
		this.start = new Vector2(0,0);
		this.finish = new Vector2(0,0);
		
		this.tileMap = tileMap;
		
		load();
	}

	private void load() {
		for (MapLayer ml : tileMap.getLayers()) {

			MapObjects objects = ml.getObjects();
			
			for (MapObject mo : objects) {
				if (mo.getProperties().containsKey("ObjectType")) {
					Vector2 position;
					EntityData data;
					Rectangle rec = null;
					EntityType type = EntityType.valueOf(((String) mo.getProperties().get("ObjectType")).toUpperCase(Locale.ENGLISH));
					
					if (mo instanceof RectangleMapObject) {
						rec = ((RectangleMapObject) mo).getRectangle();
						position = new Vector2(rec.x,rec.y);
					} else {
						position = new Vector2(0,0);
					}
					
					switch(type) {
						case START: 
							this.start = new Vector2(position.x+PLAYER_RADIUS,position.y+PLAYER_RADIUS);
							break;
						case FINISH: 
							this.finish = new Vector2(position.x+PLAYER_RADIUS,position.y+PLAYER_RADIUS);
							break;
						case PLATFORM: 
							data = new EntityData(type,position,rec.width,rec.height);
							this.objects.add(data);
							break;
						case HAZARD_PLATFORM: 
							data = new EntityData(type,position,rec.width,rec.height);
							this.objects.add(data);
							break;
						default: 
							data = new EntityData(type,position);
							this.objects.add(data);
							break;
					}
				}
				
			}
		}
	}

	public void applyMap(World world) {
		
		System.out.println("Creating map objects...\n");
		
		for (EntityData data : objects) {	
			Vector2 pos = data.getPosition();
			EntityType type = data.getType();
			
			System.out.println(type+"  "+ pos);
			
			switch(type) {
				case PLATFORM:
					float width = data.getWidth();
					float height = data.getHeight();
					new Platform(world,pos.x,pos.y,width,height);
					break;
				case HAZARD_PLATFORM:
					width = data.getWidth();
					height = data.getHeight();
					new HazardPlatform(world,pos.x,pos.y,width,height);
					break;
				case SPIKE:
					new Spike(world,pos).getBody();
					break;
				case TRAMPOLINE:
					new Trampoline(world,pos);
					break;
				case FALLING:
					new Falling(world,pos);
					break;
				default:
					break;
			}
		}
	}
	
	public Vector2 getStart() {
		return start;
	}
	
	public Vector2 getFinish() {
		return finish;
	}
	
	public TiledMap getTiledMap() {
		return tileMap;
	}
	
	private enum EntityType {
		PLATFORM, SPIKE, TRAMPOLINE, FALLING, HAZARD_PLATFORM, 
		START, FINISH;
	}
	
	private class EntityData {
		//String name;
		private EntityType type;
		private Vector2 position;
		
		private float width, height; //for PLATFORM
		
		private EntityData(EntityType type, Vector2 position) {
			this.type = type;
			this.position = position;
		}
		
		private EntityData(EntityType type, Vector2 position, float width, float height) {
			this(type,position);
			this.width = width;
			this.height = height;
		}
		
		private EntityData(EntityType type, Vector2 position, String name) {
			this(type,position);
			//this.name = name;
		}
		
		public EntityType getType() {
			return type;
		}
		public Vector2 getPosition() {
			return position;
		}
		public float getWidth() {
			return width;
		}
		public float getHeight() {
			return height;
		}
	}
	
}

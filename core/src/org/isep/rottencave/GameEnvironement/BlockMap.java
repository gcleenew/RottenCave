package org.isep.rottencave.GameEnvironement;

import java.util.HashSet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class BlockMap {
	public static final float BLOCK_SIZE = 0.5f;;
	private Body body;
	private World world;
	private Integer type;
	private Rectangle rect;
	private final float posX;
	private final float posY;

	private HashSet<Sprite> tiledSprites;
	private TextureAtlas textureAtlas;
	
	
	
	public BlockMap(World world, float x, float y, Integer type,TextureAtlas textureAtlas) {
		this.world = world;
		this.type = type;
		this.textureAtlas = textureAtlas;
		posX = x*BLOCK_SIZE;
		posY = y*BLOCK_SIZE;
		rect = new Rectangle(0, 0, BLOCK_SIZE, BLOCK_SIZE);
		createBlockBody();
		associateSpriteToBody();
		if(textureAtlas==null){
			System.out.println("NOT finded");
		}else{
			System.out.println("finded");
		}
	}
	
	public BlockMap(World world, float x, float y, Integer type,HashSet<Sprite> tiledSprites,TextureAtlas textureAtlas) {
		this.world = world;
		this.type = type;
		this.tiledSprites = tiledSprites;
		this.textureAtlas = textureAtlas;
		posX = x*BLOCK_SIZE;
		posY = y*BLOCK_SIZE;
		rect = new Rectangle(0, 0, BLOCK_SIZE, BLOCK_SIZE);
		createBlockBody();
		associateSpriteToBody();
		
	}
	
	private void createBlockBody() {
		if(type!=1){
			BodyDef blockBodyDef = new BodyDef();
			blockBodyDef.type = BodyDef.BodyType.StaticBody;
			blockBodyDef.position.set(posX, posY);
	
	
			FixtureDef blockFixtureDef = new FixtureDef();
			
			Vector2[] vect = vectorTabFromRect(rect);
			PolygonShape polyShape = new PolygonShape();
			polyShape.set(vect);
			blockFixtureDef.shape = polyShape;
	
			body = world.createBody(blockBodyDef);
	
			body.createFixture(blockFixtureDef);
		}
	}
	
	private Vector2[] vectorTabFromRect(Rectangle rect) {
		Vector2[] vect = new Vector2[4];
		vect[0] = new Vector2(rect.x, rect.y);
		vect[1] = new Vector2(rect.x + rect.width, rect.y);
		vect[2] = new Vector2(rect.x + rect.width, rect.y + rect.height);
		vect[3] = new Vector2(rect.x, rect.y + rect.height);

		return vect;
	}
	
	private void associateSpriteToBody() {
		Sprite sprite =null;
		if (type==1) {
//			sprite = new Sprite(new Texture(Gdx.files.internal("img/sol.png")));
			sprite = textureAtlas.createSprite("sol");
			sprite.setPosition(posX, posY);
			if(tiledSprites!=null)tiledSprites.add(sprite);
		}else {
			if(type==2){
//				sprite = new Sprite(new Texture(Gdx.files.internal("img/mur bas.png")));
				sprite = textureAtlas.createSprite("mur bas moisi");
			}else if(type==3){
//				sprite = new Sprite(new Texture(Gdx.files.internal("img/mur droite.png")));
				sprite = textureAtlas.createSprite("mur droite moisi");
			}else if(type==4){
//				sprite = new Sprite(new Texture(Gdx.files.internal("img/mur haut.png")));
				sprite = textureAtlas.createSprite("mur haut moisi");
			}else if(type==5){
//				sprite = new Sprite(new Texture(Gdx.files.internal("img/mur gauche.png")));
				sprite = textureAtlas.createSprite("mur gauche moisi");
			}
			body.setUserData(sprite);
		}
		sprite.setSize(BLOCK_SIZE, BLOCK_SIZE);
	}
}

package org.isep.rottencave.GameEnvironement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class BlockMap {
	static private final float BLOCK_SIZE = 0.5f;;
	
	private Body body;
	private World world;
	private Integer type;
	private Rectangle rect;
	private final float posX;
	private final float posY;
	
	
	
	public BlockMap(World world, float x, float y, Integer type) {
		this.world = world;
		this.type = type;
		posX = x*BLOCK_SIZE;
		posY = y*BLOCK_SIZE;
		rect = new Rectangle(0, 0, BLOCK_SIZE, BLOCK_SIZE);
		createBlockBody();
		associateSpriteToBody();
	}
	
	private void createBlockBody() {
		BodyDef blockBodyDef = new BodyDef();
		blockBodyDef.type = BodyDef.BodyType.StaticBody;
		blockBodyDef.position.set(posX, posY);


		FixtureDef blockFixtureDef = new FixtureDef();
		if(type==1){
			blockFixtureDef.isSensor=true;
		}
		Vector2[] vect = vectorTabFromRect(rect);
		PolygonShape polyShape = new PolygonShape();
		polyShape.set(vect);
		blockFixtureDef.shape = polyShape;

		body = world.createBody(blockBodyDef);

		body.createFixture(blockFixtureDef);
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
			sprite = new Sprite(new Texture(Gdx.files.internal("img/sol.png")));
		}else if(type==2){
			sprite = new Sprite(new Texture(Gdx.files.internal("img/mur bas.png")));
		}else if(type==3){
			sprite = new Sprite(new Texture(Gdx.files.internal("img/mur droite.png")));
		}else if(type==4){
			sprite = new Sprite(new Texture(Gdx.files.internal("img/mur haut.png")));
		}else if(type==5){
			sprite = new Sprite(new Texture(Gdx.files.internal("img/mur gauche.png")));
		}
		body.setUserData(sprite);
		sprite.setSize(BLOCK_SIZE, BLOCK_SIZE);
	}
}

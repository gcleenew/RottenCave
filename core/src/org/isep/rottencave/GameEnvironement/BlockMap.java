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
	private Boolean wall = false;
	private Rectangle rect;
	private final float posX;
	private final float posY;
	
	
	
	public BlockMap(World world, float x, float y, Boolean isWall) {
		this.world = world;
		this.wall = isWall;
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
		if(!wall){
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
		if (wall) {
			sprite = new Sprite(new Texture(Gdx.files.internal("img/wall.png")));
		}else{
			sprite = new Sprite(new Texture(Gdx.files.internal("img/ground.png")));
		}
		body.setUserData(sprite);
		sprite.setSize(BLOCK_SIZE, BLOCK_SIZE);
	}
}

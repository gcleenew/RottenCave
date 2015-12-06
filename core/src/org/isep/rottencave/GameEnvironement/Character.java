package org.isep.rottencave.GameEnvironement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Character {

	private Body body;
	private float speed;
	private World world;
	private Boolean player = true;

	private float starterX;
	private float starterY;

	public Character(World world, float f, float g, Boolean isPlayer) {
		this.world = world;
		this.player = isPlayer;
		if(player){
			this.speed = 1f;
		}else{
			this.speed = 0.5f;
		}
		this.starterX = f;
		this.starterY = g;
		createPlayerBody();
		associateSpriteToBody();
	}

	private void createPlayerBody() {
		// je cree la def (position/type...)
		BodyDef characterBodyDef = new BodyDef();
		characterBodyDef.type = BodyDef.BodyType.DynamicBody;
		characterBodyDef.position.set(starterX, starterY);

		// je cree ma fixture qui fix les carac physiques du body et sa shape
		FixtureDef characterFixtureDef = new FixtureDef();
		characterFixtureDef.density = 1;
		characterFixtureDef.restitution = 1.0f;
		characterFixtureDef.friction = 0.5f;

		// je cree mon body dans mon world partant de ma def
		body = world.createBody(characterBodyDef);

		CircleShape circleShape = new CircleShape();
		circleShape.setRadius(0.1f);
		characterFixtureDef.shape = circleShape;

		body.createFixture(characterFixtureDef);
	}

	private void associateSpriteToBody() {
		Sprite sprite =null;
		if (isPlayer()) {
			sprite = new Sprite(new Texture(Gdx.files.internal("img/playerSprite.png")));
		}else{
			sprite = new Sprite(new Texture(Gdx.files.internal("img/monsterSprite.png")));
		}
		body.setUserData(sprite);
		sprite.setSize(0.2f, 0.2f);
	}

	public void setVelocity(float vX, float vY){
		this.body.setLinearVelocity(vX, vY);
	}
	
	public Vector2 getVelocity(){
		return this.body.getLinearVelocity();
	}
	
	public void setMoveAngle(Float radian) {
		if (radian == null) {
			body.setLinearVelocity(0, 0);
		} else {
			body.setLinearVelocity(MathUtils.cos(radian) * this.speed, MathUtils.sin(radian) * this.speed);
		}
	}

	public Body getBody() {
		return this.body;
	}

	public Boolean isPlayer() {
		return this.player;
	}

}

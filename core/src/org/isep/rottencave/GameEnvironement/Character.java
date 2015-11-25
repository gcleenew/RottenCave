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

	public Character(World world, float f, float g) {
		this.world = world;
		this.speed = 2f;
		this.starterX = f;
		this.starterY = g;
		createPlayerBody();
		associateSpriteToBody();
	}

	private void createPlayerBody() {
		// je cree la def (position/type...)
		BodyDef playerBodyDef = new BodyDef();
		playerBodyDef.type = BodyDef.BodyType.DynamicBody;
		playerBodyDef.position.set(starterX, starterY);

		// je cree ma fixture qui fix les carac physiques du body et sa shape
		FixtureDef firstFixtureDef = new FixtureDef();
		firstFixtureDef.density = 1;
		firstFixtureDef.restitution = 1.0f;
		firstFixtureDef.friction = 0.5f;

		// je cree mon body dans mon world partant de ma def
		body = world.createBody(playerBodyDef);

		CircleShape circleShape = new CircleShape();
		circleShape.setRadius(0.1f);
		firstFixtureDef.shape = circleShape;

		body.createFixture(firstFixtureDef);
	}

	private void associateSpriteToBody() {
		if (isPlayer()) {
			Sprite sprite = new Sprite(new Texture(Gdx.files.internal("img/playerSprite.png")));
			body.setUserData(sprite);
			sprite.setSize(0.2f, 0.2f);
		}
	}

	public void setVelocity(float vX, float vY){
		this.body.setLinearVelocity(vX, vY);
	}
	
	public Vector2 getVelocity(){
		return this.body.getLinearVelocity();
	}
	
	private void setMoveAngle(Float radian) {
		if (radian == null) {
			body.setLinearVelocity(0, 0);
		} else {
			body.setLinearVelocity(MathUtils.cos(radian) * speed, MathUtils.sin(radian) * speed);
		}
	}

	public Body getBody() {
		return this.body;
	}

	public Boolean isPlayer() {
		return this.player;
	}

}

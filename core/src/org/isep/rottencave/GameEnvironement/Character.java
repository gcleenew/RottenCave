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
	private final static long INC_RYTHME = 10000;

	private Body body;
	private float speed;
	private World world;
	private Boolean player = true;

	// path monsterToPlayer
	private PathNode pathToPlayer = null;

	private float starterX;
	private float starterY;

	private static final float PLAYER_SPEED = 1f;
	private static final float PLAYER_RADIUS = 0.2f;

	private static final float MONSTER_SPEED = 0.5f;
	private static final float MONSTER_RADIUS = 0.2f;

	public Character(World world, float f, float g, Boolean isPlayer) {
		this.world = world;
		this.player = isPlayer;
		if (player) {
			this.speed = PLAYER_SPEED;
		} else {
			this.speed = MONSTER_SPEED;
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
		if (player) {
			circleShape.setRadius(PLAYER_RADIUS);
		} else {
			circleShape.setRadius(MONSTER_RADIUS);
		}

		characterFixtureDef.shape = circleShape;

		body.createFixture(characterFixtureDef);
	}

	private void associateSpriteToBody() {
		Sprite sprite = null;
		if (isPlayer()) {
			sprite = new Sprite(new Texture(Gdx.files.internal("img/playerSprite.png")));
			sprite.setSize(0.3f, 0.6f);
		} else {
			sprite = new Sprite(new Texture(Gdx.files.internal("img/monsterSprite.png")));
			sprite.setSize(0.8f, 1f);
		}
		body.setUserData(sprite);

	}

	public void setVelocity(float vX, float vY) {
		this.body.setLinearVelocity(vX, vY);
	}

	public Vector2 getVelocity() {
		return this.body.getLinearVelocity();
	}

	public void setMoveAngle(Float radian) {
		if (radian == null) {
			body.setLinearVelocity(0, 0);
		} else {
			body.setLinearVelocity(MathUtils.cos(radian) * this.speed, MathUtils.sin(radian) * this.speed);
		}
	}

	public void stepToPlayer() {
		Vector2 pos = body.getPosition();
		int i = (int) (pos.x / BlockMap.BLOCK_SIZE);
		int j = (int) (pos.y / BlockMap.BLOCK_SIZE);
		if (pathToPlayer != null) {
			if (i == pathToPlayer.getI() && j == pathToPlayer.getJ()) {
				pathToPlayer = pathToPlayer.getParent();
			}
		}
		if (pathToPlayer != null) {
			float newX = pathToPlayer.getI() * BlockMap.BLOCK_SIZE+ BlockMap.BLOCK_SIZE/2;
			float newY = pathToPlayer.getJ() * BlockMap.BLOCK_SIZE+ BlockMap.BLOCK_SIZE/2;

			double theta = Math.atan2(newY - pos.y, newX - pos.x);
			if (theta < 0) {
				theta += Math.PI * 2;
			}
			setMoveAngle((float) theta);

		}
	}

	public Body getBody() {
		return this.body;
	}

	public Boolean isPlayer() {
		return this.player;
	}

	public void incMonsterSpeed(long popedTime) {
		if (speed < 2 * PLAYER_SPEED) {
			speed = MONSTER_SPEED + (popedTime / INC_RYTHME) * 0.1f;
		}
	}

	public void setPathToPlayer(PathNode pathToPlayer) {
		this.pathToPlayer = pathToPlayer;
	}
}

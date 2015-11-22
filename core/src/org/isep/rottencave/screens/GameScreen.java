package org.isep.rottencave.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class GameScreen implements Screen {
	final Game game;
	
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private World world;
	private Box2DDebugRenderer debugRenderer;
	private Body playerBody;
	private Sprite playerSprite;
	private Array<Body> tmpBodies = new Array<Body>();
	
	public final static float WOLRD_WIDTH = 6.4f;
	public final static float WORLD_HEIGHT = 4.0f;
	
	public GameScreen(final Game game) {
		this.game=game;	
		
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, WOLRD_WIDTH/2, WORLD_HEIGHT/2);
		world = new World(new Vector2(0f, 0f), true);
		debugRenderer = new Box2DDebugRenderer(true,true,true,true,true,true);
		
		createStaticMap();
		createPlayerBody();
	}
	
	private void createPlayerBody(){
		
		//je crée la def (position/type...)
		BodyDef playerBodyDef = new BodyDef();
		
		playerBodyDef.type = BodyDef.BodyType.DynamicBody;
		
		playerBodyDef.position.set(WOLRD_WIDTH / 2, WORLD_HEIGHT /2);
		
		//je crée mon body dans mon world partant de ma def
		playerBody = world.createBody(playerBodyDef);
		
		//je crée ma fixture qui fix les carac physiques du body et sa shape
		FixtureDef firstFixtureDef = new FixtureDef();	
		firstFixtureDef.density = 1;	
		firstFixtureDef.restitution = 1.0f;	
		firstFixtureDef.friction = 0.5f;

		
		CircleShape circleShape = new CircleShape();
		circleShape.setRadius(0.1f);
		firstFixtureDef.shape = circleShape;
		
		playerBody.createFixture(firstFixtureDef);
		
		playerSprite = new Sprite(new Texture(Gdx.files.internal("img/playerSprite.png")));
		playerBody.setUserData(playerSprite);
		playerSprite.setSize(0.2f, 0.2f);
	}
	
	private void createStaticMap(){
		BodyDef mapPartDef = new BodyDef();
		
		mapPartDef.type = BodyDef.BodyType.StaticBody;
		mapPartDef.position.set(0, 0);
		
		Body mapPart = world.createBody(mapPartDef);

		Rectangle rect = new Rectangle(WOLRD_WIDTH, WORLD_HEIGHT, WOLRD_WIDTH, WORLD_HEIGHT);
		Vector2[] vect = vectorTabFromRect(rect);		
		ChainShape chainShape = new ChainShape();
		chainShape.createLoop(vect);
		
		mapPart.createFixture(chainShape, 0.0f);
	}
	
	private Vector2[] vectorTabFromRect(Rectangle rect){
		Vector2[] vect = new Vector2[4];
		vect[0]=new Vector2(rect.x, rect.y);
		vect[1]=new Vector2(rect.x-rect.width, rect.y);
		vect[2]=new Vector2(rect.x-rect.width, rect.y-rect.height);
		vect[3]=new Vector2(rect.x, rect.y-rect.height);
		
		return vect;
	}
	
	@Override
	public void render(float delta) {
		checkControl();
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		

		debugRenderer.render(world, camera.combined);

		//use this to center camera on player.
		camera.position.set(playerBody.getPosition(), 0f);
		camera.update();
		
		
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
		world.getBodies(tmpBodies);
		for(Body curBody : tmpBodies){
			if(curBody.getUserData()!=null && curBody.getUserData() instanceof Sprite){
				Sprite sprite = (Sprite)curBody.getUserData();
				sprite.setPosition(curBody.getPosition().x - sprite.getWidth() / 2, curBody.getPosition().y - sprite.getHeight() /2);
				sprite.draw(batch);
			}
		}

		batch.end();

		world.step(1/60f, 6, 2);

	}
	
	private void checkControl(){
		if(Gdx.input.isKeyPressed(Keys.UP)){
			playerBody.setLinearVelocity(playerBody.getLinearVelocity().x, 1f);
		}else if(Gdx.input.isKeyPressed(Keys.DOWN)){
			playerBody.setLinearVelocity(playerBody.getLinearVelocity().x, -1f);
		}else {
			playerBody.setLinearVelocity(playerBody.getLinearVelocity().x, 0);
		}
		if(Gdx.input.isKeyPressed(Keys.LEFT)){
			playerBody.setLinearVelocity(-1f, playerBody.getLinearVelocity().y);
		}else if(Gdx.input.isKeyPressed(Keys.RIGHT)){
			playerBody.setLinearVelocity(1f, playerBody.getLinearVelocity().y);
		}else{
			playerBody.setLinearVelocity(0, playerBody.getLinearVelocity().y);
		}
		
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}


}
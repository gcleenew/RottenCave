package org.isep.rottencave.screens;

import org.isep.matrice.Matrice;
import org.isep.rottencave.RottenCave;
import org.isep.rottencave.GameEnvironement.BlockMap;
import org.isep.rottencave.GameEnvironement.Character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.utils.Array;

public class GameScreen implements Screen {
	final RottenCave game;

	private SpriteBatch batch;
	private OrthographicCamera camera;
	private World world;
	private Box2DDebugRenderer debugRenderer;
	private Character playerCharacter;
	private Character monsterCharacter;
	private Array<Body> tmpBodies = new Array<Body>();
	
	/**
	 * Used for touchpad
	 */
	private Stage stage;
	private Touchpad stick;
	private Skin uiSkin;

	private Matrice matriceMap;

	public final static float WOLRD_WIDTH = 6.4f;
	public final static float WORLD_HEIGHT = 4.0f;
	
	private float starterX =  6.4f / 2;

	private float starterY = 4.0f / 2;

	

	public GameScreen(final RottenCave game, Matrice matrice) {
		this.game = game;
		this.uiSkin = game.getUiSkin();
		this.matriceMap = matrice;
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, WOLRD_WIDTH, WORLD_HEIGHT);
		world = new World(new Vector2(0f, 0f), true);
		debugRenderer = new Box2DDebugRenderer(true, true, true, true, true, true);

		
		generateBlocksFromMatrice();
		
		playerCharacter = new Character(world, starterX, starterY, true);
		monsterCharacter = new Character(world, WOLRD_WIDTH / 2, WORLD_HEIGHT / 2, false);

		createTouchpad();
	}
	
	private void generateBlocksFromMatrice(){
		Boolean firstGroud=false;
		for(int x=0; x<matriceMap.rangeX; x++){		
			for(int y=0; y<matriceMap.rangeY; y++){
				int curStatus = matriceMap.matrice[x][y].status;
				if(curStatus>0){
					new BlockMap(world, x, y, curStatus);
					if(curStatus==1 && !firstGroud){
						firstGroud=true;
						starterX = x*0.5f;
						starterY = y*0.5f;
					}
				}
			}
		}
	}
	
	private void createTouchpad() {
		stage = new Stage();
		TouchpadStyle touchpadStyle = new TouchpadStyle(uiSkin.getDrawable("touchpad-background"), uiSkin.getDrawable("touchpad-knob"));
		stick = new Touchpad(1, touchpadStyle);
		stick.setBounds(15, 15, 100, 100);
		stage.addActor(stick);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		debugRenderer.render(world, camera.combined);

		// use this to center camera on player.
		camera.position.set(playerCharacter.getBody().getPosition(), 0f);
		camera.update();
		stage.act(delta);
		stage.draw();

		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		world.getBodies(tmpBodies);
		for (Body curBody : tmpBodies) {
			if (curBody.getUserData() != null && curBody.getUserData() instanceof Sprite) {
				Sprite sprite = (Sprite) curBody.getUserData();
				if (curBody.getType() == BodyType.StaticBody) {
					sprite.setPosition(curBody.getPosition().x, curBody.getPosition().y);
				} else {
					sprite.setPosition(curBody.getPosition().x - sprite.getWidth() / 2,
							curBody.getPosition().y - sprite.getHeight() / 2);
				}
				sprite.draw(batch);
			}
		}
		batch.end();

		checkControl();
		monsterStep();
		world.step(1 / 60f, 6, 2);
	}

	private void monsterStep(){
		Vector2 monsterPos = monsterCharacter.getBody().getPosition();
		Vector2 playerPos = playerCharacter.getBody().getPosition();

		double theta = Math.atan2(playerPos.y - monsterPos.y, playerPos.x - monsterPos.x);
		if (theta < 0) {
			theta += Math.PI * 2;
		}
		monsterCharacter.setMoveAngle((float) theta);
	}
	
	private void checkControl() {
		if (Gdx.input.isKeyPressed(Keys.UP)) {
			playerCharacter.setVelocity(playerCharacter.getVelocity().x, 1f);
		} else if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			playerCharacter.setVelocity(playerCharacter.getVelocity().x, -1f);
		} else {
			playerCharacter.setVelocity(playerCharacter.getVelocity().x, 0);
		}
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			playerCharacter.setVelocity(-1f, playerCharacter.getVelocity().y);
		} else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			playerCharacter.setVelocity(1f, playerCharacter.getVelocity().y);
		} else {
			playerCharacter.setVelocity(0, playerCharacter.getVelocity().y);
		}
		
		// Touchpad action
		if (stick.getKnobPercentX() != 0 || stick.getKnobPercentY() != 0){
			playerCharacter.setVelocity(stick.getKnobPercentX(), stick.getKnobPercentY());
		}
	}

	@Override
	public void show() {
		// Process input for touchpad
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
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
		debugRenderer.dispose();
		batch.dispose();
		world.dispose();
		stage.dispose();
	}

}
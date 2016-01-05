package org.isep.rottencave.screens;

import java.util.Date;
import java.util.HashSet;

import org.isep.matrice.Matrice;
import org.isep.rottencave.RottenCave;
import org.isep.rottencave.GameEnvironement.BlockMap;
import org.isep.rottencave.GameEnvironement.Character;
import org.isep.rottencave.GameEnvironement.PathFinding;
import org.isep.rottencave.generation.ProceduralGeneration;
import org.isep.rottencave.score.PersonalScore;
import org.isep.rottencave.score.PersonalScoreDAO;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
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
	private HashSet<Sprite> tiledSprites = new HashSet<Sprite>();
	private Music ambiance;
	
	/**
	 * Used for touchpad
	 */
	private Stage stage;
	private Touchpad stick;
	private Skin uiSkin;
	private final TextureAtlas textureAtlas;
	
	private Matrice matriceMap;

	public final static float WOLRD_WIDTH = 6.4f;
	public final static float WORLD_HEIGHT = 4.0f;
	public final static float DISTANCE_RENDERING = 4.0f;
	
	private float starterX =  6.4f / 2;
	private float starterY = 4.0f / 2;

	private final static float DISTANCE_TO_WIN = 0.4f;
	private final static long MONSTER_POP_TIMER = 5000;
	private long startTimer;
	private boolean gameover = false;
	


	public GameScreen(final RottenCave game, Matrice matrice) {
		
		this.game = game;
		this.uiSkin = game.getUiSkin();
		this.matriceMap = matrice;
		this.textureAtlas = new TextureAtlas(Gdx.files.internal("atlastexture/packedTexture.atlas"));
		
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, WOLRD_WIDTH, WORLD_HEIGHT);
		world = new World(new Vector2(0f, 0f), true);
		MainMenuScreen.menuMusic.stop();
		MainMenuScreen.menuMusic.dispose();
		ambiance = Gdx.audio.newMusic(Gdx.files.internal("music/ambiance01.mp3"));
		ambiance.play();
		ambiance.setLooping(true);
		debugRenderer = new Box2DDebugRenderer(false, false, false, false, true, false);

		
		generateBlocksFromMatrice();
		
		playerCharacter = new Character(world, starterX, starterY, true);

		createTouchpad();
	}
	
	private void generateBlocksFromMatrice(){
		Boolean firstGroud=false;
		for(int x=0; x<matriceMap.rangeX; x++){		
			for(int y=0; y<matriceMap.rangeY; y++){
				int curStatus = matriceMap.matrice[x][y].status;
				if(curStatus==1){
					new BlockMap(world, x, y, curStatus, tiledSprites, textureAtlas);
					if(curStatus==1 && !firstGroud){
						firstGroud=true;
						starterX = x*0.5f;
						starterY = y*0.5f;
					}
				}else if(curStatus>1){
					new BlockMap(world, x, y, curStatus, textureAtlas);
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

		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		drawSprites();
		batch.end();

		checkControl();
		monsterStep();
		world.step(1 / 60f, 1, 1);

		stage.act(delta);
		stage.draw();
		
		if(gameover) {

			int score = (int) (System.currentTimeMillis() - startTimer)/1000;
			Gdx.app.debug("Game Over", "Score : "+score);
			PersonalScore ps = new PersonalScore(new Date(), score, ProceduralGeneration.getLastSeedUsed());
			addPersonalScore(ps);
			
			game.setScreen(new GameOverScreen(game, score));
			ambiance.stop();
			dispose();
		}
	}
	
	private void drawSprites(){
		for(Sprite tileSprite : tiledSprites){
			if(Math.abs(tileSprite.getX()-playerCharacter.getBody().getPosition().x)<DISTANCE_RENDERING && 
					Math.abs(tileSprite.getY()-playerCharacter.getBody().getPosition().y)<DISTANCE_RENDERING){
				tileSprite.draw(batch);
			}
		}
		
		world.getBodies(tmpBodies);
		for (Body curBody : tmpBodies) {
			if (curBody.getUserData() != null && curBody.getUserData() instanceof Sprite) {
				if (curBody.getType() == BodyType.StaticBody) {
					Sprite sprite = (Sprite) curBody.getUserData();
					sprite.setPosition(curBody.getPosition().x, curBody.getPosition().y);	
					if(checkDrowableDistance(sprite)){
						sprite.draw(batch);	
					}
				}
			}
		}
		if (monsterCharacter != null) {
			Sprite monsterSprite = (Sprite) monsterCharacter.getBody().getUserData();
			setSpriteCharacterPosition(monsterSprite, monsterCharacter);
			if (checkDrowableDistance(monsterSprite)) {
				monsterSprite.draw(batch);
			}
		}
		Sprite playerSprite = (Sprite) playerCharacter.getBody().getUserData();
		setSpriteCharacterPosition(playerSprite, playerCharacter);
		playerSprite.draw(batch);
	}

	private void setSpriteCharacterPosition(Sprite sprite, Character character) {
		if (character == monsterCharacter) {
			sprite.setPosition(character.getBody().getPosition().x - sprite.getWidth() / 2,
					character.getBody().getPosition().y - sprite.getHeight() / 4);
		} else {
			sprite.setPosition(character.getBody().getPosition().x - sprite.getWidth() / 2,
					character.getBody().getPosition().y - sprite.getHeight() / 2);
		}
	}

	private boolean checkDrowableDistance(Sprite sprite) {
		if (Math.abs(sprite.getX() - playerCharacter.getBody().getPosition().x) < DISTANCE_RENDERING
				&& Math.abs(sprite.getY() - playerCharacter.getBody().getPosition().y) < DISTANCE_RENDERING) {
			return true;
		}
		return false;
	}

	private void monsterStep(){
		long popedTime = System.currentTimeMillis()-startTimer;
		if(monsterCharacter!=null){
			monsterCharacter.incMonsterSpeed(popedTime);
			Vector2 monsterPos = monsterCharacter.getBody().getPosition();
			Vector2 playerPos = playerCharacter.getBody().getPosition();
			double deltaY =Math.abs(playerPos.y - monsterPos.y);
			double deltaX =Math.abs(playerPos.x - monsterPos.x);		
			if(deltaY < DISTANCE_TO_WIN && deltaX < DISTANCE_TO_WIN){
				System.out.println("GAME OVER");
				gameover=true;
			}
			double theta = Math.atan2(playerPos.y - monsterPos.y, playerPos.x - monsterPos.x);
			if (theta < 0) {
				theta += Math.PI * 2;
			}
			monsterCharacter.setMoveAngle((float) theta);
			
		}else if(popedTime>MONSTER_POP_TIMER){
			createMonster();
		}
	}
	
	private void createMonster(){
		monsterCharacter = new Character(world, starterX, starterY, false);
		PathFinding pathFinding = new PathFinding(monsterCharacter, playerCharacter, matriceMap);
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
	
	private void addPersonalScore(PersonalScore ps) {
		PersonalScoreDAO psDAO = PersonalScoreDAO.getPersonalScoreDAO();
		psDAO.addPersonalScore(ps);
	}

	@Override
	public void show() {
		startTimer = System.currentTimeMillis();
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
		ambiance.dispose();
	}

}
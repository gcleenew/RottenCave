package org.isep.rottencave.screens;

import org.isep.rottencave.GlobalConfiguration;
import org.isep.rottencave.RottenCave;
import org.isep.rottencave.generation.Corridor;
import org.isep.rottencave.generation.Hall;
import org.isep.rottencave.generation.Point;
import org.isep.rottencave.generation.ProceduralGeneration;
import org.isep.rottencave.generation.Triangle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class GenerationScreen implements Screen {
	final RottenCave game;
	private OrthographicCamera camera;
	private ShapeRenderer shape;
	private ProceduralGeneration generation;
	private Music music;
	
	public GenerationScreen(final RottenCave game) {
		this.game = game;
		instantiateGenerationScreen();
		generation = new ProceduralGeneration(GlobalConfiguration.configuredSeed);
	}
	
	public GenerationScreen(final RottenCave game, Long forcedSeed) {
		this.game = game;
		instantiateGenerationScreen();
		generation = new ProceduralGeneration(forcedSeed);
	}
	
	private void instantiateGenerationScreen() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 400);
		
		shape = new ShapeRenderer();
		
		music = Gdx.audio.newMusic(Gdx.files.internal("music/menu.mp3"));
		music.setLooping(false);
	}
	
	@Override
	public void show() {
		generation.start();
		
		RottenCave.getMenuMusic().stop();
		if(GlobalConfiguration.musicOn)
			music.play();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(255, 255, 255,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		shape.begin(ShapeType.Line);
		
		shape.setColor(Color.BLACK);
		if(generation.hallList!=null){
			synchronized (generation.hallListLock) {		
				for (Hall square: generation.hallList){
					if(square.getIsMain()){
						shape.setColor(Color.RED);
					}
					if((square.getIsMain() || square.isCorridor()) || !generation.isCorridored){
						shape.rect((int) square.getPosX() , (int) square.getPosY(), (int) square.getLargeur() , (int) square.getLongueur() );
						shape.setColor(Color.BLACK);
					}
				}
			}
			if(generation.isTriangulated) {
				shape.setColor(Color.RED);
				for(Triangle triangle: generation.triangles){
					Point a = triangle.p1;
					Point b = triangle.p2;
					Point c = triangle.p3;
					shape.line((int) a.x, (int) a.y, (int) b.x, (int) b.y);
					shape.line((int) a.x, (int) a.y, (int) c.x, (int) c.y);
					shape.line((int) b.x, (int) b.y, (int) c.x, (int) c.y);
				}
			}
			if(generation.isST){
				shape.setColor(Color.BLUE);
				for(Hall hall: generation.mainHallList){
					for (Hall successor : hall.sucessors) {
						shape.line((int) hall.getCenterX(), (int) hall.getCenterY(), (int) successor.getCenterX(), (int) successor.getCenterY());
					}
				}
			}
			//corridor
			shape.setColor(Color.BLUE);
			for (Corridor corridor : generation.corridorList) {
				shape.line((int) corridor.entree1.x, (int) corridor.entree1.y, corridor.passage.x, corridor.passage.y);
				shape.line(corridor.passage.x, corridor.passage.y, (int) corridor.entree2.x, (int) corridor.entree2.y);
			}
		}
		
		shape.end();
		if(generation.getState()==Thread.State.TERMINATED){
			GameScreen gameScreen = new GameScreen(game, generation.getMatrice());
			game.setScreen(gameScreen);
			dispose();
		}
		//Gdx.app.debug("FPS", ""+Gdx.graphics.getFramesPerSecond());
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
		music.stop();
	}

	@Override
	public void dispose() {
		shape.dispose();
		music.dispose();
	}

}

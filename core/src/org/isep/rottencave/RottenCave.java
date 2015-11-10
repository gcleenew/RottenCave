package org.isep.rottencave;

import org.isep.rottencave.generation.Point;
import org.isep.rottencave.generation.ProceduralGeneration;
import org.isep.rottencave.generation.Square;
import org.isep.rottencave.generation.Triangle;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class RottenCave extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	private ShapeRenderer shape;
	private ProceduralGeneration generation;
	
	@Override
	public void create () {
		generation = new ProceduralGeneration();

		shape = new ShapeRenderer();
		
		generation.start();
	}

	
	@Override
	public void render () {
		Gdx.gl.glClearColor(255, 255, 255,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		shape.begin(ShapeType.Line);
		
		shape.setColor(Color.BLACK);
		if(generation.squareList!=null){
			synchronized (generation.squareListLock) {		
				for (Square square: generation.squareList){
					if(square.getLargeur() > generation.width_mean*1.15 && square.getLongueur() > generation.height_mean*1.15){
						shape.setColor(Color.RED);
						square.setIsMain(true);
					}
					shape.rect((int) square.getPosX() , (int) square.getPosY(), (int) square.getLargeur() , (int) square.getLongueur() );
					shape.setColor(Color.BLACK);
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
		}
		
		shape.end();
	}
}

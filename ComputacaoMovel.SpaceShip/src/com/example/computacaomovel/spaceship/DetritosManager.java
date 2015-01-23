package com.example.computacaomovel.spaceship;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import org.andengine.entity.shape.IShape;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.ui.activity.BaseGameActivity;

public class DetritosManager {
	/* The mX and mY variables have no real purpose in this recipe, however in
	 * a real factory class, member variables might be used to define position,
	 * color, scale, and more, of a sprite or other entity. */
	private Sprite meteorito;
	private ArrayList<Sprite> detritos;
	private Random random = new Random();
	public int speed = 4;
	//private float scale = 3f;
	private float mX, maxX;
	private float mY, maxY;
    private ITextureRegion enemy;
	 
	// BaseObject constructor, all subtypes should define an mX and mY value on creation
	public DetritosManager(MainActivity game, final int maxX, final int maxY) throws IOException{
		this.maxX = maxX;
		this.maxY = maxY;
		mY = -50;
		LoadContent(game);
	}
		
	private void LoadContent(BaseGameActivity game){
		TextureRegion myTextureRegion;
		BitmapTextureAtlas mBitmapTextureAtlas = new BitmapTextureAtlas(game.getEngine().getTextureManager(), 64, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		myTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, game, "asteroidBig01.png", 0, 0);
		mBitmapTextureAtlas.load();
		enemy = TextureRegionFactory.extractFromTexture(myTextureRegion.getTexture());
     
		meteorito = new Sprite(
				50, 50,
				enemy,
				game.getEngine().getVertexBufferObjectManager());
		meteorito.setScale(0.9f, 0.9f);
	}
	 
	public void Update(){
		for (int i = 0; i < detritos.size();i++){
			mY += speed;
			detritos.get(i).setY(mY - meteorito.getHeight()/2);
			if (detritos.get(i).getY() > maxY){
				detritos.get(i).detachSelf();
				detritos.get(i).dispose();
				detritos.remove(i);
			}
		}		
	}

	public void Add(){
		this.mX = random.nextFloat()*maxX;
		mY = -meteorito.getHeight();
		meteorito.setY(mY - meteorito.getHeight()/2);
		meteorito.setX(mX - meteorito.getWidth()/2);
		detritos.add(meteorito);
	}
	 
	public boolean DetectColision(IShape otherShape){
		return (this.meteorito.collidesWith(otherShape));
	}
	 
	public Sprite Shape(){
		return this.meteorito;
	}
	
	public void Destroy(IShape shapeu){
		for (int index = 0; index < detritos.size();index++){
			if (this.detritos.get(index).collidesWith(shapeu)){
				detritos.get(index).detachSelf();
				detritos.get(index).dispose();
				detritos.remove(index);
			}
		}
	}
}


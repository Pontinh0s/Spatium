package com.example.computacaomovel.spaceship;

import java.io.IOException;
import java.util.Random;

import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.entity.shape.IShape;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;

import android.content.Context;

public class Meteorito {
	/* The mX and mY variables have no real purpose in this recipe, however in
	 * a real factory class, member variables might be used to define position,
	 * color, scale, and more, of a sprite or other entity. */
	private Sprite meteorito;
	private Random random = new Random();
	public int speed = 4;
	private float scale = 3f;
	private float mX, maxX;
	private float mY, maxY;
    private ITextureRegion enemy;
	 
	 // BaseObject constructor, all subtypes should define an mX and mY value on creation
	 public Meteorito(MainActivity game, final int maxX, final int maxY) throws IOException{
		 this.maxX = maxX;
		 this.maxY = maxY;
		 mY = -50;
		 LoadContent(game);
	 }
		
	private void LoadContent(MainActivity game) throws IOException{
		TextureRegion myTextureRegion;
		//BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("spritesheets/");
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
		 mY += speed;
		 if(mY >= maxY)
			 Restart();
		 else
			 meteorito.setY(mY);
	 }

	 public void Restart(){
		 this.mX = random.nextFloat()*maxX;
		 mY = -meteorito.getHeight();
		 meteorito.setY(mY - meteorito.getHeight()/2);
		 meteorito.setX(mX - meteorito.getWidth()/2);
	 }
	 
	 public boolean DetectColision(IShape otherShape){
		 return (this.meteorito.collidesWith(otherShape));
	 }
	 
	 public Sprite Shape(){
		 return this.meteorito;
	 }
}
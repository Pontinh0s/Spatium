package com.example.computacaomovel.spaceship;

import java.util.Random;

import org.andengine.engine.Engine;
import org.andengine.entity.shape.IShape;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;

import android.content.Context;

public class Meteorito {
	/* The mX and mY variables have no real purpose in this recipe, however in
	 * a real factory class, member variables might be used to define position,
	 * color, scale, and more, of a sprite or other entity. */
	private Sprite meteorito;
	private Random random = new Random();
	private int speed = 1;
	private int mX, maxX;
	private int mY, inicialY;
	 
	 // BaseObject constructor, all subtypes should define an mX and mY value on creation
	 public Meteorito(Engine mEngine, Context context, final int maxX){
		 this.maxX = maxX;
		 Create(mEngine, context, mX, mY);
		 mY = inicialY = (int) -meteorito.getHeight();
	 }
	 
	 public void Update(){
		 mY += speed;
		 meteorito.setY(mY - meteorito.getHeight()/2);
	 }
	 
	 private void Create(Engine mEngine, Context context, float pX, float pY){
		 this.mX = random.nextInt(maxX);
		 
		 BuildableBitmapTextureAtlas texBanana = new BuildableBitmapTextureAtlas(mEngine.getTextureManager(), 256, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		 TextureRegion regBanana = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texBanana, context.getAssets(), "asteroidBig01.png");
		 texBanana.load();
		 meteorito = new Sprite(0,0,TextureRegionFactory.extractFromTexture(texBanana),mEngine.getVertexBufferObjectManager());
	 }

	 public void Restart(){
		 this.mX = random.nextInt(maxX);
		 mY = inicialY;
		 meteorito.setY(mY - meteorito.getHeight()/2);
		 meteorito.setX(mX - meteorito.getWidth()/2);
	 }
	 
	 public boolean DetectColision(IShape otherShape){
		 return (this.meteorito.collidesWith(otherShape));
	 }
	 
	 public void Destroy(){
		 // Descarregar Texturas
	 }
}
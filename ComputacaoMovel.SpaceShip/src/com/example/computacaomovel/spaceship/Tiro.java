package com.example.computacaomovel.spaceship;

import java.io.IOException;
import java.io.InputStream;

import org.andengine.engine.Engine;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.adt.io.in.IInputStreamOpener;

import android.content.Context;

public class Tiro {
	/* The mX and mY variables have no real purpose in this recipe, however in
	 * a real factory class, member variables might be used to define position,
	 * color, scale, and more, of a sprite or other entity. */
	private Sprite tiro;
	private int speed = 6;
	private float mX;
	private float mY;
	private boolean active = false;
    private ITextureRegion regiao;
	 
	 public Tiro(BaseGameActivity game){
		TextureRegion myTextureRegion;
		BitmapTextureAtlas mBitmapTextureAtlas = new BitmapTextureAtlas(game.getEngine().getTextureManager(), 50, 25, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		myTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, game, "Tiro.png", 0, 0);
		mBitmapTextureAtlas.load();
		regiao = TextureRegionFactory.extractFromTexture(myTextureRegion.getTexture());
  
		tiro = new Sprite(
				-50, -50, regiao,
				game.getEngine().getVertexBufferObjectManager());
		tiro.setScale(0.3f, 0.3f);
		tiro.setRotation(90);
	 }
	 
	 public void Update(){
		 if(active){
			 mY -= speed;
			 tiro.setY(mY - tiro.getHeight()/2);
			 if(tiro.getY() < -tiro.getHeight())
				 active=false;
		 }
	 }
	 
	 public void Fire(final float pX, final float pY){
		 if(active==false){
			 this.mX = pX;
			 mY = pY;
			 tiro.setY(mY - tiro.getHeight() / 2);
			 tiro.setX(mX - tiro.getWidth() / 2);
			 active = true;
		 }
	 }
	 
	 public void Destroy(){
		 tiro.setX(-50);
		 tiro.setY(-50);
		 active = false;
	 }
	 
	 public Sprite Shape(){
		 return this.tiro;
	 }
	 
	 private ITexture LoadTexture(final BaseGameActivity game, final String fileName) throws IOException{
		 ITexture textura = new BitmapTexture(
				 game.getTextureManager(),
				 new IInputStreamOpener() {
					public InputStream open() throws IOException {
						return game.getAssets().open(fileName);
					}
				});
		 
		 return textura;
	 }
}

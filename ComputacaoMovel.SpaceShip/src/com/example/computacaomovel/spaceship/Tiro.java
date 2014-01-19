package com.example.computacaomovel.spaceship;

import org.andengine.engine.Engine;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;

import android.content.Context;

public class Tiro {
	/* The mX and mY variables have no real purpose in this recipe, however in
	 * a real factory class, member variables might be used to define position,
	 * color, scale, and more, of a sprite or other entity. */
	private Sprite tiro;
	private int speed = 2;
	private int mX;
	private int mY;
	 
	 // BaseObject constructor, all subtypes should define an mX and mY value on creation
	 public Tiro(Context context,Engine mEngine){
		 BuildableBitmapTextureAtlas texBanana = new BuildableBitmapTextureAtlas(mEngine.getTextureManager(), 256, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		 TextureRegion regBanana = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texBanana, context.getAssets(), "nave.png");
		 texBanana.load();
		 tiro = new Sprite(0,0,TextureRegionFactory.extractFromTexture(texBanana),mEngine.getVertexBufferObjectManager());
	 }
	 
	 public void Update(){
		 mY -= speed;
		 tiro.setY(mY - tiro.getHeight()/2);
	 }
	 
	 public void Fire(final int pX, final int pY){
		 this.mX = pX;
		 mY = pY;
		 tiro.setY(mY - tiro.getHeight()/2);
		 tiro.setX(mX - tiro.getWidth()/2);
	 }
	 
	 public void Destroy(){
		 
	 }
	 
	 public Sprite Shape(){
		 return this.tiro;
	 }
}

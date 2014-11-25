package com.example.computacaomovel.spaceship;

import java.io.IOException;
import java.util.ArrayList;

import org.andengine.engine.Engine;
import org.andengine.entity.shape.IShape;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;

import android.content.Context;

public class Tiro {
	/* The mX and mY variables have no real purpose in this recipe, however in
	 * a real factory class, member variables might be used to define position,
	 * color, scale, and more, of a sprite or other entity. */
	private Sprite tiro;
	private int speed = 6;
	private float mX;
	private float mY;
    private ITextureRegion regiao;
    private ArrayList<Sprite> tiros;
    private int alturaEcra;
	 
	 public Tiro(MainActivity game, int altura){
		 alturaEcra = altura;
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
		
		 for (int i = 0; i < tiros.size();i++){
			 mY -= speed;
			 tiros.get(i).setY(mY - tiro.getHeight()/2);
			 if (tiros.get(i).getY() > alturaEcra){
				 tiros.get(i).detachSelf();
				 tiros.get(i).dispose();
				 tiros.remove(i);
			 }
		 }		 
	 }
	 
	 public void Fire(final float pX, final float pY){
		 
			 this.mX = pX;
			 mY = pY;
			 tiro.setY(mY - tiro.getHeight() / 2);
			 tiro.setX(mX - tiro.getWidth() / 2);
			 tiros.add(tiro);
	 }
	 
	 public void Destroy(int index){
		 
		 tiros.get(index).detachSelf();
		 tiros.get(index).dispose();
		 tiros.remove(index);
		 
	 }
	 
	 public void Destroy(IShape shapeu){
		 
		 for (int index = 0; index < tiros.size();index++){
			if (this.tiros.get(index).collidesWith(shapeu)){
				
				tiros.get(index).detachSelf();
				tiros.get(index).dispose();
				tiros.remove(index);
			}
		 }
	 }
	 
	 public Sprite Shape(){
		 return this.tiro;
	 }
}

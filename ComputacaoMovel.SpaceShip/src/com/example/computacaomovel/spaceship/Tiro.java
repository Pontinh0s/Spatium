package com.example.computacaomovel.spaceship;

import org.andengine.entity.sprite.Sprite;

public class Tiro {
	/* The mX and mY variables have no real purpose in this recipe, however in
	 * a real factory class, member variables might be used to define position,
	 * color, scale, and more, of a sprite or other entity. */
	private Sprite tiro;
	private int speed = 2;
	private int mX;
	private int mY, inicialY;
	 
	 // BaseObject constructor, all subtypes should define an mX and mY value on creation
	 public Tiro(final int pY){
		 this.inicialY = pY;
	 }
	 
	 public void Update(){
		 mY -= speed;
		 tiro.setY(mY - tiro.getHeight()/2);
	 }
	 
	 public void Create(final int pX){
		 this.mX = pX;
		 mY = inicialY;
		 tiro.setY(mY - tiro.getHeight()/2);
		 tiro.setX(mX - tiro.getWidth()/2);
		 
		 // Carregar Texturas
	 }
	 
	 public void Destroy(){
		 // Descarregar Texturas
	 }
	 
	 public Sprite Shape(){
		 return this.tiro;
	 }
}

package com.example.computacaomovel.spaceship;

import org.andengine.entity.sprite.Sprite;

public class Meteorito {
	/* The mX and mY variables have no real purpose in this recipe, however in
	 * a real factory class, member variables might be used to define position,
	 * color, scale, and more, of a sprite or other entity. */
	private Sprite meteorito;
	private int speed = 1;
	private int mX;
	private int mY, inicialY;
	 
	 // BaseObject constructor, all subtypes should define an mX and mY value on creation
	 public Meteorito(final int pY){
		 this.inicialY = pY;
	 }
	 
	 public void Update(){
		 mY += speed;
		 meteorito.setY(mY - meteorito.getHeight()/2);
	 }
	 
	 public void Create(final int pX){
		 this.mX = pX;
		 mY = inicialY;
		 meteorito.setY(mY - meteorito.getHeight()/2);
		 meteorito.setX(mX - meteorito.getWidth()/2);
		 
		 // Carregar Texturas
	 }

	 public void Restart(final int pX){
		 this.mX = pX;
		 mY = inicialY;
		 meteorito.setY(mY - meteorito.getHeight()/2);
		 meteorito.setX(mX - meteorito.getWidth()/2);
	 }
	 
	 public void Destroy(){
		 // Descarregar Texturas
	 }
	 
	 public Sprite Shape(){
		 return this.meteorito;
	 }
}
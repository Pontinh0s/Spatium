package com.example.computacaomovel.spaceship;

import org.andengine.entity.sprite.Sprite;

public abstract class Enemy {
	
	//Sprite do inimigo
	private Sprite sprite; 
	//velocidades
	private float velocidadeY , velocidadeX;
	private float posX, posY;
	//integridade 
	private float shield;
	private boolean vida;

	
	public Enemy(){
	}
	
	public abstract void shoot();
	
	public void update()
	{
		moverY();
		moverX();
	}
	
	public void moverY()
	{
		posY = posY + velocidadeY;
	}
	
	public void moverX()
	{
		posX = posX + velocidadeX;
	}
	
	public void removeShield(int i)
	{
		if (shield < i){
			shield = 0;
		}else if (shield < 1){
			vida = false;
		}else
			shield = shield - i;
	}
	
	public void generateShield(float rate)
	{
		shield += rate;
	}
	
	public float getPosX() {
		return posX;
	}

	public float getPosY() {
		return posY;
	}

	public float getShield() {
		return shield;
	}

	public boolean getVida() {
		return vida;
	}
}

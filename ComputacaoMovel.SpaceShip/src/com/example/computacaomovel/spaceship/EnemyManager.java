package com.example.computacaomovel.spaceship;

import java.util.ArrayList;

public class EnemyManager{
	
	Enemy enemy;
	private ArrayList<Enemy> inimigos;
	private int alturaEcra;
	private float rate;
	
	 public EnemyManager(MainActivity game, int altura, float rate){
		 alturaEcra = altura;
		 this.rate = rate;
	 }
	
	public void Update(){
			
		
		 for (int i = 0; i < inimigos.size();i++){
			 
			 //Regenera o escudo
			 inimigos.get(i).generateShield(rate);
			 //Movimento do inimigo
			 inimigos.get(i).update();
			 
			 //Elimina o inimigo se passar da altura do ecra
			 if (inimigos.get(i).getPosY() > alturaEcra){
				 inimigos.get(i).getSprite().detachSelf();
				 inimigos.get(i).getSprite().dispose();
				 inimigos.remove(i);
			 }
		 }		 
	
	}
	
	
	
}

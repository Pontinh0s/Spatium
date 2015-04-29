package managers;

import gameObjects.Enemy;

import java.util.ArrayList;
import java.util.Random;

import org.andengine.entity.shape.IShape;

public class EnemyManager{

	private ResourcesManager resources;
	private ArrayList<Enemy> inimigos;
	private float alturaEcra, larguraEcra;
	private float rate;
	private float random;
	private int ciclos;

	public EnemyManager(ResourcesManager resources, float rate){
		alturaEcra = resources.camera.getHeight();
		larguraEcra = resources.camera.getWidth();
		this.rate = rate;
	}

	public void Update(float pSecondsElapsed){
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
				AddEnemy();
			}
		}
		random = new Random().nextInt(45);
		ciclos++;
	}
	
	public boolean colidesWith(IShape shape, int index) {
		return inimigos.get(index).getSprite().collidesWith(shape);
	}
	
	private void AddEnemy(){
		Enemy enemy = new Enemy(resources, null, null ){
			
			@Override
			public void shoot() {
			}
		};
		random = new Random().nextFloat();
		enemy.setPosX(larguraEcra*random);
		random = new Random().nextFloat();
		enemy.setPosY(-enemy.getSprite().getHeight()-(enemy.getSprite().getHeight()*random));
		inimigos.add(enemy);
	}
}
	

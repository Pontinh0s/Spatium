package managers;

import java.util.ArrayList;
import base_classes.Enemy;

public class EnemyManager{

	Enemy enemy;
	private ArrayList<Enemy> inimigos;
	private float alturaEcra;
	private float rate;

	public EnemyManager(ResourcesManager resources, float rate){
		alturaEcra = resources.camera.getHeight();
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

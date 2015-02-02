package managers;

import java.util.ArrayList;
import java.util.Random;

import org.andengine.entity.shape.IShape;

import android.util.Log;
import base_classes.Detrito;

public class DetritosManager {
	private ArrayList<Detrito> detritos;
	private Random random = new Random();
	public int speed = 4;
	private float maxX;
	private float maxY;
	private float timeRand = 1.2f, timer;
	private boolean frameCount = false;
	private ResourcesManager resources;
	private int damage = 1;
	 
	// BaseObject constructor, all subtypes should define an mX and mY value on creation
	public DetritosManager(ResourcesManager resources) {
		this.resources = resources;
		this.maxX = resources.camera.getWidth();
		this.maxY = resources.camera.getHeight();
		detritos = new ArrayList<Detrito>();
	}
	
	public void Update(float pSecondsElapsed, TirosManager tiros){
		// Count frame
		frameCount = !frameCount;
		
		// Out of Screen detection
		if ((detritos.size() > 0) && detritos.get(0).isOutOfBounds(resources.camera.getWidth(), resources.camera.getHeight()))
			Destroy(0);
		
		for (int i = 0; i < detritos.size(); i++){
			// Updates
			detritos.get(i).update(pSecondsElapsed);

			// Colisions
			for (int j = 0; j < tiros.Size(); j++) {
				if ((frameCount && (j%2 == 0)) || (!frameCount && (j%2 == 1)))
					if (detritos.get(i).getSprite().collidesWith(tiros.get(j))){
						tiros.Destroy(j);
						Destroy(i);
						break;
					}
			}

			Log.d("sizesDEBUG", String.format("meteoritos:%d | tiros:%d", detritos.size(), tiros.Size()));
			//Log.d("positionsDEBUG", String.format("meteorito 0  x:%.1f | y:%.1f", detritos.get(0).getSprite().getX(), detritos.get(0).getSprite().getY()));
		}

		//TimerTrigger
		timer += pSecondsElapsed;
		
		if (timer >= timeRand){
			timeRand = random.nextFloat() * (1.40f-0.40f) + 0.40f; // r * (max/min) + min
			AddEntity();
			timer -= timeRand;
			Log.d("createDEBUG", "Meteoro adicionado");
		}
	}

	public void AddEntity(){
		int velX = 0,
			velY = 200;
		Detrito meteoro = new Detrito(resources, 0, 0, velX, velY, 1.1f, maxX, maxY);
		float x = random.nextFloat()*resources.camera.getWidth() - meteoro.getSprite().getWidthScaled();
		meteoro.getSprite().setX(x);
		meteoro.getSprite().setY(-meteoro.getSprite().getHeight());
		detritos.add(meteoro);
	}
	
	public void Destroy(int index){
		detritos.get(index).RemoveSprite();
		detritos.remove(index);
	}
	
	public boolean colidesWith(IShape shape, int index) {
		return detritos.get(index).getSprite().collidesWith(shape);
	}
	
    public int Size() {
    	return detritos.size();
    }
	
	public void DestroyAll(){
		for (int index = 0; index < detritos.size();index++){
			detritos.get(index).RemoveSprite();
		}
		detritos.clear();
	}
	
	public int getDamage() {
		return damage;
	}
}
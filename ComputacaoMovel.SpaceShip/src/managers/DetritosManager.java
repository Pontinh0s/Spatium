package managers;

import java.util.ArrayList;
import java.util.Random;

import org.andengine.entity.shape.IShape;

import android.util.Log;
import base_classes.Detrito;
import base_classes.Tiro;

public class DetritosManager {
	/* The mX and mY variables have no real purpose in this recipe, however in
	 * a real factory class, member variables might be used to define position,
	 * color, scale, and more, of a sprite or other entity. */
	private ArrayList<Detrito> detritos;
	private Random random = new Random();
	public int speed = 4;
	//private float scale = 3f;
	private float maxX;
	private float maxY;
	private float timeRand = 1.2f, timer;
	private ResourcesManager resources;
	 
	// BaseObject constructor, all subtypes should define an mX and mY value on creation
	public DetritosManager(ResourcesManager resources) {
		this.resources = resources;
		this.maxX = resources.camera.getWidth();
		this.maxY = resources.camera.getHeight();
		detritos = new ArrayList<Detrito>();
	}
	 
	public void Update(float pSecondsElapsed, ArrayList<Tiro> tiros){
		boolean isRemoved = false;
		
		for (int i = 0; i < detritos.size(); i++){
			for (int j = 0; j < tiros.size(); j++) {
				if (detritos.get(i).getSprite().collidesWith(tiros.get(j).Shape())){
					tiros.get(i).RemoveSprite();
					detritos.get(i).RemoveSprite();
					isRemoved = true;
					i--;
					Log.d("SpriteDEBUG", "Meteoro tirado");
				}
			}

			if ((isRemoved == false) && (detritos.get(i).update())) {
				detritos.remove(i);
				i--;
				Log.d("SpriteDEBUG", "Meteoro tirado");
			}
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
		float
			velX = 0,
			velY = 4.0f;
		Detrito meteoro = new Detrito(resources, 0, 0, velX, velY, 1.1f, maxX, maxY);
		meteoro.getSprite().setX(random.nextFloat()*maxX);
		meteoro.getSprite().setY(-meteoro.getSprite().getHeight());
		detritos.add(meteoro);
	}
	
	public void Destroy(IShape shapeu){
		for (int index = 0; index < detritos.size();index++){
			detritos.get(index).RemoveSprite();
		}
		detritos.clear();
	}
}


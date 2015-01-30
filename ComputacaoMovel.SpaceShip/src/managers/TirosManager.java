package managers;

import java.util.ArrayList;

import managers.ResourcesManager;
import base_classes.Tiro;

import org.andengine.audio.sound.Sound;
import org.andengine.engine.Engine;
import org.andengine.entity.shape.IShape;
import org.andengine.entity.sprite.Sprite;

public class TirosManager {
	
	/* The mX and mY variables have no real purpose in this recipe, however in
	 * a real factory class, member variables might be used to define position,
	 * color, scale, and more, of a sprite or other entity. */
	private Sound laser;
    private ArrayList<Tiro> tiros;

    private ResourcesManager resources;
    
	public TirosManager(ResourcesManager resources){
		laser = resources.mlaser;
		tiros = new ArrayList<Tiro>();
		this.resources = resources;
	 }
	 
	public void Update(float pSecondsElapsed){
		// Out of Screen detection
		if ((tiros.size() > 0) && (tiros.get(0).isOutOfBounds(resources.camera.getWidth(), resources.camera.getHeight())))
			Destroy(0);
		
		for (int i = 0; i < tiros.size();i++){
			// Updates
			tiros.get(i).update(pSecondsElapsed);

			// Colisions
		}
	}
	 
	public void Fire(final float pX, final float pY){
		Tiro tiro = new Tiro(resources, pX, pY, 0, 240);
		tiros.add(tiro);
		//laser.play();
	}
	
	public void Destroy(int index){
		tiros.get(index).RemoveSprite();
		tiros.remove(index);
	}
	
	public void DestroyAll(){
		for (int index = 0; index < tiros.size();index++){
			tiros.get(index).RemoveSprite();
		}
		tiros.clear();
	}
	
	public Sprite get(int index) {
		return tiros.get(index).getSprite();
	}
	
    public int Size() {
    	return tiros.size();
    }
}
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
	private int speed = 2;
	private float mX;
	private float mY;
    private ArrayList<Tiro> tiros;

	private float alturaEcra;
    private ResourcesManager res;
    
	public TirosManager(ResourcesManager resources){
		alturaEcra = resources.camera.getHeight();
		laser = resources.mlaser;
		tiros = new ArrayList<Tiro>();
		res = resources;
	 }
	 
	public void Update(){
		for (int i = 0; i < tiros.size();i++){
			tiros.get(i).update();
			if (tiros.get(i).getPosY() > alturaEcra){
				tiros.get(i).getTiro().detachSelf();
				tiros.get(i).getTiro().dispose();
				tiros.remove(i);
			}
		}
	}
	 
	public void Fire(final float pX, final float pY){
		Tiro tiro = new Tiro(res);
		this.mX = pX;
		mY = pY;
		tiro.setPosY(mY - tiro.getTiro().getHeight() / 2);
		tiro.setPosX(mX - tiro.getTiro().getWidth() / 2);
		tiros.add(tiro);
		res.engine.getScene().attachChild(tiro.getTiro());
		//laser.play();
	}
	
	public void Destroy(int index){
		tiros.get(index).getTiro().detachSelf();
		tiros.get(index).getTiro().dispose();
		tiros.remove(index);
	}
	
	public void Destroy(IShape shapeu){
		for (int index = 0; index < tiros.size();index++){
			if (this.tiros.get(index).getTiro().collidesWith(shapeu)){
				tiros.get(index).getTiro().detachSelf();
				tiros.get(index).getTiro().dispose();
				tiros.remove(index);
			}
		}
	}
	
    public ArrayList<Tiro> getTiros() {
		return tiros;
	}
}
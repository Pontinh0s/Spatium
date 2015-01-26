package base_classes;

import java.util.ArrayList;
import managers.ResourcesManager;
import org.andengine.entity.shape.IShape;
import org.andengine.entity.sprite.Sprite;

public class Tiro {
	
	/* The mX and mY variables have no real purpose in this recipe, however in
	 * a real factory class, member variables might be used to define position,
	 * color, scale, and more, of a sprite or other entity. */
	private Sprite tiro;
	private int speed = 6;
	private float mX;
	private float mY;
    private ArrayList<Sprite> tiros;
    private float alturaEcra;
	 
	public Tiro(ResourcesManager resources){
		alturaEcra = resources.camera.getHeight();
  
		tiro = new Sprite(
				-50, -50,
				resources.tTiro,
				resources.vbom);
		tiro.setScale(0.3f, 0.3f);
		tiro.setRotation(90);
	 }
	 
	public void Update(){
		for (int i = 0; i < tiros.size();i++){
			mY -= speed;
			tiros.get(i).setY(mY - tiro.getHeight()/2);
			if (tiros.get(i).getY() > alturaEcra){
				tiros.get(i).detachSelf();
				tiros.get(i).dispose();
				tiros.remove(i);
			}
		}
	}
	 
	public void Fire(final float pX, final float pY){
		this.mX = pX;
		mY = pY;
		tiro.setY(mY - tiro.getHeight() / 2);
		tiro.setX(mX - tiro.getWidth() / 2);
		tiros.add(tiro);
	}
	
	public void Destroy(int index){
		tiros.get(index).detachSelf();
		tiros.get(index).dispose();
		tiros.remove(index);
	}
	
	public void Destroy(IShape shapeu){
		for (int index = 0; index < tiros.size();index++){
			if (this.tiros.get(index).collidesWith(shapeu)){
				tiros.get(index).detachSelf();
				tiros.get(index).dispose();
				tiros.remove(index);
			}
		}
	}
	
	public Sprite Shape(){
		return this.tiro;
	}
}
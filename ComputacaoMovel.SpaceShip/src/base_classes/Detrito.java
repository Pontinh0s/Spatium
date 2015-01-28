package base_classes;

import managers.ResourcesManager;

import org.andengine.entity.sprite.Sprite;

import android.util.Log;

public class Detrito {
	//Sprite do inimigo
	private final int layer = 1;
	private Sprite sprite;
	private float velocidadeX , velocidadeY;
	private float maxX, maxY;

	public Sprite getSprite() {
		return sprite;
	}

	private void setSprite(ResourcesManager resources, float X, float Y, float size) {
		this.sprite = new Sprite(X, Y, resources.tMeteorito, resources.vbom);
		//LAYERS!!!!! NEED TO BE DONE
		this.sprite.setScale(size);
	}
	
	public Detrito(ResourcesManager resources, float posX, float posY, float velX, float velY, float size, float maxX, float maxY){
		this.setSprite(resources, posX, posY, size);
		this.velocidadeX = velX;
		this.velocidadeY = velY;
		resources.engine.getScene().attachChild(this.sprite);
		this.maxX = maxX;
		this.maxY = maxY;
	}

	public boolean update() {
		boolean isDestroyed = false;
		moveYby(velocidadeY);
		moveXby(velocidadeX);
		
		if ((sprite.getX() >= maxX) || (sprite.getY() <= -sprite.getWidthScaled()) || (sprite.getY() > maxY)) {
			Log.d("removeDEBUG", String.format("sprite at x:%.2f y:%.2f", sprite.getX(), sprite.getY()));
			RemoveSprite();
			isDestroyed = true;
		}
		
		return isDestroyed;
	}
	
	public void RemoveSprite() {
		sprite.detachSelf();
		sprite.dispose();
	}
	
	private void moveYby(float velocidade) {
		sprite.setY(sprite.getY() + velocidade);
	}
	
	private void moveXby(float velocidade) {
		sprite.setX(sprite.getX() + velocidade);
	}
	
	/* ------ Get's --------
	 * ------  & -----------
	 * ------ Set's --------
	 */

	public float getVelocidadeY() {
		return velocidadeY;
	}

	public void setVelocidadeY(float velocidadeY) {
		this.velocidadeY = velocidadeY;
	}

	public float getVelocidadeX() {
		return velocidadeX;
	}

	public void setVelocidadeX(float velocidadeX) {
		this.velocidadeX = velocidadeX;
	}
}
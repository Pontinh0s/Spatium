package basicClasses;

import managers.ResourcesManager;

import org.andengine.entity.sprite.Sprite;

import android.util.Log;

public class Detrito {
	//Sprite do inimigo
	private final int layer = 1;
	private Sprite sprite;
	private int speedX , speedY;
	private float maxX, maxY;

	public Sprite getSprite() {
		return sprite;
	}
	
	public Detrito(ResourcesManager resources, float posX, float posY, int velX, int velY, float size, float maxX, float maxY){
		this.speedX = velX;
		this.speedY = velY;
		this.maxX = maxX;
		this.maxY = maxY;

		this.sprite = new Sprite(posX, posY, resources.tMeteorito, resources.vbom);
		this.sprite.setScale(size);
		
		resources.engine.getScene().attachChild(this.sprite);
	}

	public void update(float deltaTime) {
		sprite.setPosition(sprite.getX() + speedX * deltaTime, sprite.getY() + speedY * deltaTime);
	}
	
	public void RemoveSprite() {
		sprite.detachSelf();
		sprite.dispose();
	}
	
	/* ------ Get's --------
	 * ------  & -----------
	 * ------ Set's --------
	 */
	
	public boolean isOutOfBounds(float cameraWidth, float cameraHeight) {
		if ((sprite.getX() < -sprite.getWidth()) ||
			(sprite.getY() < -sprite.getHeight()) ||
			(sprite.getX() > cameraWidth) ||
			(sprite.getY() > cameraHeight))
			return true;
		else
			return false;
	}

	public float getVelocidadeY() {
		return speedY;
	}

	public void setVelocidadeY(int velocidadeY) {
		this.speedY = velocidadeY;
	}

	public float getVelocidadeX() {
		return speedX;
	}

	public void setVelocidadeX(int velocidadeX) {
		this.speedX = velocidadeX;
	}
}
package base_classes;

import managers.ResourcesManager;

import org.andengine.audio.sound.Sound;
import org.andengine.entity.sprite.Sprite;

public class Tiro {
	
	/* The mX and mY variables have no real purpose in this recipe, however in
	 * a real factory class, member variables might be used to define position,
	 * color, scale, and more, of a sprite or other entity. */
	private Sound laser;
	private Sprite sprite;
	private int speedX, speedY;

	public Tiro(ResourcesManager resources, float posX, float posY, int speedX, int speedY){
		laser = resources.mlaser;
		this.speedX = speedX;
		this.speedY = speedY;
		
		sprite = new Sprite(
				posX - resources.tTiro.getWidth()/2,
				posY - resources.tTiro.getHeight()/2,
				resources.tTiro,
				resources.vbom);
		sprite.setScale(0.6f, 0.6f);
		sprite.setRotation(90);
		
		resources.engine.getScene().attachChild(this.sprite);
	 }
	 
	public void update(float deltaTime) {
		sprite.setPosition(sprite.getX() + speedX * deltaTime, sprite.getY() - speedY * deltaTime);
	}
	
	public Sprite Shape() {
		return this.sprite;
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

	public Sprite getSprite() {
		return sprite;
	}
}
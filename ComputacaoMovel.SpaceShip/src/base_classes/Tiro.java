package base_classes;

import managers.ResourcesManager;

import org.andengine.audio.sound.Sound;
import org.andengine.entity.sprite.Sprite;

public class Tiro {
	
	/* The mX and mY variables have no real purpose in this recipe, however in
	 * a real factory class, member variables might be used to define position,
	 * color, scale, and more, of a sprite or other entity. */
	private Sound laser;
	private Sprite tiro;
	private float velocidadeY, velocidadeX;
	private float posX, posY;

	public Tiro(ResourcesManager resources){
		laser = resources.mlaser;
		tiro = new Sprite(
				-50, -50,
				resources.tTiro,
				resources.vbom);
		tiro.setScale(0.3f, 0.3f);
		tiro.setRotation(90);
	 }
	 
	public void update() {
		moverY();
		//moverX();
	}
	
	private void moverY() {
		posY = posY - velocidadeY;
	}
	
	private void moverX() {
		posX = posX + velocidadeX;
	}
	
	public Sprite Shape() {
		return this.tiro;
	}
	
	public void RemoveSprite() {
		tiro.detachSelf();
		tiro.dispose();
	}
	
	//Get's && Set's
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

	public float getPosX() {
		return posX;
	}

	public void setPosX(float posX) {
		this.posX = posX;
	}

	public float getPosY() {
		return posY;
	}

	public void setPosY(float posY) {
		this.posY = posY;
	}

	public Sprite getTiro() {
		return tiro;
	}

	public void setTiro(Sprite tiro) {
		this.tiro = tiro;
	}
	

}
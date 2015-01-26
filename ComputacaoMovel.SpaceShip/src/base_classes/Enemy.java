package base_classes;

import managers.ResourcesManager;

import org.andengine.entity.sprite.Sprite;

public abstract class Enemy {
	
	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	//Sprite do inimigo
	private Sprite sprite; 
	//velocidades
	private float velocidadeY , velocidadeX;
	private float posX, posY;
	//integridade 
	private float shield;
	private boolean vida;

	
	public Enemy(ResourcesManager resources){
		//sprite = resources.enemy;
	}
	
	//Metodo abstrato para disparar
	//É necessário fazer override do metodo
	public abstract void shoot();
	
	public void update()
	{
		moverY();
		moverX();
	}
	
	private void moverY()
	{
		posY = posY + velocidadeY;
	}
	
	private void moverX()
	{
		posX = posX + velocidadeX;
	}
	
	//Remove 1 escudo por cada ponto em i, quando tiver 0 escudos a proxima colisão destroi a nave
	public void removeShield(int i)
	{
		if (shield < i){
			shield = 0;
		}else if (shield < 1){
			vida = false;
		}else
			shield = shield - i;
	}
	
	//Regenera o escudo de acordo com o rate recebido
	public void generateShield(float rate)
	{
		shield += rate;
	}
	
	
	/* ------ Get's --------
	 * ------  & -----------
	 * ------ Set's --------
	 */
	public float getPosX() {
		return posX;
	}

	public float getPosY() {
		return posY;
	}

	public float getShield() {
		return shield;
	}

	public boolean getVida() {
		return vida;
	}

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

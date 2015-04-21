package basicClasses;

import managers.ResourcesManager;

import org.andengine.entity.sprite.Sprite;

public abstract class Enemy {
	
	//Sprite do inimigo
	private Sprite sprite; 
	//velocidades
	private float speedY , speedX;
	private float posX, posY;
	//integridade 
	private float shield;
	private int life;
	private boolean isAlive;
	//Tag caso seja necessário
	private String tag;
	private AI_State aI;
	
	private enum AI_State{
		PATTERN,
		ATTACK,
		DEFEND,
		EVADE
	}
	
	public Enemy(ResourcesManager resources,String tag,AI_State ai){
		//sprite = resources.enemy;
		this.tag = tag;
		isAlive = true;
		this.aI = aI;
	}
	
	//Metodo abstrato para disparar
	//É necessário fazer override do metodo
	public abstract void shoot();

	public void update()
	{
		moveY();
		moveX();
	}
	
	private void moveY()
	{
		posY = posY + speedY;
	}
	
	private void moveX()
	{
		posX = posX + speedX;
	}
	
	private void updateAI(AI_State ai){
		this.aI = ai;
	
		 switch (this.aI) {
         	
		 	case PATTERN:
         		System.out.println("pattern are bad.");
         		break;
         		
         	case ATTACK:
         		System.out.println("attack are bad.");
         		break;
         		
         	case DEFEND:
         		System.out.println("defend are bad.");
         		break;
         		
         	case EVADE:
         		System.out.println("evade are bad.");
         		break;
		 }
	}
	
	//Remove 1 escudo por cada ponto em i, quando tiver 0 escudos a proxima colisão 
	//Tira i pontos de vida se a nave ficar sem vida o isAlive fica falso.
	public void gotDamaged(int i)
	{
		if (shield < i){
			shield = 0;
		}else if (shield < 1){
			life -= i;
		}else
			shield = shield - i;
		if (life <= 0)
			isAlive = false;
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

	public int getVida() {
		return life;
	}

	public void setVida(int vida) {
		this.life = vida;
	}
	
	public float getVelocidadeY() {
		return speedY;
	}

	public void setVelocidadeY(float velocidadeY) {
		this.speedY = velocidadeY;
	}

	public float getVelocidadeX() {
		return speedX;
	}

	public void setVelocidadeX(float velocidadeX) {
		this.speedX = velocidadeX;
	}
	
	public void setPosX(float posX) {
		this.posX = posX;
	}

	public void setPosY(float posY) {
		this.posY = posY;
	}

	public void setShield(float shield) {
		this.shield = shield;
	}
	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

}

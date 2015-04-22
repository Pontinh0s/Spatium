package basicClasses;

import managers.ResourcesManager;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import gameObjects.Obstacles;
import player.BaseWeaponComponent;

/**
 * Enemy.java<p>
 * Defines a base object for the Enemy.
 *
 * @category Enemies
 * @author David Malheiro
 * @version 1.1 21/04/2015
 */
public abstract class Enemy extends Obstacles{
	
	//Sprite do inimigo
	private Sprite sprite; 
	//velocidades
	private float speedY , speedX;
	//integridade 
	private float shield;
	private boolean isAlive;
	//Tag caso seja necessário
	private String tag;
	private AI_State aI;
	
	private enum AI_State{
		PATTERN,
		EVADE
	}
	
	public Enemy(float pX, float pY,int hp, ITextureRegion texture,String tag,AI_State ai){
		super(pX, pY, texture);
		//sprite = resources.enemy;
		this.tag = tag;
		isAlive = true;
		this.aI = aI;
	}
	
	/**Faz o inimigo disparar*/
	public abstract void shoot();

	public void update()
	{
		moveY();
		moveX();
	}
	
	/** Artificial Intelligence */
	private void updateAI(AI_State ai){
		this.aI = ai;
	
		 switch (this.aI) {
         	
		 	case PATTERN:
         		System.out.println("pattern are bad.");
         		break;
         		
         	case EVADE:
         		System.out.println("evade are bad.");
         		break;
		 }
	}
	
	//Remove 1 escudo por cada ponto em i, quando tiver 0 escudos a proxima colisão 
	//Tira i pontos de vida se a nave ficar sem vida o isAlive fica falso.
	public void GetDamaged(int i)
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
	
	/** Regenerates the shield */
	public void generateShield(float rate)
	{
		shield += rate;
	}
	
	/** Move the sprite in the Y axis */
	private void moveY()
	{
		pX = pY + speedY;
	}
	
	/** Move the sprite in the X axis */
	private void moveX()
	{
		pX = pX + speedX;
	}
	
	/* ------ Get's --------
	 * ------  & -----------
	 * ------ Set's --------
	 */

}

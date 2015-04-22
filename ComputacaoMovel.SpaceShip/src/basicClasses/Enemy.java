package basicClasses;

import managers.ResourcesManager;

import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.modifier.ease.EaseLinear;

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
	

	//integridade 
	private float shield;
	private boolean isAlive;
	//Tag caso seja necessário
	private String tag;

	
	public Enemy(float pX, float pY, int hp, float shield, float speed, ITextureRegion texture, String tag, Path patternPath){
		super(pX, pY, texture);
		//sprite = resources.enemy;
		this.tag = tag;
		isAlive = true;
		this.registerEntityModifier(new PathModifier(speed, patternPath, EaseLinear.getInstance()));  
	}
	
	//Metodo abstrato para disparar
	//É necessário fazer override do metodo
	public abstract void shoot();

	public void update()
	{

	}
	

	/**Remove 1 escudo por cada ponto em i, quando tiver 0 escudos a proxima colisão 
	*Tira i pontos de vida se a nave ficar sem vida o isAlive fica falso.*/
	public void GetDamaged(int i)
	{
		if (shield < i){
			shield = 0;
		}else if (shield < 1){
			this.hp -= i;
		}else
			shield = shield - i;
		if (this.hp <= 0)
			this.isAlive = false;
	}
	
	/** Regenerates the shield */
	public void generateShield(float rate)
	{
		shield += rate;
	}
	
	
	/* ------ Get's --------
	 * ------  & -----------
	 * ------ Set's --------
	 */

}

package Parallax;

import java.util.ArrayList;

import source.BaseScene;
import managers.ResourcesManager;

/** ParallaxManager_v2.java<p>
 * 
 *
 * @category managers
 * @author Davide Teixeira
 * @version 1.0 25/06/2015
 */
public class ParallaxManager_v2 {
	private ArrayList<ParallaxLayer> layers;
	private final float waveForce;
	
	/** Creates a new empty parralax background with the given wave force.
	 * @param waveForce - The maximum delta position caused by the accelerometer
	 */
	public ParallaxManager_v2(float waveForce) {
		this.waveForce = waveForce;
		layers = new ArrayList<ParallaxLayer>();
	}
	
	/** Adds a layer to the parallax background. */
	public void addLayer(BaseScene scene, ParallaxLayer layer) {
		scene.attachChild(layer);
		layers.add(layer);
	}
	
	/** Updates the layers so they wave by the accelerometer data.
	  * @param <b>accX, accY</b> - Accelerometer data
	 */
	public void UpdateWithAccelerometer(final float accX, final float accY) {
		for (int i = 0; i < layers.size(); i++) {
			layers.get(i).setPosition(layers.get(i).getAnchorX()+accX*waveForce*layers.get(i).getZ(),
									  layers.get(i).getAnchorY()+accY*waveForce*layers.get(i).getZ());
		}
	}
}
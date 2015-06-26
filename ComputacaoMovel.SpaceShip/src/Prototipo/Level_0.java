/**
 * 
 */
package Prototipo;

import managers.Wave;
import managers.Wave.wave_enum;
import android.content.res.Resources;

/**
 * Level_0.java<p>
 *
 *
 * @category Prototipo
 * @author David Malheiro
 * @version 1.0 26/06/2015
 */
public class Level_0 extends BaseLevel {
	
	public Level_0(){

	}

	@Override
	protected void Update(float pSecondsElapsed) {
		super.Update(pSecondsElapsed);
		if(resourcesManager.timer == 5000)
			 new Wave(wave_enum.WAVE_3_V);
		if(resourcesManager.timer == 7000)
			 new Wave(wave_enum.WAVE_3_V);
		if(resourcesManager.timer == 15000)
			 new Wave(wave_enum.WAVE_5_V);
		if(resourcesManager.timer == 20000)
			 new Wave(wave_enum.WAVE_2_LL);
		if(resourcesManager.timer == 25000)
			 new Wave(wave_enum.WAVE_3_V);
		if(resourcesManager.timer == 30000)
			 new Wave(wave_enum.WAVE_3_V);
		if(resourcesManager.timer == 35000)
			 new Wave(wave_enum.WAVE_5_V);
		if(resourcesManager.timer == 45000)
			 new Wave(wave_enum.WAVE_2_LL);
		if(resourcesManager.timer == 48000)
			 new Wave(wave_enum.WAVE_3_V_2_ll);
		if(resourcesManager.timer == 53000)
			 new Wave(wave_enum.WAVE_3_V_2_ll);
		if(resourcesManager.timer == 60000)
			 new Wave(wave_enum.WAVE_5_V_2_ll_1C);
    }

}

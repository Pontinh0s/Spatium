package scene;

import com.example.computacaomovel.spaceship.R;
import com.example.computacaomovel.spaceship.R.id;
import com.example.computacaomovel.spaceship.R.layout;
import com.example.computacaomovel.spaceship.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.ToggleButton;

/*public class Options extends Activity {

	//OPTIONS
	public Boolean mute = MainActivity.mute;
	public int Difficulty = MainActivity.Difficulty;
	public int musicVolume = MainActivity.musicVolume;
	public int soundVolume = MainActivity.soundVolume;
	
	//BUTTONS
	ToggleButton a_mute;
	SeekBar a_musicVolume;
	SeekBar a_soundVolume;
	RatingBar a_difficulty;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_options);

		a_mute = (ToggleButton)findViewById(R.id.toggleButton1);
		a_musicVolume = (SeekBar)findViewById(R.id.SeekBar1);
		a_soundVolume = (SeekBar)findViewById(R.id.SeekBar2);
		a_difficulty = (RatingBar)findViewById(R.id.ratingBar1);
		
		a_mute.setChecked(!mute);
		a_musicVolume.setProgress(musicVolume);
		a_soundVolume.setProgress(soundVolume);
		a_difficulty.setRating(Difficulty);
		
		//EVENTS
		a_mute.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				mute = !a_mute.isChecked();
			}
		});
		a_musicVolume.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){
			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				musicVolume=a_musicVolume.getProgress();
			}
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {}
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {}
		});
		a_soundVolume.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){
			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				soundVolume=a_soundVolume.getProgress();
			}
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {}
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {}
		});
		a_difficulty.setOnRatingBarChangeListener(new OnRatingBarChangeListener(){
			@Override
			public void onRatingChanged(RatingBar arg0, float arg1, boolean arg2) {
				Difficulty=(int) a_difficulty.getRating();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.options, menu);
		return true;
	}

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) 
    {
    	super.onKeyDown(keyCode, event);
    	
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
        	MainActivity.mute = mute;
        	MainActivity.Difficulty = Difficulty;
        	MainActivity.musicVolume = musicVolume;
        	MainActivity.soundVolume = soundVolume;
    		finish();
        }
        return false;
    }
}
*/
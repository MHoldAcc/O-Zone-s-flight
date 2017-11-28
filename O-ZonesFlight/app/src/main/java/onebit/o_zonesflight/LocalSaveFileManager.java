package onebit.o_zonesflight;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Manages Save-Files on the Local Shared Preferences
 * Created by Michael on 20.09.2017.
 */
public class LocalSaveFileManager implements ISaveFileManager {

	/** UI Controller to use for saving */
	private UIController controller = null;

	/**
	 * Initializes and sets UI Controller
	 *
	 * @param uiController
	 * 		UIController to set
	 */
	LocalSaveFileManager(UIController uiController) { SetUIController(uiController); }

	/**
	 * Sets UI Controller
	 *
	 * @param uiController
	 * 		UIController to set
	 */
	private void SetUIController(UIController uiController) { controller = uiController; }

	@Override
	public SavedState LoadGame( ) {
		//load preferences
		SharedPreferences sharedPref = controller.getPreferences(Context.MODE_PRIVATE);
		//get defaults as fallback
		SavedState defaultValue = SavedState.GetDefault();
		//load values from preferences
		int highScore = sharedPref.getInt(controller.getString(R.string.sav_high_score), defaultValue.GetHighScore());
		int coins = sharedPref.getInt(controller.getString(R.string.sav_coins), defaultValue.GetCoins());
		String unlockables = sharedPref.getString(controller.getString(R.string.sav_unlockables), defaultValue.GetUnlockedItemsSaveString());
		String selected = sharedPref.getString(controller.getString(R.string.sav_selected), defaultValue.GetSelectedItemsSaveString());
		//return state with values
		return new SavedState(highScore, coins, unlockables, selected);
	}

	@Override
	public void SaveGame(SavedState savedState) {
		//load preferences
		SharedPreferences sharedPref = controller.getPreferences(Context.MODE_PRIVATE);
		//get editor
		SharedPreferences.Editor editor = sharedPref.edit();
		//put values
		editor.putInt(controller.getString(R.string.sav_high_score), savedState.GetHighScore());
		editor.putInt(controller.getString(R.string.sav_coins), savedState.GetCoins());
		editor.putString(controller.getString(R.string.sav_unlockables), savedState.GetUnlockedItemsSaveString());
		editor.putString(controller.getString(R.string.sav_selected), savedState.GetSelectedItemsSaveString());
		//apply
		editor.apply();
	}

}
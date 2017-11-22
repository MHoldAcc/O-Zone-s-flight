package onebit.o_zonesflight;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Manages Save-Files on the Local Shared Preferences
 * Created by Michael on 20.09.2017.
 */
public class LocalSaveFileManager implements ISaveFileManager {
    /**
     * Initializes and sets UI Controller
     * @param uiController UIController to set
     */
    public LocalSaveFileManager(UIController uiController){
        SetUIController(uiController);
    }

    /**
     * Sets UI Controller
     * @param uiController UIController to set
     */
    public void SetUIController(UIController uiController){
        controller = uiController;
    }

    @Override
    public SavedState LoadGame() {
        SharedPreferences sharedPref = controller.getPreferences(Context.MODE_PRIVATE);
        int defaultValue = 0;
        String defaultUnlockables = "[]";
        int highScore = sharedPref.getInt(controller.getString(R.string.sav_high_score), defaultValue);
        int coins = sharedPref.getInt(controller.getString(R.string.sav_coins), defaultValue);
        String unlockables = sharedPref.getString(controller.getString(R.string.sav_unlockables), defaultUnlockables);
        return new SavedState(highScore, coins, unlockables);
    }

    @Override
    public void SaveGame(SavedState savedState) {
        SharedPreferences sharedPref = controller.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(controller.getString(R.string.sav_high_score), savedState.GetHighscore());
        editor.putInt(controller.getString(R.string.sav_coins), savedState.GetCoins());
        editor.putString(controller.getString(R.string.sav_unlockables), savedState.GetUnlockedItemsSaveString());
        editor.apply();
    }

    /**
     * UI Controller to use for saving
     */
    private static UIController controller = null;
}

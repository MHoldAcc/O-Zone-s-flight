package onebit.o_zonesflight;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by admin on 20.09.2017.
 */
public class LocalSaveFileManager implements ISaveFileManager {
    private String SaveFile;

    public LocalSaveFileManager(UIController uiController){
        SetUIController(uiController);
    }

    public void SetUIController(UIController uiController){
        controller = uiController;
    }

    @Override
    public SavedState LoadGame() {
        SharedPreferences sharedPref = controller.getPreferences(Context.MODE_PRIVATE);
        int defaultValue = 0;
        int highScore = sharedPref.getInt(controller.getString(R.string.saved_high_score), defaultValue);
        return new SavedState(highScore);
    }

    @Override
    public void SaveGame(SavedState savedState) {
        SharedPreferences sharedPref = controller.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(controller.getString(R.string.saved_high_score), savedState.GetHighscore());
        editor.commit();
    }

    private static UIController controller = null;
}

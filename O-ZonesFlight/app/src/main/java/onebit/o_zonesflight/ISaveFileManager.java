package onebit.o_zonesflight;

/**
 * This interface provides methods to save and load save data
 * Created by Michael on 20.09.2017.
 */
public interface ISaveFileManager {
    /**
     * Loads save state
     * @return save state loaded
     */
    SavedState LoadGame();

    /**
     * Saves save state
     * @param savedState save state to save
     */
    void SaveGame(SavedState savedState);
}

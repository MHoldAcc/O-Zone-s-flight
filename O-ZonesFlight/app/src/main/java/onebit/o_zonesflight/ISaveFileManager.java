package onebit.o_zonesflight;

/**
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

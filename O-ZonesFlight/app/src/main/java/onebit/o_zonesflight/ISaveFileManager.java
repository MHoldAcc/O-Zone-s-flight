package onebit.o_zonesflight;

/**
 * Created by admin on 20.09.2017.
 */
public interface ISaveFileManager {
    public SavedState LoadGame();
    public void SaveGame(SavedState savedState);
}

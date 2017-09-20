package onebit.o_zonesflight;

/**
 * Created by admin on 20.09.2017.
 */
public class TestSaveState implements ISaveFileManager {
    @Override
    public SavedState LoadGame() {
        return new SavedState(0);
    }

    @Override
    public void SaveGame(SavedState savedState) {
    }
}

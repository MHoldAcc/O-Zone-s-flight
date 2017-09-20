package onebit.o_zonesflight;

/**
 * Created by admin on 20.09.2017.
 */
public class LocalSaveFileManager implements ISaveFileManager {
    private String SaveFile;

    public LocalSaveFileManager(String saveFile){
        SetSaveFile(saveFile);
    }

    public void SetSaveFile(String saveFile){
        if(save == null)
            save = new SavedState(0);
    }

    @Override
    public SavedState LoadGame() {
        return save;
    }

    @Override
    public void SaveGame(SavedState savedState) {
        save = savedState;
    }

    private static SavedState save = null;
}

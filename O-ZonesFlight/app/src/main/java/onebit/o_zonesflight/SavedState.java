package onebit.o_zonesflight;

import java.util.ArrayList;

/**
 * Represents the save data
 * Created by Michael on 20.09.2017.
 */
class SavedState {

	private static final int BackgroundIndex = 0;
	private static final int CharacterIndex = 1;
	private static final int MeteorIndex = 2;
	private static final int CoinIndex = 3;

	/** HighScore to save */
	private int HighScore;

	private int Coins;

	private ArrayList<Integer> UnlockedItems;

	private ArrayList<Integer> SelectedItems;

	/**
	 * Initializes save state
	 *
	 * @param highScore
	 * 		Current highScore
	 * @param coins
	 * 		Current amount of coins
	 * @param unlockedItems
	 * 		Currently unlocked items
	 * @param selectedItems
	 * 		Currently selected items
	 */
	SavedState(int highScore, int coins,
			   ArrayList<Integer> unlockedItems,
			   ArrayList<Integer> selectedItems) {
		HighScore = highScore;
		Coins = coins;
		UnlockedItems = unlockedItems;
		SelectedItems = selectedItems;
	}

	SavedState(int highScore, int coins,
			   String unlockedItemsSaveString,
			   String selectedItemsSaveString) {
		this(	highScore,
				coins,
				StringToArray(unlockedItemsSaveString, Integer::parseInt),
				StringToArray(selectedItemsSaveString, Integer::parseInt));
	}

	private static <T> ArrayList<T> StringToArray(String saveString, Utility.IFunc1<String, T> converter) {
		ArrayList<T> result = new ArrayList<>();
		saveString = saveString.substring(1, saveString.length() - 1);
		for ( String item : saveString.split(",") ) result.add(converter.apply(item));
		return result;
	}

	private static <T> String ArrayToString(ArrayList<T> array, Utility.IFunc1<T, String> converter) {
		StringBuilder result = new StringBuilder("[");
		for ( int i = 0 ; i < array.size() ; i++ ) {
			if ( i > 0 ) result.append(",");
			result.append(converter.apply(array.get(i)));
		}
		result.append("]");
		return result.toString();
	}

	/**
	 * Returns HighScore
	 *
	 * @return The HighScore
	 */
	int GetHighScore( ) {
		return HighScore;
	}

	int GetCoins( ) {
		return Coins;
	}

	Integer GetBackgroundID( ) { return SelectedItems.get(BackgroundIndex); }

	Integer GetCharacterID( ) { return SelectedItems.get(CharacterIndex); }

	Integer GetMeteorID( ) { return SelectedItems.get(MeteorIndex); }

	Integer GetCoinID( ) { return SelectedItems.get(CoinIndex); }

	void SetBackgroundID(int val) { SelectedItems.set(BackgroundIndex, val); }

	void SetCharacterID(int val) { SelectedItems.set(CharacterIndex, val); }

	void SetMeteorID(int val) { SelectedItems.set(MeteorIndex, val); }

	void SetCoinID(int val) { SelectedItems.set(CoinIndex, val); }

	void SetHighScore(int score){ HighScore = score; }

	ArrayList<Integer> GetUnlockedItems( ) { return new ArrayList<>(UnlockedItems); }

	ArrayList<Integer> GetSelectedItems( ) { return new ArrayList<>(SelectedItems); }

	String GetUnlockedItemsSaveString( ) { return ArrayToString(UnlockedItems, Object::toString); }

	String GetSelectedItemsSaveString( ) { return ArrayToString(SelectedItems, Object::toString); }

	void AddMoney(){ Coins++; }

	static SavedState GetDefault(){ return new SavedState(0, 0, "[0,1,2,3]", "[0,1,2,3]"); }
}
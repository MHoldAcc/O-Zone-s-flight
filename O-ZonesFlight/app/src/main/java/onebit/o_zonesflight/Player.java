package onebit.o_zonesflight;

import android.graphics.Bitmap;
import android.graphics.RectF;
import android.util.Size;

/**
 * This class represents the player
 * Created by Michael on 20.09.2017.
 */
public class Player implements IRenderable, ITickable {

	@SuppressWarnings( "unused" )
	private static Texture texture;
	private static Bitmap bmp;

	static void DisposeBitmap(){ if (bmp != null) bmp.recycle(); }
	static void setTexture(Texture texture, Size size) {
		Player.texture = texture;
		bmp = texture.GetBitmap(size);
	}

	/** Current horizontal position of player */
	private float Position;

	/** Initializes players at the bottom center of the world */
	Player( ) { Position = Settings.Environment_Width / 2 - Settings.Player_Width / 2; }

	/**
	 * Gets position of player
	 *
	 * @return current position of player
	 */
	float GetPosition( ) { return Position; }

	/**
	 * Uses bearing to set player position
	 *
	 * @param bearing
	 * 		how much the phone is tilted
	 */
	void ApplyBearing(float bearing) {
		//Ignore values below the DeathZone
		if ( Math.abs(bearing) > Settings.Inputs_DeathZone ) {
			//Applies Input limitation
			bearing = Math.min(Math.max(bearing, -Settings.Inputs_MaxInput), Settings.Inputs_MaxInput);
			//Sets position
			Position += Settings.Player_BaseSpeed * bearing;
			//Screen Bounds
			Position = Math.min(Math.max(Position, 0), Settings.Environment_Width - GetWidth());
		}
	}

	@Override
	public float GetTop( ) { return GetBottom() - GetHeight(); }

	@Override
	public float GetBottom( ) { return Settings.Environment_Height; }

	@Override
	public float GetLeft( ) { return GetPosition(); }

	@Override
	public float GetRight( ) { return GetLeft() + GetWidth(); }

	@Override
	public float GetWidth( ) { return Settings.Player_Width; }

	@Override
	public float GetHeight( ) { return Settings.Player_Height; }

	@Override
	public RectF GetRect( ) { return new RectF(GetLeft(), GetTop(), GetRight(), GetBottom()); }

	@Override
	public Bitmap GetImage( ) { return bmp; }

	@Override
	public void Tick(Game game, Object... data) { ApplyBearing((Float) data[0]); }// data[0] is the bearing

	@Override
	public boolean CanBeRemoved( ) { return false; }
}

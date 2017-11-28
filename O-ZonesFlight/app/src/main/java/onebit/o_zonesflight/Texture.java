package onebit.o_zonesflight;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.util.Size;

/**
 *
 * Created by Silvan Pfister on 27.11.2017.
 */

public class Texture {
	private Resources res;
	private int       ID;
	private int       resID;

	Texture(Resources res, int ID) {
		this.res = res;
		this.ID = ID;
		resID = res.getIdentifier(
				res.getStringArray(R.array.tex_files)[ID],
				"mipmap",
				"onebit.o_zonesflight");
	}

	/**
	 * Gets the description from the resources
	 * @return The description of this texture
	 */
	String GetDescription( ) { return res.getStringArray(R.array.tex_descriptions)[ID]; }

	/**
	 * Gets the name from the resources
	 * @return The name of this texture
	 */
	String GetName( )        { return res.getStringArray(R.array.tex_names)[ID]; }

	/**
	 * Generates a full-scale bitmap
	 * @return The bitmap
	 */
	Bitmap GetBitmap( )      { return BitmapFactory.decodeResource(res, resID); }

	/**
	 * Generates a bitmap.
	 * The bitmap is sampled down to the target size if possible.
	 * @param targetSize The size of the area the bitmap should be displayed on
	 * @return The sampled bitmap
	 */
	Bitmap GetBitmap(Size targetSize) {
		Size    imgSize;
		int     factor  = 1;
		Options options = new Options();
		options.inJustDecodeBounds = true;

		BitmapFactory.decodeResource(res, resID, options);

		imgSize = new Size(options.outWidth, options.outHeight);

		while ( imgSize.getWidth() / factor >= targetSize.getWidth() &&
				imgSize.getHeight() / factor >= targetSize.getHeight() )
			factor *= 2;

		options.inJustDecodeBounds = false;

		// if factor is 1, this will prevent an inSampleSize of 0.
		options.inSampleSize = (int)Math.ceil(factor / 2f);

		return BitmapFactory.decodeResource(res, resID, options);
	}
}
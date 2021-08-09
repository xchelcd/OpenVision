package com.idax.openvision.Extra

import android.graphics.Bitmap
import android.os.Environment
import java.io.File
import java.io.FileOutputStream

class Utils {

    companion object {
        fun getMeasure(widht: Int, height: Int, ratio: Float): Pair<Int, Int> {
            var w = widht
            var h = height
            if (w == 0 && h == 0) return Pair(0, 0)

            if (w > 0) h = (w * ratio).toInt()
            else if (h > 0) w = (h / ratio).toInt()

            return Pair(w, h)
        }
    }

    /*
    public static Bitmap resizeBitmap(String photoPath, int targetW, int targetH) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        }

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true; //Deprecated API 21

        return BitmapFactory.decodeFile(photoPath, bmOptions);
    }

    public static byte[] bitmapToArray(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
     */

    @JvmOverloads
    fun saveBitmap(bitmap: Bitmap, filename: String = "preview.png") {
        val root = Environment.getExternalStorageDirectory()
            .absolutePath + File.separator + "tensorflow"
        //LOGGER.i("Saving %dx%d bitmap to %s.", bitmap.getWidth(), bitmap.getHeight(), root);
        val myDir = File(root)
        if (!myDir.mkdirs()) {
            // LOGGER.i("Make dir failed");
        }
        val file = File(myDir, filename)
        if (file.exists()) {
            file.delete()
        }
        try {
            val out = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 99, out)
            out.flush()
            out.close()
        } catch (e: Exception) {
            //LOGGER.e(e, "Exception!");
        }
    }
}
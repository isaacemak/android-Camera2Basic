package curi;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.mylibrary.NativeLib;

/**
 * Created by isaac on 2016/11/17.
 */

public class ImageProcessor {
    static String  TAG= "ImageProcessor";
    public static void  deal(byte[] bytes, int width, int height){
        Log.v(TAG, String.format("bytes' length, width , height: %d, %d, %d", bytes.length, width, height));

        Log.v(TAG, Thread.currentThread().getName());
        NativeLib.getYimage(bytes, width, height);

    }
}

package com.example.mylibrary;

/**
 * Created by isaac on 2016/11/4.
 */

public class NativeLib {
    static {
        System.loadLibrary("native-lib");
    }

    public native static void getYimage(byte[] bytes, int width, int height);

}

#include <jni.h>
#include <string>
#include <opencv2/opencv.hpp>
#include <android/log.h>
#define LOG_TAG "ndklog"


using namespace cv;



static int counter=0;

extern "C"
JNIEXPORT void JNICALL
Java_com_example_mylibrary_NativeLib_getYimage(JNIEnv *env, jclass type,
                                               jbyteArray bytes_, jint width,
                                               jint height) {
    jbyte *bytes = env->GetByteArrayElements(bytes_, NULL);

    // TODO
    Mat yMat(height, width, CV_8UC1, bytes);
    if ((++counter)%20==0){
        imwrite("/sdcard/isaac.jpg", yMat);
        counter=0;
    }

    env->ReleaseByteArrayElements(bytes_, bytes, 0);
}
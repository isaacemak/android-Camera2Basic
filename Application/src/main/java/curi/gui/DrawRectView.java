package curi.gui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by xuxuan on 2016/11/9.
 * 这个图是用来画矩形的，原理就是将这个View
 */

public class DrawRectView extends SurfaceView implements SurfaceHolder.Callback {

    protected SurfaceHolder sh;
    private int mWidth;
    private int mHeight;
    private Bitmap drawBitmap;
    private Canvas mCanvas;
    private int[] data;
    double index_x;
    double index_y;

    public DrawRectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        sh = getHolder();
        sh.addCallback(this);
        sh.setFormat(PixelFormat.TRANSPARENT);
        setZOrderOnTop(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mWidth = width;
        mHeight = height;

        drawBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ALPHA_8);
        mCanvas = new Canvas(drawBitmap);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public void set_scaled_surface_size(int mWidth,int mHeight, int surface_width, int surface_height){
        scaledWidth= mWidth;
        scaledHeight= mHeight;
        this.surface_width= surface_width;
        this.surface_height= surface_height;
    }

    int scaledWidth=0, scaledHeight=0, surface_width=0, surface_height=0;
    public void drawRect(int[] data){
        if (0==scaledWidth || 0==scaledHeight || 0==surface_width || 0==surface_height)
            return;
        drawRect(data, scaledWidth, scaledHeight, surface_width, surface_height);

    }

    //draw the rect to circle the object
    public void drawRect(int[] data, int mWidth,int mHeight, int surface_width, int surface_height){
        Canvas canvas = sh.lockCanvas();
        canvas.drawColor(Color.TRANSPARENT);
        //first, clean the screen of the last frame
        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawPaint(paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        //set the parameters of the paint
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        //to get the scale between the surface size and image
        //the size of the surface get from the function of onSurfaceTextureAvailable and onSurfaceTextureSizeChanged
        //the size of image get from the ImageReader's listener
        index_x = (double)surface_width/mWidth;
        index_y = (double)surface_height/mHeight;

        //then turn the data from the algorithm in jni to match the surface
        data[0] = (int) (data[0]*index_x);
        data[1] =(int) (data[1]*index_y);
        data[2] = (int) (data[2]*index_x);
        data[3] = (int) (data[3]*index_y);

        Rect rect = new Rect(data[0],data[1],data[2],data[3]);
        canvas.drawRect(rect,paint);
        sh.unlockCanvasAndPost(canvas);
    }
    /*
    * if the camera cannot find the object, then clean the
    * screen
    */
    public void cleanScreen(){
        Canvas canvas = sh.lockCanvas();
        if(canvas!=null){
            Paint paint = new Paint();
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            canvas.drawPaint(paint);
        }
        sh.unlockCanvasAndPost(canvas);
    }
}


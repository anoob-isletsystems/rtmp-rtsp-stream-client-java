package com.pedro.encoder.utils.gl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.util.Log;


public class MultilineTextStreamObject extends StreamObjectBase{
    private static final String TAG = "MultilineStreamObject";

    private int numFrames;
    private Bitmap imageBitmap;

    public MultilineTextStreamObject() {
    }

    @Override
    public int getWidth() {
        return imageBitmap != null ? imageBitmap.getWidth() : 0;
    }

    @Override
    public int getHeight() {
        return imageBitmap != null ? imageBitmap.getHeight() : 0;
    }

    public void load(String text, float textSize, int textColor, Typeface typeface) {
        numFrames = 1;
        imageBitmap = textAsBitmap(text, textSize, textColor, typeface);
        Log.i(TAG, "finish load text");
    }

    @Override
    public void recycle() {
        if (imageBitmap != null && !imageBitmap.isRecycled()) imageBitmap.recycle();
    }

    private Bitmap textAsBitmap(String text, float textSize, int textColor, Typeface typeface) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setAlpha(255);
        if (typeface != null) paint.setTypeface(typeface);
        paint.setTextAlign(Paint.Align.LEFT);
        String[] lines = text.split("\n");
        int noOfLines= lines.length;
        float baseline = -paint.ascent(); // ascent() is negative
        int widthmax=0;
        for (String line: lines) {
            int width = (int) (paint.measureText(line) + 0.5f); // round
            if (width>widthmax){
                widthmax=width;
            }
        }
        int height = (int) (baseline + paint.descent() + 0.5f)*noOfLines;
        Bitmap image = Bitmap.createBitmap(widthmax, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        float y=  baseline;
        for (int i = 0; i < lines.length; ++i) {
            canvas.drawText(lines[i], 0, y + (baseline + paint.descent() + 0.5f) * i, paint);
        }

        return image;
    }

    @Override
    public int getNumFrames() {
        return numFrames;
    }

    @Override
    public Bitmap[] getBitmaps() {
        return new Bitmap[]{imageBitmap};
    }

    @Override
    public int updateFrame() {
        return 0;
    }
}

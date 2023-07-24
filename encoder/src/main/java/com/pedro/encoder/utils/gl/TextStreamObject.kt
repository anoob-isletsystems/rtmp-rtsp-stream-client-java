/*
 * Copyright (C) 2021 pedroSG94.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pedro.encoder.utils.gl

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.util.DisplayMetrics
import android.util.Log
import com.pedro.encoder.R


/**
 * Created by pedro on 23/09/17.
 */
class TextStreamObject : StreamObjectBase() {
    private var numFrames = 0
    private var imageBitmap: Bitmap? = null
    override fun getWidth(): Int {
        return if (imageBitmap != null) imageBitmap!!.width else 0
    }

    override fun getHeight(): Int {
        return if (imageBitmap != null) imageBitmap!!.height else 0
    }

    fun load(text: String, textSize: Float, textColor: Int, typeface: Typeface?) {
        numFrames = 1
        imageBitmap = textAsBitmap(text, textSize, textColor, typeface)
        Log.i(TAG, "finish load text")
    }

    fun load(
        text: String,
        text1: String,
        text2: String,
        textSize: Float,
        textColor: Int,
        typeface: Typeface?
    ) {
        numFrames = 1
        imageBitmap = textAsBitmap(text, text1, text2, textSize, textColor, typeface)
        Log.i(TAG, "finish load text")
    }

    fun load(
        text: String,
        text1: String,
        text2: String,
        textSize: Float,
        textColor: Int,
        typeface: Typeface?,
        bitmap: Bitmap?
    ) {
        numFrames = 1
        imageBitmap = textAsBitmap(text, text1, text2, textSize, textColor, typeface,bitmap)
        Log.i(TAG, "finish load text")
    }
    override fun recycle() {
        if (imageBitmap != null && !imageBitmap!!.isRecycled) imageBitmap!!.recycle()
    }

    //  private Bitmap textAsBitmap(String text, float textSize, int textColor, Typeface typeface) {
    //    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    //    paint.setTextSize(textSize);
    //    paint.setColor(textColor);
    //    paint.setAlpha(255);
    //    if (typeface != null) paint.setTypeface(typeface);
    //    paint.setTextAlign(Paint.Align.LEFT);
    //
    //    float baseline = -paint.ascent(); // ascent() is negative
    //    int width = (int) (paint.measureText(text) + 0.5f); // round
    //    int height = (int) (baseline + paint.descent() + 0.5f);
    //    Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    //    Canvas canvas = new Canvas(image);
    //    canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
    //
    //    canvas.drawText(text, 0, baseline, paint);
    //    return image;
    //  }
    private fun textAsBitmap(
        text: String,
        textSize: Float,
        textColor: Int,
        typeface: Typeface?
    ): Bitmap {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.textSize = textSize
        paint.color = textColor
        paint.alpha = 255
        if (typeface != null) paint.typeface = typeface
        paint.textAlign = Paint.Align.LEFT
        val lines = text.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val noOfLines = lines.size
        val baseline = -paint.ascent() // ascent() is negative
        var widthMax = 0
        for (line in lines) {
            val width = (paint.measureText(line) + 0.5f).toInt() // round
            if (width > widthMax) {
                widthMax = width
            }
        }
        val height = (baseline + paint.descent() + 0.5f).toInt() * noOfLines
        val image = Bitmap.createBitmap(widthMax * 3, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(image)
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        for (i in lines.indices) {
            canvas.drawText(lines[i], 0f, baseline + (baseline + paint.descent() + 0.5f) * i, paint)
            canvas.drawText(
                lines[i],
                (widthMax + 1).toFloat(),
                baseline + (baseline + paint.descent() + 0.5f) * i,
                paint
            )
            canvas.drawText(
                lines[i],
                (widthMax * 2 + 1).toFloat(),
                baseline + (baseline + paint.descent() + 0.5f) * i,
                paint
            )
        }
        return image
    }

    private fun textAsBitmap(
        text: String,
        text1: String,
        text2: String,
        textSize: Float,
        textColor: Int,
        typeface: Typeface?
    ): Bitmap {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.textSize = textSize
        paint.color = textColor
        paint.alpha = 255
        if (typeface != null) paint.typeface = typeface
        paint.textAlign = Paint.Align.LEFT
        val lines = text.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val lines1 = text1.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val lines2 = text2.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val paint1 = Paint()
        paint1.color = Color.BLACK
        paint1.strokeWidth = 2f
//        paint1.style = Paint.Style.STROKE
        val noOfLines = lines.size
        val baseline = -paint.ascent() // ascent() is negative
        var widthMax = 0
        var widthMax1 = 0
        var widthMax2 = 0
        for (i in lines.indices) {
            val width = (paint.measureText(lines[i]) + 0.5f).toInt() // round
            val width1 = (paint.measureText(lines1[i]) + 0.5f).toInt() // round
            val width2 = (paint.measureText(lines2[i]) + 0.5f).toInt() // round
            if (width > widthMax) {
                widthMax = width
            }
            if (width1 > widthMax1) {
                widthMax1 = width1
            }
            if (width2 > widthMax2) {
                widthMax2 = width2
            }
        }
        val totalWidth = widthMax + widthMax1 + widthMax2 + 10
        val height = (baseline + paint.descent() + 0.5f).toInt() * noOfLines
        val image = Bitmap.createBitmap(1280, 720, Bitmap.Config.ARGB_8888)

        val canvas = Canvas(image)
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)

        canvas.drawRect(
            (0).toFloat(),
            0f,
            (1280).toFloat(),
            height.toFloat(),
            paint1
        )
        for (i in lines.indices) {
            canvas.drawText(lines[i], 0f, baseline + (baseline + paint.descent() + 0.5f) * i, paint)
            canvas.drawText(
                lines1[i],
                (widthMax + 5).toFloat(),
                baseline + (baseline + paint.descent() + 0.5f) * i,
                paint
            )
            canvas.drawText(
                lines2[i],
                (widthMax + widthMax1 + 5).toFloat(),
                baseline + (baseline + paint.descent() + 0.5f) * i,
                paint
            )
        }
//        canvas.drawRect(0f, 0f, widthMax.toFloat(), height.toFloat(), paint1)
//        canvas.drawRect(
//            widthMax.toFloat(),
//            0f,
//            (widthMax + widthMax1).toFloat(),
//            height.toFloat(),
//            paint1
//        )

        return image
    }
    private fun textAsBitmap(
        text: String,
        text1: String,
        text2: String,
        textSize: Float,
        textColor: Int,
        typeface: Typeface?,
        bitmap: Bitmap?
    ): Bitmap {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.textSize = textSize
        paint.color = textColor
        paint.alpha = 255
        if (typeface != null) paint.typeface = typeface
        paint.textAlign = Paint.Align.LEFT
        val lines = text.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val lines1 = text1.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val lines2 = text2.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val paint1 = Paint()
        paint1.color = Color.BLACK
        paint1.strokeWidth = 2f
//        paint1.style = Paint.Style.STROKE
        val noOfLines = lines.size
        val baseline = -paint.ascent() // ascent() is negative
        var widthMax = 0
        var widthMax1 = 0
        var widthMax2 = 0
        for (i in lines.indices) {
            val width = (paint.measureText(lines[i]) + 0.5f).toInt() // round
            val width1 = (paint.measureText(lines1[i]) + 0.5f).toInt() // round
            val width2 = (paint.measureText(lines2[i]) + 0.5f).toInt() // round
            if (width > widthMax) {
                widthMax = width
            }
            if (width1 > widthMax1) {
                widthMax1 = width1
            }
            if (width2 > widthMax2) {
                widthMax2 = width2
            }
        }
        val totalWidth =1280- (widthMax + widthMax1 + widthMax2)-20
        val height = (baseline + paint.descent() + 0.5f).toInt() * noOfLines
        val image = Bitmap.createBitmap(1280, 720, Bitmap.Config.ARGB_8888)

        val canvas = Canvas(image)
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        if(bitmap!=null) {
        canvas.drawBitmap(bitmap,1180f,0f,paint)
        }
        canvas.drawRect(
            (0).toFloat(),
            (720-height).toFloat(),
            (1280).toFloat(),
            (720).toFloat(),
            paint1
        )
        for (i in lines.indices) {
            canvas.drawText(lines[noOfLines-i-1], 10f, 715-( (baseline + paint.descent() + 0.5f) * i), paint)
            canvas.drawText(
                lines1[noOfLines-i-1],
                (widthMax +(totalWidth/2)).toFloat(),
                715-((baseline + paint.descent() + 0.5f) * i),
                paint
            )
            canvas.drawText(
                lines2[noOfLines-i-1],
                (widthMax + widthMax1 + (totalWidth)).toFloat(),
                715-((baseline + paint.descent() + 0.5f) * i),
                paint
            )
        }
//        canvas.drawRect(0f, 0f, widthMax.toFloat(), height.toFloat(), paint1)
//        canvas.drawRect(
//            widthMax.toFloat(),
//            0f,
//            (widthMax + widthMax1).toFloat(),
//            height.toFloat(),
//            paint1
//        )

        return image
    }
    override fun getNumFrames(): Int {
        return numFrames
    }

    override fun getBitmaps(): Array<Bitmap?> {
        return arrayOf(imageBitmap)
    }

    override fun updateFrame(): Int {
        return 0
    }

    companion object {
        private const val TAG = "TextStreamObject"
    }
}
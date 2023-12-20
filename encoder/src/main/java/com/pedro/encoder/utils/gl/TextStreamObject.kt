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
import android.os.Build
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
        bitmap: Bitmap?,
        bitmap1:Bitmap?
    ) {
        numFrames = 1
        imageBitmap = textAsBitmap(text, text1, text2, textSize, textColor, typeface,bitmap,bitmap1)
        Log.i(TAG, "finish load text")
    }
    fun load(
        text: String,
        text1: String,
        text2: String,
        textSize: Float,
        textColor: Int,
        typeface: Typeface?,
        bitmap: Bitmap?,
        bitmap1:Bitmap?,
        scorecard: String?,
    ) {
        numFrames = 1
        imageBitmap = textAsBitmap(text, text1, text2, textSize, textColor, typeface,bitmap,bitmap1, scorecard)
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
        bitmap: Bitmap?,
        bitmap1: Bitmap?
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
        paint1.color = Color.rgb(39, 69, 245)
        paint1.strokeWidth = 2f
        val paint2 = Paint()
        paint2.color = Color.rgb(245, 39, 145)
        paint2.strokeWidth = 2f
        val paint3 = Paint()
        paint3.color = Color.rgb(46, 245, 39)
        paint3.strokeWidth = 2f
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
        if (bitmap1!=null){
            val bitmapHeight=bitmap1.height
            val bitmapWidth =bitmap1.width
            val heightAdjust=(720-bitmapHeight)/2
            val widthAdjust=(1280-bitmapWidth)/2
            canvas.drawBitmap(bitmap1,widthAdjust.toFloat(),heightAdjust.toFloat(),paint)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if(lines[0]!=' '.toString()){
                canvas.drawRoundRect(
                        (0).toFloat(),
                        (720-height).toFloat(),
                        (widthMax+(totalWidth/2)-30).toFloat(),
                        (720).toFloat(),
                        (16).toFloat(),
                        (16).toFloat(),
                        paint1
                )

                            }

            canvas.drawRoundRect(
                    (widthMax+(totalWidth/2)-20).toFloat(),
                    (720-height).toFloat(),
                    (widthMax+widthMax1+(totalWidth/2)+20).toFloat(),
                    (720).toFloat(),
                    (16).toFloat(),
                    (16).toFloat(),
                    paint2
            )
            if(lines2[0]!=' '.toString()){
            canvas.drawRoundRect(
                    (widthMax+widthMax1+(totalWidth/2)+30).toFloat(),
                    (720-height).toFloat(),
                    (1280).toFloat(),
                    (720).toFloat(),
                    (16).toFloat(),
                    (16).toFloat(),
                    paint3
            )
            }
        }
        for (i in lines.indices) {
            canvas.drawText(lines[noOfLines-i-1], 20f, 715-( (baseline + paint.descent() + 0.5f) * i), paint)
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

    private fun textAsBitmap(
        text: String,
        text1: String,
        text2: String,
        textSize: Float,
        textColor: Int,
        typeface: Typeface?,
        bitmap: Bitmap?,
        bitmap1: Bitmap?,
        scorecard: String?,
    ): Bitmap {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG) //teamNamePaint
        paint.textSize = textSize
        paint.color = Color.BLACK
        paint.alpha = 255
        val playerNamePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        playerNamePaint.textSize = 20f
        playerNamePaint.color = textColor
        playerNamePaint.alpha = 255
        val teamShortNamePaint = Paint(Paint.ANTI_ALIAS_FLAG) //teamNamePaint
        teamShortNamePaint.textSize = 20f
        teamShortNamePaint.color = Color.BLACK
        teamShortNamePaint.alpha = 255
        val thisOverPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        thisOverPaint.textSize = 16f
        thisOverPaint.color = textColor
        thisOverPaint.alpha = 255
        val bottomMessagePaint = Paint(Paint.ANTI_ALIAS_FLAG) //teamNamePaint
        bottomMessagePaint.textSize = 16f
        bottomMessagePaint.color = Color.BLACK
        bottomMessagePaint.alpha = 255
        if (typeface != null) {
            paint.typeface = typeface
            playerNamePaint.typeface=typeface
            teamShortNamePaint.typeface=typeface
            thisOverPaint.typeface=typeface
            bottomMessagePaint.typeface=typeface
        }
        paint.textAlign = Paint.Align.LEFT
        playerNamePaint.textAlign=Paint.Align.LEFT
        teamShortNamePaint.textAlign= Paint.Align.LEFT
        thisOverPaint.textAlign= Paint.Align.LEFT
        bottomMessagePaint.textAlign= Paint.Align.LEFT
        val whiteRectPaint= Paint()
        whiteRectPaint.color=Color.WHITE
        whiteRectPaint.strokeWidth= 2f
        val playerRectPaint = Paint ()
        playerRectPaint.color= Color.rgb(49,0,121)
        playerRectPaint.strokeWidth = 2f
        val scoreRectPaint = Paint ()
        scoreRectPaint.color= Color.rgb(233,72,81)
        scoreRectPaint.strokeWidth = 2f
        val thisOverRectPaint = Paint ()
        thisOverRectPaint.color= Color.rgb(229,28,187)
        thisOverRectPaint.strokeWidth = 2f
        val lines = text.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray() //batter
        val lines1 = text1.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()//score+over
        val lines2 = text2.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()//bowler+thisOver

        val paint1 = Paint()
        paint1.color = Color.rgb(39, 69, 245)
        paint1.strokeWidth = 2f
        val paint2 = Paint()
        paint2.color = Color.rgb(245, 39, 145)
        paint2.strokeWidth = 2f
        val paint3 = Paint()
        paint3.color = Color.rgb(46, 245, 39)
        paint3.strokeWidth = 2f
//        paint1.style = Paint.Style.STROKE
        val linePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        linePaint.color=Color.LTGRAY
        linePaint.strokeWidth=1f

        val image = Bitmap.createBitmap(1280, 720, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(image)
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        if(bitmap!=null) {
            canvas.drawBitmap(bitmap,1180f,0f,paint)
        }
        if (bitmap1!=null){
            val bitmapHeight=bitmap1.height
            val bitmapWidth =bitmap1.width
            val heightAdjust=(720-bitmapHeight)/2
            val widthAdjust=(1280-bitmapWidth)/2
            canvas.drawBitmap(bitmap1,widthAdjust.toFloat(),heightAdjust.toFloat(),paint)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if(lines[0]!=' '.toString()){
                canvas.drawRoundRect(
                    (-40).toFloat(),
                    (625).toFloat(),
                    (230).toFloat(),
                    (715).toFloat(),
                    (64).toFloat(),
                    (64).toFloat(),
                    whiteRectPaint
                )
            }

            canvas.drawRoundRect(
                (230).toFloat(),
                (625).toFloat(),
                (1050).toFloat(),
                (715).toFloat(),
                (64).toFloat(),
                (64).toFloat(),
                playerRectPaint
            )
            if(lines2[0]!=' '.toString()){
                canvas.drawRoundRect(
                    (1050).toFloat(),
                    (625).toFloat(),
                    (1320).toFloat(),
                    (715).toFloat(),
                    (64).toFloat(),
                    (64).toFloat(),
                    whiteRectPaint
                )
                canvas.drawRoundRect(
                    (510).toFloat(),
                    (633).toFloat(),
                    (770).toFloat(),
                    (707).toFloat(),
                    (64).toFloat(),
                    (64).toFloat(),
                    whiteRectPaint
                )
                canvas.drawRoundRect(
                    (580).toFloat(),
                    (638).toFloat(),
                    (700).toFloat(),
                    (678).toFloat(),
                    (64).toFloat(),
                    (64).toFloat(),
                    scoreRectPaint
                )
                canvas.drawLine(512f,682f,768f,682f,linePaint)
            }
        }
        if(lines2[0]!=' '.toString()){
            if(scorecard!=null){
                val lines3 = scorecard.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val teamFullName=lines3[0].split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val teamShortName=lines3[1].split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                canvas.drawText(teamFullName[0], 10f, 670+(paint.descent()+0.75f), paint)
                val teamNameWidth = (paint.measureText(teamFullName[1]) + 0.5f).toInt()
                canvas.drawText(teamFullName[1], (1270-teamNameWidth).toFloat(), 670+(paint.descent()+0.75f), paint)
                val teamShortName1=teamShortName[0].substring(0,
                    teamShortName[0].length.coerceAtMost(4)
                )
                val teamShortName2=teamShortName[1].substring(0,teamShortName[0].length.coerceAtMost(4))
                val teamShortNameWidth=(teamShortNamePaint.measureText(teamShortName2) + 0.5f).toInt()
                canvas.drawText(teamShortName1,530f,658+(teamShortNamePaint.descent()+0.75f),teamShortNamePaint)
                canvas.drawText(teamShortName2,(750-teamShortNameWidth).toFloat(),658+(teamShortNamePaint.descent()+0.75f),teamShortNamePaint)
            }
            canvas.drawText(lines[0], 260f, 650+( playerNamePaint.descent() + 0.75f) , playerNamePaint)
            canvas.drawText(lines[1], 260f, 690+( playerNamePaint.descent() + 0.75f) , playerNamePaint)
            canvas.drawText(lines2[0], 780f, 650+( playerNamePaint.descent() + 0.75f) , playerNamePaint)
//        canvas.drawText(lines2[1], 780f, 690+( playerNamePaint.descent() + 0.75f) , playerNamePaint)
            val scoreTextOffset=(110-playerNamePaint.measureText(lines1[0]))/2
            canvas.drawText(lines1[0], 585+scoreTextOffset, 658+( playerNamePaint.descent() + 0.75f) , playerNamePaint)
            val bottomMessageOffset=(260-bottomMessagePaint.measureText(lines1[1]))/2
            canvas.drawText(lines1[1], 510+bottomMessageOffset, 695+( bottomMessagePaint.descent() + 0.75f) , bottomMessagePaint)
            val thisOverString=lines2[1].split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            var thisOverWidth=0.0f
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                for(i in thisOverString.indices){
                    var currentWidth = when(thisOverString[i].length){
                        1->playerNamePaint.measureText(thisOverString[i])+8.0f
                        2->playerNamePaint.measureText(thisOverString[i])+6.0f
                        3->playerNamePaint.measureText(thisOverString[i])+4.0f
                        else->playerNamePaint.measureText(thisOverString[i])+2.0f
                    }
                    canvas.drawRoundRect(
                        780+thisOverWidth,
                        (676).toFloat(),
                        780+thisOverWidth+currentWidth,
                        (704).toFloat(),
                        (16).toFloat(),
                        (16).toFloat(),
                        thisOverRectPaint
                    )
                    canvas.drawText(thisOverString[i],780+thisOverWidth+4.5f,690+(thisOverPaint.descent()+0.75f),thisOverPaint)
                    thisOverWidth+=currentWidth+3.0f
                }
            }
        }
        else{
            val scoreTextOffset=(820-playerNamePaint.measureText(lines1[0]))/2
            canvas.drawText(lines1[0], 230+scoreTextOffset, 670+( playerNamePaint.descent() + 0.75f) , playerNamePaint)
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
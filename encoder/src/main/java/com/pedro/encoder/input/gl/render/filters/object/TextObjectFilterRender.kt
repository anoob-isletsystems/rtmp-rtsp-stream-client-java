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
package com.pedro.encoder.input.gl.render.filters.`object`

import android.graphics.Bitmap
import android.graphics.Typeface
import android.opengl.GLES20
import android.os.Build
import androidx.annotation.RequiresApi
import com.pedro.encoder.utils.gl.TextStreamObject

/**
 * Created by pedro on 27/07/18.
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
class TextObjectFilterRender : BaseObjectFilterRender() {
    private var text: String? = null
    private var text1: String? = null
    private var text2: String? = null
    private var textSize = 0f
    private var textColor = 0
    private var typeface: Typeface? = null
    private var bitmap: Bitmap? = null
    private var bitmap1:Bitmap? =null

    init {
        streamObject = TextStreamObject()
    }

    override fun drawFilter() {
        super.drawFilter()
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, streamObjectTextureId[0])
        //Set alpha. 0f if no image loaded.
        GLES20.glUniform1f(uAlphaHandle, if (streamObjectTextureId[0] == -1) 0f else alpha)
    }

    fun setText(text: String?, textSize: Float, textColor: Int) {
        setText(text, textSize, textColor, null)
    }

    fun setText(text: String?, textSize: Float, textColor: Int, typeface: Typeface?) {
        this.text = text
        this.textSize = textSize
        this.textColor = textColor
        this.typeface = typeface
        if (text != null) {
            (streamObject as TextStreamObject).load(text, textSize, textColor, typeface)
        }
        //    ((MultilineTextStreamObject) streamObject).load(text, textSize, textColor, typeface);
        shouldLoad = true
    }

    fun setMultiText(
        text: String?,
        text1: String?,
        text2: String?,
        textSize: Float,
        textColor: Int
    ) {
        setMultiText(text, text1, text2, textSize, textColor, null)
    }

    fun setMultiText(
        text: String?,
        text1: String?,
        text2: String?,
        textSize: Float,
        textColor: Int,
        typeface: Typeface?,
        bitmap: Bitmap,
        bitmap1: Bitmap?
    ) {
        this.text = text
        this.text1 = text1
        this.text2 = text2
        this.textSize = textSize
        this.textColor = textColor
        this.typeface = typeface
        this.bitmap=bitmap
        this.bitmap1= bitmap1
        if (text != null) {
            if (text1 != null) {
                if (text2 != null) {
                    (streamObject as TextStreamObject).load(text, text1, text2, textSize, textColor, typeface,bitmap,bitmap1)
                }
            }
        }
        //    ((MultilineTextStreamObject) streamObject).load(text, textSize, textColor, typeface);
        shouldLoad = true
    }
    fun setMultiText(
        text: String?,
        text1: String?,
        text2: String?,
        textSize: Float,
        textColor: Int,
        typeface: Typeface?
    ) {
        this.text = text
        this.text1 = text1
        this.text2 = text2
        this.textSize = textSize
        this.textColor = textColor
        this.typeface = typeface
        if (text != null) {
            if (text1 != null) {
                if (text2 != null) {
                    (streamObject as TextStreamObject).load(text, text1, text2, textSize, textColor, typeface)
                }
            }
        }
        //    ((MultilineTextStreamObject) streamObject).load(text, textSize, textColor, typeface);
        shouldLoad = true
    }

    fun addText(text: String) {
        setText(this.text + text, textSize, textColor, typeface)
    }

    fun updateColor(textColor: Int) {
        setText(text + text, textSize, textColor, typeface)
    }

    fun updateTypeface(typeface: Typeface?) {
        setText(text + text, textSize, textColor, typeface)
    }

    fun updateTextSize(textSize: Float) {
        setText(text + text, textSize, textColor, typeface)
    }
}
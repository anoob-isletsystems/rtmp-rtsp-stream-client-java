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
package com.pedro.rtpstreamer.openglexample

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Typeface
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.Surface
import android.view.SurfaceHolder
import android.view.View
import android.view.View.OnTouchListener
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.pedro.encoder.input.gl.SpriteGestureController
import com.pedro.encoder.input.gl.render.filters.AnalogTVFilterRender
import com.pedro.encoder.input.gl.render.filters.AndroidViewFilterRender
import com.pedro.encoder.input.gl.render.filters.BasicDeformationFilterRender
import com.pedro.encoder.input.gl.render.filters.BeautyFilterRender
import com.pedro.encoder.input.gl.render.filters.BlackFilterRender
import com.pedro.encoder.input.gl.render.filters.BlurFilterRender
import com.pedro.encoder.input.gl.render.filters.BrightnessFilterRender
import com.pedro.encoder.input.gl.render.filters.CartoonFilterRender
import com.pedro.encoder.input.gl.render.filters.ChromaFilterRender
import com.pedro.encoder.input.gl.render.filters.CircleFilterRender
import com.pedro.encoder.input.gl.render.filters.ColorFilterRender
import com.pedro.encoder.input.gl.render.filters.ContrastFilterRender
import com.pedro.encoder.input.gl.render.filters.DuotoneFilterRender
import com.pedro.encoder.input.gl.render.filters.EarlyBirdFilterRender
import com.pedro.encoder.input.gl.render.filters.EdgeDetectionFilterRender
import com.pedro.encoder.input.gl.render.filters.ExposureFilterRender
import com.pedro.encoder.input.gl.render.filters.FireFilterRender
import com.pedro.encoder.input.gl.render.filters.GammaFilterRender
import com.pedro.encoder.input.gl.render.filters.GlitchFilterRender
import com.pedro.encoder.input.gl.render.filters.GreyScaleFilterRender
import com.pedro.encoder.input.gl.render.filters.HalftoneLinesFilterRender
import com.pedro.encoder.input.gl.render.filters.Image70sFilterRender
import com.pedro.encoder.input.gl.render.filters.LamoishFilterRender
import com.pedro.encoder.input.gl.render.filters.MoneyFilterRender
import com.pedro.encoder.input.gl.render.filters.NegativeFilterRender
import com.pedro.encoder.input.gl.render.filters.NoFilterRender
import com.pedro.encoder.input.gl.render.filters.PixelatedFilterRender
import com.pedro.encoder.input.gl.render.filters.PolygonizationFilterRender
import com.pedro.encoder.input.gl.render.filters.RGBSaturationFilterRender
import com.pedro.encoder.input.gl.render.filters.RainbowFilterRender
import com.pedro.encoder.input.gl.render.filters.RippleFilterRender
import com.pedro.encoder.input.gl.render.filters.RotationFilterRender
import com.pedro.encoder.input.gl.render.filters.SaturationFilterRender
import com.pedro.encoder.input.gl.render.filters.SepiaFilterRender
import com.pedro.encoder.input.gl.render.filters.SharpnessFilterRender
import com.pedro.encoder.input.gl.render.filters.SnowFilterRender
import com.pedro.encoder.input.gl.render.filters.SwirlFilterRender
import com.pedro.encoder.input.gl.render.filters.TemperatureFilterRender
import com.pedro.encoder.input.gl.render.filters.ZebraFilterRender
import com.pedro.encoder.input.gl.render.filters.`object`.GifObjectFilterRender
import com.pedro.encoder.input.gl.render.filters.`object`.ImageObjectFilterRender
import com.pedro.encoder.input.gl.render.filters.`object`.SurfaceFilterRender
import com.pedro.encoder.input.gl.render.filters.`object`.TextObjectFilterRender
import com.pedro.encoder.input.video.CameraOpenException
import com.pedro.encoder.utils.gl.TranslateTo
import com.pedro.rtmp.utils.ConnectCheckerRtmp
import com.pedro.rtplibrary.rtmp.RtmpCamera1
import com.pedro.rtplibrary.view.OpenGlView
import com.pedro.rtpstreamer.R
import com.pedro.rtpstreamer.utils.PathUtils
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


/**
 * More documentation see:
 * [com.pedro.rtplibrary.base.Camera1Base]
 * [com.pedro.rtplibrary.rtmp.RtmpCamera1]
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
class OpenGlRtmpActivity : AppCompatActivity(), ConnectCheckerRtmp, View.OnClickListener,
    SurfaceHolder.Callback, OnTouchListener {
    private var rtmpCamera1: RtmpCamera1? = null
    private var button: Button? = null
    private var bRecord: Button? = null
    private var etUrl: EditText? = null
    private var currentDateAndTime = ""
    private var folder: File? = null
    private var openGlView: OpenGlView? = null
    private val spriteGestureController = SpriteGestureController()
    private var over = "39.3"
    private var score = "225/4"
    private var batterOne = "57*"
    private var batterTwo = "22"
    private var bowlerOne = "5.3-0-23-1"
    private var bowlerTwo = "6-0-25-0"
    private val path =
        arrayOf("over", "score", "Batsman one", "Batsman two", "Bowler one", "Bowler two")
    private val displayMetrics = DisplayMetrics()
    var width = displayMetrics.widthPixels
    var height = displayMetrics.heightPixels
    var density= displayMetrics.density
    var readRef: DatabaseReference? = null
    var listener: ValueEventListener? = null
    var readRef1: DatabaseReference? = null
    var listener1: ValueEventListener? = null
    var readRef2: DatabaseReference? = null
    var listener2: ValueEventListener? = null
    var readRef3: DatabaseReference? = null
    var listener3: ValueEventListener? = null
    var readRef4: DatabaseReference? = null
    var listener4: ValueEventListener? = null
    var readRef5: DatabaseReference? = null
    var listener5: ValueEventListener? = null
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setContentView(R.layout.activity_open_gl)
        folder = PathUtils.getRecordPath()
        openGlView = findViewById(R.id.surfaceView)
        button = findViewById(R.id.b_start_stop)
        button?.setOnClickListener(this)
        bRecord = findViewById(R.id.b_record)
        bRecord?.setOnClickListener(this)
        val switchCamera = findViewById<Button>(R.id.switch_camera)
        switchCamera.setOnClickListener(this)
        etUrl = findViewById(R.id.et_rtp_url)
        //etUrl.setHint(R.string.hint_rtmp);
        etUrl?.setText(
            "rtmp://global-live.mux.com:5222/app/aab614c8-4095-1bdc-2a70-d92d9203e263",
            TextView.BufferType.EDITABLE
        )
        rtmpCamera1 = RtmpCamera1(openGlView, this)
        openGlView?.getHolder()?.addCallback(this)
        openGlView?.setOnTouchListener(this)
        Readover()
        Readscore()
        Readbatter1()
        Readbatter2()
        Readbowler1()
        Readbowler2()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        width = displayMetrics.widthPixels
        height = displayMetrics.heightPixels
        density= displayMetrics.density
        val xdpi = displayMetrics.xdpi
        val ydpi = displayMetrics.ydpi
    }
    override fun onStop() {
        super.onStop()
        if (readRef != null && listener != null) {
            readRef!!.removeEventListener(listener!!)
        }
        if (readRef1 != null && listener1 != null) {
            readRef1!!.removeEventListener(listener1!!)
        }
        if (readRef2 != null && listener2 != null) {
            readRef2!!.removeEventListener(listener2!!)
        }
        if (readRef3 != null && listener3 != null) {
            readRef3!!.removeEventListener(listener3!!)
        }
        if (readRef4 != null && listener4 != null) {
            readRef4!!.removeEventListener(listener4!!)
        }
        if (readRef5 != null && listener5 != null) {
            readRef5!!.removeEventListener(listener5!!)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.gl_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //Stop listener for image, text and gif stream objects.
        spriteGestureController.stopListener()
        return when (item.itemId) {
            R.id.e_d_fxaa -> {
                rtmpCamera1!!.glInterface.enableAA(!rtmpCamera1!!.glInterface.isAAEnabled)
                Toast.makeText(
                    this,
                    "FXAA " + if (rtmpCamera1!!.glInterface.isAAEnabled) "enabled" else "disabled",
                    Toast.LENGTH_SHORT
                ).show()
                true
            }

            R.id.no_filter -> {
                rtmpCamera1!!.glInterface.setFilter(NoFilterRender())
                true
            }

            R.id.analog_tv -> {
                rtmpCamera1!!.glInterface.setFilter(AnalogTVFilterRender())
                true
            }

            R.id.android_view -> {
                val androidViewFilterRender = AndroidViewFilterRender()
                androidViewFilterRender.view = findViewById(R.id.switch_camera)
                rtmpCamera1!!.glInterface.setFilter(androidViewFilterRender)
                true
            }

            R.id.basic_deformation -> {
                rtmpCamera1!!.glInterface.setFilter(BasicDeformationFilterRender())
                true
            }

            R.id.beauty -> {
                rtmpCamera1!!.glInterface.setFilter(BeautyFilterRender())
                true
            }

            R.id.black -> {
                rtmpCamera1!!.glInterface.setFilter(BlackFilterRender())
                true
            }

            R.id.blur -> {
                rtmpCamera1!!.glInterface.setFilter(BlurFilterRender())
                true
            }

            R.id.brightness -> {
                rtmpCamera1!!.glInterface.setFilter(BrightnessFilterRender())
                true
            }

            R.id.cartoon -> {
                rtmpCamera1!!.glInterface.setFilter(CartoonFilterRender())
                true
            }

            R.id.chroma -> {
                val chromaFilterRender = ChromaFilterRender()
                rtmpCamera1!!.glInterface.setFilter(chromaFilterRender)
                chromaFilterRender.setImage(
                    BitmapFactory.decodeResource(
                        resources,
                        R.drawable.bg_chroma
                    )
                )
                true
            }

            R.id.circle -> {
                rtmpCamera1!!.glInterface.setFilter(CircleFilterRender())
                true
            }

            R.id.color -> {
                rtmpCamera1!!.glInterface.setFilter(ColorFilterRender())
                true
            }

            R.id.contrast -> {
                rtmpCamera1!!.glInterface.setFilter(ContrastFilterRender())
                true
            }

            R.id.duotone -> {
                rtmpCamera1!!.glInterface.setFilter(DuotoneFilterRender())
                true
            }

            R.id.early_bird -> {
                rtmpCamera1!!.glInterface.setFilter(EarlyBirdFilterRender())
                true
            }

            R.id.edge_detection -> {
                rtmpCamera1!!.glInterface.setFilter(EdgeDetectionFilterRender())
                true
            }

            R.id.exposure -> {
                rtmpCamera1!!.glInterface.setFilter(ExposureFilterRender())
                true
            }

            R.id.fire -> {
                rtmpCamera1!!.glInterface.setFilter(FireFilterRender())
                true
            }

            R.id.gamma -> {
                rtmpCamera1!!.glInterface.setFilter(GammaFilterRender())
                true
            }

            R.id.glitch -> {
                rtmpCamera1!!.glInterface.setFilter(GlitchFilterRender())
                true
            }

            R.id.gif -> {
                setGifToStream()
                true
            }

            R.id.grey_scale -> {
                rtmpCamera1!!.glInterface.setFilter(GreyScaleFilterRender())
                true
            }

            R.id.halftone_lines -> {
                rtmpCamera1!!.glInterface.setFilter(HalftoneLinesFilterRender())
                true
            }

            R.id.image -> {
                setImageToStream()
                true
            }

            R.id.image_70s -> {
                rtmpCamera1!!.glInterface.setFilter(Image70sFilterRender())
                true
            }

            R.id.lamoish -> {
                rtmpCamera1!!.glInterface.setFilter(LamoishFilterRender())
                true
            }

            R.id.money -> {
                rtmpCamera1!!.glInterface.setFilter(MoneyFilterRender())
                true
            }

            R.id.negative -> {
                rtmpCamera1!!.glInterface.setFilter(NegativeFilterRender())
                true
            }

            R.id.pixelated -> {
                rtmpCamera1!!.glInterface.setFilter(PixelatedFilterRender())
                true
            }

            R.id.polygonization -> {
                rtmpCamera1!!.glInterface.setFilter(PolygonizationFilterRender())
                true
            }

            R.id.rainbow -> {
                rtmpCamera1!!.glInterface.setFilter(RainbowFilterRender())
                true
            }

            R.id.rgb_saturate -> {
                val rgbSaturationFilterRender = RGBSaturationFilterRender()
                rtmpCamera1!!.glInterface.setFilter(rgbSaturationFilterRender)
                //Reduce green and blue colors 20%. Red will predominate.
                rgbSaturationFilterRender.setRGBSaturation(1f, 0.8f, 0.8f)
                true
            }

            R.id.ripple -> {
                rtmpCamera1!!.glInterface.setFilter(RippleFilterRender())
                true
            }

            R.id.rotation -> {
                val rotationFilterRender = RotationFilterRender()
                rtmpCamera1!!.glInterface.setFilter(rotationFilterRender)
                rotationFilterRender.rotation = 90
                true
            }

            R.id.saturation -> {
                rtmpCamera1!!.glInterface.setFilter(SaturationFilterRender())
                true
            }

            R.id.sepia -> {
                rtmpCamera1!!.glInterface.setFilter(SepiaFilterRender())
                true
            }

            R.id.sharpness -> {
                rtmpCamera1!!.glInterface.setFilter(SharpnessFilterRender())
                true
            }

            R.id.snow -> {
                rtmpCamera1!!.glInterface.setFilter(SnowFilterRender())
                true
            }

            R.id.swirl -> {
                rtmpCamera1!!.glInterface.setFilter(SwirlFilterRender())
                true
            }

            R.id.surface_filter -> {
                val surfaceFilterRender = SurfaceFilterRender { surfaceTexture -> //You can render this filter with other api that draw in a surface. for example you can use VLC
                    val mediaPlayer: MediaPlayer =
                        MediaPlayer.create(this@OpenGlRtmpActivity, R.raw.big_bunny_240p)
                    mediaPlayer.setSurface(Surface(surfaceTexture))
                    mediaPlayer.start()
                }
                rtmpCamera1!!.glInterface.setFilter(surfaceFilterRender)
                //Video is 360x240 so select a percent to keep aspect ratio (50% x 33.3% screen)
                surfaceFilterRender.setScale(50f, 33.3f)
                spriteGestureController.setBaseObjectFilterRender(surfaceFilterRender) //Optional
                true
            }

            R.id.temperature -> {
                rtmpCamera1!!.glInterface.setFilter(TemperatureFilterRender())
                true
            }

            R.id.text -> {
                setTextToStream()
                true
            }

            R.id.zebra -> {
                rtmpCamera1!!.glInterface.setFilter(ZebraFilterRender())
                true
            }
            R.id.mux -> {
                etUrl?.setText("rtmp://global-live.mux.com:5222/app/975d242b-940a-ef05-7f3e-b3685ad9046a",
                    TextView.BufferType.EDITABLE)
                true
            }
            R.id.youtube -> {
                etUrl?.setText("rtmp://a.rtmp.youtube.com/live2/pc65-aev0-vy3t-r5z9-egqy" ,TextView.BufferType.EDITABLE)
                true
            }
            R.id.mux_secured -> {
                etUrl?.setText("rtmps://global-live.mux.com:443/app/", TextView.BufferType.EDITABLE)
                true
            }
            else -> false
        }
    }

    private fun setTextToStream() {
        val textObjectFilterRender = TextObjectFilterRender()
        rtmpCamera1!!.glInterface.setFilter(textObjectFilterRender)
        val newline = "\n"
        //    String text="Score \nDetails";
//    String text1="Batsman One\nBatsman Two";
//    String text2="Bowler One\nBowler Two";
        val text = score + newline + over
        val text1 = batterOne + newline + batterTwo
        val text2 = bowlerOne + newline + bowlerTwo
        val bitmap=BitmapFactory.decodeResource(resources,R.drawable.scoreblox_nobg100)
        val bitmapResized=Bitmap.createScaledBitmap(bitmap,
            (bitmap.width/density).toInt(), (bitmap.height/density).toInt(),false)
        //textObjectFilterRender.setText("Hello Testing\nworld", 30, Color.WHITE, Typeface.DEFAULT_BOLD);
        textObjectFilterRender.setMultiText(
            text,
            text1,
            text2,
            30f,
            Color.WHITE,
            Typeface.DEFAULT_BOLD,
            bitmapResized
        )
        //    textObjectFilterRender.addText("testing");
        textObjectFilterRender.setDefaultScale(
            rtmpCamera1!!.streamWidth,
            rtmpCamera1!!.streamHeight
        )

        //textObjectFilterRender.setPosition(TranslateTo.CENTER);
        textObjectFilterRender.setPosition(0f, 0f)
        spriteGestureController.setBaseObjectFilterRender(textObjectFilterRender) //Optional
        //setImageToStream();
    }

    fun Readover() {
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        readRef = database.getReference(path[0])
        listener= readRef!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value: String? = dataSnapshot.getValue<String>(String::class.java)
                Log.d(TAG, "Value is: $value")
                if (value != null) {
                    over = value
                }
                setTextToStream()
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
//        if (myRef != null && listener != null) {
//            myRef.removeEventListener(listener)
//        }
    }

    fun Readscore() {
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        readRef1 = database.getReference(path[1])
        listener1= readRef1!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value: String? = dataSnapshot.getValue<String>(String::class.java)
                Log.d(TAG, "Value is: $value")
                if (value != null) {
                    score = value
                }
                setTextToStream()
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
//        if (myRef != null && listener != null) {
//            myRef.removeEventListener(listener)
//        }
    }

    fun Readbatter1() {
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        readRef2= database.getReference(path[2])
        listener2= readRef2!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value: String? = dataSnapshot.getValue<String>(String::class.java)
                Log.d(TAG, "Value is: $value")
                if (value != null) {
                    batterOne = value
                }
                setTextToStream()
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
//        if (myRef != null && listener != null) {
//            myRef.removeEventListener(listener)
//        }
    }

    fun Readbatter2() {
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        readRef3 = database.getReference(path[3])
        listener3= readRef3!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value: String? = dataSnapshot.getValue<String>(String::class.java)
                Log.d(TAG, "Value is: $value")
                if (value != null) {
                    batterTwo = value
                }
                setTextToStream()
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
//        if (myRef != null && listener != null) {
//            myRef.removeEventListener(listener)
//        }
    }

    fun Readbowler1() {
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        readRef4 = database.getReference(path[4])
        listener4= readRef4!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value: String? = dataSnapshot.getValue<String>(String::class.java)
                Log.d(TAG, "Value is: $value")
                if (value != null) {
                    bowlerOne = value
                }
                setTextToStream()
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
//        if (myRef != null && listener != null) {
//            myRef.removeEventListener(listener)
//        }
    }

    fun Readbowler2() {
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        readRef5 = database.getReference(path[5])
        listener5= readRef5!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value: String? = dataSnapshot.getValue<String>(String::class.java)
                Log.d(TAG, "Value is: $value")
                if (value != null) {
                    bowlerTwo = value
                }
                setTextToStream()
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
//        if (myRef != null && listener != null) {
//            myRef.removeEventListener(listener)
//        }
    }

    private fun setImageToStream() {
        val imageObjectFilterRender = ImageObjectFilterRender()
        rtmpCamera1!!.glInterface.setFilter(imageObjectFilterRender)
        imageObjectFilterRender.setImage(
            BitmapFactory.decodeResource(resources, R.drawable.notification_icon)
        ) //R.mipmap.ic_launcher
        imageObjectFilterRender.setDefaultScale(
            rtmpCamera1!!.streamWidth,
            rtmpCamera1!!.streamHeight
        )
        imageObjectFilterRender.setPosition(TranslateTo.RIGHT)
        spriteGestureController.setBaseObjectFilterRender(imageObjectFilterRender) //Optional
        spriteGestureController.setPreventMoveOutside(false) //Optional
    }

    private fun setGifToStream() {
        try {
            val gifObjectFilterRender = GifObjectFilterRender()
            gifObjectFilterRender.setGif(resources.openRawResource(R.raw.banana))
            rtmpCamera1!!.glInterface.setFilter(gifObjectFilterRender)
            gifObjectFilterRender.setDefaultScale(
                rtmpCamera1!!.streamWidth,
                rtmpCamera1!!.streamHeight
            )
            gifObjectFilterRender.setPosition(TranslateTo.BOTTOM)
            spriteGestureController.setBaseObjectFilterRender(gifObjectFilterRender) //Optional
        } catch (e: IOException) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onConnectionStartedRtmp(rtmpUrl: String) {}
    override fun onConnectionSuccessRtmp() {
        runOnUiThread {
            Toast.makeText(
                this@OpenGlRtmpActivity,
                "Connection success",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onConnectionFailedRtmp(reason: String) {
        runOnUiThread {
            Toast.makeText(
                this@OpenGlRtmpActivity,
                "Connection failed. $reason",
                Toast.LENGTH_SHORT
            )
                .show()
            rtmpCamera1!!.stopStream()
            button!!.setText(R.string.start_button)
        }
    }

    override fun onNewBitrateRtmp(bitrate: Long) {}
    override fun onDisconnectRtmp() {
        runOnUiThread {
            Toast.makeText(this@OpenGlRtmpActivity, "Disconnected", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onAuthErrorRtmp() {
        runOnUiThread {
            Toast.makeText(this@OpenGlRtmpActivity, "Auth error", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onAuthSuccessRtmp() {
        runOnUiThread {
            Toast.makeText(this@OpenGlRtmpActivity, "Auth success", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.b_start_stop -> if (!rtmpCamera1!!.isStreaming) {
                if (rtmpCamera1!!.isRecording
                    || rtmpCamera1!!.prepareAudio() && rtmpCamera1!!.prepareVideo()
                ) {
                    button!!.setText(R.string.stop_button)
                    rtmpCamera1!!.startStream(etUrl!!.text.toString())
                } else {
                    Toast.makeText(
                        this, "Error preparing stream, This device cant do it",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                button!!.setText(R.string.start_button)
                rtmpCamera1!!.stopStream()
            }

            R.id.switch_camera -> try {
                rtmpCamera1!!.switchCamera()
            } catch (e: CameraOpenException) {
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }

            R.id.b_record -> if (!rtmpCamera1!!.isRecording) {
                try {
                    if (!folder!!.exists()) {
                        folder!!.mkdir()
                    }
                    val sdf = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
                    currentDateAndTime = sdf.format(Date())
                    if (!rtmpCamera1!!.isStreaming) {
                        if (rtmpCamera1!!.prepareAudio() && rtmpCamera1!!.prepareVideo()) {
                            rtmpCamera1!!.startRecord(
                                folder!!.absolutePath + "/" + currentDateAndTime + ".mp4"
                            )
                            bRecord!!.setText(R.string.stop_record)
                            Toast.makeText(this, "Recording... ", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(
                                this, "Error preparing stream, This device cant do it",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        rtmpCamera1!!.startRecord(folder!!.absolutePath + "/" + currentDateAndTime + ".mp4")
                        bRecord!!.setText(R.string.stop_record)
                        Toast.makeText(this, "Recording... ", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: IOException) {
                    rtmpCamera1!!.stopRecord()
                    PathUtils.updateGallery(
                        this,
                        folder!!.absolutePath + "/" + currentDateAndTime + ".mp4"
                    )
                    bRecord!!.setText(R.string.start_record)
                    Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                }
            } else {
                rtmpCamera1!!.stopRecord()
                PathUtils.updateGallery(
                    this,
                    folder!!.absolutePath + "/" + currentDateAndTime + ".mp4"
                )
                bRecord!!.setText(R.string.start_record)
                Toast.makeText(
                    this,
                    "file " + currentDateAndTime + ".mp4 saved in " + folder!!.absolutePath,
                    Toast.LENGTH_SHORT
                ).show()
                currentDateAndTime = ""
            }

            else -> {}
        }
    }

    override fun surfaceCreated(surfaceHolder: SurfaceHolder) {}
    override fun surfaceChanged(surfaceHolder: SurfaceHolder, i: Int, i1: Int, i2: Int) {
        rtmpCamera1!!.startPreview()
    }

    override fun surfaceDestroyed(surfaceHolder: SurfaceHolder) {
        if (rtmpCamera1!!.isRecording) {
            rtmpCamera1!!.stopRecord()
            PathUtils.updateGallery(this, folder!!.absolutePath + "/" + currentDateAndTime + ".mp4")
            bRecord!!.setText(R.string.start_record)
            Toast.makeText(
                this,
                "file " + currentDateAndTime + ".mp4 saved in " + folder!!.absolutePath,
                Toast.LENGTH_SHORT
            ).show()
            currentDateAndTime = ""
        }
        if (rtmpCamera1!!.isStreaming) {
            rtmpCamera1!!.stopStream()
            button!!.text = resources.getString(R.string.start_button)
        }
        rtmpCamera1!!.stopPreview()
    }

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        if (spriteGestureController.spriteTouched(view, motionEvent)) {
            spriteGestureController.moveSprite(view, motionEvent)
            spriteGestureController.scaleSprite(motionEvent)
            return true
        }
        return false
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
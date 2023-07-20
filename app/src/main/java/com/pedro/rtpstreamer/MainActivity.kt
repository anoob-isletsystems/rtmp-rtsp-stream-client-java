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
package com.pedro.rtpstreamer

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.GridView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.pedro.rtpstreamer.backgroundexample.BackgroundActivity
import com.pedro.rtpstreamer.customexample.RtmpActivity
import com.pedro.rtpstreamer.customexample.RtspActivity
import com.pedro.rtpstreamer.defaultexample.ExampleRtmpActivity
import com.pedro.rtpstreamer.defaultexample.ExampleRtspActivity
import com.pedro.rtpstreamer.displayexample.DisplayActivity
import com.pedro.rtpstreamer.filestreamexample.RtmpFromFileActivity
import com.pedro.rtpstreamer.filestreamexample.RtspFromFileActivity
import com.pedro.rtpstreamer.openglexample.OpenGlRtmpActivity
import com.pedro.rtpstreamer.openglexample.OpenGlRtspActivity
import com.pedro.rtpstreamer.rotation.RotationExampleActivity
import com.pedro.rtpstreamer.surfacemodeexample.SurfaceModeRtmpActivity
import com.pedro.rtpstreamer.surfacemodeexample.SurfaceModeRtspActivity
import com.pedro.rtpstreamer.texturemodeexample.TextureModeRtmpActivity
import com.pedro.rtpstreamer.texturemodeexample.TextureModeRtspActivity
import com.pedro.rtpstreamer.utils.ActivityLink
import com.pedro.rtpstreamer.utils.ImageAdapter

class MainActivity : AppCompatActivity(), OnItemClickListener {
    private var list: GridView? = null
    private var activities: MutableList<ActivityLink>? = null
    private val PERMISSIONS = arrayOf(
        Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    @RequiresApi(api = VERSION_CODES.TIRAMISU)
    private val PERMISSIONS_A_13 = arrayOf(
        Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA,
        Manifest.permission.POST_NOTIFICATIONS
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        overridePendingTransition(R.transition.slide_in, R.transition.slide_out)
        val tvVersion = findViewById<TextView>(R.id.tv_version)
        tvVersion.text = getString(R.string.version, BuildConfig.VERSION_NAME)
        list = findViewById(R.id.list)
        createList()
        setListAdapter(activities)
        requestPermissions()
    }

    private fun requestPermissions() {
        if (Build.VERSION.SDK_INT >= VERSION_CODES.TIRAMISU) {
            if (!hasPermissions(this)) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_A_13, 1)
            }
        } else {
            if (!hasPermissions(this)) {
                ActivityCompat.requestPermissions(this, PERMISSIONS, 1)
            }
        }
    }

    @SuppressLint("NewApi")
    private fun createList() {
        activities = ArrayList()
        (activities as ArrayList<ActivityLink>).add(
            ActivityLink(
                Intent(this, RtmpActivity::class.java),
                getString(R.string.rtmp_streamer), VERSION_CODES.JELLY_BEAN
            )
        )
        (activities as ArrayList<ActivityLink>).add(
            ActivityLink(
                Intent(this, RtspActivity::class.java),
                getString(R.string.rtsp_streamer), VERSION_CODES.JELLY_BEAN
            )
        )
        (activities as ArrayList<ActivityLink>).add(
            ActivityLink(
                Intent(this, ExampleRtmpActivity::class.java),
                getString(R.string.default_rtmp), VERSION_CODES.JELLY_BEAN
            )
        )
        (activities as ArrayList<ActivityLink>).add(
            ActivityLink(
                Intent(this, ExampleRtspActivity::class.java),
                getString(R.string.default_rtsp), VERSION_CODES.JELLY_BEAN
            )
        )
        (activities as ArrayList<ActivityLink>).add(
            ActivityLink(
                Intent(this, RtmpFromFileActivity::class.java),
                getString(R.string.from_file_rtmp), VERSION_CODES.JELLY_BEAN_MR2
            )
        )
        (activities as ArrayList<ActivityLink>).add(
            ActivityLink(
                Intent(this, RtspFromFileActivity::class.java),
                getString(R.string.from_file_rtsp), VERSION_CODES.JELLY_BEAN_MR2
            )
        )
        (activities as ArrayList<ActivityLink>).add(
            ActivityLink(
                Intent(this, SurfaceModeRtmpActivity::class.java),
                getString(R.string.surface_mode_rtmp), VERSION_CODES.LOLLIPOP
            )
        )
        (activities as ArrayList<ActivityLink>).add(
            ActivityLink(
                Intent(this, SurfaceModeRtspActivity::class.java),
                getString(R.string.surface_mode_rtsp), VERSION_CODES.LOLLIPOP
            )
        )
        (activities as ArrayList<ActivityLink>).add(
            ActivityLink(
                Intent(this, TextureModeRtmpActivity::class.java),
                getString(R.string.texture_mode_rtmp), VERSION_CODES.LOLLIPOP
            )
        )
        (activities as ArrayList<ActivityLink>).add(
            ActivityLink(
                Intent(this, TextureModeRtspActivity::class.java),
                getString(R.string.texture_mode_rtsp), VERSION_CODES.LOLLIPOP
            )
        )
        (activities as ArrayList<ActivityLink>).add(
            ActivityLink(
                Intent(this, OpenGlRtmpActivity::class.java),
                getString(R.string.opengl_rtmp), VERSION_CODES.JELLY_BEAN_MR2
            )
        )
        (activities as ArrayList<ActivityLink>).add(
            ActivityLink(
                Intent(this, OpenGlRtspActivity::class.java),
                getString(R.string.opengl_rtsp), VERSION_CODES.JELLY_BEAN_MR2
            )
        )
        (activities as ArrayList<ActivityLink>).add(
            ActivityLink(
                Intent(this, DisplayActivity::class.java),
                getString(R.string.display_rtmp), VERSION_CODES.LOLLIPOP
            )
        )
        (activities as ArrayList<ActivityLink>).add(
            ActivityLink(
                Intent(this, BackgroundActivity::class.java),
                getString(R.string.service_rtp), VERSION_CODES.LOLLIPOP
            )
        )
        (activities as ArrayList<ActivityLink>).add(
            ActivityLink(
                Intent(this, RotationExampleActivity::class.java),
                getString(R.string.rotation_rtmp), VERSION_CODES.LOLLIPOP
            )
        )
    }

    private fun setListAdapter(activities: List<ActivityLink>?) {
        list!!.adapter = ImageAdapter(activities)
        list!!.onItemClickListener = this
    }

    override fun onItemClick(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
        if (hasPermissions(this)) {
            val link = activities!![i]
            val minSdk = link.minSdk
            if (Build.VERSION.SDK_INT >= minSdk) {
                startActivity(link.intent)
                overridePendingTransition(R.transition.slide_in, R.transition.slide_out)
            } else {
                showMinSdkError(minSdk)
            }
        } else {
            showPermissionsErrorAndRequest()
        }
    }

    private fun showMinSdkError(minSdk: Int) {
        val named: String
        named = when (minSdk) {
            VERSION_CODES.JELLY_BEAN_MR2 -> "JELLY_BEAN_MR2"
            VERSION_CODES.LOLLIPOP -> "LOLLIPOP"
            else -> "JELLY_BEAN"
        }
        Toast.makeText(
            this, "You need min Android $named (API $minSdk )",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showPermissionsErrorAndRequest() {
        Toast.makeText(this, "You need permissions before", Toast.LENGTH_SHORT).show()
        requestPermissions()
    }

    private fun hasPermissions(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= VERSION_CODES.TIRAMISU) {
            hasPermissions(context, *PERMISSIONS_A_13)
        } else {
            hasPermissions(context, *PERMISSIONS)
        }
    }

    private fun hasPermissions(context: Context?, vararg permissions: String): Boolean {
        if (Build.VERSION.SDK_INT >= VERSION_CODES.M && context != null && permissions != null) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission)
                    != PackageManager.PERMISSION_GRANTED
                ) {
                    return false
                }
            }
        }
        return true
    }
}
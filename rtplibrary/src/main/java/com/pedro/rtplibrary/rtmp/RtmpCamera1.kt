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
package com.pedro.rtplibrary.rtmp

import android.content.Context
import android.media.MediaCodec
import android.os.Build
import android.view.SurfaceView
import android.view.TextureView
import androidx.annotation.RequiresApi
import com.pedro.rtmp.flv.video.ProfileIop
import com.pedro.rtmp.rtmp.RtmpClient
import com.pedro.rtmp.utils.ConnectCheckerRtmp
import com.pedro.rtplibrary.base.Camera1Base
import com.pedro.rtplibrary.view.LightOpenGlView
import com.pedro.rtplibrary.view.OpenGlView
import java.nio.ByteBuffer

/**
 * More documentation see:
 * [com.pedro.rtplibrary.base.Camera1Base]
 *
 * Created by pedro on 25/01/17.
 */
class RtmpCamera1 : Camera1Base {
    private val rtmpClient: RtmpClient

    constructor(
        surfaceView: SurfaceView?,
        connectChecker: ConnectCheckerRtmp?
    ) : super(surfaceView) {
        rtmpClient = RtmpClient(connectChecker!!)
    }

    constructor(
        textureView: TextureView?,
        connectChecker: ConnectCheckerRtmp?
    ) : super(textureView) {
        rtmpClient = RtmpClient(connectChecker!!)
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    constructor(openGlView: OpenGlView?, connectChecker: ConnectCheckerRtmp?) : super(openGlView) {
        rtmpClient = RtmpClient(connectChecker!!)
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    constructor(lightOpenGlView: LightOpenGlView?, connectChecker: ConnectCheckerRtmp?) : super(
        lightOpenGlView
    ) {
        rtmpClient = RtmpClient(connectChecker!!)
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    constructor(context: Context?, connectChecker: ConnectCheckerRtmp?) : super(context) {
        rtmpClient = RtmpClient(connectChecker!!)
    }

    /**
     * H264 profile.
     *
     * @param profileIop Could be ProfileIop.BASELINE or ProfileIop.CONSTRAINED
     */
    fun setProfileIop(profileIop: ProfileIop?) {
        rtmpClient.setProfileIop(profileIop!!)
    }

    @Throws(RuntimeException::class)
    override fun resizeCache(newSize: Int) {
        rtmpClient.resizeCache(newSize)
    }

    override fun getCacheSize(): Int {
        return rtmpClient.cacheSize
    }

    override fun getSentAudioFrames(): Long {
        return rtmpClient.sentAudioFrames
    }

    override fun getSentVideoFrames(): Long {
        return rtmpClient.sentVideoFrames
    }

    override fun getDroppedAudioFrames(): Long {
        return rtmpClient.droppedAudioFrames
    }

    override fun getDroppedVideoFrames(): Long {
        return rtmpClient.droppedVideoFrames
    }

    override fun resetSentAudioFrames() {
        rtmpClient.resetSentAudioFrames()
    }

    override fun resetSentVideoFrames() {
        rtmpClient.resetSentVideoFrames()
    }

    override fun resetDroppedAudioFrames() {
        rtmpClient.resetDroppedAudioFrames()
    }

    override fun resetDroppedVideoFrames() {
        rtmpClient.resetDroppedVideoFrames()
    }

    override fun setAuthorization(user: String, password: String) {
        rtmpClient.setAuthorization(user, password)
    }

    /**
     * Some Livestream hosts use Akamai auth that requires RTMP packets to be sent with increasing
     * timestamp order regardless of packet type.
     * Necessary with Servers like Dacast.
     * More info here:
     * https://learn.akamai.com/en-us/webhelp/media-services-live/media-services-live-encoder-compatibility-testing-and-qualification-guide-v4.0/GUID-F941C88B-9128-4BF4-A81B-C2E5CFD35BBF.html
     */
    fun forceAkamaiTs(enabled: Boolean) {
        rtmpClient.forceAkamaiTs(enabled)
    }

    /**
     * Must be called before start stream.
     *
     * Default value 128
     * Range value: 1 to 16777215.
     *
     * The most common values example: 128, 4096, 65535
     *
     * @param chunkSize packet's chunk size send to server
     */
    fun setWriteChunkSize(chunkSize: Int) {
        if (!isStreaming) {
            rtmpClient.setWriteChunkSize(chunkSize)
        }
    }

    override fun prepareAudioRtp(isStereo: Boolean, sampleRate: Int) {
        rtmpClient.setAudioInfo(sampleRate, isStereo)
    }

    override fun startStreamRtp(url: String) {
        if (videoEncoder.rotation == 90 || videoEncoder.rotation == 270) {
            rtmpClient.setVideoResolution(videoEncoder.height, videoEncoder.width)
        } else {
            rtmpClient.setVideoResolution(videoEncoder.width, videoEncoder.height)
        }
        rtmpClient.setFps(videoEncoder.fps)
        rtmpClient.setOnlyVideo(!audioInitialized)
        rtmpClient.connect(url)
    }

    override fun stopStreamRtp() {
        rtmpClient.disconnect()
    }

    override fun setReTries(reTries: Int) {
        rtmpClient.setReTries(reTries)
    }

    override fun shouldRetry(reason: String): Boolean {
        return rtmpClient.shouldRetry(reason)
    }

    public override fun reConnect(delay: Long, backupUrl: String?) {
        rtmpClient.reConnect(delay, backupUrl)
    }

    override fun hasCongestion(): Boolean {
        return rtmpClient.hasCongestion()
    }

    override fun getAacDataRtp(aacBuffer: ByteBuffer, info: MediaCodec.BufferInfo) {
        rtmpClient.sendAudio(aacBuffer, info)
    }

    override fun onSpsPpsVpsRtp(sps: ByteBuffer, pps: ByteBuffer, vps: ByteBuffer?) {
        rtmpClient.setVideoInfo(sps, pps, vps)
    }

    override fun getH264DataRtp(h264Buffer: ByteBuffer, info: MediaCodec.BufferInfo) {
        rtmpClient.sendVideo(h264Buffer, info)
    }

    override fun setLogs(enable: Boolean) {
        rtmpClient.setLogs(enable)
    }

    override fun setCheckServerAlive(enable: Boolean) {
        rtmpClient.setCheckServerAlive(enable)
    }
}
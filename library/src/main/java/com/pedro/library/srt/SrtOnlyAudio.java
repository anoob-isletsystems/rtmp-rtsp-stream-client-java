/*
 * Copyright (C) 2023 pedroSG94.
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

package com.pedro.library.srt;

import android.media.MediaCodec;

import androidx.annotation.Nullable;

import com.pedro.library.base.OnlyAudioBase;
import com.pedro.srt.srt.SrtClient;
import com.pedro.srt.utils.ConnectCheckerSrt;

import java.nio.ByteBuffer;

/**
 * More documentation see:
 * {@link OnlyAudioBase}
 *
 * Created by pedro on 8/9/23.
 */
public class SrtOnlyAudio extends OnlyAudioBase {

  private final SrtClient rtmpClient;

  public SrtOnlyAudio(ConnectCheckerSrt connectChecker) {
    super();
    rtmpClient = new SrtClient(connectChecker);
    rtmpClient.setOnlyAudio(true);
  }

  @Override
  public void resizeCache(int newSize) throws RuntimeException {
    rtmpClient.resizeCache(newSize);
  }

  @Override
  public int getCacheSize() {
    return rtmpClient.getCacheSize();
  }

  @Override
  public long getSentAudioFrames() {
    return rtmpClient.getSentAudioFrames();
  }

  @Override
  public long getSentVideoFrames() {
    return rtmpClient.getSentVideoFrames();
  }

  @Override
  public long getDroppedAudioFrames() {
    return rtmpClient.getDroppedAudioFrames();
  }

  @Override
  public long getDroppedVideoFrames() {
    return rtmpClient.getDroppedVideoFrames();
  }

  @Override
  public void resetSentAudioFrames() {
    rtmpClient.resetSentAudioFrames();
  }

  @Override
  public void resetSentVideoFrames() {
    rtmpClient.resetSentVideoFrames();
  }

  @Override
  public void resetDroppedAudioFrames() {
    rtmpClient.resetDroppedAudioFrames();
  }

  @Override
  public void resetDroppedVideoFrames() {
    rtmpClient.resetDroppedVideoFrames();
  }

  @Override
  public void setAuthorization(String user, String password) {
    rtmpClient.setAuthorization(user, password);
  }

  @Override
  protected void prepareAudioRtp(boolean isStereo, int sampleRate) {
    rtmpClient.setAudioInfo(sampleRate, isStereo);
  }

  @Override
  protected void startStreamRtp(String url) {
    rtmpClient.connect(url);
  }

  @Override
  protected void stopStreamRtp() {
    rtmpClient.disconnect();
  }

  @Override
  public void setReTries(int reTries) {
    rtmpClient.setReTries(reTries);
  }

  @Override
  protected boolean shouldRetry(String reason) {
    return rtmpClient.shouldRetry(reason);
  }

  @Override
  public void reConnect(long delay, @Nullable String backupUrl) {
    rtmpClient.reConnect(delay, backupUrl);
  }

  @Override
  public boolean hasCongestion() {
    return rtmpClient.hasCongestion();
  }

  @Override
  protected void getAacDataRtp(ByteBuffer aacBuffer, MediaCodec.BufferInfo info) {
    rtmpClient.sendAudio(aacBuffer, info);
  }

  @Override
  public void setLogs(boolean enable) {
    rtmpClient.setLogs(enable);
  }

  @Override
  public void setCheckServerAlive(boolean enable) {
    rtmpClient.setCheckServerAlive(enable);
  }
}
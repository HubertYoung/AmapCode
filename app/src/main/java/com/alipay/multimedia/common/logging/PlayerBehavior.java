package com.alipay.multimedia.common.logging;

import android.os.Bundle;
import com.alibaba.fastjson.JSON;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.alipay.mobile.beehive.audio.Constants;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.behavor.Behavor;
import com.alipay.mobile.h5container.api.H5Param;
import java.util.concurrent.atomic.AtomicBoolean;

public class PlayerBehavior {
    private String bizExtra;
    public long bufferedCostTime;
    public int bufferedTimes;
    public int code;
    public long duration;
    public int encrypted = 0;
    private Bundle mExtra;
    private AtomicBoolean mSubmitted = new AtomicBoolean(false);
    public int netCode = 0;
    public long playedTime;
    public long preparedCostTime;
    public long realPlayTime = 0;
    private long resumeTime;
    private long startBufferingTime;
    private long startPreparingTime;
    private long startTime;
    private String url;

    public void startPlay() {
        this.startTime = System.currentTimeMillis();
        this.resumeTime = this.startTime;
    }

    public void startPreparing() {
        this.startPreparingTime = System.currentTimeMillis();
    }

    public void startBuffering() {
        this.startBufferingTime = System.currentTimeMillis();
        this.bufferedTimes++;
    }

    public void pause() {
        long tmp = System.currentTimeMillis() - this.resumeTime;
        if (tmp > 0 && this.resumeTime > 0) {
            this.realPlayTime += tmp;
        }
        this.resumeTime = 0;
    }

    public void resume() {
        this.resumeTime = System.currentTimeMillis();
    }

    private void stopPlay() {
        this.playedTime = System.currentTimeMillis() - this.startTime;
        long tmp = System.currentTimeMillis() - this.resumeTime;
        if (tmp > 0 && this.resumeTime > 0) {
            this.realPlayTime += tmp;
        }
        this.resumeTime = 0;
    }

    public void preparedFinished() {
        this.preparedCostTime = System.currentTimeMillis() - this.startPreparingTime;
    }

    public void bufferedFinished() {
        if (this.startBufferingTime > 0) {
            this.bufferedCostTime += System.currentTimeMillis() - this.startBufferingTime;
            this.startBufferingTime = 0;
        }
    }

    public PlayerBehavior(String url2, Bundle extra, String bizExtra2) {
        this.url = url2;
        if (extra != null) {
            this.mExtra = new Bundle(extra);
        }
        this.bizExtra = bizExtra2;
    }

    public void submit() {
        if (this.mSubmitted.compareAndSet(false, true)) {
            stopPlay();
            Behavor behavor = new Behavor();
            behavor.setAppID("APMultiMedia");
            behavor.setBehaviourPro("APMultiMedia");
            behavor.setUserCaseID("UC-MM-C50");
            behavor.setSeedID("MediaPlayerInfo");
            behavor.setLoggerLevel(2);
            behavor.setParam1(String.valueOf(this.code));
            behavor.setParam2("0");
            behavor.setParam3(String.valueOf(this.preparedCostTime));
            behavor.addExtParam("id", this.url);
            behavor.addExtParam("wd", String.valueOf(this.playedTime));
            behavor.addExtParam("ld", String.valueOf(this.bufferedCostTime));
            behavor.addExtParam("td", String.valueOf(this.duration));
            behavor.addExtParam(H5Param.SAFEPAY_CONTEXT, String.valueOf(this.bufferedTimes));
            behavor.addExtParam("er", String.valueOf(this.netCode));
            behavor.addExtParam("en", String.valueOf(this.encrypted));
            behavor.addExtParam("st", String.valueOf(this.startTime));
            behavor.addExtParam("rt", String.valueOf(this.realPlayTime));
            behavor.addExtParam("bz", this.mExtra == null ? "mm-player" : this.mExtra.getString(Constants.KEY_AUDIO_BUSINESS_ID, "mm-player"));
            behavor.addExtParam(LogItem.MM_C12_K4_ID, this.bizExtra == null ? "" : this.bizExtra);
            behavor.addExtParam("mc", "1");
            LoggerFactory.getTraceLogger().debug("PlayerBehavior", JSON.toJSONString(behavor));
            LoggerFactory.getBehavorLogger().event("event", behavor);
        }
    }
}

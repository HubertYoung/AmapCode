package com.alipay.mobile.mrtc.api.wwj;

import com.alibaba.fastjson.JSON;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StreamerConfig {
    public int enableStats = 0;
    public int mAudioCodecId;
    public int mAudioKBitrate;
    public String mBizName;
    public int mCameraId;
    public int mEncodeMethod;
    public int mLogLevel;
    public String mMachineId;
    private List<OptionItem> mOptions;
    public int mPreviewFrameRate;
    public int mPreviewResolution;
    public String mRoomUrl;
    public String mSignature;
    public int mStreamModel;
    public String mSubBiz;
    public int mTargetFrameRate;
    public int mTargetResolution;
    public String mUid;
    public int mVideoCodecId;
    public int mVideoKBitrate;
    public int timeout = 60;

    private class OptionItem {
        public String key;
        public String value;

        OptionItem(String key2, String value2) {
            this.key = key2;
            this.value = value2;
        }
    }

    public StreamerConfig(String machineId) {
        this.mMachineId = machineId;
        this.mRoomUrl = "";
        this.mUid = "";
        this.mSignature = StreamerConstants.DEFAULT_SIGNATURE;
        this.mBizName = StreamerConstants.DEFAULT_BIZ_NAME;
        this.mSubBiz = StreamerConstants.DEFAULT_SUB_BIZ;
        this.mPreviewFrameRate = 25;
        this.mTargetFrameRate = 15;
        this.mVideoKBitrate = 400;
        this.mAudioKBitrate = 48;
        this.mPreviewResolution = 3;
        this.mTargetResolution = 0;
        this.mEncodeMethod = 2;
        this.mVideoCodecId = 1;
        this.mAudioCodecId = 6;
        this.mCameraId = 0;
        this.mLogLevel = 1;
        this.mStreamModel = 1;
        this.mOptions = new ArrayList();
        addDefaultOptions();
    }

    private void addDefaultOptions() {
        addOrUpdateOption(StreamerConstants.OPTION_KEY_AEC_DUMP, "false");
        addOrUpdateOption(StreamerConstants.OPTION_KEY_DISABLE_BUILTIN_AEC, "false");
        addOrUpdateOption(StreamerConstants.OPTION_KEY_DISABLE_BUILTIN_AGC, "false");
        addOrUpdateOption(StreamerConstants.OPTION_KEY_DISABLE_BUILTIN_NS, "false");
        addOrUpdateOption(StreamerConstants.OPTION_KEY_DO_TRACING, "false");
        addOrUpdateOption(StreamerConstants.OPTION_KEY_ENABLE_LEVELCONTROL, "false");
        addOrUpdateOption(StreamerConstants.OPTION_KEY_IS_VIDEO_CALL, "true");
        addOrUpdateOption(StreamerConstants.OPTION_KEY_IS_AUDIO_CALL, "false");
        addOrUpdateOption(StreamerConstants.OPTION_KEY_NO_AUDIO_PROCESSING, "false");
        addOrUpdateOption(StreamerConstants.OPTION_KEY_USE_OPENSLES, "false");
        addOrUpdateOption(StreamerConstants.OPTION_KEY_VIDEO_CODEC_HWACCELERATION, "true");
        addOrUpdateOption(StreamerConstants.OPTION_KEY_DTLS_SRTP_KEY_AGREEMENT_CONSTRAINT, "true");
        addOrUpdateOption(StreamerConstants.OPTION_KEY_USE_CAMERA2, "false");
        addOrUpdateOption(StreamerConstants.OPTION_KEY_ENABLE_STAT, "true");
        addOrUpdateOption(StreamerConstants.OPTION_KEY_STAT_PERIOD_MS, "1000");
    }

    public void addOrUpdateOption(String key, String value) {
        boolean found = false;
        Iterator<OptionItem> it = this.mOptions.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            OptionItem item = it.next();
            if (item.key.equals(key)) {
                item.value = value;
                found = true;
                break;
            }
        }
        if (!found) {
            this.mOptions.add(new OptionItem(key, value));
        }
    }

    public String getOptionStr(String key) {
        for (OptionItem item : this.mOptions) {
            if (item.key.equals(key)) {
                return item.value;
            }
        }
        return "";
    }

    public int getOptionInt(String key) {
        String ret = "";
        Iterator<OptionItem> it = this.mOptions.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            OptionItem item = it.next();
            if (item.key.equals(key)) {
                ret = item.value;
                break;
            }
        }
        return Integer.parseInt(ret);
    }

    public boolean getOptionBool(String key) {
        String ret = "";
        Iterator<OptionItem> it = this.mOptions.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            OptionItem item = it.next();
            if (item.key.equals(key)) {
                ret = item.value;
                break;
            }
        }
        if ("true".equals(ret)) {
            return true;
        }
        return false;
    }

    public static StreamerConfig fromJson(String json) {
        return (StreamerConfig) JSON.parseObject(json, StreamerConfig.class);
    }

    public String toJson() {
        return JSON.toJSONString(this);
    }
}

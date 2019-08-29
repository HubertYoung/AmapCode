package com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items;

import android.text.TextUtils;
import com.alibaba.fastjson.annotation.JSONField;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.DiskExpUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import tv.danmaku.ijk.media.encode.FFmpegSessionConfig;

public class LiveConfigItem {
    public static final int LIVE_DECODE_TYPE_HARD = 1;
    public static final int LIVE_DECODE_TYPE_SOFT = 0;
    public static final int LIVE_RECORD_TYPE_HARD = 1;
    public static final int LIVE_RECORD_TYPE_SOFT = 0;
    public static final int PLAYTIMEOUT = 5000000;
    @JSONField(name = "cpuLevel")
    public int cpuLevel = 4;
    @JSONField(name = "crf")
    public String crf = "";
    @JSONField(name = "decodeType")
    public int decodeType = 1;
    @JSONField(name = "fullInterval")
    public int fullInterval = 1000;
    @JSONField(name = "handshaketo")
    public int handshakeTimeout = PLAYTIMEOUT;
    @JSONField(name = "height")
    public int height = 960;
    @JSONField(name = "dur")
    public int interval = DiskExpUtils.DISK_NO_SPACE;
    @JSONField(name = "pcint")
    public int pCountInterval = 10000;
    @JSONField(name = "pcswitch")
    public int pCountSwitch = 0;
    @JSONField(name = "pingSwitch")
    public int pingSwitch = 0;
    @JSONField(name = "pingUrl")
    public String pingUrl = "";
    @JSONField(name = "playto")
    public int playTimeout = PLAYTIMEOUT;
    @JSONField(name = "preset")
    public String preset = "";
    @JSONField(name = "rcint")
    public int rCountInterval = 10000;
    @JSONField(name = "rcswitch")
    public int rCountSwitch = 1;
    @JSONField(name = "rate")
    public int rate = 0;
    @JSONField(name = "recordType")
    public int recordType = 1;
    @JSONField(name = "retrypto")
    public int retryPlayTimeOut = 30000;
    @JSONField(name = "rtbtapi")
    public int rtbtapi = 18;
    @JSONField(name = "sucInterval")
    public int sucInterval = 1000;
    @JSONField(name = "weakNetSwitch")
    public int weakNetSwitch = 1;
    @JSONField(name = "width")
    public int width = FFmpegSessionConfig.VIDEO_SOFTENCODE_W;

    public boolean isHardEncode() {
        return this.recordType == 1;
    }

    public boolean isHardDecode() {
        return this.decodeType == 1;
    }

    public static LiveConfigItem parseLiveConfig(LiveConfigItem item, String param) {
        try {
            if (!TextUtils.isEmpty(param) && param.contains(MergeUtil.SEPARATOR_KV)) {
                String[] items = param.split("\\|");
                int size = items.length;
                if (!TextUtils.isEmpty(items[0])) {
                    item.recordType = Integer.parseInt(items[0]);
                }
                if (!TextUtils.isEmpty(items[1])) {
                    item.cpuLevel = Integer.parseInt(items[1]);
                }
                if (size >= 3) {
                    item.crf = items[2];
                }
                if (size >= 4) {
                    item.preset = items[3];
                }
                if (size >= 6) {
                    if (!TextUtils.isEmpty(items[4])) {
                        int w = Integer.parseInt(items[4]);
                        if (w > 0) {
                            item.width = w;
                        }
                    }
                    if (!TextUtils.isEmpty(items[5])) {
                        int h = Integer.parseInt(items[5]);
                        if (h > 0) {
                            item.height = h;
                        }
                    }
                }
                if (size >= 7 && !TextUtils.isEmpty(items[6])) {
                    item.rate = Integer.parseInt(items[6]);
                }
            }
        } catch (Exception e) {
            Logger.D("LiveConfigItem", "parseLiveConfig exp=" + e.toString(), new Object[0]);
        }
        Logger.D("LiveConfigItem", "parseLiveConfig item=" + (item == null ? "null" : item.toString()) + ";param=" + param, new Object[0]);
        return item;
    }

    public String toString() {
        return "LiveConfigItem{recordType=" + this.recordType + ", cpuLevel=" + this.cpuLevel + ", crf='" + this.crf + '\'' + ", preset='" + this.preset + '\'' + ", width=" + this.width + ", height=" + this.height + ", rate=" + this.rate + ", playTimeout=" + this.playTimeout + ", decodeType=" + this.decodeType + ", interval=" + this.interval + ", rCountInterval=" + this.rCountInterval + ", pCountInterval=" + this.pCountInterval + ", rCountSwitch=" + this.rCountSwitch + ", pCountSwitch=" + this.pCountSwitch + ", rtbtapi=" + this.rtbtapi + ", weakNetSwitch=" + this.weakNetSwitch + ", sucInterval=" + this.sucInterval + ", fullInterval=" + this.fullInterval + '}';
    }
}

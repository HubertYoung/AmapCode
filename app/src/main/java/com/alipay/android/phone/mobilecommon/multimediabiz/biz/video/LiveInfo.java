package com.alipay.android.phone.mobilecommon.multimediabiz.biz.video;

import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.PathUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.VideoUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LiveInfo {
    private static final Pattern a = Pattern.compile("[\\?&](beauty=(-?\\d+)&?)");
    public int beauty = -1;
    public String inputUrl;
    public String liveUrl;

    public LiveInfo(String inputUrl2) {
        this.inputUrl = inputUrl2;
        this.liveUrl = inputUrl2;
        a();
    }

    public boolean isBeautyOn() {
        return this.beauty >= 0;
    }

    private void a() {
        if (TextUtils.isEmpty(this.inputUrl)) {
            return;
        }
        if (PathUtils.isHttp(this.inputUrl) || PathUtils.isRtmp(this.inputUrl)) {
            Matcher matcher = a.matcher(this.inputUrl);
            while (matcher.find()) {
                this.beauty = Integer.parseInt(matcher.group(2));
                this.liveUrl = this.liveUrl.replace(matcher.group(1), "");
            }
            if (this.liveUrl.endsWith("&") || this.liveUrl.endsWith("?")) {
                this.liveUrl = this.liveUrl.substring(0, this.liveUrl.length() - 1);
            }
            if (!this.liveUrl.contains("tb_eagleeye_traceid=")) {
                if (this.liveUrl.contains("?")) {
                    this.liveUrl += "&tb_eagleeye_traceid=" + VideoUtils.generateEagleId();
                } else {
                    this.liveUrl += "?tb_eagleeye_traceid=" + VideoUtils.generateEagleId();
                }
            }
        }
    }

    public String toString() {
        return "LiveInfo{liveUrl='" + this.liveUrl + '\'' + ", beauty=" + this.beauty + ", inputUrl='" + this.inputUrl + '\'' + '}';
    }
}

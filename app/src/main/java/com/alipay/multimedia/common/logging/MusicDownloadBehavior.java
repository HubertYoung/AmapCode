package com.alipay.multimedia.common.logging;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.behavor.Behavor;
import java.util.concurrent.atomic.AtomicBoolean;

public class MusicDownloadBehavior {
    public static final int RESULT_CANCEL = 6;
    public static final int RESULT_CREATE_TEMP_CACHE_FILE_FAIL = 7;
    public static final int RESULT_DECRYPT_ERROR = 9;
    public static final int RESULT_DOWNLOAD_ERROR = 8;
    public static final int RESULT_ERROR_UNKNOWN = 10;
    public static final int RESULT_EXTRACT_URL_FAIL = 2;
    public static final int RESULT_GET_CONTENT_LENGTH_FAIL = 5;
    public static final int RESULT_GET_HEAD_FAIL = 4;
    public static final int RESULT_NOT_GET_REQUEST = 1;
    public static final int RESULT_OK = 0;
    public static final int RESULT_VERIFY_SIGN_FAIL = 3;
    public long contentLength;
    public long decryptTime;
    public int encrypt = 0;
    public String fileId;
    private AtomicBoolean mSubmitted = new AtomicBoolean(false);
    public int result = 0;
    public int status = 200;
    public String url;

    public void submit() {
        if (this.mSubmitted.compareAndSet(false, true)) {
            Behavor behavor = new Behavor();
            behavor.setAppID("APMultiMedia");
            behavor.setBehaviourPro("APMultiMedia");
            behavor.setUserCaseID("UC-MM-C59");
            behavor.setSeedID("MuiscDownload");
            behavor.setLoggerLevel(2);
            behavor.setParam1(String.valueOf(this.result));
            behavor.setParam2("0");
            behavor.setParam3(String.valueOf(this.decryptTime));
            behavor.addExtParam("url", TextUtils.isEmpty(this.url) ? "" : this.url);
            behavor.addExtParam("fid", TextUtils.isEmpty(this.fileId) ? "" : this.fileId);
            behavor.addExtParam("cl", String.valueOf(this.contentLength));
            behavor.addExtParam("st", String.valueOf(this.status));
            behavor.addExtParam("enc", String.valueOf(this.encrypt));
            LoggerFactory.getTraceLogger().debug("MuiscDownload", JSON.toJSONString(behavor));
            LoggerFactory.getBehavorLogger().event("event", behavor);
        }
    }
}

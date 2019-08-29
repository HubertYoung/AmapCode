package com.amap.bundle.network.response;

import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.alipay.mobile.common.share.widget.ResUtils;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;
import com.iflytek.tts.TtsService.alc.ALCTtsConstant;
import java.io.UnsupportedEncodingException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

@Keep
@KeepClassMembers
public abstract class AbstractAOSParser {
    protected static final String BASE_ERROR = AMapAppGlobal.getApplication().getString(R.string.error_unknown);
    public static final int CODE_NETWORK_FAIL = -1;
    public static final int CODE_PARSER_FAIL = -2;
    public static final int CODE_SUCCESS = 1;
    private static final boolean DEBUG = bno.a;
    public static final String DEFAULT_ERROR_MSG = "未知错误，请稍后重试";
    public static final String ERROR_NETWORK = AMapAppGlobal.getApplication().getString(R.string.network_error_message);
    public static final String PARSE_ERROR = AMapAppGlobal.getApplication().getString(R.string.error_fail_to_parse_data);
    private static final String TAG = "AbstractAOSParser";
    public static final String UNKNOWN_ERROR = AMapAppGlobal.getApplication().getString(R.string.network_error_message);
    public int errorCode = -1;
    public String errorMessage = ERROR_NETWORK;
    public JSONObject mDataObject = null;
    /* access modifiers changed from: private */
    public String noticeAction = null;
    /* access modifiers changed from: private */
    public String noticeContent = null;
    public boolean result = false;
    public long timeStamp = 0;
    public String version = "";

    public abstract String getErrorDesc(int i);

    public abstract void parser(byte[] bArr) throws UnsupportedEncodingException, JSONException;

    /* access modifiers changed from: protected */
    public JSONObject parseHeader(byte[] bArr) {
        if (bArr == null) {
            this.errorCode = -1;
            return this.mDataObject;
        }
        try {
            this.mDataObject = new JSONObject(new String(bArr, "UTF-8"));
            this.version = this.mDataObject.getString("version");
            this.result = this.mDataObject.getBoolean("result");
            this.errorCode = this.mDataObject.getInt("code");
            this.errorMessage = parserMessage(this.errorMessage, this.mDataObject);
            this.timeStamp = this.mDataObject.getLong("timestamp");
            JSONArray optJSONArray = this.mDataObject.optJSONArray("_notice_");
            if (optJSONArray != null && optJSONArray.length() > 0) {
                JSONObject optJSONObject = optJSONArray.optJSONObject(0);
                String optString = optJSONObject.optString(ResUtils.STYLE, "");
                this.noticeContent = optJSONObject.optString("content");
                this.noticeAction = optJSONObject.optString("action");
                if (optString.equalsIgnoreCase("0")) {
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        public final void run() {
                            aaf.a(AbstractAOSParser.this.noticeContent);
                            AbstractAOSParser.this.noticeContent = null;
                            AbstractAOSParser.this.noticeAction = null;
                        }
                    }, 1000);
                } else if (!optString.equalsIgnoreCase("-1")) {
                    this.noticeContent = null;
                    this.noticeAction = null;
                }
            }
        } catch (Exception e) {
            this.result = false;
            this.errorCode = -2;
            this.errorMessage = PARSE_ERROR;
            AMapLog.logFatalNative(AMapLog.GROUP_COMMON, "P0002", ALCTtsConstant.EVENT_ID_TTS_INIT_ERROR, e.toString());
        }
        this.errorMessage = getErrorDesc(this.errorCode);
        if (DEBUG && this.mDataObject != null) {
            AMapLog.i(TAG, this.mDataObject.toString());
        }
        return this.mDataObject;
    }

    public String parserMessage(String str, JSONObject jSONObject) {
        if (jSONObject != null) {
            String optString = jSONObject.optString("message");
            if (!TextUtils.isEmpty(optString)) {
                return optString;
            }
        }
        return str;
    }

    public String getNoticeContent() {
        return this.noticeContent;
    }

    public String getNoticeAction() {
        return this.noticeAction;
    }

    public boolean isSuccessRequest() {
        return 1 == this.errorCode;
    }

    public static String aosByteResponseToString(AosByteResponse aosByteResponse) {
        if (aosByteResponse == null || aosByteResponse.getResult() == null) {
            return "";
        }
        try {
            return new String((byte[]) aosByteResponse.getResult(), "UTF-8");
        } catch (UnsupportedEncodingException unused) {
            return "";
        }
    }

    public static JSONObject aosByteResponseToJSONObject(AosByteResponse aosByteResponse) throws JSONException {
        return new JSONObject(aosByteResponseToString(aosByteResponse));
    }

    public static String aosResponseCodeToMessage(int i) {
        Resources resources = AMapAppGlobal.getApplication().getResources();
        switch (i) {
            case 0:
                return resources.getString(R.string.error_server_busy);
            case 1:
                return "";
            case 2:
                return resources.getString(R.string.error_server_busy);
            case 3:
                return resources.getString(R.string.error_invalid_format_retry);
            case 4:
                return AMapAppGlobal.getApplication().getString(R.string.error_incorrect_signature);
            case 5:
                return resources.getString(R.string.error_outdated_license);
            default:
                switch (i) {
                    case 14:
                        return resources.getString(R.string.error_outdated_login_info);
                    case 15:
                        return resources.getString(R.string.error_incorrect_login_name);
                    case 16:
                        return resources.getString(R.string.error_incorrect_login_info);
                    case 17:
                        return resources.getString(R.string.error_incorrect_verify_code);
                    default:
                        switch (i) {
                            case 20:
                                return resources.getString(R.string.error_login_name_already_exist);
                            case 21:
                                return resources.getString(R.string.error_nickname_already_exist);
                            default:
                                switch (i) {
                                    case 23:
                                        return resources.getString(R.string.error_incorrect_login_info);
                                    case 24:
                                        break;
                                    default:
                                        switch (i) {
                                            case 26:
                                                return resources.getString(R.string.error_email_already_exist);
                                            case 27:
                                                return resources.getString(R.string.error_incorrect_cell_number);
                                            case 28:
                                                return resources.getString(R.string.error_incorrect_email_address);
                                            default:
                                                switch (i) {
                                                    case 35:
                                                        return resources.getString(R.string.error_incorrect_verify_code);
                                                    case 36:
                                                        return resources.getString(R.string.error_weibo_account_already_exist);
                                                    default:
                                                        switch (i) {
                                                            case 39:
                                                                return resources.getString(R.string.weibo_authorize_success);
                                                            case 40:
                                                                return resources.getString(R.string.error_weibo_account_already_exist);
                                                            case 41:
                                                                return resources.getString(R.string.error_exceed_max_number_of_request_per_minute);
                                                            case 42:
                                                                return resources.getString(R.string.error_exceed_max_number_of_request_per_hour);
                                                            default:
                                                                switch (i) {
                                                                    case 52:
                                                                        return resources.getString(R.string.error_outdated_verify_code);
                                                                    case 53:
                                                                        return resources.getString(R.string.error_incorrect_user_name);
                                                                    case 54:
                                                                        return resources.getString(R.string.error_incorrect_email_address2);
                                                                    case 55:
                                                                        return resources.getString(R.string.error_incorrect_cell_number2);
                                                                    case 56:
                                                                        return resources.getString(R.string.error_incorrect_login_info);
                                                                    case 57:
                                                                        return resources.getString(R.string.nickname_format);
                                                                    case 58:
                                                                        return resources.getString(R.string.error_taobao_account_already_exist);
                                                                    case 59:
                                                                        return resources.getString(R.string.taobao_authorize_success);
                                                                    default:
                                                                        switch (i) {
                                                                            case IjkMediaMeta.FF_PROFILE_H264_EXTENDED /*88*/:
                                                                                return resources.getString(R.string.unbound_qq_account);
                                                                            case 89:
                                                                                return resources.getString(R.string.error_bound_qq_account);
                                                                            case 90:
                                                                                return resources.getString(R.string.error_bound_qq_account);
                                                                            case 91:
                                                                                return resources.getString(R.string.error_fail_to_read_qq_info);
                                                                            default:
                                                                                switch (i) {
                                                                                    case 7:
                                                                                        return resources.getString(R.string.error_verification_nonexist);
                                                                                    case 30:
                                                                                        return resources.getString(R.string.error_require_relogin);
                                                                                    case 32:
                                                                                        break;
                                                                                    case 61:
                                                                                        return resources.getString(R.string.error_server_busy);
                                                                                    case 79:
                                                                                        return resources.getString(R.string.wo_plus_authorize_success);
                                                                                    case 113:
                                                                                        return resources.getString(R.string.error_already_made_a_comment);
                                                                                    default:
                                                                                        return AMapAppGlobal.getApplication().getString(R.string.error_server_busy);
                                                                                }
                                                                        }
                                                                }
                                                        }
                                                }
                                        }
                                }
                                return resources.getString(R.string.error_incorrect_login_info);
                        }
                }
        }
    }
}

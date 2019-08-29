package com.tencent.connect.common;

import android.content.Intent;
import com.autonavi.minimap.ajx3.modules.ModuleLongLinkService;
import com.tencent.open.a.f;
import com.tencent.open.utils.SystemUtils;
import com.tencent.open.utils.Util;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ProGuard */
public class UIListenerManager {
    private static final String TAG = "openSDK_LOG.UIListenerManager";
    private static UIListenerManager mInstance;
    private Map<String, ApiTask> mListenerMap = Collections.synchronizedMap(new HashMap());

    /* compiled from: ProGuard */
    public class ApiTask {
        public IUiListener mListener;
        public int mRequestCode;

        public ApiTask(int i, IUiListener iUiListener) {
            this.mRequestCode = i;
            this.mListener = iUiListener;
        }
    }

    public static UIListenerManager getInstance() {
        if (mInstance == null) {
            mInstance = new UIListenerManager();
        }
        return mInstance;
    }

    private UIListenerManager() {
        if (this.mListenerMap == null) {
            this.mListenerMap = Collections.synchronizedMap(new HashMap());
        }
    }

    public Object setListenerWithRequestcode(int i, IUiListener iUiListener) {
        ApiTask put;
        String actionFromRequestcode = SystemUtils.getActionFromRequestcode(i);
        if (actionFromRequestcode == null) {
            f.e(TAG, "setListener action is null! rquestCode=".concat(String.valueOf(i)));
            return null;
        }
        synchronized (this.mListenerMap) {
            put = this.mListenerMap.put(actionFromRequestcode, new ApiTask(i, iUiListener));
        }
        if (put == null) {
            return null;
        }
        return put.mListener;
    }

    public Object setListnerWithAction(String str, IUiListener iUiListener) {
        ApiTask put;
        int requestCodeFromCallback = SystemUtils.getRequestCodeFromCallback(str);
        if (requestCodeFromCallback == -1) {
            f.e(TAG, "setListnerWithAction fail, action = ".concat(String.valueOf(str)));
            return null;
        }
        synchronized (this.mListenerMap) {
            put = this.mListenerMap.put(str, new ApiTask(requestCodeFromCallback, iUiListener));
        }
        if (put == null) {
            return null;
        }
        return put.mListener;
    }

    public IUiListener getListnerWithRequestCode(int i) {
        String actionFromRequestcode = SystemUtils.getActionFromRequestcode(i);
        if (actionFromRequestcode != null) {
            return getListnerWithAction(actionFromRequestcode);
        }
        f.e(TAG, "getListner action is null! rquestCode=".concat(String.valueOf(i)));
        return null;
    }

    public IUiListener getListnerWithAction(String str) {
        ApiTask apiTask;
        if (str == null) {
            f.e(TAG, "getListnerWithAction action is null!");
            return null;
        }
        synchronized (this.mListenerMap) {
            apiTask = this.mListenerMap.get(str);
            this.mListenerMap.remove(str);
        }
        if (apiTask == null) {
            return null;
        }
        return apiTask.mListener;
    }

    public void handleDataToListener(Intent intent, IUiListener iUiListener) {
        f.c(TAG, "handleDataToListener");
        if (intent == null) {
            iUiListener.onCancel();
            return;
        }
        String stringExtra = intent.getStringExtra(Constants.KEY_ACTION);
        if (SystemUtils.ACTION_LOGIN.equals(stringExtra)) {
            int intExtra = intent.getIntExtra(Constants.KEY_ERROR_CODE, 0);
            if (intExtra == 0) {
                String stringExtra2 = intent.getStringExtra(Constants.KEY_RESPONSE);
                if (stringExtra2 != null) {
                    try {
                        iUiListener.onComplete(Util.parseJson(stringExtra2));
                    } catch (JSONException e) {
                        iUiListener.onError(new UiError(-4, Constants.MSG_JSON_ERROR, stringExtra2));
                        f.b(TAG, "OpenUi, onActivityResult, json error", e);
                    }
                } else {
                    f.b(TAG, "OpenUi, onActivityResult, onComplete");
                    iUiListener.onComplete(new JSONObject());
                }
            } else {
                f.e(TAG, "OpenUi, onActivityResult, onError = ".concat(String.valueOf(intExtra)));
                iUiListener.onError(new UiError(intExtra, intent.getStringExtra(Constants.KEY_ERROR_MSG), intent.getStringExtra(Constants.KEY_ERROR_DETAIL)));
            }
        } else {
            if (SystemUtils.ACTION_SHARE.equals(stringExtra)) {
                String stringExtra3 = intent.getStringExtra("result");
                String stringExtra4 = intent.getStringExtra(ModuleLongLinkService.CALLBACK_KEY_RESPONSE);
                if ("cancel".equals(stringExtra3)) {
                    iUiListener.onCancel();
                } else if ("error".equals(stringExtra3)) {
                    iUiListener.onError(new UiError(-6, "unknown error", String.valueOf(stringExtra4)));
                } else if ("complete".equals(stringExtra3)) {
                    try {
                        iUiListener.onComplete(new JSONObject(stringExtra4 == null ? "{\"ret\": 0}" : stringExtra4));
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                        iUiListener.onError(new UiError(-4, "json error", String.valueOf(stringExtra4)));
                    }
                }
            }
        }
    }

    private IUiListener buildListener(int i, IUiListener iUiListener) {
        if (i == 11101) {
            f.e(TAG, "登录的接口回调不能重新构建，暂时无法提供，先记录下来这种情况是否存在");
        } else if (i == 11105) {
            f.e(TAG, "Social Api 的接口回调需要使用param来重新构建，暂时无法提供，先记录下来这种情况是否存在");
        } else if (i == 11106) {
            f.e(TAG, "Social Api 的H5接口回调需要使用param来重新构建，暂时无法提供，先记录下来这种情况是否存在");
        }
        return iUiListener;
    }

    public boolean onActivityResult(int i, int i2, Intent intent, IUiListener iUiListener) {
        StringBuilder sb = new StringBuilder("onActivityResult req=");
        sb.append(i);
        sb.append(" res=");
        sb.append(i2);
        f.c(TAG, sb.toString());
        IUiListener listnerWithRequestCode = getListnerWithRequestCode(i);
        if (listnerWithRequestCode == null) {
            if (iUiListener != null) {
                listnerWithRequestCode = buildListener(i, iUiListener);
            } else {
                f.e(TAG, "onActivityResult can't find the listener");
                return false;
            }
        }
        if (i2 != -1) {
            listnerWithRequestCode.onCancel();
        } else if (intent == null) {
            listnerWithRequestCode.onError(new UiError(-6, "onActivityResult intent data is null.", "onActivityResult intent data is null."));
            return true;
        } else {
            String stringExtra = intent.getStringExtra(Constants.KEY_ACTION);
            if (SystemUtils.ACTION_LOGIN.equals(stringExtra)) {
                int intExtra = intent.getIntExtra(Constants.KEY_ERROR_CODE, 0);
                if (intExtra == 0) {
                    String stringExtra2 = intent.getStringExtra(Constants.KEY_RESPONSE);
                    if (stringExtra2 != null) {
                        try {
                            listnerWithRequestCode.onComplete(Util.parseJson(stringExtra2));
                        } catch (JSONException e) {
                            listnerWithRequestCode.onError(new UiError(-4, Constants.MSG_JSON_ERROR, stringExtra2));
                            f.b(TAG, "OpenUi, onActivityResult, json error", e);
                        }
                    } else {
                        f.b(TAG, "OpenUi, onActivityResult, onComplete");
                        listnerWithRequestCode.onComplete(new JSONObject());
                    }
                } else {
                    f.e(TAG, "OpenUi, onActivityResult, onError = ".concat(String.valueOf(intExtra)));
                    listnerWithRequestCode.onError(new UiError(intExtra, intent.getStringExtra(Constants.KEY_ERROR_MSG), intent.getStringExtra(Constants.KEY_ERROR_DETAIL)));
                }
            } else if (SystemUtils.ACTION_SHARE.equals(stringExtra)) {
                String stringExtra3 = intent.getStringExtra("result");
                String stringExtra4 = intent.getStringExtra(ModuleLongLinkService.CALLBACK_KEY_RESPONSE);
                if ("cancel".equals(stringExtra3)) {
                    listnerWithRequestCode.onCancel();
                } else if ("error".equals(stringExtra3)) {
                    listnerWithRequestCode.onError(new UiError(-6, "unknown error", String.valueOf(stringExtra4)));
                } else if ("complete".equals(stringExtra3)) {
                    try {
                        listnerWithRequestCode.onComplete(new JSONObject(stringExtra4 == null ? "{\"ret\": 0}" : stringExtra4));
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                        listnerWithRequestCode.onError(new UiError(-4, "json error", String.valueOf(stringExtra4)));
                    }
                }
            } else {
                int intExtra2 = intent.getIntExtra(Constants.KEY_ERROR_CODE, 0);
                if (intExtra2 == 0) {
                    String stringExtra5 = intent.getStringExtra(Constants.KEY_RESPONSE);
                    if (stringExtra5 != null) {
                        try {
                            listnerWithRequestCode.onComplete(Util.parseJson(stringExtra5));
                        } catch (JSONException unused) {
                            listnerWithRequestCode.onError(new UiError(-4, Constants.MSG_JSON_ERROR, stringExtra5));
                        }
                    } else {
                        listnerWithRequestCode.onComplete(new JSONObject());
                    }
                } else {
                    listnerWithRequestCode.onError(new UiError(intExtra2, intent.getStringExtra(Constants.KEY_ERROR_MSG), intent.getStringExtra(Constants.KEY_ERROR_DETAIL)));
                }
            }
        }
        return true;
    }
}

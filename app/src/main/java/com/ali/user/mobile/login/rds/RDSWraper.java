package com.ali.user.mobile.login.rds;

import android.content.Context;
import android.content.ContextWrapper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import com.ali.user.mobile.AliUserInit;
import com.ali.user.mobile.info.AppInfo;
import com.ali.user.mobile.info.DeviceInfo;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.log.LogAgent;
import com.alipay.android.phone.inside.security.SecBody;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.alipay.rdssecuritysdk.face.RDSInfoSDK;
import com.alipay.rdssecuritysdk.v2.face.RDSClient;
import java.util.HashMap;
import org.json.JSONObject;

public class RDSWraper {
    public static final String CFG_ALIUSER_RDS_PB_ENABLE = "CFG_ALIUSER_RDS_PB_ENABLE";
    /* access modifiers changed from: private */
    public final RDSClient a = new RDSClient();
    /* access modifiers changed from: private */
    public final String b;

    public static void init(Context context) {
        AliUserLog.a((String) "RDSWraper", (String) "RDSWraper init");
        if (AliUserInit.c()) {
            RDSClient.enableLog();
        }
        RDSClient.init(context);
    }

    public RDSWraper(Context context, String str) {
        AliUserLog.c("RDSWraper", "context:".concat(String.valueOf(context)));
        this.b = str;
    }

    public void initPage(Context context, String str, String str2) {
        try {
            RDSInfoSDK.setMcontext(context.getApplicationContext());
            HashMap hashMap = new HashMap();
            hashMap.put("user", str);
            hashMap.put(DictionaryKeys.V2_PAGENAME, this.b);
            hashMap.put(DictionaryKeys.V2_REFPAGENAME, str2);
            hashMap.put("appname", AppInfo.getInstance().getProductId());
            hashMap.put("appver", AppInfo.getInstance().getProductVersion());
            hashMap.put("sdkname", AppInfo.getInstance().getSdkId());
            hashMap.put("sdkver", AppInfo.getInstance().getSdkVersion());
            hashMap.put("tid", "");
            DeviceInfo.b();
            hashMap.put("utdid", DeviceInfo.h());
            hashMap.put(DictionaryKeys.V2_UMID, AppInfo.getInstance().getUmid());
            hashMap.put(DictionaryKeys.V2_APDID, AppInfo.getInstance().getApdidToken());
            hashMap.put(DictionaryKeys.V2_PACKAGENAME, context.getApplicationContext().getPackageName());
            hashMap.put("appkey", AppInfo.getInstance().getAppKey(context.getApplicationContext()));
            hashMap.put(DictionaryKeys.V2_PBSWITCH, "v3");
            this.a.onPage(context.getApplicationContext(), hashMap, false);
        } catch (Throwable th) {
            AliUserLog.b((String) "RDSWraper", th);
        }
    }

    public void initTextChange(EditText editText, final String str) {
        editText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                try {
                    RDSWraper.this.a.onKeyDown(RDSWraper.this.b, str, charSequence.subSequence(i, i3 + i).toString());
                } catch (Throwable th) {
                    AliUserLog.b((String) "RDSWraper", th);
                }
            }
        });
    }

    public void initScreenTouch(View view, final String str) {
        if (view != null) {
            view.setOnTouchListener(new OnTouchListener() {
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    try {
                        RDSWraper.this.a.onTouchScreen(RDSWraper.this.b, str, (double) motionEvent.getX(), (double) motionEvent.getY());
                    } catch (Throwable th) {
                        AliUserLog.b((String) "RDSWraper", th);
                    }
                    return false;
                }
            });
        }
    }

    public void initFocusChange(View view, final String str) {
        try {
            final OnFocusChangeListener onFocusChangeListener = view.getOnFocusChangeListener();
            view.setOnFocusChangeListener(new OnFocusChangeListener() {
                public void onFocusChange(View view, boolean z) {
                    try {
                        if (onFocusChangeListener != null) {
                            onFocusChangeListener.onFocusChange(view, z);
                        }
                        if (z) {
                            RDSWraper.this.a.onGetFocus(RDSWraper.this.b, str);
                        } else {
                            RDSWraper.this.a.onLostFocus(RDSWraper.this.b, str);
                        }
                    } catch (Throwable th) {
                        AliUserLog.b((String) "RDSWraper", th);
                    }
                }
            });
        } catch (Throwable th) {
            AliUserLog.b((String) "RDSWraper", th);
        }
    }

    public void onControlClick(String str) {
        try {
            this.a.onControlClick(this.b, str);
        } catch (Throwable th) {
            AliUserLog.b((String) "RDSWraper", th);
        }
    }

    public String getRdsData(Context context, String str) {
        try {
            return this.a.onPageEndAndZip(context.getApplicationContext(), str);
        } catch (Throwable th) {
            AliUserLog.b((String) "RDSWraper", th);
            LogAgent.a(th);
            return null;
        }
    }

    public static String getSafeData(Context context) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(System.currentTimeMillis());
            String sb2 = sb.toString();
            new SecBody(new ContextWrapper(context));
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(LogItem.MM_C15_K4_TIME, sb2);
            jSONObject.put("wua", null);
            jSONObject.put("appKey", AppInfo.getInstance().getAppKey(context));
            StringBuilder sb3 = new StringBuilder("rdswrapper: ");
            sb3.append(jSONObject.toString());
            AliUserLog.c("RDSWraper", sb3.toString());
            return jSONObject.toString();
        } catch (Throwable th) {
            StringBuilder sb4 = new StringBuilder();
            sb4.append(th.getMessage());
            AliUserLog.d("RDSWraper", sb4.toString());
            return null;
        }
    }
}

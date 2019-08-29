package com.autonavi.minimap.ajx3.modules.internalmodules;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxField;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import org.json.JSONException;
import org.json.JSONObject;

@AjxModule("ajx.phone")
public class AjxModulePhone extends AbstractModule {
    @AjxField("CALL_STATE_CONNECTED")
    public static final int CALL_STATE_CONNECTED = 3;
    @AjxField("CALL_STATE_DIALING")
    public static final int CALL_STATE_DIALING = 1;
    @AjxField("CALL_STATE_DISCONNECTE")
    public static final int CALL_STATE_DISCONNECT = 4;
    @AjxField("CALL_STATE_IDLE")
    public static final int CALL_STATE_IDLE = 100;
    @AjxField("CALL_STATE_INCOMING")
    public static final int CALL_STATE_INCOMING = 2;
    @AjxField("CALL_STATE_OFFHOOK")
    public static final int CALL_STATE_OFFHOOK = 102;
    @AjxField("CALL_STATE_RINGING")
    public static final int CALL_STATE_RINGING = 101;
    private JsFunctionCallback mJsPhoneStateCallback = null;
    /* access modifiers changed from: private */
    public JsFunctionCallback mPhoneCallback;
    private PhoneStateListener mPhoneStateListener = null;
    private JsFunctionCallback mRegisterPhoneCallback;
    private TelephonyManager mTelephonyManager;

    public AjxModulePhone(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod(invokeMode = "sync", value = "isCallOffHook")
    public boolean isCallOffHook() {
        Context nativeContext = getNativeContext();
        if (nativeContext == null) {
            return false;
        }
        if (this.mTelephonyManager == null) {
            this.mTelephonyManager = (TelephonyManager) nativeContext.getSystemService("phone");
        }
        int callState = this.mTelephonyManager.getCallState();
        if (callState == 2 || callState == 1) {
            return true;
        }
        return false;
    }

    @AjxMethod(invokeMode = "sync", value = "checkPhoneCalling")
    public String checkPhoneCalling() {
        Context nativeContext = getNativeContext();
        if (nativeContext == null) {
            return "0";
        }
        if (this.mTelephonyManager == null) {
            this.mTelephonyManager = (TelephonyManager) nativeContext.getSystemService("phone");
        }
        return this.mTelephonyManager.getCallState() == 0 ? "0" : "1";
    }

    @AjxMethod("registerPhoneStateChange")
    public void registerPhoneStateChange(JsFunctionCallback jsFunctionCallback) {
        this.mJsPhoneStateCallback = jsFunctionCallback;
        if (this.mPhoneStateListener == null) {
            registerPhoneStateChangeListener();
        }
    }

    private void registerPhoneStateChangeListener() {
        Context nativeContext = getNativeContext();
        if (nativeContext != null) {
            if (this.mTelephonyManager == null) {
                this.mTelephonyManager = (TelephonyManager) nativeContext.getSystemService("phone");
            }
            this.mPhoneStateListener = new PhoneStateListener() {
                public void onCallStateChanged(int i, String str) {
                    switch (i) {
                        case 0:
                            try {
                                JSONObject jSONObject = new JSONObject();
                                jSONObject.put("status", "0");
                                AjxModulePhone.this.notifyPhoneStateChange(jSONObject.toString());
                            } catch (JSONException unused) {
                            }
                            if (AjxModulePhone.this.mPhoneCallback != null) {
                                AjxModulePhone.this.mPhoneCallback.callback(Integer.valueOf(4));
                            }
                            AjxModulePhone.this.phoneCallStateChange(0);
                            return;
                        case 1:
                            try {
                                JSONObject jSONObject2 = new JSONObject();
                                jSONObject2.put("status", "1");
                                AjxModulePhone.this.notifyPhoneStateChange(jSONObject2.toString());
                            } catch (JSONException unused2) {
                            }
                            if (AjxModulePhone.this.mPhoneCallback != null) {
                                AjxModulePhone.this.mPhoneCallback.callback(Integer.valueOf(2));
                            }
                            AjxModulePhone.this.phoneCallStateChange(1);
                            return;
                        case 2:
                            try {
                                JSONObject jSONObject3 = new JSONObject();
                                jSONObject3.put("status", "2");
                                AjxModulePhone.this.notifyPhoneStateChange(jSONObject3.toString());
                            } catch (JSONException unused3) {
                            }
                            if (AjxModulePhone.this.mPhoneCallback != null) {
                                AjxModulePhone.this.mPhoneCallback.callback(Integer.valueOf(1));
                                AjxModulePhone.this.mPhoneCallback.callback(Integer.valueOf(3));
                            }
                            AjxModulePhone.this.phoneCallStateChange(2);
                            break;
                    }
                }
            };
            this.mTelephonyManager.listen(this.mPhoneStateListener, 32);
        }
    }

    /* access modifiers changed from: private */
    public void notifyPhoneStateChange(String str) {
        if (this.mJsPhoneStateCallback != null) {
            this.mJsPhoneStateCallback.callback(str);
        }
    }

    @AjxMethod("onPhoneCall")
    @Deprecated
    public void onPhoneCall(JsFunctionCallback jsFunctionCallback) {
        if (this.mPhoneStateListener == null) {
            registerPhoneStateChangeListener();
        }
        this.mPhoneCallback = jsFunctionCallback;
    }

    @AjxMethod("onCallStateChanged")
    public void onCallStateChanged(JsFunctionCallback jsFunctionCallback) {
        if (this.mPhoneStateListener == null) {
            registerPhoneStateChangeListener();
        }
        this.mPhoneCallback = jsFunctionCallback;
    }

    @AjxMethod(invokeMode = "sync", value = "getCallState")
    public int getCallState() {
        Context nativeContext = getNativeContext();
        int i = -1;
        if (nativeContext == null) {
            return -1;
        }
        if (this.mTelephonyManager == null) {
            this.mTelephonyManager = (TelephonyManager) nativeContext.getSystemService("phone");
        }
        if (this.mTelephonyManager == null) {
            return -1;
        }
        if (this.mTelephonyManager != null) {
            switch (this.mTelephonyManager.getCallState()) {
                case 0:
                    i = 4;
                    break;
                case 1:
                    i = 2;
                    break;
                case 2:
                    i = 3;
                    break;
            }
        }
        return i;
    }

    private void unregisterPhoneStateListener() {
        Context nativeContext = getNativeContext();
        if (nativeContext != null) {
            TelephonyManager telephonyManager = (TelephonyManager) nativeContext.getSystemService("phone");
            if (!(telephonyManager == null || this.mPhoneStateListener == null)) {
                telephonyManager.listen(this.mPhoneStateListener, 0);
            }
        }
    }

    @AjxMethod("registerPhoneCallStateChange")
    public void registerPhoneCallStateChange(JsFunctionCallback jsFunctionCallback) {
        if (this.mPhoneStateListener == null) {
            registerPhoneStateChangeListener();
        }
        this.mRegisterPhoneCallback = jsFunctionCallback;
    }

    /* access modifiers changed from: private */
    public void phoneCallStateChange(int i) {
        if (this.mRegisterPhoneCallback != null) {
            this.mRegisterPhoneCallback.callback(Integer.valueOf(i));
        }
    }

    public void onModuleDestroy() {
        unregisterPhoneStateListener();
    }
}

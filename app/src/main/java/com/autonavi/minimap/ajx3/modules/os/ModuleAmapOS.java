package com.autonavi.minimap.ajx3.modules.os;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.telephony.PhoneNumberUtils;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleOS;

@AjxModule("ajx.os")
public class ModuleAmapOS extends AjxModuleOS {
    @AjxMethod(invokeMode = "sync", value = "setLowBrightness")
    public void setLowBrightness(int i) {
    }

    public ModuleAmapOS(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("openSendSmsUI")
    public void openSmsMessage(String str, String str2, JsFunctionCallback jsFunctionCallback) {
        try {
            if (PhoneNumberUtils.isGlobalPhoneNumber(str)) {
                Intent intent = new Intent("android.intent.action.SENDTO", Uri.parse("smsto:".concat(String.valueOf(str))));
                intent.addFlags(268435456);
                intent.putExtra("sms_body", str2);
                AMapAppGlobal.getApplication().startActivity(intent);
            }
            jsFunctionCallback.callback("1");
        } catch (Exception unused) {
            jsFunctionCallback.callback("0");
        }
    }

    @AjxMethod(invokeMode = "sync", value = "isRecordAudioOn")
    public boolean isSpeechOpen() {
        return hasRecordAudioPermission();
    }

    private boolean hasRecordAudioPermission() {
        return kj.a(getNativeContext(), new String[]{"android.permission.RECORD_AUDIO"});
    }

    @AjxMethod("openSettingsUI")
    public void goSetting() {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", DoNotUseTool.getActivity().getPackageName(), null));
        DoNotUseTool.getActivity().startActivityForResult(intent, 1);
    }

    @AjxMethod("isCharging")
    public boolean isCharging() {
        return isCharging(getNativeContext());
    }

    private boolean isCharging(Context context) {
        Intent registerReceiver = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        if (registerReceiver.getIntExtra("status", -1) == 2) {
            return true;
        }
        int intExtra = registerReceiver.getIntExtra("plugged", -1);
        return (intExtra == 2) || (intExtra == 1) || (intExtra == 4);
    }
}

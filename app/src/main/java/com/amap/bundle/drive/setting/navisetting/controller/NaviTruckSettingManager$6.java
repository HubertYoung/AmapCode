package com.amap.bundle.drive.setting.navisetting.controller;

import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.CameraParams;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.statistics.util.LogUtil;
import org.json.JSONException;
import org.json.JSONObject;

public class NaviTruckSettingManager$6 implements OnCheckedChangeListener {
    final /* synthetic */ qh this$0;

    public NaviTruckSettingManager$6(qh qhVar) {
        this.this$0 = qhVar;
    }

    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        DriveUtil.setTruckAvoidLimitedLoad(z);
        String str = z ? CameraParams.FLASH_MODE_ON : CameraParams.FLASH_MODE_OFF;
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("type", str);
            LogUtil.actionLogV2("P00475", "B003", jSONObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

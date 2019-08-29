package com.amap.bundle.drive.setting.navisetting.controller;

import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.CameraParams;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.statistics.util.LogUtil;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import org.json.JSONException;
import org.json.JSONObject;

public class NaviTruckSettingManager$3 implements OnCheckedChangeListener {
    final /* synthetic */ qh this$0;

    public NaviTruckSettingManager$3(qh qhVar) {
        this.this$0 = qhVar;
    }

    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        AbstractBasePage abstractBasePage = (AbstractBasePage) this.this$0.a.get();
        if (abstractBasePage != null && abstractBasePage.isAlive()) {
            DriveUtil.setTruckAvoidSwitch(z);
            String str = z ? CameraParams.FLASH_MODE_ON : CameraParams.FLASH_MODE_OFF;
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("type", str);
                LogUtil.actionLogV2("P00475", "B002", jSONObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

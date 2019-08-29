package com.autonavi.minimap.uiperformance.page;

import android.content.Context;
import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.utils.ui.ToastHelper;
import com.amap.location.common.model.AmapLoc;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.R;

public class UIPerformanceConfigPage extends AbstractBasePage<emp> implements OnCheckedChangeListener {
    public static final String SP_KEY_CPU = "performance_cpu";
    public static final String SP_KEY_FPS = "performance_fps";
    public static final String SP_KEY_MAP_FPS = "performance_map_fps";
    public static final String SP_KEY_MEM = "performance_mem";
    private CheckBox mCpuSwitch;
    private CheckBox mMapFPSSwitch;
    private CheckBox mMemSwitch;
    private CheckBox mPFSSwitch;

    /* access modifiers changed from: protected */
    public emp createPresenter() {
        return new emp(this);
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.performance_config_layout);
        initView();
    }

    private void initView() {
        MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
        this.mCpuSwitch = (CheckBox) findViewById(R.id.cpu_switch);
        this.mCpuSwitch.setChecked(mapSharePreference.getBooleanValue(SP_KEY_CPU, true));
        this.mCpuSwitch.setOnCheckedChangeListener(this);
        this.mMapFPSSwitch = (CheckBox) findViewById(R.id.map_fps_switch);
        this.mMapFPSSwitch.setChecked(mapSharePreference.getBooleanValue(SP_KEY_MAP_FPS, true));
        this.mMapFPSSwitch.setOnCheckedChangeListener(this);
        this.mPFSSwitch = (CheckBox) findViewById(R.id.fps_switch);
        this.mPFSSwitch.setChecked(mapSharePreference.getBooleanValue(SP_KEY_FPS, true));
        this.mPFSSwitch.setOnCheckedChangeListener(this);
        this.mMemSwitch = (CheckBox) findViewById(R.id.mem_switch);
        this.mMemSwitch.setChecked(mapSharePreference.getBooleanValue(SP_KEY_MEM, true));
        this.mMemSwitch.setOnCheckedChangeListener(this);
    }

    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        emm emm = emo.a(getContext()).a;
        if ((emm.a != null ? emm.a.a.size() : 0) > 1 || z) {
            MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
            int id = compoundButton.getId();
            if (id == R.id.cpu_switch) {
                mapSharePreference.putBooleanValue(SP_KEY_CPU, z);
                updateRecord("cpu_rate", z);
            } else if (id == R.id.map_fps_switch) {
                mapSharePreference.putBooleanValue(SP_KEY_MAP_FPS, z);
                updateRecord("map_fps", z);
            } else if (id == R.id.fps_switch) {
                mapSharePreference.putBooleanValue(SP_KEY_FPS, z);
                updateRecord(LogItem.MM_C13_K4_FPS, z);
            } else {
                if (id == R.id.mem_switch) {
                    mapSharePreference.putBooleanValue(SP_KEY_MEM, z);
                    updateRecord(AmapLoc.TYPE_CACHE, z);
                }
            }
        } else {
            compoundButton.setChecked(true);
            ToastHelper.showToast("最少需要显示一项内容！");
        }
    }

    private void updateRecord(String str, boolean z) {
        Object obj = null;
        if (z) {
            emm emm = emo.a(getContext()).a;
            if (emm.a != null) {
                eml eml = emm.a;
                if (!TextUtils.isEmpty(str)) {
                    if (str.equals("cpu_rate")) {
                        obj = eml.c;
                    } else if (str.equals(LogItem.MM_C13_K4_FPS)) {
                        obj = eml.b;
                    } else if (str.equals("map_fps")) {
                        obj = eml.e;
                    } else if (str.equals(AmapLoc.TYPE_CACHE)) {
                        obj = eml.d;
                    }
                    if (obj != null) {
                        eml.a.add(obj);
                    }
                }
            }
            return;
        }
        emm emm2 = emo.a(getContext()).a;
        if (emm2.a != null) {
            eml eml2 = emm2.a;
            if (!TextUtils.isEmpty(str)) {
                if (str.equals("cpu_rate")) {
                    obj = eml2.c;
                } else if (str.equals(LogItem.MM_C13_K4_FPS)) {
                    obj = eml2.b;
                } else if (str.equals("map_fps")) {
                    obj = eml2.e;
                } else if (str.equals(AmapLoc.TYPE_CACHE)) {
                    obj = eml2.d;
                }
                if (obj != null) {
                    eml2.a.remove(obj);
                    if (str.equals(AmapLoc.TYPE_CACHE)) {
                        eml2.f.a("nativePss");
                        eml2.f.a("dalvikPss");
                        eml2.f.a("TotalPss");
                        return;
                    }
                    eml2.f.a(str);
                }
            }
        }
    }
}

package com.alipay.mobile.nebula.config;

import android.text.TextUtils;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class H5PluginConfig {
    public static final boolean DEFAULT_LAZY_INIT = true;
    public static final String TAG = "H5PluginConfig";
    private static Boolean sDisableConfigServiceOpt = null;
    public String bundleName;
    public String className;
    public List<String> eventList;
    public boolean lazyInit;
    public String scope;

    public H5PluginConfig(String bundleName2, String className2, String scope2, String events, boolean lazyInit2) {
        init();
        this.bundleName = bundleName2;
        this.className = className2;
        this.scope = scope2;
        this.lazyInit = lazyInit2;
        setEvents(events);
    }

    public H5PluginConfig(String bundleName2, String className2, String scope2, String events) {
        init();
        this.bundleName = bundleName2;
        this.className = className2;
        this.scope = scope2;
        setEvents(events);
    }

    public H5PluginConfig(String bundleName2, String className2, String scope2, List<String> events) {
        init();
        this.bundleName = bundleName2;
        this.className = className2;
        this.scope = scope2;
        setEventsList(events);
    }

    public H5PluginConfig() {
        init();
    }

    private void init() {
        this.eventList = new LinkedList();
        this.lazyInit = true;
    }

    public void setEventsList(List<String> list) {
        if (list != null && !list.isEmpty()) {
            this.eventList.addAll(list);
        }
    }

    public void setEvents(String events) {
        if (!TextUtils.isEmpty(events)) {
            if (sDisableConfigServiceOpt == null) {
                sDisableConfigServiceOpt = Boolean.valueOf("yes".equalsIgnoreCase(H5Utils.getConfigByConfigService("h5_disableConfigServiceOpt")));
            }
            if (!sDisableConfigServiceOpt.booleanValue()) {
                String events2 = events.replace(Token.SEPARATOR, "");
                if (!TextUtils.isEmpty(events2)) {
                    H5Log.debug(TAG, "add event configs " + events2);
                    this.eventList.addAll(Arrays.asList(events2.split("\\|")));
                    return;
                }
                return;
            }
            for (String trim : Arrays.asList(events.split("\\|"))) {
                String event = trim.trim();
                if (TextUtils.isEmpty(event)) {
                    H5Log.d(TAG, "invalid empty event");
                } else {
                    H5Log.debug(TAG, "add event config " + event);
                    this.eventList.add(event);
                }
            }
        }
    }

    public boolean configInvalid() {
        return TextUtils.isEmpty(this.bundleName) || TextUtils.isEmpty(this.className) || this.eventList == null || this.eventList.isEmpty();
    }
}

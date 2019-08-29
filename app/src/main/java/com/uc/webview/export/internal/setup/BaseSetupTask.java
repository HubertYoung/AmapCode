package com.uc.webview.export.internal.setup;

import android.webkit.ValueCallback;
import com.uc.webview.export.annotations.Api;
import com.uc.webview.export.extension.UCCore;
import com.uc.webview.export.internal.setup.BaseSetupTask;
import java.util.concurrent.ConcurrentHashMap;

@Api
/* compiled from: ProGuard */
public class BaseSetupTask<RETURN_TYPE extends BaseSetupTask<RETURN_TYPE, CALLBACK_TYPE>, CALLBACK_TYPE extends BaseSetupTask<RETURN_TYPE, CALLBACK_TYPE>> extends UCSubSetupTask<RETURN_TYPE, CALLBACK_TYPE> {
    protected static final String EVENT_REPAIR = "repair";
    protected static final String LEGACY_EVENT_INIT = "init";
    protected static final String LEGACY_EVENT_LOAD = "load";
    protected static final String LEGACY_EVENT_SETUP = "setup";
    protected static final String LEGACY_EVENT_SWITCH = "switch";

    public RETURN_TYPE setAsDefault() {
        return this;
    }

    public BaseSetupTask(UCAsyncTask uCAsyncTask) {
        super(uCAsyncTask);
    }

    public BaseSetupTask() {
    }

    public final RETURN_TYPE onEvent(String str, ValueCallback<CALLBACK_TYPE> valueCallback) {
        return (BaseSetupTask) super.onEvent(str, valueCallback);
    }

    public final RETURN_TYPE setOptions(ConcurrentHashMap<String, Object> concurrentHashMap) {
        super.setOptions(concurrentHashMap);
        return this;
    }

    public final RETURN_TYPE setup(String str, Object obj) {
        return (BaseSetupTask) super.setup(str, obj);
    }

    public String getInitType() {
        return (String) getOption(UCCore.OPTION_BUSINESS_INIT_TYPE);
    }
}

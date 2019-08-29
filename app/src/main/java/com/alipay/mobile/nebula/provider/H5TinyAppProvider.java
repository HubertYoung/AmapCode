package com.alipay.mobile.nebula.provider;

import android.content.Context;
import android.os.Bundle;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.nebula.appcenter.apphandler.H5StartAppInfo;
import com.alipay.mobile.nebula.appcenter.model.AppInfo;
import java.util.Map;

public interface H5TinyAppProvider {
    String getCookie(Bundle bundle, String str);

    String getTemplateAppId(String str, Bundle bundle);

    Bundle handlerAppResume(H5Page h5Page, Bundle bundle);

    void handlerBundleToH5EventForIpc(String str, Bundle bundle, H5Event h5Event, H5Page h5Page);

    void handlerH5EventToBundleForIpc(String str, H5Event h5Event, Bundle bundle);

    void handlerOnAppConfig(Bundle bundle);

    void handlerOnPushWindowParam(Bundle bundle);

    JSONObject handlerOnShareData(H5Page h5Page);

    void handlerOnWorkLog(String str, Object obj);

    void handlerStartParamsReady(Context context, Bundle bundle);

    Bundle handlerStartupParams(H5Page h5Page, Bundle bundle);

    void mergeTemplateConfigIfNeed(String str, Bundle bundle, Map<String, byte[]> map);

    void setCookie(Bundle bundle, String str, String str2);

    void startSyncApp(H5StartAppInfo h5StartAppInfo, AppInfo appInfo, Bundle bundle, String str, H5AppProvider h5AppProvider);
}

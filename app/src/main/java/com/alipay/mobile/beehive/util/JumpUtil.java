package com.alipay.mobile.beehive.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.mobile.beehive.api.BeehiveService;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.MicroApplicationContext;
import com.alipay.mobile.framework.app.MicroApplication;
import com.alipay.mobile.framework.app.ui.BaseActivity;
import com.alipay.mobile.framework.app.ui.BaseFragmentActivity;
import com.alipay.mobile.h5container.api.H5Param;
import com.autonavi.minimap.ajx3.loader.AjxHttpLoader;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class JumpUtil {
    private static BeehiveService mBeehiveService;

    public static void startActivity(Bundle b, Class<?> clazz) {
        innerStartActivity(b, clazz, null, null);
    }

    public static void startActivity(Bundle b, Class<?> targetClass, Context activity) {
        innerStartActivity(b, targetClass, null, activity);
    }

    public static void startActivityForResult(Bundle b, Class<?> clazz, int code) {
        innerStartActivity(b, clazz, Integer.valueOf(code), null);
    }

    public static void startActivityForResult(Bundle b, Class<?> targetClass, int code, Context activity) {
        innerStartActivity(b, targetClass, Integer.valueOf(code), activity);
    }

    private static void innerStartActivity(Bundle b, Class<?> targetClass, Integer code, Context activity) {
        MicroApplication ma;
        MicroApplicationContext ctx = LauncherApplicationAgent.getInstance().getMicroApplicationContext();
        Intent i = new Intent(LauncherApplicationAgent.getInstance().getApplicationContext(), targetClass);
        if (b != null) {
            i.putExtras(b);
        }
        boolean isForResult = code != null;
        if (ctx.findTopRunningApp() == null) {
            if (activity == null) {
                WeakReference ref = ctx.getTopActivity();
                if (!(ref == null || ref.get() == null)) {
                    activity = (Context) ref.get();
                }
            }
            if (activity == null) {
                return;
            }
            if ((activity instanceof BaseActivity) || (activity instanceof BaseFragmentActivity)) {
                if (activity instanceof BaseActivity) {
                    ma = ((BaseActivity) activity).getActivityApplication();
                } else {
                    ma = ((BaseFragmentActivity) activity).getActivityApplication();
                }
                if (ma == null) {
                    return;
                }
                if (isForResult) {
                    ctx.startActivityForResult(ma, i, code.intValue());
                } else {
                    ctx.startActivity(ma, i);
                }
            }
        } else if (isForResult) {
            ctx.startActivityForResult(ctx.findTopRunningApp(), i, code.intValue());
        } else {
            ctx.startActivity(ctx.findTopRunningApp(), i);
        }
    }

    public static void startApp(Bundle b, String appId) {
        LauncherApplicationAgent.getInstance().getMicroApplicationContext().startApp("", appId, b);
    }

    public static void processSchema(String url) {
        if (!TextUtils.isEmpty(url)) {
            if (url.indexOf(AjxHttpLoader.DOMAIN_HTTP) == 0 || url.indexOf(AjxHttpLoader.DOMAIN_HTTPS) == 0) {
                JumpToOutterH5("", url);
                return;
            }
            if (mBeehiveService == null) {
                mBeehiveService = (BeehiveService) MicroServiceUtil.getMicroService(BeehiveService.class);
            }
            if (mBeehiveService == null || mBeehiveService.getSchemaExecutor() == null) {
                try {
                    Bundle b = getParams(Uri.parse(url));
                    String appId = b.getString("appId");
                    if (!TextUtils.isEmpty(appId)) {
                        LauncherApplicationAgent.getInstance().getMicroApplicationContext().startApp("", appId, b);
                    }
                } catch (Exception e) {
                    LoggerFactory.getTraceLogger().warn((String) "JumpUtil", (Throwable) e);
                }
            } else {
                mBeehiveService.getSchemaExecutor().process(Uri.parse(url));
            }
        }
    }

    public static Bundle getParams(Uri uri) {
        Bundle bundle = new Bundle();
        for (String str : getQueryParameterNames(uri)) {
            bundle.putString(str, uri.getQueryParameter(str));
        }
        return bundle;
    }

    public static Set<String> getQueryParameterNames(Uri uri) {
        int end;
        String query = uri.getEncodedQuery();
        if (query == null) {
            return Collections.emptySet();
        }
        Set names = new LinkedHashSet();
        int start = 0;
        do {
            int next = query.indexOf(38, start);
            if (next == -1) {
                end = query.length();
            } else {
                end = next;
            }
            int separator = query.indexOf(61, start);
            if (separator > end || separator == -1) {
                separator = end;
            }
            names.add(Uri.decode(query.substring(start, separator)));
            start = end + 1;
        } while (start < query.length());
        return Collections.unmodifiableSet(names);
    }

    private static void JumpToOutterH5(String curAppId, String url) {
        jumpToH5(curAppId, url, "");
    }

    public static void jumpToH5(String curAppId, String url, String title) {
        Bundle b = new Bundle();
        if (!TextUtils.isEmpty(title)) {
            b.putString(H5Param.LONG_DEFAULT_TITLE, title);
        }
        b.putBoolean(H5Param.LONG_SHOW_TOOLBAR, false);
        b.putBoolean(H5Param.LONG_SHOW_TITLEBAR, true);
        b.putBoolean("longReadTitle", false);
        b.putString("url", url);
        LauncherApplicationAgent.getInstance().getMicroApplicationContext().startApp(curAppId, "20000067", b);
    }
}

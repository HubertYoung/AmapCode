package com.alipay.android.phone.wallet.tinytracker;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.ContextThemeWrapper;
import com.alipay.android.phone.wallet.spmtracker.Constant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum TrackerHelper {
    ;
    
    private final String a;
    private final String b;
    private final String c;
    private Map<String, TrackerParams> d;
    private List<String> e;

    static class TrackerParams {
        public String chInfo;
        public String lanInfo;
        public String pageBack;
        public String pageParams;
        public String srcSem;
        public int tracestep;

        TrackerParams() {
            this.pageBack = "0";
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("chInfo:").append(this.chInfo).append(";");
            stringBuilder.append("srcSem:").append(this.srcSem).append(";");
            stringBuilder.append("lanInfo:").append(this.lanInfo).append(";");
            stringBuilder.append("pageParams:").append(this.pageParams).append(";");
            stringBuilder.append("tracestep:").append(this.tracestep).append(";");
            return stringBuilder.toString();
        }
    }

    private TrackerHelper(String str) {
        this.a = TrackerHelper.class.getSimpleName();
        this.b = "=";
        this.c = "&";
        this.d = new ConcurrentHashMap();
        this.e = new ArrayList<String>() {
            {
                add("a14.b62");
                add("a13.b42");
                add("a18.b64");
                add("a108.b553");
                add("a315.b3675");
                add("a21.b375");
            }
        };
    }

    public final void onPageCreate(Object page, PageInfo pageInfo) {
        a(page);
        checkSrcSpm(page, pageInfo);
    }

    public final void onPageResume(Object page) {
        PageInfo pageInfo = getPageInfoByView(page);
        if (pageInfo != null && TextUtils.isEmpty(pageInfo.srcSpm)) {
            instance.checkSrcSpm(page, pageInfo);
        }
    }

    public final void onPagePause(Object page) {
        b(page);
    }

    public final void onPageDestroy(String pageKey) {
        a(pageKey);
    }

    private void a(Object page) {
        Bundle bundle = null;
        try {
            if (page instanceof Activity) {
                Intent intent = ((Activity) page).getIntent();
                if (intent != null) {
                    bundle = intent.getExtras();
                }
            } else if (page instanceof Fragment) {
                bundle = ((Fragment) page).getArguments();
                if (bundle == null || TextUtils.isEmpty(bundle.getString("chInfo"))) {
                    Activity act = ((Fragment) page).getActivity();
                    if (act != null) {
                        Intent intent2 = act.getIntent();
                        if (intent2 != null) {
                            bundle = intent2.getExtras();
                        }
                    }
                }
            }
            if (bundle != null) {
                bundle.setClassLoader(page.getClass().getClassLoader());
                TrackerParams trackerParams = new TrackerParams();
                trackerParams.chInfo = bundle.getString("chInfo");
                trackerParams.srcSem = bundle.getString(Constant.KEY_SRC_SEM);
                trackerParams.lanInfo = bundle.getString(Constant.KEY_LANINFO);
                this.d.put(SpmUtils.objectToString(page), trackerParams);
            }
        } catch (Exception e2) {
            SpmLogCator.warn(this.a, "parseTrackerParam exception:" + e2.toString());
        }
    }

    /* access modifiers changed from: 0000 */
    public final void checkSrcSpm(Object page, PageInfo pageInfo) {
        if (pageInfo != null) {
            Bundle bundle = null;
            if (page instanceof Activity) {
                Intent intent = ((Activity) page).getIntent();
                if (intent != null) {
                    bundle = intent.getExtras();
                }
            } else if (page instanceof Fragment) {
                bundle = ((Fragment) page).getArguments();
            }
            String srcSpm = "";
            if (bundle != null) {
                try {
                    bundle.setClassLoader(page.getClass().getClassLoader());
                    srcSpm = bundle.getString("srcSpm");
                } catch (Exception e2) {
                    SpmLogCator.warn(this.a, "checkSrcSpm exception:" + e2.toString());
                }
            }
            if (TextUtils.isEmpty(srcSpm)) {
                PageInfo referPageInfo = pageInfo.referPageInfo;
                if (referPageInfo != null) {
                    srcSpm = TextUtils.isEmpty(referPageInfo.lastClickSpm) ? referPageInfo.spm : referPageInfo.lastClickSpm;
                } else {
                    return;
                }
            }
            pageInfo.srcSpm = srcSpm;
        }
    }

    public final void updateSrcSpm(Object page, String srcSpm) {
        PageInfo pageInfo = getPageInfoByView(page);
        if (pageInfo != null) {
            pageInfo.srcSpm = srcSpm;
        }
    }

    @Deprecated
    public final void upateSrcSpm(Object page, String srcSpm) {
        updateSrcSpm(page, srcSpm);
    }

    public final String getSrcSpm(Object page) {
        PageInfo pageInfo = getPageInfoByView(page);
        if (pageInfo != null) {
            return pageInfo.srcSpm == null ? "" : pageInfo.srcSpm;
        }
        return "";
    }

    public final String getReferSpm(Object page) {
        PageInfo pageInfo = getPageInfoByView(page);
        if (pageInfo != null) {
            return pageInfo.referClickSpm == null ? "" : pageInfo.referClickSpm;
        }
        return "";
    }

    public final String getSrcSem(Object page) {
        if (page == null) {
            return "";
        }
        String lastClickSem = getLastClickSem(page);
        if (!TextUtils.isEmpty(lastClickSem)) {
            return lastClickSem;
        }
        SpmLogCator.debug(this.a, "getSrcSem page:" + SpmUtils.objectToString(page));
        TrackerParams trackerParams = this.d.get(SpmUtils.objectToString(page));
        if (trackerParams == null) {
            return "";
        }
        return trackerParams.srcSem == null ? "" : trackerParams.srcSem;
    }

    public final String getLastClickSem(Object page) {
        SpmLogCator.debug(this.a, "getLastClickSemInfo page:" + page);
        PageInfo pageInfo = getPageInfoByView(page);
        if (pageInfo == null) {
            return "";
        }
        SpmLogCator.debug(this.a, "getLastClickSemInfo pageInfo.lastClickSem:" + pageInfo.lastClickSem);
        return pageInfo.lastClickSem == null ? "" : pageInfo.lastClickSem;
    }

    public final String getPageChInfo(Object page) {
        TrackerParams trackerParams = this.d.get(SpmUtils.objectToString(page));
        if (trackerParams == null) {
            return null;
        }
        return trackerParams.chInfo;
    }

    private void b(Object page) {
        TrackerParams trackerParams = this.d.get(SpmUtils.objectToString(page));
        if (trackerParams != null) {
            trackerParams.chInfo = null;
        }
    }

    private void a(String pageKey) {
        if (this.d.containsKey(pageKey)) {
            this.d.remove(pageKey);
        }
    }

    public final String getLastClickSpmIdByPage(Object page) {
        PageInfo pageInfo = getPageInfoByView(page);
        if (pageInfo != null) {
            return pageInfo.lastClickSpm;
        }
        return "";
    }

    public final TrackerParams getTrackerParams(Object page) {
        return this.d.get(SpmUtils.objectToString(page));
    }

    public final Map<String, String> getTracerInfo(Object page) {
        Map tracerInfo = new HashMap();
        PageInfo pageInfo = getPageInfoByView(page);
        if (pageInfo != null) {
            tracerInfo.put("pagets", getMiniPageId(page));
            tracerInfo.put("srcSpm", pageInfo.srcSpm == null ? "" : pageInfo.srcSpm);
        }
        tracerInfo.put("lastClickSpm", getLastClickSpmId());
        return tracerInfo;
    }

    @TargetApi(17)
    @Deprecated
    public final String getMiniPageId(Object page) {
        PageInfo pageInfo = getPageInfoByView(page);
        if (pageInfo != null) {
            return pageInfo.miniPageId != null ? pageInfo.miniPageId : "C_NULL";
        }
        return "C_NULL";
    }

    public final String getLastClickSpmId() {
        String spmId = TinyTrackIntegrator.getInstance().getLastClickViewSpm();
        if (TextUtils.isEmpty(spmId)) {
            return "";
        }
        return spmId;
    }

    public final String getPageId(Object page) {
        PageInfo pageInfo = getPageInfoByView(page);
        if (pageInfo == null) {
            return "C_NULL";
        }
        SpmLogCator.debug(this.a, "getPageId pageId:" + pageInfo.pageId);
        return pageInfo.pageId == null ? "C_NULL" : pageInfo.pageId;
    }

    public final String getPageSpm(Object page) {
        PageInfo pageInfo = getPageInfoByView(page);
        if (pageInfo != null) {
            return pageInfo.spm;
        }
        return "";
    }

    public final boolean isPageStarted(Object page) {
        PageInfo pageInfo = getPageInfoByView(page);
        return pageInfo != null && !pageInfo.isEnd;
    }

    /* access modifiers changed from: 0000 */
    public final boolean checkIsPageBack(Object page) {
        PageInfo pageInfo = TinyTrackIntegrator.getInstance().getPageInfoByView(page);
        PageInfo topPageInfo = TinyTrackIntegrator.getInstance().getPageMonitorCurrentPageInfo();
        TrackerParams params = getTrackerParams(page);
        if (params == null) {
            params = new TrackerParams();
            this.d.put(SpmUtils.objectToString(page), params);
        }
        if (topPageInfo == null || pageInfo == null || topPageInfo.referPageInfo == null || !topPageInfo.referPageInfo.pageId.equals(pageInfo.pageId)) {
            params.pageBack = "0";
            return false;
        }
        params.pageBack = "1";
        return true;
    }

    /* access modifiers changed from: 0000 */
    public final boolean checkIsMultistepBack(Object page) {
        PageInfo pageInfo = TinyTrackIntegrator.getInstance().getPageInfoByView(page);
        if (TinyTrackIntegrator.getInstance().getPageMonitorCurrentPageInfo() == null || pageInfo == null) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: 0000 */
    public final void setHomePageTabSpms(List<String> list) {
        if (list != null) {
            this.e = list;
        }
    }

    /* access modifiers changed from: 0000 */
    public final PageInfo getPageInfoByView(Object page) {
        return TinyTrackIntegrator.getInstance().getPageInfoByView(c(page));
    }

    private static Object c(Object page) {
        if ((page instanceof Activity) || !(page instanceof ContextThemeWrapper) || TinyTrackIntegrator.getInstance().getPageInfoByView(page) != null) {
            return page;
        }
        return ((ContextThemeWrapper) page).getBaseContext();
    }
}

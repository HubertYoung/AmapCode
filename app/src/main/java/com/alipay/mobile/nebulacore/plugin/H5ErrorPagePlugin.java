package com.alipay.mobile.nebulacore.plugin;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Rect;
import android.os.Bundle;
import com.alibaba.fastjson.JSON;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.filecache.FileCache;
import com.alipay.mobile.nebula.util.H5FileUtil;
import com.alipay.mobile.nebula.util.H5ImageUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.webview.WebViewType;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.dev.trace.H5PerformanceUtils;

public class H5ErrorPagePlugin extends H5SimplePlugin {

    public class FeedbackReportData {
        public String bizCode;
        public String bizMsg;
        public String bizName;
        public String bizUrl;
        public String viewName;
        public String webViewVersion;

        public FeedbackReportData() {
        }
    }

    public void onPrepare(H5EventFilter filter) {
        filter.addAction("startFeedBack");
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        if (!"startFeedBack".equals(event.getAction())) {
            return false;
        }
        if (event.getH5page() == null || event.getH5page().getPageData() == null) {
            context.sendError(Error.UNKNOWN_ERROR.ordinal(), (String) "调用失败");
            return true;
        } else if (event.getH5page().getPageData().isShowErrorPage()) {
            a();
            return true;
        } else {
            context.sendNoRigHtToInvoke();
            return true;
        }
    }

    private void a() {
        H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
            public void run() {
                Bitmap snapshot;
                H5Page h5Page = null;
                try {
                    if (!(Nebula.getService() == null || Nebula.getService().getTopSession() == null || Nebula.getService().getTopSession().getTopPage() == null)) {
                        h5Page = Nebula.getService().getTopSession().getTopPage();
                    }
                    if (h5Page != null && h5Page.getPageData() != null) {
                        if (h5Page.getWebView() == null || h5Page.getWebView().getType() != WebViewType.THIRD_PARTY) {
                            snapshot = H5PerformanceUtils.takeScreenShot(h5Page);
                        } else {
                            int widths = h5Page.getWebView().getView().getMeasuredWidth();
                            int heights = h5Page.getWebView().getView().getMeasuredHeight();
                            snapshot = Bitmap.createBitmap(widths, heights, Config.ARGB_8888);
                            h5Page.getWebView().getCurrentPageSnapshot(new Rect(0, 0, widths, heights), new Rect(0, 0, widths, heights), snapshot, false, 0);
                        }
                        String filePath = "";
                        if (snapshot != null) {
                            filePath = new FileCache(h5Page.getContext().getContext(), h5Page.getPageData().getAppId()).getTempPath(h5Page.getContext().getContext(), String.valueOf(System.currentTimeMillis()), "png");
                            if (H5FileUtil.create(filePath, true)) {
                                H5ImageUtil.writeImage(snapshot, CompressFormat.PNG, filePath);
                            } else {
                                filePath = "";
                            }
                        }
                        String reportJsonStr = JSON.toJSONString(H5ErrorPagePlugin.this.a(h5Page));
                        Bundle params = new Bundle();
                        params.putString("feedBackBizId", h5Page.getPageData().getAppId());
                        params.putString("feedBackImage", filePath);
                        params.putString("feedBackInfo", reportJsonStr);
                        LauncherApplicationAgent.getInstance().getMicroApplicationContext().startApp(h5Page.getPageData().getAppId(), "20000049", params);
                    }
                } catch (Exception e) {
                    H5Log.e((String) "H5ErrorPagePlugin", (Throwable) e);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public FeedbackReportData a(H5Page h5Page) {
        FeedbackReportData reportData = new FeedbackReportData();
        if (!(h5Page == null || h5Page.getPageData() == null)) {
            H5PageData pageData = h5Page.getPageData();
            reportData.bizUrl = pageData.getPageUrl();
            reportData.bizCode = String.valueOf(pageData.getStatusCode());
            reportData.bizMsg = pageData.getErrorMsg();
            reportData.bizName = "nebula_errorpage";
            if (h5Page.getWebView() != null) {
                reportData.webViewVersion = h5Page.getWebView().getVersion();
            }
        }
        return reportData;
    }
}

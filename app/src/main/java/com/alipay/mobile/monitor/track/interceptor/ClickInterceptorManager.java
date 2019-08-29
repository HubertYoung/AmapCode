package com.alipay.mobile.monitor.track.interceptor;

import android.view.View;
import android.widget.AdapterView;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.monitor.track.AutoTracker;
import com.alipay.mobile.monitor.track.AutoTrackerHolder;
import com.alipay.mobile.monitor.track.TrackIntegrator;
import com.alipay.mobile.monitor.track.agent.DefaultTrackAgent;
import com.alipay.mobile.monitor.track.xpath.XPathFinder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClickInterceptorManager {
    public static final String DEF_CONTROLID = "cellAction";
    private List<ClickInterceptor> a = new CopyOnWriteArrayList();
    private AutoClickInterceptor b = new AutoClickInterceptor();

    public void addClickInterceptor(ClickInterceptor interceptor) {
        this.a.remove(interceptor);
        this.a.add(interceptor);
    }

    public boolean handleOnClick(View view, String pageId, String appId) {
        a(view, pageId, appId, false);
        for (ClickInterceptor clickInterceptor : this.a) {
            if (clickInterceptor.onClick(view)) {
                return true;
            }
        }
        return false;
    }

    public void handleOnClickAfter(View view) {
        this.b.trackWindowManagerView(view);
    }

    public boolean handleOnItemClick(AdapterView<?> parent, View view, int position, long id, String pageId, String appId) {
        a(view, pageId, appId, true);
        for (ClickInterceptor clickInterceptor : this.a) {
            if (clickInterceptor.onItemClick(parent, view, position, id)) {
                return true;
            }
        }
        return false;
    }

    private void a(View view, String pageId, String appId, boolean isAdapterView) {
        if (view != null) {
            String xPath = XPathFinder.a(view);
            LoggerFactory.getTraceLogger().info(TrackIntegrator.TAG, "xpath = " + xPath);
            Map params = new HashMap();
            params.put("pageId", pageId);
            params.put("appId", appId);
            if (isAdapterView) {
                params.put(DefaultTrackAgent.PARAM_CONTROLID, DEF_CONTROLID);
            }
            AutoTrackerHolder holder = AutoTracker.getImpl().getAutoTrackerHolder();
            if (holder != null && holder.getTrackAgent() != null) {
                holder.getTrackAgent().onViewClick(view, isAdapterView, xPath, params);
            }
        }
    }

    public void handleOnItemClickAfter(AdapterView<?> parent, View view, int position, long id) {
        this.b.trackWindowManagerView(view);
    }
}

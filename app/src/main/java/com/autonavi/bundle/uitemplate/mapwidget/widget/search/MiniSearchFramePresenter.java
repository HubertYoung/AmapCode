package com.autonavi.bundle.uitemplate.mapwidget.widget.search;

import android.text.TextUtils;
import android.view.View;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.uitemplate.mapwidget.widget.BaseMapWidgetPresenter;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.autosec.UTAnalyticsUtils;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class MiniSearchFramePresenter extends BaseMapWidgetPresenter<MiniSearchFrameWidget> {
    public void internalClickListener(View view) {
        startSearchHomePage(this.mBindWidget == null ? "" : ((MiniSearchFrameWidget) this.mBindWidget).getHotText());
    }

    private void startSearchHomePage(String str) {
        a.k();
        JSONObject logVersionStateParam = getLogVersionStateParam();
        String str2 = "0";
        if (!TextUtils.isEmpty(str)) {
            if (str.contains(AMapAppGlobal.getApplication().getString(R.string.search_indoor_end))) {
                str2 = "1";
            }
        }
        try {
            logVersionStateParam.put("isIndoor", str2);
            if (((MiniSearchFrameWidget) this.mBindWidget).getWidgetProperty().isLoadNewWidgetStyle()) {
                logVersionStateParam.put("type", "mini搜框");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String valueOf = String.valueOf(System.currentTimeMillis());
        logSearchClickForUT(valueOf);
        try {
            logVersionStateParam.put("timestamp", valueOf);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        LogManager.actionLogV2("P00001", "B002", logVersionStateParam);
        HashMap hashMap = new HashMap();
        if (((MiniSearchFrameWidget) this.mBindWidget).getWidgetProperty().isLoadNewWidgetStyle()) {
            hashMap.put("type", "mini搜框");
        }
        kd.a((String) "amap.P00001.0.B002", (Map<String, String>) hashMap);
        PageBundle pageBundle = new PageBundle();
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            pageContext.startPage((String) "amap.search.action.searchfragment", pageBundle);
        }
    }

    private void logSearchClickForUT(String str) {
        HashMap hashMap = new HashMap();
        hashMap.put("name", "SearchEdit");
        hashMap.put("timestamp", str);
        UTAnalyticsUtils.getInstance().userDefinedTracker("DefaultPage", hashMap);
    }

    public void updateText(CharSequence charSequence, int i) {
        if (isWidgetNotNull()) {
            ((MiniSearchFrameWidget) this.mBindWidget).setTextAndColor(charSequence, i);
        }
    }
}

package com.alibaba.sdk.want.widget;

import android.app.Activity;
import android.text.TextUtils;
import android.view.ViewGroup;
import com.alibaba.sdk.trade.component.cart.AlibcTkParams;
import com.alibaba.sdk.trade.container.utils.AlibcComponentLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class WantWidgetFactory {
    public static final String ADDWANTWIDGET_TYPE = "add_cart";
    private final String TAG = "WantWidgetFactory";
    private Activity mContext;
    private ViewGroup mRootView;
    private ArrayList<IWantWidget> mWidgetList;

    public WantWidgetFactory(Activity activity, ViewGroup viewGroup) {
        this.mContext = activity;
        this.mRootView = viewGroup;
        this.mWidgetList = new ArrayList<>();
    }

    public void updateData(WantBaseData wantBaseData, HashMap<String, String> hashMap, AlibcTkParams alibcTkParams) {
        IWantWidget widget = getWidget(wantBaseData.getType());
        if (widget != null) {
            widget.updateData(wantBaseData, hashMap, alibcTkParams);
        }
    }

    public void removeAllData() {
        if (this.mWidgetList != null) {
            Iterator<IWantWidget> it = this.mWidgetList.iterator();
            while (it.hasNext()) {
                IWantWidget next = it.next();
                if (next != null) {
                    next.removeData();
                }
            }
        }
    }

    private IWantWidget findWidget(String str) {
        IWantWidget iWantWidget = null;
        if (this.mWidgetList == null) {
            return null;
        }
        Iterator<IWantWidget> it = this.mWidgetList.iterator();
        while (it.hasNext()) {
            IWantWidget next = it.next();
            if (TextUtils.equals(str, next.getType())) {
                iWantWidget = next;
            }
        }
        return iWantWidget;
    }

    private IWantWidget createWidget(String str) {
        if (TextUtils.equals(str, ADDWANTWIDGET_TYPE)) {
            return new AddCartWidget(this.mContext, this.mRootView);
        }
        return null;
    }

    private IWantWidget getWidget(String str) {
        if (str == null) {
            return null;
        }
        IWantWidget findWidget = findWidget(str);
        if (findWidget == null) {
            findWidget = createWidget(str);
        }
        if (this.mWidgetList == null) {
            this.mWidgetList = new ArrayList<>();
        }
        if (findWidget != null) {
            this.mWidgetList.add(findWidget);
        }
        return findWidget;
    }

    public void destroy() {
        AlibcComponentLog.d("WantWidgetFactory", "destroy");
        if (this.mWidgetList != null) {
            Iterator<IWantWidget> it = this.mWidgetList.iterator();
            while (it.hasNext()) {
                IWantWidget next = it.next();
                if (next != null) {
                    next.destroy();
                }
            }
            this.mWidgetList = null;
            this.mContext = null;
        }
    }
}

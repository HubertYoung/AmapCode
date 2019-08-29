package com.autonavi.minimap.ajx3.modules;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageFramework;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.widget.ui.LoadingViewBL;

@AjxModule("amap_loading")
public class ModuleAmapLoading extends AbstractModule {
    public static final String MODULE_NAME = "amap_loading";
    private LoadingViewBL mLoadingView;

    static class LoadingCancelListener implements OnClickListener {
        private final JsFunctionCallback mJsRef;

        LoadingCancelListener(JsFunctionCallback jsFunctionCallback) {
            this.mJsRef = jsFunctionCallback;
        }

        public void onClick(View view) {
            if (this.mJsRef != null) {
                this.mJsRef.callback(new Object[0]);
            }
        }
    }

    public ModuleAmapLoading(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("show")
    public void show(String str, JsFunctionCallback jsFunctionCallback) {
        bid pageContext = AMapPageFramework.getPageContext();
        if (pageContext != null) {
            if (this.mLoadingView == null || !pageContext.isViewLayerShowing(this.mLoadingView)) {
                if (this.mLoadingView == null) {
                    this.mLoadingView = new LoadingViewBL(getNativeContext(), 3);
                }
                if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str.trim())) {
                    this.mLoadingView.setLoadingText(str.trim());
                }
                if (jsFunctionCallback != null) {
                    this.mLoadingView.setOnCloseClickListener(new LoadingCancelListener(jsFunctionCallback));
                }
                pageContext.showViewLayer(this.mLoadingView);
            }
        }
    }

    @AjxMethod("dismiss")
    public void dismiss() {
        bid pageContext = AMapPageFramework.getPageContext();
        if (!(pageContext == null || this.mLoadingView == null)) {
            pageContext.dismissViewLayer(this.mLoadingView);
            this.mLoadingView.setOnClickListener(null);
        }
    }
}

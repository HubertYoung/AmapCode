package com.autonavi.widget.pulltorefresh;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import com.autonavi.minimap.R;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.AnimationStyle;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.Orientation;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.c;

public class PullToRefreshWebView extends PullToRefreshBase<WebView> {
    private static final c<WebView> defaultOnRefreshListener = new c<WebView>() {
        public final void a(PullToRefreshBase<WebView> pullToRefreshBase) {
            ((WebView) pullToRefreshBase.getRefreshableView()).reload();
        }
    };
    private final WebChromeClient defaultWebChromeClient = new WebChromeClient() {
        public void onProgressChanged(WebView webView, int i) {
            if (i == 100) {
                PullToRefreshWebView.this.onRefreshComplete();
            }
        }
    };

    @TargetApi(9)
    final class InternalWebViewSDK9 extends WebView {
        static final int OVERSCROLL_FUZZY_THRESHOLD = 2;
        static final float OVERSCROLL_SCALE_FACTOR = 1.5f;

        public InternalWebViewSDK9(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        /* access modifiers changed from: protected */
        public final boolean overScrollBy(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, boolean z) {
            boolean overScrollBy = super.overScrollBy(i, i2, i3, i4, i5, i6, i7, i8, z);
            eri.a(PullToRefreshWebView.this, i, i3, i2, i4, getScrollRange(), 2, OVERSCROLL_SCALE_FACTOR, z);
            return overScrollBy;
        }

        private int getScrollRange() {
            return (int) Math.max(0.0d, Math.floor((double) (((float) ((WebView) PullToRefreshWebView.this.mRefreshableView).getContentHeight()) * ((WebView) PullToRefreshWebView.this.mRefreshableView).getScale())) - ((double) ((getHeight() - getPaddingBottom()) - getPaddingTop())));
        }
    }

    public PullToRefreshWebView(Context context) {
        super(context);
        setOnRefreshListener(defaultOnRefreshListener);
        ((WebView) this.mRefreshableView).setWebChromeClient(this.defaultWebChromeClient);
    }

    public PullToRefreshWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setOnRefreshListener(defaultOnRefreshListener);
        ((WebView) this.mRefreshableView).setWebChromeClient(this.defaultWebChromeClient);
    }

    public PullToRefreshWebView(Context context, Mode mode) {
        super(context, mode);
        setOnRefreshListener(defaultOnRefreshListener);
        ((WebView) this.mRefreshableView).setWebChromeClient(this.defaultWebChromeClient);
    }

    public PullToRefreshWebView(Context context, Mode mode, AnimationStyle animationStyle) {
        super(context, mode, animationStyle);
        setOnRefreshListener(defaultOnRefreshListener);
        ((WebView) this.mRefreshableView).setWebChromeClient(this.defaultWebChromeClient);
    }

    public final Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    /* access modifiers changed from: protected */
    public WebView createRefreshableView(Context context, AttributeSet attributeSet) {
        WebView webView;
        if (VERSION.SDK_INT >= 9) {
            webView = new InternalWebViewSDK9(context, attributeSet);
        } else {
            webView = new WebView(context, attributeSet);
        }
        webView.setId(R.id.webview);
        return webView;
    }

    /* access modifiers changed from: protected */
    public boolean isReadyForPullStart() {
        return ((WebView) this.mRefreshableView).getScrollY() == 0;
    }

    /* access modifiers changed from: protected */
    public boolean isReadyForPullEnd() {
        return ((float) ((WebView) this.mRefreshableView).getScrollY()) >= ((float) Math.floor((double) (((float) ((WebView) this.mRefreshableView).getContentHeight()) * ((WebView) this.mRefreshableView).getScale()))) - ((float) ((WebView) this.mRefreshableView).getHeight());
    }

    /* access modifiers changed from: protected */
    public void onPtrRestoreInstanceState(Bundle bundle) {
        super.onPtrRestoreInstanceState(bundle);
        ((WebView) this.mRefreshableView).restoreState(bundle);
    }

    /* access modifiers changed from: protected */
    public void onPtrSaveInstanceState(Bundle bundle) {
        super.onPtrSaveInstanceState(bundle);
        ((WebView) this.mRefreshableView).saveState(bundle);
    }
}

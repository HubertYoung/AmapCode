package com.alipay.mobile.nebulacore.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout.LayoutParams;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.view.H5LoadingView;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.core.H5PageImpl;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.alipay.mobile.nebulacore.tabbar.H5PageTabBar;
import com.alipay.mobile.nebulacore.view.H5FontBar;
import com.alipay.mobile.nebulacore.view.H5NavigationBar;

public class H5ViewHolder {
    public static final String TAG = "H5ViewHolder";
    private ViewGroup a;
    private H5NavigationBar b;
    private H5PageTabBar c;
    private H5FontBar d;
    private H5WebContent e;
    private H5TransWebContent f;
    private View g;
    private H5PageImpl h;

    public H5NavigationBar getH5NavBar() {
        return this.b;
    }

    public void setH5NavBar(H5NavigationBar h5NavBar) {
        this.b = h5NavBar;
    }

    public H5PageTabBar getH5Tabbar() {
        return this.c;
    }

    public void setH5Tabbar(H5PageTabBar h5Tabbar) {
        this.c = h5Tabbar;
    }

    public H5FontBar getH5FontBar() {
        return this.d;
    }

    public void setH5FontBar(H5FontBar h5FontBar) {
        this.d = h5FontBar;
    }

    public H5WebContent getH5WebContainer() {
        return this.e;
    }

    public void setH5WebContainer(H5WebContent h5WebContainer) {
        this.e = h5WebContainer;
    }

    public H5LoadingView getH5LoadingView() {
        if (getH5WebContainer() != null) {
            return getH5WebContainer().getH5LoadingView();
        }
        return null;
    }

    public H5TransWebContent getH5TransWebContainer() {
        return this.f;
    }

    public void setH5TransWebContainer(H5TransWebContent h5TransWebContainer) {
        this.f = h5TransWebContainer;
    }

    public View getH5WebContent() {
        return this.g;
    }

    public void setH5WebContent(View h5WebContent) {
        this.g = h5WebContent;
    }

    public H5PageImpl getH5Page() {
        return this.h;
    }

    public void setH5Page(H5PageImpl h5Page) {
        this.h = h5Page;
    }

    public ViewGroup getRootView() {
        return this.a;
    }

    public void setRootView(ViewGroup rootView) {
        this.a = rootView;
    }

    public void refreshView() {
        if (this.a == null) {
            H5Log.e((String) TAG, (String) "root contentView is null");
            return;
        }
        Bundle startParams = this.h.getParams();
        boolean transActivityFlag = false;
        if (H5Utils.getBoolean(startParams, (String) H5Param.LONG_TRANSPARENT, false)) {
            transActivityFlag = true;
        }
        H5Log.d(TAG, "transActivityFlag " + transActivityFlag);
        try {
            this.a.removeAllViews();
        } catch (Throwable throwable) {
            H5Log.e((String) TAG, throwable);
        }
        if (!transActivityFlag) {
            if (!(getH5NavBar() == null || getH5NavBar().getContent() == null)) {
                H5Log.d(TAG, "add nav bar");
                LayoutParams lp = new LayoutParams(-1, -2);
                lp.addRule(10);
                this.a.addView(getH5NavBar().getContent(), 0, lp);
            }
            if (!(getH5Tabbar() == null || getH5Tabbar().getContent() == null)) {
                H5Log.d(TAG, "add tab bar");
                LayoutParams lpBottom = new LayoutParams(-1, (int) H5Environment.getResources().getDimension(R.dimen.h5_bottom_height_tab));
                lpBottom.addRule(12);
                this.a.addView(getH5Tabbar().getContent(), this.a.getChildCount(), lpBottom);
            }
            if (getH5WebContainer() != null && getH5WebContainer().getContent() != null) {
                View h5WebContent = getH5WebContainer().getContent();
                LayoutParams lpAll = new LayoutParams(-1, -1);
                boolean transTitle = false;
                if (startParams != null && startParams.containsKey(H5Param.LONG_TRANSPARENT_TITLE)) {
                    String transparentTitle = H5Utils.getString(startParams, (String) H5Param.LONG_TRANSPARENT_TITLE);
                    if (!TextUtils.isEmpty(transparentTitle)) {
                        transTitle = TextUtils.equals("always", transparentTitle) || TextUtils.equals("auto", transparentTitle);
                        if (TextUtils.equals("custom", transparentTitle) && startParams.containsKey(H5Param.LONG_BACKBTN_IMAGE) && startParams.containsKey(H5Param.LONG_BACKBTN_TEXTCOLOR) && startParams.containsKey(H5Param.LONG_TITLE_COLOR)) {
                            String bbi = H5Utils.getString(startParams, (String) H5Param.LONG_BACKBTN_IMAGE);
                            transTitle = ((TextUtils.isEmpty(bbi) || TextUtils.equals(bbi, "default")) && H5Utils.getInt(startParams, (String) H5Param.LONG_BACKBTN_TEXTCOLOR) == -16777216 && H5Utils.getInt(startParams, (String) H5Param.LONG_TITLE_COLOR) == -16777216) ? false : true;
                        }
                    }
                }
                if (transTitle) {
                    H5Log.d(TAG, "transTitle:" + transTitle);
                    lpAll.addRule(6);
                    if (getH5WebContainer() != null) {
                        getH5WebContainer().setUrlProviderMargin((int) H5Environment.getResources().getDimension(R.dimen.h5_title_height));
                    }
                } else if (!(getH5NavBar() == null || getH5NavBar().getContent() == null)) {
                    lpAll.addRule(3, getH5NavBar().getContent().getId());
                    if (getH5WebContainer() != null) {
                        getH5WebContainer().setUrlProviderMargin(0);
                    }
                }
                if (!(getH5Tabbar() == null || getH5Tabbar().getContent() == null)) {
                    lpAll.addRule(2, getH5Tabbar().getContent().getId());
                }
                try {
                    this.a.addView(h5WebContent, 0, lpAll);
                } catch (Throwable throwable2) {
                    H5Log.e((String) TAG, throwable2);
                }
                if (Nebula.isDelayRender(startParams)) {
                    a(this.a);
                }
                if (this.h != null) {
                    this.h.checkIfShowLoadingView();
                }
            }
        } else if (getH5TransWebContainer() != null && getH5TransWebContainer().getContent() != null) {
            this.a.addView(getH5TransWebContainer().getContent(), 0, new LayoutParams(-1, -1));
        }
    }

    private void a(final View rootView) {
        AlphaAnimation transparent = new AlphaAnimation(0.0f, 0.0f);
        transparent.setDuration(300);
        rootView.startAnimation(transparent);
        rootView.postDelayed(new Runnable() {
            public void run() {
                AlphaAnimation opacity = new AlphaAnimation(0.0f, 1.0f);
                opacity.setDuration(200);
                rootView.startAnimation(opacity);
            }
        }, 300);
    }
}

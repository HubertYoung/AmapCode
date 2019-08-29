package com.alipay.mobile.tinyappcommon.dynamicpanel;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.alipay.mobile.antui.iconfont.manager.TypefaceCache;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.util.H5DimensionUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.view.H5TitleView;
import com.alipay.mobile.tinyappcommon.dynamicpanel.H5TinyPopMenu.TitleBarTheme;
import com.alipay.mobile.tinyappcommon.h5plugin.TinyAppMiniServicePlugin;
import com.alipay.mobile.tinyappcommon.storage.TinyAppStorage;
import com.autonavi.minimap.ajx3.util.Constants;

public class HomeBackControl {
    public static final String LEFT_TITLEBAR_SHOW = "leftTitlebarShow";
    public static final String REDIRECT_FROM_HOMEPAGE = "redirectFromHomepage";
    /* access modifiers changed from: private */
    public String TAG = HomeBackControl.class.getSimpleName();
    private View backToHomeContainer;
    private boolean isShowBackHome = false;
    private String mAppId = "";
    private TextView mBackToHomeTitleBar;
    private Context mContext = null;
    /* access modifiers changed from: private */
    public H5Page mH5Page = null;
    private Typeface mTinyAppIconFont;

    public void setH5Page(H5Page h5Page) {
        this.mH5Page = h5Page;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public void setAppId(String appId) {
        this.mAppId = appId;
    }

    public void shouldShowBackHomeState(TitleBarTheme titleBarTheme) {
        this.isShowBackHome = true;
        controlTitleBarBackToHomeRunUIThread(titleBarTheme);
    }

    public void shouldShowBackHomeLessState(TitleBarTheme titleBarTheme) {
        this.isShowBackHome = false;
        controlTitleBarBackToHomeRunUIThread(titleBarTheme);
    }

    private void controlTitleBarBackToHomeRunUIThread(final TitleBarTheme titleBarTheme) {
        if (H5Utils.isMain()) {
            try {
                controlTitleBarBackToHome(titleBarTheme);
            } catch (Throwable e) {
                H5Log.e(this.TAG, e);
            }
        } else {
            H5Utils.runOnMain(new Runnable() {
                public void run() {
                    try {
                        HomeBackControl.this.controlTitleBarBackToHome(titleBarTheme);
                    } catch (Throwable e) {
                        H5Log.e(HomeBackControl.this.TAG, e);
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void controlTitleBarBackToHome(TitleBarTheme titleBarTheme) {
        View backContainer = null;
        if (this.mH5Page != null) {
            String leftTitlebarShow = H5Utils.getString(this.mH5Page.getParams(), (String) LEFT_TITLEBAR_SHOW);
            H5Log.debug(this.TAG, "leftTitlebarShow " + leftTitlebarShow);
            H5TitleView titleView = this.mH5Page.getH5TitleBar();
            if (titleView != null) {
                View contentView = titleView.getContentView();
                if (contentView != null) {
                    backContainer = contentView.findViewById(R.id.h5_nav_back);
                }
            }
            if (H5Param.DEFAULT_LONG_BACK_BEHAVIOR.equalsIgnoreCase(leftTitlebarShow) && backContainer != null) {
                backContainer.setVisibility(0);
                setTitleMarginLeft(0);
                hideBackToHomeTitleBar();
                return;
            } else if (Constants.ANIMATOR_NONE.equalsIgnoreCase(leftTitlebarShow) && backContainer != null) {
                backContainer.setVisibility(8);
                hideBackToHomeTitleBar();
                return;
            }
        }
        if (this.mH5Page == null || !TinyAppMiniServicePlugin.appIsMiniService(this.mH5Page)) {
            if (this.mH5Page != null) {
                Object backToHome = this.mH5Page.getExtra(H5TinyPopMenu.SHOW_BACK_TO_HOME_TITLE_BAR);
                H5Log.d(this.TAG, "backToHome: " + backToHome);
                if (backToHome instanceof Boolean) {
                    if (!((Boolean) backToHome).booleanValue()) {
                        hideBackToHomeTitleBar();
                        return;
                    }
                    this.isShowBackHome = true;
                }
            }
            if (!TinyAppStorage.getInstance().getShouldShowBackToHomeInTitleBar(this.mAppId)) {
                H5Log.d(this.TAG, this.mAppId + "shouldn't show back to home in title bar.");
                hideBackToHomeTitleBar();
            } else if (this.mH5Page != null && this.mBackToHomeTitleBar != null && this.backToHomeContainer != null) {
                H5TitleView titleView2 = this.mH5Page.getH5TitleBar();
                if (titleView2 != null && titleView2.getContentView() != null) {
                    View contentView2 = titleView2.getContentView();
                    View backContainer2 = contentView2.findViewById(R.id.h5_nav_back);
                    View btClose = contentView2.findViewById(R.id.h5_nav_close);
                    boolean redirectFromHomepage = H5Utils.getBoolean(this.mH5Page.getParams(), (String) REDIRECT_FROM_HOMEPAGE, false);
                    if (backContainer2 == null || backContainer2.getVisibility() == 0 || btClose == null || btClose.getVisibility() == 0) {
                        if (this.backToHomeContainer != null) {
                            this.backToHomeContainer.setVisibility(8);
                        }
                    } else if (this.backToHomeContainer.getVisibility() == 0) {
                        H5Log.d(this.TAG, "backToHome container is visible, return.");
                    } else {
                        try {
                            if (this.mTinyAppIconFont == null && this.mContext != null) {
                                this.mTinyAppIconFont = TypefaceCache.getTypeface(this.mContext, "tinyfont", "tinyfont/iconfont.ttf");
                                if (this.mTinyAppIconFont != null) {
                                    this.mBackToHomeTitleBar.setTypeface(this.mTinyAppIconFont);
                                }
                            }
                        } catch (Throwable th) {
                            H5Log.e(this.TAG, (String) "iconfont failed");
                        }
                        this.mBackToHomeTitleBar.setTextColor(Color.rgb(30, 144, 230));
                        if (titleBarTheme == TitleBarTheme.TITLE_BAR_THEME_WHITE) {
                            this.mBackToHomeTitleBar.setTextColor(-1);
                        } else if (titleBarTheme == TitleBarTheme.TITLE_BAR_THEME_BLUE) {
                            this.mBackToHomeTitleBar.setTextColor(Color.rgb(30, 144, 230));
                        }
                        if (this.mContext != null) {
                            Resources resources = this.mContext.getResources();
                            if (resources != null) {
                                this.backToHomeContainer.setContentDescription(resources.getText(com.alipay.mobile.tinyapp.R.string.tiny_back_to_home));
                            }
                        }
                        H5Log.d(this.TAG, "redirectFromHomepage: " + redirectFromHomepage);
                        if (redirectFromHomepage || !this.isShowBackHome) {
                            this.backToHomeContainer.setVisibility(8);
                            return;
                        }
                        H5Log.d(this.TAG, "set visibility to visible.");
                        this.backToHomeContainer.setVisibility(0);
                        setTitleMarginLeft(0);
                        this.backToHomeContainer.setOnClickListener(new OnClickListener() {
                            public void onClick(View v) {
                                if (HomeBackControl.this.mH5Page != null && HomeBackControl.this.mH5Page.getBridge() != null) {
                                    HomeBackControl.this.mH5Page.getBridge().sendDataWarpToWeb(H5TinyPopMenu.BACK_TO_HOME_ACTION, null, null);
                                }
                            }
                        });
                    }
                }
            }
        } else {
            H5Log.d(this.TAG, "app is mini service, hide.");
            hideBackToHomeTitleBar();
        }
    }

    private void hideBackToHomeTitleBar() {
        if (this.backToHomeContainer != null && this.mH5Page != null && this.backToHomeContainer.getVisibility() == 0) {
            H5Log.d(this.TAG, "set visibility to gone.");
            this.backToHomeContainer.setVisibility(8);
            setTitleMarginLeft(16);
        }
    }

    private void setTitleMarginLeft(int marginLeft) {
        if (this.mH5Page != null) {
            H5TitleView titleView = this.mH5Page.getH5TitleBar();
            if (titleView != null) {
                View contentView = titleView.getContentView();
                if (contentView != null) {
                    RelativeLayout rlTitle = (RelativeLayout) contentView.findViewById(R.id.h5_rl_title);
                    if (rlTitle != null) {
                        LayoutParams rlTitleLayoutParams = (LayoutParams) rlTitle.getLayoutParams();
                        if (rlTitleLayoutParams != null && this.mContext != null) {
                            rlTitleLayoutParams.setMargins(H5DimensionUtil.dip2px(this.mContext, (float) marginLeft), 0, 0, 0);
                        }
                    }
                }
            }
        }
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void showBackToHomeIcon(com.alipay.mobile.tinyappcommon.dynamicpanel.H5TinyPopMenu.TitleBarTheme r7) {
        /*
            r6 = this;
            com.alipay.mobile.h5container.api.H5Page r3 = r6.mH5Page     // Catch:{ Throwable -> 0x006c }
            if (r3 == 0) goto L_0x0062
            android.content.Context r3 = r6.mContext     // Catch:{ Throwable -> 0x006c }
            if (r3 == 0) goto L_0x0062
            com.alipay.mobile.h5container.api.H5Page r3 = r6.mH5Page     // Catch:{ Throwable -> 0x006c }
            com.alipay.mobile.nebula.view.H5TitleView r2 = r3.getH5TitleBar()     // Catch:{ Throwable -> 0x006c }
            if (r2 == 0) goto L_0x0062
            android.view.View r3 = r2.getContentView()     // Catch:{ Throwable -> 0x006c }
            if (r3 == 0) goto L_0x0062
            android.view.View r3 = r2.getContentView()     // Catch:{ Throwable -> 0x006c }
            int r4 = com.alipay.mobile.nebula.R.id.h5_nav_back_to_home     // Catch:{ Throwable -> 0x006c }
            android.view.View r3 = r3.findViewById(r4)     // Catch:{ Throwable -> 0x006c }
            r6.backToHomeContainer = r3     // Catch:{ Throwable -> 0x006c }
            android.view.View r3 = r2.getContentView()     // Catch:{ Throwable -> 0x006c }
            int r4 = com.alipay.mobile.nebula.R.id.h5_tv_nav_back_to_home     // Catch:{ Throwable -> 0x006c }
            android.view.View r3 = r3.findViewById(r4)     // Catch:{ Throwable -> 0x006c }
            android.widget.TextView r3 = (android.widget.TextView) r3     // Catch:{ Throwable -> 0x006c }
            r6.mBackToHomeTitleBar = r3     // Catch:{ Throwable -> 0x006c }
            android.widget.TextView r3 = r6.mBackToHomeTitleBar     // Catch:{ Throwable -> 0x006c }
            if (r3 == 0) goto L_0x0062
            android.graphics.Typeface r3 = r6.mTinyAppIconFont     // Catch:{ Throwable -> 0x0063 }
            if (r3 != 0) goto L_0x004f
            android.content.Context r3 = r6.mContext     // Catch:{ Throwable -> 0x0063 }
            java.lang.String r4 = "tinyfont"
            java.lang.String r5 = "tinyfont/iconfont.ttf"
            android.graphics.Typeface r3 = com.alipay.mobile.antui.iconfont.manager.TypefaceCache.getTypeface(r3, r4, r5)     // Catch:{ Throwable -> 0x0063 }
            r6.mTinyAppIconFont = r3     // Catch:{ Throwable -> 0x0063 }
            android.graphics.Typeface r3 = r6.mTinyAppIconFont     // Catch:{ Throwable -> 0x0063 }
            if (r3 == 0) goto L_0x004f
            android.widget.TextView r3 = r6.mBackToHomeTitleBar     // Catch:{ Throwable -> 0x0063 }
            android.graphics.Typeface r4 = r6.mTinyAppIconFont     // Catch:{ Throwable -> 0x0063 }
            r3.setTypeface(r4)     // Catch:{ Throwable -> 0x0063 }
        L_0x004f:
            android.content.Context r3 = r6.mContext     // Catch:{ Throwable -> 0x006c }
            android.content.res.Resources r1 = r3.getResources()     // Catch:{ Throwable -> 0x006c }
            if (r1 == 0) goto L_0x0062
            android.widget.TextView r3 = r6.mBackToHomeTitleBar     // Catch:{ Throwable -> 0x006c }
            int r4 = com.alipay.mobile.tinyapp.R.string.bar_back_to_home     // Catch:{ Throwable -> 0x006c }
            java.lang.CharSequence r4 = r1.getText(r4)     // Catch:{ Throwable -> 0x006c }
            r3.setText(r4)     // Catch:{ Throwable -> 0x006c }
        L_0x0062:
            return
        L_0x0063:
            r3 = move-exception
            java.lang.String r3 = r6.TAG     // Catch:{ Throwable -> 0x006c }
            java.lang.String r4 = "iconfont failed"
            com.alipay.mobile.nebula.util.H5Log.e(r3, r4)     // Catch:{ Throwable -> 0x006c }
            goto L_0x004f
        L_0x006c:
            r0 = move-exception
            java.lang.String r3 = r6.TAG
            com.alipay.mobile.nebula.util.H5Log.e(r3, r0)
            goto L_0x0062
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.tinyappcommon.dynamicpanel.HomeBackControl.showBackToHomeIcon(com.alipay.mobile.tinyappcommon.dynamicpanel.H5TinyPopMenu$TitleBarTheme):void");
    }
}

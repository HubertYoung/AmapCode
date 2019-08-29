package com.alipay.mobile.tinyappcommon.h5plugin;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.alipay.mobile.antui.load.AntLoadingView;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.refresh.H5PullContainer;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5StateListUtils;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.view.H5TitleView;

public class H5AppearancePlugin extends H5SimplePlugin {
    public static final String ACTION_SET_BACK_BUTTON = "setBackButton";
    public static final String ACTION_SET_BG_COLOR = "setBackgroundColor";
    public static final String ACTION_SET_BG_TEXT_STYLE = "setBackgroundTextStyle";
    public static final String PARAM_BG_COLOR = "backgroundColor";
    public static final String PARAM_COLOR = "color";
    public static final String PARAM_TEXT_STYLE = "textStyle";
    private static final String TAG = "H5AppearancePlugin";
    public static final String TEXT_STYLE_DARK = "dark";
    public static final String TEXT_STYLE_LIGHT = "light";

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(ACTION_SET_BG_COLOR);
        filter.addAction(ACTION_SET_BACK_BUTTON);
        filter.addAction(ACTION_SET_BG_TEXT_STYLE);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        String action = event.getAction();
        if (ACTION_SET_BG_COLOR.equals(action)) {
            doSetBackgroundColor(event, context);
            return true;
        } else if (ACTION_SET_BACK_BUTTON.equals(action)) {
            doSetBackButton(event, context);
            return true;
        } else if (!ACTION_SET_BG_TEXT_STYLE.equals(action)) {
            return false;
        } else {
            doSetBackgroundTextStyle(event, context);
            return true;
        }
    }

    private void doSetBackgroundColor(H5Event event, H5BridgeContext context) {
        H5Log.d(TAG, ACTION_SET_BG_COLOR);
        final H5Page h5Page = event.getH5page();
        if (h5Page == null) {
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        String bgColor = H5Utils.getString(event.getParam(), (String) "backgroundColor");
        if (TextUtils.isEmpty(bgColor)) {
            context.sendError(event, Error.INVALID_PARAM);
            return;
        }
        try {
            final int color = Color.parseColor(bgColor);
            final H5BridgeContext h5BridgeContext = context;
            final H5Event h5Event = event;
            H5Utils.runOnMain(new Runnable() {
                public void run() {
                    View contentView = h5Page.getContentView();
                    if (contentView == null) {
                        h5BridgeContext.sendError(h5Event, Error.UNKNOWN_ERROR);
                        return;
                    }
                    contentView.setBackgroundColor(color);
                    View rootView = h5Page.getRootView();
                    if (rootView == null) {
                        H5Log.d(H5AppearancePlugin.TAG, "root view is null");
                        h5BridgeContext.sendError(h5Event, Error.UNKNOWN_ERROR);
                        return;
                    }
                    View h5WebContent = rootView.findViewById(R.id.h5_web_content);
                    if (h5WebContent == null) {
                        H5Log.d(H5AppearancePlugin.TAG, "web content is null");
                        h5BridgeContext.sendError(h5Event, Error.UNKNOWN_ERROR);
                        return;
                    }
                    h5WebContent.setBackgroundColor(color);
                    h5BridgeContext.sendSuccess();
                }
            });
        } catch (Exception e) {
            H5Log.e(TAG, "parse color error", e);
            context.sendError(event, Error.INVALID_PARAM);
        }
    }

    private void doSetBackButton(H5Event event, H5BridgeContext context) {
        H5Log.d(TAG, ACTION_SET_BACK_BUTTON);
        final H5Page h5Page = event.getH5page();
        if (h5Page == null) {
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        String btnColor = H5Utils.getString(event.getParam(), (String) "color");
        if (TextUtils.isEmpty(btnColor)) {
            context.sendError(event, Error.INVALID_PARAM);
            return;
        }
        try {
            final int color = Color.parseColor(btnColor);
            final H5BridgeContext h5BridgeContext = context;
            final H5Event h5Event = event;
            H5Utils.runOnMain(new Runnable() {
                public void run() {
                    H5TitleView h5TitleView = h5Page.getH5TitleBar();
                    if (h5TitleView == null) {
                        H5Log.d(H5AppearancePlugin.TAG, "title bar is null");
                        h5BridgeContext.sendError(h5Event, Error.UNKNOWN_ERROR);
                        return;
                    }
                    View contentView = h5TitleView.getContentView();
                    if (contentView == null) {
                        H5Log.d(H5AppearancePlugin.TAG, "title bar content view is null");
                        h5BridgeContext.sendError(h5Event, Error.UNKNOWN_ERROR);
                        return;
                    }
                    try {
                        TextView btnBack = (TextView) contentView.findViewById(R.id.h5_tv_nav_back);
                        if (btnBack != null) {
                            btnBack.setTextColor(H5StateListUtils.getStateColor(color));
                            h5BridgeContext.sendSuccess();
                            return;
                        }
                        H5Log.d(H5AppearancePlugin.TAG, "title bar is null");
                        h5BridgeContext.sendError(h5Event, Error.UNKNOWN_ERROR);
                    } catch (Exception e) {
                        H5Log.e(H5AppearancePlugin.TAG, "set back button color error", e);
                        h5BridgeContext.sendError(h5Event, Error.UNKNOWN_ERROR);
                    }
                }
            });
        } catch (Exception e) {
            H5Log.e(TAG, "parse color error", e);
            context.sendError(event, Error.INVALID_PARAM);
        }
    }

    private void doSetBackgroundTextStyle(H5Event event, H5BridgeContext context) {
        H5Log.d(TAG, ACTION_SET_BG_TEXT_STYLE);
        H5Page h5Page = event.getH5page();
        if (h5Page == null || h5Page.getParams() == null) {
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        String textStyle = H5Utils.getString(event.getParam(), (String) PARAM_TEXT_STYLE);
        if (TextUtils.isEmpty(textStyle)) {
            context.sendError(event, Error.INVALID_PARAM);
        } else if (TEXT_STYLE_LIGHT.equals(textStyle)) {
            doSetBackgroundTextStyle(event, context, h5Page, false);
        } else if ("dark".equals(textStyle)) {
            doSetBackgroundTextStyle(event, context, h5Page, true);
        } else {
            context.sendError(event, Error.INVALID_PARAM);
        }
    }

    private void doSetBackgroundTextStyle(H5Event event, H5BridgeContext context, H5Page h5Page, boolean isDark) {
        View rootView = h5Page.getRootView();
        if (rootView == null) {
            H5Log.d(TAG, "root view is null");
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        final View h5WebContent = rootView.findViewById(R.id.h5_web_content);
        if (h5WebContent == null) {
            H5Log.d(TAG, "web content is null");
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        h5Page.getParams().putString(H5Param.PULL_REFRESH_STYLE, isDark ? "default" : H5Param.LONG_TRANSPARENT);
        final H5BridgeContext h5BridgeContext = context;
        final H5Event h5Event = event;
        final boolean z = isDark;
        H5Utils.runOnMain(new Runnable() {
            public void run() {
                String str;
                try {
                    H5PullContainer h5PullContainer = (H5PullContainer) h5WebContent.findViewById(R.id.h5_pc_container);
                    if (h5PullContainer == null) {
                        h5BridgeContext.sendError(h5Event, Error.UNKNOWN_ERROR);
                        return;
                    }
                    AntLoadingView antLoadingView = (AntLoadingView) h5PullContainer.findViewById(h5PullContainer.getResources().getIdentifier("h5_ant_loading_view", "id", "com.alipay.mobile.nebulabiz"));
                    if (antLoadingView != null) {
                        if (z) {
                            str = "_BLUE";
                        } else {
                            str = "_WHITE";
                        }
                        antLoadingView.initAnimation(str);
                    } else {
                        TextView h5PullTitle = (TextView) h5PullContainer.findViewById(R.id.h5_pullrefresh_title);
                        if (h5PullTitle != null) {
                            if (z) {
                                h5PullTitle.setTextColor(h5PullTitle.getResources().getColorStateList(R.color.h5_provider_text));
                            } else {
                                h5PullTitle.setTextColor(-1);
                            }
                        }
                    }
                    h5BridgeContext.sendSuccess();
                } catch (Exception e) {
                    H5Log.e(H5AppearancePlugin.TAG, "set background text style error", e);
                    h5BridgeContext.sendError(h5Event, Error.UNKNOWN_ERROR);
                }
            }
        });
    }
}

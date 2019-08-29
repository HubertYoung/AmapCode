package com.alipay.mobile.tinyappcommon.h5plugin;

import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5TabbarUtils;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.view.H5TabbarLayout;
import com.alipay.mobile.nebulacore.core.H5SessionImpl;
import com.alipay.mobile.nebulacore.tabbar.H5SessionTabBar;
import com.alipay.mobile.nebulacore.tabbar.H5SessionTabInfoParser;
import com.alipay.mobile.nebulacore.tabbar.H5SessionTabInfoParser.H5SessionTabInfoListener;
import com.alipay.mobile.nebulacore.ui.H5Activity;
import com.alipay.mobile.tinyappcommon.utils.TinyAppColorUtils;
import com.alipay.mobile.tinyappcommon.utils.TinyAppEventUtils;
import com.alipay.mobile.tinyappcommon.utils.TinyAppParamUtils;
import com.alipay.mobile.tinyappcommon.utils.graphics.SafeColor;

public class H5TabBarPlugin extends H5SimplePlugin {
    private static final String ACTION_HIDE_TAB_BAR = "hideTabBar";
    private static final String ACTION_HIDE_TAB_BAR_RED_DOT = "hideTabBarRedDot";
    private static final String ACTION_RED_DOT = "redDot";
    private static final String ACTION_REMOVE_TAB_BAR_BADGE = "removeTabBarBadge";
    public static final String ACTION_SET_TAB_BAR = "setTabBar";
    private static final String ACTION_SET_TAB_BAR_BADGE = "setTabBarBadge";
    private static final String ACTION_SET_TAB_BAR_ITEM = "setTabBarItem";
    private static final String ACTION_SET_TAB_BAR_STYLE = "setTabBarStyle";
    private static final String ACTION_SHOW_TAB_BAR = "showTabBar";
    private static final String ACTION_SHOW_TAB_BAR_RED_DOT = "showTabBarRedDot";
    private static final int COLOR_TAB_BAR_BLACK = -5526610;
    private static final int COLOR_TAB_BAR_WHITE = -460551;
    private static final String TAG = "H5TabBarPlugin";
    private static final String TYPE_ANIM_ALPHA = "alpha";
    private static final String TYPE_ANIM_TRANSLATE = "translate";
    /* access modifiers changed from: private */
    public SafeColor defaultColor = SafeColor.INVALID_COLOR;
    /* access modifiers changed from: private */
    public SafeColor defaultSelectedColor = SafeColor.INVALID_COLOR;

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(ACTION_SET_TAB_BAR);
    }

    public boolean interceptEvent(H5Event event, H5BridgeContext context) {
        if (!ACTION_SET_TAB_BAR.equals(event.getAction())) {
            return false;
        }
        initDefaultData(event);
        return handleAction(event, context, H5Utils.getString(event.getParam(), (String) "actionType"));
    }

    private boolean handleAction(H5Event event, H5BridgeContext context, String action) {
        if (TextUtils.isEmpty(action)) {
            context.sendError(event, Error.INVALID_PARAM);
            return true;
        } else if (ACTION_SHOW_TAB_BAR.equals(action)) {
            showTabBar(event, context);
            return true;
        } else if (ACTION_HIDE_TAB_BAR.equals(action)) {
            hideTabBar(event, context);
            return true;
        } else if (ACTION_SET_TAB_BAR_BADGE.equals(action)) {
            setTabBarBadge(event, context);
            return true;
        } else if (ACTION_REMOVE_TAB_BAR_BADGE.equals(action)) {
            removeTabBarBadge(event, context, true);
            return true;
        } else if (ACTION_SHOW_TAB_BAR_RED_DOT.equals(action)) {
            showTabBarRedDot(event, context);
            return true;
        } else if (ACTION_HIDE_TAB_BAR_RED_DOT.equals(action)) {
            hideTabBarRedDot(event, context);
            return true;
        } else if (ACTION_SET_TAB_BAR_STYLE.equals(action)) {
            setTabBarStyle(event, context);
            return true;
        } else if (ACTION_SET_TAB_BAR_ITEM.equals(action)) {
            setTabBatItem(event, context);
            return true;
        } else if (!ACTION_RED_DOT.equals(action)) {
            return false;
        } else {
            redDot(event);
            return false;
        }
    }

    private void initDefaultData(H5Event event) {
        if ((this.defaultColor == SafeColor.INVALID_COLOR || this.defaultSelectedColor == SafeColor.INVALID_COLOR) && event != null && event.getH5page() != null && (event.getH5page().getSession() instanceof H5SessionImpl)) {
            H5SessionTabInfoParser.getOfflineData((H5SessionImpl) event.getH5page().getSession(), new H5SessionTabInfoListener() {
                public void onGetSyncData(JSONObject param) {
                    int textColor = H5Utils.getInt(param, (String) "textColor");
                    int selectedColor = H5Utils.getInt(param, (String) "selectedColor");
                    H5TabBarPlugin.this.defaultColor = new SafeColor(textColor);
                    H5TabBarPlugin.this.defaultSelectedColor = new SafeColor(selectedColor);
                }

                public void onGetAsyncData(JSONObject param) {
                }

                public void onShowDefaultTab() {
                }
            }, TinyAppParamUtils.getAppId(event));
        }
    }

    private void redDot(H5Event event) {
        if (event != null && event.getParam() != null) {
            String text = H5Utils.getString(event.getParam(), (String) ACTION_RED_DOT);
            if (text.length() > 3) {
                event.getParam().put((String) ACTION_RED_DOT, (Object) text.substring(0, 2) + "…");
            }
        }
    }

    private void showTabBar(H5Event event, H5BridgeContext context) {
        if (event == null) {
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        H5Page h5Page = event.getH5page();
        if (h5Page == null) {
            context.sendError(event, Error.UNKNOWN_ERROR);
        } else if (h5Page.getContext() == null || !(h5Page.getContext().getContext() instanceof H5Activity)) {
            context.sendError(event, Error.UNKNOWN_ERROR);
        } else {
            setTabBarVisible(event, context, (H5Activity) h5Page.getContext().getContext(), true);
        }
    }

    private void hideTabBar(H5Event event, H5BridgeContext context) {
        if (event == null) {
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        H5Page h5Page = event.getH5page();
        if (h5Page == null) {
            context.sendError(event, Error.UNKNOWN_ERROR);
        } else {
            setTabBarVisible(event, context, (H5Activity) h5Page.getContext().getContext(), false);
        }
    }

    private void setTabBarVisible(H5Event event, H5BridgeContext context, H5Activity h5Activity, boolean visible) {
        final View container = h5Activity.getSessionTabContainer();
        if (container == null) {
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        H5SessionTabBar tabBar = getTabBar(event.getH5page());
        if (tabBar == null) {
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        final View tabBarContent = tabBar.getContent();
        if (tabBarContent == null) {
            context.sendError(event, Error.UNKNOWN_ERROR);
        } else if (TinyAppEventUtils.containsNull(event, (String) "animation")) {
            context.sendError(event, Error.INVALID_PARAM);
        } else if (!TinyAppEventUtils.contains(event, (String) "animation") || TinyAppEventUtils.isValueAssignableFrom(event, (String) "animation", Boolean.class)) {
            final boolean animation = H5Utils.getBoolean(event.getParam(), (String) "animation", false);
            final String animationType = H5Utils.getString(event.getParam(), (String) "animationType");
            final boolean z = visible;
            H5Utils.runOnMain(new Runnable() {
                public void run() {
                    LayoutParams containerParams = container.getLayoutParams();
                    if (!(containerParams == null || containerParams.height == -2)) {
                        LayoutParams contentParams = tabBarContent.getLayoutParams();
                        if (contentParams != null) {
                            contentParams.height = containerParams.height;
                            tabBarContent.setLayoutParams(contentParams);
                        }
                        containerParams.height = -2;
                        container.setLayoutParams(containerParams);
                        if (container.getBackground() == null) {
                            container.setBackgroundColor(H5TabBarPlugin.COLOR_TAB_BAR_WHITE);
                        } else if ((container.getBackground() instanceof ColorDrawable) && ((ColorDrawable) container.getBackground()).getColor() == 0) {
                            container.setBackgroundColor(H5TabBarPlugin.COLOR_TAB_BAR_WHITE);
                        }
                    }
                    if (z) {
                        if (tabBarContent.getVisibility() != 0) {
                            tabBarContent.setVisibility(0);
                            if (animation) {
                                tabBarContent.clearAnimation();
                                AnimationSet animationSet = new AnimationSet(true);
                                animationSet.setInterpolator(new DecelerateInterpolator());
                                animationSet.setStartTime(AnimationUtils.currentAnimationTimeMillis());
                                animationSet.setDuration(300);
                                if (H5TabBarPlugin.TYPE_ANIM_ALPHA.equals(animationType)) {
                                    animationSet.addAnimation(new AlphaAnimation(0.0f, 1.0f));
                                } else if (H5TabBarPlugin.TYPE_ANIM_TRANSLATE.equals(animationType)) {
                                    animationSet.addAnimation(new TranslateAnimation(2, 0.0f, 2, 0.0f, 2, 1.0f, 2, 0.0f));
                                } else {
                                    Animation translate = new TranslateAnimation(2, 0.0f, 2, 0.0f, 2, 1.0f, 2, 0.0f);
                                    Animation alpha = new AlphaAnimation(0.0f, 1.0f);
                                    animationSet.addAnimation(translate);
                                    animationSet.addAnimation(alpha);
                                }
                                tabBarContent.startAnimation(animationSet);
                            }
                        }
                    } else if (tabBarContent.getVisibility() != 0) {
                    } else {
                        if (animation) {
                            tabBarContent.clearAnimation();
                            AnimationSet animationSet2 = new AnimationSet(true);
                            animationSet2.setInterpolator(new AccelerateInterpolator());
                            animationSet2.setStartTime(AnimationUtils.currentAnimationTimeMillis());
                            animationSet2.setDuration(300);
                            if (H5TabBarPlugin.TYPE_ANIM_ALPHA.equals(animationType)) {
                                animationSet2.addAnimation(new AlphaAnimation(1.0f, 0.0f));
                            } else if (H5TabBarPlugin.TYPE_ANIM_TRANSLATE.equals(animationType)) {
                                animationSet2.addAnimation(new TranslateAnimation(2, 0.0f, 2, 0.0f, 2, 0.0f, 2, 1.0f));
                            } else {
                                Animation translate2 = new TranslateAnimation(2, 0.0f, 2, 0.0f, 2, 0.0f, 2, 1.0f);
                                Animation alpha2 = new AlphaAnimation(1.0f, 0.0f);
                                animationSet2.addAnimation(translate2);
                                animationSet2.addAnimation(alpha2);
                            }
                            animationSet2.setAnimationListener(new AnimationListener() {
                                public void onAnimationStart(Animation animation) {
                                }

                                public void onAnimationEnd(Animation animation) {
                                    tabBarContent.setVisibility(8);
                                }

                                public void onAnimationRepeat(Animation animation) {
                                }
                            });
                            tabBarContent.startAnimation(animationSet2);
                            return;
                        }
                        tabBarContent.setVisibility(8);
                    }
                }
            });
            context.sendSuccess();
        } else {
            context.sendError(event, Error.INVALID_PARAM);
        }
    }

    private void setTabBarBadge(H5Event event, H5BridgeContext context) {
        if (event == null) {
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        H5Page h5Page = event.getH5page();
        if (h5Page == null) {
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        JSONObject eventParams = event.getParam();
        String text = H5Utils.getString(eventParams, (String) "text");
        if (TextUtils.isEmpty(text)) {
            context.sendError(event, Error.INVALID_PARAM);
            return;
        }
        int index = H5Utils.getInt(eventParams, (String) "index", -1);
        if (index == -1) {
            context.sendError(event, Error.INVALID_PARAM);
            return;
        }
        H5SessionTabBar tabBar = getTabBar(h5Page);
        if (tabBar == null) {
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        String tag = indexToTag(tabBar, index);
        if (TextUtils.isEmpty(tag)) {
            context.sendError(event, Error.INVALID_PARAM);
            return;
        }
        JSONObject actionParams = new JSONObject();
        actionParams.put((String) "actionType", (Object) ACTION_RED_DOT);
        actionParams.put((String) "tag", (Object) tag);
        actionParams.put((String) ACTION_RED_DOT, (Object) text);
        setTabBar(h5Page, actionParams);
        context.sendSuccess();
    }

    private void removeTabBarBadge(H5Event event, H5BridgeContext context, boolean isBadge) {
        if (event == null) {
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        H5Page h5Page = event.getH5page();
        if (h5Page == null) {
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        int index = H5Utils.getInt(event.getParam(), (String) "index", -1);
        if (index == -1) {
            context.sendError(event, Error.INVALID_PARAM);
            return;
        }
        H5SessionTabBar tabBar = getTabBar(h5Page);
        if (tabBar == null) {
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        String tag = indexToTag(tabBar, index);
        if (TextUtils.isEmpty(tag)) {
            context.sendError(event, Error.INVALID_PARAM);
            return;
        }
        JSONObject actionParams = new JSONObject();
        actionParams.put((String) "actionType", (Object) ACTION_RED_DOT);
        actionParams.put((String) "tag", (Object) tag);
        if (isBadge) {
            actionParams.put((String) ACTION_RED_DOT, (Object) "-1");
        } else {
            actionParams.put((String) ACTION_RED_DOT, (Object) "-2");
        }
        setTabBar(h5Page, actionParams);
        context.sendSuccess();
    }

    private void showTabBarRedDot(H5Event event, H5BridgeContext context) {
        if (event == null) {
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        H5Page h5Page = event.getH5page();
        if (h5Page == null) {
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        int index = H5Utils.getInt(event.getParam(), (String) "index", -1);
        if (index == -1) {
            context.sendError(event, Error.INVALID_PARAM);
            return;
        }
        H5SessionTabBar tabBar = getTabBar(h5Page);
        if (tabBar == null) {
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        String tag = indexToTag(tabBar, index);
        if (TextUtils.isEmpty(tag)) {
            context.sendError(event, Error.INVALID_PARAM);
            return;
        }
        JSONObject actionParams = new JSONObject();
        actionParams.put((String) "actionType", (Object) ACTION_RED_DOT);
        actionParams.put((String) "tag", (Object) tag);
        actionParams.put((String) ACTION_RED_DOT, (Object) "0");
        setTabBar(h5Page, actionParams);
        context.sendSuccess();
    }

    private void hideTabBarRedDot(H5Event event, H5BridgeContext context) {
        removeTabBarBadge(event, context, false);
    }

    private void setTabBarStyle(H5Event event, H5BridgeContext context) {
        if (event == null) {
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        final H5Page h5Page = event.getH5page();
        if (h5Page == null) {
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        JSONObject eventParams = event.getParam();
        if (TinyAppEventUtils.containsNull(eventParams, (String) "color")) {
            context.sendError(event, Error.INVALID_PARAM);
            return;
        }
        SafeColor color = SafeColor.INVALID_COLOR;
        if (TinyAppEventUtils.contains(event, (String) "color")) {
            color = TinyAppColorUtils.getColor(eventParams, "color");
            if (color == SafeColor.INVALID_COLOR) {
                context.sendError(event, Error.INVALID_PARAM);
                return;
            }
        }
        if (TinyAppEventUtils.containsNull(eventParams, (String) "selectedColor")) {
            context.sendError(event, Error.INVALID_PARAM);
            return;
        }
        SafeColor selectedColor = SafeColor.INVALID_COLOR;
        if (TinyAppEventUtils.contains(eventParams, (String) "selectedColor")) {
            selectedColor = TinyAppColorUtils.getColor(eventParams, "selectedColor");
            if (selectedColor == SafeColor.INVALID_COLOR) {
                context.sendError(event, Error.INVALID_PARAM);
                return;
            }
        }
        if (TinyAppEventUtils.containsNull(eventParams, (String) "backgroundColor")) {
            context.sendError(event, Error.INVALID_PARAM);
            return;
        }
        SafeColor backgroundColor = SafeColor.INVALID_COLOR;
        if (TinyAppEventUtils.contains(eventParams, (String) "backgroundColor")) {
            backgroundColor = TinyAppColorUtils.getColor(eventParams, "backgroundColor");
            if (backgroundColor == SafeColor.INVALID_COLOR) {
                context.sendError(event, Error.INVALID_PARAM);
                return;
            }
        }
        if (TinyAppEventUtils.containsNull(eventParams, (String) "borderStyle")) {
            context.sendError(event, Error.INVALID_PARAM);
            return;
        }
        SafeColor borderStyleColor = SafeColor.INVALID_COLOR;
        if (TinyAppEventUtils.contains(eventParams, (String) "borderStyle")) {
            String borderStyle = H5Utils.getString(eventParams, (String) "borderStyle");
            if (TextUtils.isEmpty(borderStyle)) {
                context.sendError(event, Error.INVALID_PARAM);
                return;
            } else if (TextUtils.equals(borderStyle, "white")) {
                borderStyleColor = new SafeColor((int) COLOR_TAB_BAR_WHITE);
            } else if (TextUtils.equals(borderStyle, "black")) {
                borderStyleColor = new SafeColor((int) COLOR_TAB_BAR_BLACK);
            } else {
                context.sendError(event, Error.INVALID_PARAM);
                return;
            }
        }
        final SafeColor borderColor = borderStyleColor;
        final SafeColor fColor = color;
        final SafeColor fSelectedColor = selectedColor;
        final SafeColor fBackgroundColor = backgroundColor;
        final H5Event h5Event = event;
        final H5BridgeContext h5BridgeContext = context;
        H5Utils.runOnMain(new Runnable() {
            public void run() {
                H5TabBarPlugin.this.setTabBarStyle(h5Event, h5BridgeContext, h5Page, fColor, fSelectedColor, fBackgroundColor, borderColor);
            }
        });
    }

    /* access modifiers changed from: private */
    public void setTabBarStyle(H5Event event, H5BridgeContext context, H5Page h5Page, SafeColor color, SafeColor selectedColor, SafeColor backgroundColor, SafeColor borderStyleColor) {
        H5SessionTabBar tabBar = getTabBar(h5Page);
        if (tabBar == null) {
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        try {
            H5TabbarLayout tabHost = (H5TabbarLayout) tabBar.getContent().findViewById(R.id.h5_tabhost);
            if (backgroundColor != SafeColor.INVALID_COLOR) {
                tabHost.setBackgroundColor(-16777216 | backgroundColor.value);
            }
            View borderView = ((ViewGroup) tabHost.getParent()).getChildAt(0);
            if (borderView.getHeight() == 1 && borderStyleColor != SafeColor.INVALID_COLOR) {
                borderView.setBackgroundColor(borderStyleColor.value);
            }
            if (color == SafeColor.INVALID_COLOR && selectedColor == SafeColor.INVALID_COLOR && this.defaultColor == SafeColor.INVALID_COLOR && this.defaultSelectedColor == SafeColor.INVALID_COLOR) {
                context.sendError(event, Error.UNKNOWN_ERROR);
                return;
            }
            int colorValue = color != SafeColor.INVALID_COLOR ? color.value : this.defaultColor.value;
            int selectedColorValue = selectedColor != SafeColor.INVALID_COLOR ? selectedColor.value : this.defaultSelectedColor.value;
            int size = tabHost.getTabSize();
            for (int i = 0; i < size; i++) {
                ((TextView) tabHost.getChildAt(i).findViewById(R.id.h5_tabbaritem_txticon)).setTextColor(H5TabbarUtils.generateTextColor(colorValue, selectedColorValue));
            }
            context.sendSuccess();
        } catch (Exception e) {
            H5Log.e(TAG, "set tab bar style error", e);
            context.sendError(event, Error.UNKNOWN_ERROR);
        }
    }

    private void setTabBatItem(H5Event event, H5BridgeContext context) {
        if (event == null) {
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        H5Page h5Page = event.getH5page();
        if (h5Page == null) {
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        JSONObject eventParams = event.getParam();
        int index = H5Utils.getInt(eventParams, (String) "index", -1);
        if (index == -1) {
            context.sendError(event, Error.INVALID_PARAM);
            return;
        }
        H5SessionTabBar tabBar = getTabBar(h5Page);
        if (tabBar == null) {
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        String tag = indexToTag(tabBar, index);
        if (TextUtils.isEmpty(tag)) {
            context.sendError(event, Error.INVALID_PARAM);
        } else if (TinyAppEventUtils.containsNull(eventParams, (String) "text")) {
            context.sendError(event, Error.INVALID_PARAM);
        } else {
            String text = H5Utils.getString(eventParams, (String) "text");
            if (TextUtils.isEmpty(text)) {
                context.sendError(event, Error.INVALID_PARAM);
            } else if (TinyAppEventUtils.containsNull(eventParams, (String) "iconPath")) {
                context.sendError(event, Error.INVALID_PARAM);
            } else {
                String iconPath = H5Utils.getString(eventParams, (String) "iconPath");
                if (TextUtils.isEmpty(iconPath)) {
                    context.sendError(event, Error.INVALID_PARAM);
                } else if (TinyAppEventUtils.containsNull(eventParams, (String) "selectedIconPath")) {
                    context.sendError(event, Error.INVALID_PARAM);
                } else {
                    String selectedIconPath = H5Utils.getString(eventParams, (String) "selectedIconPath");
                    if (TextUtils.isEmpty(selectedIconPath)) {
                        context.sendError(event, Error.INVALID_PARAM);
                        return;
                    }
                    JSONObject actionParams = new JSONObject();
                    actionParams.put((String) "actionType", (Object) H5Param.MENU_ICON);
                    actionParams.put((String) "tag", (Object) tag);
                    actionParams.put((String) H5Param.MENU_ICON, (Object) iconPath);
                    actionParams.put((String) "activeIcon", (Object) selectedIconPath);
                    actionParams.put((String) "text", (Object) text);
                    setTabBar(h5Page, actionParams);
                    context.sendSuccess();
                }
            }
        }
    }

    private void setTabBar(H5Page h5Page, JSONObject params) {
        H5Log.d(TAG, ACTION_SET_TAB_BAR);
        h5Page.sendEvent(ACTION_SET_TAB_BAR, params);
    }

    private H5SessionTabBar getTabBar(H5Page h5Page) {
        if (h5Page != null && (h5Page.getSession() instanceof H5SessionImpl)) {
            return ((H5SessionImpl) h5Page.getSession()).getH5SessionTabBar();
        }
        return null;
    }

    private String indexToTag(H5SessionTabBar tabBar, int index) {
        try {
            H5TabbarLayout tabHost = (H5TabbarLayout) tabBar.getContent().findViewById(R.id.h5_tabhost);
            if (index < 0 || index >= tabHost.getTabSize()) {
                return null;
            }
            return (String) tabHost.getChildAt(index).getTag();
        } catch (Exception e) {
            H5Log.e(TAG, "index to tag error", e);
            return null;
        }
    }
}

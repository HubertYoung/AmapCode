package com.alipay.mobile.nebulacore.tabbar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5ImageListener;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.util.H5ImageUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5TabbarUtils;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.view.H5DotView;
import com.alipay.mobile.nebula.view.H5TabbarItem;
import com.alipay.mobile.nebula.view.H5TabbarLayout;
import com.alipay.mobile.nebula.view.H5TabbarLayout.H5TabListener;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.alipay.mobile.nebulacore.ui.H5ViewHolder;
import java.lang.ref.WeakReference;

public abstract class H5BaseTabBar extends H5SimplePlugin {
    public static final String TAG = "H5BaseTabBar";
    H5ViewHolder a;
    H5TabbarLayout b;
    LinearLayout c;
    public WeakReference<Activity> context;
    private String d;

    public abstract void addTabBar();

    public abstract void setPageViewHolder(H5ViewHolder h5ViewHolder);

    public View getContent() {
        return this.c;
    }

    public void createTabBar(JSONObject param, H5BridgeContext bridgeContext, Bundle startParams) {
        H5Log.d(TAG, "createTabBar");
        int textColor = H5Utils.getInt(param, (String) "textColor");
        int selectedColor = H5Utils.getInt(param, (String) "selectedColor");
        int selectedIndex = H5Utils.getInt(param, (String) "selectedIndex");
        int tabBackgroundColor = H5Utils.getInt(param, (String) "backgroundColor", -460551) | -16777216;
        int shadowColor = H5Utils.getInt(param, (String) "shadowColor", -5526610);
        this.d = H5Utils.getString(param, (String) "iconSize", (String) "default");
        JSONArray items = H5Utils.getJSONArray(param, "items", null);
        if (items != null && !items.isEmpty()) {
            this.c = new LinearLayout((Context) this.context.get());
            this.c.setId(R.id.h5_tabrootview);
            this.c.setOrientation(1);
            this.b = new H5TabbarLayout((Context) this.context.get());
            this.b.setId(R.id.h5_tabhost);
            final H5BridgeContext h5BridgeContext = bridgeContext;
            this.b.setTabListener(new H5TabListener() {
                public void onTabItemClicked(int i, View view) {
                    H5BaseTabBar.this.b.selectTab(i);
                    if (h5BridgeContext != null) {
                        JSONObject result = new JSONObject();
                        result.put((String) "tag", view.getTag());
                        JSONObject data = new JSONObject();
                        data.put((String) "data", (Object) result);
                        h5BridgeContext.sendToWeb("tabClick", data, null);
                    }
                }
            });
            int itemsLength = items.size();
            int i = 0;
            while (i < itemsLength && i < 5) {
                JSONObject item = items.getJSONObject(i);
                if (item != null && !item.isEmpty()) {
                    String title = item.getString("name");
                    String tag = item.getString("tag");
                    String iconURL = item.getString(H5Param.MENU_ICON);
                    String activeIconURL = item.getString("activeIcon");
                    String badge = item.getString("redDot");
                    H5Log.d(TAG, "createTabBar badge value is " + badge);
                    H5TabbarItem h5TabbarItem = new H5TabbarItem((Context) this.context.get());
                    h5TabbarItem.setTag(tag);
                    TextView iconArea = (TextView) h5TabbarItem.getIconAreaView();
                    iconArea.setText(title);
                    iconArea.setTextColor(H5TabbarUtils.generateTextColor(textColor, selectedColor));
                    StateListDrawable drawableState = H5TabbarUtils.generateEmptyTopDrawable();
                    int drawableSize = a();
                    drawableState.setBounds(0, 0, drawableSize, drawableSize);
                    a(activeIconURL, iconArea, drawableState, (Context) this.context.get(), drawableSize, true, startParams);
                    a(iconURL, iconArea, drawableState, (Context) this.context.get(), drawableSize, false, startParams);
                    if (!TextUtils.isEmpty(badge)) {
                        String realBadge = badge.trim();
                        if (!TextUtils.equals("-1", realBadge)) {
                            if (TextUtils.equals("0", realBadge)) {
                                h5TabbarItem.getSmallDotView().setVisibility(0);
                            } else {
                                TextView badgeArea = (TextView) h5TabbarItem.getBadgeAreaView();
                                badgeArea.setVisibility(0);
                                badgeArea.setText(realBadge);
                            }
                        }
                    }
                    this.b.addTab(h5TabbarItem.getRootView());
                }
                i++;
            }
            LayoutParams layoutParams = new LayoutParams(-1, 1);
            View view = new View((Context) this.context.get());
            view.setBackgroundColor(shadowColor);
            this.c.addView(view, layoutParams);
            this.b.selectTab(selectedIndex);
            this.b.setBackgroundColor(tabBackgroundColor);
            LayoutParams layoutParams2 = new LayoutParams(-1, -1);
            this.c.addView(this.b, layoutParams2);
            addTabBar();
        }
    }

    public void createTabBadge(JSONObject param, H5BridgeContext bridgeContext) {
        H5Log.d(TAG, "createTabBadge " + bridgeContext);
        if (this.b != null) {
            String tag = H5Utils.getString(param, (String) "tag");
            String value = H5Utils.getString(param, (String) "redDot");
            int redDotSize = H5Utils.getInt(param, (String) "redDotSize");
            int redDotColor = H5Utils.getInt(param, (String) "redDotColor");
            H5Log.d(TAG, "createTabBadge value is " + value);
            int size = this.b.getChildCount();
            for (int i = 0; i < size; i++) {
                View rootView = this.b.getChildAt(i);
                if (TextUtils.equals((String) rootView.getTag(), tag)) {
                    TextView badgeView = (TextView) rootView.findViewById(R.id.h5_tabbaritem_badge);
                    H5DotView smallDotView = (H5DotView) rootView.findViewById(R.id.h5_tabbaritem_dotView);
                    if (TextUtils.isEmpty(value)) {
                        badgeView.setVisibility(8);
                        smallDotView.setVisibility(8);
                        return;
                    }
                    String realBadge = value.trim();
                    if (TextUtils.equals("-1", realBadge) || TextUtils.equals("-2", realBadge)) {
                        if (TextUtils.equals("-1", realBadge)) {
                            badgeView.setVisibility(8);
                            return;
                        }
                    } else if (TextUtils.equals("0", realBadge)) {
                        badgeView.setVisibility(8);
                        smallDotView.setDotColor(redDotColor);
                        smallDotView.setDotWidth(redDotSize);
                        smallDotView.setVisibility(0);
                        return;
                    } else {
                        badgeView.setText(realBadge);
                        badgeView.setVisibility(0);
                    }
                    smallDotView.setVisibility(8);
                    return;
                }
            }
        }
    }

    public void createTabTextColor(JSONObject param, H5BridgeContext bridgeContext) {
        H5Log.d(TAG, "createTabTextColor " + bridgeContext);
        if (this.b != null) {
            String tag = H5Utils.getString(param, (String) "tag");
            int textColor = H5Utils.getInt(param, (String) "textColor");
            int selectedColor = H5Utils.getInt(param, (String) "selectedColor");
            H5Log.d(TAG, "createTabTextColor textColor is " + textColor + ", selectedColor is " + selectedColor);
            int size = this.b.getChildCount();
            for (int i = 0; i < size; i++) {
                View rootView = this.b.getChildAt(i);
                if (TextUtils.equals((String) rootView.getTag(), tag)) {
                    ((TextView) rootView.findViewById(R.id.h5_tabbaritem_txticon)).setTextColor(H5TabbarUtils.generateTextColor(textColor, selectedColor));
                    return;
                }
            }
        }
    }

    public void createTabIcon(JSONObject param, H5BridgeContext bridgeContext, Bundle startParams) {
        H5Log.d(TAG, "createTabIcon " + bridgeContext);
        if (this.b != null) {
            String tag = H5Utils.getString(param, (String) "tag");
            String iconURL = H5Utils.getString(param, (String) H5Param.MENU_ICON);
            String activeIconURL = H5Utils.getString(param, (String) "activeIcon");
            try {
                if (H5ImageUtil.base64ToBitmap(iconURL) == null) {
                    iconURL = H5TabbarUtils.getAbsoluteUrl(iconURL, startParams);
                }
            } catch (Throwable th) {
                iconURL = H5TabbarUtils.getAbsoluteUrl(iconURL, startParams);
            }
            try {
                if (H5ImageUtil.base64ToBitmap(activeIconURL) == null) {
                    activeIconURL = H5TabbarUtils.getAbsoluteUrl(activeIconURL, startParams);
                }
            } catch (Throwable th2) {
                activeIconURL = H5TabbarUtils.getAbsoluteUrl(activeIconURL, startParams);
            }
            String iconText = H5Utils.getString(param, (String) "text");
            H5Log.d(TAG, "createTabIcon iconURL is " + iconURL + ", activeIconURL is " + activeIconURL);
            int size = this.b.getChildCount();
            for (int i = 0; i < size; i++) {
                View rootView = this.b.getChildAt(i);
                if (TextUtils.equals((String) rootView.getTag(), tag)) {
                    TextView iconArea = (TextView) rootView.findViewById(R.id.h5_tabbaritem_txticon);
                    StateListDrawable drawableState = H5TabbarUtils.generateEmptyTopDrawable();
                    int drawableSize = a();
                    drawableState.setBounds(0, 0, drawableSize, drawableSize);
                    a(activeIconURL, iconArea, drawableState, (Context) this.context.get(), drawableSize, true, startParams);
                    a(iconURL, iconArea, drawableState, (Context) this.context.get(), drawableSize, false, startParams);
                    if (!TextUtils.isEmpty(iconText)) {
                        iconArea.setText(iconText);
                        return;
                    }
                    return;
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void a(String imageUrl, TextView iconArea, StateListDrawable sd, Context context2, int drawableSize, boolean isCheckedState, Bundle startParams) {
        String onlineHost = H5Utils.getString(startParams, (String) H5Param.ONLINE_HOST);
        String cdnBaseUrl = H5Utils.getString(startParams, (String) H5Param.CDN_HOST);
        if (!TextUtils.isEmpty(onlineHost) && !TextUtils.isEmpty(cdnBaseUrl) && imageUrl.startsWith(onlineHost)) {
            imageUrl = imageUrl.replace(onlineHost, cdnBaseUrl);
            H5Log.d(TAG, " after replace " + imageUrl);
        }
        if (!imageUrl.startsWith("http")) {
            Bitmap bitmap = H5ImageUtil.base64ToBitmap(imageUrl);
            if (bitmap != null) {
                BitmapDrawable topDrawable = new BitmapDrawable(context2.getResources(), bitmap);
                topDrawable.setBounds(0, 0, drawableSize, drawableSize);
                if (isCheckedState) {
                    H5TabbarUtils.addCheckedState(sd, topDrawable);
                } else {
                    H5TabbarUtils.addNormalState(sd, topDrawable);
                }
                iconArea.setCompoundDrawables(null, sd, null, null);
                return;
            }
            return;
        }
        final Context context3 = context2;
        final int i = drawableSize;
        final boolean z = isCheckedState;
        final StateListDrawable stateListDrawable = sd;
        final TextView textView = iconArea;
        Nebula.loadImage(imageUrl, new H5ImageListener() {
            public void onImage(Bitmap bitmap) {
                if (bitmap != null) {
                    BitmapDrawable topDrawable = new BitmapDrawable(context3.getResources(), bitmap);
                    topDrawable.setBounds(0, 0, i, i);
                    if (z) {
                        H5TabbarUtils.addCheckedState(stateListDrawable, topDrawable);
                    } else {
                        H5TabbarUtils.addNormalState(stateListDrawable, topDrawable);
                    }
                    H5Utils.runOnMain(new Runnable() {
                        public void run() {
                            textView.setCompoundDrawables(null, stateListDrawable, null, null);
                        }
                    });
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public final int a() {
        if (TextUtils.isEmpty(this.d) || !this.d.equalsIgnoreCase("large")) {
            return H5Environment.getResources().getDimensionPixelSize(R.dimen.h5_bottom_height_tab_icon);
        }
        return H5Environment.getResources().getDimensionPixelSize(R.dimen.h5_bottom_height_tab_large_icon);
    }
}

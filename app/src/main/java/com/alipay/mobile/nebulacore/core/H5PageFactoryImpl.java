package com.alipay.mobile.nebulacore.core;

import android.app.Activity;
import android.os.Bundle;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5Page.H5ErrorHandler;
import com.alipay.mobile.h5container.api.H5Page.H5PageHandler;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5Plugin;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.factory.H5PageFactory;
import com.alipay.mobile.nebulacore.tabbar.H5PageTabBar;
import com.alipay.mobile.nebulacore.ui.H5TransWebContent;
import com.alipay.mobile.nebulacore.ui.H5ViewHolder;
import com.alipay.mobile.nebulacore.ui.H5WebContent;
import com.alipay.mobile.nebulacore.util.KeyboardUtil;
import com.alipay.mobile.nebulacore.util.KeyboardUtil.KeyboardListener;
import com.alipay.mobile.nebulacore.view.H5FontBar;
import com.alipay.mobile.nebulacore.view.H5NavigationBar;

public class H5PageFactoryImpl implements H5PageFactory {
    public static final String TAG = "H5PageFactoryImpl";
    private Activity a;

    public H5PageFactoryImpl(Activity activity) {
        this.a = activity;
    }

    public H5ViewHolder createPage(Activity mActivity, Bundle param) {
        H5Log.d(TAG, "start create page");
        long time = System.currentTimeMillis();
        H5ViewHolder rootViewHolder = new H5ViewHolder();
        final Bundle startParams = param;
        boolean transActivity = H5Utils.getBoolean(param, (String) H5Param.LONG_TRANSPARENT, false);
        H5Log.d(TAG, "transActivity " + transActivity);
        if (!transActivity) {
            H5Log.d(TAG, "init nav bar");
            try {
                rootViewHolder.setH5NavBar(new H5NavigationBar(mActivity, startParams, rootViewHolder));
            } catch (Throwable t) {
                H5Log.e(TAG, "init exception", t);
            }
            H5Log.d(TAG, "init nav bar end");
        }
        final H5PageImpl h5Page = new H5PageImpl(this.a, startParams, rootViewHolder);
        h5Page.setHandler(new H5PageHandler() {
            public boolean shouldExit() {
                return true;
            }
        });
        h5Page.setH5ErrorHandler(new H5ErrorHandler() {
            public boolean shouldInterceptError(String url, int code) {
                return false;
            }
        });
        rootViewHolder.setH5Page(h5Page);
        if (!transActivity) {
            try {
                H5Log.d(TAG, "init web content");
                H5WebContent h5WebContainer = new H5WebContent(h5Page);
                rootViewHolder.setH5WebContainer(h5WebContainer);
                h5Page.getPluginManager().register((H5Plugin) h5WebContainer);
                H5Log.d(TAG, "init web content end");
                H5Log.d(TAG, "init font bar");
                H5FontBar h5FontBar = new H5FontBar(h5Page);
                h5Page.getPluginManager().register((H5Plugin) h5FontBar);
                rootViewHolder.setH5FontBar(h5FontBar);
                H5Log.d(TAG, "init font bar end");
                H5Log.d(TAG, "init tab bar");
                H5PageTabBar h5Tabbar = new H5PageTabBar();
                h5Tabbar.setPageViewHolder(rootViewHolder);
                h5Page.getPluginManager().register((H5Plugin) h5Tabbar);
                rootViewHolder.setH5Tabbar(h5Tabbar);
                H5Log.d(TAG, "init tab bar end");
                H5NavigationBar h5NavBar = rootViewHolder.getH5NavBar();
                if (h5NavBar != null) {
                    h5NavBar.setHdivider(rootViewHolder.getH5WebContainer().getHdivider());
                    h5NavBar.setPage(h5Page);
                    h5Page.getPluginManager().register((H5Plugin) h5NavBar);
                }
            } catch (Throwable t2) {
                H5Log.e(TAG, "init exception", t2);
            }
        } else {
            try {
                H5TransWebContent h5WebContainer2 = new H5TransWebContent(h5Page);
                rootViewHolder.setH5TransWebContainer(h5WebContainer2);
                h5Page.getPluginManager().register((H5Plugin) h5WebContainer2);
            } catch (Throwable t3) {
                H5Log.e(TAG, "init exception", t3);
            }
        }
        new KeyboardUtil(this.a).setListener(new KeyboardListener() {
            public void onKeyboardVisible(boolean visible) {
                H5Log.d(H5PageFactoryImpl.TAG, "onKeyboardVisible " + visible);
                if (visible) {
                    String publicId = H5Utils.getString(startParams, (String) H5Param.PUBLIC_ID, (String) "");
                    String url = h5Page.getUrl();
                    JSONObject param = new JSONObject();
                    param.put((String) H5Param.PUBLIC_ID, (Object) publicId);
                    param.put((String) "url", (Object) url);
                    h5Page.sendEvent(CommonEvents.KEY_BOARD_BECOME_VISIBLE, param);
                }
            }
        });
        H5Log.d(TAG, "create page elapse " + (System.currentTimeMillis() - time));
        return rootViewHolder;
    }
}

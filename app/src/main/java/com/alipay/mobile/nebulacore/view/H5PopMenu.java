package com.alipay.mobile.nebulacore.view;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5ImageListener;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.provider.H5SharePanelProvider;
import com.alipay.mobile.nebula.util.H5ImageUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.view.H5NavMenuItem;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.autonavi.minimap.ajx3.loader.AjxHttpLoader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class H5PopMenu {
    public static final String TAG = "H5PopMenu";
    List<H5NavMenuItem> a;
    List<H5NavMenuItem> b;
    PopupWindow c;
    H5Page d;
    boolean e;
    boolean f;
    OnClickListener g = new OnClickListener() {
        public void onClick(View v) {
            if (H5PopMenu.this.c != null && H5PopMenu.this.c.isShowing()) {
                H5PopMenu.this.c.dismiss();
            }
            Object tag = v.getTag();
            if (tag instanceof Integer) {
                H5NavMenuItem item = H5PopMenu.this.a.get(((Integer) tag).intValue());
                item.setRedDotNum("-1");
                JSONObject data = new JSONObject();
                data.put((String) "name", (Object) item.name);
                data.put((String) "tag", (Object) item.tag);
                data.put((String) "title", (Object) item.name);
                data.put((String) "url", (Object) H5PopMenu.this.d.getUrl());
                if (H5PopMenu.this.f) {
                    data.put((String) H5Param.POP_MENU_TYPE, (Object) "popmenu");
                }
                H5PopMenu.this.d.sendEvent(CommonEvents.H5_TOOLBAR_MENU_BT, data);
            }
        }
    };

    public abstract void clearMenuList();

    public abstract void initMenu();

    public abstract void refreshIcon(int i);

    public abstract void showMenu(View view);

    public H5PopMenu() {
        initMenu();
        this.e = true;
        this.f = false;
    }

    public void setPage(H5Page h5Page) {
        this.d = h5Page;
    }

    public void setIsShowPopMenu(boolean isShowPopMenu) {
        this.f = isShowPopMenu;
    }

    public boolean hasMenu(String tag) {
        if (this.a == null || this.a.isEmpty() || TextUtils.isEmpty(tag)) {
            return false;
        }
        for (int index = this.a.size() - 1; index >= 0; index--) {
            H5NavMenuItem item = this.a.get(index);
            if (item != null && tag.equals(item.tag)) {
                return true;
            }
        }
        return false;
    }

    public void setMenu(H5Event event, boolean temp) {
        JSONObject param = event.getParam();
        if (H5Utils.getBoolean(param, (String) "reset", false)) {
            resetMenu();
            return;
        }
        JSONArray menus = H5Utils.getJSONArray(param, "menus", null);
        if (H5Utils.getBoolean(param, (String) "override", this.f) && this.a != null) {
            this.a.clear();
        }
        if (this.f) {
            this.a.clear();
        }
        if (!temp || (menus != null && !menus.isEmpty())) {
            int tempCount = 0;
            synchronized (this.a) {
                for (int index = 0; index < this.a.size(); index++) {
                    if (this.a.get(index).temp) {
                        tempCount++;
                    }
                }
            }
            if (temp && tempCount >= 11) {
                H5Log.e((String) TAG, (String) "reach max temp count!");
            } else if (menus == null || this.a.size() + menus.size() < 11) {
                int availableTemp = 11 - tempCount;
                this.b = new ArrayList();
                for (int index2 = 0; index2 < menus.size(); index2++) {
                    JSONObject jsonobject = menus.getJSONObject(index2);
                    String name = H5Utils.getString(jsonobject, (String) "name");
                    String tag = H5Utils.getString(jsonobject, (String) "tag");
                    String iconUrl = H5Utils.getString(jsonobject, (String) H5Param.MENU_ICON);
                    String redDot = H5Utils.getString(jsonobject, (String) "redDot");
                    if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(tag)) {
                        if (!a(name, tag)) {
                            if (name.length() > 8) {
                                name = name.substring(0, 8);
                            }
                            Drawable icon = a(tag);
                            if (this.f) {
                                icon = null;
                            }
                            H5NavMenuItem h5NavMenuItem = new H5NavMenuItem(name, tag, icon, temp);
                            if (this.f && !TextUtils.isEmpty(redDot)) {
                                h5NavMenuItem.setRedDotNum(redDot);
                            }
                            h5NavMenuItem.iconUrl = iconUrl;
                            if (this.b.size() >= availableTemp) {
                                break;
                            } else if (H5Param.MENU_SHARE.equals(tag)) {
                                h5NavMenuItem.temp = false;
                                this.a.add(0, h5NavMenuItem);
                            } else if (H5Param.MENU_COMPLAIN.equals(tag)) {
                                h5NavMenuItem.temp = false;
                                this.a.add(h5NavMenuItem);
                            } else {
                                this.b.add(h5NavMenuItem);
                            }
                        } else {
                            H5Log.w(TAG, "existed tag: " + tag + " name: " + name);
                        }
                    } else {
                        H5Log.w(TAG, "invalid tag: " + tag + " name: " + name);
                    }
                }
                int firstTemp = -1;
                int firstSys = -1;
                synchronized (this.a) {
                    for (int index3 = 0; index3 < this.a.size(); index3++) {
                        H5NavMenuItem menu = this.a.get(index3);
                        if (menu.temp) {
                            if (firstTemp < 0) {
                                firstTemp = index3;
                            }
                        } else if (firstSys < 0 && (index3 != 0 || !H5Param.MENU_SHARE.equals(menu.tag))) {
                            firstSys = index3;
                        }
                    }
                }
                if (firstTemp == -1) {
                    firstTemp = 0;
                    if (this.a.size() > 0 && H5Param.MENU_SHARE.equals(this.a.get(0).tag)) {
                        firstTemp = 1;
                    }
                }
                if (firstSys == -1) {
                    firstSys = 0;
                }
                if (temp) {
                    this.a.addAll(firstTemp, this.b);
                } else {
                    this.a.addAll(firstSys, this.b);
                }
                getMenuIcon();
                H5SharePanelProvider h5SharePanelProvider = (H5SharePanelProvider) Nebula.getProviderManager().getProvider(H5SharePanelProvider.class.getName());
                if (h5SharePanelProvider == null || this.d == null || this.f) {
                    StringBuilder sb = new StringBuilder("h5SharePanelProvider null ? ");
                    StringBuilder append = sb.append(h5SharePanelProvider == null).append("   h5Page null ? ");
                    boolean z = this.d == null;
                    r0 = TAG;
                    H5Log.d(TAG, append.append(z).toString());
                    return;
                }
                H5Log.d("H5SharePanelProviderImp", "param = " + param);
                H5Log.d("H5SharePanelProviderImp", "h5page = " + this.d.hashCode());
                h5SharePanelProvider.addMenuList(this.d.hashCode(), this.a);
                for (H5NavMenuItem item : this.a) {
                    H5Log.d("H5SharePanelProviderImp", "### addMenuList ### menu : " + item.name + " tag:" + item.tag);
                }
            } else {
                H5Log.e((String) TAG, (String) "(oriCount + addCount) >= MAX_TEMP_COUNT!");
            }
        } else {
            H5Log.d(TAG, "menu not set");
        }
    }

    public void getMenuIcon() {
        for (H5NavMenuItem item : this.a) {
            String iconUrl = item.iconUrl;
            boolean downloading = item.iconDownloading;
            final String name = item.name;
            if (!TextUtils.isEmpty(iconUrl) && !downloading) {
                if (iconUrl.startsWith(AjxHttpLoader.DOMAIN_HTTP) || iconUrl.startsWith(AjxHttpLoader.DOMAIN_HTTPS)) {
                    Nebula.loadImage(iconUrl, new H5ImageListener() {
                        public void onImage(Bitmap bitmap) {
                            H5PopMenu.this.a(name, (Drawable) new BitmapDrawable(bitmap));
                        }
                    });
                } else {
                    a(name, H5ImageUtil.byteToDrawable(iconUrl));
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public synchronized void a(String name, Drawable drawable) {
        if (!TextUtils.isEmpty(name) && drawable != null) {
            int i = 0;
            Iterator<H5NavMenuItem> it = this.a.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                H5NavMenuItem menuItem = it.next();
                if (TextUtils.equals(menuItem.name, name)) {
                    menuItem.icon = drawable;
                    if (this.f) {
                        refreshIcon(i);
                    }
                } else {
                    i++;
                }
            }
        }
    }

    private boolean a(String name, String tag) {
        for (H5NavMenuItem item : this.a) {
            if (!item.name.equals(name)) {
                if (item.tag.equals(tag)) {
                }
            }
            return true;
        }
        return false;
    }

    public void addMenu(int index, H5NavMenuItem menu) {
        if (this.a != null && menu != null) {
            this.a.add(index, menu);
        }
    }

    public void removeMenu(String tag) {
        if (this.a != null && !this.a.isEmpty() && !TextUtils.isEmpty(tag)) {
            int index = this.a.size() - 1;
            while (index >= 0) {
                H5NavMenuItem item = this.a.get(index);
                if (item == null || !tag.equals(item.tag)) {
                    index--;
                } else {
                    this.a.remove(index);
                    return;
                }
            }
        }
    }

    private static Drawable a(String tag) {
        Resources resources = H5Environment.getResources();
        if (H5Param.MENU_COMPLAIN.equals(tag)) {
            return resources.getDrawable(R.drawable.h5_nav_complain);
        }
        if (H5Param.MENU_SHARE.equals(tag) || H5Param.MENU_SHARE_FRIEND.equals(tag)) {
            return resources.getDrawable(R.drawable.h5_nav_share_friend);
        }
        return resources.getDrawable(R.drawable.h5_nav_default);
    }

    public void resetMenu() {
        for (int index = this.a.size() - 1; index >= 0; index--) {
            if (this.a.get(index).temp) {
                this.a.remove(index);
            }
        }
        this.e = true;
        if (this.a.size() == 0) {
            initMenu();
        }
    }
}

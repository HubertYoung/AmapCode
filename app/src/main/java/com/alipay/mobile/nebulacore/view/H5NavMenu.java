package com.alipay.mobile.nebulacore.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.provider.H5ViewProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.view.H5NavMenuItem;
import com.alipay.mobile.nebula.view.H5NavMenuView;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.alipay.mobile.nebulacore.manager.H5ProviderManagerImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class H5NavMenu extends H5PopMenu {
    public static final String TAG = "H5NavMenu";
    private H5NavMenuView h;
    private int i;
    private int j;
    /* access modifiers changed from: private */
    public List<View> k;
    /* access modifiers changed from: private */
    public View l;
    private Context m;
    /* access modifiers changed from: private */
    public FrameLayout n;
    private LayoutParams o;
    private View p;
    /* access modifiers changed from: private */
    public LinearLayout q;
    private boolean r = false;

    public H5NavMenu(Context context) {
        if (context instanceof Activity) {
            this.n = (FrameLayout) ((Activity) context).findViewById(16908290);
        }
        this.m = context;
    }

    private void a() {
        if (this.m != null && this.l == null) {
            this.l = new View(this.m);
            this.l.setBackgroundColor(-16777216);
            this.l.setAlpha(0.4f);
            this.o = new LayoutParams(-1, -1);
            this.p = ((LayoutInflater) this.m.getSystemService("layout_inflater")).inflate(R.layout.h5_popmenu, this.n, false);
            this.q = (LinearLayout) this.p.findViewById(R.id.h5_popmenu_container);
        }
    }

    public void clearMenuList() {
        if (this.a != null && !this.r) {
            this.a.clear();
            this.r = true;
        }
    }

    public void initMenu() {
        Resources resources = H5Environment.getResources();
        H5ViewProvider h5ViewProvider = (H5ViewProvider) H5ProviderManagerImpl.getInstance().getProvider(H5ViewProvider.class.getName());
        if (h5ViewProvider == null) {
            this.h = new com.alipay.mobile.h5container.api.H5NavMenu();
        } else {
            this.h = h5ViewProvider.createNavMenu();
            if (this.h == null) {
                this.h = new com.alipay.mobile.h5container.api.H5NavMenu();
            }
        }
        this.a = Collections.synchronizedList(new ArrayList());
        this.a.add(new H5NavMenuItem(resources.getString(R.string.h5_menu_copy), H5Param.MENU_COPY, resources.getDrawable(R.drawable.h5_nav_copy), false));
        this.a.add(new H5NavMenuItem(resources.getString(R.string.h5_menu_refresh), "refresh", resources.getDrawable(R.drawable.h5_nav_refresh), false));
        this.a.add(new H5NavMenuItem(resources.getString(R.string.h5_menu_open_in_browser), "openInBrowser", resources.getDrawable(R.drawable.h5_nav_browse), false));
        this.a.add(new H5NavMenuItem(resources.getString(R.string.h5_menu_font), H5Param.MENU_FONT, resources.getDrawable(R.drawable.h5_nav_font), false));
        this.a.add(new H5NavMenuItem(resources.getString(R.string.h5_menu_report_new), "report", resources.getDrawable(R.drawable.h5_nav_complain), false));
        this.h.setList(this.a);
    }

    public void showMenu(View anchor) {
        a();
        if (this.c != null && this.c.isShowing()) {
            H5Log.d(TAG, "menu is showing!");
        } else if (this.e || this.c == null) {
            this.q.setVerticalScrollBarEnabled(true);
            this.q.setOnClickListener(this.g);
            if (this.f) {
                this.k = new ArrayList();
            }
            int containerWidth = 0;
            for (int index = 0; index < this.h.getListCount(); index++) {
                if (index != 0) {
                    View divider = new View(this.m);
                    divider.setBackgroundResource(R.drawable.h5_popmenu_divider);
                    this.q.addView(divider, new LayoutParams(-1, 1));
                }
                View item = this.h.getItemView(index, this.q);
                item.setOnClickListener(this.g);
                try {
                    item.measure(0, 0);
                } catch (Exception e) {
                    containerWidth = H5Utils.dip2px(this.m, 200);
                }
                int width = item.getMeasuredWidth();
                if (containerWidth <= width) {
                    containerWidth = width;
                }
                this.q.addView(item);
                if (this.f) {
                    this.k.add(item);
                }
            }
            this.i = (-containerWidth) + (anchor.getWidth() / 2) + 28;
            this.j = 0;
            this.c = new PopupWindow(this.p, containerWidth, -2);
            this.c.setBackgroundDrawable(new ColorDrawable(0));
            this.c.setTouchable(true);
            this.c.setFocusable(true);
            this.c.setOutsideTouchable(true);
            this.c.setClippingEnabled(false);
            this.c.setOnDismissListener(new OnDismissListener() {
                public void onDismiss() {
                    H5Log.d(H5NavMenu.TAG, "popupMenu onDismiss");
                    if (H5NavMenu.this.f && H5NavMenu.this.k != null) {
                        H5NavMenu.this.k.clear();
                    }
                    H5NavMenu.this.n.removeView(H5NavMenu.this.l);
                    H5NavMenu.this.q.removeAllViews();
                }
            });
            this.n.addView(this.l, this.o);
            try {
                this.c.showAsDropDown(anchor, this.i, this.j);
                this.c.update();
            } catch (Throwable throwable) {
                H5Log.e((String) TAG, throwable);
            }
        }
    }

    public void refreshIcon(int index) {
        if (this.f && this.k != null && !this.k.isEmpty()) {
            ((ImageView) this.k.get(index).findViewById(R.id.h5_iv_icon)).setImageDrawable(((H5NavMenuItem) this.a.get(index)).icon);
        }
    }

    public List<H5NavMenuItem> getNavMenuItemList() {
        return this.a;
    }
}

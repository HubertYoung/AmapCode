package com.autonavi.map.setting.page;

import android.content.Context;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.annotation.PageAction;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.setting.presenter.AddNaviShortcutPresenter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.photograph.api.IOpenPage;
import com.autonavi.minimap.photograph.api.IOpenPage.PhotoSelectOptions;

@PageAction("amap.basemap.action.add_navi_shortcut_page")
public class AddNaviShortcutPage extends AbstractBasePage<AddNaviShortcutPresenter> implements OnClickListener {
    public TextView a;
    public TextView b;
    public TextView c;
    private ImageButton d;
    private TextView e;
    private View f;
    private Button g;
    private View h;
    private View i;
    private View j;
    private View k;
    private ImageView l;

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.create_navi_shortcut);
        this.d = (ImageButton) findViewById(R.id.title_btn_left);
        this.f = findViewById(R.id.title_btn_right);
        this.e = (TextView) findViewById(R.id.title_text_name);
        this.l = (ImageView) findViewById(R.id.img_more);
        this.h = findViewById(R.id.right_button);
        this.i = findViewById(R.id.btn_method);
        this.j = findViewById(R.id.btn_name);
        this.k = findViewById(R.id.btn_set_img);
        this.l.setOnClickListener(this);
        this.a = (TextView) findViewById(R.id.text_poi_name);
        this.b = (TextView) findViewById(R.id.text_method);
        this.c = (TextView) findViewById(R.id.text_input_name);
        this.g = (Button) findViewById(R.id.submit);
        this.f.setVisibility(4);
        this.e.setText(R.string.shortcut_navi);
        this.f.setOnClickListener(this);
        this.d.setOnClickListener(this);
        this.h.setOnClickListener(this);
        this.i.setOnClickListener(this);
        this.j.setOnClickListener(this);
        this.k.setOnClickListener(this);
        this.g.setOnClickListener(this);
    }

    public final void a() {
        if (this.a.getText().toString().equals(getString(R.string.mu_di_di_hint)) || this.c.getText().toString().equals(getString(R.string.shu_ru_ming_cheng_hint))) {
            this.g.setEnabled(false);
            this.g.setTextColor(getResources().getColor(R.color.gray_disabled));
            return;
        }
        this.g.setEnabled(true);
        this.g.setTextColor(getResources().getColor(R.color.blue));
    }

    public void onClick(View view) {
        if (view == this.d) {
            finish();
        } else if (view == this.f) {
            finish();
            startPage((String) "amap.basemap.action.default_page", (PageBundle) null);
        } else if (view == this.h) {
            AddNaviShortcutPresenter addNaviShortcutPresenter = (AddNaviShortcutPresenter) this.mPresenter;
            Intent intent = new Intent("plugin.search.SearchCallbackFragment");
            intent.setPackage(AMapAppGlobal.getApplication().getPackageName());
            intent.putExtra("hint", AMapAppGlobal.getApplication().getString(R.string.shortcut_confirm_dest));
            if (addNaviShortcutPresenter.a != null) {
                intent.putExtra(TrafficUtil.KEYWORD, addNaviShortcutPresenter.a.getName());
            }
        } else if (view == this.i) {
            bax bax = (bax) a.a.a(bax.class);
            if (bax != null) {
                bax.a((PageBundle) null);
            }
        } else if (view == this.j) {
            startPageForResult(InputNaviShortCutNamePage.class, (PageBundle) null, 1001);
        } else if (view == this.k) {
            AddNaviShortcutPresenter addNaviShortcutPresenter2 = (AddNaviShortcutPresenter) this.mPresenter;
            String string = AMapAppGlobal.getApplication().getString(R.string.shortcut_set_icon);
            dti dti = (dti) a.a.a(dti.class);
            if (dti != null) {
                IOpenPage a2 = dti.a();
                if (a2 != null) {
                    a2.a((bid) addNaviShortcutPresenter2.mPage, string, PhotoSelectOptions.DEFALUT);
                }
            }
        } else {
            if (view == this.g) {
                String str = Build.BRAND;
                if (str.equals("flyme") || str.equalsIgnoreCase("Meizu")) {
                    ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.shortcut_not_support));
                } else if ("".equals(this.c.getText().toString().trim())) {
                    ToastHelper.showLongToast(getResources().getString(R.string.shortcut_name_not_allowed_empty));
                } else {
                    AddNaviShortcutPresenter addNaviShortcutPresenter3 = (AddNaviShortcutPresenter) this.mPresenter;
                    if (addNaviShortcutPresenter3.a == null || addNaviShortcutPresenter3.a.getPoint() == null) {
                        ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.shortcut_choose_end));
                    } else if (addNaviShortcutPresenter3.b == null || "".equals(addNaviShortcutPresenter3.b)) {
                        ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.shortcut_choose_name));
                    } else {
                        Intent intent2 = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
                        intent2.putExtra("duplicate", false);
                        intent2.putExtra("android.intent.extra.shortcut.NAME", addNaviShortcutPresenter3.b);
                        intent2.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", ShortcutIconResource.fromContext(((AddNaviShortcutPage) addNaviShortcutPresenter3.mPage).getContext(), R.drawable.navishortcut));
                        if (addNaviShortcutPresenter3.c == null && "".equals(addNaviShortcutPresenter3.c)) {
                            addNaviShortcutPresenter3.c = "4";
                        }
                        int d2 = dhw.d(addNaviShortcutPresenter3.c);
                        int c2 = dhw.c(addNaviShortcutPresenter3.c);
                        auz auz = (auz) a.a.a(auz.class);
                        if (auz != null) {
                            POI poi = addNaviShortcutPresenter3.a;
                            new StringBuilder().append(d2);
                            auz.a(poi, String.valueOf(c2));
                        }
                        Intent intent3 = new Intent("com.autonavi.minimap.ACTION", Uri.parse(""));
                        intent3.putExtra("name", addNaviShortcutPresenter3.a.getName());
                        intent3.putExtra("isFromShortcutNavi", true);
                        intent2.putExtra("android.intent.extra.shortcut.INTENT", intent3);
                        ((AddNaviShortcutPage) addNaviShortcutPresenter3.mPage).getActivity().sendBroadcast(intent2);
                        ToastHelper.showToast(((AddNaviShortcutPage) addNaviShortcutPresenter3.mPage).getString(R.string.shortcut_create_success));
                        AddNaviShortcutPage addNaviShortcutPage = (AddNaviShortcutPage) addNaviShortcutPresenter3.mPage;
                        addNaviShortcutPage.a.setText(R.string.mu_di_di_hint);
                        addNaviShortcutPage.c.setText(R.string.shu_ru_ming_cheng_hint);
                        addNaviShortcutPage.b.setText(R.string.pian_hao_hint);
                        addNaviShortcutPage.finish();
                        addNaviShortcutPresenter3.a = null;
                    }
                    this.g.setEnabled(false);
                    this.g.setTextColor(getResources().getColor(R.color.gray_disabled));
                }
            }
        }
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new AddNaviShortcutPresenter(this);
    }
}

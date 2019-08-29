package com.autonavi.minimap.basemap.save.page;

import android.content.Context;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.alipay.mobile.common.logging.api.DeviceProperty;
import com.amap.bundle.datamodel.FavoritePOI;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.map.core.LocationMode.LocationNone;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.widget.ClearableEditText;
import com.autonavi.widget.ui.AlertView;
import com.autonavi.widget.ui.AlertView.a;

public class SaveEditPointPage extends AbstractBasePage<crg> implements OnClickListener, LocationNone {
    public FavoritePOI a;
    /* access modifiers changed from: private */
    public View b;
    private ClearableEditText c;
    private ClearableEditText d;
    private CheckBox e;
    private String f;
    /* access modifiers changed from: private */
    public boolean g;
    /* access modifiers changed from: private */
    public boolean h;
    private OnCheckedChangeListener i = new OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            SaveEditPointPage.this.b.setVisibility(z ? 0 : 8);
        }
    };

    public void onCreate(Context context) {
        String str;
        super.onCreate(context);
        setContentView(R.layout.save_point_edit_fragment);
        View contentView = getContentView();
        contentView.findViewById(R.id.btn_back).setOnClickListener(this);
        contentView.findViewById(R.id.text_save).setOnClickListener(this);
        contentView.findViewById(R.id.layout_set_useful).setOnClickListener(this);
        contentView.findViewById(R.id.layout_set_tag).setOnClickListener(this);
        contentView.findViewById(R.id.layout_set_shortcut).setOnClickListener(this);
        contentView.findViewById(R.id.text_delete).setOnClickListener(this);
        this.b = contentView.findViewById(R.id.layout_useful_alias);
        this.c = (ClearableEditText) contentView.findViewById(R.id.edit_name);
        this.d = (ClearableEditText) contentView.findViewById(R.id.edit_useful_alias);
        this.e = (CheckBox) contentView.findViewById(R.id.check_set_useful);
        this.e.setOnCheckedChangeListener(this.i);
        if (getArguments() != null) {
            this.a = (FavoritePOI) getArguments().getObject("key_point");
        }
        if (this.a != null) {
            boolean z = !TextUtils.isEmpty(this.a.getCommonName());
            this.b.setVisibility(z ? 0 : 8);
            this.e.setChecked(z);
            String customName = this.a.getCustomName();
            if (TextUtils.isEmpty(customName)) {
                customName = this.a.getName();
            }
            this.c.setText(customName);
            if (z) {
                this.d.setText(this.a.getCommonName());
                this.g = getString(R.string.home).equals(this.a.getCommonName());
                this.h = getString(R.string.company).equals(this.a.getCommonName());
            }
            FavoritePOI favoritePOI = this.a;
            if (!TextUtils.isEmpty(favoritePOI.getCommonName())) {
                str = favoritePOI.getCommonName();
            } else {
                String customName2 = favoritePOI.getCustomName();
                if (TextUtils.isEmpty(customName2)) {
                    customName2 = favoritePOI.getName();
                    if (TextUtils.isEmpty(customName2)) {
                        str = favoritePOI.getAddr();
                    }
                }
                str = customName2;
            }
            if (str != null) {
                str = str.trim();
            }
            this.f = str;
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.btn_back) {
            finish();
            return;
        }
        String str = null;
        if (view.getId() == R.id.text_save) {
            if (this.a != null) {
                String obj = this.c.getText().toString();
                String obj2 = this.d.getText().toString();
                boolean isChecked = this.e.isChecked();
                if (isChecked) {
                    if (TextUtils.isEmpty(obj2)) {
                        ToastHelper.showToast(getString(R.string.useful_address_alias_could_not_be_null));
                        return;
                    } else if (!obj2.equals(this.a.getCommonName())) {
                        String a2 = cpm.b().a();
                        cpf.b(a2);
                        if (cpf.a(a2, obj2) != null) {
                            ToastHelper.showToast(getString(R.string.has_same_useful_point));
                            return;
                        }
                    }
                }
                this.a.setCustomName(obj);
                if (isChecked) {
                    this.a.setCommonName(obj2);
                } else {
                    this.a.setCommonName(null);
                }
                if (this.g) {
                    if (!isChecked || !getString(R.string.home).equals(obj2)) {
                        crt.b(cpm.b().a());
                    }
                } else if (this.h && (!isChecked || !getString(R.string.company).equals(obj2))) {
                    crt.c(cpm.b().a());
                }
                cpf.b(cpm.b().a()).g(this.a);
                finish();
            }
        } else if (view.getId() == R.id.layout_set_useful) {
            this.e.toggle();
        } else if (view.getId() == R.id.layout_set_tag) {
            PageBundle pageBundle = new PageBundle();
            if (this.a != null) {
                pageBundle.putObject("key_tag", this.a.getTag());
            }
            PageBundle arguments = getArguments();
            if (arguments != null && arguments.containsKey("key_tags")) {
                pageBundle.putObject("key_tags", arguments.get("key_tags"));
            }
            startPageForResult(SetTagPage.class, pageBundle, 1);
        } else if (view.getId() == R.id.layout_set_shortcut) {
            if (this.a != null) {
                String obj3 = this.c.getText().toString();
                String obj4 = this.d.getText().toString();
                if (this.e.isChecked()) {
                    str = obj4.trim();
                } else {
                    str = obj3.trim();
                }
            }
            if (str == null || this.f == null) {
                ToastHelper.showLongToast(getContext().getResources().getString(R.string.shortcut_name_not_allowed_empty));
                return;
            }
            if (!str.equals(this.f)) {
                String str2 = this.f;
                Intent intent = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
                intent.putExtra("android.intent.extra.shortcut.NAME", str2);
                Intent intent2 = new Intent();
                intent2.setClass(getActivity(), getActivity().getClass());
                intent2.setAction("android.intent.action.MAIN");
                intent2.addCategory("android.intent.category.LAUNCHER");
                intent.putExtra("android.intent.extra.shortcut.INTENT", intent2);
                getActivity().sendBroadcast(intent);
                this.f = str;
            }
            String str3 = Build.BRAND;
            if (str3.equals("flyme") || str3.equalsIgnoreCase("Meizu") || str3.equalsIgnoreCase(DeviceProperty.ALIAS_NUBIA)) {
                ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.shortcut_not_support));
                return;
            }
            Intent intent3 = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
            intent3.putExtra("duplicate", false);
            intent3.putExtra("android.intent.extra.shortcut.NAME", this.f);
            intent3.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", ShortcutIconResource.fromContext(getContext(), R.drawable.ic_save_shortcut));
            bax bax = (bax) a.a.a(bax.class);
            if (bax != null) {
                String b2 = bax.b();
                Intent intent4 = new Intent("com.autonavi.minimap.ACTION", Uri.parse(cru.a(this.a, String.valueOf(dhw.d(b2)), String.valueOf(dhw.c(b2)))));
                intent4.setClassName(getActivity().getApplicationContext(), "com.autonavi.map.activity.SplashActivity");
                intent4.putExtra("name", this.f);
                intent4.putExtra("isFromShortcutNavi", true);
                intent3.putExtra("android.intent.extra.shortcut.INTENT", intent4);
                getActivity().sendBroadcast(intent3);
                ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.shortcut_create_success));
            }
        } else {
            if (view.getId() == R.id.text_delete) {
                a aVar = new a(getContext());
                aVar.a((CharSequence) getString(R.string.sure_to_delelte));
                AnonymousClass2 r0 = new a() {
                    public final void onClick(AlertView alertView, int i) {
                        SaveEditPointPage.this.dismissViewLayer(alertView);
                    }
                };
                aVar.b((CharSequence) getString(R.string.cancel), (a) r0);
                aVar.c = r0;
                aVar.a((CharSequence) getString(R.string.delete), (a) new a() {
                    public final void onClick(AlertView alertView, int i) {
                        SaveEditPointPage.this.dismissViewLayer(alertView);
                        if (SaveEditPointPage.this.a != null) {
                            if (SaveEditPointPage.this.g) {
                                crt.b(cpm.b().a());
                            } else if (SaveEditPointPage.this.h) {
                                crt.c(cpm.b().a());
                            }
                            cpf.b(cpm.b().a()).a((POI) SaveEditPointPage.this.a);
                        }
                        SaveEditPointPage.this.finish();
                    }
                });
                showViewLayer(aVar.a());
            }
        }
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new crg(this);
    }
}

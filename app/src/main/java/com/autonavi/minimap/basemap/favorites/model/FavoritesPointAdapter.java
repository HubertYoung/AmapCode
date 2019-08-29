package com.autonavi.minimap.basemap.favorites.model;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.favorites.fragment.FavoritesPointFragment;
import com.autonavi.minimap.basemap.favorites.view.FavoriteActionSheet;
import com.autonavi.minimap.basemap.save.page.FavoriteTagSelectPage;
import com.autonavi.minimap.basemap.save.page.SavePointEditExtraPage;
import com.autonavi.minimap.basemap.save.page.SavePointToMapPage;
import com.autonavi.minimap.basemap.save.page.SaveSearchPage;
import com.autonavi.minimap.basemap.save.page.SetTagPage;
import com.autonavi.minimap.map.FavoriteLayer;
import com.autonavi.sdk.log.util.LogConstant;
import com.autonavi.widget.ui.ActionSheet;
import com.autonavi.widget.ui.AlertView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class FavoritesPointAdapter extends BaseExpandableListAdapter {
    public static final int ACTION_SHEET_FIRST_ITEM = 0;
    public static final int ACTION_SHEET_FOURTH_ITEM = 3;
    public static final int ACTION_SHEET_SECOND_ITEM = 1;
    public static final int ACTION_SHEET_THIRD_ITEM = 2;
    private Application app;
    /* access modifiers changed from: private */
    public Map<String, String> checkedList = new HashMap();
    /* access modifiers changed from: private */
    public bid fragment;
    private LayoutInflater inflater;
    private boolean isEditMode;
    private FavoriteActionSheet mActionSheet;
    private AlertView mAlertView;
    /* access modifiers changed from: private */
    public FavoritesPointFragment mFavoritesPointFragment;
    /* access modifiers changed from: private */
    public boolean mIsHomeAndCompany;
    /* access modifiers changed from: private */
    public com.autonavi.minimap.basemap.save.page.SavePointEditMenuPage.a mOnEditSavePointListener;
    /* access modifiers changed from: private */
    public bth mSavePoint;
    /* access modifiers changed from: private */
    public coz mSelectEditModePointListener;
    private List<cpa> pointGroups;

    static class a {
        ImageView a;
        View b;
        View c;
        ImageView d;
        CheckBox e;
        TextView f;
        TextView g;
        View h;
        View i;

        private a() {
        }

        /* synthetic */ a(byte b2) {
            this();
        }
    }

    static class b {
        View a;
        View b;
        TextView c;
        View d;

        private b() {
        }

        /* synthetic */ b(byte b2) {
            this();
        }
    }

    public long getChildId(int i, int i2) {
        return (long) i2;
    }

    public long getGroupId(int i) {
        return (long) i;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int i, int i2) {
        return true;
    }

    public FavoritesPointAdapter(bid bid, List<cpa> list, com.autonavi.minimap.basemap.save.page.SavePointEditMenuPage.a aVar, FavoritesPointFragment favoritesPointFragment) {
        this.pointGroups = list;
        this.fragment = bid;
        this.mFavoritesPointFragment = favoritesPointFragment;
        this.mOnEditSavePointListener = aVar;
        this.app = AMapAppGlobal.getApplication();
        this.inflater = LayoutInflater.from(this.app);
    }

    public int getCheckedCount() {
        return this.checkedList.size();
    }

    public Map<String, String> getCheckedItems() {
        return this.checkedList;
    }

    public void setFavoritesListActionListener(coz coz) {
        this.mSelectEditModePointListener = coz;
    }

    public void setEditMode(boolean z, boolean z2) {
        if (z != this.isEditMode) {
            this.isEditMode = z;
            if (this.isEditMode && z2) {
                this.checkedList.clear();
            }
        }
    }

    public boolean getEditMode() {
        return this.isEditMode;
    }

    public int getGroupCount() {
        if (this.pointGroups != null) {
            return this.pointGroups.size();
        }
        return 0;
    }

    public int getChildrenCount(int i) {
        if (i == 1) {
            List<String> list = this.pointGroups.get(i).c;
            if (list != null) {
                return list.size();
            }
            return 0;
        }
        List<cpb> list2 = this.pointGroups.get(i).b;
        if (list2 != null) {
            return list2.size();
        }
        return 0;
    }

    public Object getGroup(int i) {
        return this.pointGroups.get(i);
    }

    public Object getChild(int i, int i2) {
        if (i == 1) {
            return new cpb(this.pointGroups.get(i).c.get(i2));
        }
        return this.pointGroups.get(i).b.get(i2);
    }

    public View getGroupView(int i, boolean z, View view, ViewGroup viewGroup) {
        b bVar;
        if (view != null) {
            bVar = (b) view.getTag();
        } else {
            view = this.inflater.inflate(R.layout.save_group_item, null);
            bVar = new b(0);
            bVar.a = view.findViewById(R.id.view_top);
            bVar.b = view.findViewById(R.id.layout_group);
            bVar.c = (TextView) view.findViewById(R.id.text_group_name);
            bVar.d = view.findViewById(R.id.shaixuan_layout);
            view.setTag(bVar);
        }
        if (bVar.a.getVisibility() != 0) {
            bVar.b.setVisibility(0);
            bVar.a.setVisibility(0);
        }
        String str = this.pointGroups.get(i).a;
        bVar.c.setText(str);
        bVar.c.setFocusable(false);
        bVar.c.setClickable(false);
        if (i == 0 && this.pointGroups.size() == 1 && !crt.d.equals(str)) {
            bVar.b.setVisibility(8);
            bVar.a.setVisibility(8);
        }
        if (i != 1 || this.pointGroups.size() != 2 || !str.equals(AMapAppGlobal.getApplication().getResources().getString(R.string.save_other))) {
            bVar.d.setVisibility(8);
        } else if (this.isEditMode) {
            bVar.d.setVisibility(8);
            bVar.d.setOnClickListener(null);
        } else {
            bVar.d.setVisibility(0);
            bVar.d.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    crb crb = new crb();
                    crb.c = bim.aa().r();
                    crb.b = bim.aa().u();
                    crb.a = bim.aa().v();
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putObject("tag_key", crb);
                    FavoritesPointAdapter.this.fragment.startPageForResult(FavoriteTagSelectPage.class, pageBundle, (int) FavoritesPointFragment.REQUEST_TAG_SELECT);
                }
            });
        }
        ((ExpandableListView) viewGroup).expandGroup(i);
        return view;
    }

    public View getChildView(int i, int i2, boolean z, View view, ViewGroup viewGroup) {
        String str;
        a aVar = view != null ? (a) view.getTag() : null;
        int i3 = 0;
        if (aVar == null) {
            view = this.inflater.inflate(R.layout.save_point_item, null);
            aVar = new a(0);
            aVar.a = (ImageView) view.findViewById(R.id.is_top);
            aVar.b = view.findViewById(R.id.more);
            aVar.d = (ImageView) view.findViewById(R.id.image_status);
            aVar.f = (TextView) view.findViewById(R.id.text_point_name);
            aVar.g = (TextView) view.findViewById(R.id.text_point_detail);
            aVar.e = (CheckBox) view.findViewById(R.id.check);
            aVar.h = view.findViewById(R.id.view_divider_part);
            aVar.i = view.findViewById(R.id.view_divider_all);
            aVar.c = view.findViewById(R.id.layout_save_point_item);
            view.setTag(aVar);
        }
        if (i2 >= getChildrenCount(i) - 1) {
            aVar.i.setVisibility(0);
            aVar.h.setVisibility(8);
        } else {
            aVar.i.setVisibility(8);
            aVar.h.setVisibility(0);
        }
        cpb cpb = (cpb) getChild(i, i2);
        if (cpb != null) {
            aVar.f.setText(cru.a(cpb));
            aVar.d.setImageResource(cpb.c());
            ImageView imageView = aVar.a;
            if (!cpb.a()) {
                i3 = 8;
            }
            imageView.setVisibility(i3);
            TextView textView = aVar.g;
            if (!cpb.b()) {
                str = cpb.d() ? AMapAppGlobal.getApplication().getString(R.string.save_set_home_positon) : cpb.e() ? AMapAppGlobal.getApplication().getString(R.string.save_set_company_position) : "";
            } else if (!cpb.f()) {
                str = cru.b(cpb);
            } else if (!TextUtils.isEmpty(cpb.m)) {
                str = cpb.m;
            } else if (!TextUtils.isEmpty(cpb.c)) {
                str = cpb.c;
            } else if (!TextUtils.isEmpty(cpb.l)) {
                str = cpb.l;
            } else {
                str = cru.c(cpb);
                if (TextUtils.isEmpty(str)) {
                    str = "";
                }
            }
            textView.setText(str);
            if (this.isEditMode) {
                bindEditView(aVar, cpb, i);
            } else {
                bindView(aVar, cpb);
            }
            if (i == 0 && !cpb.b()) {
                aVar.b.setVisibility(8);
                aVar.c.setOnLongClickListener(null);
            }
        }
        return view;
    }

    /* access modifiers changed from: private */
    public void showHomeOrCompanyActionSheet() {
        final Context appContext = AMapPageUtil.getAppContext();
        if (appContext != null) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(new com.autonavi.widget.ui.ActionSheet.a(appContext.getString(R.string.save_edit_menu_change_address)));
            arrayList.add(new com.autonavi.widget.ui.ActionSheet.a(appContext.getString(R.string.save_edit_menu_delete)));
            arrayList.add(new com.autonavi.widget.ui.ActionSheet.a(appContext.getString(R.string.save_edit_menu_add_shortcut)));
            String a2 = cru.a(this.mSavePoint);
            com.autonavi.widget.ui.ActionSheet.b bVar = new com.autonavi.widget.ui.ActionSheet.b(appContext);
            bVar.c = arrayList;
            bVar.b = a2;
            bVar.d = appContext.getString(R.string.cancel);
            bVar.f = new defpackage.erl.a() {
                public final void a(ActionSheet actionSheet, int i) {
                    FavoritesPointAdapter.this.dismissActionSheet(actionSheet);
                }
            };
            bVar.e = new defpackage.erl.a() {
                public final void a(ActionSheet actionSheet, int i) {
                    FavoritesPointAdapter.this.dismissActionSheet(actionSheet);
                }
            };
            bVar.g = new defpackage.erl.a() {
                public final void a(ActionSheet actionSheet, int i) {
                    String str = null;
                    switch (i) {
                        case 0:
                            FavoritesPointAdapter.this.startFragmentForHomeOrCompany(FavoritesPointAdapter.this.mSavePoint.d);
                            if (crt.c.equals(FavoritesPointAdapter.this.mSavePoint.d)) {
                                str = "B008";
                            } else if (crt.b.equals(FavoritesPointAdapter.this.mSavePoint.d)) {
                                str = "B007";
                            }
                            try {
                                JSONObject jSONObject = new JSONObject();
                                jSONObject.put("action", 1);
                                if (!TextUtils.isEmpty(str)) {
                                    LogManager.actionLogV2(LogConstant.PAGE_SAVE_MAIN_POINT, str, jSONObject);
                                    break;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                break;
                            }
                            break;
                        case 1:
                            FavoritesPointAdapter.this.showDeleteAlertView();
                            break;
                        case 2:
                            cru.a(FavoritesPointAdapter.this.mSavePoint, appContext);
                            if (crt.c.equals(FavoritesPointAdapter.this.mSavePoint.d)) {
                                str = "B008";
                            } else if (crt.b.equals(FavoritesPointAdapter.this.mSavePoint.d)) {
                                str = "B007";
                            }
                            try {
                                JSONObject jSONObject2 = new JSONObject();
                                jSONObject2.put("action", 3);
                                if (!TextUtils.isEmpty(str)) {
                                    LogManager.actionLogV2(LogConstant.PAGE_SAVE_MAIN_POINT, str, jSONObject2);
                                    break;
                                }
                            } catch (JSONException e2) {
                                e2.printStackTrace();
                                break;
                            }
                            break;
                    }
                    FavoritesPointAdapter.this.dismissActionSheet(actionSheet);
                }
            };
            bVar.a = new FavoriteActionSheet(appContext, 1);
            this.mActionSheet = (FavoriteActionSheet) bVar.a();
            this.mActionSheet.setCancelable(true);
            setActionSheet(this.mActionSheet);
            if (this.fragment != null) {
                this.fragment.showViewLayer(this.mActionSheet);
            }
        }
    }

    /* access modifiers changed from: private */
    public void showActionSheet() {
        Context appContext = AMapPageUtil.getAppContext();
        if (appContext != null) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(new com.autonavi.widget.ui.ActionSheet.a(appContext.getString(R.string.save_edit_menu_set_extra)));
            if (cru.b(this.mSavePoint)) {
                arrayList.add(new com.autonavi.widget.ui.ActionSheet.a(appContext.getString(R.string.save_edit_menu_cancel_top)));
            } else {
                arrayList.add(new com.autonavi.widget.ui.ActionSheet.a(appContext.getString(R.string.save_edit_menu_top)));
            }
            arrayList.add(new com.autonavi.widget.ui.ActionSheet.a(appContext.getString(R.string.save_edit_menu_set_tag)));
            arrayList.add(new com.autonavi.widget.ui.ActionSheet.a(appContext.getString(R.string.save_edit_menu_add_shortcut)));
            String a2 = cru.a(this.mSavePoint);
            com.autonavi.widget.ui.ActionSheet.b bVar = new com.autonavi.widget.ui.ActionSheet.b(appContext);
            bVar.c = arrayList;
            bVar.b = a2;
            bVar.d = appContext.getString(R.string.cancel);
            bVar.f = new defpackage.erl.a() {
                public final void a(ActionSheet actionSheet, int i) {
                    FavoritesPointAdapter.this.dismissActionSheet(actionSheet);
                }
            };
            bVar.e = new defpackage.erl.a() {
                public final void a(ActionSheet actionSheet, int i) {
                    FavoritesPointAdapter.this.dismissActionSheet(actionSheet);
                }
            };
            bVar.g = new defpackage.erl.a() {
                public final void a(ActionSheet actionSheet, int i) {
                    switch (i) {
                        case 0:
                            PageBundle pageBundle = new PageBundle();
                            pageBundle.putObject("savepointkey", FavoritesPointAdapter.this.mSavePoint);
                            pageBundle.putObject("save_fragment_key", FavoritesPointAdapter.this.mOnEditSavePointListener);
                            FavoritesPointAdapter.this.fragment.startPage(SavePointEditExtraPage.class, pageBundle);
                            try {
                                JSONObject jSONObject = new JSONObject();
                                jSONObject.put("action", 1);
                                LogManager.actionLogV2(LogConstant.PAGE_SAVE_MAIN_POINT, "B009", jSONObject);
                                break;
                            } catch (JSONException e) {
                                e.printStackTrace();
                                break;
                            }
                        case 1:
                            cru.c(FavoritesPointAdapter.this.mSavePoint);
                            FavoritesPointAdapter.this.mFavoritesPointFragment.editSavePoint(FavoritesPointAdapter.this.mSavePoint);
                            try {
                                int i2 = cru.b(FavoritesPointAdapter.this.mSavePoint) ? 3 : 2;
                                JSONObject jSONObject2 = new JSONObject();
                                jSONObject2.put("action", i2);
                                LogManager.actionLogV2(LogConstant.PAGE_SAVE_MAIN_POINT, "B009", jSONObject2);
                                break;
                            } catch (JSONException e2) {
                                e2.printStackTrace();
                                break;
                            }
                        case 2:
                            PageBundle pageBundle2 = new PageBundle();
                            pageBundle2.putObject("save_point_key", FavoritesPointAdapter.this.mSavePoint);
                            FavoritesPointAdapter.this.fragment.startPageForResult(SetTagPage.class, pageBundle2, (int) FavoritesPointFragment.REQUEST_EDIT_POINT);
                            try {
                                JSONObject jSONObject3 = new JSONObject();
                                jSONObject3.put("action", 4);
                                LogManager.actionLogV2(LogConstant.PAGE_SAVE_MAIN_POINT, "B009", jSONObject3);
                                break;
                            } catch (JSONException e3) {
                                e3.printStackTrace();
                                break;
                            }
                        case 3:
                            cru.a(FavoritesPointAdapter.this.mSavePoint, actionSheet.getContext());
                            try {
                                JSONObject jSONObject4 = new JSONObject();
                                jSONObject4.put("action", 5);
                                LogManager.actionLogV2(LogConstant.PAGE_SAVE_MAIN_POINT, "B009", jSONObject4);
                                break;
                            } catch (JSONException e4) {
                                e4.printStackTrace();
                                break;
                            }
                    }
                    FavoritesPointAdapter.this.dismissActionSheet(actionSheet);
                }
            };
            bVar.a = new FavoriteActionSheet(appContext, 1);
            this.mActionSheet = (FavoriteActionSheet) bVar.a();
            this.mActionSheet.setCancelable(true);
            this.mActionSheet.setClickable(true);
            setActionSheet(this.mActionSheet);
            if (this.fragment != null) {
                this.fragment.showViewLayer(this.mActionSheet);
            }
        }
    }

    /* access modifiers changed from: private */
    public void dismissActionSheet(ActionSheet actionSheet) {
        this.fragment.dismissViewLayer(actionSheet);
    }

    /* access modifiers changed from: private */
    public void showDeleteAlertView() {
        if (this.mAlertView == null) {
            Context appContext = AMapPageUtil.getAppContext();
            if (appContext != null) {
                com.autonavi.widget.ui.AlertView.a aVar = new com.autonavi.widget.ui.AlertView.a(appContext);
                aVar.a((CharSequence) appContext.getString(R.string.fav_delete_prompt_msg)).b((CharSequence) appContext.getString(R.string.fav_qu_xiao), (defpackage.ern.a) new defpackage.ern.a() {
                    public final void onClick(AlertView alertView, int i) {
                        FavoritesPointAdapter.this.dismissAlertView();
                    }
                }).a((CharSequence) appContext.getString(R.string.fav_shan_chu), (defpackage.ern.a) new defpackage.ern.a() {
                    public final void onClick(AlertView alertView, int i) {
                        FavoritesPointAdapter.this.mFavoritesPointFragment.deleteFavoritesPoints(FavoritesPointAdapter.this.mSavePoint);
                        String str = crt.c.equals(FavoritesPointAdapter.this.mSavePoint.d) ? "B008" : crt.b.equals(FavoritesPointAdapter.this.mSavePoint.d) ? "B007" : null;
                        try {
                            JSONObject jSONObject = new JSONObject();
                            jSONObject.put("action", 2);
                            if (!TextUtils.isEmpty(str)) {
                                LogManager.actionLogV2(LogConstant.PAGE_SAVE_MAIN_POINT, str, jSONObject);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        FavoritesPointAdapter.this.dismissAlertView();
                    }
                });
                aVar.a(true);
                this.mAlertView = aVar.a();
            } else {
                return;
            }
        }
        showAlertView();
    }

    private void showAlertView() {
        this.fragment.showViewLayer(this.mAlertView);
    }

    /* access modifiers changed from: private */
    public void dismissAlertView() {
        this.fragment.dismissViewLayer(this.mAlertView);
    }

    /* access modifiers changed from: private */
    public void startFragmentForHomeOrCompany(String str) {
        int i;
        PageBundle pageBundle = new PageBundle();
        if (crt.c.equals(str)) {
            i = FavoritesPointFragment.REQUEST_COMPNAY;
            pageBundle.putString("address", crt.c);
        } else {
            if (crt.b.equals(str)) {
                pageBundle.putString("address", crt.b);
            }
            i = FavoritesPointFragment.REQUEST_HOME;
        }
        this.fragment.startPageForResult(SaveSearchPage.class, pageBundle, i);
    }

    private void bindView(a aVar, final cpb cpb) {
        aVar.b.setVisibility(0);
        aVar.e.setVisibility(8);
        aVar.d.setVisibility(0);
        aVar.b.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                if (FavoritesPointAdapter.this.mSelectEditModePointListener != null) {
                    FavoritesPointAdapter.this.mSelectEditModePointListener.onShowDialogFragment();
                }
                FavoritesPointAdapter.this.mSavePoint = bsr.a(bim.aa().b((String) "101", cpb.b), cpb.b, FavoritesPointAdapter.this.getUid());
                FavoritesPointAdapter.this.mIsHomeAndCompany = cru.d(FavoritesPointAdapter.this.mSavePoint);
                if (FavoritesPointAdapter.this.mIsHomeAndCompany) {
                    FavoritesPointAdapter.this.showHomeOrCompanyActionSheet();
                } else {
                    FavoritesPointAdapter.this.showActionSheet();
                }
            }
        });
        aVar.c.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                if (!cpb.f() || cpb.b()) {
                    String b2 = bim.aa().b((String) "101", cpb.b);
                    brn brn = (brn) ank.a(brn.class);
                    if (!(cpb == null || brn == null)) {
                        bth a2 = bsr.a(b2, cpb.b, FavoritesPointAdapter.this.getUid());
                        PageBundle pageBundle = new PageBundle();
                        if (!(a2 == null || a2.a() == null)) {
                            bim.aa().a((biv) null);
                            a2.a().getPoiExtra().put(FavoriteLayer.POI_KEY, cpb.b);
                            pageBundle.putObject("currentSelectedPoi", a2);
                            FavoritesPointAdapter.this.fragment.startPage(SavePointToMapPage.class, pageBundle);
                            LogManager.actionLogV2(LogConstant.PAGE_SAVE_MAIN_POINT, "B011");
                        }
                    }
                    return;
                }
                FavoritesPointAdapter.this.startFragmentForHomeOrCompany(cpb);
            }
        });
        aVar.c.setOnLongClickListener(new OnLongClickListener() {
            public final boolean onLongClick(View view) {
                if (FavoritesPointAdapter.this.mSelectEditModePointListener != null) {
                    String str = cpb.a;
                    if (cpb.d()) {
                        str = crt.b;
                    } else if (cpb.e()) {
                        str = crt.c;
                    }
                    FavoritesPointAdapter.this.checkedList.clear();
                    FavoritesPointAdapter.this.checkedList.put(str, cpb.b);
                    FavoritesPointAdapter.this.mSelectEditModePointListener.onItemLongClick();
                    FavoritesPointAdapter.this.mSelectEditModePointListener.onItemCheckedChangeListener();
                }
                return true;
            }
        });
    }

    private void bindEditView(final a aVar, final cpb cpb, int i) {
        aVar.b.setVisibility(8);
        aVar.e.setVisibility(0);
        aVar.d.setVisibility(8);
        String str = cpb.a;
        if (cpb.d()) {
            str = crt.b;
        } else if (cpb.e()) {
            str = crt.c;
        }
        boolean containsKey = this.checkedList.containsKey(str);
        aVar.e.setOnCheckedChangeListener(null);
        aVar.c.setOnClickListener(null);
        aVar.e.setChecked(containsKey);
        aVar.e.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (FavoritesPointAdapter.this.mSelectEditModePointListener != null) {
                    String str = cpb.a;
                    if (cpb.d()) {
                        str = crt.b;
                    } else if (cpb.e()) {
                        str = crt.c;
                    }
                    if (!z) {
                        FavoritesPointAdapter.this.checkedList.remove(str);
                    } else {
                        FavoritesPointAdapter.this.checkedList.put(str, cpb.b);
                    }
                    FavoritesPointAdapter.this.mSelectEditModePointListener.onItemCheckedChangeListener();
                }
            }
        });
        aVar.c.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                aVar.e.setChecked(!aVar.e.isChecked());
            }
        });
        aVar.c.setOnLongClickListener(null);
        if (i == 0) {
            boolean z = cpb.d() ? this.checkedList.containsKey(crt.b) : cpb.e() ? this.checkedList.containsKey(crt.c) : false;
            aVar.e.setChecked(z);
            if (!cpb.b()) {
                aVar.c.setOnClickListener(null);
                aVar.d.setVisibility(0);
                aVar.e.setVisibility(8);
            }
        }
    }

    /* access modifiers changed from: private */
    public void startFragmentForHomeOrCompany(cpb cpb) {
        String str;
        int i;
        if (cpb.e()) {
            str = "B004";
            i = FavoritesPointFragment.REQUEST_COMPNAY;
        } else {
            str = "B003";
            i = FavoritesPointFragment.REQUEST_HOME;
        }
        PageBundle pageBundle = new PageBundle();
        if (cpb.k != null && cpb.k.equals(crt.c)) {
            pageBundle.putString("address", crt.c);
        } else if (cpb.k != null && cpb.k.equals(crt.b)) {
            pageBundle.putString("address", crt.b);
        }
        if (i == 241) {
            pageBundle.putString("address", crt.b);
            pageBundle.putString("search_hint", AMapAppGlobal.getApplication().getString(R.string.act_fromto_home_input_hint));
        }
        if (i == 242) {
            pageBundle.putString("address", crt.c);
            pageBundle.putString("search_hint", AMapAppGlobal.getApplication().getString(R.string.act_fromto_company_input_hint));
        }
        this.fragment.startPageForResult(SaveSearchPage.class, pageBundle, i);
        LogManager.actionLogV2(LogConstant.PAGE_SAVE_MAIN_POINT, str);
    }

    public ActionSheet getActionSheet() {
        return this.mActionSheet;
    }

    public void setActionSheet(FavoriteActionSheet favoriteActionSheet) {
        this.mActionSheet = favoriteActionSheet;
    }

    /* access modifiers changed from: private */
    public String getUid() {
        IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
        if (iAccountService == null) {
            return "";
        }
        ant e = iAccountService.e();
        if (e == null) {
            return "";
        }
        return e.a;
    }
}

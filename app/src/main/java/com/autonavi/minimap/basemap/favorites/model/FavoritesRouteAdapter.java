package com.autonavi.minimap.basemap.favorites.model;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.R;
import com.autonavi.sdk.log.util.LogConstant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FavoritesRouteAdapter extends BaseAdapter {
    /* access modifiers changed from: private */
    public Map<String, String> checkedList = new HashMap();
    private Context context = AMapAppGlobal.getApplication();
    private boolean isEditMode;
    private LayoutInflater mLayoutInflater = LayoutInflater.from(this.context);
    /* access modifiers changed from: private */
    public coz mListener;
    /* access modifiers changed from: private */
    public bid mPageContext;
    private List<String> routeItems;

    static class a {
        View a;
        ImageView b;
        CheckBox c;
        TextView d;
        TextView e;
        ImageView f;
        View g;
        View h;

        private a() {
        }

        /* synthetic */ a(byte b2) {
            this();
        }
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public FavoritesRouteAdapter(bid bid, List<String> list) {
        this.mPageContext = bid;
        this.routeItems = list;
    }

    public void setFavoritesListActionListener(coz coz) {
        this.mListener = coz;
    }

    public int getCheckedCount() {
        return this.checkedList.size();
    }

    public Map<String, String> getCheckedItems() {
        return this.checkedList;
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

    public int getCount() {
        if (this.routeItems != null) {
            return this.routeItems.size();
        }
        return 0;
    }

    public cpc getItem(int i) {
        if (this.routeItems != null && i < this.routeItems.size()) {
            cpc cpc = new cpc(this.routeItems.get(i));
            if (cpc.d != null) {
                return cpc;
            }
        }
        return null;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        a aVar;
        if (view == null) {
            view = this.mLayoutInflater.inflate(R.layout.save_route_item, null);
            aVar = new a(0);
            aVar.b = (ImageView) view.findViewById(R.id.image_status);
            aVar.c = (CheckBox) view.findViewById(R.id.check);
            aVar.d = (TextView) view.findViewById(R.id.main_des);
            aVar.e = (TextView) view.findViewById(R.id.sub_des);
            aVar.f = (ImageView) view.findViewById(R.id.image_edit);
            aVar.g = view.findViewById(R.id.view_divider_part);
            aVar.h = view.findViewById(R.id.view_divider_all);
            aVar.a = view.findViewById(R.id.layout_save_route_item);
            view.setTag(aVar);
        } else {
            aVar = (a) view.getTag();
        }
        if (this.routeItems == null || i >= this.routeItems.size() - 1) {
            aVar.g.setVisibility(8);
            aVar.h.setVisibility(0);
        } else {
            aVar.g.setVisibility(0);
            aVar.h.setVisibility(8);
        }
        cpc item = getItem(i);
        if (item != null) {
            TextView textView = aVar.d;
            String str = "";
            switch (item.d.routeType) {
                case 0:
                    String str2 = item.d.fromName;
                    String str3 = item.d.toName;
                    if (!TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3)) {
                        String str4 = item.d.routeName;
                        if (str4 != null && str4.length() > 0) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(str4);
                            sb.append("（");
                            sb.append(str2);
                            sb.append(" - ");
                            sb.append(str3);
                            sb.append("）");
                            str = sb.toString();
                            break;
                        }
                    } else {
                        String str5 = item.d.routeName;
                        if (str5 != null && str5.length() > 0) {
                            str = str5.replace("--", " - ");
                            break;
                        }
                    }
                    str = "";
                    break;
                case 1:
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(item.d.fromName);
                    sb2.append("  →  ");
                    sb2.append(item.d.toName);
                    str = sb2.toString();
                    break;
                case 2:
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(item.d.fromName);
                    sb3.append("  →  ");
                    sb3.append(item.d.toName);
                    str = sb3.toString();
                    break;
                case 3:
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(item.d.fromName);
                    sb4.append("  →  ");
                    sb4.append(item.d.toName);
                    str = sb4.toString();
                    break;
            }
            textView.setText(str);
            String str6 = "";
            switch (item.d.routeType) {
                case 0:
                    String str7 = item.d.startTime;
                    String str8 = item.d.endTime;
                    if (cpc.b(str7) && cpc.b(str8)) {
                        String a2 = cpc.a(str7);
                        String a3 = cpc.a(str8);
                        StringBuilder sb5 = new StringBuilder();
                        sb5.append(a2.substring(0, a2.length() - 2));
                        sb5.append(":");
                        sb5.append(a2.substring(a2.length() - 2, a2.length()));
                        String sb6 = sb5.toString();
                        StringBuilder sb7 = new StringBuilder();
                        sb7.append(a3.substring(0, a3.length() - 2));
                        sb7.append(":");
                        sb7.append(a3.substring(a3.length() - 2, a3.length()));
                        String sb8 = sb7.toString();
                        StringBuilder sb9 = new StringBuilder("首车:");
                        sb9.append(sb6);
                        sb9.append("  末车:");
                        sb9.append(sb8);
                        str6 = sb9.toString();
                        break;
                    } else {
                        str6 = "";
                        break;
                    }
                    break;
                case 1:
                    String[] a4 = cpc.a(cpc.c(item.d.len));
                    StringBuilder sb10 = new StringBuilder();
                    sb10.append(a4[0]);
                    sb10.append(a4[1]);
                    String sb11 = sb10.toString();
                    int b = item.b();
                    if (b > 0) {
                        StringBuilder sb12 = new StringBuilder("全程约");
                        sb12.append(sb11);
                        sb12.append("  ");
                        sb12.append(cpc.b(b));
                        str6 = sb12.toString();
                        break;
                    } else {
                        str6 = "";
                        break;
                    }
                case 2:
                    str6 = item.c();
                    break;
                case 3:
                    String[] a5 = cpc.a(cpc.c(item.d.len));
                    StringBuilder sb13 = new StringBuilder();
                    sb13.append(a5[0]);
                    sb13.append(a5[1]);
                    str6 = "全程约".concat(String.valueOf(sb13.toString()));
                    break;
            }
            if (str6.length() == 0) {
                aVar.e.setVisibility(8);
            } else {
                aVar.e.setVisibility(0);
                aVar.e.setText(str6);
            }
            if (this.isEditMode) {
                bindEditView(aVar, item);
            } else {
                bindView(aVar, item);
            }
        }
        return view;
    }

    private void bindView(a aVar, final cpc cpc) {
        aVar.b.setVisibility(0);
        ImageView imageView = aVar.b;
        int i = R.drawable.ic_save_car;
        switch (cpc.d.routeType) {
            case 0:
                i = R.drawable.ic_save_bus;
                break;
            case 1:
                i = R.drawable.ic_save_car;
                break;
            case 2:
                i = R.drawable.ic_save_bus;
                break;
            case 3:
                i = R.drawable.ic_save_foot;
                break;
        }
        imageView.setImageResource(i);
        aVar.c.setVisibility(8);
        aVar.a.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                bti d = cpg.c(cpm.b().a()).d(cpc.d.itemId);
                if (cpc != null && d != null) {
                    LogManager.actionLogV2(LogConstant.PAGE_SAVE_MAIN_ROUTE, "B003");
                    if (cpc.d.routeType == 1) {
                        dhz dhz = (dhz) ank.a(dhz.class);
                        if (dhz != null) {
                            PageBundle pageBundle = new PageBundle();
                            pageBundle.putObject("bundle_key_save_route", d);
                            dhz.a(pageBundle);
                        }
                        return;
                    }
                    int i = d.c;
                    if (i != 0) {
                        switch (i) {
                            case 2:
                            case 4:
                                atb atb = (atb) defpackage.esb.a.a.a(atb.class);
                                if (atb != null) {
                                    atb.e().a(FavoritesRouteAdapter.this.mPageContext, (cor) d, i);
                                    return;
                                }
                                break;
                            case 3:
                                avl avl = (avl) defpackage.esb.a.a.a(avl.class);
                                if (avl != null) {
                                    FavoritesRouteAdapter.this.mPageContext;
                                    avl.a(d);
                                    return;
                                }
                                break;
                        }
                    } else {
                        asy asy = (asy) defpackage.esb.a.a.a(asy.class);
                        if (asy != null) {
                            asz c = asy.c();
                            FavoritesRouteAdapter.this.mPageContext;
                            c.a((cor) d);
                        }
                    }
                }
            }
        });
    }

    private void bindEditView(final a aVar, final cpc cpc) {
        aVar.c.setVisibility(0);
        aVar.b.setVisibility(8);
        boolean containsKey = this.checkedList.containsKey(cpc.a());
        aVar.c.setOnCheckedChangeListener(null);
        aVar.a.setOnClickListener(null);
        aVar.c.setChecked(containsKey);
        aVar.c.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (FavoritesRouteAdapter.this.mListener != null) {
                    if (!z) {
                        FavoritesRouteAdapter.this.checkedList.remove(cpc.a());
                    } else {
                        FavoritesRouteAdapter.this.checkedList.put(cpc.a(), cpc.d.itemId);
                    }
                    FavoritesRouteAdapter.this.mListener.onItemCheckedChangeListener();
                }
            }
        });
        aVar.a.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                aVar.c.setChecked(!aVar.c.isChecked());
            }
        });
    }
}

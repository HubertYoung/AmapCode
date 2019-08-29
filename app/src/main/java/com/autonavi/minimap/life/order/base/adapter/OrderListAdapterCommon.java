package com.autonavi.minimap.life.order.base.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.template.AbstractViewHolderBaseAdapter;
import com.autonavi.minimap.R;
import com.autonavi.sdk.log.util.LogConstant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderListAdapterCommon extends AbstractViewHolderBaseAdapter<dpl, a> {
    private static final String TAG = "OrderListAdapterCommon";
    private boolean isEdit = false;
    /* access modifiers changed from: private */
    public OnCheckedChangeListener mCheckedChangeListener;
    /* access modifiers changed from: private */
    public Context mContext;
    /* access modifiers changed from: private */
    public bid mPageContext;
    /* access modifiers changed from: private */
    public List<String> oids = new ArrayList();

    public static class a extends com.autonavi.map.template.AbstractViewHolderAdapter.a {
        public TextView a;
        public TextView b;
        public TextView c;
        public TextView d;
        public CheckBox e;
        public View f;
        public TextView g;
        public View h;
        public View i;

        public a(View view) {
            super(view);
            this.a = (TextView) view.findViewById(R.id.order_shop_name);
            this.b = (TextView) view.findViewById(R.id.order_item_left_top);
            this.c = (TextView) view.findViewById(R.id.order_item_left_bottom);
            this.d = (TextView) view.findViewById(R.id.order_status);
            this.e = (CheckBox) view.findViewById(R.id.select_checkbox);
            this.f = view.findViewById(R.id.poi_tips_tel);
            this.g = (TextView) view.findViewById(R.id.poi_tips_tel_text);
            this.h = view.findViewById(R.id.poi_tips_route);
            this.i = view.findViewById(R.id.poi_tips_navi);
        }
    }

    public OrderListAdapterCommon(bid bid) {
        this.mPageContext = bid;
        this.mContext = DoNotUseTool.getContext();
    }

    public void onBindViewHolderWithData(final a aVar, final dpl dpl, int i, int i2) {
        if (aVar != null && dpl != null) {
            if (!TextUtils.isEmpty(dpl.d())) {
                aVar.b.setText(dpl.d());
            }
            if (!TextUtils.isEmpty(dpl.e())) {
                aVar.c.setText(dpl.e());
            }
            if (!TextUtils.isEmpty(dpl.b())) {
                aVar.a.setText(dpl.b());
            }
            if (!TextUtils.isEmpty(dpl.f())) {
                aVar.d.setText(dpl.f());
            }
            aVar.d.setTextColor(this.mContext.getResources().getColor(dpl.i()));
            aVar.e.setVisibility(this.isEdit ? 0 : 8);
            aVar.e.setChecked(dpl.a());
            aVar.e.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    boolean isChecked = aVar.e.isChecked();
                    dpl.a(isChecked);
                    dpl dpl = dpl;
                    if (!isChecked) {
                        OrderListAdapterCommon.this.oids.remove(dpl.m());
                    } else if (!OrderListAdapterCommon.this.oids.contains(dpl.m())) {
                        OrderListAdapterCommon.this.oids.add(dpl.m());
                    }
                    if (OrderListAdapterCommon.this.mCheckedChangeListener != null) {
                        OrderListAdapterCommon.this.mCheckedChangeListener.onCheckedChanged(aVar.e, isChecked);
                    }
                }
            });
            AnonymousClass2 r6 = new OnClickListener() {
                public final void onClick(View view) {
                    int id = view.getId();
                    if (id == R.id.poi_tips_navi) {
                        OrderListAdapterCommon.this.mPageContext;
                        POI k = dpl.k();
                        dfm dfm = (dfm) ank.a(dfm.class);
                        if (dfm != null) {
                            dfm.a(k);
                        }
                        LogManager.actionLogV2("P00043", "B011");
                    } else if (id != R.id.poi_tips_tel) {
                        if (id == R.id.poi_tips_route) {
                            OrderListAdapterCommon.this.mPageContext;
                            POI k2 = dpl.k();
                            bck bck = (bck) defpackage.esb.a.a.a(bck.class);
                            if (bck != null) {
                                bck.e().a(k2);
                            }
                            LogManager.actionLogV2("P00043", "B010");
                        }
                    } else if (TextUtils.isEmpty(dpl.l())) {
                        ToastHelper.showToast(OrderListAdapterCommon.this.mContext.getString(R.string.common_telphone_null));
                    } else {
                        String l = dpl.l();
                        String[] split = l.split(",");
                        if (split.length > 1) {
                            bnz.a(Arrays.asList(split), OrderListAdapterCommon.this.mPageContext.getActivity(), (String) LogConstant.PAGE_ID_ORDER_VIEWPOINT);
                        } else {
                            OrderListAdapterCommon.this.mContext;
                            bnz.a(l);
                        }
                        LogManager.actionLogV2("P00043", "B009");
                    }
                }
            };
            aVar.i.setOnClickListener(r6);
            if (TextUtils.isEmpty(dpl.l())) {
                aVar.g.setTextColor(this.mContext.getResources().getColor(R.color.f_c_4));
                Drawable drawable = this.mContext.getResources().getDrawable(R.drawable.tips_tel_null);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                aVar.g.setCompoundDrawables(drawable, null, null, null);
            } else {
                aVar.g.setTextColor(this.mContext.getResources().getColor(R.color.f_c_3));
                Drawable drawable2 = this.mContext.getResources().getDrawable(R.drawable.tips_tel);
                drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
                aVar.g.setCompoundDrawables(drawable2, null, null, null);
            }
            aVar.f.setOnClickListener(r6);
            aVar.h.setOnClickListener(r6);
        }
    }

    public View onCreateView(ViewGroup viewGroup, int i) {
        return LayoutInflater.from(this.mContext).inflate(R.layout.order_hotel_list_item_new, viewGroup, false);
    }

    public a onCreateViewHolder(View view, ViewGroup viewGroup, int i) {
        return new a(view);
    }

    public void setEdit(boolean z) {
        this.isEdit = z;
    }

    public void setSelectAll(boolean z) {
        List<dpl> data = getData();
        if (data != null && !data.isEmpty()) {
            for (dpl dpl : data) {
                dpl.a(z);
                if (z && !this.oids.contains(dpl.m())) {
                    this.oids.add(dpl.m());
                }
            }
        }
        if (!z) {
            this.oids.clear();
        }
    }

    public boolean isSelectAll() {
        List<dpl> data = getData();
        if (data == null || data.isEmpty()) {
            return false;
        }
        boolean z = true;
        for (dpl dpl : data) {
            if (!z || !dpl.a()) {
                z = false;
                continue;
            } else {
                z = true;
                continue;
            }
            if (!z) {
                break;
            }
        }
        return z;
    }

    public String getOids() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < this.oids.size(); i++) {
            stringBuffer.append(this.oids.get(i));
            if (i != this.oids.size() - 1) {
                stringBuffer.append(",");
            }
        }
        return stringBuffer.toString();
    }

    public void setOnCheckedChangedListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.mCheckedChangeListener = onCheckedChangeListener;
    }
}

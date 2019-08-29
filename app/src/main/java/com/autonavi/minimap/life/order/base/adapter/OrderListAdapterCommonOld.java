package com.autonavi.minimap.life.order.base.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import com.autonavi.minimap.R;
import java.util.ArrayList;
import java.util.Iterator;

public class OrderListAdapterCommonOld extends BaseAdapter {
    private boolean isEdit = false;
    /* access modifiers changed from: private */
    public OnCheckedChangeListener mCheckedChangedListener;
    private Context mContext;
    private ArrayList<dpl> mList;
    /* access modifiers changed from: private */
    public ArrayList<String> oids = new ArrayList<>();

    public static final class a {
        public TextView a;
        public TextView b;
        public TextView c;
        public TextView d;
        public TextView e;
        public CheckBox f;
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public OrderListAdapterCommonOld(Context context, ArrayList<dpl> arrayList) {
        this.mContext = context;
        if (arrayList == null || arrayList.size() <= 0) {
            this.mList = new ArrayList<>();
        } else {
            this.mList = arrayList;
        }
    }

    public OrderListAdapterCommonOld(Context context) {
        this.mContext = context;
    }

    public int getCount() {
        return this.mList.size();
    }

    public Object getItem(int i) {
        return this.mList.get(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        return loadOrderItem(this.mList.get(i), i, view, viewGroup);
    }

    private View loadOrderItem(final dpl dpl, int i, View view, ViewGroup viewGroup) {
        final a aVar = new a();
        View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.order_base_list_item, null);
        aVar.a = (TextView) inflate.findViewById(R.id.voucher_item_name);
        aVar.b = (TextView) inflate.findViewById(R.id.voucher_left_center);
        aVar.c = (TextView) inflate.findViewById(R.id.voucher_item_order_flag_caption);
        aVar.d = (TextView) inflate.findViewById(R.id.voucher_left_top);
        aVar.e = (TextView) inflate.findViewById(R.id.voucher_left_bottom);
        aVar.f = (CheckBox) inflate.findViewById(R.id.select_checkbox);
        if (!TextUtils.isEmpty(dpl.b())) {
            aVar.a.setText(dpl.b().replaceAll("\\(.*\\)|\\（.*\\）", ""));
        } else {
            aVar.a.setText("");
        }
        if (!TextUtils.isEmpty(dpl.c())) {
            aVar.b.setText(dpl.c());
        } else {
            aVar.b.setText("");
        }
        setLeftCenterTextColor(aVar.b, dpl.g());
        setLeftBottomText(aVar.e, dpl.e());
        setExpiringTime(aVar.d, dpl);
        setOrderFlagColor(aVar.c, dpl.h());
        aVar.c.setText(dpl.f());
        aVar.f.setChecked(dpl.a());
        aVar.f.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                boolean isChecked = aVar.f.isChecked();
                dpl.a(isChecked);
                String m = dpl.m();
                if (!isChecked) {
                    OrderListAdapterCommonOld.this.oids.remove(m);
                } else if (!OrderListAdapterCommonOld.this.oids.contains(m)) {
                    OrderListAdapterCommonOld.this.oids.add(m);
                }
                if (OrderListAdapterCommonOld.this.mCheckedChangedListener != null) {
                    OrderListAdapterCommonOld.this.mCheckedChangedListener.onCheckedChanged(aVar.f, isChecked);
                }
            }
        });
        if (this.isEdit) {
            aVar.f.setVisibility(0);
        } else {
            aVar.f.setVisibility(8);
        }
        return inflate;
    }

    private void setLeftBottomText(TextView textView, String str) {
        if (textView != null) {
            if (!TextUtils.isEmpty(str)) {
                textView.setVisibility(0);
                textView.setText(str);
                return;
            }
            textView.setVisibility(8);
        }
    }

    private void setOrderFlagColor(TextView textView, String str) {
        textView.setTextColor(getOrderColor(str));
    }

    private void setLeftCenterTextColor(TextView textView, String str) {
        textView.setTextColor(getOrderColor(str));
    }

    private int getOrderColor(String str) {
        if (!TextUtils.isEmpty(str)) {
            if ("FLAG_BULE".equals(str)) {
                return this.mContext.getResources().getColor(R.color.voucher_iteam_flag_blue);
            }
            if ("FLAG_RED".equals(str)) {
                return this.mContext.getResources().getColor(R.color.voucher_iteam_flag_red);
            }
            if ("FLAG_GRAY".equals(str)) {
                return this.mContext.getResources().getColor(R.color.voucher_iteam_flag_gray);
            }
            if ("FLAG_ORANGE".equals(str)) {
                return this.mContext.getResources().getColor(R.color.voucher_iteam_flag_orange);
            }
            if ("FLAG_RED2".equals(str)) {
                return this.mContext.getResources().getColor(R.color.voucher_iteam_flag_red2);
            }
        }
        return this.mContext.getResources().getColor(R.color.gary_);
    }

    private void setExpiringTime(TextView textView, dpl dpl) {
        textView.setText(dpl.d());
    }

    public final void clearDataAndChangeDataSet() {
        if (this.mList != null) {
            this.mList.clear();
            notifyDataSetChanged();
        }
    }

    public void setDataList(ArrayList<dpl> arrayList) {
        this.mList = arrayList;
    }

    public void addDataList(ArrayList<dpl> arrayList) {
        if (this.mList == null || arrayList == null || arrayList.isEmpty()) {
            this.mList = new ArrayList<>();
            return;
        }
        if (arrayList != null && !arrayList.isEmpty()) {
            this.mList.addAll(arrayList);
        }
    }

    public void setEdit(boolean z) {
        this.isEdit = z;
    }

    public boolean isEdit() {
        return this.isEdit;
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

    public void setOnCheckChangedListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.mCheckedChangedListener = onCheckedChangeListener;
    }

    public synchronized void setSelectAll(boolean z) {
        Iterator<dpl> it = this.mList.iterator();
        while (it.hasNext()) {
            dpl next = it.next();
            next.a(z);
            if (z && !this.oids.contains(next.m())) {
                this.oids.add(next.m());
            }
        }
        if (!z) {
            this.oids.clear();
        }
    }

    public boolean isSelectAll() {
        if (this.mList == null || this.mList.isEmpty()) {
            return false;
        }
        Iterator<dpl> it = this.mList.iterator();
        boolean z = true;
        while (it.hasNext()) {
            dpl next = it.next();
            if (!z || !next.a()) {
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
}

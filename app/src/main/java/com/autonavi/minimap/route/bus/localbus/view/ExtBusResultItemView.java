package com.autonavi.minimap.route.bus.localbus.view;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.autonavi.bundle.routecommon.entity.BusPath;
import com.autonavi.bundle.routecommon.entity.ExTrainPath;
import com.autonavi.bundle.routecommon.entity.ExtBusPath;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.bus.realtimebus.adapter.ViewClickAction;
import com.j256.ormlite.stmt.query.SimpleComparison;
import java.util.ArrayList;
import java.util.Iterator;

public class ExtBusResultItemView extends LinearLayout implements dxy<ExtBusPath> {
    private LinearLayout detailLayout;
    protected dxz mClickListener;
    /* access modifiers changed from: private */
    public ExtBusPath mData;
    protected int mPositionId;
    private TextView mainDes;

    public boolean isDataChanged(ExtBusPath extBusPath) {
        return true;
    }

    public ExtBusResultItemView(Context context) {
        super(context);
    }

    public ExtBusResultItemView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ExtBusResultItemView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.detailLayout = (LinearLayout) findViewById(R.id.detail_layout);
        this.mainDes = (TextView) findViewById(R.id.main_des);
    }

    public void bindData(int i, ExtBusPath extBusPath, Bundle bundle) {
        View view;
        this.mData = extBusPath;
        this.mPositionId = i;
        LayoutInflater from = LayoutInflater.from(getContext());
        this.mainDes.setText(this.mData.getMainDes());
        ArrayList<axb> busPathList = this.mData.getBusPathList();
        ArrayList arrayList = new ArrayList();
        Iterator<axb> it = busPathList.iterator();
        while (it.hasNext()) {
            axb next = it.next();
            if ((next instanceof ExTrainPath) || (next instanceof BusPath)) {
                arrayList.add(next);
            }
        }
        int size = arrayList.size();
        this.detailLayout.removeAllViews();
        for (int i2 = 0; i2 < size; i2++) {
            axb axb = (axb) arrayList.get(i2);
            if (i2 == 0) {
                view = from.inflate(R.layout.v4_from_to_extbus_detail_top_item, null);
            } else if (i2 == size - 1) {
                view = from.inflate(R.layout.v4_from_to_extbus_detail_bottom_item, null);
            } else {
                view = from.inflate(R.layout.v4_from_to_extbus_detail_mid_item, null);
            }
            TextView textView = (TextView) view.findViewById(R.id.alter_list_des);
            ImageView imageView = (ImageView) view.findViewById(R.id.section_name_icon);
            View findViewById = view.findViewById(R.id.extbus_divide);
            if (axb instanceof BusPath) {
                imageView.setImageResource(R.drawable.route_extbus_icon_bus);
                axs.a(textView, axb.getPathDesc().replace(SimpleComparison.GREATER_THAN_OPERATION, " > "), R.drawable.bus_result_item_main_des_next);
                textView.getPaint().setFakeBoldText(true);
                this.detailLayout.addView(view);
            } else if (axb instanceof ExTrainPath) {
                imageView.setImageResource(R.drawable.route_extbus_icon_train);
                textView.setText(((ExTrainPath) axb).getPathSpannableDesc());
                this.detailLayout.addView(view);
            }
            if (size == 1) {
                findViewById.setVisibility(8);
            }
        }
    }

    public ExtBusPath getData() {
        return this.mData;
    }

    public void setOnViewClickListener(dxz dxz) {
        this.mClickListener = dxz;
        NoDBClickUtil.a((View) this, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                if (ExtBusResultItemView.this.mClickListener != null) {
                    ExtBusResultItemView.this.mClickListener.onViewClicked(ExtBusResultItemView.this.mPositionId, ViewClickAction.ROUTE_RESULT_EXT_BUS_ITEM, ExtBusResultItemView.this.mData, view);
                }
            }
        });
    }
}

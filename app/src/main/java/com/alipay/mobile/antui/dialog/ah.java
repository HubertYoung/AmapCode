package com.alipay.mobile.antui.dialog;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AUTextView;

/* compiled from: AUOperationResultDialog */
final class ah extends BaseAdapter {
    final /* synthetic */ AUOperationResultDialog a;

    private ah(AUOperationResultDialog aUOperationResultDialog) {
        this.a = aUOperationResultDialog;
    }

    /* synthetic */ ah(AUOperationResultDialog x0, byte b) {
        this(x0);
    }

    public final int getCount() {
        return this.a.mItemList.size();
    }

    public final Object getItem(int position) {
        return this.a.mItemList.get(position);
    }

    public final long getItemId(int position) {
        return (long) position;
    }

    public final View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new AUTextView(this.a.getContext());
            ((AUTextView) convertView).setTextAppearance(this.a.getContext(), R.style.dialogBottomButtonStyle);
            ((AUTextView) convertView).setGravity(17);
            convertView.setLayoutParams(new LayoutParams(-1, this.a.getContext().getResources().getDimensionPixelSize(R.dimen.AU_SPACE12)));
        }
        convertView.setBackgroundResource(a(position));
        PopMenuItem item = (PopMenuItem) this.a.mItemList.get(position);
        if (item != null) {
            ((AUTextView) convertView).setText(item.getName());
        }
        return convertView;
    }

    private int a(int position) {
        if (position == getCount() - 1) {
            return R.drawable.pop_list_corner_round_bottom;
        }
        return R.drawable.pop_list_corner_shape;
    }
}

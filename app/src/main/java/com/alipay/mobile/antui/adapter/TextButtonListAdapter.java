package com.alipay.mobile.antui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.api.OnItemClickListener;
import com.alipay.mobile.antui.basic.AUTextView;
import java.util.ArrayList;
import java.util.List;

public class TextButtonListAdapter extends BaseAdapter {
    /* access modifiers changed from: private */
    public OnItemClickListener itemListener;
    private Context mContext;
    private int mGravity;
    private List<String> mItemList = new ArrayList();
    private int mTextColor;

    public TextButtonListAdapter(int gravity, int textColor) {
        this.mGravity = gravity;
        this.mTextColor = textColor;
    }

    public void setData(Context context, List<String> itemList, OnItemClickListener listener) {
        this.mItemList.clear();
        this.mItemList.addAll(itemList);
        this.itemListener = listener;
        this.mContext = context;
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.mItemList.size();
    }

    public String getItem(int position) {
        return this.mItemList.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = initTextView();
        }
        convertView.setBackgroundResource(resolveItemBgDrawable(position));
        convertView.setOnClickListener(new i(this, position));
        ((AUTextView) convertView).setText(getItem(position));
        return convertView;
    }

    private int resolveItemBgDrawable(int position) {
        if (position == getCount() - 1) {
            return R.drawable.pop_list_corner_round_bottom;
        }
        return R.drawable.pop_list_corner_shape;
    }

    private AUTextView initTextView() {
        AUTextView textView = new AUTextView(this.mContext);
        textView.setTextColor(this.mTextColor);
        textView.setTextSize(0, (float) this.mContext.getResources().getDimensionPixelSize(R.dimen.AU_TEXTSIZE4));
        textView.setGravity(this.mGravity | 16);
        if (this.mGravity == 3) {
            textView.setPadding(this.mContext.getResources().getDimensionPixelSize(R.dimen.AU_SPACE6), 0, 0, 0);
        }
        textView.setLayoutParams(new LayoutParams(-1, this.mContext.getResources().getDimensionPixelSize(R.dimen.AU_SPACE12)));
        return textView;
    }
}

package com.amap.bundle.drive.setting.quicknaviwidget.main.dialog;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import com.autonavi.minimap.R;
import java.util.List;

public class QuickAutoNaviAdapter extends BaseAdapter {
    private String mCurNaviVoice;
    private LayoutInflater mLayoutInflater;
    private List<String> mMainBodyList;
    private Style mStyle = Style.ITEM_WITH_CHECKBOX;
    private List<String> mSubList;

    public enum Style {
        ITEM_WITH_CHECKBOX,
        ITEM_WHITHOUT_CHECKBOX
    }

    static class a {
        TextView a;
        TextView b;
        CheckBox c;

        a() {
        }
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public QuickAutoNaviAdapter(Activity activity, List<String> list, List<String> list2, String str) {
        this.mLayoutInflater = activity.getLayoutInflater();
        this.mMainBodyList = list;
        this.mSubList = list2;
        this.mCurNaviVoice = str;
    }

    public void setStyle(Style style) {
        this.mStyle = style;
    }

    public int getCount() {
        if (this.mMainBodyList == null) {
            return 0;
        }
        return this.mMainBodyList.size();
    }

    public Object getItem(int i) {
        if (this.mMainBodyList == null || this.mMainBodyList.size() == 0) {
            return null;
        }
        return this.mMainBodyList.get(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        switch (this.mStyle) {
            case ITEM_WITH_CHECKBOX:
                return buildItemWithCheckbox(i, view, viewGroup, true);
            case ITEM_WHITHOUT_CHECKBOX:
                return buildItemWithCheckbox(i, view, viewGroup, false);
            default:
                return null;
        }
    }

    private View buildItemWithCheckbox(int i, View view, ViewGroup viewGroup, boolean z) {
        View view2;
        a aVar;
        if (view == null) {
            aVar = new a();
            view2 = this.mLayoutInflater.inflate(R.layout.quickautonavi_setting_dialog_select_item, null);
            aVar.a = (TextView) view2.findViewById(R.id.main_text);
            aVar.b = (TextView) view2.findViewById(R.id.sub_text);
            aVar.c = (CheckBox) view2.findViewById(R.id.choice_checkbox);
            view2.setTag(aVar);
        } else {
            view2 = view;
            aVar = (a) view.getTag();
        }
        aVar.a.setText(this.mMainBodyList.get(i));
        if (this.mSubList == null || this.mSubList.size() <= 0 || this.mSubList.get(i) == null) {
            aVar.b.setVisibility(8);
        } else {
            aVar.b.setVisibility(0);
            aVar.b.setText(this.mSubList.get(i));
        }
        if (!z) {
            aVar.c.setVisibility(4);
        } else if (this.mMainBodyList.get(i).equals(this.mCurNaviVoice)) {
            aVar.c.setChecked(true);
        } else {
            aVar.c.setChecked(false);
        }
        return view2;
    }
}

package com.alipay.mobile.beehive.compositeui.wheelview.picker;

import android.app.Activity;
import android.content.res.Resources;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.alipay.mobile.antui.utils.DensityUtil;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.compositeui.dialog.CustomDialog;
import com.alipay.mobile.beehive.compositeui.wheelview.widget.WheelView;
import com.alipay.mobile.beehive.compositeui.wheelview.widget.WheelView.OnWheelViewListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OptionPickerDialog extends CustomDialog {
    private Activity activity;
    /* access modifiers changed from: private */
    public OnOptionPickListener onOptionPickListener;
    private WheelView optionView;
    protected ArrayList<String> options;
    /* access modifiers changed from: private */
    public int selectedIndex;
    /* access modifiers changed from: private */
    public String selectedOption;

    public interface OnOptionPickListener {
        void onOptionPicked(String str, int i);
    }

    public OptionPickerDialog(Activity activity2, String title, String positiveString, String negativeString, String[] options2) {
        this(activity2, title, positiveString, negativeString, Arrays.asList(options2));
    }

    public OptionPickerDialog(Activity activity2, String title, String positiveString, String negativeString, List options2) {
        super(activity2, title, positiveString, negativeString);
        this.options = new ArrayList<>();
        this.selectedOption = "";
        this.selectedIndex = -1;
        this.activity = activity2;
        this.options.addAll(options2);
        setPositiveListener(new OnClickListener() {
            public final void onClick(View view) {
                if (OptionPickerDialog.this.onOptionPickListener != null) {
                    OptionPickerDialog.this.onOptionPickListener.onOptionPicked(OptionPickerDialog.this.selectedOption, OptionPickerDialog.this.selectedIndex);
                }
            }
        });
        init();
    }

    private void init() {
        TextView titleView = getTitleView();
        MarginLayoutParams lp = (MarginLayoutParams) titleView.getLayoutParams();
        if (lp == null) {
            lp = new MarginLayoutParams(-2, -2);
        }
        int margin = DensityUtil.dip2px(getContext(), 14.0f);
        lp.setMargins(margin, margin, margin, margin);
        titleView.setLayoutParams(lp);
        initContentView();
    }

    public void setSelectedIndex(int index) {
        this.selectedIndex = index;
        int i = 0;
        while (true) {
            if (i >= this.options.size()) {
                break;
            } else if (index == i) {
                this.selectedOption = this.options.get(index);
                break;
            } else {
                i++;
            }
        }
        this.optionView.setSelectedItem(this.selectedOption);
    }

    public void setOnOptionPickListener(OnOptionPickListener listener) {
        this.onOptionPickListener = listener;
    }

    public View getContentView() {
        if (this.options == null || this.options.size() == 0) {
            return null;
        }
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(1);
        layout.setGravity(48);
        layout.setLayoutParams(new LayoutParams(-1, -2));
        addLine(layout);
        addWheelView(layout);
        return layout;
    }

    private void addWheelView(LinearLayout layout) {
        this.optionView = new WheelView(this.activity);
        this.optionView.setTextSize(16);
        this.optionView.setTextColor(getResources().getColor(R.color.wheelview_textcolor_normal), getResources().getColor(R.color.wheelview_textcolor_focus));
        this.optionView.setLineVisible(true);
        this.optionView.setLineColor(getResources().getColor(R.color.wheelview_linecolor));
        this.optionView.setOffset(1);
        layout.addView(this.optionView, new LayoutParams(-1, -2));
        this.optionView.setItems(this.options);
        this.optionView.setOnWheelViewListener(new OnWheelViewListener() {
            public final void onSelected(boolean isUserScroll, int selectedIndex, String item) {
                OptionPickerDialog.this.selectedOption = item;
                OptionPickerDialog.this.selectedIndex = selectedIndex;
            }
        });
    }

    private void addLine(LinearLayout layout) {
        View line = new View(this.activity);
        LayoutParams lp = new LayoutParams(-1, 1);
        lp.setMargins(DensityUtil.dip2px(getContext(), 10.0f), 0, DensityUtil.dip2px(getContext(), 10.0f), DensityUtil.dip2px(getContext(), 20.0f));
        line.setBackgroundResource(R.color.linecolor);
        layout.addView(line, lp);
    }

    public Resources getResources() {
        return getContext().getResources();
    }

    public String getSelectedOption() {
        return this.selectedOption;
    }

    public int getSelectedIndex() {
        return this.selectedIndex;
    }
}

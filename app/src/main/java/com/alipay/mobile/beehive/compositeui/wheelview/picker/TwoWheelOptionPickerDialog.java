package com.alipay.mobile.beehive.compositeui.wheelview.picker;

import android.app.Activity;
import android.content.res.Resources;
import android.support.annotation.NonNull;
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

public class TwoWheelOptionPickerDialog extends CustomDialog {
    private Activity activity;
    private WheelView mLeftOptionView;
    protected List<String> mLeftOptions;
    /* access modifiers changed from: private */
    public int mLeftSelectedIndex;
    /* access modifiers changed from: private */
    public String mLeftSelectedOption;
    private WheelView mRightOptionView;
    protected List<String> mRightOptions;
    /* access modifiers changed from: private */
    public int mRightSelectedIndex;
    /* access modifiers changed from: private */
    public String mRightSelectedOption;
    /* access modifiers changed from: private */
    public OnOptionPickListener onOptionPickListener;

    public interface OnOptionPickListener {
        void onOptionPicked(String str, int i, String str2, int i2);
    }

    public TwoWheelOptionPickerDialog(Activity activity2, String title, String positiveString, String negativeString, String[] leftOptions, String[] rightOptions) {
        this(activity2, title, positiveString, negativeString, Arrays.asList(leftOptions), Arrays.asList(rightOptions));
    }

    public TwoWheelOptionPickerDialog(Activity activity2, String title, String positiveString, String negativeString, List leftOptions, List rightOptions) {
        super(activity2, title, positiveString, negativeString);
        this.mRightOptions = new ArrayList();
        this.mLeftOptions = new ArrayList();
        this.mLeftSelectedOption = "";
        this.mLeftSelectedIndex = -1;
        this.mRightSelectedOption = "";
        this.mRightSelectedIndex = -1;
        this.activity = activity2;
        this.mLeftOptions.addAll(leftOptions);
        this.mRightOptions.addAll(rightOptions);
        setPositiveListener(new OnClickListener() {
            public final void onClick(View view) {
                if (TwoWheelOptionPickerDialog.this.onOptionPickListener != null) {
                    TwoWheelOptionPickerDialog.this.onOptionPickListener.onOptionPicked(TwoWheelOptionPickerDialog.this.mLeftSelectedOption, TwoWheelOptionPickerDialog.this.mLeftSelectedIndex, TwoWheelOptionPickerDialog.this.mRightSelectedOption, TwoWheelOptionPickerDialog.this.mRightSelectedIndex);
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

    public void setRightSelected(String selectedOption) {
        this.mRightSelectedOption = selectedOption;
        this.mRightOptionView.setSelectedItem(selectedOption);
        this.mRightSelectedIndex = this.mRightOptionView.getSelectedIndex();
    }

    public void setRightSelectedIndex(int selectedOptionIndex) {
        if (this.mRightOptionView != null) {
            this.mRightSelectedIndex = selectedOptionIndex;
            this.mRightSelectedOption = this.mRightOptions.get(selectedOptionIndex);
            this.mRightOptionView.setSelectedItem(this.mRightSelectedOption);
        }
    }

    public void setLeftSelected(String selectedOption) {
        this.mLeftSelectedOption = selectedOption;
        this.mLeftOptionView.setSelectedItem(selectedOption);
        this.mLeftSelectedIndex = this.mRightOptionView.getSelectedIndex();
    }

    public void setLeftSelectedIndex(int selectedOptionIndex) {
        if (this.mLeftOptionView != null) {
            this.mLeftSelectedIndex = selectedOptionIndex;
            this.mLeftSelectedOption = this.mLeftOptions.get(selectedOptionIndex);
            this.mLeftOptionView.setSelectedItem(this.mLeftSelectedOption);
        }
    }

    public void setOnOptionPickListener(OnOptionPickListener listener) {
        this.onOptionPickListener = listener;
    }

    public View getContentView() {
        if (this.mRightOptions == null || this.mRightOptions.size() == 0) {
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
        LinearLayout wheelLayout = new LinearLayout(getContext());
        wheelLayout.setOrientation(0);
        wheelLayout.setGravity(48);
        this.mLeftOptionView = getWheelView(this.mLeftOptions, new OnWheelViewListener() {
            public final void onSelected(boolean isUserScroll, int selectedIndex, String item) {
                TwoWheelOptionPickerDialog.this.mLeftSelectedOption = item;
                TwoWheelOptionPickerDialog.this.mLeftSelectedIndex = selectedIndex;
            }
        });
        this.mRightOptionView = getWheelView(this.mRightOptions, new OnWheelViewListener() {
            public final void onSelected(boolean isUserScroll, int selectedIndex, String item) {
                TwoWheelOptionPickerDialog.this.mRightSelectedOption = item;
                TwoWheelOptionPickerDialog.this.mRightSelectedIndex = selectedIndex;
            }
        });
        addWheelToLL(wheelLayout, this.mLeftOptionView);
        addWheelToLL(wheelLayout, this.mRightOptionView);
        LayoutParams lp = new LayoutParams(-1, 500);
        lp.setMargins(DensityUtil.dip2px(getContext(), 40.0f), 0, DensityUtil.dip2px(getContext(), 40.0f), 0);
        layout.addView(wheelLayout, lp);
    }

    private void addWheelToLL(LinearLayout wheelLayout, WheelView wheelView) {
        LayoutParams childviewLP = (LayoutParams) wheelView.getLayoutParams();
        if (childviewLP == null) {
            childviewLP = new LayoutParams(0, -2);
        }
        childviewLP.weight = 1.0f;
        wheelLayout.addView(wheelView, childviewLP);
    }

    @NonNull
    private WheelView getWheelView(List<String> options, OnWheelViewListener onWheelViewListener) {
        WheelView optionView = new WheelView(this.activity);
        optionView.setTextSize(16);
        optionView.setTextColor(getResources().getColor(R.color.wheelview_textcolor_normal), getResources().getColor(R.color.wheelview_textcolor_focus));
        optionView.setLineVisible(true);
        optionView.setLineColor(getResources().getColor(R.color.wheelview_linecolor));
        optionView.setOffset(1);
        optionView.setItems(options);
        optionView.setOnWheelViewListener(onWheelViewListener);
        return optionView;
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

    public String getRightSelectedOption() {
        return this.mRightSelectedOption;
    }

    public int getRightSelectedIndex() {
        return this.mRightSelectedIndex;
    }

    public int getLeftSelectedIndex() {
        return this.mLeftSelectedIndex;
    }

    public String getLeftSelectedOption() {
        return this.mLeftSelectedOption;
    }
}

package com.alipay.mobile.antui.picker;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import com.alipay.mobile.antui.basic.AULinearLayout;
import com.alipay.mobile.antui.model.OptionPickerModel;
import java.util.ArrayList;
import java.util.List;

public class AUOptionPicker extends AUWheelPicker {
    private int LINKAGE_NUM = 0;
    private List<OptionPickerModel> optionModels = new ArrayList();
    private OptionPickerListener optionPickerListener;
    private List<AUWheelView> wheelViews = new ArrayList();

    public interface OptionPickerListener {
        void onOptionPicked(List<OptionPickerModel> list);
    }

    public AUOptionPicker(Activity activity) {
        super(activity);
    }

    /* access modifiers changed from: protected */
    @NonNull
    public View makeCenterView() {
        AULinearLayout layout = new AULinearLayout(this.activity);
        layout.setOrientation(0);
        layout.setGravity(17);
        for (int i = 0; i < this.LINKAGE_NUM; i++) {
            AUWheelView wheelView = new AUWheelView(this.activity);
            wheelView.setTextSize(this.textSize);
            wheelView.setLineVisible(this.lineVisible);
            wheelView.setOffset(this.offset);
            wheelView.setTag(new Integer(i));
            int size = this.wheelViews.size();
            if (size != 0) {
                this.wheelViews.get(size - 1).register(wheelView);
            }
            this.wheelViews.add(wheelView);
            layout.addView((View) wheelView, i);
        }
        if (this.wheelViews != null && this.wheelViews.size() > 0) {
            AUWheelView tmpWheelView = this.wheelViews.get(0);
            int i2 = 0;
            while (tmpWheelView != null) {
                OptionPickerModel tmpModels = this.optionModels.get(i2);
                if (!(tmpModels == null || tmpModels.optionStr == null)) {
                    tmpWheelView.setItems(tmpModels.optionStr, tmpModels.selected);
                    tmpWheelView.setOnWheelViewListener(new q(this, tmpModels));
                    i2++;
                    tmpWheelView = tmpWheelView.next;
                }
            }
        }
        return layout;
    }

    public void setDateData(List<OptionPickerModel> pickerModelList) {
        if (pickerModelList != null && pickerModelList.size() != 0) {
            this.optionModels.clear();
            this.optionModels.addAll(pickerModelList);
            this.LINKAGE_NUM = pickerModelList.size();
        }
    }

    /* access modifiers changed from: protected */
    public void onSubmit() {
        if (this.optionPickerListener != null) {
            this.optionPickerListener.onOptionPicked(this.optionModels);
        }
    }

    public void setOptionPickerListener(OptionPickerListener listener) {
        this.optionPickerListener = listener;
    }
}

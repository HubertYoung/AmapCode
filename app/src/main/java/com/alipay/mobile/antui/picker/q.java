package com.alipay.mobile.antui.picker;

import com.alipay.mobile.antui.model.OptionPickerModel;
import com.alipay.mobile.antui.picker.AUWheelView.OnWheelViewListener;

/* compiled from: AUOptionPicker */
final class q implements OnWheelViewListener {
    final /* synthetic */ OptionPickerModel a;
    final /* synthetic */ AUOptionPicker b;

    q(AUOptionPicker this$0, OptionPickerModel optionPickerModel) {
        this.b = this$0;
        this.a = optionPickerModel;
    }

    public final void onSelected(boolean isUserScroll, int selectedIndex, String item) {
        this.a.selected = selectedIndex;
    }
}

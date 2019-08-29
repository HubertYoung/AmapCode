package com.alipay.mobile.antui.picker;

import com.alipay.mobile.antui.model.PickerDataModel;
import com.alipay.mobile.antui.picker.AUWheelView.OnWheelViewListener;
import java.util.List;

/* compiled from: AUCascadePicker */
final class a implements OnWheelViewListener {
    final /* synthetic */ AUWheelView a;
    final /* synthetic */ AUCascadePicker b;

    a(AUCascadePicker this$0, AUWheelView aUWheelView) {
        this.b = this$0;
        this.a = aUWheelView;
    }

    public final void onSelected(boolean isUserScroll, int selectedIndex, String item) {
        List tmpModels = this.a.getCurData();
        if (tmpModels != null && selectedIndex >= 0 && selectedIndex < tmpModels.size()) {
            if (isUserScroll) {
                this.a.setSelectedModel(selectedIndex);
            } else if (tmpModels != null) {
                PickerDataModel model = tmpModels.get(selectedIndex);
                this.b.curModels = model.subList;
                if (this.a != null && this.a.next != null) {
                    if (model == null || model.subList == null || model.subList.size() == 0) {
                        this.b.setLinkageInVisible(this.a);
                        return;
                    }
                    this.b.setLinkageVisible(this.a);
                    this.a.next.setPickerDateModel(this.b.curModels);
                    this.a.next.setSelectedModel(this.b.getCurWheelIndex(this.a.next, this.b.selectedList, this.b.curModels));
                }
            }
        }
    }
}

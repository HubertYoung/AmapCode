package com.alipay.mobile.antui.picker;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AULinearLayout;
import com.alipay.mobile.antui.model.PickerDataModel;
import com.alipay.mobile.antui.utils.AuiLogger;
import java.util.ArrayList;
import java.util.List;

public class AUCascadePicker extends AUWheelPicker {
    private static String TAG = AUCascadePicker.class.getSimpleName();
    private int LINKAGE_NUM = 0;
    private Context context;
    private List<String> curList = new ArrayList();
    List<PickerDataModel> curModels = new ArrayList();
    private OnLinkagePickerListener linkagePickerListener;
    List<String> selectedList = new ArrayList();
    private List<AUWheelView> wheelViews = new ArrayList();

    public interface OnLinkagePickerListener {
        void onLinkagePicked(PickerDataModel pickerDataModel);
    }

    public AUCascadePicker(Activity activity) {
        super(activity);
        this.context = activity;
    }

    public void setDateData(List<PickerDataModel> strList) {
        if (strList != null && strList.size() != 0) {
            for (int i = 0; i < strList.size(); i++) {
                if (strList.get(i) != null) {
                    this.curModels.add(strList.get(i));
                    this.curList.add(strList.get(i).name);
                }
            }
            this.LINKAGE_NUM = getLinkageNum(strList);
            AuiLogger.info(TAG, "getData size:" + this.LINKAGE_NUM);
        }
    }

    private int getLinkageNum(List<PickerDataModel> models) {
        if (models == null || models.size() == 0) {
            return 0;
        }
        for (PickerDataModel model : models) {
            if (model != null) {
                return getLinkageNum(model.subList) + 1;
            }
        }
        return 0;
    }

    public void setSelectedItem(PickerDataModel model) {
        while (model != null) {
            if (model != null) {
                this.selectedList.add(model.name);
                if (model.subList == null || model.subList.size() == 0) {
                    model = null;
                } else {
                    model = model.subList.get(0);
                }
            }
        }
        for (int i = 0; i < this.wheelViews.size(); i++) {
            this.wheelViews.get(i).getCurData();
        }
    }

    /* access modifiers changed from: private */
    public int getCurWheelIndex(AUWheelView wheelView, List<String> models, List<PickerDataModel> curModels2) {
        if (models == null || wheelView == null) {
            return 0;
        }
        if (wheelView.getTag() != null) {
            int selected = ((Integer) wheelView.getTag()).intValue();
            try {
                if (selected < models.size() && selected >= 0) {
                    for (int i = 0; i < curModels2.size(); i++) {
                        PickerDataModel model = curModels2.get(i);
                        if (model != null && TextUtils.equals(models.get(selected), model.name)) {
                            return i;
                        }
                    }
                }
            } catch (Exception e) {
                return 0;
            }
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    @NonNull
    public View makeCenterView() {
        View view = LayoutInflater.from(this.activity).inflate(R.layout.au_linkage_picker_view, null);
        AULinearLayout layout = (AULinearLayout) view.findViewById(R.id.linkage_picker);
        layout.setGravity(17);
        for (int i = 0; i < this.LINKAGE_NUM; i++) {
            AUWheelView wheelView = new AUWheelView(this.context);
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
            for (AUWheelView tmpWheelView = this.wheelViews.get(0); tmpWheelView != null; tmpWheelView = tmpWheelView.next) {
                tmpWheelView.setPickerDateModel(this.curModels);
                tmpWheelView.setSelectedModel(getCurWheelIndex(tmpWheelView, this.selectedList, this.curModels));
                tmpWheelView.setOnWheelViewListener(new a(this, tmpWheelView));
            }
        }
        return view;
    }

    /* access modifiers changed from: private */
    public void setLinkageInVisible(AUWheelView wheelView) {
        while (wheelView != null && wheelView.next != null) {
            wheelView.next.setVisibility(4);
            wheelView = wheelView.next;
        }
    }

    /* access modifiers changed from: private */
    public void setLinkageVisible(AUWheelView wheelView) {
        while (wheelView != null && wheelView.next != null) {
            wheelView.next.setVisibility(0);
            wheelView = wheelView.next;
        }
    }

    private PickerDataModel getSeledtedMsg() {
        PickerDataModel resModel = new PickerDataModel();
        List list = new ArrayList();
        for (int i = 0; i < this.wheelViews.size(); i++) {
            AUWheelView wheelView = this.wheelViews.get(i);
            PickerDataModel model = wheelView.getSelectModel();
            if (model != null && wheelView.getVisibility() == 0) {
                model.subList = null;
                list.add(model);
            }
        }
        int size = list.size();
        int i2 = size - 1;
        PickerDataModel lastModel = new PickerDataModel();
        lastModel.name = ((PickerDataModel) list.get(i2)).name;
        while (i2 != -1) {
            PickerDataModel curModel = (PickerDataModel) list.get(i2);
            if (i2 == size - 1) {
                resModel.name = lastModel.name;
                resModel.subList = lastModel.subList;
            } else {
                resModel.name = curModel.name;
                List subs = new ArrayList();
                subs.add(lastModel);
                resModel.subList = subs;
            }
            lastModel = new PickerDataModel();
            lastModel.name = resModel.name;
            lastModel.subList = resModel.subList;
            i2--;
        }
        return resModel;
    }

    /* access modifiers changed from: protected */
    public void onSubmit() {
        if (this.linkagePickerListener != null) {
            this.linkagePickerListener.onLinkagePicked(getSeledtedMsg());
        }
    }

    public void setOnLinkagePickerListener(OnLinkagePickerListener listener) {
        this.linkagePickerListener = listener;
    }
}

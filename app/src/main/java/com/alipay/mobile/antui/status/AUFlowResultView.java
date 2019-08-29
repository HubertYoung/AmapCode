package com.alipay.mobile.antui.status;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AULinearLayout;
import java.util.List;

public class AUFlowResultView extends AULinearLayout {
    public AUFlowResultView(Context context) {
        super(context);
        init();
    }

    public AUFlowResultView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        int padding = getResources().getDimensionPixelOffset(R.dimen.AU_SPACE5);
        setPadding(padding, padding, padding, padding);
        setLayoutParams(layoutParams);
        setOrientation(1);
    }

    public void clearFlows() {
        removeAllViews();
    }

    public void setFlows(List<FlowResult> flowResultList) {
        boolean z;
        if (flowResultList == null) {
            return;
        }
        if (flowResultList.size() == 1) {
            flowResultList.get(0).setPosition(3);
            addFlow(flowResultList.get(0), false, false);
            return;
        }
        int i = 0;
        while (i < flowResultList.size()) {
            FlowResult flowResult = flowResultList.get(i);
            if (i == 0) {
                flowResult.setPosition(0);
            } else if (i == flowResultList.size() - 1) {
                flowResult.setPosition(2);
            } else {
                flowResult.setPosition(1);
            }
            if (i <= 0 || flowResultList.get(i - 1).subTitles == null || flowResultList.get(i - 1).subTitles.size() < 3) {
                addFlow(flowResult, i > 0, false);
            } else {
                if (i > 0) {
                    z = true;
                } else {
                    z = false;
                }
                addFlow(flowResult, z, true);
            }
            i++;
        }
    }

    private void addFlow(FlowResult flowResult, boolean needTopMargin, boolean lengthen) {
        int dimensionPixelSize;
        FlowStepView flowView = new FlowStepView(getContext());
        flowView.setFlowResult(flowResult, lengthen);
        LayoutParams params = new LayoutParams(-2, -2);
        if (needTopMargin) {
            if (lengthen) {
                dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.flow_step_view_lengthen_margin_top);
            } else {
                dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.flow_step_view_normal_margin_top);
            }
            params.topMargin = dimensionPixelSize;
        }
        addView((View) flowView, (ViewGroup.LayoutParams) params);
    }
}

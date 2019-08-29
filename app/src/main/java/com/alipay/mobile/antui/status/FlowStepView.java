package com.alipay.mobile.antui.status;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alipay.mobile.antui.R;

public class FlowStepView extends RelativeLayout {
    private View bottomLineView;
    private TextView forthInfoTextView;
    private ImageView indicatorImageView;
    private TextView mainInfoTextView;
    private TextView secondaryInfoTextView;
    private TextView thirdInfoTextView;
    private View topLineView;

    public FlowStepView(Context context) {
        super(context);
        init(context);
    }

    public FlowStepView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FlowStepView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private int getIndicatorBgColor(int status) {
        switch (status) {
            case 2:
            case 32:
                return getResources().getColor(R.color.AU_COLOR16);
            case 4:
                return getResources().getColor(R.color.AU_COLOR_TEXT_DISABLE);
            case 8:
                return getResources().getColor(R.color.AU_COLOR_ERROR);
            case 16:
                return getResources().getColor(R.color.AU_COLOR_APP_GREEN);
            default:
                return getResources().getColor(R.color.AU_COLOR_TEXT_DISABLE);
        }
    }

    private int getMainTextColor(int status) {
        switch (status) {
            case 2:
                return getResources().getColor(R.color.AU_COLOR_MAIN_CONTENT);
            case 4:
                return getResources().getColor(R.color.AU_COLOR_MAIN_CONTENT);
            case 8:
                return getResources().getColor(R.color.AU_COLOR_ERROR);
            case 16:
                return getResources().getColor(R.color.AU_COLOR_APP_GREEN);
            case 32:
                return getResources().getColor(R.color.AU_COLOR_MAIN_CONTENT);
            default:
                return getResources().getColor(R.color.AU_COLOR_MAIN_CONTENT);
        }
    }

    private int getIndicatorIconId(ResultStatusIcon statusIcon) {
        switch (a.a[statusIcon.ordinal()]) {
            case 1:
                return R.drawable.result_status_rmb;
            case 2:
                return R.drawable.result_status_calc;
            case 3:
                return R.drawable.result_status_yes;
            case 4:
                return R.drawable.result_status_no;
            case 5:
                return R.drawable.result_status_pending;
            default:
                return 0;
        }
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.au_flow_step, this, true);
        this.topLineView = findViewById(R.id.top_line);
        this.bottomLineView = findViewById(R.id.bottom_line);
        this.indicatorImageView = (ImageView) findViewById(R.id.flow_indicator);
        this.mainInfoTextView = (TextView) findViewById(R.id.main_info_text);
        this.secondaryInfoTextView = (TextView) findViewById(R.id.flow_secondary_info);
        this.thirdInfoTextView = (TextView) findViewById(R.id.flow_third_info);
        this.forthInfoTextView = (TextView) findViewById(R.id.flow_forth_info);
    }

    public void setFlowResult(FlowResult flowResult, boolean lengthen) {
        boolean isBottom = false;
        if (flowResult.getPosition() == 0) {
            this.topLineView.setVisibility(8);
            this.bottomLineView.setVisibility(0);
            this.bottomLineView.setBackgroundColor(getIndicatorBgColor(flowResult.resultStatus));
        } else if (flowResult.getPosition() == 1) {
            this.topLineView.setVisibility(0);
            this.topLineView.setBackgroundColor(getIndicatorBgColor(flowResult.resultStatus));
            this.bottomLineView.setVisibility(0);
            this.bottomLineView.setBackgroundColor(getIndicatorBgColor(flowResult.resultStatus));
        } else if (flowResult.getPosition() == 2) {
            isBottom = true;
            this.topLineView.setVisibility(0);
            this.topLineView.setBackgroundColor(getIndicatorBgColor(flowResult.resultStatus));
            this.bottomLineView.setVisibility(8);
        } else if (flowResult.getPosition() == 3) {
            isBottom = true;
            this.topLineView.setVisibility(8);
            this.bottomLineView.setVisibility(8);
        }
        if (lengthen) {
            LayoutParams layoutParams = this.topLineView.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new RelativeLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.flow_step_line_width), getResources().getDimensionPixelSize(R.dimen.flow_step_line_width));
            } else {
                layoutParams.height = getResources().getDimensionPixelSize(R.dimen.flow_step_line_width);
            }
            this.topLineView.setLayoutParams(layoutParams);
        }
        if (flowResult.statusIconId != 0) {
            ((GradientDrawable) this.indicatorImageView.getBackground()).setColor(getResources().getColor(17170445));
            this.indicatorImageView.setImageResource(flowResult.statusIconId);
        } else {
            ((GradientDrawable) this.indicatorImageView.getBackground()).setColor(getIndicatorBgColor(flowResult.resultStatus));
            this.indicatorImageView.setImageResource(getIndicatorIconId(flowResult.statusIcon));
        }
        if (!TextUtils.isEmpty(flowResult.mainInfoText)) {
            this.mainInfoTextView.setText(flowResult.mainInfoText);
            this.mainInfoTextView.setTextColor(getMainTextColor(flowResult.resultStatus));
            this.mainInfoTextView.setVisibility(0);
        } else {
            this.mainInfoTextView.setVisibility(8);
        }
        String secondaryInfoText = null;
        String thirdInfoText = null;
        String forthInfoText = null;
        if (flowResult.subTitles != null) {
            if (flowResult.subTitles.size() > 0) {
                secondaryInfoText = flowResult.subTitles.get(0);
            }
            if (flowResult.subTitles.size() > 1) {
                thirdInfoText = flowResult.subTitles.get(1);
            }
            if (flowResult.subTitles.size() > 2) {
                forthInfoText = flowResult.subTitles.get(2);
            }
        }
        if (!TextUtils.isEmpty(secondaryInfoText)) {
            this.secondaryInfoTextView.setText(secondaryInfoText);
            this.secondaryInfoTextView.setVisibility(0);
        } else if (isBottom) {
            this.secondaryInfoTextView.setVisibility(8);
        } else {
            this.secondaryInfoTextView.setText("");
            this.secondaryInfoTextView.setVisibility(0);
        }
        if (!TextUtils.isEmpty(thirdInfoText)) {
            this.thirdInfoTextView.setText(thirdInfoText);
            this.thirdInfoTextView.setVisibility(0);
        } else if (isBottom) {
            this.thirdInfoTextView.setVisibility(8);
        } else {
            this.thirdInfoTextView.setText("");
            this.thirdInfoTextView.setVisibility(0);
        }
        if (!TextUtils.isEmpty(forthInfoText)) {
            this.forthInfoTextView.setText(forthInfoText);
            this.forthInfoTextView.setVisibility(0);
            LayoutParams params = this.bottomLineView.getLayoutParams();
            if (params == null) {
                params = new RelativeLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.flow_step_line_width), getResources().getDimensionPixelSize(R.dimen.AU_SPACE8));
            } else {
                params.height = getResources().getDimensionPixelSize(R.dimen.flow_step_line_width);
            }
            this.bottomLineView.setLayoutParams(params);
        } else if (isBottom) {
            this.forthInfoTextView.setVisibility(8);
        } else {
            this.forthInfoTextView.setText("");
            this.forthInfoTextView.setVisibility(0);
        }
    }
}

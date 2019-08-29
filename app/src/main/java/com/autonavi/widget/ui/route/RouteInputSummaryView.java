package com.autonavi.widget.ui.route;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.autonavi.minimap.R;

public class RouteInputSummaryView extends LinearLayout {
    private RouteInputLineView mEndLineView;
    private RouteInputLineView mPassLineView;
    private RouteInputLineView mStartLineView;

    public void exchangeAnimator() {
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return true;
    }

    public RouteInputSummaryView(Context context) {
        this(context, null);
    }

    public RouteInputSummaryView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RouteInputSummaryView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    private void init(Context context) {
        setOrientation(1);
        setClipToPadding(false);
        setClipChildren(false);
        this.mStartLineView = new RouteInputLineView(context);
        this.mStartLineView.setFlagText(context.getString(R.string.route_input_from));
        this.mStartLineView.setTextSize(1, 13.0f);
        this.mEndLineView = new RouteInputLineView(context);
        this.mEndLineView.setFlagText(context.getString(R.string.route_input_to));
        this.mEndLineView.setTextSize(1, 13.0f);
        this.mPassLineView = new RouteInputLineView(context);
        this.mPassLineView.setFlagText(context.getString(R.string.route_input_pass));
        this.mPassLineView.setTextSize(1, 13.0f);
        addViews(this.mStartLineView, this.mPassLineView, this.mEndLineView);
        addTextWatchers();
    }

    public RouteInputLineView getStartLineView() {
        return this.mStartLineView;
    }

    public RouteInputLineView getEndLineView() {
        return this.mEndLineView;
    }

    public RouteInputLineView getPassLineView() {
        return this.mPassLineView;
    }

    private void addTextWatchers() {
        addTextWatcher(this.mStartLineView, R.string.route_input_from);
        addTextWatcher(this.mPassLineView, R.string.route_input_pass);
        addTextWatcher(this.mEndLineView, R.string.route_input_to);
    }

    private void addTextWatcher(final RouteInputLineView routeInputLineView, final int i) {
        routeInputLineView.getTextView().addTextChangedListener(new TextWatcher() {
            public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public final void afterTextChanged(Editable editable) {
                if (editable == null || TextUtils.isEmpty(editable)) {
                    routeInputLineView.setImageDrawable(RouteInputSummaryView.this.getResources().getDrawable(R.drawable.route_icon_input_warning));
                } else {
                    routeInputLineView.setFlagText(RouteInputSummaryView.this.getResources().getString(i));
                }
            }
        });
    }

    public void setHint(CharSequence charSequence, CharSequence charSequence2) {
        this.mStartLineView.setHint(charSequence);
        this.mEndLineView.setHint(charSequence2);
    }

    private void addViews(View... viewArr) {
        for (View addView : viewArr) {
            addView(addView, new LayoutParams(-1, 0, 1.0f));
        }
    }

    public void setOnRouteInputClickListener(eru eru) {
        this.mStartLineView.setOnRouteInputClickListener(eru);
        this.mPassLineView.setOnRouteInputClickListener(eru);
        this.mEndLineView.setOnRouteInputClickListener(eru);
    }

    public void setStartText(CharSequence charSequence) {
        this.mStartLineView.setText(charSequence);
    }

    public void setEndText(CharSequence charSequence) {
        this.mEndLineView.setText(charSequence);
    }

    public void setPassText(CharSequence... charSequenceArr) {
        this.mPassLineView.setText(passTextsToString(charSequenceArr));
    }

    private int getValidPassCount(CharSequence... charSequenceArr) {
        int i = 0;
        for (CharSequence isEmpty : charSequenceArr) {
            if (!TextUtils.isEmpty(isEmpty)) {
                i++;
            }
        }
        return i;
    }

    private String passTextsToString(CharSequence... charSequenceArr) {
        StringBuilder sb = new StringBuilder();
        if (charSequenceArr == null || charSequenceArr.length == 0) {
            return sb.toString();
        }
        int validPassCount = getValidPassCount(charSequenceArr);
        if (validPassCount <= 0) {
            return sb.toString();
        }
        if (validPassCount > 1) {
            sb.append(getResources().getString(R.string.route_input_pass_count, new Object[]{Integer.valueOf(validPassCount)}));
        }
        int length = charSequenceArr.length;
        boolean z = false;
        for (int i = 0; i < length; i++) {
            if (!TextUtils.isEmpty(charSequenceArr[i])) {
                if (z) {
                    sb.append("、");
                }
                sb.append(charSequenceArr[i]);
                z = true;
            }
        }
        return sb.toString();
    }
}

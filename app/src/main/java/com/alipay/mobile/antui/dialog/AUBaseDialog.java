package com.alipay.mobile.antui.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.adapter.TextButtonListAdapter;
import com.alipay.mobile.antui.api.OnItemClickListener;
import com.alipay.mobile.antui.basic.AUDialog;
import com.alipay.mobile.antui.basic.AULinearLayout;
import com.alipay.mobile.antui.utils.ToolUtils;
import java.util.ArrayList;
import java.util.List;

public abstract class AUBaseDialog extends AUDialog {
    private AUMaxItemCornerListView buttonListView;
    private AULinearLayout buttonView;
    private AULinearLayout customView;
    private int horizonMaskSpace;
    protected LayoutInflater inflater;
    private TextButtonListAdapter listAdapter;
    protected Button mCancelBtn;
    protected Button mEnsureBtn;
    public AULinearLayout rootView;

    public abstract void initCustomView(AULinearLayout aULinearLayout);

    public abstract int initHorizonMaskSpace();

    public AUBaseDialog(Context context, int theme) {
        super(context, theme);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.inflater = LayoutInflater.from(getContext());
        this.rootView = (AULinearLayout) this.inflater.inflate(R.layout.au_base_dailog, null);
        setContentView(this.rootView);
        this.customView = (AULinearLayout) this.rootView.findViewById(R.id.dialog_custom_view);
        initCustomView(this.customView);
        setContentView(this.rootView);
        this.horizonMaskSpace = initHorizonMaskSpace();
    }

    public void show() {
        super.show();
        setWindowMaxWidth(this.horizonMaskSpace);
    }

    public void setButtonStyle(String positiveStr, String negativeStr, OnItemClickListener listener) {
        int posLength = TextUtils.isEmpty(positiveStr) ? 0 : positiveStr.length();
        int negLength = TextUtils.isEmpty(negativeStr) ? 0 : negativeStr.length();
        if (posLength != 0 || negLength != 0) {
            boolean isHorizon = true;
            if (posLength + negLength > 8) {
                TextPaint paint = new TextPaint();
                paint.setTextSize((float) getContext().getResources().getDimensionPixelSize(R.dimen.AU_TEXTSIZE4));
                if (paint.measureText(positiveStr + negativeStr) > ((float) ((ToolUtils.getScreenWidth_Height(getContext())[0] - (this.horizonMaskSpace * 2)) - getContext().getResources().getDimensionPixelSize(R.dimen.notice_max_button_width)))) {
                    isHorizon = false;
                }
            }
            if (isHorizon) {
                addHorizonButtonView(positiveStr, negativeStr, listener);
                return;
            }
            addDividerView();
            List data = new ArrayList();
            if (posLength != 0) {
                data.add(positiveStr);
            }
            if (negLength != 0) {
                data.add(negativeStr);
            }
            addListButtonView(data, listener);
        }
    }

    public void setButtonStyle(List<String> data, OnItemClickListener listener) {
        addListButtonView(data, listener);
    }

    public void setButtonStyle(BaseAdapter adapter) {
        if (this.buttonListView == null) {
            this.buttonListView = new AUMaxItemCornerListView(getContext());
            this.buttonListView.setBackgroundColor(0);
            this.buttonListView.setDivider(new ColorDrawable(getContext().getResources().getColor(R.color.AU_COLOR_DIALOG_DIVIDER_COLOR)));
            this.buttonListView.setDividerHeight(1);
            this.rootView.addView(this.buttonListView);
        }
        this.buttonListView.setAdapter(adapter);
    }

    public void addHorizonButtonView(String positiveStr, String negativeStr, OnItemClickListener listener) {
        if (this.buttonView == null) {
            this.buttonView = (AULinearLayout) this.inflater.inflate(R.layout.au_base_dialog_button, null);
            this.mEnsureBtn = (Button) this.buttonView.findViewById(R.id.ensure);
            this.mCancelBtn = (Button) this.buttonView.findViewById(R.id.cancel);
            this.rootView.addView(this.buttonView);
        }
        if (TextUtils.isEmpty(negativeStr)) {
            this.mCancelBtn.setVisibility(8);
        } else {
            this.mCancelBtn.setVisibility(0);
            this.mCancelBtn.setText(negativeStr);
        }
        this.mCancelBtn.setOnClickListener(new g(this, listener));
        if (TextUtils.isEmpty(positiveStr)) {
            this.mEnsureBtn.setVisibility(8);
        } else {
            this.mEnsureBtn.setText(positiveStr);
            this.mEnsureBtn.setVisibility(0);
        }
        this.mEnsureBtn.setOnClickListener(new h(this, listener));
    }

    public void addListButtonView(List<String> data, OnItemClickListener listener) {
        if (this.buttonListView == null) {
            this.buttonListView = new AUMaxItemCornerListView(getContext());
            this.buttonListView.setBackgroundColor(0);
            this.buttonListView.setDivider(new ColorDrawable(getContext().getResources().getColor(R.color.AU_COLOR_DIALOG_DIVIDER_COLOR)));
            this.buttonListView.setDividerHeight(1);
            this.listAdapter = new TextButtonListAdapter(17, getContext().getResources().getColor(R.color.AU_COLOR_LINK));
            this.buttonListView.setAdapter(this.listAdapter);
            this.rootView.addView(this.buttonListView);
        }
        this.listAdapter.setData(getContext(), data, listener);
    }

    public TextView getButtonItem(int position) {
        if (this.buttonView != null) {
            return position == 0 ? this.mEnsureBtn : this.mCancelBtn;
        }
        if (this.buttonListView != null) {
            return (TextView) this.buttonListView.getChildAt(position);
        }
        return null;
    }

    public void addDividerView() {
        View divider = new View(getContext());
        divider.setBackgroundColor(getContext().getResources().getColor(R.color.AU_COLOR_DIALOG_DIVIDER_COLOR));
        divider.setLayoutParams(new LayoutParams(-1, 1));
        this.rootView.addView(divider);
    }
}

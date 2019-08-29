package com.amap.bundle.planhome.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.minimap.R;

public class UgcBusResultEditView extends LinearLayout {
    private TextWatcher changedListener = new TextWatcher() {
        public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public final void afterTextChanged(Editable editable) {
            if (editable.length() == 0) {
                UgcBusResultEditView.this.mTvSubmit.setTextColor(UgcBusResultEditView.this.getResources().getColor(R.color.f_c_6_c));
                UgcBusResultEditView.this.mTvSubmit.setClickable(false);
                UgcBusResultEditView.this.mTvCount.setText(UgcBusResultEditView.this.getResources().getString(R.string.route_ugc_bus_result_edit_count));
                UgcBusResultEditView.this.mEt.setHint(UgcBusResultEditView.this.getResources().getString(R.string.route_ugc_bus_result_edit_hint));
            } else if (editable.length() < 5) {
                UgcBusResultEditView.this.mTvSubmit.setTextColor(UgcBusResultEditView.this.getResources().getColor(R.color.f_c_6_c));
                UgcBusResultEditView.this.mTvSubmit.setClickable(false);
                UgcBusResultEditView.this.mTvCount.setText(UgcBusResultEditView.this.getResources().getString(R.string.route_ugc_bus_result_edit_count));
            } else {
                UgcBusResultEditView.this.mTvSubmit.setTextColor(UgcBusResultEditView.this.getResources().getColor(R.color.f_c_6));
                UgcBusResultEditView.this.mTvSubmit.setClickable(true);
                UgcBusResultEditView.this.mTvCount.setText(editable);
                String string = UgcBusResultEditView.this.getResources().getString(R.string.route_ugc_bus_result_edit_count_remain);
                UgcBusResultEditView.this.mTvCount.setText(String.format(string, new Object[]{Integer.valueOf(100 - editable.length())}));
                if (editable.length() >= 100) {
                    ToastHelper.showToast(UgcBusResultEditView.this.getResources().getString(R.string.route_ugc_bus_result_edit_count_fill));
                }
            }
        }
    };
    private OnClickListener listener = new OnClickListener() {
        public final void onClick(View view) {
            int id = view.getId();
            if (id == R.id.tv_cancle) {
                if (UgcBusResultEditView.this.mListener != null) {
                    UgcBusResultEditView.this.mListener.a();
                }
            } else if (id == R.id.tv_submit && UgcBusResultEditView.this.mListener != null && UgcBusResultEditView.this.mEt != null && UgcBusResultEditView.this.mEt.getText().length() >= 5) {
                UgcBusResultEditView.this.mListener.a(UgcBusResultEditView.this.mEt.getText().toString().trim());
            }
        }
    };
    /* access modifiers changed from: private */
    public EditText mEt;
    /* access modifiers changed from: private */
    public a mListener;
    private RelativeLayout mRlBg;
    private TextView mTvCancle;
    /* access modifiers changed from: private */
    public TextView mTvCount;
    /* access modifiers changed from: private */
    public TextView mTvSubmit;

    public UgcBusResultEditView(Context context) {
        super(context);
        initView();
    }

    public UgcBusResultEditView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.route_ugc_bus_result_edit, this, true);
        this.mRlBg = (RelativeLayout) findViewById(R.id.rl_ugc_busresult_edit_bg);
        this.mRlBg.setOnTouchListener(new OnTouchListener() {
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                if (UgcBusResultEditView.this.mListener != null) {
                    UgcBusResultEditView.this.mListener.a();
                }
                return true;
            }
        });
        findViewById(R.id.ll_bottom).setOnTouchListener(new OnTouchListener() {
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        this.mTvSubmit = (TextView) findViewById(R.id.tv_submit);
        this.mTvCancle = (TextView) findViewById(R.id.tv_cancle);
        this.mTvCount = (TextView) findViewById(R.id.tv_count);
        this.mEt = (EditText) findViewById(R.id.et_ugc_bus_result);
        NoDBClickUtil.a((View) this.mTvSubmit, this.listener);
        NoDBClickUtil.a((View) this.mTvCancle, this.listener);
        this.mEt.addTextChangedListener(this.changedListener);
    }

    public void setEtHindText(String str) {
        if (this.mEt != null) {
            this.mEt.setHint(str);
        }
    }

    public void setEtText(String str) {
        if (this.mEt != null) {
            this.mEt.setText(str);
        }
    }

    public void setEtFocusable() {
        if (this.mEt != null) {
            this.mEt.setFocusable(true);
            this.mEt.requestFocus();
        }
    }

    public EditText getEditText() {
        if (this.mEt != null) {
            return this.mEt;
        }
        return null;
    }

    public void setOnUgcBusResultEditListener(a aVar) {
        this.mListener = aVar;
    }
}

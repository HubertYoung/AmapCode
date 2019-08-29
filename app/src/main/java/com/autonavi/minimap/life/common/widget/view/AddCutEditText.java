package com.autonavi.minimap.life.common.widget.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;

public class AddCutEditText extends LinearLayout {
    /* access modifiers changed from: private */
    public int MaxSize = 9999;
    /* access modifiers changed from: private */
    public int MinSize = 1;
    /* access modifiers changed from: private */
    public Button bAdd;
    /* access modifiers changed from: private */
    public Button bReduce;
    /* access modifiers changed from: private */
    public TextView mEditText;
    /* access modifiers changed from: private */
    public a onChangeListener;

    public interface a {
    }

    public AddCutEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void setOnChangeListener(a aVar) {
        this.onChangeListener = aVar;
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(getContext()).inflate(R.layout.add_cut_edittext, this);
        init_widget();
        addListener();
    }

    public void setAmount(int i) {
        setText(i);
    }

    /* access modifiers changed from: private */
    public void setText(int i) {
        TextView textView = this.mEditText;
        StringBuilder sb = new StringBuilder();
        sb.append(i);
        sb.append(AMapAppGlobal.getApplication().getResources().getString(R.string.add_cut_day));
        textView.setText(sb.toString());
    }

    public void init_widget() {
        this.mEditText = (TextView) findViewById(R.id.et01);
        this.bAdd = (Button) findViewById(R.id.bt01);
        this.bReduce = (Button) findViewById(R.id.bt02);
        setText(this.MinSize);
        this.bReduce.setEnabled(false);
        this.mEditText.addTextChangedListener(new TextWatcher() {
            public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public final void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(AddCutEditText.this.mEditText.getText().toString())) {
                    int parseInt = Integer.parseInt(AddCutEditText.this.mEditText.getText().toString().replace(AMapAppGlobal.getApplication().getString(R.string.add_cut_day), ""));
                    if (AddCutEditText.this.onChangeListener != null) {
                        AddCutEditText.this.onChangeListener;
                        Integer.valueOf(parseInt);
                    }
                    if (parseInt >= AddCutEditText.this.MaxSize) {
                        AddCutEditText.this.bAdd.setEnabled(false);
                    } else {
                        AddCutEditText.this.bAdd.setEnabled(true);
                    }
                    if (parseInt <= AddCutEditText.this.MinSize) {
                        AddCutEditText.this.bReduce.setEnabled(false);
                        return;
                    }
                    AddCutEditText.this.bReduce.setEnabled(true);
                }
            }
        });
    }

    public void addListener() {
        this.bAdd.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                if (AddCutEditText.this.onChangeListener != null) {
                    AddCutEditText.this.onChangeListener;
                }
                AddCutEditText.this.bReduce.setEnabled(true);
                int parseInt = Integer.parseInt(AddCutEditText.this.mEditText.getText().toString().replace(AMapAppGlobal.getApplication().getString(R.string.add_cut_day), ""));
                if (parseInt < AddCutEditText.this.MaxSize) {
                    AddCutEditText.this.setText(parseInt + 1);
                }
            }
        });
        this.bReduce.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                AddCutEditText.this.bAdd.setEnabled(true);
                int parseInt = Integer.parseInt(AddCutEditText.this.mEditText.getText().toString().replace(AMapAppGlobal.getApplication().getString(R.string.add_cut_day), ""));
                if (parseInt > AddCutEditText.this.MinSize) {
                    AddCutEditText.this.setText(parseInt - 1);
                    if (AddCutEditText.this.onChangeListener != null) {
                        AddCutEditText.this.onChangeListener;
                    }
                }
            }
        });
    }

    public void setMaxSize(int i) {
        if (i != 0) {
            this.MaxSize = i;
        }
    }

    public void setMinSize(int i) {
        if (i != 0) {
            this.MinSize = i;
        }
    }
}

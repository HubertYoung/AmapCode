package com.autonavi.minimap.route.train.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.autonavi.minimap.R;

public class TrainSearchInfoView extends RelativeLayout implements OnClickListener {
    /* access modifiers changed from: private */
    public ImageButton mBtnSearchClear;
    private Context mContext;
    private EditText mEditSearch;
    private RelativeLayout mSearchLayout;

    public TrainSearchInfoView(Context context) {
        super(context);
        initialize(context);
    }

    public TrainSearchInfoView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initialize(context);
    }

    private void initialize(Context context) {
        this.mContext = context;
        addViews();
    }

    private void addViews() {
        this.mSearchLayout = (RelativeLayout) LayoutInflater.from(this.mContext).inflate(R.layout.train_search_info_input_view, null);
        this.mEditSearch = (EditText) this.mSearchLayout.findViewById(R.id.search_text);
        this.mBtnSearchClear = (ImageButton) this.mSearchLayout.findViewById(R.id.search_clear);
        addView(this.mSearchLayout, new LayoutParams(-2, -2));
        this.mEditSearch.addTextChangedListener(new TextWatcher() {
            public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public final void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    TrainSearchInfoView.this.mBtnSearchClear.setVisibility(0);
                    return;
                }
                if (editable.length() == 0) {
                    TrainSearchInfoView.this.mBtnSearchClear.setVisibility(8);
                }
            }
        });
        this.mEditSearch.setOnClickListener(this);
        this.mBtnSearchClear.setOnClickListener(this);
    }

    public void onClick(View view) {
        if (this.mBtnSearchClear.equals(view)) {
            this.mEditSearch.setText("");
        }
    }
}

package com.autonavi.minimap.route.train.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
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
import com.autonavi.minimap.route.train.page.TrainSearchPage;
import com.autonavi.minimap.route.train.page.TrainSearchPage.SearchType;

public class TrainSearchStationEndView extends RelativeLayout implements OnClickListener {
    /* access modifiers changed from: private */
    public ImageButton mBtnSearchClear;
    /* access modifiers changed from: private */
    public ImageButton mBtnSubmit;
    private Context mContext;
    /* access modifiers changed from: private */
    public EditText mEditSearch;
    private RelativeLayout mSearchLayout;
    private TrainSearchPage mTrainDialog;

    public void showIatDialog() {
    }

    public TrainSearchStationEndView(Context context) {
        super(context);
        initialize(context);
    }

    public TrainSearchStationEndView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initialize(context);
    }

    private void initialize(Context context) {
        this.mContext = context;
        addViews();
    }

    private void addViews() {
        this.mSearchLayout = (RelativeLayout) LayoutInflater.from(this.mContext).inflate(R.layout.train_search_station_end_input_view, null);
        this.mEditSearch = (EditText) this.mSearchLayout.findViewById(R.id.search_end_text);
        this.mBtnSubmit = (ImageButton) this.mSearchLayout.findViewById(R.id.search_submit);
        this.mBtnSubmit.setOnClickListener(this);
        this.mBtnSearchClear = (ImageButton) this.mSearchLayout.findViewById(R.id.search_clear);
        addView(this.mSearchLayout, new LayoutParams(-2, -2));
        this.mEditSearch.addTextChangedListener(new TextWatcher() {
            public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public final void afterTextChanged(Editable editable) {
                int length = editable.length();
                if (length > 0) {
                    TrainSearchStationEndView.this.mBtnSubmit.setVisibility(8);
                    TrainSearchStationEndView.this.mBtnSearchClear.setVisibility(0);
                    TrainSearchStationEndView.this.mEditSearch.setImeOptions(3);
                    return;
                }
                if (length == 0) {
                    TrainSearchStationEndView.this.mBtnSubmit.setVisibility(0);
                    TrainSearchStationEndView.this.mBtnSearchClear.setVisibility(8);
                }
            }
        });
        this.mEditSearch.setOnClickListener(this);
        this.mBtnSearchClear.setOnClickListener(this);
    }

    public void onClick(View view) {
        if (this.mBtnSearchClear.equals(view)) {
            this.mEditSearch.setText("");
            return;
        }
        if (this.mBtnSubmit.equals(view) && TextUtils.isEmpty(this.mEditSearch.getText().toString())) {
            showIatDialog();
        }
    }

    public void setTrainDlg(TrainSearchPage trainSearchPage) {
        this.mTrainDialog = trainSearchPage;
    }

    private void setEditTextFocus() {
        if (this.mTrainDialog != null && this.mTrainDialog.d() == SearchType.TICKET_LIST) {
            int length = this.mTrainDialog.c.getText().toString().trim().length();
            int length2 = this.mTrainDialog.d.getText().toString().trim().length();
            if (length > 0 && length2 > 0) {
                this.mTrainDialog.c.requestFocus();
                this.mTrainDialog.c.setSelection(length);
                this.mTrainDialog.b();
            } else if (length == 0) {
                this.mTrainDialog.c.requestFocus();
                this.mTrainDialog.c.setSelection(length);
            } else if (length2 == 0) {
                this.mTrainDialog.d.requestFocus();
                this.mTrainDialog.d.setSelection(length2);
            }
        }
    }
}

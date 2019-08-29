package com.autonavi.widget.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.autonavi.minimap.R;

public class CellView extends RelativeLayout implements OnClickListener {
    public static final int CELL_A1 = 0;
    public static final int CELL_A2 = 1;
    public static final int CELL_A3 = 2;
    public static final int CELL_B1 = 3;
    private static final int INVALID_ID = -1;
    private View mBottomDivide;
    private CheckBox mCheckBox;
    private int mCurrentType;
    private TextView mDescText;
    private EditText mEditText;
    private ImageView mIcon;
    private OnClickListener mOnClickListener;
    private RelativeLayout mRootView;
    private TextView mSubText;
    private TextView mText;
    private View mTopDivide;

    public CellView(Context context, int i) {
        super(context);
        init(context, null, i);
    }

    public CellView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet, 0);
    }

    private void init(Context context, AttributeSet attributeSet, int i) {
        int i2;
        CharSequence charSequence;
        CharSequence charSequence2;
        boolean z;
        CharSequence charSequence3;
        CharSequence charSequence4;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.CellView);
        this.mCurrentType = i;
        if (obtainStyledAttributes != null) {
            this.mCurrentType = obtainStyledAttributes.getInt(R.styleable.CellView_cellStyle, 0);
            CharSequence text = obtainStyledAttributes.getText(R.styleable.CellView_cellText);
            CharSequence text2 = obtainStyledAttributes.getText(R.styleable.CellView_cellSubText);
            CharSequence text3 = obtainStyledAttributes.getText(R.styleable.CellView_cellDesc);
            boolean z2 = obtainStyledAttributes.getBoolean(R.styleable.CellView_cellChecked, false);
            CharSequence text4 = obtainStyledAttributes.getText(R.styleable.CellView_cellHint);
            int resourceId = obtainStyledAttributes.getResourceId(R.styleable.CellView_cellIcon, -1);
            obtainStyledAttributes.recycle();
            charSequence4 = text;
            i2 = resourceId;
            charSequence3 = text2;
            charSequence2 = text3;
            charSequence = text4;
            z = z2;
        } else {
            charSequence4 = "";
            charSequence3 = "";
            charSequence2 = "";
            charSequence = "";
            z = false;
            i2 = -1;
        }
        inflateView();
        initView(charSequence4, charSequence3, z, charSequence2, charSequence, i2);
    }

    private void inflateView() {
        LayoutInflater from = LayoutInflater.from(getContext());
        switch (this.mCurrentType) {
            case 0:
                from.inflate(R.layout.view_cell_a1, this, true);
                return;
            case 1:
                from.inflate(R.layout.view_cell_a2, this, true);
                return;
            case 2:
                from.inflate(R.layout.view_cell_a3, this, true);
                return;
            case 3:
                from.inflate(R.layout.view_cell_b1, this, true);
                break;
        }
    }

    private void initView(CharSequence charSequence, CharSequence charSequence2, boolean z, CharSequence charSequence3, CharSequence charSequence4, int i) {
        this.mRootView = (RelativeLayout) findViewById(R.id.cell_root);
        this.mTopDivide = findViewById(R.id.cell_top_divide);
        this.mBottomDivide = findViewById(R.id.cell_bottom_divide);
        this.mIcon = (ImageView) findViewById(R.id.cell_icon);
        this.mText = (TextView) findViewById(R.id.cell_text);
        this.mSubText = (TextView) findViewById(R.id.cell_sub_text);
        this.mDescText = (TextView) findViewById(R.id.cell_desc);
        this.mEditText = (EditText) findViewById(R.id.cell_edit);
        this.mCheckBox = (CheckBox) findViewById(R.id.cell_checkbox);
        setIconResource(i);
        setText(charSequence);
        setSubText(charSequence2);
        setDescText(charSequence3);
        setEditTextHint(charSequence4);
        setChecked(z);
        this.mRootView.setOnClickListener(this);
    }

    /* access modifiers changed from: 0000 */
    public void setBottomDivideColor(int i) {
        if (this.mBottomDivide != null) {
            setChildMarginLeft(this.mBottomDivide, getBottomDividePadding());
            this.mBottomDivide.setBackgroundColor(i);
        }
    }

    private void setChildMarginLeft(View view, int i) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        layoutParams.leftMargin = i;
        view.setLayoutParams(layoutParams);
    }

    /* access modifiers changed from: 0000 */
    public void setTopDivideVisibility(int i) {
        if (this.mTopDivide != null) {
            this.mTopDivide.setVisibility(i);
        }
    }

    private int getBottomDividePadding() {
        if (this.mCurrentType == 2) {
            return getResources().getDimensionPixelOffset(R.dimen.cell_view_divide_padding_2);
        }
        return getResources().getDimensionPixelOffset(R.dimen.cell_view_divide_padding_1);
    }

    public void setChecked(boolean z) {
        if (this.mCheckBox != null) {
            this.mCheckBox.setChecked(z);
        }
    }

    public void setIconResource(int i) {
        if (this.mIcon != null) {
            this.mIcon.setImageResource(i);
            this.mIcon.setVisibility(0);
        }
    }

    public void setText(CharSequence charSequence) {
        if (this.mText != null) {
            this.mText.setText(charSequence);
        }
    }

    public void setText(int i) {
        if (this.mText != null) {
            this.mText.setText(i);
        }
    }

    public void setEditTextHint(CharSequence charSequence) {
        if (this.mEditText != null && !TextUtils.isEmpty(charSequence)) {
            this.mEditText.setHint(charSequence);
            this.mEditText.setVisibility(0);
            this.mDescText.setVisibility(4);
        }
    }

    public EditText getEditText() {
        if (this.mEditText != null) {
            return this.mEditText;
        }
        throw new IllegalArgumentException("current cell style not support get edit text");
    }

    public void setSubText(int i) {
        if (this.mSubText != null && i != -1) {
            this.mSubText.setText(i);
            setRootHeight();
            this.mSubText.setVisibility(0);
        }
    }

    public void setSubText(CharSequence charSequence) {
        if (this.mSubText != null && !TextUtils.isEmpty(charSequence)) {
            this.mSubText.setText(charSequence);
            setRootHeight();
            this.mSubText.setVisibility(0);
        }
    }

    public void setDescText(CharSequence charSequence) {
        if (this.mCurrentType == 3 && this.mEditText != null) {
            this.mEditText.setVisibility(8);
        }
        if (this.mDescText != null && !TextUtils.isEmpty(charSequence)) {
            this.mDescText.setText(charSequence);
            this.mDescText.setVisibility(0);
        }
    }

    public void setDescText(int i) {
        if (this.mCurrentType == 3 && this.mEditText != null) {
            this.mEditText.setVisibility(8);
        }
        if (this.mDescText != null) {
            this.mDescText.setText(i);
        }
    }

    public void setOnCheckChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        if (this.mCheckBox != null) {
            this.mCheckBox.setOnCheckedChangeListener(onCheckedChangeListener);
        }
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    private void setRootHeight() {
        LayoutParams layoutParams = (LayoutParams) this.mRootView.getLayoutParams();
        layoutParams.height = getResources().getDimensionPixelOffset(R.dimen.cell_view_height_2);
        this.mRootView.setLayoutParams(layoutParams);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.cell_root && this.mCurrentType == 0) {
            this.mCheckBox.toggle();
            if (this.mOnClickListener != null) {
                this.mOnClickListener.onClick(view);
            }
        }
    }
}

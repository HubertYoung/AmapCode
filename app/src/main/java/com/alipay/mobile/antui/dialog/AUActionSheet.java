package com.alipay.mobile.antui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AUDialog;
import com.alipay.mobile.antui.basic.AUEmptyGoneTextView;
import com.alipay.mobile.antui.basic.AUMaxItemListView;
import com.alipay.mobile.antui.iconfont.model.MessagePopItem;
import java.util.List;

public class AUActionSheet extends AUDialog implements OnItemClickListener {
    public static final int NEGATIVE_STYLE_DEFAULT = 16;
    public static final int NEGATIVE_STYLE_NONE = 17;
    public static final String TEXT_WARNING_TYPE = "warning_text";
    private OnItemClickListener itemClickListener;
    private AUMaxItemListView mListView;
    private AUEmptyGoneTextView mMessageView;
    private View mNegativeDivider;
    private AUEmptyGoneTextView mNegativeView;
    private AUEmptyGoneTextView mTitleView;
    /* access modifiers changed from: private */
    public int normalTitleColor;
    private View rootView;
    private c sheetAdapter;
    /* access modifiers changed from: private */
    public int warningTitleColor;

    public AUActionSheet(Context context, List<MessagePopItem> list, OnItemClickListener itemClickListener2) {
        this(context, "", "", list, itemClickListener2, 16);
    }

    public AUActionSheet(Context context, List<MessagePopItem> list, OnItemClickListener itemClickListener2, int negativeStyle) {
        this(context, "", "", list, itemClickListener2, negativeStyle);
    }

    public AUActionSheet(Context context, String title, String message, List<MessagePopItem> list, OnItemClickListener itemClickListener2, int negativeStyle) {
        super(context, R.style.noTitleTransBgDialogStyle);
        if (negativeStyle == 17) {
            initView(context, title, message, list, itemClickListener2, "", null);
            return;
        }
        initView(context, title, message, list, itemClickListener2, context.getString(R.string.cancel), new a(this));
    }

    public AUActionSheet(Context context, String title, String message, List<MessagePopItem> list, OnItemClickListener itemClickListener2, String negativeString, OnClickListener negativeListener) {
        super(context, R.style.noTitleTransBgDialogStyle);
        initView(context, title, message, list, itemClickListener2, negativeString, negativeListener);
    }

    private void initView(Context context, String title, String message, List<MessagePopItem> list, OnItemClickListener itemClickListener2, String negativeString, OnClickListener negativeListener) {
        this.rootView = LayoutInflater.from(context).inflate(R.layout.au_action_sheet_view, null);
        this.mListView = (AUMaxItemListView) this.rootView.findViewById(R.id.action_sheet_list_view);
        if (!TextUtils.isEmpty(title) || !TextUtils.isEmpty(message)) {
            initTitleView(context);
            this.mTitleView.setText(title);
            this.mMessageView.setText(message);
        }
        this.sheetAdapter = new c(this, getContext(), list);
        this.mListView.setAdapter(this.sheetAdapter);
        this.mListView.setOnItemClickListener(this);
        this.mNegativeView = (AUEmptyGoneTextView) this.rootView.findViewById(R.id.action_sheet_cancel_btn);
        this.mNegativeDivider = this.rootView.findViewById(R.id.action_sheet_cancel_divider);
        this.mNegativeView.setText(negativeString);
        if (negativeListener != null) {
            this.mNegativeView.setOnClickListener(negativeListener);
        }
        if (!TextUtils.isEmpty(negativeString)) {
            this.mNegativeDivider.setVisibility(0);
        }
        this.itemClickListener = itemClickListener2;
        this.warningTitleColor = context.getResources().getColor(R.color.AU_COLOR_ERROR);
        this.normalTitleColor = context.getResources().getColor(R.color.AU_COLOR_MAIN_CONTENT);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(this.rootView);
    }

    public void show() {
        getWindow().setGravity(80);
        getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        setWindowMaxWidth(0);
        super.show();
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (this.itemClickListener != null) {
            this.itemClickListener.onItemClick(parent, view, position - this.mListView.getHeaderViewsCount(), id);
        }
    }

    private void initTitleView(Context context) {
        View headView = LayoutInflater.from(context).inflate(R.layout.view_action_sheet_head, null);
        this.mTitleView = (AUEmptyGoneTextView) headView.findViewById(R.id.action_head_title);
        this.mMessageView = (AUEmptyGoneTextView) headView.findViewById(R.id.action_head_message);
        this.mListView.addHeaderView(headView, null, false);
    }
}

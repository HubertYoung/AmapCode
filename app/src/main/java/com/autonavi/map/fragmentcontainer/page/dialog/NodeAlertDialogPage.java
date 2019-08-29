package com.autonavi.map.fragmentcontainer.page.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface.OnKeyListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import com.autonavi.annotation.PageAction;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.PageTheme.Transparent;
import com.autonavi.minimap.R;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.lang.ref.WeakReference;

@SuppressFBWarnings({"RCN_REDUNDANT_NULLCHECK_OF_NULL_VALUE", "NP_LOAD_OF_KNOWN_NULL_VALUE"})
@PageAction("amap.page.action.alert_dialog_page")
public class NodeAlertDialogPage extends AbstractBasePage<NodeAlertDialogPresenter> implements anl, Transparent {
    public static final int BUTTON_NEGATIVE = -2;
    public static final int BUTTON_NEUTRAL = -3;
    public static final int BUTTON_POSITIVE = -1;
    public static final int DIALOG_BG_COLOR = -1610612736;
    private ListAdapter mAdapter;
    /* access modifiers changed from: private */
    public Builder mBuilder;
    OnClickListener mButtonHandler = new OnClickListener() {
        public void onClick(View view) {
            Message message = (view != NodeAlertDialogPage.this.mButtonPositive || NodeAlertDialogPage.this.mButtonPositiveMessage == null) ? (view != NodeAlertDialogPage.this.mButtonNegative || NodeAlertDialogPage.this.mButtonNegativeMessage == null) ? (view != NodeAlertDialogPage.this.mButtonNeutral || NodeAlertDialogPage.this.mButtonNeutralMessage == null) ? null : Message.obtain(NodeAlertDialogPage.this.mButtonNeutralMessage) : Message.obtain(NodeAlertDialogPage.this.mButtonNegativeMessage) : Message.obtain(NodeAlertDialogPage.this.mButtonPositiveMessage);
            if (message != null) {
                message.sendToTarget();
            }
            if (NodeAlertDialogPage.this.mBuilder.mAutoFinished) {
                NodeAlertDialogPage.this.mHandler.obtainMessage(1, NodeAlertDialogPage.this).sendToTarget();
            }
        }
    };
    /* access modifiers changed from: private */
    public Button mButtonNegative;
    /* access modifiers changed from: private */
    public Message mButtonNegativeMessage;
    private CharSequence mButtonNegativeText;
    /* access modifiers changed from: private */
    public Button mButtonNeutral;
    /* access modifiers changed from: private */
    public Message mButtonNeutralMessage;
    private CharSequence mButtonNeutralText;
    /* access modifiers changed from: private */
    public Button mButtonPositive;
    /* access modifiers changed from: private */
    public Message mButtonPositiveMessage;
    private CharSequence mButtonPositiveText;
    protected boolean mCancelable = true;
    protected boolean mCanceledOnTouchOutside;
    private int mCheckedItem = -1;
    private View mCustomTitleView;
    private boolean mForceInverseBackground;
    /* access modifiers changed from: private */
    public Handler mHandler;
    private Drawable mIcon;
    private int mIconId = -1;
    private ImageView mIconView;
    private int mLastOrientation = -1;
    private ListView mListView;
    private CharSequence mMessage;
    private TextView mMessageView;
    private ScrollView mScrollView;
    private CharSequence mTitle;
    private TextView mTitleView;
    private View mView;
    private int mViewSpacingBottom;
    private int mViewSpacingLeft;
    private int mViewSpacingRight;
    private boolean mViewSpacingSpecified = false;
    private int mViewSpacingTop;
    private View realView;

    public static class Builder implements a {
        private ListAdapter mAdapter;
        /* access modifiers changed from: private */
        public boolean mAutoFinished = true;
        /* access modifiers changed from: private */
        public boolean mCancelable;
        /* access modifiers changed from: private */
        public boolean mCanceledOnTouchOutside;
        private int mCheckedItem = -1;
        private boolean[] mCheckedItems;
        /* access modifiers changed from: private */
        public OnConfirguationChangedListener mConfirguationChangedListener;
        private Context mContext;
        private Cursor mCursor;
        /* access modifiers changed from: private */
        public View mCustomTitleView;
        private boolean mForceInverseBackground;
        /* access modifiers changed from: private */
        public int mGravity = 17;
        /* access modifiers changed from: private */
        public Drawable mIcon;
        /* access modifiers changed from: private */
        public int mIconId = 0;
        /* access modifiers changed from: private */
        public boolean mIsAllScreen = false;
        private String mIsCheckedColumn;
        private boolean mIsMultiChoice;
        private boolean mIsSingleChoice;
        private CharSequence[] mItems;
        private String mLabelColumn;
        /* access modifiers changed from: private */
        public CharSequence mMessage;
        /* access modifiers changed from: private */
        public NodeDialogFragmentOnClickListener mNegativeButtonListener;
        /* access modifiers changed from: private */
        public CharSequence mNegativeButtonText;
        /* access modifiers changed from: private */
        public NodeDialogFragmentOnClickListener mNeutralButtonListener;
        /* access modifiers changed from: private */
        public CharSequence mNeutralButtonText;
        /* access modifiers changed from: private */
        public NodeDialogFragmentOnClickListener mOnCancelListener;
        private OnMultiChoiceClickListener mOnCheckboxClickListener;
        private NodeDialogFragmentOnClickListener mOnClickListener;
        /* access modifiers changed from: private */
        public NodeDialogFragmentOnClickListener mOnDismissListener;
        private OnItemSelectedListener mOnItemSelectedListener;
        private OnKeyListener mOnKeyListener;
        /* access modifiers changed from: private */
        public NodeDialogFragmentOnClickListener mPositiveButtonListener;
        /* access modifiers changed from: private */
        public CharSequence mPositiveButtonText;
        private boolean mRecycleOnMeasure = true;
        /* access modifiers changed from: private */
        public CharSequence mTitle;
        /* access modifiers changed from: private */
        public View mView;
        /* access modifiers changed from: private */
        public int mViewSpacingBottom;
        /* access modifiers changed from: private */
        public int mViewSpacingLeft;
        /* access modifiers changed from: private */
        public int mViewSpacingRight;
        /* access modifiers changed from: private */
        public boolean mViewSpacingSpecified = false;
        /* access modifiers changed from: private */
        public int mViewSpacingTop;

        public void setDialogFragment(Object obj) {
        }

        public Builder(Context context) {
            this.mContext = context;
            this.mCancelable = true;
        }

        private Context getContext() {
            return this.mContext;
        }

        public Builder setConfigurationChangedListener(OnConfirguationChangedListener onConfirguationChangedListener) {
            this.mConfirguationChangedListener = onConfirguationChangedListener;
            return this;
        }

        public Builder setFixedFullScreen(boolean z) {
            this.mIsAllScreen = z;
            return this;
        }

        public Builder setAutoFinished(boolean z) {
            this.mAutoFinished = z;
            return this;
        }

        public Builder setGravity(int i) {
            this.mGravity = i;
            return this;
        }

        public Builder setTitle(int i) {
            this.mTitle = this.mContext.getText(i);
            return this;
        }

        public Builder setTitle(CharSequence charSequence) {
            this.mTitle = charSequence;
            return this;
        }

        public Builder setCustomTitle(View view) {
            this.mCustomTitleView = view;
            return this;
        }

        public Builder setMessage(int i) {
            this.mMessage = this.mContext.getText(i);
            return this;
        }

        public Builder setMessage(CharSequence charSequence) {
            StringBuilder sb = new StringBuilder();
            sb.append(charSequence);
            sb.append("                 ");
            this.mMessage = sb.toString();
            return this;
        }

        public Builder setIcon(int i) {
            this.mIconId = i;
            return this;
        }

        public Builder setIcon(Drawable drawable) {
            this.mIcon = drawable;
            return this;
        }

        public Builder setIconAttribute(int i) {
            TypedValue typedValue = new TypedValue();
            this.mContext.getTheme().resolveAttribute(i, typedValue, true);
            this.mIconId = typedValue.resourceId;
            return this;
        }

        public Builder setPositiveButton(int i, NodeDialogFragmentOnClickListener nodeDialogFragmentOnClickListener) {
            this.mPositiveButtonText = this.mContext.getText(i);
            this.mPositiveButtonListener = nodeDialogFragmentOnClickListener;
            return this;
        }

        public Builder setPositiveButton(CharSequence charSequence, NodeDialogFragmentOnClickListener nodeDialogFragmentOnClickListener) {
            this.mPositiveButtonText = charSequence;
            this.mPositiveButtonListener = nodeDialogFragmentOnClickListener;
            return this;
        }

        public Builder setNegativeButton(int i, NodeDialogFragmentOnClickListener nodeDialogFragmentOnClickListener) {
            this.mNegativeButtonText = this.mContext.getText(i);
            this.mNegativeButtonListener = nodeDialogFragmentOnClickListener;
            return this;
        }

        public Builder setNegativeButton(CharSequence charSequence, NodeDialogFragmentOnClickListener nodeDialogFragmentOnClickListener) {
            this.mNegativeButtonText = charSequence;
            this.mNegativeButtonListener = nodeDialogFragmentOnClickListener;
            return this;
        }

        public Builder setNeutralButton(int i, NodeDialogFragmentOnClickListener nodeDialogFragmentOnClickListener) {
            this.mNeutralButtonText = this.mContext.getText(i);
            this.mNeutralButtonListener = nodeDialogFragmentOnClickListener;
            return this;
        }

        public Builder setNeutralButton(CharSequence charSequence, NodeDialogFragmentOnClickListener nodeDialogFragmentOnClickListener) {
            this.mNeutralButtonText = charSequence;
            this.mNeutralButtonListener = nodeDialogFragmentOnClickListener;
            return this;
        }

        public Builder setCancelable(boolean z) {
            this.mCancelable = z;
            return this;
        }

        public Builder setCanceledOnTouchOutside(boolean z) {
            this.mCanceledOnTouchOutside = z;
            return this;
        }

        public Builder setOnCancelListener(NodeDialogFragmentOnClickListener nodeDialogFragmentOnClickListener) {
            this.mOnCancelListener = nodeDialogFragmentOnClickListener;
            return this;
        }

        public Builder setOnDismissListener(NodeDialogFragmentOnClickListener nodeDialogFragmentOnClickListener) {
            this.mOnDismissListener = nodeDialogFragmentOnClickListener;
            return this;
        }

        public Builder setOnKeyListener(OnKeyListener onKeyListener) {
            this.mOnKeyListener = onKeyListener;
            return this;
        }

        public Builder setItems(int i, NodeDialogFragmentOnClickListener nodeDialogFragmentOnClickListener) {
            this.mItems = this.mContext.getResources().getTextArray(i);
            this.mOnClickListener = nodeDialogFragmentOnClickListener;
            return this;
        }

        public Builder setItems(CharSequence[] charSequenceArr, NodeDialogFragmentOnClickListener nodeDialogFragmentOnClickListener) {
            this.mItems = charSequenceArr;
            this.mOnClickListener = nodeDialogFragmentOnClickListener;
            return this;
        }

        public Builder setAdapter(ListAdapter listAdapter, NodeDialogFragmentOnClickListener nodeDialogFragmentOnClickListener) {
            this.mAdapter = listAdapter;
            this.mOnClickListener = nodeDialogFragmentOnClickListener;
            return this;
        }

        public Builder setCursor(Cursor cursor, NodeDialogFragmentOnClickListener nodeDialogFragmentOnClickListener, String str) {
            this.mCursor = cursor;
            this.mLabelColumn = str;
            this.mOnClickListener = nodeDialogFragmentOnClickListener;
            return this;
        }

        public Builder setMultiChoiceItems(int i, boolean[] zArr, OnMultiChoiceClickListener onMultiChoiceClickListener) {
            this.mItems = this.mContext.getResources().getTextArray(i);
            this.mOnCheckboxClickListener = onMultiChoiceClickListener;
            this.mCheckedItems = zArr;
            this.mIsMultiChoice = true;
            return this;
        }

        public Builder setMultiChoiceItems(CharSequence[] charSequenceArr, boolean[] zArr, OnMultiChoiceClickListener onMultiChoiceClickListener) {
            this.mItems = charSequenceArr;
            this.mOnCheckboxClickListener = onMultiChoiceClickListener;
            this.mCheckedItems = zArr;
            this.mIsMultiChoice = true;
            return this;
        }

        public Builder setMultiChoiceItems(Cursor cursor, String str, String str2, OnMultiChoiceClickListener onMultiChoiceClickListener) {
            this.mCursor = cursor;
            this.mOnCheckboxClickListener = onMultiChoiceClickListener;
            this.mIsCheckedColumn = str;
            this.mLabelColumn = str2;
            this.mIsMultiChoice = true;
            return this;
        }

        public Builder setSingleChoiceItems(int i, int i2, NodeDialogFragmentOnClickListener nodeDialogFragmentOnClickListener) {
            this.mItems = this.mContext.getResources().getTextArray(i);
            this.mOnClickListener = nodeDialogFragmentOnClickListener;
            this.mCheckedItem = i2;
            this.mIsSingleChoice = true;
            return this;
        }

        public Builder setSingleChoiceItems(Cursor cursor, int i, String str, NodeDialogFragmentOnClickListener nodeDialogFragmentOnClickListener) {
            this.mCursor = cursor;
            this.mOnClickListener = nodeDialogFragmentOnClickListener;
            this.mCheckedItem = i;
            this.mLabelColumn = str;
            this.mIsSingleChoice = true;
            return this;
        }

        public Builder setSingleChoiceItems(CharSequence[] charSequenceArr, int i, NodeDialogFragmentOnClickListener nodeDialogFragmentOnClickListener) {
            this.mItems = charSequenceArr;
            this.mOnClickListener = nodeDialogFragmentOnClickListener;
            this.mCheckedItem = i;
            this.mIsSingleChoice = true;
            return this;
        }

        public Builder setSingleChoiceItems(ListAdapter listAdapter, int i, NodeDialogFragmentOnClickListener nodeDialogFragmentOnClickListener) {
            this.mAdapter = listAdapter;
            this.mOnClickListener = nodeDialogFragmentOnClickListener;
            this.mCheckedItem = i;
            this.mIsSingleChoice = true;
            return this;
        }

        public Builder setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
            this.mOnItemSelectedListener = onItemSelectedListener;
            return this;
        }

        public Builder setView(View view) {
            this.mView = view;
            this.mViewSpacingSpecified = false;
            return this;
        }

        public Builder setView(View view, int i, int i2, int i3, int i4) {
            this.mView = view;
            this.mViewSpacingSpecified = true;
            this.mViewSpacingLeft = i;
            this.mViewSpacingTop = i2;
            this.mViewSpacingRight = i3;
            this.mViewSpacingBottom = i4;
            return this;
        }

        public Builder setInverseBackgroundForced(boolean z) {
            this.mForceInverseBackground = z;
            return this;
        }

        public Builder setRecycleOnMeasureEnabled(boolean z) {
            this.mRecycleOnMeasure = z;
            return this;
        }
    }

    static final class ButtonHandler extends Handler {
        private static final int MSG_DISMISS_DIALOG = 1;
        private WeakReference<NodeAlertDialogPage> mDialog;

        public ButtonHandler(NodeAlertDialogPage nodeAlertDialogPage) {
            this.mDialog = new WeakReference<>(nodeAlertDialogPage);
        }

        public final void handleMessage(Message message) {
            int i = message.what;
            if (i != 1) {
                switch (i) {
                    case -3:
                    case -2:
                    case -1:
                        ((NodeDialogFragmentOnClickListener) message.obj).onClick((NodeAlertDialogPage) this.mDialog.get());
                        return;
                }
            } else {
                ((NodeAlertDialogPage) message.obj).finish();
            }
        }
    }

    static class CanceledOnTouchOutsideListener implements OnTouchListener {
        private boolean mCanceledOnTouchOutside = false;
        private WeakReference<NodeAlertDialogPage> mFragmentRef;

        CanceledOnTouchOutsideListener(NodeAlertDialogPage nodeAlertDialogPage, boolean z) {
            this.mFragmentRef = new WeakReference<>(nodeAlertDialogPage);
            this.mCanceledOnTouchOutside = z;
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (!(!this.mCanceledOnTouchOutside || this.mFragmentRef == null || this.mFragmentRef.get() == null)) {
                ((NodeAlertDialogPage) this.mFragmentRef.get()).finish();
            }
            return true;
        }
    }

    public interface NodeDialogFragmentOnClickListener {
        void onClick(NodeAlertDialogPage nodeAlertDialogPage);
    }

    public interface OnConfirguationChangedListener {
        void onLand(NodeAlertDialogPage nodeAlertDialogPage);

        void onPort(NodeAlertDialogPage nodeAlertDialogPage);
    }

    /* access modifiers changed from: protected */
    public NodeAlertDialogPresenter createPresenter() {
        return new NodeAlertDialogPresenter(this);
    }

    public void onPageConfigurationChanged(Configuration configuration) {
        if (getResources() != null) {
            this.mLastOrientation = -1;
            onLandOrPortAction();
            executeCustomCallback();
        }
    }

    private void executeCustomCallback() {
        onOritentionChanged();
    }

    private void onOritentionChanged() {
        Resources resources = getResources();
        if (this.mBuilder.mConfirguationChangedListener != null) {
            if (resources.getConfiguration().orientation == 2) {
                this.mBuilder.mConfirguationChangedListener.onLand(this);
            } else if (resources.getConfiguration().orientation == 1) {
                this.mBuilder.mConfirguationChangedListener.onPort(this);
            }
        }
    }

    private void onLandOrPortAction() {
        Resources resources = getResources();
        if (resources != null) {
            int i = getResources().getConfiguration().orientation;
            if (!this.mBuilder.mIsAllScreen && i != this.mLastOrientation) {
                this.mLastOrientation = i;
                if (i == 2) {
                    int width = (ags.a(getActivity()).width() - (ags.a(getActivity()).height() - (resources.getDimensionPixelSize(R.dimen.node_alert_dialog_padding_left) + resources.getDimensionPixelSize(R.dimen.node_alert_dialog_padding_right)))) / 2;
                    this.realView.setPadding(width, 0, width, 0);
                } else if (i == 1) {
                    this.realView.setPadding(resources.getDimensionPixelSize(R.dimen.node_alert_dialog_padding_left), resources.getDimensionPixelSize(R.dimen.node_alert_dialog_padding_top), resources.getDimensionPixelSize(R.dimen.node_alert_dialog_padding_right), resources.getDimensionPixelSize(R.dimen.node_alert_dialog_padding_bottom));
                }
                onOritentionChanged();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onPagePerformCreateView(View view) {
        this.realView = view;
        if (!(((ViewGroup) this.realView).getChildAt(0) == null || this.mBuilder == null)) {
            LayoutParams layoutParams = ((ViewGroup) this.realView).getChildAt(0).getLayoutParams();
            if (layoutParams instanceof LinearLayout.LayoutParams) {
                LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) layoutParams;
                layoutParams2.gravity = this.mBuilder.mGravity;
                layoutParams2.setMargins(0, 0, 0, 0);
                ((ViewGroup) this.realView).getChildAt(0).setPadding(0, 0, 0, 0);
                ((ViewGroup) this.realView).getChildAt(0).setLayoutParams(layoutParams2);
            } else if (layoutParams instanceof FrameLayout.LayoutParams) {
                FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) layoutParams;
                layoutParams3.gravity = this.mBuilder.mGravity;
                layoutParams3.setMargins(0, 0, 0, 0);
                ((ViewGroup) this.realView).getChildAt(0).setPadding(0, 0, 0, 0);
                ((ViewGroup) this.realView).getChildAt(0).setLayoutParams(layoutParams3);
            }
        }
        this.mLastOrientation = -1;
        onLandOrPortAction();
        executeCustomCallback();
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        this.mHandler = new ButtonHandler(this);
        retrieveBuilder();
        onPageCreateView();
        onPagePerformCreateView(getContentView());
        onPageViewCreated(getContentView());
    }

    /* access modifiers changed from: protected */
    public void onPageCreateView() {
        View inflate = LayoutInflater.from(getContext()).inflate(onGetContentViewResId(), null);
        FrameLayout frameLayout = new FrameLayout(getContext());
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -2);
        layoutParams.gravity = 17;
        frameLayout.addView(inflate, layoutParams);
        frameLayout.setBackgroundColor(DIALOG_BG_COLOR);
        frameLayout.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        setContentView((View) frameLayout);
    }

    /* access modifiers changed from: protected */
    public int onGetContentViewResId() {
        return R.layout.node_alert_dialog_fragment;
    }

    /* access modifiers changed from: protected */
    public void retrieveBuilder() {
        PageBundle arguments = getArguments();
        if (arguments != null) {
            this.mBuilder = (Builder) arguments.getObject("builder");
            this.mBuilder.setDialogFragment(this);
        }
    }

    public void onPageViewCreated(View view) {
        apply();
        setupView(view);
        hideSystemKeyBoard();
    }

    public void onPageResume() {
        onLandOrPortAction();
    }

    public void onPageDestroy() {
        if (this.mButtonPositive != null) {
            this.mButtonPositive.setOnClickListener(null);
        }
        if (this.mButtonNegative != null) {
            this.mButtonNegative.setOnClickListener(null);
        }
        if (this.mButtonNeutral != null) {
            this.mButtonNeutral.setOnClickListener(null);
        }
    }

    public void hideSystemKeyBoard() {
        try {
            Activity activity = getActivity();
            IBinder iBinder = null;
            View currentFocus = activity != null ? activity.getCurrentFocus() : null;
            if (currentFocus != null) {
                iBinder = currentFocus.getWindowToken();
            }
            if (iBinder != null) {
                ((InputMethodManager) activity.getSystemService("input_method")).hideSoftInputFromWindow(iBinder, 0);
            }
        } catch (Exception unused) {
        }
    }

    /* access modifiers changed from: protected */
    public void setupView(View view) {
        setupContent(view, (LinearLayout) view.findViewById(R.id.contentPanel));
        boolean z = setupButtons(view);
        setupTitle(view, (LinearLayout) view.findViewById(R.id.topPanel));
        View findViewById = view.findViewById(R.id.buttonPanel);
        View findViewById2 = view.findViewById(R.id.messageDivider);
        if (!z) {
            findViewById.setVisibility(8);
            findViewById2.setVisibility(8);
        }
        if (this.mView != null) {
            FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.customPanel);
            frameLayout.setVisibility(0);
            FrameLayout frameLayout2 = (FrameLayout) view.findViewById(R.id.custom);
            frameLayout2.addView(this.mView, new FrameLayout.LayoutParams(-1, -1));
            if (this.mViewSpacingSpecified) {
                frameLayout2.setPadding(this.mViewSpacingLeft, this.mViewSpacingTop, this.mViewSpacingRight, this.mViewSpacingBottom);
            }
            if (this.mListView != null) {
                ((LinearLayout.LayoutParams) frameLayout.getLayoutParams()).weight = 0.0f;
            }
            return;
        }
        view.findViewById(R.id.customPanel).setVisibility(8);
    }

    private boolean setupTitle(View view, LinearLayout linearLayout) {
        if (this.mCustomTitleView != null) {
            linearLayout.addView(this.mCustomTitleView, 0, new LinearLayout.LayoutParams(-1, -2));
            view.findViewById(R.id.title_template).setVisibility(8);
        } else {
            boolean z = !TextUtils.isEmpty(this.mTitle);
            this.mIconView = (ImageView) view.findViewById(R.id.icon);
            if (z) {
                this.mTitleView = (TextView) view.findViewById(R.id.alertTitle);
                this.mTitleView.setText(this.mTitle.toString().trim());
                if (this.mIconId > 0) {
                    this.mIconView.setImageResource(this.mIconId);
                } else if (this.mIcon != null) {
                    this.mIconView.setImageDrawable(this.mIcon);
                } else if (this.mIconId == 0) {
                    this.mTitleView.setPadding(this.mIconView.getPaddingLeft(), this.mIconView.getPaddingTop(), this.mIconView.getPaddingRight(), this.mIconView.getPaddingBottom());
                    this.mIconView.setVisibility(8);
                }
            } else {
                view.findViewById(R.id.title_template).setVisibility(8);
                this.mIconView.setVisibility(8);
                linearLayout.setVisibility(8);
                return false;
            }
        }
        return true;
    }

    private void setupContent(View view, LinearLayout linearLayout) {
        this.mScrollView = (ScrollView) view.findViewById(R.id.scrollView);
        this.mScrollView.setFocusable(false);
        this.mMessageView = (TextView) view.findViewById(R.id.message);
        if (this.mMessageView != null) {
            if (this.mMessage != null) {
                this.mMessageView.setText(this.mMessage.toString().trim());
                return;
            }
            if (this.mView == null) {
                View findViewById = view.findViewById(R.id.messageDivider);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) findViewById.getLayoutParams();
                layoutParams.setMargins(0, 0, 0, 0);
                findViewById.setLayoutParams(layoutParams);
            }
            this.mMessageView.setVisibility(8);
            this.mScrollView.removeView(this.mMessageView);
            if (this.mListView != null) {
                linearLayout.removeView(view.findViewById(R.id.scrollView));
                linearLayout.addView(this.mListView, new LinearLayout.LayoutParams(-1, -1));
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, 0, 1.0f));
                return;
            }
            linearLayout.setVisibility(8);
        }
    }

    private boolean setupButtons(View view) {
        boolean z;
        this.mButtonPositive = (Button) view.findViewById(R.id.button2);
        View findViewById = view.findViewById(R.id.btDriver_left);
        View findViewById2 = view.findViewById(R.id.btDriver_right);
        this.mButtonPositive.setOnClickListener(this.mButtonHandler);
        if (TextUtils.isEmpty(this.mButtonPositiveText)) {
            this.mButtonPositive.setVisibility(8);
            z = false;
        } else {
            this.mButtonPositive.setText(this.mButtonPositiveText);
            this.mButtonPositive.setVisibility(0);
            z = true;
        }
        this.mButtonNegative = (Button) view.findViewById(R.id.button1);
        this.mButtonNegative.setOnClickListener(this.mButtonHandler);
        if (TextUtils.isEmpty(this.mButtonNegativeText)) {
            this.mButtonNegative.setVisibility(8);
        } else {
            this.mButtonNegative.setText(this.mButtonNegativeText);
            this.mButtonNegative.setVisibility(0);
            z |= true;
        }
        this.mButtonNeutral = (Button) view.findViewById(R.id.button3);
        this.mButtonNeutral.setOnClickListener(this.mButtonHandler);
        if (TextUtils.isEmpty(this.mButtonNeutralText)) {
            this.mButtonNeutral.setVisibility(8);
            findViewById2.setVisibility(8);
        } else {
            this.mButtonNeutral.setText(this.mButtonNeutralText);
            this.mButtonNeutral.setVisibility(0);
            z |= true;
        }
        boolean z2 = !TextUtils.isEmpty(this.mButtonPositiveText);
        boolean z3 = !TextUtils.isEmpty(this.mButtonNegativeText);
        boolean z4 = !TextUtils.isEmpty(this.mButtonNeutralText);
        if (z2 && !z3 && !z4) {
            findViewById.setVisibility(4);
            findViewById2.setVisibility(4);
        }
        if (z3 && !z2 && !z4) {
            findViewById.setVisibility(4);
            findViewById2.setVisibility(4);
        }
        if (z4 && !z2 && !z3) {
            findViewById.setVisibility(4);
            findViewById2.setVisibility(4);
        }
        return z;
    }

    public void setTitle(CharSequence charSequence) {
        this.mTitle = charSequence;
        if (this.mTitleView != null) {
            this.mTitleView.setText(charSequence.toString().trim());
        }
    }

    public void setCustomTitle(View view) {
        this.mCustomTitleView = view;
    }

    public void setMessage(CharSequence charSequence) {
        this.mMessage = charSequence;
        if (this.mMessageView != null) {
            this.mMessageView.setText(charSequence);
        }
    }

    public void setView(View view) {
        this.mView = view;
        this.mViewSpacingSpecified = false;
    }

    public void setView(View view, int i, int i2, int i3, int i4) {
        this.mView = view;
        this.mViewSpacingSpecified = true;
        this.mViewSpacingLeft = i;
        this.mViewSpacingTop = i2;
        this.mViewSpacingRight = i3;
        this.mViewSpacingBottom = i4;
    }

    public void setButton(int i, CharSequence charSequence, NodeDialogFragmentOnClickListener nodeDialogFragmentOnClickListener, Message message) {
        if (message == null && nodeDialogFragmentOnClickListener != null) {
            message = this.mHandler.obtainMessage(i, nodeDialogFragmentOnClickListener);
        }
        switch (i) {
            case -3:
                this.mButtonNeutralText = charSequence;
                this.mButtonNeutralMessage = message;
                return;
            case -2:
                this.mButtonNegativeText = charSequence;
                this.mButtonNegativeMessage = message;
                return;
            case -1:
                this.mButtonPositiveText = charSequence;
                this.mButtonPositiveMessage = message;
                return;
            default:
                throw new IllegalArgumentException("Button does not exist");
        }
    }

    public void setIcon(int i) {
        this.mIconId = i;
        if (this.mIconView != null) {
            if (i > 0) {
                this.mIconView.setImageResource(this.mIconId);
            } else if (i == 0) {
                this.mIconView.setVisibility(8);
            }
        }
    }

    public void setIcon(Drawable drawable) {
        this.mIcon = drawable;
        if (this.mIconView != null && this.mIcon != null) {
            this.mIconView.setImageDrawable(drawable);
        }
    }

    public void setCancelable(boolean z) {
        this.mCancelable = z;
    }

    public void setCanceledOnTouchOutside(boolean z) {
        this.mCanceledOnTouchOutside = z;
    }

    public ListView getListView() {
        return this.mListView;
    }

    public boolean onPageKeyDown(int i, KeyEvent keyEvent) {
        return this.mScrollView != null && this.mScrollView.executeKeyEvent(keyEvent);
    }

    public boolean onPageKeyUp(int i, KeyEvent keyEvent) {
        return this.mScrollView != null && this.mScrollView.executeKeyEvent(keyEvent);
    }

    public Button getButton(int i) {
        switch (i) {
            case -3:
                return this.mButtonNeutral;
            case -2:
                return this.mButtonNegative;
            case -1:
                return this.mButtonPositive;
            default:
                return null;
        }
    }

    private void centerButton(View view, Button button) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) button.getLayoutParams();
        layoutParams.gravity = 1;
        layoutParams.weight = 1.0f;
        button.setLayoutParams(layoutParams);
        View findViewById = view.findViewById(R.id.leftSpacer);
        if (findViewById != null) {
            findViewById.setVisibility(0);
        }
        View findViewById2 = view.findViewById(R.id.rightSpacer);
        if (findViewById2 != null) {
            findViewById2.setVisibility(0);
        }
    }

    private void apply() {
        if (this.mBuilder != null) {
            setCancelable(this.mBuilder.mCancelable);
            setCanceledOnTouchOutside(this.mBuilder.mCanceledOnTouchOutside);
            if (this.mBuilder.mCustomTitleView != null) {
                setCustomTitle(this.mBuilder.mCustomTitleView);
            } else {
                if (this.mBuilder.mTitle != null) {
                    setTitle(this.mBuilder.mTitle);
                }
                if (this.mBuilder.mIcon != null) {
                    setIcon(this.mBuilder.mIcon);
                }
                if (this.mBuilder.mIconId >= 0) {
                    setIcon(this.mBuilder.mIconId);
                }
            }
            if (this.mBuilder.mMessage != null) {
                if (this.mBuilder.mTitle == null) {
                    setTitle(this.mBuilder.mMessage);
                } else {
                    setMessage(this.mBuilder.mMessage);
                }
            }
            if (this.mBuilder.mPositiveButtonText != null) {
                setButton(-1, this.mBuilder.mPositiveButtonText, this.mBuilder.mPositiveButtonListener, null);
            }
            if (this.mBuilder.mNegativeButtonText != null) {
                setButton(-2, this.mBuilder.mNegativeButtonText, this.mBuilder.mNegativeButtonListener, null);
            }
            if (this.mBuilder.mNeutralButtonText != null) {
                setButton(-3, this.mBuilder.mNeutralButtonText, this.mBuilder.mNeutralButtonListener, null);
            }
            if (this.mBuilder.mView != null) {
                if (this.mBuilder.mViewSpacingSpecified) {
                    setView(this.mBuilder.mView, this.mBuilder.mViewSpacingLeft, this.mBuilder.mViewSpacingTop, this.mBuilder.mViewSpacingRight, this.mBuilder.mViewSpacingBottom);
                } else {
                    setView(this.mBuilder.mView);
                }
            }
            if (!(getPageContext() == null || getPageContext().getContentView() == null)) {
                getPageContext().getContentView().setOnTouchListener(new CanceledOnTouchOutsideListener(this, this.mCanceledOnTouchOutside));
            }
        }
    }

    public ON_BACK_TYPE onPageBackPressed() {
        if (!this.mCancelable) {
            return ON_BACK_TYPE.TYPE_IGNORE;
        }
        if (!(this.mBuilder == null || this.mBuilder.mOnCancelListener == null)) {
            this.mBuilder.mOnCancelListener.onClick(this);
        }
        return ON_BACK_TYPE.TYPE_NORMAL;
    }

    public void onPageFinishFragment() {
        if (this.mBuilder != null && this.mBuilder.mOnDismissListener != null) {
            this.mBuilder.mOnDismissListener.onClick(this);
        }
    }
}

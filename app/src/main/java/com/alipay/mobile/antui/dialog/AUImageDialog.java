package com.alipay.mobile.antui.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.api.TimerListener;
import com.alipay.mobile.antui.basic.AUButton;
import com.alipay.mobile.antui.basic.AUDialog;
import com.alipay.mobile.antui.basic.AUEmptyGoneTextView;
import com.alipay.mobile.antui.basic.AURoundImageView;
import com.alipay.mobile.antui.basic.AUTextView;
import com.alipay.mobile.antui.iconfont.AUIconView;
import com.alipay.mobile.antui.utils.AuiLogger;
import com.alipay.mobile.antui.utils.DensityUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AUImageDialog extends AUDialog {
    private static final String TAG = AUImageDialog.class.getSimpleName();
    @Deprecated
    static AUImageDialog mInstance;
    static Context tempContext;
    /* access modifiers changed from: private */
    public static int times = 0;
    private ImageView bottomLine;
    private boolean canceledOnTouch = false;
    private AUIconView closeImageView;
    /* access modifiers changed from: private */
    public AUButton confirmBtn;
    private LinearLayout dialogBg;
    private AURoundImageView imageView;
    private LayoutInflater inflater;
    private boolean isShow;
    /* access modifiers changed from: private */
    public OnItemClickListener itemListener;
    private ButtonListAdapter listAdapter;
    private AUMaxItemCornerListView listView;
    /* access modifiers changed from: private */
    public OnClickListener mCloseBtnClickListener;
    /* access modifiers changed from: private */
    public String mConfirmStr;
    public Handler mHandler = new v(this);
    /* access modifiers changed from: private */
    public String mTimeColor;
    public Timer mTimer = new Timer();
    private TimerListener mTimerListener;
    private TimerTask mTimerTask;
    private OnClickListener onClickListener = new s(this);
    private AUEmptyGoneTextView titleTextView_1;
    private AUEmptyGoneTextView titleTextView_2;
    private AUEmptyGoneTextView titleTextView_3;
    private boolean usdAnim = true;

    public class ButtonListAdapter extends BaseAdapter {
        private List<String> mItemList = new ArrayList();

        public ButtonListAdapter() {
        }

        public void setData(List<String> itemList) {
            this.mItemList.clear();
            this.mItemList.addAll(itemList);
            notifyDataSetChanged();
        }

        public int getCount() {
            return this.mItemList.size();
        }

        public String getItem(int position) {
            return this.mItemList.get(position);
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = new AUTextView(AUImageDialog.this.getContext());
                ((AUTextView) convertView).setTextAppearance(AUImageDialog.this.getContext(), R.style.dialogBottomButtonStyle);
                ((AUTextView) convertView).setGravity(17);
                convertView.setLayoutParams(new LayoutParams(-1, AUImageDialog.this.getContext().getResources().getDimensionPixelSize(R.dimen.AU_SPACE12)));
            }
            convertView.setBackgroundResource(resolveItemBgDrawable(position));
            convertView.setOnClickListener(new x(this, position));
            ((AUTextView) convertView).setText(getItem(position));
            return convertView;
        }

        private int resolveItemBgDrawable(int position) {
            if (position == getCount() - 1) {
                return R.drawable.pop_list_corner_round_bottom;
            }
            return R.drawable.pop_list_corner_shape;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int i);
    }

    static /* synthetic */ int access$310() {
        int i = times;
        times = i - 1;
        return i;
    }

    @Deprecated
    public static AUImageDialog getInstance(Context context) {
        if (context == null) {
            AuiLogger.error(TAG, "Context == null");
            return null;
        }
        synchronized (AUImageDialog.class) {
            try {
                if (mInstance == null) {
                    AuiLogger.info(TAG, "new instance");
                    mInstance = new AUImageDialog(context);
                } else if (tempContext != null && !tempContext.equals(context)) {
                    AuiLogger.info(TAG, "Context changed ,reset instance");
                    resetParam(context);
                }
            }
        }
        return mInstance;
    }

    private static void resetParam(Context context) {
        mInstance.realDismiss();
        tempContext = context;
        mInstance = new AUImageDialog(context);
    }

    public AUImageDialog(Context context) {
        super(context, R.style.noTitleTransBgDialogStyle);
        AuiLogger.info(TAG, "new AUImageDialog");
        tempContext = context;
        this.inflater = LayoutInflater.from(context);
        View view = this.inflater.inflate(R.layout.au_image_dialog, null);
        this.dialogBg = (LinearLayout) view.findViewById(R.id.action_container);
        this.titleTextView_1 = (AUEmptyGoneTextView) view.findViewById(R.id.title_txt_1);
        this.titleTextView_2 = (AUEmptyGoneTextView) view.findViewById(R.id.title_txt_2);
        this.titleTextView_3 = (AUEmptyGoneTextView) view.findViewById(R.id.title_txt_3);
        this.imageView = (AURoundImageView) view.findViewById(R.id.info_logo);
        this.bottomLine = (ImageView) view.findViewById(R.id.bottom_line);
        this.closeImageView = (AUIconView) view.findViewById(R.id.btn_close);
        this.confirmBtn = (AUButton) view.findViewById(R.id.btn_confirm);
        setContentView(view);
        setCanceledOnTouchOutside(false);
        this.closeImageView.setOnClickListener(new t(this));
    }

    public void setCloseBtnClickListener(OnClickListener mCloseBtnClickListener2) {
        this.mCloseBtnClickListener = mCloseBtnClickListener2;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setTitle(CharSequence title) {
        this.titleTextView_1.setText(title);
    }

    public void setTitleTextSize(float size) {
        if (this.titleTextView_1 != null) {
            this.titleTextView_1.setTextSize(size);
        }
    }

    public void setTitleTextVisibility(int visibility) {
        if (this.titleTextView_1 != null) {
            this.titleTextView_1.setVisibility(visibility);
        }
    }

    public void setSubTitleTextVisibility(int visibility) {
        if (this.titleTextView_2 != null) {
            this.titleTextView_2.setVisibility(visibility);
        }
    }

    public void setTitleTextColor(int color) {
        if (this.titleTextView_1 != null) {
            this.titleTextView_1.setTextColor(color);
        }
    }

    public void setSubTitle(CharSequence title) {
        this.titleTextView_2.setText(title);
    }

    public void setSubTitleTextSize(float size) {
        if (this.titleTextView_2 != null) {
            this.titleTextView_2.setTextSize(size);
        }
    }

    public void setSubTitleTextColor(int color) {
        if (this.titleTextView_2 != null) {
            this.titleTextView_2.setTextColor(color);
        }
    }

    public void setThirdTitleText(String text) {
        if (this.titleTextView_3 != null) {
            this.titleTextView_3.setText(text);
        }
    }

    public void setThirdTitleTextColor(int color) {
        if (this.titleTextView_3 != null) {
            this.titleTextView_3.setTextColor(color);
        }
    }

    public void setLogoBackground(Drawable drawable) {
        if (this.imageView != null) {
            setBackground(this.imageView, drawable);
        }
    }

    public void setLogoBackgroundResource(int resid) {
        if (this.imageView != null) {
            this.imageView.setBackgroundResource(resid);
        }
    }

    public void setBigImageResource(int resid) {
        if (this.imageView != null) {
            this.imageView.setImageResource(resid);
        }
    }

    public void setLogoBackgroundColor(int color) {
        if (this.imageView != null) {
            this.imageView.setBackgroundColor(color);
        }
    }

    public void setBackgroundTransparency(float alpha) {
        if (this.dialogBg != null) {
            this.dialogBg.setAlpha(alpha);
        }
    }

    public void setLogoDefaultLoading() {
        if (this.imageView != null) {
            this.imageView.setImageDrawable(new ColorDrawable(getContext().getResources().getColor(R.color.AU_COLOR8)));
        }
    }

    public boolean isUsdAnim() {
        return this.usdAnim;
    }

    public void setUsdAnim(boolean usdAnim2) {
        this.usdAnim = usdAnim2;
    }

    public void setCloseButtonVisibility(int visibility) {
        if (this.closeImageView != null) {
            this.closeImageView.setVisibility(visibility);
        }
    }

    /* JADX WARNING: type inference failed for: r3v1, types: [android.text.Spanned] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setConfirmBtnText(java.lang.String r3) {
        /*
            r2 = this;
            com.alipay.mobile.antui.basic.AUButton r0 = r2.confirmBtn
            if (r0 == 0) goto L_0x000f
            com.alipay.mobile.antui.basic.AUButton r0 = r2.confirmBtn
            boolean r1 = android.text.TextUtils.isEmpty(r3)
            if (r1 == 0) goto L_0x0010
        L_0x000c:
            r0.setText(r3)
        L_0x000f:
            return
        L_0x0010:
            android.text.Spanned r3 = android.text.Html.fromHtml(r3)
            goto L_0x000c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.antui.dialog.AUImageDialog.setConfirmBtnText(java.lang.String):void");
    }

    public Button getConfirmBtn() {
        return this.confirmBtn;
    }

    public void setOnConfirmBtnClickListener(OnClickListener clickListener) {
        this.onClickListener = clickListener;
    }

    public void show() {
        if (!this.isShow) {
            AuiLogger.info(TAG, "AUImageDialog show");
            super.show();
            if (this.usdAnim) {
                this.dialogBg.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_bottom));
            }
            this.isShow = true;
        }
    }

    public void showWithoutAnim() {
        AuiLogger.info(TAG, "AUImageDialog showWithoutAnim");
        setOnConfirmBtnClick(this.onClickListener);
        if (this.isShow) {
            AuiLogger.info(TAG, "AUImageDialog showWithoutAnim, isShow");
            return;
        }
        this.usdAnim = false;
        this.isShow = true;
        super.show();
    }

    public void showWithTimer(int seconds, String tickColor, String action, OnClickListener clickListener, TimerListener timerListener) {
        if (seconds >= 0) {
            AuiLogger.info(TAG, "AUImageDialog showWithTimer : " + tempContext.getPackageManager());
            setCloseButtonVisibility(0);
            setSubTitleTextVisibility(0);
            this.mTimeColor = filterColor(tickColor);
            this.mConfirmStr = action;
            this.mTimerListener = timerListener;
            setConfirmBtnText(action);
            setOnConfirmBtnClick(clickListener);
            timerTask(seconds);
            super.show();
            this.usdAnim = false;
            this.isShow = true;
        }
    }

    public void showWithTimer(int seconds, OnClickListener clickListener, TimerListener timerListener) {
        showWithTimer(seconds, getDefaultTimeColorStr(), getContext().getString(R.string.try_again_once), clickListener, timerListener);
    }

    public String getDefaultTimeColorStr() {
        return "#D83B1E";
    }

    private String filterColor(String color) {
        if (!TextUtils.isEmpty(color)) {
            return "<font color='" + color + "'>%s</font>";
        }
        return "%s";
    }

    public void dismissWithoutAnim() {
        AuiLogger.info(TAG, "dismissWithoutAnim");
        this.isShow = false;
        mInstance = null;
        tempContext = null;
        if (this.mTimer != null) {
            this.mTimer.cancel();
        }
        super.dismiss();
    }

    public void setImageSmallType() {
        int size = getContext().getResources().getDimensionPixelSize(R.dimen.image_dialog_image_size_small);
        setImageSize(size, size);
    }

    public void setImageMatchType() {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) this.imageView.getLayoutParams();
        lp.width = -1;
        lp.height = DensityUtil.dip2px(getContext(), 156.0f);
        lp.topMargin = 0;
        this.imageView.setRadiusTopLeft((int) getContext().getResources().getDimension(R.dimen.AU_CORNER3));
        this.imageView.setRadiusTopRight((int) getContext().getResources().getDimension(R.dimen.AU_CORNER3));
        this.imageView.setRounded(true);
        setCloseBtnColor(-1);
    }

    public void setCloseBtnColor(int color) {
        this.closeImageView.setIconfontColor(color);
    }

    public void setImageSize(int width, int height) {
        this.imageView.getLayoutParams().height = height;
        this.imageView.getLayoutParams().width = width;
    }

    public void dismiss() {
        if (this.usdAnim) {
            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_bottom);
            animation.setAnimationListener(new u(this));
            this.dialogBg.startAnimation(animation);
            this.isShow = false;
            this.mTimer.cancel();
            mInstance = null;
            return;
        }
        realDismiss();
    }

    /* access modifiers changed from: protected */
    public void realDismiss() {
        AuiLogger.info(TAG, "realDismiss");
        this.isShow = false;
        cancelTimer();
        mInstance = null;
        tempContext = null;
        super.dismiss();
    }

    public boolean isCanceledOnTouch() {
        return this.canceledOnTouch;
    }

    @Deprecated
    public void setCanceledOnTouch(boolean canceledOnTouch2) {
        this.canceledOnTouch = canceledOnTouch2;
    }

    private void timerTask(int seconds) {
        times = seconds + 1;
        cancelTimer();
        this.mTimer = new Timer();
        this.mTimerTask = new w(this);
        this.mTimer.schedule(this.mTimerTask, 0, 1000);
    }

    /* access modifiers changed from: private */
    public void callTimeFinish() {
        if (this.mTimerListener != null) {
            this.mTimerListener.onFinish();
        }
    }

    /* access modifiers changed from: private */
    public void cancelTimer() {
        if (this.mTimer != null) {
            this.mTimer.cancel();
            this.mTimer = null;
        }
        if (this.mTimerTask != null) {
            this.mTimerTask.cancel();
            this.mTimerTask = null;
        }
    }

    public void setOnConfirmBtnClick(OnClickListener clickListener) {
        if (clickListener != null) {
            this.onClickListener = clickListener;
        }
        this.confirmBtn.setOnClickListener(this.onClickListener);
    }

    private void setBackground(View view, Drawable drawable) {
        if (VERSION.SDK_INT >= 16) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }

    public void changeComfirmBtnStyleToMain() {
        getConfirmBtn().setBackgroundResource(R.drawable.au_button_bg_for_main);
        getConfirmBtn().setTextColor(getContext().getResources().getColorStateList(R.color.au_button_textcolor_white));
        this.bottomLine.setVisibility(4);
        if (getConfirmBtn().getLayoutParams() != null) {
            MarginLayoutParams lp = (MarginLayoutParams) getConfirmBtn().getLayoutParams();
            lp.height = getContext().getResources().getDimensionPixelOffset(R.dimen.image_dialog_main_button_height);
            lp.setMargins(getContext().getResources().getDimensionPixelOffset(R.dimen.AU_SPACE3), 0, getContext().getResources().getDimensionPixelOffset(R.dimen.AU_SPACE3), 0);
            this.dialogBg.setPadding(0, 0, 0, getContext().getResources().getDimensionPixelOffset(R.dimen.AU_SPACE4));
        }
    }

    public void setButtonListInfo(List<String> buttonListInfo, OnItemClickListener listener) {
        this.bottomLine.setVisibility(0);
        this.confirmBtn.setVisibility(8);
        this.itemListener = listener;
        if (this.listView == null) {
            this.listView = new AUMaxItemCornerListView(getContext());
            this.listView.setBackgroundColor(0);
            this.listView.setDivider(new ColorDrawable(getContext().getResources().getColor(R.color.AU_COLOR_DIALOG_DIVIDER_COLOR)));
            this.listView.setDividerHeight(1);
            this.listAdapter = new ButtonListAdapter();
            this.listView.setAdapter(this.listAdapter);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
            layoutParams.addRule(3, this.bottomLine.getId());
            this.dialogBg.addView(this.listView, layoutParams);
        }
        this.listView.setVisibility(0);
        this.listAdapter.setData(buttonListInfo);
    }

    public ImageView getLogoImageView() {
        return this.imageView;
    }

    public TextView getTitleTextView() {
        return this.titleTextView_1;
    }

    public TextView getSubTitleTextView() {
        return this.titleTextView_2;
    }

    public TextView getThirdTitleTextView() {
        return this.titleTextView_3;
    }

    public ImageView getBottomLine() {
        return this.bottomLine;
    }
}

package com.alipay.mobile.antui.pop;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AULinearLayout;
import com.alipay.mobile.antui.basic.AUTextView;
import com.alipay.mobile.antui.iconfont.AUIconView;
import com.alipay.mobile.antui.utils.AuiLogger;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;

public class AUPopTipContainer extends AULinearLayout {
    private static final String TAG = "AUPopTipContainer";
    private View mAnchorView;
    private Context mContext;
    private AUIconView mDownIcon;
    private boolean mIsDown;
    private AUTextView mTipButton;
    private AULinearLayout mTipContainer;
    private AUTextView mTipTextView;
    private PopupWindow mTipWindow;
    private AUIconView mUpIcon;

    public AUPopTipContainer(Context context) {
        super(context);
    }

    public AUPopTipContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setTipLocate(Context context, PopupWindow tipWindow, AULinearLayout tipContainer, AUIconView upIcon, AUIconView downIcon, View anchorView, boolean isDown, AUTextView tipTextView, AUTextView tipButton) {
        this.mContext = context;
        this.mTipWindow = tipWindow;
        this.mTipContainer = tipContainer;
        this.mUpIcon = upIcon;
        this.mDownIcon = downIcon;
        this.mAnchorView = anchorView;
        this.mIsDown = isDown;
        this.mTipTextView = tipTextView;
        this.mTipButton = tipButton;
        setTipLocate(this.mAnchorView, this.mIsDown, getTipContainerWidth());
    }

    private int getTipContainerWidth() {
        if (this.mTipButton.getVisibility() == 8) {
            return (int) (this.mTipTextView.getPaint().measureText(this.mTipTextView.getText().toString()) + ((float) this.mTipTextView.getPaddingLeft()) + ((float) this.mTipTextView.getPaddingRight()));
        }
        return ((int) (this.mTipTextView.getPaint().measureText(this.mTipTextView.getText().toString()) + ((float) this.mTipTextView.getPaddingLeft()) + ((float) this.mTipTextView.getPaddingRight()) + this.mTipButton.getPaint().measureText(this.mTipButton.getText().toString()) + ((float) this.mTipButton.getPaddingLeft()) + ((float) this.mTipButton.getPaddingRight()))) + this.mContext.getResources().getDimensionPixelSize(R.dimen.au_tip_pop_btn_margin_left) + this.mContext.getResources().getDimensionPixelSize(R.dimen.au_tip_pop_btn_margin_right);
    }

    private void setTipLocate(View anchorView, boolean isDown, int tipContainerWidth) {
        int[] location = new int[2];
        anchorView.getLocationOnScreen(location);
        AuiLogger.debug(TAG, "location: x = " + location[0] + ", y = " + location[1]);
        int anchorViewCenterX = getAnchorViewCenterX(location[0], anchorView.getWidth());
        int gravity = isLeftScreen(anchorViewCenterX) ? 3 : 5;
        setTipContainerLocationX(gravity, tipContainerWidth, anchorViewCenterX);
        if (!isDown) {
            this.mDownIcon.setVisibility(8);
            this.mUpIcon.setVisibility(0);
            setArrowLocationX(this.mUpIcon, gravity, location[0], anchorView.getWidth(), isDown);
            tipWindowShow(anchorView, gravity | 80, 0, getScreenHeight(this.mContext) - location[1]);
            return;
        }
        this.mDownIcon.setVisibility(0);
        this.mUpIcon.setVisibility(8);
        setArrowLocationX(this.mDownIcon, gravity, location[0], anchorView.getWidth(), isDown);
        tipWindowShow(anchorView, gravity | 48, 0, location[1] + anchorView.getHeight());
    }

    private void setTipContainerLocationX(int gravity, int tipContainerWidth, int anchorViewCenterX) {
        int margin;
        int margin2;
        LayoutParams params = (LayoutParams) this.mTipContainer.getLayoutParams();
        int mTipTextViewHalfWidth = tipContainerWidth / 2;
        int anchorViewRightWidth = getScreenWidth(this.mContext) - anchorViewCenterX;
        switch (gravity) {
            case 3:
                if (anchorViewCenterX < mTipTextViewHalfWidth) {
                    margin2 = 0;
                } else {
                    margin2 = (anchorViewCenterX - mTipTextViewHalfWidth) - getWindowMargin();
                }
                params.gravity = 3;
                params.setMargins(margin2, 0, 0, 0);
                break;
            case 5:
                if (anchorViewRightWidth < mTipTextViewHalfWidth) {
                    margin = 0;
                } else {
                    margin = (anchorViewRightWidth - mTipTextViewHalfWidth) - getWindowMargin();
                }
                params.gravity = 5;
                params.setMargins(0, 0, margin, 0);
                break;
            default:
                throw new IllegalArgumentException("Gravity must have be LEFT, RIGHT.");
        }
        this.mTipContainer.setLayoutParams(params);
    }

    private boolean isLeftScreen(int anchorViewCenterX) {
        return anchorViewCenterX <= getScreenWidth(this.mContext) / 2;
    }

    private int getAnchorViewCenterX(int viewX, int viewWidth) {
        return (viewWidth / 2) + viewX;
    }

    private int getWindowMargin() {
        return this.mContext.getResources().getDimensionPixelSize(R.dimen.au_tip_pop_margin);
    }

    private void setArrowLocationX(AUIconView iconView, int gravity, int viewX, int viewWidth, boolean isDown) {
        LayoutParams params = (LayoutParams) iconView.getLayoutParams();
        int marginTopOrBottom = this.mContext.getResources().getDimensionPixelOffset(R.dimen.au_tip_pop_arrow_margin_top_or_bottom);
        switch (gravity) {
            case 3:
                int margin = (getAnchorViewCenterX(viewX, viewWidth) - (this.mContext.getResources().getDimensionPixelSize(R.dimen.au_tip_pop_arrow_width) / 2)) - getWindowMargin();
                params.gravity = 3;
                if (!isDown) {
                    params.setMargins(margin, marginTopOrBottom, 0, 0);
                    break;
                } else {
                    params.setMargins(margin, 0, 0, marginTopOrBottom);
                    break;
                }
            case 5:
                int margin2 = ((getScreenWidth(this.mContext) - getAnchorViewCenterX(viewX, viewWidth)) - (this.mContext.getResources().getDimensionPixelSize(R.dimen.au_tip_pop_arrow_width) / 2)) - getWindowMargin();
                params.gravity = 5;
                if (!isDown) {
                    params.setMargins(0, marginTopOrBottom, margin2, 0);
                    break;
                } else {
                    params.setMargins(0, 0, margin2, marginTopOrBottom);
                    break;
                }
            default:
                throw new IllegalArgumentException("Gravity must have be LEFT, RIGHT.");
        }
        iconView.setLayoutParams(params);
    }

    public static int getScreenHeight(Context context) {
        return ((WindowManager) context.getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay().getHeight();
    }

    private static int getScreenWidth(Context context) {
        return ((WindowManager) context.getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay().getWidth();
    }

    private void tipWindowShow(View parent, int gravity, int x, int y) {
        if (this.mContext != null && (this.mContext instanceof Activity) && !((Activity) this.mContext).isFinishing() && this.mTipWindow != null) {
            this.mTipWindow.showAtLocation(parent, gravity, x, y);
        }
    }
}

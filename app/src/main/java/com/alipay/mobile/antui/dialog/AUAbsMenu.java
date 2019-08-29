package com.alipay.mobile.antui.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface.OnDismissListener;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AUCornerListView;
import com.alipay.mobile.antui.iconfont.AUIconView;
import com.alipay.mobile.antui.iconfont.model.MessagePopItem;
import com.alipay.mobile.antui.utils.AUStatusBarUtil;
import com.alipay.mobile.antui.utils.AuiLogger;
import com.alipay.mobile.antui.utils.ToolUtils;
import java.util.ArrayList;

public abstract class AUAbsMenu implements OnItemClickListener {
    private static final String TAG = AUAbsMenu.class.getSimpleName();
    public static final String TYPE_LEFT_ICON = "leftIcon";
    public static final String TYPE_RIGHT_ICON = "rightIcon";
    protected int arrowMarginR;
    protected AUWithoutSlapDialog dialog;
    protected LayoutInflater inflater;
    protected Context mContext;
    protected AUIconView mDropDownIcon;
    protected AUIconView mDropUpIcon;
    protected OnItemClickListener mItemClickListener;
    protected BaseAdapter mListAdapter;
    protected AUCornerListView mListView;
    protected ArrayList<MessagePopItem> mPopItemList = new ArrayList<>();
    protected RelativeLayout windows;

    public class ViewLoc {
        public int height;
        public int width;
        public int x;
        public int y;
    }

    public abstract BaseAdapter initAdapter(Context context);

    public AUAbsMenu(Context context) {
        this.mContext = context;
        initView();
    }

    public void showDrop(View view, ArrayList<MessagePopItem> popItems) {
        showDrop(view, popItems, getScreenWidth(this.mContext) - this.mContext.getResources().getDimensionPixelSize(R.dimen.AU_SPACE5));
    }

    public void showDrop(View view, ArrayList<MessagePopItem> popItems, int width) {
        showDrop(view, popItems, width, this.mContext.getResources().getDimensionPixelSize(R.dimen.AU_SPACE5) / 2);
    }

    public void showDrop(View view, ArrayList<MessagePopItem> popItems, int width, int marginRight) {
        if (popItems != null && popItems.size() > 0) {
            Log.d(TAG, "popItems size = " + popItems.size());
            refreshListView(popItems);
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            float pointY = (float) (location[1] + (view.getHeight() / 2));
            int heightOffset = view.getHeight() - getStatusBarHeight();
            if (width == -2) {
                width = meathureWidthByChilds();
                marginRight = Math.max(((getScreenWidth(this.mContext) - location[0]) - (view.getWidth() / 2)) - (width / 2), marginRight);
            }
            setArrowMarginRight(location[0], view.getWidth(), marginRight);
            if (pointY < ((float) (getScreenHeight(this.mContext) / 2))) {
                showDialogBelow(marginRight, location[1] + heightOffset, width);
            } else {
                showDialogAbove(marginRight, location[1], width);
            }
        }
    }

    public void showAsDropDownTitleCenter(View parent, ArrayList<MessagePopItem> popItems, int width) {
        if (popItems != null && popItems.size() > 0) {
            refreshListView(popItems);
            if (width == -2) {
                width = meathureWidthByChilds();
            }
            this.mDropUpIcon.setVisibility(8);
            this.mDropDownIcon.setVisibility(8);
            Window dialogWindow = this.dialog.getWindow();
            dialogWindow.setGravity(17);
            LayoutParams lp = dialogWindow.getAttributes();
            lp.width = width;
            dialogWindow.setAttributes(lp);
            this.dialog.show();
        }
    }

    /* access modifiers changed from: protected */
    public int meathureWidthByChilds() {
        int itemWidth = this.mContext.getResources().getDimensionPixelSize(R.dimen.menu_min_width);
        ListAdapter adapter = this.mListView.getAdapter();
        int i = 0;
        while (i < adapter.getCount()) {
            try {
                View view = adapter.getView(i, null, this.mListView);
                view.measure(0, 0);
                if (view.getMeasuredWidth() > itemWidth) {
                    itemWidth = view.getMeasuredWidth();
                }
                i++;
            } catch (Exception e) {
                TextPaint paint = new TextPaint();
                paint.setTextSize((float) this.mContext.getResources().getDimensionPixelSize(R.dimen.AU_TEXTSIZE4));
                int itemMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.AU_MARGIN_UNIVERSAL);
                for (int i2 = 0; i2 < adapter.getCount(); i2++) {
                    int measuredWidth = ((int) paint.measureText(this.mPopItemList.get(i2).title)) + (itemMargin * 2);
                    if (measuredWidth > itemWidth) {
                        itemWidth = measuredWidth;
                    }
                }
            }
        }
        int defaultMaxWidth = getScreenWidth(this.mContext) - this.mContext.getResources().getDimensionPixelSize(R.dimen.AU_SPACE5);
        if (itemWidth > defaultMaxWidth) {
            return defaultMaxWidth;
        }
        return itemWidth;
    }

    /* access modifiers changed from: protected */
    public int getDefaultWidth() {
        return 300;
    }

    public void refreshListView(ArrayList<MessagePopItem> popItems) {
        this.mPopItemList.clear();
        this.mPopItemList.addAll(popItems);
        this.mListAdapter.notifyDataSetChanged();
    }

    public void showDropWithLocation(ViewLoc location, ArrayList<MessagePopItem> popItems) {
        showDropWithLocation(location, popItems, getScreenWidth(this.mContext) - this.mContext.getResources().getDimensionPixelSize(R.dimen.AU_SPACE5));
    }

    public void showDropWithLocation(ViewLoc location, ArrayList<MessagePopItem> popItems, int width) {
        showDropWithLocation(location, popItems, width, this.mContext.getResources().getDimensionPixelSize(R.dimen.AU_SPACE5) / 2);
    }

    public void showDropWithLocation(ViewLoc location, ArrayList<MessagePopItem> popItems, int width, int marginRight) {
        if (location == null) {
            Log.d(TAG, "location is null");
        } else if (popItems != null && popItems.size() > 0) {
            Log.d(TAG, "popItems size = " + popItems.size());
            refreshListView(popItems);
            float pointY = (float) (location.y + (location.height / 2));
            int heightOffset = location.height - getStatusBarHeight();
            setArrowMarginRight(location.x, location.width, marginRight);
            if (pointY < ((float) (getScreenHeight(this.mContext) / 2))) {
                showDialogBelow(marginRight, location.y + heightOffset, width);
            } else {
                showDialogAbove(marginRight, location.y, width);
            }
        }
    }

    public void directionShow(View view, ArrayList<MessagePopItem> popItems, boolean isDown) {
        directionShow(view, popItems, getScreenWidth(this.mContext) - this.mContext.getResources().getDimensionPixelSize(R.dimen.AU_SPACE5), isDown);
    }

    public void directionShow(View view, ArrayList<MessagePopItem> popItems, int width, boolean isDown) {
        directionShow(view, popItems, width, this.mContext.getResources().getDimensionPixelSize(R.dimen.AU_SPACE5) / 2, isDown);
    }

    public void directionShow(View view, ArrayList<MessagePopItem> popItems, int width, int marginRight, boolean isDown) {
        if (popItems != null && popItems.size() > 0) {
            refreshListView(popItems);
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            int heightOffset = view.getHeight() - getStatusBarHeight();
            setArrowMarginRight(location[0], view.getWidth(), marginRight);
            if (isDown) {
                showDialogBelow(marginRight, location[1] + heightOffset, width);
            } else {
                showDialogAbove(marginRight, location[1], width);
            }
        }
    }

    public void setOnDismissListener(OnDismissListener listener) {
        if (this.dialog != null) {
            this.dialog.setOnDismissListener(listener);
        }
    }

    public void hideDrop() {
        if (this.mContext != null && (this.mContext instanceof Activity) && !((Activity) this.mContext).isFinishing() && this.dialog != null) {
            this.dialog.dismiss();
        }
    }

    /* access modifiers changed from: protected */
    public void showDialogBelow(int marginRight, int locationY, int width) {
        if (!isLiving()) {
            AuiLogger.info("AUAbsMenu", "Activity is finished");
            return;
        }
        this.mDropUpIcon.setVisibility(8);
        this.mDropDownIcon.setVisibility(0);
        ((RelativeLayout.LayoutParams) this.mDropDownIcon.getLayoutParams()).setMargins(0, 0, this.arrowMarginR, -3);
        Window dialogWindow = this.dialog.getWindow();
        dialogWindow.setGravity(53);
        LayoutParams lp = dialogWindow.getAttributes();
        lp.x = marginRight;
        lp.y = locationY;
        lp.width = width;
        dialogWindow.setAttributes(lp);
        this.dialog.show();
    }

    /* access modifiers changed from: protected */
    public void showDialogAbove(int marginRight, int locationY, int width) {
        if (!isLiving()) {
            AuiLogger.info("AUAbsMenu", "Activity is finished");
            return;
        }
        this.mDropUpIcon.setVisibility(0);
        this.mDropDownIcon.setVisibility(8);
        ((RelativeLayout.LayoutParams) this.mDropUpIcon.getLayoutParams()).setMargins(0, -2, this.arrowMarginR, 0);
        Window dialogWindow = this.dialog.getWindow();
        dialogWindow.setGravity(85);
        LayoutParams lp = dialogWindow.getAttributes();
        lp.x = marginRight;
        lp.y = getScreenHeight(this.mContext) - locationY;
        lp.width = width;
        dialogWindow.setAttributes(lp);
        this.dialog.show();
    }

    private boolean isLiving() {
        if (this.mContext == null) {
            return false;
        }
        if (this.mContext instanceof Activity) {
            Activity activity = (Activity) this.mContext;
            if (activity.isFinishing() || activity.isDestroyed()) {
                return false;
            }
        }
        return true;
    }

    protected static int getScreenWidth(Context context) {
        return ToolUtils.getScreenWidth_Height(context)[0];
    }

    protected static int getScreenHeight(Context context) {
        return ToolUtils.getScreenWidth_Height(context)[1];
    }

    /* access modifiers changed from: protected */
    public int getStatusBarHeight() {
        return AUStatusBarUtil.getStatusBarHeight(this.mContext);
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, String.format("OnItemClick: position = %d", new Object[]{Integer.valueOf(position)}));
        if (this.mItemClickListener != null) {
            this.mItemClickListener.onItemClick(parent, view, position, id);
        } else {
            Log.d(TAG, "OnItemClick: mListener is null");
        }
    }

    /* access modifiers changed from: protected */
    public void setArrowMarginRight(int viewLocX, int viewWidth, int winMarRight) {
        this.arrowMarginR = (((getScreenWidth(this.mContext) - viewLocX) - (viewWidth / 2)) - winMarRight) - (this.mContext.getResources().getDimensionPixelSize(R.dimen.pop_over_icon_width) / 2);
    }

    /* access modifiers changed from: protected */
    public void initView() {
        this.inflater = (LayoutInflater) this.mContext.getSystemService("layout_inflater");
        this.windows = (RelativeLayout) this.inflater.inflate(R.layout.layout_pop_window, null);
        this.mListView = (AUCornerListView) this.windows.findViewById(R.id.pop_list);
        this.mDropDownIcon = (AUIconView) this.windows.findViewById(R.id.drop_down_icon);
        this.mDropUpIcon = (AUIconView) this.windows.findViewById(R.id.drop_up_icon);
        this.mListAdapter = initAdapter(this.mContext);
        this.mListView.setAdapter(this.mListAdapter);
        this.mListView.setOnItemClickListener(this);
        this.mListView.setDivider(this.mContext.getResources().getDrawable(R.drawable.popmenu_list_devider));
        this.mListView.setHeaderDividersEnabled(false);
        this.mListView.setFooterDividersEnabled(false);
        this.mListView.setDividerHeight(this.mContext.getResources().getDimensionPixelSize(R.dimen.AU_DIVIDER_SPACE1));
        this.dialog = new AUWithoutSlapDialog(this.mContext, R.style.MessageDialogTheme);
        this.dialog.setContentView(this.windows);
    }

    public void dismiss() {
        this.dialog.dismiss();
    }

    public boolean isShowing() {
        if (this.dialog == null) {
            return false;
        }
        return this.dialog.isShowing();
    }
}

package com.alipay.mobile.antui.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AUCornerListView;
import com.alipay.mobile.antui.basic.AURelativeLayout;
import com.alipay.mobile.antui.basic.AUTextView;
import com.alipay.mobile.antui.iconfont.model.MessagePopItem;
import com.alipay.mobile.antui.utils.AuiLogger;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import java.util.ArrayList;

public class AUPopMenu {
    private static final int CENTER = 2;
    private static final int LEFT = 1;
    private static final int RIGHT = 3;
    private static final String TAG = AUPopMenu.class.getSimpleName();
    /* access modifiers changed from: private */
    public int itemMargin;
    /* access modifiers changed from: private */
    public Context mContext;
    private ImageView mDownIcon;
    private AUCornerListView mMenuList;
    private AURelativeLayout mMenuView;
    private PopupWindow mMenuWindow;
    private int mType = 2;
    private ImageView mUpIcon;
    private int screenWidth;
    private int universeMargin;
    private int windowWidth;

    public AUPopMenu(Context context, ArrayList<MessagePopItem> itemArrayList) {
        initView(context, new ai(this, itemArrayList, 0));
    }

    public AUPopMenu(Context context, BaseAdapter listAdapter) {
        initView(context, listAdapter);
    }

    private void initView(Context context, BaseAdapter listAdapter) {
        this.mContext = context;
        this.itemMargin = context.getResources().getDimensionPixelSize(R.dimen.AU_MARGIN_UNIVERSAL);
        this.mMenuView = (AURelativeLayout) ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.au_pop_menu, null);
        this.mMenuView.setLayoutParams(new LayoutParams(-2, -2));
        this.mMenuList = (AUCornerListView) this.mMenuView.findViewById(R.id.pop_menu_list);
        this.mMenuList.setAdapter(listAdapter);
        this.mDownIcon = (ImageView) this.mMenuView.findViewById(R.id.pop_down_icon);
        this.mUpIcon = (ImageView) this.mMenuView.findViewById(R.id.pop_up_icon);
        this.screenWidth = getScreenWidth(context);
        this.windowWidth = measureWidthByChildren(this.mMenuList, listAdapter);
        this.universeMargin = context.getResources().getDimensionPixelSize(R.dimen.AU_SPACE17);
        this.mMenuWindow = new PopupWindow(this.mMenuView, -2, -2, true);
        this.mMenuWindow.setWidth(this.windowWidth);
        this.mMenuWindow.setOutsideTouchable(true);
        this.mMenuWindow.setBackgroundDrawable(new ColorDrawable(0));
    }

    public void showTipView(View anchorView) {
        showTipView(anchorView, true);
    }

    public void showTipView(View anchorView, boolean isDown) {
        if (anchorView == null) {
            AuiLogger.debug(TAG, "anchorView is null");
        } else {
            setTipLocate(anchorView, isDown);
        }
    }

    private void setTipLocate(View anchorView, boolean isDown) {
        int locationX;
        int locationY;
        int[] location = new int[2];
        anchorView.getLocationOnScreen(location);
        if (!isDown) {
            this.mDownIcon.setVisibility(8);
            this.mUpIcon.setVisibility(0);
            int anchorWidth = anchorView.getWidth();
            locationX = getViewLocationX(location[0], anchorWidth);
            setArrowLocationX(this.mUpIcon, anchorWidth);
            locationY = (((location[1] - (this.mContext.getResources().getDimensionPixelSize(R.dimen.AU_HOTSPACE1) * this.mMenuList.getAdapter().getCount())) - this.mMenuList.getAdapter().getCount()) - this.mContext.getResources().getDimensionPixelSize(R.dimen.border_arrow_height)) - this.mContext.getResources().getDimensionPixelSize(R.dimen.menu_margin_vertical);
        } else {
            this.mDownIcon.setVisibility(0);
            this.mUpIcon.setVisibility(8);
            int anchorWidth2 = anchorView.getWidth();
            locationX = getViewLocationX(location[0], anchorWidth2);
            setArrowLocationX(this.mDownIcon, anchorWidth2);
            locationY = location[1] + anchorView.getHeight() + this.mContext.getResources().getDimensionPixelSize(R.dimen.menu_margin_vertical);
        }
        tipWindowShow(anchorView, locationX, locationY);
    }

    private int getViewLocationX(int viewX, int viewWidth) {
        int locationX = viewX + ((viewWidth - this.windowWidth) / 2);
        if (locationX < this.universeMargin) {
            int locationX2 = this.universeMargin;
            this.mType = 1;
            return locationX2;
        } else if (locationX <= (this.screenWidth - this.windowWidth) - this.universeMargin) {
            return locationX;
        } else {
            int locationX3 = (this.screenWidth - this.windowWidth) - this.universeMargin;
            this.mType = 3;
            return locationX3;
        }
    }

    private void setArrowLocationX(ImageView iconView, int viewWidth) {
        int margin;
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) iconView.getLayoutParams();
        int arrowWidth = this.mContext.getResources().getDimensionPixelSize(R.dimen.border_arrow_width);
        switch (this.mType) {
            case 1:
                margin = ((viewWidth / 2) - this.universeMargin) - (arrowWidth / 2);
                break;
            case 2:
                margin = (this.windowWidth - arrowWidth) / 2;
                break;
            case 3:
                margin = ((this.windowWidth - (viewWidth / 2)) + this.universeMargin) - (arrowWidth / 2);
                break;
            default:
                margin = (this.windowWidth - arrowWidth) / 2;
                break;
        }
        params.leftMargin = margin;
        iconView.setLayoutParams(params);
    }

    private static int getScreenWidth(Context context) {
        return ((WindowManager) context.getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay().getWidth();
    }

    public void dismiss() {
        if (this.mContext != null && (this.mContext instanceof Activity) && !((Activity) this.mContext).isFinishing() && this.mMenuWindow != null) {
            this.mMenuWindow.dismiss();
        }
    }

    private void tipWindowShow(View parent, int x, int y) {
        if (this.mContext != null && (this.mContext instanceof Activity) && !((Activity) this.mContext).isFinishing() && this.mMenuWindow != null) {
            this.mMenuWindow.showAtLocation(parent, 0, x, y);
        }
    }

    private int measureWidthByChildren(ListView listView, BaseAdapter adapter) {
        int itemWidth = this.mContext.getResources().getDimensionPixelSize(R.dimen.menu_min_width);
        int i = 0;
        while (i < adapter.getCount()) {
            try {
                View view = adapter.getView(i, null, listView);
                view.measure(0, 0);
                if (view.getMeasuredWidth() > itemWidth) {
                    itemWidth = view.getMeasuredWidth();
                }
                i++;
            } catch (Exception e) {
                for (int i2 = 0; i2 < adapter.getCount(); i2++) {
                    View view2 = adapter.getView(i2, null, listView);
                    if (view2 instanceof aj) {
                        AUTextView textView = ((aj) view2).a();
                        int measuredWidth = ((int) textView.getPaint().measureText(textView.getText().toString())) + (this.itemMargin * 2);
                        if (measuredWidth > itemWidth) {
                            itemWidth = measuredWidth;
                        }
                    }
                }
            }
        }
        int defaultMaxWidth = this.screenWidth - this.mContext.getResources().getDimensionPixelSize(R.dimen.AU_SPACE5);
        if (itemWidth > defaultMaxWidth) {
            return defaultMaxWidth;
        }
        return itemWidth;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mMenuList.setOnItemClickListener(listener);
    }
}

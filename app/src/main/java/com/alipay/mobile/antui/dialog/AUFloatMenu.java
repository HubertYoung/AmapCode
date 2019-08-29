package com.alipay.mobile.antui.dialog;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AUTextView;
import com.alipay.mobile.antui.iconfont.model.MessagePopItem;
import com.alipay.mobile.antui.theme.AUAbsTheme;
import com.alipay.mobile.antui.theme.AUThemeKey;
import com.alipay.mobile.antui.theme.AUThemeManager;
import java.util.ArrayList;

public class AUFloatMenu extends AUAbsMenu {
    private View mTitleHeader;
    private AUTextView mTitleTextView;

    public AUFloatMenu(Context context) {
        super(context);
    }

    public BaseAdapter initAdapter(Context context) {
        return new q(this, context, this.mPopItemList);
    }

    /* access modifiers changed from: protected */
    public void initView() {
        super.initView();
        this.mTitleHeader = this.inflater.inflate(R.layout.floatmenu_title_header, null, false);
        this.mTitleTextView = (AUTextView) this.mTitleHeader.findViewById(R.id.title);
        AUAbsTheme theme = AUThemeManager.getCurrentTheme();
        this.mListView.setDivider(this.mContext.getResources().getDrawable(theme.getResId(AUThemeKey.FLOATMENU_LINE_COLOR, Integer.valueOf(R.drawable.popmenu_list_devider)).intValue()));
        this.mListView.setDividerHeight(this.mContext.getResources().getDimensionPixelSize(R.dimen.AU_DIVIDER_SPACE1));
        this.mListView.setSingleLineBGResid(theme.getResId(AUThemeKey.FLOATMENU_SINGLELINE_BG, Integer.valueOf(R.drawable.pop_list_corner_round)).intValue());
        this.mListView.setMultiLineFirstBGResid(theme.getResId(AUThemeKey.FLOATMENU_MULTILINEFIRST_BG, Integer.valueOf(R.drawable.pop_list_corner_round_top)).intValue());
        this.mListView.setMultiLineLastBGResid(theme.getResId(AUThemeKey.FLOATMENU_MULTILINELAST_BG, Integer.valueOf(R.drawable.pop_list_corner_round_bottom)).intValue());
        this.mListView.setMultiLineDefaultBGResid(theme.getResId(AUThemeKey.FLOATMENU_MULTILINE_DEFAULT_BG, Integer.valueOf(R.drawable.pop_list_corner_shape)).intValue());
    }

    /* access modifiers changed from: protected */
    public int getDefaultWidth() {
        return 150;
    }

    public void showDrop(View view, ArrayList<MessagePopItem> popItems) {
        removeTitleHeader();
        super.showDrop(view, popItems, -2);
    }

    public void showAsDropDownLeft(View view, ArrayList<MessagePopItem> popItems) {
        showAsDropDownLeft(view, popItems, this.mContext.getResources().getDimensionPixelSize(R.dimen.AU_SPACE6));
    }

    public void showAsDropDownLeft(View view, ArrayList<MessagePopItem> popItems, int viewCenterOffset) {
        int viewCenterOffset2 = viewCenterOffset + this.mContext.getResources().getDimensionPixelSize(R.dimen.AU_SPACE2);
        if (viewCenterOffset2 < 0) {
            viewCenterOffset2 = this.mContext.getResources().getDimensionPixelSize(R.dimen.AU_SPACE8);
        }
        removeTitleHeader();
        refreshListView(popItems);
        int width = meathureWidthByChilds();
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int screenWidth = getScreenWidth(this.mContext);
        int marginRight = (screenWidth - width) - location[0];
        int heightOffset = view.getHeight() - getStatusBarHeight();
        int defaultDialogMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.AU_SPACE5);
        if (marginRight < defaultDialogMargin) {
            this.arrowMarginR = ((screenWidth - location[0]) - viewCenterOffset2) - (defaultDialogMargin / 2);
            marginRight = defaultDialogMargin / 2;
        } else {
            this.arrowMarginR = width - viewCenterOffset2;
        }
        showDialogBelow(marginRight, location[1] + heightOffset, width);
    }

    public void showAsDropDownTitleCenter(View parent, String title, ArrayList<MessagePopItem> popItems) {
        addTitleHeader(title);
        super.showAsDropDownTitleCenter(parent, popItems, -2);
    }

    private void addTitleHeader(String title) {
        this.mTitleTextView.setText(title);
        this.mListView.addHeaderView(this.mTitleHeader, null, false);
        this.mListView.setHeaderDividersEnabled(true);
    }

    private void removeTitleHeader() {
        this.mListView.removeHeaderView(this.mTitleHeader);
        this.mListView.setHeaderDividersEnabled(false);
    }

    public void setOnClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }
}

package com.alipay.mobile.h5container.api;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.h5container.api.H5Param.OptionType;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.search.H5SearchType;
import com.alipay.mobile.nebula.search.H5SearchView;
import com.alipay.mobile.nebula.util.H5DimensionUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5StateListUtils;
import com.alipay.mobile.nebula.util.H5StatusBarUtils;
import com.alipay.mobile.nebula.util.H5TypefaceCache;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.util.H5ViewStubUtil;
import com.alipay.mobile.nebula.view.H5TitleBarFrameLayout;
import com.alipay.mobile.nebula.view.H5TitleView;
import com.alipay.mobile.nebula.view.IH5TinyPopMenu;
import com.autonavi.minimap.ajx3.loader.AjxHttpLoader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class H5TitleBar implements OnClickListener, H5TitleView {
    public static final String TAG = "H5TitleBar";
    public View backContainer;
    public View backToHomeContainer;
    public TextView btBack;
    public TextView btBackToHome;
    public TextView btClose;
    public View btDotView;
    public View btDotView1;
    public List<View> btDotViewList = new ArrayList();
    public ImageButton btIcon;
    public ImageButton btIcon1;
    public List<ImageButton> btIconList = new ArrayList();
    public TextView btMenu;
    public TextView btMenu1;
    public List<TextView> btMenuList = new ArrayList();
    public TextView btText;
    public TextView btText1;
    public List<TextView> btTextList = new ArrayList();
    public H5TitleBarFrameLayout contentView;
    public TextView disClaimer;
    public ImageView dotImage;
    public ImageView dotImage1;
    public List<ImageView> dotImageList = new ArrayList();
    public TextView dotText;
    public TextView dotText1;
    public List<TextView> dotTextList = new ArrayList();
    public View h5NavOptions;
    public View h5NavOptions1;
    public List<View> h5NavOptionsList = new ArrayList();
    private H5Page h5Page;
    private IH5TinyPopMenu h5TinyPopMenu;
    private RelativeLayout h5TitleBarReLayout;
    private View hDivider;
    private Typeface iconfont;
    public ImageView ivImageTitle;
    public LinearLayout llTitle;
    private Context mContext;
    private boolean mTrimTitleContent = true;
    private OptionType[] optionTypes = new OptionType[2];
    @DrawableRes
    private int progressBarLoadingDrawable = R.drawable.h5_title_bar_progress;
    public RelativeLayout rlTitle;
    private boolean searchPage = false;
    private H5SearchView searchView;
    private boolean showCloseButton;
    private View statusBarAdjustView;
    private ViewGroup titleViewContainer;
    public TextView tvSubtitle;
    public TextView tvTitle;
    private int visibleOptionNum;

    public H5TitleBar(Context context) {
        boolean z;
        this.mContext = context;
        ViewGroup parent = null;
        this.contentView = (H5TitleBarFrameLayout) LayoutInflater.from(context).inflate(R.layout.h5_navigation_bar, context instanceof Activity ? (ViewGroup) ((Activity) this.mContext).findViewById(16908290) : parent, false);
        this.tvTitle = (TextView) this.contentView.findViewById(R.id.h5_tv_title);
        this.tvSubtitle = (TextView) this.contentView.findViewById(R.id.h5_tv_subtitle);
        this.disClaimer = (TextView) this.contentView.findViewById(R.id.h5_nav_disclaimer);
        this.ivImageTitle = (ImageView) this.contentView.findViewById(R.id.h5_tv_title_img);
        this.statusBarAdjustView = this.contentView.findViewById(R.id.h5_status_bar_adjust_view);
        this.tvSubtitle.setVisibility(8);
        this.ivImageTitle.setVisibility(8);
        this.tvTitle.setOnClickListener(this);
        this.tvSubtitle.setOnClickListener(this);
        this.ivImageTitle.setOnClickListener(this);
        this.btBack = (TextView) this.contentView.findViewById(R.id.h5_tv_nav_back);
        this.backContainer = this.contentView.findViewById(R.id.h5_nav_back);
        this.btClose = (TextView) this.contentView.findViewById(R.id.h5_nav_close);
        this.btBackToHome = (TextView) this.contentView.findViewById(R.id.h5_tv_nav_back_to_home);
        this.backToHomeContainer = this.contentView.findViewById(R.id.h5_nav_back_to_home);
        H5TypefaceCache.getInstance();
        this.iconfont = H5TypefaceCache.getTypeface(context, "h5iconfont", "h5iconfont" + File.separator + "h5titlebar.ttf");
        this.btBack.setTypeface(this.iconfont);
        this.btBack.setTextColor(H5StateListUtils.getStateColor(-15692055));
        this.btClose.setTypeface(this.iconfont);
        this.btClose.setTextColor(H5StateListUtils.getStateColor(-15692055));
        this.disClaimer.setTypeface(this.iconfont);
        this.disClaimer.setTextColor(H5StateListUtils.getStateColor(-6710887));
        this.hDivider = this.contentView.findViewById(R.id.h5_h_divider_intitle);
        this.rlTitle = (RelativeLayout) this.contentView.findViewById(R.id.h5_rl_title);
        this.llTitle = (LinearLayout) this.contentView.findViewById(R.id.h5_ll_title);
        this.h5NavOptions = this.contentView.findViewById(R.id.h5_nav_options);
        this.btDotView = this.contentView.findViewById(R.id.h5_bt_dot);
        this.btIcon = (ImageButton) this.contentView.findViewById(R.id.h5_bt_image);
        this.btText = (TextView) this.contentView.findViewById(R.id.h5_bt_text);
        this.btMenu = (TextView) this.contentView.findViewById(R.id.h5_bt_options);
        this.btMenu.setTypeface(this.iconfont);
        this.btMenu.setTextColor(H5StateListUtils.getStateColor(-15692055));
        this.dotImage = (ImageView) this.contentView.findViewById(R.id.h5_bt_dot_bg);
        this.dotText = (TextView) this.contentView.findViewById(R.id.h5_bt_dot_number);
        this.h5NavOptions1 = this.contentView.findViewById(R.id.h5_nav_options1);
        this.btDotView1 = this.contentView.findViewById(R.id.h5_bt_dot1);
        this.btIcon1 = (ImageButton) this.contentView.findViewById(R.id.h5_bt_image1);
        this.btText1 = (TextView) this.contentView.findViewById(R.id.h5_bt_text1);
        this.btMenu1 = (TextView) this.contentView.findViewById(R.id.h5_bt_options1);
        this.btMenu1.setTypeface(this.iconfont);
        this.btMenu1.setTextColor(H5StateListUtils.getStateColor(-15692055));
        this.dotImage1 = (ImageView) this.contentView.findViewById(R.id.h5_bt_dot_bg1);
        this.dotText1 = (TextView) this.contentView.findViewById(R.id.h5_bt_dot_number1);
        this.h5NavOptionsList.add(this.h5NavOptions);
        this.h5NavOptionsList.add(this.h5NavOptions1);
        this.btDotViewList.add(this.btDotView);
        this.btDotViewList.add(this.btDotView1);
        this.btIconList.add(this.btIcon);
        this.btIconList.add(this.btIcon1);
        this.btTextList.add(this.btText);
        this.btTextList.add(this.btText1);
        this.btMenuList.add(this.btMenu);
        this.btMenuList.add(this.btMenu1);
        this.dotImageList.add(this.dotImage);
        this.dotImageList.add(this.dotImage1);
        this.dotTextList.add(this.dotText);
        this.dotTextList.add(this.dotText1);
        this.visibleOptionNum = 1;
        ((RelativeLayout) this.contentView.findViewById(R.id.adView)).setTag(H5Utils.TRANSPARENT_AD_VIEW_TAG);
        ((RelativeLayout) this.contentView.findViewById(R.id.h5_custom_view)).setTag("h5_custom_view");
        this.btBack.setOnClickListener(this);
        this.btClose.setOnClickListener(this);
        this.disClaimer.setOnClickListener(this);
        this.btText.setOnClickListener(this);
        this.btIcon.setOnClickListener(this);
        this.btText1.setOnClickListener(this);
        this.btIcon1.setOnClickListener(this);
        this.btMenu.setOnClickListener(this);
        this.btMenu1.setOnClickListener(this);
        this.h5TitleBarReLayout = (RelativeLayout) this.contentView.findViewById(R.id.h5_title_bar_layout);
        this.searchPage = false;
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider != null) {
            if (!BQCCameraParam.VALUE_NO.equalsIgnoreCase(h5ConfigProvider.getConfigWithProcessCache("h5_trimTitle"))) {
                z = true;
            } else {
                z = false;
            }
            this.mTrimTitleContent = z;
        }
    }

    public void setBtIcon(Bitmap btIcon2, int index) {
        if (!isOutOfBound(index, this.btIconList.size())) {
            this.btIconList.get(index).setImageBitmap(btIcon2);
        }
    }

    public String getTitle() {
        if (this.tvTitle != null) {
            return this.tvTitle.getText().toString();
        }
        return null;
    }

    public void setTitle(String title) {
        if (title != null && enableSetTitle(title)) {
            if (this.mTrimTitleContent) {
                this.tvTitle.setText(title.trim());
            } else {
                this.tvTitle.setText(title);
            }
            this.tvTitle.setVisibility(0);
            this.ivImageTitle.setVisibility(8);
        }
    }

    private boolean enableSetTitle(String title) {
        if (title.startsWith(AjxHttpLoader.DOMAIN_HTTP) || title.startsWith(AjxHttpLoader.DOMAIN_HTTPS)) {
            return false;
        }
        return true;
    }

    public void setOptionType(OptionType type) {
        setOptionType(type, 0, true);
    }

    public void setOptionType(OptionType type, int num, boolean byIndex) {
        int i;
        int i2 = 0;
        boolean icon = false;
        boolean text = false;
        boolean menu = false;
        if (type == OptionType.ICON) {
            icon = true;
        } else if (type == OptionType.TEXT) {
            text = true;
        } else if (type == OptionType.MENU) {
            menu = true;
        }
        ctrlbtText(num, text ? 0 : 8, byIndex);
        if (icon) {
            i = 0;
        } else {
            i = 4;
        }
        ctrlbtIcon(num, i, byIndex);
        if (!menu) {
            i2 = 4;
        }
        ctrlbtMenu(num, i2, byIndex);
    }

    private boolean isOutOfBound(int num, int length) {
        if (length != 0 && length >= num) {
            return false;
        }
        return true;
    }

    private void ctrlbtText(int num, int visible, boolean byIndex) {
        if (!isOutOfBound(num, this.btTextList.size())) {
            if (byIndex) {
                this.btTextList.get(num).setVisibility(visible);
                if (visible == 0) {
                    this.optionTypes[num] = OptionType.TEXT;
                    return;
                }
                return;
            }
            for (int i = 0; i < num; i++) {
                this.btTextList.get(i).setVisibility(visible);
                if (visible == 0) {
                    this.optionTypes[num] = OptionType.TEXT;
                }
            }
        }
    }

    public void ctrlbtIcon(int num, int visible, boolean byIndex) {
        if (!isOutOfBound(num, this.btIconList.size())) {
            if (byIndex) {
                this.btIconList.get(num).setVisibility(visible);
                if (visible == 0) {
                    this.optionTypes[num] = OptionType.ICON;
                    return;
                }
                return;
            }
            for (int i = 0; i < num; i++) {
                this.btIconList.get(i).setVisibility(visible);
                if (visible == 0) {
                    this.optionTypes[num] = OptionType.ICON;
                }
            }
        }
    }

    private void ctrlbtMenu(int num, int visible, boolean byIndex) {
        if (!isOutOfBound(num, this.btMenuList.size())) {
            if (byIndex) {
                this.btMenuList.get(num).setVisibility(visible);
                if (visible == 0) {
                    this.optionTypes[num] = OptionType.MENU;
                    return;
                }
                return;
            }
            for (int i = 0; i < num; i++) {
                this.btMenuList.get(i).setVisibility(visible);
                if (visible == 0) {
                    this.optionTypes[num] = OptionType.MENU;
                }
            }
        }
    }

    private void ctrlbtDotView(int num, int visible) {
        if (!isOutOfBound(num, this.btDotViewList.size())) {
            for (int i = 0; i < num; i++) {
                this.btDotViewList.get(i).setVisibility(visible);
            }
        }
    }

    public void setSubTitle(String subtitle) {
        boolean showSub;
        if (TextUtils.isEmpty(subtitle)) {
            showSub = false;
        } else {
            showSub = true;
            this.tvSubtitle.setText(subtitle);
        }
        this.tvSubtitle.setVisibility(showSub ? 0 : 8);
    }

    public void setImgTitle(Bitmap imgTitle) {
        if (imgTitle != null) {
            H5Log.d(TAG, "imgTitle width " + imgTitle.getWidth() + ", imgTitle height " + imgTitle.getHeight());
            this.ivImageTitle.setImageBitmap(imgTitle);
            this.ivImageTitle.setVisibility(0);
            this.tvTitle.setVisibility(8);
            this.tvSubtitle.setVisibility(8);
            H5Log.d(TAG, "ivImageTitle width " + this.ivImageTitle.getWidth() + ", ivImageTitle height " + this.ivImageTitle.getHeight());
        }
    }

    public void setImgTitle(Bitmap imgTitle, String contentDescription) {
        if (!TextUtils.isEmpty(contentDescription)) {
            this.ivImageTitle.setContentDescription(contentDescription);
        }
        setImgTitle(imgTitle);
    }

    public void showCloseButton(boolean show) {
        int i;
        this.showCloseButton = show;
        TextView textView = this.btClose;
        if (show) {
            i = 0;
        } else {
            i = 8;
        }
        textView.setVisibility(i);
        if (show && this.backToHomeContainer != null) {
            this.backToHomeContainer.setVisibility(8);
        }
    }

    public void showBackButton(boolean show) {
        int i;
        int i2;
        boolean isAddMarginLeft;
        int i3;
        View view = this.backContainer;
        if (show) {
            i = 0;
        } else {
            i = 8;
        }
        view.setVisibility(i);
        TextView textView = this.btClose;
        if (!show || !this.showCloseButton) {
            i2 = 8;
        } else {
            i2 = 0;
        }
        textView.setVisibility(i2);
        if (show || this.backToHomeContainer.getVisibility() == 0) {
            isAddMarginLeft = false;
        } else {
            isAddMarginLeft = true;
        }
        LayoutParams layoutParams = (LayoutParams) this.rlTitle.getLayoutParams();
        if (isAddMarginLeft) {
            i3 = H5DimensionUtil.dip2px(this.mContext, 16.0f);
        } else {
            i3 = 0;
        }
        layoutParams.setMargins(i3, 0, 0, 0);
        if (show && this.backToHomeContainer != null) {
            this.backToHomeContainer.setVisibility(8);
        }
    }

    public void showOptionMenu(boolean show) {
        if (show) {
            switch (this.visibleOptionNum) {
                case 1:
                    this.h5NavOptions.setVisibility(0);
                    return;
                case 2:
                    this.h5NavOptions.setVisibility(0);
                    this.h5NavOptions1.setVisibility(0);
                    return;
                default:
                    return;
            }
        } else {
            this.h5NavOptions.setVisibility(8);
            this.h5NavOptions1.setVisibility(8);
        }
    }

    public void showTitleLoading(boolean show) {
        int i = 0;
        if (show) {
            Drawable drawable = this.mContext.getResources().getDrawable(this.progressBarLoadingDrawable);
            int dimen = H5DimensionUtil.dip2px(this.mContext, 17.0f);
            drawable.setBounds(0, 0, dimen, dimen);
            ((ProgressBar) H5ViewStubUtil.getView(this.contentView, R.id.h5_nav_loading_stub, R.id.h5_nav_loading)).setIndeterminateDrawable(drawable);
            H5ViewStubUtil.setViewVisibility(this.contentView, R.id.h5_nav_loading_stub, R.id.h5_nav_loading, 8);
        }
        H5TitleBarFrameLayout h5TitleBarFrameLayout = this.contentView;
        int i2 = R.id.h5_nav_loading_stub;
        int i3 = R.id.h5_nav_loading;
        if (!show) {
            i = 8;
        }
        H5ViewStubUtil.setViewVisibility(h5TitleBarFrameLayout, i2, i3, i);
    }

    public void showTitleDisclaimer(boolean visible) {
        if (this.disClaimer == null) {
            return;
        }
        if (visible) {
            this.disClaimer.setVisibility(0);
        } else {
            this.disClaimer.setVisibility(8);
        }
    }

    public View getContentView() {
        return this.contentView;
    }

    public ColorDrawable getContentBgView() {
        return this.contentView.getContentBgView();
    }

    public TextView getMainTitleView() {
        return this.tvTitle;
    }

    public TextView getSubTitleView() {
        return this.tvSubtitle;
    }

    public void onClick(View view) {
        int i = 0;
        if (this.h5Page != null) {
            String eventName = null;
            JSONObject data = null;
            if (view.equals(this.btBack)) {
                eventName = CommonEvents.H5_TOOLBAR_BACK;
            } else if (view.equals(this.btClose)) {
                eventName = CommonEvents.H5_TOOLBAR_CLOSE;
            } else if (view.equals(this.btIcon) || view.equals(this.btText)) {
                eventName = CommonEvents.H5_TITLEBAR_OPTIONS;
                data = new JSONObject();
                data.put((String) "index", (Object) Integer.valueOf(0));
            } else if (view.equals(this.btIcon1) || view.equals(this.btText1)) {
                eventName = CommonEvents.H5_TITLEBAR_OPTIONS;
                data = new JSONObject();
                data.put((String) "index", (Object) Integer.valueOf(1));
            } else if (view.equals(this.btMenu) || view.equals(this.btMenu1)) {
                eventName = CommonEvents.H5_TITLEBAR_OPTIONS;
                data = new JSONObject();
                data.put((String) "fromMenu", (Object) Boolean.valueOf(true));
                if (!view.equals(this.btMenu)) {
                    i = 1;
                }
                data.put((String) "index", (Object) Integer.valueOf(i));
            } else if (view.equals(this.tvTitle) || view.equals(this.ivImageTitle)) {
                eventName = CommonEvents.H5_TITLEBAR_TITLE;
            } else if (view.equals(this.tvSubtitle)) {
                eventName = CommonEvents.H5_TITLEBAR_SUBTITLE;
            } else if (view.equals(this.disClaimer)) {
                eventName = "disClaimerClick";
            }
            if (view.equals(this.btIcon) || view.equals(this.btText) || view.equals(this.btMenu)) {
                this.btDotView.setVisibility(8);
            }
            if (view.equals(this.btIcon1) || view.equals(this.btText1) || view.equals(this.btMenu1)) {
                this.btDotView1.setVisibility(8);
            }
            if (!TextUtils.isEmpty(eventName)) {
                this.h5Page.sendEvent(eventName, data);
            }
        }
    }

    public void setH5Page(H5Page h5Page2) {
        this.h5Page = h5Page2;
    }

    public void setOptionMenu(JSONObject params) {
        int menuSize = 2;
        boolean reset = H5Utils.getBoolean(params, (String) "reset", false);
        boolean override = H5Utils.getBoolean(params, (String) "override", false);
        boolean isTinySence = TextUtils.equals("tiny", H5Utils.getString(params, (String) "bizType"));
        JSONArray menus = H5Utils.getJSONArray(params, "menus", null);
        if (reset && !isTinySence) {
            this.h5NavOptions1.setVisibility(8);
            ctrlbtDotView(1, 8);
            setOptionType(OptionType.MENU, 0, true);
            this.visibleOptionNum = 1;
        } else if (menus != null && !menus.isEmpty()) {
            this.visibleOptionNum = 0;
            if (!override || isTinySence) {
                this.visibleOptionNum = 2;
                setOptionMenuInternal(menus.getJSONObject(0), 1);
                return;
            }
            if (menus.size() <= 2) {
                menuSize = menus.size();
            }
            for (int i = 0; i < menuSize; i++) {
                setOptionMenuInternal(menus.getJSONObject(i), i);
                this.visibleOptionNum++;
            }
        } else if (isTinySence) {
            setOptionMenuInternal(params, 1);
            this.visibleOptionNum = 2;
        } else {
            setOptionMenuInternal(params, 0);
            this.visibleOptionNum = 1;
        }
    }

    private void setOptionMenuInternal(JSONObject params, int index) {
        String title = H5Utils.getString(params, (String) "title");
        String icon = H5Utils.getString(params, (String) H5Param.MENU_ICON);
        String icontype = H5Utils.getString(params, (String) "icontype");
        String redDot = H5Utils.getString(params, (String) "redDot");
        String contentDesc = H5Utils.getString(params, (String) "contentDesc");
        if (TextUtils.isEmpty(redDot)) {
            redDot = H5Utils.getInt(params, (String) "redDot", -1);
        }
        String colorText = H5Utils.getString(params, (String) "color");
        int color = -15692055;
        if (!TextUtils.isEmpty(colorText)) {
            try {
                color = Color.parseColor(colorText);
            } catch (Throwable th) {
            }
            this.btTextList.get(index).setTextColor(color | -16777216);
        } else {
            int currentColor = this.tvTitle.getCurrentTextColor() | -16777216;
            H5Log.d(TAG, "setOptionMenuInternal currentColor is " + currentColor);
            if (currentColor != -15658735) {
                this.btText.setTextColor(-1);
                this.btText1.setTextColor(-1);
            } else {
                this.btText.setTextColor(-15692055);
                this.btText1.setTextColor(-15692055);
            }
        }
        if (!TextUtils.isEmpty(title)) {
            this.btDotViewList.get(index).setVisibility(8);
            String title2 = title.trim();
            this.btTextList.get(index).setText(title2);
            setOptionType(OptionType.TEXT, index, true);
            this.btTextList.get(index).setContentDescription(title2);
        } else if (!TextUtils.isEmpty(icon) || !TextUtils.isEmpty(icontype)) {
            this.btDotViewList.get(index).setVisibility(8);
            if (!TextUtils.isEmpty(contentDesc)) {
                this.btIconList.get(index).setContentDescription(contentDesc);
            }
        }
        if (!TextUtils.isEmpty(redDot)) {
            int dotNum = -1;
            try {
                dotNum = Integer.parseInt(redDot);
            } catch (NumberFormatException e) {
            }
            this.btDotViewList.get(index).setVisibility(dotNum >= 0 ? 0 : 8);
            adjustBadgePosition(index, dotNum);
        }
    }

    private void adjustBadgePosition(int index, int dotNum) {
        if (this.optionTypes[index] == OptionType.MENU) {
            H5Log.debug(TAG, "adjust menu");
        } else if (this.optionTypes[index] == OptionType.ICON) {
            H5Log.debug(TAG, "adjust icon");
        } else if (this.optionTypes[index] == OptionType.TEXT) {
            H5Log.debug(TAG, "adjust text");
            if (dotNum == 0) {
                this.dotImageList.get(index).setPadding(0, H5DimensionUtil.dip2px(this.mContext, 7.8f), H5DimensionUtil.dip2px(this.mContext, 6.0f), 0);
            }
        }
        if (dotNum == 0) {
            this.dotImageList.get(index).setVisibility(0);
            this.dotTextList.get(index).setVisibility(8);
        } else if (dotNum > 0) {
            this.dotTextList.get(index).setVisibility(0);
            this.dotImageList.get(index).setVisibility(8);
            this.dotTextList.get(index).setText(dotNum > 99 ? "···" : String.valueOf(dotNum));
        }
    }

    public View getDivider() {
        return null;
    }

    public View getHdividerInTitle() {
        return this.hDivider;
    }

    public View getPopAnchor() {
        return this.btMenu;
    }

    public void resetTitleColor(int color) {
        if (this.contentView != null) {
            this.contentView.getContentBgView().setColor(color);
            if (getH5SearchView() != null) {
                getH5SearchView().setSearchBarColor(color);
            }
        }
    }

    public void switchToWhiteTheme() {
        this.tvTitle.setTextColor(-1);
        this.tvSubtitle.setTextColor(-1);
        if (this.btText.getCurrentTextColor() == -15692055) {
            this.btText.setTextColor(-1);
        }
        if (this.btText1.getCurrentTextColor() == -15692055) {
            this.btText1.setTextColor(-1);
        }
        this.btBack.setTextColor(H5StateListUtils.getStateColor(-1));
        this.btClose.setTextColor(H5StateListUtils.getStateColor(-1));
        this.btMenu.setTextColor(H5StateListUtils.getStateColor(-1));
        this.btMenu1.setTextColor(H5StateListUtils.getStateColor(-1));
        this.disClaimer.setTextColor(H5StateListUtils.getStateColor(-1));
        this.progressBarLoadingDrawable = R.drawable.h5_title_bar_progress_white;
        if (this.searchPage && getH5SearchView() != null) {
            getH5SearchView().switchToWhiteTheme();
        }
        if (this.h5TinyPopMenu != null) {
            this.h5TinyPopMenu.onSwitchToWhiteTheme();
        }
    }

    public void switchToBlueTheme() {
        this.tvTitle.setTextColor(-15658735);
        this.tvSubtitle.setTextColor(-15658735);
        if (this.btText.getCurrentTextColor() == -1) {
            this.btText.setTextColor(-15692055);
        }
        if (this.btText1.getCurrentTextColor() == -1) {
            this.btText1.setTextColor(-15692055);
        }
        this.btBack.setTextColor(H5StateListUtils.getStateColor(-15692055));
        this.btClose.setTextColor(H5StateListUtils.getStateColor(-15692055));
        this.btMenu.setTextColor(H5StateListUtils.getStateColor(-15692055));
        this.btMenu1.setTextColor(H5StateListUtils.getStateColor(-15692055));
        this.disClaimer.setTextColor(H5StateListUtils.getStateColor(-6710887));
        this.progressBarLoadingDrawable = R.drawable.h5_title_bar_progress;
        if (this.searchPage && getH5SearchView() != null) {
            getH5SearchView().switchToOriginal();
        }
        if (this.h5TinyPopMenu != null) {
            this.h5TinyPopMenu.onSwitchToBlueTheme();
        }
    }

    public void releaseViewList() {
        if (this.h5NavOptionsList != null) {
            this.h5NavOptionsList.clear();
        }
        if (this.btDotViewList != null) {
            this.btDotViewList.clear();
        }
        if (this.btIconList != null) {
            this.btIconList.clear();
        }
        if (this.btTextList != null) {
            this.btTextList.clear();
        }
        if (this.btMenuList != null) {
            this.btMenuList.clear();
        }
        if (this.dotImageList != null) {
            this.dotImageList.clear();
        }
        if (this.dotTextList != null) {
            this.dotTextList.clear();
        }
    }

    public void openTranslucentStatusBarSupport(int color) {
        if (H5StatusBarUtils.isSupport()) {
            int statusBarHeight = H5StatusBarUtils.getStatusBarHeight(this.mContext);
            if (statusBarHeight != 0) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.statusBarAdjustView.getLayoutParams();
                layoutParams.height = statusBarHeight;
                this.statusBarAdjustView.setLayoutParams(layoutParams);
                this.statusBarAdjustView.setVisibility(0);
                try {
                    H5StatusBarUtils.setTransparentColor((Activity) this.mContext, color);
                } catch (Exception e) {
                    H5Log.e((String) TAG, (Throwable) e);
                }
            }
        }
    }

    public void switchToTitleBar() {
        H5ViewStubUtil.setViewVisibility(this.contentView, R.id.h5_full_search_bar_stub, R.id.h5_full_search_bar, 8);
        this.h5TitleBarReLayout.setVisibility(0);
        try {
            ((InputMethodManager) this.mContext.getSystemService("input_method")).hideSoftInputFromWindow(this.contentView.getWindowToken(), 0);
        } catch (Exception e) {
            H5Log.e((String) TAG, (Throwable) e);
        }
    }

    public View setTitleBarSearch(Bundle bundle) {
        this.searchPage = true;
        if (getH5SearchView() == null) {
            return null;
        }
        String navSearchBar_type = H5Utils.getString(bundle, (String) H5Param.LONG_NAV_SEARCH_BAR_TYPE);
        getH5SearchView().switchToWhiteTheme();
        if (TextUtils.equals(navSearchBar_type, H5SearchType.ENTRANCE)) {
            this.llTitle.setVisibility(8);
            H5ViewStubUtil.setViewVisibility(this.contentView, R.id.h5_embed_title_search_stub, R.id.h5_embed_title_search, 0);
            LinearLayout searchInput = (LinearLayout) H5ViewStubUtil.getView(this.contentView, R.id.h5_embed_title_search_stub, R.id.h5_embed_title_search);
            searchInput.setVisibility(0);
            getH5SearchView().showSearch(this.mContext, searchInput, bundle);
            if (TextUtils.isEmpty(H5Utils.getString(bundle, (String) H5Param.LONG_TRANSPARENT_TITLE))) {
                return searchInput;
            }
            this.btBack.setTextColor(H5StateListUtils.getStateColor(-1));
            return searchInput;
        }
        this.h5TitleBarReLayout.setVisibility(8);
        H5ViewStubUtil.setViewVisibility(this.contentView, R.id.h5_full_search_bar_stub, R.id.h5_full_search_bar, 0);
        LinearLayout searchBar = (LinearLayout) H5ViewStubUtil.getView(this.contentView, R.id.h5_full_search_bar_stub, R.id.h5_full_search_bar);
        getH5SearchView().showSearch(this.mContext, searchBar, bundle);
        return searchBar;
    }

    public void setBackCloseBtnImage(String type) {
        if ("yellow".equalsIgnoreCase(type)) {
            this.btBack.setTextColor(H5StateListUtils.getStateColor(-2109303));
            this.btClose.setTextColor(H5StateListUtils.getStateColor(-2109303));
            this.progressBarLoadingDrawable = R.drawable.h5_title_bar_progress_gold;
        }
        if ("black".equalsIgnoreCase(type)) {
            this.btBack.setTextColor(H5StateListUtils.getStateColor(-16777216));
            this.btClose.setTextColor(H5StateListUtils.getStateColor(-16777216));
            this.progressBarLoadingDrawable = R.drawable.h5_title_bar_progress;
        }
    }

    public void setTitleTxtColor(int color) {
        int color2 = color | -16777216;
        this.tvTitle.setTextColor(color2);
        this.tvSubtitle.setTextColor(color2);
    }

    public View getOptionMenuContainer() {
        return this.h5NavOptions;
    }

    public void setIH5TinyPopMenu(IH5TinyPopMenu tinyPopMenu) {
        this.h5TinyPopMenu = tinyPopMenu;
    }

    public void setTitleView(View view) {
        if (view != null) {
            if (this.titleViewContainer == null) {
                this.titleViewContainer = new LinearLayout(this.mContext);
                LayoutParams layoutParams = new LayoutParams(-1, -2);
                layoutParams.addRule(15);
                this.rlTitle.addView(this.titleViewContainer, layoutParams);
            } else {
                this.titleViewContainer.removeAllViews();
            }
            ViewGroup.LayoutParams viewParams = view.getLayoutParams();
            if (viewParams == null) {
                viewParams = new LinearLayout.LayoutParams(-2, -2);
            }
            this.titleViewContainer.addView(view, viewParams);
            this.llTitle.setVisibility(4);
        } else if (this.titleViewContainer != null) {
            this.rlTitle.removeView(this.titleViewContainer);
            this.llTitle.setVisibility(0);
            this.titleViewContainer.removeAllViews();
            this.titleViewContainer = null;
        }
    }

    public H5SearchView getH5SearchView() {
        if (this.searchView != null) {
            return this.searchView;
        }
        this.searchView = (H5SearchView) H5Utils.getProvider(H5SearchView.class.getName());
        return this.searchView;
    }

    public boolean isSearchPage() {
        return this.searchPage;
    }

    public void setSearchPage(boolean searchPage2) {
        this.searchPage = searchPage2;
    }
}

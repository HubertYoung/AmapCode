package com.alipay.mobile.nebula.view;

import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Param.OptionType;

public interface H5TitleView {
    void ctrlbtIcon(int i, int i2, boolean z);

    ColorDrawable getContentBgView();

    View getContentView();

    View getDivider();

    View getHdividerInTitle();

    TextView getMainTitleView();

    View getOptionMenuContainer();

    View getPopAnchor();

    TextView getSubTitleView();

    String getTitle();

    void openTranslucentStatusBarSupport(int i);

    void releaseViewList();

    void resetTitleColor(int i);

    void setBackCloseBtnImage(String str);

    void setBtIcon(Bitmap bitmap, int i);

    void setH5Page(H5Page h5Page);

    void setIH5TinyPopMenu(IH5TinyPopMenu iH5TinyPopMenu);

    void setImgTitle(Bitmap bitmap);

    void setImgTitle(Bitmap bitmap, String str);

    void setOptionMenu(JSONObject jSONObject);

    void setOptionType(OptionType optionType);

    void setOptionType(OptionType optionType, int i, boolean z);

    void setSubTitle(String str);

    void setTitle(String str);

    View setTitleBarSearch(Bundle bundle);

    void setTitleTxtColor(int i);

    void setTitleView(View view);

    void showBackButton(boolean z);

    void showCloseButton(boolean z);

    void showOptionMenu(boolean z);

    void showTitleDisclaimer(boolean z);

    void showTitleLoading(boolean z);

    void switchToBlueTheme();

    void switchToTitleBar();

    void switchToWhiteTheme();
}

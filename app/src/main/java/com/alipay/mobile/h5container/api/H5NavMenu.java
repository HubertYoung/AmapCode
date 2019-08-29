package com.alipay.mobile.h5container.api;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.view.H5NavMenuItem;
import com.alipay.mobile.nebula.view.H5NavMenuView;
import java.util.List;

public class H5NavMenu implements H5NavMenuView {
    public List<H5NavMenuItem> menuList;

    public void setList(List<H5NavMenuItem> menuList2) {
        this.menuList = menuList2;
    }

    public int getListCount() {
        return this.menuList.size();
    }

    public View getItemView(int position, ViewGroup parent) {
        View content = LayoutInflater.from(parent.getContext()).inflate(R.layout.h5_nav_menu_item, parent, false);
        content.setTag(Integer.valueOf(position));
        H5NavMenuItem item = this.menuList.get(position);
        ((TextView) content.findViewById(R.id.h5_tv_title)).setText(item.name);
        ImageView ivIcon = (ImageView) content.findViewById(R.id.h5_iv_icon);
        if (item.icon != null) {
            ivIcon.setVisibility(0);
            ivIcon.setImageDrawable(item.icon);
        } else {
            ivIcon.setVisibility(8);
        }
        RelativeLayout dotContainer = (RelativeLayout) content.findViewById(R.id.h5_popmenu_dot);
        ImageView dotBg = (ImageView) content.findViewById(R.id.h5_popmenu_dot_bg);
        TextView dotText = (TextView) content.findViewById(R.id.h5_popmenu_dot_num);
        String redDot = item.redDotNum;
        if (TextUtils.isEmpty(redDot)) {
            redDot = "-1";
        }
        if (!TextUtils.isEmpty(redDot)) {
            int dotNum = -1;
            try {
                dotNum = Integer.parseInt(redDot);
            } catch (NumberFormatException e) {
            }
            dotContainer.setVisibility(dotNum >= 0 ? 0 : 8);
            if (dotNum == 0) {
                dotBg.setVisibility(0);
                dotText.setVisibility(8);
            } else if (dotNum > 0) {
                dotText.setVisibility(0);
                dotBg.setVisibility(8);
                dotText.setText(dotNum > 99 ? "···" : String.valueOf(dotNum));
            }
        }
        return content;
    }
}

package com.autonavi.minimap.evaluate.draugorp;

import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.PageContainer;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;

public class EvaluateOther {
    public static String getViewName() {
        String str = "";
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext instanceof AbstractBasePage) {
            PageContainer pageContainer = ((AbstractBasePage) pageContext).getPageContainer();
            if (pageContainer != null) {
                AbstractBasePage cureentRecordPage = pageContainer.getCureentRecordPage();
                str = cureentRecordPage != null ? cureentRecordPage.getClass().getSimpleName() : "";
            }
        }
        Class<?> topPageClass = AMapPageUtil.getTopPageClass();
        if (topPageClass == null) {
            return "$".concat(String.valueOf(str));
        }
        StringBuilder sb = new StringBuilder();
        sb.append(topPageClass.getSimpleName());
        sb.append("$");
        sb.append(str);
        return sb.toString();
    }
}

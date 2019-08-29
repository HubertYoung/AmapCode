package com.autonavi.minimap.basemap.errorback.inter;

import com.autonavi.common.KeyValueStorage;
import com.autonavi.minimap.basemap.errorback.model.HistoryErrorBean;
import com.autonavi.minimap.basemap.errorback.model.ReportErrorBean;
import java.util.ArrayList;
import java.util.List;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public interface IReportErrorManager extends bie {

    @KeepClassMembers
    @Keep
    public interface ErrorReportTaskOtherPageCallback {
        void onRequestFinish(Boolean bool, ReportErrorBean reportErrorBean);
    }

    public interface UserContact extends KeyValueStorage<UserContact> {
        String getContact();

        void setContact(String str);
    }

    void del(HistoryErrorBean historyErrorBean);

    void del(ReportErrorBean reportErrorBean);

    void del(String str, int i);

    void delAll();

    ReportErrorBean getCurrentBean();

    ReportErrorBean getCurrentErrorBean();

    int getErrorListCount(String str);

    ArrayList<HistoryErrorBean> getHistoryList();

    List<ReportErrorBean> getList(String str);

    String getNaviErrorReportFlag();

    boolean hasError(String str);

    ReportErrorBean saveOrUpdate(ReportErrorBean reportErrorBean);

    void setCurrentBean(ReportErrorBean reportErrorBean);

    void setNaviErrorReportFlag(String str);

    void toNaviErrorListPage(String str);
}

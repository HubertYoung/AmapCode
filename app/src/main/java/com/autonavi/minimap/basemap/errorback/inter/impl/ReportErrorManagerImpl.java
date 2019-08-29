package com.autonavi.minimap.basemap.errorback.inter.impl;

import android.os.Environment;
import android.text.TextUtils;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.KeyValueStorage;
import com.autonavi.common.KeyValueStorage.StorageKey;
import com.autonavi.common.PageBundle;
import com.autonavi.common.SQLiteMapper;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.errorback.inter.IReportErrorManager;
import com.autonavi.minimap.basemap.errorback.model.HistoryErrorBean;
import com.autonavi.minimap.basemap.errorback.model.ReportErrorBean;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReportErrorManagerImpl implements IReportErrorManager {
    private SQLiteMapper<ReportErrorBean> a = bkl.a.a(ReportErrorBean.class, AMapAppGlobal.getApplication());
    private ReportErrorBean b;
    private String c;

    @StorageKey(name = "user_contact")
    public interface UserContact extends KeyValueStorage<UserContact> {
        String getContact();

        void setContact(String str);
    }

    public ReportErrorBean saveOrUpdate(ReportErrorBean reportErrorBean) {
        if (this.a == null) {
            return reportErrorBean;
        }
        return (ReportErrorBean) this.a.a(reportErrorBean);
    }

    public void del(ReportErrorBean reportErrorBean) {
        if (this.a != null) {
            this.a.b(Integer.valueOf(reportErrorBean.id));
            File file = new File(reportErrorBean.errorImgUrl);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    public void delAll() {
        List<ReportErrorBean> list = getList("");
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                del(list.get(i));
            }
            if (list != null && list.size() > 0) {
                StringBuilder sb = new StringBuilder();
                sb.append(Environment.getExternalStorageDirectory().toString());
                sb.append("/saved_images");
                a(sb.toString());
            }
        }
    }

    public void del(HistoryErrorBean historyErrorBean) {
        List<ReportErrorBean> list = getList(historyErrorBean.naviId);
        for (int i = 0; i < list.size(); i++) {
            del(list.get(i));
        }
    }

    public void del(String str, int i) {
        List<ReportErrorBean> list = getList(str);
        for (int i2 = 0; i2 < list.size(); i2++) {
            if (i == list.get(i2).id) {
                del(list.get(i2));
                return;
            }
        }
    }

    private static boolean a(String str) {
        boolean z;
        File file;
        File file2 = new File(str);
        if (!file2.exists() || !file2.isDirectory()) {
            return false;
        }
        String[] list = file2.list();
        if (list != null) {
            z = false;
            for (int i = 0; i < list.length; i++) {
                if (str.endsWith(File.separator)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.append(list[i]);
                    file = new File(sb.toString());
                } else {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(str);
                    sb2.append(File.separator);
                    sb2.append(list[i]);
                    file = new File(sb2.toString());
                }
                if (file.isFile()) {
                    file.delete();
                }
                if (file.isDirectory()) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(str);
                    sb3.append("/");
                    sb3.append(list[i]);
                    a(sb3.toString());
                    z = true;
                }
            }
        } else {
            z = false;
        }
        return z;
    }

    public List<ReportErrorBean> getList(String str) {
        List<ReportErrorBean> list;
        if (this.a == null) {
            return null;
        }
        if (!TextUtils.isEmpty(str)) {
            SQLiteMapper<ReportErrorBean> sQLiteMapper = this.a;
            StringBuilder sb = new StringBuilder("reported = 0 and naviId = '");
            sb.append(str);
            sb.append("'");
            list = sQLiteMapper.a(sb.toString());
        } else {
            list = this.a.a((String) "reported = 0");
        }
        for (int i = 0; i < list.size(); i++) {
            list.get(i).beanCovertPoi();
        }
        return list;
    }

    public boolean hasError(String str) {
        List<ReportErrorBean> list = getList(str);
        return list != null && list.size() > 0;
    }

    public int getErrorListCount(String str) {
        List<ReportErrorBean> list = getList(str);
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public void setCurrentBean(ReportErrorBean reportErrorBean) {
        this.b = reportErrorBean;
    }

    public ReportErrorBean getCurrentErrorBean() {
        return this.b;
    }

    public void setNaviErrorReportFlag(String str) {
        this.c = str;
    }

    public String getNaviErrorReportFlag() {
        return this.c;
    }

    public ReportErrorBean getCurrentBean() {
        return this.b;
    }

    public ArrayList<HistoryErrorBean> getHistoryList() {
        ArrayList<HistoryErrorBean> arrayList = new ArrayList<>();
        List<ReportErrorBean> list = getList("");
        if (list != null && list.size() > 0) {
            for (int size = list.size() - 1; size >= 0; size--) {
                ReportErrorBean reportErrorBean = list.get(size);
                if (reportErrorBean != null) {
                    if (arrayList.size() >= 5) {
                        del(reportErrorBean);
                    } else if (!a(arrayList, reportErrorBean.naviId).booleanValue()) {
                        HistoryErrorBean historyErrorBean = new HistoryErrorBean(reportErrorBean.fromPoi == null ? AMapPageUtil.getAppContext().getString(R.string.oper_unknown_loc) : reportErrorBean.fromPoi.getName(), reportErrorBean.endPoi == null ? AMapPageUtil.getAppContext().getString(R.string.oper_unknown_loc) : reportErrorBean.endPoi.getName(), reportErrorBean.naviId, reportErrorBean.date);
                        arrayList.add(historyErrorBean);
                    }
                }
            }
        }
        return arrayList;
    }

    private static Boolean a(ArrayList<HistoryErrorBean> arrayList, String str) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).naviId.equals(str)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    public void toNaviErrorListPage(String str) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString("ReportErrorListFragment.naviId", str);
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            pageContext.startPage((String) "amap.basemap.action.feedback_report_error_list_page", pageBundle);
        }
    }
}

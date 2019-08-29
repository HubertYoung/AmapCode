package com.autonavi.mine.feedback.navi.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.mine.feedback.navi.ReportErrorListPage;
import com.autonavi.mine.feedback.navi.ReportErrorPicFullScreenPage;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.errorback.inter.IReportErrorManager.ErrorReportTaskOtherPageCallback;
import com.autonavi.minimap.basemap.errorback.model.ReportErrorBean;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class ReportListPageAdapter extends BaseAdapter implements ErrorReportTaskOtherPageCallback {
    public static final String CALLBACK = "callback";
    private final ProgressDlg dlg;
    private Context mContext;
    private ArrayList<ReportErrorBean> mList;
    private HashMap<Integer, ReportErrorBean> mMap;
    /* access modifiers changed from: private */
    public String mNaviType;
    /* access modifiers changed from: private */
    public ReportErrorListPage mPage;

    public static final class a {
        public TextView a;
        public TextView b;
        public TextView c;
        public ImageView d;
        public View e;
        public ReportErrorBean f;
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public ReportListPageAdapter(Context context, ArrayList<ReportErrorBean> arrayList, String str, ReportErrorListPage reportErrorListPage) {
        this.mContext = context;
        if (arrayList == null || arrayList.size() <= 0) {
            this.mList = new ArrayList<>();
        } else {
            this.mList = arrayList;
        }
        this.mNaviType = str;
        this.mPage = reportErrorListPage;
        this.mMap = new HashMap<>();
        for (int i = 0; i < this.mList.size(); i++) {
            this.mMap.put(Integer.valueOf(this.mList.get(i).id), this.mList.get(i));
        }
        this.dlg = new ProgressDlg(AMapAppGlobal.getTopActivity(), AMapPageUtil.getAppContext().getString(R.string.oper_updating));
        this.dlg.setCancelable(true);
        this.dlg.setOnCancelListener(new OnCancelListener() {
            public final void onCancel(DialogInterface dialogInterface) {
            }
        });
    }

    public int getCount() {
        return this.mList.size();
    }

    public Object getItem(int i) {
        return Integer.valueOf(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        return loadOrderItem(this.mList.get(i), i, view, viewGroup);
    }

    @SuppressLint({"ResourceAsColor"})
    private View loadOrderItem(final ReportErrorBean reportErrorBean, int i, View view, ViewGroup viewGroup) {
        a aVar;
        if (view == null) {
            aVar = new a();
            view = LayoutInflater.from(this.mContext).inflate(R.layout.item_navi_report_error_list, null);
            aVar.a = (TextView) view.findViewById(R.id.tv_title);
            aVar.b = (TextView) view.findViewById(R.id.tv_time);
            aVar.c = (TextView) view.findViewById(R.id.tv_btn);
            aVar.d = (ImageView) view.findViewById(R.id.iv_pic);
            aVar.e = view.findViewById(R.id.view_bottom);
            view.setTag(aVar);
        } else {
            aVar = (a) view.getTag();
        }
        if (this.mList.size() == 1 || this.mList.indexOf(reportErrorBean) == this.mList.size() - 1) {
            aVar.e.setVisibility(8);
        } else {
            aVar.e.setVisibility(0);
        }
        aVar.f = reportErrorBean;
        ko.a(aVar.d, reportErrorBean.errorImgUrl);
        aVar.a.setText(reportErrorBean.title);
        aVar.b.setText(reportErrorBean.getTime());
        aVar.c.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                ReportListPageAdapter.this.mPage.a(reportErrorBean, ReportListPageAdapter.this.mNaviType);
            }
        });
        aVar.d.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                if (!TextUtils.isEmpty(reportErrorBean.errorImgUrl)) {
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putString(ReportErrorPicFullScreenPage.a, reportErrorBean.errorImgUrl);
                    bid pageContext = AMapPageUtil.getPageContext();
                    if (pageContext != null) {
                        pageContext.startPage(ReportErrorPicFullScreenPage.class, pageBundle);
                    }
                }
            }
        });
        return view;
    }

    public void setDataList(ArrayList<ReportErrorBean> arrayList) {
        this.mList = arrayList;
        this.mMap.clear();
        for (int i = 0; i < this.mList.size(); i++) {
            this.mMap.put(Integer.valueOf(this.mList.get(i).id), this.mList.get(i));
        }
    }

    public void onRequestFinish(Boolean bool, ReportErrorBean reportErrorBean) {
        this.dlg.dismiss();
        if (bool.booleanValue()) {
            this.mList.remove(this.mMap.remove(Integer.valueOf(reportErrorBean.id)));
            notifyDataSetChanged();
        }
    }

    public static Bitmap getLoacalBitmap(String str) {
        try {
            FileInputStream fileInputStream = new FileInputStream(str);
            Options options = new Options();
            options.inPreferredConfig = Config.RGB_565;
            options.inPurgeable = true;
            options.inInputShareable = true;
            return BitmapFactory.decodeStream(fileInputStream, null, options);
        } catch (FileNotFoundException e) {
            kf.a((Throwable) e);
            return null;
        }
    }
}

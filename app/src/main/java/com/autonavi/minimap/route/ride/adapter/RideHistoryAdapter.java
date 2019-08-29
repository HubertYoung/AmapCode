package com.autonavi.minimap.route.ride.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.imageloader.ImageLoader;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.ride.beans.RideTraceHistory;
import com.autonavi.minimap.route.ride.beans.RideTraceHistory.RideType;
import com.autonavi.minimap.route.run.view.PinnedSectionListView.PinnedSectionListAdapter;
import com.uc.webview.export.extension.UCCore;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RideHistoryAdapter implements PinnedSectionListAdapter {
    private SimpleDateFormat dateFomater = new SimpleDateFormat(AMapPageUtil.getAppContext().getString(R.string.ride_date_pattern));
    private List<RideTraceHistory> mDatas;
    /* access modifiers changed from: private */
    public List<Object> mItemList;
    private LayoutInflater mLayoutInflater = LayoutInflater.from(AMapPageUtil.getAppContext());
    /* access modifiers changed from: private */
    public a mListener;
    private List<String> mResultDate = new ArrayList();

    public interface a {
        void a(RideTraceHistory rideTraceHistory);

        void b(RideTraceHistory rideTraceHistory);
    }

    static class b {
        public View a;

        private b() {
        }

        /* synthetic */ b(byte b) {
            this();
        }
    }

    static class c {
        public TextView a;
        public TextView b;
        public TextView c;

        private c() {
        }

        /* synthetic */ c(byte b2) {
            this();
        }
    }

    static class d {
        public ImageView a;
        public ImageView b;
        public TextView c;
        public TextView d;
        public TextView e;
        public TextView f;
        public TextView g;
        public TextView h;

        private d() {
        }

        /* synthetic */ d(byte b2) {
            this();
        }
    }

    public boolean areAllItemsEnabled() {
        return false;
    }

    public long getItemId(int i) {
        return 0;
    }

    public int getViewTypeCount() {
        return 3;
    }

    public boolean hasStableIds() {
        return false;
    }

    public boolean isEmpty() {
        return false;
    }

    public boolean isEnabled(int i) {
        return false;
    }

    public boolean isItemViewTypePinned(int i) {
        return i == 0;
    }

    public void registerDataSetObserver(DataSetObserver dataSetObserver) {
    }

    public void setData() {
    }

    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
    }

    public RideHistoryAdapter(List<RideTraceHistory> list) {
        this.mDatas = list;
        initData(this.mDatas);
    }

    private void initData(List<RideTraceHistory> list) {
        this.mDatas = list;
        if (list != null && list.size() != 0) {
            initDate();
            initAllItemData();
        }
    }

    private void initDate() {
        for (int i = 0; i < this.mDatas.size(); i++) {
            String format = this.dateFomater.format(new Date(this.mDatas.get(i).h));
            if (this.mResultDate.size() == 0 || this.mResultDate.get(this.mResultDate.size() - 1).compareTo(format) != 0) {
                this.mResultDate.add(format);
            }
        }
    }

    private void initAllItemData() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.mDatas.size(); i++) {
            arrayList.add(this.mDatas.get(i));
        }
        for (String next : this.mResultDate) {
            int i2 = 0;
            while (true) {
                if (i2 < arrayList.size()) {
                    if ((arrayList.get(i2) instanceof RideTraceHistory) && next.compareTo(this.dateFomater.format(new Date(((RideTraceHistory) arrayList.get(i2)).h))) == 0) {
                        arrayList.add(i2, next);
                        break;
                    }
                    i2++;
                } else {
                    break;
                }
            }
        }
        this.mItemList = arrayList;
    }

    public boolean setHistoryItemListener(a aVar) {
        this.mListener = aVar;
        return true;
    }

    public int getCount() {
        return this.mItemList.size() + 1;
    }

    public Object getItem(int i) {
        if (i == this.mItemList.size()) {
            return null;
        }
        return this.mItemList.get(i);
    }

    public View getView(final int i, View view, ViewGroup viewGroup) {
        View view2;
        d dVar;
        View view3;
        c cVar;
        if (this.mItemList == null) {
            return view;
        }
        if (i == this.mItemList.size() || (this.mItemList.size() == 0 && i == 0)) {
            if (view == null) {
                b bVar = new b(0);
                view = this.mLayoutInflater.inflate(R.layout.ride_history_list_bottom, null);
                bVar.a = view;
            }
            return view;
        }
        if (getItemViewType(i) == 0) {
            if (view == null) {
                cVar = new c(0);
                view2 = this.mLayoutInflater.inflate(R.layout.ride_history_list_header, null);
                cVar.a = (TextView) view2.findViewById(R.id.running_history_list_distance);
                cVar.b = (TextView) view2.findViewById(R.id.today_run_length);
                cVar.c = (TextView) view2.findViewById(R.id.today_run_length_unit);
                view2.setTag(cVar);
            } else {
                view2 = view;
                cVar = (c) view.getTag();
            }
            List<Object> list = this.mItemList;
            setHeadView(cVar, list, i, (String) list.get(i));
        } else {
            if (view == null) {
                dVar = new d(0);
                view3 = this.mLayoutInflater.inflate(R.layout.ride_history_list_item, null);
                dVar.a = (ImageView) view3.findViewById(R.id.running_history_list_preview_img);
                dVar.b = (ImageView) view3.findViewById(R.id.running_history_item_delete);
                dVar.c = (TextView) view3.findViewById(R.id.running_history_list_distance);
                dVar.d = (TextView) view3.findViewById(R.id.running_history_cp_name);
                dVar.e = (TextView) view3.findViewById(R.id.running_history_list_distance_unit);
                dVar.f = (TextView) view3.findViewById(R.id.running_history_list_time_tip);
                dVar.g = (TextView) view3.findViewById(R.id.running_history_list_heat);
                dVar.h = (TextView) view3.findViewById(R.id.running_history_list_heat_unit);
                efx.a(dVar.c);
                efx.a(dVar.f);
                efx.a(dVar.g);
                view3.setTag(dVar);
            } else {
                view3 = view;
                dVar = (d) view.getTag();
            }
            dVar.b.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    Object obj = RideHistoryAdapter.this.mItemList.get(i);
                    if (obj instanceof RideTraceHistory) {
                        RideHistoryAdapter.this.mListener.b((RideTraceHistory) obj);
                    }
                }
            });
            setItemView(dVar, (RideTraceHistory) this.mItemList.get(i));
        }
        view2.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                Object obj = RideHistoryAdapter.this.mItemList.get(i);
                if (obj instanceof RideTraceHistory) {
                    RideHistoryAdapter.this.mListener.a((RideTraceHistory) obj);
                }
            }
        });
        return view2;
    }

    private int getWidth() {
        Context appContext = AMapPageUtil.getAppContext();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (appContext != null) {
            WindowManager windowManager = (WindowManager) appContext.getSystemService(TemplateTinyApp.WINDOW_KEY);
            if (!(windowManager == null || windowManager.getDefaultDisplay() == null)) {
                windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            }
        }
        return ((int) (((float) displayMetrics.widthPixels) - (displayMetrics.density * 58.0f))) / 2;
    }

    private void setHeadView(c cVar, List<Object> list, int i, String str) {
        cVar.a.setText(str);
        int i2 = 0;
        while (i < list.size()) {
            Object obj = list.get(i);
            if (obj instanceof RideTraceHistory) {
                RideTraceHistory rideTraceHistory = (RideTraceHistory) obj;
                if (str.compareTo(this.dateFomater.format(new Date(rideTraceHistory.h))) != 0) {
                    break;
                }
                i2 += rideTraceHistory.c;
            }
            i++;
        }
        String[] a2 = efv.a(i2);
        if (cVar.b != null) {
            cVar.b.setText(a2[0]);
        }
        if (cVar.c != null) {
            cVar.c.setText(a2[1]);
        }
    }

    private void setItemView(d dVar, RideTraceHistory rideTraceHistory) {
        if (dVar != null && rideTraceHistory != null) {
            Context appContext = AMapPageUtil.getAppContext();
            String str = "";
            if (appContext != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(appContext.getFilesDir().getPath());
                sb.append(File.separator);
                sb.append("rideTrace");
                sb.append(File.separator);
                sb.append(rideTraceHistory.i);
                str = sb.toString();
            }
            if (isBitmapExist(str)) {
                String concat = "file://".concat(String.valueOf(str));
                LayoutParams layoutParams = (LayoutParams) dVar.a.getLayoutParams();
                layoutParams.width = MeasureSpec.makeMeasureSpec(getWidth() - 2, UCCore.VERIFY_POLICY_QUICK);
                layoutParams.height = MeasureSpec.makeMeasureSpec(((getWidth() * 3) / 4) - 2, UCCore.VERIFY_POLICY_QUICK);
                layoutParams.setMargins(1, 1, 1, 1);
                dVar.a.requestLayout();
                if (appContext != null) {
                    ImageLoader.a(appContext).a(concat).a(getWidth(), (getWidth() * 3) / 4).a(dVar.a, (bjl) null);
                }
            } else {
                Bitmap convertResIdToBitmapMatchWidth = convertResIdToBitmapMatchWidth(R.drawable.run_his_load_failed, getWidth());
                dVar.a.setPadding(1, 1, 1, 1);
                dVar.a.setImageBitmap(convertResIdToBitmapMatchWidth);
            }
            if (rideTraceHistory.k == RideType.SHARE_RIDE_TYPE) {
                eab a2 = eaa.a().a(rideTraceHistory.i);
                String str2 = "";
                if (a2 != null) {
                    str2 = new MapSharePreference(SharePreferenceName.SharedPreferences).getStringValue(a2.b, "");
                    if (TextUtils.isEmpty(str2)) {
                        if ("mobike".equalsIgnoreCase(a2.b)) {
                            str2 = "摩拜";
                        } else if ("ofo".equalsIgnoreCase(a2.b)) {
                            str2 = "ofo";
                        }
                    }
                }
                if (!TextUtils.isEmpty(str2)) {
                    dVar.d.setText(str2);
                    dVar.d.setVisibility(0);
                } else {
                    dVar.d.setVisibility(8);
                }
            } else {
                dVar.d.setText("");
                dVar.d.setVisibility(8);
            }
            dVar.f.setText(efv.a((long) rideTraceHistory.b));
            efv.a(dVar.c, dVar.e, efv.a(rideTraceHistory.c));
            dVar.g.setText(efv.b(rideTraceHistory.e));
        }
    }

    public boolean isBitmapExist(String str) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        options.inPreferredConfig = Config.ARGB_8888;
        BitmapFactory.decodeFile(str, options);
        int i = options.outWidth;
        int i2 = options.outHeight;
        if (i == 0 || i2 == 0) {
            return false;
        }
        return true;
    }

    public Bitmap convertResIdToBitmapMatchWidth(int i, int i2) {
        Resources resources;
        float f;
        if (AMapPageUtil.getAppContext() != null) {
            resources = AMapPageUtil.getAppContext().getResources();
            if (resources == null) {
                return null;
            }
        } else {
            resources = null;
        }
        Options options = new Options();
        options.inJustDecodeBounds = true;
        options.inPreferredConfig = Config.ARGB_8888;
        BitmapFactory.decodeResource(resources, i, options);
        float f2 = (float) options.outWidth;
        float f3 = (float) options.outHeight;
        float f4 = 0.0f;
        if (f2 == 0.0f && f3 == 0.0f) {
            return null;
        }
        float f5 = (float) i2;
        if (f2 > f5) {
            f4 = f2 / f5;
            f = f3 / f5;
        } else {
            f = 0.0f;
        }
        options.inJustDecodeBounds = false;
        options.inSampleSize = (int) Math.max(f4, f);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) AMapAppGlobal.getApplication().getSystemService(TemplateTinyApp.WINDOW_KEY);
        if (windowManager != null) {
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        }
        float f6 = (float) ((int) (displayMetrics.density * 113.0f));
        float f7 = (f3 * f5) / f2;
        int i3 = (f7 > f6 ? 1 : (f7 == f6 ? 0 : -1));
        if (i3 <= 0) {
            f5 = (f6 / 3.0f) * 4.0f;
        }
        if (i3 > 0) {
            f6 = f7;
        }
        return Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, i, options), (int) f5, (int) f6, true);
    }

    public int getItemViewType(int i) {
        if (i == getCount() - 1) {
            return 2;
        }
        if (this.mItemList.get(i) instanceof String) {
            return 0;
        }
        return this.mItemList.get(i) instanceof RideTraceHistory ? 1 : 2;
    }
}

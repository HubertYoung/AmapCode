package com.autonavi.minimap.bundle.activities.page;

import android.content.Intent;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.autonavi.bundle.banner.data.BannerItem;
import com.autonavi.minimap.R;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class ActivitiesAdapter extends BaseAdapter {
    private static final int LIMIT_DURATION_DAYS = 29;
    /* access modifiers changed from: private */
    public String AD_TAG = "301";
    private final ReentrantLock clickLock = new ReentrantLock();
    private volatile long latestClick = 0;
    private List<Boolean> mItemDisplayList = new ArrayList();
    private List<BannerItem> mItems = new ArrayList();
    /* access modifiers changed from: private */
    public ActivitiesPage mPage;

    final class a {
        ImageView a;
        TextView b;
        TextView c;

        a(View view) {
            this.a = (ImageView) view.findViewById(R.id.imageView1);
            this.b = (TextView) view.findViewById(R.id.event_current_text_view);
            this.c = (TextView) view.findViewById(R.id.event_past_text_view);
        }

        static Date a(String str) {
            long j;
            try {
                j = (long) (Double.parseDouble(str) * 1000.0d);
            } catch (Throwable unused) {
                j = System.currentTimeMillis();
            }
            return new Date(j);
        }
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public ActivitiesAdapter(ActivitiesPage activitiesPage, List<BannerItem> list) {
        if (list != null) {
            this.mItems.addAll(list);
        }
        resetItemDisplayList();
        this.mPage = activitiesPage;
    }

    /* access modifiers changed from: private */
    public void setGrayScale(Drawable drawable) {
        drawable.setColorFilter(-7829368, Mode.MULTIPLY);
    }

    public void updateBannerList(List<BannerItem> list) {
        this.mItems.clear();
        if (list != null) {
            this.mItems.addAll(list);
        }
        resetItemDisplayList();
        notifyDataSetChanged();
    }

    private void resetItemDisplayList() {
        this.mItemDisplayList.clear();
        for (int i = 0; i < this.mItems.size(); i++) {
            this.mItemDisplayList.add(Boolean.FALSE);
        }
    }

    public int getCount() {
        if (this.mItems == null) {
            return 0;
        }
        return this.mItems.size();
    }

    public Object getItem(int i) {
        if (this.mItems == null) {
            return null;
        }
        return this.mItems.get(i);
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x009c  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x00a9  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.view.View getView(int r10, android.view.View r11, android.view.ViewGroup r12) {
        /*
            r9 = this;
            r12 = 0
            if (r11 != 0) goto L_0x001c
            com.autonavi.minimap.bundle.activities.page.ActivitiesPage r11 = r9.mPage
            android.content.Context r11 = r11.getContext()
            android.view.LayoutInflater r11 = android.view.LayoutInflater.from(r11)
            int r0 = com.autonavi.minimap.R.layout.activitiesitem
            android.view.View r11 = r11.inflate(r0, r12)
            com.autonavi.minimap.bundle.activities.page.ActivitiesAdapter$a r0 = new com.autonavi.minimap.bundle.activities.page.ActivitiesAdapter$a
            r0.<init>(r11)
            r11.setTag(r0)
            goto L_0x0022
        L_0x001c:
            java.lang.Object r0 = r11.getTag()
            com.autonavi.minimap.bundle.activities.page.ActivitiesAdapter$a r0 = (com.autonavi.minimap.bundle.activities.page.ActivitiesAdapter.a) r0
        L_0x0022:
            java.util.List<com.autonavi.bundle.banner.data.BannerItem> r1 = r9.mItems
            java.lang.Object r1 = r1.get(r10)
            com.autonavi.bundle.banner.data.BannerItem r1 = (com.autonavi.bundle.banner.data.BannerItem) r1
            java.lang.String r2 = r1.imageURL
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            r3 = 1
            if (r2 != 0) goto L_0x00e4
            java.lang.String r2 = r1.endDateTimestampInSecond
            com.autonavi.minimap.bundle.activities.page.ActivitiesAdapter r4 = com.autonavi.minimap.bundle.activities.page.ActivitiesAdapter.this
            com.autonavi.minimap.bundle.activities.page.ActivitiesPage r4 = r4.mPage
            com.autonavi.map.fragmentcontainer.page.IPresenter r4 = r4.mPresenter
            cub r4 = (defpackage.cub) r4
            java.lang.String r4 = r4.b
            com.autonavi.minimap.bundle.activities.page.ActivitiesAdapter r5 = com.autonavi.minimap.bundle.activities.page.ActivitiesAdapter.this
            java.lang.String r5 = r5.AD_TAG
            java.lang.String r6 = r1.tag
            boolean r5 = r5.equals(r6)
            r6 = 8
            if (r5 == 0) goto L_0x005c
            android.widget.TextView r2 = r0.b
            r2.setVisibility(r6)
            android.widget.TextView r2 = r0.c
            r2.setVisibility(r6)
            goto L_0x0098
        L_0x005c:
            boolean r5 = android.text.TextUtils.isEmpty(r2)
            if (r5 != 0) goto L_0x0098
            boolean r5 = android.text.TextUtils.isEmpty(r4)
            if (r5 != 0) goto L_0x0098
            java.util.Date r2 = com.autonavi.minimap.bundle.activities.page.ActivitiesAdapter.a.a(r2)
            java.util.Date r4 = com.autonavi.minimap.bundle.activities.page.ActivitiesAdapter.a.a(r4)
            boolean r5 = r2.before(r4)
            r7 = 0
            if (r5 == 0) goto L_0x0082
            android.widget.TextView r2 = r0.b
            r2.setVisibility(r6)
            android.widget.TextView r2 = r0.c
            r2.setVisibility(r7)
            goto L_0x0099
        L_0x0082:
            com.autonavi.minimap.bundle.activities.page.ActivitiesAdapter r8 = com.autonavi.minimap.bundle.activities.page.ActivitiesAdapter.this
            java.lang.String r2 = r8.getDurationDescription(r2, r4)
            android.widget.TextView r4 = r0.b
            r4.setText(r2)
            android.widget.TextView r2 = r0.b
            r2.setVisibility(r7)
            android.widget.TextView r2 = r0.c
            r2.setVisibility(r6)
            goto L_0x0099
        L_0x0098:
            r5 = 1
        L_0x0099:
            r2 = -1
            if (r5 == 0) goto L_0x00a9
            android.widget.ImageView r4 = r0.a
            java.lang.String r5 = r1.imageURL
            com.autonavi.minimap.bundle.activities.page.ActivitiesAdapter$a$1 r6 = new com.autonavi.minimap.bundle.activities.page.ActivitiesAdapter$a$1
            r6.<init>()
            defpackage.ko.a(r4, r5, r12, r2, r6)
            goto L_0x00b0
        L_0x00a9:
            android.widget.ImageView r4 = r0.a
            java.lang.String r5 = r1.imageURL
            defpackage.ko.a(r4, r5, r12, r2)
        L_0x00b0:
            android.util.DisplayMetrics r12 = new android.util.DisplayMetrics
            r12.<init>()
            android.widget.ImageView r2 = r0.a
            android.view.ViewGroup$LayoutParams r2 = r2.getLayoutParams()
            com.autonavi.minimap.bundle.activities.page.ActivitiesAdapter r4 = com.autonavi.minimap.bundle.activities.page.ActivitiesAdapter.this
            com.autonavi.minimap.bundle.activities.page.ActivitiesPage r4 = r4.mPage
            android.app.Activity r4 = r4.getActivity()
            android.view.WindowManager r4 = r4.getWindowManager()
            android.view.Display r4 = r4.getDefaultDisplay()
            r4.getMetrics(r12)
            int r4 = r12.widthPixels
            r2.width = r4
            int r12 = r12.widthPixels
            float r12 = (float) r12
            r4 = 1049941587(0x3e94d653, float:0.29069766)
            float r12 = r12 * r4
            int r12 = (int) r12
            r2.height = r12
            android.widget.ImageView r12 = r0.a
            r12.setLayoutParams(r2)
        L_0x00e4:
            com.autonavi.minimap.bundle.activities.page.ActivitiesAdapter$1 r12 = new com.autonavi.minimap.bundle.activities.page.ActivitiesAdapter$1
            r12.<init>(r1)
            r11.setOnClickListener(r12)
            java.util.List<java.lang.Boolean> r12 = r9.mItemDisplayList
            int r12 = r12.size()
            if (r10 >= r12) goto L_0x012d
            java.util.List<java.lang.Boolean> r12 = r9.mItemDisplayList
            java.lang.Object r12 = r12.get(r10)
            java.lang.Boolean r12 = (java.lang.Boolean) r12
            boolean r12 = r12.booleanValue()
            if (r12 != 0) goto L_0x012d
            esb r12 = defpackage.esb.a.a
            java.lang.Class<vw> r0 = defpackage.vw.class
            esc r12 = r12.a(r0)
            vw r12 = (defpackage.vw) r12
            if (r12 == 0) goto L_0x0126
            java.lang.String r0 = r9.AD_TAG
            java.lang.String r2 = r1.tag
            boolean r0 = r0.equals(r2)
            if (r0 == 0) goto L_0x0126
            java.lang.String r0 = r1.msg_id
            r12.a(r0, r3)
            r0 = 12
            java.lang.String r1 = r1.impression
            r12.a(r0, r1)
        L_0x0126:
            java.util.List<java.lang.Boolean> r12 = r9.mItemDisplayList
            java.lang.Boolean r0 = java.lang.Boolean.TRUE
            r12.set(r10, r0)
        L_0x012d:
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.bundle.activities.page.ActivitiesAdapter.getView(int, android.view.View, android.view.ViewGroup):android.view.View");
    }

    /* access modifiers changed from: private */
    public void prepareIntent(String str) {
        if (!TextUtils.isEmpty(str)) {
            long currentTimeMillis = System.currentTimeMillis();
            this.clickLock.lock();
            try {
                if (Math.abs(currentTimeMillis - this.latestClick) > 500) {
                    this.latestClick = currentTimeMillis;
                    Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
                    intent.putExtra("owner", "banner");
                    this.mPage.startScheme(intent);
                }
            } finally {
                this.clickLock.unlock();
            }
        }
    }

    /* access modifiers changed from: private */
    public String getDurationDescription(Date date, Date date2) {
        int computeDurationInDays = computeDurationInDays(date2, date);
        if (computeDurationInDays > 29) {
            Calendar dateToCalendar = dateToCalendar(date);
            return String.format("%d年%d月%d日结束", new Object[]{Integer.valueOf(dateToCalendar.get(1)), Integer.valueOf(dateToCalendar.get(2) + 1), Integer.valueOf(dateToCalendar.get(5))});
        } else if (computeDurationInDays == 0) {
            return String.format("%d小时", new Object[]{Integer.valueOf(computeDurationInHours(date2, date) + 1)});
        } else {
            return String.format("%d天", new Object[]{Integer.valueOf(computeDurationInDays + 1)});
        }
    }

    private int convertMillis2Hours(long j) {
        return (int) (j / 3600000);
    }

    private int convertMillis2Days(long j) {
        return (int) (j / 86400000);
    }

    private int computeDurationInDays(Date date, Date date2) {
        return convertMillis2Days(date2.getTime() - date.getTime());
    }

    @NonNull
    private Calendar dateToCalendar(Date date) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        return gregorianCalendar;
    }

    private int computeDurationInHours(Date date, Date date2) {
        return convertMillis2Hours(date2.getTime() - date.getTime());
    }
}

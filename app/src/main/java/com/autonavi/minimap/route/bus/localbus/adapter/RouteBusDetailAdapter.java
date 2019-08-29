package com.autonavi.minimap.route.bus.localbus.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.bus.localbus.view.RouteBusStationUpDownNameView;
import com.autonavi.minimap.route.common.view.RouteArrivingBusInfoView;
import java.util.ArrayList;
import java.util.List;

@SuppressLint({"InflateParams", "ClickableViewAccessibility"})
public class RouteBusDetailAdapter extends BaseAdapter implements OnTouchListener {
    public static final int KEY_ALTER_LIST_INDEX = R.id.section_alert_list_layout;
    public static final int KEY_INDEX = R.id.bus_result_alert_List;
    private OnClickListener mClickListener;
    protected Context mContext;
    /* access modifiers changed from: private */
    public c mFollowMeClickListener;
    protected LayoutInflater mInflater;
    private final boolean mScreenShots;
    /* access modifiers changed from: private */
    public final SparseBooleanArray mStationExpandList;
    private final List<dvs> mStationList;

    public interface a {
        void b(View view);

        void c(View view);
    }

    public static class b implements OnClickListener {
        private a a;

        public b(a aVar) {
            this.a = aVar;
        }

        public final void onClick(View view) {
            if (this.a != null) {
                int id = view.getId();
                if (id == R.id.section_alert_list_layout) {
                    this.a.b(view);
                } else if (id == R.id.irregular_time_view) {
                    this.a.c(view);
                } else {
                    int i = R.id.busline_detail_refresh;
                }
            }
        }
    }

    public interface c {
    }

    static class d {
        View a;
        ImageView b;
        TextView c;
        TextView d;
        View e;
        View f;
        RouteBusStationUpDownNameView g;
        RouteBusStationUpDownNameView h;
        View i;
        TextView j;
        TextView k;
        View l;
        View m;
        View n;
        TextView o;
        TextView p;
        TextView q;
        RelativeLayout r;
        TextView s;
        RouteArrivingBusInfoView[] t;
        LinearLayout u;
        LinearLayout v;
        LinearLayout w;
        OnPreDrawListener x;
        OnPreDrawListener y;

        private d() {
            this.x = new OnPreDrawListener() {
                public final boolean onPreDraw() {
                    boolean z;
                    if (d.this.h.getMeasuredWidth() > 0) {
                        d.this.h.getViewTreeObserver().removeOnPreDrawListener(this);
                    }
                    boolean z2 = true;
                    if (d.this.h.mEntranceNameView.getVisibility() != 0) {
                        return true;
                    }
                    ImageView imageView = d.this.h.mEndPointView;
                    View view = d.this.e;
                    int[] iArr = null;
                    if (!(imageView == null || view == null)) {
                        int[] iArr2 = new int[2];
                        float[] fArr = {0.0f, 0.0f};
                        fArr[0] = fArr[0] + ((float) imageView.getLeft());
                        fArr[1] = fArr[1] + ((float) imageView.getTop());
                        ViewParent parent = imageView.getParent();
                        while (true) {
                            if (!(parent instanceof View)) {
                                z = false;
                                break;
                            }
                            View view2 = (View) parent;
                            if (parent == view) {
                                z = true;
                                break;
                            }
                            fArr[0] = fArr[0] - ((float) view2.getScrollX());
                            fArr[1] = fArr[1] - ((float) view2.getScrollY());
                            fArr[0] = fArr[0] + ((float) view2.getLeft());
                            fArr[1] = fArr[1] + ((float) view2.getTop());
                            parent = view2.getParent();
                        }
                        if (z) {
                            iArr2[0] = (int) (fArr[0] + 0.5f);
                            iArr2[1] = (int) (fArr[1] + 0.5f);
                            iArr = iArr2;
                        }
                    }
                    if (iArr != null && iArr.length > 0) {
                        int height = (d.this.e.getHeight() - iArr[1]) - 5;
                        View view3 = d.this.f;
                        if (view3 != null) {
                            LayoutParams layoutParams = view3.getLayoutParams();
                            if (layoutParams instanceof MarginLayoutParams) {
                                MarginLayoutParams marginLayoutParams = (MarginLayoutParams) layoutParams;
                                if (height == Integer.MIN_VALUE || height == marginLayoutParams.bottomMargin) {
                                    z2 = false;
                                } else {
                                    marginLayoutParams.bottomMargin = height;
                                }
                                if (z2) {
                                    view3.requestLayout();
                                }
                            }
                        }
                    }
                    d.this.f.requestLayout();
                    return false;
                }
            };
            this.y = new OnPreDrawListener() {
                public final boolean onPreDraw() {
                    if (d.this.w == null) {
                        return true;
                    }
                    if (d.this.w.getMeasuredWidth() <= 0 || d.this.w.getVisibility() != 0) {
                        if (d.this.w.getVisibility() != 0) {
                            d.this.w.getViewTreeObserver().removeOnPreDrawListener(this);
                        }
                        return true;
                    }
                    d.this.w.getViewTreeObserver().removeOnPreDrawListener(this);
                    int childCount = d.this.w.getChildCount();
                    if (childCount < 2) {
                        return true;
                    }
                    int paddingLeft = d.this.w.getPaddingLeft() + d.this.w.getPaddingRight();
                    for (int i = 0; i < childCount; i++) {
                        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) d.this.w.getChildAt(i).getLayoutParams();
                        paddingLeft += marginLayoutParams.leftMargin + marginLayoutParams.rightMargin;
                    }
                    int measuredWidth = (d.this.w.getMeasuredWidth() - paddingLeft) / 3;
                    for (int i2 = 0; i2 < childCount; i2++) {
                        View childAt = d.this.w.getChildAt(i2);
                        if (childAt instanceof TextView) {
                            ((TextView) childAt).setMaxWidth(measuredWidth);
                        }
                    }
                    d.this.w.requestLayout();
                    return false;
                }
            };
        }

        /* synthetic */ d(byte b2) {
            this();
        }
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public int getViewTypeCount() {
        return 18;
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        return true;
    }

    public RouteBusDetailAdapter(Context context) {
        this(context, false);
    }

    public RouteBusDetailAdapter(Context context, boolean z) {
        this.mContext = context;
        this.mScreenShots = z;
        this.mInflater = LayoutInflater.from(this.mContext);
        this.mStationExpandList = new SparseBooleanArray();
        this.mStationList = new ArrayList();
    }

    public void setBusDetailClickListener(a aVar) {
        this.mClickListener = new b(aVar);
    }

    public void setFollowMeClickListener(c cVar) {
        this.mFollowMeClickListener = cVar;
    }

    public void setData(List<dvs> list) {
        this.mStationList.clear();
        this.mStationList.addAll(list);
        notifyDataSetChanged();
    }

    public List<dvs> getBusStationList() {
        return this.mStationList;
    }

    public void clearExpandStations() {
        this.mStationExpandList.clear();
    }

    public int getCount() {
        return this.mStationList.size();
    }

    public Object getItem(int i) {
        return Integer.valueOf(i);
    }

    public int getItemViewType(int i) {
        return this.mStationList.get(i).A;
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x002f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.view.View getView(int r3, android.view.View r4, android.view.ViewGroup r5) {
        /*
            r2 = this;
            java.util.List<dvs> r5 = r2.mStationList
            java.lang.Object r5 = r5.get(r3)
            dvs r5 = (defpackage.dvs) r5
            int r0 = r2.getItemViewType(r3)
            if (r0 == 0) goto L_0x0029
            r1 = 6
            if (r0 == r1) goto L_0x0024
            r3 = 12
            if (r0 == r3) goto L_0x0029
            switch(r0) {
                case 2: goto L_0x0019;
                case 3: goto L_0x0019;
                default: goto L_0x0018;
            }
        L_0x0018:
            goto L_0x002d
        L_0x0019:
            r3 = 2
            if (r0 != r3) goto L_0x001e
            r3 = 1
            goto L_0x001f
        L_0x001e:
            r3 = 0
        L_0x001f:
            android.view.View r4 = r2.setStartEndPointDesView(r4, r5, r3)
            goto L_0x002d
        L_0x0024:
            android.view.View r4 = r2.setBusStationDesView(r4, r5, r3)
            goto L_0x002d
        L_0x0029:
            android.view.View r4 = r2.setFootOrTransferDesView(r4, r5)
        L_0x002d:
            if (r4 == 0) goto L_0x0032
            r4.setOnTouchListener(r2)
        L_0x0032:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.route.bus.localbus.adapter.RouteBusDetailAdapter.getView(int, android.view.View, android.view.ViewGroup):android.view.View");
    }

    private void setRightDrawable(int i, TextView textView) {
        textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, i, 0);
        textView.setPadding(textView.getPaddingLeft(), 0, agn.a(textView.getContext(), (float) (i == 0 ? 6 : 8)), 0);
    }

    private SpannableStringBuilder getStartEndTimeDes(Context context, int i, String str, String str2, boolean z) {
        if (context == null) {
            return null;
        }
        long a2 = dwk.a();
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append(this.mContext.getString(R.string.route_bus_detail_bus_firsttime)).append(Token.SEPARATOR);
        int i2 = (a2 > -2 ? 1 : (a2 == -2 ? 0 : -1));
        if (i2 == 0 || !z || !(i == 2 || i == 3)) {
            spannableStringBuilder.append(str);
        } else {
            int length = spannableStringBuilder.length();
            spannableStringBuilder.append(str);
            spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.argb(255, 248, 107, 38)), length, spannableStringBuilder.length(), 33);
        }
        spannableStringBuilder.append("   ");
        spannableStringBuilder.append(this.mContext.getString(R.string.route_bus_detail_bus_endtime)).append(Token.SEPARATOR);
        if (i2 == 0 || !z || !(i == 2 || i == 4)) {
            spannableStringBuilder.append(str2);
        } else {
            int length2 = spannableStringBuilder.length();
            spannableStringBuilder.append(str2);
            spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.argb(255, 248, 107, 38)), length2, spannableStringBuilder.length(), 33);
        }
        return spannableStringBuilder;
    }

    /* access modifiers changed from: protected */
    public final <T extends View> T getView(@NonNull View view, int i) {
        SparseArray sparseArray = (SparseArray) view.getTag();
        if (sparseArray == null) {
            sparseArray = new SparseArray();
            view.setTag(sparseArray);
        }
        T t = (View) sparseArray.get(i);
        if (t != null) {
            return t;
        }
        T findViewById = view.findViewById(i);
        sparseArray.put(i, findViewById);
        return findViewById;
    }

    /* access modifiers changed from: protected */
    public View setStartEndPointDesView(View view, dvs dvs, boolean z) {
        if (view == null) {
            view = this.mInflater.inflate(R.layout.route_bus_result_detail_start_end_item, null);
        }
        ((TextView) getView(view, R.id.section_name)).setText(dvs.a);
        ImageView imageView = (ImageView) getView(view, R.id.section_name_icon);
        if (z) {
            imageView.setImageResource(R.drawable.fromto_bus_detail_start_icon);
        } else {
            imageView.setImageResource(R.drawable.fromto_bus_detail_end_icon);
        }
        int i = 0;
        if (z) {
            getView(view, R.id.top_divider).setVisibility(0);
        } else {
            getView(view, R.id.bottom_divider).setVisibility(0);
        }
        View view2 = getView(view, R.id.cur_location_img);
        if (!dvs.p) {
            i = 8;
        }
        view2.setVisibility(i);
        return view;
    }

    /* access modifiers changed from: protected */
    public View setFootOrTransferDesView(View view, final dvs dvs) {
        if (view == null) {
            view = this.mInflater.inflate(R.layout.route_bus_result_detail_foot_transfer_item, null);
            BitmapDrawable bitmapDrawable = new BitmapDrawable(this.mContext.getResources(), BitmapFactory.decodeResource(this.mContext.getResources(), R.drawable.route_bus_detail_line_point));
            bitmapDrawable.setTileModeXY(TileMode.CLAMP, TileMode.REPEAT);
            bitmapDrawable.setDither(true);
            getView(view, R.id.transfer_line).setBackgroundDrawable(bitmapDrawable);
        }
        getView(view, R.id.cur_location_img).setVisibility(dvs.p ? 0 : 8);
        TextView textView = (TextView) getView(view, R.id.section_name);
        int a2 = dvs.a();
        String str = dvs.H >= 0 ? dvs.I : dvs.k != null ? dvs.k.mTransferTip : null;
        StringBuilder sb = new StringBuilder();
        if (a2 == 0) {
            sb.append(AMapAppGlobal.getApplication().getString(R.string.route_foot));
            sb.append(cfe.a(dvs.h));
            String b2 = dvs.b();
            if (!TextUtils.isEmpty(b2)) {
                sb.append(Token.SEPARATOR);
                sb.append(b2);
            }
        } else if (a2 == 1) {
            if (TextUtils.isEmpty(str)) {
                sb.append(AMapAppGlobal.getApplication().getString(R.string.route_bus_detail_stationinner_transfer));
            } else {
                sb.append(str);
            }
            if (dvs.h > 10) {
                sb.append(cfe.a(dvs.h));
                String b3 = dvs.b();
                if (!TextUtils.isEmpty(b3)) {
                    sb.append(Token.SEPARATOR);
                    sb.append(b3);
                }
            }
        } else if (a2 == 2) {
            if (TextUtils.isEmpty(str)) {
                sb.append(AMapAppGlobal.getApplication().getString(R.string.route_bus_detail_bus_station_transfer));
            } else {
                sb.append(str);
            }
        } else if (a2 == 3) {
            if (TextUtils.isEmpty(str)) {
                sb.append(AMapAppGlobal.getApplication().getString(R.string.route_foot));
            } else {
                sb.append(str);
            }
            sb.append(cfe.a(dvs.h));
            String b4 = dvs.b();
            if (!TextUtils.isEmpty(b4) && !b4.equals("约1分钟")) {
                sb.append(Token.SEPARATOR);
                sb.append(b4);
            }
        } else if (a2 == 4) {
            if (TextUtils.isEmpty(str)) {
                sb.append(AMapAppGlobal.getApplication().getString(R.string.route_bus_detail_bus_station_transfer_outer));
            } else {
                sb.append(str);
            }
            sb.append(cfe.a(dvs.h));
            String b5 = dvs.b();
            if (!TextUtils.isEmpty(b5) && !b5.equals("约1分钟")) {
                sb.append(Token.SEPARATOR);
                sb.append(b5);
            }
        } else {
            sb.append(AMapAppGlobal.getApplication().getString(R.string.bus_navi_changeride));
        }
        textView.setText(sb.toString());
        int a3 = dvs.a();
        if (a3 == 0 || a3 == 3 || a3 == 4) {
            getView(view, R.id.transfer_icon).setVisibility(8);
            getView(view, R.id.transfer_line).setVisibility(0);
            getView(view, R.id.foot_icon_container).setVisibility(0);
            NoDBClickUtil.a(getView(view, R.id.follow_me_click_area), (OnClickListener) new OnClickListener() {
                public final void onClick(View view) {
                    if (RouteBusDetailAdapter.this.mFollowMeClickListener != null) {
                        RouteBusDetailAdapter.this.mFollowMeClickListener;
                    }
                }
            });
        } else if (a3 == 1 || a3 == 2) {
            ImageView imageView = (ImageView) getView(view, R.id.transfer_icon);
            imageView.setImageResource(R.drawable.fromto_bus_detail_transfer_icon);
            imageView.setVisibility(0);
            getView(view, R.id.transfer_line).setVisibility(0);
            getView(view, R.id.foot_icon_container).setVisibility(8);
        } else {
            ImageView imageView2 = (ImageView) getView(view, R.id.transfer_icon);
            imageView2.setImageResource(R.drawable.fromto_bus_detail_transfer_default);
            imageView2.setVisibility(0);
            getView(view, R.id.transfer_line).setVisibility(8);
            getView(view, R.id.foot_icon_container).setVisibility(8);
        }
        View view2 = getView(view, 16908290);
        if (a3 == 0 || a3 == 3 || a3 == 4) {
            NoDBClickUtil.a(view2, this.mClickListener);
            view2.setTag(KEY_INDEX, dvs);
        } else {
            view2.setOnClickListener(null);
            view2.setTag(KEY_INDEX, null);
        }
        return view;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:107:0x03ce  */
    /* JADX WARNING: Removed duplicated region for block: B:108:0x03d0  */
    /* JADX WARNING: Removed duplicated region for block: B:111:0x03f6  */
    /* JADX WARNING: Removed duplicated region for block: B:121:0x0465  */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x047c  */
    /* JADX WARNING: Removed duplicated region for block: B:129:0x0486  */
    /* JADX WARNING: Removed duplicated region for block: B:136:0x050b  */
    /* JADX WARNING: Removed duplicated region for block: B:139:0x051e A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:144:0x0533 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:151:0x0545  */
    /* JADX WARNING: Removed duplicated region for block: B:152:0x054a  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x01e7  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x01ea  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0264  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0269  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x02f1  */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x032b  */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x0338  */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x0363  */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x0368  */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x036f  */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x037a  */
    /* JADX WARNING: Removed duplicated region for block: B:95:0x0383  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.view.View setBusStationDesView(android.view.View r25, defpackage.dvs r26, int r27) {
        /*
            r24 = this;
            r6 = r24
            r7 = r26
            r8 = r27
            r9 = 2
            r10 = 3
            r11 = 0
            r12 = 1
            r13 = 0
            if (r25 != 0) goto L_0x012c
            android.view.LayoutInflater r0 = r6.mInflater
            int r1 = com.autonavi.minimap.R.layout.route_bus_result_detail_section_item
            android.view.View r0 = r0.inflate(r1, r11)
            com.autonavi.minimap.route.bus.localbus.adapter.RouteBusDetailAdapter$d r1 = new com.autonavi.minimap.route.bus.localbus.adapter.RouteBusDetailAdapter$d
            r1.<init>(r13)
            int r2 = com.autonavi.minimap.R.id.section_item_title_layout
            android.view.View r2 = r0.findViewById(r2)
            r1.a = r2
            int r2 = com.autonavi.minimap.R.id.section_item_title_icon
            android.view.View r2 = r0.findViewById(r2)
            android.widget.ImageView r2 = (android.widget.ImageView) r2
            r1.b = r2
            int r2 = com.autonavi.minimap.R.id.section_item_title_name
            android.view.View r2 = r0.findViewById(r2)
            android.widget.TextView r2 = (android.widget.TextView) r2
            r1.c = r2
            int r2 = com.autonavi.minimap.R.id.section_item_bus_direction
            android.view.View r2 = r0.findViewById(r2)
            android.widget.TextView r2 = (android.widget.TextView) r2
            r1.d = r2
            int r2 = com.autonavi.minimap.R.id.section_item_content_layout
            android.view.View r2 = r0.findViewById(r2)
            r1.e = r2
            int r2 = com.autonavi.minimap.R.id.content_line_view
            android.view.View r2 = r0.findViewById(r2)
            r1.f = r2
            int r2 = com.autonavi.minimap.R.id.up_station_name_layout
            android.view.View r2 = r0.findViewById(r2)
            com.autonavi.minimap.route.bus.localbus.view.RouteBusStationUpDownNameView r2 = (com.autonavi.minimap.route.bus.localbus.view.RouteBusStationUpDownNameView) r2
            r1.g = r2
            int r2 = com.autonavi.minimap.R.id.down_station_name_layout
            android.view.View r2 = r0.findViewById(r2)
            com.autonavi.minimap.route.bus.localbus.view.RouteBusStationUpDownNameView r2 = (com.autonavi.minimap.route.bus.localbus.view.RouteBusStationUpDownNameView) r2
            r1.h = r2
            int r2 = com.autonavi.minimap.R.id.bus_time_warning_layout
            android.view.View r2 = r0.findViewById(r2)
            r1.i = r2
            int r2 = com.autonavi.minimap.R.id.bus_time_warning_view
            android.view.View r2 = r0.findViewById(r2)
            android.widget.TextView r2 = (android.widget.TextView) r2
            r1.j = r2
            int r2 = com.autonavi.minimap.R.id.content_detail_expand_view
            android.view.View r2 = r0.findViewById(r2)
            android.widget.TextView r2 = (android.widget.TextView) r2
            r1.k = r2
            int r2 = com.autonavi.minimap.R.id.content_detail_layout
            android.view.View r2 = r0.findViewById(r2)
            r1.l = r2
            int r2 = com.autonavi.minimap.R.id.irregular_time_view
            android.view.View r2 = r0.findViewById(r2)
            r1.m = r2
            int r2 = com.autonavi.minimap.R.id.start_end_time_layout
            android.view.View r2 = r0.findViewById(r2)
            r1.n = r2
            int r2 = com.autonavi.minimap.R.id.start_end_time_type
            android.view.View r2 = r0.findViewById(r2)
            android.widget.TextView r2 = (android.widget.TextView) r2
            r1.o = r2
            int r2 = com.autonavi.minimap.R.id.start_end_time_des
            android.view.View r2 = r0.findViewById(r2)
            android.widget.TextView r2 = (android.widget.TextView) r2
            r1.p = r2
            int r2 = com.autonavi.minimap.R.id.interval_time_view
            android.view.View r2 = r0.findViewById(r2)
            android.widget.TextView r2 = (android.widget.TextView) r2
            r1.q = r2
            int r2 = com.autonavi.minimap.R.id.bus_real_time_layout
            android.view.View r2 = r0.findViewById(r2)
            android.widget.RelativeLayout r2 = (android.widget.RelativeLayout) r2
            r1.r = r2
            int r2 = com.autonavi.minimap.R.id.busline_detail_arriving_bus
            android.view.View r2 = r0.findViewById(r2)
            android.widget.TextView r2 = (android.widget.TextView) r2
            r1.s = r2
            com.autonavi.minimap.route.common.view.RouteArrivingBusInfoView[] r2 = new com.autonavi.minimap.route.common.view.RouteArrivingBusInfoView[r10]
            r1.t = r2
            com.autonavi.minimap.route.common.view.RouteArrivingBusInfoView[] r2 = r1.t
            int r3 = com.autonavi.minimap.R.id.arriving_bus_info_item_1
            android.view.View r3 = r0.findViewById(r3)
            com.autonavi.minimap.route.common.view.RouteArrivingBusInfoView r3 = (com.autonavi.minimap.route.common.view.RouteArrivingBusInfoView) r3
            r2[r13] = r3
            com.autonavi.minimap.route.common.view.RouteArrivingBusInfoView[] r2 = r1.t
            int r3 = com.autonavi.minimap.R.id.arriving_bus_info_item_2
            android.view.View r3 = r0.findViewById(r3)
            com.autonavi.minimap.route.common.view.RouteArrivingBusInfoView r3 = (com.autonavi.minimap.route.common.view.RouteArrivingBusInfoView) r3
            r2[r12] = r3
            com.autonavi.minimap.route.common.view.RouteArrivingBusInfoView[] r2 = r1.t
            int r3 = com.autonavi.minimap.R.id.arriving_bus_info_item_3
            android.view.View r3 = r0.findViewById(r3)
            com.autonavi.minimap.route.common.view.RouteArrivingBusInfoView r3 = (com.autonavi.minimap.route.common.view.RouteArrivingBusInfoView) r3
            r2[r9] = r3
            int r2 = com.autonavi.minimap.R.id.real_time_arriving_bus_details
            android.view.View r2 = r0.findViewById(r2)
            int r3 = com.autonavi.minimap.R.drawable.busline_detail_info_white_bg
            r2.setBackgroundResource(r3)
            int r2 = com.autonavi.minimap.R.id.busline_detail_refresh
            android.view.View r2 = r0.findViewById(r2)
            android.view.View$OnClickListener r3 = r6.mClickListener
            com.amap.bundle.utils.ui.NoDBClickUtil.a(r2, r3)
            int r2 = com.autonavi.minimap.R.id.station_list_layout
            android.view.View r2 = r0.findViewById(r2)
            android.widget.LinearLayout r2 = (android.widget.LinearLayout) r2
            r1.u = r2
            int r2 = com.autonavi.minimap.R.id.section_alert_list_layout
            android.view.View r2 = r0.findViewById(r2)
            android.widget.LinearLayout r2 = (android.widget.LinearLayout) r2
            r1.v = r2
            int r2 = com.autonavi.minimap.R.id.alter_list_container
            android.view.View r2 = r0.findViewById(r2)
            android.widget.LinearLayout r2 = (android.widget.LinearLayout) r2
            r1.w = r2
            r0.setTag(r1)
            r15 = r0
        L_0x012a:
            r14 = r1
            goto L_0x0135
        L_0x012c:
            java.lang.Object r1 = r25.getTag()
            com.autonavi.minimap.route.bus.localbus.adapter.RouteBusDetailAdapter$d r1 = (com.autonavi.minimap.route.bus.localbus.adapter.RouteBusDetailAdapter.d) r1
            r15 = r25
            goto L_0x012a
        L_0x0135:
            com.autonavi.bundle.routecommon.entity.BusPathSection r5 = r7.k
            com.autonavi.bundle.routecommon.entity.BusPathSection$IrregularTime r0 = r5.irregulartime
            if (r0 == 0) goto L_0x015c
            com.autonavi.bundle.routecommon.entity.BusPathSection$IrregularTime r0 = r5.irregulartime
            java.lang.String r0 = r0.normalday
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 == 0) goto L_0x0159
            com.autonavi.bundle.routecommon.entity.BusPathSection$IrregularTime r0 = r5.irregulartime
            java.lang.String r0 = r0.workday
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 == 0) goto L_0x0159
            com.autonavi.bundle.routecommon.entity.BusPathSection$IrregularTime r0 = r5.irregulartime
            java.lang.String r0 = r0.holiday
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x015c
        L_0x0159:
            r16 = 1
            goto L_0x015e
        L_0x015c:
            r16 = 0
        L_0x015e:
            int r0 = r5.mBusType
            boolean r0 = com.autonavi.bundle.routecommon.entity.BusPath.isBus(r0)
            if (r0 != 0) goto L_0x0183
            int r0 = r5.mBusType
            boolean r0 = com.autonavi.bundle.routecommon.entity.BusPath.isSubway(r0)
            if (r0 == 0) goto L_0x0171
            int r0 = com.autonavi.minimap.R.drawable.fromto_bus_result_detail_item_subway_icon
            goto L_0x0185
        L_0x0171:
            int r0 = r5.mBusType
            r1 = 12
            if (r0 != r1) goto L_0x017a
            int r0 = com.autonavi.minimap.R.drawable.fromto_bus_result_detail_item_lundu_icon
            goto L_0x0185
        L_0x017a:
            int r0 = r5.mBusType
            r1 = 13
            if (r0 != r1) goto L_0x0183
            int r0 = com.autonavi.minimap.R.drawable.fromto_bus_result_detail_item_suodao_icon
            goto L_0x0185
        L_0x0183:
            int r0 = com.autonavi.minimap.R.drawable.fromto_bus_result_detail_item_bus_icon
        L_0x0185:
            int r1 = r5.getColor()
            r3 = 4633922541587529728(0x404f000000000000, double:62.0)
            int r2 = defpackage.ebn.a(r3, r1)
            android.view.View r3 = r14.a
            int r4 = KEY_INDEX
            r3.setTag(r4, r7)
            android.view.View r3 = r14.a
            r10 = 4634978072750194688(0x4052c00000000000, double:75.0)
            int r4 = defpackage.ebn.a(r10, r1)
            defpackage.dwn.a(r3, r4)
            android.widget.ImageView r3 = r14.b
            r3.setImageResource(r0)
            android.widget.TextView r0 = r14.c
            java.lang.String r3 = r5.mExactSectionName
            r0.setText(r3)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r3 = "("
            r0.append(r3)
            java.lang.String r3 = r5.getSectionDirection(r12)
            r0.append(r3)
            java.lang.String r3 = ")"
            r0.append(r3)
            android.widget.TextView r3 = r14.d
            java.lang.String r0 = r0.toString()
            r3.setText(r0)
            android.view.View r0 = r14.e
            r3 = 4621819117588971520(0x4024000000000000, double:10.0)
            int r1 = defpackage.ebn.a(r3, r1)
            defpackage.dwn.a(r0, r1)
            android.view.View r0 = r14.f
            defpackage.dwn.a(r0, r2)
            int r0 = r7.z
            boolean r0 = com.autonavi.bundle.routecommon.entity.BusPath.isSubway(r0)
            if (r0 == 0) goto L_0x01ea
            java.lang.String r11 = r7.c
            goto L_0x01eb
        L_0x01ea:
            r11 = 0
        L_0x01eb:
            com.autonavi.minimap.route.bus.localbus.view.RouteBusStationUpDownNameView r0 = r14.g
            java.lang.String r1 = r7.b
            r0.setStationData(r1, r11, r2, r12)
            dvs r0 = new dvs
            r0.<init>()
            int r1 = r7.o
            int r1 = r1 - r12
            r0.o = r1
            com.autonavi.minimap.route.bus.localbus.view.RouteBusStationUpDownNameView r1 = r14.g
            int r3 = KEY_INDEX
            r1.setTag(r3, r0)
            int r0 = r7.z
            boolean r0 = com.autonavi.bundle.routecommon.entity.BusPath.isSubway(r0)
            if (r0 == 0) goto L_0x0231
            java.lang.String r0 = r7.e
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x0231
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = r7.e
            r0.<init>(r1)
            java.lang.String r1 = r7.f
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 != 0) goto L_0x022c
            java.lang.String r1 = "-"
            r0.append(r1)
            java.lang.String r1 = r7.f
            r0.append(r1)
        L_0x022c:
            java.lang.String r11 = r0.toString()
            goto L_0x0232
        L_0x0231:
            r11 = 0
        L_0x0232:
            com.autonavi.minimap.route.bus.localbus.view.RouteBusStationUpDownNameView r0 = r14.h
            java.lang.String r1 = r7.d
            r0.setStationData(r1, r11, r2, r13)
            com.autonavi.minimap.route.bus.localbus.view.RouteBusStationUpDownNameView r0 = r14.h
            int r1 = KEY_INDEX
            r0.setTag(r1, r7)
            com.autonavi.minimap.route.bus.localbus.view.RouteBusStationUpDownNameView r0 = r14.h
            android.view.ViewTreeObserver r0 = r0.getViewTreeObserver()
            android.view.ViewTreeObserver$OnPreDrawListener r1 = r14.x
            r0.removeOnPreDrawListener(r1)
            com.autonavi.minimap.route.bus.localbus.view.RouteBusStationUpDownNameView r0 = r14.h
            android.view.ViewTreeObserver r0 = r0.getViewTreeObserver()
            android.view.ViewTreeObserver$OnPreDrawListener r1 = r14.x
            r0.addOnPreDrawListener(r1)
            com.autonavi.bundle.routecommon.entity.BusPathSection r0 = r7.k
            int r10 = r0.busTimeTag
            com.autonavi.bundle.routecommon.entity.BusPathSection r0 = r7.k
            java.lang.String r0 = r0.bus_des
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x0269
            com.autonavi.bundle.routecommon.entity.BusPathSection r0 = r7.k
            java.lang.String r11 = r0.bus_des
            goto L_0x028d
        L_0x0269:
            if (r10 != r9) goto L_0x0274
            android.content.Context r0 = r6.mContext
            int r1 = com.autonavi.minimap.R.string.route_bus_notin_operationtime
            java.lang.String r11 = r0.getString(r1)
            goto L_0x028d
        L_0x0274:
            r0 = 3
            if (r10 != r0) goto L_0x0280
            android.content.Context r0 = r6.mContext
            int r1 = com.autonavi.minimap.R.string.route_bus_firstbus_not_setout
            java.lang.String r11 = r0.getString(r1)
            goto L_0x028d
        L_0x0280:
            r0 = 4
            if (r10 != r0) goto L_0x028c
            android.content.Context r0 = r6.mContext
            int r1 = com.autonavi.minimap.R.string.route_bus_maybe_miss_lastbus
            java.lang.String r11 = r0.getString(r1)
            goto L_0x028d
        L_0x028c:
            r11 = 0
        L_0x028d:
            boolean r0 = r6.mScreenShots
            r4 = 8
            if (r0 != 0) goto L_0x02a5
            boolean r0 = android.text.TextUtils.isEmpty(r11)
            if (r0 == 0) goto L_0x029a
            goto L_0x02a5
        L_0x029a:
            android.widget.TextView r0 = r14.j
            r0.setText(r11)
            android.view.View r0 = r14.i
            r0.setVisibility(r13)
            goto L_0x02aa
        L_0x02a5:
            android.view.View r0 = r14.i
            r0.setVisibility(r4)
        L_0x02aa:
            java.lang.String r0 = r5.stationStartTime
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x02ca
            java.lang.String r0 = r5.stationEndTime
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x02ca
            android.content.Context r0 = r6.mContext
            int r1 = com.autonavi.minimap.R.string.route_bus_detail_bus_home_station
            java.lang.String r11 = r0.getString(r1)
            java.lang.String r0 = r5.stationStartTime
            java.lang.String r1 = r5.stationEndTime
        L_0x02c6:
            r3 = r0
            r17 = r1
            goto L_0x02eb
        L_0x02ca:
            java.lang.String r0 = r5.start_time
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x02e7
            java.lang.String r0 = r5.end_time
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x02e7
            android.content.Context r0 = r6.mContext
            int r1 = com.autonavi.minimap.R.string.route_bus_detail_bus_line
            java.lang.String r11 = r0.getString(r1)
            java.lang.String r0 = r5.start_time
            java.lang.String r1 = r5.end_time
            goto L_0x02c6
        L_0x02e7:
            r3 = 0
            r11 = 0
            r17 = 0
        L_0x02eb:
            boolean r0 = android.text.TextUtils.isEmpty(r11)
            if (r0 != 0) goto L_0x032b
            boolean r0 = r6.mScreenShots
            if (r0 != 0) goto L_0x0300
            android.view.View r0 = r14.i
            int r0 = r0.getVisibility()
            if (r0 != r4) goto L_0x0300
            r18 = 1
            goto L_0x0302
        L_0x0300:
            r18 = 0
        L_0x0302:
            android.widget.TextView r1 = r14.p
            android.content.Context r0 = r6.mContext
            int r4 = r5.busTimeTag
            r19 = r0
            r0 = r6
            r12 = r1
            r1 = r19
            r20 = r2
            r2 = r4
            r9 = 8
            r4 = r17
            r21 = r5
            r5 = r18
            android.text.SpannableStringBuilder r0 = r0.getStartEndTimeDes(r1, r2, r3, r4, r5)
            r12.setText(r0)
            android.widget.TextView r0 = r14.o
            r0.setText(r11)
            android.view.View r0 = r14.n
            r0.setVisibility(r13)
            goto L_0x0336
        L_0x032b:
            r20 = r2
            r21 = r5
            r9 = 8
            android.view.View r0 = r14.n
            r0.setVisibility(r9)
        L_0x0336:
            if (r16 == 0) goto L_0x0354
            android.view.View r0 = r14.n
            int r0 = r0.getVisibility()
            if (r0 != r9) goto L_0x0354
            android.view.View r0 = r14.m
            r1 = r21
            r0.setTag(r1)
            android.view.View r0 = r14.m
            android.view.View$OnClickListener r2 = r6.mClickListener
            com.amap.bundle.utils.ui.NoDBClickUtil.a(r0, r2)
            android.view.View r0 = r14.m
            r0.setVisibility(r13)
            goto L_0x035b
        L_0x0354:
            r1 = r21
            android.view.View r0 = r14.m
            r0.setVisibility(r9)
        L_0x035b:
            android.view.View r0 = r14.n
            int r0 = r0.getVisibility()
            if (r0 != 0) goto L_0x0368
            java.lang.String r11 = r1.getIntervalDesc()
            goto L_0x0369
        L_0x0368:
            r11 = 0
        L_0x0369:
            boolean r0 = android.text.TextUtils.isEmpty(r11)
            if (r0 != 0) goto L_0x037a
            android.widget.TextView r0 = r14.q
            r0.setText(r11)
            android.widget.TextView r0 = r14.q
            r0.setVisibility(r13)
            goto L_0x037f
        L_0x037a:
            android.widget.TextView r0 = r14.q
            r0.setVisibility(r9)
        L_0x037f:
            boolean r0 = r6.mScreenShots
            if (r0 != 0) goto L_0x03c3
            boolean r0 = r1.isRealTime
            if (r0 == 0) goto L_0x03c3
            if (r10 != 0) goto L_0x03bd
            int r0 = r1.mRealTimeStatus
            switch(r0) {
                case 0: goto L_0x03b7;
                case 1: goto L_0x039f;
                case 2: goto L_0x038f;
                case 3: goto L_0x03b7;
                default: goto L_0x038e;
            }
        L_0x038e:
            goto L_0x03c8
        L_0x038f:
            android.widget.TextView r2 = r14.s
            defpackage.ebj.a(r2, r13)
            com.autonavi.minimap.route.common.view.RouteArrivingBusInfoView[] r2 = r14.t
            defpackage.ebj.a(r2, r0)
            android.widget.RelativeLayout r0 = r14.r
            r0.setVisibility(r13)
            goto L_0x03c8
        L_0x039f:
            java.util.List r0 = defpackage.ebj.a(r1)
            android.widget.TextView r2 = r14.s
            int r3 = r0.size()
            defpackage.ebj.a(r2, r3)
            com.autonavi.minimap.route.common.view.RouteArrivingBusInfoView[] r2 = r14.t
            defpackage.ebj.a(r2, r0)
            android.widget.RelativeLayout r0 = r14.r
            r0.setVisibility(r13)
            goto L_0x03c8
        L_0x03b7:
            android.widget.RelativeLayout r0 = r14.r
            r0.setVisibility(r9)
            goto L_0x03c8
        L_0x03bd:
            android.widget.RelativeLayout r0 = r14.r
            r0.setVisibility(r9)
            goto L_0x03c8
        L_0x03c3:
            android.widget.RelativeLayout r0 = r14.r
            r0.setVisibility(r9)
        L_0x03c8:
            int r0 = r7.g
            r2 = 2
            int r0 = r0 - r2
            if (r0 <= 0) goto L_0x03d0
            r2 = 1
            goto L_0x03d1
        L_0x03d0:
            r2 = 0
        L_0x03d1:
            android.widget.LinearLayout r3 = r14.u
            r3.removeAllViews()
            android.widget.TextView r3 = r14.k
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            int r5 = r0 + 1
            r4.append(r5)
            android.content.Context r5 = r6.mContext
            int r10 = com.autonavi.minimap.R.string.route_station
            java.lang.String r5 = r5.getString(r10)
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            r3.setText(r4)
            if (r2 == 0) goto L_0x0465
            android.content.Context r3 = r6.mContext
            r4 = 1077936128(0x40400000, float:3.0)
            int r3 = defpackage.agn.a(r3, r4)
            r4 = 2
            int r3 = r3 / r4
            r4 = 0
            r12 = 0
        L_0x0402:
            if (r4 >= r0) goto L_0x044f
            java.util.ArrayList<com.autonavi.bundle.routecommon.entity.Station> r5 = r7.l
            java.lang.Object r5 = r5.get(r4)
            com.autonavi.bundle.routecommon.entity.Station r5 = (com.autonavi.bundle.routecommon.entity.Station) r5
            android.view.LayoutInflater r10 = r6.mInflater
            int r11 = com.autonavi.minimap.R.layout.route_bus_result_detail_section_include_station_name
            r9 = 0
            android.view.View r10 = r10.inflate(r11, r9)
            int r9 = com.autonavi.minimap.R.id.bus_station_icon
            android.view.View r9 = r10.findViewById(r9)
            r11 = r20
            defpackage.dwn.a(r9, r3, r11)
            int r9 = com.autonavi.minimap.R.id.bus_include_station_name
            android.view.View r9 = r10.findViewById(r9)
            android.widget.TextView r9 = (android.widget.TextView) r9
            java.lang.String r13 = r5.mName
            r9.setText(r13)
            boolean r9 = r7.p
            if (r9 == 0) goto L_0x0441
            boolean r5 = r5.isNearestStation
            if (r5 == 0) goto L_0x0441
            int r5 = com.autonavi.minimap.R.id.bus_station_location_img
            android.view.View r5 = r10.findViewById(r5)
            r9 = 0
            r5.setVisibility(r9)
            r12 = 1
            goto L_0x0442
        L_0x0441:
            r9 = 0
        L_0x0442:
            android.widget.LinearLayout r5 = r14.u
            r5.addView(r10)
            int r4 = r4 + 1
            r20 = r11
            r9 = 8
            r13 = 0
            goto L_0x0402
        L_0x044f:
            r9 = 0
            android.widget.LinearLayout r3 = r14.u
            r3.setVisibility(r9)
            android.widget.TextView r3 = r14.k
            r3.setTag(r14)
            android.widget.TextView r3 = r14.k
            com.autonavi.minimap.route.bus.localbus.adapter.RouteBusDetailAdapter$2 r4 = new com.autonavi.minimap.route.bus.localbus.adapter.RouteBusDetailAdapter$2
            r4.<init>(r8)
            com.amap.bundle.utils.ui.NoDBClickUtil.a(r3, r4)
            goto L_0x0478
        L_0x0465:
            android.widget.LinearLayout r3 = r14.u
            r4 = 8
            r3.setVisibility(r4)
            android.widget.TextView r3 = r14.k
            r4 = 0
            r3.setTag(r4)
            android.widget.TextView r3 = r14.k
            r3.setOnClickListener(r4)
            r12 = 0
        L_0x0478:
            com.autonavi.bundle.routecommon.entity.BusPathSection[] r3 = r1.alter_list
            if (r3 == 0) goto L_0x0483
            com.autonavi.bundle.routecommon.entity.BusPathSection[] r3 = r1.alter_list
            int r3 = r3.length
            if (r3 <= 0) goto L_0x0483
            r3 = 1
            goto L_0x0484
        L_0x0483:
            r3 = 0
        L_0x0484:
            if (r3 == 0) goto L_0x050b
            android.widget.LinearLayout r4 = r14.w
            r4.removeAllViews()
            android.widget.LinearLayout r4 = r14.w
            android.view.ViewTreeObserver r4 = r4.getViewTreeObserver()
            android.view.ViewTreeObserver$OnPreDrawListener r5 = r14.y
            r4.removeOnPreDrawListener(r5)
            com.autonavi.bundle.routecommon.entity.BusPathSection[] r4 = r1.alter_list
            int r10 = r4.length
            r4 = 3
            if (r10 <= r4) goto L_0x049d
            r10 = 3
        L_0x049d:
            r4 = 0
        L_0x049e:
            if (r4 >= r10) goto L_0x04d9
            com.autonavi.bundle.routecommon.entity.BusPathSection[] r5 = r1.alter_list
            r5 = r5[r4]
            android.view.LayoutInflater r9 = r6.mInflater
            int r11 = com.autonavi.minimap.R.layout.route_bus_result_detail_tag_textview
            android.widget.LinearLayout r13 = r14.w
            r22 = r10
            r10 = 0
            android.view.View r9 = r9.inflate(r11, r13, r10)
            int r10 = com.autonavi.minimap.R.id.tag_text
            android.view.View r10 = r9.findViewById(r10)
            android.widget.TextView r10 = (android.widget.TextView) r10
            java.lang.String r11 = r5.mExactSectionName
            r10.setText(r11)
            int r5 = r5.getColor()
            r23 = r12
            r11 = 4633922541587529728(0x404f000000000000, double:62.0)
            int r5 = defpackage.ebn.a(r11, r5)
            defpackage.dwn.a(r10, r5)
            android.widget.LinearLayout r5 = r14.w
            r5.addView(r9)
            int r4 = r4 + 1
            r10 = r22
            r12 = r23
            goto L_0x049e
        L_0x04d9:
            r23 = r12
            android.widget.LinearLayout r4 = r14.w
            android.view.ViewTreeObserver r4 = r4.getViewTreeObserver()
            android.view.ViewTreeObserver$OnPreDrawListener r5 = r14.y
            r4.addOnPreDrawListener(r5)
            android.widget.LinearLayout r4 = r14.w
            r4.requestLayout()
            android.widget.LinearLayout r4 = r14.v
            r4.setTag(r1)
            android.widget.LinearLayout r1 = r14.v
            int r4 = KEY_ALTER_LIST_INDEX
            int r5 = r7.F
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            r1.setTag(r4, r5)
            android.widget.LinearLayout r1 = r14.v
            android.view.View$OnClickListener r4 = r6.mClickListener
            com.amap.bundle.utils.ui.NoDBClickUtil.a(r1, r4)
            android.widget.LinearLayout r1 = r14.v
            r4 = 0
            r1.setVisibility(r4)
            goto L_0x0514
        L_0x050b:
            r23 = r12
            android.widget.LinearLayout r1 = r14.v
            r4 = 8
            r1.setVisibility(r4)
        L_0x0514:
            android.widget.LinearLayout r1 = r14.u
            android.view.ViewGroup$LayoutParams r1 = r1.getLayoutParams()
            android.view.ViewGroup$MarginLayoutParams r1 = (android.view.ViewGroup.MarginLayoutParams) r1
            if (r2 == 0) goto L_0x052c
            if (r3 == 0) goto L_0x052c
            android.content.Context r2 = r6.mContext
            r3 = 1095761920(0x41500000, float:13.0)
            int r2 = defpackage.agn.a(r2, r3)
            r1.bottomMargin = r2
            r9 = 0
            goto L_0x052f
        L_0x052c:
            r9 = 0
            r1.bottomMargin = r9
        L_0x052f:
            boolean r1 = r6.mScreenShots
            if (r1 != 0) goto L_0x0542
            if (r23 != 0) goto L_0x0542
            if (r0 == 0) goto L_0x0542
            android.util.SparseBooleanArray r0 = r6.mStationExpandList
            boolean r0 = r0.get(r8, r9)
            if (r0 == 0) goto L_0x0540
            goto L_0x0542
        L_0x0540:
            r0 = 0
            goto L_0x0543
        L_0x0542:
            r0 = 1
        L_0x0543:
            if (r0 == 0) goto L_0x054a
            r0 = 1
            r6.expandStationItemViews(r14, r8, r0)
            goto L_0x054d
        L_0x054a:
            r6.expandStationItemViews(r14, r8, r9)
        L_0x054d:
            return r15
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.route.bus.localbus.adapter.RouteBusDetailAdapter.setBusStationDesView(android.view.View, dvs, int):android.view.View");
    }

    /* access modifiers changed from: private */
    public void expandStationItemViews(d dVar, int i, boolean z) {
        if (dVar != null) {
            if (z) {
                if (dVar.u.getChildCount() == 0) {
                    setRightDrawable(0, dVar.k);
                    dVar.k.setCompoundDrawablePadding(0);
                } else {
                    setRightDrawable(R.drawable.route_arrow_up, dVar.k);
                    dVar.k.setCompoundDrawablePadding(this.mContext.getResources().getDimensionPixelSize(R.dimen.route_5dp));
                }
                dVar.h.getViewTreeObserver().removeOnPreDrawListener(dVar.x);
                dVar.h.getViewTreeObserver().addOnPreDrawListener(dVar.x);
                dVar.l.setVisibility(0);
            } else {
                if (dVar.u.getChildCount() == 0) {
                    setRightDrawable(0, dVar.k);
                    dVar.k.setCompoundDrawablePadding(0);
                } else {
                    setRightDrawable(R.drawable.route_arrow_down, dVar.k);
                    dVar.k.setCompoundDrawablePadding(this.mContext.getResources().getDimensionPixelSize(R.dimen.route_5dp));
                }
                dVar.h.getViewTreeObserver().removeOnPreDrawListener(dVar.x);
                dVar.h.getViewTreeObserver().addOnPreDrawListener(dVar.x);
                dVar.l.setVisibility(8);
            }
            this.mStationExpandList.append(i, z);
        }
    }
}

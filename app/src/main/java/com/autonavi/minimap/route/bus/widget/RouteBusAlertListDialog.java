package com.autonavi.minimap.route.bus.widget;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.view.accessibility.AccessibilityEvent;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.bundle.routecommon.entity.BusPath;
import com.autonavi.bundle.routecommon.entity.BusPathSection;
import com.autonavi.bundle.routecommon.entity.Trip;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.minimap.R;
import com.autonavi.minimap.navigation.NavigationRequestHolder;
import com.autonavi.minimap.navigation.param.BusAlterLineRequest;
import com.autonavi.minimap.route.bus.localbus.RouteManager$1;
import com.autonavi.minimap.route.bus.localbus.uitl.AlterBusesManager;
import com.autonavi.minimap.route.bus.localbus.uitl.AlterBusesManager.AlterListResponser;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public final class RouteBusAlertListDialog extends AlertDialog implements OnClickListener {
    private ListView a;
    /* access modifiers changed from: private */
    public ArrayList<BusPathSection> b;
    /* access modifiers changed from: private */
    public BusPath c;
    /* access modifiers changed from: private */
    public BusPathSection d;
    private BusAlertListAdapter e;
    /* access modifiers changed from: private */
    public int f = 0;
    /* access modifiers changed from: private */
    public int g;
    /* access modifiers changed from: private */
    public ArrayList<dvr> h;
    private ProgressDlg i;
    /* access modifiers changed from: private */
    public a j;
    /* access modifiers changed from: private */
    public GeoPoint k;
    private Context l;

    class BusAlertListAdapter extends BaseAdapter {
        private List<dvr> mAlertList;

        class a {
            public ImageView a;
            public TextView b;
            public TextView c;
            public TextView d;

            private a() {
            }

            /* synthetic */ a(BusAlertListAdapter busAlertListAdapter, byte b2) {
                this();
            }
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public BusAlertListAdapter(List<dvr> list) {
            if (list == null) {
                this.mAlertList = new ArrayList();
            } else {
                this.mAlertList = list;
            }
        }

        public int getCount() {
            return this.mAlertList.size();
        }

        public Object getItem(int i) {
            return this.mAlertList.get(i);
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            a aVar;
            int i2;
            if (view == null) {
                view = RouteBusAlertListDialog.this.getLayoutInflater().inflate(R.layout.v4_fromto_bus_result_alertlist_item, null);
                aVar = new a(this, 0);
                aVar.a = (ImageView) view.findViewById(R.id.bus_result_list_selector_imgview);
                aVar.b = (TextView) view.findViewById(R.id.bus_result_main_name_textview);
                aVar.c = (TextView) view.findViewById(R.id.bus_result_detail_alertlistitem_time_textview);
                aVar.d = (TextView) view.findViewById(R.id.bus_result_detail_alertlistitem_interval_textview);
                view.setTag(aVar);
            } else {
                aVar = (a) view.getTag();
            }
            dvr dvr = this.mAlertList.get(i);
            if (dvr.e) {
                aVar.a.setVisibility(0);
            } else {
                aVar.a.setVisibility(8);
            }
            aVar.b.setText(dvr.a);
            if (TextUtils.isEmpty(dvr.b) || TextUtils.isEmpty(dvr.c)) {
                aVar.c.setVisibility(8);
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(RouteBusAlertListDialog.this.getContext().getString(R.string.route_bus_detail_bus_firsttime));
                sb.append(Token.SEPARATOR);
                sb.append(dvr.b);
                sb.append("  ");
                sb.append(RouteBusAlertListDialog.this.getContext().getString(R.string.route_bus_detail_bus_endtime));
                sb.append(Token.SEPARATOR);
                sb.append(dvr.c);
                aVar.c.setText(sb.toString());
                aVar.c.setVisibility(0);
            }
            if (TextUtils.isEmpty(dvr.d)) {
                aVar.d.setVisibility(8);
            } else {
                if (dvr.g) {
                    aVar.d.setText(dvr.d);
                } else {
                    SpannableString spannableString = new SpannableString(dvr.d);
                    if (dvr.f) {
                        i2 = dvr.d.indexOf(RouteBusAlertListDialog.this.getContext().getString(R.string.arrive_after_arrived));
                        if (i2 < 0) {
                            i2 = dvr.d.length();
                        }
                    } else {
                        i2 = dvr.d.length();
                    }
                    spannableString.setSpan(new ForegroundColorSpan(-496858), 0, i2, 33);
                    aVar.d.setText(spannableString);
                }
                aVar.d.setVisibility(0);
            }
            return view;
        }
    }

    public interface a {
        void a(boolean z, boolean z2, String str);
    }

    public final boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        return true;
    }

    public RouteBusAlertListDialog(Context context, BusPath busPath, GeoPoint geoPoint, ArrayList<BusPathSection> arrayList, a aVar) {
        super(context);
        this.l = context;
        this.c = busPath;
        this.b = arrayList;
        this.j = aVar;
        this.k = geoPoint;
    }

    /* access modifiers changed from: protected */
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.v4_fromto_bus_result_alertlist_dlg);
        findViewById(R.id.content_view).setOnClickListener(this);
        ((Button) findViewById(R.id.btn_cancel)).setOnClickListener(this);
        this.a = (ListView) findViewById(R.id.bus_result_alert_List);
        this.a.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                RouteBusAlertListDialog.this.d = (BusPathSection) RouteBusAlertListDialog.this.b.get(i);
                if (RouteBusAlertListDialog.this.d != null) {
                    RouteBusAlertListDialog.this.g = i;
                    boolean z = false;
                    for (int i2 = 0; i2 < RouteBusAlertListDialog.this.h.size(); i2++) {
                        dvr dvr = (dvr) RouteBusAlertListDialog.this.h.get(i);
                        if (i2 == i) {
                            dvr.e = true;
                        } else {
                            dvr.e = false;
                        }
                    }
                    AlterBusesManager alterBusesManager = new AlterBusesManager(RouteBusAlertListDialog.this.c);
                    if (!RouteBusAlertListDialog.this.d.isNeedRequest) {
                        z = alterBusesManager.a(RouteBusAlertListDialog.this.d, RouteBusAlertListDialog.this.f);
                    } else {
                        GeoPoint f = RouteBusAlertListDialog.this.k;
                        String str = RouteBusAlertListDialog.this.d.bus_id;
                        String str2 = RouteBusAlertListDialog.this.d.start_id;
                        String str3 = RouteBusAlertListDialog.this.d.end_id;
                        String str4 = RouteBusAlertListDialog.this.d.mCityCode;
                        int e = RouteBusAlertListDialog.this.f;
                        int g = RouteBusAlertListDialog.this.g;
                        ArrayList a = RouteBusAlertListDialog.this.b;
                        AnonymousClass1 r5 = new com.autonavi.minimap.route.bus.localbus.uitl.AlterBusesManager.a() {
                            public final void a() {
                                RouteBusAlertListDialog.h(RouteBusAlertListDialog.this);
                                ToastHelper.showLongToast(RouteBusAlertListDialog.this.getContext().getString(R.string.route_request_server_error));
                            }

                            public final void b() {
                                RouteBusAlertListDialog.h(RouteBusAlertListDialog.this);
                            }

                            public final void a(boolean z) {
                                if (RouteBusAlertListDialog.this.j != null) {
                                    a i = RouteBusAlertListDialog.this.j;
                                    String str = RouteBusAlertListDialog.this.d.bus_id;
                                    RouteBusAlertListDialog.this.d;
                                    i.a(false, z, str);
                                }
                            }
                        };
                        POI createPOI = POIFactory.createPOI("", f);
                        AlterListResponser alterListResponser = new AlterListResponser(a, e, g, r5);
                        BusAlterLineRequest busAlterLineRequest = new BusAlterLineRequest();
                        busAlterLineRequest.b = str;
                        busAlterLineRequest.c = str2;
                        busAlterLineRequest.d = str3;
                        busAlterLineRequest.e = str4;
                        if (createPOI != null) {
                            busAlterLineRequest.g = String.valueOf(createPOI.getPoint().getLatitude());
                            busAlterLineRequest.f = String.valueOf(createPOI.getPoint().getLongitude());
                        }
                        Calendar instance = Calendar.getInstance();
                        instance.setTimeInMillis(System.currentTimeMillis());
                        StringBuilder sb = new StringBuilder();
                        sb.append(instance.get(1));
                        sb.append("-");
                        sb.append(instance.get(2) + 1);
                        sb.append("-");
                        sb.append(instance.get(5));
                        String sb2 = sb.toString();
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(instance.get(11));
                        sb3.append("-");
                        sb3.append(instance.get(12));
                        String sb4 = sb3.toString();
                        busAlterLineRequest.h = sb2;
                        busAlterLineRequest.i = sb4;
                        NavigationRequestHolder.getInstance().sendBusAlterLine(busAlterLineRequest, new RouteManager$1(alterListResponser));
                        RouteBusAlertListDialog.a(RouteBusAlertListDialog.this, (AosRequest) busAlterLineRequest);
                    }
                    if (RouteBusAlertListDialog.this.j != null) {
                        LogManager.actionLogV2("P00019", "B012");
                        new HashMap().put(Integer.valueOf(RouteBusAlertListDialog.this.f), RouteBusAlertListDialog.this.d.bus_id);
                        a i3 = RouteBusAlertListDialog.this.j;
                        boolean z2 = RouteBusAlertListDialog.this.d.isNeedRequest;
                        String str5 = RouteBusAlertListDialog.this.d.bus_id;
                        RouteBusAlertListDialog.this.d;
                        i3.a(z2, z, str5);
                    }
                }
            }
        });
        if (this.h == null) {
            this.h = new ArrayList<>();
        } else {
            this.h.clear();
        }
        for (int i2 = 0; i2 < this.b.size(); i2++) {
            BusPathSection busPathSection = this.b.get(i2);
            dvr dvr = new dvr();
            dvr.a = busPathSection.mExactSectionName;
            dvr.b = busPathSection.start_time;
            dvr.c = busPathSection.end_time;
            boolean z = true;
            if (busPathSection.isRealTime && busPathSection.busTimeTag == 0) {
                BusPathSection busPathSection2 = this.b.get(0);
                if (busPathSection2 != null && busPathSection2.tripList != null && busPathSection2.tripList.size() > 0) {
                    int i3 = 0;
                    while (true) {
                        if (i3 >= busPathSection2.tripList.size()) {
                            break;
                        }
                        Trip trip = busPathSection2.tripList.get(i3);
                        if (trip == null || !trip.lindID.equals(busPathSection.bus_id)) {
                            i3++;
                        } else if (busPathSection.mRealTimeStatus == 1) {
                            dvr.d = ebj.a(getContext(), 2, trip.arrival, trip.station_left);
                            dvr.f = true;
                        } else if (busPathSection.mRealTimeStatus == 2) {
                            dvr.d = getContext().getString(R.string.real_time_bus_no_bus);
                        }
                    }
                    if (TextUtils.isEmpty(dvr.d) && busPathSection.mRealTimeStatus == 2) {
                        dvr.d = getContext().getString(R.string.real_time_bus_no_bus);
                    }
                } else if (busPathSection.mRealTimeStatus == 2) {
                    dvr.d = getContext().getString(R.string.real_time_bus_no_bus);
                }
            }
            if (TextUtils.isEmpty(dvr.d)) {
                dvr.d = busPathSection.getIntervalDesc();
                dvr.g = true;
            }
            if (i2 != 0) {
                z = false;
            }
            dvr.e = z;
            this.h.add(dvr);
        }
        if (this.e == null) {
            this.e = new BusAlertListAdapter(this.h);
            this.a.setAdapter(this.e);
        } else {
            this.e.notifyDataSetChanged();
        }
        Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(R.drawable.transparent);
            LayoutParams attributes = window.getAttributes();
            attributes.gravity = 80;
            attributes.width = -1;
            attributes.height = -2;
            window.setAttributes(attributes);
        }
    }

    public final void onClick(View view) {
        dismiss();
    }

    static /* synthetic */ void h(RouteBusAlertListDialog routeBusAlertListDialog) {
        if (routeBusAlertListDialog.i != null) {
            routeBusAlertListDialog.i.dismiss();
            routeBusAlertListDialog.i = null;
        }
    }

    static /* synthetic */ void a(RouteBusAlertListDialog routeBusAlertListDialog, final AosRequest aosRequest) {
        routeBusAlertListDialog.i = new ProgressDlg((Activity) routeBusAlertListDialog.l, "", "");
        routeBusAlertListDialog.i.setCancelable(true);
        routeBusAlertListDialog.i.setOnCancelListener(new OnCancelListener() {
            public final void onCancel(DialogInterface dialogInterface) {
                if (aosRequest != null) {
                    aosRequest.cancel();
                }
            }
        });
        routeBusAlertListDialog.i.show();
    }
}

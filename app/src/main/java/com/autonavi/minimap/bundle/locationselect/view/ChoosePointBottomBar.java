package com.autonavi.minimap.bundle.locationselect.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alipay.android.phone.mobilesdk.socketcraft.framing.CloseFrame;
import com.amap.bundle.datamodel.FavoritePOI;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.maphome.api.reverse.ReverseGeocodeManager;
import com.autonavi.bundle.maphome.api.reverse.ReverseGeocodeResponser;
import com.autonavi.common.Callback;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.common.utils.Constant.SelectPoiFromMapFragment.SelectFor;
import com.autonavi.map.widget.AmapButton;
import com.autonavi.minimap.R;
import com.autonavi.minimap.widget.AmapTextView;
import java.util.ArrayList;
import java.util.Iterator;

@SuppressLint({"HandlerLeak"})
public class ChoosePointBottomBar extends RelativeLayout {
    private static final String TAG = "ChoosePointBottomBar";
    /* access modifiers changed from: private */
    public a listener;
    private LinearLayout mChoosePointLayout;
    /* access modifiers changed from: private */
    public RelativeLayout mDetailSubinfoLayout;
    /* access modifiers changed from: private */
    public Handler mHandler = new Handler() {
        public final void handleMessage(Message message) {
            ChoosePointBottomBar.this.mListRequestPoint = null;
            switch (message.what) {
                case 1005:
                    ReverseGeocodeResponser reverseGeocodeResponser = (ReverseGeocodeResponser) message.obj;
                    String desc = reverseGeocodeResponser.getDesc();
                    if (desc == null || desc.length() <= 0) {
                        desc = ChoosePointBottomBar.this.mNoAdressInfo;
                    }
                    ArrayList arrayList = new ArrayList();
                    cyo cyo = new cyo();
                    cyo.c = desc;
                    cyo.b = AMapAppGlobal.getApplication().getString(R.string.location_name_title);
                    arrayList.add(cyo);
                    int size = reverseGeocodeResponser.getPoiList().size();
                    for (int i = 0; i < size; i++) {
                        new cyo();
                        POI poi = reverseGeocodeResponser.getPoiList().get(i);
                        cyo cyo2 = new cyo();
                        cyo2.b = poi.getName();
                        cyo2.c = poi.getAddr();
                        cyo2.e = poi.getId();
                        cyo2.a = 1;
                        cyo2.d = poi.getPoint();
                        cyo2.g = poi.getEndPoiExtension();
                        cyo2.h = poi.getTransparent();
                        ArrayList<GeoPoint> entranceList = poi.getEntranceList();
                        if (entranceList != null && entranceList.size() > 0) {
                            ArrayList<GeoPoint> arrayList2 = new ArrayList<>();
                            Iterator<GeoPoint> it = entranceList.iterator();
                            while (it.hasNext()) {
                                GeoPoint next = it.next();
                                arrayList2.add(new GeoPoint(next.x, next.y));
                            }
                            cyo2.i = arrayList2;
                        }
                        cyo2.f = ((FavoritePOI) poi.as(FavoritePOI.class)).getNewType();
                        arrayList.add(cyo2);
                    }
                    ChoosePointBottomBar.this.mPoiAdapter = new PoiListAdapter(arrayList);
                    ChoosePointBottomBar.this.mPoiListView.setAdapter(ChoosePointBottomBar.this.mPoiAdapter);
                    ChoosePointBottomBar.this.mDetailSubinfoLayout.setVisibility(8);
                    ChoosePointBottomBar.this.mPoiListView.setVisibility(0);
                    ChoosePointBottomBar.this.mListRequestPoint = ChoosePointBottomBar.this.mRequestPoint;
                    return;
                case 1006:
                    if (ChoosePointBottomBar.this.mSelectFor != SelectFor.FIX_POI) {
                        ChoosePointBottomBar.this.mPoiListView.setVisibility(8);
                        ChoosePointBottomBar.this.mLoadingRequestLayout.setVisibility(8);
                        ChoosePointBottomBar.this.mRequestErrorLayout.setVisibility(0);
                        return;
                    }
                    ChoosePointBottomBar.this.mDetailSubinfoLayout.setVisibility(8);
                    return;
                case 1014:
                    String desc2 = ((ReverseGeocodeResponser) message.obj).getDesc();
                    if (desc2 == null || desc2.length() <= 0) {
                        ChoosePointBottomBar.this.mNoAdressInfo;
                    }
                    ChoosePointBottomBar.this.mPoiListView.setVisibility(8);
                    ChoosePointBottomBar.this.mLoadingRequestLayout.setVisibility(8);
                    ChoosePointBottomBar.this.mRequestErrorLayout.setVisibility(8);
                    ChoosePointBottomBar.this.mNearNotHaveTextView.setVisibility(0);
                    return;
                case CloseFrame.TLS_ERROR /*1015*/:
                    String desc3 = ((ReverseGeocodeResponser) message.obj).getDesc();
                    if (desc3 == null || desc3.length() <= 0) {
                        ChoosePointBottomBar.this.mNoAdressInfo;
                    }
                    ChoosePointBottomBar.this.mPoiListView.setVisibility(8);
                    ChoosePointBottomBar.this.mDetailSubinfoLayout.setVisibility(8);
                    break;
            }
        }
    };
    /* access modifiers changed from: private */
    public LayoutInflater mLayoutInflater;
    /* access modifiers changed from: private */
    public GeoPoint mListRequestPoint;
    /* access modifiers changed from: private */
    public LinearLayout mLoadingRequestLayout;
    /* access modifiers changed from: private */
    public AmapTextView mNearNotHaveTextView;
    /* access modifiers changed from: private */
    public String mNoAdressInfo = AMapAppGlobal.getApplication().getString(R.string.map_specific_location);
    /* access modifiers changed from: private */
    public PoiListAdapter mPoiAdapter;
    /* access modifiers changed from: private */
    public ListView mPoiListView;
    /* access modifiers changed from: private */
    public b mRequestDoneCallback = null;
    /* access modifiers changed from: private */
    public LinearLayout mRequestErrorLayout;
    /* access modifiers changed from: private */
    public GeoPoint mRequestPoint;
    /* access modifiers changed from: private */
    public SelectFor mSelectFor = null;
    private com.autonavi.common.Callback.a mTaskCancelable;

    class PoiListAdapter extends BaseAdapter {
        /* access modifiers changed from: private */
        public int mClickPosition = 0;
        private ArrayList<cyo> mItems = null;

        class a {
            public RelativeLayout a;
            public ImageView b;
            public TextView c;
            public TextView d;
            public AmapButton e;

            private a() {
            }

            /* synthetic */ a(PoiListAdapter poiListAdapter, byte b2) {
                this();
            }
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public PoiListAdapter(ArrayList<cyo> arrayList) {
            this.mItems = arrayList;
        }

        public int getCount() {
            if (this.mItems == null) {
                return 0;
            }
            return this.mItems.size();
        }

        public Object getItem(int i) {
            return this.mItems.get(i);
        }

        public View getView(final int i, View view, ViewGroup viewGroup) {
            a aVar;
            final cyo cyo = this.mItems.get(i);
            if (view == null) {
                view = ChoosePointBottomBar.this.mLayoutInflater.inflate(R.layout.v4_choose_point_detail_list_item_782, viewGroup, false);
                aVar = new a(this, 0);
                aVar.a = (RelativeLayout) view.findViewById(R.id.choose_point_list_item_layout);
                aVar.b = (ImageView) view.findViewById(R.id.choose_point_list_icon);
                aVar.c = (TextView) view.findViewById(R.id.choose_point_text_name);
                aVar.d = (TextView) view.findViewById(R.id.choose_point_text_address);
                aVar.e = (AmapButton) view.findViewById(R.id.choose_point_submit_btn);
                view.setTag(aVar);
            } else {
                aVar = (a) view.getTag();
            }
            switch (ChoosePointBottomBar.this.mSelectFor) {
                case DEFAULT_POI:
                case FIX_POI:
                case SAVE_POI:
                    aVar.e.setText(R.string.comfirm_submit);
                    break;
                case FROM_POI:
                    aVar.e.setText(R.string.set_as_start);
                    break;
                case MID_POI:
                    aVar.e.setText(R.string.set_as_turn_point);
                    break;
                case TO_POI:
                    aVar.e.setText(R.string.set_as_end);
                    break;
                default:
                    aVar.e.setText(R.string.comfirm_submit);
                    break;
            }
            aVar.c.setText(cyo.b);
            if (cyo.c == null || cyo.c.trim().equals("")) {
                aVar.d.setVisibility(8);
            } else {
                aVar.d.setText(cyo.c);
                aVar.d.setVisibility(0);
            }
            if (i == this.mClickPosition) {
                aVar.a.setBackgroundResource(R.color.map_select_list_item_pressed);
            } else {
                aVar.a.setBackgroundResource(R.color.white);
            }
            aVar.e.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    if (cyo.a == 0) {
                        ChoosePointBottomBar.this.listener.a(cyo.c, null);
                    } else {
                        ChoosePointBottomBar.this.listener.a(cyo.b, cyo);
                    }
                }
            });
            NoDBClickUtil.a((View) aVar.a, (OnClickListener) new OnClickListener() {
                public final void onClick(View view) {
                    if (ChoosePointBottomBar.this.listener != null) {
                        if (cyo.a == 0) {
                            ChoosePointBottomBar.this.listener.a(null);
                        } else {
                            ChoosePointBottomBar.this.listener.a(cyo.d);
                        }
                    }
                    PoiListAdapter.this.mClickPosition = i;
                    ChoosePointBottomBar.this.mPoiAdapter.notifyDataSetChanged();
                    ChoosePointBottomBar.this.mPoiListView.setSelection(PoiListAdapter.this.mClickPosition);
                }
            });
            return view;
        }
    }

    class ReverseGeocodeListener implements Callback<ReverseGeocodeResponser> {
        private ReverseGeocodeListener() {
        }

        /* synthetic */ ReverseGeocodeListener(ChoosePointBottomBar choosePointBottomBar, byte b) {
            this();
        }

        public void error(Throwable th, boolean z) {
            if (ChoosePointBottomBar.this.mRequestDoneCallback != null) {
                ChoosePointBottomBar.this.mRequestDoneCallback.d();
            }
            ChoosePointBottomBar.this.mHandler.sendMessage(ChoosePointBottomBar.this.mHandler.obtainMessage(1006));
        }

        public void callback(ReverseGeocodeResponser reverseGeocodeResponser) {
            if (ChoosePointBottomBar.this.mRequestDoneCallback != null) {
                ChoosePointBottomBar.this.mRequestDoneCallback.d();
            }
            if (reverseGeocodeResponser.errorCode == -1 || reverseGeocodeResponser.errorCode == 500 || reverseGeocodeResponser.errorCode == 7) {
                ChoosePointBottomBar.this.mHandler.sendMessage(ChoosePointBottomBar.this.mHandler.obtainMessage(1006));
            } else if (ChoosePointBottomBar.this.mSelectFor == SelectFor.FIX_POI) {
                Message obtainMessage = ChoosePointBottomBar.this.mHandler.obtainMessage(CloseFrame.TLS_ERROR);
                obtainMessage.obj = reverseGeocodeResponser;
                ChoosePointBottomBar.this.mHandler.sendMessage(obtainMessage);
            } else if (reverseGeocodeResponser.getPoiList().size() == 0) {
                Message obtainMessage2 = ChoosePointBottomBar.this.mHandler.obtainMessage(1014);
                obtainMessage2.obj = reverseGeocodeResponser;
                ChoosePointBottomBar.this.mHandler.sendMessage(obtainMessage2);
            } else {
                Message obtainMessage3 = ChoosePointBottomBar.this.mHandler.obtainMessage(1005);
                obtainMessage3.obj = reverseGeocodeResponser;
                ChoosePointBottomBar.this.mHandler.sendMessage(obtainMessage3);
            }
        }
    }

    public interface a {
        void a(GeoPoint geoPoint);

        void a(String str, cyo cyo);
    }

    public interface b {
        void d();
    }

    public void registerCallback(a aVar) {
        this.listener = aVar;
    }

    public ChoosePointBottomBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public ChoosePointBottomBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    private void init(Context context) {
        this.mLayoutInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        setTag("ChoosePointView");
        addViews();
    }

    private void addViews() {
        View inflate = this.mLayoutInflater.inflate(R.layout.v4_choose_point_detail, this);
        this.mChoosePointLayout = (LinearLayout) inflate.findViewById(R.id.choose_point_layout);
        this.mDetailSubinfoLayout = (RelativeLayout) inflate.findViewById(R.id.choose_point_detail_subinfo_layout);
        this.mNearNotHaveTextView = (AmapTextView) inflate.findViewById(R.id.choose_point_near_nohave_view);
        this.mLoadingRequestLayout = (LinearLayout) inflate.findViewById(R.id.choose_point_loading_request_layout);
        this.mRequestErrorLayout = (LinearLayout) inflate.findViewById(R.id.choose_point_request_error_view);
        NoDBClickUtil.a((View) this.mRequestErrorLayout, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                if (ChoosePointBottomBar.this.mRequestPoint != null) {
                    ChoosePointBottomBar.this.requestPoint(ChoosePointBottomBar.this.mRequestPoint);
                }
            }
        });
        this.mPoiListView = (ListView) inflate.findViewById(R.id.choose_point_detail_list);
        this.mChoosePointLayout.setOnTouchListener(new OnTouchListener() {
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
    }

    public void setSelectType(SelectFor selectFor) {
        this.mSelectFor = selectFor;
    }

    public void setOnRequestDoneCallback(b bVar) {
        this.mRequestDoneCallback = bVar;
    }

    public void requestPoint(GeoPoint geoPoint) {
        cancleNetWork();
        if (this.mSelectFor == SelectFor.FIX_POI) {
            this.mDetailSubinfoLayout.setVisibility(8);
            this.mPoiListView.setVisibility(8);
        } else {
            this.mRequestPoint = geoPoint;
            this.mPoiListView.setVisibility(8);
            this.mDetailSubinfoLayout.setVisibility(0);
            this.mRequestErrorLayout.setVisibility(8);
            this.mNearNotHaveTextView.setVisibility(8);
            this.mLoadingRequestLayout.setVisibility(0);
        }
        this.mTaskCancelable = ReverseGeocodeManager.getReverseGeocodeResult(geoPoint, 10, new ReverseGeocodeListener(this, 0));
    }

    public void cancleNetWork() {
        if (this.mTaskCancelable != null) {
            this.mTaskCancelable.cancel();
            this.mTaskCancelable = null;
        }
        this.mRequestPoint = null;
    }

    public GeoPoint getListRequestPoint() {
        return this.mListRequestPoint;
    }
}

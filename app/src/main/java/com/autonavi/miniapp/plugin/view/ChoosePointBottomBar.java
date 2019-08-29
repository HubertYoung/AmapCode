package com.autonavi.miniapp.plugin.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
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
import com.alipay.mobile.map.model.geocode.PoiItem;
import com.amap.bundle.datamodel.FavoritePOI;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.maphome.api.reverse.ReverseGeocodeManager;
import com.autonavi.bundle.maphome.api.reverse.ReverseGeocodeResponser;
import com.autonavi.common.Callback;
import com.autonavi.common.Callback.a;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.common.utils.Constant.SelectPoiFromMapFragment.SelectFor;
import com.autonavi.map.widget.AmapButton;
import com.autonavi.miniapp.plugin.entity.ChoosePoiListItem;
import com.autonavi.minimap.R;
import com.autonavi.minimap.widget.AmapTextView;
import java.util.ArrayList;
import java.util.Iterator;

@SuppressLint({"HandlerLeak"})
public class ChoosePointBottomBar extends RelativeLayout {
    private static final String TAG = "ChoosePointBottomBar";
    /* access modifiers changed from: private */
    public BottomBarCallListener listener;
    private LinearLayout mChoosePointLayout;
    /* access modifiers changed from: private */
    public RelativeLayout mDetailSubinfoLayout;
    /* access modifiers changed from: private */
    public Handler mHandler = new Handler() {
        public void handleMessage(Message message) {
            ChoosePointBottomBar.this.mListRequestPoint = null;
            switch (message.what) {
                case 1005:
                    ReverseGeocodeResponser reverseGeocodeResponser = (ReverseGeocodeResponser) message.obj;
                    String desc = reverseGeocodeResponser.getDesc();
                    if (desc == null || desc.length() <= 0) {
                        desc = ChoosePointBottomBar.this.mNoAdressInfo;
                    }
                    ArrayList arrayList = new ArrayList();
                    ChoosePoiListItem choosePoiListItem = new ChoosePoiListItem();
                    if (Mode.LOCATION.equals(ChoosePointBottomBar.this.mode)) {
                        choosePoiListItem.mAddress = desc;
                        choosePoiListItem.mPoiName = ChoosePointBottomBar.this.mNoPoiName;
                    } else if (Mode.SEARCH.equals(ChoosePointBottomBar.this.mode)) {
                        choosePoiListItem.mAddress = ChoosePointBottomBar.this.mPoiItem.getSnippet();
                        choosePoiListItem.mPoiName = ChoosePointBottomBar.this.mPoiItem.getTitle();
                        choosePoiListItem.mPoint = new GeoPoint(ChoosePointBottomBar.this.mPoiItem.getLatLonPoint().getLongitude(), ChoosePointBottomBar.this.mPoiItem.getLatLonPoint().getLatitude());
                    }
                    arrayList.add(choosePoiListItem);
                    int size = reverseGeocodeResponser.getPoiList().size();
                    for (int i = 0; i < size; i++) {
                        new ChoosePoiListItem();
                        POI poi = reverseGeocodeResponser.getPoiList().get(i);
                        ChoosePoiListItem choosePoiListItem2 = new ChoosePoiListItem();
                        choosePoiListItem2.mPoiName = poi.getName();
                        choosePoiListItem2.mAddress = poi.getAddr();
                        choosePoiListItem2.mPoiId = poi.getId();
                        choosePoiListItem2.mIconType = 1;
                        choosePoiListItem2.mPoint = poi.getPoint();
                        choosePoiListItem2.mEndPoiExtension = poi.getEndPoiExtension();
                        choosePoiListItem2.mTransparent = poi.getTransparent();
                        ArrayList<GeoPoint> entranceList = poi.getEntranceList();
                        if (entranceList != null && entranceList.size() > 0) {
                            ArrayList<GeoPoint> arrayList2 = new ArrayList<>();
                            Iterator<GeoPoint> it = entranceList.iterator();
                            while (it.hasNext()) {
                                GeoPoint next = it.next();
                                arrayList2.add(new GeoPoint(next.x, next.y));
                            }
                            choosePoiListItem2.mEntranceList = arrayList2;
                        }
                        choosePoiListItem2.mNewType = ((FavoritePOI) poi.as(FavoritePOI.class)).getNewType();
                        arrayList.add(choosePoiListItem2);
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
    public String mNoPoiName = AMapAppGlobal.getApplication().getString(R.string.miniapp_location_name_title);
    /* access modifiers changed from: private */
    public PoiListAdapter mPoiAdapter;
    /* access modifiers changed from: private */
    public PoiItem mPoiItem;
    /* access modifiers changed from: private */
    public ListView mPoiListView;
    /* access modifiers changed from: private */
    public IRequestDoneCallback mRequestDoneCallback = null;
    /* access modifiers changed from: private */
    public LinearLayout mRequestErrorLayout;
    /* access modifiers changed from: private */
    public GeoPoint mRequestPoint;
    /* access modifiers changed from: private */
    public SelectFor mSelectFor = null;
    private a mTaskCancelable;
    /* access modifiers changed from: private */
    public Mode mode = Mode.LOCATION;

    public interface BottomBarCallListener {
        void onClick(View view, String str, ChoosePoiListItem choosePoiListItem);

        void onItemClick(View view, int i, GeoPoint geoPoint);
    }

    public interface IRequestDoneCallback {
        void onRequestDone();
    }

    public enum Mode {
        LOCATION,
        SEARCH
    }

    class PoiListAdapter extends BaseAdapter {
        /* access modifiers changed from: private */
        public int mClickPosition = 0;
        private ArrayList<ChoosePoiListItem> mItems = null;

        class ViewHolder {
            public TextView poi_address_textview;
            public ImageView poi_icon_imageview;
            public RelativeLayout poi_list_item_layout;
            public TextView poi_name_textview;
            public AmapButton poi_type_button;

            private ViewHolder() {
            }
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public PoiListAdapter(ArrayList<ChoosePoiListItem> arrayList) {
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
            ViewHolder viewHolder;
            final ChoosePoiListItem choosePoiListItem = this.mItems.get(i);
            if (view == null) {
                view = ChoosePointBottomBar.this.mLayoutInflater.inflate(R.layout.v4_choose_point_detail_list_item_782, viewGroup, false);
                viewHolder = new ViewHolder();
                viewHolder.poi_list_item_layout = (RelativeLayout) view.findViewById(R.id.choose_point_list_item_layout);
                viewHolder.poi_icon_imageview = (ImageView) view.findViewById(R.id.choose_point_list_icon);
                viewHolder.poi_name_textview = (TextView) view.findViewById(R.id.choose_point_text_name);
                viewHolder.poi_address_textview = (TextView) view.findViewById(R.id.choose_point_text_address);
                viewHolder.poi_type_button = (AmapButton) view.findViewById(R.id.choose_point_submit_btn);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            switch (ChoosePointBottomBar.this.mSelectFor) {
                case DEFAULT_POI:
                case FIX_POI:
                case SAVE_POI:
                    viewHolder.poi_type_button.setText(R.string.comfirm_submit);
                    break;
                case FROM_POI:
                    viewHolder.poi_type_button.setText(R.string.set_as_start);
                    break;
                case MID_POI:
                    viewHolder.poi_type_button.setText(R.string.set_as_turn_point);
                    break;
                case TO_POI:
                    viewHolder.poi_type_button.setText(R.string.set_as_end);
                    break;
                default:
                    viewHolder.poi_type_button.setText(R.string.comfirm_submit);
                    break;
            }
            viewHolder.poi_name_textview.setText(choosePoiListItem.mPoiName);
            if (choosePoiListItem.mAddress == null || choosePoiListItem.mAddress.trim().equals("")) {
                viewHolder.poi_address_textview.setVisibility(8);
            } else {
                viewHolder.poi_address_textview.setText(choosePoiListItem.mAddress);
                viewHolder.poi_address_textview.setVisibility(0);
            }
            if (i == this.mClickPosition) {
                viewHolder.poi_list_item_layout.setBackgroundResource(R.color.map_select_list_item_pressed);
            } else {
                viewHolder.poi_list_item_layout.setBackgroundResource(R.color.white);
            }
            viewHolder.poi_type_button.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (choosePoiListItem.mIconType != 0) {
                        ChoosePointBottomBar.this.listener.onClick(view, choosePoiListItem.mPoiName, choosePoiListItem);
                    } else if (TextUtils.equals(choosePoiListItem.mPoiName, ChoosePointBottomBar.this.mNoPoiName)) {
                        ChoosePointBottomBar.this.listener.onClick(view, choosePoiListItem.mAddress, null);
                    } else {
                        ChoosePointBottomBar.this.listener.onClick(view, choosePoiListItem.mPoiName, choosePoiListItem);
                    }
                }
            });
            NoDBClickUtil.a((View) viewHolder.poi_list_item_layout, (OnClickListener) new OnClickListener() {
                public void onClick(View view) {
                    if (ChoosePointBottomBar.this.listener != null) {
                        if (choosePoiListItem.mIconType == 0) {
                            ChoosePointBottomBar.this.listener.onItemClick(view, i, null);
                        } else {
                            ChoosePointBottomBar.this.listener.onItemClick(view, i, choosePoiListItem.mPoint);
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

        public void error(Throwable th, boolean z) {
            if (ChoosePointBottomBar.this.mRequestDoneCallback != null) {
                ChoosePointBottomBar.this.mRequestDoneCallback.onRequestDone();
            }
            ChoosePointBottomBar.this.mHandler.sendMessage(ChoosePointBottomBar.this.mHandler.obtainMessage(1006));
        }

        public void callback(ReverseGeocodeResponser reverseGeocodeResponser) {
            if (ChoosePointBottomBar.this.mRequestDoneCallback != null) {
                ChoosePointBottomBar.this.mRequestDoneCallback.onRequestDone();
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

    public void registerCallback(BottomBarCallListener bottomBarCallListener) {
        this.listener = bottomBarCallListener;
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
            public void onClick(View view) {
                if (ChoosePointBottomBar.this.mRequestPoint != null) {
                    ChoosePointBottomBar.this.requestPoint(ChoosePointBottomBar.this.mRequestPoint);
                }
            }
        });
        this.mPoiListView = (ListView) inflate.findViewById(R.id.choose_point_detail_list);
        this.mChoosePointLayout.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
    }

    public void setSelectType(SelectFor selectFor) {
        this.mSelectFor = selectFor;
    }

    public void setOnRequestDoneCallback(IRequestDoneCallback iRequestDoneCallback) {
        this.mRequestDoneCallback = iRequestDoneCallback;
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
        this.mTaskCancelable = ReverseGeocodeManager.getReverseGeocodeResult(geoPoint, 10, new ReverseGeocodeListener());
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

    public void setPoiSearched(PoiItem poiItem) {
        this.mPoiItem = poiItem;
    }

    public void setMode(Mode mode2) {
        this.mode = mode2;
    }
}

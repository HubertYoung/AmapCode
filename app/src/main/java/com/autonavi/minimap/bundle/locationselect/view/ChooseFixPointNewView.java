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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.alipay.android.phone.mobilesdk.socketcraft.framing.CloseFrame;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.maphome.api.reverse.ReverseGeocodeManager;
import com.autonavi.bundle.maphome.api.reverse.ReverseGeocodeResponser;
import com.autonavi.common.Callback;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.widget.AmapButton;
import com.autonavi.minimap.R;
import com.autonavi.minimap.widget.AmapTextView;
import java.lang.ref.WeakReference;

@SuppressLint({"HandlerLeak"})
public class ChooseFixPointNewView extends RelativeLayout {
    private static final String TAG = "ChooseFixPointView";
    /* access modifiers changed from: private */
    public a listener;
    private LinearLayout mChoosePointLayout;
    private Handler mHandler;
    /* access modifiers changed from: private */
    public AmapTextView mItemAdressTextView;
    private ImageView mItemIconView;
    private LayoutInflater mLayoutInflater;
    /* access modifiers changed from: private */
    public String mNoAdressInfo;
    /* access modifiers changed from: private */
    public AmapButton mSubmitButton;
    private com.autonavi.common.Callback.a mTaskCancelable;

    static class ReverseGeocodeListener implements Callback<ReverseGeocodeResponser> {
        private WeakReference<ChooseFixPointNewView> weakReference;

        public ReverseGeocodeListener(ChooseFixPointNewView chooseFixPointNewView) {
            this.weakReference = new WeakReference<>(chooseFixPointNewView);
        }

        public void error(Throwable th, boolean z) {
            if (this.weakReference != null && this.weakReference.get() != null) {
                ((ChooseFixPointNewView) this.weakReference.get()).sendOnGeoErrorMsg();
            }
        }

        public void callback(ReverseGeocodeResponser reverseGeocodeResponser) {
            if (reverseGeocodeResponser.errorCode == -1 || reverseGeocodeResponser.errorCode == 500 || reverseGeocodeResponser.errorCode == 7) {
                if (!(this.weakReference == null || this.weakReference.get() == null)) {
                    ((ChooseFixPointNewView) this.weakReference.get()).sendOnGeoErrorMsg();
                }
            } else if (!(this.weakReference == null || this.weakReference.get() == null)) {
                ((ChooseFixPointNewView) this.weakReference.get()).sendOnGeoFixPOIMsg(reverseGeocodeResponser);
            }
        }
    }

    public interface a {
        void a(String str, Object obj);
    }

    public void registerCallback(a aVar) {
        this.listener = aVar;
    }

    public ChooseFixPointNewView(Context context) {
        this(context, null);
    }

    public ChooseFixPointNewView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ChooseFixPointNewView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mNoAdressInfo = AMapAppGlobal.getApplication().getString(R.string.map_specific_location);
        this.mHandler = new Handler() {
            public final void handleMessage(Message message) {
                String str;
                int i = message.what;
                if (i == 1006) {
                    ChooseFixPointNewView.this.mItemAdressTextView.setText(ChooseFixPointNewView.this.mNoAdressInfo);
                    ChooseFixPointNewView.this.mSubmitButton.setTag(ChooseFixPointNewView.this.mNoAdressInfo);
                } else if (i == 1015) {
                    ReverseGeocodeResponser reverseGeocodeResponser = (ReverseGeocodeResponser) message.obj;
                    String desc = reverseGeocodeResponser.getDesc();
                    if (desc == null || desc.length() <= 0) {
                        str = ChooseFixPointNewView.this.mNoAdressInfo;
                    } else {
                        str = AMapAppGlobal.getApplication().getString(R.string.something_nearby, new Object[]{desc});
                    }
                    ChooseFixPointNewView.this.mItemAdressTextView.setText(str);
                    ChooseFixPointNewView.this.mSubmitButton.setTag(R.id.tag_first, str);
                    ChooseFixPointNewView.this.mSubmitButton.setTag(R.id.tag_second, reverseGeocodeResponser);
                }
            }
        };
        init(context);
    }

    private void init(Context context) {
        this.mLayoutInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        setTag(TAG);
        addViews();
    }

    private void addViews() {
        View inflate = this.mLayoutInflater.inflate(R.layout.choose_fixpoint_new_detail, this);
        this.mChoosePointLayout = (LinearLayout) inflate.findViewById(R.id.choose_point_layout);
        this.mSubmitButton = (AmapButton) inflate.findViewById(R.id.choose_point_submit_btn);
        this.mItemIconView = (ImageView) inflate.findViewById(R.id.choose_point_list_icon);
        this.mItemAdressTextView = (AmapTextView) inflate.findViewById(R.id.choose_point_text_address);
        NoDBClickUtil.a((View) this.mSubmitButton, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                if (ChooseFixPointNewView.this.listener != null) {
                    Object tag = view.getTag(R.id.tag_second);
                    ChooseFixPointNewView.this.listener.a((String) view.getTag(R.id.tag_first), tag);
                }
            }
        });
        this.mSubmitButton.setText(R.string.comfirm_submit);
        this.mChoosePointLayout.setOnTouchListener(new OnTouchListener() {
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
    }

    public void requestPoint(GeoPoint geoPoint) {
        cancleNetWork();
        this.mTaskCancelable = ReverseGeocodeManager.getReverseGeocodeResult(geoPoint, new ReverseGeocodeListener(this));
    }

    public void cancleNetWork() {
        if (this.mTaskCancelable != null) {
            this.mTaskCancelable.cancel();
            this.mTaskCancelable = null;
        }
    }

    /* access modifiers changed from: private */
    public void sendOnGeoErrorMsg() {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(1006));
    }

    /* access modifiers changed from: private */
    public void sendOnGeoFixPOIMsg(ReverseGeocodeResponser reverseGeocodeResponser) {
        Message obtainMessage = this.mHandler.obtainMessage(CloseFrame.TLS_ERROR);
        obtainMessage.obj = reverseGeocodeResponser;
        this.mHandler.sendMessage(obtainMessage);
    }
}

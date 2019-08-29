package com.autonavi.minimap.basemap.traffic;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;

public class GestureView extends RelativeLayout implements OnTouchListener {
    public static final int CENTRE = 4;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final int UP = 0;
    OnGestureListener gestureListener = new OnGestureListener() {
        public final boolean onDown(MotionEvent motionEvent) {
            return false;
        }

        public final boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return false;
        }

        public final void onLongPress(MotionEvent motionEvent) {
        }

        public final boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return false;
        }

        public final void onShowPress(MotionEvent motionEvent) {
        }

        public final boolean onSingleTapUp(MotionEvent motionEvent) {
            if (GestureView.this.mViewClickable) {
                GestureView.this.showText(AMapPageUtil.getAppContext().getString(R.string.oper_push_to_talk));
                GestureView.this.mParentLayout.setSelected(false);
                if (GestureView.this.listener != null) {
                    GestureView.this.listener;
                }
            }
            GestureView.this.ivVoice.setImageResource(R.drawable.voice_btn_normal);
            return true;
        }
    };
    private c gestureTouchListener = null;
    /* access modifiers changed from: private */
    public boolean isClickAble = true;
    /* access modifiers changed from: private */
    public ImageView ivVoice;
    /* access modifiers changed from: private */
    public b listener = null;
    private Rect mChangeImageBackgroundRect = null;
    private GestureDetector mGestureDetector;
    /* access modifiers changed from: private */
    public RelativeLayout mParentLayout;
    private TextView mTxtRecord;
    private a mViewClickTimer = new a(0);
    /* access modifiers changed from: private */
    public boolean mViewClickable;

    static class a {
        long a;

        private a() {
        }

        /* synthetic */ a(byte b) {
            this();
        }
    }

    public interface b {
    }

    public interface c {
        boolean a();
    }

    public GestureView(Context context) {
        super(context);
        init(context);
    }

    public GestureView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public GestureView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    private void init(Context context) {
        try {
            this.mParentLayout = (RelativeLayout) ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.audio_layout, null);
            addView(this.mParentLayout);
            this.mParentLayout.findViewById(R.id.traffic_report_voice_btn).setOnTouchListener(this);
            this.mTxtRecord = (TextView) this.mParentLayout.findViewById(R.id.txt_record);
            this.ivVoice = (ImageView) this.mParentLayout.findViewById(R.id.imgVoice);
            this.mGestureDetector = new GestureDetector(context, this.gestureListener);
            this.ivVoice.setImageResource(R.drawable.voice_btn_normal);
            showText(AMapPageUtil.getAppContext().getString(R.string.oper_push_to_talk));
        } catch (Exception e) {
            kf.a((Throwable) e);
        }
    }

    public void seOnGestureListener(b bVar) {
        this.listener = bVar;
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        if ((this.gestureTouchListener != null && this.gestureTouchListener.a()) || !this.isClickAble) {
            return true;
        }
        boolean onTouchEvent = this.mGestureDetector.onTouchEvent(motionEvent);
        if (onTouchEvent) {
            this.isClickAble = false;
            view.postDelayed(new Runnable() {
                public final void run() {
                    GestureView.this.isClickAble = true;
                }
            }, 300);
            return onTouchEvent;
        }
        switch (motionEvent.getAction()) {
            case 0:
                a aVar = this.mViewClickTimer;
                long j = aVar.a;
                aVar.a = System.currentTimeMillis();
                if (!(j == 0 || aVar.a - j >= 1000)) {
                    this.mViewClickable = false;
                    break;
                } else {
                    this.mViewClickable = true;
                    this.ivVoice.setImageResource(R.drawable.voice_btn_press);
                    showText(AMapPageUtil.getAppContext().getString(R.string.oepr_loosen_to_upload));
                    this.mParentLayout.setSelected(true);
                    b bVar = this.listener;
                    break;
                }
                break;
            case 1:
                this.ivVoice.setImageResource(R.drawable.voice_btn_normal);
                if (this.mViewClickable) {
                    showText(AMapPageUtil.getAppContext().getString(R.string.oper_push_to_talk));
                    this.mParentLayout.setSelected(false);
                    isInChangeImageZone(view, (int) motionEvent.getRawX(), (int) motionEvent.getRawY());
                    break;
                }
                break;
            case 2:
                if (this.mViewClickable) {
                    isInChangeImageZone(view, (int) motionEvent.getRawX(), (int) motionEvent.getRawY());
                    break;
                }
                break;
        }
        return false;
    }

    public void showText(String str) {
        this.mTxtRecord.setText(str);
    }

    private boolean isInChangeImageZone(View view, int i, int i2) {
        if (this.mChangeImageBackgroundRect == null) {
            this.mChangeImageBackgroundRect = new Rect();
        }
        view.getDrawingRect(this.mChangeImageBackgroundRect);
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        this.mChangeImageBackgroundRect.left = iArr[0];
        this.mChangeImageBackgroundRect.top = iArr[1];
        this.mChangeImageBackgroundRect.right += iArr[0];
        this.mChangeImageBackgroundRect.bottom += iArr[1];
        return this.mChangeImageBackgroundRect.contains(i, i2);
    }

    public void setGestureTouchListener(c cVar) {
        this.gestureTouchListener = cVar;
    }
}

package com.ali.user.mobile.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;
import com.ali.user.mobile.security.ui.R;
import com.alipay.mobile.beehive.photo.util.DiskFormatter;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.autonavi.minimap.ajx3.util.LogHelper;
import com.taobao.wireless.security.sdk.securesignature.SecureSignatureDefine;

public class APBladeView extends View {
    private OnItemClickListener a;
    protected String[] b = {"A", DiskFormatter.B, "C", "D", "E", "F", DiskFormatter.GB, "H", LogHelper.DEFAULT_LEVEL, "J", DiskFormatter.KB, "L", DiskFormatter.MB, "N", "O", "P", "Q", "R", "S", "T", "U", SecureSignatureDefine.SG_KEY_SIGN_VERSION, "W", "X", "Y", "Z", MetaRecord.LOG_SEPARATOR};
    /* access modifiers changed from: private */
    public PopupWindow c;
    int choose = -1;
    private Handler d = new Handler();
    Runnable dismissRunnable = new Runnable() {
        public void run() {
            if (APBladeView.this.c != null) {
                APBladeView.this.c.dismiss();
            }
        }
    };
    Paint paint = new Paint();
    boolean showBkg = false;

    public interface OnItemClickListener {
        void a(String str);
    }

    public APBladeView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public APBladeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public APBladeView(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.showBkg) {
            canvas.drawColor(Color.parseColor("#00000000"));
        }
        int height = getHeight();
        int width = getWidth();
        int length = height / this.b.length;
        for (int i = 0; i < this.b.length; i++) {
            this.paint.setColor(Color.parseColor("#999999"));
            this.paint.setAntiAlias(true);
            if (height < 400) {
                this.paint.setTextSize(getResources().getDimension(R.dimen.o));
            } else {
                this.paint.setTextSize(getResources().getDimension(R.dimen.n));
            }
            if (i == this.choose) {
                this.paint.setColor(Color.parseColor("#3399ff"));
            }
            canvas.drawText(this.b[i], ((float) (width / 2)) - (this.paint.measureText(this.b[i]) / 2.0f), (float) ((length * i) + length), this.paint);
            this.paint.reset();
        }
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        float y = motionEvent.getY();
        int i = this.choose;
        int height = (int) ((y / ((float) getHeight())) * ((float) this.b.length));
        switch (action) {
            case 0:
                this.showBkg = true;
                if (i != height && height >= 0 && height < this.b.length) {
                    a(height);
                    this.choose = height;
                    invalidate();
                    break;
                }
            case 1:
                this.showBkg = false;
                this.choose = -1;
                this.d.postDelayed(this.dismissRunnable, 800);
                invalidate();
                break;
            case 2:
                if (i != height && height >= 0 && height < this.b.length) {
                    a(height);
                    this.choose = height;
                    invalidate();
                    break;
                }
        }
        return true;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return super.onTouchEvent(motionEvent);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.a = onItemClickListener;
    }

    private void a(int i) {
        if (this.a != null) {
            this.a.a(this.b[i]);
        }
    }
}

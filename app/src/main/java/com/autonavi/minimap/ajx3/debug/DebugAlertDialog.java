package com.autonavi.minimap.ajx3.debug;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.R;

public class DebugAlertDialog extends Dialog {
    private OnClickListener mCancelBtnClickListner = new OnClickListener() {
        public void onClick(View view) {
            DebugAlertDialog.this.dismiss();
        }
    };
    private Button mCancle;
    private TextView mContent;
    private long mContextId;
    private CountDownTimer mCountDownTimer;
    private int mWaitingTime = 0;

    public DebugAlertDialog(@NonNull Context context) {
        super(context);
    }

    public void init() {
        setCanceledOnTouchOutside(false);
        requestWindowFeature(1);
        if (VERSION.SDK_INT >= 26) {
            getWindow().setType(2038);
        } else {
            getWindow().setType(2002);
        }
        View inflate = getLayoutInflater().inflate(R.layout.waiting_dialog, null);
        this.mCancle = (Button) inflate.findViewById(R.id.cancel);
        this.mCancle.setOnClickListener(this.mCancelBtnClickListner);
        this.mContent = (TextView) inflate.findViewById(R.id.pageurl);
        setContentView(inflate);
    }

    public void setData(long j, String str, int i) {
        this.mContextId = j;
        this.mWaitingTime = i;
        Button button = this.mCancle;
        StringBuilder sb = new StringBuilder("取消(");
        sb.append(this.mWaitingTime);
        sb.append(")");
        button.setText(sb.toString());
        TextView textView = this.mContent;
        StringBuilder sb2 = new StringBuilder("调试:");
        sb2.append(str);
        sb2.append("\n等待调试器加载...");
        textView.setText(sb2.toString());
    }

    public void show() {
        if (this.mWaitingTime > 0) {
            if (VERSION.SDK_INT < 23 || Settings.canDrawOverlays(getContext())) {
                AnonymousClass2 r2 = new CountDownTimer((long) (this.mWaitingTime * 1000), 1000) {
                    public void onTick(long j) {
                        DebugAlertDialog.this.updateBtnText((int) Math.round(((double) j) / 1000.0d));
                    }

                    public void onFinish() {
                        DebugAlertDialog.this.dismiss();
                    }
                };
                this.mCountDownTimer = r2;
                this.mCountDownTimer.start();
                super.show();
                Ajx.getInstance().reShowBlueBall();
                return;
            }
            getContext().startActivity(new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION"));
        }
    }

    public void updateBtnText(int i) {
        Button button = this.mCancle;
        StringBuilder sb = new StringBuilder("取消(");
        sb.append(i);
        sb.append(")");
        button.setText(sb.toString());
    }

    public void dismiss() {
        this.mCountDownTimer.cancel();
        Ajx.getInstance().cancelDebuggerWait(this.mContextId);
        super.dismiss();
    }
}

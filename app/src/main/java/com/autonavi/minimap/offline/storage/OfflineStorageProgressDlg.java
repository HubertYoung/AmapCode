package com.autonavi.minimap.offline.storage;

import android.app.Activity;
import android.view.KeyEvent;
import android.widget.TextView;
import com.amap.bundle.utils.ui.CompatDialog;
import com.autonavi.minimap.R;

public class OfflineStorageProgressDlg extends CompatDialog {
    private MyOnCancelListener mCancelListener;
    private TextView tvMsg;

    public interface MyOnCancelListener {
        void onCancel();
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4 || this.mCancelListener == null) {
            return super.onKeyDown(i, keyEvent);
        }
        this.mCancelListener.onCancel();
        return true;
    }

    public OfflineStorageProgressDlg(Activity activity) {
        this(activity, null);
    }

    public OfflineStorageProgressDlg(Activity activity, String str) {
        super(activity, R.style.custom_dlg);
        requestWindowFeature(1);
        setContentView(R.layout.widget_fix_progress_dlg);
        this.tvMsg = (TextView) findViewById(R.id.msg);
        if (str != null && !str.equals("")) {
            this.tvMsg.setText(str);
        }
    }

    public void setMyCancelListener(MyOnCancelListener myOnCancelListener) {
        this.mCancelListener = myOnCancelListener;
    }

    public void setMessage(String str) {
        if (str != null && !str.equals("")) {
            this.tvMsg.setText(str);
        }
    }
}

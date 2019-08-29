package defpackage;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.autonavi.minimap.R;
import com.autonavi.minimap.widget.ListDialog;

/* renamed from: qw reason: default package */
/* compiled from: QuickAutoNaviSettingDlg */
public final class qw extends ListDialog {
    public qw(Activity activity) {
        super(activity);
    }

    public final void setView() {
        setContentView(R.layout.autonavi_list_dlg);
        if (this.mRefreshListener == null) {
            this.listView = (ListView) findViewById(R.id.list);
            this.listView.setVisibility(0);
        } else {
            this.listView = initPullList();
            this.listView.setVisibility(0);
        }
        this.tvTitle = (TextView) findViewById(R.id.title);
        this.comfirm = (Button) findViewById(R.id.btn_confirm);
        this.cancel = (Button) findViewById(R.id.clean_history);
        this.cancel.setText(R.string.cancel);
        this.cancel.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                qw.this.dismiss();
            }
        });
        this.m_btnNetSearch = (Button) findViewById(R.id.btn_netsearch);
        View findViewById = findViewById(R.id.rootView);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                }
            });
        }
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        int i = this.listView.getResources().getConfiguration().orientation;
        this.listView.setOnTouchListener(new OnTouchListener() {
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });
    }
}

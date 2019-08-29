package com.autonavi.minimap.widget;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.minimap.R;
import java.lang.ref.WeakReference;
import org.json.JSONException;
import org.json.JSONObject;

public class SyncPopupWindow {
    PopupWindow a;
    WeakReference<View> b;

    public SyncPopupWindow(View view) {
        this.b = new WeakReference<>(view);
        View inflate = LayoutInflater.from(view.getContext()).inflate(R.layout.sync_popup_bar, null);
        this.a = new PopupWindow(inflate, -1, -2, false);
        this.a.setContentView(inflate);
        ((Button) inflate.findViewById(R.id.sync_popup_login_button)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("from", 8);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                LogManager.actionLogV2("P00400", "B001", jSONObject);
                bin.a.q();
                SyncPopupWindow.this.a.dismiss();
            }
        });
        ((ImageButton) inflate.findViewById(R.id.sync_popup_close_button)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SyncPopupWindow.this.a.dismiss();
            }
        });
    }

    public void show() {
        if (this.a != null) {
            aho.a(new Runnable() {
                public void run() {
                    if (SyncPopupWindow.this.b != null) {
                        View view = (View) SyncPopupWindow.this.b.get();
                        if (view != null) {
                            try {
                                if (view.getWindowToken() != null) {
                                    SyncPopupWindow.this.a.showAtLocation(view, 80, 0, 0);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
        }
    }

    public void hide() {
        if (this.a != null) {
            aho.a(new Runnable() {
                public void run() {
                    SyncPopupWindow.this.a.dismiss();
                }
            });
        }
    }

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.a.setOnDismissListener(onDismissListener);
    }
}

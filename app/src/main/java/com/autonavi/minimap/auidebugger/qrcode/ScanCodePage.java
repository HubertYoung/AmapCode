package com.autonavi.minimap.auidebugger.qrcode;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.qrscan.scanner.ScanView;
import org.json.JSONArray;

public class ScanCodePage extends AbstractBasePage<cnx> {
    public View a;
    public ProgressBar b;
    public TextView c;
    public TextView d;
    public RecyclerView e;
    public ScanView f;
    /* access modifiers changed from: private */
    public boolean g;

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.ajxdebug_scan_layout);
    }

    public final void a() {
        while (this.g) {
            final JSONArray b2 = cny.b(getContext());
            if (b2.length() <= 0) {
                this.g = false;
            } else {
                b();
                this.d.setText("关闭");
                this.e.setVisibility(0);
                final LayoutInflater from = LayoutInflater.from(getContext());
                this.e.setAdapter(new Adapter() {
                    public final ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                        return new ViewHolder(from.inflate(R.layout.ajxdebug_scan_histroy, viewGroup, false)) {
                        };
                    }

                    public final void onBindViewHolder(ViewHolder viewHolder, int i) {
                        final TextView textView = (TextView) viewHolder.itemView.findViewById(R.id.history);
                        viewHolder.itemView.findViewById(R.id.line).setVisibility(i == 0 ? 8 : 0);
                        textView.setText(b2.optString(i));
                        viewHolder.itemView.setOnClickListener(new OnClickListener() {
                            public final void onClick(View view) {
                                ((cnx) ScanCodePage.this.mPresenter).a(textView.getText().toString());
                            }
                        });
                    }

                    public final int getItemCount() {
                        return b2.length();
                    }
                });
                return;
            }
        }
        c();
        this.d.setText("历史");
        this.e.setVisibility(8);
    }

    public final void b() {
        this.f.setVisibility(4);
        this.f.onPause();
    }

    public final void c() {
        this.f.onResume();
        this.f.setVisibility(0);
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new cnx(this);
    }
}

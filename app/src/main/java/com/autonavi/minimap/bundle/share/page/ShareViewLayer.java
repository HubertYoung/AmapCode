package com.autonavi.minimap.bundle.share.page;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.autonavi.map.fragmentcontainer.IViewLayer;
import com.autonavi.map.template.AbstractViewHolderBaseAdapter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.widget.SimpleList;
import java.util.ArrayList;

public final class ShareViewLayer implements IViewLayer {
    private View a;
    /* access modifiers changed from: private */
    public View b = this.a.findViewById(R.id.share_content_view);
    private SimpleList c = ((SimpleList) this.a.findViewById(R.id.share_list));
    private View d;
    private ShareListAdapter e;
    private a f;

    class ShareListAdapter extends AbstractViewHolderBaseAdapter<dcr, b> {
        Context mContext;

        public ShareListAdapter(Context context) {
            this.mContext = context;
        }

        public View onCreateView(ViewGroup viewGroup, int i) {
            return LayoutInflater.from(this.mContext).inflate(R.layout.view_share_item, viewGroup, false);
        }

        public b onCreateViewHolder(View view, ViewGroup viewGroup, int i) {
            return new b(view);
        }

        public void onBindViewHolderWithData(b bVar, dcr dcr, int i, int i2) {
            if (dcr != null) {
                bVar.a = dcr;
                if (dcr != null && dcr.f != null) {
                    bVar.d.setImageResource(dcr.f.a);
                    bVar.c.setText(dcr.f.b);
                }
            }
        }
    }

    public interface a {
        void a();

        void a(dcr dcr);
    }

    class b extends com.autonavi.map.template.AbstractViewHolderAdapter.a {
        dcr a;
        View b;
        TextView c;
        ImageView d;
        OnClickListener e = new OnClickListener() {
            public final void onClick(View view) {
                if (b.this.a != null) {
                    ShareViewLayer.a(ShareViewLayer.this, b.this.a);
                }
            }
        };

        public b(View view) {
            super(view);
            this.b = view;
            this.d = (ImageView) view.findViewById(com.autonavi.widget.R.id.icon);
            this.c = (TextView) view.findViewById(com.autonavi.widget.R.id.text);
            this.b.setOnClickListener(this.e);
        }
    }

    public final void onConfigurationChanged(Configuration configuration) {
    }

    public final void showBackground(boolean z) {
    }

    public ShareViewLayer(Context context, ArrayList<dcr> arrayList, a aVar) {
        this.e = new ShareListAdapter(context);
        this.f = aVar;
        this.a = LayoutInflater.from(context).inflate(R.layout.share_view_layer_layout, null);
        this.c.setAdapter(this.e);
        this.d = this.a.findViewById(R.id.cancel);
        this.a.setOnTouchListener(new OnTouchListener() {
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 0) {
                    motionEvent.getX();
                    motionEvent.getY();
                    Rect rect = new Rect();
                    ShareViewLayer.this.b.getGlobalVisibleRect(rect);
                    if (!rect.contains((int) motionEvent.getRawX(), (int) motionEvent.getRawY())) {
                        ShareViewLayer.this.a();
                    }
                }
                return true;
            }
        });
        this.d.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                ShareViewLayer.this.a();
            }
        });
        this.e.setDataAndChangeDataSet(arrayList);
    }

    /* access modifiers changed from: private */
    public void a() {
        if (this.f != null) {
            this.f.a();
        }
    }

    public final View getView() {
        return this.a;
    }

    public final boolean onBackPressed() {
        a();
        return true;
    }

    static /* synthetic */ void a(ShareViewLayer shareViewLayer, dcr dcr) {
        if (shareViewLayer.f != null) {
            shareViewLayer.f.a(dcr);
        }
    }
}

package com.autonavi.minimap.drive.sticker.page;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.amap.bundle.drivecommon.mvp.view.DriveBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.drive.sticker.StickerTimeTableView;

public class StickerDetailPage extends DriveBasePage<djc> {
    public StickerTimeTableView a;
    public ImageView b;
    public TextView c;
    public TextView d;
    public TextView e;
    public TextView f;
    public TextView g;
    public TextView h;
    public TextView i;
    public TextView j;
    public TextView k;
    public FrameLayout l;
    public TextView m;
    public LinearLayout n;

    public class a extends ClickableSpan {
        private String b;

        public a(String str) {
            this.b = str;
        }

        public final void onClick(View view) {
            try {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setData(Uri.parse(this.b));
                StickerDetailPage.this.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        requestScreenOrientation(1);
        setContentView(R.layout.route_stickers_detail_layout);
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new djc(this);
    }
}

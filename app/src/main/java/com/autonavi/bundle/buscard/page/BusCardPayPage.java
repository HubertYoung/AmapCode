package com.autonavi.bundle.buscard.page;

import android.app.Activity;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import com.autonavi.annotation.PageAction;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.ajx3.Ajx3Page;

@PageAction("amap.extra.route.buscard_hor")
public class BusCardPayPage extends Ajx3Page {
    private boolean a = false;

    public void onCreate(Context context) {
        super.onCreate(context);
        PageBundle arguments = getArguments();
        if (arguments != null) {
            this.a = arguments.getBoolean("hor");
        }
    }

    public void resume() {
        super.resume();
        if (this.a) {
            Window window = ((Activity) getContext()).getWindow();
            LayoutParams attributes = window.getAttributes();
            attributes.flags |= 1024;
            window.setAttributes(attributes);
            window.addFlags(512);
            return;
        }
        a();
    }

    public void pause() {
        super.pause();
        if (this.a) {
            a();
        }
    }

    private void a() {
        Window window = ((Activity) getContext()).getWindow();
        LayoutParams attributes = window.getAttributes();
        attributes.flags &= -1025;
        window.setAttributes(attributes);
        window.clearFlags(512);
    }
}

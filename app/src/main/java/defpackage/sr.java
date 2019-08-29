package defpackage;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import com.autonavi.map.fragmentcontainer.IViewLayer;

/* renamed from: sr reason: default package */
/* compiled from: DriveViewLayer */
public abstract class sr implements IViewLayer {
    protected View a;
    public ss b;

    public boolean onBackPressed() {
        return false;
    }

    public void onConfigurationChanged(Configuration configuration) {
    }

    public void showBackground(boolean z) {
    }

    public sr(Context context, int i) {
        this.a = LayoutInflater.from(context).inflate(i, null);
    }

    public View getView() {
        return this.a;
    }
}

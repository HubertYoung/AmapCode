package com.j256.ormlite.android.apptools;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.support.ConnectionSource;

public abstract class OrmLiteBaseActivity<H extends OrmLiteSqliteOpenHelper> extends Activity {
    private static Logger logger = LoggerFactory.getLogger(OrmLiteBaseActivity.class);
    private volatile boolean created = false;
    private volatile boolean destroyed = false;
    private volatile H helper;

    public H getHelper() {
        if (this.helper != null) {
            return this.helper;
        }
        if (!this.created) {
            throw new IllegalStateException("A call has not been made to onCreate() yet so the helper is null");
        } else if (this.destroyed) {
            throw new IllegalStateException("A call to onDestroy has already been made and the helper cannot be used after that point");
        } else {
            throw new IllegalStateException("Helper is null for some unknown reason");
        }
    }

    public ConnectionSource getConnectionSource() {
        return getHelper().getConnectionSource();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        if (this.helper == null) {
            this.helper = getHelperInternal(this);
            this.created = true;
        }
        super.onCreate(savedInstanceState);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        releaseHelper(this.helper);
        this.destroyed = true;
    }

    /* access modifiers changed from: protected */
    public H getHelperInternal(Context context) {
        OrmLiteSqliteOpenHelper newHelper = OpenHelperManager.getHelper(context);
        logger.trace((String) "{}: got new helper {} from OpenHelperManager", (Object) this, (Object) newHelper);
        return newHelper;
    }

    /* access modifiers changed from: protected */
    public void releaseHelper(H helper2) {
        OpenHelperManager.releaseHelper();
        logger.trace((String) "{}: helper {} was released, set to null", (Object) this, (Object) helper2);
        this.helper = null;
    }

    public String toString() {
        return getClass().getSimpleName() + AUScreenAdaptTool.PREFIX_ID + Integer.toHexString(super.hashCode());
    }
}

package com.alipay.android.phone.mobilesdk.permission.guide.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.alipay.android.phone.mobilesdk.permission.guide.PermissionStatus;
import com.alipay.android.phone.mobilesdk.permission.guide.PermissionType;
import com.alipay.android.phone.mobilesdk.permission.guide.a.c;
import com.alipay.android.phone.mobilesdk.permission.guide.a.d;
import com.alipay.android.phone.mobilesdk.permission.sdk.PGDelegatorResult;
import com.alipay.android.phone.mobilesdk.permission.sdk.PermissionGuideSdk;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.squareup.wire.Wire;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class DataProvider extends ContentProvider {
    private static final String[] a = {"SPSB", "SPDB", "DPDB"};
    private UriMatcher b;

    public boolean onCreate() {
        if (getContext() == null) {
            return false;
        }
        String authority = getContext().getPackageName() + ".PermissionProvider";
        this.b = new UriMatcher(-1);
        this.b.addURI(authority, "Fatigue", 99);
        this.b.addURI(authority, "PermissionRecord", 100);
        return true;
    }

    @Nullable
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        try {
            LoggerFactory.getTraceLogger().info("PermissionDataProvider", "query, uri: " + uri);
            switch (this.b.match(uri)) {
                case 99:
                    return a();
                case 100:
                    return a(selectionArgs);
                default:
                    return null;
            }
        } catch (Throwable tr) {
            LoggerFactory.getTraceLogger().error("PermissionDataProvider", "query error", tr);
            return null;
        }
    }

    private static Cursor a(String... args) {
        if (args == null) {
            LoggerFactory.getTraceLogger().error((String) "PermissionDataProvider", (String) "query error, args is null");
            return null;
        } else if (args.length != 2) {
            LoggerFactory.getTraceLogger().error((String) "PermissionDataProvider", "query error, args' length is invalid, args=" + Arrays.toString(args));
            return null;
        } else {
            String permissionName = args[0];
            long spsb = c.a().a(args[1], permissionName);
            long spdb = c.a().b(permissionName);
            long dpdb = c.a().a(permissionName);
            LoggerFactory.getTraceLogger().info("PermissionDataProvider", String.format(Locale.US, "query last permission record guide time, spsb: %s, spdb: %s, dpdb: %s", new Object[]{Long.valueOf(spsb), Long.valueOf(spdb), Long.valueOf(dpdb)}));
            MatrixCursor cursor = new MatrixCursor(a, 1);
            cursor.addRow(new Object[]{Long.valueOf(spsb), Long.valueOf(spdb), Long.valueOf(dpdb)});
            return cursor;
        }
    }

    private static Cursor a() {
        long spsb = c.a().b();
        long spdb = c.a().c();
        long dpdb = c.a().d();
        LoggerFactory.getTraceLogger().info("PermissionDataProvider", String.format(Locale.US, "query fatigue, spsb: %s, spdb: %s, dpdb: %s", new Object[]{Long.valueOf(spsb), Long.valueOf(spdb), Long.valueOf(dpdb)}));
        MatrixCursor cursor = new MatrixCursor(a, 1);
        cursor.addRow(new Object[]{Long.valueOf(spsb), Long.valueOf(spdb), Long.valueOf(dpdb)});
        return cursor;
    }

    @Nullable
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        try {
            LoggerFactory.getTraceLogger().info("PermissionDataProvider", String.format(Locale.US, "insert, uri: %s, values: %s", new Object[]{uri, values}));
            if (values.size() == 0) {
                LoggerFactory.getTraceLogger().warn((String) "PermissionDataProvider", (String) "insert but data is empty!");
                return null;
            }
            switch (this.b.match(uri)) {
                case 100:
                    return a(values);
                default:
                    return null;
            }
        } catch (Throwable tr) {
            LoggerFactory.getTraceLogger().error("PermissionDataProvider", "insert error", tr);
            return null;
        }
    }

    private static Uri a(ContentValues values) {
        List records = new ArrayList(values.size());
        Wire wire = new Wire((Class<?>[]) new Class[0]);
        for (String key : values.keySet()) {
            records.add(wire.parseFrom(values.getAsByteArray(key), d.class));
        }
        c.a().a((Context) LauncherApplicationAgent.getInstance().getApplicationContext(), (d[]) records.toArray(new d[records.size()]));
        LoggerFactory.getTraceLogger().info("PermissionDataProvider", "insert permission record successful! values: " + records.toString());
        return null;
    }

    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    public static Uri a(Context ctx) {
        return Uri.parse("content://" + ctx.getPackageName() + ".PermissionProvider");
    }

    public static Uri b(Context ctx) {
        return Uri.parse("content://" + ctx.getPackageName() + ".PermissionProvider/Fatigue");
    }

    public static Uri c(Context ctx) {
        return Uri.parse("content://" + ctx.getPackageName() + ".PermissionProvider/PermissionRecord");
    }

    @Nullable
    public Bundle call(@NonNull String method, String arg, Bundle extras) {
        if (TextUtils.isEmpty(method) || extras == null) {
            return null;
        }
        if ("checkPermissionStatus".equals(method)) {
            return a(extras);
        }
        if ("overridePermissionGuideBehavior".equals(method)) {
            return b(extras);
        }
        LoggerFactory.getTraceLogger().error((String) "PermissionDataProvider", "Unsupported method, name: " + method);
        return null;
    }

    private static Bundle a(Bundle bundle) {
        try {
            PermissionType type = (PermissionType) bundle.getSerializable("permissionType");
            if (type == null) {
                LoggerFactory.getTraceLogger().error((String) "PermissionDataProvider", (String) "Call checkPermissionStatus but permissionType is null");
                return null;
            }
            PermissionStatus status = com.alipay.android.phone.mobilesdk.permission.utils.c.a(PermissionGuideSdk.getDelegators(), type);
            if (status == null) {
                LoggerFactory.getTraceLogger().error((String) "PermissionDataProvider", (String) "Call checkPermissionStatus but result is null");
                return null;
            }
            Bundle result = new Bundle();
            result.putSerializable("permissionStatus", status);
            return result;
        } catch (Throwable tr) {
            LoggerFactory.getTraceLogger().error((String) "PermissionDataProvider", tr);
            return null;
        }
    }

    private static Bundle b(Bundle bundle) {
        try {
            boolean force = bundle.getBoolean("force");
            PGDelegatorResult result = com.alipay.android.phone.mobilesdk.permission.utils.c.a(PermissionGuideSdk.getDelegators(), bundle.getString("bizType"), (PermissionType[]) bundle.getSerializable("permissionTypes"), force);
            if (result == null) {
                return null;
            }
            Bundle data = new Bundle();
            data.putSerializable("pgDelegatorResult", result);
            return data;
        } catch (Throwable tr) {
            LoggerFactory.getTraceLogger().error((String) "PermissionDataProvider", tr);
            return null;
        }
    }
}

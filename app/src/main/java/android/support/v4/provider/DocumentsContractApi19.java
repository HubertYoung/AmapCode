package android.support.v4.provider;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.text.TextUtils;
import com.taobao.accs.common.Constants;

class DocumentsContractApi19 {
    DocumentsContractApi19() {
    }

    public static boolean a(Context context, Uri uri) {
        return DocumentsContract.isDocumentUri(context, uri);
    }

    public static String b(Context context, Uri uri) {
        return a(context, uri, "_display_name");
    }

    public static long f(Context context, Uri uri) {
        return b(context, uri, "last_modified");
    }

    public static long g(Context context, Uri uri) {
        return b(context, uri, "_size");
    }

    public static boolean h(Context context, Uri uri) {
        if (context.checkCallingOrSelfUriPermission(uri, 1) == 0 && !TextUtils.isEmpty(a(context, uri, "mime_type"))) {
            return true;
        }
        return false;
    }

    public static boolean i(Context context, Uri uri) {
        if (context.checkCallingOrSelfUriPermission(uri, 2) != 0) {
            return false;
        }
        String a = a(context, uri, "mime_type");
        int b = (int) b(context, uri, Constants.KEY_FLAGS);
        if (TextUtils.isEmpty(a)) {
            return false;
        }
        if ((b & 4) != 0) {
            return true;
        }
        if ("vnd.android.document/directory".equals(a) && (b & 8) != 0) {
            return true;
        }
        if (TextUtils.isEmpty(a) || (b & 2) == 0) {
            return false;
        }
        return true;
    }

    public static boolean j(Context context, Uri uri) {
        return DocumentsContract.deleteDocument(context.getContentResolver(), uri);
    }

    public static boolean k(Context context, Uri uri) {
        ContentResolver contentResolver = context.getContentResolver();
        boolean z = false;
        Cursor cursor = null;
        try {
            Cursor query = contentResolver.query(uri, new String[]{"document_id"}, null, null, null);
            try {
                if (query.getCount() > 0) {
                    z = true;
                }
                a(query);
                return z;
            } catch (Exception e) {
                e = e;
                cursor = query;
                try {
                    new StringBuilder("Failed query: ").append(e);
                    a(cursor);
                    return false;
                } catch (Throwable th) {
                    th = th;
                    a(cursor);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                cursor = query;
                a(cursor);
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            new StringBuilder("Failed query: ").append(e);
            a(cursor);
            return false;
        }
    }

    private static String a(Context context, Uri uri, String str) {
        Cursor cursor;
        try {
            cursor = context.getContentResolver().query(uri, new String[]{str}, null, null, null);
            try {
                if (!cursor.moveToFirst() || cursor.isNull(0)) {
                    a(cursor);
                    return null;
                }
                String string = cursor.getString(0);
                a(cursor);
                return string;
            } catch (Exception e) {
                e = e;
                try {
                    new StringBuilder("Failed query: ").append(e);
                    a(cursor);
                    return null;
                } catch (Throwable th) {
                    th = th;
                    a(cursor);
                    throw th;
                }
            }
        } catch (Exception e2) {
            e = e2;
            cursor = null;
            new StringBuilder("Failed query: ").append(e);
            a(cursor);
            return null;
        } catch (Throwable th2) {
            th = th2;
            cursor = null;
            a(cursor);
            throw th;
        }
    }

    private static long b(Context context, Uri uri, String str) {
        Cursor cursor = null;
        try {
            Cursor query = context.getContentResolver().query(uri, new String[]{str}, null, null, null);
            try {
                if (!query.moveToFirst() || query.isNull(0)) {
                    a(query);
                    return 0;
                }
                long j = query.getLong(0);
                a(query);
                return j;
            } catch (Exception e) {
                e = e;
                cursor = query;
                try {
                    new StringBuilder("Failed query: ").append(e);
                    a(cursor);
                    return 0;
                } catch (Throwable th) {
                    th = th;
                    a(cursor);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                cursor = query;
                a(cursor);
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            new StringBuilder("Failed query: ").append(e);
            a(cursor);
            return 0;
        }
    }

    private static void a(AutoCloseable autoCloseable) {
        if (autoCloseable != null) {
            try {
                autoCloseable.close();
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception unused) {
            }
        }
    }

    public static String c(Context context, Uri uri) {
        String a = a(context, uri, "mime_type");
        if ("vnd.android.document/directory".equals(a)) {
            return null;
        }
        return a;
    }

    public static boolean d(Context context, Uri uri) {
        return "vnd.android.document/directory".equals(a(context, uri, "mime_type"));
    }

    public static boolean e(Context context, Uri uri) {
        String a = a(context, uri, "mime_type");
        return !"vnd.android.document/directory".equals(a) && !TextUtils.isEmpty(a);
    }
}

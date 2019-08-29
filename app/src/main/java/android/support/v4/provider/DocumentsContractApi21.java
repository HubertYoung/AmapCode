package android.support.v4.provider;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import java.util.ArrayList;

class DocumentsContractApi21 {
    DocumentsContractApi21() {
    }

    public static Uri a(Context context, Uri uri, String str, String str2) {
        return DocumentsContract.createDocument(context.getContentResolver(), uri, str, str2);
    }

    public static Uri a(Context context, Uri uri, String str) {
        return a(context, uri, "vnd.android.document/directory", str);
    }

    public static Uri a(Uri uri) {
        return DocumentsContract.buildDocumentUriUsingTree(uri, DocumentsContract.getTreeDocumentId(uri));
    }

    public static Uri[] a(Context context, Uri uri) {
        ContentResolver contentResolver = context.getContentResolver();
        Uri buildChildDocumentsUriUsingTree = DocumentsContract.buildChildDocumentsUriUsingTree(uri, DocumentsContract.getDocumentId(uri));
        ArrayList arrayList = new ArrayList();
        Cursor cursor = null;
        try {
            Cursor query = contentResolver.query(buildChildDocumentsUriUsingTree, new String[]{"document_id"}, null, null, null);
            while (query.moveToNext()) {
                try {
                    arrayList.add(DocumentsContract.buildDocumentUriUsingTree(uri, query.getString(0)));
                } catch (Exception e) {
                    e = e;
                    cursor = query;
                    try {
                        new StringBuilder("Failed query: ").append(e);
                        a((AutoCloseable) cursor);
                        return (Uri[]) arrayList.toArray(new Uri[arrayList.size()]);
                    } catch (Throwable th) {
                        th = th;
                        a((AutoCloseable) cursor);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    cursor = query;
                    a((AutoCloseable) cursor);
                    throw th;
                }
            }
            a((AutoCloseable) query);
        } catch (Exception e2) {
            e = e2;
            new StringBuilder("Failed query: ").append(e);
            a((AutoCloseable) cursor);
            return (Uri[]) arrayList.toArray(new Uri[arrayList.size()]);
        }
        return (Uri[]) arrayList.toArray(new Uri[arrayList.size()]);
    }

    public static Uri b(Context context, Uri uri, String str) {
        return DocumentsContract.renameDocument(context.getContentResolver(), uri, str);
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
}

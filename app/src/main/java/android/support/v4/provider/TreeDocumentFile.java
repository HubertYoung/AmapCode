package android.support.v4.provider;

import android.content.Context;
import android.net.Uri;

class TreeDocumentFile extends DocumentFile {
    private Context a;
    private Uri b;

    TreeDocumentFile(DocumentFile documentFile, Context context, Uri uri) {
        super(documentFile);
        this.a = context;
        this.b = uri;
    }

    public DocumentFile createFile(String str, String str2) {
        Uri a2 = DocumentsContractApi21.a(this.a, this.b, str, str2);
        if (a2 != null) {
            return new TreeDocumentFile(this, this.a, a2);
        }
        return null;
    }

    public DocumentFile createDirectory(String str) {
        Uri a2 = DocumentsContractApi21.a(this.a, this.b, str);
        if (a2 != null) {
            return new TreeDocumentFile(this, this.a, a2);
        }
        return null;
    }

    public Uri getUri() {
        return this.b;
    }

    public String getName() {
        return DocumentsContractApi19.b(this.a, this.b);
    }

    public String getType() {
        return DocumentsContractApi19.c(this.a, this.b);
    }

    public boolean isDirectory() {
        return DocumentsContractApi19.d(this.a, this.b);
    }

    public boolean isFile() {
        return DocumentsContractApi19.e(this.a, this.b);
    }

    public long lastModified() {
        return DocumentsContractApi19.f(this.a, this.b);
    }

    public long length() {
        return DocumentsContractApi19.g(this.a, this.b);
    }

    public boolean canRead() {
        return DocumentsContractApi19.h(this.a, this.b);
    }

    public boolean canWrite() {
        return DocumentsContractApi19.i(this.a, this.b);
    }

    public boolean delete() {
        return DocumentsContractApi19.j(this.a, this.b);
    }

    public boolean exists() {
        return DocumentsContractApi19.k(this.a, this.b);
    }

    public DocumentFile[] listFiles() {
        Uri[] a2 = DocumentsContractApi21.a(this.a, this.b);
        DocumentFile[] documentFileArr = new DocumentFile[a2.length];
        for (int i = 0; i < a2.length; i++) {
            documentFileArr[i] = new TreeDocumentFile(this, this.a, a2[i]);
        }
        return documentFileArr;
    }

    public boolean renameTo(String str) {
        Uri b2 = DocumentsContractApi21.b(this.a, this.b, str);
        if (b2 == null) {
            return false;
        }
        this.b = b2;
        return true;
    }
}

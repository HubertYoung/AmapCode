package android.support.v4.provider;

import android.content.Context;
import android.net.Uri;

class SingleDocumentFile extends DocumentFile {
    private Context a;
    private Uri b;

    SingleDocumentFile(Context context, Uri uri) {
        super(null);
        this.a = context;
        this.b = uri;
    }

    public DocumentFile createFile(String str, String str2) {
        throw new UnsupportedOperationException();
    }

    public DocumentFile createDirectory(String str) {
        throw new UnsupportedOperationException();
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
        throw new UnsupportedOperationException();
    }

    public boolean renameTo(String str) {
        throw new UnsupportedOperationException();
    }
}

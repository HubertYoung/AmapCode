package anet.channel.request;

import android.os.Parcelable;
import java.io.IOException;
import java.io.OutputStream;

public interface BodyEntry extends Parcelable {
    int a(OutputStream outputStream) throws IOException;

    String a();
}

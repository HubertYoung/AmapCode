package defpackage;

import android.text.TextUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* renamed from: etk reason: default package */
/* compiled from: GetRequest */
final class etk {
    private static final Pattern d = Pattern.compile("[R,r]ange:[ ]?bytes=(\\d*)-");
    private static final Pattern e = Pattern.compile("GET /(.*) HTTP");
    public final String a;
    public final long b;
    public final boolean c;

    private etk(String str) {
        long j;
        etr.a(str);
        Matcher matcher = d.matcher(str);
        if (matcher.find()) {
            j = Long.parseLong(matcher.group(1));
        } else {
            j = -1;
        }
        this.b = Math.max(0, j);
        this.c = j >= 0;
        Matcher matcher2 = e.matcher(str);
        if (matcher2.find()) {
            this.a = matcher2.group(1);
            return;
        }
        StringBuilder sb = new StringBuilder("Invalid request `");
        sb.append(str);
        sb.append("`: url not found!");
        throw new IllegalArgumentException(sb.toString());
    }

    public static etk a(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        while (true) {
            String readLine = bufferedReader.readLine();
            if (TextUtils.isEmpty(readLine)) {
                return new etk(sb.toString());
            }
            sb.append(readLine);
            sb.append(10);
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("GetRequest{rangeOffset=");
        sb.append(this.b);
        sb.append(", partial=");
        sb.append(this.c);
        sb.append(", uri='");
        sb.append(this.a);
        sb.append('\'');
        sb.append('}');
        return sb.toString();
    }
}

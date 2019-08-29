package com.alipay.mobile.android.verify.logger;

import com.j256.ormlite.stmt.query.SimpleComparison;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: LoggerPrinter */
class a implements Printer {
    private final ThreadLocal<String> a = new ThreadLocal<>();
    private final List<LogAdapter> b = new ArrayList();

    a() {
    }

    public Printer t(String str) {
        if (str != null) {
            this.a.set(str);
        }
        return this;
    }

    public void d(String str, Object... objArr) {
        a(3, null, str, objArr);
    }

    public void d(Object obj) {
        a(3, null, c.a(obj), new Object[0]);
    }

    public void e(String str, Object... objArr) {
        e(null, str, objArr);
    }

    public void e(Throwable th, String str, Object... objArr) {
        a(6, th, str, objArr);
    }

    public void w(String str, Object... objArr) {
        a(5, null, str, objArr);
    }

    public void i(String str, Object... objArr) {
        a(4, null, str, objArr);
    }

    public void v(String str, Object... objArr) {
        a(2, null, str, objArr);
    }

    public void wtf(String str, Object... objArr) {
        a(7, null, str, objArr);
    }

    public void json(String str) {
        if (c.a((CharSequence) str)) {
            d("Empty/Null json content");
            return;
        }
        try {
            String trim = str.trim();
            if (trim.startsWith("{")) {
                d(new JSONObject(trim).toString(2));
            } else if (trim.startsWith("[")) {
                d(new JSONArray(trim).toString(2));
            } else {
                e("Invalid Json", new Object[0]);
            }
        } catch (JSONException unused) {
            e("Invalid Json", new Object[0]);
        }
    }

    public void xml(String str) {
        if (c.a((CharSequence) str)) {
            d("Empty/Null xml content");
            return;
        }
        try {
            StreamSource streamSource = new StreamSource(new StringReader(str));
            StreamResult streamResult = new StreamResult(new StringWriter());
            Transformer newTransformer = TransformerFactory.newInstance().newTransformer();
            newTransformer.setOutputProperty("indent", "yes");
            newTransformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            newTransformer.transform(streamSource, streamResult);
            d(streamResult.getWriter().toString().replaceFirst(SimpleComparison.GREATER_THAN_OPERATION, ">\n"));
        } catch (TransformerException unused) {
            e("Invalid xml", new Object[0]);
        }
    }

    public synchronized void log(int i, String str, String str2, Throwable th) {
        if (!(th == null || str2 == null)) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(str2);
                sb.append(" : ");
                sb.append(c.a(th));
                str2 = sb.toString();
            } catch (Throwable th2) {
                throw th2;
            }
        }
        if (th != null && str2 == null) {
            str2 = c.a(th);
        }
        if (c.a((CharSequence) str2)) {
            str2 = "Empty/NULL log message";
        }
        for (LogAdapter next : this.b) {
            if (next.isLoggable(i, str)) {
                next.log(i, str, str2);
            }
        }
    }

    public void clearLogAdapters() {
        this.b.clear();
    }

    public void addAdapter(LogAdapter logAdapter) {
        this.b.add(logAdapter);
    }

    private synchronized void a(int i, Throwable th, String str, Object... objArr) {
        log(i, a(), a(str, objArr), th);
    }

    private String a() {
        String str = this.a.get();
        if (str == null) {
            return null;
        }
        this.a.remove();
        return str;
    }

    private String a(String str, Object... objArr) {
        return (objArr == null || objArr.length == 0) ? str : String.format(str, objArr);
    }
}

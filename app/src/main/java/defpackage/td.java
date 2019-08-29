package defpackage;

import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.sdk.log.util.LogConstant;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: td reason: default package */
/* compiled from: RouteActionLogController */
public class td {
    private static volatile td d;
    public int a = 0;
    public long b = -1;
    private JSONObject c = new JSONObject();

    private td() {
    }

    public static td a() {
        if (d == null) {
            synchronized (td.class) {
                try {
                    if (d == null) {
                        d = new td();
                    }
                }
            }
        }
        return d;
    }

    public final void a(int i) {
        switch (i) {
            case 0:
                b(0);
                this.b = -1;
                break;
            case 1:
                b((String) "**************");
                StringBuilder sb = new StringBuilder("     用户点击     previous state:");
                sb.append(this.a);
                sb.append("  time:");
                sb.append(System.currentTimeMillis());
                b(sb.toString());
                b(1);
                this.b = System.currentTimeMillis();
                break;
            case 2:
                StringBuilder sb2 = new StringBuilder("客户端发起请求    previous state:");
                sb2.append(this.a);
                sb2.append("  time:");
                sb2.append(System.currentTimeMillis());
                b(sb2.toString());
                if (this.a == 1) {
                    b(2);
                    break;
                }
                break;
            case 3:
                StringBuilder sb3 = new StringBuilder("客户端收到结果    previous state:");
                sb3.append(this.a);
                sb3.append("  time:");
                sb3.append(System.currentTimeMillis());
                b(sb3.toString());
                if (this.a == 2) {
                    b(3);
                    break;
                }
                break;
            case 4:
                StringBuilder sb4 = new StringBuilder("TBT解析完成       previous state:");
                sb4.append(this.a);
                sb4.append("  time:");
                sb4.append(System.currentTimeMillis());
                b(sb4.toString());
                if (this.a == 3) {
                    b(4);
                    break;
                }
                break;
            case 5:
                StringBuilder sb5 = new StringBuilder("渲染开始绘制路线  previous state:");
                sb5.append(this.a);
                sb5.append("  time:");
                sb5.append(System.currentTimeMillis());
                b(sb5.toString());
                if (this.a == 4) {
                    b(5);
                    break;
                }
                break;
            case 6:
                StringBuilder sb6 = new StringBuilder("路线绘制结束     previous state:");
                sb6.append(this.a);
                sb6.append("  time:");
                sb6.append(System.currentTimeMillis());
                b(sb6.toString());
                if (this.a == 5) {
                    b(6);
                    break;
                }
                break;
        }
        if (this.a == 6) {
            if (this.c != null) {
                StringBuilder sb7 = new StringBuilder("上传数据:");
                sb7.append(this.c);
                b(sb7.toString());
                LogManager.actionLogV2(LogConstant.PAGE_ID_CAR_RESULT_MAP, "B118", this.c);
                b((String) "埋点数据准备完成，上传数据成功 ");
            }
            this.a = 0;
        }
    }

    public final void a(String str) {
        if (this.a == 2 && this.c != null) {
            try {
                this.c.put("csid", str);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void b(int i) {
        this.a = i;
        try {
            switch (this.a) {
                case 1:
                    this.c = new JSONObject();
                    this.c.put("click", System.currentTimeMillis());
                    return;
                case 2:
                    if (this.c != null) {
                        this.c.put("initial_request", System.currentTimeMillis());
                        return;
                    }
                    break;
                case 3:
                    if (this.c != null) {
                        this.c.put("result_return", System.currentTimeMillis());
                        return;
                    }
                    break;
                case 4:
                    if (this.c != null) {
                        this.c.put("parse_complete", System.currentTimeMillis());
                        return;
                    }
                    break;
                case 5:
                    if (this.c != null) {
                        this.c.put("start_rendering", System.currentTimeMillis());
                        return;
                    }
                    break;
                case 6:
                    if (this.c != null) {
                        this.c.put("finish_rendering", System.currentTimeMillis());
                        break;
                    }
                    break;
            }
        } catch (Exception unused) {
        }
    }

    public static void b(String str) {
        if (bno.a) {
            AMapLog.i("RouteActionLogController", str);
            ku.a().c("RouteActionLogController", str);
        }
    }
}

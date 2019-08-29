package defpackage;

import android.app.Activity;
import android.text.TextUtils;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.PageContainer;
import com.autonavi.map.fragmentcontainer.page.PageContainer.PageRecord;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import java.util.ArrayList;

/* renamed from: ckg reason: default package */
/* compiled from: NavHistoryHelper */
public final class ckg {
    private static String a;
    private static boa<String> b = new boa<>(0);
    private static long c;

    public static void a(bid bid) {
        if (bid != null) {
            String simpleName = bid.getClass().getSimpleName();
            Activity activity = bid.getActivity();
            String simpleName2 = activity != null ? activity.getClass().getSimpleName() : "";
            StringBuilder sb = new StringBuilder();
            sb.append(simpleName2);
            sb.append(!TextUtils.isEmpty(simpleName2) ? MetaRecord.LOG_SEPARATOR : "");
            sb.append(simpleName);
            String sb2 = sb.toString();
            if (sb2 != null && !sb2.equals(a)) {
                boa<String> boa = b;
                if (boa.b.size() == boa.a) {
                    boa.b.poll();
                }
                boa.b.add(sb2);
                a = sb2;
                c++;
            }
        }
    }

    public static String a() {
        StringBuilder sb = new StringBuilder();
        sb.append(c());
        sb.append(b());
        return sb.toString();
    }

    private static String b() {
        StringBuilder sb = new StringBuilder();
        sb.append("\ncurr stack \n");
        ArrayList<akc> pagesStacks = AMapPageUtil.getPagesStacks();
        if (pagesStacks == null || pagesStacks.size() <= 0) {
            sb.append("null \n");
            return sb.toString();
        }
        try {
            for (int size = pagesStacks.size() - 1; size >= 0; size--) {
                akc akc = pagesStacks.get(size);
                AbstractBasePage abstractBasePage = null;
                if (akc != null && (akc instanceof bun)) {
                    abstractBasePage = ((bun) akc).a;
                    if (abstractBasePage instanceof AbstractBasePage) {
                        PageContainer pageContainer = abstractBasePage.getPageContainer();
                        if (!(pageContainer == null || pageContainer.getPageRecords() == null || pageContainer.getPageRecords().size() <= 0)) {
                            PageRecord[] pageRecordArr = (PageRecord[]) pageContainer.getPageRecords().toArray(new PageRecord[pageContainer.getPageRecords().size()]);
                            for (int length = pageRecordArr.length - 1; length >= 0; length--) {
                                String name = pageRecordArr[length].getPageClazz().getName();
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append(name);
                                sb2.append("\n");
                                sb.append(sb2.toString());
                            }
                        }
                    }
                }
                StringBuilder sb3 = new StringBuilder();
                sb3.append(abstractBasePage);
                sb3.append("\n");
                sb.append(sb3.toString());
            }
        } catch (Throwable th) {
            th.printStackTrace();
            sb.append("error \n");
        }
        return sb.toString();
    }

    private static String c() {
        StringBuilder sb = new StringBuilder();
        Object[] array = b.b.toArray();
        StringBuilder sb2 = new StringBuilder("\nall navigation page count: ");
        sb2.append(c);
        sb2.append("\n");
        sb.append(sb2.toString());
        sb.append("\nlatest navigation page quene: \n");
        if (array != null) {
            for (int length = array.length - 1; length >= 0; length--) {
                Object obj = array[length];
                if (obj != null) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(obj.toString());
                    sb3.append("\n");
                    sb.append(sb3.toString());
                }
            }
        }
        return sb.toString();
    }
}

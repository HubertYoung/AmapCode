package defpackage;

import com.google.zxing.BarcodeFormat;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/* renamed from: dtu reason: default package */
/* compiled from: DecodeFormatManager */
final class dtu {
    static final Set<BarcodeFormat> a = EnumSet.of(BarcodeFormat.UPC_A, new BarcodeFormat[]{BarcodeFormat.UPC_E, BarcodeFormat.EAN_13, BarcodeFormat.EAN_8, BarcodeFormat.RSS_14, BarcodeFormat.RSS_EXPANDED});
    static final Set<BarcodeFormat> b = EnumSet.of(BarcodeFormat.CODE_39, BarcodeFormat.CODE_93, BarcodeFormat.CODE_128, BarcodeFormat.ITF, BarcodeFormat.CODABAR);
    static final Set<BarcodeFormat> c = EnumSet.of(BarcodeFormat.QR_CODE);
    static final Set<BarcodeFormat> d = EnumSet.of(BarcodeFormat.DATA_MATRIX);
    static final Set<BarcodeFormat> e = EnumSet.of(BarcodeFormat.AZTEC);
    static final Set<BarcodeFormat> f = EnumSet.of(BarcodeFormat.PDF_417);
    private static final Pattern g = Pattern.compile(",");
    private static final Set<BarcodeFormat> h;
    private static final Map<String, Set<BarcodeFormat>> i;

    static {
        EnumSet<BarcodeFormat> copyOf = EnumSet.copyOf(a);
        h = copyOf;
        copyOf.addAll(b);
        HashMap hashMap = new HashMap();
        i = hashMap;
        hashMap.put("ONE_D_MODE", h);
        i.put("PRODUCT_MODE", a);
        i.put("QR_CODE_MODE", c);
        i.put("DATA_MATRIX_MODE", d);
        i.put("AZTEC_MODE", e);
        i.put("PDF417_MODE", f);
    }
}

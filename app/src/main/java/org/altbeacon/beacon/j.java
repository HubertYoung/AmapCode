package org.altbeacon.beacon;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothDevice;
import android.support.v4.view.InputDeviceCompat;
import android.util.Log;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.altbeacon.beacon.b.d;
import org.altbeacon.bluetooth.a;
import org.altbeacon.bluetooth.c;

/* compiled from: BeaconParser */
public class j implements Serializable {
    private static final Pattern A = Pattern.compile("p\\:(\\d+)\\-(\\d+)\\:?([\\-\\d]+)?");
    private static final Pattern B = Pattern.compile(DictionaryKeys.CTRLXY_X);
    private static final char[] C = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final Pattern w = Pattern.compile("i\\:(\\d+)\\-(\\d+)([blv]*)?");
    private static final Pattern x = Pattern.compile("m\\:(\\d+)-(\\d+)\\=([0-9A-Fa-f]+)");
    private static final Pattern y = Pattern.compile("s\\:(\\d+)-(\\d+)\\=([0-9A-Fa-f]+)");
    private static final Pattern z = Pattern.compile("d\\:(\\d+)\\-(\\d+)([bl]*)?");
    private Long D;
    protected String a;
    protected final List<Integer> b = new ArrayList();
    protected final List<Integer> c = new ArrayList();
    protected final List<Boolean> d = new ArrayList();
    protected final List<Integer> e = new ArrayList();
    protected final List<Integer> f = new ArrayList();
    protected final List<Boolean> g = new ArrayList();
    protected final List<Boolean> h = new ArrayList();
    protected Integer i;
    protected Integer j;
    protected Integer k;
    protected Integer l;
    protected Long m;
    protected Boolean n;
    protected Integer o;
    protected Integer p;
    protected Integer q;
    protected Integer r;
    protected Boolean s = Boolean.valueOf(true);
    protected String t;
    protected int[] u = {76};
    protected List<j> v = new ArrayList();

    public final j a(String beaconLayout) {
        this.a = beaconLayout;
        Log.d("BeaconParser", "Parsing beacon layout: " + beaconLayout);
        String[] terms = beaconLayout.split(",");
        this.n = Boolean.valueOf(false);
        for (String term : terms) {
            boolean found = false;
            Matcher matcher = w.matcher(term);
            while (matcher.find()) {
                found = true;
                try {
                    int startOffset = Integer.parseInt(matcher.group(1));
                    int endOffset = Integer.parseInt(matcher.group(2));
                    this.d.add(Boolean.valueOf(matcher.group(3).contains("l")));
                    this.h.add(Boolean.valueOf(matcher.group(3).contains("v")));
                    this.b.add(Integer.valueOf(startOffset));
                    this.c.add(Integer.valueOf(endOffset));
                } catch (NumberFormatException e2) {
                    throw new k("Cannot parse integer byte offset in term: " + term);
                }
            }
            Matcher matcher2 = z.matcher(term);
            while (matcher2.find()) {
                found = true;
                try {
                    int startOffset2 = Integer.parseInt(matcher2.group(1));
                    int endOffset2 = Integer.parseInt(matcher2.group(2));
                    this.g.add(Boolean.valueOf(matcher2.group(3).contains("l")));
                    this.e.add(Integer.valueOf(startOffset2));
                    this.f.add(Integer.valueOf(endOffset2));
                } catch (NumberFormatException e3) {
                    throw new k("Cannot parse integer byte offset in term: " + term);
                }
            }
            Matcher matcher3 = A.matcher(term);
            while (matcher3.find()) {
                found = true;
                try {
                    int startOffset3 = Integer.parseInt(matcher3.group(1));
                    int endOffset3 = Integer.parseInt(matcher3.group(2));
                    int dBmCorrection = 0;
                    if (matcher3.group(3) != null) {
                        dBmCorrection = Integer.parseInt(matcher3.group(3));
                    }
                    this.q = Integer.valueOf(dBmCorrection);
                    this.o = Integer.valueOf(startOffset3);
                    this.p = Integer.valueOf(endOffset3);
                } catch (NumberFormatException e4) {
                    throw new k("Cannot parse integer power byte offset in term: " + term);
                }
            }
            Matcher matcher4 = x.matcher(term);
            while (matcher4.find()) {
                found = true;
                try {
                    int startOffset4 = Integer.parseInt(matcher4.group(1));
                    int endOffset4 = Integer.parseInt(matcher4.group(2));
                    this.i = Integer.valueOf(startOffset4);
                    this.j = Integer.valueOf(endOffset4);
                    String hexString = matcher4.group(3);
                    try {
                        this.D = Long.decode("0x" + hexString);
                    } catch (NumberFormatException e5) {
                        throw new k("Cannot parse beacon type code: " + hexString + " in term: " + term);
                    }
                } catch (NumberFormatException e6) {
                    throw new k("Cannot parse integer byte offset in term: " + term);
                }
            }
            Matcher matcher5 = y.matcher(term);
            while (matcher5.find()) {
                found = true;
                try {
                    int startOffset5 = Integer.parseInt(matcher5.group(1));
                    int endOffset5 = Integer.parseInt(matcher5.group(2));
                    this.k = Integer.valueOf(startOffset5);
                    this.l = Integer.valueOf(endOffset5);
                    String hexString2 = matcher5.group(3);
                    try {
                        this.m = Long.decode("0x" + hexString2);
                    } catch (NumberFormatException e7) {
                        throw new k("Cannot parse serviceUuid: " + hexString2 + " in term: " + term);
                    }
                } catch (NumberFormatException e8) {
                    throw new k("Cannot parse integer byte offset in term: " + term);
                }
            }
            Matcher matcher6 = B.matcher(term);
            while (matcher6.find()) {
                found = true;
                this.n = Boolean.valueOf(true);
            }
            if (!found) {
                d.a("BeaconParser", "cannot parse term %s", term);
                throw new k("Cannot parse beacon layout term: " + term);
            }
        }
        if (!this.n.booleanValue()) {
            if (this.b.size() == 0 || this.c.size() == 0) {
                throw new k("You must supply at least one identifier offset with a prefix of 'i'");
            } else if (this.o == null || this.p == null) {
                throw new k("You must supply a power byte offset with a prefix of 'p'");
            }
        }
        if (this.i == null || this.j == null) {
            throw new k("You must supply a matching beacon type expression with a prefix of 'm'");
        }
        this.r = Integer.valueOf(h());
        return this;
    }

    public final List<j> a() {
        return new ArrayList(this.v);
    }

    public final int[] b() {
        return this.u;
    }

    public final Long c() {
        return this.D;
    }

    public final int d() {
        return this.i.intValue();
    }

    public final int e() {
        return this.j.intValue();
    }

    public final Long f() {
        return this.m;
    }

    public Beacon a(byte[] scanData, int rssi, BluetoothDevice device) {
        return a(scanData, rssi, device, new Beacon());
    }

    /* access modifiers changed from: protected */
    public final Beacon a(byte[] bytesToProcess, int rssi, BluetoothDevice device, Beacon beacon) {
        boolean z2;
        c pdu;
        a advert = new a(bytesToProcess);
        boolean parseFailed = false;
        c pduToParse = null;
        int startByte = 0;
        ArrayList identifiers = new ArrayList();
        ArrayList dataFields = new ArrayList();
        Iterator<c> it = advert.a().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            pdu = it.next();
            if (pdu.a() == 22 || pdu.a() == -1) {
                pduToParse = pdu;
            } else if (d.a()) {
                d.a("BeaconParser", "Ignoring pdu type %02X", Byte.valueOf(pdu.a()));
            }
        }
        pduToParse = pdu;
        if (d.a()) {
            d.a("BeaconParser", "Processing pdu type %02X: %s with startIndex: %d, endIndex: %d", Byte.valueOf(pdu.a()), a(bytesToProcess), Integer.valueOf(pdu.c()), Integer.valueOf(pdu.d()));
        }
        if (pduToParse == null) {
            if (d.a()) {
                d.a("BeaconParser", "No PDUs to process in this packet.", new Object[0]);
            }
            parseFailed = true;
        } else {
            byte[] serviceUuidBytes = null;
            byte[] typeCodeBytes = a(c().longValue(), (this.j.intValue() - this.i.intValue()) + 1);
            if (f() != null) {
                serviceUuidBytes = a(f().longValue(), (this.l.intValue() - this.k.intValue()) + 1, false);
            }
            startByte = pduToParse.c();
            boolean patternFound = false;
            if (f() == null) {
                if (a(bytesToProcess, this.i.intValue() + startByte, typeCodeBytes)) {
                    patternFound = true;
                }
            } else if (a(bytesToProcess, this.k.intValue() + startByte, serviceUuidBytes) && a(bytesToProcess, this.i.intValue() + startByte, typeCodeBytes)) {
                patternFound = true;
            }
            if (!patternFound) {
                if (f() == null) {
                    if (d.a()) {
                        d.a("BeaconParser", "This is not a matching Beacon advertisement. (Was expecting %s.  The bytes I see are: %s", b(typeCodeBytes), a(bytesToProcess));
                    }
                } else if (d.a()) {
                    d.a("BeaconParser", "This is not a matching Beacon advertisement. Was expecting %s at offset %d and %s at offset %d.  The bytes I see are: %s", b(serviceUuidBytes), Integer.valueOf(this.k.intValue() + startByte), b(typeCodeBytes), Integer.valueOf(this.i.intValue() + startByte), a(bytesToProcess));
                }
                parseFailed = true;
                beacon = null;
            } else if (d.a()) {
                d.a("BeaconParser", "This is a recognized beacon advertisement -- %s seen", b(typeCodeBytes));
                d.a("BeaconParser", "Bytes are: %s", a(bytesToProcess));
            }
            if (patternFound) {
                if (bytesToProcess.length <= this.r.intValue() + startByte && this.s.booleanValue()) {
                    if (d.a()) {
                        d.a("BeaconParser", "Expanding buffer because it is too short to parse: " + bytesToProcess.length + ", needed: " + (this.r.intValue() + startByte), new Object[0]);
                    }
                    bytesToProcess = a(bytesToProcess, this.r.intValue() + startByte);
                }
                for (int i2 = 0; i2 < this.c.size(); i2++) {
                    int endIndex = this.c.get(i2).intValue() + startByte;
                    if (endIndex > pduToParse.d() && this.h.get(i2).booleanValue()) {
                        if (d.a()) {
                            d.a("BeaconParser", "Need to truncate identifier by " + (endIndex - pduToParse.d()), new Object[0]);
                        }
                        identifiers.add(l.a(bytesToProcess, this.b.get(i2).intValue() + startByte, pduToParse.d() + 1, this.d.get(i2).booleanValue()));
                    } else if (endIndex <= pduToParse.d() || this.s.booleanValue()) {
                        identifiers.add(l.a(bytesToProcess, this.b.get(i2).intValue() + startByte, endIndex + 1, this.d.get(i2).booleanValue()));
                    } else {
                        parseFailed = true;
                        if (d.a()) {
                            d.a("BeaconParser", "Cannot parse identifier " + i2 + " because PDU is too short.  endIndex: " + endIndex + " PDU endIndex: " + pduToParse.d(), new Object[0]);
                        }
                    }
                }
                for (int i3 = 0; i3 < this.f.size(); i3++) {
                    int endIndex2 = this.f.get(i3).intValue() + startByte;
                    if (endIndex2 <= pduToParse.d() || this.s.booleanValue()) {
                        dataFields.add(Long.decode(a(bytesToProcess, this.e.get(i3).intValue() + startByte, endIndex2, this.g.get(i3).booleanValue())));
                    } else {
                        if (d.a()) {
                            d.a("BeaconParser", "Cannot parse data field " + i3 + " because PDU is too short.  endIndex: " + endIndex2 + " PDU endIndex: " + pduToParse.d() + ".  Setting value to 0", new Object[0]);
                        }
                        dataFields.add(new Long(0));
                    }
                }
                if (this.o != null) {
                    int endIndex3 = this.p.intValue() + startByte;
                    try {
                        if (endIndex3 <= pduToParse.d() || this.s.booleanValue()) {
                            int txPower = Integer.parseInt(a(bytesToProcess, this.o.intValue() + startByte, this.p.intValue() + startByte, false)) + this.q.intValue();
                            if (txPower > 127) {
                                txPower += InputDeviceCompat.SOURCE_ANY;
                            }
                            beacon.h = txPower;
                        } else {
                            parseFailed = true;
                            if (d.a()) {
                                d.a("BeaconParser", "Cannot parse power field because PDU is too short.  endIndex: " + endIndex3 + " PDU endIndex: " + pduToParse.d(), new Object[0]);
                            }
                        }
                    } catch (NumberFormatException e2) {
                    } catch (NullPointerException e3) {
                    }
                }
            }
        }
        if (parseFailed) {
            return null;
        }
        int beaconTypeCode = Integer.parseInt(a(bytesToProcess, this.i.intValue() + startByte, this.j.intValue() + startByte, false));
        int manufacturer = Integer.parseInt(a(bytesToProcess, startByte, startByte + 1, true));
        String macAddress = null;
        String name = null;
        if (device != null) {
            macAddress = device.getAddress();
            name = device.getName();
        }
        beacon.c = identifiers;
        beacon.d = dataFields;
        beacon.g = rssi;
        beacon.k = beaconTypeCode;
        if (this.m != null) {
            beacon.m = (int) this.m.longValue();
        } else {
            beacon.m = -1;
        }
        beacon.i = macAddress;
        beacon.n = name;
        beacon.l = manufacturer;
        beacon.o = this.t;
        if (this.v.size() > 0 || this.n.booleanValue()) {
            z2 = true;
        } else {
            z2 = false;
        }
        beacon.p = z2;
        return beacon;
    }

    public final String g() {
        return this.a;
    }

    private static String a(byte[] bytes) {
        char[] hexChars = new char[(bytes.length * 2)];
        for (int j2 = 0; j2 < bytes.length; j2++) {
            int v2 = bytes[j2] & 255;
            hexChars[j2 * 2] = C[v2 >>> 4];
            hexChars[(j2 * 2) + 1] = C[v2 & 15];
        }
        return new String(hexChars);
    }

    public static byte[] a(long longValue, int length) {
        return a(longValue, length, true);
    }

    private static byte[] a(long longValue, int length, boolean bigEndian) {
        byte[] array = new byte[length];
        for (int i2 = 0; i2 < length; i2++) {
            int adjustedI = bigEndian ? i2 : (length - i2) - 1;
            array[i2] = (byte) ((int) ((longValue & (255 << (((length - adjustedI) - 1) * 8))) >> ((int) ((long) (((length - adjustedI) - 1) * 8)))));
        }
        return array;
    }

    private int h() {
        int lastEndOffset = 0;
        if (this.c != null) {
            for (Integer intValue : this.c) {
                int endOffset = intValue.intValue();
                if (endOffset > lastEndOffset) {
                    lastEndOffset = endOffset;
                }
            }
        }
        if (this.f != null) {
            for (Integer intValue2 : this.f) {
                int endOffset2 = intValue2.intValue();
                if (endOffset2 > lastEndOffset) {
                    lastEndOffset = endOffset2;
                }
            }
        }
        if (this.p != null && this.p.intValue() > lastEndOffset) {
            lastEndOffset = this.p.intValue();
        }
        if (this.l != null && this.l.intValue() > lastEndOffset) {
            lastEndOffset = this.l.intValue();
        }
        return lastEndOffset + 1;
    }

    private static boolean a(byte[] source, int offset, byte[] expected) {
        int length = expected.length;
        if (source.length - offset < length) {
            return false;
        }
        for (int i2 = 0; i2 < length; i2++) {
            if (source[offset + i2] != expected[i2]) {
                return false;
            }
        }
        return true;
    }

    private static String b(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte valueOf : bytes) {
            sb.append(String.format("%02x", new Object[]{Byte.valueOf(valueOf)}));
            sb.append(Token.SEPARATOR);
        }
        return sb.toString().trim();
    }

    private static String a(byte[] byteBuffer, int startIndex, int endIndex, boolean littleEndian) {
        byte[] bytes = new byte[((endIndex - startIndex) + 1)];
        if (littleEndian) {
            for (int i2 = 0; i2 <= endIndex - startIndex; i2++) {
                bytes[i2] = byteBuffer[((bytes.length + startIndex) - 1) - i2];
            }
        } else {
            for (int i3 = 0; i3 <= endIndex - startIndex; i3++) {
                bytes[i3] = byteBuffer[startIndex + i3];
            }
        }
        if ((endIndex - startIndex) + 1 < 5) {
            long number = 0;
            for (int i4 = 0; i4 < bytes.length; i4++) {
                number += ((long) (bytes[(bytes.length - i4) - 1] & 255)) * ((long) Math.pow(256.0d, ((double) i4) * 1.0d));
            }
            return Long.toString(number);
        }
        String hexString = a(bytes);
        if (bytes.length != 16) {
            return "0x" + hexString;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(hexString.substring(0, 8));
        sb.append("-");
        sb.append(hexString.substring(8, 12));
        sb.append("-");
        sb.append(hexString.substring(12, 16));
        sb.append("-");
        sb.append(hexString.substring(16, 20));
        sb.append("-");
        sb.append(hexString.substring(20, 32));
        return sb.toString();
    }

    @TargetApi(9)
    private static byte[] a(byte[] array, int requiredLength) {
        return array.length >= requiredLength ? array : Arrays.copyOf(array, requiredLength);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.D, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.m, this.n, this.o, this.p, this.q, this.r, this.s, this.t, this.u, this.v});
    }

    public boolean equals(Object o2) {
        try {
            j that = (j) o2;
            if (that.a == null || !that.a.equals(this.a) || that.t == null || !that.t.equals(this.t)) {
                return false;
            }
            return true;
        } catch (ClassCastException e2) {
            return false;
        }
    }
}

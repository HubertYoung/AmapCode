package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alipay.apmobilesecuritysdk.rpc.gen.DeviceData;
import com.amap.location.common.model.AmapLoc;
import com.autonavi.miniapp.plugin.map.route.MiniAppShowRouteConfigHelper;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public final class JSONLexer {
    public static final char[] CA = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
    public static final int END = 4;
    public static final char EOI = '\u001a';
    static final int[] IA;
    public static final int NOT_MATCH = -1;
    public static final int NOT_MATCH_NAME = -2;
    public static final int UNKNOWN = 0;
    private static boolean V6 = false;
    public static final int VALUE = 3;
    protected static final int[] digits = new int[103];
    public static final boolean[] firstIdentifierFlags = new boolean[256];
    public static final boolean[] identifierFlags = new boolean[256];
    private static final ThreadLocal<char[]> sbufLocal = new ThreadLocal<>();
    protected int bp;
    public Calendar calendar;
    protected char ch;
    public boolean disableCircularReferenceDetect;
    protected int eofPos;
    protected boolean exp;
    public int features;
    protected long fieldHash;
    protected boolean hasSpecial;
    protected boolean isDouble;
    protected final int len;
    public Locale locale;
    public int matchStat;
    protected int np;
    protected int pos;
    protected char[] sbuf;
    protected int sp;
    protected String stringDefaultValue;
    protected final String text;
    public TimeZone timeZone;
    protected int token;

    static boolean checkDate(char c, char c2, char c3, char c4, char c5, char c6, int i, int i2) {
        if (c < '1' || c > '3' || c2 < '0' || c2 > '9' || c3 < '0' || c3 > '9' || c4 < '0' || c4 > '9') {
            return false;
        }
        if (c5 == '0') {
            if (c6 < '1' || c6 > '9') {
                return false;
            }
        } else if (c5 != '1') {
            return false;
        } else {
            if (!(c6 == '0' || c6 == '1' || c6 == '2')) {
                return false;
            }
        }
        if (i == 48) {
            if (i2 < 49 || i2 > 57) {
                return false;
            }
        } else if (i == 49 || i == 50) {
            if (i2 < 48 || i2 > 57) {
                return false;
            }
        } else if (i != 51) {
            return false;
        } else {
            if (!(i2 == 48 || i2 == 49)) {
                return false;
            }
        }
        return true;
    }

    static boolean checkTime(char c, char c2, char c3, char c4, char c5, char c6) {
        if (c == '0') {
            if (c2 < '0' || c2 > '9') {
                return false;
            }
        } else if (c == '1') {
            if (c2 < '0' || c2 > '9') {
                return false;
            }
        } else if (c != '2' || c2 < '0' || c2 > '4') {
            return false;
        }
        if (c3 < '0' || c3 > '5') {
            if (!(c3 == '6' && c4 == '0')) {
                return false;
            }
        } else if (c4 < '0' || c4 > '9') {
            return false;
        }
        if (c5 < '0' || c5 > '5') {
            if (!(c5 == '6' && c6 == '0')) {
                return false;
            }
        } else if (c6 < '0' || c6 > '9') {
            return false;
        }
        return true;
    }

    static {
        int i;
        try {
            i = Class.forName("android.os.Build$VERSION").getField("SDK_INT").getInt(null);
        } catch (Exception unused) {
            i = -1;
        }
        V6 = i >= 23;
        for (int i2 = 48; i2 <= 57; i2++) {
            digits[i2] = i2 - 48;
        }
        for (int i3 = 97; i3 <= 102; i3++) {
            digits[i3] = (i3 - 97) + 10;
        }
        for (int i4 = 65; i4 <= 70; i4++) {
            digits[i4] = (i4 - 65) + 10;
        }
        int[] iArr = new int[256];
        IA = iArr;
        Arrays.fill(iArr, -1);
        int length = CA.length;
        for (int i5 = 0; i5 < length; i5++) {
            IA[CA[i5]] = i5;
        }
        IA[61] = 0;
        for (char c = 0; c < firstIdentifierFlags.length; c = (char) (c + 1)) {
            if (c >= 'A' && c <= 'Z') {
                firstIdentifierFlags[c] = true;
            } else if (c >= 'a' && c <= 'z') {
                firstIdentifierFlags[c] = true;
            } else if (c == '_') {
                firstIdentifierFlags[c] = true;
            }
        }
        for (char c2 = 0; c2 < identifierFlags.length; c2 = (char) (c2 + 1)) {
            if (c2 >= 'A' && c2 <= 'Z') {
                identifierFlags[c2] = true;
            } else if (c2 >= 'a' && c2 <= 'z') {
                identifierFlags[c2] = true;
            } else if (c2 == '_') {
                identifierFlags[c2] = true;
            } else if (c2 >= '0' && c2 <= '9') {
                identifierFlags[c2] = true;
            }
        }
    }

    public JSONLexer(String str) {
        this(str, JSON.DEFAULT_PARSER_FEATURE);
    }

    public JSONLexer(char[] cArr, int i) {
        this(cArr, i, JSON.DEFAULT_PARSER_FEATURE);
    }

    public JSONLexer(char[] cArr, int i, int i2) {
        this(new String(cArr, 0, i), i2);
    }

    public JSONLexer(String str, int i) {
        char c;
        this.features = JSON.DEFAULT_PARSER_FEATURE;
        boolean z = false;
        this.exp = false;
        this.isDouble = false;
        this.timeZone = JSON.defaultTimeZone;
        this.locale = JSON.defaultLocale;
        String str2 = null;
        this.calendar = null;
        this.matchStat = 0;
        this.sbuf = sbufLocal.get();
        if (this.sbuf == null) {
            this.sbuf = new char[512];
        }
        this.features = i;
        this.text = str;
        this.len = this.text.length();
        this.bp = -1;
        int i2 = this.bp + 1;
        this.bp = i2;
        if (i2 >= this.len) {
            c = EOI;
        } else {
            c = this.text.charAt(i2);
        }
        this.ch = c;
        if (this.ch == 65279) {
            next();
        }
        this.stringDefaultValue = (Feature.InitStringFieldAsEmpty.mask & i) != 0 ? "" : str2;
        this.disableCircularReferenceDetect = (Feature.DisableCircularReferenceDetect.mask & i) != 0 ? true : z;
    }

    public final int token() {
        return this.token;
    }

    public final void close() {
        if (this.sbuf.length <= 8196) {
            sbufLocal.set(this.sbuf);
        }
        this.sbuf = null;
    }

    public final char next() {
        char c;
        int i = this.bp + 1;
        this.bp = i;
        if (i >= this.len) {
            c = EOI;
        } else {
            c = this.text.charAt(i);
        }
        this.ch = c;
        return c;
    }

    public final void config(Feature feature, boolean z) {
        if (z) {
            this.features |= feature.mask;
        } else {
            this.features &= ~feature.mask;
        }
        if (feature == Feature.InitStringFieldAsEmpty) {
            this.stringDefaultValue = z ? "" : null;
        }
        this.disableCircularReferenceDetect = (this.features & Feature.DisableCircularReferenceDetect.mask) != 0;
    }

    public final boolean isEnabled(Feature feature) {
        return (feature.mask & this.features) != 0;
    }

    public final void nextTokenWithChar(char c) {
        char c2;
        this.sp = 0;
        while (this.ch != c) {
            if (this.ch == ' ' || this.ch == 10 || this.ch == 13 || this.ch == 9 || this.ch == 12 || this.ch == 8) {
                next();
            } else {
                StringBuilder sb = new StringBuilder("not match ");
                sb.append(c);
                sb.append(" - ");
                sb.append(this.ch);
                throw new JSONException(sb.toString());
            }
        }
        int i = this.bp + 1;
        this.bp = i;
        if (i >= this.len) {
            c2 = EOI;
        } else {
            c2 = this.text.charAt(i);
        }
        this.ch = c2;
        nextToken();
    }

    public final String numberString() {
        char charAt = this.text.charAt((this.np + this.sp) - 1);
        int i = this.sp;
        if (charAt == 'L' || charAt == 'S' || charAt == 'B' || charAt == 'F' || charAt == 'D') {
            i--;
        }
        return subString(this.np, i);
    }

    /* access modifiers changed from: protected */
    public final char charAt(int i) {
        if (i >= this.len) {
            return EOI;
        }
        return this.text.charAt(i);
    }

    public final void nextToken() {
        int i = 0;
        this.sp = 0;
        while (true) {
            this.pos = this.bp;
            if (this.ch == '/') {
                skipComment();
            } else if (this.ch == '\"') {
                scanString();
                return;
            } else if ((this.ch < '0' || this.ch > '9') && this.ch != '-') {
                if (this.ch == ',') {
                    next();
                    this.token = 16;
                    return;
                }
                char c = this.ch;
                char c2 = EOI;
                switch (c) {
                    case 8:
                    case 9:
                    case 10:
                    case 12:
                    case 13:
                    case ' ':
                        next();
                        break;
                    case '\'':
                        scanString();
                        return;
                    case '(':
                        next();
                        this.token = 10;
                        return;
                    case ')':
                        next();
                        this.token = 11;
                        return;
                    case ':':
                        next();
                        this.token = 17;
                        return;
                    case DeviceData.TAG_AC10 /*83*/:
                    case 'T':
                    case 'u':
                        scanIdent();
                        return;
                    case '[':
                        int i2 = this.bp + 1;
                        this.bp = i2;
                        if (i2 < this.len) {
                            c2 = this.text.charAt(i2);
                        }
                        this.ch = c2;
                        this.token = 14;
                        return;
                    case ']':
                        next();
                        this.token = 15;
                        return;
                    case 'f':
                        if (this.text.startsWith("false", this.bp)) {
                            this.bp += 5;
                            this.ch = charAt(this.bp);
                            if (this.ch == ' ' || this.ch == ',' || this.ch == '}' || this.ch == ']' || this.ch == 10 || this.ch == 13 || this.ch == 9 || this.ch == 26 || this.ch == 12 || this.ch == 8 || this.ch == ':') {
                                this.token = 7;
                                return;
                            }
                        }
                        throw new JSONException("scan false error");
                    case 'n':
                        if (this.text.startsWith("null", this.bp)) {
                            this.bp += 4;
                            i = 8;
                        } else if (this.text.startsWith(AmapLoc.TYPE_NEW, this.bp)) {
                            this.bp += 3;
                            i = 9;
                        }
                        if (i != 0) {
                            this.ch = charAt(this.bp);
                            if (this.ch == ' ' || this.ch == ',' || this.ch == '}' || this.ch == ']' || this.ch == 10 || this.ch == 13 || this.ch == 9 || this.ch == 26 || this.ch == 12 || this.ch == 8) {
                                this.token = i;
                                return;
                            }
                        }
                        throw new JSONException("scan null/new error");
                    case 't':
                        if (this.text.startsWith("true", this.bp)) {
                            this.bp += 4;
                            this.ch = charAt(this.bp);
                            if (this.ch == ' ' || this.ch == ',' || this.ch == '}' || this.ch == ']' || this.ch == 10 || this.ch == 13 || this.ch == 9 || this.ch == 26 || this.ch == 12 || this.ch == 8 || this.ch == ':') {
                                this.token = 6;
                                return;
                            }
                        }
                        throw new JSONException("scan true error");
                    case '{':
                        int i3 = this.bp + 1;
                        this.bp = i3;
                        if (i3 < this.len) {
                            c2 = this.text.charAt(i3);
                        }
                        this.ch = c2;
                        this.token = 12;
                        return;
                    case '}':
                        int i4 = this.bp + 1;
                        this.bp = i4;
                        if (i4 < this.len) {
                            c2 = this.text.charAt(i4);
                        }
                        this.ch = c2;
                        this.token = 13;
                        return;
                    default:
                        if (this.bp == this.len || (this.ch == 26 && this.bp + 1 == this.len)) {
                            if (this.token == 20) {
                                throw new JSONException("EOF error");
                            }
                            this.token = 20;
                            int i5 = this.eofPos;
                            this.bp = i5;
                            this.pos = i5;
                            return;
                        } else if (this.ch <= 31 || this.ch == 127) {
                            next();
                            break;
                        } else {
                            this.token = 1;
                            next();
                            return;
                        }
                }
            }
        }
        scanNumber();
    }

    public final void nextToken(int i) {
        this.sp = 0;
        while (true) {
            if (i != 2) {
                char c = EOI;
                if (i != 4) {
                    if (i != 12) {
                        if (i != 18) {
                            if (i != 20) {
                                switch (i) {
                                    case 14:
                                        if (this.ch == '[') {
                                            this.token = 14;
                                            next();
                                            return;
                                        } else if (this.ch == '{') {
                                            this.token = 12;
                                            next();
                                            return;
                                        }
                                        break;
                                    case 15:
                                        if (this.ch == ']') {
                                            this.token = 15;
                                            next();
                                            return;
                                        }
                                        break;
                                    case 16:
                                        if (this.ch == ',') {
                                            this.token = 16;
                                            int i2 = this.bp + 1;
                                            this.bp = i2;
                                            if (i2 < this.len) {
                                                c = this.text.charAt(i2);
                                            }
                                            this.ch = c;
                                            return;
                                        } else if (this.ch == '}') {
                                            this.token = 13;
                                            int i3 = this.bp + 1;
                                            this.bp = i3;
                                            if (i3 < this.len) {
                                                c = this.text.charAt(i3);
                                            }
                                            this.ch = c;
                                            return;
                                        } else if (this.ch == ']') {
                                            this.token = 15;
                                            int i4 = this.bp + 1;
                                            this.bp = i4;
                                            if (i4 < this.len) {
                                                c = this.text.charAt(i4);
                                            }
                                            this.ch = c;
                                            return;
                                        } else if (this.ch == 26) {
                                            this.token = 20;
                                            return;
                                        }
                                        break;
                                }
                            }
                            if (this.ch == 26) {
                                this.token = 20;
                                return;
                            }
                        } else {
                            nextIdent();
                            return;
                        }
                    } else if (this.ch == '{') {
                        this.token = 12;
                        int i5 = this.bp + 1;
                        this.bp = i5;
                        if (i5 < this.len) {
                            c = this.text.charAt(i5);
                        }
                        this.ch = c;
                        return;
                    } else if (this.ch == '[') {
                        this.token = 14;
                        int i6 = this.bp + 1;
                        this.bp = i6;
                        if (i6 < this.len) {
                            c = this.text.charAt(i6);
                        }
                        this.ch = c;
                        return;
                    }
                } else if (this.ch == '\"') {
                    this.pos = this.bp;
                    scanString();
                    return;
                } else if (this.ch >= '0' && this.ch <= '9') {
                    this.pos = this.bp;
                    scanNumber();
                    return;
                } else if (this.ch == '{') {
                    this.token = 12;
                    int i7 = this.bp + 1;
                    this.bp = i7;
                    if (i7 < this.len) {
                        c = this.text.charAt(i7);
                    }
                    this.ch = c;
                    return;
                }
            } else if (this.ch >= '0' && this.ch <= '9') {
                this.pos = this.bp;
                scanNumber();
                return;
            } else if (this.ch == '\"') {
                this.pos = this.bp;
                scanString();
                return;
            } else if (this.ch == '[') {
                this.token = 14;
                next();
                return;
            } else if (this.ch == '{') {
                this.token = 12;
                next();
                return;
            }
            if (this.ch == ' ' || this.ch == 10 || this.ch == 13 || this.ch == 9 || this.ch == 12 || this.ch == 8) {
                next();
            } else {
                nextToken();
                return;
            }
        }
    }

    public final void nextIdent() {
        while (true) {
            if (!(this.ch <= ' ' && (this.ch == ' ' || this.ch == 10 || this.ch == 13 || this.ch == 9 || this.ch == 12 || this.ch == 8))) {
                break;
            }
            next();
        }
        if (this.ch == '_' || Character.isLetter(this.ch)) {
            scanIdent();
        } else {
            nextToken();
        }
    }

    public final Number integerValue() throws NumberFormatException {
        char c;
        char c2;
        char c3;
        boolean z;
        long j;
        long j2;
        char c4;
        char c5;
        int i = this.np;
        int i2 = this.np + this.sp;
        int i3 = i2 - 1;
        if (i3 >= this.len) {
            c = EOI;
        } else {
            c = this.text.charAt(i3);
        }
        if (c == 'B') {
            i2--;
            c2 = 'B';
        } else if (c == 'L') {
            i2--;
            c2 = 'L';
        } else if (c != 'S') {
            c2 = ' ';
        } else {
            i2--;
            c2 = 'S';
        }
        if (this.np >= this.len) {
            c3 = EOI;
        } else {
            c3 = this.text.charAt(this.np);
        }
        if (c3 == '-') {
            j = Long.MIN_VALUE;
            i++;
            z = true;
        } else {
            j = -9223372036854775807L;
            z = false;
        }
        if (i < i2) {
            int i4 = i + 1;
            if (i >= this.len) {
                c5 = EOI;
            } else {
                c5 = this.text.charAt(i);
            }
            j2 = (long) (-(c5 - '0'));
            i = i4;
        } else {
            j2 = 0;
        }
        while (i < i2) {
            int i5 = i + 1;
            if (i >= this.len) {
                c4 = EOI;
            } else {
                c4 = this.text.charAt(i);
            }
            int i6 = c4 - '0';
            if (j2 < -922337203685477580L) {
                return new BigInteger(numberString());
            }
            long j3 = j2 * 10;
            long j4 = (long) i6;
            if (j3 < j + j4) {
                return new BigInteger(numberString());
            }
            j2 = j3 - j4;
            i = i5;
        }
        if (!z) {
            long j5 = -j2;
            if (j5 > 2147483647L || c2 == 'L') {
                return Long.valueOf(j5);
            }
            if (c2 == 'S') {
                return Short.valueOf((short) ((int) j5));
            }
            if (c2 == 'B') {
                return Byte.valueOf((byte) ((int) j5));
            }
            return Integer.valueOf((int) j5);
        } else if (i <= this.np + 1) {
            throw new NumberFormatException(numberString());
        } else if (j2 < -2147483648L || c2 == 'L') {
            return Long.valueOf(j2);
        } else {
            if (c2 == 'S') {
                return Short.valueOf((short) ((int) j2));
            }
            if (c2 == 'B') {
                return Byte.valueOf((byte) ((int) j2));
            }
            return Integer.valueOf((int) j2);
        }
    }

    public final String scanSymbol(SymbolTable symbolTable) {
        while (true) {
            if (this.ch != ' ' && this.ch != 10 && this.ch != 13 && this.ch != 9 && this.ch != 12 && this.ch != 8) {
                break;
            }
            next();
        }
        if (this.ch == '\"') {
            return scanSymbol(symbolTable, '\"');
        }
        if (this.ch == '\'') {
            return scanSymbol(symbolTable, '\'');
        }
        if (this.ch == '}') {
            next();
            this.token = 13;
            return null;
        } else if (this.ch == ',') {
            next();
            this.token = 16;
            return null;
        } else if (this.ch != 26) {
            return scanSymbolUnQuoted(symbolTable);
        } else {
            this.token = 20;
            return null;
        }
    }

    public final String scanSymbol(SymbolTable symbolTable, char c) {
        String str;
        char c2;
        int i = this.bp + 1;
        int indexOf = this.text.indexOf(c, i);
        if (indexOf == -1) {
            StringBuilder sb = new StringBuilder("unclosed str, ");
            sb.append(info());
            throw new JSONException(sb.toString());
        }
        int i2 = indexOf - i;
        char[] sub_chars = sub_chars(this.bp + 1, i2);
        int i3 = indexOf;
        boolean z = false;
        while (i2 > 0 && sub_chars[i2 - 1] == '\\') {
            int i4 = i2 - 2;
            int i5 = 1;
            while (i4 >= 0 && sub_chars[i4] == '\\') {
                i5++;
                i4--;
            }
            if (i5 % 2 == 0) {
                break;
            }
            int indexOf2 = this.text.indexOf(c, i3 + 1);
            int i6 = (indexOf2 - i3) + i2;
            if (i6 >= sub_chars.length) {
                int length = (sub_chars.length * 3) / 2;
                if (length < i6) {
                    length = i6;
                }
                char[] cArr = new char[length];
                System.arraycopy(sub_chars, 0, cArr, 0, sub_chars.length);
                sub_chars = cArr;
            }
            this.text.getChars(i3, indexOf2, sub_chars, i2);
            i3 = indexOf2;
            i2 = i6;
            z = true;
        }
        if (!z) {
            boolean z2 = z;
            int i7 = 0;
            for (int i8 = 0; i8 < i2; i8++) {
                char c3 = sub_chars[i8];
                i7 = (i7 * 31) + c3;
                if (c3 == '\\') {
                    z2 = true;
                }
            }
            str = z2 ? readString(sub_chars, i2) : i2 < 20 ? symbolTable.addSymbol(sub_chars, 0, i2, i7) : new String(sub_chars, 0, i2);
        } else {
            str = readString(sub_chars, i2);
        }
        this.bp = i3 + 1;
        int i9 = this.bp;
        if (i9 >= this.len) {
            c2 = EOI;
        } else {
            c2 = this.text.charAt(i9);
        }
        this.ch = c2;
        return str;
    }

    private static String readString(char[] cArr, int i) {
        int i2;
        int i3;
        char[] cArr2 = new char[i];
        int i4 = 0;
        int i5 = 0;
        while (i2 < i) {
            char c = cArr[i2];
            if (c != '\\') {
                cArr2[i5] = c;
                i5++;
            } else {
                i2++;
                char c2 = cArr[i2];
                switch (c2) {
                    case '/':
                        i3 = i5 + 1;
                        cArr2[i5] = '/';
                        break;
                    case '0':
                        i3 = i5 + 1;
                        cArr2[i5] = 0;
                        break;
                    case '1':
                        i3 = i5 + 1;
                        cArr2[i5] = 1;
                        break;
                    case '2':
                        i3 = i5 + 1;
                        cArr2[i5] = 2;
                        break;
                    case '3':
                        i3 = i5 + 1;
                        cArr2[i5] = 3;
                        break;
                    case '4':
                        i3 = i5 + 1;
                        cArr2[i5] = 4;
                        break;
                    case '5':
                        i3 = i5 + 1;
                        cArr2[i5] = 5;
                        break;
                    case '6':
                        i3 = i5 + 1;
                        cArr2[i5] = 6;
                        break;
                    case '7':
                        i3 = i5 + 1;
                        cArr2[i5] = 7;
                        break;
                    default:
                        switch (c2) {
                            case 't':
                                i3 = i5 + 1;
                                cArr2[i5] = 9;
                                break;
                            case 'u':
                                i3 = i5 + 1;
                                int i6 = i2 + 1;
                                int i7 = i6 + 1;
                                int i8 = i7 + 1;
                                i2 = i8 + 1;
                                cArr2[i5] = (char) Integer.parseInt(new String(new char[]{cArr[i6], cArr[i7], cArr[i8], cArr[i2]}), 16);
                                break;
                            case 'v':
                                i3 = i5 + 1;
                                cArr2[i5] = 11;
                                break;
                            default:
                                switch (c2) {
                                    case '\"':
                                        i3 = i5 + 1;
                                        cArr2[i5] = '\"';
                                        break;
                                    case '\'':
                                        i3 = i5 + 1;
                                        cArr2[i5] = '\'';
                                        break;
                                    case 'F':
                                    case 'f':
                                        i3 = i5 + 1;
                                        cArr2[i5] = 12;
                                        break;
                                    case '\\':
                                        i3 = i5 + 1;
                                        cArr2[i5] = '\\';
                                        break;
                                    case 'b':
                                        i3 = i5 + 1;
                                        cArr2[i5] = 8;
                                        break;
                                    case 'n':
                                        i3 = i5 + 1;
                                        cArr2[i5] = 10;
                                        break;
                                    case 'r':
                                        i3 = i5 + 1;
                                        cArr2[i5] = 13;
                                        break;
                                    case MiniAppShowRouteConfigHelper.LINE_TYPE_VIA_ROAD_LINE_FREE_ /*120*/:
                                        i3 = i5 + 1;
                                        int i9 = i2 + 1;
                                        i2 = i9 + 1;
                                        cArr2[i5] = (char) ((digits[cArr[i9]] * 16) + digits[cArr[i2]]);
                                        break;
                                    default:
                                        throw new JSONException("unclosed.str.lit");
                                }
                        }
                }
                i5 = i3;
            }
            i4 = i2 + 1;
        }
        return new String(cArr2, 0, i5);
    }

    public final String info() {
        String str;
        StringBuilder sb = new StringBuilder("pos ");
        sb.append(this.bp);
        sb.append(", json : ");
        if (this.len < 65536) {
            str = this.text;
        } else {
            str = this.text.substring(0, 65536);
        }
        sb.append(str);
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public final void skipComment() {
        next();
        if (this.ch == '/') {
            do {
                next();
            } while (this.ch != 10);
            next();
        } else if (this.ch == '*') {
            next();
            while (this.ch != 26) {
                if (this.ch == '*') {
                    next();
                    if (this.ch == '/') {
                        next();
                        return;
                    }
                } else {
                    next();
                }
            }
        } else {
            throw new JSONException("invalid comment");
        }
    }

    public final String scanSymbolUnQuoted(SymbolTable symbolTable) {
        int i = this.ch;
        if (!(this.ch >= firstIdentifierFlags.length || firstIdentifierFlags[i])) {
            StringBuilder sb = new StringBuilder("illegal identifier : ");
            sb.append(this.ch);
            sb.append(", ");
            sb.append(info());
            throw new JSONException(sb.toString());
        }
        this.np = this.bp;
        this.sp = 1;
        while (true) {
            char next = next();
            if (next < identifierFlags.length && !identifierFlags[next]) {
                break;
            }
            i = (i * 31) + next;
            this.sp++;
        }
        this.ch = charAt(this.bp);
        this.token = 18;
        if (this.sp != 4 || !this.text.startsWith("null", this.np)) {
            return symbolTable.addSymbol(this.text, this.np, this.sp, i);
        }
        return null;
    }

    public final void scanString() {
        char c;
        char c2 = this.ch;
        int i = this.bp + 1;
        int indexOf = this.text.indexOf(c2, i);
        if (indexOf == -1) {
            StringBuilder sb = new StringBuilder("unclosed str, ");
            sb.append(info());
            throw new JSONException(sb.toString());
        }
        int i2 = indexOf - i;
        char[] sub_chars = sub_chars(this.bp + 1, i2);
        boolean z = false;
        while (i2 > 0 && sub_chars[i2 - 1] == '\\') {
            int i3 = i2 - 2;
            int i4 = 1;
            while (i3 >= 0 && sub_chars[i3] == '\\') {
                i4++;
                i3--;
            }
            if (i4 % 2 == 0) {
                break;
            }
            int indexOf2 = this.text.indexOf(c2, indexOf + 1);
            int i5 = (indexOf2 - indexOf) + i2;
            if (i5 >= sub_chars.length) {
                int length = (sub_chars.length * 3) / 2;
                if (length < i5) {
                    length = i5;
                }
                char[] cArr = new char[length];
                System.arraycopy(sub_chars, 0, cArr, 0, sub_chars.length);
                sub_chars = cArr;
            }
            this.text.getChars(indexOf, indexOf2, sub_chars, i2);
            indexOf = indexOf2;
            i2 = i5;
            z = true;
        }
        if (!z) {
            for (int i6 = 0; i6 < i2; i6++) {
                if (sub_chars[i6] == '\\') {
                    z = true;
                }
            }
        }
        this.sbuf = sub_chars;
        this.sp = i2;
        this.np = this.bp;
        this.hasSpecial = z;
        this.bp = indexOf + 1;
        int i7 = this.bp;
        if (i7 >= this.len) {
            c = EOI;
        } else {
            c = this.text.charAt(i7);
        }
        this.ch = c;
        this.token = 4;
    }

    public final String scanStringValue(char c) {
        String str;
        char c2;
        int i = this.bp + 1;
        int indexOf = this.text.indexOf(c, i);
        if (indexOf == -1) {
            StringBuilder sb = new StringBuilder("unclosed str, ");
            sb.append(info());
            throw new JSONException(sb.toString());
        }
        if (V6) {
            str = this.text.substring(i, indexOf);
        } else {
            int i2 = indexOf - i;
            str = new String(sub_chars(this.bp + 1, i2), 0, i2);
        }
        if (str.indexOf(92) != -1) {
            while (true) {
                int i3 = indexOf - 1;
                int i4 = 0;
                while (i3 >= 0 && this.text.charAt(i3) == '\\') {
                    i4++;
                    i3--;
                }
                if (i4 % 2 == 0) {
                    break;
                }
                indexOf = this.text.indexOf(c, indexOf + 1);
            }
            int i5 = indexOf - i;
            str = readString(sub_chars(this.bp + 1, i5), i5);
        }
        this.bp = indexOf + 1;
        int i6 = this.bp;
        if (i6 >= this.len) {
            c2 = EOI;
        } else {
            c2 = this.text.charAt(i6);
        }
        this.ch = c2;
        return str;
    }

    public final int intValue() {
        char c;
        int i;
        boolean z;
        int i2;
        char c2;
        char c3;
        int i3 = this.np;
        int i4 = this.np + this.sp;
        if (this.np >= this.len) {
            c = EOI;
        } else {
            c = this.text.charAt(this.np);
        }
        int i5 = 0;
        if (c == '-') {
            i3++;
            z = true;
            i = Integer.MIN_VALUE;
        } else {
            z = false;
            i = -2147483647;
        }
        if (i3 < i4) {
            int i6 = i3 + 1;
            if (i3 >= this.len) {
                c3 = EOI;
            } else {
                c3 = this.text.charAt(i3);
            }
            int i7 = i6;
            i5 = -(c3 - '0');
            i3 = i7;
        }
        while (true) {
            if (i3 >= i4) {
                break;
            }
            i2 = i3 + 1;
            if (i3 >= this.len) {
                c2 = EOI;
            } else {
                c2 = this.text.charAt(i3);
            }
            if (c2 == 'L' || c2 == 'S' || c2 == 'B') {
                i3 = i2;
            } else {
                int i8 = c2 - '0';
                if (i5 < -214748364) {
                    throw new NumberFormatException(numberString());
                }
                int i9 = i5 * 10;
                if (i9 < i + i8) {
                    throw new NumberFormatException(numberString());
                }
                i5 = i9 - i8;
                i3 = i2;
            }
        }
        i3 = i2;
        if (!z) {
            return -i5;
        }
        if (i3 > this.np + 1) {
            return i5;
        }
        throw new NumberFormatException(numberString());
    }

    public final byte[] bytesValue() {
        return decodeFast(this.text, this.np + 1, this.sp);
    }

    private void scanIdent() {
        this.np = this.bp - 1;
        this.hasSpecial = false;
        do {
            this.sp++;
            next();
        } while (Character.isLetterOrDigit(this.ch));
        String stringVal = stringVal();
        if (stringVal.equals("null")) {
            this.token = 8;
        } else if (stringVal.equals("true")) {
            this.token = 6;
        } else if (stringVal.equals("false")) {
            this.token = 7;
        } else if (stringVal.equals(AmapLoc.TYPE_NEW)) {
            this.token = 9;
        } else if (stringVal.equals("undefined")) {
            this.token = 23;
        } else if (stringVal.equals("Set")) {
            this.token = 21;
        } else if (stringVal.equals("TreeSet")) {
            this.token = 22;
        } else {
            this.token = 18;
        }
    }

    public final String stringVal() {
        if (this.hasSpecial) {
            return readString(this.sbuf, this.sp);
        }
        return subString(this.np + 1, this.sp);
    }

    private final String subString(int i, int i2) {
        if (i2 < this.sbuf.length) {
            this.text.getChars(i, i + i2, this.sbuf, 0);
            return new String(this.sbuf, 0, i2);
        }
        char[] cArr = new char[i2];
        this.text.getChars(i, i2 + i, cArr, 0);
        return new String(cArr);
    }

    /* access modifiers changed from: 0000 */
    public final char[] sub_chars(int i, int i2) {
        if (i2 < this.sbuf.length) {
            this.text.getChars(i, i2 + i, this.sbuf, 0);
            return this.sbuf;
        }
        char[] cArr = new char[i2];
        this.sbuf = cArr;
        this.text.getChars(i, i2 + i, cArr, 0);
        return cArr;
    }

    public final boolean isBlankInput() {
        int i = 0;
        while (true) {
            char charAt = charAt(i);
            boolean z = true;
            if (charAt == 26) {
                return true;
            }
            if (charAt > ' ' || !(charAt == ' ' || charAt == 10 || charAt == 13 || charAt == 9 || charAt == 12 || charAt == 8)) {
                z = false;
            }
            if (!z) {
                return false;
            }
            i++;
        }
    }

    /* access modifiers changed from: 0000 */
    public final void skipWhitespace() {
        while (this.ch <= '/') {
            if (this.ch == ' ' || this.ch == 13 || this.ch == 10 || this.ch == 9 || this.ch == 12 || this.ch == 8) {
                next();
            } else if (this.ch == '/') {
                skipComment();
            } else {
                return;
            }
        }
    }

    public final void scanNumber() {
        char c;
        char c2;
        char c3;
        char c4;
        char c5;
        char c6;
        char c7;
        this.np = this.bp;
        this.exp = false;
        if (this.ch == '-') {
            this.sp++;
            int i = this.bp + 1;
            this.bp = i;
            if (i >= this.len) {
                c7 = EOI;
            } else {
                c7 = this.text.charAt(i);
            }
            this.ch = c7;
        }
        while (this.ch >= '0' && this.ch <= '9') {
            this.sp++;
            int i2 = this.bp + 1;
            this.bp = i2;
            if (i2 >= this.len) {
                c6 = EOI;
            } else {
                c6 = this.text.charAt(i2);
            }
            this.ch = c6;
        }
        this.isDouble = false;
        if (this.ch == '.') {
            this.sp++;
            int i3 = this.bp + 1;
            this.bp = i3;
            if (i3 >= this.len) {
                c4 = EOI;
            } else {
                c4 = this.text.charAt(i3);
            }
            this.ch = c4;
            this.isDouble = true;
            while (this.ch >= '0' && this.ch <= '9') {
                this.sp++;
                int i4 = this.bp + 1;
                this.bp = i4;
                if (i4 >= this.len) {
                    c5 = EOI;
                } else {
                    c5 = this.text.charAt(i4);
                }
                this.ch = c5;
            }
        }
        if (this.ch == 'L') {
            this.sp++;
            next();
        } else if (this.ch == 'S') {
            this.sp++;
            next();
        } else if (this.ch == 'B') {
            this.sp++;
            next();
        } else if (this.ch == 'F') {
            this.sp++;
            next();
            this.isDouble = true;
        } else if (this.ch == 'D') {
            this.sp++;
            next();
            this.isDouble = true;
        } else if (this.ch == 'e' || this.ch == 'E') {
            this.sp++;
            int i5 = this.bp + 1;
            this.bp = i5;
            if (i5 >= this.len) {
                c = EOI;
            } else {
                c = this.text.charAt(i5);
            }
            this.ch = c;
            if (this.ch == '+' || this.ch == '-') {
                this.sp++;
                int i6 = this.bp + 1;
                this.bp = i6;
                if (i6 >= this.len) {
                    c3 = EOI;
                } else {
                    c3 = this.text.charAt(i6);
                }
                this.ch = c3;
            }
            while (this.ch >= '0' && this.ch <= '9') {
                this.sp++;
                int i7 = this.bp + 1;
                this.bp = i7;
                if (i7 >= this.len) {
                    c2 = EOI;
                } else {
                    c2 = this.text.charAt(i7);
                }
                this.ch = c2;
            }
            if (this.ch == 'D' || this.ch == 'F') {
                this.sp++;
                next();
            }
            this.exp = true;
            this.isDouble = true;
        }
        if (this.isDouble) {
            this.token = 3;
        } else {
            this.token = 2;
        }
    }

    public final boolean scanBoolean() {
        boolean z = false;
        int i = 1;
        if (this.text.startsWith("false", this.bp)) {
            i = 5;
        } else if (this.text.startsWith("true", this.bp)) {
            z = true;
            i = 4;
        } else if (this.ch == '1') {
            z = true;
        } else if (this.ch != '0') {
            this.matchStat = -1;
            return false;
        }
        this.bp += i;
        this.ch = charAt(this.bp);
        return z;
    }

    /* JADX WARNING: Removed duplicated region for block: B:151:0x02a9 A[Catch:{ NumberFormatException -> 0x02ef }] */
    /* JADX WARNING: Removed duplicated region for block: B:160:0x02c1 A[Catch:{ NumberFormatException -> 0x02ef }] */
    /* JADX WARNING: Removed duplicated region for block: B:165:0x02cc A[Catch:{ NumberFormatException -> 0x02ef }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Number scanNumberValue() {
        /*
            r22 = this;
            r1 = r22
            int r2 = r1.bp
            r3 = 0
            r1.np = r3
            char r4 = r1.ch
            r5 = 45
            r7 = 1
            if (r4 != r5) goto L_0x002b
            r8 = -9223372036854775808
            int r4 = r1.np
            int r4 = r4 + r7
            r1.np = r4
            int r4 = r1.bp
            int r4 = r4 + r7
            r1.bp = r4
            int r10 = r1.len
            if (r4 < r10) goto L_0x0021
            r4 = 26
            goto L_0x0027
        L_0x0021:
            java.lang.String r10 = r1.text
            char r4 = r10.charAt(r4)
        L_0x0027:
            r1.ch = r4
            r4 = 1
            goto L_0x0031
        L_0x002b:
            r8 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            r4 = 0
        L_0x0031:
            r10 = 0
            r11 = r10
            r10 = 1
            r13 = 0
        L_0x0036:
            char r14 = r1.ch
            r15 = -922337203685477580(0xf333333333333334, double:-8.390303882365713E246)
            r6 = 18
            r3 = 57
            r17 = 10
            r5 = 48
            if (r14 < r5) goto L_0x0085
            char r14 = r1.ch
            if (r14 > r3) goto L_0x0085
            char r3 = r1.ch
            int r3 = r3 - r5
            if (r10 >= r6) goto L_0x0055
            long r11 = r11 * r17
            long r5 = (long) r3
            long r11 = r11 - r5
            goto L_0x0066
        L_0x0055:
            int r5 = (r11 > r15 ? 1 : (r11 == r15 ? 0 : -1))
            if (r5 >= 0) goto L_0x005a
            r13 = 1
        L_0x005a:
            long r11 = r11 * r17
            long r5 = (long) r3
            long r14 = r8 + r5
            int r3 = (r11 > r14 ? 1 : (r11 == r14 ? 0 : -1))
            if (r3 >= 0) goto L_0x0064
            r13 = 1
        L_0x0064:
            r3 = 0
            long r11 = r11 - r5
        L_0x0066:
            int r3 = r1.np
            int r3 = r3 + r7
            r1.np = r3
            int r3 = r1.bp
            int r3 = r3 + r7
            r1.bp = r3
            int r5 = r1.len
            if (r3 < r5) goto L_0x0077
            r6 = 26
            goto L_0x007d
        L_0x0077:
            java.lang.String r5 = r1.text
            char r6 = r5.charAt(r3)
        L_0x007d:
            r1.ch = r6
            int r10 = r10 + 1
            r3 = 0
            r5 = 45
            goto L_0x0036
        L_0x0085:
            char r14 = r1.ch
            r15 = 46
            r16 = 0
            if (r14 != r15) goto L_0x00fd
            int r14 = r1.np
            int r14 = r14 + r7
            r1.np = r14
            int r14 = r1.bp
            int r14 = r14 + r7
            r1.bp = r14
            int r15 = r1.len
            if (r14 < r15) goto L_0x009e
            r14 = 26
            goto L_0x00a4
        L_0x009e:
            java.lang.String r15 = r1.text
            char r14 = r15.charAt(r14)
        L_0x00a4:
            r1.ch = r14
            r14 = r13
            r12 = r11
            r11 = r10
            r10 = 0
        L_0x00aa:
            char r15 = r1.ch
            if (r15 < r5) goto L_0x00f6
            char r15 = r1.ch
            if (r15 > r3) goto L_0x00f6
            int r10 = r10 + 1
            char r15 = r1.ch
            int r15 = r15 - r5
            r3 = 18
            if (r11 >= r3) goto L_0x00c0
            long r12 = r12 * r17
            long r5 = (long) r15
            long r12 = r12 - r5
            goto L_0x00d6
        L_0x00c0:
            r5 = -922337203685477580(0xf333333333333334, double:-8.390303882365713E246)
            int r19 = (r12 > r5 ? 1 : (r12 == r5 ? 0 : -1))
            if (r19 >= 0) goto L_0x00ca
            r14 = 1
        L_0x00ca:
            long r12 = r12 * r17
            long r5 = (long) r15
            long r19 = r8 + r5
            int r15 = (r12 > r19 ? 1 : (r12 == r19 ? 0 : -1))
            if (r15 >= 0) goto L_0x00d4
            r14 = 1
        L_0x00d4:
            r15 = 0
            long r12 = r12 - r5
        L_0x00d6:
            int r5 = r1.np
            int r5 = r5 + r7
            r1.np = r5
            int r5 = r1.bp
            int r5 = r5 + r7
            r1.bp = r5
            int r6 = r1.len
            if (r5 < r6) goto L_0x00e7
            r6 = 26
            goto L_0x00ed
        L_0x00e7:
            java.lang.String r6 = r1.text
            char r6 = r6.charAt(r5)
        L_0x00ed:
            r1.ch = r6
            int r11 = r11 + 1
            r3 = 57
            r5 = 48
            goto L_0x00aa
        L_0x00f6:
            if (r4 != 0) goto L_0x00f9
            long r12 = -r12
        L_0x00f9:
            r11 = r12
            r13 = r14
            r3 = 1
            goto L_0x0167
        L_0x00fd:
            if (r4 != 0) goto L_0x0100
            long r11 = -r11
        L_0x0100:
            char r3 = r1.ch
            r5 = 76
            if (r3 != r5) goto L_0x0115
            int r3 = r1.np
            int r3 = r3 + r7
            r1.np = r3
            r22.next()
            java.lang.Long r16 = java.lang.Long.valueOf(r11)
        L_0x0112:
            r3 = 0
            r10 = 0
            goto L_0x0167
        L_0x0115:
            char r3 = r1.ch
            r5 = 83
            if (r3 != r5) goto L_0x012a
            int r3 = r1.np
            int r3 = r3 + r7
            r1.np = r3
            r22.next()
            int r3 = (int) r11
            short r3 = (short) r3
            java.lang.Short r16 = java.lang.Short.valueOf(r3)
            goto L_0x0112
        L_0x012a:
            char r3 = r1.ch
            r5 = 66
            if (r3 != r5) goto L_0x013f
            int r3 = r1.np
            int r3 = r3 + r7
            r1.np = r3
            r22.next()
            int r3 = (int) r11
            byte r3 = (byte) r3
            java.lang.Byte r16 = java.lang.Byte.valueOf(r3)
            goto L_0x0112
        L_0x013f:
            char r3 = r1.ch
            r5 = 70
            if (r3 != r5) goto L_0x0153
            int r3 = r1.np
            int r3 = r3 + r7
            r1.np = r3
            r22.next()
            float r3 = (float) r11
            java.lang.Float r16 = java.lang.Float.valueOf(r3)
            goto L_0x0112
        L_0x0153:
            char r3 = r1.ch
            r5 = 68
            if (r3 != r5) goto L_0x0112
            int r3 = r1.np
            int r3 = r3 + r7
            r1.np = r3
            r22.next()
            double r5 = (double) r11
            java.lang.Double r16 = java.lang.Double.valueOf(r5)
            goto L_0x0112
        L_0x0167:
            char r5 = r1.ch
            r6 = 101(0x65, float:1.42E-43)
            r8 = 43
            if (r5 == r6) goto L_0x017a
            char r5 = r1.ch
            r6 = 69
            if (r5 != r6) goto L_0x0176
            goto L_0x017a
        L_0x0176:
            r5 = 0
            r6 = 0
            goto L_0x01f7
        L_0x017a:
            int r5 = r1.np
            int r5 = r5 + r7
            r1.np = r5
            int r5 = r1.bp
            int r5 = r5 + r7
            r1.bp = r5
            int r6 = r1.len
            if (r5 < r6) goto L_0x018b
            r6 = 26
            goto L_0x0191
        L_0x018b:
            java.lang.String r6 = r1.text
            char r6 = r6.charAt(r5)
        L_0x0191:
            r1.ch = r6
            char r5 = r1.ch
            if (r5 == r8) goto L_0x019d
            char r5 = r1.ch
            r6 = 45
            if (r5 != r6) goto L_0x01b6
        L_0x019d:
            int r5 = r1.np
            int r5 = r5 + r7
            r1.np = r5
            int r5 = r1.bp
            int r5 = r5 + r7
            r1.bp = r5
            int r6 = r1.len
            if (r5 < r6) goto L_0x01ae
            r6 = 26
            goto L_0x01b4
        L_0x01ae:
            java.lang.String r6 = r1.text
            char r6 = r6.charAt(r5)
        L_0x01b4:
            r1.ch = r6
        L_0x01b6:
            char r5 = r1.ch
            r6 = 48
            if (r5 < r6) goto L_0x01dc
            char r5 = r1.ch
            r6 = 57
            if (r5 > r6) goto L_0x01dc
            int r5 = r1.np
            int r5 = r5 + r7
            r1.np = r5
            int r5 = r1.bp
            int r5 = r5 + r7
            r1.bp = r5
            int r9 = r1.len
            if (r5 < r9) goto L_0x01d3
            r5 = 26
            goto L_0x01d9
        L_0x01d3:
            java.lang.String r9 = r1.text
            char r5 = r9.charAt(r5)
        L_0x01d9:
            r1.ch = r5
            goto L_0x01b6
        L_0x01dc:
            char r5 = r1.ch
            r6 = 68
            if (r5 == r6) goto L_0x01eb
            char r5 = r1.ch
            r6 = 70
            if (r5 != r6) goto L_0x01e9
            goto L_0x01eb
        L_0x01e9:
            r5 = 0
            goto L_0x01f5
        L_0x01eb:
            int r5 = r1.np
            int r5 = r5 + r7
            r1.np = r5
            char r5 = r1.ch
            r22.next()
        L_0x01f5:
            r6 = r5
            r5 = 1
        L_0x01f7:
            if (r3 != 0) goto L_0x0231
            if (r5 != 0) goto L_0x0231
            if (r13 == 0) goto L_0x0216
            int r3 = r1.bp
            int r3 = r3 - r2
            char[] r3 = new char[r3]
            java.lang.String r4 = r1.text
            int r5 = r1.bp
            r6 = 0
            r4.getChars(r2, r5, r3, r6)
            java.lang.String r2 = new java.lang.String
            r2.<init>(r3)
            java.math.BigInteger r3 = new java.math.BigInteger
            r3.<init>(r2)
            r16 = r3
        L_0x0216:
            if (r16 != 0) goto L_0x0230
            r2 = -2147483648(0xffffffff80000000, double:NaN)
            int r2 = (r11 > r2 ? 1 : (r11 == r2 ? 0 : -1))
            if (r2 <= 0) goto L_0x022c
            r2 = 2147483647(0x7fffffff, double:1.060997895E-314)
            int r2 = (r11 > r2 ? 1 : (r11 == r2 ? 0 : -1))
            if (r2 >= 0) goto L_0x022c
            int r2 = (int) r11
            java.lang.Integer r16 = java.lang.Integer.valueOf(r2)
            goto L_0x0230
        L_0x022c:
            java.lang.Long r16 = java.lang.Long.valueOf(r11)
        L_0x0230:
            return r16
        L_0x0231:
            int r3 = r1.bp
            int r3 = r3 - r2
            if (r6 == 0) goto L_0x0238
            int r3 = r3 + -1
        L_0x0238:
            if (r5 != 0) goto L_0x0270
            int r9 = r1.features
            com.alibaba.fastjson.parser.Feature r14 = com.alibaba.fastjson.parser.Feature.UseBigDecimal
            int r14 = r14.mask
            r9 = r9 & r14
            if (r9 == 0) goto L_0x0270
            if (r13 != 0) goto L_0x024b
            java.math.BigDecimal r2 = java.math.BigDecimal.valueOf(r11, r10)
            goto L_0x02ee
        L_0x024b:
            char[] r4 = r1.sbuf
            int r4 = r4.length
            if (r3 >= r4) goto L_0x025d
            java.lang.String r4 = r1.text
            int r5 = r2 + r3
            char[] r6 = r1.sbuf
            r9 = 0
            r4.getChars(r2, r5, r6, r9)
            char[] r2 = r1.sbuf
            goto L_0x0268
        L_0x025d:
            r9 = 0
            char[] r4 = new char[r3]
            java.lang.String r5 = r1.text
            int r6 = r2 + r3
            r5.getChars(r2, r6, r4, r9)
            r2 = r4
        L_0x0268:
            java.math.BigDecimal r4 = new java.math.BigDecimal
            r4.<init>(r2, r9, r3)
            r2 = r4
            goto L_0x02ee
        L_0x0270:
            r9 = 0
            char[] r10 = r1.sbuf
            int r10 = r10.length
            if (r3 >= r10) goto L_0x0282
            java.lang.String r10 = r1.text
            int r11 = r2 + r3
            char[] r12 = r1.sbuf
            r10.getChars(r2, r11, r12, r9)
            char[] r2 = r1.sbuf
            goto L_0x028c
        L_0x0282:
            char[] r10 = new char[r3]
            java.lang.String r11 = r1.text
            int r12 = r2 + r3
            r11.getChars(r2, r12, r10, r9)
            r2 = r10
        L_0x028c:
            r10 = 9
            if (r3 > r10) goto L_0x02d7
            if (r5 != 0) goto L_0x02d7
            char r5 = r2[r9]     // Catch:{ NumberFormatException -> 0x02ef }
            r9 = 45
            if (r5 == r9) goto L_0x029f
            if (r5 != r8) goto L_0x029b
            goto L_0x029f
        L_0x029b:
            r8 = 1
        L_0x029c:
            r9 = 48
            goto L_0x02a5
        L_0x029f:
            r5 = 2
            char r8 = r2[r7]     // Catch:{ NumberFormatException -> 0x02ef }
            r5 = r8
            r8 = 2
            goto L_0x029c
        L_0x02a5:
            int r5 = r5 - r9
            r9 = 0
        L_0x02a7:
            if (r8 >= r3) goto L_0x02bd
            char r10 = r2[r8]     // Catch:{ NumberFormatException -> 0x02ef }
            r11 = 46
            if (r10 != r11) goto L_0x02b1
            r9 = 1
            goto L_0x02ba
        L_0x02b1:
            int r10 = r10 + -48
            int r5 = r5 * 10
            int r5 = r5 + r10
            if (r9 == 0) goto L_0x02ba
            int r9 = r9 * 10
        L_0x02ba:
            int r8 = r8 + 1
            goto L_0x02a7
        L_0x02bd:
            r2 = 70
            if (r6 != r2) goto L_0x02cc
            float r2 = (float) r5     // Catch:{ NumberFormatException -> 0x02ef }
            float r3 = (float) r9     // Catch:{ NumberFormatException -> 0x02ef }
            float r2 = r2 / r3
            if (r4 == 0) goto L_0x02c7
            float r2 = -r2
        L_0x02c7:
            java.lang.Float r2 = java.lang.Float.valueOf(r2)     // Catch:{ NumberFormatException -> 0x02ef }
            return r2
        L_0x02cc:
            double r2 = (double) r5     // Catch:{ NumberFormatException -> 0x02ef }
            double r5 = (double) r9     // Catch:{ NumberFormatException -> 0x02ef }
            double r2 = r2 / r5
            if (r4 == 0) goto L_0x02d2
            double r2 = -r2
        L_0x02d2:
            java.lang.Double r2 = java.lang.Double.valueOf(r2)     // Catch:{ NumberFormatException -> 0x02ef }
            return r2
        L_0x02d7:
            java.lang.String r4 = new java.lang.String     // Catch:{ NumberFormatException -> 0x02ef }
            r5 = 0
            r4.<init>(r2, r5, r3)     // Catch:{ NumberFormatException -> 0x02ef }
            r2 = 70
            if (r6 != r2) goto L_0x02e6
            java.lang.Float r2 = java.lang.Float.valueOf(r4)     // Catch:{ NumberFormatException -> 0x02ef }
            goto L_0x02ee
        L_0x02e6:
            double r2 = java.lang.Double.parseDouble(r4)     // Catch:{ NumberFormatException -> 0x02ef }
            java.lang.Double r2 = java.lang.Double.valueOf(r2)     // Catch:{ NumberFormatException -> 0x02ef }
        L_0x02ee:
            return r2
        L_0x02ef:
            r0 = move-exception
            r2 = r0
            com.alibaba.fastjson.JSONException r3 = new com.alibaba.fastjson.JSONException
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = r2.getMessage()
            r4.append(r5)
            java.lang.String r5 = ", "
            r4.append(r5)
            java.lang.String r5 = r22.info()
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            r3.<init>(r4, r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexer.scanNumberValue():java.lang.Number");
    }

    public final long scanLongValue() {
        boolean z;
        long j;
        char c;
        this.np = 0;
        if (this.ch == '-') {
            j = Long.MIN_VALUE;
            this.np++;
            int i = this.bp + 1;
            this.bp = i;
            if (i >= this.len) {
                StringBuilder sb = new StringBuilder("syntax error, ");
                sb.append(info());
                throw new JSONException(sb.toString());
            }
            this.ch = this.text.charAt(i);
            z = true;
        } else {
            j = -9223372036854775807L;
            z = false;
        }
        long j2 = 0;
        while (this.ch >= '0' && this.ch <= '9') {
            int i2 = this.ch - '0';
            if (j2 < -922337203685477580L) {
                StringBuilder sb2 = new StringBuilder("error long value, ");
                sb2.append(j2);
                sb2.append(", ");
                sb2.append(info());
                throw new JSONException(sb2.toString());
            }
            long j3 = j2 * 10;
            long j4 = (long) i2;
            if (j3 < j + j4) {
                StringBuilder sb3 = new StringBuilder("error long value, ");
                sb3.append(j3);
                sb3.append(", ");
                sb3.append(info());
                throw new JSONException(sb3.toString());
            }
            j2 = j3 - j4;
            this.np++;
            int i3 = this.bp + 1;
            this.bp = i3;
            if (i3 >= this.len) {
                c = EOI;
            } else {
                c = this.text.charAt(i3);
            }
            this.ch = c;
        }
        return !z ? -j2 : j2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x007b  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x008b  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0030  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final long longValue() throws java.lang.NumberFormatException {
        /*
            r13 = this;
            int r0 = r13.np
            int r1 = r13.np
            int r2 = r13.sp
            int r1 = r1 + r2
            int r2 = r13.np
            char r2 = r13.charAt(r2)
            r3 = 1
            r4 = 45
            if (r2 != r4) goto L_0x0018
            r4 = -9223372036854775808
            int r0 = r0 + 1
            r2 = 1
            goto L_0x001e
        L_0x0018:
            r4 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            r2 = 0
        L_0x001e:
            if (r0 >= r1) goto L_0x002c
            int r6 = r0 + 1
            char r0 = r13.charAt(r0)
            int r0 = r0 + -48
            int r0 = -r0
            long r7 = (long) r0
        L_0x002a:
            r0 = r6
            goto L_0x002e
        L_0x002c:
            r7 = 0
        L_0x002e:
            if (r0 >= r1) goto L_0x0079
            int r6 = r0 + 1
            int r9 = r13.len
            if (r0 < r9) goto L_0x0039
            r0 = 26
            goto L_0x003f
        L_0x0039:
            java.lang.String r9 = r13.text
            char r0 = r9.charAt(r0)
        L_0x003f:
            r9 = 76
            if (r0 == r9) goto L_0x0078
            r9 = 83
            if (r0 == r9) goto L_0x0078
            r9 = 66
            if (r0 == r9) goto L_0x0078
            int r0 = r0 + -48
            r9 = -922337203685477580(0xf333333333333334, double:-8.390303882365713E246)
            int r9 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r9 >= 0) goto L_0x0060
            java.lang.NumberFormatException r0 = new java.lang.NumberFormatException
            java.lang.String r1 = r13.numberString()
            r0.<init>(r1)
            throw r0
        L_0x0060:
            r9 = 10
            long r7 = r7 * r9
            long r9 = (long) r0
            long r11 = r4 + r9
            int r0 = (r7 > r11 ? 1 : (r7 == r11 ? 0 : -1))
            if (r0 >= 0) goto L_0x0075
            java.lang.NumberFormatException r0 = new java.lang.NumberFormatException
            java.lang.String r1 = r13.numberString()
            r0.<init>(r1)
            throw r0
        L_0x0075:
            r0 = 0
            long r7 = r7 - r9
            goto L_0x002a
        L_0x0078:
            r0 = r6
        L_0x0079:
            if (r2 == 0) goto L_0x008b
            int r1 = r13.np
            int r1 = r1 + r3
            if (r0 <= r1) goto L_0x0081
            return r7
        L_0x0081:
            java.lang.NumberFormatException r0 = new java.lang.NumberFormatException
            java.lang.String r1 = r13.numberString()
            r0.<init>(r1)
            throw r0
        L_0x008b:
            long r0 = -r7
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexer.longValue():long");
    }

    public final Number decimalValue(boolean z) {
        char c;
        char[] cArr;
        boolean z2;
        int i = (this.np + this.sp) - 1;
        if (i >= this.len) {
            c = EOI;
        } else {
            c = this.text.charAt(i);
        }
        if (c == 'F') {
            try {
                return Float.valueOf(Float.parseFloat(numberString()));
            } catch (NumberFormatException e) {
                StringBuilder sb = new StringBuilder();
                sb.append(e.getMessage());
                sb.append(", ");
                sb.append(info());
                throw new JSONException(sb.toString());
            }
        } else if (c == 'D') {
            return Double.valueOf(Double.parseDouble(numberString()));
        } else {
            if (z) {
                return decimalValue();
            }
            char charAt = this.text.charAt((this.np + this.sp) - 1);
            int i2 = this.sp;
            if (charAt == 'L' || charAt == 'S' || charAt == 'B' || charAt == 'F' || charAt == 'D') {
                i2--;
            }
            int i3 = this.np;
            int i4 = 0;
            if (i2 < this.sbuf.length) {
                this.text.getChars(i3, i3 + i2, this.sbuf, 0);
                cArr = this.sbuf;
            } else {
                char[] cArr2 = new char[i2];
                this.text.getChars(i3, i3 + i2, cArr2, 0);
                cArr = cArr2;
            }
            if (i2 > 9 || this.exp) {
                return Double.valueOf(Double.parseDouble(new String(cArr, 0, i2)));
            }
            char c2 = cArr[0];
            int i5 = 2;
            if (c2 == '-') {
                c2 = cArr[1];
                z2 = true;
            } else if (c2 == '+') {
                c2 = cArr[1];
                z2 = false;
            } else {
                z2 = false;
                i5 = 1;
            }
            int i6 = c2 - '0';
            while (i5 < i2) {
                char c3 = cArr[i5];
                if (c3 == '.') {
                    i4 = 1;
                } else {
                    i6 = (i6 * 10) + (c3 - '0');
                    if (i4 != 0) {
                        i4 *= 10;
                    }
                }
                i5++;
            }
            double d = ((double) i6) / ((double) i4);
            if (z2) {
                d = -d;
            }
            return Double.valueOf(d);
        }
    }

    public final BigDecimal decimalValue() {
        char charAt = this.text.charAt((this.np + this.sp) - 1);
        int i = this.sp;
        if (charAt == 'L' || charAt == 'S' || charAt == 'B' || charAt == 'F' || charAt == 'D') {
            i--;
        }
        int i2 = this.np;
        if (i < this.sbuf.length) {
            this.text.getChars(i2, i2 + i, this.sbuf, 0);
            return new BigDecimal(this.sbuf, 0, i);
        }
        char[] cArr = new char[i];
        this.text.getChars(i2, i + i2, cArr, 0);
        return new BigDecimal(cArr);
    }

    /* JADX WARNING: Removed duplicated region for block: B:75:0x0118  */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x011b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean matchField(long r19) {
        /*
            r18 = this;
            r0 = r18
            char r1 = r0.ch
            int r2 = r0.bp
            r3 = 1
            int r2 = r2 + r3
            r4 = 1
        L_0x0009:
            r5 = 34
            r6 = 0
            r7 = -2
            r8 = 8
            r9 = 9
            r10 = 13
            r11 = 10
            r12 = 12
            r13 = 32
            if (r1 == r5) goto L_0x0048
            r5 = 39
            if (r1 == r5) goto L_0x0048
            if (r1 > r13) goto L_0x0041
            if (r1 == r13) goto L_0x002d
            if (r1 == r11) goto L_0x002d
            if (r1 == r10) goto L_0x002d
            if (r1 == r9) goto L_0x002d
            if (r1 == r12) goto L_0x002d
            if (r1 != r8) goto L_0x0041
        L_0x002d:
            int r1 = r0.bp
            int r5 = r4 + 1
            int r1 = r1 + r4
            int r4 = r0.len
            if (r1 < r4) goto L_0x0039
            r1 = 26
            goto L_0x003f
        L_0x0039:
            java.lang.String r4 = r0.text
            char r1 = r4.charAt(r1)
        L_0x003f:
            r4 = r5
            goto L_0x0009
        L_0x0041:
            r1 = 0
            r0.fieldHash = r1
            r0.matchStat = r7
            return r6
        L_0x0048:
            r15 = -3750763034362895579(0xcbf29ce484222325, double:-7.302176725335867E57)
            r5 = r2
            r14 = r15
        L_0x004f:
            int r8 = r0.len
            if (r5 >= r8) goto L_0x0070
            java.lang.String r8 = r0.text
            char r8 = r8.charAt(r5)
            if (r8 != r1) goto L_0x005f
            int r5 = r5 - r2
            int r5 = r5 + r3
            int r4 = r4 + r5
            goto L_0x0070
        L_0x005f:
            long r9 = (long) r8
            long r8 = r14 ^ r9
            r14 = 1099511628211(0x100000001b3, double:5.43230922702E-312)
            long r14 = r14 * r8
            int r5 = r5 + 1
            r9 = 9
            r10 = 13
            goto L_0x004f
        L_0x0070:
            int r1 = (r14 > r19 ? 1 : (r14 == r19 ? 0 : -1))
            if (r1 == 0) goto L_0x0079
            r0.matchStat = r7
            r0.fieldHash = r14
            return r6
        L_0x0079:
            int r1 = r0.bp
            int r2 = r4 + 1
            int r1 = r1 + r4
            int r4 = r0.len
            if (r1 < r4) goto L_0x0085
            r14 = 26
            goto L_0x008b
        L_0x0085:
            java.lang.String r4 = r0.text
            char r14 = r4.charAt(r1)
        L_0x008b:
            r1 = 58
            if (r14 != r1) goto L_0x00f4
            int r1 = r0.bp
            int r1 = r1 + r2
            int r2 = r0.len
            if (r1 < r2) goto L_0x0099
            r14 = 26
            goto L_0x009f
        L_0x0099:
            java.lang.String r2 = r0.text
            char r14 = r2.charAt(r1)
        L_0x009f:
            r2 = 123(0x7b, float:1.72E-43)
            if (r14 != r2) goto L_0x00bc
            int r1 = r1 + r3
            r0.bp = r1
            int r1 = r0.bp
            int r2 = r0.len
            if (r1 < r2) goto L_0x00af
            r14 = 26
            goto L_0x00b7
        L_0x00af:
            java.lang.String r1 = r0.text
            int r2 = r0.bp
            char r14 = r1.charAt(r2)
        L_0x00b7:
            r0.ch = r14
            r0.token = r12
            goto L_0x00f3
        L_0x00bc:
            r2 = 91
            if (r14 != r2) goto L_0x00db
            int r1 = r1 + r3
            r0.bp = r1
            int r1 = r0.bp
            int r2 = r0.len
            if (r1 < r2) goto L_0x00cc
            r14 = 26
            goto L_0x00d4
        L_0x00cc:
            java.lang.String r1 = r0.text
            int r2 = r0.bp
            char r14 = r1.charAt(r2)
        L_0x00d4:
            r0.ch = r14
            r1 = 14
            r0.token = r1
            goto L_0x00f3
        L_0x00db:
            r0.bp = r1
            int r1 = r0.bp
            int r2 = r0.len
            if (r1 < r2) goto L_0x00e6
            r14 = 26
            goto L_0x00ee
        L_0x00e6:
            java.lang.String r1 = r0.text
            int r2 = r0.bp
            char r14 = r1.charAt(r2)
        L_0x00ee:
            r0.ch = r14
            r18.nextToken()
        L_0x00f3:
            return r3
        L_0x00f4:
            if (r14 > r13) goto L_0x0124
            if (r14 == r13) goto L_0x0109
            if (r14 == r11) goto L_0x0109
            r1 = 13
            if (r14 == r1) goto L_0x010b
            r4 = 9
            if (r14 == r4) goto L_0x010d
            if (r14 == r12) goto L_0x010d
            r5 = 8
            if (r14 != r5) goto L_0x0124
            goto L_0x010f
        L_0x0109:
            r1 = 13
        L_0x010b:
            r4 = 9
        L_0x010d:
            r5 = 8
        L_0x010f:
            int r6 = r0.bp
            int r7 = r2 + 1
            int r6 = r6 + r2
            int r2 = r0.len
            if (r6 < r2) goto L_0x011b
            r14 = 26
            goto L_0x0121
        L_0x011b:
            java.lang.String r2 = r0.text
            char r14 = r2.charAt(r6)
        L_0x0121:
            r2 = r7
            goto L_0x008b
        L_0x0124:
            com.alibaba.fastjson.JSONException r1 = new com.alibaba.fastjson.JSONException
            java.lang.String r2 = "match feild error expect ':'"
            r1.<init>(r2)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexer.matchField(long):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:50:0x00af  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00b2  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int matchFieldHash(long r18) {
        /*
            r17 = this;
            r0 = r17
            char r1 = r0.ch
            r2 = 1
            r3 = 1
        L_0x0006:
            r4 = 34
            r5 = 0
            r6 = -2
            r7 = 8
            r8 = 12
            r9 = 9
            r10 = 13
            r11 = 10
            r13 = 32
            if (r1 == r4) goto L_0x0044
            r4 = 39
            if (r1 == r4) goto L_0x0044
            if (r1 == r13) goto L_0x0030
            if (r1 == r11) goto L_0x0030
            if (r1 == r10) goto L_0x0030
            if (r1 == r9) goto L_0x0030
            if (r1 == r8) goto L_0x0030
            if (r1 != r7) goto L_0x0029
            goto L_0x0030
        L_0x0029:
            r1 = 0
            r0.fieldHash = r1
            r0.matchStat = r6
            return r5
        L_0x0030:
            int r1 = r0.bp
            int r4 = r3 + 1
            int r1 = r1 + r3
            int r3 = r0.len
            if (r1 < r3) goto L_0x003c
            r1 = 26
            goto L_0x0042
        L_0x003c:
            java.lang.String r3 = r0.text
            char r1 = r3.charAt(r1)
        L_0x0042:
            r3 = r4
            goto L_0x0006
        L_0x0044:
            r14 = -3750763034362895579(0xcbf29ce484222325, double:-7.302176725335867E57)
            int r4 = r0.bp
            int r4 = r4 + r3
        L_0x004c:
            int r12 = r0.len
            if (r4 >= r12) goto L_0x006e
            java.lang.String r12 = r0.text
            char r12 = r12.charAt(r4)
            if (r12 != r1) goto L_0x005e
            int r1 = r0.bp
            int r4 = r4 - r1
            int r4 = r4 - r3
            int r3 = r3 + r4
            goto L_0x006e
        L_0x005e:
            long r7 = (long) r12
            long r7 = r7 ^ r14
            r14 = 1099511628211(0x100000001b3, double:5.43230922702E-312)
            long r14 = r14 * r7
            int r4 = r4 + 1
            r7 = 8
            r8 = 12
            goto L_0x004c
        L_0x006e:
            r1 = r3
            int r3 = (r14 > r18 ? 1 : (r14 == r18 ? 0 : -1))
            if (r3 == 0) goto L_0x0078
            r0.fieldHash = r14
            r0.matchStat = r6
            return r5
        L_0x0078:
            int r3 = r0.bp
            int r1 = r1 + r2
            int r3 = r3 + r1
            int r4 = r0.len
            if (r3 < r4) goto L_0x0083
            r12 = 26
            goto L_0x0089
        L_0x0083:
            java.lang.String r4 = r0.text
            char r12 = r4.charAt(r3)
        L_0x0089:
            r3 = 58
            if (r12 != r3) goto L_0x008f
            int r1 = r1 + r2
            return r1
        L_0x008f:
            if (r12 > r13) goto L_0x00ba
            if (r12 == r13) goto L_0x00a2
            if (r12 == r11) goto L_0x00a2
            if (r12 == r10) goto L_0x00a2
            if (r12 == r9) goto L_0x00a2
            r3 = 12
            if (r12 == r3) goto L_0x00a4
            r4 = 8
            if (r12 != r4) goto L_0x00ba
            goto L_0x00a6
        L_0x00a2:
            r3 = 12
        L_0x00a4:
            r4 = 8
        L_0x00a6:
            int r5 = r0.bp
            int r6 = r1 + 1
            int r5 = r5 + r1
            int r1 = r0.len
            if (r5 < r1) goto L_0x00b2
            r12 = 26
            goto L_0x00b8
        L_0x00b2:
            java.lang.String r1 = r0.text
            char r12 = r1.charAt(r5)
        L_0x00b8:
            r1 = r6
            goto L_0x0089
        L_0x00ba:
            com.alibaba.fastjson.JSONException r1 = new com.alibaba.fastjson.JSONException
            java.lang.String r2 = "match feild error expect ':'"
            r1.<init>(r2)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexer.matchFieldHash(long):int");
    }

    public final int scanFieldInt(long j) {
        char c;
        int i;
        char c2;
        int i2;
        int i3;
        int charAt;
        char charAt2;
        char charAt3;
        this.matchStat = 0;
        int matchFieldHash = matchFieldHash(j);
        if (matchFieldHash == 0) {
            return 0;
        }
        int i4 = matchFieldHash + 1;
        int i5 = this.bp + matchFieldHash;
        int i6 = this.len;
        char c3 = EOI;
        if (i5 >= i6) {
            c = EOI;
        } else {
            c = this.text.charAt(i5);
        }
        boolean z = c == '\"';
        if (z) {
            int i7 = i4 + 1;
            int i8 = this.bp + i4;
            if (i8 >= this.len) {
                charAt3 = EOI;
            } else {
                charAt3 = this.text.charAt(i8);
            }
            i4 = i7;
            z = true;
        }
        boolean z2 = c == '-';
        if (z2) {
            int i9 = i4 + 1;
            int i10 = this.bp + i4;
            if (i10 >= this.len) {
                charAt2 = EOI;
            } else {
                charAt2 = this.text.charAt(i10);
            }
            i4 = i9;
        }
        if (c < '0' || c > '9') {
            this.matchStat = -1;
            return 0;
        }
        int i11 = c - '0';
        while (true) {
            i = i4 + 1;
            int i12 = this.bp + i4;
            if (i12 >= this.len) {
                c2 = EOI;
            } else {
                c2 = this.text.charAt(i12);
            }
            if (c2 >= '0' && c2 <= '9') {
                i11 = (i11 * 10) + (c2 - '0');
                i4 = i;
            }
        }
        if (c2 == '.') {
            this.matchStat = -1;
            return 0;
        }
        if (c2 != '\"') {
            i2 = c2;
            i3 = i;
        } else if (!z) {
            this.matchStat = -1;
            return 0;
        } else {
            i3 = i + 1;
            int i13 = this.bp + i;
            i2 = i13 >= this.len ? 26 : this.text.charAt(i13);
        }
        if (i11 < 0) {
            this.matchStat = -1;
            return 0;
        }
        while (i2 != 44) {
            if (i2 <= 32 && (i2 == 32 || i2 == 10 || i2 == 13 || i2 == 9 || i2 == 12 || i2 == 8)) {
                int i14 = i3 + 1;
                int i15 = this.bp + i3;
                if (i15 >= this.len) {
                    charAt = 26;
                } else {
                    charAt = this.text.charAt(i15);
                }
                i3 = i14;
            } else if (i2 == 125) {
                int i16 = i3 + 1;
                char charAt4 = charAt(this.bp + i3);
                if (charAt4 == ',') {
                    this.token = 16;
                    this.bp += i16 - 1;
                    int i17 = this.bp + 1;
                    this.bp = i17;
                    if (i17 < this.len) {
                        c3 = this.text.charAt(i17);
                    }
                    this.ch = c3;
                } else if (charAt4 == ']') {
                    this.token = 15;
                    this.bp += i16 - 1;
                    int i18 = this.bp + 1;
                    this.bp = i18;
                    if (i18 < this.len) {
                        c3 = this.text.charAt(i18);
                    }
                    this.ch = c3;
                } else if (charAt4 == '}') {
                    this.token = 13;
                    this.bp += i16 - 1;
                    int i19 = this.bp + 1;
                    this.bp = i19;
                    if (i19 < this.len) {
                        c3 = this.text.charAt(i19);
                    }
                    this.ch = c3;
                } else if (charAt4 == 26) {
                    this.token = 20;
                    this.bp += i16 - 1;
                    this.ch = EOI;
                } else {
                    this.matchStat = -1;
                    return 0;
                }
                this.matchStat = 4;
                if (z2) {
                    i11 = -i11;
                }
                return i11;
            } else {
                this.matchStat = -1;
                return 0;
            }
        }
        this.bp += i3 - 1;
        int i20 = this.bp + 1;
        this.bp = i20;
        if (i20 < this.len) {
            c3 = this.text.charAt(i20);
        }
        this.ch = c3;
        this.matchStat = 3;
        this.token = 16;
        if (z2) {
            i11 = -i11;
        }
        return i11;
    }

    public final int[] scanFieldIntArray(long j) {
        char c;
        char c2;
        int[] iArr;
        int i;
        int i2;
        char c3;
        int i3;
        boolean z;
        int[] iArr2;
        int i4;
        int i5;
        char c4;
        char c5;
        char charAt;
        this.matchStat = 0;
        int matchFieldHash = matchFieldHash(j);
        int[] iArr3 = null;
        if (matchFieldHash == 0) {
            return null;
        }
        int i6 = matchFieldHash + 1;
        int i7 = this.bp + matchFieldHash;
        if (i7 >= this.len) {
            c = EOI;
        } else {
            c = this.text.charAt(i7);
        }
        if (c != '[') {
            this.matchStat = -1;
            return null;
        }
        int i8 = i6 + 1;
        int i9 = this.bp + i6;
        if (i9 >= this.len) {
            c2 = EOI;
        } else {
            c2 = this.text.charAt(i9);
        }
        int[] iArr4 = new int[16];
        if (c2 == ']') {
            int i10 = i8 + 1;
            int i11 = this.bp + i8;
            if (i11 >= this.len) {
                c3 = EOI;
            } else {
                c3 = this.text.charAt(i11);
            }
            i = i10;
            i2 = 0;
            iArr = iArr4;
        } else {
            iArr = iArr4;
            int i12 = 0;
            while (true) {
                if (c2 == '-') {
                    i3 = i8 + 1;
                    int i13 = this.bp + i8;
                    if (i13 >= this.len) {
                        charAt = EOI;
                    } else {
                        charAt = this.text.charAt(i13);
                    }
                    z = true;
                } else {
                    i3 = i8;
                    z = false;
                }
                if (c2 >= '0') {
                    if (c2 > '9') {
                        i4 = -1;
                        iArr2 = null;
                        break;
                    }
                    int i14 = c2 - '0';
                    while (true) {
                        i5 = i3 + 1;
                        int i15 = this.bp + i3;
                        if (i15 >= this.len) {
                            c4 = EOI;
                        } else {
                            c4 = this.text.charAt(i15);
                        }
                        if (c4 >= '0' && c4 <= '9') {
                            i14 = (i14 * 10) + (c4 - '0');
                            i3 = i5;
                        }
                    }
                    if (i12 >= iArr.length) {
                        int[] iArr5 = new int[((iArr.length * 3) / 2)];
                        System.arraycopy(iArr, 0, iArr5, 0, i12);
                        iArr = iArr5;
                    }
                    i2 = i12 + 1;
                    if (z) {
                        i14 = -i14;
                    }
                    iArr[i12] = i14;
                    if (c4 == ',') {
                        i8 = i5 + 1;
                        int i16 = this.bp + i5;
                        if (i16 >= this.len) {
                            c5 = EOI;
                        } else {
                            c5 = this.text.charAt(i16);
                        }
                        i12 = i2;
                    } else if (c4 == ']') {
                        i = i5 + 1;
                        int i17 = this.bp + i5;
                        if (i17 >= this.len) {
                            c3 = EOI;
                        } else {
                            c3 = this.text.charAt(i17);
                        }
                    } else {
                        i12 = i2;
                        c5 = c4;
                        i8 = i5;
                    }
                    iArr3 = null;
                } else {
                    iArr2 = iArr3;
                    i4 = -1;
                    break;
                }
            }
            this.matchStat = i4;
            return iArr2;
        }
        if (i2 != iArr.length) {
            int[] iArr6 = new int[i2];
            System.arraycopy(iArr, 0, iArr6, 0, i2);
            iArr = iArr6;
        }
        if (c3 == ',') {
            this.bp += i - 1;
            next();
            this.matchStat = 3;
            this.token = 16;
            return iArr;
        } else if (c3 == '}') {
            int i18 = i + 1;
            char charAt2 = charAt(this.bp + i);
            if (charAt2 == ',') {
                this.token = 16;
                this.bp += i18 - 1;
                next();
            } else if (charAt2 == ']') {
                this.token = 15;
                this.bp += i18 - 1;
                next();
            } else if (charAt2 == '}') {
                this.token = 13;
                this.bp += i18 - 1;
                next();
            } else if (charAt2 == 26) {
                this.bp += i18 - 1;
                this.token = 20;
                this.ch = EOI;
            } else {
                this.matchStat = -1;
                return null;
            }
            this.matchStat = 4;
            return iArr;
        } else {
            this.matchStat = -1;
            return null;
        }
    }

    public final long scanFieldLong(long j) {
        char c;
        int i;
        char c2;
        char c3;
        char c4;
        char c5;
        char c6;
        char charAt;
        char charAt2;
        char charAt3;
        boolean z = false;
        this.matchStat = 0;
        int matchFieldHash = matchFieldHash(j);
        if (matchFieldHash == 0) {
            return 0;
        }
        int i2 = matchFieldHash + 1;
        int i3 = this.bp + matchFieldHash;
        if (i3 >= this.len) {
            c = EOI;
        } else {
            c = this.text.charAt(i3);
        }
        boolean z2 = c == '\"';
        if (z2) {
            int i4 = i2 + 1;
            int i5 = this.bp + i2;
            if (i5 >= this.len) {
                charAt3 = EOI;
            } else {
                charAt3 = this.text.charAt(i5);
            }
            i2 = i4;
        }
        if (c == '-') {
            z = true;
        }
        if (z) {
            int i6 = i2 + 1;
            int i7 = this.bp + i2;
            if (i7 >= this.len) {
                charAt2 = EOI;
            } else {
                charAt2 = this.text.charAt(i7);
            }
            i2 = i6;
        }
        if (c < '0' || c > '9') {
            this.matchStat = -1;
            return 0;
        }
        long j2 = (long) (c - '0');
        while (true) {
            i = i2 + 1;
            int i8 = this.bp + i2;
            if (i8 >= this.len) {
                c2 = EOI;
            } else {
                c2 = this.text.charAt(i8);
            }
            if (c2 >= '0' && c2 <= '9') {
                j2 = (j2 * 10) + ((long) (c2 - '0'));
                i2 = i;
            }
        }
        if (c2 == '.') {
            this.matchStat = -1;
            return 0;
        }
        if (c2 == '\"') {
            if (!z2) {
                this.matchStat = -1;
                return 0;
            }
            int i9 = i + 1;
            int i10 = this.bp + i;
            if (i10 >= this.len) {
                charAt = EOI;
            } else {
                charAt = this.text.charAt(i10);
            }
            i = i9;
        }
        if (j2 < 0) {
            this.matchStat = -1;
            return 0;
        } else if (c2 == ',') {
            this.bp += i - 1;
            int i11 = this.bp + 1;
            this.bp = i11;
            if (i11 >= this.len) {
                c6 = EOI;
            } else {
                c6 = this.text.charAt(i11);
            }
            this.ch = c6;
            this.matchStat = 3;
            this.token = 16;
            return z ? -j2 : j2;
        } else if (c2 == '}') {
            int i12 = i + 1;
            char charAt4 = charAt(this.bp + i);
            if (charAt4 == ',') {
                this.token = 16;
                this.bp += i12 - 1;
                int i13 = this.bp + 1;
                this.bp = i13;
                if (i13 >= this.len) {
                    c5 = EOI;
                } else {
                    c5 = this.text.charAt(i13);
                }
                this.ch = c5;
            } else if (charAt4 == ']') {
                this.token = 15;
                this.bp += i12 - 1;
                int i14 = this.bp + 1;
                this.bp = i14;
                if (i14 >= this.len) {
                    c4 = EOI;
                } else {
                    c4 = this.text.charAt(i14);
                }
                this.ch = c4;
            } else if (charAt4 == '}') {
                this.token = 13;
                this.bp += i12 - 1;
                int i15 = this.bp + 1;
                this.bp = i15;
                if (i15 >= this.len) {
                    c3 = EOI;
                } else {
                    c3 = this.text.charAt(i15);
                }
                this.ch = c3;
            } else if (charAt4 == 26) {
                this.token = 20;
                this.bp += i12 - 1;
                this.ch = EOI;
            } else {
                this.matchStat = -1;
                return 0;
            }
            this.matchStat = 4;
            return z ? -j2 : j2;
        } else {
            this.matchStat = -1;
            return 0;
        }
    }

    public final String scanFieldString(long j) {
        String str;
        char c;
        char c2;
        boolean z;
        this.matchStat = 0;
        int matchFieldHash = matchFieldHash(j);
        if (matchFieldHash == 0) {
            return null;
        }
        int i = matchFieldHash + 1;
        int i2 = this.bp + matchFieldHash;
        if (i2 >= this.len) {
            StringBuilder sb = new StringBuilder("unclosed str, ");
            sb.append(info());
            throw new JSONException(sb.toString());
        } else if (this.text.charAt(i2) != '\"') {
            this.matchStat = -1;
            return this.stringDefaultValue;
        } else {
            int i3 = this.bp + i;
            int indexOf = this.text.indexOf(34, i3);
            if (indexOf == -1) {
                StringBuilder sb2 = new StringBuilder("unclosed str, ");
                sb2.append(info());
                throw new JSONException(sb2.toString());
            }
            if (V6) {
                str = this.text.substring(i3, indexOf);
            } else {
                int i4 = indexOf - i3;
                str = new String(sub_chars(this.bp + i, i4), 0, i4);
            }
            if (str.indexOf(92) != -1) {
                boolean z2 = false;
                while (true) {
                    int i5 = indexOf - 1;
                    z = z2;
                    int i6 = 0;
                    while (i5 >= 0 && this.text.charAt(i5) == '\\') {
                        i6++;
                        i5--;
                        z = true;
                    }
                    if (i6 % 2 == 0) {
                        break;
                    }
                    indexOf = this.text.indexOf(34, indexOf + 1);
                    z2 = z;
                }
                int i7 = indexOf - i3;
                char[] sub_chars = sub_chars(this.bp + i, i7);
                if (z) {
                    str = readString(sub_chars, i7);
                } else {
                    str = new String(sub_chars, 0, i7);
                    if (str.indexOf(92) != -1) {
                        str = readString(sub_chars, i7);
                    }
                }
            }
            int i8 = indexOf + 1;
            int i9 = this.len;
            char c3 = EOI;
            if (i8 >= i9) {
                c = EOI;
            } else {
                c = this.text.charAt(i8);
            }
            if (c == ',') {
                this.bp = i8;
                int i10 = this.bp + 1;
                this.bp = i10;
                if (i10 < this.len) {
                    c3 = this.text.charAt(i10);
                }
                this.ch = c3;
                this.matchStat = 3;
                this.token = 16;
                return str;
            } else if (c == '}') {
                int i11 = i8 + 1;
                if (i11 >= this.len) {
                    c2 = EOI;
                } else {
                    c2 = this.text.charAt(i11);
                }
                if (c2 == ',') {
                    this.token = 16;
                    this.bp = i11;
                    next();
                } else if (c2 == ']') {
                    this.token = 15;
                    this.bp = i11;
                    next();
                } else if (c2 == '}') {
                    this.token = 13;
                    this.bp = i11;
                    next();
                } else if (c2 == 26) {
                    this.token = 20;
                    this.bp = i11;
                    this.ch = EOI;
                } else {
                    this.matchStat = -1;
                    return this.stringDefaultValue;
                }
                this.matchStat = 4;
                return str;
            } else {
                this.matchStat = -1;
                return this.stringDefaultValue;
            }
        }
    }

    public final Date scanFieldDate(long j) {
        char c;
        Date date;
        char c2;
        int i;
        int i2;
        char c3;
        char c4;
        this.matchStat = 0;
        int matchFieldHash = matchFieldHash(j);
        if (matchFieldHash == 0) {
            return null;
        }
        int i3 = this.bp;
        char c5 = this.ch;
        int i4 = matchFieldHash + 1;
        int i5 = this.bp + matchFieldHash;
        int i6 = this.len;
        char c6 = EOI;
        if (i5 >= i6) {
            c = EOI;
        } else {
            c = this.text.charAt(i5);
        }
        if (c == '\"') {
            int i7 = this.bp + i4;
            int i8 = i4 + 1;
            int i9 = this.bp + i4;
            if (i9 < this.len) {
                this.text.charAt(i9);
            }
            int indexOf = this.text.indexOf(34, this.bp + i8);
            if (indexOf == -1) {
                throw new JSONException("unclosed str");
            }
            int i10 = indexOf - i7;
            this.bp = i7;
            if (scanISO8601DateIfMatch(false, i10)) {
                date = this.calendar.getTime();
                int i11 = i8 + i10;
                i = i11 + 1;
                c2 = charAt(i11 + i3);
                this.bp = i3;
            } else {
                this.bp = i3;
                this.matchStat = -1;
                return null;
            }
        } else if (c < '0' || c > '9') {
            this.matchStat = -1;
            return null;
        } else {
            long j2 = (long) (c - '0');
            while (true) {
                i2 = i4 + 1;
                int i12 = this.bp + i4;
                if (i12 >= this.len) {
                    c3 = EOI;
                } else {
                    c3 = this.text.charAt(i12);
                }
                if (c3 >= '0' && c3 <= '9') {
                    j2 = (j2 * 10) + ((long) (c3 - '0'));
                    i4 = i2;
                }
            }
            if (c3 == '.') {
                this.matchStat = -1;
                return null;
            }
            if (c3 == '\"') {
                i = i2 + 1;
                int i13 = this.bp + i2;
                if (i13 >= this.len) {
                    c4 = EOI;
                } else {
                    c4 = this.text.charAt(i13);
                }
                c2 = c4;
            } else {
                c2 = c3;
                i = i2;
            }
            if (j2 < 0) {
                this.matchStat = -1;
                return null;
            }
            date = new Date(j2);
        }
        if (c2 == ',') {
            this.bp += i - 1;
            int i14 = this.bp + 1;
            this.bp = i14;
            if (i14 < this.len) {
                c6 = this.text.charAt(i14);
            }
            this.ch = c6;
            this.matchStat = 3;
            this.token = 16;
            return date;
        } else if (c2 == '}') {
            int i15 = i + 1;
            char charAt = charAt(this.bp + i);
            if (charAt == ',') {
                this.token = 16;
                this.bp += i15 - 1;
                int i16 = this.bp + 1;
                this.bp = i16;
                if (i16 < this.len) {
                    c6 = this.text.charAt(i16);
                }
                this.ch = c6;
            } else if (charAt == ']') {
                this.token = 15;
                this.bp += i15 - 1;
                int i17 = this.bp + 1;
                this.bp = i17;
                if (i17 < this.len) {
                    c6 = this.text.charAt(i17);
                }
                this.ch = c6;
            } else if (charAt == '}') {
                this.token = 13;
                this.bp += i15 - 1;
                int i18 = this.bp + 1;
                this.bp = i18;
                if (i18 < this.len) {
                    c6 = this.text.charAt(i18);
                }
                this.ch = c6;
            } else if (charAt == 26) {
                this.token = 20;
                this.bp += i15 - 1;
                this.ch = EOI;
            } else {
                this.bp = i3;
                this.ch = c5;
                this.matchStat = -1;
                return null;
            }
            this.matchStat = 4;
            return date;
        } else {
            this.bp = i3;
            this.ch = c5;
            this.matchStat = -1;
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x0098  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x009b  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00b7  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00c4  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean scanFieldBoolean(long r13) {
        /*
            r12 = this;
            r0 = 0
            r12.matchStat = r0
            int r13 = r12.matchFieldHash(r13)
            if (r13 != 0) goto L_0x000a
            return r0
        L_0x000a:
            java.lang.String r14 = r12.text
            java.lang.String r1 = "false"
            int r2 = r12.bp
            int r2 = r2 + r13
            boolean r14 = r14.startsWith(r1, r2)
            r1 = 4
            r2 = -1
            r3 = 3
            r4 = 1
            if (r14 == 0) goto L_0x0020
            int r13 = r13 + 5
        L_0x001d:
            r14 = 0
            goto L_0x008d
        L_0x0020:
            java.lang.String r14 = r12.text
            java.lang.String r5 = "true"
            int r6 = r12.bp
            int r6 = r6 + r13
            boolean r14 = r14.startsWith(r5, r6)
            if (r14 == 0) goto L_0x0031
            int r13 = r13 + r1
        L_0x002f:
            r14 = 1
            goto L_0x008d
        L_0x0031:
            java.lang.String r14 = r12.text
            java.lang.String r5 = "\"false\""
            int r6 = r12.bp
            int r6 = r6 + r13
            boolean r14 = r14.startsWith(r5, r6)
            if (r14 == 0) goto L_0x0041
            int r13 = r13 + 7
            goto L_0x001d
        L_0x0041:
            java.lang.String r14 = r12.text
            java.lang.String r5 = "\"true\""
            int r6 = r12.bp
            int r6 = r6 + r13
            boolean r14 = r14.startsWith(r5, r6)
            if (r14 == 0) goto L_0x0051
            int r13 = r13 + 6
            goto L_0x002f
        L_0x0051:
            java.lang.String r14 = r12.text
            int r5 = r12.bp
            int r5 = r5 + r13
            char r14 = r14.charAt(r5)
            r5 = 49
            if (r14 != r5) goto L_0x0060
            int r13 = r13 + r4
            goto L_0x002f
        L_0x0060:
            java.lang.String r14 = r12.text
            int r5 = r12.bp
            int r5 = r5 + r13
            char r14 = r14.charAt(r5)
            r5 = 48
            if (r14 != r5) goto L_0x006f
            int r13 = r13 + r4
            goto L_0x001d
        L_0x006f:
            java.lang.String r14 = r12.text
            java.lang.String r5 = "\"1\""
            int r6 = r12.bp
            int r6 = r6 + r13
            boolean r14 = r14.startsWith(r5, r6)
            if (r14 == 0) goto L_0x007e
            int r13 = r13 + r3
            goto L_0x002f
        L_0x007e:
            java.lang.String r14 = r12.text
            java.lang.String r5 = "\"0\""
            int r6 = r12.bp
            int r6 = r6 + r13
            boolean r14 = r14.startsWith(r5, r6)
            if (r14 == 0) goto L_0x0171
            int r13 = r13 + r3
            goto L_0x001d
        L_0x008d:
            int r5 = r12.bp
            int r6 = r13 + 1
            int r5 = r5 + r13
            int r13 = r12.len
            r7 = 26
            if (r5 < r13) goto L_0x009b
            r13 = 26
            goto L_0x00a1
        L_0x009b:
            java.lang.String r13 = r12.text
            char r13 = r13.charAt(r5)
        L_0x00a1:
            r5 = 16
            r8 = 44
            if (r13 != r8) goto L_0x00c4
            int r13 = r12.bp
            int r6 = r6 - r4
            int r13 = r13 + r6
            r12.bp = r13
            int r13 = r12.bp
            int r13 = r13 + r4
            r12.bp = r13
            int r0 = r12.len
            if (r13 < r0) goto L_0x00b7
            goto L_0x00bd
        L_0x00b7:
            java.lang.String r0 = r12.text
            char r7 = r0.charAt(r13)
        L_0x00bd:
            r12.ch = r7
            r12.matchStat = r3
            r12.token = r5
            return r14
        L_0x00c4:
            r9 = 13
            r10 = 125(0x7d, float:1.75E-43)
            if (r13 == r10) goto L_0x00f4
            r11 = 32
            if (r13 == r11) goto L_0x00e0
            r11 = 10
            if (r13 == r11) goto L_0x00e0
            if (r13 == r9) goto L_0x00e0
            r11 = 9
            if (r13 == r11) goto L_0x00e0
            r11 = 12
            if (r13 == r11) goto L_0x00e0
            r11 = 8
            if (r13 != r11) goto L_0x00f4
        L_0x00e0:
            int r13 = r12.bp
            int r5 = r6 + 1
            int r13 = r13 + r6
            int r6 = r12.len
            if (r13 < r6) goto L_0x00ec
            r13 = 26
            goto L_0x00f2
        L_0x00ec:
            java.lang.String r6 = r12.text
            char r13 = r6.charAt(r13)
        L_0x00f2:
            r6 = r5
            goto L_0x00a1
        L_0x00f4:
            if (r13 != r10) goto L_0x016e
            int r13 = r12.bp
            int r3 = r6 + 1
            int r13 = r13 + r6
            char r13 = r12.charAt(r13)
            if (r13 != r8) goto L_0x011c
            r12.token = r5
            int r13 = r12.bp
            int r3 = r3 - r4
            int r13 = r13 + r3
            r12.bp = r13
            int r13 = r12.bp
            int r13 = r13 + r4
            r12.bp = r13
            int r0 = r12.len
            if (r13 < r0) goto L_0x0113
            goto L_0x0119
        L_0x0113:
            java.lang.String r0 = r12.text
            char r7 = r0.charAt(r13)
        L_0x0119:
            r12.ch = r7
            goto L_0x0168
        L_0x011c:
            r5 = 93
            if (r13 != r5) goto L_0x013d
            r13 = 15
            r12.token = r13
            int r13 = r12.bp
            int r3 = r3 - r4
            int r13 = r13 + r3
            r12.bp = r13
            int r13 = r12.bp
            int r13 = r13 + r4
            r12.bp = r13
            int r0 = r12.len
            if (r13 < r0) goto L_0x0134
            goto L_0x013a
        L_0x0134:
            java.lang.String r0 = r12.text
            char r7 = r0.charAt(r13)
        L_0x013a:
            r12.ch = r7
            goto L_0x0168
        L_0x013d:
            if (r13 != r10) goto L_0x015a
            r12.token = r9
            int r13 = r12.bp
            int r3 = r3 - r4
            int r13 = r13 + r3
            r12.bp = r13
            int r13 = r12.bp
            int r13 = r13 + r4
            r12.bp = r13
            int r0 = r12.len
            if (r13 < r0) goto L_0x0151
            goto L_0x0157
        L_0x0151:
            java.lang.String r0 = r12.text
            char r7 = r0.charAt(r13)
        L_0x0157:
            r12.ch = r7
            goto L_0x0168
        L_0x015a:
            if (r13 != r7) goto L_0x016b
            r13 = 20
            r12.token = r13
            int r13 = r12.bp
            int r3 = r3 - r4
            int r13 = r13 + r3
            r12.bp = r13
            r12.ch = r7
        L_0x0168:
            r12.matchStat = r1
            return r14
        L_0x016b:
            r12.matchStat = r2
            return r0
        L_0x016e:
            r12.matchStat = r2
            return r0
        L_0x0171:
            r12.matchStat = r2
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexer.scanFieldBoolean(long):boolean");
    }

    public final float scanFieldFloat(long j) {
        int i;
        int i2;
        char charAt;
        char c;
        int i3;
        boolean z;
        JSONLexer jSONLexer;
        float f;
        int i4;
        boolean z2 = false;
        this.matchStat = 0;
        int matchFieldHash = matchFieldHash(j);
        if (matchFieldHash == 0) {
            return 0.0f;
        }
        int i5 = matchFieldHash + 1;
        char charAt2 = charAt(this.bp + matchFieldHash);
        int i6 = (this.bp + i5) - 1;
        boolean z3 = charAt2 == '-';
        if (z3) {
            charAt2 = charAt(this.bp + i5);
            i5++;
        }
        if (charAt2 < '0' || charAt2 > '9') {
            this.matchStat = -1;
            return 0.0f;
        }
        int i7 = charAt2 - '0';
        while (true) {
            i2 = i5 + 1;
            charAt = charAt(this.bp + i5);
            if (charAt >= '0' && charAt <= '9') {
                i7 = (i * 10) + (charAt - '0');
                i5 = i2;
            }
        }
        if (charAt == '.') {
            int i8 = i2 + 1;
            char charAt3 = charAt(this.bp + i2);
            if (charAt3 < '0' || charAt3 > '9') {
                this.matchStat = -1;
                return 0.0f;
            }
            i = (i * 10) + (charAt3 - '0');
            i3 = 10;
            while (true) {
                i4 = i8 + 1;
                c = charAt(this.bp + i8);
                if (c < '0' || c > '9') {
                    i2 = i4;
                } else {
                    i = (i * 10) + (c - '0');
                    i3 *= 10;
                    i8 = i4;
                }
            }
            i2 = i4;
        } else {
            c = charAt;
            i3 = 1;
        }
        if (c == 'e' || c == 'E') {
            z2 = true;
        }
        if (z2) {
            int i9 = i2 + 1;
            char charAt4 = charAt(this.bp + i2);
            if (charAt4 == '+' || charAt4 == '-') {
                z = z2;
                jSONLexer = this;
            } else {
                z = z2;
                jSONLexer = this;
                if (c < '0' || c > '9') {
                    i2 = i9;
                }
            }
            charAt4 = jSONLexer.charAt(jSONLexer.bp + i9);
            i9++;
            i2 = i9;
        } else {
            z = z2;
            jSONLexer = this;
        }
        int i10 = ((jSONLexer.bp + i2) - i6) - 1;
        if (z || i10 >= 10) {
            f = Float.parseFloat(jSONLexer.subString(i6, i10));
        } else {
            f = ((float) i) / ((float) i3);
            if (z3) {
                f = -f;
            }
        }
        if (c == ',') {
            jSONLexer.bp += i2 - 1;
            jSONLexer.next();
            jSONLexer.matchStat = 3;
            jSONLexer.token = 16;
            return f;
        } else if (c == '}') {
            int i11 = i2 + 1;
            char charAt5 = jSONLexer.charAt(jSONLexer.bp + i2);
            if (charAt5 == ',') {
                jSONLexer.token = 16;
                jSONLexer.bp += i11 - 1;
                jSONLexer.next();
            } else if (charAt5 == ']') {
                jSONLexer.token = 15;
                jSONLexer.bp += i11 - 1;
                jSONLexer.next();
            } else if (charAt5 == '}') {
                jSONLexer.token = 13;
                jSONLexer.bp += i11 - 1;
                jSONLexer.next();
            } else if (charAt5 == 26) {
                jSONLexer.bp += i11 - 1;
                jSONLexer.token = 20;
                jSONLexer.ch = EOI;
            } else {
                jSONLexer.matchStat = -1;
                return 0.0f;
            }
            jSONLexer.matchStat = 4;
            return f;
        } else {
            jSONLexer.matchStat = -1;
            return 0.0f;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:142:0x0217, code lost:
        return r5;
     */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x0116 A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final float[] scanFieldFloatArray(long r20) {
        /*
            r19 = this;
            r0 = r19
            r1 = 0
            r0.matchStat = r1
            int r2 = r19.matchFieldHash(r20)
            r3 = 0
            if (r2 != 0) goto L_0x000d
            return r3
        L_0x000d:
            int r4 = r0.bp
            int r5 = r2 + 1
            int r4 = r4 + r2
            int r2 = r0.len
            if (r4 < r2) goto L_0x0019
            r2 = 26
            goto L_0x001f
        L_0x0019:
            java.lang.String r2 = r0.text
            char r2 = r2.charAt(r4)
        L_0x001f:
            r4 = 91
            r7 = -1
            if (r2 == r4) goto L_0x0027
            r0.matchStat = r7
            return r3
        L_0x0027:
            int r2 = r0.bp
            int r4 = r5 + 1
            int r2 = r2 + r5
            int r5 = r0.len
            if (r2 < r5) goto L_0x0033
            r2 = 26
            goto L_0x0039
        L_0x0033:
            java.lang.String r5 = r0.text
            char r2 = r5.charAt(r2)
        L_0x0039:
            r5 = 16
            float[] r8 = new float[r5]
            r9 = 0
        L_0x003e:
            int r10 = r0.bp
            int r10 = r10 + r4
            r11 = 1
            int r10 = r10 - r11
            r12 = 45
            if (r2 != r12) goto L_0x0049
            r13 = 1
            goto L_0x004a
        L_0x0049:
            r13 = 0
        L_0x004a:
            if (r13 == 0) goto L_0x005f
            int r2 = r0.bp
            int r14 = r4 + 1
            int r2 = r2 + r4
            int r4 = r0.len
            if (r2 < r4) goto L_0x0058
            r2 = 26
            goto L_0x0060
        L_0x0058:
            java.lang.String r4 = r0.text
            char r2 = r4.charAt(r2)
            goto L_0x0060
        L_0x005f:
            r14 = r4
        L_0x0060:
            r4 = 48
            if (r2 < r4) goto L_0x0214
            r15 = 57
            if (r2 > r15) goto L_0x0214
            int r2 = r2 + -48
        L_0x006a:
            int r6 = r0.bp
            int r16 = r14 + 1
            int r6 = r6 + r14
            int r14 = r0.len
            if (r6 < r14) goto L_0x0076
            r6 = 26
            goto L_0x007c
        L_0x0076:
            java.lang.String r14 = r0.text
            char r6 = r14.charAt(r6)
        L_0x007c:
            if (r6 < r4) goto L_0x0088
            if (r6 > r15) goto L_0x0088
            int r2 = r2 * 10
            int r6 = r6 + -48
            int r2 = r2 + r6
            r14 = r16
            goto L_0x006a
        L_0x0088:
            r14 = 46
            if (r6 != r14) goto L_0x008e
            r14 = 1
            goto L_0x008f
        L_0x008e:
            r14 = 0
        L_0x008f:
            r5 = 10
            if (r14 == 0) goto L_0x00d4
            int r6 = r0.bp
            int r14 = r16 + 1
            int r6 = r6 + r16
            int r1 = r0.len
            if (r6 < r1) goto L_0x00a0
            r6 = 26
            goto L_0x00a6
        L_0x00a0:
            java.lang.String r1 = r0.text
            char r6 = r1.charAt(r6)
        L_0x00a6:
            if (r6 < r4) goto L_0x00d1
            if (r6 > r15) goto L_0x00d1
            int r2 = r2 * 10
            int r6 = r6 + -48
            int r2 = r2 + r6
            r1 = 10
        L_0x00b1:
            int r6 = r0.bp
            int r16 = r14 + 1
            int r6 = r6 + r14
            int r14 = r0.len
            if (r6 < r14) goto L_0x00bd
            r6 = 26
            goto L_0x00c3
        L_0x00bd:
            java.lang.String r14 = r0.text
            char r6 = r14.charAt(r6)
        L_0x00c3:
            if (r6 < r4) goto L_0x00d5
            if (r6 > r15) goto L_0x00d5
            int r2 = r2 * 10
            int r6 = r6 + -48
            int r2 = r2 + r6
            int r1 = r1 * 10
            r14 = r16
            goto L_0x00b1
        L_0x00d1:
            r0.matchStat = r7
            return r3
        L_0x00d4:
            r1 = 1
        L_0x00d5:
            r14 = 101(0x65, float:1.42E-43)
            if (r6 == r14) goto L_0x00e0
            r14 = 69
            if (r6 != r14) goto L_0x00de
            goto L_0x00e0
        L_0x00de:
            r14 = 0
            goto L_0x00e1
        L_0x00e0:
            r14 = 1
        L_0x00e1:
            if (r14 == 0) goto L_0x012d
            int r6 = r0.bp
            int r17 = r16 + 1
            int r6 = r6 + r16
            int r3 = r0.len
            if (r6 < r3) goto L_0x00f0
            r6 = 26
            goto L_0x00f6
        L_0x00f0:
            java.lang.String r3 = r0.text
            char r6 = r3.charAt(r6)
        L_0x00f6:
            r3 = 43
            if (r6 == r3) goto L_0x00ff
            if (r6 != r12) goto L_0x00fd
            goto L_0x00ff
        L_0x00fd:
            r3 = r6
            goto L_0x0114
        L_0x00ff:
            int r3 = r0.bp
            int r6 = r17 + 1
            int r3 = r3 + r17
            int r12 = r0.len
            if (r3 < r12) goto L_0x010c
        L_0x0109:
            r3 = 26
            goto L_0x0112
        L_0x010c:
            java.lang.String r12 = r0.text
            char r3 = r12.charAt(r3)
        L_0x0112:
            r17 = r6
        L_0x0114:
            if (r3 < r4) goto L_0x012a
            if (r3 > r15) goto L_0x012a
            int r3 = r0.bp
            int r6 = r17 + 1
            int r3 = r3 + r17
            int r12 = r0.len
            if (r3 < r12) goto L_0x0123
            goto L_0x0109
        L_0x0123:
            java.lang.String r12 = r0.text
            char r3 = r12.charAt(r3)
            goto L_0x0112
        L_0x012a:
            r4 = r17
            goto L_0x0130
        L_0x012d:
            r3 = r6
            r4 = r16
        L_0x0130:
            int r6 = r0.bp
            int r6 = r6 + r4
            int r6 = r6 - r10
            int r6 = r6 - r11
            if (r14 != 0) goto L_0x0140
            if (r6 >= r5) goto L_0x0140
            float r2 = (float) r2
            float r1 = (float) r1
            float r2 = r2 / r1
            if (r13 == 0) goto L_0x0148
            float r2 = -r2
            goto L_0x0148
        L_0x0140:
            java.lang.String r1 = r0.subString(r10, r6)
            float r2 = java.lang.Float.parseFloat(r1)
        L_0x0148:
            int r1 = r8.length
            r5 = 3
            if (r9 < r1) goto L_0x0158
            int r1 = r8.length
            int r1 = r1 * 3
            int r1 = r1 / 2
            float[] r1 = new float[r1]
            r6 = 0
            java.lang.System.arraycopy(r8, r6, r1, r6, r9)
            r8 = r1
        L_0x0158:
            int r1 = r9 + 1
            r8[r9] = r2
            r2 = 44
            if (r3 != r2) goto L_0x0177
            int r2 = r0.bp
            int r3 = r4 + 1
            int r2 = r2 + r4
            int r4 = r0.len
            if (r2 < r4) goto L_0x016c
            r2 = 26
            goto L_0x0173
        L_0x016c:
            java.lang.String r4 = r0.text
            char r6 = r4.charAt(r2)
            r2 = r6
        L_0x0173:
            r9 = r1
            r4 = r3
            goto L_0x020e
        L_0x0177:
            r6 = 93
            if (r3 != r6) goto L_0x020c
            int r3 = r0.bp
            int r9 = r4 + 1
            int r3 = r3 + r4
            int r4 = r0.len
            if (r3 < r4) goto L_0x0187
            r3 = 26
            goto L_0x018d
        L_0x0187:
            java.lang.String r4 = r0.text
            char r3 = r4.charAt(r3)
        L_0x018d:
            int r4 = r8.length
            if (r1 == r4) goto L_0x0197
            float[] r4 = new float[r1]
            r10 = 0
            java.lang.System.arraycopy(r8, r10, r4, r10, r1)
            goto L_0x0198
        L_0x0197:
            r4 = r8
        L_0x0198:
            if (r3 != r2) goto L_0x01aa
            int r1 = r0.bp
            int r9 = r9 - r11
            int r1 = r1 + r9
            r0.bp = r1
            r19.next()
            r0.matchStat = r5
            r1 = 16
            r0.token = r1
            return r4
        L_0x01aa:
            r1 = 125(0x7d, float:1.75E-43)
            if (r3 != r1) goto L_0x0208
            int r3 = r0.bp
            int r5 = r9 + 1
            int r3 = r3 + r9
            int r8 = r0.len
            if (r3 < r8) goto L_0x01ba
            r3 = 26
            goto L_0x01c0
        L_0x01ba:
            java.lang.String r8 = r0.text
            char r3 = r8.charAt(r3)
        L_0x01c0:
            if (r3 != r2) goto L_0x01d0
            r2 = 16
            r0.token = r2
            int r1 = r0.bp
            int r5 = r5 - r11
            int r1 = r1 + r5
            r0.bp = r1
            r19.next()
            goto L_0x0200
        L_0x01d0:
            if (r3 != r6) goto L_0x01e0
            r1 = 15
            r0.token = r1
            int r1 = r0.bp
            int r5 = r5 - r11
            int r1 = r1 + r5
            r0.bp = r1
            r19.next()
            goto L_0x0200
        L_0x01e0:
            if (r3 != r1) goto L_0x01f0
            r1 = 13
            r0.token = r1
            int r1 = r0.bp
            int r5 = r5 - r11
            int r1 = r1 + r5
            r0.bp = r1
            r19.next()
            goto L_0x0200
        L_0x01f0:
            r6 = 26
            if (r3 != r6) goto L_0x0204
            int r1 = r0.bp
            int r5 = r5 - r11
            int r1 = r1 + r5
            r0.bp = r1
            r1 = 20
            r0.token = r1
            r0.ch = r6
        L_0x0200:
            r1 = 4
            r0.matchStat = r1
            return r4
        L_0x0204:
            r0.matchStat = r7
            r5 = 0
            return r5
        L_0x0208:
            r5 = 0
            r0.matchStat = r7
            return r5
        L_0x020c:
            r9 = r1
            r2 = r3
        L_0x020e:
            r1 = 0
            r3 = 0
            r5 = 16
            goto L_0x003e
        L_0x0214:
            r5 = r3
            r0.matchStat = r7
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexer.scanFieldFloatArray(long):float[]");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:107:0x0189, code lost:
        r11 = r10 + 1;
        r1 = r0.bp + r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:108:0x0190, code lost:
        if (r1 < r0.len) goto L_0x0195;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:109:0x0192, code lost:
        r1 = EOI;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:110:0x0195, code lost:
        r1 = r0.text.charAt(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:112:0x019c, code lost:
        if (r3 == r9.length) goto L_0x01a6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:113:0x019e, code lost:
        r10 = new float[r3];
        r12 = 0;
        java.lang.System.arraycopy(r9, 0, r10, 0, r3);
        r9 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:114:0x01a6, code lost:
        r12 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:116:0x01a8, code lost:
        if (r5 < r8.length) goto L_0x01b4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:117:0x01aa, code lost:
        r8 = new float[((r8.length * 3) / 2)][];
        java.lang.System.arraycopy(r9, r12, r8, r12, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:118:0x01b4, code lost:
        r3 = r5 + 1;
        r8[r5] = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:119:0x01b8, code lost:
        if (r1 != ',') goto L_0x01cf;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:120:0x01ba, code lost:
        r4 = r11 + 1;
        r1 = r0.bp + r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:121:0x01c1, code lost:
        if (r1 >= r0.len) goto L_0x01c8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:122:0x01c3, code lost:
        r0.text.charAt(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:123:0x01c8, code lost:
        r5 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:125:0x01cf, code lost:
        if (r1 != ']') goto L_0x025b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:126:0x01d1, code lost:
        r5 = r11 + 1;
        r1 = r0.bp + r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:127:0x01d8, code lost:
        if (r1 < r0.len) goto L_0x01dd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:128:0x01da, code lost:
        r1 = EOI;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:129:0x01dd, code lost:
        r1 = r0.text.charAt(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:131:0x01e4, code lost:
        if (r3 == r8.length) goto L_0x01ed;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:132:0x01e6, code lost:
        r9 = new float[r3][];
        java.lang.System.arraycopy(r8, 0, r9, 0, r3);
        r8 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:133:0x01ed, code lost:
        if (r1 != ',') goto L_0x0200;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:134:0x01ef, code lost:
        r0.bp += r5 - 1;
        next();
        r0.matchStat = 3;
        r0.token = 16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:135:0x01ff, code lost:
        return r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:137:0x0204, code lost:
        if (r1 != '}') goto L_0x0257;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:138:0x0206, code lost:
        r9 = r5 + 1;
        r1 = charAt(r0.bp + r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:139:0x020f, code lost:
        if (r1 != ',') goto L_0x021e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:140:0x0211, code lost:
        r0.token = 16;
        r0.bp += r9 - 1;
        next();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:142:0x021f, code lost:
        if (r1 != ']') goto L_0x022f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:143:0x0221, code lost:
        r0.token = 15;
        r0.bp += r9 - 1;
        next();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:144:0x022f, code lost:
        if (r1 != '}') goto L_0x023f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:145:0x0231, code lost:
        r0.token = 13;
        r0.bp += r9 - 1;
        next();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:147:0x0241, code lost:
        if (r1 != 26) goto L_0x0253;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:148:0x0243, code lost:
        r0.bp += r9 - 1;
        r0.token = 20;
        r0.ch = EOI;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:149:0x024f, code lost:
        r0.matchStat = 4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:150:0x0252, code lost:
        return r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:151:0x0253, code lost:
        r0.matchStat = -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:152:0x0256, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:153:0x0257, code lost:
        r0.matchStat = -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:154:0x025a, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:155:0x025b, code lost:
        r5 = r3;
        r4 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:159:0x026a, code lost:
        return r2;
     */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x0123 A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final float[][] scanFieldFloatArray2(long r20) {
        /*
            r19 = this;
            r0 = r19
            r1 = 0
            r0.matchStat = r1
            int r2 = r19.matchFieldHash(r20)
            r3 = 0
            if (r2 != 0) goto L_0x000d
            return r3
        L_0x000d:
            int r4 = r0.bp
            int r5 = r2 + 1
            int r4 = r4 + r2
            int r2 = r0.len
            if (r4 < r2) goto L_0x0019
            r2 = 26
            goto L_0x001f
        L_0x0019:
            java.lang.String r2 = r0.text
            char r2 = r2.charAt(r4)
        L_0x001f:
            r4 = 91
            r7 = -1
            if (r2 == r4) goto L_0x0027
            r0.matchStat = r7
            return r3
        L_0x0027:
            int r2 = r0.bp
            int r4 = r5 + 1
            int r2 = r2 + r5
            int r5 = r0.len
            if (r2 >= r5) goto L_0x0035
            java.lang.String r5 = r0.text
            r5.charAt(r2)
        L_0x0035:
            r2 = 16
            float[][] r5 = new float[r2][]
            r8 = r5
            r5 = 0
        L_0x003b:
            int r9 = r0.bp
            int r10 = r4 + 1
            int r9 = r9 + r4
            int r4 = r0.len
            if (r9 < r4) goto L_0x0047
            r4 = 26
            goto L_0x004d
        L_0x0047:
            java.lang.String r4 = r0.text
            char r4 = r4.charAt(r9)
        L_0x004d:
            float[] r9 = new float[r2]
            r11 = 0
        L_0x0050:
            int r12 = r0.bp
            int r12 = r12 + r10
            r13 = 1
            int r12 = r12 - r13
            r14 = 45
            if (r4 != r14) goto L_0x005b
            r15 = 1
            goto L_0x005c
        L_0x005b:
            r15 = 0
        L_0x005c:
            if (r15 == 0) goto L_0x0071
            int r4 = r0.bp
            int r16 = r10 + 1
            int r4 = r4 + r10
            int r10 = r0.len
            if (r4 < r10) goto L_0x006a
            r4 = 26
            goto L_0x0073
        L_0x006a:
            java.lang.String r10 = r0.text
            char r4 = r10.charAt(r4)
            goto L_0x0073
        L_0x0071:
            r16 = r10
        L_0x0073:
            r10 = 48
            if (r4 < r10) goto L_0x0267
            r6 = 57
            if (r4 > r6) goto L_0x0267
            int r4 = r4 + -48
        L_0x007d:
            int r2 = r0.bp
            int r17 = r16 + 1
            int r2 = r2 + r16
            int r1 = r0.len
            if (r2 < r1) goto L_0x008a
            r1 = 26
            goto L_0x0090
        L_0x008a:
            java.lang.String r1 = r0.text
            char r1 = r1.charAt(r2)
        L_0x0090:
            if (r1 < r10) goto L_0x009d
            if (r1 > r6) goto L_0x009d
            int r4 = r4 * 10
            int r1 = r1 + -48
            int r4 = r4 + r1
            r16 = r17
            r1 = 0
            goto L_0x007d
        L_0x009d:
            r2 = 46
            if (r1 != r2) goto L_0x00e2
            int r1 = r0.bp
            int r2 = r17 + 1
            int r1 = r1 + r17
            int r13 = r0.len
            if (r1 < r13) goto L_0x00ae
            r1 = 26
            goto L_0x00b4
        L_0x00ae:
            java.lang.String r13 = r0.text
            char r1 = r13.charAt(r1)
        L_0x00b4:
            if (r1 < r10) goto L_0x00df
            if (r1 > r6) goto L_0x00df
            int r4 = r4 * 10
            int r1 = r1 + -48
            int r4 = r4 + r1
            r13 = 10
        L_0x00bf:
            int r1 = r0.bp
            int r16 = r2 + 1
            int r1 = r1 + r2
            int r2 = r0.len
            if (r1 < r2) goto L_0x00cb
            r1 = 26
            goto L_0x00d1
        L_0x00cb:
            java.lang.String r2 = r0.text
            char r1 = r2.charAt(r1)
        L_0x00d1:
            if (r1 < r10) goto L_0x00e5
            if (r1 > r6) goto L_0x00e5
            int r4 = r4 * 10
            int r1 = r1 + -48
            int r4 = r4 + r1
            int r13 = r13 * 10
            r2 = r16
            goto L_0x00bf
        L_0x00df:
            r0.matchStat = r7
            return r3
        L_0x00e2:
            r16 = r17
            r13 = 1
        L_0x00e5:
            r2 = 101(0x65, float:1.42E-43)
            if (r1 == r2) goto L_0x00f0
            r2 = 69
            if (r1 != r2) goto L_0x00ee
            goto L_0x00f0
        L_0x00ee:
            r2 = 0
            goto L_0x00f1
        L_0x00f0:
            r2 = 1
        L_0x00f1:
            if (r2 == 0) goto L_0x013a
            int r1 = r0.bp
            int r17 = r16 + 1
            int r1 = r1 + r16
            int r3 = r0.len
            if (r1 < r3) goto L_0x0100
            r1 = 26
            goto L_0x0106
        L_0x0100:
            java.lang.String r3 = r0.text
            char r1 = r3.charAt(r1)
        L_0x0106:
            r3 = 43
            if (r1 == r3) goto L_0x010c
            if (r1 != r14) goto L_0x0121
        L_0x010c:
            int r1 = r0.bp
            int r3 = r17 + 1
            int r1 = r1 + r17
            int r14 = r0.len
            if (r1 < r14) goto L_0x0119
        L_0x0116:
            r1 = 26
            goto L_0x011f
        L_0x0119:
            java.lang.String r14 = r0.text
            char r1 = r14.charAt(r1)
        L_0x011f:
            r17 = r3
        L_0x0121:
            if (r1 < r10) goto L_0x0137
            if (r1 > r6) goto L_0x0137
            int r1 = r0.bp
            int r3 = r17 + 1
            int r1 = r1 + r17
            int r14 = r0.len
            if (r1 < r14) goto L_0x0130
            goto L_0x0116
        L_0x0130:
            java.lang.String r14 = r0.text
            char r1 = r14.charAt(r1)
            goto L_0x011f
        L_0x0137:
            r10 = r17
            goto L_0x013c
        L_0x013a:
            r10 = r16
        L_0x013c:
            int r3 = r0.bp
            int r3 = r3 + r10
            int r3 = r3 - r12
            r6 = 1
            int r3 = r3 - r6
            if (r2 != 0) goto L_0x014f
            r2 = 10
            if (r3 >= r2) goto L_0x014f
            float r2 = (float) r4
            float r3 = (float) r13
            float r2 = r2 / r3
            if (r15 == 0) goto L_0x0157
            float r2 = -r2
            goto L_0x0157
        L_0x014f:
            java.lang.String r2 = r0.subString(r12, r3)
            float r2 = java.lang.Float.parseFloat(r2)
        L_0x0157:
            int r3 = r9.length
            r4 = 3
            if (r11 < r3) goto L_0x0167
            int r3 = r9.length
            int r3 = r3 * 3
            int r3 = r3 / 2
            float[] r3 = new float[r3]
            r6 = 0
            java.lang.System.arraycopy(r9, r6, r3, r6, r11)
            r9 = r3
        L_0x0167:
            int r3 = r11 + 1
            r9[r11] = r2
            r2 = 44
            if (r1 != r2) goto L_0x0185
            int r1 = r0.bp
            int r2 = r10 + 1
            int r1 = r1 + r10
            int r4 = r0.len
            if (r1 < r4) goto L_0x017b
            r4 = 26
            goto L_0x0182
        L_0x017b:
            java.lang.String r4 = r0.text
            char r6 = r4.charAt(r1)
            r4 = r6
        L_0x0182:
            r10 = r2
            goto L_0x0260
        L_0x0185:
            r6 = 93
            if (r1 != r6) goto L_0x025f
            int r1 = r0.bp
            int r11 = r10 + 1
            int r1 = r1 + r10
            int r10 = r0.len
            if (r1 < r10) goto L_0x0195
            r1 = 26
            goto L_0x019b
        L_0x0195:
            java.lang.String r10 = r0.text
            char r1 = r10.charAt(r1)
        L_0x019b:
            int r10 = r9.length
            if (r3 == r10) goto L_0x01a6
            float[] r10 = new float[r3]
            r12 = 0
            java.lang.System.arraycopy(r9, r12, r10, r12, r3)
            r9 = r10
            goto L_0x01a7
        L_0x01a6:
            r12 = 0
        L_0x01a7:
            int r10 = r8.length
            if (r5 < r10) goto L_0x01b4
            int r8 = r8.length
            int r8 = r8 * 3
            int r8 = r8 / 2
            float[][] r8 = new float[r8][]
            java.lang.System.arraycopy(r9, r12, r8, r12, r3)
        L_0x01b4:
            int r3 = r5 + 1
            r8[r5] = r9
            if (r1 != r2) goto L_0x01cf
            int r1 = r0.bp
            int r4 = r11 + 1
            int r1 = r1 + r11
            int r2 = r0.len
            if (r1 >= r2) goto L_0x01c8
            java.lang.String r2 = r0.text
            r2.charAt(r1)
        L_0x01c8:
            r5 = r3
        L_0x01c9:
            r1 = 0
            r2 = 16
            r3 = 0
            goto L_0x003b
        L_0x01cf:
            if (r1 != r6) goto L_0x025b
            int r1 = r0.bp
            int r5 = r11 + 1
            int r1 = r1 + r11
            int r9 = r0.len
            if (r1 < r9) goto L_0x01dd
            r1 = 26
            goto L_0x01e3
        L_0x01dd:
            java.lang.String r9 = r0.text
            char r1 = r9.charAt(r1)
        L_0x01e3:
            int r9 = r8.length
            if (r3 == r9) goto L_0x01ed
            float[][] r9 = new float[r3][]
            r12 = 0
            java.lang.System.arraycopy(r8, r12, r9, r12, r3)
            r8 = r9
        L_0x01ed:
            if (r1 != r2) goto L_0x0200
            int r1 = r0.bp
            r2 = 1
            int r5 = r5 - r2
            int r1 = r1 + r5
            r0.bp = r1
            r19.next()
            r0.matchStat = r4
            r4 = 16
            r0.token = r4
            return r8
        L_0x0200:
            r4 = 16
            r3 = 125(0x7d, float:1.75E-43)
            if (r1 != r3) goto L_0x0257
            int r1 = r0.bp
            int r9 = r5 + 1
            int r1 = r1 + r5
            char r1 = r0.charAt(r1)
            if (r1 != r2) goto L_0x021e
            r0.token = r4
            int r1 = r0.bp
            r2 = 1
            int r9 = r9 - r2
            int r1 = r1 + r9
            r0.bp = r1
            r19.next()
            goto L_0x024f
        L_0x021e:
            r2 = 1
            if (r1 != r6) goto L_0x022f
            r1 = 15
            r0.token = r1
            int r1 = r0.bp
            int r9 = r9 - r2
            int r1 = r1 + r9
            r0.bp = r1
            r19.next()
            goto L_0x024f
        L_0x022f:
            if (r1 != r3) goto L_0x023f
            r1 = 13
            r0.token = r1
            int r1 = r0.bp
            int r9 = r9 - r2
            int r1 = r1 + r9
            r0.bp = r1
            r19.next()
            goto L_0x024f
        L_0x023f:
            r6 = 26
            if (r1 != r6) goto L_0x0253
            int r1 = r0.bp
            int r9 = r9 - r2
            int r1 = r1 + r9
            r0.bp = r1
            r1 = 20
            r0.token = r1
            r0.ch = r6
        L_0x024f:
            r1 = 4
            r0.matchStat = r1
            return r8
        L_0x0253:
            r0.matchStat = r7
            r2 = 0
            return r2
        L_0x0257:
            r2 = 0
            r0.matchStat = r7
            return r2
        L_0x025b:
            r5 = r3
            r4 = r11
            goto L_0x01c9
        L_0x025f:
            r4 = r1
        L_0x0260:
            r11 = r3
            r1 = 0
            r2 = 16
            r3 = 0
            goto L_0x0050
        L_0x0267:
            r2 = r3
            r0.matchStat = r7
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexer.scanFieldFloatArray2(long):float[][]");
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Regions count limit reached
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:89)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:690)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:695)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:695)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
        	at jadx.core.ProcessClass.process(ProcessClass.java:30)
        	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
        	at jadx.core.ProcessClass.process(ProcessClass.java:35)
        	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
        	at jadx.api.JavaClass.decompile(JavaClass.java:62)
        	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
        */
    public final double scanFieldDouble(long r19) {
        /*
            r18 = this;
            r0 = r18
            r1 = 0
            r0.matchStat = r1
            int r2 = r18.matchFieldHash(r19)
            r3 = 0
            if (r2 != 0) goto L_0x000e
            return r3
        L_0x000e:
            int r5 = r0.bp
            int r6 = r2 + 1
            int r5 = r5 + r2
            char r2 = r0.charAt(r5)
            int r5 = r0.bp
            int r5 = r5 + r6
            r7 = 1
            int r5 = r5 - r7
            r8 = 45
            if (r2 != r8) goto L_0x0022
            r9 = 1
            goto L_0x0023
        L_0x0022:
            r9 = 0
        L_0x0023:
            if (r9 == 0) goto L_0x002f
            int r2 = r0.bp
            int r10 = r6 + 1
            int r2 = r2 + r6
            char r2 = r0.charAt(r2)
            r6 = r10
        L_0x002f:
            r10 = -1
            r11 = 48
            if (r2 < r11) goto L_0x0146
            r12 = 57
            if (r2 > r12) goto L_0x0146
            int r2 = r2 - r11
        L_0x0039:
            int r13 = r0.bp
            int r14 = r6 + 1
            int r13 = r13 + r6
            char r6 = r0.charAt(r13)
            if (r6 < r11) goto L_0x004d
            if (r6 > r12) goto L_0x004d
            int r2 = r2 * 10
            int r6 = r6 + -48
            int r2 = r2 + r6
            r6 = r14
            goto L_0x0039
        L_0x004d:
            r13 = 46
            if (r6 != r13) goto L_0x0053
            r13 = 1
            goto L_0x0054
        L_0x0053:
            r13 = 0
        L_0x0054:
            r15 = 10
            if (r13 == 0) goto L_0x0088
            int r6 = r0.bp
            int r13 = r14 + 1
            int r6 = r6 + r14
            char r6 = r0.charAt(r6)
            if (r6 < r11) goto L_0x0085
            if (r6 > r12) goto L_0x0085
            int r2 = r2 * 10
            int r6 = r6 - r11
            int r2 = r2 + r6
            r6 = 10
        L_0x006b:
            int r14 = r0.bp
            int r16 = r13 + 1
            int r14 = r14 + r13
            char r13 = r0.charAt(r14)
            if (r13 < r11) goto L_0x0082
            if (r13 > r12) goto L_0x0082
            int r2 = r2 * 10
            int r13 = r13 + -48
            int r2 = r2 + r13
            int r6 = r6 * 10
            r13 = r16
            goto L_0x006b
        L_0x0082:
            r14 = r16
            goto L_0x008a
        L_0x0085:
            r0.matchStat = r10
            return r3
        L_0x0088:
            r13 = r6
            r6 = 1
        L_0x008a:
            r1 = 101(0x65, float:1.42E-43)
            if (r13 == r1) goto L_0x0096
            r1 = 69
            if (r13 != r1) goto L_0x0093
            goto L_0x0096
        L_0x0093:
            r17 = 0
            goto L_0x0098
        L_0x0096:
            r17 = 1
        L_0x0098:
            if (r17 == 0) goto L_0x00c0
            int r1 = r0.bp
            int r13 = r14 + 1
            int r1 = r1 + r14
            char r1 = r0.charAt(r1)
            r14 = 43
            if (r1 == r14) goto L_0x00ae
            if (r1 != r8) goto L_0x00aa
            goto L_0x00ae
        L_0x00aa:
            r14 = r13
            r13 = r1
            r1 = r0
            goto L_0x00b9
        L_0x00ae:
            r1 = r0
        L_0x00af:
            int r8 = r1.bp
            int r14 = r13 + 1
            int r8 = r8 + r13
            char r8 = r1.charAt(r8)
            r13 = r8
        L_0x00b9:
            if (r13 < r11) goto L_0x00c1
            if (r13 <= r12) goto L_0x00be
            goto L_0x00c1
        L_0x00be:
            r13 = r14
            goto L_0x00af
        L_0x00c0:
            r1 = r0
        L_0x00c1:
            int r8 = r1.bp
            int r8 = r8 + r14
            int r8 = r8 - r5
            int r8 = r8 - r7
            if (r17 != 0) goto L_0x00d1
            if (r8 >= r15) goto L_0x00d1
            double r11 = (double) r2
            double r5 = (double) r6
            double r11 = r11 / r5
            if (r9 == 0) goto L_0x00d9
            double r5 = -r11
            goto L_0x00da
        L_0x00d1:
            java.lang.String r2 = r1.subString(r5, r8)
            double r11 = java.lang.Double.parseDouble(r2)
        L_0x00d9:
            r5 = r11
        L_0x00da:
            r2 = 16
            r8 = 44
            if (r13 != r8) goto L_0x00ef
            int r3 = r1.bp
            int r14 = r14 - r7
            int r3 = r3 + r14
            r1.bp = r3
            r1.next()
            r3 = 3
            r1.matchStat = r3
            r1.token = r2
            return r5
        L_0x00ef:
            r9 = 125(0x7d, float:1.75E-43)
            if (r13 != r9) goto L_0x0143
            int r11 = r1.bp
            int r12 = r14 + 1
            int r11 = r11 + r14
            char r11 = r1.charAt(r11)
            if (r11 != r8) goto L_0x010a
            r1.token = r2
            int r2 = r1.bp
            int r12 = r12 - r7
            int r2 = r2 + r12
            r1.bp = r2
            r1.next()
            goto L_0x013c
        L_0x010a:
            r2 = 93
            if (r11 != r2) goto L_0x011c
            r2 = 15
            r1.token = r2
            int r2 = r1.bp
            int r12 = r12 - r7
            int r2 = r2 + r12
            r1.bp = r2
            r1.next()
            goto L_0x013c
        L_0x011c:
            if (r11 != r9) goto L_0x012c
            r2 = 13
            r1.token = r2
            int r2 = r1.bp
            int r12 = r12 - r7
            int r2 = r2 + r12
            r1.bp = r2
            r1.next()
            goto L_0x013c
        L_0x012c:
            r2 = 26
            if (r11 != r2) goto L_0x0140
            int r3 = r1.bp
            int r12 = r12 - r7
            int r3 = r3 + r12
            r1.bp = r3
            r3 = 20
            r1.token = r3
            r1.ch = r2
        L_0x013c:
            r2 = 4
            r1.matchStat = r2
            return r5
        L_0x0140:
            r1.matchStat = r10
            return r3
        L_0x0143:
            r1.matchStat = r10
            return r3
        L_0x0146:
            r0.matchStat = r10
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexer.scanFieldDouble(long):double");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:142:0x0217, code lost:
        return r2;
     */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x0116 A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final double[] scanFieldDoubleArray(long r20) {
        /*
            r19 = this;
            r0 = r19
            r1 = 0
            r0.matchStat = r1
            int r2 = r19.matchFieldHash(r20)
            r3 = 0
            if (r2 != 0) goto L_0x000d
            return r3
        L_0x000d:
            int r4 = r0.bp
            int r5 = r2 + 1
            int r4 = r4 + r2
            int r2 = r0.len
            if (r4 < r2) goto L_0x0019
            r2 = 26
            goto L_0x001f
        L_0x0019:
            java.lang.String r2 = r0.text
            char r2 = r2.charAt(r4)
        L_0x001f:
            r4 = 91
            r7 = -1
            if (r2 == r4) goto L_0x0027
            r0.matchStat = r7
            return r3
        L_0x0027:
            int r2 = r0.bp
            int r4 = r5 + 1
            int r2 = r2 + r5
            int r5 = r0.len
            if (r2 < r5) goto L_0x0033
            r2 = 26
            goto L_0x0039
        L_0x0033:
            java.lang.String r5 = r0.text
            char r2 = r5.charAt(r2)
        L_0x0039:
            r5 = 16
            double[] r8 = new double[r5]
            r9 = 0
        L_0x003e:
            int r10 = r0.bp
            int r10 = r10 + r4
            r11 = 1
            int r10 = r10 - r11
            r12 = 45
            if (r2 != r12) goto L_0x0049
            r13 = 1
            goto L_0x004a
        L_0x0049:
            r13 = 0
        L_0x004a:
            if (r13 == 0) goto L_0x005f
            int r2 = r0.bp
            int r14 = r4 + 1
            int r2 = r2 + r4
            int r4 = r0.len
            if (r2 < r4) goto L_0x0058
            r2 = 26
            goto L_0x0060
        L_0x0058:
            java.lang.String r4 = r0.text
            char r2 = r4.charAt(r2)
            goto L_0x0060
        L_0x005f:
            r14 = r4
        L_0x0060:
            r4 = 48
            if (r2 < r4) goto L_0x0214
            r15 = 57
            if (r2 > r15) goto L_0x0214
            int r2 = r2 + -48
        L_0x006a:
            int r6 = r0.bp
            int r16 = r14 + 1
            int r6 = r6 + r14
            int r14 = r0.len
            if (r6 < r14) goto L_0x0076
            r6 = 26
            goto L_0x007c
        L_0x0076:
            java.lang.String r14 = r0.text
            char r6 = r14.charAt(r6)
        L_0x007c:
            if (r6 < r4) goto L_0x0088
            if (r6 > r15) goto L_0x0088
            int r2 = r2 * 10
            int r6 = r6 + -48
            int r2 = r2 + r6
            r14 = r16
            goto L_0x006a
        L_0x0088:
            r14 = 46
            if (r6 != r14) goto L_0x008e
            r14 = 1
            goto L_0x008f
        L_0x008e:
            r14 = 0
        L_0x008f:
            r5 = 10
            if (r14 == 0) goto L_0x00d4
            int r6 = r0.bp
            int r14 = r16 + 1
            int r6 = r6 + r16
            int r1 = r0.len
            if (r6 < r1) goto L_0x00a0
            r6 = 26
            goto L_0x00a6
        L_0x00a0:
            java.lang.String r1 = r0.text
            char r6 = r1.charAt(r6)
        L_0x00a6:
            if (r6 < r4) goto L_0x00d1
            if (r6 > r15) goto L_0x00d1
            int r2 = r2 * 10
            int r6 = r6 + -48
            int r2 = r2 + r6
            r1 = 10
        L_0x00b1:
            int r6 = r0.bp
            int r16 = r14 + 1
            int r6 = r6 + r14
            int r14 = r0.len
            if (r6 < r14) goto L_0x00bd
            r6 = 26
            goto L_0x00c3
        L_0x00bd:
            java.lang.String r14 = r0.text
            char r6 = r14.charAt(r6)
        L_0x00c3:
            if (r6 < r4) goto L_0x00d5
            if (r6 > r15) goto L_0x00d5
            int r2 = r2 * 10
            int r6 = r6 + -48
            int r2 = r2 + r6
            int r1 = r1 * 10
            r14 = r16
            goto L_0x00b1
        L_0x00d1:
            r0.matchStat = r7
            return r3
        L_0x00d4:
            r1 = 1
        L_0x00d5:
            r14 = 101(0x65, float:1.42E-43)
            if (r6 == r14) goto L_0x00e0
            r14 = 69
            if (r6 != r14) goto L_0x00de
            goto L_0x00e0
        L_0x00de:
            r14 = 0
            goto L_0x00e1
        L_0x00e0:
            r14 = 1
        L_0x00e1:
            if (r14 == 0) goto L_0x012d
            int r6 = r0.bp
            int r17 = r16 + 1
            int r6 = r6 + r16
            int r3 = r0.len
            if (r6 < r3) goto L_0x00f0
            r6 = 26
            goto L_0x00f6
        L_0x00f0:
            java.lang.String r3 = r0.text
            char r6 = r3.charAt(r6)
        L_0x00f6:
            r3 = 43
            if (r6 == r3) goto L_0x00ff
            if (r6 != r12) goto L_0x00fd
            goto L_0x00ff
        L_0x00fd:
            r3 = r6
            goto L_0x0114
        L_0x00ff:
            int r3 = r0.bp
            int r6 = r17 + 1
            int r3 = r3 + r17
            int r12 = r0.len
            if (r3 < r12) goto L_0x010c
        L_0x0109:
            r3 = 26
            goto L_0x0112
        L_0x010c:
            java.lang.String r12 = r0.text
            char r3 = r12.charAt(r3)
        L_0x0112:
            r17 = r6
        L_0x0114:
            if (r3 < r4) goto L_0x012a
            if (r3 > r15) goto L_0x012a
            int r3 = r0.bp
            int r6 = r17 + 1
            int r3 = r3 + r17
            int r12 = r0.len
            if (r3 < r12) goto L_0x0123
            goto L_0x0109
        L_0x0123:
            java.lang.String r12 = r0.text
            char r3 = r12.charAt(r3)
            goto L_0x0112
        L_0x012a:
            r4 = r17
            goto L_0x0130
        L_0x012d:
            r3 = r6
            r4 = r16
        L_0x0130:
            int r6 = r0.bp
            int r6 = r6 + r4
            int r6 = r6 - r10
            int r6 = r6 - r11
            if (r14 != 0) goto L_0x0140
            if (r6 >= r5) goto L_0x0140
            double r5 = (double) r2
            double r1 = (double) r1
            double r5 = r5 / r1
            if (r13 == 0) goto L_0x0148
            double r5 = -r5
            goto L_0x0148
        L_0x0140:
            java.lang.String r1 = r0.subString(r10, r6)
            double r5 = java.lang.Double.parseDouble(r1)
        L_0x0148:
            int r1 = r8.length
            r2 = 3
            if (r9 < r1) goto L_0x0158
            int r1 = r8.length
            int r1 = r1 * 3
            int r1 = r1 / 2
            double[] r1 = new double[r1]
            r10 = 0
            java.lang.System.arraycopy(r8, r10, r1, r10, r9)
            r8 = r1
        L_0x0158:
            int r1 = r9 + 1
            r8[r9] = r5
            r5 = 44
            if (r3 != r5) goto L_0x0177
            int r2 = r0.bp
            int r3 = r4 + 1
            int r2 = r2 + r4
            int r4 = r0.len
            if (r2 < r4) goto L_0x016c
            r2 = 26
            goto L_0x0173
        L_0x016c:
            java.lang.String r4 = r0.text
            char r6 = r4.charAt(r2)
            r2 = r6
        L_0x0173:
            r9 = r1
            r4 = r3
            goto L_0x020e
        L_0x0177:
            r6 = 93
            if (r3 != r6) goto L_0x020c
            int r3 = r0.bp
            int r9 = r4 + 1
            int r3 = r3 + r4
            int r4 = r0.len
            if (r3 < r4) goto L_0x0187
            r3 = 26
            goto L_0x018d
        L_0x0187:
            java.lang.String r4 = r0.text
            char r3 = r4.charAt(r3)
        L_0x018d:
            int r4 = r8.length
            if (r1 == r4) goto L_0x0197
            double[] r4 = new double[r1]
            r10 = 0
            java.lang.System.arraycopy(r8, r10, r4, r10, r1)
            goto L_0x0198
        L_0x0197:
            r4 = r8
        L_0x0198:
            if (r3 != r5) goto L_0x01aa
            int r1 = r0.bp
            int r9 = r9 - r11
            int r1 = r1 + r9
            r0.bp = r1
            r19.next()
            r0.matchStat = r2
            r1 = 16
            r0.token = r1
            return r4
        L_0x01aa:
            r1 = 125(0x7d, float:1.75E-43)
            if (r3 != r1) goto L_0x0208
            int r2 = r0.bp
            int r3 = r9 + 1
            int r2 = r2 + r9
            int r8 = r0.len
            if (r2 < r8) goto L_0x01ba
            r2 = 26
            goto L_0x01c0
        L_0x01ba:
            java.lang.String r8 = r0.text
            char r2 = r8.charAt(r2)
        L_0x01c0:
            if (r2 != r5) goto L_0x01d0
            r5 = 16
            r0.token = r5
            int r1 = r0.bp
            int r3 = r3 - r11
            int r1 = r1 + r3
            r0.bp = r1
            r19.next()
            goto L_0x0200
        L_0x01d0:
            if (r2 != r6) goto L_0x01e0
            r1 = 15
            r0.token = r1
            int r1 = r0.bp
            int r3 = r3 - r11
            int r1 = r1 + r3
            r0.bp = r1
            r19.next()
            goto L_0x0200
        L_0x01e0:
            if (r2 != r1) goto L_0x01f0
            r1 = 13
            r0.token = r1
            int r1 = r0.bp
            int r3 = r3 - r11
            int r1 = r1 + r3
            r0.bp = r1
            r19.next()
            goto L_0x0200
        L_0x01f0:
            r6 = 26
            if (r2 != r6) goto L_0x0204
            int r1 = r0.bp
            int r3 = r3 - r11
            int r1 = r1 + r3
            r0.bp = r1
            r1 = 20
            r0.token = r1
            r0.ch = r6
        L_0x0200:
            r1 = 4
            r0.matchStat = r1
            return r4
        L_0x0204:
            r0.matchStat = r7
            r2 = 0
            return r2
        L_0x0208:
            r2 = 0
            r0.matchStat = r7
            return r2
        L_0x020c:
            r9 = r1
            r2 = r3
        L_0x020e:
            r1 = 0
            r3 = 0
            r5 = 16
            goto L_0x003e
        L_0x0214:
            r2 = r3
            r0.matchStat = r7
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexer.scanFieldDoubleArray(long):double[]");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:107:0x018e, code lost:
        r11 = r10 + 1;
        r1 = r0.bp + r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:108:0x0195, code lost:
        if (r1 < r0.len) goto L_0x019a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:109:0x0197, code lost:
        r1 = EOI;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:110:0x019a, code lost:
        r1 = r0.text.charAt(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:112:0x01a1, code lost:
        if (r4 == r9.length) goto L_0x01ab;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:113:0x01a3, code lost:
        r10 = new double[r4];
        r12 = 0;
        java.lang.System.arraycopy(r9, 0, r10, 0, r4);
        r9 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:114:0x01ab, code lost:
        r12 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:116:0x01ad, code lost:
        if (r5 < r8.length) goto L_0x01b9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:117:0x01af, code lost:
        r8 = new double[((r8.length * 3) / 2)][];
        java.lang.System.arraycopy(r9, r12, r8, r12, r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:118:0x01b9, code lost:
        r4 = r5 + 1;
        r8[r5] = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:119:0x01bd, code lost:
        if (r1 != ',') goto L_0x01d5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:120:0x01bf, code lost:
        r2 = r11 + 1;
        r1 = r0.bp + r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:121:0x01c6, code lost:
        if (r1 >= r0.len) goto L_0x01cd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:122:0x01c8, code lost:
        r0.text.charAt(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:123:0x01cd, code lost:
        r5 = r4;
        r3 = null;
        r4 = r2;
        r2 = 16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:124:0x01d5, code lost:
        if (r1 != ']') goto L_0x0261;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:125:0x01d7, code lost:
        r5 = r11 + 1;
        r1 = r0.bp + r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:126:0x01de, code lost:
        if (r1 < r0.len) goto L_0x01e3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:127:0x01e0, code lost:
        r1 = EOI;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:128:0x01e3, code lost:
        r1 = r0.text.charAt(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:130:0x01ea, code lost:
        if (r4 == r8.length) goto L_0x01f3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:131:0x01ec, code lost:
        r9 = new double[r4][];
        java.lang.System.arraycopy(r8, 0, r9, 0, r4);
        r8 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:132:0x01f3, code lost:
        if (r1 != ',') goto L_0x0206;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:133:0x01f5, code lost:
        r0.bp += r5 - 1;
        next();
        r0.matchStat = 3;
        r0.token = 16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:134:0x0205, code lost:
        return r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:136:0x020a, code lost:
        if (r1 != '}') goto L_0x025d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:137:0x020c, code lost:
        r9 = r5 + 1;
        r1 = charAt(r0.bp + r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:138:0x0215, code lost:
        if (r1 != ',') goto L_0x0224;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:139:0x0217, code lost:
        r0.token = 16;
        r0.bp += r9 - 1;
        next();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:141:0x0225, code lost:
        if (r1 != ']') goto L_0x0235;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:142:0x0227, code lost:
        r0.token = 15;
        r0.bp += r9 - 1;
        next();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:143:0x0235, code lost:
        if (r1 != '}') goto L_0x0245;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:144:0x0237, code lost:
        r0.token = 13;
        r0.bp += r9 - 1;
        next();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:146:0x0247, code lost:
        if (r1 != 26) goto L_0x0259;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:147:0x0249, code lost:
        r0.bp += r9 - 1;
        r0.token = 20;
        r0.ch = EOI;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:148:0x0255, code lost:
        r0.matchStat = 4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:149:0x0258, code lost:
        return r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:150:0x0259, code lost:
        r0.matchStat = -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:151:0x025c, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:152:0x025d, code lost:
        r0.matchStat = -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:153:0x0260, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:154:0x0261, code lost:
        r5 = r4;
        r4 = r11;
        r2 = 16;
        r3 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:157:0x0274, code lost:
        return r2;
     */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x0123 A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final double[][] scanFieldDoubleArray2(long r20) {
        /*
            r19 = this;
            r0 = r19
            r1 = 0
            r0.matchStat = r1
            int r2 = r19.matchFieldHash(r20)
            r3 = 0
            if (r2 != 0) goto L_0x000d
            return r3
        L_0x000d:
            int r4 = r0.bp
            int r5 = r2 + 1
            int r4 = r4 + r2
            int r2 = r0.len
            if (r4 < r2) goto L_0x0019
            r2 = 26
            goto L_0x001f
        L_0x0019:
            java.lang.String r2 = r0.text
            char r2 = r2.charAt(r4)
        L_0x001f:
            r4 = 91
            r7 = -1
            if (r2 == r4) goto L_0x0027
            r0.matchStat = r7
            return r3
        L_0x0027:
            int r2 = r0.bp
            int r4 = r5 + 1
            int r2 = r2 + r5
            int r5 = r0.len
            if (r2 >= r5) goto L_0x0035
            java.lang.String r5 = r0.text
            r5.charAt(r2)
        L_0x0035:
            r2 = 16
            double[][] r5 = new double[r2][]
            r8 = r5
            r5 = 0
        L_0x003b:
            int r9 = r0.bp
            int r10 = r4 + 1
            int r9 = r9 + r4
            int r4 = r0.len
            if (r9 < r4) goto L_0x0047
            r4 = 26
            goto L_0x004d
        L_0x0047:
            java.lang.String r4 = r0.text
            char r4 = r4.charAt(r9)
        L_0x004d:
            double[] r9 = new double[r2]
            r11 = 0
        L_0x0050:
            int r12 = r0.bp
            int r12 = r12 + r10
            r13 = 1
            int r12 = r12 - r13
            r14 = 45
            if (r4 != r14) goto L_0x005b
            r15 = 1
            goto L_0x005c
        L_0x005b:
            r15 = 0
        L_0x005c:
            if (r15 == 0) goto L_0x0071
            int r4 = r0.bp
            int r16 = r10 + 1
            int r4 = r4 + r10
            int r10 = r0.len
            if (r4 < r10) goto L_0x006a
            r4 = 26
            goto L_0x0073
        L_0x006a:
            java.lang.String r10 = r0.text
            char r4 = r10.charAt(r4)
            goto L_0x0073
        L_0x0071:
            r16 = r10
        L_0x0073:
            r10 = 48
            if (r4 < r10) goto L_0x0271
            r6 = 57
            if (r4 > r6) goto L_0x0271
            int r4 = r4 + -48
        L_0x007d:
            int r2 = r0.bp
            int r17 = r16 + 1
            int r2 = r2 + r16
            int r1 = r0.len
            if (r2 < r1) goto L_0x008a
            r1 = 26
            goto L_0x0090
        L_0x008a:
            java.lang.String r1 = r0.text
            char r1 = r1.charAt(r2)
        L_0x0090:
            if (r1 < r10) goto L_0x009d
            if (r1 > r6) goto L_0x009d
            int r4 = r4 * 10
            int r1 = r1 + -48
            int r4 = r4 + r1
            r16 = r17
            r1 = 0
            goto L_0x007d
        L_0x009d:
            r2 = 46
            if (r1 != r2) goto L_0x00e2
            int r1 = r0.bp
            int r2 = r17 + 1
            int r1 = r1 + r17
            int r13 = r0.len
            if (r1 < r13) goto L_0x00ae
            r1 = 26
            goto L_0x00b4
        L_0x00ae:
            java.lang.String r13 = r0.text
            char r1 = r13.charAt(r1)
        L_0x00b4:
            if (r1 < r10) goto L_0x00df
            if (r1 > r6) goto L_0x00df
            int r4 = r4 * 10
            int r1 = r1 + -48
            int r4 = r4 + r1
            r13 = 10
        L_0x00bf:
            int r1 = r0.bp
            int r16 = r2 + 1
            int r1 = r1 + r2
            int r2 = r0.len
            if (r1 < r2) goto L_0x00cb
            r1 = 26
            goto L_0x00d1
        L_0x00cb:
            java.lang.String r2 = r0.text
            char r1 = r2.charAt(r1)
        L_0x00d1:
            if (r1 < r10) goto L_0x00e5
            if (r1 > r6) goto L_0x00e5
            int r4 = r4 * 10
            int r1 = r1 + -48
            int r4 = r4 + r1
            int r13 = r13 * 10
            r2 = r16
            goto L_0x00bf
        L_0x00df:
            r0.matchStat = r7
            return r3
        L_0x00e2:
            r16 = r17
            r13 = 1
        L_0x00e5:
            r2 = 101(0x65, float:1.42E-43)
            if (r1 == r2) goto L_0x00f0
            r2 = 69
            if (r1 != r2) goto L_0x00ee
            goto L_0x00f0
        L_0x00ee:
            r2 = 0
            goto L_0x00f1
        L_0x00f0:
            r2 = 1
        L_0x00f1:
            if (r2 == 0) goto L_0x013a
            int r1 = r0.bp
            int r17 = r16 + 1
            int r1 = r1 + r16
            int r3 = r0.len
            if (r1 < r3) goto L_0x0100
            r1 = 26
            goto L_0x0106
        L_0x0100:
            java.lang.String r3 = r0.text
            char r1 = r3.charAt(r1)
        L_0x0106:
            r3 = 43
            if (r1 == r3) goto L_0x010c
            if (r1 != r14) goto L_0x0121
        L_0x010c:
            int r1 = r0.bp
            int r3 = r17 + 1
            int r1 = r1 + r17
            int r14 = r0.len
            if (r1 < r14) goto L_0x0119
        L_0x0116:
            r1 = 26
            goto L_0x011f
        L_0x0119:
            java.lang.String r14 = r0.text
            char r1 = r14.charAt(r1)
        L_0x011f:
            r17 = r3
        L_0x0121:
            if (r1 < r10) goto L_0x0137
            if (r1 > r6) goto L_0x0137
            int r1 = r0.bp
            int r3 = r17 + 1
            int r1 = r1 + r17
            int r14 = r0.len
            if (r1 < r14) goto L_0x0130
            goto L_0x0116
        L_0x0130:
            java.lang.String r14 = r0.text
            char r1 = r14.charAt(r1)
            goto L_0x011f
        L_0x0137:
            r10 = r17
            goto L_0x013c
        L_0x013a:
            r10 = r16
        L_0x013c:
            int r3 = r0.bp
            int r3 = r3 + r10
            int r3 = r3 - r12
            r6 = 1
            int r3 = r3 - r6
            if (r2 != 0) goto L_0x014f
            r2 = 10
            if (r3 >= r2) goto L_0x014f
            double r2 = (double) r4
            double r12 = (double) r13
            double r2 = r2 / r12
            if (r15 == 0) goto L_0x0157
            double r2 = -r2
            goto L_0x0157
        L_0x014f:
            java.lang.String r2 = r0.subString(r12, r3)
            double r2 = java.lang.Double.parseDouble(r2)
        L_0x0157:
            int r4 = r9.length
            r6 = 3
            if (r11 < r4) goto L_0x0167
            int r4 = r9.length
            int r4 = r4 * 3
            int r4 = r4 / 2
            double[] r4 = new double[r4]
            r12 = 0
            java.lang.System.arraycopy(r9, r12, r4, r12, r11)
            r9 = r4
        L_0x0167:
            int r4 = r11 + 1
            r9[r11] = r2
            r2 = 44
            if (r1 != r2) goto L_0x018a
            int r1 = r0.bp
            int r2 = r10 + 1
            int r1 = r1 + r10
            int r3 = r0.len
            if (r1 < r3) goto L_0x017b
            r6 = 26
            goto L_0x0181
        L_0x017b:
            java.lang.String r3 = r0.text
            char r6 = r3.charAt(r1)
        L_0x0181:
            r10 = r2
            r11 = r4
            r4 = r6
            r1 = 0
            r2 = 16
            r3 = 0
            goto L_0x0050
        L_0x018a:
            r3 = 93
            if (r1 != r3) goto L_0x0269
            int r1 = r0.bp
            int r11 = r10 + 1
            int r1 = r1 + r10
            int r10 = r0.len
            if (r1 < r10) goto L_0x019a
            r1 = 26
            goto L_0x01a0
        L_0x019a:
            java.lang.String r10 = r0.text
            char r1 = r10.charAt(r1)
        L_0x01a0:
            int r10 = r9.length
            if (r4 == r10) goto L_0x01ab
            double[] r10 = new double[r4]
            r12 = 0
            java.lang.System.arraycopy(r9, r12, r10, r12, r4)
            r9 = r10
            goto L_0x01ac
        L_0x01ab:
            r12 = 0
        L_0x01ac:
            int r10 = r8.length
            if (r5 < r10) goto L_0x01b9
            int r8 = r8.length
            int r8 = r8 * 3
            int r8 = r8 / 2
            double[][] r8 = new double[r8][]
            java.lang.System.arraycopy(r9, r12, r8, r12, r4)
        L_0x01b9:
            int r4 = r5 + 1
            r8[r5] = r9
            if (r1 != r2) goto L_0x01d5
            int r1 = r0.bp
            int r2 = r11 + 1
            int r1 = r1 + r11
            int r3 = r0.len
            if (r1 >= r3) goto L_0x01cd
            java.lang.String r3 = r0.text
            r3.charAt(r1)
        L_0x01cd:
            r5 = r4
            r1 = 0
            r3 = 0
            r4 = r2
            r2 = 16
            goto L_0x003b
        L_0x01d5:
            if (r1 != r3) goto L_0x0261
            int r1 = r0.bp
            int r5 = r11 + 1
            int r1 = r1 + r11
            int r9 = r0.len
            if (r1 < r9) goto L_0x01e3
            r1 = 26
            goto L_0x01e9
        L_0x01e3:
            java.lang.String r9 = r0.text
            char r1 = r9.charAt(r1)
        L_0x01e9:
            int r9 = r8.length
            if (r4 == r9) goto L_0x01f3
            double[][] r9 = new double[r4][]
            r12 = 0
            java.lang.System.arraycopy(r8, r12, r9, r12, r4)
            r8 = r9
        L_0x01f3:
            if (r1 != r2) goto L_0x0206
            int r1 = r0.bp
            r2 = 1
            int r5 = r5 - r2
            int r1 = r1 + r5
            r0.bp = r1
            r19.next()
            r0.matchStat = r6
            r6 = 16
            r0.token = r6
            return r8
        L_0x0206:
            r6 = 16
            r4 = 125(0x7d, float:1.75E-43)
            if (r1 != r4) goto L_0x025d
            int r1 = r0.bp
            int r9 = r5 + 1
            int r1 = r1 + r5
            char r1 = r0.charAt(r1)
            if (r1 != r2) goto L_0x0224
            r0.token = r6
            int r1 = r0.bp
            r2 = 1
            int r9 = r9 - r2
            int r1 = r1 + r9
            r0.bp = r1
            r19.next()
            goto L_0x0255
        L_0x0224:
            r2 = 1
            if (r1 != r3) goto L_0x0235
            r1 = 15
            r0.token = r1
            int r1 = r0.bp
            int r9 = r9 - r2
            int r1 = r1 + r9
            r0.bp = r1
            r19.next()
            goto L_0x0255
        L_0x0235:
            if (r1 != r4) goto L_0x0245
            r1 = 13
            r0.token = r1
            int r1 = r0.bp
            int r9 = r9 - r2
            int r1 = r1 + r9
            r0.bp = r1
            r19.next()
            goto L_0x0255
        L_0x0245:
            r3 = 26
            if (r1 != r3) goto L_0x0259
            int r1 = r0.bp
            int r9 = r9 - r2
            int r1 = r1 + r9
            r0.bp = r1
            r1 = 20
            r0.token = r1
            r0.ch = r3
        L_0x0255:
            r1 = 4
            r0.matchStat = r1
            return r8
        L_0x0259:
            r0.matchStat = r7
            r2 = 0
            return r2
        L_0x025d:
            r2 = 0
            r0.matchStat = r7
            return r2
        L_0x0261:
            r5 = r4
            r4 = r11
            r1 = 0
            r2 = 16
            r3 = 0
            goto L_0x003b
        L_0x0269:
            r11 = r4
            r2 = 16
            r3 = 0
            r4 = r1
            r1 = 0
            goto L_0x0050
        L_0x0271:
            r2 = r3
            r0.matchStat = r7
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexer.scanFieldDoubleArray2(long):double[][]");
    }

    public final long scanFieldSymbol(long j) {
        char c;
        char c2;
        char c3;
        char c4;
        this.matchStat = 0;
        int matchFieldHash = matchFieldHash(j);
        if (matchFieldHash == 0) {
            return 0;
        }
        int i = matchFieldHash + 1;
        int i2 = this.bp + matchFieldHash;
        int i3 = this.len;
        char c5 = EOI;
        if (i2 >= i3) {
            c = EOI;
        } else {
            c = this.text.charAt(i2);
        }
        if (c != '\"') {
            this.matchStat = -1;
            return 0;
        }
        long j2 = -3750763034362895579L;
        while (true) {
            int i4 = i + 1;
            int i5 = this.bp + i;
            if (i5 >= this.len) {
                c2 = EOI;
            } else {
                c2 = this.text.charAt(i5);
            }
            if (c2 == '\"') {
                int i6 = i4 + 1;
                int i7 = this.bp + i4;
                if (i7 >= this.len) {
                    c3 = EOI;
                } else {
                    c3 = this.text.charAt(i7);
                }
                if (c3 == ',') {
                    this.bp += i6 - 1;
                    int i8 = this.bp + 1;
                    this.bp = i8;
                    if (i8 < this.len) {
                        c5 = this.text.charAt(i8);
                    }
                    this.ch = c5;
                    this.matchStat = 3;
                    return j2;
                } else if (c3 == '}') {
                    int i9 = i6 + 1;
                    int i10 = this.bp + i6;
                    if (i10 >= this.len) {
                        c4 = EOI;
                    } else {
                        c4 = this.text.charAt(i10);
                    }
                    if (c4 == ',') {
                        this.token = 16;
                        this.bp += i9 - 1;
                        next();
                    } else if (c4 == ']') {
                        this.token = 15;
                        this.bp += i9 - 1;
                        next();
                    } else if (c4 == '}') {
                        this.token = 13;
                        this.bp += i9 - 1;
                        next();
                    } else if (c4 == 26) {
                        this.token = 20;
                        this.bp += i9 - 1;
                        this.ch = EOI;
                    } else {
                        this.matchStat = -1;
                        return 0;
                    }
                    this.matchStat = 4;
                    return j2;
                } else {
                    this.matchStat = -1;
                    return 0;
                }
            } else {
                j2 = (j2 ^ ((long) c2)) * 1099511628211L;
                if (c2 == '\\') {
                    this.matchStat = -1;
                    return 0;
                }
                i = i4;
            }
        }
    }

    public final boolean scanISO8601DateIfMatch(boolean z) {
        return scanISO8601DateIfMatch(z, this.len - this.bp);
    }

    /* JADX WARNING: type inference failed for: r0v9, types: [char] */
    /* JADX WARNING: type inference failed for: r1v4, types: [char] */
    /* JADX WARNING: type inference failed for: r2v2, types: [char] */
    /* JADX WARNING: type inference failed for: r22v0, types: [char] */
    /* JADX WARNING: type inference failed for: r27v0 */
    /* JADX WARNING: type inference failed for: r26v0 */
    /* JADX WARNING: type inference failed for: r25v0 */
    /* JADX WARNING: type inference failed for: r5v2, types: [char] */
    /* JADX WARNING: type inference failed for: r6v1, types: [char] */
    /* JADX WARNING: type inference failed for: r7v1, types: [int] */
    /* JADX WARNING: type inference failed for: r5v3, types: [char] */
    /* JADX WARNING: type inference failed for: r6v2, types: [char] */
    /* JADX WARNING: type inference failed for: r7v2, types: [char] */
    /* JADX WARNING: type inference failed for: r7v4, types: [int] */
    /* JADX WARNING: type inference failed for: r3v14, types: [int] */
    /* JADX WARNING: type inference failed for: r2v10, types: [int] */
    /* JADX WARNING: type inference failed for: r0v20, types: [int] */
    /* JADX WARNING: type inference failed for: r24v0, types: [char] */
    /* JADX WARNING: type inference failed for: r25v1, types: [char] */
    /* JADX WARNING: type inference failed for: r27v1, types: [char] */
    /* JADX WARNING: type inference failed for: r28v1, types: [char] */
    /* JADX WARNING: type inference failed for: r3v16 */
    /* JADX WARNING: type inference failed for: r0v28 */
    /* JADX WARNING: type inference failed for: r7v7 */
    /* JADX WARNING: type inference failed for: r3v17 */
    /* JADX WARNING: type inference failed for: r2v14 */
    /* JADX WARNING: type inference failed for: r25v2 */
    /* JADX WARNING: type inference failed for: r26v2 */
    /* JADX WARNING: type inference failed for: r27v2 */
    /* JADX WARNING: type inference failed for: r25v3 */
    /* JADX WARNING: type inference failed for: r26v3 */
    /* JADX WARNING: type inference failed for: r27v3 */
    /* JADX WARNING: type inference failed for: r0v152 */
    /* JADX WARNING: type inference failed for: r7v23 */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00cd, code lost:
        if (r0 != ' ') goto L_0x00cf;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:111:0x0224 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x0225  */
    /* JADX WARNING: Removed duplicated region for block: B:173:0x03b2  */
    /* JADX WARNING: Unknown variable types count: 19 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean scanISO8601DateIfMatch(boolean r39, int r40) {
        /*
            r38 = this;
            r9 = r38
            r11 = r40
            r12 = 57
            r13 = 6
            r14 = 3
            r15 = 2
            r8 = 5
            r16 = 1
            r7 = 48
            r6 = 0
            if (r39 != 0) goto L_0x00ad
            r0 = 13
            if (r11 <= r0) goto L_0x00ad
            int r0 = r9.bp
            char r0 = r9.charAt(r0)
            int r1 = r9.bp
            int r1 = r1 + 1
            char r1 = r9.charAt(r1)
            int r2 = r9.bp
            int r2 = r2 + r15
            char r2 = r9.charAt(r2)
            int r3 = r9.bp
            int r3 = r3 + r14
            char r3 = r9.charAt(r3)
            int r4 = r9.bp
            int r4 = r4 + 4
            char r4 = r9.charAt(r4)
            int r5 = r9.bp
            int r5 = r5 + r8
            char r5 = r9.charAt(r5)
            int r14 = r9.bp
            int r14 = r14 + r11
            int r14 = r14 + -1
            char r14 = r9.charAt(r14)
            int r8 = r9.bp
            int r8 = r8 + r11
            int r8 = r8 - r15
            char r8 = r9.charAt(r8)
            r15 = 47
            if (r0 != r15) goto L_0x00ad
            r0 = 68
            if (r1 != r0) goto L_0x00ad
            r0 = 97
            if (r2 != r0) goto L_0x00ad
            r0 = 116(0x74, float:1.63E-43)
            if (r3 != r0) goto L_0x00ad
            r0 = 101(0x65, float:1.42E-43)
            if (r4 != r0) goto L_0x00ad
            r0 = 40
            if (r5 != r0) goto L_0x00ad
            r0 = 47
            if (r14 != r0) goto L_0x00ad
            r0 = 41
            if (r8 != r0) goto L_0x00ad
            r0 = -1
            r0 = 6
            r1 = -1
        L_0x0074:
            if (r0 >= r11) goto L_0x008a
            int r2 = r9.bp
            int r2 = r2 + r0
            char r2 = r9.charAt(r2)
            r3 = 43
            if (r2 != r3) goto L_0x0083
            r1 = r0
            goto L_0x0087
        L_0x0083:
            if (r2 < r7) goto L_0x008a
            if (r2 > r12) goto L_0x008a
        L_0x0087:
            int r0 = r0 + 1
            goto L_0x0074
        L_0x008a:
            r0 = -1
            if (r1 != r0) goto L_0x008e
            return r6
        L_0x008e:
            int r0 = r9.bp
            int r0 = r0 + r13
            int r1 = r1 - r0
            java.lang.String r0 = r9.subString(r0, r1)
            long r0 = java.lang.Long.parseLong(r0)
            java.util.TimeZone r2 = r9.timeZone
            java.util.Locale r3 = r9.locale
            java.util.Calendar r2 = java.util.Calendar.getInstance(r2, r3)
            r9.calendar = r2
            java.util.Calendar r2 = r9.calendar
            r2.setTimeInMillis(r0)
            r0 = 5
            r9.token = r0
            return r16
        L_0x00ad:
            r15 = 8
            r8 = 9
            r5 = 14
            r4 = 45
            r20 = 10
            if (r11 == r15) goto L_0x04e1
            if (r11 == r5) goto L_0x04e1
            r0 = 16
            if (r11 != r0) goto L_0x00cf
            int r0 = r9.bp
            int r0 = r0 + 10
            char r0 = r9.charAt(r0)
            r1 = 84
            if (r0 == r1) goto L_0x04e1
            r1 = 32
            if (r0 == r1) goto L_0x04e1
        L_0x00cf:
            r0 = 17
            if (r11 != r0) goto L_0x00de
            int r0 = r9.bp
            int r0 = r0 + r13
            char r0 = r9.charAt(r0)
            if (r0 == r4) goto L_0x00de
            goto L_0x04e1
        L_0x00de:
            if (r11 >= r8) goto L_0x00e1
            return r6
        L_0x00e1:
            int r0 = r9.bp
            char r0 = r9.charAt(r0)
            int r1 = r9.bp
            int r1 = r1 + 1
            char r1 = r9.charAt(r1)
            int r2 = r9.bp
            r3 = 2
            int r2 = r2 + r3
            char r2 = r9.charAt(r2)
            int r3 = r9.bp
            r17 = 3
            int r3 = r3 + 3
            char r3 = r9.charAt(r3)
            int r5 = r9.bp
            int r5 = r5 + 4
            char r5 = r9.charAt(r5)
            int r7 = r9.bp
            r18 = 5
            int r7 = r7 + 5
            char r7 = r9.charAt(r7)
            int r12 = r9.bp
            int r12 = r12 + r13
            char r12 = r9.charAt(r12)
            int r13 = r9.bp
            int r13 = r13 + 7
            char r13 = r9.charAt(r13)
            int r14 = r9.bp
            int r14 = r14 + r15
            char r14 = r9.charAt(r14)
            int r15 = r9.bp
            int r15 = r15 + r8
            char r15 = r9.charAt(r15)
            if (r5 != r4) goto L_0x0134
            if (r13 == r4) goto L_0x013c
        L_0x0134:
            r8 = 47
            if (r5 != r8) goto L_0x0149
            r8 = 47
            if (r13 != r8) goto L_0x0149
        L_0x013c:
            r13 = r1
            r8 = r12
            r34 = r14
            r35 = r15
            r36 = 10
        L_0x0144:
            r12 = r0
            r14 = r2
            r15 = r3
            goto L_0x020e
        L_0x0149:
            if (r5 != r4) goto L_0x016f
            if (r12 != r4) goto L_0x016f
            r5 = 32
            if (r14 != r5) goto L_0x0160
            r12 = r0
            r14 = r2
            r15 = r3
            r8 = r7
            r35 = r13
            r7 = 48
            r34 = 48
            r36 = 8
        L_0x015d:
            r13 = r1
            goto L_0x020e
        L_0x0160:
            r12 = r0
            r15 = r3
            r8 = r7
            r34 = r13
            r35 = r14
            r7 = 48
            r36 = 9
        L_0x016b:
            r13 = r1
        L_0x016c:
            r14 = r2
            goto L_0x020e
        L_0x016f:
            r8 = 46
            if (r2 != r8) goto L_0x0177
            r8 = 46
            if (r7 == r8) goto L_0x017b
        L_0x0177:
            if (r2 != r4) goto L_0x0185
            if (r7 != r4) goto L_0x0185
        L_0x017b:
            r34 = r0
            r35 = r1
            r7 = r3
            r8 = r5
            r36 = 10
            goto L_0x020e
        L_0x0185:
            r8 = 24180(0x5e74, float:3.3883E-41)
            if (r5 == r8) goto L_0x0190
            r8 = 45380(0xb144, float:6.3591E-41)
            if (r5 != r8) goto L_0x018f
            goto L_0x0190
        L_0x018f:
            return r6
        L_0x0190:
            r5 = 26376(0x6708, float:3.696E-41)
            if (r13 == r5) goto L_0x01d3
            r5 = 50900(0xc6d4, float:7.1326E-41)
            if (r13 != r5) goto L_0x019a
            goto L_0x01d3
        L_0x019a:
            r5 = 26376(0x6708, float:3.696E-41)
            if (r12 == r5) goto L_0x01a5
            r5 = 50900(0xc6d4, float:7.1326E-41)
            if (r12 != r5) goto L_0x01a4
            goto L_0x01a5
        L_0x01a4:
            return r6
        L_0x01a5:
            r5 = 26085(0x65e5, float:3.6553E-41)
            if (r14 == r5) goto L_0x01c6
            r5 = 51068(0xc77c, float:7.1562E-41)
            if (r14 != r5) goto L_0x01af
            goto L_0x01c6
        L_0x01af:
            r5 = 26085(0x65e5, float:3.6553E-41)
            if (r15 == r5) goto L_0x01ba
            r5 = 51068(0xc77c, float:7.1562E-41)
            if (r15 != r5) goto L_0x01b9
            goto L_0x01ba
        L_0x01b9:
            return r6
        L_0x01ba:
            r12 = r0
            r15 = r3
            r8 = r7
            r34 = r13
            r35 = r14
            r7 = 48
            r36 = 10
            goto L_0x016b
        L_0x01c6:
            r12 = r0
            r14 = r2
            r15 = r3
            r8 = r7
            r35 = r13
            r7 = 48
            r34 = 48
            r36 = 10
            goto L_0x015d
        L_0x01d3:
            r5 = 26085(0x65e5, float:3.6553E-41)
            if (r15 == r5) goto L_0x0202
            r5 = 51068(0xc77c, float:7.1562E-41)
            if (r15 != r5) goto L_0x01dd
            goto L_0x0202
        L_0x01dd:
            int r5 = r9.bp
            int r5 = r5 + 10
            char r5 = r9.charAt(r5)
            r8 = 26085(0x65e5, float:3.6553E-41)
            if (r5 == r8) goto L_0x01f8
            int r5 = r9.bp
            int r5 = r5 + 10
            char r5 = r9.charAt(r5)
            r8 = 51068(0xc77c, float:7.1562E-41)
            if (r5 != r8) goto L_0x01f7
            goto L_0x01f8
        L_0x01f7:
            return r6
        L_0x01f8:
            r13 = r1
            r8 = r12
            r34 = r14
            r35 = r15
            r36 = 11
            goto L_0x0144
        L_0x0202:
            r13 = r1
            r15 = r3
            r8 = r12
            r35 = r14
            r34 = 48
            r36 = 10
            r12 = r0
            goto L_0x016c
        L_0x020e:
            r26 = r12
            r27 = r13
            r28 = r14
            r29 = r15
            r30 = r7
            r31 = r8
            r32 = r34
            r33 = r35
            boolean r0 = checkDate(r26, r27, r28, r29, r30, r31, r32, r33)
            if (r0 != 0) goto L_0x0225
            return r6
        L_0x0225:
            r0 = r9
            r1 = r12
            r2 = r13
            r3 = r14
            r12 = 45
            r4 = r15
            r13 = 14
            r5 = r7
            r14 = 0
            r6 = r8
            r15 = 48
            r7 = r34
            r13 = 5
            r18 = 9
            r8 = r35
            r0.setCalendar(r1, r2, r3, r4, r5, r6, r7, r8)
            int r0 = r9.bp
            int r0 = r0 + r36
            char r7 = r9.charAt(r0)
            r0 = 84
            if (r7 == r0) goto L_0x02f3
            r0 = 32
            if (r7 != r0) goto L_0x0251
            if (r39 != 0) goto L_0x0251
            goto L_0x02f3
        L_0x0251:
            r0 = 34
            if (r7 == r0) goto L_0x02c8
            r0 = 26
            if (r7 == r0) goto L_0x02c8
            r0 = 26085(0x65e5, float:3.6553E-41)
            if (r7 == r0) goto L_0x02c8
            r0 = 51068(0xc77c, float:7.1562E-41)
            if (r7 != r0) goto L_0x0263
            goto L_0x02c8
        L_0x0263:
            r0 = 43
            if (r7 == r0) goto L_0x026b
            if (r7 != r12) goto L_0x026a
            goto L_0x026b
        L_0x026a:
            return r14
        L_0x026b:
            int r0 = r9.len
            int r1 = r36 + 6
            if (r0 != r1) goto L_0x02c7
            int r0 = r9.bp
            int r0 = r0 + r36
            r1 = 3
            int r0 = r0 + r1
            char r0 = r9.charAt(r0)
            r1 = 58
            if (r0 != r1) goto L_0x02c6
            int r0 = r9.bp
            int r0 = r0 + r36
            int r0 = r0 + 4
            char r0 = r9.charAt(r0)
            if (r0 != r15) goto L_0x02c6
            int r0 = r9.bp
            int r0 = r0 + r36
            int r0 = r0 + r13
            char r0 = r9.charAt(r0)
            if (r0 == r15) goto L_0x0297
            goto L_0x02c6
        L_0x0297:
            r1 = 48
            r2 = 48
            r3 = 48
            r4 = 48
            r5 = 48
            r6 = 48
            r0 = r9
            r0.setTime(r1, r2, r3, r4, r5, r6)
            java.util.Calendar r0 = r9.calendar
            r1 = 14
            r0.set(r1, r14)
            int r0 = r9.bp
            int r0 = r0 + r36
            int r0 = r0 + 1
            char r0 = r9.charAt(r0)
            int r1 = r9.bp
            int r1 = r1 + r36
            r2 = 2
            int r1 = r1 + r2
            char r1 = r9.charAt(r1)
            r9.setTimeZone(r7, r0, r1)
            return r16
        L_0x02c6:
            return r14
        L_0x02c7:
            return r14
        L_0x02c8:
            java.util.Calendar r0 = r9.calendar
            r1 = 11
            r0.set(r1, r14)
            java.util.Calendar r0 = r9.calendar
            r1 = 12
            r0.set(r1, r14)
            java.util.Calendar r0 = r9.calendar
            r1 = 13
            r0.set(r1, r14)
            java.util.Calendar r0 = r9.calendar
            r1 = 14
            r0.set(r1, r14)
            int r0 = r9.bp
            int r0 = r0 + r36
            r9.bp = r0
            char r0 = r9.charAt(r0)
            r9.ch = r0
            r9.token = r13
            return r16
        L_0x02f3:
            int r7 = r36 + 9
            if (r11 >= r7) goto L_0x02f8
            return r14
        L_0x02f8:
            int r0 = r9.bp
            int r0 = r0 + r36
            r1 = 3
            int r0 = r0 + r1
            char r0 = r9.charAt(r0)
            r1 = 58
            if (r0 == r1) goto L_0x0307
            return r14
        L_0x0307:
            int r0 = r9.bp
            int r0 = r0 + r36
            r1 = 6
            int r0 = r0 + r1
            char r0 = r9.charAt(r0)
            r1 = 58
            if (r0 == r1) goto L_0x0316
            return r14
        L_0x0316:
            int r0 = r9.bp
            int r0 = r0 + r36
            int r0 = r0 + 1
            char r8 = r9.charAt(r0)
            int r0 = r9.bp
            int r0 = r0 + r36
            r1 = 2
            int r0 = r0 + r1
            char r10 = r9.charAt(r0)
            int r0 = r9.bp
            int r0 = r0 + r36
            int r0 = r0 + 4
            char r21 = r9.charAt(r0)
            int r0 = r9.bp
            int r0 = r0 + r36
            int r0 = r0 + r13
            char r22 = r9.charAt(r0)
            int r0 = r9.bp
            int r0 = r0 + r36
            int r0 = r0 + 7
            char r25 = r9.charAt(r0)
            int r0 = r9.bp
            int r0 = r0 + r36
            r1 = 8
            int r0 = r0 + r1
            char r24 = r9.charAt(r0)
            r1 = r8
            r2 = r10
            r3 = r21
            r4 = r22
            r5 = r25
            r6 = r24
            boolean r0 = checkTime(r1, r2, r3, r4, r5, r6)
            if (r0 != 0) goto L_0x0363
            return r14
        L_0x0363:
            r0 = r9
            r1 = r8
            r2 = r10
            r3 = r21
            r4 = r22
            r5 = r25
            r6 = r24
            r0.setTime(r1, r2, r3, r4, r5, r6)
            int r0 = r9.bp
            int r0 = r0 + r36
            int r0 = r0 + 9
            char r0 = r9.charAt(r0)
            r1 = 46
            if (r0 != r1) goto L_0x04aa
            int r0 = r36 + 11
            if (r11 >= r0) goto L_0x0384
            return r14
        L_0x0384:
            int r1 = r9.bp
            int r1 = r1 + r36
            int r1 = r1 + 10
            char r1 = r9.charAt(r1)
            if (r1 < r15) goto L_0x04a9
            r2 = 57
            if (r1 <= r2) goto L_0x0396
            goto L_0x04a9
        L_0x0396:
            int r1 = r1 - r15
            if (r11 <= r0) goto L_0x03ae
            int r0 = r9.bp
            int r0 = r0 + r36
            r3 = 11
            int r0 = r0 + r3
            char r0 = r9.charAt(r0)
            if (r0 < r15) goto L_0x03ae
            if (r0 > r2) goto L_0x03ae
            int r1 = r1 * 10
            int r0 = r0 - r15
            int r1 = r1 + r0
            r0 = 2
            goto L_0x03af
        L_0x03ae:
            r0 = 1
        L_0x03af:
            r2 = 2
            if (r0 != r2) goto L_0x03c7
            int r2 = r9.bp
            int r2 = r2 + r36
            int r2 = r2 + 12
            char r2 = r9.charAt(r2)
            if (r2 < r15) goto L_0x03c7
            r3 = 57
            if (r2 > r3) goto L_0x03c7
            int r1 = r1 * 10
            int r2 = r2 - r15
            int r1 = r1 + r2
            r0 = 3
        L_0x03c7:
            java.util.Calendar r2 = r9.calendar
            r3 = 14
            r2.set(r3, r1)
            int r1 = r9.bp
            int r1 = r1 + r36
            int r1 = r1 + 10
            int r1 = r1 + r0
            char r1 = r9.charAt(r1)
            r2 = 43
            if (r1 == r2) goto L_0x040a
            if (r1 != r12) goto L_0x03e0
            goto L_0x040a
        L_0x03e0:
            r2 = 90
            if (r1 != r2) goto L_0x0406
            java.util.Calendar r1 = r9.calendar
            java.util.TimeZone r1 = r1.getTimeZone()
            int r1 = r1.getRawOffset()
            if (r1 == 0) goto L_0x0402
            java.lang.String[] r1 = java.util.TimeZone.getAvailableIDs(r14)
            int r2 = r1.length
            if (r2 <= 0) goto L_0x0402
            r1 = r1[r14]
            java.util.TimeZone r1 = java.util.TimeZone.getTimeZone(r1)
            java.util.Calendar r2 = r9.calendar
            r2.setTimeZone(r1)
        L_0x0402:
            r17 = 1
            goto L_0x0481
        L_0x0406:
            r17 = 0
            goto L_0x0481
        L_0x040a:
            int r2 = r9.bp
            int r2 = r2 + r36
            int r2 = r2 + 10
            int r2 = r2 + r0
            int r2 = r2 + 1
            char r2 = r9.charAt(r2)
            if (r2 < r15) goto L_0x04a8
            r3 = 49
            if (r2 <= r3) goto L_0x041f
            goto L_0x04a8
        L_0x041f:
            int r3 = r9.bp
            int r3 = r3 + r36
            int r3 = r3 + 10
            int r3 = r3 + r0
            r4 = 2
            int r3 = r3 + r4
            char r3 = r9.charAt(r3)
            if (r3 < r15) goto L_0x04a7
            r4 = 57
            if (r3 <= r4) goto L_0x0434
            goto L_0x04a7
        L_0x0434:
            int r4 = r9.bp
            int r4 = r4 + r36
            int r4 = r4 + 10
            int r4 = r4 + r0
            r5 = 3
            int r4 = r4 + r5
            char r4 = r9.charAt(r4)
            r5 = 58
            if (r4 != r5) goto L_0x0467
            int r4 = r9.bp
            int r4 = r4 + r36
            int r4 = r4 + 10
            int r4 = r4 + r0
            int r4 = r4 + 4
            char r4 = r9.charAt(r4)
            if (r4 == r15) goto L_0x0455
            return r14
        L_0x0455:
            int r4 = r9.bp
            int r4 = r4 + r36
            int r4 = r4 + 10
            int r4 = r4 + r0
            int r4 = r4 + r13
            char r4 = r9.charAt(r4)
            if (r4 == r15) goto L_0x0464
            return r14
        L_0x0464:
            r17 = 6
            goto L_0x047e
        L_0x0467:
            if (r4 != r15) goto L_0x047c
            int r4 = r9.bp
            int r4 = r4 + r36
            int r4 = r4 + 10
            int r4 = r4 + r0
            int r4 = r4 + 4
            char r4 = r9.charAt(r4)
            if (r4 == r15) goto L_0x0479
            return r14
        L_0x0479:
            r17 = 5
            goto L_0x047e
        L_0x047c:
            r17 = 3
        L_0x047e:
            r9.setTimeZone(r1, r2, r3)
        L_0x0481:
            int r1 = r9.bp
            int r36 = r36 + 10
            int r36 = r36 + r0
            int r36 = r36 + r17
            int r1 = r1 + r36
            char r0 = r9.charAt(r1)
            r1 = 26
            if (r0 == r1) goto L_0x0498
            r1 = 34
            if (r0 == r1) goto L_0x0498
            return r14
        L_0x0498:
            int r0 = r9.bp
            int r0 = r0 + r36
            r9.bp = r0
            char r0 = r9.charAt(r0)
            r9.ch = r0
            r9.token = r13
            return r16
        L_0x04a7:
            return r14
        L_0x04a8:
            return r14
        L_0x04a9:
            return r14
        L_0x04aa:
            java.util.Calendar r1 = r9.calendar
            r2 = 14
            r1.set(r2, r14)
            int r1 = r9.bp
            int r1 = r1 + r7
            r9.bp = r1
            char r1 = r9.charAt(r1)
            r9.ch = r1
            r9.token = r13
            r1 = 90
            if (r0 != r1) goto L_0x04e0
            java.util.Calendar r0 = r9.calendar
            java.util.TimeZone r0 = r0.getTimeZone()
            int r0 = r0.getRawOffset()
            if (r0 == 0) goto L_0x04e0
            java.lang.String[] r0 = java.util.TimeZone.getAvailableIDs(r14)
            int r1 = r0.length
            if (r1 <= 0) goto L_0x04e0
            r0 = r0[r14]
            java.util.TimeZone r0 = java.util.TimeZone.getTimeZone(r0)
            java.util.Calendar r1 = r9.calendar
            r1.setTimeZone(r0)
        L_0x04e0:
            return r16
        L_0x04e1:
            r12 = 45
            r13 = 5
            r14 = 0
            r15 = 48
            r18 = 9
            if (r39 == 0) goto L_0x04ec
            return r14
        L_0x04ec:
            int r0 = r9.bp
            char r10 = r9.charAt(r0)
            int r0 = r9.bp
            int r0 = r0 + 1
            char r21 = r9.charAt(r0)
            int r0 = r9.bp
            r1 = 2
            int r0 = r0 + r1
            char r19 = r9.charAt(r0)
            int r0 = r9.bp
            r1 = 3
            int r0 = r0 + r1
            char r17 = r9.charAt(r0)
            int r0 = r9.bp
            int r0 = r0 + 4
            char r0 = r9.charAt(r0)
            int r1 = r9.bp
            int r1 = r1 + r13
            char r1 = r9.charAt(r1)
            int r2 = r9.bp
            r3 = 6
            int r2 = r2 + r3
            char r2 = r9.charAt(r2)
            int r3 = r9.bp
            int r3 = r3 + 7
            char r3 = r9.charAt(r3)
            int r4 = r9.bp
            r5 = 8
            int r4 = r4 + r5
            char r22 = r9.charAt(r4)
            if (r0 != r12) goto L_0x0538
            if (r3 != r12) goto L_0x0538
            r4 = 1
            goto L_0x0539
        L_0x0538:
            r4 = 0
        L_0x0539:
            if (r4 == 0) goto L_0x0541
            r5 = 16
            if (r11 != r5) goto L_0x0541
            r12 = 1
            goto L_0x0542
        L_0x0541:
            r12 = 0
        L_0x0542:
            if (r4 == 0) goto L_0x054b
            r4 = 17
            if (r11 != r4) goto L_0x054b
            r23 = 1
            goto L_0x054d
        L_0x054b:
            r23 = 0
        L_0x054d:
            if (r23 != 0) goto L_0x055b
            if (r12 == 0) goto L_0x0552
            goto L_0x055b
        L_0x0552:
            r25 = r0
            r26 = r1
            r27 = r2
            r28 = r3
            goto L_0x056b
        L_0x055b:
            int r0 = r9.bp
            int r0 = r0 + 9
            char r0 = r9.charAt(r0)
            r28 = r0
            r25 = r1
            r26 = r2
            r27 = r22
        L_0x056b:
            r1 = r10
            r2 = r21
            r3 = r19
            r4 = r17
            r5 = r25
            r6 = r26
            r7 = r27
            r8 = r28
            boolean r0 = checkDate(r1, r2, r3, r4, r5, r6, r7, r8)
            if (r0 != 0) goto L_0x0581
            return r14
        L_0x0581:
            r0 = r9
            r1 = r10
            r2 = r21
            r3 = r19
            r4 = r17
            r5 = r25
            r6 = r26
            r7 = r27
            r8 = r28
            r0.setCalendar(r1, r2, r3, r4, r5, r6, r7, r8)
            r0 = 8
            if (r11 == r0) goto L_0x0665
            int r0 = r9.bp
            int r0 = r0 + 9
            char r0 = r9.charAt(r0)
            int r1 = r9.bp
            int r1 = r1 + 10
            char r1 = r9.charAt(r1)
            int r2 = r9.bp
            r3 = 11
            int r2 = r2 + r3
            char r2 = r9.charAt(r2)
            int r3 = r9.bp
            int r3 = r3 + 12
            char r7 = r9.charAt(r3)
            int r3 = r9.bp
            int r3 = r3 + 13
            char r3 = r9.charAt(r3)
            if (r23 == 0) goto L_0x05d7
            r4 = 84
            if (r1 != r4) goto L_0x05d7
            r4 = 58
            if (r3 != r4) goto L_0x05d7
            int r4 = r9.bp
            int r4 = r4 + 16
            char r4 = r9.charAt(r4)
            r5 = 90
            if (r4 == r5) goto L_0x05e5
        L_0x05d7:
            if (r12 == 0) goto L_0x05fd
            r4 = 32
            if (r1 == r4) goto L_0x05e1
            r4 = 84
            if (r1 != r4) goto L_0x05fd
        L_0x05e1:
            r4 = 58
            if (r3 != r4) goto L_0x05fd
        L_0x05e5:
            int r0 = r9.bp
            r1 = 14
            int r0 = r0 + r1
            char r1 = r9.charAt(r0)
            int r0 = r9.bp
            int r0 = r0 + 15
            char r0 = r9.charAt(r0)
            r3 = r0
            r0 = r7
            r4 = 48
            r7 = 48
            goto L_0x0601
        L_0x05fd:
            r4 = r3
            r3 = r2
            r2 = r22
        L_0x0601:
            r24 = r2
            r25 = r0
            r26 = r1
            r27 = r3
            r28 = r7
            r29 = r4
            boolean r5 = checkTime(r24, r25, r26, r27, r28, r29)
            if (r5 != 0) goto L_0x0614
            return r14
        L_0x0614:
            r5 = 17
            if (r11 != r5) goto L_0x0652
            if (r23 != 0) goto L_0x0652
            int r5 = r9.bp
            r6 = 14
            int r5 = r5 + r6
            char r5 = r9.charAt(r5)
            int r6 = r9.bp
            int r6 = r6 + 15
            char r6 = r9.charAt(r6)
            int r8 = r9.bp
            int r8 = r8 + 16
            char r8 = r9.charAt(r8)
            if (r5 < r15) goto L_0x0651
            r10 = 57
            if (r5 <= r10) goto L_0x063a
            goto L_0x0651
        L_0x063a:
            if (r6 < r15) goto L_0x0650
            if (r6 <= r10) goto L_0x063f
            goto L_0x0650
        L_0x063f:
            if (r8 < r15) goto L_0x064f
            if (r8 <= r10) goto L_0x0644
            goto L_0x064f
        L_0x0644:
            int r5 = r5 - r15
            int r5 = r5 * 100
            int r6 = r6 - r15
            int r6 = r6 * 10
            int r5 = r5 + r6
            int r8 = r8 - r15
            int r6 = r5 + r8
            goto L_0x0653
        L_0x064f:
            return r14
        L_0x0650:
            return r14
        L_0x0651:
            return r14
        L_0x0652:
            r6 = 0
        L_0x0653:
            int r2 = r2 - r15
            int r2 = r2 * 10
            int r0 = r0 - r15
            int r0 = r0 + r2
            int r1 = r1 - r15
            int r1 = r1 * 10
            int r3 = r3 - r15
            int r1 = r1 + r3
            int r7 = r7 - r15
            int r7 = r7 * 10
            int r4 = r4 - r15
            int r2 = r7 + r4
            r14 = r0
            goto L_0x0668
        L_0x0665:
            r1 = 0
            r2 = 0
            r6 = 0
        L_0x0668:
            java.util.Calendar r0 = r9.calendar
            r3 = 11
            r0.set(r3, r14)
            java.util.Calendar r0 = r9.calendar
            r3 = 12
            r0.set(r3, r1)
            java.util.Calendar r0 = r9.calendar
            r1 = 13
            r0.set(r1, r2)
            java.util.Calendar r0 = r9.calendar
            r1 = 14
            r0.set(r1, r6)
            r9.token = r13
            return r16
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexer.scanISO8601DateIfMatch(boolean, int):boolean");
    }

    /* access modifiers changed from: protected */
    public final void setTime(char c, char c2, char c3, char c4, char c5, char c6) {
        this.calendar.set(11, ((c - '0') * 10) + (c2 - '0'));
        this.calendar.set(12, ((c3 - '0') * 10) + (c4 - '0'));
        this.calendar.set(13, ((c5 - '0') * 10) + (c6 - '0'));
    }

    /* access modifiers changed from: protected */
    public final void setTimeZone(char c, char c2, char c3) {
        int i = (((c2 - '0') * 10) + (c3 - '0')) * 3600 * 1000;
        if (c == '-') {
            i = -i;
        }
        if (this.calendar.getTimeZone().getRawOffset() != i) {
            String[] availableIDs = TimeZone.getAvailableIDs(i);
            if (availableIDs.length > 0) {
                this.calendar.setTimeZone(TimeZone.getTimeZone(availableIDs[0]));
            }
        }
    }

    private void setCalendar(char c, char c2, char c3, char c4, char c5, char c6, char c7, char c8) {
        this.calendar = Calendar.getInstance(this.timeZone, this.locale);
        this.calendar.set(1, ((c - '0') * 1000) + ((c2 - '0') * 100) + ((c3 - '0') * 10) + (c4 - '0'));
        this.calendar.set(2, (((c5 - '0') * 10) + (c6 - '0')) - 1);
        this.calendar.set(5, ((c7 - '0') * 10) + (c8 - '0'));
    }

    public static final byte[] decodeFast(String str, int i, int i2) {
        int i3;
        int i4 = 0;
        if (i2 == 0) {
            return new byte[0];
        }
        int i5 = (i + i2) - 1;
        while (i < i5 && IA[str.charAt(i)] < 0) {
            i++;
        }
        while (i5 > 0 && IA[str.charAt(i5)] < 0) {
            i5--;
        }
        int i6 = str.charAt(i5) == '=' ? str.charAt(i5 + -1) == '=' ? 2 : 1 : 0;
        int i7 = (i5 - i) + 1;
        if (i2 > 76) {
            i3 = (str.charAt(76) == 13 ? i7 / 78 : 0) << 1;
        } else {
            i3 = 0;
        }
        int i8 = (((i7 - i3) * 6) >> 3) - i6;
        byte[] bArr = new byte[i8];
        int i9 = (i8 / 3) * 3;
        int i10 = i;
        int i11 = 0;
        int i12 = 0;
        while (i11 < i9) {
            int i13 = i10 + 1;
            int i14 = i13 + 1;
            int i15 = i14 + 1;
            int i16 = i15 + 1;
            int i17 = (IA[str.charAt(i10)] << 18) | (IA[str.charAt(i13)] << 12) | (IA[str.charAt(i14)] << 6) | IA[str.charAt(i15)];
            int i18 = i11 + 1;
            bArr[i11] = (byte) (i17 >> 16);
            int i19 = i18 + 1;
            bArr[i18] = (byte) (i17 >> 8);
            int i20 = i19 + 1;
            bArr[i19] = (byte) i17;
            if (i3 > 0) {
                i12++;
                if (i12 == 19) {
                    i10 = i16 + 2;
                    i12 = 0;
                    i11 = i20;
                }
            }
            i10 = i16;
            i11 = i20;
        }
        if (i11 < i8) {
            int i21 = 0;
            while (i10 <= i5 - i6) {
                i4 |= IA[str.charAt(i10)] << (18 - (i21 * 6));
                i21++;
                i10++;
            }
            int i22 = 16;
            while (i11 < i8) {
                bArr[i11] = (byte) (i4 >> i22);
                i22 -= 8;
                i11++;
            }
        }
        return bArr;
    }
}

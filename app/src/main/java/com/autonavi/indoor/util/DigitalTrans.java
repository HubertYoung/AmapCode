package com.autonavi.indoor.util;

public class DigitalTrans {
    public static String stringToAsciiString(String str) {
        int length = str.length();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            stringBuffer.append(Integer.toHexString(str.charAt(i)));
        }
        return stringBuffer.toString();
    }

    public static String hexStringToString(String str, int i) {
        int length = str.length() / i;
        StringBuffer stringBuffer = new StringBuffer();
        int i2 = 0;
        while (i2 < length) {
            int i3 = i2 * i;
            i2++;
            stringBuffer.append((char) hexStringToAlgorism(str.substring(i3, i2 * i)));
        }
        return stringBuffer.toString();
    }

    public static int hexStringToAlgorism(String str) {
        String upperCase = str.toUpperCase();
        int length = upperCase.length();
        int i = 0;
        for (int i2 = length; i2 > 0; i2--) {
            char charAt = upperCase.charAt(i2 - 1);
            i = (int) (((double) i) + (Math.pow(16.0d, (double) (length - i2)) * ((double) ((charAt < '0' || charAt > '9') ? charAt - '7' : charAt - '0'))));
        }
        return i;
    }

    public static String hexStringToBinary(String str) {
        String upperCase = str.toUpperCase();
        String str2 = "";
        int length = upperCase.length();
        for (int i = 0; i < length; i++) {
            char charAt = upperCase.charAt(i);
            switch (charAt) {
                case '0':
                    StringBuilder sb = new StringBuilder();
                    sb.append(str2);
                    sb.append("0000");
                    str2 = sb.toString();
                    break;
                case '1':
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(str2);
                    sb2.append("0001");
                    str2 = sb2.toString();
                    break;
                case '2':
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(str2);
                    sb3.append("0010");
                    str2 = sb3.toString();
                    break;
                case '3':
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(str2);
                    sb4.append("0011");
                    str2 = sb4.toString();
                    break;
                case '4':
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append(str2);
                    sb5.append("0100");
                    str2 = sb5.toString();
                    break;
                case '5':
                    StringBuilder sb6 = new StringBuilder();
                    sb6.append(str2);
                    sb6.append("0101");
                    str2 = sb6.toString();
                    break;
                case '6':
                    StringBuilder sb7 = new StringBuilder();
                    sb7.append(str2);
                    sb7.append("0110");
                    str2 = sb7.toString();
                    break;
                case '7':
                    StringBuilder sb8 = new StringBuilder();
                    sb8.append(str2);
                    sb8.append("0111");
                    str2 = sb8.toString();
                    break;
                case '8':
                    StringBuilder sb9 = new StringBuilder();
                    sb9.append(str2);
                    sb9.append("1000");
                    str2 = sb9.toString();
                    break;
                case '9':
                    StringBuilder sb10 = new StringBuilder();
                    sb10.append(str2);
                    sb10.append("1001");
                    str2 = sb10.toString();
                    break;
                default:
                    switch (charAt) {
                        case 'A':
                            StringBuilder sb11 = new StringBuilder();
                            sb11.append(str2);
                            sb11.append("1010");
                            str2 = sb11.toString();
                            break;
                        case 'B':
                            StringBuilder sb12 = new StringBuilder();
                            sb12.append(str2);
                            sb12.append("1011");
                            str2 = sb12.toString();
                            break;
                        case 'C':
                            StringBuilder sb13 = new StringBuilder();
                            sb13.append(str2);
                            sb13.append("1100");
                            str2 = sb13.toString();
                            break;
                        case 'D':
                            StringBuilder sb14 = new StringBuilder();
                            sb14.append(str2);
                            sb14.append("1101");
                            str2 = sb14.toString();
                            break;
                        case 'E':
                            StringBuilder sb15 = new StringBuilder();
                            sb15.append(str2);
                            sb15.append("1110");
                            str2 = sb15.toString();
                            break;
                        case 'F':
                            StringBuilder sb16 = new StringBuilder();
                            sb16.append(str2);
                            sb16.append("1111");
                            str2 = sb16.toString();
                            break;
                    }
            }
        }
        return str2;
    }

    public static String asciiStringToString(String str) {
        int length = str.length() / 2;
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            stringBuffer.append(String.valueOf((char) hexStringToAlgorism(str.substring(i2, i2 + 2))));
        }
        return stringBuffer.toString();
    }

    public static String algorismToHEXString(int i, int i2) {
        String hexString = Integer.toHexString(i);
        if (hexString.length() % 2 != 0) {
            hexString = "0".concat(String.valueOf(hexString));
        }
        return patchHexString(hexString.toUpperCase(), i2);
    }

    public static String bytetoString(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b : bArr) {
            stringBuffer.append((char) b);
        }
        return stringBuffer.toString();
    }

    public static int binaryToAlgorism(String str) {
        int length = str.length();
        int i = 0;
        for (int i2 = length; i2 > 0; i2--) {
            i = (int) (((double) i) + (Math.pow(2.0d, (double) (length - i2)) * ((double) (str.charAt(i2 - 1) - '0'))));
        }
        return i;
    }

    public static String algorismToHEXString(int i) {
        String hexString = Integer.toHexString(i);
        if (hexString.length() % 2 != 0) {
            hexString = "0".concat(String.valueOf(hexString));
        }
        return hexString.toUpperCase();
    }

    public static String patchHexString(String str, int i) {
        String str2 = "";
        for (int i2 = 0; i2 < i - str.length(); i2++) {
            str2 = "0".concat(String.valueOf(str2));
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append(str);
        return sb.toString().substring(0, i);
    }

    public static int parseToInt(String str, int i, int i2) {
        try {
            return Integer.parseInt(str, i2);
        } catch (NumberFormatException unused) {
            return i;
        }
    }

    public static int parseToInt(String str, int i) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException unused) {
            return i;
        }
    }

    public static byte[] hexStringToByte(String str) {
        int length = str.length() / 2;
        byte[] bArr = new byte[length];
        String hexStringToBinary = hexStringToBinary(str);
        int i = 0;
        while (i < length) {
            int i2 = i * 8;
            int i3 = i + 1;
            bArr[i] = (byte) binaryToAlgorism(hexStringToBinary.substring(i2 + 1, i3 * 8));
            if (hexStringToBinary.charAt(i2) == '1') {
                bArr[i] = (byte) (0 - bArr[i]);
            }
            i = i3;
        }
        return bArr;
    }

    public static final byte[] hex2byte(String str) throws IllegalArgumentException {
        if (str.length() % 2 != 0) {
            throw new IllegalArgumentException();
        }
        char[] charArray = str.toCharArray();
        byte[] bArr = new byte[(str.length() / 2)];
        int length = str.length();
        int i = 0;
        int i2 = 0;
        while (i < length) {
            StringBuilder sb = new StringBuilder();
            int i3 = i + 1;
            sb.append(charArray[i]);
            sb.append(charArray[i3]);
            bArr[i2] = Integer.valueOf(Integer.parseInt(sb.toString(), 16) & 255).byteValue();
            i = i3 + 1;
            i2++;
        }
        return bArr;
    }

    public static final String byte2hex(byte[] bArr) {
        if (bArr == null) {
            throw new IllegalArgumentException("Argument b ( byte array ) is null! ");
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b : bArr) {
            String hexString = Integer.toHexString(b & 255);
            if (hexString.length() == 1) {
                stringBuffer.append("0".concat(String.valueOf(hexString)));
            } else {
                stringBuffer.append(hexString);
            }
        }
        return stringBuffer.toString().toUpperCase();
    }
}

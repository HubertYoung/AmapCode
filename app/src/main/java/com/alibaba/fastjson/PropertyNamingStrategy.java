package com.alibaba.fastjson;

public enum PropertyNamingStrategy {
    CamelCase,
    PascalCase,
    SnakeCase,
    KebabCase;

    public final String translate(String str) {
        int i = 0;
        switch (this) {
            case SnakeCase:
                StringBuilder sb = new StringBuilder();
                while (i < str.length()) {
                    char charAt = str.charAt(i);
                    if (charAt < 'A' || charAt > 'Z') {
                        sb.append(charAt);
                    } else {
                        char c = (char) (charAt + ' ');
                        if (i > 0) {
                            sb.append('_');
                        }
                        sb.append(c);
                    }
                    i++;
                }
                return sb.toString();
            case KebabCase:
                StringBuilder sb2 = new StringBuilder();
                while (i < str.length()) {
                    char charAt2 = str.charAt(i);
                    if (charAt2 < 'A' || charAt2 > 'Z') {
                        sb2.append(charAt2);
                    } else {
                        char c2 = (char) (charAt2 + ' ');
                        if (i > 0) {
                            sb2.append('-');
                        }
                        sb2.append(c2);
                    }
                    i++;
                }
                return sb2.toString();
            case PascalCase:
                char charAt3 = str.charAt(0);
                if (charAt3 < 'a' || charAt3 > 'z') {
                    return str;
                }
                char[] charArray = str.toCharArray();
                charArray[0] = (char) (charArray[0] - ' ');
                return new String(charArray);
            case CamelCase:
                char charAt4 = str.charAt(0);
                if (charAt4 < 'A' || charAt4 > 'Z') {
                    return str;
                }
                char[] charArray2 = str.toCharArray();
                charArray2[0] = (char) (charArray2[0] + ' ');
                return new String(charArray2);
            default:
                return str;
        }
    }
}

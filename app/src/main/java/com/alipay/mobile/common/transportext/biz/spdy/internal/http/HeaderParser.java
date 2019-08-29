package com.alipay.mobile.common.transportext.biz.spdy.internal.http;

final class HeaderParser {

    public interface CacheControlHandler {
        void handle(String str, String str2);
    }

    public static void parseCacheControl(String value, CacheControlHandler handler) {
        String parameter;
        int pos = 0;
        while (pos < value.length()) {
            int tokenStart = pos;
            int pos2 = skipUntil(value, pos, "=,;");
            String directive = value.substring(tokenStart, pos2).trim();
            if (pos2 == value.length() || value.charAt(pos2) == ',' || value.charAt(pos2) == ';') {
                pos = pos2 + 1;
                handler.handle(directive, null);
            } else {
                int pos3 = skipWhitespace(value, pos2 + 1);
                if (pos3 >= value.length() || value.charAt(pos3) != '\"') {
                    int parameterStart = pos3;
                    pos = skipUntil(value, pos3, ",;");
                    parameter = value.substring(parameterStart, pos).trim();
                } else {
                    int pos4 = pos3 + 1;
                    int parameterStart2 = pos4;
                    int pos5 = skipUntil(value, pos4, "\"");
                    parameter = value.substring(parameterStart2, pos5);
                    pos = pos5 + 1;
                }
                handler.handle(directive, parameter);
            }
        }
    }

    public static int skipUntil(String input, int pos, String characters) {
        while (pos < input.length() && characters.indexOf(input.charAt(pos)) == -1) {
            pos++;
        }
        return pos;
    }

    public static int skipWhitespace(String input, int pos) {
        while (pos < input.length()) {
            char c = input.charAt(pos);
            if (c != ' ' && c != 9) {
                break;
            }
            pos++;
        }
        return pos;
    }

    public static int parseSeconds(String value) {
        try {
            long seconds = Long.parseLong(value);
            if (seconds > 2147483647L) {
                return Integer.MAX_VALUE;
            }
            if (seconds < 0) {
                return 0;
            }
            return (int) seconds;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private HeaderParser() {
    }
}

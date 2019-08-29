package org.json.alipay.inside;

import java.io.IOException;
import java.io.Writer;

public class JSONWriter {
    private static final int maxdepth = 20;
    private boolean comma = false;
    protected char mode = 'i';
    private char[] stack = new char[20];
    private int top = 0;
    protected Writer writer;

    public JSONWriter(Writer writer2) {
        this.writer = writer2;
    }

    private JSONWriter append(String str) throws JSONException {
        if (str == null) {
            throw new JSONException((String) "Null pointer");
        } else if (this.mode == 'o' || this.mode == 'a') {
            try {
                if (this.comma && this.mode == 'a') {
                    this.writer.write(44);
                }
                this.writer.write(str);
                if (this.mode == 'o') {
                    this.mode = 'k';
                }
                this.comma = true;
                return this;
            } catch (IOException e) {
                throw new JSONException((Throwable) e);
            }
        } else {
            throw new JSONException((String) "Value out of sequence.");
        }
    }

    public JSONWriter array() throws JSONException {
        if (this.mode == 'i' || this.mode == 'o' || this.mode == 'a') {
            push('a');
            append("[");
            this.comma = false;
            return this;
        }
        throw new JSONException((String) "Misplaced array.");
    }

    private JSONWriter end(char c, char c2) throws JSONException {
        if (this.mode != c) {
            throw new JSONException(c == 'o' ? "Misplaced endObject." : "Misplaced endArray.");
        }
        pop(c);
        try {
            this.writer.write(c2);
            this.comma = true;
            return this;
        } catch (IOException e) {
            throw new JSONException((Throwable) e);
        }
    }

    public JSONWriter endArray() throws JSONException {
        return end('a', ']');
    }

    public JSONWriter endObject() throws JSONException {
        return end('k', '}');
    }

    public JSONWriter key(String str) throws JSONException {
        if (str == null) {
            throw new JSONException((String) "Null key.");
        } else if (this.mode == 'k') {
            try {
                if (this.comma) {
                    this.writer.write(44);
                }
                this.writer.write(JSONObject.quote(str));
                this.writer.write(58);
                this.comma = false;
                this.mode = 'o';
                return this;
            } catch (IOException e) {
                throw new JSONException((Throwable) e);
            }
        } else {
            throw new JSONException((String) "Misplaced key.");
        }
    }

    public JSONWriter object() throws JSONException {
        if (this.mode == 'i') {
            this.mode = 'o';
        }
        if (this.mode == 'o' || this.mode == 'a') {
            append("{");
            push('k');
            this.comma = false;
            return this;
        }
        throw new JSONException((String) "Misplaced object.");
    }

    private void pop(char c) throws JSONException {
        if (this.top <= 0 || this.stack[this.top - 1] != c) {
            throw new JSONException((String) "Nesting error.");
        }
        this.top--;
        this.mode = this.top == 0 ? 'd' : this.stack[this.top - 1];
    }

    private void push(char c) throws JSONException {
        if (this.top >= 20) {
            throw new JSONException((String) "Nesting too deep.");
        }
        this.stack[this.top] = c;
        this.mode = c;
        this.top++;
    }

    public JSONWriter value(boolean z) throws JSONException {
        return append(z ? "true" : "false");
    }

    public JSONWriter value(double d) throws JSONException {
        return value((Object) Double.valueOf(d));
    }

    public JSONWriter value(long j) throws JSONException {
        return append(Long.toString(j));
    }

    public JSONWriter value(Object obj) throws JSONException {
        return append(JSONObject.valueToString(obj));
    }
}

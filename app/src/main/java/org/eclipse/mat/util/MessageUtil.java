package org.eclipse.mat.util;

import java.text.MessageFormat;
import org.eclipse.mat.hprof.Messages;

public final class MessageUtil {
    public static String format(Messages messages, Object... objArr) {
        if (objArr.length == 0) {
            return messages.pattern;
        }
        return MessageFormat.format(messages.pattern, objArr);
    }

    private MessageUtil() {
        throw new AssertionError();
    }
}

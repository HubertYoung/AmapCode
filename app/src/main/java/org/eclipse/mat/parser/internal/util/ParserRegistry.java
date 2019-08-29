package org.eclipse.mat.parser.internal.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.eclipse.mat.hprof.HprofHeapObjectReader;
import org.eclipse.mat.hprof.HprofIndexBuilder;
import org.eclipse.mat.parser.IIndexBuilder;
import org.eclipse.mat.parser.IObjectReader;
import org.eclipse.mat.snapshot.SnapshotFormat;

public class ParserRegistry {
    public static final String INDEX_BUILDER = "indexBuilder";
    public static final String OBJECT_READER = "objectReader";
    private static ParserRegistry instance = new ParserRegistry();
    public List<Parser> parsers = new ArrayList();

    public static class Parser {
        private String id;
        private IIndexBuilder indexBuilder;
        private IObjectReader objectReader;
        /* access modifiers changed from: private */
        public Pattern[] patterns;
        private SnapshotFormat snapshotFormat;

        public Parser(String str, SnapshotFormat snapshotFormat2, IObjectReader iObjectReader, IIndexBuilder iIndexBuilder) {
            this.id = str;
            this.snapshotFormat = snapshotFormat2;
            this.patterns = new Pattern[snapshotFormat2.getFileExtensions().length];
            for (int i = 0; i < snapshotFormat2.getFileExtensions().length; i++) {
                Pattern[] patternArr = this.patterns;
                StringBuilder sb = new StringBuilder("(.*\\.)((?i)");
                sb.append(snapshotFormat2.getFileExtensions()[i]);
                sb.append(")(\\.[0-9]*)?");
                patternArr[i] = Pattern.compile(sb.toString());
            }
            this.objectReader = iObjectReader;
            this.indexBuilder = iIndexBuilder;
        }

        public IObjectReader getObjectReader() {
            return this.objectReader;
        }

        public IIndexBuilder getIndexBuilder() {
            return this.indexBuilder;
        }

        public String getId() {
            return this.id;
        }

        public String getUniqueIdentifier() {
            return this.id;
        }

        public SnapshotFormat getSnapshotFormat() {
            return this.snapshotFormat;
        }
    }

    static {
        addParser("hprof", "hprof", new String[]{"hprof", "bin"}, new HprofHeapObjectReader(), new HprofIndexBuilder());
    }

    private ParserRegistry() {
    }

    public static void addParser(String str, String str2, String[] strArr, IObjectReader iObjectReader, IIndexBuilder iIndexBuilder) {
        instance.parsers.add(new Parser(str, new SnapshotFormat(str2, strArr), iObjectReader, iIndexBuilder));
    }

    public static Parser lookupParser(String str) {
        for (Parser next : instance.parsers) {
            if (str.equals(next.getUniqueIdentifier())) {
                return next;
            }
        }
        return null;
    }

    public static List<Parser> matchParser(String str) {
        ArrayList arrayList = new ArrayList();
        for (Parser next : instance.parsers) {
            Pattern[] access$000 = next.patterns;
            int length = access$000.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                } else if (access$000[i].matcher(str).matches()) {
                    arrayList.add(next);
                    break;
                } else {
                    i++;
                }
            }
        }
        return arrayList;
    }

    public static List<Parser> allParsers() {
        return instance.parsers;
    }
}

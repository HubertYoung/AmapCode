package org.androidannotations.api.sharedpreferences;

import android.util.Log;
import android.util.Xml;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

public final class SetXmlSerializer {
    private SetXmlSerializer() {
    }

    public static String serialize(Set<String> set) {
        if (set == null) {
            set = Collections.emptySet();
        }
        StringWriter writer = new StringWriter();
        XmlSerializer serializer = Xml.newSerializer();
        try {
            serializer.setOutput(writer);
            serializer.startTag("", "AA_set");
            for (String string : set) {
                serializer.startTag("", "AA_string").text(string).endTag("", "AA_string");
            }
            serializer.endTag("", "AA_set").endDocument();
        } catch (IOException | IllegalArgumentException | IllegalStateException e) {
        }
        return writer.toString();
    }

    public static Set<String> deserialize(String data) {
        Set stringSet = new TreeSet();
        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(new StringReader(data));
            parser.next();
            parser.require(2, "", "AA_set");
            while (parser.next() != 3) {
                parser.require(2, "", "AA_string");
                parser.next();
                parser.require(4, null, null);
                stringSet.add(parser.getText());
                parser.next();
                parser.require(3, null, "AA_string");
            }
            return stringSet;
        } catch (XmlPullParserException e) {
            Log.w("getStringSet", e);
            return null;
        } catch (IOException e2) {
            Log.w("getStringSet", e2);
            return null;
        }
    }
}

package com.quasardevelopment.bodyarchitect.client.wcf.Marshals;

import org.ksoap2.serialization.Marshal;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 13.04.13
 * Time: 19:51
 * To change this template use File | Settings | File Templates.
 */

public class MarshalGuid implements Marshal
{

    public Object readInstance(XmlPullParser parser, String namespace, String name,
                               PropertyInfo expected) throws IOException, XmlPullParserException {

        return UUID.fromString(parser.nextText());
    }


    public void register(SoapSerializationEnvelope cm) {
        cm.addMapping(cm.xsd, "guid", UUID.class, this);

    }


    public void writeInstance(XmlSerializer writer, Object obj) throws IOException {
        writer.text(obj.toString());
    }
}


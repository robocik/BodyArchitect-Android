package com.quasardevelopment.bodyarchitect.client.wcf.Marshals;

import org.joda.time.DateTime;
import org.ksoap2.serialization.Marshal;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 21.04.13
 * Time: 12:16
 * To change this template use File | Settings | File Templates.
 */
public class MarshalDateTime implements Marshal
{

    public Object readInstance(XmlPullParser parser, String namespace, String name,
                               PropertyInfo expected) throws IOException, XmlPullParserException {

        return DateTime.parse(parser.nextText());
    }


    public void register(SoapSerializationEnvelope cm) {
        cm.addMapping(cm.xsd, "datetime", DateTime.class, this);

    }


    public void writeInstance(XmlSerializer writer, Object obj) throws IOException {
        writer.text(obj.toString());
    }
}

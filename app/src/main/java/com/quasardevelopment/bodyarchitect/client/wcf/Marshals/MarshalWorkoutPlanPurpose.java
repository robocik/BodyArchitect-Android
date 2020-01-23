package com.quasardevelopment.bodyarchitect.client.wcf.Marshals;

import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums;
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
 * Date: 02.10.13
 * Time: 20:04
 * To change this template use File | Settings | File Templates.
 */
public class MarshalWorkoutPlanPurpose implements Marshal
{
    public Object readInstance(XmlPullParser arg0, String arg1, String arg2,
                               PropertyInfo arg3) throws IOException, XmlPullParserException {
        return WS_Enums.WorkoutPlanPurpose.valueOf(arg0.nextText());
    }

    public void register(SoapSerializationEnvelope arg0) {
        arg0.addMapping("http://tempuri.org/", "WorkoutPlanPurpose", WS_Enums.WorkoutPlanPurpose.class,new MarshalWorkoutPlanPurpose());
    }

    public void writeInstance(XmlSerializer arg0, Object arg1)
            throws IOException {
        arg0.text(((WS_Enums.WorkoutPlanPurpose)arg1).name());
    }
}

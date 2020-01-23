package com.quasardevelopment.bodyarchitect.client.wcf;


import com.quasardevelopment.bodyarchitect.client.wcf.Marshals.*;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WcfInterfaces;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalDate;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Vector;

public class ExtendedSoapSerializationEnvelope extends SoapSerializationEnvelope {

    HashMap<Object,String> referencesTable = new HashMap<Object, String>();
    private final String MsNs="http://schemas.microsoft.com/2003/10/Serialization/";
    protected static final int QNAME_NAMESPACE = 0;
    private static final String TYPE_LABEL = "type";
    public ExtendedSoapSerializationEnvelope() {
        super(SoapEnvelope.VER11);
        implicitTypes = true;
        dotNet = true;
        WcfConstans.AddStandardHeaders(this);

        new MarshalDate().register(this);
        new MarshalGuid().register(this);
        new MarshalDateTime().register(this);
        new MarshalExerciseSearchCriteriaGroup().register(this);
        new MarshalExerciseType().register(this);
        new MarshalFloat().register(this);
        new MarshalUserSearchGroup().register(this);
        new MarshalGender().register(this);
        new MarshalVoteObject().register(this);
        new MarshalWorkoutPlanSearchCriteriaGroup().register(this);
        new MarshalWorkoutPlanPurpose().register(this);
        new MarshalTrainingType().register(this);
        new MarshalTrainingPlanDifficult().register(this);

    }



    @Override
    protected void writeProperty(XmlSerializer writer, Object obj,
                                 PropertyInfo type) throws IOException {
        if (obj == null) {
            writer.attribute(xsi, "nil", "true");
            return;
        }

        if(referencesTable.containsKey(obj))
        {    //this object has been already serialized so use Ref instead
            String id=referencesTable.get(obj);
            writer.attribute(MsNs, "Ref",id);
              return;
        }
        else
        {
            if(obj instanceof WcfInterfaces.IReferenceObject)
            {
                String id=String.format("i%d", referencesTable.size() + 1);
                referencesTable.put(obj,id);
                writer.attribute(MsNs, "Id",id);
            }
//            super.writeProperty(writer,obj,type);

            Object[] qName = getInfo(null, obj);
            if (!type.multiRef && qName[2] == null) {
                if (!implicitTypes || (obj.getClass() != type.type && !(obj instanceof Vector ) && type.type!=String.class)) {
                    //String prefix = writer.getPrefix((String) qName[QNAME_NAMESPACE], true);
                    String prefix = writer.getPrefix(WcfConstans.ServiceNamespace, true);
                    writer.attribute(xsi, TYPE_LABEL, prefix + ":" + obj.getClass().getSimpleName());
                }
                //super.writeProperty(writer,obj,type);

                try {
                    Method method = this.getClass().getSuperclass().getDeclaredMethod("writeElement",org.xmlpull.v1.XmlSerializer.class,Object.class,PropertyInfo.class,Object.class);
                    method.setAccessible(true);
                    method.invoke(this,writer, obj, type, qName[QNAME_MARSHAL]);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (IllegalAccessException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (InvocationTargetException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                //writeElement(writer, obj, type, qName[QNAME_MARSHAL]);
            } else {
                super.writeProperty(writer, obj, type);
            }
        }



    }

//    private void writeElement(XmlSerializer writer, Object element, PropertyInfo type, Object marshal)
//            throws IOException {
//        if (marshal != null) {
//            ((Marshal) marshal).writeInstance(writer, element);
//        } else if (element instanceof SoapObject) {
//            writeObjectBody(writer, (SoapObject) element);
//        } else if (element instanceof KvmSerializable) {
//            writeObjectBody(writer, (KvmSerializable) element);
//        } else if (element instanceof Vector) {
//            writeVectorBody(writer, (Vector) element, type.elementType);
//        } else {
//            throw new RuntimeException("Cannot serialize: " + element);
//        }
//    }




}
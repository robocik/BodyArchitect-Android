package com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService;

//------------------------------------------------------------------------------
// <wsdl2code-generated>
//    This code was generated by http://www.wsdl2code.com version  2.4
//
// Date Of Creation: 4/11/2013 5:22:00 PM
//    Please dont change this code, regeneration will override your changes
//</wsdl2code-generated>
//
//------------------------------------------------------------------------------
//
//This source code was auto-generated by Wsdl2Code  Version
//

import com.quasardevelopment.bodyarchitect.client.wcf.ReferencesManager;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.text.ParseException;
import java.util.Hashtable;

public class WeightReperitionReportResultItem implements KvmSerializable {
    
    public ExerciseLightDTO exercise;
    public double repetitions;
    public boolean repetitionsSpecified;
    public String strengthTrainingItemId;
    public double weight;
    public boolean weightSpecified;
    
    public WeightReperitionReportResultItem(){}
    
    public WeightReperitionReportResultItem(SoapObject soapObject,ReferencesManager referencesTable)   throws ParseException
    {
        if (soapObject == null)
            return;
        if (soapObject.hasProperty("Exercise"))
        {
            SoapObject j = (SoapObject)soapObject.getProperty("Exercise");
            exercise =  new ExerciseLightDTO (j,referencesTable);
            
        }
        if (soapObject.hasProperty("Repetitions"))
        {
            Object obj = soapObject.getProperty("Repetitions");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("Repetitions");
                repetitions = Double.parseDouble(j.toString());
            }else if (obj!= null && obj instanceof Number){
                repetitions = (Integer) soapObject.getProperty("Repetitions");
            }
        }
        if (soapObject.hasProperty("RepetitionsSpecified"))
        {
            Object obj = soapObject.getProperty("RepetitionsSpecified");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("RepetitionsSpecified");
                repetitionsSpecified = Boolean.parseBoolean(j.toString());
            }else if (obj!= null && obj instanceof Boolean){
                repetitionsSpecified = (Boolean) soapObject.getProperty("RepetitionsSpecified");
            }
        }
        if (soapObject.hasProperty("StrengthTrainingItemId"))
        {
            Object obj = soapObject.getProperty("StrengthTrainingItemId");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("StrengthTrainingItemId");
                strengthTrainingItemId = j.toString();
            }else if (obj!= null && obj instanceof String){
                strengthTrainingItemId = (String) soapObject.getProperty("StrengthTrainingItemId");
            }
        }
        if (soapObject.hasProperty("Weight"))
        {
            Object obj = soapObject.getProperty("Weight");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("Weight");
                weight = Double.parseDouble(j.toString());
            }else if (obj!= null && obj instanceof Number){
                weight = (Integer) soapObject.getProperty("Weight");
            }
        }
        if (soapObject.hasProperty("WeightSpecified"))
        {
            Object obj = soapObject.getProperty("WeightSpecified");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("WeightSpecified");
                weightSpecified = Boolean.parseBoolean(j.toString());
            }else if (obj!= null && obj instanceof Boolean){
                weightSpecified = (Boolean) soapObject.getProperty("WeightSpecified");
            }
        }
    }
    @Override
    public Object getProperty(int arg0) {
        switch(arg0){
            case 0:
                return exercise;
            case 1:
                return repetitions;
            case 2:
                return repetitionsSpecified;
            case 3:
                return strengthTrainingItemId;
            case 4:
                return weight;
            case 5:
                return weightSpecified;
        }
        return null;
    }
    
    @Override
    public int getPropertyCount() {
        return 6;
    }
    
    @Override
    public void getPropertyInfo(int index, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo info) {
        switch(index){
            case 0:
                info.type = ExerciseLightDTO.class;
                info.name = "Exercise";
                break;
            case 1:
                info.type = Double.class;
                info.name = "Repetitions";
                break;
            case 2:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "RepetitionsSpecified";
                break;
            case 3:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "StrengthTrainingItemId";
                break;
            case 4:
                info.type = Double.class;
                info.name = "Weight";
                break;
            case 5:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "WeightSpecified";
                break;
        }
    }

    

    @Override
    public void setProperty(int arg0, Object arg1) {
    }
    
}
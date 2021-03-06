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

import java.io.Serializable;
import java.text.ParseException;
import java.util.Hashtable;

public class ExerciseRecordsReportResultItem implements KvmSerializable,Serializable {
    
    public String customerId;
    public ExerciseLightDTO exercise;
    public double maxWeight;
    public boolean maxWeightSpecified;
    public int repetitions;
    public boolean repetitionsSpecified;
    public String serieId;
    public String trainingDate;
    public boolean trainingDateSpecified;
    public UserDTO user;
    
    public ExerciseRecordsReportResultItem(){}
    
    public ExerciseRecordsReportResultItem(SoapObject soapObject,ReferencesManager referencesTable)      throws ParseException
    {
        if (soapObject == null)
            return;
        if (soapObject.hasProperty("CustomerId"))
        {
            Object obj = soapObject.getProperty("CustomerId");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("CustomerId");
                customerId = j.toString();
            }else if (obj!= null && obj instanceof String){
                customerId = (String) soapObject.getProperty("CustomerId");
            }
        }
        if (soapObject.hasProperty("Exercise"))
        {
            SoapObject j = (SoapObject)soapObject.getProperty("Exercise");
            exercise =  new ExerciseLightDTO (j,referencesTable);
            
        }
        if (soapObject.hasProperty("MaxWeight"))
        {
            Object obj = soapObject.getProperty("MaxWeight");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("MaxWeight");
                maxWeight = Double.parseDouble(j.toString());
            }else if (obj!= null && obj instanceof Number){
                maxWeight = (Integer) soapObject.getProperty("MaxWeight");
            }
        }
        if (soapObject.hasProperty("MaxWeightSpecified"))
        {
            Object obj = soapObject.getProperty("MaxWeightSpecified");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("MaxWeightSpecified");
                maxWeightSpecified = Boolean.parseBoolean(j.toString());
            }else if (obj!= null && obj instanceof Boolean){
                maxWeightSpecified = (Boolean) soapObject.getProperty("MaxWeightSpecified");
            }
        }
        if (soapObject.hasProperty("Repetitions"))
        {
            Object obj = soapObject.getProperty("Repetitions");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("Repetitions");
                repetitions = Integer.parseInt(j.toString());
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
        if (soapObject.hasProperty("SerieId"))
        {
            Object obj = soapObject.getProperty("SerieId");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("SerieId");
                serieId = j.toString();
            }else if (obj!= null && obj instanceof String){
                serieId = (String) soapObject.getProperty("SerieId");
            }
        }
        if (soapObject.hasProperty("TrainingDate"))
        {
            Object obj = soapObject.getProperty("TrainingDate");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("TrainingDate");
                trainingDate = j.toString();
            }else if (obj!= null && obj instanceof String){
                trainingDate = (String) soapObject.getProperty("TrainingDate");
            }
        }
        if (soapObject.hasProperty("TrainingDateSpecified"))
        {
            Object obj = soapObject.getProperty("TrainingDateSpecified");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("TrainingDateSpecified");
                trainingDateSpecified = Boolean.parseBoolean(j.toString());
            }else if (obj!= null && obj instanceof Boolean){
                trainingDateSpecified = (Boolean) soapObject.getProperty("TrainingDateSpecified");
            }
        }
        if (soapObject.hasProperty("User"))
        {
            SoapObject j = (SoapObject)soapObject.getProperty("User");
            user = (UserDTO)referencesTable.get(j,UserDTO.class);
            
        }
    }
    @Override
    public Object getProperty(int arg0) {
        switch(arg0){
            case 0:
                return customerId;
            case 1:
                return exercise;
            case 2:
                return maxWeight;
            case 3:
                return maxWeightSpecified;
            case 4:
                return repetitions;
            case 5:
                return repetitionsSpecified;
            case 6:
                return serieId;
            case 7:
                return trainingDate;
            case 8:
                return trainingDateSpecified;
            case 9:
                return user;
        }
        return null;
    }
    
    @Override
    public int getPropertyCount() {
        return 10;
    }
    
    @Override
    public void getPropertyInfo(int index, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo info) {
        switch(index){
            case 0:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "CustomerId";
                break;
            case 1:
                info.type = ExerciseLightDTO.class;
                info.name = "Exercise";
                break;
            case 2:
                info.type = Double.class;
                info.name = "MaxWeight";
                break;
            case 3:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "MaxWeightSpecified";
                break;
            case 4:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "Repetitions";
                break;
            case 5:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "RepetitionsSpecified";
                break;
            case 6:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "SerieId";
                break;
            case 7:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "TrainingDate";
                break;
            case 8:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "TrainingDateSpecified";
                break;
            case 9:
                info.type = UserDTO.class;
                info.name = "User";
                break;
        }
    }

    

    @Override
    public void setProperty(int arg0, Object arg1) {
    }
    
}

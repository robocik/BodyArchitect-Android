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
import com.quasardevelopment.bodyarchitect.client.wcf.WcfConstans;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Hashtable;
import java.util.UUID;

public class WymiaryDTO extends BAGlobalObject implements KvmSerializable,Serializable, WcfInterfaces.IReferenceObject {
    
    public double height;
    public double klatka;
    public double leftBiceps;
    public double leftForearm;
    public double leftUdo;
    public double pas;
    public double rightBiceps;
    public double rightForearm;
    public double rightUdo;
    public BATimeDTO time = new BATimeDTO();
    public double weight;
    
    public WymiaryDTO(){}
    
    public WymiaryDTO(SoapObject soapObject,ReferencesManager referencesTable)   throws ParseException
    {
        if (soapObject == null)
            return;
        if (soapObject.hasAttribute("Id"))
        {
            Object obj = soapObject.getAttribute("Id");
            referencesTable.Add((String) obj, this);
        }
        if (soapObject.hasProperty("Height"))
        {
            Object obj = soapObject.getProperty("Height");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("Height");
                height = Double.parseDouble(j.toString());
            }else if (obj!= null && obj instanceof Number){
                height = (Double) soapObject.getProperty("Height");
            }
        }
        if (soapObject.hasProperty("Klatka"))
        {
            Object obj = soapObject.getProperty("Klatka");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("Klatka");
                klatka = Double.parseDouble(j.toString());
            }else if (obj!= null && obj instanceof Number){
                klatka = (Double) soapObject.getProperty("Klatka");
            }
        }
        if (soapObject.hasProperty("LeftBiceps"))
        {
            Object obj = soapObject.getProperty("LeftBiceps");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("LeftBiceps");
                leftBiceps = Double.parseDouble(j.toString());
            }else if (obj!= null && obj instanceof Number){
                leftBiceps = (Double) soapObject.getProperty("LeftBiceps");
            }
        }
        if (soapObject.hasProperty("LeftForearm"))
        {
            Object obj = soapObject.getProperty("LeftForearm");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("LeftForearm");
                leftForearm = Double.parseDouble(j.toString());
            }else if (obj!= null && obj instanceof Number){
                leftForearm = (Double) soapObject.getProperty("LeftForearm");
            }
        }
        if (soapObject.hasProperty("LeftUdo"))
        {
            Object obj = soapObject.getProperty("LeftUdo");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("LeftUdo");
                leftUdo = Double.parseDouble(j.toString());
            }else if (obj!= null && obj instanceof Number){
                leftUdo = (Double) soapObject.getProperty("LeftUdo");
            }
        }
        if (soapObject.hasProperty("Pas"))
        {
            Object obj = soapObject.getProperty("Pas");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("Pas");
                pas = Double.parseDouble(j.toString());
            }else if (obj!= null && obj instanceof Number){
                pas = (Double) soapObject.getProperty("Pas");
            }
        }
        if (soapObject.hasProperty("RightBiceps"))
        {
            Object obj = soapObject.getProperty("RightBiceps");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("RightBiceps");
                rightBiceps = Double.parseDouble(j.toString());
            }else if (obj!= null && obj instanceof Number){
                rightBiceps = (Double) soapObject.getProperty("RightBiceps");
            }
        }
        if (soapObject.hasProperty("RightForearm"))
        {
            Object obj = soapObject.getProperty("RightForearm");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("RightForearm");
                rightForearm = Double.parseDouble(j.toString());
            }else if (obj!= null && obj instanceof Number){
                rightForearm = (Double) soapObject.getProperty("RightForearm");
            }
        }
        if (soapObject.hasProperty("RightUdo"))
        {
            Object obj = soapObject.getProperty("RightUdo");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("RightUdo");
                rightUdo = Double.parseDouble(j.toString());
            }else if (obj!= null && obj instanceof Number){
                rightUdo = (Double) soapObject.getProperty("RightUdo");
            }
        }
        if (soapObject.hasProperty("Time"))
        {
            SoapObject j = (SoapObject)soapObject.getProperty("Time");
            time =  new BATimeDTO (j);
            
        }
        if (soapObject.hasProperty("Weight"))
        {
            Object obj = soapObject.getProperty("Weight");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("Weight");
                weight = Double.parseDouble(j.toString());
            }else if (obj!= null && obj instanceof Number){
                weight = (Double) soapObject.getProperty("Weight");
            }
        }
        if (soapObject.hasProperty("GlobalId"))
        {
            Object obj = soapObject.getProperty("GlobalId");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("GlobalId");
                globalId = UUID.fromString(j.toString());
            }else if (obj!= null && obj instanceof String){
                globalId = UUID.fromString((String) soapObject.getProperty("GlobalId"));
            }
        }
    }
    @Override
    public Object getProperty(int arg0) {
        switch(arg0){
            case 0:
                return globalId;
            case 1:
                return height;
            case 2:
                return klatka;
            case 3:
                return leftBiceps;
            case 4:
                return leftForearm;
            case 5:
                return leftUdo;
            case 6:
                return pas;
            case 7:
                return rightBiceps;
            case 8:
                return rightForearm;
            case 9:
                return rightUdo;
            case 10:
                return time;
            case 11:
                return weight;


        }
        return null;
    }
    
    @Override
    public int getPropertyCount() {
        return 12;
    }
    
    @Override
    public void getPropertyInfo(int index, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo info) {
        switch(index){
            case 0:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "GlobalId";
                break;
            case 1:
                info.type = Double.class;
                info.name = "Height";
                break;
            case 2:
                info.type = Double.class;
                info.name = "Klatka";
                break;
            case 3:
                info.type = Double.class;
                info.name = "LeftBiceps";
                break;
            case 4:
                info.type = Double.class;
                info.name = "LeftForearm";
                break;
            case 5:
                info.type = Double.class;
                info.name = "LeftUdo";
                break;
            case 6:
                info.type = Double.class;
                info.name = "Pas";
                break;
            case 7:
                info.type = Double.class;
                info.name = "RightBiceps";
                break;
            case 8:
                info.type = Double.class;
                info.name = "RightForearm";
                break;
            case 9:
                info.type = Double.class;
                info.name = "RightUdo";
                break;
            case 10:
                info.type = BATimeDTO.class;
                info.name = "Time";
                break;

            case 11:
                info.type = Double.class;
                info.name = "Weight";
                break;
        }
        info.namespace= WcfConstans.ServiceNamespace;
    }

    

    @Override
    public void setProperty(int arg0, Object arg1) {
    }

    public boolean isEmpty() {
        return height == 0 && weight == 0 && leftBiceps == 0 && rightBiceps == 0 && klatka == 0 && rightUdo == 0 && leftUdo == 0 && rightForearm == 0 && leftForearm == 0 && pas == 0;
    }
}
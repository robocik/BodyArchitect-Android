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

import android.text.TextUtils;
import ch.lambdaj.function.matcher.Predicate;
import com.quasardevelopment.bodyarchitect.client.wcf.ReferencesManager;
import com.quasardevelopment.bodyarchitect.client.wcf.WcfConstans;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums.ExerciseDoneWay;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;

import static ch.lambdaj.Lambda.filter;

public class StrengthTrainingItemDTO extends BAGlobalObject implements KvmSerializable,Serializable,WcfInterfaces.IReferenceObject {
    
    public String comment;
    public ExerciseDoneWay doneWay=ExerciseDoneWay.Default;
    public ExerciseLightDTO exercise;
    public int position;
    public VectorSerieDTO series = new VectorSerieDTO();
    public StrengthTrainingEntryDTO strengthTrainingEntry;
    public String superSetGroup;
    public UUID trainingPlanItemId;
    
    public StrengthTrainingItemDTO(){}
    
    public StrengthTrainingItemDTO(SoapObject soapObject,ReferencesManager referencesTable)   throws Exception
    {
        if (soapObject == null)
            return;

        if (soapObject.hasAttribute("Id"))
        {
            Object obj = soapObject.getAttribute("Id");
            referencesTable.Add((String) obj, this);
        }

        if (soapObject.hasProperty("Comment"))
        {
            Object obj = soapObject.getProperty("Comment");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("Comment");
                comment = j.toString();
            }else if (obj!= null && obj instanceof String){
                comment = (String) soapObject.getProperty("Comment");
            }
        }
        if (soapObject.hasProperty("DoneWay"))
        {
            Object obj = soapObject.getProperty("DoneWay");
            if (obj!= null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("DoneWay");
                doneWay = ExerciseDoneWay.fromString(j.toString());
            }
        }
        if (soapObject.hasProperty("Exercise"))
        {
            SoapObject j = (SoapObject)soapObject.getProperty("Exercise");
            exercise = (ExerciseLightDTO)referencesTable.get(j,ExerciseLightDTO.class);
        }
        if (soapObject.hasProperty("Position"))
        {
            Object obj = soapObject.getProperty("Position");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("Position");
                position = Integer.parseInt(j.toString());
            }else if (obj!= null && obj instanceof Number){
                position = (Integer) soapObject.getProperty("Position");
            }
        }
        if (soapObject.hasProperty("Series"))
        {
            SoapObject j = (SoapObject)soapObject.getProperty("Series");
            series = new VectorSerieDTO(j,referencesTable);
        }
        if (soapObject.hasProperty("StrengthTrainingEntry"))
        {
            SoapObject j = (SoapObject)soapObject.getProperty("StrengthTrainingEntry");
            strengthTrainingEntry=(StrengthTrainingEntryDTO)referencesTable.get(j,StrengthTrainingEntryDTO.class);
        }
        if (soapObject.hasProperty("SuperSetGroup"))
        {
            Object obj = soapObject.getProperty("SuperSetGroup");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("SuperSetGroup");
                superSetGroup = j.toString();
            }else if (obj!= null && obj instanceof String){
                superSetGroup = (String) soapObject.getProperty("SuperSetGroup");
            }
        }
        if (soapObject.hasProperty("TrainingPlanItemId"))
        {
            Object obj = soapObject.getProperty("TrainingPlanItemId");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("TrainingPlanItemId");
                trainingPlanItemId = UUID.fromString(j.toString());
            }else if (obj!= null && obj instanceof String){
                trainingPlanItemId = UUID.fromString((String) soapObject.getProperty("TrainingPlanItemId"));
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
                return comment;
            case 2:
                return doneWay.toString();
            case 3:
                return exercise;
            case 4:
                return position;
            case 5:
                return series;
            case 6:
                return strengthTrainingEntry;
            case 7:
                return superSetGroup;
            case 8:
                return trainingPlanItemId;

        }
        return null;
    }
    
    @Override
    public int getPropertyCount() {
        return 9;
    }
    
    @Override
    public void getPropertyInfo(int index, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo info) {
        switch(index){
            case 0:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "GlobalId";
                break;
            case 1:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Comment";
                break;
            case 2:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "DoneWay";
                break;
            case 3:
                info.type = ExerciseLightDTO.class;
                info.name = "Exercise";
                break;
            case 4:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "Position";
                break;
            case 5:
                info.type = PropertyInfo.VECTOR_CLASS;
                info.name = "Series";
                break;
            case 6:
                info.type = StrengthTrainingEntryDTO.class;
                info.name = "StrengthTrainingEntry";
                break;
            case 7:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "SuperSetGroup";
                break;
            case 8:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "TrainingPlanItemId";
                break;

        }
        info.namespace= WcfConstans.ServiceNamespace;
    }

    

    @Override
    public void setProperty(int arg0, Object arg1) {
    }

    public List<StrengthTrainingItemDTO>  getJoinedItems()
    {
        List<StrengthTrainingItemDTO> items = new ArrayList<StrengthTrainingItemDTO>();

        if(!TextUtils.isEmpty(superSetGroup))
        {
            List<StrengthTrainingItemDTO> joinedItems= filter(new Predicate<StrengthTrainingItemDTO>() {
                public boolean apply(StrengthTrainingItemDTO item) {
                    return TextUtils.equals(item.superSetGroup,superSetGroup) && item!=StrengthTrainingItemDTO.this;
                }
            }, this.strengthTrainingEntry.entries);
            items.addAll(joinedItems);
        }
        return items;
    }


}

package com.quasardevelopment.bodyarchitect.client.wcf.envelopes;

import android.os.Build;
import com.quasardevelopment.bodyarchitect.client.model.Settings;
import com.quasardevelopment.bodyarchitect.client.util.Constants;
import com.quasardevelopment.bodyarchitect.client.wcf.WcfConstans;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.*;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums.PlatformType;

public class BAService
{
    IWsdl2CodeEvents callback;

    public BAService(IWsdl2CodeEvents callback)
    {
        this.callback=callback;
    }


	public static ClientInformation CreateClientInformation()
	{
		ClientInformation clientInfo = new ClientInformation();
		clientInfo.applicationVersion=Constants.Version;
		clientInfo.applicationLanguage= WcfConstans.getCurrentServiceLanguage();
		clientInfo.platform=PlatformType.Android;
		clientInfo.platformVersion=String.format("Android %s(%s)", Build.VERSION.RELEASE,android.os.Build.VERSION.SDK_INT);
		clientInfo.version="4.5.0.0";
		clientInfo.clientInstanceId=Settings.getClientInstanceId();
		return clientInfo;
	}
	
	public void Login(String username,String pwdHash)    throws Exception
	{
	    
//		RequestFactory requestFactory = new RequestFactoryImpl();
//		ClientInformation clientInfo=CreateClientInformation();
//		LoginEnvelope loginEnvelope = new LoginEnvelope(
//				clientInfo.applicationLanguage,clientInfo.applicationVersion,UUID.fromString(clientInfo.clientInstanceId),
//				clientInfo.platform.toString(),clientInfo.platformVersion,
//				clientInfo.version, username, pwdHash);
//
//
//
//    		SOAP11Request<LoginResponse> loginRequest = requestFactory.buildRequest(
//	    		    "http://test.bodyarchitectonline.com/V2/BodyArchitect.svc/WP7",
//	    		    loginEnvelope,
//	    		    "http://MYBASERVICE.TK/IBodyArchitectAccessService/Login",
//	    		    LoginResponse.class);
//	    	loginRequest.execute(loginObserver);

        BasicHttpBinding_IBodyArchitectAccessService service = new BasicHttpBinding_IBodyArchitectAccessService(callback,Settings.getEndPointUrl());
        service.LoginAsync(CreateClientInformation(),username,pwdHash);
	}


    public void GetProfileInformation(GetProfileInformationCriteria criteria) throws Exception
    {
        BasicHttpBinding_IBodyArchitectAccessService service = new BasicHttpBinding_IBodyArchitectAccessService(callback,Settings.getEndPointUrl());
        service.GetProfileInformationAsync(criteria);
    }

    public void GetTrainingDaysAsync(WorkoutDaysSearchCriteria searchCriteria,PartialRetrievingInfo retrievingInfo) throws Exception{
        BasicHttpBinding_IBodyArchitectAccessService service = new BasicHttpBinding_IBodyArchitectAccessService(callback,Settings.getEndPointUrl());
        service.GetTrainingDaysAsync(searchCriteria,retrievingInfo);
    }

}

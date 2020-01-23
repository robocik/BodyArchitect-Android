package com.quasardevelopment.bodyarchitect.client.model;

import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WymiaryDTO;
import org.joda.time.DateTime;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 20.05.13
 * Time: 09:30
 * To change this template use File | Settings | File Templates.
 */
public interface IPerson
{
    DateTime getBirthday();

    WS_Enums.Gender getGender();

    WymiaryDTO getWymiary();
}

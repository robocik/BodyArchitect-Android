package com.quasardevelopment.bodyarchitect.client.model;

import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.UserDTO;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 14.05.13
 * Time: 11:58
 * To change this template use File | Settings | File Templates.
 */
public interface IRatingable
{
    UUID getGlobalId();

    float getRating();

    String getName();

    Float getUserRating();

    String getUserShortComment();

    UserDTO getProfile();
}

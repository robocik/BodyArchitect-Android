package com.quasardevelopment.bodyarchitect.client.ui.controls;

import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 08.05.13
 * Time: 10:03
 * To change this template use File | Settings | File Templates.
 */
public interface MoodChangedListener {
    public void moodChanged(WS_Enums.Mood mood);
}

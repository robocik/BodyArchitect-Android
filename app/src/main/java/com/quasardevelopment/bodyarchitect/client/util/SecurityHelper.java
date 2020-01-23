package com.quasardevelopment.bodyarchitect.client.util;

import android.util.Base64;

import java.security.MessageDigest;

public class SecurityHelper {

    public static String SHA1(String text)  {
        try
        {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(text.getBytes("iso-8859-1"), 0, text.length());
            byte[] sha1hash = md.digest();
            return Base64.encodeToString(sha1hash,Base64.NO_WRAP);
        }
        catch(Exception ex)
        {
            return null;
        }
    }
}

package org.shutiak.lab8.utils;

import javax.servlet.http.HttpServletRequest;

public class TokenUtils {
    public static String getTokenFromRequest(final HttpServletRequest httpServletRequest){
        String rawCookie = httpServletRequest.getHeader("Cookie");
        if(rawCookie!=null)
        {String[] rawCookieParams = rawCookie.split(";");

            for(String rawCookieNameAndValue :rawCookieParams)
            {
                String[] rawCookieNameAndValuePair = rawCookieNameAndValue.split("=");
                if(rawCookieNameAndValuePair.length>1&&rawCookieNameAndValuePair[0].equals("Authorization")) {
                   return rawCookieNameAndValuePair[1];
                }
            }}
        return null;
    }
}
